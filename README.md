# Implementation of a Neural Network in Chisel

## Getting started

1. Set up your environment to run Chisel locally. [Setup](https://github.com/chipsalliance/chisel3/blob/master/SETUP.md) instructions can be found here.
2. Clone the repo

```
git clone https://github.com/karthik-r-rao/neural-net-chisel
cd neural-net-chisel
```

## Generate look-up table values for sigmoid activation function

Usage:
```
python3 ./scripts/generate_sigmoid_lut.py <name of output file> <fixed_point_length> <fraction_length>
```

Always store the output file in src/test/resources/ for the test files to load and initialize the sigmoid look-up table. Example of a look-up table with 1024 entries of 10 bits each, interpreted as fixed point with 3 integer bits and 7 fraction bits:

```
python3 ./scripts/generate_sigmoid_lut.py ./src/test/resources/sigmoidlut.txt 10 7
```

## Simulation

To test any module:

```
sbt "testOnly nntests.<name of test class>"
```

Test parameters can be changed in the respective test files. Example:
```
sbt "testOnly nntests.NeuronSpec"
```

## Generate verilog files

To generate verilog files for any module:
```
sbt "runMain nn.<name of driver class>"
```

The verilog files will be present in the generated/ folder. Example:
```
sbt "runMain nn.DriverNeuron"
```
