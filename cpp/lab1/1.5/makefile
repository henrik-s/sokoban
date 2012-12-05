%.out:	%.cpp
	g++ -g -std=c++0x -Wall $*.cpp -o $*.out -I /info/cprog12/cxxtest/

test:
	g++ -c must_follow_a.cpp
	/info/cprog12/cxxtest/cxxtestgen.py --error-printer -o 1.2b.cpp test_must_follow_a.cpp
	g++ -o test_1.2b.out -I /info/cprog12/cxxtest/ 1.2b.cpp must_follow_a.o

testv:
	@/info/cprog12/cxxtest/cxxtestgen.py --error-printer -o 1.4.cpp test_vec.cpp 
	@g++ -std=c++0x -o Test_1.4.out -I /info/cprog12/cxxtest/ 1.4.cpp
	valgrind --tool=memcheck --leak-check=yes ./Test_1.4.out
clean:
	rm -rf *.out *.o
