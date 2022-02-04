package nn

import chisel3._

/*
    Ref: Simple approximation of sigmoidal functions: 
    realistic design of digital neural networks capable of learning
*/

class SigmoidCLA(intWidth: Int, fracWidth: Int) extends Module{
    val io = IO(new Bundle{
        val sigmoid_cla_in = Input(new NNWire(intWidth + fracWidth))
        val sigmoid_cla_out = Output(new NNWire(intWidth + fracWidth))
    })

    // pipe regs
    // stage0
    val i0 = RegInit(0.U(intWidth.W))
    val f0 = RegInit(0.U(fracWidth.W))
    val in_valid0 = RegNext(io.sigmoid_cla_in.valid)

    // stage1
    val out1 = RegInit(0.U((intWidth + fracWidth).W))
    val out_valid1 = RegNext(in_valid0)
    
    // wires
    val y = Wire(Bits((intWidth + fracWidth).W))
    val i = Wire(Bits(intWidth.W))
    val f = Wire(Bits(fracWidth.W))
    val sign = Wire(Bool())

    val one = scala.math.pow(2, fracWidth).toInt.U((intWidth + fracWidth).W)

    sign := Mux(io.sigmoid_cla_in.data(intWidth + fracWidth - 1), true.B, false.B)
    i := io.sigmoid_cla_in.data(intWidth + fracWidth - 1, fracWidth)
    f := io.sigmoid_cla_in.data(fracWidth - 1, 0)

    i0 := Mux(sign, (~i + 1.U)(intWidth - 1, 0), i)
    f0 := Mux(sign, (one - f)(fracWidth - 1, 0), f)

    y := (one - (f0 >> 1.U))(fracWidth - 1, 0) >> (i0)

    when (in_valid0){
        out1 := Mux(sign, y, one - (y >> 1.U))
        out_valid1 := true.B
    } otherwise{
        out1 := 0.U
        out_valid1 := false.B
    }

    io.sigmoid_cla_out.data := out1
    io.sigmoid_cla_out.valid := out_valid1
}

object DriverSigmoidCLA extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new SigmoidCLA(6, 12), Array("--target-dir", "generated/"))
}