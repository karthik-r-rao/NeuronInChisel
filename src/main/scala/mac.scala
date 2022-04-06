package nn

import chisel3._

class MultiplyAccumulateInterfaceIn(intWidth: Int, fracWidth: Int) extends Bundle{
    // reset MAC
    val rst = Input(Bool())

    // data signals
    val op1 = Input(SInt((intWidth + fracWidth).W))
    val op2 = Input(SInt((intWidth + fracWidth).W))

    // bias valid 
    val bias = Input(Bool())
}

class MultiplyAccumulate(intWidth: Int, fracWidth: Int) extends Module{
    val io = IO(new Bundle{
        val mac_in = new MultiplyAccumulateInterfaceIn(intWidth, fracWidth)
        val mac_out = Output(SInt((2*intWidth + 2*fracWidth).W))
    })

    // wires
    val multiply = Wire(SInt((2*intWidth + 2*fracWidth).W)) // multiplication; twice the number of bits
    val op1 = Wire(SInt((intWidth + fracWidth).W))

    // registers
    val acc = Reg(SInt((2*intWidth + 2*fracWidth).W))

    multiply := 0.S

    when (io.mac_in.bias) {
        op1 := io.mac_in.op1 << fracWidth  // shift to make 1 in fixed point 
    }
    .otherwise {
        op1 := io.mac_in.op1
    }

    when (io.mac_in.rst){
        acc := 0.S  // reset MAC
    }
    .otherwise {
        multiply := op1.asSInt * io.mac_in.op2.asSInt
        acc := acc + multiply
    }

    io.mac_out := acc
}

object DriverMultiplyAccumulate extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new MultiplyAccumulate(6, 12), Array("--target-dir", "generated/"))
}