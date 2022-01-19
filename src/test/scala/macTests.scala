package neurontests

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import neuron._ 

class MultiplyAccumulateSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new MultiplyAccumulate(10)) { dut => 
            dut.io.mac_in.reset.poke(true.B)
            dut.io.mac_in.weight_valid.poke(false.B)
            dut.io.mac_in.data_valid.poke(false.B)
            dut.io.mac_in.bias_valid.poke(false.B)
            
            dut.clock.step()

            val weight_in = 15
            val data_in = 27
            dut.io.mac_in.weight_valid.poke(true.B)
            dut.io.mac_in.data_valid.poke(true.B)
            dut.io.mac_in.weight.poke(weight_in.U)
            dut.io.mac_in.data.poke(data_in.U)

            dut.clock.step()

            val bias_in = 21
            dut.io.mac_in.bias_valid.poke(true.B)
            dut.io.mac_in.weight_valid.poke(false.B)
            dut.io.mac_in.data_valid.poke(false.B)
            dut.io.mac_in.bias.poke(bias_in.U)

            dut.clock.step()

            dut.io.mac_out.data.expect((weight_in * data_in + bias_in).U)
        }
    }
}