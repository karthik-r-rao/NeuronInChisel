package neuron

import chisel3._

class Neuron(memoryDepth: Int, dataWidth: Int) extends Module{
    val io = IO(new Bundle{
        val neuron_in = new MultiplyAccumulateInterfaceIn(dataWidth)
        val neuron_out = new SigmoidLutInterfaceOut(dataWidth)
    })

    val mac = Module(new MultiplyAccumulate(dataWidth))
    val sigmoid = Module(new SigmoidLut(memoryDepth, dataWidth))

    // make connections at input
    mac.io.mac_in := io.neuron_in

    // make connections at output
    io.neuron_out := sigmoid.io.sigmoid_lut_out

    // make connections between modules
    sigmoid.io.sigmoid_lut_in <> mac.io.mac_out
}