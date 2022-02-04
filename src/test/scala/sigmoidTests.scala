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
    val num_test_vec = 10000
      
    def findSigmoid(x: Double): Double = {
        return 1 / (1 + (scala.math.exp(-x)))
    }

    def checker(x: Double): Boolean = {
        if (x < 0.05)
            return true
        return false
    } 

    "SigmoidTester" should "pass" in{
        test(new SigmoidCLA(intWidth, fracWidth)).withAnnotations(Seq(WriteVcdAnnotation)) { dut => 

            // stats variables
            var num_passed = 0
            var error = 0.0
            var max_error = 0.0

            // true and approx. outputs
            var expected = 0.0
            var output = 0.0

            for( w <- 0 until num_test_vec){

                var input = Random.between(0, scala.math.pow(2, fracWidth + 3).toInt) // sigmoid range taken as [-5, 5]
                var input_scaled = input / scala.math.pow(2, fracWidth)

                // positive numbers
                dut.io.sigmoid_cla_in.valid.poke(true.B)
                dut.io.sigmoid_cla_in.data.poke(input.toInt.U)
                dut.clock.step(2)
                output = (dut.io.sigmoid_cla_out.data.peek().litValue.toDouble) / scala.math.pow(2, fracWidth)
                expected = findSigmoid(input_scaled)
                error = scala.math.abs(output - expected)   // absolute error
                
                if (checker(error)){
                    num_passed = num_passed + 1
                }
                else{
                    println(s"[test] In: ${input_scaled} Exp: ${expected} Out: ${output} Err: ${error}")
                }  

                if (error > max_error){
                    max_error = error
                }

                dut.clock.step(2)

                // construct mask "1111...1111"
                val minus_one = (-scala.math.pow(2, intWidth + fracWidth - 1).toInt) | ((scala.math.pow(2, intWidth + fracWidth - 1) - 1).toInt)
                // negative numbers
                var input_2s = (~input + 1) & (minus_one)
                dut.io.sigmoid_cla_in.valid.poke(true.B)
                dut.io.sigmoid_cla_in.data.poke(("h" + input_2s.toHexString).U)
                dut.clock.step(2)
                output = (dut.io.sigmoid_cla_out.data.peek().litValue.toDouble) / scala.math.pow(2, fracWidth)
                expected = findSigmoid(-input_scaled)
                error = scala.math.abs(output - expected)   // absolute error
                
                if (checker(error)){
                    num_passed = num_passed + 1
                }
                else{
                    println(s"[test] In: ${-input_scaled} Exp: ${expected} Out: ${output} Err: ${error}")
                }
                
                if (error > max_error){
                    max_error = error
                }
                
                dut.clock.step(2)

                dut.io.sigmoid_cla_in.valid.poke(false.B)
                dut.clock.step()
            }

            println("[test] Test completed")
            println("[test] === Test statistics ===")
            println(s"[test] Number of test vectors: ${2 * num_test_vec}")
            println(s"[test] Number of test vectors passed: ${num_passed}")
            println(s"[test] Test success rate: ${num_passed / (2.0 * num_test_vec)}")
            println(s"[test] Maximum Error: ${max_error}")
            
        }
    }
}