circuit Neuron :
  module MultiplyAccumulate :
    input clock : Clock
    input reset : Reset
    output io : { mac_in : { flip rst : UInt<1>, flip op1 : SInt<18>, flip op2 : SInt<18>, flip bias : UInt<1>}, mac_out : SInt<36>}

    wire multiply : SInt<36> @[mac.scala 24:24]
    wire op1 : SInt<18> @[mac.scala 25:19]
    reg acc : SInt<36>, clock with :
      reset => (UInt<1>("h0"), acc) @[mac.scala 28:18]
    multiply <= asSInt(UInt<1>("h0")) @[mac.scala 30:14]
    when io.mac_in.bias : @[mac.scala 32:27]
      node _op1_T = shl(io.mac_in.op1, 12) @[mac.scala 33:30]
      op1 <= _op1_T @[mac.scala 33:13]
    else :
      op1 <= io.mac_in.op1 @[mac.scala 36:13]
    when io.mac_in.rst : @[mac.scala 39:25]
      acc <= asSInt(UInt<1>("h0")) @[mac.scala 40:13]
    else :
      node _multiply_T = mul(op1, io.mac_in.op2) @[mac.scala 43:32]
      multiply <= _multiply_T @[mac.scala 43:18]
      node _acc_T = add(acc, multiply) @[mac.scala 44:20]
      node _acc_T_1 = tail(_acc_T, 1) @[mac.scala 44:20]
      node _acc_T_2 = asSInt(_acc_T_1) @[mac.scala 44:20]
      acc <= _acc_T_2 @[mac.scala 44:13]
    io.mac_out <= acc @[mac.scala 47:16]

  module SigmoidLut :
    input clock : Clock
    input reset : Reset
    output io : { flip addr : UInt<10>, dataOut : UInt<10>}

    smem mem : UInt<10> [1024] @[sigmoid.scala 17:26]
    infer mport io_dataOut_MPORT = mem[io.addr], clock @[sigmoid.scala 21:22]
    io.dataOut <= io_dataOut_MPORT @[sigmoid.scala 21:16]

  module Neuron :
    input clock : Clock
    input reset : UInt<1>
    output io : { neuron_in : { flip rst : UInt<1>, flip op1 : SInt<18>, flip op2 : SInt<18>, flip bias : UInt<1>}, neuron_out : UInt<18>}

    inst mac_inst of MultiplyAccumulate @[neuron.scala 17:26]
    mac_inst.clock <= clock
    mac_inst.reset <= reset
    inst sigmoid_inst of SigmoidLut @[neuron.scala 18:30]
    sigmoid_inst.clock <= clock
    sigmoid_inst.reset <= reset
    mac_inst.io.mac_in.bias <= io.neuron_in.bias @[neuron.scala 21:24]
    mac_inst.io.mac_in.op2 <= io.neuron_in.op2 @[neuron.scala 21:24]
    mac_inst.io.mac_in.op1 <= io.neuron_in.op1 @[neuron.scala 21:24]
    mac_inst.io.mac_in.rst <= io.neuron_in.rst @[neuron.scala 21:24]
    node _sigmoid_inst_io_addr_T = bits(mac_inst.io.mac_out, 26, 17) @[neuron.scala 23:48]
    sigmoid_inst.io.addr <= _sigmoid_inst_io_addr_T @[neuron.scala 23:26]
    node io_neuron_out_hi = cat(UInt<1>("h0"), sigmoid_inst.io.dataOut) @[Cat.scala 31:58]
    node _io_neuron_out_T = cat(io_neuron_out_hi, UInt<1>("h0")) @[Cat.scala 31:58]
    io.neuron_out <= _io_neuron_out_T @[neuron.scala 25:19]

