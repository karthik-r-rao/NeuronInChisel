package controlUnitTests

import chisel3._
import chisel3.util.log2Up
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import ControlUnit._ 

class AddressGeneratorSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new AddressGenerator(128, 16, 16)) { dut => 
            dut.io.address_generator_in.reset.poke(true.B)
            dut.io.address_generator_in.address_valid.poke(true.B)
            dut.io.address_generator_in.enable_valid.poke(true.B)
            dut.io.address_generator_in.datapoint_write_valid.poke(false.B)
            
            dut.io.address_generator_in.datapoint_write_data.poke(10.U(16.W))
            
            dut.io.address_generator_in.max_iteration.poke(5.U(16.W))
            
            dut.io.address_generator_in.memoryUnit1.poke(4.U(16.W))
            dut.io.address_generator_in.memoryUnit2.poke(5.U(16.W))
            dut.io.address_generator_in.memoryUnit3.poke(6.U(16.W))
            dut.io.address_generator_in.memoryUnit4.poke(7.U(16.W))
            
            dut.clock.step()
            
            dut.io.address_generator_in.reset.poke(false.B)

            dut.io.address_generator_out.Address.expect(0.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)

            dut.io.address_generator_out.read_enable1.expect(16.U(128.W))
            dut.io.address_generator_out.read_enable2.expect(32.U(128.W))
            dut.io.address_generator_out.read_enable3.expect(64.U(128.W))
            dut.io.address_generator_out.read_enable4.expect(128.U(128.W))
            
            dut.io.address_generator_out.datapoint_write_valid.expect(false.B)
            dut.io.address_generator_out.datapoint_write_data.expect(0.U(16.W))
            
            dut.clock.step()

            dut.io.address_generator_out.Address.expect(1.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)
            
            dut.io.address_generator_out.datapoint_write_valid.expect(false.B)
            dut.io.address_generator_out.datapoint_write_data.expect(0.U(16.W))


            dut.clock.step()

            dut.io.address_generator_out.Address.expect(2.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)

            dut.io.address_generator_out.datapoint_write_valid.expect(false.B)
            dut.io.address_generator_out.datapoint_write_data.expect(0.U(16.W))

            dut.clock.step()

            dut.io.address_generator_out.Address.expect(3.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)

            dut.io.address_generator_out.datapoint_write_valid.expect(false.B)
            dut.io.address_generator_out.datapoint_write_data.expect(0.U(16.W))

            dut.clock.step()

            dut.io.address_generator_out.Address.expect(4.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(true.B)

            dut.io.address_generator_out.datapoint_write_valid.expect(false.B)
            dut.io.address_generator_out.datapoint_write_data.expect(0.U(16.W))

            dut.clock.step()            
            
            dut.io.address_generator_in.address_valid.poke(false.B)
            
            dut.io.address_generator_out.Address.expect(0.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)

            dut.io.address_generator_out.datapoint_write_valid.expect(false.B)
            dut.io.address_generator_out.datapoint_write_data.expect(0.U(16.W))

            dut.clock.step()            

            dut.io.address_generator_out.Address.expect(0.U(log2Up(16).W))
            
            dut.io.address_generator_in.reset.poke(true.B)
            dut.io.address_generator_in.address_valid.poke(true.B)
            dut.io.address_generator_in.enable_valid.poke(false.B)
            dut.io.address_generator_in.datapoint_write_valid.poke(true.B)
            
            dut.io.address_generator_in.datapoint_write_data.poke(10.U(16.W))
            
            dut.io.address_generator_in.max_iteration.poke(3.U(16.W))
            
            dut.io.address_generator_in.memoryUnit1.poke(4.U(16.W))
            dut.io.address_generator_in.memoryUnit2.poke(5.U(16.W))
            dut.io.address_generator_in.memoryUnit3.poke(6.U(16.W))
            dut.io.address_generator_in.memoryUnit4.poke(7.U(16.W))
            
            dut.clock.step()

            dut.io.address_generator_in.datapoint_write_data.poke(20.U(16.W))
            
            dut.io.address_generator_in.reset.poke(false.B)

            dut.io.address_generator_out.Address.expect(0.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)

            dut.io.address_generator_out.read_enable1.expect(0.U(128.W))
            dut.io.address_generator_out.read_enable2.expect(0.U(128.W))
            dut.io.address_generator_out.read_enable3.expect(0.U(128.W))
            dut.io.address_generator_out.read_enable4.expect(0.U(128.W))
            
            dut.io.address_generator_out.datapoint_write_valid.expect(true.B)
            dut.io.address_generator_out.datapoint_write_data.expect(10.U(16.W))

            dut.clock.step()

            dut.io.address_generator_in.datapoint_write_data.poke(30.U(16.W))
            
            dut.io.address_generator_out.Address.expect(1.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)
            
            dut.io.address_generator_out.datapoint_write_valid.expect(true.B)
            dut.io.address_generator_out.datapoint_write_data.expect(20.U(16.W))

            dut.clock.step()

            dut.io.address_generator_in.datapoint_write_data.poke(40.U(16.W))
            
            dut.io.address_generator_out.Address.expect(2.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(true.B)
            
            dut.io.address_generator_out.datapoint_write_valid.expect(true.B)
            dut.io.address_generator_out.datapoint_write_data.expect(30.U(16.W))
            
            dut.clock.step()

            dut.io.address_generator_in.datapoint_write_data.poke(0.U(16.W))
            
            dut.io.address_generator_out.Address.expect(0.U(log2Up(16).W))
            dut.io.address_generator_out.address_generation_complete.expect(false.B)
            
            dut.io.address_generator_out.datapoint_write_valid.expect(false.B)
            dut.io.address_generator_out.datapoint_write_data.expect(40.U(16.W))

        }
    }
}
