# Prolog - Auto Generated #
# import os, uuid
import pandas as pd
import numpy as np
import gc
import time
import os
import re

# os.chdir('C:/Users/koreyko_ea/Documents')

df66y = pd.read_csv('drom_compare_pollDATA/google_form_yandex_direct_66.csv')
df66g = pd.read_csv('drom_compare_pollDATA/google_form_google_ads_66.csv')
df74y = pd.read_csv('drom_compare_pollDATA/google_form_yandex_direct_74.csv')
df74g =  pd.read_csv('drom_compare_pollDATA/google_form_google_ads_74.csv')

dataset_y = df66y.append(df74y, ignore_index=True)
dataset_g = df66g.append(df74g, ignore_index=True)
dataset_y['idx'] = ["yd{}".format(i) for i in dataset_y.index]
dataset_g['idx'] = ["ga{}".format(i) for i in dataset_g.index]
dataset = dataset_y.append(dataset_g, ignore_index=True)
dataset.drop(columns=['statDate', 'q2', 'auto', 'geo', 'gender', 'age', 'poll'], inplace=True)
dataset.rename(columns={'idx': 'id'}, inplace=True)

del df66g, df66y, df74g, df74y, dataset_y, dataset_g

print("Number of unique ids: ", len(dataset.id.unique()))

ru_patterns = " .ru;. ru; .ru;,ru ;, ru ; ,ru ;,.ru;, .ru; ru ;"
dromru_patterns = "drom,ru;drom ru;дром ру;дром.ру;д-ром.ру;дром.ru;дромру;dromru;drom;дром"
autoru_patterns = "auto,ru;auto ru;avto.ru;avto,ru;авто.ру;авто.ru;автору"
avitoru_patterns = "avito,ru;avito ru;авито ру;авито.ру;авито.ru;авитору;avitoru;avito;авито"
e1ru_patterns = "e1,ru;e1 ru;е1 ру;е1.ру;e1.ru;e1ru;е1ру;e1;е1"
yoularu_patterns = "youla,ru;youla ru;юла ру;youla;юла.ру;юла.ru;юлару;yoularu;youla;юла"

# value '1' assume to be e1

domains = ["drom.ru", "auto.ru", "avito.ru", "e1.ru", "youla.ru"]
and_patterns = [';', " и "]

def replace_patterns():

    rupatterns = ru_patterns.split(';')
    patternlist = [
        dromru_patterns.split(";"),
        autoru_patterns.split(";"),
        avitoru_patterns.split(";"),
        e1ru_patterns.split(";"),
        yoularu_patterns.split(";")
    ]

    dataset['q1'] = dataset['q1'].str.lower()
    dataset['q1'] = dataset['q1'].str.replace('  ', ' ')
    dataset['q1'] = dataset['q1'].str.replace("'", "")

    listoftuples = list(
        zip(domains, patternlist)
    )

    for andp in and_patterns:
        dataset['q1'] = dataset['q1'].str.replace(andp, ',')
        
    print(rupatterns)
    # for ru in rupatterns:
    #     dataset['q1'] = dataset['q1'].str.replace(ru, ".ru")
    df = pd.DataFrame(dataset['q1'].str.contains(rupatterns[1]), columns=dataset.columns)
    print(df.head(10))
    print(df.loc[df['q1'] == True]) #print(df.loc[df['q1']])
    df1 = pd.DataFrame(dataset['q1'].loc[df.index[df['q1'] == True].tolist()], columns=dataset.columns) # [!] not ()
    print(df1.head(10))
    df1['q1'] = df1['q1'].str.replace(rupatterns[1], 'yay .ru')
    print(df1.head(10))
    # print(dataset.head(10))
    # print(dataset[(dataset['q1'].str.contains('drom'))])
    # for lt in listoftuples:
    #     for pattern in lt[1]:
    #         dataset['q1'] = dataset['q1'].str.replace(pattern, lt[0], case=False)

replace_patterns()

gc.collect()
