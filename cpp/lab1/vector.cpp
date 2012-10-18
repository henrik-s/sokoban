#include "vector.h"
	//Default
	Vector::Vector(unsigned int size_t){
		vector = new unsigned int[size_t];
		size = size_t;
		for(unsigned int i = 0; i < size; i++){
			vector[i] = 0;
		}
	}
	//Move constructor
	Vector::Vector(Vector && rRef){
		vector = rRef.vector;
		size = rRef.size;
		rRef.vector = 0;
		rRef.size = 0;
	}

	Vector::Vector(std::initializer_list<int> il){
		size = il.size();
		vector = new unsigned int[size];
		int i = 0;
		for (const int *beg = il.begin(); beg != il.end(); ++beg){
			vector[i] = *beg;
			i++;								                }
	}
	//Destruktor
	Vector::~Vector(){
		delete [] vector;
	}
	//Copy
	Vector::Vector(const Vector & orig){
		vector = new unsigned int[orig.size];
		size = orig.size;
		for(unsigned int i = 0; i < size; i++){
			vector[i] = orig.vector[i];
		}
	}
	//Tilldelning med initializer_list
	Vector & Vector::operator=(std::initializer_list<int> orig){
		delete [] vector;
		size = orig.size();
		vector = new unsigned int[size];
		int i = 0;
		for (const int *beg = orig.begin(); beg != orig.end(); ++beg){
			vector[i] = *beg;
			i++;
		}
		
	return *this;
	}
	

	//Tilldelning
	Vector & Vector::operator=(const Vector & orig){
		if(this != &orig){
			delete [] vector;
			vector = new unsigned int[orig.size];
			size = orig.size;
			for(unsigned int i=0; i < size; i++){
				vector[i] = orig.vector[i];
			}
		}
		return *this;
	}

	//Move operator
	Vector & Vector::operator=(Vector &&rhs){
		if(this != &rhs){
			delete [] vector;
			vector = rhs.vector;
			size = rhs.size;
			rhs.vector = 0;
			rhs.size = 0;
		}
	return *this;
	}
	
	unsigned int & Vector::operator[](unsigned int i) const{
		if(i>=size || i < 0){
			throw std::out_of_range("Out of range");
		}
		return vector[i];
	}


