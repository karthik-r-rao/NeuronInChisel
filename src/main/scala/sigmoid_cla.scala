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

    val y = Wire(Bits((intWidth + fracWidth).W))
    val y_valid = Wire(Bool())
    val i = Wire(Bits((intWidth).W))
    val f = Wire(Bits(fracWidth.W))
    val i1 = Wire(Bits((intWidth).W))
    val f1 = Wire(Bits(fracWidth.W))
    val sign = Wire(Bool())
    

    val one_f = scala.math.pow(2, fracWidth).toInt.U((fracWidth + 1).W)
    val one = scala.math.pow(2, fracWidth).toInt.U((intWidth + fracWidth).W)

    y := 0.U
    y_valid := false.B
    sign := Mux(io.sigmoid_cla_in.data(intWidth + fracWidth - 1), true.B, false.B)
    i := io.sigmoid_cla_in.data(intWidth + fracWidth - 1, fracWidth)
    f := io.sigmoid_cla_in.data(fracWidth - 1, 0)

    i1 := Mux(sign, ~i + 1.U, i)
    f1 := Mux(sign, one_f - f, f)

    when (io.sigmoid_cla_in.valid){
        y := (one_f - f1) >> (i1)
        y_valid := true.B
    }

    io.sigmoid_cla_out.data := Mux(sign, y, one - (y >> 1.U))
    io.sigmoid_cla_out.valid := y_valid
}

object DriverSigmoidCLA extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new SigmoidCLA(16, 16), Array("--target-dir", "generated/"))
}