#include <gmp.h>
#include <gmpxx.h>
#include <iostream>
#include "gcd.h"
#include <math.h>
#include <queue>
#include <vector>
using namespace std;
void f(mpz_t x, mpz_t N);
#define db(i) cout<<"Debug: "<<i <<endl;

#define print(N) mpz_out_str(stdout, 10,N); printf("\n");

static int NUM_PRIMES = 168;
int primes[] = {2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997};

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

void trial_division(mpz_t N){
	
	pollard_roh(N);
}

void perfect_squares(mpz_t N) {
	mpz_t x;
	mpz_t init(x);
	if(mpz_sqrt(x, N)) { // If x is perfect sqrt(N)
		mpz_set(x, N);
		trial_division(x);
		trial_division(N);
		return;
	}
	else {
		mpz_t x2, y2;
		mpz_t init(x2); mpz_t init(y2);
		int i;
		for(i = 0; i<5; i++) {	
			mpz_add_ui(x,x,1); 
			mpz_pow_ui(x2,x,2);
			mpz_sub(y2, x2, N);
			if(mpz_perfect_square_p(y2)) {
				mpz_sqrt(y2,y2); // y2 = y
				mpz_add(N, x, y2);
				mpz_sub(x, x, y2);
				mpz_clear(x2); mpz_clear(y2);
				factorize(N);
				factorize(x);	
			}
		}		
	}
}

int main(int argc, char **argv){
	mpz_t N;
	mpz_init (N);
	int i;
	for(i = 0; i<100; i++) {
		gmp_scanf("%Zd",N);
		trial_division(N);	
	}
	return 0;
}
