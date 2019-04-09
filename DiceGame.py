# -*- coding: utf-8 -*-
"""
Created on Tue Apr  9 10:29:59 2019

@author: anhvt
"""
NUM_OF_PYRAMID = 9
NUM_OF_CUBIC = 6

NUM_OF_SURFACE_PYRAMID = 4
NUM_OF_SURFACE_CUBIC = 6

sum1_spectre = [0 for i in range(NUM_OF_PYRAMID * NUM_OF_SURFACE_PYRAMID + 1)]
sum2_spectre = [0 for i in range(NUM_OF_CUBIC * NUM_OF_SURFACE_CUBIC + 1)]

def factorial(n):
    A = 1
    for i in range(2, n + 1):
        A *= i
    return A

# multinomial coefficients
def C(n, arr):
    A = factorial(n)
    
    B = 1
    for x in arr:
        if x > 1:
            B *= factorial(x)
    return A//B

# an integer n as sum of k non-negative integer (in order)
def partitions(n, k):
    if k == 1:
        return [[n]]
    out = []
    for i in range(n + 1):
        out = out + [[i] + x for x in partitions(n - i, k - 1)]
    return out

for c in partitions(NUM_OF_PYRAMID, NUM_OF_SURFACE_PYRAMID):
    sum1_spectre[sum([c[i] * (i + 1) for i in range(NUM_OF_SURFACE_PYRAMID)])] += C(NUM_OF_PYRAMID, c)
    
for c in partitions(NUM_OF_CUBIC, NUM_OF_SURFACE_CUBIC):
    sum2_spectre[sum([c[i] * (i + 1) for i in range(NUM_OF_SURFACE_CUBIC)])] += C(NUM_OF_CUBIC, c)

freq = 0
for i in range(1, len(sum1_spectre)):
    freq += (sum1_spectre[i] * sum(sum2_spectre[:i]))

print(freq/(sum(sum1_spectre) * sum(sum2_spectre)))