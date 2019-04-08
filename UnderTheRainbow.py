# -*- coding: utf-8 -*-
"""
Created on Mon Apr  8 14:45:06 2019

@author: anhvt
"""

def C(n, k):
    
    A = 1
    for i in range(2, n - k + 1):
        A *= i

    B = 1
    for i in range(k + 1, n + 1):
        B *= i
    
    return B//A

x = [0 for i in range(8)]
n = [0 for i in range(8)]

for i in range(2, 8):
    if i == 2:
        x[i] = 1
    else:
        x[i] = C(10 * i, 20)
        k = i - 1
        while k >= 2:
            x[i] = x[i] - C(i, k) * x[k]
            k -= 1
    n[i] = C(7, i) * x[i]

expect = sum([i * n[i] for i in range(2, 8)])/sum([n[i] for i in range(2, 8)])

print(expect)