/*
//Inputs
//    val load_initial_weights = Input(Bool())

//  Loading Datapoint
    val load_datapoint = Input(Bool())
    val load_new_request = Input(Bool())
    val load_data_from_buffer = Input(Bool())
    val load_same_data = Input(Bool())

//  Loading Buffer Memory
    val loading_layer = Input(Bits(16.W))
    val loading_length = Input(Bits(16.W))
    val loading_activations = Input(Vec(numberOfPE, Bits(16.W)))
    val write_memoryUnits = Input(Vec(numberOfPE, Bits(2.W)))
    val weight_buffer_load_request = Input(Bool())
    
//  Buffer Memory Output
    val buffer_memory_output = Input(Bits(16.W))
    
//  Datapoint Memory Output
    val datapoint_memory_output = Input(Bits(16.W))

    val new_datapoint_ready = Input(Bits(16.W))
        
//  Interconnect Input
    val interconnect_load_ready = Input(Bits(16.W))
    val interconnect_memory_output = Input(Bits(16.W))

//Outputs    
    val load_initial_weights_complete = Output(Bool())    
    val load_datapoint_complete = Output(Bool())
    val load_buffer_weight_memory_complete = Output(Bool())
    
    val datapoint_memory_write_enable = Output(Bool())
    val datapoint_memory_write_data = Output(Bits(16.W))
    val datapoint_memory_write_address = Output(Bits(16.W))
    
    val buffer_memory_write_enable = Output(Bool())
    val buffer_memory_write_data = Output(Bits(16.W))
    val buffer_memory_write_address = Output(Bits(16.W))
    
    val weight_buffer_memory_write_enable = Output(Bool())
    val weight_buffer_memory_write_data = Output(Bits(16.W))
    val weight_buffer_memory_write_address = Output(Bits(16.W))
    
    val interconnect_loading_layer = Output(Bits(16.W))
    val interconnect_loading_activation = Output(Bits(16.W))

    val interconnect_load_request = Output(Bool())
    val interconnect_load_read_address = Output(Bool())    
*/



package controlUnitTests

import chisel3._
import chisel3.util.log2Up
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import ControlUnit._ 

class LoadPEMemorySpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new LoadPEMemory(8, 32, 16, 4)) { dut => 
        
//Inputs
            dut.io.load_pe_memory_in.load_initial_weights.poke(false.B)
            dut.io.load_pe_memory_in.load_datapoint.poke(false.B)
            dut.io.load_pe_memory_in.load_new_request.poke(false.B)
            dut.io.load_pe_memory_in.load_data_from_buffer.poke(false.B)
            dut.io.load_pe_memory_in.load_same_data.poke(false.B)

	    dut.io.load_pe_memory_in.loading_length.poke(0.U(16.W))
	    dut.io.load_pe_memory_in.loading_layer.poke(0.U(16.W))
	    dut.io.load_pe_memory_in.loading_activations(0).poke(0.U(16.W))
	    dut.io.load_pe_memory_in.loading_activations(1).poke(0.U(16.W))
	    dut.io.load_pe_memory_in.loading_activations(2).poke(0.U(16.W))
	    dut.io.load_pe_memory_in.loading_activations(3).poke(0.U(16.W))
	    dut.io.load_pe_memory_in.write_memoryUnits(0).poke(0.U(2.W))
	    dut.io.load_pe_memory_in.write_memoryUnits(1).poke(0.U(2.W))
	    dut.io.load_pe_memory_in.write_memoryUnits(2).poke(0.U(2.W))
	    dut.io.load_pe_memory_in.write_memoryUnits(3).poke(0.U(2.W))
	    dut.io.load_pe_memory_in.weight_buffer_load_request.poke(false.B)
	    
	    dut.io.load_pe_memory_in.buffer_memory_output.poke(0.U(16.W))
	    dut.io.load_pe_memory_in.datapoint_memory_output.poke(0.U(16.W))
	    dut.io.load_pe_memory_in.interconnect_memory_output.poke(0.U(16.W))

	    dut.io.load_pe_memory_in.new_datapoint_ready.poke(false.B)
	    dut.io.load_pe_memory_in.interconnect_load_ready.poke(false.B)
	    
//Outputs
    	    dut.io.load_pe_memory_out.current_load_weights_state.expect(0.U(16.W))

