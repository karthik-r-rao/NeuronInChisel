package neurontests

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import neuron._ 

class MultiplyAccumulateSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new MultiplyAccumulate(10)) { dut => 
            dut.io.control_io.reset.poke(true.B)
            dut.io.control_io.weight_in_valid.poke(false.B)
            dut.io.control_io.data_in_valid.poke(false.B)
            dut.io.control_io.bias_in_valid.poke(false.B)
            
            dut.clock.step()

            val weight_in = 15
            val data_in = 27
            dut.io.control_io.weight_in_valid.poke(true.B)
            dut.io.control_io.data_in_valid.poke(true.B)
            dut.io.data_io.weight_in.poke(weight_in.U)
            dut.io.data_io.data_in.poke(data_in.U)

            dut.clock.step()

            val bias_in = 21
            dut.io.control_io.bias_in_valid.poke(true.B)
            dut.io.control_io.weight_in_valid.poke(false.B)
            dut.io.control_io.data_in_valid.poke(false.B)
            dut.io.data_io.bias_in.poke(bias_in.U)

            dut.clock.step()

            dut.io.data_io.data_out.expect((weight_in * data_in + bias_in).U)
        }
    }
}