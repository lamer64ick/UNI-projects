import pandas as pd
import numpy as np
import gc
import time 
# importing regex module 
import re 


df66y = pd.read_csv('drom_compare_pollDATA/google_form_yandex_direct_66.csv')
df66g = pd.read_csv('drom_compare_pollDATA/google_form_google_ads_66.csv')
df74y = pd.read_csv('drom_compare_pollDATA/google_form_yandex_direct_74.csv')
df74g =  pd.read_csv('drom_compare_pollDATA/google_form_google_ads_74.csv')
print("in the beggining: {} {} {} {}".format(len(df66y.index), len(df66g.index), len(df74y.index), len(df74g.index)))

dataset_y = df66y.append(df74y, ignore_index=True)
dataset_g = df66g.append(df74g, ignore_index=True)
print("new len: {0} {1}".format(len(dataset_y.index), len(dataset_g.index)))

dataset_y['idx'] = ["yd{}".format(i) for i in dataset_y.index]
dataset_g['idx'] = ["ga{}".format(i) for i in dataset_g.index]
dataset = dataset_y.append(dataset_g, ignore_index=True)

print(dataset[(dataset['idx'].str.startswith('ga'))])

dataset.drop(columns=['statDate', 'q2', 'auto', 'geo', 'gender', 'age', 'poll'], inplace=True)
dataset.rename(columns={'idx': 'id'}, inplace=True)

# dataset_y['idx'] = ["yd{}".format(i) for i in dataset_y.index]
# dataset_g['idx'] = ["ga{}".format(i) for i in dataset_g.index]
# dataset = pd.concat([dataset_y, dataset_g])
# print(dataset.columns)
# dataset.drop(columns=['statDate', 'q2', 'auto', 'geo', 'gender', 'age', 'poll'], inplace=True)
# print(dataset.head(10))

# gc.collect()