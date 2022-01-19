package neurontests

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import chiseltest.simulator.WriteVcdAnnotation
import neuron._ 

class NeuronSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new Neuron(1024, 10)).withAnnotations(Seq(WriteVcdAnnotation)) { dut => 
            dut.io.neuron_in.reset.poke(true.B)
            dut.io.neuron_in.weight_valid.poke(false.B)
            dut.io.neuron_in.data_valid.poke(false.B)
            dut.io.neuron_in.bias_valid.poke(false.B)
            
            dut.clock.step()

            val weight_in = 9
            val data_in = 111
            dut.io.neuron_in.weight_valid.poke(true.B)
            dut.io.neuron_in.data_valid.poke(true.B)
            dut.io.neuron_in.weight.poke(weight_in.U)
            dut.io.neuron_in.data.poke(data_in.U)

            dut.clock.step()

            val bias_in = 1
            dut.io.neuron_in.bias_valid.poke(true.B)
            dut.io.neuron_in.weight_valid.poke(false.B)
            dut.io.neuron_in.data_valid.poke(false.B)
            dut.io.neuron_in.bias.poke(bias_in.U)

            dut.clock.step()

            dut.io.neuron_in.bias_valid.poke(false.B)

            dut.clock.step()

            dut.io.neuron_out.data.expect(("b" + "0000000011").U)
        }
    }
}