circuit NNDescriptionTableAccess :
  module NNDescriptionTableAccess :
    input clock : Clock
    input reset : UInt<1>
    output io : { flip wrEna : UInt<1>, flip Addr : UInt<10>, flip dataIn : UInt<16>, rdData : UInt<16>}

    smem mem : UInt<16> [32] @[nn_description_table_access.scala 20:24]
    io.rdData <= UInt<16>("h0") @[nn_description_table_access.scala 24:13]
    node _rdwrPort_T = bits(io.Addr, 4, 0) @[nn_description_table_access.scala 26:21]
    infer mport rdwrPort = mem[_rdwrPort_T], clock @[nn_description_table_access.scala 26:21]
    when io.wrEna : @[nn_description_table_access.scala 28:19]
      rdwrPort <= io.dataIn @[nn_description_table_access.scala 28:30]
    else :
      io.rdData <= rdwrPort @[nn_description_table_access.scala 29:31]

