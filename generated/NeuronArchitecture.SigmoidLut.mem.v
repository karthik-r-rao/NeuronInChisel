module BindsTo_0_SigmoidLut(
  input        clock,
  input  [9:0] io_addr,
  output [9:0] io_dataOut
);

  
initial begin
  $readmemb("file:/home/karthikrrao/neural-net-chisel/target/bg-jobs/sbt_3bd6783e/job-1/target/68e72027/369e09f9/nninchisel_2.13-0.1.0.jar!/sigmoidlut.txt", SigmoidLut.mem);
end
                      endmodule

bind SigmoidLut BindsTo_0_SigmoidLut BindsTo_0_SigmoidLut_Inst(.*);