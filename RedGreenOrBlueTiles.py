RED = 2
GREEN = 3
BLUE = 4

def num_of_replace(n, k):
    maximum_tiles = n//k
    count = 0
    for i in range(1, maximum_tiles + 1):
        count += C(n - i * (k - 1), i)

    return count

def C(n, k):
    A = 1
    for i in range(1, n + 1):
        A *= i

    B = 1
    for i in range(1, k + 1):
        B *= i

    C = 1
    for i in range(1, n - k + 1):
        C *= i

    return A//(B * C)

def no_mixed_replace(n):
    out = 0
    for color in [RED, GREEN, BLUE]:
        out += num_of_replace(n, color)
    return out

print(no_mixed_replace(50))