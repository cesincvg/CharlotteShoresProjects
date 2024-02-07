/* File: SortingList.cpp
 * Course: CS216-00x
 * Project: Lab 12
 * Purpose: the implementation of two functions to provide sorting operations.
 *
 *** DO NOT CHANGE THIS FILE ***
 */
#ifndef SORTINGLIST_CPP
#define SORTINGLIST_CPP

#include "SortingList.h"

#include <iostream>
#include <vector>
#include <utility>   //std::swap
#include <iomanip> 
#include <algorithm> //std::sort
#include <cassert>

template <typename T>
void SortingList<T>::insert(T newitem){
    items.push_back(newitem);
}

template <typename T>
int SortingList<T>::size(){
    return items.size();
}

template <typename T>
T& SortingList<T>::operator[](int index){
    assert(index >= 0 && index < size());
    return items[index];
}


template <typename T>
void SortingList<T>::sort(){
    std::sort(items.begin(), items.end());
}

template <typename T>
void SortingList<T>::selection_sort(int (T::*compare) (T other) const){
    size_t min_index = 0;
    for(size_t i = 0; i < items.size()-1 ; i++) 
    {
        min_index = i;
        for(size_t j = i+1; j < items.size(); j++) 
        {
            // find the index of the minimum from the current sequence 	
            if((this->items[min_index].*compare)(this->items[j]) < 0)
            {
                min_index = j;
            }
        }
        // if min_index != i, swap these two
        if (min_index != i) {
            swap(this->items[i], this->items[min_index]);
    }
}
}

template <typename T>
void SortingList<T>::bubble_sort(int (T::*compare)(T other) const){
    for(size_t i = 1; i < items.size(); i++) 
    {
        for(size_t j = 0; j < items.size() - 1; j++) 
        {
            if((this->items[j].*compare)(this->items[j+1]) < 0) 
                swap(this->items[j], this->items[j+1]);
        }
    }
}

template <typename T>
void SortingList<T>::shuffle(){
    for(size_t i = items.size()-1; i > 1; i--)
    {
        // j = random integer with 0 <= j <= i
        //swap(A[j], A[i])
        size_t j = rand() % (i+1);
        swap(items[i], items[j]);		
    } 
}

template <typename T>
void SortingList<T>::print(){
    cout<<"Data itmes in the list: " << endl;
    for(size_t i = 0; i < items.size(); i++){
        cout << items[i] << endl;
    }
    cout<<endl;
}

#endif 
