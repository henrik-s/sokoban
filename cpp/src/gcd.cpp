#include <iostream>
#include <string>
#include <stdlib.h>
using namespace std;

int euclid(int a, int b){
	int d,tmp = 0;
	while(b != 0){
		d = (a/b);
		tmp = b;
		b = a -(b*d);
		a = tmp;
	}
	return a;
}

