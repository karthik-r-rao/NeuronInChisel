package ControlUnit

import chisel3._
import chisel3.util.log2Up

class ControllerInterfaceIn(memoryDepth: Int, memoryHeight: Int, datawidth: Int) extends Bundle{
    
    //control Inputs
    val controller_reset = Input(Bool())
    val address_generation_complete = Input(Bool())
    
    val datapoint_write_data = Input(Bits(datawidth.W))
    val datapoint_write_valid = Input(Bool())
    
}

class ControllerInterfaceOut(memoryDepth: Int, memoryHeight: Int, datawidth: Int) extends Bundle{
    
    // data signals
    val datapoint_request_data = Output(Bool())
    val datapoint_write_valid = Output(Bool())
    
    val datapoint_write_data = Output(Bits(datawidth.W))
    
    val memoryUnit1 = Output(Bits(datawidth.W))
    val memoryUnit2 = Output(Bits(datawidth.W))
    val memoryUnit3 = Output(Bits(datawidth.W))
    val memoryUnit4 = Output(Bits(datawidth.W))

    val max_iteration = Output(Bits(datawidth.W))
        
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
    
    val idle::load_data::start_compute::save_data::Nil = Enum(4)
    
    val curr_state = RegInit(idle)
    val next_state = RegInit(idle)
    
    val current_layer = RegInit(0.U(datawidth.W))
    val next_layer = RegInit(0.U(datawidth.W))
    
    val current_layer_total_activations = RegInit(0.U(datawidth.W))
    val current_layer_current_activation = RegInit(0.U(datawidth.W))
    
    val current_layer_max_computations = RegInit(0.U(datawidth.W))
    
    
    curr_state := next_state
    
    
    switch(curr_state){
    	is(idle){
    	    when(io.controller_in.controller_reset){
    	    	next_state = idle
    	    } .otherwise{
    	    	next_state = load_data
    	    }
    	}
    	
    	is(load_data){
    	
    	    when(current_layer == max_layer && current_layer_current_activation == current_layer_total_activations){
    	    	io.controller_out.load_datapoint = true
    	    }
		    	
    	    
    	    
    	
    	}
    	
    	is(start_compute){
    	    
    	    address_generator_address_valid = true
    	    address_generator_enable_valid = true
    	    address_generator_datapoint_write_valid = false
    	    address_generator_reset = false

    	    
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
    	
    	
    	
    	}
    
    }
}

object DriverController extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new Controller(8, 32, 16), Array("--target-dir", "generated/"))
}
