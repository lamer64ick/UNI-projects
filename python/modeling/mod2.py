import numpy as np
import os
import matplotlib.pyplot as plt

# изменение концентрации жидкости вдоль канала для 2-мерн случая

n = 50
nt = 100
l, d = 1, 1/8
u, v, C = [], [], []
ax, bx = 0, 1
ay, by = 0, 1
dt = 0.001
X, Y = [ax, ],  [ay, ]
U, V = [], []
C = []

def phi(x, y):
    return np.sin(2*np.pi*x/l) * np.sin(2*np.pi*y/l)


def setU(x, y): #dphi/dy
    for i in range(n):
        U.append(
            - 2*np.pi/l*np.sin(2*np.pi*x/l)*np.cos(2*np.pi*y/l)
        )
    
    
def setV(x, y): #dphi/dx
    for i in range(n):
        V.append(
            2*np.pi/l*np.sin(2*np.pi*y/l)*np.cos(2*np.pi*x/l)
        )
    
    
def C0(x, y):
    return np.tanh((y - l/2)/d)


def init():
    
    for i in range(1, n):
        X.append(
            X[i-1] + V[i-1]*dt
        )
        Y.append(
            Y[i-1] + U[i-1]*dt
        )
    C.append(C0(X, Y))

def c_k(t):
    c = []
    for i in range(1, nt):
        if i == 0:
            c.append(
                C[t-1][i] +  0.5 * dt * (C[t-1][i+1] - C[t-1][n-1])/dx
            )
        elif i == n-1:
            c.append(
                C[t-1][i] +  0.5 * dt * (C[t-1][0] - C[t-1][i-1])/dx
            )
        else:c.append(
                C[t-1][i] +  0.5 * dt * (C[t-1][i+1] - C[t-1][i-1])/dx
            )
        return np.array(c)
    
    
def save(name='', fmt='png'):
    pwd = os.getcwd()
    iPath = './{}'.format(fmt)
    if not os.path.exists(iPath):
        os.mkdir(iPath)
    os.chdir(iPath)
    plt.savefig('{}.{}'.format(name, fmt), fmt='png')
    os.chdir(pwd)
    