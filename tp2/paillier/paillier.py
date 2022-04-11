##############################################################################

# Completer le code
# Debugger
# Tester les proprietes d'homomorphie de Paillier

##############################################################################

from sympy import *
from random import *

def getprime(k):
	p = randprime(2**(k-1), 2**k) # [ 2^(k-1) , 2^k )
	return p

def genkeys(k):
    p = getprime(k)
    q = getprime(k)
    while (p == q):
        q = getprime(k)

    n = int(p * q)
    phi = int((p-1) * (q-1))
    return [n, mod_inverse(n, phi)]

def encrypt(m, pk):
    r = randint(1, pk-1)
    n2 = pk * pk
    g = pk + 1
    c = g**m * pow(r, pk, n2)
    return int(c)

def decrypt(c, pk, sk):
    n2 = pk * pk
    r = pow(c, sk, pk)
    s = mod_inverse(r, pk)
    m = ((c * pow(s, pk, n2)) % n2 - 1)/pk
    return int(m)

################ Tests fonctions de chiffrement et dechiffrement ######################

pk, sk = genkeys(300)
print("pk = ", pk)
print("sk = ", sk)
print()

x = 2
X = encrypt(x, pk)
dx = decrypt(X, pk, sk)
print('Decrypt ( Encrypt (', x, ')) = ', dx)

################ Tests des proprietes d'homomorphie ####################################

def oplus(encX, encY, pk):
    n2 = pk * pk
    return (encX * encY) % n2

def produitParConstante(encX, y, pk):
    n2 = pk * pk
    return pow(encX, y, n2)

# def oppose(X, pk):
#     n2 = pk * pk
#     return mod_inverse(X, n2)

y = 1000
Y = encrypt(y, pk)

Z1 = oplus(X, Y, pk)
Z2 = produitParConstante(X, y, pk)
# Z3 = oppose(Y, pk)

print()

# Preuves des proprietes d'homomorphie:
v1 = decrypt(Z1, pk, sk)
v2 = (x + y) % (pk*pk)
if v1 == v2:
    print(v1, " = ", v2)

v3 = decrypt(Z2, pk, sk)
v4 = (x * y) % (pk*pk)
if v3 == v4:
    print(v3, " = ", v4)

# print(decrypt(Z3, pk, sk) - pk)
