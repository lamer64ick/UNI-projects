import os
import matplotlib.pyplot as pyplot
import numpy as nmp
from dfmethod import Euler, RungeKutt, Adams, f, Point


def save(name='', fmt='png'):
    pwd = os.getcwd()
    iPath = './pictures/{}'.format(fmt)
    if not os.path.exists(iPath):
        os.mkdir(iPath)
    os.chdir(iPath)
    pyplot.savefig('{}.{}'.format(name, fmt), fmt='png')
    os.chdir(pwd)
    pyplot.close()


def draw(grid, N, name):

    SIZE = len(grid)
    xx = []
    dy = []
    for p in grid:
        xx.append(p.x)
        dy.append(p.y)

    h = abs(xx[0] - xx[SIZE-1])/N  # для функции, посчитанной вручную

    x = nmp.arange(xx[0], xx[SIZE-1], h)
    nmp.append(x, [1.0])
    figure = pyplot.figure()
    axes = figure.add_subplot(111)
    dy1 = []
    for x in xx:
        dy1.append(f(x))
    # axes.plot(xx, dy1, label="hnd", color='black')
    axes.plot(xx, dy, label="mtd", color='red')
    axes.set_title("method")

    print(nmp.subtract(f(x), dy))
    axes.grid(True)
    # for ax in figure.axes:
    #     ax.grid(True)

    # save(name=name, fmt='pdf')
    save(name=name, fmt='png')

    # pyplot.show()

xmin = 0.0
xmax = 12.0
ymin = 0.0
N = 11

x = nmp.arange(xmin, xmax, abs(xmin-xmax)/N)
y = f(x)
figure = pyplot.figure()
axes = figure.add_subplot(111)
axes.plot(x, y, color='black')
axes.set_title("вручную")
axes.grid(True)
save(name='by_hand', fmt='png')

euler = Euler()
grid1 = euler.run(xmin, ymin, xmax, N)
draw(grid1, N, 'euler')

runge = RungeKutt()
grid2 = runge.run(xmin, ymin, xmax, N)
draw(grid2, N, 'runge')

adams = Adams()
grid3 = adams.run(xmin, ymin, xmax, N)
draw(grid3, N, 'adams')
