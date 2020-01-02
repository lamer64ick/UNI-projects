import numpy as np
from matplotlib import pyplot as plt

x = np.linspace(-3, 3, 200)

fig, ax = plt.subplots()

for a in range(1, 11):
    y = x*(x + 2)*(x - 2)/a
    ax.plot(x, y)
    fig.savefig('мой график' + str(a))

plt.show()