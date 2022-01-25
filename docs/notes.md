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

## Control Unit