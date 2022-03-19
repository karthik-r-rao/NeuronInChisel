"""
    Usage:
        python3 generate_sigmoid_lut.py <name of output file> <fixed_point_length> <fraction_length>
"""

import sys
import numpy as np
import math 
import matplotlib.pyplot as plt

# sigmoid function
def sigmoid(x):
  return 1 / (1 + math.exp(-x))

if __name__ == "__main__":
    FILE = sys.argv[1]
    # fixed point <DATA_WIDTH, FRACTION_WIDTH>
    DATA_WIDTH = int(sys.argv[2])
    FRACTION_WIDTH = int(sys.argv[3])
    LUT_SIZE = 2 ** DATA_WIDTH

    fixed_point_high = (2 ** (DATA_WIDTH - FRACTION_WIDTH - 1)) - (2 ** (-FRACTION_WIDTH))
    fixed_point_low = -(2 ** (DATA_WIDTH - FRACTION_WIDTH - 1))
    unsigned_high = (2 ** DATA_WIDTH) - 1
    unsigned_low = 0
    print(f"fixed low: {fixed_point_low} \nfixed high: {fixed_point_high} \nunsigned low: {unsigned_low} \nunsigned high: {unsigned_high}")

    values = (np.arange(unsigned_low, unsigned_high + 1)) / (2 ** FRACTION_WIDTH)
    fixed_point_values = np.copy(values)
    for i in range(fixed_point_values.shape[0]):
        if i >= 2 ** (DATA_WIDTH - 1):
            fixed_point_values[i] -= 2 ** (DATA_WIDTH - FRACTION_WIDTH)

    sigmoid_vector_function = np.vectorize(sigmoid)
    sigmoid_vector = sigmoid_vector_function(fixed_point_values)
    bins = np.digitize(sigmoid_vector, values)
    sigmoid_fixed_point = values[bins]

    # scale sigmoid array to write to text file
    sigmoid_fixed_point_scaled = np.array(sigmoid_fixed_point * (2 ** FRACTION_WIDTH), dtype=np.uint8)
    with open(FILE, "w") as f:
        for val in sigmoid_fixed_point_scaled:
            f.write(f"{bin(val)[2:].zfill(DATA_WIDTH)}\n")

    print("Done writing!")

    plt.plot(sigmoid_fixed_point)
    plt.show()