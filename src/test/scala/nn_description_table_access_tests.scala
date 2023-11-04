//Inputs
	
//val wrEna = Input(Bool())
//val dataIn = Input(Bits(datawidth.W))
//val Addr = Input(Bits(datawidth.W))

//Outputs
//val rdData = Output(Bits(datawidth.W))



package controlUnitTests

import chisel3._
import chisel3.util.log2Up
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import ControlUnit._ 


class NNDescriptionTableAccessSpec extends AnyFlatSpec with ChiselScalatestTester{
    "Tester" should "pass" in{
        test(new NNDescriptionTableAccess(8, 32, 16, 4, 32)) { dut => 

	    dut.io.Addr.poke(0.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.rdData.expect(10.U(16.W))
	    dut.io.Addr.poke(1.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.rdData.expect(15.U(16.W))
	    dut.io.Addr.poke(2.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.rdData.expect(10.U(16.W))
	    dut.io.Addr.poke(3.U(16.W))

	    dut.clock.step()
	    
	    dut.io.rdData.expect(8.U(16.W))
	    dut.io.Addr.poke(4.U(16.W))

	    dut.clock.step()
	    
	    dut.io.rdData.expect(6.U(16.W))
	    
	    dut.io.wrEna.poke(true.B)
	    dut.io.dataIn.poke(5.U(16.W))
	    dut.io.Addr.poke(5.U(16.W))
	    
	    dut.clock.step()

	    dut.io.wrEna.poke(false.B)
	    dut.io.Addr.poke(5.U(16.W))
	    
	    dut.clock.step()
	    
	    dut.io.rdData.expect(5.U(16.W))
	    
	
	}
    }
}
