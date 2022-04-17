module BindsTo_0_NNDescriptionTableAccess(
  input         clock,
  input         reset,
  input         io_wrEna,
  input  [9:0]  io_Addr,
  input  [15:0] io_dataIn,
  output [15:0] io_rdData
);

  
initial begin
  $readmemb("file:/home/vsathvik/Academics/major_project/neural-net-chisel-main/target/bg-jobs/sbt_70215035/job-1/target/0cfe3e9f/a6da8bc6/neuroninchisel_2.13-0.1.0.jar!/nn_description_table.txt", NNDescriptionTableAccess.mem);
end
                      endmodule

bind NNDescriptionTableAccess BindsTo_0_NNDescriptionTableAccess BindsTo_0_NNDescriptionTableAccess_Inst(.*);