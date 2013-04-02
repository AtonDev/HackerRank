#include <stdio.h>
#include <string.h>
#include <math.h>
#include <stdlib.h>



int insertionSort2(int ar_size, int *  ar) {
    int insert = ar[ar_size - 1];
    int shifts = 0;
    for (int i = ar_size - 1; i >= 0; --i) {
        if (insert < ar[i - 1]) {
            ar[i] = ar[i - 1];
            shifts++;
        } else {
            ar[i] = insert;
            break;
        }
    }
    return shifts;

}

int insertionSort(int ar_size, int *  ar) {
	int result = 0;
    for (int i = 2; i <= ar_size; i++) {
        result += insertionSort2(i, ar);
    }
    return result;
}


/* Tail starts here */
int main() {
   
   int _ar_size;
   scanf("%d", &_ar_size);
   int _ar[_ar_size], _ar_i;
   for(_ar_i = 0; _ar_i < _ar_size; _ar_i++) { 
      scanf("%d", &_ar[_ar_i]); 
   }

   printf("%d\n", insertionSort(_ar_size, _ar));
   
   return 0;
}