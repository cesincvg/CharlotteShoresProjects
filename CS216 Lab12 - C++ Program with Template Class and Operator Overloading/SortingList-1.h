
/* File: SortingList.h
 * Course: CS216-00x
 * Project: Lab 12
 * Purpose: the declaration for the SortingList class.
 *          it stores a sequence of Term objects to perform sorting operation
 *** DO NOT CHANGE THE DECLARATION OF SortingList CLASS ***
 *
 */

#ifndef SORTINGLIST_H
#define SORTINGLIST_H


#include <vector>
#include <string>
#include "term.h"

using namespace std;

template<class T>
class SortingList
{
   public:
    // inserts the newitem to the end of the current vector   
    void insert(T newitem);

    // return how many items in the list
    int size();

    // operator overloading for "[]"
    // provide the direct access by index number
    T& operator[](int index);

    // sort all items in ascending order 
    // the items are compared using operator "<"
    // using sort() from standard library
    void sort();

    // provide different sorting algorithms 
    // based on comparison defined by the function passing in as parameter
    
    // apply selection sorting algorithm
    // pass in member function name as the parameter
    // where function defines the comparison between two Term objects
    void selection_sort(int (T::*compare)(T other) const);

    // apply bubble sorting algorithm
    // pass in member function name as the parameter
    // where function defines the comparison between two Term objects
    void bubble_sort(int (T::*compare)(T other) const);

    // apply shuffle algorithm
    void shuffle();

    // display all the items in the sequence
    void print();
    
   private:
    vector<T> items;
};


#endif	/* SORTINGLIST.H */
#include "SortingList.cpp"

