package NeuronArchitecture

import chisel3._
import chisel3.util._
import chisel3.util.log2Up
import chisel3.util.experimental.loadMemoryFromFile   
import firrtl.annotations.MemoryLoadFileType
import scala.io.Source

import ControlUnit._ 
import nn._

class NeuronArchitectureInterfaceIn(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int, tableDepth: Int) extends Bundle{

    val controller_reset = Input(Bool())
    
    val interconnect_new_datapoint_ready = Input(Bool())
    val interconnect_load_ready = Input(Bool())
    val interconnect_memory_output = Input(UInt(datawidth.W))

}



class NeuronArchitectureInterfaceOut(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int, tableDepth: Int) extends Bundle{


    val interconnect_loading_layer = Output(Bits(datawidth.W))
    val interconnect_loading_activation = Output(Bits(datawidth.W))
    val interconnect_load_request = Output(Bool())
    
    val interconnect_load_read_address = Output(Bits(datawidth.W))  
    
    val load_new_data_request = Output(Bool())

}


class NeuronArchitecture(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int, tableDepth: Int) extends Module{
    val io = IO(new Bundle{
        val neuron_architecture_in = new NeuronArchitectureInterfaceIn(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth)
        val neuron_architecture_out = new NeuronArchitectureInterfaceOut(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth)
    })

/*
//INITIALIZE
// Load PE memory outputs
    for(j <- 0 until numberOfPE){
    	io.neuron_architecture_out.write_memoryUnits = Output(Vec(numberOfPE, Bits(2.W)))
    }
    io.neuron_architecture_out.weight_buffer_memory_write_enable = Output(Bool())
    io.neuron_architecture_out.weight_buffer_memory_write_data = Output(SInt(datawidth.W))
    io.neuron_architecture_out.weight_buffer_memory_write_address = Output(Bits(datawidth.W))

    io.neuron_architecture_out.interconnect_loading_layer = Output(Bits(datawidth.W))
    io.neuron_architecture_out.interconnect_loading_activation = Output(Bits(datawidth.W))
    io.neuron_architecture_out.interconnect_load_request = Output(Bool())
    
    io.neuron_architecture_out.interconnect_load_read_address = Output(Bits(datawidth.W))  
    
    io.neuron_architecture_out.load_new_data_request = Output(Bool())

// Address Generator Outputs
    io.neuron_architecture_out.datapoint_memory_write_enable = Output(Bool())
    io.neuron_architecture_out.datapoint_memory_write_data = Output(SInt(datawidth.W))
    io.neuron_architecture_out.datapoint_memory_write_address = Output(Bits(datawidth.W))

    io.neuron_architecture_out.Address = Output(Bits(log2Up(memoryHeight).W)) 
    io.neuron_architecture_out.read_memoryUnits = Output(Vec(numberOfPE, Bits(2.W)))
    io.neuron_architecture_out.neuron_reset = Output(Bool())
    io.neuron_architecture_out.bias_valid = Output(Bool())
*/

    val neuron_1 = Module(new Neuron(6, 12, 3, 7))
    val neuron_2 = Module(new Neuron(6, 12, 3, 7))
    val neuron_3 = Module(new Neuron(6, 12, 3, 7)) 
    val neuron_4 = Module(new Neuron(6, 12, 3, 7)) 

    val datapoint_memory = Module(new datapointMemoryAccess(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth))
    
    val weight_memory_1 = Module(new WeightMemoryAccess(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth))
    val weight_memory_2 = Module(new WeightMemoryAccess(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth))
    val weight_memory_3 = Module(new WeightMemoryAccess(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth))
    val weight_memory_4 = Module(new WeightMemoryAccess(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth))
    val weight_memory_5 = Module(new WeightMemoryAccess(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth))
    val weight_memory_6 = Module(new WeightMemoryAccess(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth))
    val weight_memory_7 = Module(new WeightMemoryAccess(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth))
    val weight_memory_8 = Module(new WeightMemoryAccess(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth))
    
    val control_unit = Module(new ControlUnitIntegrated(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth))

//  Control Unit Inputs
    
    control_unit.io.controlUnit_in.controller_reset := io.neuron_architecture_in.controller_reset
    control_unit.io.controlUnit_in.interconnect_new_datapoint_ready := io.neuron_architecture_in.interconnect_new_datapoint_ready
    control_unit.io.controlUnit_in.interconnect_load_ready := io.neuron_architecture_in.interconnect_load_ready
    control_unit.io.controlUnit_in.interconnect_memory_output := io.neuron_architecture_in.interconnect_memory_output
    
