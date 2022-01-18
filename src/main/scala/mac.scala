package neuron

import chisel3._

class DataInterface(dataWidth: Int) extends Bundle{
    val bias_in = Input(Bits(dataWidth.W))
    val weight_in = Input(Bits(dataWidth.W))
    val data_in = Input(Bits(dataWidth.W))
    val data_out = Output(Bits(dataWidth.W))
}

class ControlInterface extends Bundle{
    val reset = Input(Bool())
    val bias_in_valid = Input(Bool())
    val weight_in_valid = Input(Bool())
    val data_in_valid = Input(Bool())
    val data_out_valid = Output(Bool())
}

class MultiplyAccumulateInterface(dataWidth: Int) extends Bundle{
    val data_io = new DataInterface(dataWidth)
    val control_io = new ControlInterface()
}

class MultiplyAccumulate(dataWidth: Int) extends Module{
    val io = IO(new MultiplyAccumulateInterface(dataWidth))
    val multiply = WireDefault(0.U(dataWidth.W))
    val input_valid = Wire(Bool())
    val acc = RegInit(0.U(dataWidth.W))
    val acc_valid = RegInit(false.B)

    input_valid := io.control_io.weight_in_valid & io.control_io.data_in_valid

    when (io.control_io.reset){
        acc := 0.U
    }

    when (input_valid){
        multiply := io.data_io.weight_in * io.data_io.data_in
        acc := acc + multiply
    }

    when (io.control_io.bias_in_valid){
        acc := acc + io.data_io.bias_in
        acc_valid := true.B
    }

    io.data_io.data_out := acc
    io.control_io.data_out_valid := acc_valid
}

object DriverMultiplyAccumulate extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new MultiplyAccumulate(10), Array("--target-dir", "generated/"))
}