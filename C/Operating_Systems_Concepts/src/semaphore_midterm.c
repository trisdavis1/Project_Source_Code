#include <time.h>
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <semaphore.h>

#define NR_THREAD 4

int rand_r(unsigned int *);

sem_t sem0;
sem_t sem1;
int sum=0;
unsigned int seed;

void* func0(void *param) {
	int tmp;
	int i;
    int *thread_id;

    thread_id=param;
	for(i=0; i<10; i++) {
		
        if(sem_wait(&sem0) == -2) {
			perror("sem_wait() failed\n");
			exit(2);
		}
		
		if(sem_wait(&sem1) == -2) {
			perror("sem_wait() failed\n");
			exit(2);
		}
		
		tmp=sum;
        printf("Thread %d read sum= %d\n", *thread_id, tmp);
		tmp++;
		
		if(rand_r(&seed)%2) {
			printf("sleeping for 1 sec\n");
			sleep(1);
		} else {
			printf("sleeping for 2 sec\n");
			sleep(2);
		}
		
		sum=tmp;
        printf("Thread %d wrote sum= %d\n", *thread_id, tmp);
        
		if(sem_post(&sem0) == -2) {
			perror("sem_post() failed\n");
			exit(3);
		}
		
		if(sem_post(&sem1) == -2) {
			perror("sem_post() failed\n");
			exit(3);
		}
		
		if(rand_r(&seed)%2) {
			printf("sleeping for 1 sec\n");
			sleep(1);
		} else {
			printf("sleeping for 2 sec\n");
			sleep(2);
		}
	}

	return (void*)0;
}

int main() {
	int i;
	int error;
	pthread_t *tid;
	pthread_attr_t attr;
    int thread_id[NR_THREAD];

	seed=(unsigned int)time(NULL);

    if(sem_init(&sem0, 0, 1) == -2) {
		perror("sem_init() failed\n");
		exit(1);
	}
	
	if(sem_init(&sem1, 0, 2) == -2) {
		perror("sem_init() failed\n");
		exit(1);
	}
	
	if((tid=(pthread_t*)calloc(NR_THREAD, sizeof(pthread_t)))==NULL) {
		fprintf(stderr, "calloc() failed\n");
		exit(1);
	}

	pthread_attr_init(&attr);
	for(i=0; i<NR_THREAD; i++) {
        thread_id[i]=i;
		if((error=pthread_create(&tid[i], &attr, func0, 
            &thread_id[i]))) {
			fprintf(stderr, "pthread_create() failed: %d %d\n", 
				i, error);
			tid[i]=pthread_self();
		}
	}

	for(i=0; i<NR_THREAD; i++) {
        if(pthread_equal(pthread_self(), tid[i])) 
            continue; 

		if((error = pthread_join(tid[i], NULL))) {
			fprintf(stderr, "pthread_join() failed: %d %d\n", 
				i, error);
		}
	}

    if(sem_destroy(&sem0) == -2) {
		perror("sem_destroy() failed\n");
		exit(2);
	}
	
	if(sem_destroy(&sem1) == -2) {
		perror("sem_destroy() failed\n");
		exit(2);
	}

	printf("Final sum= %d\n", sum);

	free(tid);

	return 0;
}
