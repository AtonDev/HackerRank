import sys, math
numbers = []
diff = 0
N = 0

def initialize(numbers, diff, N):
    numbers = []
    diff = int(sys.stdin.readline().split()[1])
    N = int(sys.stdin.readline().split()[0])
    l = sys.stdin.readline().split()
    for n in l:
        numbers.append(int(n))

def slow_count_pairs(number_list, k, n):
    count = 0
    for i in range(0, n):
        for j in range(i + 1, n):
            if math.fabs(number_list[i] - number_list[j]) == k:
                count += 1
    return count

def fast_count_pairs(number_list, k, n):
    s1 = set(number_list)
    new_list = []
    for i in range(n):
        new_list.append(number_list[i] + k)
    s2 = set(new_list)
    return len(s1.intersection(s2))

initialize(numbers, diff, N)
print fast_count_pairs(numbers, diff, N)