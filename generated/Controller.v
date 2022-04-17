module Controller(
  input         clock,
  input         reset,
  input         io_controller_in_controller_reset,
  input         io_controller_in_address_generation_complete,
  input         io_controller_in_loading_initial_weights_complete,
  input         io_controller_in_load_datapoint_complete,
  input         io_controller_in_load_buffer_weight_memory_complete,
  input         io_controller_in_save_data_complete,
  input  [15:0] io_controller_in_nn_description_table_input,
  output        io_controller_out_load_initial_weights,
  output        io_controller_out_load_datapoint,
  output        io_controller_out_load_new_request,
  output        io_controller_out_load_data_from_buffer,
  output        io_controller_out_load_same_data,
  output [1:0]  io_controller_out_read_memoryUnits_0,
  output [1:0]  io_controller_out_read_memoryUnits_1,
  output [1:0]  io_controller_out_read_memoryUnits_2,
  output [1:0]  io_controller_out_read_memoryUnits_3,
  output [15:0] io_controller_out_max_iteration,
  output        io_controller_out_address_generator_address_valid,
  output        io_controller_out_address_generator_enable_valid,
  output        io_controller_out_address_generator_reset,
  output [15:0] io_controller_out_loading_layer,
  output [15:0] io_controller_out_loading_activations_0,
  output [15:0] io_controller_out_loading_activations_1,
  output [15:0] io_controller_out_loading_activations_2,
  output [15:0] io_controller_out_loading_activations_3,
  output [1:0]  io_controller_out_write_memoryUnits_0,
  output [1:0]  io_controller_out_write_memoryUnits_1,
  output [1:0]  io_controller_out_write_memoryUnits_2,
  output [1:0]  io_controller_out_write_memoryUnits_3,
  output        io_controller_out_weight_buffer_load_request,
  output        io_controller_out_save_data_request,
  output [15:0] io_controller_out_current_buffer_memory_pointer,
  output [3:0]  io_controller_out_current_state,
  output [15:0] io_controller_out_current_layer_total_activations,
  output [15:0] io_controller_out_loading_layer_total_activations,
  output [15:0] io_controller_out_current_layer,
  output [15:0] io_controller_out_iteration_layer,
  output [15:0] io_controller_out_max_layer,
  output [15:0] io_controller_out_current_layer_current_activation,
  output [15:0] io_controller_out_nn_description_table_address
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
  reg [31:0] _RAND_13;
  reg [31:0] _RAND_14;
  reg [31:0] _RAND_15;
  reg [31:0] _RAND_16;
  reg [31:0] _RAND_17;
  reg [31:0] _RAND_18;
  reg [31:0] _RAND_19;
  reg [31:0] _RAND_20;
  reg [31:0] _RAND_21;
  reg [31:0] _RAND_22;
  reg [31:0] _RAND_23;
  reg [31:0] _RAND_24;
  reg [31:0] _RAND_25;
  reg [31:0] _RAND_26;
  reg [31:0] _RAND_27;
  reg [31:0] _RAND_28;
  reg [31:0] _RAND_29;
  reg [31:0] _RAND_30;
  reg [31:0] _RAND_31;
  reg [31:0] _RAND_32;
  reg [31:0] _RAND_33;
  reg [31:0] _RAND_34;
  reg [31:0] _RAND_35;
  reg [31:0] _RAND_36;
  reg [31:0] _RAND_37;
`endif // RANDOMIZE_REG_INIT
  reg  load_initial_weights_out; // @[controlUnit.scala 86:43]
  reg  load_datapoint; // @[controlUnit.scala 88:33]
  reg  load_new_request; // @[controlUnit.scala 89:35]
  reg  load_data_from_buffer; // @[controlUnit.scala 90:40]
  reg  load_same_data; // @[controlUnit.scala 91:33]
  reg [1:0] read_memoryUnits_0; // @[controlUnit.scala 93:31]
  reg [1:0] read_memoryUnits_1; // @[controlUnit.scala 93:31]
  reg [1:0] read_memoryUnits_2; // @[controlUnit.scala 93:31]
  reg [1:0] read_memoryUnits_3; // @[controlUnit.scala 93:31]
  reg  address_generator_address_valid; // @[controlUnit.scala 95:50]
  reg  address_generator_enable_valid; // @[controlUnit.scala 96:49]
  reg  address_generator_reset; // @[controlUnit.scala 97:42]
  reg [1:0] write_memoryUnits_0; // @[controlUnit.scala 99:32]
  reg [1:0] write_memoryUnits_1; // @[controlUnit.scala 99:32]
  reg [1:0] write_memoryUnits_2; // @[controlUnit.scala 99:32]
  reg [1:0] write_memoryUnits_3; // @[controlUnit.scala 99:32]
  reg [15:0] loading_activations_0; // @[controlUnit.scala 100:34]
  reg [15:0] loading_activations_1; // @[controlUnit.scala 100:34]
  reg [15:0] loading_activations_2; // @[controlUnit.scala 100:34]
  reg [15:0] loading_activations_3; // @[controlUnit.scala 100:34]
  reg  weight_buffer_load_request; // @[controlUnit.scala 101:45]
  reg [3:0] curr_state; // @[controlUnit.scala 111:29]
  reg [15:0] current_layer; // @[controlUnit.scala 118:32]
  reg [15:0] iteration_layer; // @[controlUnit.scala 119:34]
  reg [15:0] loading_layer; // @[controlUnit.scala 120:32]
  reg [15:0] max_layer; // @[controlUnit.scala 121:28]
  reg [15:0] current_layer_total_activations; // @[controlUnit.scala 129:50]
  reg [15:0] current_layer_current_activation; // @[controlUnit.scala 132:51]
  reg [15:0] current_layer_next_activation; // @[controlUnit.scala 133:48]
  reg [15:0] current_buffer_memory_pointer; // @[controlUnit.scala 135:48]
  reg [15:0] current_loading_activation; // @[controlUnit.scala 140:45]
  reg [15:0] next_loading_activation; // @[controlUnit.scala 141:42]
  reg [15:0] loading_layer_total_activations; // @[controlUnit.scala 143:50]
  reg [15:0] current_layer_max_computations; // @[controlUnit.scala 147:49]
  reg [1:0] current_read_memory_usage; // @[controlUnit.scala 152:44]
  reg [1:0] previous_read_memory_usage; // @[controlUnit.scala 154:45]
  reg  load_initial_weights; // @[controlUnit.scala 156:39]
  reg  load_weight_buffer_signal; // @[controlUnit.scala 157:44]
  wire  _T_4 = current_layer == max_layer; // @[controlUnit.scala 198:73]
  wire  _T_5 = current_layer_current_activation > current_layer_total_activations; // @[controlUnit.scala 198:122]
  wire [15:0] _current_layer_T_1 = current_layer + 16'h1; // @[controlUnit.scala 207:48]
  wire [15:0] _GEN_3 = _T_5 ? _current_layer_T_1 : current_layer; // @[controlUnit.scala 206:92 207:31 118:32]
  wire [15:0] _GEN_4 = _T_5 ? 16'h1 : current_layer_current_activation; // @[controlUnit.scala 206:92 208:50 132:51]
  wire [15:0] _GEN_5 = _T_5 ? 16'h0 : current_buffer_memory_pointer; // @[controlUnit.scala 206:92 209:47 214:47]
  wire  _GEN_6 = _T_5 | load_data_from_buffer; // @[controlUnit.scala 206:92 211:39 90:40]
  wire  _GEN_7 = _T_5 ? load_same_data : 1'h1; // @[controlUnit.scala 206:92 216:32 91:33]
  wire [15:0] _GEN_8 = current_layer == 16'h0 | current_layer == max_layer & current_layer_current_activation >
    current_layer_total_activations ? 16'h0 : _GEN_5; // @[controlUnit.scala 198:157 199:47]
  wire [15:0] _GEN_9 = current_layer == 16'h0 | current_layer == max_layer & current_layer_current_activation >
    current_layer_total_activations ? 16'h2 : _GEN_3; // @[controlUnit.scala 198:157 200:31]
  wire [15:0] _GEN_10 = current_layer == 16'h0 | current_layer == max_layer & current_layer_current_activation >
    current_layer_total_activations ? 16'h1 : _GEN_4; // @[controlUnit.scala 198:157 201:50]
  wire  _GEN_11 = current_layer == 16'h0 | current_layer == max_layer & current_layer_current_activation >
    current_layer_total_activations | load_initial_weights; // @[controlUnit.scala 198:157 202:38 156:39]
  wire  _GEN_12 = current_layer == 16'h0 | current_layer == max_layer & current_layer_current_activation >
    current_layer_total_activations | load_new_request; // @[controlUnit.scala 198:157 204:34 89:35]
  wire  _GEN_13 = current_layer == 16'h0 | current_layer == max_layer & current_layer_current_activation >
    current_layer_total_activations ? load_data_from_buffer : _GEN_6; // @[controlUnit.scala 198:157 90:40]
  wire  _GEN_14 = current_layer == 16'h0 | current_layer == max_layer & current_layer_current_activation >
    current_layer_total_activations ? load_same_data : _GEN_7; // @[controlUnit.scala 198:157 91:33]
  wire [3:0] _GEN_15 = io_controller_in_controller_reset ? 4'h0 : 4'h3; // @[controlUnit.scala 219:52 220:28 222:28]
  wire [15:0] _iteration_layer_T_1 = current_layer - 16'h1; // @[controlUnit.scala 234:46]
  wire [3:0] _GEN_16 = io_controller_in_controller_reset ? 4'h0 : 4'h4; // @[controlUnit.scala 236:52 237:28 239:28]
  wire [3:0] _GEN_17 = io_controller_in_controller_reset ? 4'h0 : 4'h5; // @[controlUnit.scala 246:52 247:28 249:28]
  wire [15:0] _T_13 = current_layer_current_activation + 16'h4; // @[controlUnit.scala 264:52]
  wire  _T_14 = _T_13 > current_layer_total_activations; // @[controlUnit.scala 264:80]
  wire [15:0] _GEN_18 = _T_14 ? _current_layer_T_1 : current_layer; // @[controlUnit.scala 268:120 269:31 273:31]
  wire [15:0] _GEN_19 = _T_14 ? 16'h1 : _T_13; // @[controlUnit.scala 268:120 270:44 274:44]
  wire [15:0] _GEN_20 = _T_13 > current_layer_total_activations & _T_4 ? 16'h2 : _GEN_18; // @[controlUnit.scala 264:145 265:31]
  wire [15:0] _GEN_21 = _T_13 > current_layer_total_activations & _T_4 ? 16'h1 : _GEN_19; // @[controlUnit.scala 264:145 266:44]
  wire [3:0] _GEN_22 = io_controller_in_controller_reset ? 4'h0 : 4'h6; // @[controlUnit.scala 277:52 278:28 280:28]
  wire [15:0] _current_layer_max_computations_T_1 = io_controller_in_nn_description_table_input + 16'h1; // @[controlUnit.scala 289:91]
  wire [3:0] _GEN_23 = load_initial_weights ? 4'h8 : 4'h7; // @[controlUnit.scala 296:53 297:28 299:28]
  wire [3:0] _GEN_24 = io_controller_in_controller_reset ? 4'h0 : _GEN_23; // @[controlUnit.scala 294:52 295:28]
  wire [1:0] _GEN_25 = 16'h0 >= current_layer_total_activations ? 2'h0 : 2'h1; // @[controlUnit.scala 309:74 310:42 312:42]
  wire [1:0] _GEN_26 = 16'h1 >= current_layer_total_activations ? 2'h0 : 2'h1; // @[controlUnit.scala 309:74 310:42 312:42]
  wire [1:0] _GEN_27 = 16'h2 >= current_layer_total_activations ? 2'h0 : 2'h1; // @[controlUnit.scala 309:74 310:42 312:42]
  wire [1:0] _GEN_28 = 16'h3 >= current_layer_total_activations ? 2'h0 : 2'h1; // @[controlUnit.scala 309:74 310:42 312:42]
  wire [3:0] _GEN_29 = io_controller_in_loading_initial_weights_complete ? 4'h7 : curr_state; // @[controlUnit.scala 319:75 320:28 111:29]
  wire [3:0] _GEN_30 = io_controller_in_controller_reset ? 4'h0 : _GEN_29; // @[controlUnit.scala 317:52 318:28]
  wire [3:0] _GEN_31 = io_controller_in_load_datapoint_complete ? 4'h9 : curr_state; // @[controlUnit.scala 335:66 336:28 111:29]
  wire [3:0] _GEN_32 = io_controller_in_controller_reset ? 4'h0 : _GEN_31; // @[controlUnit.scala 333:52 334:28]
  wire [16:0] _T_29 = {{1'd0}, current_layer_current_activation}; // @[controlUnit.scala 360:55]
  wire [1:0] _GEN_33 = previous_read_memory_usage == 2'h1 ? 2'h2 : 2'h1; // @[controlUnit.scala 364:70 365:51 368:51]
  wire [1:0] _GEN_34 = _T_29[15:0] > current_layer_total_activations ? 2'h0 : _GEN_33; // @[controlUnit.scala 360:108 361:41]
  wire [1:0] _GEN_35 = _T_29[15:0] > current_layer_total_activations ? current_read_memory_usage : _GEN_33; // @[controlUnit.scala 360:108 152:44]
  wire [15:0] _T_34 = current_layer_current_activation + 16'h1; // @[controlUnit.scala 360:55]
  wire [1:0] _GEN_37 = _T_34 > current_layer_total_activations ? 2'h0 : _GEN_33; // @[controlUnit.scala 360:108 361:41]
  wire [1:0] _GEN_38 = _T_34 > current_layer_total_activations ? _GEN_35 : _GEN_33; // @[controlUnit.scala 360:108]
  wire [15:0] _T_38 = current_layer_current_activation + 16'h2; // @[controlUnit.scala 360:55]
  wire [1:0] _GEN_40 = _T_38 > current_layer_total_activations ? 2'h0 : _GEN_33; // @[controlUnit.scala 360:108 361:41]
  wire [1:0] _GEN_41 = _T_38 > current_layer_total_activations ? _GEN_38 : _GEN_33; // @[controlUnit.scala 360:108]
  wire [15:0] _T_42 = current_layer_current_activation + 16'h3; // @[controlUnit.scala 360:55]
  wire [1:0] _GEN_43 = _T_42 > current_layer_total_activations ? 2'h0 : _GEN_33; // @[controlUnit.scala 360:108 361:41]
  wire [1:0] _GEN_44 = _T_42 > current_layer_total_activations ? _GEN_41 : _GEN_33; // @[controlUnit.scala 360:108]
  wire [16:0] _T_45 = {{1'd0}, current_loading_activation}; // @[controlUnit.scala 378:49]
  wire [1:0] _GEN_45 = previous_read_memory_usage == 2'h2 ? 2'h2 : 2'h1; // @[controlUnit.scala 381:70 382:52 386:52]
  wire [1:0] _GEN_46 = _T_45[15:0] > loading_layer_total_activations ? 2'h0 : _GEN_45; // @[controlUnit.scala 378:102 379:42]
  wire [15:0] _GEN_48 = _T_45[15:0] > loading_layer_total_activations ? loading_activations_0 : _T_45[15:0]; // @[controlUnit.scala 378:102 100:34 390:44]
  wire [15:0] _T_50 = current_loading_activation + 16'h1; // @[controlUnit.scala 378:49]
  wire [1:0] _GEN_50 = _T_50 > loading_layer_total_activations ? 2'h0 : _GEN_45; // @[controlUnit.scala 378:102 379:42]
  wire [15:0] _GEN_52 = _T_50 > loading_layer_total_activations ? loading_activations_1 : _T_50; // @[controlUnit.scala 378:102 100:34 390:44]
  wire [15:0] _T_54 = current_loading_activation + 16'h2; // @[controlUnit.scala 378:49]
  wire [1:0] _GEN_54 = _T_54 > loading_layer_total_activations ? 2'h0 : _GEN_45; // @[controlUnit.scala 378:102 379:42]
  wire [15:0] _GEN_56 = _T_54 > loading_layer_total_activations ? loading_activations_2 : _T_54; // @[controlUnit.scala 378:102 100:34 390:44]
  wire [15:0] _T_58 = current_loading_activation + 16'h3; // @[controlUnit.scala 378:49]
  wire [1:0] _GEN_58 = _T_58 > loading_layer_total_activations ? 2'h0 : _GEN_45; // @[controlUnit.scala 378:102 379:42]
  wire [15:0] _GEN_60 = _T_58 > loading_layer_total_activations ? loading_activations_3 : _T_58; // @[controlUnit.scala 378:102 100:34 390:44]
  wire [15:0] _next_loading_activation_T_1 = current_loading_activation + 16'h4; // @[controlUnit.scala 394:67]
  wire  _GEN_61 = io_controller_in_load_buffer_weight_memory_complete | load_weight_buffer_signal; // @[controlUnit.scala 404:70 405:43 157:44]
  wire [3:0] _GEN_62 = io_controller_in_address_generation_complete ? 4'ha : curr_state; // @[controlUnit.scala 410:70 411:28 111:29]
  wire [3:0] _GEN_63 = io_controller_in_controller_reset ? 4'h0 : _GEN_62; // @[controlUnit.scala 408:52 409:28]
  wire [15:0] _current_buffer_memory_pointer_T_1 = current_buffer_memory_pointer + 16'h4; // @[controlUnit.scala 444:80]
  wire [15:0] _GEN_64 = (load_weight_buffer_signal | io_controller_in_load_buffer_weight_memory_complete) &
    io_controller_in_save_data_complete ? _current_buffer_memory_pointer_T_1 : current_buffer_memory_pointer; // @[controlUnit.scala 443:154 444:47 135:48]
  wire [3:0] _GEN_65 = (load_weight_buffer_signal | io_controller_in_load_buffer_weight_memory_complete) &
    io_controller_in_save_data_complete ? 4'h2 : curr_state; // @[controlUnit.scala 443:154 445:28 111:29]
  wire [3:0] _GEN_66 = io_controller_in_controller_reset ? 4'h0 : _GEN_65; // @[controlUnit.scala 441:52 442:28]
  wire [15:0] _GEN_67 = io_controller_in_controller_reset ? current_buffer_memory_pointer : _GEN_64; // @[controlUnit.scala 135:48 441:52]
  wire [1:0] _GEN_68 = 4'ha == curr_state ? current_read_memory_usage : previous_read_memory_usage; // @[controlUnit.scala 159:23 418:40 154:45]
  wire [15:0] _GEN_69 = 4'ha == curr_state ? current_layer_next_activation : current_layer_current_activation; // @[controlUnit.scala 159:23 419:46 132:51]
  wire [15:0] _GEN_70 = 4'ha == curr_state ? next_loading_activation : current_loading_activation; // @[controlUnit.scala 159:23 420:40 140:45]
  wire [3:0] _GEN_71 = 4'ha == curr_state ? 4'h8 : 4'h0; // @[controlUnit.scala 159:23 112:37 422:45]
  wire [1:0] _GEN_72 = 4'ha == curr_state ? 2'h0 : read_memoryUnits_0; // @[controlUnit.scala 159:23 425:37 93:31]
  wire [1:0] _GEN_73 = 4'ha == curr_state ? 2'h0 : write_memoryUnits_0; // @[controlUnit.scala 159:23 426:38 99:32]
  wire [1:0] _GEN_74 = 4'ha == curr_state ? 2'h0 : read_memoryUnits_1; // @[controlUnit.scala 159:23 425:37 93:31]
  wire [1:0] _GEN_75 = 4'ha == curr_state ? 2'h0 : write_memoryUnits_1; // @[controlUnit.scala 159:23 426:38 99:32]
  wire [1:0] _GEN_76 = 4'ha == curr_state ? 2'h0 : read_memoryUnits_2; // @[controlUnit.scala 159:23 425:37 93:31]
  wire [1:0] _GEN_77 = 4'ha == curr_state ? 2'h0 : write_memoryUnits_2; // @[controlUnit.scala 159:23 426:38 99:32]
  wire [1:0] _GEN_78 = 4'ha == curr_state ? 2'h0 : read_memoryUnits_3; // @[controlUnit.scala 159:23 425:37 93:31]
  wire [1:0] _GEN_79 = 4'ha == curr_state ? 2'h0 : write_memoryUnits_3; // @[controlUnit.scala 159:23 426:38 99:32]
  wire  _GEN_80 = 4'ha == curr_state | address_generator_reset; // @[controlUnit.scala 159:23 429:37 97:42]
  wire  _GEN_81 = 4'ha == curr_state ? 1'h0 : address_generator_address_valid; // @[controlUnit.scala 159:23 430:45 95:50]
  wire  _GEN_82 = 4'ha == curr_state ? 1'h0 : address_generator_enable_valid; // @[controlUnit.scala 159:23 431:44 96:49]
  wire  _GEN_83 = 4'ha == curr_state ? 1'h0 : weight_buffer_load_request; // @[controlUnit.scala 159:23 433:40 101:45]
  wire  _GEN_85 = 4'ha == curr_state ? _GEN_61 : load_weight_buffer_signal; // @[controlUnit.scala 159:23 157:44]
  wire [3:0] _GEN_86 = 4'ha == curr_state ? _GEN_66 : curr_state; // @[controlUnit.scala 159:23 111:29]
  wire [15:0] _GEN_87 = 4'ha == curr_state ? _GEN_67 : current_buffer_memory_pointer; // @[controlUnit.scala 159:23 135:48]
  wire [3:0] _GEN_88 = 4'h9 == curr_state ? 4'h7 : _GEN_71; // @[controlUnit.scala 159:23 351:45]
  wire  _GEN_89 = 4'h9 == curr_state ? 1'h0 : load_datapoint; // @[controlUnit.scala 159:23 353:28 88:33]
  wire  _GEN_90 = 4'h9 == curr_state ? 1'h0 : load_new_request; // @[controlUnit.scala 159:23 354:30 89:35]
  wire  _GEN_91 = 4'h9 == curr_state ? 1'h0 : load_data_from_buffer; // @[controlUnit.scala 159:23 355:35 90:40]
  wire  _GEN_92 = 4'h9 == curr_state ? 1'h0 : load_same_data; // @[controlUnit.scala 159:23 356:28 91:33]
  wire [1:0] _GEN_93 = 4'h9 == curr_state ? _GEN_34 : _GEN_72; // @[controlUnit.scala 159:23]
  wire [1:0] _GEN_94 = 4'h9 == curr_state ? _GEN_44 : current_read_memory_usage; // @[controlUnit.scala 159:23 152:44]
  wire [1:0] _GEN_95 = 4'h9 == curr_state ? _GEN_37 : _GEN_74; // @[controlUnit.scala 159:23]
  wire [1:0] _GEN_96 = 4'h9 == curr_state ? _GEN_40 : _GEN_76; // @[controlUnit.scala 159:23]
  wire [1:0] _GEN_97 = 4'h9 == curr_state ? _GEN_43 : _GEN_78; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_98 = 4'h9 == curr_state ? _T_13 : current_layer_next_activation; // @[controlUnit.scala 159:23 374:43 133:48]
  wire [1:0] _GEN_99 = 4'h9 == curr_state ? _GEN_46 : _GEN_73; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_101 = 4'h9 == curr_state ? _GEN_48 : loading_activations_0; // @[controlUnit.scala 159:23 100:34]
  wire [1:0] _GEN_102 = 4'h9 == curr_state ? _GEN_50 : _GEN_75; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_103 = 4'h9 == curr_state ? _GEN_52 : loading_activations_1; // @[controlUnit.scala 159:23 100:34]
  wire [1:0] _GEN_104 = 4'h9 == curr_state ? _GEN_54 : _GEN_77; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_105 = 4'h9 == curr_state ? _GEN_56 : loading_activations_2; // @[controlUnit.scala 159:23 100:34]
  wire [1:0] _GEN_106 = 4'h9 == curr_state ? _GEN_58 : _GEN_79; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_107 = 4'h9 == curr_state ? _GEN_60 : loading_activations_3; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_108 = 4'h9 == curr_state ? _next_loading_activation_T_1 : next_loading_activation; // @[controlUnit.scala 159:23 394:37 141:42]
  wire  _GEN_109 = 4'h9 == curr_state | _GEN_81; // @[controlUnit.scala 159:23 397:45]
  wire  _GEN_110 = 4'h9 == curr_state | _GEN_82; // @[controlUnit.scala 159:23 398:44]
  wire  _GEN_111 = 4'h9 == curr_state ? 1'h0 : _GEN_80; // @[controlUnit.scala 159:23 399:37]
  wire  _GEN_112 = 4'h9 == curr_state | _GEN_83; // @[controlUnit.scala 159:23 401:40]
  wire  _GEN_113 = 4'h9 == curr_state ? _GEN_61 : _GEN_85; // @[controlUnit.scala 159:23]
  wire [3:0] _GEN_114 = 4'h9 == curr_state ? _GEN_63 : _GEN_86; // @[controlUnit.scala 159:23]
  wire [1:0] _GEN_115 = 4'h9 == curr_state ? previous_read_memory_usage : _GEN_68; // @[controlUnit.scala 159:23 154:45]
  wire [15:0] _GEN_116 = 4'h9 == curr_state ? current_layer_current_activation : _GEN_69; // @[controlUnit.scala 159:23 132:51]
  wire [15:0] _GEN_117 = 4'h9 == curr_state ? current_loading_activation : _GEN_70; // @[controlUnit.scala 159:23 140:45]
  wire  _GEN_118 = 4'h9 == curr_state ? 1'h0 : 4'ha == curr_state; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_119 = 4'h9 == curr_state ? current_buffer_memory_pointer : _GEN_87; // @[controlUnit.scala 159:23 135:48]
  wire  _GEN_120 = 4'h7 == curr_state ? 1'h0 : load_initial_weights_out; // @[controlUnit.scala 159:23 326:38 86:43]
  wire [3:0] _GEN_121 = 4'h7 == curr_state ? 4'h6 : _GEN_88; // @[controlUnit.scala 159:23 329:45]
  wire [15:0] _GEN_122 = 4'h7 == curr_state ? io_controller_in_nn_description_table_input :
    loading_layer_total_activations; // @[controlUnit.scala 159:23 330:45 143:50]
  wire  _GEN_123 = 4'h7 == curr_state | _GEN_89; // @[controlUnit.scala 159:23 331:28]
  wire [3:0] _GEN_124 = 4'h7 == curr_state ? _GEN_32 : _GEN_114; // @[controlUnit.scala 159:23]
  wire  _GEN_125 = 4'h7 == curr_state ? load_new_request : _GEN_90; // @[controlUnit.scala 159:23 89:35]
  wire  _GEN_126 = 4'h7 == curr_state ? load_data_from_buffer : _GEN_91; // @[controlUnit.scala 159:23 90:40]
  wire  _GEN_127 = 4'h7 == curr_state ? load_same_data : _GEN_92; // @[controlUnit.scala 159:23 91:33]
  wire [1:0] _GEN_128 = 4'h7 == curr_state ? read_memoryUnits_0 : _GEN_93; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_129 = 4'h7 == curr_state ? current_read_memory_usage : _GEN_94; // @[controlUnit.scala 159:23 152:44]
  wire [1:0] _GEN_130 = 4'h7 == curr_state ? read_memoryUnits_1 : _GEN_95; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_131 = 4'h7 == curr_state ? read_memoryUnits_2 : _GEN_96; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_132 = 4'h7 == curr_state ? read_memoryUnits_3 : _GEN_97; // @[controlUnit.scala 159:23 93:31]
  wire [15:0] _GEN_133 = 4'h7 == curr_state ? current_layer_next_activation : _GEN_98; // @[controlUnit.scala 159:23 133:48]
  wire [1:0] _GEN_134 = 4'h7 == curr_state ? write_memoryUnits_0 : _GEN_99; // @[controlUnit.scala 159:23 99:32]
  wire [15:0] _GEN_136 = 4'h7 == curr_state ? loading_activations_0 : _GEN_101; // @[controlUnit.scala 159:23 100:34]
  wire [1:0] _GEN_137 = 4'h7 == curr_state ? write_memoryUnits_1 : _GEN_102; // @[controlUnit.scala 159:23 99:32]
  wire [15:0] _GEN_138 = 4'h7 == curr_state ? loading_activations_1 : _GEN_103; // @[controlUnit.scala 159:23 100:34]
  wire [1:0] _GEN_139 = 4'h7 == curr_state ? write_memoryUnits_2 : _GEN_104; // @[controlUnit.scala 159:23 99:32]
  wire [15:0] _GEN_140 = 4'h7 == curr_state ? loading_activations_2 : _GEN_105; // @[controlUnit.scala 159:23 100:34]
  wire [1:0] _GEN_141 = 4'h7 == curr_state ? write_memoryUnits_3 : _GEN_106; // @[controlUnit.scala 159:23 99:32]
  wire [15:0] _GEN_142 = 4'h7 == curr_state ? loading_activations_3 : _GEN_107; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_143 = 4'h7 == curr_state ? next_loading_activation : _GEN_108; // @[controlUnit.scala 159:23 141:42]
  wire  _GEN_144 = 4'h7 == curr_state ? address_generator_address_valid : _GEN_109; // @[controlUnit.scala 159:23 95:50]
  wire  _GEN_145 = 4'h7 == curr_state ? address_generator_enable_valid : _GEN_110; // @[controlUnit.scala 159:23 96:49]
  wire  _GEN_146 = 4'h7 == curr_state ? address_generator_reset : _GEN_111; // @[controlUnit.scala 159:23 97:42]
  wire  _GEN_147 = 4'h7 == curr_state ? weight_buffer_load_request : _GEN_112; // @[controlUnit.scala 159:23 101:45]
  wire  _GEN_148 = 4'h7 == curr_state ? load_weight_buffer_signal : _GEN_113; // @[controlUnit.scala 159:23 157:44]
  wire [1:0] _GEN_149 = 4'h7 == curr_state ? previous_read_memory_usage : _GEN_115; // @[controlUnit.scala 159:23 154:45]
  wire [15:0] _GEN_150 = 4'h7 == curr_state ? current_layer_current_activation : _GEN_116; // @[controlUnit.scala 159:23 132:51]
  wire [15:0] _GEN_151 = 4'h7 == curr_state ? current_loading_activation : _GEN_117; // @[controlUnit.scala 159:23 140:45]
  wire  _GEN_152 = 4'h7 == curr_state ? 1'h0 : _GEN_118; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_153 = 4'h7 == curr_state ? current_buffer_memory_pointer : _GEN_119; // @[controlUnit.scala 159:23 135:48]
  wire  _GEN_154 = 4'h8 == curr_state | _GEN_120; // @[controlUnit.scala 159:23 306:38]
  wire [1:0] _GEN_155 = 4'h8 == curr_state ? _GEN_25 : _GEN_134; // @[controlUnit.scala 159:23]
  wire [1:0] _GEN_156 = 4'h8 == curr_state ? _GEN_26 : _GEN_137; // @[controlUnit.scala 159:23]
  wire [1:0] _GEN_157 = 4'h8 == curr_state ? _GEN_27 : _GEN_139; // @[controlUnit.scala 159:23]
  wire [1:0] _GEN_158 = 4'h8 == curr_state ? _GEN_28 : _GEN_141; // @[controlUnit.scala 159:23]
  wire  _GEN_159 = 4'h8 == curr_state ? 1'h0 : load_initial_weights; // @[controlUnit.scala 159:23 316:34 156:39]
  wire [3:0] _GEN_160 = 4'h8 == curr_state ? _GEN_30 : _GEN_124; // @[controlUnit.scala 159:23]
  wire [3:0] _GEN_161 = 4'h8 == curr_state ? 4'h0 : _GEN_121; // @[controlUnit.scala 159:23 112:37]
  wire [15:0] _GEN_162 = 4'h8 == curr_state ? loading_layer_total_activations : _GEN_122; // @[controlUnit.scala 159:23 143:50]
  wire  _GEN_163 = 4'h8 == curr_state ? load_datapoint : _GEN_123; // @[controlUnit.scala 159:23 88:33]
  wire  _GEN_164 = 4'h8 == curr_state ? load_new_request : _GEN_125; // @[controlUnit.scala 159:23 89:35]
  wire  _GEN_165 = 4'h8 == curr_state ? load_data_from_buffer : _GEN_126; // @[controlUnit.scala 159:23 90:40]
  wire  _GEN_166 = 4'h8 == curr_state ? load_same_data : _GEN_127; // @[controlUnit.scala 159:23 91:33]
  wire [1:0] _GEN_167 = 4'h8 == curr_state ? read_memoryUnits_0 : _GEN_128; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_168 = 4'h8 == curr_state ? current_read_memory_usage : _GEN_129; // @[controlUnit.scala 159:23 152:44]
  wire [1:0] _GEN_169 = 4'h8 == curr_state ? read_memoryUnits_1 : _GEN_130; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_170 = 4'h8 == curr_state ? read_memoryUnits_2 : _GEN_131; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_171 = 4'h8 == curr_state ? read_memoryUnits_3 : _GEN_132; // @[controlUnit.scala 159:23 93:31]
  wire [15:0] _GEN_172 = 4'h8 == curr_state ? current_layer_next_activation : _GEN_133; // @[controlUnit.scala 159:23 133:48]
  wire [15:0] _GEN_174 = 4'h8 == curr_state ? loading_activations_0 : _GEN_136; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_175 = 4'h8 == curr_state ? loading_activations_1 : _GEN_138; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_176 = 4'h8 == curr_state ? loading_activations_2 : _GEN_140; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_177 = 4'h8 == curr_state ? loading_activations_3 : _GEN_142; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_178 = 4'h8 == curr_state ? next_loading_activation : _GEN_143; // @[controlUnit.scala 159:23 141:42]
  wire  _GEN_179 = 4'h8 == curr_state ? address_generator_address_valid : _GEN_144; // @[controlUnit.scala 159:23 95:50]
  wire  _GEN_180 = 4'h8 == curr_state ? address_generator_enable_valid : _GEN_145; // @[controlUnit.scala 159:23 96:49]
  wire  _GEN_181 = 4'h8 == curr_state ? address_generator_reset : _GEN_146; // @[controlUnit.scala 159:23 97:42]
  wire  _GEN_182 = 4'h8 == curr_state ? weight_buffer_load_request : _GEN_147; // @[controlUnit.scala 159:23 101:45]
  wire  _GEN_183 = 4'h8 == curr_state ? load_weight_buffer_signal : _GEN_148; // @[controlUnit.scala 159:23 157:44]
  wire [1:0] _GEN_184 = 4'h8 == curr_state ? previous_read_memory_usage : _GEN_149; // @[controlUnit.scala 159:23 154:45]
  wire [15:0] _GEN_185 = 4'h8 == curr_state ? current_layer_current_activation : _GEN_150; // @[controlUnit.scala 159:23 132:51]
  wire [15:0] _GEN_186 = 4'h8 == curr_state ? current_loading_activation : _GEN_151; // @[controlUnit.scala 159:23 140:45]
  wire  _GEN_187 = 4'h8 == curr_state ? 1'h0 : _GEN_152; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_188 = 4'h8 == curr_state ? current_buffer_memory_pointer : _GEN_153; // @[controlUnit.scala 159:23 135:48]
  wire [3:0] _GEN_189 = 4'h6 == curr_state ? 4'h5 : _GEN_161; // @[controlUnit.scala 159:23 287:45]
  wire [15:0] _GEN_190 = 4'h6 == curr_state ? _current_layer_max_computations_T_1 : current_layer_max_computations; // @[controlUnit.scala 159:23 289:44 147:49]
  wire [15:0] _GEN_191 = 4'h6 == curr_state ? loading_layer : 16'h0; // @[controlUnit.scala 159:23 106:52 290:60]
  wire [3:0] _GEN_192 = 4'h6 == curr_state ? _GEN_24 : _GEN_160; // @[controlUnit.scala 159:23]
  wire  _GEN_193 = 4'h6 == curr_state ? load_initial_weights_out : _GEN_154; // @[controlUnit.scala 159:23 86:43]
  wire [1:0] _GEN_194 = 4'h6 == curr_state ? write_memoryUnits_0 : _GEN_155; // @[controlUnit.scala 159:23 99:32]
  wire [1:0] _GEN_195 = 4'h6 == curr_state ? write_memoryUnits_1 : _GEN_156; // @[controlUnit.scala 159:23 99:32]
  wire [1:0] _GEN_196 = 4'h6 == curr_state ? write_memoryUnits_2 : _GEN_157; // @[controlUnit.scala 159:23 99:32]
  wire [1:0] _GEN_197 = 4'h6 == curr_state ? write_memoryUnits_3 : _GEN_158; // @[controlUnit.scala 159:23 99:32]
  wire  _GEN_198 = 4'h6 == curr_state ? load_initial_weights : _GEN_159; // @[controlUnit.scala 159:23 156:39]
  wire [15:0] _GEN_199 = 4'h6 == curr_state ? loading_layer_total_activations : _GEN_162; // @[controlUnit.scala 159:23 143:50]
  wire  _GEN_200 = 4'h6 == curr_state ? load_datapoint : _GEN_163; // @[controlUnit.scala 159:23 88:33]
  wire  _GEN_201 = 4'h6 == curr_state ? load_new_request : _GEN_164; // @[controlUnit.scala 159:23 89:35]
  wire  _GEN_202 = 4'h6 == curr_state ? load_data_from_buffer : _GEN_165; // @[controlUnit.scala 159:23 90:40]
  wire  _GEN_203 = 4'h6 == curr_state ? load_same_data : _GEN_166; // @[controlUnit.scala 159:23 91:33]
  wire [1:0] _GEN_204 = 4'h6 == curr_state ? read_memoryUnits_0 : _GEN_167; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_205 = 4'h6 == curr_state ? current_read_memory_usage : _GEN_168; // @[controlUnit.scala 159:23 152:44]
  wire [1:0] _GEN_206 = 4'h6 == curr_state ? read_memoryUnits_1 : _GEN_169; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_207 = 4'h6 == curr_state ? read_memoryUnits_2 : _GEN_170; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_208 = 4'h6 == curr_state ? read_memoryUnits_3 : _GEN_171; // @[controlUnit.scala 159:23 93:31]
  wire [15:0] _GEN_209 = 4'h6 == curr_state ? current_layer_next_activation : _GEN_172; // @[controlUnit.scala 159:23 133:48]
  wire [15:0] _GEN_211 = 4'h6 == curr_state ? loading_activations_0 : _GEN_174; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_212 = 4'h6 == curr_state ? loading_activations_1 : _GEN_175; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_213 = 4'h6 == curr_state ? loading_activations_2 : _GEN_176; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_214 = 4'h6 == curr_state ? loading_activations_3 : _GEN_177; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_215 = 4'h6 == curr_state ? next_loading_activation : _GEN_178; // @[controlUnit.scala 159:23 141:42]
  wire  _GEN_216 = 4'h6 == curr_state ? address_generator_address_valid : _GEN_179; // @[controlUnit.scala 159:23 95:50]
  wire  _GEN_217 = 4'h6 == curr_state ? address_generator_enable_valid : _GEN_180; // @[controlUnit.scala 159:23 96:49]
  wire  _GEN_218 = 4'h6 == curr_state ? address_generator_reset : _GEN_181; // @[controlUnit.scala 159:23 97:42]
  wire  _GEN_219 = 4'h6 == curr_state ? weight_buffer_load_request : _GEN_182; // @[controlUnit.scala 159:23 101:45]
  wire  _GEN_220 = 4'h6 == curr_state ? load_weight_buffer_signal : _GEN_183; // @[controlUnit.scala 159:23 157:44]
  wire [1:0] _GEN_221 = 4'h6 == curr_state ? previous_read_memory_usage : _GEN_184; // @[controlUnit.scala 159:23 154:45]
  wire [15:0] _GEN_222 = 4'h6 == curr_state ? current_layer_current_activation : _GEN_185; // @[controlUnit.scala 159:23 132:51]
  wire [15:0] _GEN_223 = 4'h6 == curr_state ? current_loading_activation : _GEN_186; // @[controlUnit.scala 159:23 140:45]
  wire  _GEN_224 = 4'h6 == curr_state ? 1'h0 : _GEN_187; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_225 = 4'h6 == curr_state ? current_buffer_memory_pointer : _GEN_188; // @[controlUnit.scala 159:23 135:48]
  wire [3:0] _GEN_226 = 4'h5 == curr_state ? 4'h4 : _GEN_189; // @[controlUnit.scala 159:23 260:45]
  wire [15:0] _GEN_227 = 4'h5 == curr_state ? iteration_layer : _GEN_191; // @[controlUnit.scala 159:23 262:60]
  wire [15:0] _GEN_228 = 4'h5 == curr_state ? _GEN_20 : loading_layer; // @[controlUnit.scala 159:23 120:32]
  wire [15:0] _GEN_229 = 4'h5 == curr_state ? _GEN_21 : _GEN_223; // @[controlUnit.scala 159:23]
  wire [3:0] _GEN_230 = 4'h5 == curr_state ? _GEN_22 : _GEN_192; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_231 = 4'h5 == curr_state ? current_layer_max_computations : _GEN_190; // @[controlUnit.scala 159:23 147:49]
  wire  _GEN_232 = 4'h5 == curr_state ? load_initial_weights_out : _GEN_193; // @[controlUnit.scala 159:23 86:43]
  wire [1:0] _GEN_233 = 4'h5 == curr_state ? write_memoryUnits_0 : _GEN_194; // @[controlUnit.scala 159:23 99:32]
  wire [1:0] _GEN_234 = 4'h5 == curr_state ? write_memoryUnits_1 : _GEN_195; // @[controlUnit.scala 159:23 99:32]
  wire [1:0] _GEN_235 = 4'h5 == curr_state ? write_memoryUnits_2 : _GEN_196; // @[controlUnit.scala 159:23 99:32]
  wire [1:0] _GEN_236 = 4'h5 == curr_state ? write_memoryUnits_3 : _GEN_197; // @[controlUnit.scala 159:23 99:32]
  wire  _GEN_237 = 4'h5 == curr_state ? load_initial_weights : _GEN_198; // @[controlUnit.scala 159:23 156:39]
  wire [15:0] _GEN_238 = 4'h5 == curr_state ? loading_layer_total_activations : _GEN_199; // @[controlUnit.scala 159:23 143:50]
  wire  _GEN_239 = 4'h5 == curr_state ? load_datapoint : _GEN_200; // @[controlUnit.scala 159:23 88:33]
  wire  _GEN_240 = 4'h5 == curr_state ? load_new_request : _GEN_201; // @[controlUnit.scala 159:23 89:35]
  wire  _GEN_241 = 4'h5 == curr_state ? load_data_from_buffer : _GEN_202; // @[controlUnit.scala 159:23 90:40]
  wire  _GEN_242 = 4'h5 == curr_state ? load_same_data : _GEN_203; // @[controlUnit.scala 159:23 91:33]
  wire [1:0] _GEN_243 = 4'h5 == curr_state ? read_memoryUnits_0 : _GEN_204; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_244 = 4'h5 == curr_state ? current_read_memory_usage : _GEN_205; // @[controlUnit.scala 159:23 152:44]
  wire [1:0] _GEN_245 = 4'h5 == curr_state ? read_memoryUnits_1 : _GEN_206; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_246 = 4'h5 == curr_state ? read_memoryUnits_2 : _GEN_207; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_247 = 4'h5 == curr_state ? read_memoryUnits_3 : _GEN_208; // @[controlUnit.scala 159:23 93:31]
  wire [15:0] _GEN_248 = 4'h5 == curr_state ? current_layer_next_activation : _GEN_209; // @[controlUnit.scala 159:23 133:48]
  wire [15:0] _GEN_250 = 4'h5 == curr_state ? loading_activations_0 : _GEN_211; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_251 = 4'h5 == curr_state ? loading_activations_1 : _GEN_212; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_252 = 4'h5 == curr_state ? loading_activations_2 : _GEN_213; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_253 = 4'h5 == curr_state ? loading_activations_3 : _GEN_214; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_254 = 4'h5 == curr_state ? next_loading_activation : _GEN_215; // @[controlUnit.scala 159:23 141:42]
  wire  _GEN_255 = 4'h5 == curr_state ? address_generator_address_valid : _GEN_216; // @[controlUnit.scala 159:23 95:50]
  wire  _GEN_256 = 4'h5 == curr_state ? address_generator_enable_valid : _GEN_217; // @[controlUnit.scala 159:23 96:49]
  wire  _GEN_257 = 4'h5 == curr_state ? address_generator_reset : _GEN_218; // @[controlUnit.scala 159:23 97:42]
  wire  _GEN_258 = 4'h5 == curr_state ? weight_buffer_load_request : _GEN_219; // @[controlUnit.scala 159:23 101:45]
  wire  _GEN_259 = 4'h5 == curr_state ? load_weight_buffer_signal : _GEN_220; // @[controlUnit.scala 159:23 157:44]
  wire [1:0] _GEN_260 = 4'h5 == curr_state ? previous_read_memory_usage : _GEN_221; // @[controlUnit.scala 159:23 154:45]
  wire [15:0] _GEN_261 = 4'h5 == curr_state ? current_layer_current_activation : _GEN_222; // @[controlUnit.scala 159:23 132:51]
  wire  _GEN_262 = 4'h5 == curr_state ? 1'h0 : _GEN_224; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_263 = 4'h5 == curr_state ? current_buffer_memory_pointer : _GEN_225; // @[controlUnit.scala 159:23 135:48]
  wire [15:0] _GEN_264 = 4'h4 == curr_state ? io_controller_in_nn_description_table_input :
    current_layer_total_activations; // @[controlUnit.scala 159:23 244:45 129:50]
  wire [3:0] _GEN_265 = 4'h4 == curr_state ? _GEN_17 : _GEN_230; // @[controlUnit.scala 159:23]
  wire [3:0] _GEN_266 = 4'h4 == curr_state ? 4'h0 : _GEN_226; // @[controlUnit.scala 159:23 112:37]
  wire [15:0] _GEN_267 = 4'h4 == curr_state ? 16'h0 : _GEN_227; // @[controlUnit.scala 159:23 106:52]
  wire [15:0] _GEN_268 = 4'h4 == curr_state ? loading_layer : _GEN_228; // @[controlUnit.scala 159:23 120:32]
  wire [15:0] _GEN_269 = 4'h4 == curr_state ? current_loading_activation : _GEN_229; // @[controlUnit.scala 159:23 140:45]
  wire [15:0] _GEN_270 = 4'h4 == curr_state ? current_layer_max_computations : _GEN_231; // @[controlUnit.scala 159:23 147:49]
  wire  _GEN_271 = 4'h4 == curr_state ? load_initial_weights_out : _GEN_232; // @[controlUnit.scala 159:23 86:43]
  wire [1:0] _GEN_272 = 4'h4 == curr_state ? write_memoryUnits_0 : _GEN_233; // @[controlUnit.scala 159:23 99:32]
  wire [1:0] _GEN_273 = 4'h4 == curr_state ? write_memoryUnits_1 : _GEN_234; // @[controlUnit.scala 159:23 99:32]
  wire [1:0] _GEN_274 = 4'h4 == curr_state ? write_memoryUnits_2 : _GEN_235; // @[controlUnit.scala 159:23 99:32]
  wire [1:0] _GEN_275 = 4'h4 == curr_state ? write_memoryUnits_3 : _GEN_236; // @[controlUnit.scala 159:23 99:32]
  wire  _GEN_276 = 4'h4 == curr_state ? load_initial_weights : _GEN_237; // @[controlUnit.scala 159:23 156:39]
  wire [15:0] _GEN_277 = 4'h4 == curr_state ? loading_layer_total_activations : _GEN_238; // @[controlUnit.scala 159:23 143:50]
  wire  _GEN_278 = 4'h4 == curr_state ? load_datapoint : _GEN_239; // @[controlUnit.scala 159:23 88:33]
  wire  _GEN_279 = 4'h4 == curr_state ? load_new_request : _GEN_240; // @[controlUnit.scala 159:23 89:35]
  wire  _GEN_280 = 4'h4 == curr_state ? load_data_from_buffer : _GEN_241; // @[controlUnit.scala 159:23 90:40]
  wire  _GEN_281 = 4'h4 == curr_state ? load_same_data : _GEN_242; // @[controlUnit.scala 159:23 91:33]
  wire [1:0] _GEN_282 = 4'h4 == curr_state ? read_memoryUnits_0 : _GEN_243; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_283 = 4'h4 == curr_state ? current_read_memory_usage : _GEN_244; // @[controlUnit.scala 159:23 152:44]
  wire [1:0] _GEN_284 = 4'h4 == curr_state ? read_memoryUnits_1 : _GEN_245; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_285 = 4'h4 == curr_state ? read_memoryUnits_2 : _GEN_246; // @[controlUnit.scala 159:23 93:31]
  wire [1:0] _GEN_286 = 4'h4 == curr_state ? read_memoryUnits_3 : _GEN_247; // @[controlUnit.scala 159:23 93:31]
  wire [15:0] _GEN_287 = 4'h4 == curr_state ? current_layer_next_activation : _GEN_248; // @[controlUnit.scala 159:23 133:48]
  wire [15:0] _GEN_289 = 4'h4 == curr_state ? loading_activations_0 : _GEN_250; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_290 = 4'h4 == curr_state ? loading_activations_1 : _GEN_251; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_291 = 4'h4 == curr_state ? loading_activations_2 : _GEN_252; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_292 = 4'h4 == curr_state ? loading_activations_3 : _GEN_253; // @[controlUnit.scala 159:23 100:34]
  wire [15:0] _GEN_293 = 4'h4 == curr_state ? next_loading_activation : _GEN_254; // @[controlUnit.scala 159:23 141:42]
  wire  _GEN_294 = 4'h4 == curr_state ? address_generator_address_valid : _GEN_255; // @[controlUnit.scala 159:23 95:50]
  wire  _GEN_295 = 4'h4 == curr_state ? address_generator_enable_valid : _GEN_256; // @[controlUnit.scala 159:23 96:49]
  wire  _GEN_296 = 4'h4 == curr_state ? address_generator_reset : _GEN_257; // @[controlUnit.scala 159:23 97:42]
  wire  _GEN_297 = 4'h4 == curr_state ? weight_buffer_load_request : _GEN_258; // @[controlUnit.scala 159:23 101:45]
  wire  _GEN_298 = 4'h4 == curr_state ? load_weight_buffer_signal : _GEN_259; // @[controlUnit.scala 159:23 157:44]
  wire [1:0] _GEN_299 = 4'h4 == curr_state ? previous_read_memory_usage : _GEN_260; // @[controlUnit.scala 159:23 154:45]
  wire [15:0] _GEN_300 = 4'h4 == curr_state ? current_layer_current_activation : _GEN_261; // @[controlUnit.scala 159:23 132:51]
  wire  _GEN_301 = 4'h4 == curr_state ? 1'h0 : _GEN_262; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_302 = 4'h4 == curr_state ? current_buffer_memory_pointer : _GEN_263; // @[controlUnit.scala 159:23 135:48]
  wire [3:0] _GEN_303 = 4'h3 == curr_state ? 4'h3 : _GEN_266; // @[controlUnit.scala 159:23 231:45]
  wire [15:0] _GEN_304 = 4'h3 == curr_state ? current_layer : _GEN_267; // @[controlUnit.scala 159:23 233:60]
  wire [15:0] _GEN_305 = 4'h3 == curr_state ? _iteration_layer_T_1 : iteration_layer; // @[controlUnit.scala 159:23 234:29 119:34]
  wire [3:0] _GEN_306 = 4'h3 == curr_state ? _GEN_16 : _GEN_265; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_307 = 4'h3 == curr_state ? current_layer_total_activations : _GEN_264; // @[controlUnit.scala 159:23 129:50]
  wire [15:0] _GEN_308 = 4'h3 == curr_state ? loading_layer : _GEN_268; // @[controlUnit.scala 159:23 120:32]
  wire [15:0] _GEN_309 = 4'h3 == curr_state ? current_loading_activation : _GEN_269; // @[controlUnit.scala 159:23 140:45]
  wire [15:0] _GEN_310 = 4'h3 == curr_state ? current_layer_max_computations : _GEN_270; // @[controlUnit.scala 159:23 147:49]
  wire  _GEN_311 = 4'h3 == curr_state ? load_initial_weights_out : _GEN_271; // @[controlUnit.scala 159:23 86:43]
  wire  _GEN_316 = 4'h3 == curr_state ? load_initial_weights : _GEN_276; // @[controlUnit.scala 159:23 156:39]
  wire [15:0] _GEN_317 = 4'h3 == curr_state ? loading_layer_total_activations : _GEN_277; // @[controlUnit.scala 159:23 143:50]
  wire  _GEN_318 = 4'h3 == curr_state ? load_datapoint : _GEN_278; // @[controlUnit.scala 159:23 88:33]
  wire  _GEN_319 = 4'h3 == curr_state ? load_new_request : _GEN_279; // @[controlUnit.scala 159:23 89:35]
  wire  _GEN_320 = 4'h3 == curr_state ? load_data_from_buffer : _GEN_280; // @[controlUnit.scala 159:23 90:40]
  wire  _GEN_321 = 4'h3 == curr_state ? load_same_data : _GEN_281; // @[controlUnit.scala 159:23 91:33]
  wire [1:0] _GEN_323 = 4'h3 == curr_state ? current_read_memory_usage : _GEN_283; // @[controlUnit.scala 159:23 152:44]
  wire [15:0] _GEN_327 = 4'h3 == curr_state ? current_layer_next_activation : _GEN_287; // @[controlUnit.scala 159:23 133:48]
  wire [15:0] _GEN_333 = 4'h3 == curr_state ? next_loading_activation : _GEN_293; // @[controlUnit.scala 159:23 141:42]
  wire  _GEN_334 = 4'h3 == curr_state ? address_generator_address_valid : _GEN_294; // @[controlUnit.scala 159:23 95:50]
  wire  _GEN_335 = 4'h3 == curr_state ? address_generator_enable_valid : _GEN_295; // @[controlUnit.scala 159:23 96:49]
  wire  _GEN_336 = 4'h3 == curr_state ? address_generator_reset : _GEN_296; // @[controlUnit.scala 159:23 97:42]
  wire  _GEN_337 = 4'h3 == curr_state ? weight_buffer_load_request : _GEN_297; // @[controlUnit.scala 159:23 101:45]
  wire  _GEN_338 = 4'h3 == curr_state ? load_weight_buffer_signal : _GEN_298; // @[controlUnit.scala 159:23 157:44]
  wire [1:0] _GEN_339 = 4'h3 == curr_state ? previous_read_memory_usage : _GEN_299; // @[controlUnit.scala 159:23 154:45]
  wire [15:0] _GEN_340 = 4'h3 == curr_state ? current_layer_current_activation : _GEN_300; // @[controlUnit.scala 159:23 132:51]
  wire  _GEN_341 = 4'h3 == curr_state ? 1'h0 : _GEN_301; // @[controlUnit.scala 159:23]
  wire [15:0] _GEN_342 = 4'h3 == curr_state ? current_buffer_memory_pointer : _GEN_302; // @[controlUnit.scala 159:23 135:48]
  wire  _GEN_344 = 4'h2 == curr_state ? 1'h0 : _GEN_341; // @[controlUnit.scala 159:23 193:31]
  wire [3:0] _GEN_345 = 4'h2 == curr_state ? 4'h2 : _GEN_303; // @[controlUnit.scala 159:23 196:45]
  wire [15:0] _GEN_354 = 4'h2 == curr_state ? 16'h0 : _GEN_304; // @[controlUnit.scala 159:23 106:52]
  wire  _GEN_381 = 4'h2 == curr_state ? address_generator_reset : _GEN_336; // @[controlUnit.scala 159:23 97:42]
  wire  _GEN_387 = 4'h1 == curr_state ? 1'h0 : _GEN_344; // @[controlUnit.scala 159:23]
  wire [3:0] _GEN_388 = 4'h1 == curr_state ? 4'h0 : _GEN_345; // @[controlUnit.scala 159:23 112:37]
  wire [15:0] _GEN_396 = 4'h1 == curr_state ? 16'h0 : _GEN_354; // @[controlUnit.scala 159:23 106:52]
  wire  _GEN_423 = 4'h1 == curr_state ? address_generator_reset : _GEN_381; // @[controlUnit.scala 159:23 97:42]
  wire  _GEN_465 = 4'h0 == curr_state ? address_generator_reset : _GEN_423; // @[controlUnit.scala 159:23 97:42]
  assign io_controller_out_load_initial_weights = load_initial_weights_out; // @[controlUnit.scala 484:44]
  assign io_controller_out_load_datapoint = load_datapoint; // @[controlUnit.scala 452:38]
  assign io_controller_out_load_new_request = load_new_request; // @[controlUnit.scala 453:40]
  assign io_controller_out_load_data_from_buffer = load_data_from_buffer; // @[controlUnit.scala 454:45]
  assign io_controller_out_load_same_data = load_same_data; // @[controlUnit.scala 455:38]
  assign io_controller_out_read_memoryUnits_0 = read_memoryUnits_0; // @[controlUnit.scala 458:47]
  assign io_controller_out_read_memoryUnits_1 = read_memoryUnits_1; // @[controlUnit.scala 458:47]
  assign io_controller_out_read_memoryUnits_2 = read_memoryUnits_2; // @[controlUnit.scala 458:47]
  assign io_controller_out_read_memoryUnits_3 = read_memoryUnits_3; // @[controlUnit.scala 458:47]
  assign io_controller_out_max_iteration = current_layer_max_computations; // @[controlUnit.scala 460:37]
  assign io_controller_out_address_generator_address_valid = address_generator_address_valid; // @[controlUnit.scala 463:55]
  assign io_controller_out_address_generator_enable_valid = address_generator_enable_valid; // @[controlUnit.scala 464:54]
  assign io_controller_out_address_generator_reset = address_generator_reset; // @[controlUnit.scala 465:47]
  assign io_controller_out_loading_layer = loading_layer; // @[controlUnit.scala 472:37]
  assign io_controller_out_loading_activations_0 = loading_activations_0; // @[controlUnit.scala 469:50]
  assign io_controller_out_loading_activations_1 = loading_activations_1; // @[controlUnit.scala 469:50]
  assign io_controller_out_loading_activations_2 = loading_activations_2; // @[controlUnit.scala 469:50]
  assign io_controller_out_loading_activations_3 = loading_activations_3; // @[controlUnit.scala 469:50]
  assign io_controller_out_write_memoryUnits_0 = write_memoryUnits_0; // @[controlUnit.scala 468:48]
  assign io_controller_out_write_memoryUnits_1 = write_memoryUnits_1; // @[controlUnit.scala 468:48]
  assign io_controller_out_write_memoryUnits_2 = write_memoryUnits_2; // @[controlUnit.scala 468:48]
  assign io_controller_out_write_memoryUnits_3 = write_memoryUnits_3; // @[controlUnit.scala 468:48]
  assign io_controller_out_weight_buffer_load_request = weight_buffer_load_request; // @[controlUnit.scala 473:50]
  assign io_controller_out_save_data_request = 4'h0 == curr_state ? 1'h0 : _GEN_387; // @[controlUnit.scala 159:23]
  assign io_controller_out_current_buffer_memory_pointer = current_buffer_memory_pointer; // @[controlUnit.scala 478:53]
  assign io_controller_out_current_state = 4'h0 == curr_state ? 4'h1 : _GEN_388; // @[controlUnit.scala 159:23 164:45]
  assign io_controller_out_current_layer_total_activations = current_layer_total_activations; // @[controlUnit.scala 474:55]
  assign io_controller_out_loading_layer_total_activations = loading_layer_total_activations; // @[controlUnit.scala 475:55]
  assign io_controller_out_current_layer = current_layer; // @[controlUnit.scala 480:37]
  assign io_controller_out_iteration_layer = iteration_layer; // @[controlUnit.scala 481:39]
  assign io_controller_out_max_layer = max_layer; // @[controlUnit.scala 482:33]
  assign io_controller_out_current_layer_current_activation = current_layer_current_activation; // @[controlUnit.scala 483:56]
  assign io_controller_out_nn_description_table_address = 4'h0 == curr_state ? 16'h0 : _GEN_396; // @[controlUnit.scala 159:23]
  always @(posedge clock) begin
    if (reset) begin // @[controlUnit.scala 86:43]
      load_initial_weights_out <= 1'h0; // @[controlUnit.scala 86:43]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          load_initial_weights_out <= _GEN_311;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 88:33]
      load_datapoint <= 1'h0; // @[controlUnit.scala 88:33]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          load_datapoint <= _GEN_318;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 89:35]
      load_new_request <= 1'h0; // @[controlUnit.scala 89:35]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (4'h2 == curr_state) begin // @[controlUnit.scala 159:23]
          load_new_request <= _GEN_12;
        end else begin
          load_new_request <= _GEN_319;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 90:40]
      load_data_from_buffer <= 1'h0; // @[controlUnit.scala 90:40]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (4'h2 == curr_state) begin // @[controlUnit.scala 159:23]
          load_data_from_buffer <= _GEN_13;
        end else begin
          load_data_from_buffer <= _GEN_320;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 91:33]
      load_same_data <= 1'h0; // @[controlUnit.scala 91:33]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (4'h2 == curr_state) begin // @[controlUnit.scala 159:23]
          load_same_data <= _GEN_14;
        end else begin
          load_same_data <= _GEN_321;
        end
      end
    end
    if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          if (!(4'h3 == curr_state)) begin // @[controlUnit.scala 159:23]
            read_memoryUnits_0 <= _GEN_282;
          end
        end
      end
    end
    if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          if (!(4'h3 == curr_state)) begin // @[controlUnit.scala 159:23]
            read_memoryUnits_1 <= _GEN_284;
          end
        end
      end
    end
    if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          if (!(4'h3 == curr_state)) begin // @[controlUnit.scala 159:23]
            read_memoryUnits_2 <= _GEN_285;
          end
        end
      end
    end
    if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          if (!(4'h3 == curr_state)) begin // @[controlUnit.scala 159:23]
            read_memoryUnits_3 <= _GEN_286;
          end
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 95:50]
      address_generator_address_valid <= 1'h0; // @[controlUnit.scala 95:50]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          address_generator_address_valid <= _GEN_334;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 96:49]
      address_generator_enable_valid <= 1'h0; // @[controlUnit.scala 96:49]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          address_generator_enable_valid <= _GEN_335;
        end
      end
    end
    address_generator_reset <= reset | _GEN_465; // @[controlUnit.scala 97:{42,42}]
    if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          if (!(4'h3 == curr_state)) begin // @[controlUnit.scala 159:23]
            write_memoryUnits_0 <= _GEN_272;
          end
        end
      end
    end
    if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          if (!(4'h3 == curr_state)) begin // @[controlUnit.scala 159:23]
            write_memoryUnits_1 <= _GEN_273;
          end
        end
      end
    end
    if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          if (!(4'h3 == curr_state)) begin // @[controlUnit.scala 159:23]
            write_memoryUnits_2 <= _GEN_274;
          end
        end
      end
    end
    if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          if (!(4'h3 == curr_state)) begin // @[controlUnit.scala 159:23]
            write_memoryUnits_3 <= _GEN_275;
          end
        end
      end
    end
    if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          if (!(4'h3 == curr_state)) begin // @[controlUnit.scala 159:23]
            loading_activations_0 <= _GEN_289;
          end
        end
      end
    end
    if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          if (!(4'h3 == curr_state)) begin // @[controlUnit.scala 159:23]
            loading_activations_1 <= _GEN_290;
          end
        end
      end
    end
    if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          if (!(4'h3 == curr_state)) begin // @[controlUnit.scala 159:23]
            loading_activations_2 <= _GEN_291;
          end
        end
      end
    end
    if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          if (!(4'h3 == curr_state)) begin // @[controlUnit.scala 159:23]
            loading_activations_3 <= _GEN_292;
          end
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 101:45]
      weight_buffer_load_request <= 1'h0; // @[controlUnit.scala 101:45]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          weight_buffer_load_request <= _GEN_337;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 111:29]
      curr_state <= 4'h0; // @[controlUnit.scala 111:29]
    end else if (4'h0 == curr_state) begin // @[controlUnit.scala 159:23]
      if (io_controller_in_controller_reset) begin // @[controlUnit.scala 168:52]
        curr_state <= 4'h0; // @[controlUnit.scala 169:28]
      end else begin
        curr_state <= 4'h1; // @[controlUnit.scala 172:28]
      end
    end else if (4'h1 == curr_state) begin // @[controlUnit.scala 159:23]
      if (io_controller_in_controller_reset) begin // @[controlUnit.scala 179:52]
        curr_state <= 4'h0; // @[controlUnit.scala 180:28]
      end else begin
        curr_state <= 4'h2; // @[controlUnit.scala 182:28]
      end
    end else if (4'h2 == curr_state) begin // @[controlUnit.scala 159:23]
      curr_state <= _GEN_15;
    end else begin
      curr_state <= _GEN_306;
    end
    if (reset) begin // @[controlUnit.scala 118:32]
      current_layer <= 16'h0; // @[controlUnit.scala 118:32]
    end else if (4'h0 == curr_state) begin // @[controlUnit.scala 159:23]
      current_layer <= 16'h0; // @[controlUnit.scala 166:27]
    end else if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (4'h2 == curr_state) begin // @[controlUnit.scala 159:23]
        current_layer <= _GEN_9;
      end
    end
    if (reset) begin // @[controlUnit.scala 119:34]
      iteration_layer <= 16'h0; // @[controlUnit.scala 119:34]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          iteration_layer <= _GEN_305;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 120:32]
      loading_layer <= 16'h0; // @[controlUnit.scala 120:32]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          loading_layer <= _GEN_308;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 121:28]
      max_layer <= 16'h0; // @[controlUnit.scala 121:28]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (4'h1 == curr_state) begin // @[controlUnit.scala 159:23]
        max_layer <= io_controller_in_nn_description_table_input; // @[controlUnit.scala 178:23]
      end
    end
    if (reset) begin // @[controlUnit.scala 129:50]
      current_layer_total_activations <= 16'h0; // @[controlUnit.scala 129:50]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          current_layer_total_activations <= _GEN_307;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 132:51]
      current_layer_current_activation <= 16'h1; // @[controlUnit.scala 132:51]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (4'h2 == curr_state) begin // @[controlUnit.scala 159:23]
          current_layer_current_activation <= _GEN_10;
        end else begin
          current_layer_current_activation <= _GEN_340;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 133:48]
      current_layer_next_activation <= 16'h1; // @[controlUnit.scala 133:48]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          current_layer_next_activation <= _GEN_327;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 135:48]
      current_buffer_memory_pointer <= 16'h0; // @[controlUnit.scala 135:48]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (4'h2 == curr_state) begin // @[controlUnit.scala 159:23]
          current_buffer_memory_pointer <= _GEN_8;
        end else begin
          current_buffer_memory_pointer <= _GEN_342;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 140:45]
      current_loading_activation <= 16'h1; // @[controlUnit.scala 140:45]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          current_loading_activation <= _GEN_309;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 141:42]
      next_loading_activation <= 16'h1; // @[controlUnit.scala 141:42]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          next_loading_activation <= _GEN_333;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 143:50]
      loading_layer_total_activations <= 16'h0; // @[controlUnit.scala 143:50]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          loading_layer_total_activations <= _GEN_317;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 147:49]
      current_layer_max_computations <= 16'h0; // @[controlUnit.scala 147:49]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          current_layer_max_computations <= _GEN_310;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 152:44]
      current_read_memory_usage <= 2'h0; // @[controlUnit.scala 152:44]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          current_read_memory_usage <= _GEN_323;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 154:45]
      previous_read_memory_usage <= 2'h2; // @[controlUnit.scala 154:45]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (!(4'h2 == curr_state)) begin // @[controlUnit.scala 159:23]
          previous_read_memory_usage <= _GEN_339;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 156:39]
      load_initial_weights <= 1'h0; // @[controlUnit.scala 156:39]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (4'h2 == curr_state) begin // @[controlUnit.scala 159:23]
          load_initial_weights <= _GEN_11;
        end else begin
          load_initial_weights <= _GEN_316;
        end
      end
    end
    if (reset) begin // @[controlUnit.scala 157:44]
      load_weight_buffer_signal <= 1'h0; // @[controlUnit.scala 157:44]
    end else if (!(4'h0 == curr_state)) begin // @[controlUnit.scala 159:23]
      if (!(4'h1 == curr_state)) begin // @[controlUnit.scala 159:23]
        if (4'h2 == curr_state) begin // @[controlUnit.scala 159:23]
          load_weight_buffer_signal <= 1'h0; // @[controlUnit.scala 192:39]
        end else begin
          load_weight_buffer_signal <= _GEN_338;
        end
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
  load_initial_weights_out = _RAND_0[0:0];
  _RAND_1 = {1{`RANDOM}};
  load_datapoint = _RAND_1[0:0];
  _RAND_2 = {1{`RANDOM}};
  load_new_request = _RAND_2[0:0];
  _RAND_3 = {1{`RANDOM}};
  load_data_from_buffer = _RAND_3[0:0];
  _RAND_4 = {1{`RANDOM}};
  load_same_data = _RAND_4[0:0];
  _RAND_5 = {1{`RANDOM}};
  read_memoryUnits_0 = _RAND_5[1:0];
  _RAND_6 = {1{`RANDOM}};
  read_memoryUnits_1 = _RAND_6[1:0];
  _RAND_7 = {1{`RANDOM}};
  read_memoryUnits_2 = _RAND_7[1:0];
  _RAND_8 = {1{`RANDOM}};
  read_memoryUnits_3 = _RAND_8[1:0];
  _RAND_9 = {1{`RANDOM}};
  address_generator_address_valid = _RAND_9[0:0];
  _RAND_10 = {1{`RANDOM}};
  address_generator_enable_valid = _RAND_10[0:0];
  _RAND_11 = {1{`RANDOM}};
  address_generator_reset = _RAND_11[0:0];
  _RAND_12 = {1{`RANDOM}};
  write_memoryUnits_0 = _RAND_12[1:0];
  _RAND_13 = {1{`RANDOM}};
  write_memoryUnits_1 = _RAND_13[1:0];
  _RAND_14 = {1{`RANDOM}};
  write_memoryUnits_2 = _RAND_14[1:0];
  _RAND_15 = {1{`RANDOM}};
  write_memoryUnits_3 = _RAND_15[1:0];
  _RAND_16 = {1{`RANDOM}};
  loading_activations_0 = _RAND_16[15:0];
  _RAND_17 = {1{`RANDOM}};
  loading_activations_1 = _RAND_17[15:0];
  _RAND_18 = {1{`RANDOM}};
  loading_activations_2 = _RAND_18[15:0];
  _RAND_19 = {1{`RANDOM}};
  loading_activations_3 = _RAND_19[15:0];
  _RAND_20 = {1{`RANDOM}};
  weight_buffer_load_request = _RAND_20[0:0];
  _RAND_21 = {1{`RANDOM}};
  curr_state = _RAND_21[3:0];
  _RAND_22 = {1{`RANDOM}};
  current_layer = _RAND_22[15:0];
  _RAND_23 = {1{`RANDOM}};
  iteration_layer = _RAND_23[15:0];
  _RAND_24 = {1{`RANDOM}};
  loading_layer = _RAND_24[15:0];
  _RAND_25 = {1{`RANDOM}};
  max_layer = _RAND_25[15:0];
  _RAND_26 = {1{`RANDOM}};
  current_layer_total_activations = _RAND_26[15:0];
  _RAND_27 = {1{`RANDOM}};
  current_layer_current_activation = _RAND_27[15:0];
  _RAND_28 = {1{`RANDOM}};
  current_layer_next_activation = _RAND_28[15:0];
  _RAND_29 = {1{`RANDOM}};
  current_buffer_memory_pointer = _RAND_29[15:0];
  _RAND_30 = {1{`RANDOM}};
  current_loading_activation = _RAND_30[15:0];
  _RAND_31 = {1{`RANDOM}};
  next_loading_activation = _RAND_31[15:0];
  _RAND_32 = {1{`RANDOM}};
  loading_layer_total_activations = _RAND_32[15:0];
  _RAND_33 = {1{`RANDOM}};
  current_layer_max_computations = _RAND_33[15:0];
  _RAND_34 = {1{`RANDOM}};
  current_read_memory_usage = _RAND_34[1:0];
  _RAND_35 = {1{`RANDOM}};
  previous_read_memory_usage = _RAND_35[1:0];
  _RAND_36 = {1{`RANDOM}};
  load_initial_weights = _RAND_36[0:0];
  _RAND_37 = {1{`RANDOM}};
  load_weight_buffer_signal = _RAND_37[0:0];
`endif // RANDOMIZE_REG_INIT
  `endif // RANDOMIZE
end // initial
`ifdef FIRRTL_AFTER_INITIAL
`FIRRTL_AFTER_INITIAL
`endif
`endif // SYNTHESIS
endmodule