    control_unit.io.controlUnit_in.PE_outputs(0) := neuron_1.io.neuron_out.asUInt
    control_unit.io.controlUnit_in.PE_outputs(1) := neuron_2.io.neuron_out.asUInt
    control_unit.io.controlUnit_in.PE_outputs(2) := neuron_3.io.neuron_out.asUInt
    control_unit.io.controlUnit_in.PE_outputs(3) := neuron_4.io.neuron_out.asUInt
    
    
//  datapoint memory inputs
    
    when(control_unit.io.controlUnit_out.datapoint_memory_write_enable){
    	datapoint_memory.io.Addr := control_unit.io.controlUnit_out.datapoint_memory_write_address
    	datapoint_memory.io.dataIn := control_unit.io.controlUnit_out.datapoint_memory_write_data
    	datapoint_memory.io.wrEna := control_unit.io.controlUnit_out.datapoint_memory_write_enable
    } .otherwise{
    	datapoint_memory.io.dataIn := 0.U(datawidth.W) 
    	datapoint_memory.io.wrEna := false.B
    	datapoint_memory.io.Addr := control_unit.io.controlUnit_out.Address
    }
    
//  Weight Memory Inputs
    weight_memory_1.io.wrEna := control_unit.io.controlUnit_out.write_memoryUnits(0)(0)
    weight_memory_1.io.dataIn := control_unit.io.controlUnit_out.weight_buffer_memory_write_data
    weight_memory_1.io.Addr := control_unit.io.controlUnit_out.Address | control_unit.io.controlUnit_out.weight_buffer_memory_write_address
    	
    weight_memory_2.io.wrEna := control_unit.io.controlUnit_out.write_memoryUnits(0)(1)
    weight_memory_2.io.dataIn := control_unit.io.controlUnit_out.weight_buffer_memory_write_data
    weight_memory_2.io.Addr := control_unit.io.controlUnit_out.Address | control_unit.io.controlUnit_out.weight_buffer_memory_write_address

    weight_memory_3.io.wrEna := control_unit.io.controlUnit_out.write_memoryUnits(1)(0)
    weight_memory_3.io.dataIn := control_unit.io.controlUnit_out.weight_buffer_memory_write_data
    weight_memory_3.io.Addr := control_unit.io.controlUnit_out.Address | control_unit.io.controlUnit_out.weight_buffer_memory_write_address

    weight_memory_4.io.wrEna := control_unit.io.controlUnit_out.write_memoryUnits(1)(1)
    weight_memory_4.io.dataIn := control_unit.io.controlUnit_out.weight_buffer_memory_write_data
    weight_memory_4.io.Addr := control_unit.io.controlUnit_out.Address | control_unit.io.controlUnit_out.weight_buffer_memory_write_address

    weight_memory_5.io.wrEna := control_unit.io.controlUnit_out.write_memoryUnits(2)(0)
    weight_memory_5.io.dataIn := control_unit.io.controlUnit_out.weight_buffer_memory_write_data
    weight_memory_5.io.Addr := control_unit.io.controlUnit_out.Address | control_unit.io.controlUnit_out.weight_buffer_memory_write_address

    weight_memory_6.io.wrEna := control_unit.io.controlUnit_out.write_memoryUnits(2)(1)
    weight_memory_6.io.dataIn := control_unit.io.controlUnit_out.weight_buffer_memory_write_data
    weight_memory_6.io.Addr := control_unit.io.controlUnit_out.Address | control_unit.io.controlUnit_out.weight_buffer_memory_write_address

    weight_memory_7.io.wrEna := control_unit.io.controlUnit_out.write_memoryUnits(3)(0)
    weight_memory_7.io.dataIn := control_unit.io.controlUnit_out.weight_buffer_memory_write_data
    weight_memory_7.io.Addr := control_unit.io.controlUnit_out.Address | control_unit.io.controlUnit_out.weight_buffer_memory_write_address

