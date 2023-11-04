circuit MultiplyAccumulate :
  module MultiplyAccumulate :
    input clock : Clock
    input reset : UInt<1>
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