	    dut.io.load_pe_memory_out.load_initial_weights_complete.expect(false.B)   
	    dut.io.load_pe_memory_out.load_datapoint_complete.expect(false.B)
	    dut.io.load_pe_memory_out.load_buffer_weight_memory_complete.expect(false.B)
    
	    dut.io.load_pe_memory_out.datapoint_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.datapoint_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.datapoint_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.buffer_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_address.expect(0.U(16.W))
	    
	    dut.io.load_pe_memory_out.interconnect_loading_layer.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_loading_activation.expect(0.U(16.W))
	    
	    dut.io.load_pe_memory_out.interconnect_load_request.expect(false.B)
	    dut.io.load_pe_memory_out.interconnect_load_read_address.expect(0.U(16.W))
	    
	    
	    
	    
	    dut.clock.step()
	    
	    
	    
//Inputs
            dut.io.load_pe_memory_in.load_initial_weights.poke(true.B)

	    dut.io.load_pe_memory_in.loading_length.poke(4.U(16.W))
	    dut.io.load_pe_memory_in.loading_layer.poke(0.U(16.W))
	    
	    dut.io.load_pe_memory_in.loading_length.poke(4.U(16.W))
	    dut.io.load_pe_memory_in.loading_layer.poke(0.U(16.W))
	    dut.io.load_pe_memory_in.loading_activations(0).poke(0.U(16.W))
	    dut.io.load_pe_memory_in.loading_activations(1).poke(0.U(16.W))
	    dut.io.load_pe_memory_in.loading_activations(2).poke(0.U(16.W))
	    dut.io.load_pe_memory_in.loading_activations(3).poke(0.U(16.W))
	    dut.io.load_pe_memory_in.write_memoryUnits(0).poke(1.U(2.W))
	    dut.io.load_pe_memory_in.write_memoryUnits(1).poke(1.U(2.W))
	    dut.io.load_pe_memory_in.write_memoryUnits(2).poke(1.U(2.W))
	    dut.io.load_pe_memory_in.write_memoryUnits(3).poke(1.U(2.W))

	    
	    
//Outputs	    
    	    dut.io.load_pe_memory_out.current_load_weights_state.expect(0.U(16.W))

	    dut.io.load_pe_memory_out.load_initial_weights_complete.expect(false.B)   
	    dut.io.load_pe_memory_out.load_datapoint_complete.expect(false.B)
	    dut.io.load_pe_memory_out.load_buffer_weight_memory_complete.expect(false.B)
    
	    dut.io.load_pe_memory_out.datapoint_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.datapoint_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.datapoint_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.buffer_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_address.expect(0.U(16.W))
	    
	    dut.io.load_pe_memory_out.interconnect_loading_layer.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_loading_activation.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_load_request.expect(false.B)
	    
	    dut.io.load_pe_memory_out.interconnect_load_read_address.expect(0.U(16.W))




	    dut.clock.step()
	    
	    
	    
//Inputs
            dut.io.load_pe_memory_in.load_initial_weights.poke(true.B)

	    dut.io.load_pe_memory_in.loading_length.poke(4.U(16.W))
	    dut.io.load_pe_memory_in.loading_layer.poke(0.U(16.W))
	    
	    
//Outputs	    
    	    dut.io.load_pe_memory_out.current_load_weights_state.expect(1.U(16.W))

	    dut.io.load_pe_memory_out.load_initial_weights_complete.expect(false.B)   
	    dut.io.load_pe_memory_out.load_datapoint_complete.expect(false.B)
	    dut.io.load_pe_memory_out.load_buffer_weight_memory_complete.expect(false.B)
    
	    dut.io.load_pe_memory_out.datapoint_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.datapoint_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.datapoint_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.buffer_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_address.expect(0.U(16.W))
	    
