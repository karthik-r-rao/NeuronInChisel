package neuron

import chisel3._

class MultiplyAccumulateInterfaceIn(dataWidth: Int) extends Bundle{
    val reset = Input(Bool())
    
    // data signals
    val bias_in = Input(Bits(dataWidth.W))
    val weight_in = Input(Bits(dataWidth.W))
    val data_in = Input(Bits(dataWidth.W))

    // control signals
    val bias_in_valid = Input(Bool())
    val weight_in_valid = Input(Bool())
    val data_in_valid = Input(Bool())
}

class MultiplyAccumulateInterfaceOut(dataWidth: Int) extends Bundle{
    // data signals
    val data_out = Output(Bits(dataWidth.W))

    // control signals
    val data_out_valid = Output(Bool())
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

    input_valid := io.mac_in.weight_in_valid & io.mac_in.data_in_valid

    when (io.mac_in.reset){
        acc := 0.U
    }

    when (input_valid){
        multiply := io.mac_in.weight_in * io.mac_in.data_in
        acc := acc + multiply
    }

    when (io.mac_in.bias_in_valid){
        acc := acc + io.mac_in.bias_in
        acc_valid := true.B
    }

    io.mac_out.data_out := acc
    io.mac_out.data_out_valid := acc_valid
}

object DriverMultiplyAccumulate extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new MultiplyAccumulate(10), Array("--target-dir", "generated/"))
}