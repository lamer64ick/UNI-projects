
finput = open("input.txt", "r")
fouput = open("output.txt", "w")

w = []
while True:
    c = finput.read(1)
    if c == '\n':
        break
    w.append(c)
n = len(w)

def find(x, w, begin):
    for i in range(begin, n):
        if w[i] == x:
            return i
    return -1
    
out = [False for i in w]
while True:
    c = finput.read(1)
    if c == '':
        break
    id = find(c, w, 0)
    while id >= 0:
        out[id] = not out[id]
        id = find(c, w, id+1)
            

for i in range(n):
    if out[i]:
        fouput.write(w[i])
    else:
        fouput.write('.')

finput.close()
fouput.close()