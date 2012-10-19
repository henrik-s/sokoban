#include <gmp.h>
#include <gmpxx.h>
#include <iostream>
#include "gcd.h"
#include <math.h>
#include <queue>
using namespace std;

void pollard_roh(mpz_t & a, mpz_t & N){
	mpz_init_set(a,N);
}
	
int main(int argc, char **argv){
	mpz_t N,curr;
	gmp_scanf("%Zd",N);
	mpz_init_set(curr,N);

	
	mpz_init_set_str(N,"9999",10);
	mpz_t a;
	pollard_roh(a,N); 
	mpz_out_str(stdout, 10,a);
	return 0;
}
