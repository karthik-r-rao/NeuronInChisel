circuit WeightMemoryAccess :
  module WeightMemoryAccess :
    input clock : Clock
    input reset : UInt<1>
    output io : { flip wrEna : UInt<1>, flip Addr : UInt<10>, flip dataIn : UInt<16>, rdData : UInt<16>}

    smem mem : UInt<16> [1024] @[weight_memory_access.scala 20:24]
    io.rdData <= UInt<16>("h0") @[weight_memory_access.scala 22:13]
    infer mport rdwrPort = mem[io.Addr], clock @[weight_memory_access.scala 24:21]
    when io.wrEna : @[weight_memory_access.scala 26:19]
      rdwrPort <= io.dataIn @[weight_memory_access.scala 26:30]
    else :
      io.rdData <= rdwrPort @[weight_memory_access.scala 27:31]

