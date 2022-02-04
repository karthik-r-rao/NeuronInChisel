'''
    Ref: Simple approximation of sigmoidal functions: 
    realistic design of digital neural networks capable of learning

'''

import math

import fixedpoint as fp
import matplotlib.pyplot as plt
import numpy as np


# sigmoid function
def sigmoid(x):
  return 1 / (1 + math.exp(-x))

if __name__ == "__main__":
    # set fixed point parameters 
    INT_WIDTH = 6
    FRAC_WIDTH = 12
    nums = np.linspace(-5, 5, num=20000, endpoint=True) 

    y_actual = []
    y_cla = []
    error = []

    for num in nums:
        x = fp.FixedPoint(num, signed=True, m=INT_WIDTH, n=FRAC_WIDTH)
        integer = x.bits[INT_WIDTH + FRAC_WIDTH - 1:FRAC_WIDTH]
        frac = x.bits[FRAC_WIDTH - 1: 0]

        # true value
        y1 = sigmoid(num)

        if num < 0:
            integer_converted = (integer ^ ((2 ** INT_WIDTH) - 1)) + 1  # 2's complement
            frac_scaled = 1 - (frac / (2 ** FRAC_WIDTH))
            # approximated value
            y2 = (0.5 - (frac_scaled / 4)) / (2 ** (integer_converted - 1))
            err = abs(y1 - y2)
        else:
            frac_scaled = frac / (2 ** FRAC_WIDTH)
            # approximated value
            y2 = 1 - ((2 - frac_scaled) / (2 ** (integer + 2)))
            err = abs(y1 - y2)

        y_actual.append(y1)
        y_cla.append(y2)
        error.append(err)

    plt.figure()
    plt.plot(nums, y_actual, label="true")
    plt.plot(nums, y_cla, label="approximated")
    plt.title("Sigmoid function: True value vs Classic Piecewise Linear Approximation")
    plt.savefig('approximation.png', bbox_inches='tight')
    
    plt.figure()
    plt.plot(nums, error)
    plt.title("Sigmoid function: Error in Classic Piecewise Linear Approximation")
    plt.savefig('error.png', bbox_inches='tight')
