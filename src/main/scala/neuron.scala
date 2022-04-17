package nn

import chisel3._
import chisel3.util._
import chisel3.util.log2Up
import chisel3.util.experimental.loadMemoryFromFile   
import firrtl.annotations.MemoryLoadFileType
import scala.io.Source


class Neuron(intWidth: Int, fracWidth: Int, sigmoidIntWidth: Int, sigmoidFracWidth: Int) extends Module{
    val io = IO(new Bundle{
        val neuron_in = new MultiplyAccumulateInterfaceIn(intWidth, fracWidth)
        val neuron_out = Output(UInt((intWidth + fracWidth).W))
    })

    val mac_inst = Module(new MultiplyAccumulate(intWidth, fracWidth))
    val sigmoid_inst = Module(new SigmoidLut(sigmoidIntWidth, sigmoidFracWidth))

    // make connections
    mac_inst.io.mac_in := io.neuron_in

    sigmoid_inst.io.addr := mac_inst.io.mac_out(2*fracWidth + sigmoidIntWidth - 1, 2*fracWidth - sigmoidFracWidth)

    io.neuron_out := Cat("b000".U, sigmoid_inst.io.dataOut, "b00000".U)
}

object DriverNeuron extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new Neuron(6, 12, 3, 7), Array("--target-dir", "generated/"))
}
