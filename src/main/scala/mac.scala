package neuron

import chisel3._

class MultiplyAccumulateInterfaceIn(dataWidth: Int) extends Bundle{
    val reset = Input(Bool())
    
    // data signals
    val bias = Input(Bits(dataWidth.W))
    val weight = Input(Bits(dataWidth.W))
    val data = Input(Bits(dataWidth.W))

    // control signals
    val bias_valid = Input(Bool())
    val weight_valid = Input(Bool())
    val data_valid = Input(Bool())
}

class MultiplyAccumulateInterfaceOut(dataWidth: Int) extends Bundle{
    // data signals
    val data = Output(Bits(dataWidth.W))

    // control signals
    val valid = Output(Bool())
}

class MultiplyAccumulate(dataWidth: Int) extends Module{
    val io = IO(new Bundle{
        val mac_in = new MultiplyAccumulateInterfaceIn(dataWidth)
        val mac_out = new MultiplyAccumulateInterfaceOut(dataWidth)
    })
    val multiply = WireDefault(0.U(dataWidth.W))
    val input_valid = Wire(Bool())
    val acc = RegInit(0.U(dataWidth.W))
    val acc_valid = RegInit(false.B)

    input_valid := io.mac_in.weight_valid & io.mac_in.data_valid

    when (io.mac_in.reset){
        acc := 0.U
    }

    when (input_valid){
        multiply := io.mac_in.weight * io.mac_in.data
        acc := acc + multiply
    }

    when (io.mac_in.bias_valid){
        acc := acc + io.mac_in.bias
        acc_valid := true.B
    }

    io.mac_out.data := acc
    io.mac_out.valid := acc_valid
}

object DriverMultiplyAccumulate extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new MultiplyAccumulate(10), Array("--target-dir", "generated/"))
}