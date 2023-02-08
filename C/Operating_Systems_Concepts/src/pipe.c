#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/wait.h>

#define BUF_SIZE 16

int main(void) {
    pid_t child_pid; 
    int status;
    /** fildes[0] is the read end. fildes[1] is the write end. */
    int fildes[2];
    char buffer[BUF_SIZE];
	
    if(!(pipe(fildes)==0)) {
        perror("pipe");
        exit(1);
    }       
    child_pid=fork(); 
	
    if(child_pid==0) {
        /** close the write end */
        close(fildes[1]);
        if(read(fildes[0], buffer, BUF_SIZE-1)==-1) {
            perror("read");
            exit(2);
        }
        printf("child process. My PID= %s\n", buffer);
    } else {
        /** close the read end */
        close(fildes[0]);
        printf("parent process. Child PID= %d\n", (int)child_pid);
        sprintf(buffer, "%d", (int)child_pid);
        if(write(fildes[1], buffer, strlen(buffer)+1)==-1) {
            perror("write");
            exit(3);
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