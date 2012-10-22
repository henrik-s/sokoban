#include <stdio.h>
#include <iostream>
using namespace std;


int main(){
	FILE *fp,*fw;
	fp=fopen("10000primes","r");
	fw=fopen("primes.h","w");
	int prime;
	int numPrimes = 10000;

	fprintf(fw,"static int NUM_PRIMES = %d;\n", numPrimes);
	fprintf(fw,"int primes[] = {");
	fscanf(fp,"%d",&prime);
	fprintf(fw,"%d",prime);
	for(int i = 0; i < numPrimes-1;i++){
		fscanf(fp,"%d",&prime);
		fprintf(fw,",%d",prime);
	}
	fprintf(fw,"};");
	return 0;
}
