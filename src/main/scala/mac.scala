package nn

import chisel3._

class MultiplyAccumulateInterfaceIn(intWidth: Int, fracWidth: Int) extends Bundle{
    // reset MAC
    val rst = Input(Bool())

    // data signals
    val op1 = Input(new NNWireSigned(intWidth + fracWidth))
    val op2 = Input(new NNWireSigned(intWidth + fracWidth))
    val bias = Input(Bool())
}

class MultiplyAccumulate(intWidth: Int, fracWidth: Int) extends Module{
    val io = IO(new Bundle{
        val mac_in = new MultiplyAccumulateInterfaceIn(intWidth, fracWidth)
        val mac_out = Output(new NNWireSigned(2*intWidth + 2*fracWidth))
    })
    val multiply = Wire(SInt((2*intWidth + 2*fracWidth).W)) // multiplication; twice the number of bits
    val input_valid = Wire(Bool())
    val op1 = Wire(SInt((intWidth + fracWidth).W))
    val acc = Reg(SInt((2*intWidth + 2*fracWidth).W))
    val acc_valid = Reg(Bool())

    input_valid := io.mac_in.op1.valid & io.mac_in.op2.valid
    multiply := 0.S
    acc_valid := false.B

    when (io.mac_in.bias) {
        op1 := io.mac_in.op1.data << fracWidth
    }
    .otherwise {
        op1 := io.mac_in.op1.data
    }

    when (io.mac_in.rst){
        acc := 0.S
    }

    when (input_valid){
        multiply := op1.asSInt * io.mac_in.op2.data.asSInt
        acc := acc + multiply
        acc_valid := true.B
    }

    io.mac_out.data := acc
    io.mac_out.valid := acc_valid
}

object DriverMultiplyAccumulate extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new MultiplyAccumulate(6, 12), Array("--target-dir", "generated/"))
}