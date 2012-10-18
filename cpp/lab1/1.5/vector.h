#include <algorithm>
#include <stdio.h>
#include <iostream>
#include <stdlib.h>
#include <initializer_list>
#include <utility>
#include <stdexcept>
#include <vector>

template<typename T>  
struct Vector{

public:
	T *vector;
	unsigned int Size;
	unsigned int cap;
	//Default för 2 args
	Vector(unsigned int size_t, T defVal){
		if(size_t > 0)
			cap = size_t*2;
		else 
			cap = 5;
		vector = new T[cap];
		Size = size_t;
		for(unsigned int i = 0; i < Size; i++){
			vector[i] = defVal;
		}
	}
	//Default
   	Vector(){	
		vector = new T[5];
		Size = 0;
		cap = 5;
		for(unsigned int i = 0; i < Size; i++){
			vector[i] = T();
		}
	}


	//Default
	Vector(unsigned int size_t){
		if(size_t > 0)
			cap = size_t*2;
		else 
			cap = 5;
		vector = new T[cap];
		Size = size_t;

		for(unsigned int i = 0; i < Size; i++){
			vector[i] = T();
		}
	}
	//Move constructor
	Vector(Vector<T> && rRef){
		vector = rRef.vector;
		Size = rRef.Size;
		rRef.vector = 0;
		rRef.Size = 0;
	}
 	//Constructor with scalar list
	Vector(std::initializer_list<T> il){
		Size = il.size();
		cap = Size*2;
		vector = new T[cap];
		int i = 0;
		for (const T *beg = il.begin(); beg != il.end(); ++beg){
			vector[i] = *beg;
			i++;							                
		}
	}
	//Destruktor
	~Vector(){
		delete [] vector;
	}
	//Copy
	Vector(const Vector<T> & orig){
		
		Size = orig.Size;
		cap = Size*2;
		vector = new T[cap];
		for(unsigned int i = 0; i < Size; i++){
			vector[i] = orig.vector[i];
		}
	}
	//Tilldelning med initializer_list

	Vector<T> & operator=(std::initializer_list<T> orig){
		delete [] vector;
		Size = orig.size();
		cap = Size*2;
		vector = new T[cap];
		int i = 0;
		for (const T *beg = orig.begin(); beg != orig.end(); ++beg){
			vector[i] = *beg;
			i++;
		}
		
	return *this;
	}
	

	//Tilldelning
	Vector<T> & operator=(const Vector<T> & orig){
		if(this != &orig){
			delete [] vector;
			Size = orig.Size;
			cap = Size*2;
			vector = new T[cap];
			for(unsigned int i=0; i < Size; i++){
				vector[i] = orig.vector[i];
			}
		}
		return *this;
	}

	//Move operator
	Vector<T> & operator=(Vector<T> &&rhs){
		if(this != &rhs){
			delete [] vector;
			vector = rhs.vector;
			Size = rhs.Size;
			rhs.vector = 0;
			rhs.Size = 0;
		}
	return *this;
	}
	
	T & operator[](unsigned int i) const{
		if(i>=Size || i < 0){
			throw std::out_of_range("Out of range");
		}
		return vector[i];
	}
	//pushback lägger element sist i listan	
	void push_back(T val){
		if(Size == cap){
			cap = cap*2;
			T *tmp = new T[cap];
			for(unsigned int i = 0; i<Size;i++){
				tmp[i] = vector[i];
			}
			delete [] vector;
			vector = tmp;
			vector[Size] = val;
			Size++;
		}
		else{
			vector[Size] = val;
			Size++;
		}
	}
	//Inserts a value at the given index
	void insert(unsigned int i, T val){
		if(i<0||i>Size)
			throw std::out_of_range("Out of range");
		if(i == Size){
			push_back(val);
		}
		else if(Size == cap){
			cap = cap*2;
			T *tmp = new T[cap];
			for(unsigned int j = Size; j>i;j--){
				tmp[j] = vector[j-1];
			}
			tmp[i] = val;

			for(unsigned int j = 0; j<i;j++){
				tmp[j] = vector[j];
			}

			delete [] vector;
			vector = tmp;
			Size++;
		}
		else{
			for(unsigned int j = Size; j>i;j--){
				vector[j] = vector[j-1];
			}
			vector[i] = val;
			Size++;
		}
	}

	void erase(unsigned int i){
		if(i < 0 || i>=Size)
			throw std::out_of_range("Erase: index out of range");
		T tmp1,tmp2;
		for(unsigned int j = i; j<Size;j++){
			if(j==i){
				tmp1 = vector[j+1];
				vector[j] = tmp1;
			}
			else if(j == Size-1)
					vector[j] = T();
			else{
				tmp1 = vector[j+1];
				vector[j] = tmp1;
			}
		}
		Size--;
	}

	void clear(){
		for(unsigned int i = 0; i<Size;i++){
			vector[i].~T();
		}
		Size = 0;
	}

	unsigned int size() const{
		return Size;
	}

	void sort(bool ascending=true) {
		if(Size != 0){
			std::sort(&vector[0], &vector[Size]);
		}
		if(!ascending){ 
			std::reverse(&vector[0], &vector[Size]);
		}
	
	}
	bool exists(const T & val) const{
		if(Size == 0){
			return false;
		}
		auto iter = std::find(&vector[0], &vector[Size], val); 
		if(iter != &vector[Size]){
			return true;
		}else{
			return false;
		}
	}

};
