from scipy.optimize import linprog
import numpy as np


def print_matrix(matrix):
    for i in range(len(matrix)):
        for j in range(len(matrix[i])):
            print("{:4d}".format(matrix[i][j]), end="")
        print()


matrix = [[6, 4, 1, 3],
          [1, 5, 2, 1],
          [-1, 3, 3, 3],
          [2, 5, -2, -2]]

print_matrix(matrix)
n = len(matrix)
m = len(matrix[0])
print("size: ", n, "x", m)

H = np.array(matrix)

minimumsOfAlpha = []
for i in range(n):
    minimumsOfAlpha.append(min(H[i, :]))
alpha = max(minimumsOfAlpha)
print("alpha:", alpha)

maximumsOfBeta = []
for i in range(m):
    maximumsOfBeta.append(max(H[:, i]))
beta = min(maximumsOfBeta)
print("beta:", beta)

if alpha != beta:
    print("Игра разрешима в смешанных стратегиях:")
    solutionX = linprog([1 for i in range(n)], -H.transpose(), [-1 for i in range(m)]).x
    print("x: ", solutionX)
    solutionY = linprog([-1 for i in range(m)], H, [1 for i in range(n)]).x
    print("y: ", solutionY)
    I = 1 / sum(solutionY)
    print("I: ", I)
    print("p: ", [I * variable for variable in solutionX])
    print("q: ", [I * variable for variable in solutionY])
else:
    print("Игра разрешима в чистых стратегиях")
