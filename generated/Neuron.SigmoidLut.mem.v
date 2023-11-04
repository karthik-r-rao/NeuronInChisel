module BindsTo_0_SigmoidLut(
  input        clock,
  input  [9:0] io_addr,
  output [9:0] io_dataOut
);

  
initial begin
  $readmemb("file:/home/vsathvik/Academics/major_project/neural-net-chisel-main/target/bg-jobs/sbt_3380c28/job-1/target/0cfe3e9f/a6da8bc6/neuroninchisel_2.13-0.1.0.jar!/sigmoidlut.txt", SigmoidLut.mem);
end
                      endmodule

bind SigmoidLut BindsTo_0_SigmoidLut BindsTo_0_SigmoidLut_Inst(.*);