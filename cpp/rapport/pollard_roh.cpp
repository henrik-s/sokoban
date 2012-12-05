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
void factorize(mpz_t N, int number);
void perfect_squares(mpz_t N, int number);
vector<mpz_class> output_mpz;
vector<int> output_int;
int fail;
static int LOOPS =  380000;

void addFactors(mpz_t N, int i) {
	for (int j = 0; j<i; j++) {
			output_mpz.push_back(mpz_class(N));
	}
}
void addIntFactors(int factor, int number) {
	for(int i = 0; i<number;i++) {
			output_int.push_back(factor);
	}
}

void pollard_roh(mpz_t  N, int number){
	if(mpz_probab_prime_p(N,10)){
		addFactors(N, number);
		mpz_clear(N);
		return;
	}
	mpz_t x,y,d, abs, sum;
	mpz_init_set_ui (x, 7);		
	mpz_init_set_ui (y, 7);
	mpz_init_set_ui(d, 1); 
	mpz_init (abs);	
	mpz_init_set_ui(sum,1);
	int counter = 0; int i = 0;
	while(!mpz_cmp_ui(d,1) && counter < LOOPS ){
		f(x, N);
		f(y, N); f(y, N); 
		mpz_sub(abs, x, y);
		mpz_abs(abs, abs);
		mpz_mul(sum, sum, abs);
		mpz_mod(sum, sum, N);
		i++;
		if(i == 100) {
			mpz_gcd(d, sum, N);
			mpz_set_ui(sum, 1);
			i = 0;
		}
		counter++;
	}
	mpz_clear(x); mpz_clear(y);				
	if(!mpz_cmp(d,N) || counter == LOOPS) {
		 mpz_clear(d); mpz_clear(abs);
		cout << "fail" << endl;
		mpz_clear(N);
		fail = 1;
		return;
	}
	mpz_cdiv_q(abs, N, d);
	mpz_clear(N);
	perfect_squares(abs, number); perfect_squares(d, number);
}

void f(mpz_t x, mpz_t N) {
	mpz_pow_ui(x, x, 2);
	mpz_add_ui(x,x,1);
	mpz_mod(x,x,N);
}
void perfect_squares(mpz_t N, int number) {
	if(mpz_probab_prime_p(N,10)){	
		addFactors(N, number);
		mpz_clear(N);
		return;
	}
	if(mpz_perfect_power_p(N)) {
		mpz_t root; mpz_init(root);
		int	found = 0; int i = 2;
		while(!found) {
			if(mpz_root(root, N, i)) {
				found = 1;
				mpz_clear(N);
				perfect_squares(root, number*i);
			}
			i++;
		}
	}
	else {
		pollard_roh(N, number);
	}
}

void factorize(mpz_t N, int number){
	if(mpz_probab_prime_p(N,10)){	
		addFactors(N, number);
		mpz_clear(N);
		return;
	}
	mpz_t tmp; mpz_init(tmp);
	mpz_t curr;	mpz_init_set(curr,N);
	for(int i = 0; i < NUM_PRIMES; i++){
		if(mpz_cdiv_q_ui(tmp,curr,primes[i]) == 0){
			addIntFactors(primes[i], number);
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
	perfect_squares(curr, number);
}
void print_output() {
	for(unsigned int i = 0; i<output_mpz.size(); i++) {
		cout<<output_mpz[i]<<endl;
	}
	for(unsigned int j = 0; j<output_int.size(); j++) {
		cout<<output_int[j]<<endl;
	}
}	
int main(){
	for(int i = 0; i < 100;i++){
		mpz_t N; mpz_init (N);
		gmp_scanf("%Zd",N);
		fail = 0;
		output_mpz.clear();
		output_int.clear();
		factorize(N, 1);
		if(!fail) {
			print_output();
		}
		cout << endl;	
	}
	return 0;
}
