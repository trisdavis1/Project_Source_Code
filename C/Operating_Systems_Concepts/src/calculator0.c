#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <math.h>

#define BUF_SIZE 16

int main(void) {
	int num;
	pid_t child_pid;
	int status;
	/** fildes[0] is read end and fildes[1] is write end for both fildes0 & fildes1 */
	int fildes0[2];
	int fildes1[2];
	char buffer[BUF_SIZE];
	
	if(!(pipe(fildes0) == 0)) {
		perror("pipe");
		exit(1);
	}
	if(!(pipe(fildes1) == 0)) {
		perror("pipe");
		exit(1);
	}
	
	child_pid = fork();
	
	if(child_pid == 0) {
		/** close the write end */
		close(fildes0[1]);
		close(fildes1[0]);
		
		while(num >= 0){
			if(read(fildes0[0], buffer, BUF_SIZE - 1) == -1) {
				perror("read");
				exit(2);
			}
			
			num = atoi(buffer);
			printf("In child. Generating the square of %d recieved from parent...\n", num);
			printf("Sending back to parent...\n");
			sprintf(buffer, "%d\n", num * num);
			
			if(write(fildes1[1], buffer, strlen(buffer) + 1) == -1){
				perror("write");
				exit(3);
			}
		}
		
	}else {
		/** close the read end */
		close(fildes0[0]);
		close(fildes1[1]);
		
		while(num >= 0){
			printf("Enter an integer to square or enter a neg value to quit: ");
			scanf("%s", buffer);
			num = atoi(buffer);
			
			if(write(fildes0[1], buffer, strlen(buffer) + 1) == -1) {
				perror("write");
				exit(3);
			}
			if(read(fildes1[0], buffer, BUF_SIZE-1)==-1) {
               perror("read");
               exit(2);
			}
			
			printf("In parent. Square recieved from child = %s \n", buffer);
		}
		/** 
        call waitpid(pid, *status, options) 
        to suspend the parent process till the child 
        process has changed its state. Examples are 
        1. child process termination
        2. child process stopped by a signal 
        3. child process resumed by a signal
        */
        waitpid(-1, &status, 0);
	}
	return 0;	
}