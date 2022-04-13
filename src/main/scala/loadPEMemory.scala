//Loading PE memories

package ControlUnit

import chisel3._
import chisel3.util._
import chisel3.util.log2Up
import chisel3.util.experimental.loadMemoryFromFile   
import firrtl.annotations.MemoryLoadFileType
import scala.io.Source


class LoadPEMemoryInterfaceIn(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Bundle{
    val load_initial_weights = Input(Bool())

//  Loading Datapoint
    val load_datapoint = Input(Bool())
    val load_new_request = Input(Bool())
    val load_data_from_buffer = Input(Bool())
    val load_same_data = Input(Bool())

//  Loading Buffer Memory
    val loading_layer = Input(Bits(datawidth.W))
    val loading_length = Input(Bits(datawidth.W))
    val loading_activations = Input(Vec(numberOfPE, Bits(datawidth.W)))
    val write_memoryUnits = Input(Vec(numberOfPE, Bits(2.W)))
    val weight_buffer_load_request = Input(Bool())
    
//  Buffer Memory Output
    val buffer_memory_output = Input(Bits(datawidth.W))
    
//  Datapoint Memory Output
    val datapoint_memory_output = Input(Bits(datawidth.W))
    
    val new_datapoint_ready = Input(Bool())
    
//  Interconnect Input
    val interconnect_load_ready = Input(Bool())
    val interconnect_memory_output = Input(Bits(datawidth.W))

}

class LoadPEMemoryInterfaceOut(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Bundle{
    
    val load_initial_weights_complete = Output(Bool())    
    val load_datapoint_complete = Output(Bool())
    val load_buffer_weight_memory_complete = Output(Bool())
    
    val datapoint_memory_write_enable = Output(Bool())
    val datapoint_memory_write_data = Output(Bits(datawidth.W))
    val datapoint_memory_write_address = Output(Bits(datawidth.W))
    
    val buffer_memory_write_enable = Output(Bool())
    val buffer_memory_write_data = Output(Bits(datawidth.W))
    val buffer_memory_write_address = Output(Bits(datawidth.W))
    
    val weight_buffer_memory_write_enable = Output(Bool())
    val weight_buffer_memory_write_data = Output(Bits(datawidth.W))
    val weight_buffer_memory_write_address = Output(Bits(datawidth.W))
    
    val interconnect_loading_layer = Output(Bits(datawidth.W))
    val interconnect_loading_activation = Output(Bits(datawidth.W))
    val interconnect_load_request = Output(Bool())
    
    val interconnect_load_read_address = Output(Bits(datawidth.W))  
    
    val load_new_data_request = Output(Bool())
    
    val current_load_weights_state = Output(Bits(datawidth.W))
    val current_load_datapoint_state = Output(Bits(datawidth.W))


// Test signals
    val current_pe = Output(Bits(datawidth.W))

}

class LoadPEMemory(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Module{
    val io = IO(new Bundle{
        val load_pe_memory_in = new LoadPEMemoryInterfaceIn(memoryDepth, memoryHeight, datawidth, numberOfPE)
        val load_pe_memory_out = new LoadPEMemoryInterfaceOut(memoryDepth, memoryHeight, datawidth, numberOfPE)
    })


// INITIALIZE
    io.load_pe_memory_out.load_new_data_request := false.B
    io.load_pe_memory_out.datapoint_memory_write_enable := false.B
    io.load_pe_memory_out.datapoint_memory_write_data := 0.U(datawidth.W)
    io.load_pe_memory_out.datapoint_memory_write_address := 0.U(datawidth.W)
    
    io.load_pe_memory_out.buffer_memory_write_enable := false.B
    io.load_pe_memory_out.buffer_memory_write_address := 0.U(datawidth.W)
    io.load_pe_memory_out.buffer_memory_write_data := 0.U(datawidth.W)

    io.load_pe_memory_out.weight_buffer_memory_write_enable := false.B
    io.load_pe_memory_out.weight_buffer_memory_write_address := 0.U(datawidth.W)
    io.load_pe_memory_out.weight_buffer_memory_write_data := 0.U(datawidth.W)

    io.load_pe_memory_out.interconnect_loading_layer := 0.U(datawidth.W)
    io.load_pe_memory_out.interconnect_loading_activation := 0.U(datawidth.W)    
    io.load_pe_memory_out.interconnect_load_request := 0.U(1.W)
    
    io.load_pe_memory_out.interconnect_load_read_address := 0.U(datawidth.W)
    

    val idle::copy_buffer::reset_state::Nil = Enum(3)
    val copy_buffer_state = RegInit(idle)
    val current_copy_address = RegInit(0.U(datawidth.W))
    val current_save_address = RegInit(0.U(datawidth.W))
    val loading_length = RegInit(0.U(datawidth.W))
    val load_datapoint_complete = RegInit(0.U(1.W))
    

    val idle2::request_stage::weight_load_state::reset_state2::Nil = Enum(4)
    
    val current_state = RegInit(idle2)
    
    val current_pe = RegInit(0.U(datawidth.W))
    val next_pe = WireDefault(0.U(datawidth.W))

    val load_initial_weights_complete = RegInit(0.U(1.W))
    val load_buffer_weight_memory_complete = RegInit(0.U(1.W))
    val loading_layer = RegInit(0.U(datawidth.W))
    
    io.load_pe_memory_out.current_load_weights_state := 0.U(datawidth.W)
    io.load_pe_memory_out.current_load_datapoint_state := 0.U(datawidth.W)
    
    
    
    	switch(current_state){
    	    is(idle2){
    	    	load_initial_weights_complete := false.B
    	    	load_buffer_weight_memory_complete := false.B


    	    	io.load_pe_memory_out.current_load_weights_state := 0.U(datawidth.W)
    	    	
    	    	current_pe := 0.U(datawidth.W)
    	    	
	        io.load_pe_memory_out.interconnect_loading_layer := 0.U(datawidth.W)
    	        io.load_pe_memory_out.interconnect_loading_activation := 0.U(datawidth.W)    
    		io.load_pe_memory_out.interconnect_load_request := 0.U(1.W)
    		
    		io.load_pe_memory_out.interconnect_load_read_address := 0.U(datawidth.W)
    	    	
    	    	
    	    	when(io.load_pe_memory_in.load_initial_weights || io.load_pe_memory_in.weight_buffer_load_request){
    	    	    loading_layer := io.load_pe_memory_in.loading_layer
    	    	    loading_length := io.load_pe_memory_in.loading_length
    	    	    current_state := request_stage
    	    	}
    	    }
    	    
    	    is(request_stage){
    	    	io.load_pe_memory_out.current_load_weights_state := 1.U(datawidth.W)
    	    	
    	    	next_pe := current_pe + 1.U(datawidth.W)
    	    	
    	    	current_copy_address := 0.U(datawidth.W)
    	    	current_save_address := 0.U(datawidth.W)


    	    	io.load_pe_memory_out.interconnect_load_read_address := 0.U(datawidth.W)
    	    	io.load_pe_memory_out.weight_buffer_memory_write_address := 0.U(datawidth.W)
    	    	io.load_pe_memory_out.weight_buffer_memory_write_enable := false.B
    	    	    
    	    	io.load_pe_memory_out.weight_buffer_memory_write_data := 0.U(datawidth.W)
    	    	
    	    	    	    	
    	    	when(io.load_pe_memory_in.write_memoryUnits(current_pe) === 0.U(2.W)){
    	    	    current_state := reset_state2
    	    	    when(io.load_pe_memory_in.load_initial_weights){
    	    	    	load_initial_weights_complete := true.B
    	    	    } .otherwise{
    	    	    	load_buffer_weight_memory_complete := true.B
    	    	    }
    	    	    
    	    	} .otherwise{
    	    	    when(io.load_pe_memory_in.load_initial_weights){    	    	
	            	io.load_pe_memory_out.interconnect_loading_layer := 2.U(datawidth.W)
    	            	io.load_pe_memory_out.interconnect_loading_activation := next_pe
    	            	io.load_pe_memory_out.interconnect_load_request := 1.U(1.W)
    	    	    } .otherwise{
	            	io.load_pe_memory_out.interconnect_loading_layer := loading_layer
    	            	io.load_pe_memory_out.interconnect_loading_activation := io.load_pe_memory_in.loading_activations(current_pe)
    	            	io.load_pe_memory_out.interconnect_load_request := 1.U(1.W)    	    	
    	    	    }
    	    	    
    	    	    when(io.load_pe_memory_in.interconnect_load_ready){
    	    	    	current_state := weight_load_state
    	    	    	
    	    	    	current_pe := current_pe + 1.U(datawidth.W)

		    	io.load_pe_memory_out.interconnect_loading_layer := 0.U(datawidth.W)
    	            	io.load_pe_memory_out.interconnect_loading_activation := 0.U(datawidth.W)
    	            	io.load_pe_memory_out.interconnect_load_request := 0.U(1.W)
    	    	    }

    	    	}
    	    }
    	    
    	    is(weight_load_state){
    	    	
    	    	io.load_pe_memory_out.current_load_weights_state := 2.U(datawidth.W)
    	    	
    	    	
    	    	io.load_pe_memory_out.interconnect_load_read_address := current_copy_address
    	    	io.load_pe_memory_out.weight_buffer_memory_write_address := current_save_address
    	    	io.load_pe_memory_out.weight_buffer_memory_write_enable := true.B
    	    	    
    	    	io.load_pe_memory_out.weight_buffer_memory_write_data := io.load_pe_memory_in.interconnect_memory_output
    	    	
    	    	current_copy_address := current_copy_address + 1.U(datawidth.W)
    	    	current_save_address := current_copy_address
    	    	    
    	    	    	    	
    	    	when(current_copy_address === loading_length)
    	    	{   when(current_pe === numberOfPE.U(datawidth.W))
    	    	    {
    	    	    	when(io.load_pe_memory_in.load_initial_weights){
    	    	    	    load_initial_weights_complete := true.B
    	    	    	} .otherwise{
    	    	    	    load_buffer_weight_memory_complete := true.B
    	    	    	}
    	    	    	current_state := reset_state2
    	    	    	
    	    	    } .otherwise{
    	    	    	current_state := request_stage
    	    	    }
    	    	} .otherwise{
    	    	    current_state := weight_load_state
    	    	}
    	    	
    	    }
    	    
    	    
    	    is(reset_state2){

    	    	io.load_pe_memory_out.current_load_weights_state := 3.U(datawidth.W)


    	    	load_initial_weights_complete := false.B
    	    	load_buffer_weight_memory_complete := false.B
    	    
    	    	when((io.load_pe_memory_in.load_initial_weights === false.B)  && (io.load_pe_memory_in.weight_buffer_load_request === false.B)){
    	    	    current_state := idle2
    	    	}
    	    }
    	
    	}
    
   when(io.load_pe_memory_in.load_datapoint){
    	
    	when(io.load_pe_memory_in.load_new_request){
	    io.load_pe_memory_out.load_new_data_request := true.B
	    	 	
    	} 
    	
    	when(io.load_pe_memory_in.load_data_from_buffer || io.load_pe_memory_in.new_datapoint_ready){
    	    switch(copy_buffer_state){
    	    	is(idle){
	            io.load_pe_memory_out.current_load_datapoint_state := 0.U(datawidth.W)

    	    	
    	    	    current_copy_address := 0.U(datawidth.W)
    	    	    current_save_address := 0.U(datawidth.W)
    	    	    load_datapoint_complete := false.B

    	    	    io.load_pe_memory_out.buffer_memory_write_address := 0.U(datawidth.W)
    	    	    io.load_pe_memory_out.datapoint_memory_write_address := 0.U(datawidth.W)
    	    	    io.load_pe_memory_out.datapoint_memory_write_enable := false.B
    	    	    
    	    	    io.load_pe_memory_out.datapoint_memory_write_data := 0.U(datawidth.W)
    	    	    
    	    	    when(io.load_pe_memory_in.load_data_from_buffer){
    	    	    	loading_length := io.load_pe_memory_in.loading_length
    	    	    	copy_buffer_state := copy_buffer
    	    	    }
    	    	}
    	    	
    	    	is(copy_buffer){
		    io.load_pe_memory_out.current_load_datapoint_state := 1.U(datawidth.W)

    	    	    when(io.load_pe_memory_in.load_data_from_buffer){
    	    	    	io.load_pe_memory_out.buffer_memory_write_address := current_copy_address
    	    	        io.load_pe_memory_out.datapoint_memory_write_data := io.load_pe_memory_in.buffer_memory_output
    	    	    } .otherwise{
    	    	    	io.load_pe_memory_out.interconnect_load_read_address := current_copy_address
    	    	    	io.load_pe_memory_out.datapoint_memory_write_data := io.load_pe_memory_in.interconnect_memory_output    	    	    	
    	    	    }
    	    	    
    	    	    io.load_pe_memory_out.datapoint_memory_write_address := current_save_address
    	    	    io.load_pe_memory_out.datapoint_memory_write_enable := true.B
    	    	        	    	    
    	    	    current_copy_address := current_copy_address + 1.U(datawidth.W)
    	    	    current_save_address := current_copy_address
    	    	    
    	    	    when(current_copy_address === loading_length){
    	    	    	load_datapoint_complete := true.B
    	    	    	copy_buffer_state := reset_state
    	    	    }
    	    	}
    	    	
    	    	is(reset_state){
		    io.load_pe_memory_out.current_load_datapoint_state := 2.U(datawidth.W)

    	    	    load_datapoint_complete := false.B

    	    	    io.load_pe_memory_out.buffer_memory_write_address := 0.U(datawidth.W)
    	    	    io.load_pe_memory_out.datapoint_memory_write_address := 0.U(datawidth.W)
    	    	    io.load_pe_memory_out.datapoint_memory_write_enable := false.B

    	    	    when(io.load_pe_memory_in.load_datapoint === false.B){
    	    	    	copy_buffer_state := idle
    	    	    }
    	    	}
    	    
    	    }
    	
    	} .elsewhen(io.load_pe_memory_in.load_same_data){
    	    load_datapoint_complete := true.B
    	    when(io.load_pe_memory_in.load_datapoint === false.B){
    	    	load_datapoint_complete := false.B
    	    }
    	} 
    
    
    }


io.load_pe_memory_out.load_datapoint_complete := load_datapoint_complete
io.load_pe_memory_out.load_initial_weights_complete := load_initial_weights_complete
io.load_pe_memory_out.load_buffer_weight_memory_complete := load_buffer_weight_memory_complete
io.load_pe_memory_out.current_pe := current_pe

}

object DriverLoadPEMemory extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new LoadPEMemory(8, 32, 16, 4), Array("--target-dir", "generated/"))
}
