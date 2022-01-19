package neurontests

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import scala.io.Source
import neuron._ 

class SigmoidSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new SigmoidLut(1024, 10)) { dut => 
            val lines = Source.fromFile("/home/karthikrrao/NeuronInChisel/generated/lut.txt").getLines().toArray
            for (i <- 0 until 1023){
                dut.io.sigmoid_lut_in.data.poke(i.U)
                dut.io.sigmoid_lut_in.valid.poke(true.B)
                dut.clock.step()
                dut.io.sigmoid_lut_out.data.expect(("b" + lines(i)).U)
            }
        }
    }
}