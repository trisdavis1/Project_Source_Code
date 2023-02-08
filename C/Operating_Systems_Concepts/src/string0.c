#include <stdio.h>
#define BUF_SIZE 1024

int to_lower_case(char* str) {
	int i = 0;
	int nr_converted_char = 0;
	
	for(i = 0; str[i] != '\0'; i++) {
	/** This for-loop convrets upper-case alphabets into lowercase
		Hint: refer to the ASCII table     */
		if(str[i] >= 'A' && str[i] <= 'Z'){
			str[i] = str[i] + 32;
			nr_converted_char++;
		}
	}
	return nr_converted_char;
}

int main(int argc, char*argv[]) {
	char buffer[BUF_SIZE];
	char *ptr0;

	printf("Entering a string: ");
	scanf("%s", buffer);
	
	printf("Number of converted characters is %d\n", to_lower_case(buffer));
	printf("Converted string: ");
	
	for(ptr0 = buffer; *ptr0 != '\0'; ptr0++)
		printf("%c", *ptr0);
	printf("\n");
	
	return 0;
}