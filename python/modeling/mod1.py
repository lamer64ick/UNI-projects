import numpy as np
import os
import matplotlib.pyplot as plt
import matplotlib.animation as animation

def save(name='', fmt='png'):
    pwd = os.getcwd()
    iPath = './{}'.format(fmt)
    if not os.path.exists(iPath):
        os.mkdir(iPath)
    os.chdir(iPath)
    plt.savefig('{}.{}'.format(name, fmt), fmt='png')
    os.chdir(pwd)
    # plt.close()
    
# изменение концентрации жидкости вдоль канала для 1-мерн случая

i = 0
Nframes = 360
Ntime = 1000
ax, bx = 0, 1

def c_by_tx(n = 50, const=1):

    x, dx = np.linspace(ax, bx, n, retstep=True) # сетка и шаг по х
   
    dt = 0.1*dx/const # шаг по t
    c = []
    
    def c0(x): #
        c = 1/8
        a, b = 1, 4*c
        return a*np.exp(-0.5*np.power(((x - b)/c), 2))
        # return np.exp(-0.5*np.power(((x-0.5)/0.08), 2))

    def scheme(t):
        # print(t, x, u)
        # if t == 0:
        #     return c0(datax)
        c_ki = []
        for i in range(n):
            if i == 0:
                c_ki.append(c[t-1][i] + 0.5 * const * dt *
                           (c[t-1][i+1] - c[t-1][n-1])/dx)
            elif i == n-1:
                c_ki.append(c[t-1][i] + 0.5 * const * dt *
                           (c[t-1][0] - c[t-1][i-1])/dx)
            else:
                c_ki.append(c[t-1][i] + 0.5 * const * dt *
                           (c[t-1][i+1] - c[t-1][i-1])/dx)

        return np.array(c_ki)

    c.append(c0(x))
    for t in range(1, Ntime):
        c.append(scheme(t))

    return (x, c)
    
def draw_plots():
    
    plt.style.use('classic')
    # fig = plt.figure() # figsize=(x, y)
    # ax = fig.add_subplot(1,1,1)
    # fn = plt.plot([], [])[0] 
    
    # def init():
    #     fn.set_data([],[])
    #     plt.grid(True)
    #     return (fn, )

    # def update(frame):
    #     global i
    #     ax.clear()
    #     fn.set_data(x, c[i])
    #     plt.plot(x, c[i], color='cyan')
    #     plt.grid(True)
    #     plt.ylim([-2, 2])
    #     plt.xlim([0, 1])
    #     i += 1
    #     return (fn, )
    
    # call the animator. blit=True means only re-draw the parts that have changed.
    # anim = animation.FuncAnimation(fig, update, frames=Nframes, interval=20, # frames by time
    #                                init_func=init)
    # anim = animation.FuncAnimation(fig, update, frames=Nframes, interval=20)
    
    plt.title("Concentration")
    plt.xlim(0, 1)
    plt.ylim(0, 2)
    plt.xlabel('x')
    plt.ylabel('C')
    plt.axis('equal')
        
    for i in range(1, 4):
        fig, ax = plt.subplots(1, 2, figsize=(6, 2))
        x, c = c_by_tx(50*i, 1)
        ax[0].plot(x, c[1], label="t_0")
        ax[1].plot(x, c[-1], label="t_n")
        # ax.legend()
        fig.savefig('png/figure_{}'.format(i))
        
    plt.show()
    plt.close()    

draw_plots()