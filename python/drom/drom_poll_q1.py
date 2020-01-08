# Prolog - Auto Generated #
# import os, uuid
import pandas as pd
import numpy as np
import gc
import time 

dataset = pd.read_csv('q1-for-py-test.csv')

ru_patterns = " .ru;. ru; .ru;,ru ;, ru ; ,ru ;,.ru;, .ru; ru "
dromru_patterns = "drom ru;drom,ru;дром.ру;д-ром;дром.ru;дромру;dromru"
autoru_patterns = "auto ru;auto,ru;авто.ру;авто.ru;автору"
avitoru_patterns = "avito ru;avito,ru;авито.ру;авито.ru;авитору"
e1ru_patterns = "e1 ru;e1,ru;е1.ру;e1.ru;e1ru"
yoularu_patterns = "youla ru;youla,ru;youla;юла.ру;юла.ru;юлару"
# value '1' assume to be e1

domains = ["drom.ru", "auto.ru", "avito.ru", "e1.ru", "youla.ru"]
# patterns и domains строго в соответствующем порядке !
and_patterns = [';', " и "]

def replace_patterns():

    rupatterns = ru_patterns.split(';')
    patternlist = [
        dromru_patterns.split(";"),
        dromru_patterns.split(";"),
        autoru_patterns.split(";"),
        avitoru_patterns.split(";"),
        e1ru_patterns.split(";"),
        yoularu_patterns.split(";")
    ]

    dataset
    dataset['q1'] = dataset['q1'].str.lower()
    dataset['q1'] = dataset['q1'].str.replace("'", "")

    for andp in and_patterns:
        dataset['q1'] = dataset['q1'].str.replace(andp, ',')
    
    for ru in rupatterns:
        dataset['q1'] = dataset['q1'].str.replace(ru, ".ru")

    for dmn in domains:
        for patterntype in patternlist:
            for pattern in patterntype:
                dataset['q1'] = dataset['q1'].str.replace(pattern, dmn)

t0 = time.time()

replace_patterns()

q1sr = dataset['q1'].str.split(",", expand = True)
print(q1sr.head(10))

colnames = ["q1.{}".format(col+1) for col in q1sr]

q1sr = q1sr.set_axis(colnames, axis=1, inplace=False)
dataset = dataset.join(q1sr)
del q1sr
dataset.drop(columns=["q1"], inplace=True)
print(dataset.head(10))
dataset = pd.melt(dataset, id_vars="id", var_name="Attribute", value_name="a1")
print(dataset.head(10))
dataset.drop(columns=["Attribute"], inplace=True)
print(dataset.head(10))

# print(type(dataset['a1'][0]), dataset['a1'][0])

dataset['a1'].replace("", np.nan, inplace=True)
dataset['a1'] = dataset['a1'].dropna()
dataset['a1'] = dataset['a1'].str.lstrip()
dataset['a1'] = dataset['a1'].dropna()
dataset['a1'] = dataset['a1'].str.lstrip()
print(dataset.head(10))

t1 = time.time()

f = open("time_spend.txt", "a")
f.write("{0:.3f}\n".format(t1 - t0))
f.close()
gc.collect()
