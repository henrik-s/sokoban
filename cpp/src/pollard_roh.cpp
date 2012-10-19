#include <gmp.h>
#include <gmpxx.h>
#include <iostream>
#include "gcd.h"
#include <math.h>
#include <queue>
using namespace std;
void f(mpz_t x, mpz_t N);
#define db(i) cout<<"Debug: "<<i <<endl;

#define print(N) mpz_out_str(stdout, 10,N); printf("\n");



void pollard_roh(mpz_t  N){
	if(mpz_probab_prime_p(N,5)){	
		mpz_out_str(stdout, 10,N); printf("\n");
		mpz_clear(N);
		return;
	}
	mpz_t x,y,d, abs;
	mpz_init_set_str (x, "2", 10);		
	mpz_init_set_str (y, "2", 10);
	mpz_init_set_str(d,"1",10); 
	mpz_init (abs);
	
	while(!mpz_cmp_ui(d,1)){
		f(x, N); printf("x: "); print(x);
		f(y, N); f(y, N); printf("y: "); print(y);
		mpz_sub(abs, x, y);
		mpz_abs(abs, abs); printf("abs: "); print(abs);
		mpz_gcd(d, abs, N); printf("d: "); print(d);
	}
	if(!mpz_cmp(d,N)) {
		cout << "Fail" << endl;
		return;
	}
	mpz_clear(x); mpz_clear(y);				
	mpz_cdiv_q(abs, N, d);
	mpz_clear(N);
	pollard_roh(abs); // Pollard rho for the rest: N/d
	pollard_roh(d);	// PRoh for a divisor d
}

void f(mpz_t x, mpz_t N) {
	mpz_pow_ui(x, x, 2);
	mpz_add_ui(x,x,2);
	mpz_mod(x,x,N);
}

int main(int argc, char **argv){
	mpz_t N;
	mpz_init (N);
	gmp_scanf("%Zd",N);
	pollard_roh(N); 

	return 0;
}
