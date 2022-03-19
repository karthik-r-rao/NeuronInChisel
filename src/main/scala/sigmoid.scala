package nn

import chisel3._

class SigmoidLut(addressWidth: Int, dataWidth: Int) extends Module {
    val io = IO(new Bundle {
        val enable = Input(Bool())
        val write = Input(Bool())
        val addr = Input(UInt(addressWidth.W))
        val dataIn = Input(UInt(dataWidth.W))
        val dataOut = Output(UInt(dataWidth.W))
    })

    val depth = scala.math.pow(2, addressWidth).toInt
    val mem = SyncReadMem(depth, UInt(dataWidth.W))
    
    io.dataOut := DontCare

    when(io.enable) {
        val rdwrPort = mem(io.addr)
        when (io.write) { 
            rdwrPort := io.dataIn
        }
        .otherwise { 
            io.dataOut := rdwrPort 
        }
    }
}

object DriverSigmoidLut extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new SigmoidLut(10, 10), Array("--target-dir", "generated/"))
}