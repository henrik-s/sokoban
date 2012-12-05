#include <stdio.h>
#include <iostream>
#include <stdlib.h>
#include <initializer_list>
#include <utility>
#include <stdexcept>

struct Vector{
public:
	unsigned int *vector;
	unsigned int size;
	
	explicit Vector();
	//Med storlek
	explicit Vector(unsigned int size_t);
	//Move
	Vector(Vector && rRef);
	//Initialize with list
	Vector(std::initializer_list<int> il);
	//desturctor
	~Vector();
	//Copy
	Vector(const Vector & orig);
	//Tilldelning med list
	Vector & operator=(std::initializer_list<int> orig);
	//Tilldelning
	Vector & operator=(const Vector & orig);
	//Move operator
	Vector & operator=(Vector &&rhs);
	//Index tilldelning
	unsigned int & operator[](unsigned int i) const;
};

