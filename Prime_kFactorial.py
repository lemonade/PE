# -*- coding: utf-8 -*-
"""
Created on Fri Apr 12 09:04:58 2019

@author: anhvt
"""

def S(p):
    # (p - 3)!
    out = (p - 1)//2
    
    # (p - 4)!
    if p % 6 == 5:
        out += (p + 1)//6
    else:
        out += (5 * p + 1)//6
    
    # (p - 5)!
    out += ((p % 24) * p - 1)//24
    
    return out % p

LIMIT = 10**8
prime = [True for i in range(LIMIT)]

p = 2
while p * p <= len(prime):
    if prime[p] == True:
        for i in range(p * 2, len(prime), p):
            prime[i] = False
            
    p += 1
    
SUM = 0
for i in range(5, len(prime)):
    if prime[i]:
        SUM += S(i)

print(SUM)