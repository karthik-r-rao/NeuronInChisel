package nn

import chisel3._

class MultiplyAccumulateInterfaceIn(dataWidth: Int) extends Bundle{
    // reset MAC
    val rst = Input(Bool())

    // data signals
    val bias = Input(new NNWire(dataWidth))
    val weight = Input(new NNWire(dataWidth))
    val x = Input(new NNWire(dataWidth))
}

class MultiplyAccumulateInterfaceOut(dataWidth: Int) extends Bundle{
    val y = Output(new NNWire(dataWidth))
}

class MultiplyAccumulate(dataWidth: Int) extends Module{
    val io = IO(new Bundle{
        val mac_in = new MultiplyAccumulateInterfaceIn(dataWidth)
        val mac_out = new MultiplyAccumulateInterfaceOut(dataWidth)
    })
    val multiply = Wire(Bits(dataWidth.W))
    val input_valid = Wire(Bool())
    val acc = RegInit(0.U(dataWidth.W))
    val acc_valid = RegInit(false.B)

    input_valid := io.mac_in.weight.valid & io.mac_in.x.valid
    multiply := 0.U

    when (io.mac_in.rst){
        acc := 0.U
    }

    when (input_valid){
        multiply := io.mac_in.weight.data * io.mac_in.x.data
        acc := acc + multiply
    }

    when (io.mac_in.bias.valid){
        acc := acc + io.mac_in.bias.data
        acc_valid := true.B
    }

    io.mac_out.y.data := acc
    io.mac_out.y.valid := acc_valid
}

object DriverMultiplyAccumulate extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new MultiplyAccumulate(10), Array("--target-dir", "generated/"))
}