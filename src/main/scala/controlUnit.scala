package ControlUnit

import chisel3._
import chisel3.util._
import chisel3.util.log2Up
import chisel3.util.experimental.loadMemoryFromFile   
import firrtl.annotations.MemoryLoadFileType
import scala.io.Source

class ControllerInterfaceIn(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int, tableDepth: Int) extends Bundle{
    
    //control Inputs
    val controller_reset = Input(Bool())
    
    val address_generation_complete = Input(Bool())
    
    val loading_initial_weights_complete = Input(Bool())
    
    val load_datapoint_complete = Input(Bool())
    
    val load_buffer_weight_memory_complete = Input(Bool())
    
    val save_data_complete = Input(Bool())
    

    val nn_description_table_input = Input(Bits(datawidth.W))

    val PE_outputs = Input(Vec(numberOfPE, Bits(datawidth.W)))

}




class ControllerInterfaceOut(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int, tableDepth: Int) extends Bundle{
    //Load Initial Weights
    val load_initial_weights = Output(Bool())
    
    // Load Datapoint Control Signals
    val load_datapoint = Output(Bool())
    val load_new_request = Output(Bool())
    val load_data_from_buffer = Output(Bool())
    val load_same_data = Output(Bool())
    
    // Address Generator control signals
    val read_memoryUnits = Output(Vec(numberOfPE, Bits(2.W)))    
    val max_iteration = Output(Bits(datawidth.W))

    val address_generator_address_valid = Output(Bool())
    val address_generator_enable_valid = Output(Bool())
    val address_generator_reset = Output(Bool())
    
    // Buffer Memory Loading
    val loading_layer = Output(Bits(datawidth.W))
    val loading_activations = Output(Vec(numberOfPE, Bits(datawidth.W)))
    val write_memoryUnits = Output(Vec(numberOfPE, Bits(2.W)))
    val weight_buffer_load_request = Output(Bool())
    
    // Save Data
    val save_data_request = Output(Bool())
    val current_buffer_memory_pointer = Output(Bits(datawidth.W))



    val current_state = Output(Bits(4.W))
    val current_layer_total_activations = Output(Bits(datawidth.W))
    val loading_layer_total_activations = Output(Bits(datawidth.W))
    
    val current_layer = Output(Bits(datawidth.W))
    val iteration_layer = Output(Bits(datawidth.W))
    val max_layer = Output(Bits(datawidth.W))
    val current_layer_current_activation = Output(Bits(datawidth.W))
    
    val nn_description_table_address = Output(Bits(datawidth.W))
}




class Controller(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int, tableDepth: Int) extends Module{
    val io = IO(new Bundle{
        val controller_in = new ControllerInterfaceIn(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth)
        val controller_out = new ControllerInterfaceOut(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth)
    })


//    val nn_description_table = SyncReadMem(tableDepth, Bits(datawidth.W))
//    val path = getClass.getResource("/nn_description_table.txt").getPath
//    loadMemoryFromFile(nn_description_table, path, MemoryLoadFileType.Binary)

    val buffer_memory = SyncReadMem(memoryDepth, Bits(datawidth.W))

//INITIALIZATION
    val load_initial_weights_out = RegInit(0.U(1.W))

    val load_datapoint = RegInit(0.U(1.W))
    val load_new_request = RegInit(0.U(1.W))
    val load_data_from_buffer = RegInit(0.U(1.W))
    val load_same_data = RegInit(0.U(1.W))
        
    val read_memoryUnits = Reg(Vec(numberOfPE, Bits(2.W))) 

    val address_generator_address_valid = RegInit(0.U(1.W))
    val address_generator_enable_valid = RegInit(0.U(1.W))
    val address_generator_reset = RegInit(1.U(1.W))
        
    val write_memoryUnits = Reg(Vec(numberOfPE, Bits(2.W)))
    val loading_activations = Reg(Vec(numberOfPE, Bits(datawidth.W)))
    val weight_buffer_load_request = RegInit(0.U(1.W))

