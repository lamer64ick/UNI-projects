import numpy as nmp
from ctypes import *


# function -------------------

# solved by hand -------------
def f(x):
    return 1/2*x**2 - 1/2*x + 1/4 + 3/4*nmp.exp(-2*x)
# ----------------------------


def df(x, y):
    return x**2 - 2 * y
# function -------------------


class Point(Structure):
    _fields_ = ('x', c_float), ('y', c_float)


# euler formla ---------------
class Euler:
    def y_i2_0(self, x_i1, y_i1, h):
        return y_i1 + h * df(x_i1, y_i1)

    def y_i2(self, x_i1, y_i1, h):
        dx_i1 = x_i1 + h / 2
        dy_i1 = y_i1 + h / 2 * df(x_i1, y_i1)
        # print(h*df(dx_i1, dy_i1))
        return y_i1 + h * df(dx_i1, dy_i1)

    def run(self, x_i1, y_i1, x_i2, N):
        grid = []
        h = abs(x_i2 - x_i1) / N

        print(h, N)
        while x_i1 < x_i2:
            # print('x: {0:2f} y: {1:2f} df: {2:2f}'
            #       .format(x_i1, y_i1, df(x_i1, y_i1)))
            grid.append(Point(x_i1, y_i1))
            y_i1 = self.y_i2(x_i1, y_i1, h)
            x_i1 += h

        return grid


# ----------------------------


# ----------------------------
class RungeKutt:

    def y_i2(self, x_i1, y_i1, h):
        k1 = df(x_i1, y_i1)
        k2 = df(x_i1 + h / 2, y_i1 + h * k1 / 2)
        k3 = df(x_i1 + h / 2, y_i1 + h * k2 / 2)
        k4 = df(x_i1 + h, y_i1 + h * k3)
        return y_i1 + h / 6 * (k1 + 2 * k2 + 2 * k3 + k4)

    def run(self, x_i1, y_i1, x_i2, N, k=0):
        grid = []
        h = nmp.absolute(x_i2 - x_i1) / N

        print("Runge-Kutt:")

        while x_i1 < x_i2:
            # print('x: {0:2f} y: {1:2f} df: {2:2f}'
            #       .format(x_i1, y_i1, df(x_i1, y_i1)))
            grid.append(Point(x_i1, y_i1))
            y_i1 = self.y_i2(x_i1, y_i1, h)
            x_i1 += h

        return grid


# использует рунге-кутты для первых 4х узлов
class Adams:

    def y_i2(self, grid, h, k):
        return grid[k].y + h / 24 * (55 * df(grid[k].x, grid[k].y) - 59 * df(grid[k-1].x, grid[k-1].y) +
                                       37 * df(grid[k-2].x, grid[k-2].y) - 9 * df(grid[k-3].x, grid[k-3].y))

    def run(self, x_i1, y_i1, x_i2, N):
        grid = []
        h = nmp.absolute(x_i2 - x_i1) / N

        print("Adams-Bashford:")
        k = 4
        x_i12 = x_i1 + h*(k+1)
        rk = Euler()
        y = [Point(1, 1), Point(x_i1 + h, 5), Point(x_i1 + h*2, 8), Point(x_i1 + h*3, 12)]
        while x_i1 < x_i12:
            grid.append(Point(x_i1, y_i1))
            print('x: {0:2f} y: {1:2f} df: {2:2f}'
                  .format(x_i1, y_i1, df(x_i1, y_i1)))
            y_i1 = rk.y_i2(x_i1, y_i1, h)
            x_i1 += h
        # x_i1 += h*3
        # grid.extend(y)
        while x_i1 < x_i2:
            y_i1 = self.y_i2(grid, h, k-1)
            print('x: {0:2f} y: {1:2f} df: {2:2f}'
                  .format(x_i1, y_i1, df(x_i1, y_i1)))
            grid.append(Point(x_i1, y_i1))
            x_i1 += h
            k += 1

        return grid
