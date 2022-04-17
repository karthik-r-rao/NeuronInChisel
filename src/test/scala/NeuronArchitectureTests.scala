package controlUnitTests

import chisel3._
import chisel3.util.log2Up
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import NeuronArchitecture._ 

class NeuronArchitectureSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new NeuronArchitecture(8, 32, 16, 4, 32)) { dut => 
     }
  }
}