    val save_data_request = WireDefault(0.U(1.W))

    
    io.controller_out.nn_description_table_address := (0.U(datawidth.W))

// STATE MACHINE STATE
    val idle::initiate_nn::set_layer::save_layer::save_layer2::set_loading_layer::save_loading_layer::load_data::loading_initial_weights::start_compute::save_data::Nil = Enum(11)
    
    val curr_state = RegInit(idle)
    io.controller_out.current_state := 0.U(4.W)
    
// STORING CONTROL VALUES: 	CURRENT_LAYER - LAYER IN WHICH CURRENT ACTIVATION IS COMPUTED
//			    	LOADING_LAYER - LAYER FOR WHICH NEXT SET OF ACTIVATION WEIGHTS ARE LOADED TO BUFFER MEMORY
//			    	MAX_LAYER     - TOTAL LAYERS IN THE NEURAL NETWORK

    val current_layer = RegInit(0.U(datawidth.W))
    val iteration_layer = RegInit(0.U(datawidth.W))
    val loading_layer = RegInit(0.U(datawidth.W))
    val max_layer = RegInit(0.U(datawidth.W))
        

//				CURRENT_LAYER_TOTAL_ACTIVATIONS 	- TOTAL ACTIVATIONS TO BE COMPUTED IN THE CURRENT LAYER
//				CURRENT_LAYER_CURRENT_ACTIVATION 	- CURRENT ACTIVATION COMPUTED BY THE LAST PE
//				CURRENT_LAYER_ACTIVATIONS_COMPUTED 	- TOTAL ACTIVATIONS COMPUTED IN THE CURRENT ITERATION, CAN BE NO_OF_PES OR LESS THAN THAT
//				CURRENT_BUFFER_MEMORY_POINTER 	- POINTER TO LOAD COMPUTED OUTPUT VALUES TO A BUFFER MEMORY

    val current_layer_total_activations = RegInit(0.U(datawidth.W))
    //current_layer_total_activations := nn_description_table(2)
    
    val current_layer_current_activation = RegInit(1.U(datawidth.W))
    val current_layer_next_activation = RegInit(1.U(datawidth.W))
    
    val current_buffer_memory_pointer = RegInit(0.U(datawidth.W))

//				CURRENT_LOADING_ACTIVATION		- CURRENT ACTIVATION FOR WHICH THE WEIGHT IS LOADED TO THE BUFFER MEMORY
//				LOADING_LAYER_TOTAL_ACTIVATIONS	- TOTAL ACTIVATIONS IN THE LAYER WHERE LOADING OF WEIGHTS TO BUFFER MEMORY IS HAPPENING
    
    val current_loading_activation = RegInit(1.U(datawidth.W))
    val next_loading_activation = RegInit(1.U(datawidth.W))
    
    val loading_layer_total_activations = RegInit(0.U(datawidth.W))
//    loading_layer_total_activations := nn_description_table(loading_layer)
//				CURRENT_LAYER_MAX_COMPUTATION		- =PREVIOUS LAYER TOTAL ACTIVATIONS, SIZE OF DATAPOINT MEMORY

    val current_layer_max_computations = RegInit(0.U(datawidth.W))
//    current_layer_max_computations := nn_description_table(1)
//				CURRENT_READ_MEMORY_USAGE		- MEMORY TO BE USED BY PEs FOR COMPUTATION, MAIN OR BUFFER ONE    
//				CURRENT_WRITE_MEMORY_USAGE		- MEMORY TO BE USED FOR LOADING OF FUTURE WEIGHTS, MAIN OR BUFFER ONE

    val current_read_memory_usage = RegInit("b00".U(2.W))
    val current_write_memory_usage = RegInit("b00".U(2.W))
    val previous_read_memory_usage = RegInit("b10".U(2.W))
    
    val load_initial_weights = RegInit(0.U(1.W))
    val load_weight_buffer_signal = RegInit("b0".U(1.W))
                
