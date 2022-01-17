package sigmoid

import chisel3._
import chisel3.util.experimental.loadMemoryFromFile   
import firrtl.annotations.MemoryLoadFileType

class SigmoidLut(memoryDepth: Int, memoryWidth: Int) extends Module{
    val io = IO(new Bundle{
        val address = Input(Bits(memoryWidth.W))
        val value = Output(Bits(memoryWidth.W))
    })

    val memory = SyncReadMem(memoryDepth, Bits(memoryWidth.W))
    io.value := memory(io.address)
    loadMemoryFromFile(memory, "/home/karthikrrao/NeuronInChisel/scripts/lut.txt", MemoryLoadFileType.Binary)  
}

object DriverSigmoidLut extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new SigmoidLut(1024, 10), Array("--target-dir", "generated/"))
}