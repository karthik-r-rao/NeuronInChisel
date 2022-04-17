circuit SavePEOutput :
  module SavePEOutput :
    input clock : Clock
    input reset : UInt<1>
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

