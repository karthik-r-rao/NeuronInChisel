package nn

import chisel3._

class SigmoidLut(intWidth: Int, fracWidth: Int) extends Module {
    val addressWidth = intWidth + fracWidth
    val dataWidth = intWidth + fracWidth
    val io = IO(new Bundle {
        val write = Input(Bool())   // write enable
        val addr = Input(UInt(addressWidth.W))  
        val dataIn = Input(UInt(dataWidth.W))   // write data
        val dataOut = Output(UInt(dataWidth.W)) // read data
    })

    val depth = scala.math.pow(2, addressWidth).toInt
    val mem = SyncReadMem(depth, UInt(dataWidth.W))
    
    io.dataOut := DontCare

    val rdwrPort = mem(io.addr)
    when (io.write) { 
        rdwrPort := io.dataIn
    }
    .otherwise { 
        io.dataOut := rdwrPort 
    }
}

object DriverSigmoidLut extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new SigmoidLut(3, 7), Array("--target-dir", "generated/"))
}