#include <gmp.h>
#include <gmpxx.h>
#include <iostream>
#include "gcd.h"
#include <math.h>
#include <stdlib.h>

using namespace std;

void gmpExample() {
	mpz_t r, n;
	mpz_init (r);
	mpz_init_set_str (n, "123456", 10);
	mpz_out_str (stdout, 10, n); printf("\n");
	
	gmp_scanf("%Zd", n);
	printf("\nThe number you entered = ");
	mpz_out_str (stdout, 10, n); printf("\n\nx2 = ");
	
	mpz_mul_ui(r, n, 2);
	mpz_out_str (stdout, 10, r); printf("\n");

}	
int main(int argc, char **argv){
	gmpExample();	
	return 0;
}
