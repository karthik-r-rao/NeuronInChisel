module BindsTo_1_datapointMemoryAccess(
  input         clock,
  input         io_wrEna,
  input  [9:0]  io_Addr,
  input  [17:0] io_dataIn,
  output [17:0] io_rdData
);

  
initial begin
  $readmemb("file:/home/karthikrrao/neural-net-chisel/target/bg-jobs/sbt_3bd6783e/job-1/target/68e72027/369e09f9/nninchisel_2.13-0.1.0.jar!/nn_description_table.txt", datapointMemoryAccess.mem);
end
                      endmodule

bind datapointMemoryAccess BindsTo_1_datapointMemoryAccess BindsTo_1_datapointMemoryAccess_Inst(.*);