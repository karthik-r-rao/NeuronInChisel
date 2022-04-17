module BindsTo_1_datapointMemoryAccess(
  input         clock,
  input         io_wrEna,
  input  [9:0]  io_Addr,
  input  [17:0] io_dataIn,
  output [17:0] io_rdData
);

  
initial begin
  $readmemb("file:/home/vsathvik/Academics/major_project/neural-net-chisel-main/target/bg-jobs/sbt_87ddf643/job-1/target/0cfe3e9f/a6da8bc6/neuroninchisel_2.13-0.1.0.jar!/nn_description_table.txt", datapointMemoryAccess.mem);
end
                      endmodule

bind datapointMemoryAccess BindsTo_1_datapointMemoryAccess BindsTo_1_datapointMemoryAccess_Inst(.*);