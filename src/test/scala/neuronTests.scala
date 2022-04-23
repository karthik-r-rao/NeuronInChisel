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

class NeuronSpec extends AnyFlatSpec with ChiselScalatestTester{

    // change parameters here
    val intWidth = 6
    val fracWidth = 12
    val sigmoidIntWidth = 3
    val sigmoidFracWidth = 7
    val num_test_vec = 100
    val tolerance = 0.05
    val file = "values.txt"

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
            val lut_values = Source.fromResource("sigmoidlut.txt").getLines().toList
            println(s"[test] Length of file: ${lut_values.length}")

            // for( w <- 0 until lut_values.length){
            //     var input = lut_values(w)
            //     var dut_in = ("b" + input).U

            //     dut.io.write.poke(true.B)
            //     dut.io.w_addr.poke(w.U)
            //     dut.io.w_dataIn.poke(dut_in)
            //     dut.clock.step()
            // }
            // println("[test] Memory initialized")

            // stats variables
            var num_passed = 0
            var error = 0.0

            // true and approx outputs
            var expected = 0.0
            var output = 0.0

            for (w <- 0 until num_test_vec){
                // dut.io.write.poke(false.B)
                dut.io.neuron_in.rst.poke(true.B)
                
                dut.clock.step()

                var weight_in = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var data_in = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                var bias_in = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                
                var dut_weight_in = weight_in.S
                var dut_data_in = data_in.S
                var dut_bias_in = bias_in.S

                var acc = 0.0

                writer.write("======\n\n")
                writer.write("W:")
                writer.write(weight_in.toBinaryString + "\n")
                writer.write("D:")
                writer.write(data_in.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.rst.poke(false.B)
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                weight_in = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                data_in = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                dut_weight_in = weight_in.S
                dut_data_in = data_in.S
                acc = acc + ((weight_in * data_in) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("W:")
                writer.write(weight_in.toBinaryString + "\n")
                writer.write("D:")
                writer.write(data_in.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                weight_in = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                data_in = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                dut_weight_in = weight_in.S
                dut_data_in = data_in.S
                acc = acc + ((weight_in * data_in) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("W:")
                writer.write(weight_in.toBinaryString + "\n")
                writer.write("D:")
                writer.write(data_in.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                weight_in = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                data_in = Random.between(-scala.math.pow(2, fracWidth-1).toInt, scala.math.pow(2, fracWidth-1).toInt)
                dut_weight_in = weight_in.S
                dut_data_in = data_in.S
                acc = acc + ((weight_in * data_in) / (scala.math.pow(2, 2*fracWidth)))

                writer.write("W:")
                writer.write(weight_in.toBinaryString + "\n")
                writer.write("D:")
                writer.write(data_in.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)
                dut.io.neuron_in.op1.poke(dut_data_in)
                dut.io.neuron_in.op2.poke(dut_weight_in)

                writer.write("B:")
                writer.write(bias_in.toBinaryString + "\n")

                dut.clock.step()
                dut.io.neuron_in.bias.poke(true.B)
                dut.io.neuron_in.op1.poke(1.S)
                dut.io.neuron_in.op2.poke(dut_bias_in)

                dut.clock.step()
                dut.io.neuron_in.bias.poke(false.B)

                dut.clock.step()

                acc = acc + (bias_in / (scala.math.pow(2, fracWidth)))
                expected = findSigmoid(acc)
                output = (dut.io.neuron_out.peek().litValue.toDouble) / scala.math.pow(2, fracWidth) 
                var error = scala.math.abs(expected - output)

                writer.write("Expected:")
                writer.write((output * scala.math.pow(2, fracWidth)).toInt.toBinaryString + "\n")

                if (checker(error))
                    num_passed = num_passed + 1
                else
                    println(s"[test] Error: ${error}")
                    //println(s"[test] W: ${weight_in/(scala.math.pow(2, fracWidth))} X: ${data_in/(scala.math.pow(2, fracWidth))} B: ${bias_in/(scala.math.pow(2, fracWidth))} Mac: ${mac_out} Exp: ${expected} Out: ${output} Err: ${error}")
                
                dut.clock.step()
            }
            println("[test] Test completed")
            println("[test] === Test statistics ===")
            println(s"[test] Number of test vectors: ${num_test_vec}")
            println(s"[test] Number of test vectors passed: ${num_passed}")
            println(s"[test] Test success rate: ${num_passed / (1.0 * num_test_vec)}")
            writer.close()
        }
    }
}