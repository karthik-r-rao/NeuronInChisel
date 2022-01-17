package sigmoidtests

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import scala.io.Source
import sigmoid._ 

class SigmoidSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new SigmoidLut(1024, 10)) { dut => 
            val lines = Source.fromFile("/home/karthikrrao/NeuronInChisel/generated/lut.txt").getLines().toArray
            for (i <- 0 until 1023){
                dut.io.address.poke(i.U)
                dut.clock.step()
                dut.io.value.expect(("b" + lines(i)).U)
            }
        }
    }
}