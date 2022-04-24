package ControlUnit

import chisel3._
import chisel3.util._
import chisel3.util.log2Up
import chisel3.util.experimental.loadMemoryFromFile   
import firrtl.annotations.MemoryLoadFileType
import scala.io.Source


class ControlUnitIntegratedInterfaceIn(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int, tableDepth: Int) extends Bundle{

    val controller_reset = Input(Bool())
    
    val interconnect_new_datapoint_ready = Input(Bool())
    val interconnect_load_ready = Input(Bool())
    val interconnect_memory_output = Input(UInt(datawidth.W))

    val PE_outputs = Input(Vec(numberOfPE, UInt(datawidth.W)))

}



class ControlUnitIntegratedInterfaceOut(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int, tableDepth: Int) extends Bundle{

// Load PE memory outputs
    val write_memoryUnits = Output(Vec(numberOfPE, Bits(2.W)))
    val weight_buffer_memory_write_data = Output(UInt(datawidth.W))
    val weight_buffer_memory_write_address = Output(Bits(datawidth.W))

    val interconnect_loading_layer = Output(Bits(datawidth.W))
    val interconnect_loading_activation = Output(Bits(datawidth.W))
    val interconnect_load_request = Output(Bool())
    
    val interconnect_load_read_address = Output(Bits(datawidth.W))  
    
    val load_new_data_request = Output(Bool())


    val loading_layer = Output(Bits(datawidth.W))
    val write_memoryUnits_testing = Output(Vec(numberOfPE, Bits(2.W)))

// Address Generator Outputs
    val datapoint_memory_write_enable = Output(Bool())
    val datapoint_memory_write_data = Output(UInt(datawidth.W))
    val datapoint_memory_write_address = Output(Bits(datawidth.W))

    val Address = Output(Bits(log2Up(memoryHeight).W)) 
    val read_memoryUnits = Output(Vec(numberOfPE, Bits(2.W)))
    val neuron_reset = Output(Bool())
    val bias_valid = Output(Bool())
    
    val controlUnit_current_state = Output(Bits(datawidth.W))
    val load_pe_memory_load_weights_state = Output(Bits(datawidth.W))
    val load_pe_memory_load_datapoint_state = Output(Bits(datawidth.W))
}


class ControlUnitIntegrated(memoryDepth: Int, memoryHeight: Int, datawidth: Int, numberOfPE: Int, tableDepth: Int) extends Module{
    val io = IO(new Bundle{
        val controlUnit_in = new ControlUnitIntegratedInterfaceIn(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth)
        val controlUnit_out = new ControlUnitIntegratedInterfaceOut(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth)
    })

    val controller = Module(new Controller(memoryDepth, memoryHeight, datawidth, numberOfPE))
    val load_pe_memory = Module(new LoadPEMemory(memoryDepth, memoryHeight, datawidth, numberOfPE))
    val save_pe_outputs = Module(new SavePEOutput(memoryDepth, memoryHeight, datawidth, numberOfPE))
    val address_generator = Module(new AddressGenerator(memoryDepth, memoryHeight, datawidth, numberOfPE))
    
    val buffer_datapoint_memory_access = Module(new bufferDatapointMemoryAccess(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth))
    
    val nn_description_table_access = Module(new NNDescriptionTableAccess(memoryDepth, memoryHeight, datawidth, numberOfPE, tableDepth))



//  Controller Inputs
    controller.io.controller_in.controller_reset := io.controlUnit_in.controller_reset
    controller.io.controller_in.address_generation_complete := address_generator.io.address_generator_out.address_generation_complete
    controller.io.controller_in.loading_initial_weights_complete := load_pe_memory.io.load_pe_memory_out.load_initial_weights_complete
    controller.io.controller_in.load_datapoint_complete := load_pe_memory.io.load_pe_memory_out.load_datapoint_complete
    controller.io.controller_in.load_buffer_weight_memory_complete := load_pe_memory.io.load_pe_memory_out.load_buffer_weight_memory_complete
    controller.io.controller_in.save_data_complete := save_pe_outputs.io.save_pe_output_out.save_data_complete
    
    controller.io.controller_in.nn_description_table_input := nn_description_table_access.io.rdData


//  Load PE Memory Inputs
    load_pe_memory.io.load_pe_memory_in.load_initial_weights := controller.io.controller_out.load_initial_weights
    load_pe_memory.io.load_pe_memory_in.load_datapoint := controller.io.controller_out.load_datapoint
    load_pe_memory.io.load_pe_memory_in.load_new_request := controller.io.controller_out.load_new_request
    load_pe_memory.io.load_pe_memory_in.load_data_from_buffer := controller.io.controller_out.load_data_from_buffer
    load_pe_memory.io.load_pe_memory_in.load_same_data := controller.io.controller_out.load_same_data
    load_pe_memory.io.load_pe_memory_in.weight_buffer_load_request := controller.io.controller_out.weight_buffer_load_request
    
