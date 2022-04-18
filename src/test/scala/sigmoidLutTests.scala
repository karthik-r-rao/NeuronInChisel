package nntests

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import scala.io.Source

import chisel3._
import chiseltest._
import chiseltest.simulator.WriteVcdAnnotation
import nn._ 

class SigmoidLutSpec extends AnyFlatSpec with ChiselScalatestTester{

    // change parameters here
    val intWidth = 3
    val fracWidth = 7

    "sigmoidLutTester" should "pass" in{
        test(new SigmoidLut(intWidth, fracWidth)).withAnnotations(Seq(WriteVcdAnnotation)) { dut => 

            // stats variables
            var num_passed = 0

            val lut_values = Source.fromResource("sigmoidlut.txt").getLines().toList
            println(s"[test] Length of file: ${lut_values.length}")

            // for( w <- 0 until lut_values.length){
            //     var input = lut_values(w)
            //     var dut_in = ("b" + input).U

            //     dut.io.write.poke(true.B)
            //     dut.io.addr.poke(w.U)
            //     dut.io.dataIn.poke(dut_in)
            //     dut.clock.step()
            // }
            // println("[test] Memory initialized")

            // read back from LUT
            for (w <- 0 until lut_values.length){
                // dut.io.write.poke(false.B)
                dut.io.addr.poke(w.U)
                dut.clock.step()
                var output = dut.io.dataOut.peek().litValue.toInt
                var expected = Integer.parseInt(lut_values(w), 2)
                
                if (output == expected)
                    num_passed = num_passed + 1
                else
                    println(s"[test] Exp: ${expected} Out: ${output}")

                dut.clock.step()
            }

            println("[test] Test completed")
            println("[test] === Test statistics ===")
            println(s"[test] Number of test vectors: ${lut_values.length}")
            println(s"[test] Number of test vectors passed: ${num_passed}")
            println(s"[test] Test success rate: ${num_passed / (1.0 * lut_values.length)}")
        }
    }
}