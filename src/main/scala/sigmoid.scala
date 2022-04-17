package nn

import chisel3._
import chisel3.util.experimental.loadMemoryFromFile   
import firrtl.annotations.MemoryLoadFileType
import scala.io.Source

class SigmoidLut(intWidth: Int, fracWidth: Int) extends Module {
    val addressWidth = intWidth + fracWidth
    val dataWidth = intWidth + fracWidth
    val io = IO(new Bundle {
        val addr = Input(UInt(addressWidth.W))  
        val dataOut = Output(SInt(dataWidth.W)) // read data
    })

    val depth = scala.math.pow(2, addressWidth).toInt
    val mem = SyncReadMem(depth, SInt(dataWidth.W))
    val path = getClass.getResource("/sigmoidlut.txt").getPath
    loadMemoryFromFile(mem, path, MemoryLoadFileType.Binary) 
    
    io.dataOut := mem(io.addr)
}

object DriverSigmoidLut extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new SigmoidLut(3, 7), Array("--target-dir", "generated/"))
}
