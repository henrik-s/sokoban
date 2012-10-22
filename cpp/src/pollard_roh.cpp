#include <gmp.h>
#include <gmpxx.h>
#include <iostream>
#include <math.h>
#include <queue>
#include <vector>
#include <time.h>
#include "primes.h"
using namespace std;
void f(mpz_t x, mpz_t N);
void factorize(mpz_t N);
#define db(i) cout<<"Debug: "<<i <<endl;

#define print(N) mpz_out_str(stdout, 10,N); printf("\n");

time_t start;

void pollard_roh(mpz_t  N){

	mpz_t x,y,d, abs;
	mpz_init_set_str (x, "2", 10);		
	mpz_init_set_str (y, "2", 10);
	mpz_init_set_str(d,"1",10); 
	mpz_init (abs);
	while(!mpz_cmp_ui(d,1)){
		f(x, N);
		f(y, N); f(y, N); 
		mpz_sub(abs, x, y);
		mpz_abs(abs, abs);
		mpz_gcd(d, abs, N);
	}
	if(!mpz_cmp(d,N)) {
		cout << "fail" << endl;
		return;
	}
	mpz_clear(x); mpz_clear(y);				
	mpz_cdiv_q(abs, N, d);
	mpz_clear(N);
	//pollard_roh(abs); // Pollard rho for the rest: N/d
	//pollard_roh(d);	// PRoh for a divisor d
	factorize(abs);
	factorize(d);
}

void f(mpz_t x, mpz_t N) {
	mpz_pow_ui(x, x, 2);
	mpz_add_ui(x,x,2);
	mpz_mod(x,x,N);
}

void perfect_squares(mpz_t N) {
	if(mpz_probab_prime_p(N,5)){	
		mpz_out_str(stdout, 10,N); printf("\n");
		mpz_clear(N);
		return;
	}	
	mpz_t x, tmp;
	mpz_init(x); mpz_init_set(tmp, N);
	if(mpz_root(x, N, 2)) { 	// x = sqrt(N), no rest?
		mpz_set(N, x); 		// N = sqrt(N)
		factorize(N);
		factorize(x);
		mpz_clear(tmp);
		return;
	}
	else { // N is not a perfect, let's try find x^2-y^2 = N
		mpz_t x2, y2;
		mpz_init(x2); mpz_init(y2);
		for(int i = 0; i<5; i++) {	
			mpz_add_ui(x,x,1); 		// x = x + 1 
			mpz_pow_ui(x2,x,2); 		// x^2 = x^2
			mpz_sub(y2, x2, N);		// y^2 = x^2 - N
			if(mpz_perfect_square_p(y2)) {
				mpz_sqrt(y2,y2); 	// y2 = sqrt(y^2)
				mpz_add(N, x, y2); 	// N = (x+y)
				mpz_sub(x, x, y2); 	// x = (x-y)
				
				mpz_clear(x2); mpz_clear(y2); mpz_clear(tmp);
				factorize(N);
				factorize(x);
				return;
			}
		}		
	}
	mpz_clear(x);
	mpz_clear(N);
	pollard_roh(tmp);
}


void factorize(mpz_t N){
	if(mpz_probab_prime_p(N,5)){	
		mpz_out_str(stdout, 10,N); printf("\n");
		mpz_clear(N);
		return;
	}
	mpz_t tmp;
	mpz_t curr;

	mpz_init(tmp);
	mpz_init_set(curr,N);
	for(int i = 0; i < NUM_PRIMES; i++){
		if(mpz_cdiv_q_ui(tmp,curr,primes[i]) == 0){
			cout << primes[i] << endl;
			if(!mpz_cmp_ui(tmp,1)){
				mpz_clear(tmp);
				mpz_clear(N);
				return;
			}
			mpz_set(curr,tmp);
			i= -1;			
		}		
	}
	mpz_clear(tmp);
	mpz_clear(N);
	perfect_squares(curr);
}


void resign(int i) {
	for(int j = i; j < 100; j++) {
		cout<< "fail" << endl << endl;
	}
}	

int timeIsUp() {
	if (time (NULL) - start > 13)
		return 1;
	return 0;
}

int main(){
	start = time (NULL);
	for(int i = 0; i < 100;i++){
		if( time (NULL) - start > 7) {
			resign(i);
			return 0;
		}	
		mpz_t N;
		mpz_init (N);
		gmp_scanf("%Zd",N);
		factorize(N);
		cout << endl;	
	}
	return 0;
}
