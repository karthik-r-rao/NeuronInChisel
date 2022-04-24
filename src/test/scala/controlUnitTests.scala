//controller Inputs
//val controller_reset = Input(Bool())
//val address_generation_complete = Input(Bool())
//val load_weight_memory_complete = Input(Bool())
//val load_datapoint_complete = Input(Bool())
//val load_buffer_weight_memory_complete = Input(Bool())
//val PE_outputs = Input(Vec(numberOfPE, Bits(16.W)))


//Controller Outputs
//val load_datapoint = Output(Bool())
//val load_new_request = Output(Bool())
//val load_data_from_buffer = Output(Bool())
//val load_same_data = Output(Bool())
        
// Address Generator control signals
//val read_memoryUnits = Output(Vec(numberOfPE, Bits(2.W)))    
//val max_iteration = Output(Bits(16.W))

//val address_generator_address_valid = Output(Bool())
//val address_generator_enable_valid = Output(Bool())
//val address_generator_reset = Output(Bool())
    
// Buffer Memory Loading
//val loading_layer = Output(Bits(16.W))
//val loading_activations = Output(Vec(numberOfPE, Bits(16.W)))
//val write_memoryUnits = Output(Vec(numberOfPE, Bits(2.W)))
//val weight_buffer_load_request = Output(Bool())

//    val current_state = Output(Bits(3.W))
//    val current_layer_total_activations = Output(Bits(16.W))
//    val loading_layer_total_activations = Output(Bits(16.W))
    
//    val current_layer = Output(Bits(16.W))
//    val current_layer_current_activation = Output(Bits(16.W))
//    val current_buffer_memory_pointer = Output(Bits(16.W))


package controlUnitTests

import chisel3._
import chisel3.util.log2Up
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import ControlUnit._ 

class controlUnitSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new Controller(8, 32, 16, 4)) { dut => 

            dut.io.controller_in.controller_reset.poke(true.B)
            
            dut.io.controller_out.current_state.expect(1.U(4.W))
            dut.io.controller_out.load_datapoint.expect(false.B)
            dut.io.controller_out.address_generator_reset.expect(true.B)
            dut.io.controller_out.address_generator_address_valid.expect(false.B)
            dut.io.controller_out.weight_buffer_load_request.expect(false.B)

            dut.io.controller_out.read_memoryUnits(0).expect("b00".U(2.W))
            dut.io.controller_out.read_memoryUnits(1).expect("b00".U(2.W))
            dut.io.controller_out.write_memoryUnits(0).expect("b00".U(2.W))
            dut.io.controller_out.write_memoryUnits(1).expect("b00".U(2.W))
            
            dut.clock.step()
            
            dut.io.controller_in.controller_reset.poke(false.B)
	    dut.io.controller_out.nn_description_table_address.expect(0.U(16.W))

	    
	    dut.clock.step()	    
	    
            dut.io.controller_in.nn_description_table_input.poke(10.U(16.W))	    
            //dut.io.controller_out.max_layer.expect(10.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.max_layer.expect(10.U(16.W))
            dut.io.controller_out.current_state.expect(2.U(4.W))
            
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(3.U(4.W))
	    
	    dut.io.controller_out.max_layer.expect(10.U(16.W))
	    dut.io.controller_out.nn_description_table_address.expect(2.U(16.W))
	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.clock.step()

