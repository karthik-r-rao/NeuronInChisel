module AddressGenerator(
  input         clock,
  input         reset,
  input  [1:0]  io_address_generator_in_memoryUnits_0,
  input  [1:0]  io_address_generator_in_memoryUnits_1,
  input  [1:0]  io_address_generator_in_memoryUnits_2,
  input  [1:0]  io_address_generator_in_memoryUnits_3,
  input  [15:0] io_address_generator_in_max_iteration,
  input         io_address_generator_in_address_valid,
  input         io_address_generator_in_enable_valid,
  input         io_address_generator_in_reset,
  output [15:0] io_address_generator_out_Address,
  output [1:0]  io_address_generator_out_read_enables_0,
  output [1:0]  io_address_generator_out_read_enables_1,
  output [1:0]  io_address_generator_out_read_enables_2,
  output [1:0]  io_address_generator_out_read_enables_3,
  output        io_address_generator_out_bias_valid,
  output        io_address_generator_out_neuron_reset,
  output        io_address_generator_out_address_generation_complete
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
  reg [31:0] _RAND_1;
`endif // RANDOMIZE_REG_INIT
  reg [15:0] Address; // @[addressGenerator.scala 40:26]
  reg  address_generation_complete; // @[addressGenerator.scala 42:46]
  wire [15:0] _T_1 = io_address_generator_in_max_iteration - 16'h1; // @[addressGenerator.scala 70:68]
  wire [15:0] _Address_T_1 = Address + 16'h1; // @[addressGenerator.scala 74:32]
  wire  _T_5 = Address == _T_1; // @[addressGenerator.scala 77:29]
  wire  _GEN_4 = Address == io_address_generator_in_max_iteration ? 1'h0 : address_generation_complete; // @[addressGenerator.scala 84:71 85:41 42:46]
  wire  _GEN_7 = Address == _T_1 ? 1'h0 : 1'h1; // @[addressGenerator.scala 77:77 78:51]
  wire [15:0] _GEN_9 = Address == _T_1 ? _Address_T_1 : Address; // @[addressGenerator.scala 77:77 81:21 40:26]
  wire  _GEN_10 = Address == _T_1 | _GEN_4; // @[addressGenerator.scala 77:77 82:41]
  wire  _GEN_11 = Address < _T_1 ? 1'h0 : _GEN_7; // @[addressGenerator.scala 70:73 71:51]
  wire  _GEN_12 = Address < _T_1 ? 1'h0 : _T_5; // @[addressGenerator.scala 70:73 72:49]
  wire  _GEN_15 = io_address_generator_in_reset | _GEN_11; // @[addressGenerator.scala 63:44 64:51]
  wire  _GEN_16 = io_address_generator_in_reset ? 1'h0 : _GEN_12; // @[addressGenerator.scala 63:44 65:49]
  assign io_address_generator_out_Address = Address; // @[addressGenerator.scala 94:38]
  assign io_address_generator_out_read_enables_0 = io_address_generator_in_enable_valid ?
    io_address_generator_in_memoryUnits_0 : 2'h0; // @[addressGenerator.scala 47:47 50:24 56:24]
  assign io_address_generator_out_read_enables_1 = io_address_generator_in_enable_valid ?
    io_address_generator_in_memoryUnits_1 : 2'h0; // @[addressGenerator.scala 47:47 50:24 56:24]
  assign io_address_generator_out_read_enables_2 = io_address_generator_in_enable_valid ?
    io_address_generator_in_memoryUnits_2 : 2'h0; // @[addressGenerator.scala 47:47 50:24 56:24]
  assign io_address_generator_out_read_enables_3 = io_address_generator_in_enable_valid ?
    io_address_generator_in_memoryUnits_3 : 2'h0; // @[addressGenerator.scala 47:47 50:24 56:24]
  assign io_address_generator_out_bias_valid = io_address_generator_in_address_valid & _GEN_16; // @[addressGenerator.scala 45:41 62:48]
  assign io_address_generator_out_neuron_reset = io_address_generator_in_address_valid ? _GEN_15 : 1'h1; // @[addressGenerator.scala 44:43 62:48]
  assign io_address_generator_out_address_generation_complete = address_generation_complete; // @[addressGenerator.scala 95:58]
  always @(posedge clock) begin
    if (reset) begin // @[addressGenerator.scala 40:26]
      Address <= 16'h0; // @[addressGenerator.scala 40:26]
    end else if (io_address_generator_in_address_valid) begin // @[addressGenerator.scala 62:48]
      if (io_address_generator_in_reset) begin // @[addressGenerator.scala 63:44]
        Address <= 16'h0; // @[addressGenerator.scala 67:21]
      end else if (Address < _T_1) begin // @[addressGenerator.scala 70:73]
        Address <= _Address_T_1; // @[addressGenerator.scala 74:21]
      end else begin
        Address <= _GEN_9;
      end
    end
    if (reset) begin // @[addressGenerator.scala 42:46]
      address_generation_complete <= 1'h0; // @[addressGenerator.scala 42:46]
    end else if (io_address_generator_in_address_valid) begin // @[addressGenerator.scala 62:48]
      if (io_address_generator_in_reset) begin // @[addressGenerator.scala 63:44]
        address_generation_complete <= 1'h0; // @[addressGenerator.scala 68:41]
      end else if (Address < _T_1) begin // @[addressGenerator.scala 70:73]
        address_generation_complete <= 1'h0; // @[addressGenerator.scala 75:41]
      end else begin
        address_generation_complete <= _GEN_10;
      end
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
  _RAND_0 = {1{`RANDOM}};
  Address = _RAND_0[15:0];
  _RAND_1 = {1{`RANDOM}};
  address_generation_complete = _RAND_1[0:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