    weight_memory_8.io.wrEna := control_unit.io.controlUnit_out.write_memoryUnits(3)(1)
    weight_memory_8.io.dataIn := control_unit.io.controlUnit_out.weight_buffer_memory_write_data
    weight_memory_8.io.Addr := control_unit.io.controlUnit_out.Address | control_unit.io.controlUnit_out.weight_buffer_memory_write_address

    

//  Neuron Inputs
    neuron_1.io.neuron_in.rst := control_unit.io.controlUnit_out.neuron_reset
    neuron_1.io.neuron_in.op1 := datapoint_memory.io.rdData.asSInt
    when(control_unit.io.controlUnit_out.read_memoryUnits(0) === 1.U(2.W)){
    	neuron_1.io.neuron_in.op2 := weight_memory_1.io.rdData.asSInt
    } .elsewhen(control_unit.io.controlUnit_out.read_memoryUnits(0) === 1.U(2.W)){
    	neuron_1.io.neuron_in.op2 := weight_memory_2.io.rdData.asSInt
    } .otherwise{
    	neuron_1.io.neuron_in.op2 := 0.U(datawidth.W).asSInt
    }
    neuron_1.io.neuron_in.bias := control_unit.io.controlUnit_out.bias_valid
    

    neuron_2.io.neuron_in.rst := control_unit.io.controlUnit_out.neuron_reset
    neuron_2.io.neuron_in.op1 := datapoint_memory.io.rdData.asSInt
    when(control_unit.io.controlUnit_out.read_memoryUnits(1) === 1.U(2.W)){
    	neuron_2.io.neuron_in.op2 := weight_memory_3.io.rdData.asSInt
    } .elsewhen(control_unit.io.controlUnit_out.read_memoryUnits(1) === 1.U(2.W)){
    	neuron_2.io.neuron_in.op2 := weight_memory_4.io.rdData.asSInt
    } .otherwise{
    	neuron_2.io.neuron_in.op2 := 0.U(datawidth.W).asSInt
    }
    neuron_2.io.neuron_in.bias := control_unit.io.controlUnit_out.bias_valid


    neuron_3.io.neuron_in.rst := control_unit.io.controlUnit_out.neuron_reset
    neuron_3.io.neuron_in.op1 := datapoint_memory.io.rdData.asSInt
    when(control_unit.io.controlUnit_out.read_memoryUnits(2) === 1.U(2.W)){
    	neuron_3.io.neuron_in.op2 := weight_memory_5.io.rdData.asSInt
    } .elsewhen(control_unit.io.controlUnit_out.read_memoryUnits(2) === 1.U(2.W)){
    	neuron_3.io.neuron_in.op2 := weight_memory_6.io.rdData.asSInt
    } .otherwise{
    	neuron_3.io.neuron_in.op2 := 0.U(datawidth.W).asSInt
    }
    neuron_3.io.neuron_in.bias := control_unit.io.controlUnit_out.bias_valid


    neuron_4.io.neuron_in.rst := control_unit.io.controlUnit_out.neuron_reset
    neuron_4.io.neuron_in.op1 := datapoint_memory.io.rdData.asSInt
    when(control_unit.io.controlUnit_out.read_memoryUnits(3) === 1.U(2.W)){
    	neuron_4.io.neuron_in.op2 := weight_memory_7.io.rdData.asSInt
    } .elsewhen(control_unit.io.controlUnit_out.read_memoryUnits(3) === 1.U(2.W)){
    	neuron_4.io.neuron_in.op2 := weight_memory_8.io.rdData.asSInt
    } .otherwise{
    	neuron_4.io.neuron_in.op2 := 0.U(datawidth.W).asSInt
    }
    neuron_4.io.neuron_in.bias := control_unit.io.controlUnit_out.bias_valid
    
    io.neuron_architecture_out.interconnect_loading_layer := control_unit.io.controlUnit_out.interconnect_loading_layer
    io.neuron_architecture_out.interconnect_loading_activation := control_unit.io.controlUnit_out.interconnect_loading_activation
    io.neuron_architecture_out.interconnect_load_request := control_unit.io.controlUnit_out.interconnect_load_request
    io.neuron_architecture_out.interconnect_load_read_address := control_unit.io.controlUnit_out.interconnect_load_read_address
    io.neuron_architecture_out.load_new_data_request := control_unit.io.controlUnit_out.load_new_data_request
    

}


object DriverNeuronArchitecture extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new NeuronArchitecture(8, 32, 18, 4, 32), Array("--target-dir", "generated/"))
}
