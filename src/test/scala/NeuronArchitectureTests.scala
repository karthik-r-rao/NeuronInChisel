//  Inputs
//    val controller_reset = Input(Bool())
    
//    val interconnect_new_datapoint_ready = Input(Bool())
//    val interconnect_load_ready = Input(Bool())
//    val interconnect_memory_output = Input(UInt(datawidth.W))

//  Outputs
//    val interconnect_loading_layer = Output(Bits(datawidth.W))
//    val interconnect_loading_activation = Output(Bits(datawidth.W))
//    val interconnect_load_request = Output(Bool())
    
//    val interconnect_load_read_address = Output(Bits(datawidth.W))  
    
//    val load_new_data_request = Output(Bool())
    
//    val PE_outputs = Outputs(Vec(numberOfPE, UInt(datawidth.W)))

//    val controlUnit_current_state = Output(Bits(datawidth.W))
//    val load_pe_memory_load_weights_state = Output(Bits(datawidth.W))
//    val load_pe_memory_load_datapoint_state = Output(Bits(datawidth.W))



package NeuronArchitectureTests

import chisel3._
import chisel3.util.log2Up
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

import ControlUnit._
import NeuronArchitecture._
import nn._


class NeuronArchitectureSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new NeuronArchitecture(8, 32, 18, 4, 32)) { dut => 

//Inputs
            	dut.io.neuron_architecture_in.controller_reset.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)
		
		dut.io.neuron_architecture_out.PE_outputs(0).expect("b000100000100000".U(18.W))
		dut.io.neuron_architecture_out.PE_outputs(1).expect("b000100000100000".U(18.W))
		dut.io.neuron_architecture_out.PE_outputs(2).expect("b000100000100000".U(18.W))
		dut.io.neuron_architecture_out.PE_outputs(3).expect("b000100000100000".U(18.W))
		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))
            	
            	dut.clock.step()
            	
            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)

            	dut.clock.step()
            	
            	dut.clock.step()
            	
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))
            	
            	dut.clock.step()
            	
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(3.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

            	dut.clock.step()

            	dut.clock.step()
            	
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(4.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

            	dut.clock.step()
            	
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(5.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))
		
		dut.clock.step()            

            	dut.clock.step()
            	
		dut.clock.step()
		
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))










// Load Initial Weights Stage







		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(2.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(1.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(true.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)













		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))


		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b100101100".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(1.U(18.W))


		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b11110010001".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(2.U(18.W))
				 
 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b10100000111".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(3.U(18.W))


 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b10110000110".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(4.U(18.W))


 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b11001101111".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(5.U(18.W))


 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(2.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(2.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(true.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)
















		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))

		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111111101101000".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(1.U(18.W))


		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b11110010001".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(2.U(18.W))
				
 
 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111100101111110".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(3.U(18.W))


 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b11001101111".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(4.U(18.W))


 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b1100101000".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(5.U(18.W))


 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(2.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(3.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(true.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)








		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))


		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111100010000100".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(1.U(18.W))


		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b1010100100".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(2.U(18.W))
				
 
 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111100011100111".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(3.U(18.W))


 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b10100010011".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(4.U(18.W))


 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111101010011001".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(5.U(18.W))


 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(2.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(4.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(true.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)












		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))


		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111100101000000".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(1.U(18.W))

		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111110000100101".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(2.U(18.W))
				
 
 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b11010100010".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(3.U(18.W))


 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b11000010100".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(4.U(18.W))


 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b10001101111".U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(5.U(18.W))



 		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))

//Outputs
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(3.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)
		
		
		
		
		
		
//	END INITIAL_WEIGHT_LOAD_STAGE		
		
		
		
		
		dut.clock.step()
		
		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)
				
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(3.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))
		
		
		dut.clock.step()

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(true.B)
		

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(3.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		
		dut.clock.step()
		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(true.B)
				







// LOAD DATAPOINT STAGE











		dut.clock.step()
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(true.B)
				

		dut.clock.step()
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)
				

		dut.clock.step()
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111101001011010".U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b1100011111".U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)



		dut.clock.step()
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b10110110111".U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(3.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)
		

		dut.clock.step()
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111110110111011".U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(4.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)
		
		
		dut.clock.step()
