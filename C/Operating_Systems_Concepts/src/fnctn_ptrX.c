/**
To compile: 
gcc -ansi -pedantic -Wall -o ./fnctn_ptr.exe ./fnctn_ptr.c

To run:
./fnctn_ptr.exe

Demo:
$ ./fnctn_ptr.exe                  
proxy: last_name= Adams
*/

#include<stdio.h> 
#include<stdlib.h> 
#include<string.h> 

#define BUF_SIZE 1024

struct employee {
    char last_name[BUF_SIZE];
    double salary;
    struct dept *dept_info;
};

struct dept {
    char dept_name[BUF_SIZE];
};

void print_employee_info(struct employee *employee) {
    printf("Last name: %s, salary: %f, Dept: %s\n", 
        employee->last_name, 
        employee->salary, 
        employee->dept_info->dept_name
        );
}

void proxy(
	char* (*fnctn)(struct employee*), 
	struct employee* employee1) {
	printf("proxy: last_name= %s\n", fnctn(employee1));
}

char* compare(struct employee* employee1) {
	return employee1->last_name;
}

int main(int argc, char* argv[]) {
    struct employee *employee0;
    struct dept     *dept0;
    /**
    // declare employee1 and his/her dept here 
    */
    /**
    FILL_HERE
    */

    employee0=malloc(sizeof(struct employee));
    if(employee0==NULL) {
        fprintf(stderr, "malloc: unable to allocate memory to employee0\n");
        return 1;
    }
    strcpy(employee0->last_name, "Adams");
    employee0->salary = 1234.56;

    dept0=malloc(sizeof(struct dept));
    if(dept0==NULL) {
        fprintf(stderr, "malloc: unable to allocate memory to dept0\n");
        return 1;
    }
    strcpy(dept0->dept_name, "HR");

    employee0->dept_info = dept0;


    /**
    // allocate memory for employee1 and dept1 here. 
    */
    /**
    FILL_HERE
    */

    proxy(compare, employee0);
    
    return 0;
}
