package ControlUnit

import chisel3._
import chisel3.util.log2Up

class ControllerInterfaceIn(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Bundle{
    
    //control Inputs
    val controller_reset = Input(Bool())
    val address_generation_complete = Input(Bool())
    val load_weight_memory_complete = Input(Bool())
    
    
    val load_datapoint_complete = Input(Bool())
    
    
    val datapoint_write_data = Input(Bits(datawidth.W))
    val datapoint_write_valid = Input(Bool())
    
}




class ControllerInterfaceOut(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Bundle{
    
    // data signals
    val datapoint_request_data = Output(Bool())
    val datapoint_write_valid = Output(Bool())
    
    val datapoint_write_data = Output(Bits(datawidth.W))
    
    val read_memoryUnits = Input(Vec(numberOfPE, Bits(2.W)))
    val write_memoryUnits = Input(Vec(numberOfPE, Bits(2.W)))
    
    val max_iteration = Output(Bits(datawidth.W))
    
    //
    val load_datapoint = Output(Bool())
    val load_new_request = Output(Bool())
    val 
        
    // control signals
    val address_generator_address_valid = Output(Bool())
    val address_generator_enable_valid = Output(Bool())
    val address_generator_datapoint_write_valid = Output(Bool())
    val address_generator_reset = Output(Bool())
}




class Controller(memoryDepth: Int, memoryHeight: Int, datawidth: Int) extends Module{
    val io = IO(new Bundle{
        val controller_in = new ControllerInterfaceIn(memoryDepth, memoryHeight, datawidth)
        val controller_out = new ControllerInterfaceOut(memoryDepth, memoryHeight, datawidth)
    })
    

// STATE MACHINE STATE
    val idle::load_data::start_compute::save_data::Nil = Enum(4)
    
    val curr_state = RegInit(idle)
    val next_state = RegInit(idle)
    


// STORING CONTROL VALUES: 	CURRENT_LAYER - LAYER IN WHICH CURRENT ACTIVATION IS COMPUTED
//			    	LOADING_LAYER - LAYER FOR WHICH NEXT SET OF ACTIVATION WEIGHTS ARE LOADED TO BUFFER MEMORY
//			    	MAX_LAYER     - TOTAL LAYERS IN THE NEURAL NETWORK

    val current_layer = RegInit(0.U(datawidth.W))
    val loading_layer = RegInit(0.U(datawidth.W))
    val max_layer = RegInit(0.U(datawidth.W)) //Load from table
    

//				CURRENT_LAYER_TOTAL_ACTIVATIONS 	- TOTAL ACTIVATIONS TO BE COMPUTED IN THE CURRENT LAYER
//				CURRENT_LAYER_CURRENT_ACTIVATION 	- CURRENT ACTIVATION COMPUTED BY THE LAST PE
//				CURRENT_LAYER_ACTIVATIONS_COMPUTED 	- TOTAL ACTIVATIONS COMPUTED IN THE CURRENT ITERATION, CAN BE NO_OF_PES OR LESS THAN THAT
//				CURRENT_BUFFER_MEMORY_POINTER 	- POINTER TO LOAD COMPUTED OUTPUT VALUES TO A BUFFER MEMORY

    val current_layer_total_activations = RegInit(0.U(datawidth.W))
    val current_layer_current_activation = RegInit(0.U(datawidth.W))
    val current_activations_computed = RegInit(0.U(datawidth.W))
    val current_buffer_memory_pointer = RegInit(0.U(datawidth.W))

//				CURRENT_LOADING_ACTIVATION		- CURRENT ACTIVATION FOR WHICH THE WEIGHT IS LOADED TO THE BUFFER MEMORY
//				LOADING_LAYER_TOTAL_ACTIVATIONS	- TOTAL ACTIVATIONS IN THE LAYER WHERE LOADING OF WEIGHTS TO BUFFER MEMORY IS HAPPENING
    
    val current_loading_activation = RegInit(0.U(datawidth.W))
    val loading_layer_total_activations = RegInit(0.U(datawidth.W))
    
//				CURRENT_LAYER_MAX_COMPUTATION		- =PREVIOUS LAYER TOTAL ACTIVATIONS, SIZE OF DATAPOINT MEMORY

    val current_layer_max_computations = RegInit(0.U(datawidth.W))
    
//				CURRENT_READ_MEMORY_USAGE		- MEMORY TO BE USED BY PEs FOR COMPUTATION, MAIN OR BUFFER ONE    
//				CURRENT_WRITE_MEMORY_USAGE		- MEMORY TO BE USED FOR LOADING OF FUTURE WEIGHTS, MAIN OR BUFFER ONE

    val current_read_memory_usage = RegInit(0.U(2.W))
    val current_write_memory_usage = RegInit(0.U(2.W))
    
    curr_state := next_state
    
    
    switch(curr_state){
    
//	IDLE STATE: INITIALIZATION STATE, CHANGES TO LOAD_DATA STAGE BASED ON RESET INPUT    	
    	
    	is(idle){
    	    when(io.controller_in.controller_reset){
    	    	next_state = idle
    	    } .otherwise{
    	    	next_state = load_data
    	    }
    	}
    	
//	LOADING OF DATAPOINT (X) STAGE: 	TO LOAD X VALUES IF NECESSARY
//				3 CASES:	1) SAME DATA (X) TO BE USED FOR NEXT ACTIVATION
//						2) CURRENT COMPUTED LAYER DATA (X) TO BE USED FOR NEXT LAYER ACTIVATIONS
//						3) LOADING OF NEW DATA FROM THE DATASET (X)

//	CHANGE OF STATE BASED ON AN INPUT SIGNAL FROM LOAD DATA MODULE - 'LOAD_DATAPOINT_COMPLETE'
    	
    	is(load_data){
    	
    	    io.controller_out.load_datapoint = true
    	    
    	    when(current_layer == max_layer && current_layer_current_activation == current_layer_total_activations){
    	    	io.controller_out.load_new_request = true
    	    	
    	    }	.elsewhen(current_layer_current_activation == current_layer_total_activation){
    	    	
    	    	current_layer_max_computation = current_layer_total_activations
    	    	current_layer = current_layer + 1
    	    	current_layer_current_activation = 1
    	    	
    	    	current_layer_total_activations = //get from table
    	    	
    	    	io.controller_out.load_data_from_buffer = true    
    	    
    	    }	.otherwise{    	    	
    	    	io.controller_out.load_same_data = true
    	    
    	    }
    	    
    	    when(io.controller_in.load_datapoint_complete == true){
    	    	next_state = start_compute
    	    }
    	
    	}
    	
//	START COMPUTATION STAGE: LOAD OF BUFFER WEIGHT MEMORY AND READING OTHER WEIGHT MEMORY USING ADDRESS GENERATOR MODULE
//				
//	CHANGE OF STATE BASED ON AN INPUT SIGNAL FROM ADDRESS GENERATOR MODULE - 'ADDRESS_GENERATION_COMPLETE'
    	
    	is(start_compute){
    	
    	    when(current_read_memory_usage == 1){				//SELECT WHICH MEMORY TO READ FROM AND WHICH MEMORY TO WRITE
    	    	current_read_memory_usage = 2
    	    } .otherwise{
    	    	current_read_memory_usage = 1
    	    }
    	
    	    current_activations_computed = 0					// SEND ENABLE SIGNALS TO PEs WHERE COMPUTATIONS HAPPEN, NOT ALL NECESSARILY COMPUTING
	    for(j <- 0 until numberOfPE){
	    	when(current_layer_current_activation == current_layer_total_activations){
	    	    io.controller_out.read_memoryUnits(j) = 0
	    	} .otherwise{
	    	    io.controller_out.read_memoryUnits(j) = current_read_memory_usage
	    	    current_layer_current_activation = current_layer_current_activation + 1
	    	    current_activations_computed = current_activations_computed + 1
	    	}	    	
    	    }

    	    when(current_read_memory_usage == 1){
    	    	current_write_memory_usage = 2
    	    } .otherwise{
    	    	current_write_memory_usage = 1
    	    }
	        	    
    	    
    	    when(current_layer_current_activation == current_layer_total_activations && current_layer == max_layer){
    	    	loading_layer = 2
    	    	current_loading_activation = 1
    	    
    	    } .elsewhen(current_layer_current_activation == current_layer_total_activations) {
    	    	loading_layer = current_layer + 1
    	    	current_loading_activation = 1
    	    
    	    } .otherwise{
    	    	loading_layer = current_layer
    	    	current_loading_activation = current_layer_current_activation + 1
    	    
    	    }
    	    
    	    loading_layer_total_activations = //get from table using loading_layer
    	    
	    for(j <- 0 until numberOfPE){
	    	when(current_loading_activation == loading_layer_total_activations){
	    	    io.controller_out.write_memoryUnits(j) = 0
	    	} .otherwise{
	    	    io.controller_out.write_memoryUnits(j) = current_write_memory_usage
	    	    current_loading_activation = current_loading_activation + 1
	    	}	    	
    	    }
    	    
    	    
// SEND SIGNALS FOR ADDRESS GENERATION
    	    
    	    io.controller_out.address_generator_address_valid := true
    	    io.controller_out.address_generator_enable_valid := true
    	    io.controller_out.address_generator_datapoint_write_valid := false
    	    io.controller_out.address_generator_reset := false

    	    
    	    when(io.controller_in.address_generation_complete){
    	    	next_state = save_data
    	    } .otherwise{
    	    	next_state = start_compute
    	    }
    	    
    	
    	}
    	
    	is(save_data){
    	
    	    io.controller_out.address_generator_reset = true
    	    io.controller_out.address_generator_address_valid = false
    	    io.controller_out.address_generator_enable_valid = false
    	    io.controller_out.address_generator_datapoint_write_valid = false
    	
    	    for(j <- 0 until current_activations_computed){
    		buffer_memory(current_buffer_memory_pointer + j) = //PE input
    	    }
    	    current_buffer_memory_pointer = current_buffer_memory_pointer + current_activations_computed
    	    
    	    when(load_weight_memory_complete){
    	    	next_state = load_data
    	    }
    	
    	}
    
    }
}

object DriverController extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new Controller(8, 32, 16), Array("--target-dir", "generated/"))
}
