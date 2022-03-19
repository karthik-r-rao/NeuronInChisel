package ControlUnit

import chisel3._
import chisel3.util.log2Up

class AddressGeneratorInterfaceIn(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Bundle{
    // data signals

    val memoryUnits = Input(Vec(numberOfPE, Bits(datawidth.W)))
    
    val max_iteration = Input(Bits(datawidth.W))
    
    val datapoint_write_data = Input(Bits(datawidth.W)) 
        
    // control signals
    val address_valid = Input(Bool())
    val enable_valid = Input(Bool())
    val datapoint_write_valid = Input(Bool())
    val reset = Input(Bool())
}

class AddressGeneratorInterfaceOut(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Bundle{
    val Address = Output(Bits(log2Up(memoryHeight).W))
    
    val read_enables = Output(Vec(numberOfPE, Bits(memoryDepth.W)))
    
    val datapoint_write_data = Output(Bits(datawidth.W))
    val datapoint_write_valid = Output(Bool())
    
    val address_generation_complete = Output(Bool())    
}

class AddressGenerator(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int) extends Module{
    val io = IO(new Bundle{
        val address_generator_in = new AddressGeneratorInterfaceIn(memoryDepth, memoryHeight, datawidth, numberOfPE)
        val address_generator_out = new AddressGeneratorInterfaceOut(memoryDepth, memoryHeight, datawidth, numberOfPE)
    })


    val enables = Wire(Vec(numberOfPE, UInt(memoryDepth.W)))
        
    val Address = RegInit(0.U(log2Up(memoryHeight).W))

    val address_generation_complete = WireDefault(false.B)
    
    val datapoint_write_data = RegInit(0.U(datawidth.W))
    val datapoint_write_valid = RegInit(false.B)
    
    
    when(io.address_generator_in.enable_valid){

	for(j <- 0 until numberOfPE){
	    enables(j) := 1.U(memoryDepth.W)<<io.address_generator_in.memoryUnits(j)
	}

    } .otherwise{

	for(j <- 0 until numberOfPE){
	    enables(j) := 0.U(memoryDepth.W)	
	}


    }
    
    when(io.address_generator_in.address_valid){
    	when(io.address_generator_in.reset){
    	    Address := 0.U(log2Up(memoryHeight).W)
    	    address_generation_complete := false.B
    	    when(io.address_generator_in.datapoint_write_valid){
    	    	datapoint_write_data := io.address_generator_in.datapoint_write_data
    	    	datapoint_write_valid := true.B
    	    }
    	    
    	} .elsewhen(Address < io.address_generator_in.max_iteration-1.U){
    	    Address := Address + 1.U(memoryDepth.W)
    	    address_generation_complete := false.B
    	    when(io.address_generator_in.datapoint_write_valid){
    	    	datapoint_write_data := io.address_generator_in.datapoint_write_data
    	    	datapoint_write_valid := true.B
    	    }
    	
    	} .elsewhen(Address === io.address_generator_in.max_iteration - 1.U){
    	    Address := 0.U(log2Up(memoryHeight).W)
    	    address_generation_complete := true.B
    	    when(io.address_generator_in.datapoint_write_valid){
    	    	datapoint_write_data := io.address_generator_in.datapoint_write_data
    	    	datapoint_write_valid := false.B
    	    }
    	}
    	
    }
    
    io.address_generator_out.Address := Address
    io.address_generator_out.address_generation_complete := address_generation_complete

    io.address_generator_out.read_enables := enables

    io.address_generator_out.datapoint_write_data := datapoint_write_data
    io.address_generator_out.datapoint_write_valid := datapoint_write_valid
    
}

object DriverAddressGenerator extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new AddressGenerator(128, 16, 16, 4), Array("--target-dir", "generated/"))
}