//Inputs
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111110110111011".U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(5.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)
		
		
		dut.clock.step()
//Inputs
            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(1.U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(2.U(18.W))














		dut.clock.step()
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(2.U(18.W))

		dut.io.neuron_architecture_out.neuron_reset.expect(true.B)
		dut.io.neuron_architecture_out.neuron_bias.expect(false.B)



		dut.clock.step()
		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(2.U(18.W))

		dut.io.neuron_architecture_out.neuron_reset.expect(true.B)
		dut.io.neuron_architecture_out.neuron_bias.expect(false.B)
		 
		dut.clock.step()
		
//Inputs
            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(1.U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(3.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(1.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(true.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)

		dut.io.neuron_architecture_out.neuron_reset.expect(false.B)
		dut.io.neuron_architecture_out.neuron_bias.expect(false.B)

		
		dut.clock.step()
		
		
		
		
// NEW WEIGHT LOAD STAGE
		
		

//Inputs
            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(1.U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b11010010001".U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b1111011100".U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b11001111".U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(3.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111101110010111".U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.PE_outputs(0).expect("b101000100000".U(18.W))
		dut.io.neuron_architecture_out.PE_outputs(1).expect("b100001100000".U(18.W))
		dut.io.neuron_architecture_out.PE_outputs(2).expect("b11011000000".U(18.W))
		dut.io.neuron_architecture_out.PE_outputs(3).expect("b100111100000".U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(4.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111111111111001".U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(5.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)













		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(3.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(2.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(true.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()

            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(1.U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()

            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(1.U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))



		dut.clock.step()

            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b1111001001".U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(1.U(18.W))


		dut.clock.step()

            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b1101001000".U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(2.U(18.W))


		dut.clock.step()

            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b11111111111".U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(3.U(18.W))



		dut.clock.step()

            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b11111100110".U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(4.U(18.W))


		dut.clock.step()

            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b1010101011".U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(5.U(18.W))










		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(3.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(3.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(true.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)


		dut.clock.step()

            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(1.U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)

		dut.io.neuron_architecture_out.neuron_reset.expect(true.B)
		dut.io.neuron_architecture_out.neuron_bias.expect(false.B)


		dut.clock.step()

            	dut.io.neuron_architecture_in.controller_reset.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_new_datapoint_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(1.U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))

		dut.clock.step()

            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111111001111000".U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(1.U(18.W))

		dut.clock.step()

            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b1111111101".U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(2.U(18.W))

		dut.clock.step()

            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111100010101101".U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(3.U(18.W))




		dut.clock.step()

            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b1011001001".U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(4.U(18.W))


		dut.clock.step()

            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111100010111100".U(18.W))

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(5.U(18.W))


		dut.clock.step()

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)


		dut.clock.step()

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(3.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))











// ONE CYCLE COMPLETE







		dut.clock.step()

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(3.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))


            	dut.clock.step()
            	
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(3.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

            	dut.clock.step()

            	dut.clock.step()
            	
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(4.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

            	dut.clock.step()
            	
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(5.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))
		
		dut.clock.step()            
            	
		dut.clock.step()

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))		
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))
				

		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))



		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(2.U(18.W))


		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(2.U(18.W))


		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(2.U(18.W))

		dut.io.neuron_architecture_out.neuron_reset.expect(true.B)
		dut.io.neuron_architecture_out.neuron_bias.expect(false.B)

		dut.clock.step()

		
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(1.U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(4.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(1.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(true.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)
		
		dut.io.neuron_architecture_out.neuron_reset.expect(false.B)
		dut.io.neuron_architecture_out.neuron_bias.expect(false.B)

		dut.clock.step()		
		

		
// NEW WEIGHT LOAD STAGE
		
		

//Inputs
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(true.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(1.U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)



		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(0.U(18.W))
		
//Outputs
//		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


//		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))


		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111111100100001110".U(18.W))
		
//Outputs
//		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


//		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(1.U(18.W))


		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b111".U(18.W))
		
//Outputs
//		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


//		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(2.U(18.W))


		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b1110010000".U(18.W))
		
//Outputs
//		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


//		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(3.U(18.W))


		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b10110110000".U(18.W))
		
//Outputs
//		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.PE_outputs(0).expect("b100000000000".U(18.W))
		dut.io.neuron_architecture_out.PE_outputs(1).expect("b101110000000".U(18.W))
		dut.io.neuron_architecture_out.PE_outputs(2).expect("b11001000000".U(18.W))
//		dut.io.neuron_architecture_out.PE_outputs(3).expect("b100111100000".U(18.W))

//		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(4.U(18.W))


		dut.clock.step()

//Inputs
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke("b10110110000".U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(5.U(18.W))






		dut.clock.step()

//		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

//		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))

//		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(0.U(18.W))
//		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(0.U(18.W))
//		dut.io.neuron_architecture_out.interconnect_load_request.expect(false.B)


		dut.clock.step()

//		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(3.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

//		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))


		dut.clock.step()

//		dut.io.neuron_architecture_out.controlUnit_current_state.expect(8.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(3.U(18.W))
//		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

//		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))







// TWO CYCLE COMPLETE







		dut.clock.step()

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(2.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))



            	dut.clock.step()
            	
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(3.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

            	dut.clock.step()

            	dut.clock.step()
            	
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(4.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))

            	dut.clock.step()
            	
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(5.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))
		
		dut.clock.step()            
            	
		dut.clock.step()

		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))		
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))
				

		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))



		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.clock.step()


		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(1.U(18.W))


		dut.clock.step()


		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(6.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(2.U(18.W))


		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(2.U(18.W))


		dut.clock.step()

		
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(2.U(18.W))

		dut.io.neuron_architecture_out.neuron_reset.expect(true.B)
		dut.io.neuron_architecture_out.neuron_bias.expect(false.B)

		dut.clock.step()

		
            	dut.io.neuron_architecture_in.interconnect_load_ready.poke(false.B)
            	dut.io.neuron_architecture_in.interconnect_memory_output.poke(1.U(18.W))
		
//Outputs
		dut.io.neuron_architecture_out.controlUnit_current_state.expect(7.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_weights_state.expect(1.U(18.W))
		dut.io.neuron_architecture_out.load_pe_memory_load_datapoint_state.expect(0.U(18.W))


		dut.io.neuron_architecture_out.interconnect_loading_layer.expect(2.U(18.W))
		dut.io.neuron_architecture_out.interconnect_loading_activation.expect(1.U(18.W))
		dut.io.neuron_architecture_out.interconnect_load_request.expect(true.B)
		dut.io.neuron_architecture_out.interconnect_load_read_address.expect(0.U(18.W))
		dut.io.neuron_architecture_out.load_new_data_request.expect(false.B)
		
		dut.io.neuron_architecture_out.neuron_reset.expect(false.B)
		dut.io.neuron_architecture_out.neuron_bias.expect(false.B)

		dut.clock.step()
		
		
		
		
		
		
		
		
// NEW WEIGHT LOAD STAGE
		



		dut.clock.step()


		dut.clock.step()



		dut.clock.step()


		dut.clock.step()



		dut.clock.step()


		dut.io.neuron_architecture_out.PE_outputs(0).expect("b101000000000".U(18.W))
//		dut.io.neuron_architecture_out.PE_outputs(1).expect("b101110000000".U(18.W))
//		dut.io.neuron_architecture_out.PE_outputs(2).expect("b11001000000".U(18.W))
//		dut.io.neuron_architecture_out.PE_outputs(3).expect("b100111100000".U(18.W))
	

        }
    }
}

