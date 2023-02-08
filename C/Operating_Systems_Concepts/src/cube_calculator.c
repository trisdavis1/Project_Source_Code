#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/wait.h>

#define BUF_SIZE 16

int main(void) {
	int num;
	int num2;
	pid_t child_pid;
	pid_t grandchild_pid;
	int status;
	/** fildes[0] is read end and fildes[1] is write end for both fildes0 & fildes1 */
	int fildes0[2];
	int fildes1[2];
	int fildes2[2];
	int fildes3[2];
	char buffer[BUF_SIZE];
	
	if(!(pipe(fildes0) == 0)) {
		perror("pipe");
		exit(1);
	}
	if(!(pipe(fildes1) == 0)) {
		perror("pipe");
		exit(1);
	}
	if(!(pipe(fildes2) == 0)) {
		perror("pipe");
		exit(1);
	}
	if(!(pipe(fildes3) == 0)) {
		perror("pipe");
		exit(1);
	}
	
	grandchild_pid = fork();
	
	if(grandchild_pid == 0) {
		/** close the write end */
		close(fildes2[1]);
		close(fildes3[0]);
		
		while(num >= 0){
			if(read(fildes2[0], buffer, BUF_SIZE - 1) == -1) {
				perror("read");
				exit(2);
			}
			
			num = atoi(buffer);
			printf("In grandchild. Generating the square of %d recieved from child...\n", num);
			printf("Sending back to child!\n");
			sprintf(buffer, "%d\n", num * num);
			
			if(write(fildes3[1], buffer, strlen(buffer) + 1) == -1){
				perror("write");
				exit(3);
			}
		}
		
	}
	
	child_pid = fork();
	
	if(child_pid == 0) {
		/** close the write end */
		close(fildes0[1]);
		close(fildes1[0]);
		close(fildes2[0]);
		close(fildes3[1]);
		
		while(num2 >= 0){
			if(read(fildes0[0], buffer, BUF_SIZE - 1) == -1) {
				perror("read");
				exit(2);
			}
			
			num2 = atoi(buffer);
			printf("In child. Integer recieved from parent = %d\n", num2);
			printf("Sending to grandchild!\n");
			sprintf(buffer, "%d\n", num2);
			
			if(write(fildes2[1], buffer, strlen(buffer) + 1) == -1){
				perror("write");
				exit(3);
			}
			if(read(fildes3[0], buffer, BUF_SIZE - 1) == -1){
				perror("read");
				exit(2);
			}
			
			num = atoi(buffer);
			printf("In child. Generating the cube from %d recieved from grandchild...\n", num);
			printf("Sending back to parent!\n");
			sprintf(buffer, "%d\n", num * num2);
			
			if(write(fildes1[1], buffer, strlen(buffer) + 1) == -1) {
				perror("write");
				exit(3);
			}
		}
		
	}else {
		/** close the read end */
		close(fildes0[0]);
		close(fildes1[1]);
		
		while(num >= 0){
			printf("In parent. Enter an integer to cube or enter a neg value to quit: ");
			
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
			
			printf("In parent. Cube recieved from child = %s\n", buffer);
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