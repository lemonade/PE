import math

def load_words():
    lines = open('C:\\Users\\Admin\\Downloads\\p098_words.txt', 'r')
    for line in lines:
        return [w.replace('"', '') for w in line.split(',')]

def count_char_of_word(word):
    m = {}
    for c in word:
        m[c] = m.get(c, 0) + 1

    l = []
    for c in m:
        l.append((c, m[c]))
    return tuple(sorted(l, key=lambda x: x[0]))

def create_anagram_group(words):
    m = {}
    for word in words:
        t = count_char_of_word(word)
        if m.get(t, -1) == -1:
            m[t] = [word]
        else:
            m[t].append(word)
    out = {}
    for t in m:
        if len(m[t]) > 1:
            out[t] = m[t]

    return out


def replace_to_square_number(word1, word2, char_map):
    l = len(char_map)
    char_set = str.join('', [c[0] for c in char_map]).replace(word1[-1], '').replace(word2[-1], '')
    m = {}
    out = []
    for c1 in '014569':
        m[word1[-1]] = c1
        if word1[-1] == word2[-1]:
            remain_digits = '0123456789'.replace(c1, '')
            candidates = get_candidate_of_digit_replacement(l - 1, remain_digits)
            for candidate in candidates:
                for i in range(l - 1):
                    m[char_set[i]] = candidate[i]

                num1 = int(str.join('', [m[c] for c in word1]))
                num2 = int(str.join('', [m[c] for c in word2]))

                if len(str(num1)) != len(str(num2)):
                    continue

                sqr1 = math.sqrt(num1)
                sqr2 = math.sqrt(num2)

                if int(sqr1) ** 2 == num1 and int(sqr2) ** 2 == num2:
                    out.append([num1, num2])
        else:
            tmp1 = '014569'.replace(c1, '')
            for c2 in tmp1:
                m[word2[-1]] = c2

                remain_digits = '0123456789'.replace(c1, '').replace(c2, '')
                candidates = get_candidate_of_digit_replacement(l - 2, remain_digits)
                for candidate in candidates:
                    for i in range(l - 2):
                        m[char_set[i]] = candidate[i]

                    num1 = int(str.join('', [m[c] for c in word1]))
                    num2 = int(str.join('', [m[c] for c in word2]))

                    if len(str(num1)) != len(str(num2)):
                        continue

                    sqr1 = math.sqrt(num1)
                    sqr2 = math.sqrt(num2)

                    if int(sqr1)**2 == num1 and int(sqr2)**2 == num2:
                        out.append([num1, num2])

    return out


def get_candidate_of_digit_replacement(l, digits):
    if l == 1:
        return list(digits)

    out = []
    for d in digits:
        _digits = digits.replace(d, '')
        arr = [d + x for x in get_candidate_of_digit_replacement(l - 1, _digits)]
        out = out + arr

    return out


def find_biggest_among_anagramic_squares(groups):
    big_one = 0

    for t in groups:
        pair = groups[t]
        print('---- Considering pair ', pair)
        result = replace_to_square_number(pair[0], pair[1], t)
        if len(result) > 0:
            print('  Candidate: ', result)
            for pair in result:
                big_one = max(big_one, max(pair[0], pair[1]))
        else:
            print('  No candidate')

    return big_one


groups = create_anagram_group(load_words())

for t in groups:
    print(t, ' key of: ', groups[t])

print(find_biggest_among_anagramic_squares(groups))