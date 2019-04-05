
def u(n):
    return (n**11 + 1)//(n + 1)


def solve_equation_system(mat): # a matrix of size k x (k + 1)
    if len(mat) == 1:
        return [mat[0][1]//mat[0][0]]
    sub_mat = []
    for i in range(len(mat) - 1):
        row = []
        for j in range(1, len(mat) + 1):
            row.append(mat[i + 1][j] - mat[i][j])
        sub_mat.append(row)
    arr = solve_equation_system(sub_mat)

    # last coef
    x = mat[0][len(mat)]

    for j in range(1, len(mat)):
        x = x - arr[j - 1] * mat[0][j]
    arr.insert(0, x//mat[0][0])
    return arr


def compute_item(coef, i):
    sum = 0
    for c in coef[::-1]:
        sum = sum * i + c
    return sum


coef = [1, -1, 1, -1, 1, -1, 1, -1, 1, -1, 1]
u_arr = [1] # u_0


def sum_FIT_of_BOP():
    tmp_coef = []
    sum = 0
    i = 0

    while tmp_coef != coef:
        u_arr.append(u(i + 1))

        incorrect_term = compute_item(tmp_coef, i + 1)
        if i != 0 and incorrect_term != u_arr[-1]:
            print('Incorrect term: ', incorrect_term, ', expected:', u_arr[-1])
            sum += incorrect_term

        mat = []
        # build matrix
        for j in range(i + 1):
            row = []
            for k in range(i + 1):
                row.append((j + 1)**k)
            row.append(u_arr[j + 1])
            mat.append(row)
        print('Equation system:\n', mat)

        tmp_coef = solve_equation_system(mat)
        print('Solution:', tmp_coef)
        i += 1

    return sum

print(sum_FIT_of_BOP())