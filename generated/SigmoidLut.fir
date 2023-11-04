circuit SigmoidLut :
  module SigmoidLut :
    input clock : Clock
    input reset : UInt<1>
    output io : { flip addr : UInt<10>, dataOut : UInt<10>}

    smem mem : UInt<10> [1024] @[sigmoid.scala 17:26]
    infer mport io_dataOut_MPORT = mem[io.addr], clock @[sigmoid.scala 21:22]
    io.dataOut <= io_dataOut_MPORT @[sigmoid.scala 21:16]

