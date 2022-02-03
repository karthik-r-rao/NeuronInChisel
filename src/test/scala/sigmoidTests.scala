package nntests

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import scala.util.Random

import chisel3._
import chiseltest._
import chiseltest.simulator.WriteVcdAnnotation
import nn._ 

class SigmoidCLASpec extends AnyFlatSpec with ChiselScalatestTester{
      
    def findSigmoid(x: Double) : Double = {
        return 1 / (1 + (scala.math.exp(-x)))
    }

    "SigmoidTester" should "pass" in{
        test(new SigmoidCLA(16, 16)).withAnnotations(Seq(WriteVcdAnnotation)) { dut => 

            for( w <- 0 to 10){
                var expected = 0.0
                var output = 0.0
                var error = 0.0

                var input = Random.between(0, scala.math.pow(2, 19).toInt)
                var input_scaled = input / scala.math.pow(2, 16)

                // positive numbers
                dut.io.sigmoid_cla_in.valid.poke(true.B)
                dut.io.sigmoid_cla_in.data.poke(input.toInt.U)
                output = (dut.io.sigmoid_cla_out.data.peek().litValue.toDouble) / scala.math.pow(2, 16)
                expected = findSigmoid(input_scaled)
                error = scala.math.abs(expected - output)
                println(s"[test] In: ${input} ScaledIn: ${input_scaled} Exp: ${expected} Out: ${output} Err: ${error}")

                dut.clock.step(2)

                // negative numbers
                var input_2s = (~input + 1) & (0xffffffff)
                dut.io.sigmoid_cla_in.valid.poke(true.B)
                dut.io.sigmoid_cla_in.data.poke(("h" + input_2s.toHexString).U)
                output = (dut.io.sigmoid_cla_out.data.peek().litValue.toDouble) / scala.math.pow(2, 16)
                expected = findSigmoid(-input_scaled)
                error = scala.math.abs(expected - output)
                println(s"[test] In: ${input_2s} ScaledIn: ${-input_scaled} Exp: ${expected} Out: ${output} Err: ${error}")

                dut.clock.step(2)
            }
            
        }
    }
}