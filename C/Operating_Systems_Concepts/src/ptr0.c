#include <stdio.h>
#include <stdlib.h>

void fnctn0(int *j) {
	void *ptr0;
	
	ptr0 = j;
	printf("Memory Address : %p\n", ptr0);
	printf("Size of address : %ld\n", sizeof(j));
	printf("Size of value : %ld\n", sizeof(*j));
	/** figure this part out below*/
	*j = *j + *j;
}

int main(int argc, char* argv[]) {
	int i;
	
	i = atoi(argv[1]);
	fnctn0(&i);
	printf("Final result : i = %d\n", i);
	
	return 0;
}
