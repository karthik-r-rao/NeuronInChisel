module BindsTo_0_bufferDatapointMemoryAccess(
  input         clock,
  input         io_wrEna,
  input  [9:0]  io_Addr,
  input  [15:0] io_dataIn,
  output [15:0] io_rdData
);

  
initial begin
  $readmemb("file:/home/vsathvik/Academics/major_project/neural-net-chisel-main/target/bg-jobs/sbt_5e7bbd18/job-1/target/0cfe3e9f/a6da8bc6/neuroninchisel_2.13-0.1.0.jar!/nn_description_table.txt", bufferDatapointMemoryAccess.mem);
end
                      endmodule

bind bufferDatapointMemoryAccess BindsTo_0_bufferDatapointMemoryAccess BindsTo_0_bufferDatapointMemoryAccess_Inst(.*);