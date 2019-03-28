# According to http://www.numericana.com/answer/numbers.htm#partitions

DIVISOR = 1000000
p_arr = [1]

def P(n):

    for i in range(1, n + 1):
        j = 1
        k = 1
        s = 0
        while j > 0:
            j = i - (3 * k * k + k)//2
            if j >= 0:
                s = s - (-1)**k * p_arr[j]

            j = i - (3 * k * k - k)//2
            if j >= 0:
                s = s - (-1)**k * p_arr[j]

            k += 1
        p_arr.append(s % DIVISOR)
    return p_arr[-1]