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
	employee -> last_name,
	employee -> salary,
	employee -> dept_info -> dept_name
	); 
}

int main(int argc, char* argv[]) {
	struct employee *employee0;
	struct dept *dept0;
	
	struct employee *employee1;
	struct dept *dept1;
	
	employee0 = malloc(sizeof(struct employee));
	
	if(employee0 == NULL) {
		fprintf(stderr, "malloc: unable to allocate memory to employee0\n");
		return 1;
	}
	strcpy(employee0 -> last_name, "Adams");
	employee0 -> salary = 1234.56;
	
	dept0 = malloc(sizeof(struct dept));
	
	if(dept0 == NULL) {
		fprintf(stderr, "malloc: unable to allocate memory to dept0\n");
		return 1;
	}
	
	strcpy(dept0 -> dept_name, "HR");
	employee0 -> dept_info = dept0;
	print_employee_info(employee0);
	
	employee1 = malloc(sizeof(struct employee));
	
	if(employee1 == NULL) {
		fprintf(stderr, "malloc: unable to allocate memory to employee1\n");
		return 1;
	}
	strcpy(employee1 -> last_name, "Bryant");
	employee1 -> salary = 0.0;
	
	dept1 = malloc(sizeof(struct dept));
	
	if(dept1 == NULL) {
		fprintf(stderr, "malloc: unable to allocate memory to dept1\n");
		return 1;
	}
	
	strcpy(dept1 -> dept_name, "Accounting");
	employee1 -> dept_info = dept1;
	print_employee_info(employee1);
	
	return 0;
}
	