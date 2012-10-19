#include <gmp.h>
#include <gmpxx.h>
#include <iostream>
#include "gcd.h"
#include <math.h>
<<<<<<< HEAD
#include <stdlib.h>
=======

>>>>>>> c7b3619653a16208ac9f826d86c423812fc328a3
using namespace std;




int parseArgv(int argc, char ** argv){
	int a = 0;
	
	if(argc == 1){
		cout << "Enter number to be factorized: ";
		cin >> a;
		cout << endl;
	}else if(argc > 2){
		cout << "Input must be exactly one integer" << endl;
		return -1;
	}else{
<<<<<<< HEAD
		int a = atoi(argv[1]);
	}
=======
	}
	return 1;
}
>>>>>>> c7b3619653a16208ac9f826d86c423812fc328a3

	return a;
}

<<<<<<< HEAD
int main(int argc, char **argv){
	int arg = parseArgv(argc, argv);
	cout << "Här kommer a: " << arg << endl;
=======
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
>>>>>>> c7b3619653a16208ac9f826d86c423812fc328a3
	return 0;
}
