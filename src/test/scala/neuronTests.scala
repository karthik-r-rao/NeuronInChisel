package neurontests

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import chiseltest.simulator.WriteVcdAnnotation
import scala.util.Random
import scala.io.Source
import neuron._ 

class NeuronSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new Neuron(1024, 10)).withAnnotations(Seq(WriteVcdAnnotation)) { dut => 
            val lines = Source.fromResource("lut.txt").getLines().toArray
            for (i <- 0 until 1023){
                dut.io.neuron_in.reset.poke(true.B)
                dut.io.neuron_in.weight_valid.poke(false.B)
                dut.io.neuron_in.data_valid.poke(false.B)
                dut.io.neuron_in.bias_valid.poke(false.B)
                
                dut.clock.step()

                var acc = 0
                var weight_in = Random.between(0, 16)
                var data_in = Random.between(0, 16)
                acc = acc + weight_in * data_in
                dut.io.neuron_in.weight_valid.poke(true.B)
                dut.io.neuron_in.data_valid.poke(true.B)
                dut.io.neuron_in.weight.poke(weight_in.U)
                dut.io.neuron_in.data.poke(data_in.U)

                dut.clock.step()

                weight_in = Random.between(0, 16)
                data_in = Random.between(0, 16)
                acc = acc + weight_in * data_in
                dut.io.neuron_in.weight_valid.poke(true.B)
                dut.io.neuron_in.data_valid.poke(true.B)
                dut.io.neuron_in.weight.poke(weight_in.U)
                dut.io.neuron_in.data.poke(data_in.U)

                dut.clock.step()

                var bias_in = Random.between(0, 16)
                acc = acc + bias_in
                dut.io.neuron_in.bias_valid.poke(true.B)
                dut.io.neuron_in.weight_valid.poke(false.B)
                dut.io.neuron_in.data_valid.poke(false.B)
                dut.io.neuron_in.bias.poke(bias_in.U)

                dut.clock.step()

                dut.io.neuron_in.bias_valid.poke(false.B)

                dut.clock.step()

                dut.io.neuron_out.data.expect(("b" + lines(acc)).U)
            }
        }
    }
}