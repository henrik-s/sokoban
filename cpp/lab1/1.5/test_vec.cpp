#include <cxxtest/TestSuite.h>
#include "vector.h"
using namespace std;
class MyTestSuite : public CxxTest::TestSuite
{
public:

	void test_assign_vector(void){
		Vector<int> a(5);
		Vector<int> b(5);
		Vector<int> c(1);
		Vector<int> d(10);
		a[1] = 1;
		a[2] = 2;
		a = a;
		b = a;
		c = b;
		d = c;
	
		TS_ASSERT_EQUALS(b[1],1);
		TS_ASSERT_EQUALS(d[2],2);
	}

	void test_assign_vector_string(void){
		Vector<string> a(5);
		Vector<string> b(3);
		Vector<string> c;
		a[1] = "a";
		a[2] = "b";
		a = a;
		b = a;
		c = b;

		TS_ASSERT_EQUALS(b[1],"a");
		TS_ASSERT_EQUALS(c[2],"b");
	}
	void test_copy_string_vector(void){
		Vector<string> a(5);
		a[0] = "tja";
		a[3] = "tjabba";
		Vector<string> b = a;
		Vector<string> c(b);
		TS_ASSERT_EQUALS(b[0],"tja");
		TS_ASSERT_EQUALS(c[3],"tjabba");
	}
	void test_copy_double_vector(void){
		Vector<double> a(5);
		a[0] = 1.5;
		a[1] = 5.7;
		Vector<double> b = a;
		Vector<double> c(a);
		TS_ASSERT_EQUALS(b[0], 1.5);
		TS_ASSERT_EQUALS(c[1], 5.7);
	}
	void test_double_rand(void){
		Vector<double> a(5);
		TS_ASSERT_THROWS(a[5],std::out_of_range);
		TS_ASSERT_THROWS(a[5] = 1,std::out_of_range);
		TS_ASSERT_THROWS(a[-1],std::out_of_range);
	}

	void test_initialize_string_with_scalar(void){
		Vector<string> a(5);
		a = {"1","2","3"};
		Vector<string> b = {"a","b","c"};
		TS_ASSERT_EQUALS(a[1],"2");
		TS_ASSERT_EQUALS(b[2],"c");
		TS_ASSERT_THROWS(a[3],std::out_of_range);
	}

	void test_index_string_insertion(void){
		Vector<string> a(5);
		string x = "hej";
		Vector<string> b = {"a","b","c"};
		a[1] = x;
		string y = b[1];
		TS_ASSERT_EQUALS(y,"b");
		TS_ASSERT_EQUALS(a[1],"hej");
	}
	
	void test_pushbacki_int(void){
		Vector<int> a(5,1);
		a.push_back(2);
		for(int i = 0; i<10;i++)
			a.push_back(i);
		TS_ASSERT_EQUALS(a[5],2);
		TS_ASSERT_EQUALS(a[15],9)
		TS_ASSERT_EQUALS(a.cap, 20);
		TS_ASSERT_EQUALS(a.size(), 16);
	}

	void test_pushback_string(void){

		Vector<string> a(5,"a");
		a.push_back("b");
		for(int i = 0; i<10;i++)
			a.push_back("y");
		TS_ASSERT_EQUALS(a[5],"b");
		TS_ASSERT_EQUALS(a[15],"y")
		TS_ASSERT_EQUALS(a.cap, 20);

	}

	void test_insert(void){
		Vector<int> a(5,1);
		a.insert(2,3);
		a.insert(6,6);
		TS_ASSERT_EQUALS(a[2],3);
		TS_ASSERT_EQUALS(a.size(),7);
		TS_ASSERT_EQUALS(a[6],6);
		TS_ASSERT_EQUALS(a[5],1);
		TS_ASSERT_THROWS(a[7],std::out_of_range);
		TS_ASSERT_THROWS(a.insert(8,2),std::out_of_range);
		TS_ASSERT_THROWS(a.insert(-1,2),std::out_of_range);
		a.insert(2,4);
		TS_ASSERT_EQUALS(a[2],4);
		TS_ASSERT_EQUALS(a[3],3);
		TS_ASSERT_EQUALS(a[7],6);
	}
	
	void test_insert_rand(void){
		Vector<long> a(20,0);
		a.insert(20,2);
		a.insert(0,1);
		TS_ASSERT_EQUALS(a[0],1);
		TS_ASSERT_EQUALS(a[21],2);

	}
	void test_erase(void){
		Vector<string> a;
		a = {"a","b","c"};
		a.erase(1);
		TS_ASSERT_EQUALS(a[1],"c");
		TS_ASSERT_EQUALS(a.size(),2);
		TS_ASSERT_THROWS(a[2],std::out_of_range);
		TS_ASSERT_THROWS(a.erase(-1),std::out_of_range);
		TS_ASSERT_THROWS(a.erase(2),std::out_of_range);
	}

	void test_clear(){
		Vector<double> a = {1.2,1.4,1.5};
		a.clear();
		TS_ASSERT_EQUALS(a.size(),0);
		TS_ASSERT_THROWS(a[0],std::out_of_range);
		TS_ASSERT_THROWS(a[1],std::out_of_range);
	}
	
	void test_size(void){
		Vector<int> a;
		for(int i = 0;i<10;i++){
			a.push_back(2);
			TS_ASSERT_EQUALS(a.size(),i+1);
		}
	}
	
	void test_sort_int(void){
		Vector<int> a = {1,5,3,4,2};
		a.sort(true);
		TS_ASSERT_EQUALS(a.size(),5);
		TS_ASSERT_EQUALS(a[0],1);
		TS_ASSERT_EQUALS(a[1],2);
		TS_ASSERT_EQUALS(a[4],5);
		a.sort(false);
		TS_ASSERT_EQUALS(a[0],5);
		TS_ASSERT_EQUALS(a[4],1);
		TS_ASSERT_EQUALS(a[1],4);
	}

	void test_sort_string(void){
		Vector<string> a = {"z","b","a"};
		a.sort();
		TS_ASSERT_EQUALS(a[1],"b");
		TS_ASSERT_EQUALS(a[2],"z");
		a.sort(true);
		a.sort(false);
		TS_ASSERT_EQUALS(a[0],"z");

	}	
	void test_exists(void){
		Vector<string> a = {"a", "b", "c", "hej"};
		TS_ASSERT_EQUALS(a.exists("a"),true);
		TS_ASSERT_EQUALS(a.exists("b"),true);
		TS_ASSERT_EQUALS(a.exists("c"),true);
		TS_ASSERT_EQUALS(a.exists("hej"),true);
		TS_ASSERT_EQUALS(a.exists("ab"),false);
		TS_ASSERT_EQUALS(a.exists("2"),false);
		TS_ASSERT_EQUALS(a.exists("hejl"),false);
	}

	void test_pointerVector(void){

		Vector<char *> a(5);
		char * b = new char[2];
		a[1] = b;
		TS_ASSERT_EQUALS(a[1],b);
		delete [] b;
	}
};
