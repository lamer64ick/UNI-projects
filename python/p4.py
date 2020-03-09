from math import pi

finput = open("input.txt", "r")
fouput = open("output.txt", "w")

R, L = (int(x) for x in finput.readline()[:].split(" "))

fouput.write(
    "{:.5f}".format(pi*L*R/2 - pi*R**2/2 + 2*pi*R**2)
)

finput.close()
fouput.close()