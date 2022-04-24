package ControlUnit

import chisel3._
import chisel3.util._
import chisel3.util.log2Up
import chisel3.util.experimental.loadMemoryFromFile   
import firrtl.annotations.MemoryLoadFileType
import scala.io.Source

class NNDescriptionTableAccess(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int, tableDepth: Int) extends Module {
  
  val io = IO(new Bundle {
    val wrEna = Input(Bool())
    val Addr = Input(UInt(10.W))
    val dataIn = Input(UInt(datawidth.W))
    val rdData = Output(UInt(datawidth.W))
  
  })

  val mem = SyncReadMem(tableDepth, UInt(datawidth.W))
  val path = getClass.getResource("/nn_description_table2.txt").getPath
  loadMemoryFromFile(mem, path, MemoryLoadFileType.Binary)  

  io.rdData := 0.U(datawidth.W)
  
  val rdwrPort = mem(io.Addr)
  
  when (io.wrEna) { rdwrPort := io.dataIn }
    .otherwise    { io.rdData := rdwrPort }
}

object DriverNN_description_table_access extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new NNDescriptionTableAccess(8, 32, 16, 4, 32), Array("--target-dir", "generated/"))
}
