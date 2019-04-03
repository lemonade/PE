RED = 2
GREEN = 3
BLUE = 4
LENGTH = 50

map = {}

def num_of_replace(n):
    if n < RED:
        return 1
    elif n < GREEN:
        return 2
    elif n < BLUE:
        return 4
    else:
        if map.get(n, -1) != -1:
            return map[n]

        count = 1
        for color in [RED, GREEN, BLUE]:
            for i in range(n - color + 1):
                count += num_of_replace(n - color - i)

        map[n] = count
        return count

print(num_of_replace(LENGTH))