    switch(curr_state){
    
//	IDLE STATE: INITIALIZATION STATE, CHANGES TO LOAD_DATA STAGE BASED ON RESET INPUT    	
    	
    	is(idle){
    	    io.controller_out.current_state := 1.U(4.W)
    	    
    	    current_layer := 0.U(datawidth.W)
    	    
    	    when(io.controller_in.controller_reset){
    	    	curr_state := idle
    	    } .otherwise{
    	    	io.controller_out.nn_description_table_address := 0.U(datawidth.W)
    	    	curr_state := initiate_nn
    	    }
    	}
    	
    	
    	is(initiate_nn){
    	    max_layer := io.controller_in.nn_description_table_input
    	    when(io.controller_in.controller_reset){
    	    	curr_state := idle
    	    } .otherwise{
    	    	curr_state := set_layer
    	    }
    	}
    	
    	
    	
    	
    	
    	is(set_layer){
    	
    	    load_weight_buffer_signal := "b0".U(1.W)
    	    save_data_request := false.B

    	    
    	    io.controller_out.current_state := 2.U(4.W)
    	    
    	    when((current_layer === 0.U(datawidth.W)) || (current_layer === max_layer & current_layer_current_activation > current_layer_total_activations)){
    	    	current_buffer_memory_pointer := 0.U(datawidth.W)
    	    	current_layer := 2.U(datawidth.W)
    	    	current_layer_current_activation := 1.U(datawidth.W)
    	    	load_initial_weights := true.B
    	    	
    	    	load_new_request := true.B
    	    
    	    } .elsewhen(current_layer_current_activation > current_layer_total_activations){
    	    	current_layer := current_layer + 1.U(datawidth.W)
    	    	current_layer_current_activation := 1.U(datawidth.W)
    	    	current_buffer_memory_pointer := 0.U(datawidth.W)
    	    	
    	    	load_data_from_buffer := true.B
    	    
    	    } .otherwise{
    	    	current_buffer_memory_pointer := current_buffer_memory_pointer
    	    	
    	    	load_same_data := true.B
    	    }
    	    
    	    when(io.controller_in.controller_reset){
    	    	curr_state := idle
    	    } .otherwise{
    	    	curr_state := save_layer
    	    }
    	}
    	



    	is(save_layer){
    	
    	    io.controller_out.current_state := 3.U(4.W)
    	
    	    io.controller_out.nn_description_table_address := current_layer
    	    iteration_layer := current_layer - 1.U(datawidth.W)
    	    
    	    when(io.controller_in.controller_reset){
    	    	curr_state := idle
    	    } .otherwise{
    	    	curr_state := save_layer2
    	    }
    	}
    	
    	is(save_layer2){
    	    current_layer_total_activations := io.controller_in.nn_description_table_input
    	    
    	    when(io.controller_in.controller_reset){
    	    	curr_state := idle
    	    } .otherwise{
    	    	curr_state := set_loading_layer
    	    }
    	}







    	is(set_loading_layer){
    	    io.controller_out.current_state := 4.U(4.W)
    	    
    	    io.controller_out.nn_description_table_address := iteration_layer
    	    
    	    when(current_layer_current_activation  + numberOfPE.U(datawidth.W) > current_layer_total_activations && current_layer === max_layer){
    	    	loading_layer := 2.U(datawidth.W)
    	    	current_loading_activation := 1.U(datawidth.W)
    	    
    	    } .elsewhen(current_layer_current_activation + numberOfPE.U(datawidth.W) > current_layer_total_activations){
    	    	loading_layer := current_layer + 1.U(datawidth.W)
    	    	current_loading_activation := 1.U(datawidth.W)
    	    
    	    } .otherwise{
    	    	loading_layer := current_layer
    	    	current_loading_activation := current_layer_current_activation + numberOfPE.U(datawidth.W)
    	    }
    	
    	    when(io.controller_in.controller_reset){
    	    	curr_state := idle
    	    } .otherwise{
    	    	curr_state := save_loading_layer
    	    }
    	}
    	


    	is(save_loading_layer){
    	    io.controller_out.current_state := 5.U(4.W)
    	    
    	    current_layer_max_computations := io.controller_in.nn_description_table_input
    	    io.controller_out.nn_description_table_address := loading_layer
    	    
    	    //loading_layer_total_activations := nn_description_table(loading_layer)
    	    
    	    when(io.controller_in.controller_reset){
    	    	curr_state := idle
    	    } .elsewhen(load_initial_weights.asBool){
    	    	curr_state := loading_initial_weights
    	    } .otherwise{
    	    	curr_state := load_data
    	    }
    	}
    	
    	
    	
    	is(loading_initial_weights){
    	    load_initial_weights_out := true.B
    	    
    	    for(j <- 0 until numberOfPE){
    	    	when(j.U(datawidth.W) >= current_layer_max_computations){
    	    	    write_memoryUnits(j) := 0.U(2.W)
    	    	} .otherwise{
    	    	    write_memoryUnits(j) := 1.U(2.W)
    	    	}
    	    }
    	    
    	    load_initial_weights := false.B
    	    when(io.controller_in.controller_reset){
    	    	curr_state := idle
    	    } .elsewhen(io.controller_in.loading_initial_weights_complete){
    	    	curr_state := load_data
    	    }
    	}
    	

    	is(load_data){
    	    load_initial_weights_out := false.B


    	    io.controller_out.current_state := 6.U(4.W)
    	    loading_layer_total_activations := io.controller_in.nn_description_table_input
    	    load_datapoint := true.B
    	    
    	    when(io.controller_in.controller_reset){
    	    	curr_state := idle
    	    } .elsewhen(io.controller_in.load_datapoint_complete){
    	    	curr_state := start_compute
    	    }
    	
    	}
    	
//	START COMPUTATION STAGE: LOAD OF BUFFER WEIGHT MEMORY AND READING OTHER WEIGHT MEMORY USING ADDRESS GENERATOR MODULE
//				
//	CHANGE OF STATE BASED ON AN INPUT SIGNAL FROM ADDRESS GENERATOR MODULE - 'ADDRESS_GENERATION_COMPLETE'
    	
    	
    	   	
    	
    	
    	is(start_compute){
    	        	
    	    io.controller_out.current_state := 7.U(4.W)
    	
    	    load_datapoint := false.B
    	    load_new_request := false.B
    	    load_data_from_buffer := false.B
    	    load_same_data := false.B
    	        	
	    
	    for(j <- 0 until numberOfPE){
	    	when(current_layer_current_activation + j.U(datawidth.W) > current_layer_total_activations){
	    	    read_memoryUnits(j) := "b00".U(2.W)
	    	} .otherwise{
	    	    
	    	    when(previous_read_memory_usage === "b01".U(2.W)){				//SELECT WHICH MEMORY TO READ FROM AND WHICH MEMORY TO WRITE
    	    		current_read_memory_usage := "b10".U(2.W)
    	    		read_memoryUnits(j) := "b10".U(2.W)
    	    	    } .otherwise{
    	    		current_read_memory_usage := "b01".U(2.W)
    	    		read_memoryUnits(j) := "b01".U(2.W)
    	    	    }
	    	}
    	    }
    	    
    	    current_layer_next_activation := current_layer_current_activation + numberOfPE.U(datawidth.W)
	        	    
    	    
	    for(j <- 0 until numberOfPE){
	    	when(current_loading_activation + j.U(datawidth.W) > loading_layer_total_activations){
	    	    write_memoryUnits(j) := "b00".U(2.W)
	    	} .otherwise{
	    	    when(previous_read_memory_usage === "b10".U(2.W)){
    	    		current_write_memory_usage := "b10".U(2.W)
    	    		write_memoryUnits(j) := "b10".U(2.W)

    	            } .otherwise{
    	    		current_write_memory_usage := "b01".U(2.W)
    	    		write_memoryUnits(j) := "b01".U(2.W)
    	    	    }

	    	    loading_activations(j) := current_loading_activation + j.U(datawidth.W)
	    	}
    	    }
    	    
    	    next_loading_activation := current_loading_activation + numberOfPE.U(datawidth.W)
    	    
    	        	    
    	    address_generator_address_valid := true.B
    	    address_generator_enable_valid := true.B
    	    address_generator_reset := false.B
    	    
    	    weight_buffer_load_request := true.B


    	    when(io.controller_in.load_buffer_weight_memory_complete){
    	    	load_weight_buffer_signal := "b1".U(1.W)
    	    }
    	    
    	    when(io.controller_in.controller_reset){
    	    	curr_state := idle
    	    } .elsewhen(io.controller_in.address_generation_complete){
    	    	curr_state := save_data
	    }
	    
    	}
    	
    	is(save_data){

	    previous_read_memory_usage := current_read_memory_usage
	    current_layer_current_activation := current_layer_next_activation
	    current_loading_activation := next_loading_activation
	    
    	    io.controller_out.current_state := 8.U(4.W)

	    for(j <- 0 until numberOfPE){
	    	read_memoryUnits(j) := "b00".U(2.W)
	    	write_memoryUnits(j) := "b00".U(2.W)
    	    }
    	
    	    address_generator_reset := true.B
    	    address_generator_address_valid := false.B
    	    address_generator_enable_valid := false.B
    	    
    	    weight_buffer_load_request := false.B
    	
	    save_data_request := true.B
	        	    
    	    when(io.controller_in.load_buffer_weight_memory_complete){
    	    	load_weight_buffer_signal := "b1".U(1.W)
    	    }

    	    when(io.controller_in.controller_reset){
    	    	curr_state := idle
    	    } .elsewhen((load_weight_buffer_signal.asBool || io.controller_in.load_buffer_weight_memory_complete) && io.controller_in.save_data_complete){
    	    	current_buffer_memory_pointer := current_buffer_memory_pointer + numberOfPE.U(datawidth.W)
    	    	curr_state := set_layer
    	    }
    	
    	}
    
    }

