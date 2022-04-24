package nntests

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import scala.util.Random
import scala.io.Source
import java.io._

import chisel3._
import chiseltest._
import chiseltest.simulator.WriteVcdAnnotation
import nn._ 

class NeuronSpec1 extends AnyFlatSpec with ChiselScalatestTester{

    // change parameters here
    val intWidth = 6
    val fracWidth = 12
    val sigmoidIntWidth = 3
    val sigmoidFracWidth = 7
    val tolerance = 0.05
    val file = "layers.txt"

    def findSigmoid(x: Double): Double = {
        return 1 / (1 + (scala.math.exp(-x)))
    }

    def checker(x: Double): Boolean = {
        if (x < tolerance)
            return true
        return false
    } 

    "neuronTester" should "pass" in{
        test(new Neuron(intWidth, fracWidth, sigmoidIntWidth, sigmoidFracWidth)).withAnnotations(Seq(WriteVcdAnnotation)) { dut => 

            val writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))

            // stats variables
            var error = 0.0

            // true and approx outputs
            var expected = 0.0
            var output = 0.0

                // data input
                var d1 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var d2 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var d3 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var d4 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)

                // weights

                // layer 1
                var w1_1_1 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w1_1_2 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w1_1_3 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w1_1_4 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                
                var w1_2_1 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w1_2_2 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w1_2_3 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w1_2_4 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                
                var w1_3_1 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w1_3_2 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w1_3_3 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w1_3_4 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)

                var w1_4_1 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w1_4_2 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w1_4_3 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w1_4_4 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)

                // layer 2
                var w2_1_1 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w2_1_2 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w2_1_3 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w2_1_4 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                
                var w2_2_1 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w2_2_2 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w2_2_3 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w2_2_4 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)

                var w2_3_1 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w2_3_2 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w2_3_3 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w2_3_4 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                
                // layer 3
                var w3_1_1 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w3_1_2 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var w3_1_3 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)

                // bias

                // layer 1
                var b1_1 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var b1_2 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var b1_3 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var b1_4 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)

                // layer 2
                var b2_1 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var b2_2 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var b2_3 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var b2_4 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)

                 // layer 3
                var b3_1 = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)

                var dut_weight_in = w1_1_1.S
                var dut_data_in = d1.S
                var dut_bias_in = b1_1.S

                dut.io.neuron_in.rst.poke(true.B)
                
                dut.clock.step()

                var acc = 0.0
                acc = acc + ((w1_1_1 * d1) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("======\n\n")
                writer.write("w1_1_1:")
                writer.write(w1_1_1.toBinaryString + "\n")
                writer.write("D1:")
                writer.write(d1.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w1_1_2.S
                dut_data_in = d2.S
                acc = acc + ((w1_1_2 * d2) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w1_1_2:")
                writer.write(w1_1_2.toBinaryString + "\n")
                writer.write("D2:")
                writer.write(d2.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w1_1_3.S
                dut_data_in = d3.S
                acc = acc + ((w1_1_3 * d3) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w1_1_3:")
                writer.write(w1_1_3.toBinaryString + "\n")
                writer.write("D3:")
                writer.write(d3.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w1_1_4.S
                dut_data_in = d4.S
                acc = acc + ((w1_1_4 * d4) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w1_1_4:")
                writer.write(w1_1_4.toBinaryString + "\n")
                writer.write("D4:")
                writer.write(d4.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                dut_bias_in = b1_1.S

                writer.write("B:")
                writer.write(b1_1.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(true.B)
                dut.io.neuron_in.op1.poke(1.S)
                dut.io.neuron_in.op2.poke(dut_bias_in)

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)

                dut.clock.step()

                acc = acc + (b1_1 / (scala.math.pow(2, fracWidth)))
                expected = findSigmoid(acc)
                output = (dut.io.neuron_out.peek().litValue.toDouble) / scala.math.pow(2, fracWidth) 
                error = scala.math.abs(expected - output)

                writer.write("Expected 1_1:")
                writer.write((output * scala.math.pow(2, fracWidth)).toInt.toBinaryString + "\n")
                var d1_1 = (output * scala.math.pow(2, fracWidth)).toInt

                if (checker(error))
                    println(s"[test] All fine.")
                else
                    println(s"[test] Error: ${error}")
                    
                dut.clock.step()
                writer.write("\n\n\n")

                // === Activation 1_1 Over ===

                dut.io.neuron_in.rst.poke(true.B)
                
                dut.clock.step()

                acc = 0.0
                acc = acc + ((w1_2_1 * d1) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("======\n\n")
                writer.write("w1_2_1:")
                writer.write(w1_2_1.toBinaryString + "\n")
                writer.write("D1:")
                writer.write(d1.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w1_2_2.S
                dut_data_in = d2.S
                acc = acc + ((w1_2_2 * d2) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w1_2_2:")
                writer.write(w1_2_2.toBinaryString + "\n")
                writer.write("D2:")
                writer.write(d2.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w1_2_3.S
                dut_data_in = d3.S
                acc = acc + ((w1_2_3 * d3) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w1_2_3:")
                writer.write(w1_2_3.toBinaryString + "\n")
                writer.write("D3:")
                writer.write(d3.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w1_2_4.S
                dut_data_in = d4.S
                acc = acc + ((w1_2_4 * d4) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w1_2_4:")
                writer.write(w1_2_4.toBinaryString + "\n")
                writer.write("D4:")
                writer.write(d4.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                dut_bias_in = b1_2.S

                writer.write("B:")
                writer.write(b1_2.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(true.B)
                dut.io.neuron_in.op1.poke(1.S)
                dut.io.neuron_in.op2.poke(dut_bias_in)

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)

                dut.clock.step()

                acc = acc + (b1_2 / (scala.math.pow(2, fracWidth)))
                expected = findSigmoid(acc)
                output = (dut.io.neuron_out.peek().litValue.toDouble) / scala.math.pow(2, fracWidth) 
                error = scala.math.abs(expected - output)

                writer.write("Expected 1_2:")
                writer.write((output * scala.math.pow(2, fracWidth)).toInt.toBinaryString + "\n")
                var d1_2 = (output * scala.math.pow(2, fracWidth)).toInt

                if (checker(error))
                    println(s"[test] All fine.")
                else
                    println(s"[test] Error: ${error}")
                    
                dut.clock.step()
                writer.write("\n\n\n")

                // === Activation 1_2 Over ===

                dut.io.neuron_in.rst.poke(true.B)
                
                dut.clock.step()

                acc = 0.0
                acc = acc + ((w1_3_1 * d1) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("======\n\n")
                writer.write("w1_3_1:")
                writer.write(w1_3_1.toBinaryString + "\n")
                writer.write("D1:")
                writer.write(d1.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w1_3_2.S
                dut_data_in = d2.S
                acc = acc + ((w1_3_2 * d2) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w1_3_2:")
                writer.write(w1_3_2.toBinaryString + "\n")
                writer.write("D2:")
                writer.write(d2.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w1_3_3.S
                dut_data_in = d3.S
                acc = acc + ((w1_3_3 * d3) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w1_3_3:")
                writer.write(w1_3_3.toBinaryString + "\n")
                writer.write("D3:")
                writer.write(d3.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w1_3_4.S
                dut_data_in = d4.S
                acc = acc + ((w1_3_4 * d4) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w1_3_4:")
                writer.write(w1_3_4.toBinaryString + "\n")
                writer.write("D4:")
                writer.write(d4.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                dut_bias_in = b1_3.S

                writer.write("B:")
                writer.write(b1_3.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(true.B)
                dut.io.neuron_in.op1.poke(1.S)
                dut.io.neuron_in.op2.poke(dut_bias_in)

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)

                dut.clock.step()

                acc = acc + (b1_3 / (scala.math.pow(2, fracWidth)))
                expected = findSigmoid(acc)
                output = (dut.io.neuron_out.peek().litValue.toDouble) / scala.math.pow(2, fracWidth) 
                error = scala.math.abs(expected - output)

                writer.write("Expected 1_3:")
                writer.write((output * scala.math.pow(2, fracWidth)).toInt.toBinaryString + "\n")
                var d1_3 = (output * scala.math.pow(2, fracWidth)).toInt

                if (checker(error))
                    println(s"[test] All fine.")
                else
                    println(s"[test] Error: ${error}")
                    
                dut.clock.step()
                writer.write("\n\n\n")

                // === Activation 1_3 Over ===

                dut.io.neuron_in.rst.poke(true.B)
                
                dut.clock.step()

                acc = 0.0
                acc = acc + ((w1_4_1 * d1) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("======\n\n")
                writer.write("w1_4_1:")
                writer.write(w1_4_1.toBinaryString + "\n")
                writer.write("D1:")
                writer.write(d1.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w1_4_2.S
                dut_data_in = d2.S
                acc = acc + ((w1_4_2 * d2) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w1_4_2:")
                writer.write(w1_4_2.toBinaryString + "\n")
                writer.write("D2:")
                writer.write(d2.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w1_4_3.S
                dut_data_in = d3.S
                acc = acc + ((w1_4_3 * d3) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w1_4_3:")
                writer.write(w1_4_3.toBinaryString + "\n")
                writer.write("D3:")
                writer.write(d3.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w1_4_4.S
                dut_data_in = d4.S
                acc = acc + ((w1_4_4 * d4) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w1_4_4:")
                writer.write(w1_4_4.toBinaryString + "\n")
                writer.write("D4:")
                writer.write(d4.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                dut_bias_in = b1_4.S

                writer.write("B:")
                writer.write(b1_4.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(true.B)
                dut.io.neuron_in.op1.poke(1.S)
                dut.io.neuron_in.op2.poke(dut_bias_in)

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)

                dut.clock.step()

                acc = acc + (b1_4 / (scala.math.pow(2, fracWidth)))
                expected = findSigmoid(acc)
                output = (dut.io.neuron_out.peek().litValue.toDouble) / scala.math.pow(2, fracWidth) 
                error = scala.math.abs(expected - output)

                writer.write("Expected 1_4:")
                writer.write((output * scala.math.pow(2, fracWidth)).toInt.toBinaryString + "\n")
                var d1_4 = (output * scala.math.pow(2, fracWidth)).toInt

                if (checker(error))
                    println(s"[test] All fine.")
                else
                    println(s"[test] Error: ${error}")
                    
                dut.clock.step()
                writer.write("\n\n\n")

                // === Activation 1_4 Over ===


                // **********
                // Layer 1 done
                // **********

                dut.io.neuron_in.rst.poke(true.B)
                
                dut.clock.step()

                acc = 0.0
                acc = acc + ((w2_1_1 * d1_1) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("======\n\n")
                writer.write("w2_1_1:")
                writer.write(w2_1_1.toBinaryString + "\n")
                writer.write("D1_1:")
                writer.write(d1_1.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w2_1_2.S
                dut_data_in = d1_2.S
                acc = acc + ((w2_1_2 * d1_2) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w2_1_2:")
                writer.write(w2_1_2.toBinaryString + "\n")
                writer.write("D1_2:")
                writer.write(d1_2.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w2_1_3.S
                dut_data_in = d1_3.S
                acc = acc + ((w2_1_3 * d1_3) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w2_1_3:")
                writer.write(w2_1_3.toBinaryString + "\n")
                writer.write("D1_3:")
                writer.write(d1_3.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w2_1_4.S
                dut_data_in = d1_4.S
                acc = acc + ((w2_1_4 * d1_4) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w2_1_4:")
                writer.write(w2_1_4.toBinaryString + "\n")
                writer.write("D1_4:")
                writer.write(d1_4.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                dut_bias_in = b2_1.S

                writer.write("B:")
                writer.write(b2_1.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(true.B)
                dut.io.neuron_in.op1.poke(1.S)
                dut.io.neuron_in.op2.poke(dut_bias_in)

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)

                dut.clock.step()

                acc = acc + (b2_1 / (scala.math.pow(2, fracWidth)))
                expected = findSigmoid(acc)
                output = (dut.io.neuron_out.peek().litValue.toDouble) / scala.math.pow(2, fracWidth) 
                error = scala.math.abs(expected - output)

                writer.write("Expected 2_1:")
                writer.write((output * scala.math.pow(2, fracWidth)).toInt.toBinaryString + "\n")
                var d2_1 = (output * scala.math.pow(2, fracWidth)).toInt

                if (checker(error))
                    println(s"[test] All fine.")
                else
                    println(s"[test] Error: ${error}")
                    
                dut.clock.step()
                writer.write("\n\n\n")

                // === Activation 2_1 Over ===

                dut.io.neuron_in.rst.poke(true.B)
                
                dut.clock.step()

                acc = 0.0
                acc = acc + ((w2_2_1 * d1_1) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("======\n\n")
                writer.write("w2_2_1:")
                writer.write(w2_2_1.toBinaryString + "\n")
                writer.write("D1_1:")
                writer.write(d1_1.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w2_2_2.S
                dut_data_in = d1_2.S
                acc = acc + ((w2_2_2 * d1_2) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w2_2_2:")
                writer.write(w2_2_2.toBinaryString + "\n")
                writer.write("D1_2:")
                writer.write(d1_2.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w2_2_3.S
                dut_data_in = d1_3.S
                acc = acc + ((w2_2_3 * d1_3) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w2_2_3:")
                writer.write(w2_2_3.toBinaryString + "\n")
                writer.write("D1_3:")
                writer.write(d1_3.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w2_2_4.S
                dut_data_in = d1_4.S
                acc = acc + ((w2_2_4 * d1_4) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w2_2_4:")
                writer.write(w2_2_4.toBinaryString + "\n")
                writer.write("D1_4:")
                writer.write(d1_4.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                dut_bias_in = b2_2.S

                writer.write("B:")
                writer.write(b2_2.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(true.B)
                dut.io.neuron_in.op1.poke(1.S)
                dut.io.neuron_in.op2.poke(dut_bias_in)

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)

                dut.clock.step()

                acc = acc + (b2_2 / (scala.math.pow(2, fracWidth)))
                expected = findSigmoid(acc)
                output = (dut.io.neuron_out.peek().litValue.toDouble) / scala.math.pow(2, fracWidth) 
                error = scala.math.abs(expected - output)

                writer.write("Expected 2_2:")
                writer.write((output * scala.math.pow(2, fracWidth)).toInt.toBinaryString + "\n")
                var d2_2 = (output * scala.math.pow(2, fracWidth)).toInt

                if (checker(error))
                    println(s"[test] All fine.")
                else
                    println(s"[test] Error: ${error}")
                    
                dut.clock.step()
                writer.write("\n\n\n")

                // === Activation 2_2 Over ===

                dut.io.neuron_in.rst.poke(true.B)
                
                dut.clock.step()

                acc = 0.0
                acc = acc + ((w2_3_1 * d1_1) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("======\n\n")
                writer.write("w2_3_1:")
                writer.write(w2_3_1.toBinaryString + "\n")
                writer.write("D1_1:")
                writer.write(d1_1.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w2_3_2.S
                dut_data_in = d1_2.S
                acc = acc + ((w2_3_2 * d1_2) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w2_3_2:")
                writer.write(w2_3_2.toBinaryString + "\n")
                writer.write("D1_2:")
                writer.write(d1_2.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w2_3_3.S
                dut_data_in = d1_3.S
                acc = acc + ((w2_3_3 * d1_3) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w2_3_3:")
                writer.write(w2_3_3.toBinaryString + "\n")
                writer.write("D1_3:")
                writer.write(d1_3.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w2_3_4.S
                dut_data_in = d1_4.S
                acc = acc + ((w2_3_4 * d1_4) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w2_3_4:")
                writer.write(w2_3_4.toBinaryString + "\n")
                writer.write("D1_4:")
                writer.write(d1_4.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                dut_bias_in = b2_3.S

                writer.write("B:")
                writer.write(b2_3.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(true.B)
                dut.io.neuron_in.op1.poke(1.S)
                dut.io.neuron_in.op2.poke(dut_bias_in)

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)

                dut.clock.step()

                acc = acc + (b2_3 / (scala.math.pow(2, fracWidth)))
                expected = findSigmoid(acc)
                output = (dut.io.neuron_out.peek().litValue.toDouble) / scala.math.pow(2, fracWidth) 
                error = scala.math.abs(expected - output)

                writer.write("Expected 2_3:")
                writer.write((output * scala.math.pow(2, fracWidth)).toInt.toBinaryString + "\n")
                var d2_3 = (output * scala.math.pow(2, fracWidth)).toInt

                if (checker(error))
                    println(s"[test] All fine.")
                else
                    println(s"[test] Error: ${error}")
                    
                dut.clock.step()
                writer.write("\n\n\n")

                // === Activation 2_3 Over ===

                // **********
                // Layer 2 done
                // **********

                dut.io.neuron_in.rst.poke(true.B)
                
                dut.clock.step()

                acc = 0.0
                acc = acc + ((w3_1_1 * d2_1) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("======\n\n")
                writer.write("w3_1_1:")
                writer.write(w3_1_1.toBinaryString + "\n")
                writer.write("D2_1:")
                writer.write(d2_1.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                dut_weight_in = w3_1_2.S
                dut_data_in = d2_2.S
                acc = acc + ((w3_1_2 * d2_2) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w3_1_2:")
                writer.write(w3_1_2.toBinaryString + "\n")
                writer.write("D2_2:")
                writer.write(d2_2.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                
                dut_weight_in = w3_1_3.S
                dut_data_in = d2_3.S
                acc = acc + ((w3_1_3 * d2_3) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("w3_1_3:")
                writer.write(w3_1_3.toBinaryString + "\n")
                writer.write("D2_3:")
                writer.write(d2_3.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                dut_bias_in = b3_1.S

                writer.write("B:")
                writer.write(b3_1.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(true.B)
                dut.io.neuron_in.op1.poke(1.S)
                dut.io.neuron_in.op2.poke(dut_bias_in)

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)

                dut.clock.step()

                acc = acc + (b3_1 / (scala.math.pow(2, fracWidth)))
                expected = findSigmoid(acc)
                output = (dut.io.neuron_out.peek().litValue.toDouble) / scala.math.pow(2, fracWidth) 
                error = scala.math.abs(expected - output)

                writer.write("Expected 3_1:")
                writer.write((output * scala.math.pow(2, fracWidth)).toInt.toBinaryString + "\n")
                var d3_1 = (output * scala.math.pow(2, fracWidth)).toInt

                if (checker(error))
                    println(s"[test] All fine.")
                else
                    println(s"[test] Error: ${error}")
                    
                dut.clock.step()
                writer.write("\n\n\n")

                // === Activation 3_1 Over ===
    
            writer.close()
        }
    }
}