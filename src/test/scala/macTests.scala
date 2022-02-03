package nntests

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import chiseltest.simulator.WriteVcdAnnotation
import nn._ 

class MultiplyAccumulateSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new MultiplyAccumulate(10)).withAnnotations(Seq(WriteVcdAnnotation)) { dut => 
            dut.io.mac_in.rst.poke(true.B)
            dut.io.mac_in.weight.valid.poke(false.B)
            dut.io.mac_in.x.valid.poke(false.B)
            dut.io.mac_in.bias.valid.poke(false.B)
            
            dut.clock.step()

            val weight_in = 15
            val data_in = 27
            dut.io.mac_in.weight.valid.poke(true.B)
            dut.io.mac_in.x.valid.poke(true.B)
            dut.io.mac_in.weight.data.poke(weight_in.U)
            dut.io.mac_in.x.data.poke(data_in.U)

            dut.clock.step()

            val bias_in = 21
            dut.io.mac_in.bias.valid.poke(true.B)
            dut.io.mac_in.weight.valid.poke(false.B)
            dut.io.mac_in.x.valid.poke(false.B)
            dut.io.mac_in.bias.data.poke(bias_in.U)

            dut.clock.step()

            dut.io.mac_out.y.data.expect((weight_in * data_in + bias_in).U)

            dut.clock.step()
        }
    }
}