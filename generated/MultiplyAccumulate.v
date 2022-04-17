module MultiplyAccumulate(
  input         clock,
  input         reset,
  input         io_mac_in_rst,
  input  [17:0] io_mac_in_op1,
  input  [17:0] io_mac_in_op2,
  input         io_mac_in_bias,
  output [35:0] io_mac_out
);
`ifdef RANDOMIZE_REG_INIT
  reg [63:0] _RAND_0;
`endif // RANDOMIZE_REG_INIT
  reg [35:0] acc; // @[mac.scala 28:18]
  wire [29:0] _op1_T = {$signed(io_mac_in_op1), 12'h0}; // @[mac.scala 33:30]
  wire [29:0] _GEN_0 = io_mac_in_bias ? $signed(_op1_T) : $signed({{12{io_mac_in_op1[17]}},io_mac_in_op1}); // @[mac.scala 32:27 33:13 36:13]
  wire [17:0] op1 = _GEN_0[17:0]; // @[mac.scala 25:19]
  wire [35:0] _multiply_T = $signed(op1) * $signed(io_mac_in_op2); // @[mac.scala 43:32]
  wire [35:0] multiply = io_mac_in_rst ? $signed(36'sh0) : $signed(_multiply_T); // @[mac.scala 30:14 39:25 43:18]
  wire [35:0] _acc_T_2 = $signed(acc) + $signed(multiply); // @[mac.scala 44:20]
  assign io_mac_out = acc; // @[mac.scala 47:16]
  always @(posedge clock) begin
    if (io_mac_in_rst) begin // @[mac.scala 39:25]
      acc <= 36'sh0; // @[mac.scala 40:13]
    end else begin
      acc <= _acc_T_2; // @[mac.scala 44:13]
    end
  end
// Register and memory initialization
`ifdef RANDOMIZE_GARBAGE_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_INVALID_ASSIGN
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_REG_INIT
`define RANDOMIZE
`endif
`ifdef RANDOMIZE_MEM_INIT
`define RANDOMIZE
`endif
`ifndef RANDOM
`define RANDOM $random
`endif
`ifdef RANDOMIZE_MEM_INIT
  integer initvar;
`endif
`ifndef SYNTHESIS
`ifdef FIRRTL_BEFORE_INITIAL
`FIRRTL_BEFORE_INITIAL
`endif
initial begin
  `ifdef RANDOMIZE
    `ifdef INIT_RANDOM
      `INIT_RANDOM
    `endif
    `ifndef VERILATOR
      `ifdef RANDOMIZE_DELAY
        #`RANDOMIZE_DELAY begin end
      `else
        #0.002 begin end
      `endif
    `endif
`ifdef RANDOMIZE_REG_INIT
  _RAND_0 = {2{`RANDOM}};
  acc = _RAND_0[35:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
