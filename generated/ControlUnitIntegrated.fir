circuit ControlUnitIntegrated :
  module Controller :
    input clock : Clock
    input reset : Reset
    output io : { controller_in : { flip controller_reset : UInt<1>, flip address_generation_complete : UInt<1>, flip loading_initial_weights_complete : UInt<1>, flip load_datapoint_complete : UInt<1>, flip load_buffer_weight_memory_complete : UInt<1>, flip save_data_complete : UInt<1>, flip nn_description_table_input : UInt<16>}, controller_out : { load_initial_weights : UInt<1>, load_datapoint : UInt<1>, load_new_request : UInt<1>, load_data_from_buffer : UInt<1>, load_same_data : UInt<1>, read_memoryUnits : UInt<2>[4], max_iteration : UInt<16>, address_generator_address_valid : UInt<1>, address_generator_enable_valid : UInt<1>, address_generator_reset : UInt<1>, loading_layer : UInt<16>, loading_activations : UInt<16>[4], write_memoryUnits : UInt<2>[4], weight_buffer_load_request : UInt<1>, save_data_request : UInt<1>, current_buffer_memory_pointer : UInt<16>, current_state : UInt<4>, current_layer_total_activations : UInt<16>, loading_layer_total_activations : UInt<16>, current_layer : UInt<16>, iteration_layer : UInt<16>, max_layer : UInt<16>, current_layer_current_activation : UInt<16>, nn_description_table_address : UInt<16>}}

    reg load_initial_weights_out : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[controlUnit.scala 86:43]
    reg load_datapoint : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[controlUnit.scala 88:33]
    reg load_new_request : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[controlUnit.scala 89:35]
    reg load_data_from_buffer : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[controlUnit.scala 90:40]
    reg load_same_data : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[controlUnit.scala 91:33]
    reg read_memoryUnits : UInt<2>[4], clock with :
      reset => (UInt<1>("h0"), read_memoryUnits) @[controlUnit.scala 93:31]
    reg address_generator_address_valid : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[controlUnit.scala 95:50]
    reg address_generator_enable_valid : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[controlUnit.scala 96:49]
    reg address_generator_reset : UInt<1>, clock with :
      reset => (reset, UInt<1>("h1")) @[controlUnit.scala 97:42]
    reg write_memoryUnits : UInt<2>[4], clock with :
      reset => (UInt<1>("h0"), write_memoryUnits) @[controlUnit.scala 99:32]
    reg loading_activations : UInt<16>[4], clock with :
      reset => (UInt<1>("h0"), loading_activations) @[controlUnit.scala 100:34]
    reg weight_buffer_load_request : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[controlUnit.scala 101:45]
    wire save_data_request : UInt<1>
    save_data_request <= UInt<1>("h0")
    io.controller_out.nn_description_table_address <= UInt<16>("h0") @[controlUnit.scala 106:52]
    reg curr_state : UInt<4>, clock with :
      reset => (reset, UInt<4>("h0")) @[controlUnit.scala 111:29]
    io.controller_out.current_state <= UInt<4>("h0") @[controlUnit.scala 112:37]
    reg current_layer : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[controlUnit.scala 118:32]
    reg iteration_layer : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[controlUnit.scala 119:34]
    reg loading_layer : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[controlUnit.scala 120:32]
    reg max_layer : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[controlUnit.scala 121:28]
    reg current_layer_total_activations : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[controlUnit.scala 129:50]
    reg current_layer_current_activation : UInt<16>, clock with :
      reset => (reset, UInt<16>("h1")) @[controlUnit.scala 132:51]
    reg current_layer_next_activation : UInt<16>, clock with :
      reset => (reset, UInt<16>("h1")) @[controlUnit.scala 133:48]
    reg current_buffer_memory_pointer : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[controlUnit.scala 135:48]
    reg current_loading_activation : UInt<16>, clock with :
      reset => (reset, UInt<16>("h1")) @[controlUnit.scala 140:45]
    reg next_loading_activation : UInt<16>, clock with :
      reset => (reset, UInt<16>("h1")) @[controlUnit.scala 141:42]
    reg loading_layer_total_activations : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[controlUnit.scala 143:50]
    reg current_layer_max_computations : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[controlUnit.scala 147:49]
    reg current_read_memory_usage : UInt<2>, clock with :
      reset => (reset, UInt<2>("h0")) @[controlUnit.scala 152:44]
    reg current_write_memory_usage : UInt<2>, clock with :
      reset => (reset, UInt<2>("h0")) @[controlUnit.scala 153:45]
    reg previous_read_memory_usage : UInt<2>, clock with :
      reset => (reset, UInt<2>("h2")) @[controlUnit.scala 154:45]
    reg load_initial_weights : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[controlUnit.scala 156:39]
    reg load_weight_buffer_signal : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[controlUnit.scala 157:44]
    node _T = eq(UInt<4>("h0"), curr_state) @[controlUnit.scala 159:23]
    when _T : @[controlUnit.scala 159:23]
      io.controller_out.current_state <= UInt<4>("h1") @[controlUnit.scala 164:45]
      current_layer <= UInt<16>("h0") @[controlUnit.scala 166:27]
      when io.controller_in.controller_reset : @[controlUnit.scala 168:52]
        curr_state <= UInt<4>("h0") @[controlUnit.scala 169:28]
      else :
        io.controller_out.nn_description_table_address <= UInt<16>("h0") @[controlUnit.scala 171:64]
        curr_state <= UInt<4>("h1") @[controlUnit.scala 172:28]
    else :
      node _T_1 = eq(UInt<4>("h1"), curr_state) @[controlUnit.scala 159:23]
      when _T_1 : @[controlUnit.scala 159:23]
        max_layer <= io.controller_in.nn_description_table_input @[controlUnit.scala 178:23]
        when io.controller_in.controller_reset : @[controlUnit.scala 179:52]
          curr_state <= UInt<4>("h0") @[controlUnit.scala 180:28]
        else :
          curr_state <= UInt<4>("h2") @[controlUnit.scala 182:28]
      else :
        node _T_2 = eq(UInt<4>("h2"), curr_state) @[controlUnit.scala 159:23]
        when _T_2 : @[controlUnit.scala 159:23]
          load_weight_buffer_signal <= UInt<1>("h0") @[controlUnit.scala 192:39]
          save_data_request <= UInt<1>("h0") @[controlUnit.scala 193:31]
          io.controller_out.current_state <= UInt<4>("h2") @[controlUnit.scala 196:45]
          node _T_3 = eq(current_layer, UInt<16>("h0")) @[controlUnit.scala 198:33]
          node _T_4 = eq(current_layer, max_layer) @[controlUnit.scala 198:73]
          node _T_5 = gt(current_layer_current_activation, current_layer_total_activations) @[controlUnit.scala 198:122]
          node _T_6 = and(_T_4, _T_5) @[controlUnit.scala 198:87]
          node _T_7 = or(_T_3, _T_6) @[controlUnit.scala 198:55]
          when _T_7 : @[controlUnit.scala 198:157]
            current_buffer_memory_pointer <= UInt<16>("h0") @[controlUnit.scala 199:47]
            current_layer <= UInt<16>("h2") @[controlUnit.scala 200:31]
            current_layer_current_activation <= UInt<16>("h1") @[controlUnit.scala 201:50]
            load_initial_weights <= UInt<1>("h1") @[controlUnit.scala 202:38]
            load_new_request <= UInt<1>("h1") @[controlUnit.scala 204:34]
          else :
            node _T_8 = gt(current_layer_current_activation, current_layer_total_activations) @[controlUnit.scala 206:58]
            when _T_8 : @[controlUnit.scala 206:92]
              node _current_layer_T = add(current_layer, UInt<16>("h1")) @[controlUnit.scala 207:48]
              node _current_layer_T_1 = tail(_current_layer_T, 1) @[controlUnit.scala 207:48]
              current_layer <= _current_layer_T_1 @[controlUnit.scala 207:31]
              current_layer_current_activation <= UInt<16>("h1") @[controlUnit.scala 208:50]
              current_buffer_memory_pointer <= UInt<16>("h0") @[controlUnit.scala 209:47]
              load_data_from_buffer <= UInt<1>("h1") @[controlUnit.scala 211:39]
            else :
              current_buffer_memory_pointer <= current_buffer_memory_pointer @[controlUnit.scala 214:47]
              load_same_data <= UInt<1>("h1") @[controlUnit.scala 216:32]
          when io.controller_in.controller_reset : @[controlUnit.scala 219:52]
            curr_state <= UInt<4>("h0") @[controlUnit.scala 220:28]
          else :
            curr_state <= UInt<4>("h3") @[controlUnit.scala 222:28]
        else :
          node _T_9 = eq(UInt<4>("h3"), curr_state) @[controlUnit.scala 159:23]
          when _T_9 : @[controlUnit.scala 159:23]
            io.controller_out.current_state <= UInt<4>("h3") @[controlUnit.scala 231:45]
            io.controller_out.nn_description_table_address <= current_layer @[controlUnit.scala 233:60]
            node _iteration_layer_T = sub(current_layer, UInt<16>("h1")) @[controlUnit.scala 234:46]
            node _iteration_layer_T_1 = tail(_iteration_layer_T, 1) @[controlUnit.scala 234:46]
            iteration_layer <= _iteration_layer_T_1 @[controlUnit.scala 234:29]
            when io.controller_in.controller_reset : @[controlUnit.scala 236:52]
              curr_state <= UInt<4>("h0") @[controlUnit.scala 237:28]
            else :
              curr_state <= UInt<4>("h4") @[controlUnit.scala 239:28]
          else :
            node _T_10 = eq(UInt<4>("h4"), curr_state) @[controlUnit.scala 159:23]
            when _T_10 : @[controlUnit.scala 159:23]
              current_layer_total_activations <= io.controller_in.nn_description_table_input @[controlUnit.scala 244:45]
              when io.controller_in.controller_reset : @[controlUnit.scala 246:52]
                curr_state <= UInt<4>("h0") @[controlUnit.scala 247:28]
              else :
                curr_state <= UInt<4>("h5") @[controlUnit.scala 249:28]
            else :
              node _T_11 = eq(UInt<4>("h5"), curr_state) @[controlUnit.scala 159:23]
              when _T_11 : @[controlUnit.scala 159:23]
                io.controller_out.current_state <= UInt<4>("h4") @[controlUnit.scala 260:45]
                io.controller_out.nn_description_table_address <= iteration_layer @[controlUnit.scala 262:60]
                node _T_12 = add(current_layer_current_activation, UInt<16>("h4")) @[controlUnit.scala 264:52]
                node _T_13 = tail(_T_12, 1) @[controlUnit.scala 264:52]
                node _T_14 = gt(_T_13, current_layer_total_activations) @[controlUnit.scala 264:80]
                node _T_15 = eq(current_layer, max_layer) @[controlUnit.scala 264:131]
                node _T_16 = and(_T_14, _T_15) @[controlUnit.scala 264:114]
                when _T_16 : @[controlUnit.scala 264:145]
                  loading_layer <= UInt<16>("h2") @[controlUnit.scala 265:31]
                  current_loading_activation <= UInt<16>("h1") @[controlUnit.scala 266:44]
                else :
                  node _T_17 = add(current_layer_current_activation, UInt<16>("h4")) @[controlUnit.scala 268:58]
                  node _T_18 = tail(_T_17, 1) @[controlUnit.scala 268:58]
                  node _T_19 = gt(_T_18, current_layer_total_activations) @[controlUnit.scala 268:86]
                  when _T_19 : @[controlUnit.scala 268:120]
                    node _loading_layer_T = add(current_layer, UInt<16>("h1")) @[controlUnit.scala 269:48]
                    node _loading_layer_T_1 = tail(_loading_layer_T, 1) @[controlUnit.scala 269:48]
                    loading_layer <= _loading_layer_T_1 @[controlUnit.scala 269:31]
                    current_loading_activation <= UInt<16>("h1") @[controlUnit.scala 270:44]
                  else :
                    loading_layer <= current_layer @[controlUnit.scala 273:31]
                    node _current_loading_activation_T = add(current_layer_current_activation, UInt<16>("h4")) @[controlUnit.scala 274:80]
                    node _current_loading_activation_T_1 = tail(_current_loading_activation_T, 1) @[controlUnit.scala 274:80]
                    current_loading_activation <= _current_loading_activation_T_1 @[controlUnit.scala 274:44]
                when io.controller_in.controller_reset : @[controlUnit.scala 277:52]
                  curr_state <= UInt<4>("h0") @[controlUnit.scala 278:28]
                else :
                  curr_state <= UInt<4>("h6") @[controlUnit.scala 280:28]
              else :
                node _T_20 = eq(UInt<4>("h6"), curr_state) @[controlUnit.scala 159:23]
                when _T_20 : @[controlUnit.scala 159:23]
                  io.controller_out.current_state <= UInt<4>("h5") @[controlUnit.scala 287:45]
                  node _current_layer_max_computations_T = add(io.controller_in.nn_description_table_input, UInt<16>("h1")) @[controlUnit.scala 289:91]
                  node _current_layer_max_computations_T_1 = tail(_current_layer_max_computations_T, 1) @[controlUnit.scala 289:91]
                  current_layer_max_computations <= _current_layer_max_computations_T_1 @[controlUnit.scala 289:44]
                  io.controller_out.nn_description_table_address <= loading_layer @[controlUnit.scala 290:60]
                  when io.controller_in.controller_reset : @[controlUnit.scala 294:52]
                    curr_state <= UInt<4>("h0") @[controlUnit.scala 295:28]
                  else :
                    node _T_21 = bits(load_initial_weights, 0, 0) @[controlUnit.scala 296:46]
                    when _T_21 : @[controlUnit.scala 296:53]
                      curr_state <= UInt<4>("h8") @[controlUnit.scala 297:28]
                    else :
                      curr_state <= UInt<4>("h7") @[controlUnit.scala 299:28]
                else :
                  node _T_22 = eq(UInt<4>("h8"), curr_state) @[controlUnit.scala 159:23]
                  when _T_22 : @[controlUnit.scala 159:23]
                    load_initial_weights_out <= UInt<1>("h1") @[controlUnit.scala 306:38]
                    node _T_23 = geq(UInt<16>("h0"), current_layer_total_activations) @[controlUnit.scala 309:39]
                    when _T_23 : @[controlUnit.scala 309:74]
                      write_memoryUnits[0] <= UInt<2>("h0") @[controlUnit.scala 310:42]
                    else :
                      write_memoryUnits[0] <= UInt<2>("h1") @[controlUnit.scala 312:42]
                    node _T_24 = geq(UInt<16>("h1"), current_layer_total_activations) @[controlUnit.scala 309:39]
                    when _T_24 : @[controlUnit.scala 309:74]
                      write_memoryUnits[1] <= UInt<2>("h0") @[controlUnit.scala 310:42]
                    else :
                      write_memoryUnits[1] <= UInt<2>("h1") @[controlUnit.scala 312:42]
                    node _T_25 = geq(UInt<16>("h2"), current_layer_total_activations) @[controlUnit.scala 309:39]
                    when _T_25 : @[controlUnit.scala 309:74]
                      write_memoryUnits[2] <= UInt<2>("h0") @[controlUnit.scala 310:42]
                    else :
                      write_memoryUnits[2] <= UInt<2>("h1") @[controlUnit.scala 312:42]
                    node _T_26 = geq(UInt<16>("h3"), current_layer_total_activations) @[controlUnit.scala 309:39]
                    when _T_26 : @[controlUnit.scala 309:74]
                      write_memoryUnits[3] <= UInt<2>("h0") @[controlUnit.scala 310:42]
                    else :
                      write_memoryUnits[3] <= UInt<2>("h1") @[controlUnit.scala 312:42]
                    load_initial_weights <= UInt<1>("h0") @[controlUnit.scala 316:34]
                    when io.controller_in.controller_reset : @[controlUnit.scala 317:52]
                      curr_state <= UInt<4>("h0") @[controlUnit.scala 318:28]
                    else :
                      when io.controller_in.loading_initial_weights_complete : @[controlUnit.scala 319:75]
                        curr_state <= UInt<4>("h7") @[controlUnit.scala 320:28]
                  else :
                    node _T_27 = eq(UInt<4>("h7"), curr_state) @[controlUnit.scala 159:23]
                    when _T_27 : @[controlUnit.scala 159:23]
                      load_initial_weights_out <= UInt<1>("h0") @[controlUnit.scala 326:38]
                      io.controller_out.current_state <= UInt<4>("h6") @[controlUnit.scala 329:45]
                      loading_layer_total_activations <= io.controller_in.nn_description_table_input @[controlUnit.scala 330:45]
                      load_datapoint <= UInt<1>("h1") @[controlUnit.scala 331:28]
                      when io.controller_in.controller_reset : @[controlUnit.scala 333:52]
                        curr_state <= UInt<4>("h0") @[controlUnit.scala 334:28]
                      else :
                        when io.controller_in.load_datapoint_complete : @[controlUnit.scala 335:66]
                          curr_state <= UInt<4>("h9") @[controlUnit.scala 336:28]
                    else :
                      node _T_28 = eq(UInt<4>("h9"), curr_state) @[controlUnit.scala 159:23]
                      when _T_28 : @[controlUnit.scala 159:23]
                        io.controller_out.current_state <= UInt<4>("h7") @[controlUnit.scala 351:45]
                        load_datapoint <= UInt<1>("h0") @[controlUnit.scala 353:28]
                        load_new_request <= UInt<1>("h0") @[controlUnit.scala 354:30]
                        load_data_from_buffer <= UInt<1>("h0") @[controlUnit.scala 355:35]
                        load_same_data <= UInt<1>("h0") @[controlUnit.scala 356:28]
                        node _T_29 = add(current_layer_current_activation, UInt<16>("h0")) @[controlUnit.scala 360:55]
                        node _T_30 = tail(_T_29, 1) @[controlUnit.scala 360:55]
                        node _T_31 = gt(_T_30, current_layer_total_activations) @[controlUnit.scala 360:74]
                        when _T_31 : @[controlUnit.scala 360:108]
                          read_memoryUnits[0] <= UInt<2>("h0") @[controlUnit.scala 361:41]
                        else :
                          node _T_32 = eq(previous_read_memory_usage, UInt<2>("h1")) @[controlUnit.scala 364:53]
                          when _T_32 : @[controlUnit.scala 364:70]
                            current_read_memory_usage <= UInt<2>("h2") @[controlUnit.scala 365:51]
                            read_memoryUnits[0] <= UInt<2>("h2") @[controlUnit.scala 366:45]
                          else :
                            current_read_memory_usage <= UInt<2>("h1") @[controlUnit.scala 368:51]
                            read_memoryUnits[0] <= UInt<2>("h1") @[controlUnit.scala 369:45]
                        node _T_33 = add(current_layer_current_activation, UInt<16>("h1")) @[controlUnit.scala 360:55]
                        node _T_34 = tail(_T_33, 1) @[controlUnit.scala 360:55]
                        node _T_35 = gt(_T_34, current_layer_total_activations) @[controlUnit.scala 360:74]
                        when _T_35 : @[controlUnit.scala 360:108]
                          read_memoryUnits[1] <= UInt<2>("h0") @[controlUnit.scala 361:41]
                        else :
                          node _T_36 = eq(previous_read_memory_usage, UInt<2>("h1")) @[controlUnit.scala 364:53]
                          when _T_36 : @[controlUnit.scala 364:70]
                            current_read_memory_usage <= UInt<2>("h2") @[controlUnit.scala 365:51]
                            read_memoryUnits[1] <= UInt<2>("h2") @[controlUnit.scala 366:45]
                          else :
                            current_read_memory_usage <= UInt<2>("h1") @[controlUnit.scala 368:51]
                            read_memoryUnits[1] <= UInt<2>("h1") @[controlUnit.scala 369:45]
                        node _T_37 = add(current_layer_current_activation, UInt<16>("h2")) @[controlUnit.scala 360:55]
                        node _T_38 = tail(_T_37, 1) @[controlUnit.scala 360:55]
                        node _T_39 = gt(_T_38, current_layer_total_activations) @[controlUnit.scala 360:74]
                        when _T_39 : @[controlUnit.scala 360:108]
                          read_memoryUnits[2] <= UInt<2>("h0") @[controlUnit.scala 361:41]
                        else :
                          node _T_40 = eq(previous_read_memory_usage, UInt<2>("h1")) @[controlUnit.scala 364:53]
                          when _T_40 : @[controlUnit.scala 364:70]
                            current_read_memory_usage <= UInt<2>("h2") @[controlUnit.scala 365:51]
                            read_memoryUnits[2] <= UInt<2>("h2") @[controlUnit.scala 366:45]
                          else :
                            current_read_memory_usage <= UInt<2>("h1") @[controlUnit.scala 368:51]
                            read_memoryUnits[2] <= UInt<2>("h1") @[controlUnit.scala 369:45]
                        node _T_41 = add(current_layer_current_activation, UInt<16>("h3")) @[controlUnit.scala 360:55]
                        node _T_42 = tail(_T_41, 1) @[controlUnit.scala 360:55]
                        node _T_43 = gt(_T_42, current_layer_total_activations) @[controlUnit.scala 360:74]
                        when _T_43 : @[controlUnit.scala 360:108]
                          read_memoryUnits[3] <= UInt<2>("h0") @[controlUnit.scala 361:41]
                        else :
                          node _T_44 = eq(previous_read_memory_usage, UInt<2>("h1")) @[controlUnit.scala 364:53]
                          when _T_44 : @[controlUnit.scala 364:70]
                            current_read_memory_usage <= UInt<2>("h2") @[controlUnit.scala 365:51]
                            read_memoryUnits[3] <= UInt<2>("h2") @[controlUnit.scala 366:45]
                          else :
                            current_read_memory_usage <= UInt<2>("h1") @[controlUnit.scala 368:51]
                            read_memoryUnits[3] <= UInt<2>("h1") @[controlUnit.scala 369:45]
                        node _current_layer_next_activation_T = add(current_layer_current_activation, UInt<16>("h4")) @[controlUnit.scala 374:79]
                        node _current_layer_next_activation_T_1 = tail(_current_layer_next_activation_T, 1) @[controlUnit.scala 374:79]
                        current_layer_next_activation <= _current_layer_next_activation_T_1 @[controlUnit.scala 374:43]
                        node _T_45 = add(current_loading_activation, UInt<16>("h0")) @[controlUnit.scala 378:49]
                        node _T_46 = tail(_T_45, 1) @[controlUnit.scala 378:49]
                        node _T_47 = gt(_T_46, loading_layer_total_activations) @[controlUnit.scala 378:68]
                        when _T_47 : @[controlUnit.scala 378:102]
                          write_memoryUnits[0] <= UInt<2>("h0") @[controlUnit.scala 379:42]
                        else :
                          node _T_48 = eq(previous_read_memory_usage, UInt<2>("h2")) @[controlUnit.scala 381:53]
                          when _T_48 : @[controlUnit.scala 381:70]
                            current_write_memory_usage <= UInt<2>("h2") @[controlUnit.scala 382:52]
                            write_memoryUnits[0] <= UInt<2>("h2") @[controlUnit.scala 383:46]
                          else :
                            current_write_memory_usage <= UInt<2>("h1") @[controlUnit.scala 386:52]
                            write_memoryUnits[0] <= UInt<2>("h1") @[controlUnit.scala 387:46]
                          node _loading_activations_0_T = add(current_loading_activation, UInt<16>("h0")) @[controlUnit.scala 390:74]
                          node _loading_activations_0_T_1 = tail(_loading_activations_0_T, 1) @[controlUnit.scala 390:74]
                          loading_activations[0] <= _loading_activations_0_T_1 @[controlUnit.scala 390:44]
                        node _T_49 = add(current_loading_activation, UInt<16>("h1")) @[controlUnit.scala 378:49]
                        node _T_50 = tail(_T_49, 1) @[controlUnit.scala 378:49]
                        node _T_51 = gt(_T_50, loading_layer_total_activations) @[controlUnit.scala 378:68]
                        when _T_51 : @[controlUnit.scala 378:102]
                          write_memoryUnits[1] <= UInt<2>("h0") @[controlUnit.scala 379:42]
                        else :
                          node _T_52 = eq(previous_read_memory_usage, UInt<2>("h2")) @[controlUnit.scala 381:53]
                          when _T_52 : @[controlUnit.scala 381:70]
                            current_write_memory_usage <= UInt<2>("h2") @[controlUnit.scala 382:52]
                            write_memoryUnits[1] <= UInt<2>("h2") @[controlUnit.scala 383:46]
                          else :
                            current_write_memory_usage <= UInt<2>("h1") @[controlUnit.scala 386:52]
                            write_memoryUnits[1] <= UInt<2>("h1") @[controlUnit.scala 387:46]
                          node _loading_activations_1_T = add(current_loading_activation, UInt<16>("h1")) @[controlUnit.scala 390:74]
                          node _loading_activations_1_T_1 = tail(_loading_activations_1_T, 1) @[controlUnit.scala 390:74]
                          loading_activations[1] <= _loading_activations_1_T_1 @[controlUnit.scala 390:44]
                        node _T_53 = add(current_loading_activation, UInt<16>("h2")) @[controlUnit.scala 378:49]
                        node _T_54 = tail(_T_53, 1) @[controlUnit.scala 378:49]
                        node _T_55 = gt(_T_54, loading_layer_total_activations) @[controlUnit.scala 378:68]
                        when _T_55 : @[controlUnit.scala 378:102]
                          write_memoryUnits[2] <= UInt<2>("h0") @[controlUnit.scala 379:42]
                        else :
                          node _T_56 = eq(previous_read_memory_usage, UInt<2>("h2")) @[controlUnit.scala 381:53]
                          when _T_56 : @[controlUnit.scala 381:70]
                            current_write_memory_usage <= UInt<2>("h2") @[controlUnit.scala 382:52]
                            write_memoryUnits[2] <= UInt<2>("h2") @[controlUnit.scala 383:46]
                          else :
                            current_write_memory_usage <= UInt<2>("h1") @[controlUnit.scala 386:52]
                            write_memoryUnits[2] <= UInt<2>("h1") @[controlUnit.scala 387:46]
                          node _loading_activations_2_T = add(current_loading_activation, UInt<16>("h2")) @[controlUnit.scala 390:74]
                          node _loading_activations_2_T_1 = tail(_loading_activations_2_T, 1) @[controlUnit.scala 390:74]
                          loading_activations[2] <= _loading_activations_2_T_1 @[controlUnit.scala 390:44]
                        node _T_57 = add(current_loading_activation, UInt<16>("h3")) @[controlUnit.scala 378:49]
                        node _T_58 = tail(_T_57, 1) @[controlUnit.scala 378:49]
                        node _T_59 = gt(_T_58, loading_layer_total_activations) @[controlUnit.scala 378:68]
                        when _T_59 : @[controlUnit.scala 378:102]
                          write_memoryUnits[3] <= UInt<2>("h0") @[controlUnit.scala 379:42]
                        else :
                          node _T_60 = eq(previous_read_memory_usage, UInt<2>("h2")) @[controlUnit.scala 381:53]
                          when _T_60 : @[controlUnit.scala 381:70]
                            current_write_memory_usage <= UInt<2>("h2") @[controlUnit.scala 382:52]
                            write_memoryUnits[3] <= UInt<2>("h2") @[controlUnit.scala 383:46]
                          else :
                            current_write_memory_usage <= UInt<2>("h1") @[controlUnit.scala 386:52]
                            write_memoryUnits[3] <= UInt<2>("h1") @[controlUnit.scala 387:46]
                          node _loading_activations_3_T = add(current_loading_activation, UInt<16>("h3")) @[controlUnit.scala 390:74]
                          node _loading_activations_3_T_1 = tail(_loading_activations_3_T, 1) @[controlUnit.scala 390:74]
                          loading_activations[3] <= _loading_activations_3_T_1 @[controlUnit.scala 390:44]
                        node _next_loading_activation_T = add(current_loading_activation, UInt<16>("h4")) @[controlUnit.scala 394:67]
                        node _next_loading_activation_T_1 = tail(_next_loading_activation_T, 1) @[controlUnit.scala 394:67]
                        next_loading_activation <= _next_loading_activation_T_1 @[controlUnit.scala 394:37]
                        address_generator_address_valid <= UInt<1>("h1") @[controlUnit.scala 397:45]
                        address_generator_enable_valid <= UInt<1>("h1") @[controlUnit.scala 398:44]
                        address_generator_reset <= UInt<1>("h0") @[controlUnit.scala 399:37]
                        weight_buffer_load_request <= UInt<1>("h1") @[controlUnit.scala 401:40]
                        when io.controller_in.load_buffer_weight_memory_complete : @[controlUnit.scala 404:70]
                          load_weight_buffer_signal <= UInt<1>("h1") @[controlUnit.scala 405:43]
                        when io.controller_in.controller_reset : @[controlUnit.scala 408:52]
                          curr_state <= UInt<4>("h0") @[controlUnit.scala 409:28]
                        else :
                          when io.controller_in.address_generation_complete : @[controlUnit.scala 410:70]
                            curr_state <= UInt<4>("ha") @[controlUnit.scala 411:28]
                      else :
                        node _T_61 = eq(UInt<4>("ha"), curr_state) @[controlUnit.scala 159:23]
                        when _T_61 : @[controlUnit.scala 159:23]
                          previous_read_memory_usage <= current_read_memory_usage @[controlUnit.scala 418:40]
                          current_layer_current_activation <= current_layer_next_activation @[controlUnit.scala 419:46]
                          current_loading_activation <= next_loading_activation @[controlUnit.scala 420:40]
                          io.controller_out.current_state <= UInt<4>("h8") @[controlUnit.scala 422:45]
                          read_memoryUnits[0] <= UInt<2>("h0") @[controlUnit.scala 425:37]
                          write_memoryUnits[0] <= UInt<2>("h0") @[controlUnit.scala 426:38]
                          read_memoryUnits[1] <= UInt<2>("h0") @[controlUnit.scala 425:37]
                          write_memoryUnits[1] <= UInt<2>("h0") @[controlUnit.scala 426:38]
                          read_memoryUnits[2] <= UInt<2>("h0") @[controlUnit.scala 425:37]
                          write_memoryUnits[2] <= UInt<2>("h0") @[controlUnit.scala 426:38]
                          read_memoryUnits[3] <= UInt<2>("h0") @[controlUnit.scala 425:37]
                          write_memoryUnits[3] <= UInt<2>("h0") @[controlUnit.scala 426:38]
                          address_generator_reset <= UInt<1>("h1") @[controlUnit.scala 429:37]
                          address_generator_address_valid <= UInt<1>("h0") @[controlUnit.scala 430:45]
                          address_generator_enable_valid <= UInt<1>("h0") @[controlUnit.scala 431:44]
                          weight_buffer_load_request <= UInt<1>("h0") @[controlUnit.scala 433:40]
                          save_data_request <= UInt<1>("h1") @[controlUnit.scala 435:31]
                          when io.controller_in.load_buffer_weight_memory_complete : @[controlUnit.scala 437:70]
                            load_weight_buffer_signal <= UInt<1>("h1") @[controlUnit.scala 438:43]
                          when io.controller_in.controller_reset : @[controlUnit.scala 441:52]
                            curr_state <= UInt<4>("h0") @[controlUnit.scala 442:28]
                          else :
                            node _T_62 = bits(load_weight_buffer_signal, 0, 0) @[controlUnit.scala 443:52]
                            node _T_63 = or(_T_62, io.controller_in.load_buffer_weight_memory_complete) @[controlUnit.scala 443:59]
                            node _T_64 = and(_T_63, io.controller_in.save_data_complete) @[controlUnit.scala 443:115]
                            when _T_64 : @[controlUnit.scala 443:154]
                              node _current_buffer_memory_pointer_T = add(current_buffer_memory_pointer, UInt<16>("h4")) @[controlUnit.scala 444:80]
                              node _current_buffer_memory_pointer_T_1 = tail(_current_buffer_memory_pointer_T, 1) @[controlUnit.scala 444:80]
                              current_buffer_memory_pointer <= _current_buffer_memory_pointer_T_1 @[controlUnit.scala 444:47]
                              curr_state <= UInt<4>("h2") @[controlUnit.scala 445:28]
    io.controller_out.load_datapoint <= load_datapoint @[controlUnit.scala 452:38]
    io.controller_out.load_new_request <= load_new_request @[controlUnit.scala 453:40]
    io.controller_out.load_data_from_buffer <= load_data_from_buffer @[controlUnit.scala 454:45]
    io.controller_out.load_same_data <= load_same_data @[controlUnit.scala 455:38]
    io.controller_out.read_memoryUnits[0] <= read_memoryUnits[0] @[controlUnit.scala 458:47]
    io.controller_out.read_memoryUnits[1] <= read_memoryUnits[1] @[controlUnit.scala 458:47]
    io.controller_out.read_memoryUnits[2] <= read_memoryUnits[2] @[controlUnit.scala 458:47]
    io.controller_out.read_memoryUnits[3] <= read_memoryUnits[3] @[controlUnit.scala 458:47]
    io.controller_out.max_iteration <= current_layer_max_computations @[controlUnit.scala 460:37]
    io.controller_out.address_generator_address_valid <= address_generator_address_valid @[controlUnit.scala 463:55]
    io.controller_out.address_generator_enable_valid <= address_generator_enable_valid @[controlUnit.scala 464:54]
    io.controller_out.address_generator_reset <= address_generator_reset @[controlUnit.scala 465:47]
    io.controller_out.write_memoryUnits[0] <= write_memoryUnits[0] @[controlUnit.scala 468:48]
    io.controller_out.loading_activations[0] <= loading_activations[0] @[controlUnit.scala 469:50]
    io.controller_out.write_memoryUnits[1] <= write_memoryUnits[1] @[controlUnit.scala 468:48]
    io.controller_out.loading_activations[1] <= loading_activations[1] @[controlUnit.scala 469:50]
    io.controller_out.write_memoryUnits[2] <= write_memoryUnits[2] @[controlUnit.scala 468:48]
    io.controller_out.loading_activations[2] <= loading_activations[2] @[controlUnit.scala 469:50]
    io.controller_out.write_memoryUnits[3] <= write_memoryUnits[3] @[controlUnit.scala 468:48]
    io.controller_out.loading_activations[3] <= loading_activations[3] @[controlUnit.scala 469:50]
    io.controller_out.loading_layer <= loading_layer @[controlUnit.scala 472:37]
    io.controller_out.weight_buffer_load_request <= weight_buffer_load_request @[controlUnit.scala 473:50]
    io.controller_out.current_layer_total_activations <= current_layer_total_activations @[controlUnit.scala 474:55]
    io.controller_out.loading_layer_total_activations <= loading_layer_total_activations @[controlUnit.scala 475:55]
    io.controller_out.save_data_request <= save_data_request @[controlUnit.scala 477:41]
    io.controller_out.current_buffer_memory_pointer <= current_buffer_memory_pointer @[controlUnit.scala 478:53]
    io.controller_out.current_layer <= current_layer @[controlUnit.scala 480:37]
    io.controller_out.iteration_layer <= iteration_layer @[controlUnit.scala 481:39]
    io.controller_out.max_layer <= max_layer @[controlUnit.scala 482:33]
    io.controller_out.current_layer_current_activation <= current_layer_current_activation @[controlUnit.scala 483:56]
    io.controller_out.load_initial_weights <= load_initial_weights_out @[controlUnit.scala 484:44]

  module LoadPEMemory :
    input clock : Clock
    input reset : Reset
    output io : { load_pe_memory_in : { flip load_initial_weights : UInt<1>, flip load_datapoint : UInt<1>, flip load_new_request : UInt<1>, flip load_data_from_buffer : UInt<1>, flip load_same_data : UInt<1>, flip loading_layer : UInt<16>, flip loading_length : UInt<16>, flip loading_activations : UInt<16>[4], flip write_memoryUnits : UInt<2>[4], flip weight_buffer_load_request : UInt<1>, flip buffer_memory_output : UInt<16>, flip new_datapoint_ready : UInt<1>, flip interconnect_load_ready : UInt<1>, flip interconnect_memory_output : UInt<16>}, load_pe_memory_out : { load_initial_weights_complete : UInt<1>, load_datapoint_complete : UInt<1>, load_buffer_weight_memory_complete : UInt<1>, datapoint_memory_write_enable : UInt<1>, datapoint_memory_write_data : UInt<16>, datapoint_memory_write_address : UInt<16>, buffer_memory_write_enable : UInt<1>, buffer_memory_write_data : UInt<16>, buffer_memory_write_address : UInt<16>, write_memoryUnits : UInt<2>[4], weight_buffer_memory_write_data : UInt<16>, weight_buffer_memory_write_address : UInt<16>, interconnect_loading_layer : UInt<16>, interconnect_loading_activation : UInt<16>, interconnect_load_request : UInt<1>, interconnect_load_read_address : UInt<16>, load_new_data_request : UInt<1>, current_load_weights_state : UInt<16>, current_load_datapoint_state : UInt<16>, current_pe : UInt<16>}}

    io.load_pe_memory_out.load_new_data_request <= UInt<1>("h0") @[loadPEMemory.scala 83:49]
    io.load_pe_memory_out.datapoint_memory_write_enable <= UInt<1>("h0") @[loadPEMemory.scala 84:57]
    io.load_pe_memory_out.datapoint_memory_write_data <= UInt<16>("h0") @[loadPEMemory.scala 85:55]
    io.load_pe_memory_out.datapoint_memory_write_address <= UInt<16>("h0") @[loadPEMemory.scala 86:58]
    io.load_pe_memory_out.buffer_memory_write_enable <= UInt<1>("h0") @[loadPEMemory.scala 88:54]
    io.load_pe_memory_out.buffer_memory_write_address <= UInt<16>("h0") @[loadPEMemory.scala 89:55]
    io.load_pe_memory_out.buffer_memory_write_data <= UInt<16>("h0") @[loadPEMemory.scala 90:52]
    io.load_pe_memory_out.write_memoryUnits[0] <= UInt<2>("h0") @[loadPEMemory.scala 93:52]
    io.load_pe_memory_out.write_memoryUnits[1] <= UInt<2>("h0") @[loadPEMemory.scala 93:52]
    io.load_pe_memory_out.write_memoryUnits[2] <= UInt<2>("h0") @[loadPEMemory.scala 93:52]
    io.load_pe_memory_out.write_memoryUnits[3] <= UInt<2>("h0") @[loadPEMemory.scala 93:52]
    io.load_pe_memory_out.weight_buffer_memory_write_address <= UInt<16>("h0") @[loadPEMemory.scala 96:62]
    io.load_pe_memory_out.weight_buffer_memory_write_data <= UInt<16>("h0") @[loadPEMemory.scala 97:59]
    io.load_pe_memory_out.interconnect_loading_layer <= UInt<16>("h0") @[loadPEMemory.scala 99:54]
    io.load_pe_memory_out.interconnect_loading_activation <= UInt<16>("h0") @[loadPEMemory.scala 100:59]
    io.load_pe_memory_out.interconnect_load_request <= UInt<1>("h0") @[loadPEMemory.scala 101:53]
    io.load_pe_memory_out.interconnect_load_read_address <= UInt<16>("h0") @[loadPEMemory.scala 103:58]
    reg copy_buffer_state : UInt<2>, clock with :
      reset => (reset, UInt<2>("h0")) @[loadPEMemory.scala 107:36]
    reg current_copy_address2 : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[loadPEMemory.scala 108:40]
    reg current_save_address2 : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[loadPEMemory.scala 109:40]
    reg loading_length : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[loadPEMemory.scala 110:33]
    reg load_datapoint_complete : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[loadPEMemory.scala 111:42]
    reg current_copy_address : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[loadPEMemory.scala 115:39]
    reg current_save_address : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[loadPEMemory.scala 116:39]
    reg current_state : UInt<2>, clock with :
      reset => (reset, UInt<2>("h0")) @[loadPEMemory.scala 117:32]
    reg current_pe : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[loadPEMemory.scala 119:29]
    wire next_pe : UInt<16>
    next_pe <= UInt<16>("h0")
    reg previous_pe : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[loadPEMemory.scala 121:30]
    reg load_initial_weights_complete : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[loadPEMemory.scala 123:48]
    reg load_buffer_weight_memory_complete : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[loadPEMemory.scala 124:53]
    reg loading_layer : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[loadPEMemory.scala 125:32]
    io.load_pe_memory_out.current_load_weights_state <= UInt<16>("h0") @[loadPEMemory.scala 127:54]
    io.load_pe_memory_out.current_load_datapoint_state <= UInt<16>("h0") @[loadPEMemory.scala 128:56]
    node _T = eq(UInt<2>("h0"), current_state) @[loadPEMemory.scala 132:30]
    when _T : @[loadPEMemory.scala 132:30]
      load_initial_weights_complete <= UInt<1>("h0") @[loadPEMemory.scala 134:47]
      load_buffer_weight_memory_complete <= UInt<1>("h0") @[loadPEMemory.scala 135:52]
      io.load_pe_memory_out.current_load_weights_state <= UInt<16>("h0") @[loadPEMemory.scala 138:66]
      current_pe <= UInt<16>("h0") @[loadPEMemory.scala 140:28]
      io.load_pe_memory_out.interconnect_loading_layer <= UInt<16>("h0") @[loadPEMemory.scala 142:66]
      io.load_pe_memory_out.interconnect_loading_activation <= UInt<16>("h0") @[loadPEMemory.scala 143:71]
      io.load_pe_memory_out.interconnect_load_request <= UInt<1>("h0") @[loadPEMemory.scala 144:65]
      io.load_pe_memory_out.interconnect_load_read_address <= UInt<16>("h0") @[loadPEMemory.scala 146:70]
      node _T_1 = or(io.load_pe_memory_in.load_initial_weights, io.load_pe_memory_in.weight_buffer_load_request) @[loadPEMemory.scala 149:64]
      when _T_1 : @[loadPEMemory.scala 149:115]
        loading_layer <= io.load_pe_memory_in.loading_layer @[loadPEMemory.scala 150:35]
        loading_length <= io.load_pe_memory_in.loading_length @[loadPEMemory.scala 151:36]
        current_state <= UInt<2>("h1") @[loadPEMemory.scala 152:35]
    else :
      node _T_2 = eq(UInt<2>("h1"), current_state) @[loadPEMemory.scala 132:30]
      when _T_2 : @[loadPEMemory.scala 132:30]
        io.load_pe_memory_out.current_load_weights_state <= UInt<16>("h1") @[loadPEMemory.scala 157:66]
        node _next_pe_T = add(current_pe, UInt<16>("h1")) @[loadPEMemory.scala 159:39]
        node _next_pe_T_1 = tail(_next_pe_T, 1) @[loadPEMemory.scala 159:39]
        next_pe <= _next_pe_T_1 @[loadPEMemory.scala 159:25]
        current_copy_address <= UInt<16>("h0") @[loadPEMemory.scala 161:38]
        current_save_address <= UInt<16>("h0") @[loadPEMemory.scala 162:38]
        io.load_pe_memory_out.interconnect_load_read_address <= UInt<16>("h0") @[loadPEMemory.scala 165:70]
        io.load_pe_memory_out.weight_buffer_memory_write_address <= UInt<16>("h0") @[loadPEMemory.scala 167:74]
        io.load_pe_memory_out.write_memoryUnits[0] <= UInt<2>("h0") @[loadPEMemory.scala 169:68]
        io.load_pe_memory_out.write_memoryUnits[1] <= UInt<2>("h0") @[loadPEMemory.scala 169:68]
        io.load_pe_memory_out.write_memoryUnits[2] <= UInt<2>("h0") @[loadPEMemory.scala 169:68]
        io.load_pe_memory_out.write_memoryUnits[3] <= UInt<2>("h0") @[loadPEMemory.scala 169:68]
        io.load_pe_memory_out.weight_buffer_memory_write_data <= UInt<16>("h0") @[loadPEMemory.scala 171:71]
        node _T_3 = bits(current_pe, 1, 0)
        node _T_4 = eq(io.load_pe_memory_in.write_memoryUnits[_T_3], UInt<2>("h0")) @[loadPEMemory.scala 174:73]
        when _T_4 : @[loadPEMemory.scala 174:86]
          current_state <= UInt<2>("h3") @[loadPEMemory.scala 175:35]
          when io.load_pe_memory_in.load_initial_weights : @[loadPEMemory.scala 176:68]
            load_initial_weights_complete <= UInt<1>("h1") @[loadPEMemory.scala 177:55]
          else :
            load_buffer_weight_memory_complete <= UInt<1>("h1") @[loadPEMemory.scala 179:60]
        else :
          when io.load_pe_memory_in.load_initial_weights : @[loadPEMemory.scala 183:68]
            io.load_pe_memory_out.interconnect_loading_layer <= UInt<16>("h2") @[loadPEMemory.scala 184:74]
            io.load_pe_memory_out.interconnect_loading_activation <= next_pe @[loadPEMemory.scala 185:79]
            io.load_pe_memory_out.interconnect_load_request <= UInt<1>("h1") @[loadPEMemory.scala 186:73]
          else :
            io.load_pe_memory_out.interconnect_loading_layer <= loading_layer @[loadPEMemory.scala 188:74]
            node _io_load_pe_memory_out_interconnect_loading_activation_T = bits(current_pe, 1, 0)
            io.load_pe_memory_out.interconnect_loading_activation <= io.load_pe_memory_in.loading_activations[_io_load_pe_memory_out_interconnect_loading_activation_T] @[loadPEMemory.scala 189:79]
            io.load_pe_memory_out.interconnect_load_request <= UInt<1>("h1") @[loadPEMemory.scala 190:73]
          when io.load_pe_memory_in.interconnect_load_ready : @[loadPEMemory.scala 193:71]
            current_state <= UInt<2>("h2") @[loadPEMemory.scala 194:39]
            node _current_pe_T = add(current_pe, UInt<16>("h1")) @[loadPEMemory.scala 196:50]
            node _current_pe_T_1 = tail(_current_pe_T, 1) @[loadPEMemory.scala 196:50]
            current_pe <= _current_pe_T_1 @[loadPEMemory.scala 196:36]
            previous_pe <= current_pe @[loadPEMemory.scala 197:37]
            io.load_pe_memory_out.interconnect_loading_layer <= UInt<16>("h0") @[loadPEMemory.scala 199:74]
            io.load_pe_memory_out.interconnect_loading_activation <= UInt<16>("h0") @[loadPEMemory.scala 200:79]
            io.load_pe_memory_out.interconnect_load_request <= UInt<1>("h0") @[loadPEMemory.scala 201:73]
      else :
        node _T_5 = eq(UInt<2>("h2"), current_state) @[loadPEMemory.scala 132:30]
        when _T_5 : @[loadPEMemory.scala 132:30]
          io.load_pe_memory_out.current_load_weights_state <= UInt<16>("h2") @[loadPEMemory.scala 209:66]
          io.load_pe_memory_out.interconnect_load_read_address <= current_copy_address @[loadPEMemory.scala 212:70]
          io.load_pe_memory_out.weight_buffer_memory_write_address <= current_save_address @[loadPEMemory.scala 213:74]
          node _T_6 = bits(previous_pe, 1, 0)
          node _io_load_pe_memory_out_write_memoryUnits_T = bits(previous_pe, 1, 0)
          io.load_pe_memory_out.write_memoryUnits[_T_6] <= io.load_pe_memory_in.write_memoryUnits[_io_load_pe_memory_out_write_memoryUnits_T] @[loadPEMemory.scala 214:70]
          io.load_pe_memory_out.weight_buffer_memory_write_data <= io.load_pe_memory_in.interconnect_memory_output @[loadPEMemory.scala 216:71]
          node _current_copy_address_T = add(current_copy_address, UInt<16>("h1")) @[loadPEMemory.scala 218:62]
          node _current_copy_address_T_1 = tail(_current_copy_address_T, 1) @[loadPEMemory.scala 218:62]
          current_copy_address <= _current_copy_address_T_1 @[loadPEMemory.scala 218:38]
          current_save_address <= current_copy_address @[loadPEMemory.scala 219:38]
          node _T_7 = eq(current_copy_address, loading_length) @[loadPEMemory.scala 222:43]
          when _T_7 : @[loadPEMemory.scala 223:17]
            node _T_8 = eq(current_pe, UInt<16>("h4")) @[loadPEMemory.scala 223:37]
            when _T_8 : @[loadPEMemory.scala 224:21]
              when io.load_pe_memory_in.load_initial_weights : @[loadPEMemory.scala 225:72]
                load_initial_weights_complete <= UInt<1>("h1") @[loadPEMemory.scala 226:59]
              else :
                load_buffer_weight_memory_complete <= UInt<1>("h1") @[loadPEMemory.scala 228:64]
              current_state <= UInt<2>("h3") @[loadPEMemory.scala 230:39]
            else :
              current_state <= UInt<2>("h1") @[loadPEMemory.scala 233:39]
          else :
            current_state <= UInt<2>("h2") @[loadPEMemory.scala 236:35]
        else :
          node _T_9 = eq(UInt<2>("h3"), current_state) @[loadPEMemory.scala 132:30]
          when _T_9 : @[loadPEMemory.scala 132:30]
            io.load_pe_memory_out.current_load_weights_state <= UInt<16>("h3") @[loadPEMemory.scala 244:66]
            load_initial_weights_complete <= UInt<1>("h0") @[loadPEMemory.scala 247:47]
            load_buffer_weight_memory_complete <= UInt<1>("h0") @[loadPEMemory.scala 248:52]
            node _T_10 = eq(io.load_pe_memory_in.load_initial_weights, UInt<1>("h0")) @[loadPEMemory.scala 250:65]
            node _T_11 = eq(io.load_pe_memory_in.weight_buffer_load_request, UInt<1>("h0")) @[loadPEMemory.scala 250:131]
            node _T_12 = and(_T_10, _T_11) @[loadPEMemory.scala 250:79]
            when _T_12 : @[loadPEMemory.scala 250:144]
              current_state <= UInt<2>("h0") @[loadPEMemory.scala 251:35]
    node _T_13 = and(io.load_pe_memory_in.load_new_request, io.load_pe_memory_in.load_datapoint) @[loadPEMemory.scala 258:52]
    when _T_13 : @[loadPEMemory.scala 258:91]
      io.load_pe_memory_out.load_new_data_request <= UInt<1>("h1") @[loadPEMemory.scala 259:57]
    node _T_14 = eq(UInt<2>("h0"), copy_buffer_state) @[loadPEMemory.scala 263:38]
    when _T_14 : @[loadPEMemory.scala 263:38]
      io.load_pe_memory_out.current_load_datapoint_state <= UInt<16>("h0") @[loadPEMemory.scala 265:72]
      current_copy_address2 <= UInt<16>("h0") @[loadPEMemory.scala 268:43]
      current_save_address2 <= UInt<16>("h0") @[loadPEMemory.scala 269:43]
      io.load_pe_memory_out.buffer_memory_write_address <= UInt<16>("h0") @[loadPEMemory.scala 271:71]
      io.load_pe_memory_out.datapoint_memory_write_address <= UInt<16>("h0") @[loadPEMemory.scala 272:74]
      io.load_pe_memory_out.datapoint_memory_write_enable <= UInt<1>("h0") @[loadPEMemory.scala 273:73]
      io.load_pe_memory_out.datapoint_memory_write_data <= UInt<16>("h0") @[loadPEMemory.scala 275:71]
      node _T_15 = or(io.load_pe_memory_in.load_data_from_buffer, io.load_pe_memory_in.new_datapoint_ready) @[loadPEMemory.scala 277:69]
      when _T_15 : @[loadPEMemory.scala 277:113]
        loading_length <= io.load_pe_memory_in.loading_length @[loadPEMemory.scala 278:40]
        copy_buffer_state <= UInt<2>("h1") @[loadPEMemory.scala 279:43]
    else :
      node _T_16 = eq(UInt<2>("h1"), copy_buffer_state) @[loadPEMemory.scala 263:38]
      when _T_16 : @[loadPEMemory.scala 263:38]
        io.load_pe_memory_out.load_new_data_request <= UInt<1>("h0") @[loadPEMemory.scala 285:65]
        io.load_pe_memory_out.current_load_datapoint_state <= UInt<16>("h1") @[loadPEMemory.scala 286:72]
        when io.load_pe_memory_in.load_data_from_buffer : @[loadPEMemory.scala 288:69]
          io.load_pe_memory_out.buffer_memory_write_address <= current_copy_address2 @[loadPEMemory.scala 289:75]
          io.load_pe_memory_out.datapoint_memory_write_data <= io.load_pe_memory_in.buffer_memory_output @[loadPEMemory.scala 290:75]
        else :
          io.load_pe_memory_out.interconnect_load_read_address <= current_copy_address2 @[loadPEMemory.scala 292:78]
          io.load_pe_memory_out.datapoint_memory_write_data <= io.load_pe_memory_in.interconnect_memory_output @[loadPEMemory.scala 293:75]
        io.load_pe_memory_out.datapoint_memory_write_address <= current_save_address2 @[loadPEMemory.scala 296:74]
        io.load_pe_memory_out.datapoint_memory_write_enable <= UInt<1>("h1") @[loadPEMemory.scala 297:73]
        node _current_copy_address2_T = add(current_copy_address2, UInt<16>("h1")) @[loadPEMemory.scala 299:68]
        node _current_copy_address2_T_1 = tail(_current_copy_address2_T, 1) @[loadPEMemory.scala 299:68]
        current_copy_address2 <= _current_copy_address2_T_1 @[loadPEMemory.scala 299:43]
        current_save_address2 <= current_copy_address2 @[loadPEMemory.scala 300:43]
        node _T_17 = eq(current_copy_address2, loading_length) @[loadPEMemory.scala 302:48]
        when _T_17 : @[loadPEMemory.scala 302:67]
          load_datapoint_complete <= UInt<1>("h1") @[loadPEMemory.scala 303:49]
          copy_buffer_state <= UInt<2>("h2") @[loadPEMemory.scala 304:43]
      else :
        node _T_18 = eq(UInt<2>("h2"), copy_buffer_state) @[loadPEMemory.scala 263:38]
        when _T_18 : @[loadPEMemory.scala 263:38]
          io.load_pe_memory_out.current_load_datapoint_state <= UInt<16>("h2") @[loadPEMemory.scala 309:72]
          load_datapoint_complete <= UInt<1>("h0") @[loadPEMemory.scala 311:45]
          io.load_pe_memory_out.interconnect_load_read_address <= UInt<16>("h0") @[loadPEMemory.scala 313:74]
          io.load_pe_memory_out.buffer_memory_write_address <= UInt<16>("h0") @[loadPEMemory.scala 314:71]
          io.load_pe_memory_out.datapoint_memory_write_address <= UInt<16>("h0") @[loadPEMemory.scala 316:74]
          io.load_pe_memory_out.datapoint_memory_write_enable <= UInt<1>("h0") @[loadPEMemory.scala 317:73]
          node _T_19 = eq(io.load_pe_memory_in.load_datapoint, UInt<1>("h0")) @[loadPEMemory.scala 319:62]
          when _T_19 : @[loadPEMemory.scala 319:74]
            copy_buffer_state <= UInt<2>("h0") @[loadPEMemory.scala 320:43]
    node _T_20 = and(io.load_pe_memory_in.load_same_data, io.load_pe_memory_in.load_datapoint) @[loadPEMemory.scala 327:50]
    when _T_20 : @[loadPEMemory.scala 327:89]
      load_datapoint_complete <= UInt<1>("h1") @[loadPEMemory.scala 328:37]
    node _T_21 = eq(io.load_pe_memory_in.load_datapoint, UInt<1>("h0")) @[loadPEMemory.scala 330:50]
    when _T_21 : @[loadPEMemory.scala 330:62]
      load_datapoint_complete <= UInt<1>("h0") @[loadPEMemory.scala 331:37]
    io.load_pe_memory_out.load_datapoint_complete <= load_datapoint_complete @[loadPEMemory.scala 338:47]
    io.load_pe_memory_out.load_initial_weights_complete <= load_initial_weights_complete @[loadPEMemory.scala 339:53]
    io.load_pe_memory_out.load_buffer_weight_memory_complete <= load_buffer_weight_memory_complete @[loadPEMemory.scala 340:58]
    io.load_pe_memory_out.current_pe <= current_pe @[loadPEMemory.scala 341:34]

  module SavePEOutput :
    input clock : Clock
    input reset : Reset
    output io : { save_pe_output_in : { flip save_data_request : UInt<1>, flip current_buffer_memory_pointer : UInt<16>, flip PE_outputs : UInt<16>[4]}, save_pe_output_out : { save_data_complete : UInt<1>, buffer_memory_write_enable : UInt<1>, buffer_memory_write_address : UInt<16>, buffer_memory_write_data : UInt<16>}}

    reg current_save_stage : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[save_pe_outputs.scala 35:37]
    reg curr_state : UInt<2>, clock with :
      reset => (reset, UInt<2>("h0")) @[save_pe_outputs.scala 38:29]
    reg save_data_complete : UInt<1>, clock with :
      reset => (reset, UInt<1>("h0")) @[save_pe_outputs.scala 40:37]
    reg buffer_memory_pointer : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[save_pe_outputs.scala 41:40]
    reg max_iter : UInt<16>, clock with :
      reset => (reset, UInt<16>("h0")) @[save_pe_outputs.scala 42:27]
    io.save_pe_output_out.buffer_memory_write_enable <= UInt<1>("h0") @[save_pe_outputs.scala 44:54]
    io.save_pe_output_out.buffer_memory_write_address <= UInt<16>("h0") @[save_pe_outputs.scala 45:55]
    io.save_pe_output_out.buffer_memory_write_data <= UInt<16>("h0") @[save_pe_outputs.scala 46:52]
    node _T = eq(UInt<2>("h0"), curr_state) @[save_pe_outputs.scala 48:23]
    when _T : @[save_pe_outputs.scala 48:23]
      io.save_pe_output_out.buffer_memory_write_enable <= UInt<1>("h0") @[save_pe_outputs.scala 50:62]
      io.save_pe_output_out.buffer_memory_write_address <= UInt<16>("h0") @[save_pe_outputs.scala 51:63]
      io.save_pe_output_out.buffer_memory_write_data <= UInt<16>("h0") @[save_pe_outputs.scala 52:60]
      save_data_complete <= UInt<1>("h0") @[save_pe_outputs.scala 54:32]
      current_save_stage <= UInt<16>("h0") @[save_pe_outputs.scala 55:32]
      node _max_iter_T = sub(UInt<16>("h4"), UInt<16>("h1")) @[save_pe_outputs.scala 56:51]
      node _max_iter_T_1 = tail(_max_iter_T, 1) @[save_pe_outputs.scala 56:51]
      max_iter <= _max_iter_T_1 @[save_pe_outputs.scala 56:22]
      when io.save_pe_output_in.save_data_request : @[save_pe_outputs.scala 58:57]
        curr_state <= UInt<2>("h1") @[save_pe_outputs.scala 59:28]
        buffer_memory_pointer <= io.save_pe_output_in.current_buffer_memory_pointer @[save_pe_outputs.scala 60:39]
    else :
      node _T_1 = eq(UInt<2>("h1"), curr_state) @[save_pe_outputs.scala 48:23]
      when _T_1 : @[save_pe_outputs.scala 48:23]
        node _current_save_stage_T = add(current_save_stage, UInt<16>("h1")) @[save_pe_outputs.scala 66:54]
        node _current_save_stage_T_1 = tail(_current_save_stage_T, 1) @[save_pe_outputs.scala 66:54]
        current_save_stage <= _current_save_stage_T_1 @[save_pe_outputs.scala 66:32]
        io.save_pe_output_out.buffer_memory_write_enable <= UInt<1>("h1") @[save_pe_outputs.scala 67:62]
        node _io_save_pe_output_out_buffer_memory_write_address_T = add(buffer_memory_pointer, current_save_stage) @[save_pe_outputs.scala 68:88]
        node _io_save_pe_output_out_buffer_memory_write_address_T_1 = tail(_io_save_pe_output_out_buffer_memory_write_address_T, 1) @[save_pe_outputs.scala 68:88]
        io.save_pe_output_out.buffer_memory_write_address <= _io_save_pe_output_out_buffer_memory_write_address_T_1 @[save_pe_outputs.scala 68:63]
        node _io_save_pe_output_out_buffer_memory_write_data_T = bits(current_save_stage, 1, 0)
        io.save_pe_output_out.buffer_memory_write_data <= io.save_pe_output_in.PE_outputs[_io_save_pe_output_out_buffer_memory_write_data_T] @[save_pe_outputs.scala 69:60]
        node _T_2 = eq(current_save_stage, max_iter) @[save_pe_outputs.scala 71:37]
        when _T_2 : @[save_pe_outputs.scala 71:50]
          save_data_complete <= UInt<1>("h1") @[save_pe_outputs.scala 72:36]
          curr_state <= UInt<2>("h2") @[save_pe_outputs.scala 73:28]
      else :
        node _T_3 = eq(UInt<2>("h2"), curr_state) @[save_pe_outputs.scala 48:23]
        when _T_3 : @[save_pe_outputs.scala 48:23]
          io.save_pe_output_out.buffer_memory_write_enable <= UInt<1>("h0") @[save_pe_outputs.scala 78:62]
          io.save_pe_output_out.buffer_memory_write_address <= UInt<16>("h0") @[save_pe_outputs.scala 79:63]
          io.save_pe_output_out.buffer_memory_write_data <= UInt<16>("h0") @[save_pe_outputs.scala 80:60]
          save_data_complete <= UInt<1>("h0") @[save_pe_outputs.scala 82:32]
          node _T_4 = eq(io.save_pe_output_in.save_data_request, UInt<1>("h0")) @[save_pe_outputs.scala 84:57]
          when _T_4 : @[save_pe_outputs.scala 84:69]
            curr_state <= UInt<2>("h0") @[save_pe_outputs.scala 85:28]
    io.save_pe_output_out.save_data_complete <= save_data_complete @[save_pe_outputs.scala 90:46]

  module AddressGenerator :
    input clock : Clock
    input reset : Reset
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

  module bufferDatapointMemoryAccess :
    input clock : Clock
    input reset : Reset
    output io : { flip wrEna : UInt<1>, flip Addr : UInt<10>, flip dataIn : UInt<16>, rdData : UInt<16>}

    smem mem : UInt<16> [32] @[buffer_datapoint_memory_access.scala 20:24]
    io.rdData <= UInt<16>("h0") @[buffer_datapoint_memory_access.scala 22:13]
    node _rdwrPort_T = bits(io.Addr, 4, 0) @[buffer_datapoint_memory_access.scala 24:21]
    infer mport rdwrPort = mem[_rdwrPort_T], clock @[buffer_datapoint_memory_access.scala 24:21]
    when io.wrEna : @[buffer_datapoint_memory_access.scala 26:19]
      rdwrPort <= io.dataIn @[buffer_datapoint_memory_access.scala 26:30]
    else :
      io.rdData <= rdwrPort @[buffer_datapoint_memory_access.scala 27:31]

  module NNDescriptionTableAccess :
    input clock : Clock
    input reset : Reset
    output io : { flip wrEna : UInt<1>, flip Addr : UInt<10>, flip dataIn : UInt<16>, rdData : UInt<16>}

    smem mem : UInt<16> [32] @[nn_description_table_access.scala 20:24]
    io.rdData <= UInt<16>("h0") @[nn_description_table_access.scala 24:13]
    node _rdwrPort_T = bits(io.Addr, 4, 0) @[nn_description_table_access.scala 26:21]
    infer mport rdwrPort = mem[_rdwrPort_T], clock @[nn_description_table_access.scala 26:21]
    when io.wrEna : @[nn_description_table_access.scala 28:19]
      rdwrPort <= io.dataIn @[nn_description_table_access.scala 28:30]
    else :
      io.rdData <= rdwrPort @[nn_description_table_access.scala 29:31]

  module ControlUnitIntegrated :
    input clock : Clock
    input reset : UInt<1>
    output io : { controlUnit_in : { flip controller_reset : UInt<1>, flip interconnect_new_datapoint_ready : UInt<1>, flip interconnect_load_ready : UInt<1>, flip interconnect_memory_output : UInt<16>, flip PE_outputs : UInt<16>[4]}, controlUnit_out : { write_memoryUnits : UInt<2>[4], weight_buffer_memory_write_data : UInt<16>, weight_buffer_memory_write_address : UInt<16>, interconnect_loading_layer : UInt<16>, interconnect_loading_activation : UInt<16>, interconnect_load_request : UInt<1>, interconnect_load_read_address : UInt<16>, load_new_data_request : UInt<1>, datapoint_memory_write_enable : UInt<1>, datapoint_memory_write_data : UInt<16>, datapoint_memory_write_address : UInt<16>, Address : UInt<5>, read_memoryUnits : UInt<2>[4], neuron_reset : UInt<1>, bias_valid : UInt<1>}}

    inst controller of Controller @[controlUnitIntegrated.scala 59:28]
    controller.clock <= clock
    controller.reset <= reset
    inst load_pe_memory of LoadPEMemory @[controlUnitIntegrated.scala 60:32]
    load_pe_memory.clock <= clock
    load_pe_memory.reset <= reset
    inst save_pe_outputs of SavePEOutput @[controlUnitIntegrated.scala 61:33]
    save_pe_outputs.clock <= clock
    save_pe_outputs.reset <= reset
    inst address_generator of AddressGenerator @[controlUnitIntegrated.scala 62:35]
    address_generator.clock <= clock
    address_generator.reset <= reset
    inst buffer_datapoint_memory_access of bufferDatapointMemoryAccess @[controlUnitIntegrated.scala 64:48]
    buffer_datapoint_memory_access.clock <= clock
    buffer_datapoint_memory_access.reset <= reset
    inst nn_description_table_access of NNDescriptionTableAccess @[controlUnitIntegrated.scala 66:45]
    nn_description_table_access.clock <= clock
    nn_description_table_access.reset <= reset
    controller.io.controller_in.controller_reset <= io.controlUnit_in.controller_reset @[controlUnitIntegrated.scala 71:50]
    controller.io.controller_in.address_generation_complete <= address_generator.io.address_generator_out.address_generation_complete @[controlUnitIntegrated.scala 72:61]
    controller.io.controller_in.loading_initial_weights_complete <= load_pe_memory.io.load_pe_memory_out.load_initial_weights_complete @[controlUnitIntegrated.scala 73:66]
    controller.io.controller_in.load_datapoint_complete <= load_pe_memory.io.load_pe_memory_out.load_datapoint_complete @[controlUnitIntegrated.scala 74:57]
    controller.io.controller_in.load_buffer_weight_memory_complete <= load_pe_memory.io.load_pe_memory_out.load_buffer_weight_memory_complete @[controlUnitIntegrated.scala 75:68]
    controller.io.controller_in.save_data_complete <= save_pe_outputs.io.save_pe_output_out.save_data_complete @[controlUnitIntegrated.scala 76:52]
    controller.io.controller_in.nn_description_table_input <= nn_description_table_access.io.rdData @[controlUnitIntegrated.scala 78:60]
    load_pe_memory.io.load_pe_memory_in.load_initial_weights <= controller.io.controller_out.load_initial_weights @[controlUnitIntegrated.scala 82:62]
    load_pe_memory.io.load_pe_memory_in.load_datapoint <= controller.io.controller_out.load_datapoint @[controlUnitIntegrated.scala 83:56]
    load_pe_memory.io.load_pe_memory_in.load_new_request <= controller.io.controller_out.load_new_request @[controlUnitIntegrated.scala 84:58]
    load_pe_memory.io.load_pe_memory_in.load_data_from_buffer <= controller.io.controller_out.load_data_from_buffer @[controlUnitIntegrated.scala 85:63]
    load_pe_memory.io.load_pe_memory_in.load_same_data <= controller.io.controller_out.load_same_data @[controlUnitIntegrated.scala 86:56]
    load_pe_memory.io.load_pe_memory_in.weight_buffer_load_request <= controller.io.controller_out.weight_buffer_load_request @[controlUnitIntegrated.scala 87:68]
    load_pe_memory.io.load_pe_memory_in.loading_layer <= controller.io.controller_out.loading_layer @[controlUnitIntegrated.scala 89:55]
    load_pe_memory.io.load_pe_memory_in.loading_length <= controller.io.controller_out.max_iteration @[controlUnitIntegrated.scala 90:56]
    load_pe_memory.io.load_pe_memory_in.loading_activations[0] <= controller.io.controller_out.loading_activations[0] @[controlUnitIntegrated.scala 91:61]
    load_pe_memory.io.load_pe_memory_in.loading_activations[1] <= controller.io.controller_out.loading_activations[1] @[controlUnitIntegrated.scala 91:61]
    load_pe_memory.io.load_pe_memory_in.loading_activations[2] <= controller.io.controller_out.loading_activations[2] @[controlUnitIntegrated.scala 91:61]
    load_pe_memory.io.load_pe_memory_in.loading_activations[3] <= controller.io.controller_out.loading_activations[3] @[controlUnitIntegrated.scala 91:61]
    load_pe_memory.io.load_pe_memory_in.write_memoryUnits[0] <= controller.io.controller_out.write_memoryUnits[0] @[controlUnitIntegrated.scala 92:59]
    load_pe_memory.io.load_pe_memory_in.write_memoryUnits[1] <= controller.io.controller_out.write_memoryUnits[1] @[controlUnitIntegrated.scala 92:59]
    load_pe_memory.io.load_pe_memory_in.write_memoryUnits[2] <= controller.io.controller_out.write_memoryUnits[2] @[controlUnitIntegrated.scala 92:59]
    load_pe_memory.io.load_pe_memory_in.write_memoryUnits[3] <= controller.io.controller_out.write_memoryUnits[3] @[controlUnitIntegrated.scala 92:59]
    load_pe_memory.io.load_pe_memory_in.buffer_memory_output <= buffer_datapoint_memory_access.io.rdData @[controlUnitIntegrated.scala 94:62]
    load_pe_memory.io.load_pe_memory_in.new_datapoint_ready <= io.controlUnit_in.interconnect_new_datapoint_ready @[controlUnitIntegrated.scala 96:61]
    load_pe_memory.io.load_pe_memory_in.interconnect_load_ready <= io.controlUnit_in.interconnect_load_ready @[controlUnitIntegrated.scala 97:65]
    load_pe_memory.io.load_pe_memory_in.interconnect_memory_output <= io.controlUnit_in.interconnect_memory_output @[controlUnitIntegrated.scala 98:68]
    save_pe_outputs.io.save_pe_output_in.save_data_request <= controller.io.controller_out.save_data_request @[controlUnitIntegrated.scala 102:60]
    save_pe_outputs.io.save_pe_output_in.current_buffer_memory_pointer <= controller.io.controller_out.current_buffer_memory_pointer @[controlUnitIntegrated.scala 103:72]
    save_pe_outputs.io.save_pe_output_in.PE_outputs[0] <= io.controlUnit_in.PE_outputs[0] @[controlUnitIntegrated.scala 105:53]
    save_pe_outputs.io.save_pe_output_in.PE_outputs[1] <= io.controlUnit_in.PE_outputs[1] @[controlUnitIntegrated.scala 105:53]
    save_pe_outputs.io.save_pe_output_in.PE_outputs[2] <= io.controlUnit_in.PE_outputs[2] @[controlUnitIntegrated.scala 105:53]
    save_pe_outputs.io.save_pe_output_in.PE_outputs[3] <= io.controlUnit_in.PE_outputs[3] @[controlUnitIntegrated.scala 105:53]
    address_generator.io.address_generator_in.memoryUnits[0] <= controller.io.controller_out.read_memoryUnits[0] @[controlUnitIntegrated.scala 109:59]
    address_generator.io.address_generator_in.memoryUnits[1] <= controller.io.controller_out.read_memoryUnits[1] @[controlUnitIntegrated.scala 109:59]
    address_generator.io.address_generator_in.memoryUnits[2] <= controller.io.controller_out.read_memoryUnits[2] @[controlUnitIntegrated.scala 109:59]
    address_generator.io.address_generator_in.memoryUnits[3] <= controller.io.controller_out.read_memoryUnits[3] @[controlUnitIntegrated.scala 109:59]
    address_generator.io.address_generator_in.max_iteration <= controller.io.controller_out.max_iteration @[controlUnitIntegrated.scala 110:61]
    address_generator.io.address_generator_in.address_valid <= controller.io.controller_out.address_generator_address_valid @[controlUnitIntegrated.scala 112:61]
    address_generator.io.address_generator_in.enable_valid <= controller.io.controller_out.address_generator_enable_valid @[controlUnitIntegrated.scala 113:60]
    address_generator.io.address_generator_in.reset <= controller.io.controller_out.address_generator_reset @[controlUnitIntegrated.scala 114:53]
    buffer_datapoint_memory_access.io.wrEna <= save_pe_outputs.io.save_pe_output_out.buffer_memory_write_enable @[controlUnitIntegrated.scala 118:45]
    node _buffer_datapoint_memory_access_io_Addr_T = or(save_pe_outputs.io.save_pe_output_out.buffer_memory_write_address, load_pe_memory.io.load_pe_memory_out.buffer_memory_write_address) @[controlUnitIntegrated.scala 119:114]
    buffer_datapoint_memory_access.io.Addr <= _buffer_datapoint_memory_access_io_Addr_T @[controlUnitIntegrated.scala 119:44]
    buffer_datapoint_memory_access.io.dataIn <= save_pe_outputs.io.save_pe_output_out.buffer_memory_write_data @[controlUnitIntegrated.scala 120:46]
    io.controlUnit_out.datapoint_memory_write_enable <= load_pe_memory.io.load_pe_memory_out.datapoint_memory_write_enable @[controlUnitIntegrated.scala 124:54]
    io.controlUnit_out.datapoint_memory_write_data <= load_pe_memory.io.load_pe_memory_out.datapoint_memory_write_data @[controlUnitIntegrated.scala 125:52]
    io.controlUnit_out.datapoint_memory_write_address <= load_pe_memory.io.load_pe_memory_out.datapoint_memory_write_address @[controlUnitIntegrated.scala 126:55]
    nn_description_table_access.io.wrEna <= UInt<1>("h0") @[controlUnitIntegrated.scala 130:42]
    nn_description_table_access.io.Addr <= controller.io.controller_out.nn_description_table_address @[controlUnitIntegrated.scala 131:41]
    nn_description_table_access.io.dataIn <= UInt<16>("h0") @[controlUnitIntegrated.scala 132:43]
    io.controlUnit_out.Address <= address_generator.io.address_generator_out.Address @[controlUnitIntegrated.scala 135:32]
    io.controlUnit_out.read_memoryUnits[0] <= address_generator.io.address_generator_out.read_enables[0] @[controlUnitIntegrated.scala 136:41]
    io.controlUnit_out.read_memoryUnits[1] <= address_generator.io.address_generator_out.read_enables[1] @[controlUnitIntegrated.scala 136:41]
    io.controlUnit_out.read_memoryUnits[2] <= address_generator.io.address_generator_out.read_enables[2] @[controlUnitIntegrated.scala 136:41]
    io.controlUnit_out.read_memoryUnits[3] <= address_generator.io.address_generator_out.read_enables[3] @[controlUnitIntegrated.scala 136:41]
    io.controlUnit_out.neuron_reset <= address_generator.io.address_generator_out.neuron_reset @[controlUnitIntegrated.scala 137:37]
    io.controlUnit_out.bias_valid <= address_generator.io.address_generator_out.bias_valid @[controlUnitIntegrated.scala 138:35]
    io.controlUnit_out.interconnect_loading_layer <= load_pe_memory.io.load_pe_memory_out.interconnect_loading_layer @[controlUnitIntegrated.scala 141:51]
    io.controlUnit_out.interconnect_loading_activation <= load_pe_memory.io.load_pe_memory_out.interconnect_loading_activation @[controlUnitIntegrated.scala 142:56]
    io.controlUnit_out.interconnect_load_request <= load_pe_memory.io.load_pe_memory_out.interconnect_load_request @[controlUnitIntegrated.scala 143:50]
    io.controlUnit_out.interconnect_load_read_address <= load_pe_memory.io.load_pe_memory_out.interconnect_load_read_address @[controlUnitIntegrated.scala 144:55]
    io.controlUnit_out.load_new_data_request <= load_pe_memory.io.load_pe_memory_out.load_new_data_request @[controlUnitIntegrated.scala 145:46]
    io.controlUnit_out.write_memoryUnits[0] <= controller.io.controller_out.write_memoryUnits[0] @[controlUnitIntegrated.scala 147:42]
    io.controlUnit_out.write_memoryUnits[1] <= controller.io.controller_out.write_memoryUnits[1] @[controlUnitIntegrated.scala 147:42]
    io.controlUnit_out.write_memoryUnits[2] <= controller.io.controller_out.write_memoryUnits[2] @[controlUnitIntegrated.scala 147:42]
    io.controlUnit_out.write_memoryUnits[3] <= controller.io.controller_out.write_memoryUnits[3] @[controlUnitIntegrated.scala 147:42]
    io.controlUnit_out.weight_buffer_memory_write_address <= load_pe_memory.io.load_pe_memory_out.weight_buffer_memory_write_address @[controlUnitIntegrated.scala 148:59]
    io.controlUnit_out.weight_buffer_memory_write_data <= load_pe_memory.io.load_pe_memory_out.weight_buffer_memory_write_data @[controlUnitIntegrated.scala 149:56]

