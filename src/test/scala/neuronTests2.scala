package nntests

import org.scalatest._


import org.scalatest.flatspec.AnyFlatSpec
import scala.util.Random
import scala.io.Source

import chisel3._
import chiseltest._
import chiseltest.simulator.WriteVcdAnnotation
import nn._ 

class NeuronSpec2 extends AnyFlatSpec with ChiselScalatestTester{

    // change parameters here
    val intWidth = 6
    val fracWidth = 12
    val sigmoidIntWidth = 3
    val sigmoidFracWidth = 7

    "neuronTester" should "pass" in{
        test(new Neuron(intWidth, fracWidth, sigmoidIntWidth, sigmoidFracWidth)).withAnnotations(Seq(WriteVcdAnnotation)) { dut => 

                dut.io.neuron_in.rst.poke(true.B)
                dut.io.neuron_in.bias.poke(false.B)

		dut.clock.step()

                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(895.S(19.W))
                dut.io.neuron_in.op2.poke(261302.S(19.W))
		
		dut.clock.step()
		
                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(260953.S(19.W))
                dut.io.neuron_in.op2.poke(260597.S(19.W))
		
		
		dut.clock.step()

                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(261750.S(19.W))
                dut.io.neuron_in.op2.poke(261162.S(19.W))

		dut.clock.step()

                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(261656.S(19.W))
                dut.io.neuron_in.op2.poke(261094.S(19.W))


		dut.clock.step()

                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(true.B)
                dut.io.neuron_in.op1.poke(261656.S(19.W))
                dut.io.neuron_in.op2.poke(654.S(19.W))

		dut.io.neuron_out.expect("b000100000100000".U(18.W))

		dut.clock.step()

                dut.io.neuron_in.rst.poke(true.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke((0.S(19.W)).asSInt)
                dut.io.neuron_in.op2.poke((0.S(19.W)).asSInt)

		dut.io.neuron_out.expect("b000100000100000".U(18.W))


		dut.clock.step()

		dut.io.neuron_out.expect("b100100100000".U(18.W))


       }
    }
}
