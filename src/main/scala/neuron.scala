package nn

import chisel3._

class Neuron(intWidth: Int, fracWidth: Int) extends Module{
    val io = IO(new Bundle{
        val neuron_in = new MultiplyAccumulateInterfaceIn(intWidth, fracWidth)
        val neuron_out = Output(new NNWireSigned(intWidth + fracWidth))
    })

    val mac_inst = Module(new MultiplyAccumulate(intWidth, fracWidth))
    val sigmoid_inst = Module(new SigmoidCLA(intWidth, fracWidth))

    // make connections
    mac_inst.io.mac_in := io.neuron_in
    sigmoid_inst.io.sigmoid_cla_in := mac_inst.io.mac_out 
    io.neuron_out := sigmoid_inst.io.sigmoid_cla_out
}

object DriverNeuron extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new Neuron(6, 12), Array("--target-dir", "generated/"))
}