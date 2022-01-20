# NeuronInChisel

Implementation of a Neuron, the basic processing element of an ANN, in Chisel. 

## Getting started

1. Set up your environment to run Chisel locally. [Setup](https://github.com/chipsalliance/chisel3/blob/master/SETUP.md) instructions can be found here.
2. Clone the repo

```
git clone https://github.com/karthik-r-rao/NeuronInChisel
cd NeuronInChisel
```

## Generate `lut.txt` in `resources/`

The `lut.txt` file should be present in `src/main/resources`. The file present in the repo is for `fixed point <10, 4>`. If you desire a different fixed point representation, run
```
python3 ./scripts/generate_sigmoid_lut.py ./src/main/resources/lut.txt <fixed_point_width> <fraction_width>
```

Note: Changing the fixed point representation also requires changes in the parameterized modules. 

## Run tests

To run a test on class NeuronSpec, run 

```
sbt "testOnly neurontests.NeuronSpec"
```

## Generate Verilog files

To generate Verilog files for any class, run

```
sbt run 
```
and choose the desired class.
