module MultiplyAccumulate(
  input         clock,
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
module SigmoidLut(
  input        clock,
  input  [9:0] io_addr,
  output [9:0] io_dataOut
);
`ifdef RANDOMIZE_MEM_INIT
  reg [31:0] _RAND_0;
`endif // RANDOMIZE_MEM_INIT
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_1;
  reg [31:0] _RAND_2;
`endif // RANDOMIZE_REG_INIT
  reg [9:0] mem [0:1023]; // @[sigmoid.scala 17:26]
  wire  mem_io_dataOut_MPORT_en; // @[sigmoid.scala 17:26]
  wire [9:0] mem_io_dataOut_MPORT_addr; // @[sigmoid.scala 17:26]
  wire [9:0] mem_io_dataOut_MPORT_data; // @[sigmoid.scala 17:26]
  reg  mem_io_dataOut_MPORT_en_pipe_0;
  reg [9:0] mem_io_dataOut_MPORT_addr_pipe_0;
  assign mem_io_dataOut_MPORT_en = mem_io_dataOut_MPORT_en_pipe_0;
  assign mem_io_dataOut_MPORT_addr = mem_io_dataOut_MPORT_addr_pipe_0;
  assign mem_io_dataOut_MPORT_data = mem[mem_io_dataOut_MPORT_addr]; // @[sigmoid.scala 17:26]
  assign io_dataOut = mem_io_dataOut_MPORT_data; // @[sigmoid.scala 21:16]
  always @(posedge clock) begin
    mem_io_dataOut_MPORT_en_pipe_0 <= 1'h1;
    if (1'h1) begin
      mem_io_dataOut_MPORT_addr_pipe_0 <= io_addr;
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
`ifdef RANDOMIZE_MEM_INIT
  _RAND_0 = {1{`RANDOM}};
  for (initvar = 0; initvar < 1024; initvar = initvar+1)
    mem[initvar] = _RAND_0[9:0];
`endif // RANDOMIZE_MEM_INIT
`ifdef RANDOMIZE_REG_INIT
  _RAND_1 = {1{`RANDOM}};
  mem_io_dataOut_MPORT_en_pipe_0 = _RAND_1[0:0];
  _RAND_2 = {1{`RANDOM}};
  mem_io_dataOut_MPORT_addr_pipe_0 = _RAND_2[9:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
module Neuron(
  input         clock,
  input         reset,
  input         io_neuron_in_rst,
  input  [17:0] io_neuron_in_op1,
  input  [17:0] io_neuron_in_op2,
  input         io_neuron_in_bias,
  output [17:0] io_neuron_out
);
  wire  mac_inst_clock; // @[neuron.scala 17:26]
  wire  mac_inst_io_mac_in_rst; // @[neuron.scala 17:26]
  wire [17:0] mac_inst_io_mac_in_op1; // @[neuron.scala 17:26]
  wire [17:0] mac_inst_io_mac_in_op2; // @[neuron.scala 17:26]
  wire  mac_inst_io_mac_in_bias; // @[neuron.scala 17:26]
  wire [35:0] mac_inst_io_mac_out; // @[neuron.scala 17:26]
  wire  sigmoid_inst_clock; // @[neuron.scala 18:30]
  wire [9:0] sigmoid_inst_io_addr; // @[neuron.scala 18:30]
  wire [9:0] sigmoid_inst_io_dataOut; // @[neuron.scala 18:30]
  wire [11:0] _io_neuron_out_T = {1'h0,sigmoid_inst_io_dataOut,1'h0}; // @[Cat.scala 31:58]
  MultiplyAccumulate mac_inst ( // @[neuron.scala 17:26]
    .clock(mac_inst_clock),
    .io_mac_in_rst(mac_inst_io_mac_in_rst),
    .io_mac_in_op1(mac_inst_io_mac_in_op1),
    .io_mac_in_op2(mac_inst_io_mac_in_op2),
    .io_mac_in_bias(mac_inst_io_mac_in_bias),
    .io_mac_out(mac_inst_io_mac_out)
  );
  SigmoidLut sigmoid_inst ( // @[neuron.scala 18:30]
    .clock(sigmoid_inst_clock),
    .io_addr(sigmoid_inst_io_addr),
    .io_dataOut(sigmoid_inst_io_dataOut)
  );
  assign io_neuron_out = {{6'd0}, _io_neuron_out_T}; // @[neuron.scala 25:19]
  assign mac_inst_clock = clock;
  assign mac_inst_io_mac_in_rst = io_neuron_in_rst; // @[neuron.scala 21:24]
  assign mac_inst_io_mac_in_op1 = io_neuron_in_op1; // @[neuron.scala 21:24]
  assign mac_inst_io_mac_in_op2 = io_neuron_in_op2; // @[neuron.scala 21:24]
  assign mac_inst_io_mac_in_bias = io_neuron_in_bias; // @[neuron.scala 21:24]
  assign sigmoid_inst_clock = clock;
  assign sigmoid_inst_io_addr = mac_inst_io_mac_out[26:17]; // @[neuron.scala 23:48]
endmodule
