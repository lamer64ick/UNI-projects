from math import pi, sin, cos, sqrt

finput = open("input.txt", "r")
fouput = open("output.txt", "w")

R, r = (float(x) for x in finput.readline()[:].split(" "))
# n = 5
# a = 360/n
# l = 2*r*abs(sin(a/2))
# h1 = r*abs(cos(a/2))
# h1 = sqrt(r**2 - l**2/4)
# S1 = 1/2*l*h1
# S2 = 1/2*l*(R-h1)
# print(l, h1)
fouput.write(
    "{:.5f}".format(
        pi*R*r
    )
)

finput.close()
fouput.close()