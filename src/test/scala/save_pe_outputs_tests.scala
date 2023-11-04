//Inputs
//val save_data_request = Input(Bool())
//val current_buffer_memory_pointer = Input(Bits(datawidth.W))    
//val PE_outputs = Input(Vec(numberOfPE, Bits(datawidth.W)))

//Outputs
//val save_data_complete = Output(Bool())
//val buffer_memory_write_enable = Output(Bool())
//val buffer_memory_write_address = Output(Bits(datawidth.W))
//val buffer_memory_write_data = Output(Bits(datawidth.W))



package controlUnitTests

import chisel3._
import chisel3.util.log2Up
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import ControlUnit._ 

class SavePEOutputSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new SavePEOutput(8, 32, 16, 4)) { dut => 

	    dut.io.save_pe_output_in.save_data_request.poke(false.B)
	    dut.io.save_pe_output_out.buffer_memory_write_enable.expect(false.B)
    	    dut.io.save_pe_output_out.buffer_memory_write_address.expect(0.U(16.W))
	    dut.io.save_pe_output_out.buffer_memory_write_data.expect(0.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.save_pe_output_in.save_data_request.poke(true.B)
	    dut.io.save_pe_output_in.current_buffer_memory_pointer.poke(4.U(16.W))
	    dut.io.save_pe_output_in.PE_outputs(0).poke(10.U(16.W))
	    dut.io.save_pe_output_in.PE_outputs(1).poke(20.U(16.W))
	    dut.io.save_pe_output_in.PE_outputs(2).poke(30.U(16.W))
	    dut.io.save_pe_output_in.PE_outputs(3).poke(40.U(16.W))
	    dut.io.save_pe_output_out.save_data_complete.expect(false.B)
	    
	    dut.clock.step()
	    
	    
	    dut.io.save_pe_output_out.buffer_memory_write_enable.expect(true.B)
    	    dut.io.save_pe_output_out.buffer_memory_write_address.expect(4.U(16.W))
	    dut.io.save_pe_output_out.buffer_memory_write_data.expect(10.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.save_pe_output_out.buffer_memory_write_enable.expect(true.B)
    	    dut.io.save_pe_output_out.buffer_memory_write_address.expect(5.U(16.W))
	    dut.io.save_pe_output_out.buffer_memory_write_data.expect(20.U(16.W))

	    dut.clock.step()
	    
	    dut.io.save_pe_output_out.buffer_memory_write_enable.expect(true.B)
    	    dut.io.save_pe_output_out.buffer_memory_write_address.expect(6.U(16.W))
	    dut.io.save_pe_output_out.buffer_memory_write_data.expect(30.U(16.W))

	    dut.clock.step()
	    
	    dut.io.save_pe_output_out.buffer_memory_write_enable.expect(true.B)
    	    dut.io.save_pe_output_out.buffer_memory_write_address.expect(7.U(16.W))
	    dut.io.save_pe_output_out.buffer_memory_write_data.expect(40.U(16.W))
	    dut.io.save_pe_output_out.save_data_complete.expect(false.B)
	    
	    dut.clock.step()
	    
	    dut.io.save_pe_output_out.buffer_memory_write_enable.expect(false.B)
    	    dut.io.save_pe_output_out.buffer_memory_write_address.expect(0.U(16.W))
	    dut.io.save_pe_output_out.buffer_memory_write_data.expect(0.U(16.W))	    
	    dut.io.save_pe_output_out.save_data_complete.expect(true.B)
	    
	    dut.clock.step()

	    dut.io.save_pe_output_out.save_data_complete.expect(false.B)
	    dut.io.save_pe_output_in.current_buffer_memory_pointer.poke(0.U(16.W))
	    dut.io.save_pe_output_in.PE_outputs(0).poke(0.U(16.W))
	    dut.io.save_pe_output_in.PE_outputs(1).poke(0.U(16.W))
	    dut.io.save_pe_output_in.PE_outputs(2).poke(0.U(16.W))
	    dut.io.save_pe_output_in.PE_outputs(3).poke(0.U(16.W))
	    dut.io.save_pe_output_in.save_data_request.poke(true.B)

	    dut.io.save_pe_output_out.buffer_memory_write_enable.expect(false.B)
    	    dut.io.save_pe_output_out.buffer_memory_write_address.expect(0.U(16.W))
	    dut.io.save_pe_output_out.buffer_memory_write_data.expect(0.U(16.W))	    
	    
	    dut.clock.step()

	    dut.io.save_pe_output_in.save_data_request.poke(false.B)
	    dut.io.save_pe_output_out.buffer_memory_write_enable.expect(false.B)
    	    dut.io.save_pe_output_out.buffer_memory_write_address.expect(0.U(16.W))
	    dut.io.save_pe_output_out.buffer_memory_write_data.expect(0.U(16.W))	    
	    
	    dut.clock.step()

	    dut.io.save_pe_output_in.save_data_request.poke(false.B)
	    dut.io.save_pe_output_out.buffer_memory_write_enable.expect(false.B)
    	    dut.io.save_pe_output_out.buffer_memory_write_address.expect(0.U(16.W))
	    dut.io.save_pe_output_out.buffer_memory_write_data.expect(0.U(16.W))	    


	}
    }
}
