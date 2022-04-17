package ControlUnit

import chisel3._
import chisel3.util.log2Up

class AddressGeneratorInterfaceIn(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Bundle{
    // data signals

    val memoryUnits = Input(Vec(numberOfPE, Bits(2.W)))
    val max_iteration = Input(Bits(datawidth.W)) 
        
    // control signals
    val address_valid = Input(Bool())
    val enable_valid = Input(Bool())
    val reset = Input(Bool())
}

class AddressGeneratorInterfaceOut(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Bundle{
    val Address = Output(Bits(datawidth.W))
    
    val read_enables = Output(Vec(numberOfPE, Bits(2.W)))
    
    val bias_valid = Output(Bool())

    val neuron_reset = Output(Bool())    
    
    val address_generation_complete = Output(Bool())

}

class AddressGenerator(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Module{
    val io = IO(new Bundle{
        val address_generator_in = new AddressGeneratorInterfaceIn(memoryDepth, memoryHeight, datawidth, numberOfPE)
        val address_generator_out = new AddressGeneratorInterfaceOut(memoryDepth, memoryHeight, datawidth, numberOfPE)
    })


    val enables = Wire(Vec(numberOfPE, UInt(2.W)))
        
    val Address = RegInit(0.U(datawidth.W))

    val address_generation_complete = RegInit(false.B)
    
    io.address_generator_out.neuron_reset := true.B
    io.address_generator_out.bias_valid := false.B
    
    when(io.address_generator_in.enable_valid){

	for(j <- 0 until numberOfPE){
	    enables(j) := io.address_generator_in.memoryUnits(j)
	}

    } .otherwise{

	for(j <- 0 until numberOfPE){
	    enables(j) := 0.U(2.W)	
	}


    }
    
    when(io.address_generator_in.address_valid){
    	when(io.address_generator_in.reset){
    	    io.address_generator_out.neuron_reset := true.B
    	    io.address_generator_out.bias_valid := false.B
    	    
    	    Address := 0.U(datawidth.W)
    	    address_generation_complete := false.B
    	    
    	} .elsewhen(Address < io.address_generator_in.max_iteration-1.U){
    	    io.address_generator_out.neuron_reset := false.B
    	    io.address_generator_out.bias_valid := false.B

    	    Address := Address + 1.U(memoryDepth.W)
    	    address_generation_complete := false.B
    	
    	} .elsewhen(Address === io.address_generator_in.max_iteration - 1.U){
    	    io.address_generator_out.neuron_reset := false.B
    	    io.address_generator_out.bias_valid := true.B

    	    Address := Address + 1.U(datawidth.W)
    	    address_generation_complete := true.B
    	    
    	} .elsewhen(Address === io.address_generator_in.max_iteration){
    	    address_generation_complete := false.B

    	    io.address_generator_out.neuron_reset := true.B
    	    io.address_generator_out.bias_valid := false.B
    	    
    	}
    	
    }
    
    io.address_generator_out.Address := Address
    io.address_generator_out.address_generation_complete := address_generation_complete

    io.address_generator_out.read_enables := enables
    
}

object DriverAddressGenerator extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new AddressGenerator(8, 32, 16, 4), Array("--target-dir", "generated/"))
}