    io.controller_out.load_datapoint := load_datapoint
    io.controller_out.load_new_request := load_new_request
    io.controller_out.load_data_from_buffer := load_data_from_buffer
    io.controller_out.load_same_data := load_same_data
        
    for(j <- 0 until numberOfPE){
    	io.controller_out.read_memoryUnits(j) := read_memoryUnits(j)
    }
    io.controller_out.max_iteration := current_layer_max_computations


    io.controller_out.address_generator_address_valid := address_generator_address_valid
    io.controller_out.address_generator_enable_valid := address_generator_enable_valid
    io.controller_out.address_generator_reset := address_generator_reset
    
    for(j <- 0 until numberOfPE){
    	io.controller_out.write_memoryUnits(j) := write_memoryUnits(j)
    	io.controller_out.loading_activations(j) := loading_activations(j)
    }
    
    io.controller_out.loading_layer := loading_layer
    io.controller_out.weight_buffer_load_request := weight_buffer_load_request
    io.controller_out.current_layer_total_activations := current_layer_total_activations
    io.controller_out.loading_layer_total_activations := loading_layer_total_activations
    
    io.controller_out.save_data_request := save_data_request
    io.controller_out.current_buffer_memory_pointer := current_buffer_memory_pointer
    
    io.controller_out.current_layer := current_layer
    io.controller_out.iteration_layer := iteration_layer
    io.controller_out.max_layer := max_layer
    io.controller_out.current_layer_current_activation := current_layer_current_activation
    io.controller_out.load_initial_weights := load_initial_weights_out    


}

object DriverController extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new Controller(8, 32, 16, 4, 32), Array("--target-dir", "generated/"))
}
