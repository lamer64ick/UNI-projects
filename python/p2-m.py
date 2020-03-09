input = open("input.txt", "r")
output = open("output.txt", "w")

data = []

data.append(input.readline()[:-1])

data.append(input.readline())

a = [0 for i in data[0]]

for i in data[1]:

    for j in range(len(data[0])):

        if data[0][j] == i:

            a[j] = (a[j] + 1) % 2

    for j in range(len(data[0])):

        if a[j] == 0:

            output.write(".")

        else:

            output.write(data[0][j])
