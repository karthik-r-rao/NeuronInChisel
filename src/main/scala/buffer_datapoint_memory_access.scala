package ControlUnit

import chisel3._
import chisel3.util._
import chisel3.util.log2Up
import chisel3.util.experimental.loadMemoryFromFile   
import firrtl.annotations.MemoryLoadFileType
import scala.io.Source

class bufferDatapointMemoryAccess(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int, tableDepth: Int) extends Module {
  
  val io = IO(new Bundle {
    val wrEna = Input(Bool())
    val Addr = Input(UInt(10.W))
    val dataIn = Input(UInt(datawidth.W))
    val rdData = Output(UInt(datawidth.W))
  
  })

  val mem = SyncReadMem(tableDepth, UInt(datawidth.W))

  io.rdData := 0.U(datawidth.W)
  
  val rdwrPort = mem(io.Addr)
  
  when (io.wrEna) { rdwrPort := io.dataIn }
    .otherwise    { io.rdData := rdwrPort }
}

object DriverBufferDatapointMemoryAccess extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new bufferDatapointMemoryAccess(8, 32, 16, 4, 1024), Array("--target-dir", "generated/"))
}
