module WeightMemoryAccess(
  input         clock,
  input         reset,
  input         io_wrEna,
  input  [9:0]  io_Addr,
  input  [15:0] io_dataIn,
  output [15:0] io_rdData
);
`ifdef RANDOMIZE_MEM_INIT
  reg [31:0] _RAND_0;
`endif // RANDOMIZE_MEM_INIT
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_1;
  reg [31:0] _RAND_2;
`endif // RANDOMIZE_REG_INIT
  reg [15:0] mem [0:1023]; // @[weight_memory_access.scala 20:24]
  wire  mem_rdwrPort_r_en; // @[weight_memory_access.scala 20:24]
  wire [9:0] mem_rdwrPort_r_addr; // @[weight_memory_access.scala 20:24]
  wire [15:0] mem_rdwrPort_r_data; // @[weight_memory_access.scala 20:24]
  wire [15:0] mem_rdwrPort_w_data; // @[weight_memory_access.scala 20:24]
  wire [9:0] mem_rdwrPort_w_addr; // @[weight_memory_access.scala 20:24]
  wire  mem_rdwrPort_w_mask; // @[weight_memory_access.scala 20:24]
  wire  mem_rdwrPort_w_en; // @[weight_memory_access.scala 20:24]
  reg  mem_rdwrPort_r_en_pipe_0;
  reg [9:0] mem_rdwrPort_r_addr_pipe_0;
  assign mem_rdwrPort_r_en = mem_rdwrPort_r_en_pipe_0;
  assign mem_rdwrPort_r_addr = mem_rdwrPort_r_addr_pipe_0;
  assign mem_rdwrPort_r_data = mem[mem_rdwrPort_r_addr]; // @[weight_memory_access.scala 20:24]
  assign mem_rdwrPort_w_data = io_dataIn;
  assign mem_rdwrPort_w_addr = io_Addr;
  assign mem_rdwrPort_w_mask = io_wrEna;
  assign mem_rdwrPort_w_en = 1'h1 & io_wrEna;
  assign io_rdData = io_wrEna ? 16'h0 : mem_rdwrPort_r_data; // @[weight_memory_access.scala 22:13 26:19 27:31]
  always @(posedge clock) begin
    if (mem_rdwrPort_w_en & mem_rdwrPort_w_mask) begin
      mem[mem_rdwrPort_w_addr] <= mem_rdwrPort_w_data; // @[weight_memory_access.scala 20:24]
    end
    mem_rdwrPort_r_en_pipe_0 <= 1'h1 & ~io_wrEna;
    if (1'h1 & ~io_wrEna) begin
      mem_rdwrPort_r_addr_pipe_0 <= io_Addr;
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
    mem[initvar] = _RAND_0[15:0];
`endif // RANDOMIZE_MEM_INIT
`ifdef RANDOMIZE_REG_INIT
  _RAND_1 = {1{`RANDOM}};
  mem_rdwrPort_r_en_pipe_0 = _RAND_1[0:0];
  _RAND_2 = {1{`RANDOM}};
  mem_rdwrPort_r_addr_pipe_0 = _RAND_2[9:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
