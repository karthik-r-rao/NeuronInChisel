circuit AddressGenerator :
  module AddressGenerator :
    input clock : Clock
    input reset : UInt<1>
    output io : { address_generator_in : { flip memoryUnits : UInt<2>[4], flip max_iteration : UInt<16>, flip address_valid : UInt<1>, flip enable_valid : UInt<1>, flip reset : UInt<1>}, address_generator_out : { Address : UInt<16>, read_enables : UInt<2>[4], bias_valid : UInt<1>, neuron_reset : UInt<1>, address_generation_complete : UInt<1>}}

    wire enables : UInt<2>[4] @[addressGenerator.scala 38:23]
    reg Address : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[addressGenerator.scala 40:26]
    reg address_generation_complete : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[addressGenerator.scala 42:46]
    io.address_generator_out.neuron_reset <= UInt<1>("h1") @[addressGenerator.scala 44:43]
    io.address_generator_out.bias_valid <= UInt<1>("h0") @[addressGenerator.scala 45:41]
    when io.address_generator_in.enable_valid : @[addressGenerator.scala 47:47]
      enables[0] <= io.address_generator_in.memoryUnits[0] @[addressGenerator.scala 50:24]
      enables[1] <= io.address_generator_in.memoryUnits[1] @[addressGenerator.scala 50:24]
      enables[2] <= io.address_generator_in.memoryUnits[2] @[addressGenerator.scala 50:24]
      enables[3] <= io.address_generator_in.memoryUnits[3] @[addressGenerator.scala 50:24]
    else :
      enables[0] <= UInt<2>("h0") @[addressGenerator.scala 56:24]
      enables[1] <= UInt<2>("h0") @[addressGenerator.scala 56:24]
      enables[2] <= UInt<2>("h0") @[addressGenerator.scala 56:24]
      enables[3] <= UInt<2>("h0") @[addressGenerator.scala 56:24]
    when io.address_generator_in.address_valid : @[addressGenerator.scala 62:48]
      when io.address_generator_in.reset : @[addressGenerator.scala 63:44]
        io.address_generator_out.neuron_reset <= UInt<1>("h1") @[addressGenerator.scala 64:51]
        io.address_generator_out.bias_valid <= UInt<1>("h0") @[addressGenerator.scala 65:49]
        Address <= UInt<16>("h0") @[addressGenerator.scala 67:21]
        address_generation_complete <= UInt<1>("h0") @[addressGenerator.scala 68:41]
      else :
        node _T = sub(io.address_generator_in.max_iteration, UInt<1>("h1")) @[addressGenerator.scala 70:68]
        node _T_1 = tail(_T, 1) @[addressGenerator.scala 70:68]
        node _T_2 = lt(Address, _T_1) @[addressGenerator.scala 70:29]
        when _T_2 : @[addressGenerator.scala 70:73]
          io.address_generator_out.neuron_reset <= UInt<1>("h0") @[addressGenerator.scala 71:51]
          io.address_generator_out.bias_valid <= UInt<1>("h0") @[addressGenerator.scala 72:49]
          node _Address_T = add(Address, UInt<8>("h1")) @[addressGenerator.scala 74:32]
          node _Address_T_1 = tail(_Address_T, 1) @[addressGenerator.scala 74:32]
          Address <= _Address_T_1 @[addressGenerator.scala 74:21]
          address_generation_complete <= UInt<1>("h0") @[addressGenerator.scala 75:41]
        else :
          node _T_3 = sub(io.address_generator_in.max_iteration, UInt<1>("h1")) @[addressGenerator.scala 77:71]
          node _T_4 = tail(_T_3, 1) @[addressGenerator.scala 77:71]
          node _T_5 = eq(Address, _T_4) @[addressGenerator.scala 77:29]
          when _T_5 : @[addressGenerator.scala 77:77]
            io.address_generator_out.neuron_reset <= UInt<1>("h0") @[addressGenerator.scala 78:51]
            io.address_generator_out.bias_valid <= UInt<1>("h1") @[addressGenerator.scala 79:49]
            node _Address_T_2 = add(Address, UInt<16>("h1")) @[addressGenerator.scala 81:32]
            node _Address_T_3 = tail(_Address_T_2, 1) @[addressGenerator.scala 81:32]
            Address <= _Address_T_3 @[addressGenerator.scala 81:21]
            address_generation_complete <= UInt<1>("h1") @[addressGenerator.scala 82:41]
          else :
            node _T_6 = eq(Address, io.address_generator_in.max_iteration) @[addressGenerator.scala 84:29]
            when _T_6 : @[addressGenerator.scala 84:71]
              address_generation_complete <= UInt<1>("h0") @[addressGenerator.scala 85:41]
              io.address_generator_out.neuron_reset <= UInt<1>("h1") @[addressGenerator.scala 87:51]
              io.address_generator_out.bias_valid <= UInt<1>("h0") @[addressGenerator.scala 88:49]
    io.address_generator_out.Address <= Address @[addressGenerator.scala 94:38]
    io.address_generator_out.address_generation_complete <= address_generation_complete @[addressGenerator.scala 95:58]
    io.address_generator_out.read_enables[0] <= enables[0] @[addressGenerator.scala 97:43]
    io.address_generator_out.read_enables[1] <= enables[1] @[addressGenerator.scala 97:43]
    io.address_generator_out.read_enables[2] <= enables[2] @[addressGenerator.scala 97:43]
    io.address_generator_out.read_enables[3] <= enables[3] @[addressGenerator.scala 97:43]

