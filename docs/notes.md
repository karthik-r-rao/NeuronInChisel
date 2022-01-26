# Design notes

## General

- Follow [Scala directory structure](https://www.scala-sbt.org/1.x/docs/Directories.html)
- Follow [Chisel developers style guide](https://www.chisel-lang.org/chisel3/docs/developers/style.html)
- All modules must be parameterizable

## Data representation

- Fixed point 
- Figure out how to handle overflows in outputs

## Processing Element (PE)

- MAC
- Activation functions
    - Sigmoid 
        - Classic piecewise linear
        - Zhang second-order approximation
    - Hyperbolic Tangent
        - Kwan second-order approximation 
    - ReLU
    - Softmax ####(Maybe)
## Control Unit

### IMPORTANT
    - Table keeping address of each datapoint in the dataset, (along with computed activations) in the main memory
    - To generate (0-m) in a cycle that is sent both to Weight Memory for weights and datapoint memory for the datapoint.
    - Save the computed activations in Main memory and Update the addresses of these activations in the table

### Additional
    - Different running types for training and testing of the neural network.
    - Including parameters like "Batch_size"
