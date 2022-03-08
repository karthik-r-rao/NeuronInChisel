package nntests

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import scala.util.Random

import chisel3._
import chiseltest._
import chiseltest.simulator.WriteVcdAnnotation
import nn._ 

class SigmoidCLASpec extends AnyFlatSpec with ChiselScalatestTester{

    // change parameters here
    val intWidth = 6
    val fracWidth = 12
    val num_test_vec = 100
    val tolerance = 0.05
      
    def findSigmoid(x: Double): Double = {
        return 1 / (1 + (scala.math.exp(-x)))
    }

    def checker(x: Double): Boolean = {
        if (x < tolerance)
            return true
        return false
    } 

    "sigmoidTester" should "pass" in{
        test(new SigmoidCLA(intWidth, fracWidth)).withAnnotations(Seq(WriteVcdAnnotation)) { dut => 

            // stats variables
            var num_passed = 0
            var error = 0.0
            var pos = 0
            var neg = 0

            // true and approx. outputs
            var expected = 0.0
            var output = 0.0

            for( w <- 0 until num_test_vec){

                var input = Random.between(-scala.math.pow(2, fracWidth + 2).toInt, scala.math.pow(2, fracWidth + 2).toInt) 
                var input_scaled = input / scala.math.pow(2, fracWidth)
                var dut_in = input.S

                if (input >= 0)
                    pos = pos + 1
                else 
                    neg = neg + 1

                dut.io.sigmoid_cla_in.valid.poke(true.B)
                dut.io.sigmoid_cla_in.data.poke(dut_in)
                dut.clock.step(2)
                output = (dut.io.sigmoid_cla_out.data.peek().litValue.toDouble) / scala.math.pow(2, fracWidth)
                expected = findSigmoid(input_scaled)
                error = scala.math.abs(output - expected)   // absolute error
                
                if (checker(error))
                    num_passed = num_passed + 1
                else
                    println(s"[test] In: ${input_scaled} Exp: ${expected} Out: ${output} Err: ${error}")

                dut.clock.step(2)

                dut.io.sigmoid_cla_in.valid.poke(false.B)
                dut.clock.step()
            }

            println("[test] Test completed")
            println("[test] === Test statistics ===")
            println(s"[test] Number of test vectors: ${num_test_vec}")
            println(s"[test] Number of test vectors passed: ${num_passed}")
            println(s"[test] Number of positive test vectors: ${pos}")
            println(s"[test] Number of negative test vectors: ${neg}")
            println(s"[test] Test success rate: ${num_passed * 1.0 / (num_test_vec)}")
        }
    }
}