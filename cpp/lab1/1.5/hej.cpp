#include <iostream>
#include <algorithm>
template<typename T>
void getMax(T a,T b){
	T result = (a>b ? a:b);
	std::cout << result << std::endl;
}

int main(void){
	int *t = new int[5];
	t[1] = 1;
	t[2] = 2;
	t[3] = 0;
	t[4] = 9;
	t[0] = 7;

	std::sort(t.begin(), t.end());
	std::cout << t << std::endl;
	
	getMax("v","b");
	getMax("a","c");
	getMax(1,2);
	getMax(4,1);
	return 0;


}

