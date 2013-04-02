#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdlib.h>
#include <assert.h>

/* Head ends here */
void partition(int ar_size, int *  ar) {
	int p = ar[0], index, index1 = 0, index2 = 0;
	int ar1[ar_size], ar2[ar_size];
	for (index = 1; index < ar_size; index++) {
		int value = ar[index];
		if (value < p) {
			ar1[index1] = value;
			index1++;
		} else {
			ar2[index2] = value;
			index2++;
		}
	}
	for (int i = 0; i < ar_size; i++) {
		if (i < index1) {
			ar[i] = ar1[i];
		} else if (i == index1) {
			ar[i] = p;
		} else {
			ar[i] = ar2[i - index1 - 1];
		}
	}

}

/* Tail starts here */
int main() {
   
    int _ar_size;
	scanf("%d", &_ar_size);
	int _ar[_ar_size], _ar_i;
	for(_ar_i = 0; _ar_i < _ar_size; _ar_i++) { 
	   scanf("%d", &_ar[_ar_i]); 
	}

	partition(_ar_size, _ar);
	for (int i = 0; i < _ar_size; i++) {
		printf("%d ", _ar[i]);
	}
	printf("\n");
   
    return 0;
}