package ControlUnit

import chisel3._
import chisel3.util._
import chisel3.util.log2Up
import chisel3.util.experimental.loadMemoryFromFile   
import firrtl.annotations.MemoryLoadFileType
import scala.io.Source


class SavePEOutputInterfaceIn(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Bundle{
    val save_data_request = Input(Bool())
    val current_buffer_memory_pointer = Input(Bits(datawidth.W))
    
    val PE_outputs = Input(Vec(numberOfPE, UInt(datawidth.W)))

}

class SavePEOutputInterfaceOut(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Bundle{
    val save_data_complete = Output(Bool())

    val buffer_memory_write_enable = Output(Bool())
    val buffer_memory_write_address = Output(Bits(datawidth.W))
    val buffer_memory_write_data = Output(UInt(datawidth.W))

}

class SavePEOutput(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Module{
    val io = IO(new Bundle{
        val save_pe_output_in = new SavePEOutputInterfaceIn(memoryDepth, memoryHeight, datawidth, numberOfPE)
        val save_pe_output_out = new SavePEOutputInterfaceOut(memoryDepth, memoryHeight, datawidth, numberOfPE)
    })


    val current_save_stage = RegInit(0.U(datawidth.W))
    
    val idle::save_to_buffer::reset_state::Nil = Enum(3)
    val curr_state = RegInit(idle)
    
    val save_data_complete = RegInit(0.U(1.W))
    val buffer_memory_pointer = RegInit(0.U(datawidth.W))
    val max_iter = RegInit(0.U(datawidth.W))
    
    io.save_pe_output_out.buffer_memory_write_enable := false.B
    io.save_pe_output_out.buffer_memory_write_address := 0.U(datawidth.W)
    io.save_pe_output_out.buffer_memory_write_data := 0.U(datawidth.W)
    
    switch(curr_state){
    	is(idle){
	    io.save_pe_output_out.buffer_memory_write_enable := false.B
    	    io.save_pe_output_out.buffer_memory_write_address := 0.U(datawidth.W)
	    io.save_pe_output_out.buffer_memory_write_data := 0.U(datawidth.W)

    	    save_data_complete := false.B
    	    current_save_stage := 0.U(datawidth.W)
    	    max_iter := numberOfPE.U(datawidth.W) - 1.U(datawidth.W)
    	    
    	    when(io.save_pe_output_in.save_data_request){
    	    	curr_state := save_to_buffer
    	    	buffer_memory_pointer := io.save_pe_output_in.current_buffer_memory_pointer
    	    }
    
    	}
    	
    	is(save_to_buffer){
    	    current_save_stage := current_save_stage + 1.U(datawidth.W)
    	    io.save_pe_output_out.buffer_memory_write_enable := true.B
    	    io.save_pe_output_out.buffer_memory_write_address := buffer_memory_pointer + current_save_stage
    	    io.save_pe_output_out.buffer_memory_write_data := io.save_pe_output_in.PE_outputs(current_save_stage)
    	    
    	    when(current_save_stage === max_iter){
    	    	save_data_complete := true.B
    	    	curr_state := reset_state
    	    }
    	}
    	
    	is(reset_state){
	    io.save_pe_output_out.buffer_memory_write_enable := false.B
    	    io.save_pe_output_out.buffer_memory_write_address := 0.U(datawidth.W)
	    io.save_pe_output_out.buffer_memory_write_data := 0.U(datawidth.W)

    	    save_data_complete := true.B

    	    when(io.save_pe_output_in.save_data_request === false.B){
    	    	curr_state := idle
    	    	save_data_complete := false.B

    	    }
    	}
    }
    
    io.save_pe_output_out.save_data_complete := save_data_complete
}

object DriverSavePEOutput extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new SavePEOutput(8, 32, 16, 4), Array("--target-dir", "generated/"))
}
