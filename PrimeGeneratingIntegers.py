# -*- coding: utf-8 -*-
"""
Created on Wed Apr  3 08:53:26 2019

@author: anhvt
"""

import math
import time

LIMIT = 100_000_000
prime = [True for i in range(LIMIT + 2)]

def is_satisfy_prime_generating_condition(n):
    for i in range(1, int(math.sqrt(n)) + 1):
        if n % i == 0:
            if not prime[i + n//i]:
                return False
    return True

def get_prime_list_sieve_method():
    p = 2
    while p * p <= len(prime):
        if prime[p] == True:
            for i in range(p * 2, len(prime), p):
                prime[i] = False
                
        p += 1
    out = []
    for i in range(3, len(prime)):
        if prime[i]:
            out.append(i)
    return out

def get_candidates(prime_list, limit):
    if len(prime_list) == 1:
        return [1, prime_list[0]]
    
    mid = (len(prime_list) + 1) // 2
    c1 = get_candidates(prime_list[:mid], limit)
    c2 = get_candidates(prime_list[mid:], limit)
    
    out = []
    l2 = len(c2)
    for x in c1:
        for i in range(l2):
            if x * c2[i] <= limit:
                out.append(x * c2[i])
            else:
                break
    return sorted(out)

def prime_generating_integers(limit):
    t0 = time.time()
    prime_list = get_prime_list_sieve_method()
    t1 = time.time()
    print('Get prime list done, length =', len(prime_list))
    print('Time:', (t1 - t0))
    
    t0 = time.time()
    candidate_list = [2 * x for x in get_candidates(prime_list, limit//2)]
    t1 = time.time()
    print('Get candidate time:', (t1 - t0))
    
    out = 1
    for c in candidate_list:
        if is_satisfy_prime_generating_condition(c):
            out += c
    
    return out

print('Sum:', prime_generating_integers(LIMIT))