#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]){
	int i;
	int reps;
	if(argc ==1)
		printf("Hello World!\n");
	else if(argc == 2){
		reps = atoi(argv[1]);
		if(reps){
			printf("Hello");
			for(i=0;i<reps;i++)
				printf(" world");
			printf("!\n");
		}
		else{
			printf("Hello %s!\n",argv[1]);
		}
	}
	else if(argc == 3){
		reps = atoi(argv[1]);
		printf("Hello");
		for(i=0;i<reps;i++){
			printf(" %s",argv[2]);
		}
		printf("!\n");
	}
	return 0;
}
