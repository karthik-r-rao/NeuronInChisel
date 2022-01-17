"""
    Usage:
        python3 generate_sigmoid_lut.py <name of output file> <fixed_point_length> <fraction_length>
"""

import sys
import numpy as np
import math 

# sigmoid function
def sigmoid(x):
  return 1 / (1 + math.exp(-x))

if __name__ == "__main__":
    FILE = sys.argv[1]
    # fixed point <DATA_WIDTH, FRACTION_WIDTH>
    DATA_WIDTH = int(sys.argv[2])
    FRACTION_WIDTH = int(sys.argv[3])
    LUT_SIZE = 2 ** DATA_WIDTH

    high = (2 ** (DATA_WIDTH - FRACTION_WIDTH - 1)) - (2 ** (-FRACTION_WIDTH))
    low = -(2 ** (DATA_WIDTH - FRACTION_WIDTH - 1))
    print(f"low: {low} \nhigh: {high}")

    values = np.linspace(low, high, LUT_SIZE, endpoint=True)
    sigmoid_vector_function = np.vectorize(sigmoid)
    sigmoid_vector = sigmoid_vector_function(values)
    bins = np.digitize(sigmoid_vector, values)
    sigmoid_fixed_point = values[bins]

    # scale sigmoid array to write to text file
    sigmoid_fixed_point_scaled = np.array(sigmoid_fixed_point * (2 ** FRACTION_WIDTH), dtype=np.uint8)
    with open(FILE, "w") as f:
        for val in sigmoid_fixed_point_scaled:
            f.write(f"{bin(val)[2:].zfill(DATA_WIDTH)}\n")

    print(f"Done writing {FILE}!")