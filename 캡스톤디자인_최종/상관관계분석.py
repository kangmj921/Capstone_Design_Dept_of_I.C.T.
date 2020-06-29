import pandas as pd
from sklearn.linear_model import LinearRegression
from matplotlib import style
import matplotlib.pyplot as plt
import matplotlib
import numpy as np
import matplotlib.pyplot as plt
import matplotlib.font_manager as fm
import platform
from sklearn.linear_model import LinearRegression
from sklearn.model_selection import train_test_split
import openpyxl

data = pd.read_excel('./data/서울시_상관관계분석.xlsx', sheet_name='Sheet1')
corr = data.corr(method='pearson')
print(corr)

if platform.system() == 'Windows':
    # 윈도우인 경우
    font_name = fm.FontProperties(fname="c:/Windows/Fonts/malgun.ttf").get_name()
    plt.rc('font', family=font_name)

style.use('seaborn-talk')
plt.rc('font', family=font_name)
matplotlib.rcParams['axes.unicode_minus'] = False


def lin_regplot(X, y, model):
    plt.scatter(X, y, c='b')
    plt.plot(X, model.predict(X), c='r')


df = pd.read_excel('./data/서울시_연도별_상관관계분석.xlsx', sheet_name='Sheet1')
X = df[['유흥주점개수']].values
y = df[['범죄발생건수']].values

slr = LinearRegression()
slr.fit(X, y)

slope = slr.coef_[0]
intercept = slr.intercept_

print('회귀선 기울기: %.3f\n절편: %.3f' % (slope, intercept))

lin_regplot(X, y, slr)
plt.xlabel('유흥주점개수')
plt.ylabel('범죄발생건수')
plt.title('유흥주점개수에따른 한 해 범죄발생건수\n')
plt.show()

x = df[['인구', '인구밀도', '유흥주점개수']]
y = df[['범죄발생건수']]

x_train, x_test, y_train, y_test = train_test_split(x, y, train_size=0.6, test_size=0.4)

mlr = LinearRegression()
mlr.fit(x_train, y_train)

y_predicted = mlr.predict(x_test)

plt.scatter(y_test, y_predicted, alpha=0.4)
plt.xlabel('actual crime')
plt.ylabel('predicted crime')
plt.title('multiple linear regression')
plt.show()
print("accuracy score : ", mlr.score(x_test, y_test))

data2 = pd.read_excel('./data/중구_상관관계분석예측용.xlsx', sheet_name='Sheet1')
input_data = data2[['인구', '인구밀도', '유흥주점개수']]
output_data = list()
for i in range(len(input_data)):
    output_data.append(mlr.predict(np.array(input_data.loc[i][:]).reshape(1, 3)))
output_data = np.array(output_data).reshape(1, 15)

write_excel = openpyxl.load_workbook('./data/중구_상관관계분석예측용.xlsx')
sheet = write_excel['Sheet1']
k = 2
for j in range(len(input_data)):
    sheet.cell(row=k, column=8).value = round(output_data[0][j])
    k += 1
write_excel.save('중구_범죄발생건수_예측.xlsx')
