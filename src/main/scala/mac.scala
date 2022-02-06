package nn

import chisel3._

class MultiplyAccumulateInterfaceIn(intWidth: Int, fracWidth: Int) extends Bundle{
    // reset MAC
    val rst = Input(Bool())

    // data signals
    val bias = Input(new NNWireSigned(intWidth + fracWidth))
    val weight = Input(new NNWireSigned(intWidth + fracWidth))
    val x = Input(new NNWireSigned(intWidth + fracWidth))
}

class MultiplyAccumulateInterfaceOut(intWidth: Int, fracWidth: Int) extends Bundle{
    val y = Output(new NNWireSigned(intWidth + fracWidth))
}

class MultiplyAccumulate(intWidth: Int, fracWidth: Int) extends Module{
    val io = IO(new Bundle{
        val mac_in = new MultiplyAccumulateInterfaceIn(intWidth, fracWidth)
        val mac_out = new MultiplyAccumulateInterfaceOut(intWidth, fracWidth)
    })
    val multiply = Wire(SInt((2*intWidth + 2*fracWidth).W)) // multiplication; twice the number of bits
    val input_valid = Wire(Bool())
    val acc = Reg(SInt((intWidth + fracWidth).W))
    val acc_valid = Reg(Bool())

    input_valid := io.mac_in.weight.valid & io.mac_in.x.valid
    multiply := 0.S

    when (io.mac_in.rst){
        acc := 0.S
    }

    when (input_valid){
        multiply := io.mac_in.weight.data.asSInt * io.mac_in.x.data.asSInt
        acc := acc + multiply(2*fracWidth + intWidth - 1 , fracWidth).asSInt // remove underflow and overflow in fixed point multiplication
    }

    when (io.mac_in.bias.valid){
        acc := acc + io.mac_in.bias.data.asSInt
        acc_valid := true.B
    } otherwise{
        acc_valid := false.B
    }

    io.mac_out.y.data := acc
    io.mac_out.y.valid := acc_valid
}

object DriverMultiplyAccumulate extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new MultiplyAccumulate(6, 12), Array("--target-dir", "generated/"))
}