    load_pe_memory.io.load_pe_memory_in.loading_layer := controller.io.controller_out.loading_layer
    load_pe_memory.io.load_pe_memory_in.loading_length := controller.io.controller_out.max_iteration
    load_pe_memory.io.load_pe_memory_in.loading_activations := controller.io.controller_out.loading_activations
    load_pe_memory.io.load_pe_memory_in.write_memoryUnits := controller.io.controller_out.write_memoryUnits

    load_pe_memory.io.load_pe_memory_in.buffer_memory_output := buffer_datapoint_memory_access.io.rdData

    load_pe_memory.io.load_pe_memory_in.new_datapoint_ready := io.controlUnit_in.interconnect_new_datapoint_ready
    load_pe_memory.io.load_pe_memory_in.interconnect_load_ready := io.controlUnit_in.interconnect_load_ready
    load_pe_memory.io.load_pe_memory_in.interconnect_memory_output := io.controlUnit_in.interconnect_memory_output


//  Save PE Outputs - Inputs
    save_pe_outputs.io.save_pe_output_in.save_data_request := controller.io.controller_out.save_data_request
    save_pe_outputs.io.save_pe_output_in.current_buffer_memory_pointer := controller.io.controller_out.current_buffer_memory_pointer

    save_pe_outputs.io.save_pe_output_in.PE_outputs := io.controlUnit_in.PE_outputs


//  Address Generator Inputs
    address_generator.io.address_generator_in.memoryUnits := controller.io.controller_out.read_memoryUnits
    address_generator.io.address_generator_in.max_iteration := controller.io.controller_out.max_iteration

    address_generator.io.address_generator_in.address_valid := controller.io.controller_out.address_generator_address_valid
    address_generator.io.address_generator_in.enable_valid := controller.io.controller_out.address_generator_enable_valid
    address_generator.io.address_generator_in.reset := controller.io.controller_out.address_generator_reset


//  Buffer Datapoint Memory Access Inputs
    buffer_datapoint_memory_access.io.wrEna := save_pe_outputs.io.save_pe_output_out.buffer_memory_write_enable
    buffer_datapoint_memory_access.io.Addr := (save_pe_outputs.io.save_pe_output_out.buffer_memory_write_address | load_pe_memory.io.load_pe_memory_out.buffer_memory_write_address)
    buffer_datapoint_memory_access.io.dataIn := save_pe_outputs.io.save_pe_output_out.buffer_memory_write_data


//  Datapoint Memory Access Inputs
    io.controlUnit_out.datapoint_memory_write_enable := load_pe_memory.io.load_pe_memory_out.datapoint_memory_write_enable
    io.controlUnit_out.datapoint_memory_write_data := load_pe_memory.io.load_pe_memory_out.datapoint_memory_write_data
    io.controlUnit_out.datapoint_memory_write_address := load_pe_memory.io.load_pe_memory_out.datapoint_memory_write_address
    
    
// NN description Table access inputs
    nn_description_table_access.io.wrEna := false.B
    nn_description_table_access.io.Addr := controller.io.controller_out.nn_description_table_address
    nn_description_table_access.io.dataIn := 0.U(datawidth.W)
    
    
    io.controlUnit_out.Address := address_generator.io.address_generator_out.Address
    io.controlUnit_out.read_memoryUnits := address_generator.io.address_generator_out.read_enables
    io.controlUnit_out.neuron_reset := address_generator.io.address_generator_out.neuron_reset
    io.controlUnit_out.bias_valid := address_generator.io.address_generator_out.bias_valid
    
    
    io.controlUnit_out.interconnect_loading_layer := load_pe_memory.io.load_pe_memory_out.interconnect_loading_layer
    io.controlUnit_out.interconnect_loading_activation := load_pe_memory.io.load_pe_memory_out.interconnect_loading_activation
    io.controlUnit_out.interconnect_load_request := load_pe_memory.io.load_pe_memory_out.interconnect_load_request
    io.controlUnit_out.interconnect_load_read_address := load_pe_memory.io.load_pe_memory_out.interconnect_load_read_address
    io.controlUnit_out.load_new_data_request := load_pe_memory.io.load_pe_memory_out.load_new_data_request
    
    io.controlUnit_out.write_memoryUnits := load_pe_memory.io.load_pe_memory_out.write_memoryUnits    
    io.controlUnit_out.weight_buffer_memory_write_address := load_pe_memory.io.load_pe_memory_out.weight_buffer_memory_write_address
    io.controlUnit_out.weight_buffer_memory_write_data := load_pe_memory.io.load_pe_memory_out.weight_buffer_memory_write_data


    io.controlUnit_out.controlUnit_current_state := controller.io.controller_out.current_state
    io.controlUnit_out.load_pe_memory_load_datapoint_state := load_pe_memory.io.load_pe_memory_out.current_load_datapoint_state
    io.controlUnit_out.load_pe_memory_load_weights_state := load_pe_memory.io.load_pe_memory_out.current_load_weights_state

    io.controlUnit_out.loading_layer := controller.io.controller_out.loading_layer
    io.controlUnit_out.write_memoryUnits_testing := controller.io.controller_out.write_memoryUnits
    
}

object DriverControlUnitIntegrated extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new ControlUnitIntegrated(8, 32, 16, 4, 32), Array("--target-dir", "generated/"))
}
