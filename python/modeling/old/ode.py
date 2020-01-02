import numpy as np
from matplotlib import pyplot as plt

def euler(f, y0, at, bt, n):

    t = np.linspace(at, bt, num=n)
    deltat = abs(at - bt)/n
    y = [y0]

    for i in range(1, n):
        y.append(
            y[i-1] + deltat*f(t[i-1], y[i-1])
        )
        print(t[i], y[i])
    y = np.array(y)
    
    return tuple((t, y))