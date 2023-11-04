module SavePEOutput(
  input         clock,
  input         reset,
  input         io_save_pe_output_in_save_data_request,
  input  [15:0] io_save_pe_output_in_current_buffer_memory_pointer,
  input  [15:0] io_save_pe_output_in_PE_outputs_0,
  input  [15:0] io_save_pe_output_in_PE_outputs_1,
  input  [15:0] io_save_pe_output_in_PE_outputs_2,
  input  [15:0] io_save_pe_output_in_PE_outputs_3,
  output        io_save_pe_output_out_save_data_complete,
  output        io_save_pe_output_out_buffer_memory_write_enable,
  output [15:0] io_save_pe_output_out_buffer_memory_write_address,
  output [15:0] io_save_pe_output_out_buffer_memory_write_data
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
  reg [31:0] _RAND_1;
  reg [31:0] _RAND_2;
  reg [31:0] _RAND_3;
  reg [31:0] _RAND_4;
`endif // RANDOMIZE_REG_INIT
  reg [15:0] current_save_stage; // @[save_pe_outputs.scala 35:37]
  reg [1:0] curr_state; // @[save_pe_outputs.scala 38:29]
  reg  save_data_complete; // @[save_pe_outputs.scala 40:37]
  reg [15:0] buffer_memory_pointer; // @[save_pe_outputs.scala 41:40]
  reg [15:0] max_iter; // @[save_pe_outputs.scala 42:27]
  wire [15:0] _max_iter_T_1 = 16'h4 - 16'h1; // @[save_pe_outputs.scala 56:51]
  wire [15:0] _current_save_stage_T_1 = current_save_stage + 16'h1; // @[save_pe_outputs.scala 66:54]
  wire [15:0] _io_save_pe_output_out_buffer_memory_write_address_T_1 = buffer_memory_pointer + current_save_stage; // @[save_pe_outputs.scala 68:88]
  wire [15:0] _GEN_3 = 2'h1 == current_save_stage[1:0] ? io_save_pe_output_in_PE_outputs_1 :
    io_save_pe_output_in_PE_outputs_0; // @[save_pe_outputs.scala 69:{60,60}]
  wire [15:0] _GEN_4 = 2'h2 == current_save_stage[1:0] ? io_save_pe_output_in_PE_outputs_2 : _GEN_3; // @[save_pe_outputs.scala 69:{60,60}]
  wire [15:0] _GEN_5 = 2'h3 == current_save_stage[1:0] ? io_save_pe_output_in_PE_outputs_3 : _GEN_4; // @[save_pe_outputs.scala 69:{60,60}]
  wire  _GEN_6 = current_save_stage == max_iter | save_data_complete; // @[save_pe_outputs.scala 71:50 72:36 40:37]
  wire [1:0] _GEN_8 = ~io_save_pe_output_in_save_data_request ? 2'h0 : curr_state; // @[save_pe_outputs.scala 84:69 85:28 38:29]
  wire [15:0] _GEN_15 = 2'h1 == curr_state ? _io_save_pe_output_out_buffer_memory_write_address_T_1 : 16'h0; // @[save_pe_outputs.scala 48:23 68:63]
  wire [15:0] _GEN_16 = 2'h1 == curr_state ? _GEN_5 : 16'h0; // @[save_pe_outputs.scala 48:23 69:60]
  assign io_save_pe_output_out_save_data_complete = save_data_complete; // @[save_pe_outputs.scala 90:46]
  assign io_save_pe_output_out_buffer_memory_write_enable = 2'h0 == curr_state ? 1'h0 : 2'h1 == curr_state; // @[save_pe_outputs.scala 48:23 50:62]
  assign io_save_pe_output_out_buffer_memory_write_address = 2'h0 == curr_state ? 16'h0 : _GEN_15; // @[save_pe_outputs.scala 48:23 51:63]
  assign io_save_pe_output_out_buffer_memory_write_data = 2'h0 == curr_state ? 16'h0 : _GEN_16; // @[save_pe_outputs.scala 48:23 52:60]
  always @(posedge clock) begin
    if (reset) begin // @[save_pe_outputs.scala 35:37]
      current_save_stage <= 16'h0; // @[save_pe_outputs.scala 35:37]
    end else if (2'h0 == curr_state) begin // @[save_pe_outputs.scala 48:23]
      current_save_stage <= 16'h0; // @[save_pe_outputs.scala 55:32]
    end else if (2'h1 == curr_state) begin // @[save_pe_outputs.scala 48:23]
      current_save_stage <= _current_save_stage_T_1; // @[save_pe_outputs.scala 66:32]
    end
    if (reset) begin // @[save_pe_outputs.scala 38:29]
      curr_state <= 2'h0; // @[save_pe_outputs.scala 38:29]
    end else if (2'h0 == curr_state) begin // @[save_pe_outputs.scala 48:23]
      if (io_save_pe_output_in_save_data_request) begin // @[save_pe_outputs.scala 58:57]
        curr_state <= 2'h1; // @[save_pe_outputs.scala 59:28]
      end
    end else if (2'h1 == curr_state) begin // @[save_pe_outputs.scala 48:23]
      if (current_save_stage == max_iter) begin // @[save_pe_outputs.scala 71:50]
        curr_state <= 2'h2; // @[save_pe_outputs.scala 73:28]
      end
    end else if (2'h2 == curr_state) begin // @[save_pe_outputs.scala 48:23]
      curr_state <= _GEN_8;
    end
    if (reset) begin // @[save_pe_outputs.scala 40:37]
      save_data_complete <= 1'h0; // @[save_pe_outputs.scala 40:37]
    end else if (2'h0 == curr_state) begin // @[save_pe_outputs.scala 48:23]
      save_data_complete <= 1'h0; // @[save_pe_outputs.scala 54:32]
    end else if (2'h1 == curr_state) begin // @[save_pe_outputs.scala 48:23]
      save_data_complete <= _GEN_6;
    end else if (2'h2 == curr_state) begin // @[save_pe_outputs.scala 48:23]
      save_data_complete <= 1'h0; // @[save_pe_outputs.scala 82:32]
    end
    if (reset) begin // @[save_pe_outputs.scala 41:40]
      buffer_memory_pointer <= 16'h0; // @[save_pe_outputs.scala 41:40]
    end else if (2'h0 == curr_state) begin // @[save_pe_outputs.scala 48:23]
      if (io_save_pe_output_in_save_data_request) begin // @[save_pe_outputs.scala 58:57]
        buffer_memory_pointer <= io_save_pe_output_in_current_buffer_memory_pointer; // @[save_pe_outputs.scala 60:39]
      end
    end
    if (reset) begin // @[save_pe_outputs.scala 42:27]
      max_iter <= 16'h0; // @[save_pe_outputs.scala 42:27]
    end else if (2'h0 == curr_state) begin // @[save_pe_outputs.scala 48:23]
      max_iter <= _max_iter_T_1; // @[save_pe_outputs.scala 56:22]
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
  current_save_stage = _RAND_0[15:0];
  _RAND_1 = {1{`RANDOM}};
  curr_state = _RAND_1[1:0];
  _RAND_2 = {1{`RANDOM}};
  save_data_complete = _RAND_2[0:0];
  _RAND_3 = {1{`RANDOM}};
  buffer_memory_pointer = _RAND_3[15:0];
  _RAND_4 = {1{`RANDOM}};
  max_iter = _RAND_4[15:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
