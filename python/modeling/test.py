import random
import numpy as np


def cumsum_and_erase(A, erase=1):
    ln = len(A)
    i = 0
    while i < ln:
        if i > 0:
            A[i] += A[i-1]
        ln = len(A)
        i += 1
    B = list(filter(lambda x: x != erase, A))
    return B


def process(sentences):
    ln = len(sentences)
    many = []
    i = 0
    while i < ln:
        line = list(filter(lambda x: x.isalpha() ==
                           True, sentences[i].split(' ')))
        if not line:
            ln -= 1
        else:
            many.append(
                " ".join(line)
            )
        i += 1
    result = many
    return result


class Neuron:

    def __init__(self, w, f=lambda x: x):
        self.w = w
        self.f = f

    def forward(self, x):
        self.x = x
        sum = 0
        for litem, witem in self.x, self.w:
            sum += litem*witem
        return self.f(sum)

    def backlog(self):
        return self.x


# import scipy.stats as sps


def numpy_mult():
    a = np.random.randint(1, 10, size=(3, 3))
    b = np.random.randint(1, 10, size=(4, 3))
    print(np.dot(a, b))

# функция, решающая задачу с помощью NumPy


def np_diag_2k():
    a = np.random.randint(1, 10, size=(3, 3))
    # a = np.arange(16).reshape(4, 4)
    # print(dir(a))
    id = np.diag_indices(len(a))
    print(id)
    print(a, a[id])
    p = 1
    ex = 0
    for el in a[id]:
        if el % 2 == 0:
            ex = 1
            p = p*el
    if ex == 0:
        result = 0
    else:
        result = p
    print(result)


def encode1():
    # print(cumsum_and_erase([1, 2, 3, 4, 5, 6], erase = 1))
    # print(Neuron([1, 2]).forward([2, 3]))
    # numpy_mult()
    # np_diag_2k()
    a = [2, 2, 3, 2, 4, 4, 1, 4]
    # u, rep = np.unique(a, return_counts=True)
    # u, indices = np.unique(a, return_inverse=True) # indices in 'u'
    # print(a, u, rep, indices)
    res = [a[0]]
    rep = [1]
    j = 0
    for i in range(1, len(a)):
        if a[i] != a[i-1]:
            res.append(a[i])
            rep.append(1)
            j += 1
        else:
            rep[j] += 1
            continue
    result = tuple((np.array(res), np.array(rep)))
    print(a)
    print(result)           


def encode():
    a = np.array([0, 0, 0, 1, 2, 4])
    print(a)
    result = tuple(([], []))
    i = 0
    asize = a.size
    while i < asize:
        ids = np.where(a[i:asize] == a[i])[0] + i
        idsize = ids.size
        nrep = 1
        if idsize > 1:
            dif = np.diff(ids)
            ids1 = np.where(dif==1)[0]
            nrep += ids1.size
        result[0].append(a[i])
        result[1].append(nrep)    
        i += nrep
    result = tuple(
        (np.array(result[0]), np.array(result[1]))
    )
    
    print(result)
             
def transform(X, a=1):
    """
    param X: np.array[batch_size, n]
    """
    print(t)
    m = X[0].size
    id1 = np.arange(1,m-1,2)
    id2 = np.arange(0,m,2)
    result = []
    for i in range(len(X)):
        x = np.zeros(m)
        x[id1] = a
        x[id2] = X[i][id2]**2
        x = np.flip(x, axis=0)
        result.append(
            np.concatenate((X[i], x), axis=None)
        )
    
    return np.array(result)

def best_transform(X, a=1):
    X_ = X.copy() ** 3
    X_[:, 1::2] = a
    return np.hstack((X, X_[:, ::-1]))

# newvals = np.setdiff1d(a,u)
# print(newvals)
# encode()
t = np.arange(20).reshape(4, 5)
print(best_transform(t))