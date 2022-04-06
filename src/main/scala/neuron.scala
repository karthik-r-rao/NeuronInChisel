package nn

import chisel3._

class Neuron(intWidth: Int, fracWidth: Int, sigmoidIntWidth: Int, sigmoidFracWidth: Int) extends Module{
    val io = IO(new Bundle{
        val neuron_in = new MultiplyAccumulateInterfaceIn(intWidth, fracWidth)
        val write = Input(Bool())   // write enable
        val w_addr = Input(UInt((sigmoidIntWidth + sigmoidFracWidth).W))    // write address
        val w_dataIn = Input(UInt((sigmoidIntWidth + sigmoidFracWidth).W))  // write data
        val neuron_out = Output(UInt((sigmoidIntWidth + sigmoidFracWidth).W))
    })

    val mac_inst = Module(new MultiplyAccumulate(intWidth, fracWidth))
    val sigmoid_inst = Module(new SigmoidLut(sigmoidIntWidth, sigmoidFracWidth))

    // make connections
    mac_inst.io.mac_in := io.neuron_in

    sigmoid_inst.io.addr := Mux(io.write, io.w_addr, mac_inst.io.mac_out(2*fracWidth + sigmoidIntWidth - 1, 2*fracWidth - sigmoidFracWidth)) 
    sigmoid_inst.io.write := io.write
    sigmoid_inst.io.dataIn := io.w_dataIn

    io.neuron_out := sigmoid_inst.io.dataOut
}

object DriverNeuron extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new Neuron(6, 12, 3, 7), Array("--target-dir", "generated/"))
}