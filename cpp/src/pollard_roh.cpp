#include <iostream>
#include "gcd.h"
#include <math.h>
#include <stdlib.h>
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
		int a = atoi(argv[1]);
	}

	return a;
}

int main(int argc, char **argv){
	int arg = parseArgv(argc, argv);
	cout << "Här kommer a: " << arg << endl;
	return 0;
}