	    dut.io.load_pe_memory_out.interconnect_loading_layer.expect(2.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_loading_activation.expect(1.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_load_request.expect(true.B)
	    
	    dut.io.load_pe_memory_out.interconnect_load_read_address.expect(0.U(16.W))





	    dut.clock.step()
	    
	    
	    
//Inputs
            dut.io.load_pe_memory_in.load_initial_weights.poke(true.B)

	    dut.io.load_pe_memory_in.loading_length.poke(4.U(16.W))
	    dut.io.load_pe_memory_in.loading_layer.poke(0.U(16.W))
	    dut.io.load_pe_memory_in.interconnect_load_ready.poke(true.B)

	    
//Outputs	    
    	    dut.io.load_pe_memory_out.current_load_weights_state.expect(1.U(16.W))

	    dut.io.load_pe_memory_out.load_initial_weights_complete.expect(false.B)   
	    dut.io.load_pe_memory_out.load_datapoint_complete.expect(false.B)
	    dut.io.load_pe_memory_out.load_buffer_weight_memory_complete.expect(false.B)
    
	    dut.io.load_pe_memory_out.datapoint_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.datapoint_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.datapoint_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.buffer_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_address.expect(0.U(16.W))
	    
	    dut.io.load_pe_memory_out.interconnect_loading_layer.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_loading_activation.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_load_request.expect(false.B)
	    
	    dut.io.load_pe_memory_out.interconnect_load_read_address.expect(0.U(16.W))




	    dut.clock.step()
	    
	    
	    
//Inputs
            dut.io.load_pe_memory_in.load_initial_weights.poke(true.B)

	    dut.io.load_pe_memory_in.loading_length.poke(4.U(16.W))
	    dut.io.load_pe_memory_in.loading_layer.poke(0.U(16.W))
	    dut.io.load_pe_memory_in.interconnect_memory_output.poke(0.U(16.W))
	    
	    
//Outputs	    
    	    dut.io.load_pe_memory_out.current_load_weights_state.expect(2.U(16.W))


	    dut.io.load_pe_memory_out.load_initial_weights_complete.expect(false.B)   
	    dut.io.load_pe_memory_out.load_datapoint_complete.expect(false.B)
	    dut.io.load_pe_memory_out.load_buffer_weight_memory_complete.expect(false.B)
    
	    dut.io.load_pe_memory_out.datapoint_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.datapoint_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.datapoint_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.buffer_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_enable.expect(true.B)
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_address.expect(0.U(16.W))
	    
	    dut.io.load_pe_memory_out.interconnect_loading_layer.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_loading_activation.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_load_request.expect(false.B)
	    
	    dut.io.load_pe_memory_out.interconnect_load_read_address.expect(0.U(16.W))
	    







	    dut.clock.step()
	    
	    
	    
//Inputs
            dut.io.load_pe_memory_in.load_initial_weights.poke(true.B)

	    dut.io.load_pe_memory_in.loading_length.poke(4.U(16.W))
	    dut.io.load_pe_memory_in.loading_layer.poke(0.U(16.W))
	    dut.io.load_pe_memory_in.interconnect_memory_output.poke(1.U(16.W))
	    
	    
//Outputs	    
    	    dut.io.load_pe_memory_out.current_load_weights_state.expect(2.U(16.W))


	    dut.io.load_pe_memory_out.load_initial_weights_complete.expect(false.B)   
	    dut.io.load_pe_memory_out.load_datapoint_complete.expect(false.B)
	    dut.io.load_pe_memory_out.load_buffer_weight_memory_complete.expect(false.B)
    
	    dut.io.load_pe_memory_out.datapoint_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.datapoint_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.datapoint_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.buffer_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_enable.expect(true.B)
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_data.expect(1.U(16.W))
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_address.expect(0.U(16.W))
	    
	    dut.io.load_pe_memory_out.interconnect_loading_layer.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_loading_activation.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_load_request.expect(false.B)
	    
	    dut.io.load_pe_memory_out.interconnect_load_read_address.expect(1.U(16.W))





	    dut.clock.step()
	    
	    
	    
//Inputs
            dut.io.load_pe_memory_in.load_initial_weights.poke(true.B)

	    dut.io.load_pe_memory_in.loading_length.poke(4.U(16.W))
	    dut.io.load_pe_memory_in.loading_layer.poke(0.U(16.W))
	    dut.io.load_pe_memory_in.interconnect_memory_output.poke(2.U(16.W))
	    
	    
//Outputs	    
    	    dut.io.load_pe_memory_out.current_load_weights_state.expect(2.U(16.W))


	    dut.io.load_pe_memory_out.load_initial_weights_complete.expect(false.B)   
	    dut.io.load_pe_memory_out.load_datapoint_complete.expect(false.B)
	    dut.io.load_pe_memory_out.load_buffer_weight_memory_complete.expect(false.B)
    
	    dut.io.load_pe_memory_out.datapoint_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.datapoint_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.datapoint_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.buffer_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_enable.expect(true.B)
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_data.expect(2.U(16.W))
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_address.expect(1.U(16.W))
	    
	    dut.io.load_pe_memory_out.interconnect_loading_layer.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_loading_activation.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_load_request.expect(false.B)
	    
	    dut.io.load_pe_memory_out.interconnect_load_read_address.expect(2.U(16.W))






	    dut.clock.step()
	    
	    
	    
//Inputs
            dut.io.load_pe_memory_in.load_initial_weights.poke(true.B)

	    dut.io.load_pe_memory_in.loading_length.poke(4.U(16.W))
	    dut.io.load_pe_memory_in.loading_layer.poke(0.U(16.W))
	    dut.io.load_pe_memory_in.interconnect_memory_output.poke(3.U(16.W))
	    
	    
//Outputs	    
    	    dut.io.load_pe_memory_out.current_load_weights_state.expect(2.U(16.W))


	    dut.io.load_pe_memory_out.load_initial_weights_complete.expect(false.B)   
	    dut.io.load_pe_memory_out.load_datapoint_complete.expect(false.B)
	    dut.io.load_pe_memory_out.load_buffer_weight_memory_complete.expect(false.B)
    
	    dut.io.load_pe_memory_out.datapoint_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.datapoint_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.datapoint_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.buffer_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_enable.expect(true.B)
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_data.expect(3.U(16.W))
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_address.expect(2.U(16.W))
	    
	    dut.io.load_pe_memory_out.interconnect_loading_layer.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_loading_activation.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_load_request.expect(false.B)
	    
	    dut.io.load_pe_memory_out.interconnect_load_read_address.expect(3.U(16.W))




	    dut.clock.step()
	    
	    
	    
//Inputs
            dut.io.load_pe_memory_in.load_initial_weights.poke(true.B)

	    dut.io.load_pe_memory_in.loading_length.poke(4.U(16.W))
	    dut.io.load_pe_memory_in.loading_layer.poke(0.U(16.W))
	    dut.io.load_pe_memory_in.interconnect_memory_output.poke(4.U(16.W))
	    
	    
//Outputs	    
    	    dut.io.load_pe_memory_out.current_load_weights_state.expect(2.U(16.W))


	    dut.io.load_pe_memory_out.load_initial_weights_complete.expect(false.B)   
	    dut.io.load_pe_memory_out.load_datapoint_complete.expect(false.B)
	    dut.io.load_pe_memory_out.load_buffer_weight_memory_complete.expect(false.B)
    
	    dut.io.load_pe_memory_out.datapoint_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.datapoint_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.datapoint_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.buffer_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_enable.expect(true.B)
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_data.expect(4.U(16.W))
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_address.expect(3.U(16.W))
	    
	    dut.io.load_pe_memory_out.interconnect_loading_layer.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_loading_activation.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_load_request.expect(false.B)
	    
	    dut.io.load_pe_memory_out.interconnect_load_read_address.expect(4.U(16.W))




	    dut.clock.step()
	    
	    
	    
//Inputs
            dut.io.load_pe_memory_in.load_initial_weights.poke(true.B)

	    dut.io.load_pe_memory_in.loading_length.poke(4.U(16.W))
	    dut.io.load_pe_memory_in.loading_layer.poke(0.U(16.W))
	    dut.io.load_pe_memory_in.interconnect_memory_output.poke(5.U(16.W))
	    
	    
//Outputs	    
    	    dut.io.load_pe_memory_out.current_load_weights_state.expect(1.U(16.W))


	    dut.io.load_pe_memory_out.load_initial_weights_complete.expect(false.B)   
	    dut.io.load_pe_memory_out.load_datapoint_complete.expect(false.B)
	    dut.io.load_pe_memory_out.load_buffer_weight_memory_complete.expect(false.B)
    
	    dut.io.load_pe_memory_out.datapoint_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.datapoint_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.datapoint_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.buffer_memory_write_address.expect(0.U(16.W))
    
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_enable.expect(false.B)
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_data.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.weight_buffer_memory_write_address.expect(0.U(16.W))
	    
	    dut.io.load_pe_memory_out.interconnect_loading_layer.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_loading_activation.expect(0.U(16.W))
	    dut.io.load_pe_memory_out.interconnect_load_request.expect(false.B)
	    
	    dut.io.load_pe_memory_out.interconnect_load_read_address.expect(0.U(16.W))


	}
    }
}
