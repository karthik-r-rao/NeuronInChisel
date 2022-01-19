package neuron

import chisel3._
import chisel3.util.experimental.loadMemoryFromFile   
import firrtl.annotations.MemoryLoadFileType

class SigmoidLutInterfaceIn(memoryWidth: Int) extends Bundle{
    // data signals
    val data = Input(Bits(memoryWidth.W))

    // control signals
    val valid = Input(Bool())
}

class SigmoidLutInterfaceOut(memoryWidth: Int) extends Bundle{
    // data signals
    val data = Output(Bits(memoryWidth.W))

    // control signals
    val valid = Output(Bool())
}

class SigmoidLut(memoryDepth: Int, memoryWidth: Int) extends Module{
    val io = IO(new Bundle{
        val sigmoid_lut_in = new SigmoidLutInterfaceIn(memoryWidth)
        val sigmoid_lut_out = new SigmoidLutInterfaceOut(memoryWidth)
    })

    val memory = SyncReadMem(memoryDepth, Bits(memoryWidth.W))

    val data = WireDefault(0.U(memoryWidth.W))
    val valid = WireDefault(false.B)
    when (io.sigmoid_lut_in.valid){
        data := memory(io.sigmoid_lut_in.data)
        valid := true.B
    }
    io.sigmoid_lut_out.data := data
    io.sigmoid_lut_out.valid := valid

    loadMemoryFromFile(memory, "/home/karthikrrao/NeuronInChisel/generated/lut.txt", MemoryLoadFileType.Binary)  
}

object DriverSigmoidLut extends App{
    (new chisel3.stage.ChiselStage).emitVerilog(new SigmoidLut(1024, 10), Array("--target-dir", "generated/"))
}