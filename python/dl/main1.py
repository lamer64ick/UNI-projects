import pandas as pd
import numpy as np
from matplotlib import pyplot as plt

appstore_csv = pd.read_csv('./AppleStore.csv')
appstroredesc_csv = pd.read_csv('./appleStore_description.csv')

num_cols = [
    'size_bytes',
    'price',
    'rating_count_tot',
    'rating_count_ver',
    'sup_devices.num',
    'ipadSc_urls.num',
    'lang.num',
    # Эта фича - не числовая, а порядковая, но мы все равно возьмем ее как числовую для удобства
    'cont_rating',
]
cat_cols = [
    'currency',
    'prime_genre'
]

target_col = 'user_rating'

cols = num_cols + cat_cols + [target_col]

appstore_csv['cont_rating'] = appstore_csv['cont_rating'].str.slice(0, -1).astype(int)
print(appstore_csv[cols])
print(appstore_csv[cols].isna().mean())

for col in cat_cols:
    print(f"{col} DISTRIBUTION")
    print(appstore_csv[col].value_counts())
    print()
    
appstore_csv = appstore_csv.drop(columns=['currency'])
cat_cols.remove('currency')

ax1 = appstore_csv.hist(column=num_cols + cat_cols+[target_col], figsize=(14,10))
plt.show()
doc = Document()
doc.add_paragraph(appstore_csv.corr().style.background_gradient(cmap='coolwarm').set_precision(2))

