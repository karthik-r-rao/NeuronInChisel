package ControlUnit

import chisel3._
import chisel3.util.log2Up

class AddressGeneratorInterfaceIn(memoryDepth: Int, memoryHeight: Int, datawidth: Int) extends Bundle{
    // data signals
    val memoryUnit1 = Input(Bits(datawidth.W))
    val memoryUnit2 = Input(Bits(datawidth.W))
    val memoryUnit3 = Input(Bits(datawidth.W))
    val memoryUnit4 = Input(Bits(datawidth.W))

    val max_iteration = Input(Bits(datawidth.W))
        
    // control signals
    val valid = Input(Bool())
    val reset = Input(Bool())
}

class AddressGeneratorInterfaceOut(memoryDepth: Int, memoryHeight: Int, datawidth: Int) extends Bundle{
    val Address = Output(Bits(log2Up(memoryHeight).W))
    
    val read_enable1 = Output(Bits(memoryDepth.W))
    val read_enable2 = Output(Bits(memoryDepth.W))
    val read_enable3 = Output(Bits(memoryDepth.W))
    val read_enable4 = Output(Bits(memoryDepth.W))
    
    val address_generation_complete = Output(Bool())    
}

class AddressGenerator(memoryDepth: Int, memoryHeight: Int, datawidth: Int) extends Module{
    val io = IO(new Bundle{
        val address_generator_in = new AddressGeneratorInterfaceIn(memoryDepth, memoryHeight, datawidth)
        val address_generator_out = new AddressGeneratorInterfaceOut(memoryDepth, memoryHeight, datawidth)
    })

    val enable1 = WireDefault(0.U(memoryDepth.W))
    val enable2 = WireDefault(0.U(memoryDepth.W))
    val enable3 = WireDefault(0.U(memoryDepth.W))
    val enable4 = WireDefault(0.U(memoryDepth.W))
    
    val Address = RegInit(0.U(log2Up(memoryHeight).W))

    val address_generation_complete = WireDefault(false.B)
    
    when(io.address_generator_in.valid){
    	enable1 := 1.U(memoryDepth.W)<<io.address_generator_in.memoryUnit1
    	enable2 := 1.U(memoryDepth.W)<<io.address_generator_in.memoryUnit2
    	enable3 := 1.U(memoryDepth.W)<<io.address_generator_in.memoryUnit3
    	enable4 := 1.U(memoryDepth.W)<<io.address_generator_in.memoryUnit4
    }
    
    when(io.address_generator_in.valid){
    	when(io.address_generator_in.reset){
    	    Address := 0.U(log2Up(memoryHeight).W)
    	    address_generation_complete := false.B
    	    
    	} .elsewhen(Address < io.address_generator_in.max_iteration-1.U){
    	    Address := Address + 1.U(memoryDepth.W)
    	    address_generation_complete := false.B
    	
    	} .elsewhen(Address === io.address_generator_in.max_iteration - 1.U){
    	    Address := 0.U(log2Up(memoryHeight).W)
    	    address_generation_complete := true.B
    	}
    }
    
    io.address_generator_out.Address := Address
    io.address_generator_out.address_generation_complete := address_generation_complete
    io.address_generator_out.read_enable1 := enable1
    io.address_generator_out.read_enable2 := enable2
    io.address_generator_out.read_enable3 := enable3
    io.address_generator_out.read_enable4 := enable4
    
}

object DriverAddressGenerator extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new AddressGenerator(128, 16, 16), Array("--target-dir", "generated/"))
}
