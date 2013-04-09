import sys





primes = [2, 3, 5, 7, 11, 13, 17, 19, 23 

, 29, 31, 37, 41, 43, 47, 53, 59, 61 

, 67, 71, 73, 79, 83, 89, 97, 101, 103 

, 107, 109, 113, 127, 131, 137, 139, 149, 151 

, 157, 163, 167, 173, 179, 181, 191, 193, 197 

, 199, 211, 223, 227, 229, 233, 239, 241, 251 

, 257, 263, 269, 271, 277, 281, 283, 293, 307 

, 311, 313, 317, 331, 337, 347, 349, 353, 359 

, 367, 373, 379, 383, 389, 397, 401, 409, 419 

, 421, 431, 433, 439, 443, 449, 457, 461, 463 

, 467, 479, 487, 491, 499, 503, 509, 521, 523 

, 541, 547, 557, 563, 569, 571, 577, 587, 593 

, 599, 601, 607, 613, 617, 619, 631, 641, 643 

, 647, 653, 659, 661, 673, 677, 683, 691, 701 

, 709, 719, 727, 733, 739, 743, 751, 757, 761 

, 769, 773, 787, 797, 809, 811, 821, 823, 827 

, 829, 839, 853, 857, 859, 863, 877, 881, 883 

, 887, 907, 911, 919, 929, 937, 941, 947, 953 

, 967, 971, 977, 983, 991, 997, 1009]


def main():
	N = int(sys.argv[1])
	print divisors_n_fact_sqr(N) % 1000007
	return 0


fact_cache = {}

def prime_fact(n, d, start):
	if n in fact_cache and start == 0:
		return fact_cache[n]
	if n == 1:
		return d
	else:
		i = start
		isPrime = True
		while i < len(primes):
			p = primes[i]
			i += 1
			if p > n:
				return d
			elif n % p == 0:
				m = multiplicity(n, p)
				d[p] = m
				d = prime_fact(n / (p ** m), d, i)
				isPrime = False
				break
		if isPrime:
			d[n] = 1
		if start == 0:
			fact_cache[n] = d
		return d

def prime_fact2(n, start):
	d = {}
	if n in fact_cache:
		return fact_cache[n]
	if n == 1:
		return d
	else:
		i = start
		isPrime = True
		while i < len(primes):
			p = primes[i]
			i += 1
			if n % p == 0:
				m = multiplicity(n, p)
				d[p] = m
				d = combine2(prime_fact2(n / (p ** m), start), d)
				isPrime = False
				break
		if isPrime:
			d[n] = 1
		fact_cache[n] = d
		return d

def multiplicity(n, p):
	counter = 0;
	tmp = p
	while n % tmp == 0:
		counter += 1
		tmp *= p
	return counter
		
def combine1(d0, d1):
	for i in d1:
		if i in d0:
			d0[i] += d1[i]
		else:
			d0[i] = d1[i]
	return d0

def combine2(d0, d1):
	res = {}
	for i in d0:
		res[i] = d0[i]
	for i in d1:
		if i in res:
			res[i] += d1[i]
		else:
			res[i] = d1[i]
	return res

def fact_n_fact(n):
	res = {}
	for i in range(2, n+1):
		pf = prime_fact2(i,  0)
		res = combine1(res, pf)
	return res


def divisors_n_fact_sqr(n):
	fact = fact_n_fact(n)
	result = 1
	for p in fact:
		result *= ((2*fact[p] + 1) % 1000007)

	return (result % 1000007)


if __name__ == "__main__":
	exit = main()
	sys.exit(exit)































