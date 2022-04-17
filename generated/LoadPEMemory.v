module LoadPEMemory(
  input         clock,
  input         reset,
  input         io_load_pe_memory_in_load_initial_weights,
  input         io_load_pe_memory_in_load_datapoint,
  input         io_load_pe_memory_in_load_new_request,
  input         io_load_pe_memory_in_load_data_from_buffer,
  input         io_load_pe_memory_in_load_same_data,
  input  [15:0] io_load_pe_memory_in_loading_layer,
  input  [15:0] io_load_pe_memory_in_loading_length,
  input  [15:0] io_load_pe_memory_in_loading_activations_0,
  input  [15:0] io_load_pe_memory_in_loading_activations_1,
  input  [15:0] io_load_pe_memory_in_loading_activations_2,
  input  [15:0] io_load_pe_memory_in_loading_activations_3,
  input  [1:0]  io_load_pe_memory_in_write_memoryUnits_0,
  input  [1:0]  io_load_pe_memory_in_write_memoryUnits_1,
  input  [1:0]  io_load_pe_memory_in_write_memoryUnits_2,
  input  [1:0]  io_load_pe_memory_in_write_memoryUnits_3,
  input         io_load_pe_memory_in_weight_buffer_load_request,
  input  [15:0] io_load_pe_memory_in_buffer_memory_output,
  input         io_load_pe_memory_in_new_datapoint_ready,
  input         io_load_pe_memory_in_interconnect_load_ready,
  input  [15:0] io_load_pe_memory_in_interconnect_memory_output,
  output        io_load_pe_memory_out_load_initial_weights_complete,
  output        io_load_pe_memory_out_load_datapoint_complete,
  output        io_load_pe_memory_out_load_buffer_weight_memory_complete,
  output        io_load_pe_memory_out_datapoint_memory_write_enable,
  output [15:0] io_load_pe_memory_out_datapoint_memory_write_data,
  output [15:0] io_load_pe_memory_out_datapoint_memory_write_address,
  output        io_load_pe_memory_out_buffer_memory_write_enable,
  output [15:0] io_load_pe_memory_out_buffer_memory_write_data,
  output [15:0] io_load_pe_memory_out_buffer_memory_write_address,
  output [1:0]  io_load_pe_memory_out_write_memoryUnits_0,
  output [1:0]  io_load_pe_memory_out_write_memoryUnits_1,
  output [1:0]  io_load_pe_memory_out_write_memoryUnits_2,
  output [1:0]  io_load_pe_memory_out_write_memoryUnits_3,
  output [15:0] io_load_pe_memory_out_weight_buffer_memory_write_data,
  output [15:0] io_load_pe_memory_out_weight_buffer_memory_write_address,
  output [15:0] io_load_pe_memory_out_interconnect_loading_layer,
  output [15:0] io_load_pe_memory_out_interconnect_loading_activation,
  output        io_load_pe_memory_out_interconnect_load_request,
  output [15:0] io_load_pe_memory_out_interconnect_load_read_address,
  output        io_load_pe_memory_out_load_new_data_request,
  output [15:0] io_load_pe_memory_out_current_load_weights_state,
  output [15:0] io_load_pe_memory_out_current_load_datapoint_state,
  output [15:0] io_load_pe_memory_out_current_pe
);
`ifdef RANDOMIZE_REG_INIT
  reg [31:0] _RAND_0;
  reg [31:0] _RAND_1;
  reg [31:0] _RAND_2;
  reg [31:0] _RAND_3;
  reg [31:0] _RAND_4;
  reg [31:0] _RAND_5;
  reg [31:0] _RAND_6;
  reg [31:0] _RAND_7;
  reg [31:0] _RAND_8;
  reg [31:0] _RAND_9;
  reg [31:0] _RAND_10;
  reg [31:0] _RAND_11;
  reg [31:0] _RAND_12;
`endif // RANDOMIZE_REG_INIT
  reg [1:0] copy_buffer_state; // @[loadPEMemory.scala 107:36]
  reg [15:0] current_copy_address2; // @[loadPEMemory.scala 108:40]
  reg [15:0] current_save_address2; // @[loadPEMemory.scala 109:40]
  reg [15:0] loading_length; // @[loadPEMemory.scala 110:33]
  reg  load_datapoint_complete; // @[loadPEMemory.scala 111:42]
  reg [15:0] current_copy_address; // @[loadPEMemory.scala 115:39]
  reg [15:0] current_save_address; // @[loadPEMemory.scala 116:39]
  reg [1:0] current_state; // @[loadPEMemory.scala 117:32]
  reg [15:0] current_pe; // @[loadPEMemory.scala 119:29]
  reg [15:0] previous_pe; // @[loadPEMemory.scala 121:30]
  reg  load_initial_weights_complete; // @[loadPEMemory.scala 123:48]
  reg  load_buffer_weight_memory_complete; // @[loadPEMemory.scala 124:53]
  reg [15:0] loading_layer; // @[loadPEMemory.scala 125:32]
  wire [15:0] _GEN_1 = io_load_pe_memory_in_load_initial_weights | io_load_pe_memory_in_weight_buffer_load_request ?
    io_load_pe_memory_in_loading_length : loading_length; // @[loadPEMemory.scala 149:115 110:33 151:36]
  wire [15:0] _next_pe_T_1 = current_pe + 16'h1; // @[loadPEMemory.scala 159:39]
  wire [1:0] _GEN_4 = 2'h1 == current_pe[1:0] ? io_load_pe_memory_in_write_memoryUnits_1 :
    io_load_pe_memory_in_write_memoryUnits_0; // @[loadPEMemory.scala 174:{73,73}]
  wire [1:0] _GEN_5 = 2'h2 == current_pe[1:0] ? io_load_pe_memory_in_write_memoryUnits_2 : _GEN_4; // @[loadPEMemory.scala 174:{73,73}]
  wire [1:0] _GEN_6 = 2'h3 == current_pe[1:0] ? io_load_pe_memory_in_write_memoryUnits_3 : _GEN_5; // @[loadPEMemory.scala 174:{73,73}]
  wire  _GEN_7 = io_load_pe_memory_in_load_initial_weights | load_initial_weights_complete; // @[loadPEMemory.scala 123:48 176:68 177:55]
  wire  _GEN_8 = io_load_pe_memory_in_load_initial_weights ? load_buffer_weight_memory_complete : 1'h1; // @[loadPEMemory.scala 124:53 176:68 179:60]
  wire [15:0] _GEN_10 = 2'h1 == current_pe[1:0] ? io_load_pe_memory_in_loading_activations_1 :
    io_load_pe_memory_in_loading_activations_0; // @[loadPEMemory.scala 189:{79,79}]
  wire [15:0] _GEN_11 = 2'h2 == current_pe[1:0] ? io_load_pe_memory_in_loading_activations_2 : _GEN_10; // @[loadPEMemory.scala 189:{79,79}]
  wire [15:0] _GEN_12 = 2'h3 == current_pe[1:0] ? io_load_pe_memory_in_loading_activations_3 : _GEN_11; // @[loadPEMemory.scala 189:{79,79}]
  wire [15:0] _GEN_13 = io_load_pe_memory_in_load_initial_weights ? 16'h2 : loading_layer; // @[loadPEMemory.scala 183:68 184:74 188:74]
  wire [15:0] _GEN_63 = 2'h1 == current_state ? _next_pe_T_1 : 16'h0; // @[loadPEMemory.scala 132:30 159:25]
  wire [15:0] next_pe = 2'h0 == current_state ? 16'h0 : _GEN_63; // @[loadPEMemory.scala 132:30]
  wire [15:0] _GEN_14 = io_load_pe_memory_in_load_initial_weights ? next_pe : _GEN_12; // @[loadPEMemory.scala 183:68 185:79 189:79]
  wire [1:0] _GEN_16 = io_load_pe_memory_in_interconnect_load_ready ? 2'h2 : current_state; // @[loadPEMemory.scala 117:32 193:71 194:39]
  wire [15:0] _GEN_17 = io_load_pe_memory_in_interconnect_load_ready ? _next_pe_T_1 : current_pe; // @[loadPEMemory.scala 119:29 193:71 196:36]
  wire [15:0] _GEN_18 = io_load_pe_memory_in_interconnect_load_ready ? current_pe : previous_pe; // @[loadPEMemory.scala 121:30 193:71 197:37]
  wire [15:0] _GEN_19 = io_load_pe_memory_in_interconnect_load_ready ? 16'h0 : _GEN_13; // @[loadPEMemory.scala 193:71 199:74]
  wire [15:0] _GEN_20 = io_load_pe_memory_in_interconnect_load_ready ? 16'h0 : _GEN_14; // @[loadPEMemory.scala 193:71 200:79]
  wire  _GEN_21 = io_load_pe_memory_in_interconnect_load_ready ? 1'h0 : 1'h1; // @[loadPEMemory.scala 193:71 201:73]
  wire [15:0] _GEN_25 = _GEN_6 == 2'h0 ? 16'h0 : _GEN_19; // @[loadPEMemory.scala 174:86 99:54]
  wire [15:0] _GEN_26 = _GEN_6 == 2'h0 ? 16'h0 : _GEN_20; // @[loadPEMemory.scala 100:59 174:86]
  wire  _GEN_27 = _GEN_6 == 2'h0 ? 1'h0 : _GEN_21; // @[loadPEMemory.scala 101:53 174:86]
  wire [1:0] _GEN_35 = 2'h1 == previous_pe[1:0] ? io_load_pe_memory_in_write_memoryUnits_1 :
    io_load_pe_memory_in_write_memoryUnits_0; // @[loadPEMemory.scala 214:{70,70}]
  wire [1:0] _GEN_36 = 2'h2 == previous_pe[1:0] ? io_load_pe_memory_in_write_memoryUnits_2 : _GEN_35; // @[loadPEMemory.scala 214:{70,70}]
  wire [1:0] _GEN_37 = 2'h3 == previous_pe[1:0] ? io_load_pe_memory_in_write_memoryUnits_3 : _GEN_36; // @[loadPEMemory.scala 214:{70,70}]
  wire [1:0] _GEN_30 = 2'h0 == previous_pe[1:0] ? _GEN_37 : 2'h0; // @[loadPEMemory.scala 214:{70,70} 93:52]
  wire [1:0] _GEN_31 = 2'h1 == previous_pe[1:0] ? _GEN_37 : 2'h0; // @[loadPEMemory.scala 214:{70,70} 93:52]
  wire [1:0] _GEN_32 = 2'h2 == previous_pe[1:0] ? _GEN_37 : 2'h0; // @[loadPEMemory.scala 214:{70,70} 93:52]
  wire [1:0] _GEN_33 = 2'h3 == previous_pe[1:0] ? _GEN_37 : 2'h0; // @[loadPEMemory.scala 214:{70,70} 93:52]
  wire [15:0] _current_copy_address_T_1 = current_copy_address + 16'h1; // @[loadPEMemory.scala 218:62]
  wire  _T_7 = current_copy_address == loading_length; // @[loadPEMemory.scala 222:43]
  wire  _T_8 = current_pe == 16'h4; // @[loadPEMemory.scala 223:37]
  wire  _GEN_38 = _T_8 ? _GEN_7 : load_initial_weights_complete; // @[loadPEMemory.scala 224:21 123:48]
  wire  _GEN_39 = _T_8 ? _GEN_8 : load_buffer_weight_memory_complete; // @[loadPEMemory.scala 224:21 124:53]
  wire [1:0] _GEN_40 = _T_8 ? 2'h3 : 2'h1; // @[loadPEMemory.scala 224:21 230:39 233:39]
  wire  _GEN_41 = _T_7 ? _GEN_38 : load_initial_weights_complete; // @[loadPEMemory.scala 223:17 123:48]
  wire  _GEN_42 = _T_7 ? _GEN_39 : load_buffer_weight_memory_complete; // @[loadPEMemory.scala 223:17 124:53]
  wire [1:0] _GEN_43 = _T_7 ? _GEN_40 : 2'h2; // @[loadPEMemory.scala 223:17 236:35]
  wire [1:0] _GEN_44 = ~io_load_pe_memory_in_load_initial_weights & ~io_load_pe_memory_in_weight_buffer_load_request ? 2'h0
     : current_state; // @[loadPEMemory.scala 250:144 117:32 251:35]
  wire [15:0] _GEN_45 = 2'h3 == current_state ? 16'h3 : 16'h0; // @[loadPEMemory.scala 132:30 127:54 244:66]
  wire  _GEN_46 = 2'h3 == current_state ? 1'h0 : load_initial_weights_complete; // @[loadPEMemory.scala 132:30 247:47 123:48]
  wire  _GEN_47 = 2'h3 == current_state ? 1'h0 : load_buffer_weight_memory_complete; // @[loadPEMemory.scala 132:30 248:52 124:53]
  wire [1:0] _GEN_48 = 2'h3 == current_state ? _GEN_44 : current_state; // @[loadPEMemory.scala 132:30 117:32]
  wire [15:0] _GEN_49 = 2'h2 == current_state ? 16'h2 : _GEN_45; // @[loadPEMemory.scala 132:30 209:66]
  wire [15:0] _GEN_50 = 2'h2 == current_state ? current_copy_address : 16'h0; // @[loadPEMemory.scala 132:30 103:58 212:70]
  wire [15:0] _GEN_51 = 2'h2 == current_state ? current_save_address : 16'h0; // @[loadPEMemory.scala 132:30 213:74 96:62]
  wire [1:0] _GEN_52 = 2'h2 == current_state ? _GEN_30 : 2'h0; // @[loadPEMemory.scala 132:30 93:52]
  wire [1:0] _GEN_53 = 2'h2 == current_state ? _GEN_31 : 2'h0; // @[loadPEMemory.scala 132:30 93:52]
  wire [1:0] _GEN_54 = 2'h2 == current_state ? _GEN_32 : 2'h0; // @[loadPEMemory.scala 132:30 93:52]
  wire [1:0] _GEN_55 = 2'h2 == current_state ? _GEN_33 : 2'h0; // @[loadPEMemory.scala 132:30 93:52]
  wire [15:0] _GEN_56 = 2'h2 == current_state ? io_load_pe_memory_in_interconnect_memory_output : 16'h0; // @[loadPEMemory.scala 132:30 216:71 97:59]
  wire [15:0] _GEN_62 = 2'h1 == current_state ? 16'h1 : _GEN_49; // @[loadPEMemory.scala 132:30 157:66]
  wire [15:0] _GEN_66 = 2'h1 == current_state ? 16'h0 : _GEN_50; // @[loadPEMemory.scala 132:30 165:70]
  wire [15:0] _GEN_67 = 2'h1 == current_state ? 16'h0 : _GEN_51; // @[loadPEMemory.scala 132:30 167:74]
  wire [1:0] _GEN_68 = 2'h1 == current_state ? 2'h0 : _GEN_52; // @[loadPEMemory.scala 132:30 169:68]
  wire [1:0] _GEN_69 = 2'h1 == current_state ? 2'h0 : _GEN_53; // @[loadPEMemory.scala 132:30 169:68]
  wire [1:0] _GEN_70 = 2'h1 == current_state ? 2'h0 : _GEN_54; // @[loadPEMemory.scala 132:30 169:68]
  wire [1:0] _GEN_71 = 2'h1 == current_state ? 2'h0 : _GEN_55; // @[loadPEMemory.scala 132:30 169:68]
  wire [15:0] _GEN_72 = 2'h1 == current_state ? 16'h0 : _GEN_56; // @[loadPEMemory.scala 132:30 171:71]
  wire [15:0] _GEN_76 = 2'h1 == current_state ? _GEN_25 : 16'h0; // @[loadPEMemory.scala 132:30 99:54]
  wire [15:0] _GEN_77 = 2'h1 == current_state ? _GEN_26 : 16'h0; // @[loadPEMemory.scala 132:30 100:59]
  wire  _GEN_78 = 2'h1 == current_state & _GEN_27; // @[loadPEMemory.scala 132:30 101:53]
  wire [15:0] _GEN_88 = 2'h0 == current_state ? 16'h0 : _GEN_66; // @[loadPEMemory.scala 132:30 146:70]
  wire [15:0] _GEN_90 = 2'h0 == current_state ? _GEN_1 : loading_length; // @[loadPEMemory.scala 132:30 110:33]
  wire  _T_13 = io_load_pe_memory_in_load_new_request & io_load_pe_memory_in_load_datapoint; // @[loadPEMemory.scala 258:52]
  wire [15:0] _GEN_105 = io_load_pe_memory_in_load_data_from_buffer ? current_copy_address2 : 16'h0; // @[loadPEMemory.scala 288:69 289:75 89:55]
  wire [15:0] _GEN_106 = io_load_pe_memory_in_load_data_from_buffer ? io_load_pe_memory_in_buffer_memory_output :
    io_load_pe_memory_in_interconnect_memory_output; // @[loadPEMemory.scala 288:69 290:75 293:75]
  wire [15:0] _GEN_107 = io_load_pe_memory_in_load_data_from_buffer ? _GEN_88 : current_copy_address2; // @[loadPEMemory.scala 288:69 292:78]
  wire [15:0] _current_copy_address2_T_1 = current_copy_address2 + 16'h1; // @[loadPEMemory.scala 299:68]
  wire  _GEN_108 = current_copy_address2 == loading_length | load_datapoint_complete; // @[loadPEMemory.scala 111:42 302:67 303:49]
  wire  _T_19 = ~io_load_pe_memory_in_load_datapoint; // @[loadPEMemory.scala 319:62]
  wire [1:0] _GEN_110 = ~io_load_pe_memory_in_load_datapoint ? 2'h0 : copy_buffer_state; // @[loadPEMemory.scala 107:36 319:74 320:43]
  wire [15:0] _GEN_111 = 2'h2 == copy_buffer_state ? 16'h2 : 16'h0; // @[loadPEMemory.scala 263:38 128:56 309:72]
  wire  _GEN_112 = 2'h2 == copy_buffer_state ? 1'h0 : load_datapoint_complete; // @[loadPEMemory.scala 263:38 111:42 311:45]
  wire [15:0] _GEN_113 = 2'h2 == copy_buffer_state ? 16'h0 : _GEN_88; // @[loadPEMemory.scala 263:38 313:74]
  wire  _GEN_117 = 2'h1 == copy_buffer_state ? 1'h0 : _T_13; // @[loadPEMemory.scala 263:38 285:65]
  wire [15:0] _GEN_118 = 2'h1 == copy_buffer_state ? 16'h1 : _GEN_111; // @[loadPEMemory.scala 263:38 286:72]
  wire [15:0] _GEN_119 = 2'h1 == copy_buffer_state ? _GEN_105 : 16'h0; // @[loadPEMemory.scala 263:38]
  wire [15:0] _GEN_120 = 2'h1 == copy_buffer_state ? _GEN_106 : 16'h0; // @[loadPEMemory.scala 263:38 85:55]
  wire [15:0] _GEN_121 = 2'h1 == copy_buffer_state ? _GEN_107 : _GEN_113; // @[loadPEMemory.scala 263:38]
  wire [15:0] _GEN_122 = 2'h1 == copy_buffer_state ? current_save_address2 : 16'h0; // @[loadPEMemory.scala 263:38 296:74]
  wire  _GEN_126 = 2'h1 == copy_buffer_state ? _GEN_108 : _GEN_112; // @[loadPEMemory.scala 263:38]
  wire  _GEN_139 = 2'h0 == copy_buffer_state ? load_datapoint_complete : _GEN_126; // @[loadPEMemory.scala 263:38 111:42]
  wire  _GEN_140 = io_load_pe_memory_in_load_same_data & io_load_pe_memory_in_load_datapoint | _GEN_139; // @[loadPEMemory.scala 327:89 328:37]
  assign io_load_pe_memory_out_load_initial_weights_complete = load_initial_weights_complete; // @[loadPEMemory.scala 339:53]
  assign io_load_pe_memory_out_load_datapoint_complete = load_datapoint_complete; // @[loadPEMemory.scala 338:47]
  assign io_load_pe_memory_out_load_buffer_weight_memory_complete = load_buffer_weight_memory_complete; // @[loadPEMemory.scala 340:58]
  assign io_load_pe_memory_out_datapoint_memory_write_enable = 2'h0 == copy_buffer_state ? 1'h0 : 2'h1 ==
    copy_buffer_state; // @[loadPEMemory.scala 263:38 273:73]
  assign io_load_pe_memory_out_datapoint_memory_write_data = 2'h0 == copy_buffer_state ? 16'h0 : _GEN_120; // @[loadPEMemory.scala 263:38 275:71]
  assign io_load_pe_memory_out_datapoint_memory_write_address = 2'h0 == copy_buffer_state ? 16'h0 : _GEN_122; // @[loadPEMemory.scala 263:38 272:74]
  assign io_load_pe_memory_out_buffer_memory_write_enable = 1'h0; // @[loadPEMemory.scala 88:54]
  assign io_load_pe_memory_out_buffer_memory_write_data = 16'h0; // @[loadPEMemory.scala 90:52]
  assign io_load_pe_memory_out_buffer_memory_write_address = 2'h0 == copy_buffer_state ? 16'h0 : _GEN_119; // @[loadPEMemory.scala 263:38 271:71]
  assign io_load_pe_memory_out_write_memoryUnits_0 = 2'h0 == current_state ? 2'h0 : _GEN_68; // @[loadPEMemory.scala 132:30 93:52]
  assign io_load_pe_memory_out_write_memoryUnits_1 = 2'h0 == current_state ? 2'h0 : _GEN_69; // @[loadPEMemory.scala 132:30 93:52]
  assign io_load_pe_memory_out_write_memoryUnits_2 = 2'h0 == current_state ? 2'h0 : _GEN_70; // @[loadPEMemory.scala 132:30 93:52]
  assign io_load_pe_memory_out_write_memoryUnits_3 = 2'h0 == current_state ? 2'h0 : _GEN_71; // @[loadPEMemory.scala 132:30 93:52]
  assign io_load_pe_memory_out_weight_buffer_memory_write_data = 2'h0 == current_state ? 16'h0 : _GEN_72; // @[loadPEMemory.scala 132:30 97:59]
  assign io_load_pe_memory_out_weight_buffer_memory_write_address = 2'h0 == current_state ? 16'h0 : _GEN_67; // @[loadPEMemory.scala 132:30 96:62]
  assign io_load_pe_memory_out_interconnect_loading_layer = 2'h0 == current_state ? 16'h0 : _GEN_76; // @[loadPEMemory.scala 132:30 142:66]
  assign io_load_pe_memory_out_interconnect_loading_activation = 2'h0 == current_state ? 16'h0 : _GEN_77; // @[loadPEMemory.scala 132:30 143:71]
  assign io_load_pe_memory_out_interconnect_load_request = 2'h0 == current_state ? 1'h0 : _GEN_78; // @[loadPEMemory.scala 132:30 144:65]
  assign io_load_pe_memory_out_interconnect_load_read_address = 2'h0 == copy_buffer_state ? _GEN_88 : _GEN_121; // @[loadPEMemory.scala 263:38]
  assign io_load_pe_memory_out_load_new_data_request = 2'h0 == copy_buffer_state ? _T_13 : _GEN_117; // @[loadPEMemory.scala 263:38]
  assign io_load_pe_memory_out_current_load_weights_state = 2'h0 == current_state ? 16'h0 : _GEN_62; // @[loadPEMemory.scala 132:30 138:66]
  assign io_load_pe_memory_out_current_load_datapoint_state = 2'h0 == copy_buffer_state ? 16'h0 : _GEN_118; // @[loadPEMemory.scala 263:38 265:72]
  assign io_load_pe_memory_out_current_pe = current_pe; // @[loadPEMemory.scala 341:34]
  always @(posedge clock) begin
    if (reset) begin // @[loadPEMemory.scala 107:36]
      copy_buffer_state <= 2'h0; // @[loadPEMemory.scala 107:36]
    end else if (2'h0 == copy_buffer_state) begin // @[loadPEMemory.scala 263:38]
      if (io_load_pe_memory_in_load_data_from_buffer | io_load_pe_memory_in_new_datapoint_ready) begin // @[loadPEMemory.scala 277:113]
        copy_buffer_state <= 2'h1; // @[loadPEMemory.scala 279:43]
      end
    end else if (2'h1 == copy_buffer_state) begin // @[loadPEMemory.scala 263:38]
      if (current_copy_address2 == loading_length) begin // @[loadPEMemory.scala 302:67]
        copy_buffer_state <= 2'h2; // @[loadPEMemory.scala 304:43]
      end
    end else if (2'h2 == copy_buffer_state) begin // @[loadPEMemory.scala 263:38]
      copy_buffer_state <= _GEN_110;
    end
    if (reset) begin // @[loadPEMemory.scala 108:40]
      current_copy_address2 <= 16'h0; // @[loadPEMemory.scala 108:40]
    end else if (2'h0 == copy_buffer_state) begin // @[loadPEMemory.scala 263:38]
      current_copy_address2 <= 16'h0; // @[loadPEMemory.scala 268:43]
    end else if (2'h1 == copy_buffer_state) begin // @[loadPEMemory.scala 263:38]
      current_copy_address2 <= _current_copy_address2_T_1; // @[loadPEMemory.scala 299:43]
    end
    if (reset) begin // @[loadPEMemory.scala 109:40]
      current_save_address2 <= 16'h0; // @[loadPEMemory.scala 109:40]
    end else if (2'h0 == copy_buffer_state) begin // @[loadPEMemory.scala 263:38]
      current_save_address2 <= 16'h0; // @[loadPEMemory.scala 269:43]
    end else if (2'h1 == copy_buffer_state) begin // @[loadPEMemory.scala 263:38]
      current_save_address2 <= current_copy_address2; // @[loadPEMemory.scala 300:43]
    end
    if (reset) begin // @[loadPEMemory.scala 110:33]
      loading_length <= 16'h0; // @[loadPEMemory.scala 110:33]
    end else if (2'h0 == copy_buffer_state) begin // @[loadPEMemory.scala 263:38]
      if (io_load_pe_memory_in_load_data_from_buffer | io_load_pe_memory_in_new_datapoint_ready) begin // @[loadPEMemory.scala 277:113]
        loading_length <= io_load_pe_memory_in_loading_length; // @[loadPEMemory.scala 278:40]
      end else begin
        loading_length <= _GEN_90;
      end
    end else begin
      loading_length <= _GEN_90;
    end
    if (reset) begin // @[loadPEMemory.scala 111:42]
      load_datapoint_complete <= 1'h0; // @[loadPEMemory.scala 111:42]
    end else if (_T_19) begin // @[loadPEMemory.scala 330:62]
      load_datapoint_complete <= 1'h0; // @[loadPEMemory.scala 331:37]
    end else begin
      load_datapoint_complete <= _GEN_140;
    end
    if (reset) begin // @[loadPEMemory.scala 115:39]
      current_copy_address <= 16'h0; // @[loadPEMemory.scala 115:39]
    end else if (!(2'h0 == current_state)) begin // @[loadPEMemory.scala 132:30]
      if (2'h1 == current_state) begin // @[loadPEMemory.scala 132:30]
        current_copy_address <= 16'h0; // @[loadPEMemory.scala 161:38]
      end else if (2'h2 == current_state) begin // @[loadPEMemory.scala 132:30]
        current_copy_address <= _current_copy_address_T_1; // @[loadPEMemory.scala 218:38]
      end
    end
    if (reset) begin // @[loadPEMemory.scala 116:39]
      current_save_address <= 16'h0; // @[loadPEMemory.scala 116:39]
    end else if (!(2'h0 == current_state)) begin // @[loadPEMemory.scala 132:30]
      if (2'h1 == current_state) begin // @[loadPEMemory.scala 132:30]
        current_save_address <= 16'h0; // @[loadPEMemory.scala 162:38]
      end else if (2'h2 == current_state) begin // @[loadPEMemory.scala 132:30]
        current_save_address <= current_copy_address; // @[loadPEMemory.scala 219:38]
      end
    end
    if (reset) begin // @[loadPEMemory.scala 117:32]
      current_state <= 2'h0; // @[loadPEMemory.scala 117:32]
    end else if (2'h0 == current_state) begin // @[loadPEMemory.scala 132:30]
      if (io_load_pe_memory_in_load_initial_weights | io_load_pe_memory_in_weight_buffer_load_request) begin // @[loadPEMemory.scala 149:115]
        current_state <= 2'h1; // @[loadPEMemory.scala 152:35]
      end
    end else if (2'h1 == current_state) begin // @[loadPEMemory.scala 132:30]
      if (_GEN_6 == 2'h0) begin // @[loadPEMemory.scala 174:86]
        current_state <= 2'h3; // @[loadPEMemory.scala 175:35]
      end else begin
        current_state <= _GEN_16;
      end
    end else if (2'h2 == current_state) begin // @[loadPEMemory.scala 132:30]
      current_state <= _GEN_43;
    end else begin
      current_state <= _GEN_48;
    end
    if (reset) begin // @[loadPEMemory.scala 119:29]
      current_pe <= 16'h0; // @[loadPEMemory.scala 119:29]
    end else if (2'h0 == current_state) begin // @[loadPEMemory.scala 132:30]
      current_pe <= 16'h0; // @[loadPEMemory.scala 140:28]
    end else if (2'h1 == current_state) begin // @[loadPEMemory.scala 132:30]
      if (!(_GEN_6 == 2'h0)) begin // @[loadPEMemory.scala 174:86]
        current_pe <= _GEN_17;
      end
    end
    if (reset) begin // @[loadPEMemory.scala 121:30]
      previous_pe <= 16'h0; // @[loadPEMemory.scala 121:30]
    end else if (!(2'h0 == current_state)) begin // @[loadPEMemory.scala 132:30]
      if (2'h1 == current_state) begin // @[loadPEMemory.scala 132:30]
        if (!(_GEN_6 == 2'h0)) begin // @[loadPEMemory.scala 174:86]
          previous_pe <= _GEN_18;
        end
      end
    end
    if (reset) begin // @[loadPEMemory.scala 123:48]
      load_initial_weights_complete <= 1'h0; // @[loadPEMemory.scala 123:48]
    end else if (2'h0 == current_state) begin // @[loadPEMemory.scala 132:30]
      load_initial_weights_complete <= 1'h0; // @[loadPEMemory.scala 134:47]
    end else if (2'h1 == current_state) begin // @[loadPEMemory.scala 132:30]
      if (_GEN_6 == 2'h0) begin // @[loadPEMemory.scala 174:86]
        load_initial_weights_complete <= _GEN_7;
      end
    end else if (2'h2 == current_state) begin // @[loadPEMemory.scala 132:30]
      load_initial_weights_complete <= _GEN_41;
    end else begin
      load_initial_weights_complete <= _GEN_46;
    end
    if (reset) begin // @[loadPEMemory.scala 124:53]
      load_buffer_weight_memory_complete <= 1'h0; // @[loadPEMemory.scala 124:53]
    end else if (2'h0 == current_state) begin // @[loadPEMemory.scala 132:30]
      load_buffer_weight_memory_complete <= 1'h0; // @[loadPEMemory.scala 135:52]
    end else if (2'h1 == current_state) begin // @[loadPEMemory.scala 132:30]
      if (_GEN_6 == 2'h0) begin // @[loadPEMemory.scala 174:86]
        load_buffer_weight_memory_complete <= _GEN_8;
      end
    end else if (2'h2 == current_state) begin // @[loadPEMemory.scala 132:30]
      load_buffer_weight_memory_complete <= _GEN_42;
    end else begin
      load_buffer_weight_memory_complete <= _GEN_47;
    end
    if (reset) begin // @[loadPEMemory.scala 125:32]
      loading_layer <= 16'h0; // @[loadPEMemory.scala 125:32]
    end else if (2'h0 == current_state) begin // @[loadPEMemory.scala 132:30]
      if (io_load_pe_memory_in_load_initial_weights | io_load_pe_memory_in_weight_buffer_load_request) begin // @[loadPEMemory.scala 149:115]
        loading_layer <= io_load_pe_memory_in_loading_layer; // @[loadPEMemory.scala 150:35]
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
  copy_buffer_state = _RAND_0[1:0];
  _RAND_1 = {1{`RANDOM}};
  current_copy_address2 = _RAND_1[15:0];
  _RAND_2 = {1{`RANDOM}};
  current_save_address2 = _RAND_2[15:0];
  _RAND_3 = {1{`RANDOM}};
  loading_length = _RAND_3[15:0];
  _RAND_4 = {1{`RANDOM}};
  load_datapoint_complete = _RAND_4[0:0];
  _RAND_5 = {1{`RANDOM}};
  current_copy_address = _RAND_5[15:0];
  _RAND_6 = {1{`RANDOM}};
  current_save_address = _RAND_6[15:0];
  _RAND_7 = {1{`RANDOM}};
  current_state = _RAND_7[1:0];
  _RAND_8 = {1{`RANDOM}};
  current_pe = _RAND_8[15:0];
  _RAND_9 = {1{`RANDOM}};
  previous_pe = _RAND_9[15:0];
  _RAND_10 = {1{`RANDOM}};
  load_initial_weights_complete = _RAND_10[0:0];
  _RAND_11 = {1{`RANDOM}};
  load_buffer_weight_memory_complete = _RAND_11[0:0];
  _RAND_12 = {1{`RANDOM}};
  loading_layer = _RAND_12[15:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