	    dut.io.controller_in.nn_description_table_input.poke(10.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(4.U(4.W))
	    
	    dut.io.controller_out.nn_description_table_address.expect(1.U(16.W))
	    
	    dut.io.controller_out.iteration_layer.expect(1.U(16.W))
	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    //dut.io.controller_out.max_iteration.expect(15.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(5.U(4.W))

	    dut.io.controller_in.nn_description_table_input.poke(15.U(16.W))
	    dut.io.controller_out.nn_description_table_address.expect(2.U(16.W))
	    
	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_in.nn_description_table_input.poke(10.U(16.W))	    
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.load_initial_weights.expect(false.B)

	    dut.clock.step()
	    
	    dut.io.controller_out.write_memoryUnits(0).expect(1.U(2.W))
	    dut.io.controller_out.write_memoryUnits(1).expect(1.U(2.W))
	    dut.io.controller_out.write_memoryUnits(2).expect(1.U(2.W))
	    dut.io.controller_out.write_memoryUnits(3).expect(1.U(2.W))
	    
	    
	    dut.io.controller_out.load_initial_weights.expect(true.B)
	    dut.io.controller_in.loading_initial_weights_complete.poke(true.B)
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(6.U(4.W))
	    
	    
	    dut.io.controller_out.load_datapoint.expect(false.B)
	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_in.load_datapoint_complete.poke(true.B)
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.io.controller_out.current_state.expect(6.U(4.W))
	    
	    dut.io.controller_out.load_datapoint.expect(true.B)
	    dut.io.controller_out.load_new_request.expect(true.B)
	    dut.io.controller_out.load_data_from_buffer.expect(false.B)
	    dut.io.controller_out.load_same_data.expect(false.B)

	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_in.load_datapoint_complete.poke(false.B)
	    
	    dut.io.controller_out.current_state.expect(7.U(4.W))
	    
	    dut.io.controller_out.load_datapoint.expect(true.B)
	    dut.io.controller_out.load_new_request.expect(true.B)
	    dut.io.controller_out.load_data_from_buffer.expect(false.B)
	    dut.io.controller_out.load_same_data.expect(false.B)

	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(false.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(false.B)
	    dut.io.controller_out.address_generator_reset.expect(true.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(false.B)

	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(7.U(4.W))
	    
	    dut.io.controller_in.address_generation_complete.poke(true.B)
	    dut.io.controller_in.load_buffer_weight_memory_complete.poke(true.B)
	    dut.io.controller_out.save_data_request.expect(false.B)
	    
	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.io.controller_out.read_memoryUnits(0).expect(1.U(2.W))
	    dut.io.controller_out.read_memoryUnits(1).expect(1.U(2.W))
	    dut.io.controller_out.read_memoryUnits(2).expect(1.U(2.W))
	    dut.io.controller_out.read_memoryUnits(3).expect(1.U(2.W))
	    
	    dut.io.controller_out.write_memoryUnits(0).expect(2.U(2.W))
	    dut.io.controller_out.write_memoryUnits(1).expect(2.U(2.W))
	    dut.io.controller_out.write_memoryUnits(2).expect(2.U(2.W))
	    dut.io.controller_out.write_memoryUnits(3).expect(2.U(2.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(true.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(true.B)
	    dut.io.controller_out.address_generator_reset.expect(false.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(true.B)
	    
	    dut.io.controller_out.loading_activations(0).expect(5.U(16.W))
	    dut.io.controller_out.loading_activations(1).expect(6.U(16.W))
	    dut.io.controller_out.loading_activations(2).expect(7.U(16.W))
	    dut.io.controller_out.loading_activations(3).expect(8.U(16.W))
           
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(8.U(4.W))
	    
	    dut.io.controller_in.address_generation_complete.poke(false.B)
	    dut.io.controller_in.load_buffer_weight_memory_complete.poke(false.B)

	    dut.io.controller_out.save_data_request.expect(true.B)
	    dut.io.controller_in.save_data_complete.poke(true.B)
	    
	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.io.controller_out.read_memoryUnits(0).expect(1.U(2.W))
	    dut.io.controller_out.read_memoryUnits(1).expect(1.U(2.W))
	    dut.io.controller_out.read_memoryUnits(2).expect(1.U(2.W))
	    dut.io.controller_out.read_memoryUnits(3).expect(1.U(2.W))
	    
	    dut.io.controller_out.write_memoryUnits(0).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(1).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(true.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(true.B)
	    dut.io.controller_out.address_generator_reset.expect(false.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(false.B)
	    
	    dut.io.controller_out.loading_activations(0).expect(5.U(16.W))
	    dut.io.controller_out.loading_activations(1).expect(6.U(16.W))
	    dut.io.controller_out.loading_activations(2).expect(7.U(16.W))
	    dut.io.controller_out.loading_activations(3).expect(8.U(16.W))
           
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(2.U(4.W))
	    	    
	    dut.io.controller_out.max_layer.expect(10.U(16.W))

	    dut.io.controller_in.save_data_complete.poke(false.B)

	    dut.io.controller_out.current_buffer_memory_pointer.expect(4.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(5.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.io.controller_out.read_memoryUnits(0).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(1).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.write_memoryUnits(0).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(1).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(false.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(false.B)
	    dut.io.controller_out.address_generator_reset.expect(true.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(false.B)
	    
	    dut.io.controller_out.loading_activations(0).expect(5.U(16.W))
	    dut.io.controller_out.loading_activations(1).expect(6.U(16.W))
	    dut.io.controller_out.loading_activations(2).expect(7.U(16.W))
	    dut.io.controller_out.loading_activations(3).expect(8.U(16.W))


	
	
	
	
	
	    
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(3.U(4.W))
	    
	    dut.io.controller_out.max_layer.expect(10.U(16.W))
	    dut.io.controller_out.nn_description_table_address.expect(2.U(16.W))
	    dut.io.controller_out.current_buffer_memory_pointer.expect(4.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(5.U(16.W))
	    
	    dut.clock.step()

	    dut.io.controller_in.nn_description_table_input.poke(10.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(4.U(4.W))
	    
	    dut.io.controller_out.nn_description_table_address.expect(1.U(16.W))
	    
	    dut.io.controller_out.iteration_layer.expect(1.U(16.W))
	    dut.io.controller_out.current_buffer_memory_pointer.expect(4.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(5.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(5.U(4.W))

	    dut.io.controller_in.nn_description_table_input.poke(15.U(16.W))
	    dut.io.controller_out.nn_description_table_address.expect(2.U(16.W))
	    
	    dut.io.controller_out.current_buffer_memory_pointer.expect(4.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(5.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    
	    dut.clock.step()

	    dut.io.controller_in.nn_description_table_input.poke(10.U(16.W))	    
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(6.U(4.W))
	    
	    
	    dut.io.controller_out.load_datapoint.expect(false.B)
	    dut.io.controller_out.current_buffer_memory_pointer.expect(4.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(5.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_in.load_datapoint_complete.poke(true.B)
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.io.controller_out.current_state.expect(6.U(4.W))
	    
	    dut.io.controller_out.load_datapoint.expect(true.B)
	    dut.io.controller_out.load_new_request.expect(false.B)
	    dut.io.controller_out.load_data_from_buffer.expect(false.B)
	    dut.io.controller_out.load_same_data.expect(true.B)

	    dut.io.controller_out.current_buffer_memory_pointer.expect(4.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(5.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_in.load_datapoint_complete.poke(false.B)
	    
	    dut.io.controller_out.current_state.expect(7.U(4.W))
	    
	    dut.io.controller_out.load_datapoint.expect(true.B)
	    dut.io.controller_out.load_new_request.expect(false.B)
	    dut.io.controller_out.load_data_from_buffer.expect(false.B)
	    dut.io.controller_out.load_same_data.expect(true.B)

	    dut.io.controller_out.current_buffer_memory_pointer.expect(4.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(5.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(false.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(false.B)
	    dut.io.controller_out.address_generator_reset.expect(true.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(false.B)

	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(7.U(4.W))
	    
	    dut.io.controller_in.address_generation_complete.poke(true.B)
	    dut.io.controller_in.load_buffer_weight_memory_complete.poke(true.B)

	    dut.io.controller_out.save_data_request.expect(false.B)
	    
	    dut.io.controller_out.current_buffer_memory_pointer.expect(4.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(5.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.io.controller_out.read_memoryUnits(0).expect(2.U(2.W))
	    dut.io.controller_out.read_memoryUnits(1).expect(2.U(2.W))
	    dut.io.controller_out.read_memoryUnits(2).expect(2.U(2.W))
	    dut.io.controller_out.read_memoryUnits(3).expect(2.U(2.W))
	    
	    dut.io.controller_out.write_memoryUnits(0).expect(1.U(2.W))
	    dut.io.controller_out.write_memoryUnits(1).expect(1.U(2.W))
	    dut.io.controller_out.write_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(true.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(true.B)
	    dut.io.controller_out.address_generator_reset.expect(false.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(true.B)
	    
	    dut.io.controller_out.loading_activations(0).expect(9.U(16.W))
	    dut.io.controller_out.loading_activations(1).expect(10.U(16.W))
           
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(8.U(4.W))
	    
	    dut.io.controller_in.address_generation_complete.poke(false.B)
	    dut.io.controller_in.load_buffer_weight_memory_complete.poke(false.B)
	    
	    dut.io.controller_out.save_data_request.expect(true.B)
	    dut.io.controller_in.save_data_complete.poke(true.B)

	    dut.io.controller_out.current_buffer_memory_pointer.expect(4.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(5.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.io.controller_out.read_memoryUnits(0).expect(2.U(2.W))
	    dut.io.controller_out.read_memoryUnits(1).expect(2.U(2.W))
	    dut.io.controller_out.read_memoryUnits(2).expect(2.U(2.W))
	    dut.io.controller_out.read_memoryUnits(3).expect(2.U(2.W))
	    
	    dut.io.controller_out.write_memoryUnits(0).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(1).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(true.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(true.B)
	    dut.io.controller_out.address_generator_reset.expect(false.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(false.B)
	    
	    dut.io.controller_out.loading_activations(0).expect(9.U(16.W))
	    dut.io.controller_out.loading_activations(1).expect(10.U(16.W))
           
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(2.U(4.W))

	    dut.io.controller_in.save_data_complete.poke(false.B)
	    	    
	    dut.io.controller_out.max_layer.expect(10.U(16.W))

	    dut.io.controller_out.current_buffer_memory_pointer.expect(8.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(9.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(2.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(10.U(16.W))
	    
	    dut.io.controller_out.read_memoryUnits(0).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(1).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.write_memoryUnits(0).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(1).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(false.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(false.B)
	    dut.io.controller_out.address_generator_reset.expect(true.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(false.B)
	    
	    dut.io.controller_out.loading_activations(0).expect(9.U(16.W))
	    dut.io.controller_out.loading_activations(1).expect(10.U(16.W))
        
 
 
 
 
	    
	    
	    
	    
	    
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(3.U(4.W))
	    
	    dut.io.controller_out.max_layer.expect(10.U(16.W))
	    dut.io.controller_out.nn_description_table_address.expect(2.U(16.W))
	    
	    dut.io.controller_out.current_buffer_memory_pointer.expect(8.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(9.U(16.W))
	    
	    dut.clock.step()

	    dut.io.controller_in.nn_description_table_input.poke(10.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(4.U(4.W))
	    
	    dut.io.controller_out.nn_description_table_address.expect(1.U(16.W))
	    
	    dut.io.controller_out.iteration_layer.expect(1.U(16.W))
	    dut.io.controller_out.current_buffer_memory_pointer.expect(8.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(9.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(5.U(4.W))

	    dut.io.controller_in.nn_description_table_input.poke(15.U(16.W))
	    dut.io.controller_out.nn_description_table_address.expect(3.U(16.W))
	    
	    dut.io.controller_out.current_buffer_memory_pointer.expect(8.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(9.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_in.nn_description_table_input.poke(8.U(16.W))	    
	    
	    dut.clock.step()
	    
	    
	    dut.io.controller_out.current_state.expect(6.U(4.W))
	    
	    
	    dut.io.controller_out.load_datapoint.expect(false.B)
	    dut.io.controller_out.current_buffer_memory_pointer.expect(8.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(9.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_in.load_datapoint_complete.poke(true.B)
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.io.controller_out.current_state.expect(6.U(4.W))
	    
	    dut.io.controller_out.load_datapoint.expect(true.B)
	    dut.io.controller_out.load_new_request.expect(false.B)
	    dut.io.controller_out.load_data_from_buffer.expect(false.B)
	    dut.io.controller_out.load_same_data.expect(true.B)

	    dut.io.controller_out.current_buffer_memory_pointer.expect(8.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(9.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_in.load_datapoint_complete.poke(false.B)
	    
	    dut.io.controller_out.current_state.expect(7.U(4.W))
	    
	    dut.io.controller_out.load_datapoint.expect(true.B)
	    dut.io.controller_out.load_new_request.expect(false.B)
	    dut.io.controller_out.load_data_from_buffer.expect(false.B)
	    dut.io.controller_out.load_same_data.expect(true.B)

	    dut.io.controller_out.current_buffer_memory_pointer.expect(8.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(9.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(false.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(false.B)
	    dut.io.controller_out.address_generator_reset.expect(true.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(false.B)

	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(7.U(4.W))
	    
	    dut.io.controller_in.address_generation_complete.poke(true.B)
	    dut.io.controller_in.load_buffer_weight_memory_complete.poke(true.B)

	    dut.io.controller_out.save_data_request.expect(false.B)
	    
	    dut.io.controller_out.current_buffer_memory_pointer.expect(8.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(9.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.io.controller_out.read_memoryUnits(0).expect(1.U(2.W))
	    dut.io.controller_out.read_memoryUnits(1).expect(1.U(2.W))
	    dut.io.controller_out.read_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.write_memoryUnits(0).expect(2.U(2.W))
	    dut.io.controller_out.write_memoryUnits(1).expect(2.U(2.W))
	    dut.io.controller_out.write_memoryUnits(2).expect(2.U(2.W))
	    dut.io.controller_out.write_memoryUnits(3).expect(2.U(2.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(true.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(true.B)
	    dut.io.controller_out.address_generator_reset.expect(false.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(true.B)
	    
	    dut.io.controller_out.loading_activations(0).expect(1.U(16.W))
	    dut.io.controller_out.loading_activations(1).expect(2.U(16.W))
	    dut.io.controller_out.loading_activations(2).expect(3.U(16.W))
	    dut.io.controller_out.loading_activations(3).expect(4.U(16.W))

           
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(8.U(4.W))
	    
	    dut.io.controller_in.address_generation_complete.poke(false.B)
	    dut.io.controller_in.load_buffer_weight_memory_complete.poke(false.B)

	    dut.io.controller_out.save_data_request.expect(true.B)
	    dut.io.controller_in.save_data_complete.poke(true.B)
	    
	    dut.io.controller_out.current_buffer_memory_pointer.expect(8.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(9.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.io.controller_out.read_memoryUnits(0).expect(1.U(2.W))
	    dut.io.controller_out.read_memoryUnits(1).expect(1.U(2.W))
	    dut.io.controller_out.read_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.write_memoryUnits(0).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(1).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(true.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(true.B)
	    dut.io.controller_out.address_generator_reset.expect(false.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(false.B)
	    
	    dut.io.controller_out.loading_activations(0).expect(1.U(16.W))
	    dut.io.controller_out.loading_activations(1).expect(2.U(16.W))
	    dut.io.controller_out.loading_activations(2).expect(3.U(16.W))
	    dut.io.controller_out.loading_activations(3).expect(4.U(16.W))
           
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(2.U(4.W))
	    	    
	    dut.io.controller_out.max_layer.expect(10.U(16.W))

	    dut.io.controller_in.save_data_complete.poke(false.B)

	    dut.io.controller_out.current_buffer_memory_pointer.expect(12.U(16.W))
	    dut.io.controller_out.current_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(13.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(10.U(16.W))
	    dut.io.controller_out.max_iteration.expect(16.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.io.controller_out.read_memoryUnits(0).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(1).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.write_memoryUnits(0).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(1).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(false.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(false.B)
	    dut.io.controller_out.address_generator_reset.expect(true.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(false.B)
	    
	    dut.io.controller_out.loading_activations(0).expect(1.U(16.W))
	    dut.io.controller_out.loading_activations(1).expect(2.U(16.W))
	    dut.io.controller_out.loading_activations(2).expect(3.U(16.W))
	    dut.io.controller_out.loading_activations(3).expect(4.U(16.W))















	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(3.U(4.W))
	    
	    dut.io.controller_out.max_layer.expect(10.U(16.W))
	    dut.io.controller_out.nn_description_table_address.expect(3.U(16.W))
	    
	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(3.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.clock.step()

	    dut.io.controller_in.nn_description_table_input.poke(8.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(4.U(4.W))
	    
	    dut.io.controller_out.nn_description_table_address.expect(2.U(16.W))
	    
	    dut.io.controller_out.iteration_layer.expect(2.U(16.W))
	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(3.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(8.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(5.U(4.W))

	    dut.io.controller_in.nn_description_table_input.poke(10.U(16.W))
	    dut.io.controller_out.nn_description_table_address.expect(3.U(16.W))
	    
	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(3.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(8.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    
	    dut.clock.step()

	    dut.io.controller_in.nn_description_table_input.poke(8.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(6.U(4.W))
	    
	    
	    dut.io.controller_out.load_datapoint.expect(false.B)
	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(3.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(8.U(16.W))
	    dut.io.controller_out.max_iteration.expect(11.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_in.load_datapoint_complete.poke(true.B)
	    
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.io.controller_out.current_state.expect(6.U(4.W))
	    
	    dut.io.controller_out.load_datapoint.expect(true.B)
	    dut.io.controller_out.load_new_request.expect(false.B)
	    dut.io.controller_out.load_data_from_buffer.expect(true.B)
	    dut.io.controller_out.load_same_data.expect(false.B)

	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(3.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(8.U(16.W))
	    dut.io.controller_out.max_iteration.expect(11.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.controller_in.load_datapoint_complete.poke(false.B)
	    
	    dut.io.controller_out.current_state.expect(7.U(4.W))
	    
	    dut.io.controller_out.load_datapoint.expect(true.B)
	    dut.io.controller_out.load_new_request.expect(false.B)
	    dut.io.controller_out.load_data_from_buffer.expect(true.B)
	    dut.io.controller_out.load_same_data.expect(false.B)

	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(3.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(8.U(16.W))
	    dut.io.controller_out.max_iteration.expect(11.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(false.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(false.B)
	    dut.io.controller_out.address_generator_reset.expect(true.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(false.B)

	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(7.U(4.W))
	    
	    dut.io.controller_in.address_generation_complete.poke(true.B)
	    dut.io.controller_in.load_buffer_weight_memory_complete.poke(true.B)

	    dut.io.controller_out.save_data_request.expect(false.B)
	    
	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(3.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(8.U(16.W))
	    dut.io.controller_out.max_iteration.expect(11.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.io.controller_out.read_memoryUnits(0).expect(2.U(2.W))
	    dut.io.controller_out.read_memoryUnits(1).expect(2.U(2.W))
	    dut.io.controller_out.read_memoryUnits(2).expect(2.U(2.W))
	    dut.io.controller_out.read_memoryUnits(3).expect(2.U(2.W))
	    
	    dut.io.controller_out.write_memoryUnits(0).expect(1.U(2.W))
	    dut.io.controller_out.write_memoryUnits(1).expect(1.U(2.W))
	    dut.io.controller_out.write_memoryUnits(2).expect(1.U(2.W))
	    dut.io.controller_out.write_memoryUnits(3).expect(1.U(2.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(true.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(true.B)
	    dut.io.controller_out.address_generator_reset.expect(false.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(true.B)
	    
	    dut.io.controller_out.loading_activations(0).expect(5.U(16.W))
	    dut.io.controller_out.loading_activations(1).expect(6.U(16.W))
	    dut.io.controller_out.loading_activations(2).expect(7.U(16.W))
	    dut.io.controller_out.loading_activations(3).expect(8.U(16.W))

           
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(8.U(4.W))

	    dut.io.controller_out.save_data_request.expect(true.B)
	    dut.io.controller_in.save_data_complete.poke(true.B)
	    
	    dut.io.controller_in.address_generation_complete.poke(false.B)
	    dut.io.controller_in.load_buffer_weight_memory_complete.poke(false.B)
	    
	    dut.io.controller_out.current_buffer_memory_pointer.expect(0.U(16.W))
	    dut.io.controller_out.current_layer.expect(3.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(1.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(8.U(16.W))
	    dut.io.controller_out.max_iteration.expect(11.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.io.controller_out.read_memoryUnits(0).expect(2.U(2.W))
	    dut.io.controller_out.read_memoryUnits(1).expect(2.U(2.W))
	    dut.io.controller_out.read_memoryUnits(2).expect(2.U(2.W))
	    dut.io.controller_out.read_memoryUnits(3).expect(2.U(2.W))
	    
	    dut.io.controller_out.write_memoryUnits(0).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(1).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(true.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(true.B)
	    dut.io.controller_out.address_generator_reset.expect(false.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(false.B)
	    
	    dut.io.controller_out.loading_activations(0).expect(5.U(16.W))
	    dut.io.controller_out.loading_activations(1).expect(6.U(16.W))
	    dut.io.controller_out.loading_activations(2).expect(7.U(16.W))
	    dut.io.controller_out.loading_activations(3).expect(8.U(16.W))
           
	    dut.clock.step()
	    
	    dut.io.controller_out.current_state.expect(2.U(4.W))

	    dut.io.controller_in.save_data_complete.poke(false.B)
	    	    
	    dut.io.controller_out.max_layer.expect(10.U(16.W))

	    dut.io.controller_out.current_buffer_memory_pointer.expect(4.U(16.W))
	    dut.io.controller_out.current_layer.expect(3.U(16.W))
	    dut.io.controller_out.current_layer_current_activation.expect(5.U(16.W))
	    
	    dut.io.controller_out.current_layer_total_activations.expect(8.U(16.W))
	    dut.io.controller_out.max_iteration.expect(11.U(16.W))
	    dut.io.controller_out.loading_layer.expect(3.U(16.W))
	    dut.io.controller_out.loading_layer_total_activations.expect(8.U(16.W))
	    
	    dut.io.controller_out.read_memoryUnits(0).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(1).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.read_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.write_memoryUnits(0).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(1).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(2).expect(0.U(2.W))
	    dut.io.controller_out.write_memoryUnits(3).expect(0.U(2.W))
	    
	    dut.io.controller_out.address_generator_address_valid.expect(false.B)
	    dut.io.controller_out.address_generator_enable_valid.expect(false.B)
	    dut.io.controller_out.address_generator_reset.expect(true.B)
	    dut.io.controller_out.weight_buffer_load_request.expect(false.B)
	    
	    dut.io.controller_out.loading_activations(0).expect(5.U(16.W))
	    dut.io.controller_out.loading_activations(1).expect(6.U(16.W))
	    dut.io.controller_out.loading_activations(2).expect(7.U(16.W))
	    dut.io.controller_out.loading_activations(3).expect(8.U(16.W))
        
        }
    }
}
