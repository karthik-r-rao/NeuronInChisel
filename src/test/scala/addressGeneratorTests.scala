package controlUnitTests

import chisel3._
import chisel3.util.log2Up
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import ControlUnit._ 

class AddressGeneratorSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new AddressGenerator(8, 32, 16, 4)) { dut => 
            dut.io.address_generator_in.reset.poke(true.B)
            dut.io.address_generator_in.address_valid.poke(true.B)
            dut.io.address_generator_in.enable_valid.poke(true.B)
                        
            dut.io.address_generator_in.max_iteration.poke(5.U(16.W))
            
            dut.io.address_generator_in.memoryUnits(0).poke(2.U(2.W))
            dut.io.address_generator_in.memoryUnits(1).poke(2.U(2.W))
            dut.io.address_generator_in.memoryUnits(2).poke(2.U(2.W))
            dut.io.address_generator_in.memoryUnits(3).poke(2.U(2.W))

            dut.io.address_generator_out.bias_valid.expect(false.B)
            dut.io.address_generator_out.neuron_reset.expect(true.B)
            
            dut.clock.step()
            
            dut.io.address_generator_in.reset.poke(false.B)

            dut.io.address_generator_out.Address.expect(0.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)

            dut.io.address_generator_out.bias_valid.expect(false.B)
            dut.io.address_generator_out.neuron_reset.expect(false.B)

            dut.io.address_generator_out.read_enables(0).expect(2.U(2.W))
            dut.io.address_generator_out.read_enables(1).expect(2.U(2.W))
            dut.io.address_generator_out.read_enables(2).expect(2.U(2.W))
            dut.io.address_generator_out.read_enables(3).expect(2.U(2.W))
                        
            dut.clock.step()

            dut.io.address_generator_out.Address.expect(1.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)
            
            dut.io.address_generator_out.bias_valid.expect(false.B)
            dut.io.address_generator_out.neuron_reset.expect(false.B)

            dut.clock.step()

            dut.io.address_generator_out.Address.expect(2.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)

            dut.io.address_generator_out.bias_valid.expect(false.B)
            dut.io.address_generator_out.neuron_reset.expect(false.B)

            dut.clock.step()

            dut.io.address_generator_out.Address.expect(3.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)
            
            dut.io.address_generator_out.bias_valid.expect(false.B)
            dut.io.address_generator_out.neuron_reset.expect(false.B)


            dut.clock.step()

            dut.io.address_generator_out.Address.expect(4.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)

            dut.io.address_generator_out.bias_valid.expect(true.B)
            dut.io.address_generator_out.neuron_reset.expect(false.B)

            dut.clock.step()            

	    dut.io.address_generator_in.reset.poke(false.B)

            dut.io.address_generator_out.Address.expect(5.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(true.B)
                        
            dut.io.address_generator_out.bias_valid.expect(false.B)
            dut.io.address_generator_out.neuron_reset.expect(true.B)

            dut.clock.step()
            
	    dut.io.address_generator_in.reset.poke(true.B)

            dut.io.address_generator_out.Address.expect(5.U(log2Up(16).W))

            dut.io.address_generator_out.bias_valid.expect(false.B)
            dut.io.address_generator_out.neuron_reset.expect(true.B)
            
            dut.io.address_generator_in.address_valid.poke(true.B)
            dut.io.address_generator_in.enable_valid.poke(true.B)
                        
            dut.io.address_generator_in.max_iteration.poke(3.U(16.W))
            
            dut.io.address_generator_in.memoryUnits(0).poke(1.U(2.W))
            dut.io.address_generator_in.memoryUnits(1).poke(1.U(2.W))
            dut.io.address_generator_in.memoryUnits(2).poke(1.U(2.W))
            dut.io.address_generator_in.memoryUnits(3).poke(0.U(2.W))
            
            dut.clock.step()

            
            dut.io.address_generator_in.reset.poke(false.B)

            dut.io.address_generator_out.Address.expect(0.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)

            dut.io.address_generator_out.read_enables(0).expect(1.U(2.W))
            dut.io.address_generator_out.read_enables(1).expect(1.U(2.W))
            dut.io.address_generator_out.read_enables(2).expect(1.U(2.W))
            dut.io.address_generator_out.read_enables(3).expect(0.U(2.W))

            dut.io.address_generator_out.bias_valid.expect(false.B)
            dut.io.address_generator_out.neuron_reset.expect(false.B)
            

            dut.clock.step()
            
            dut.io.address_generator_out.Address.expect(1.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)

            dut.io.address_generator_out.bias_valid.expect(false.B)
            dut.io.address_generator_out.neuron_reset.expect(false.B)
            

            dut.clock.step()
            
            dut.io.address_generator_out.Address.expect(2.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)

            dut.io.address_generator_out.bias_valid.expect(true.B)
            dut.io.address_generator_out.neuron_reset.expect(false.B)
                        
            dut.clock.step()
            
            dut.io.address_generator_out.Address.expect(3.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(true.B)

            dut.io.address_generator_out.bias_valid.expect(false.B)
            dut.io.address_generator_out.neuron_reset.expect(true.B)
            
        }
    }
}
