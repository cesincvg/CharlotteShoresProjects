/* File: card.cpp
 * Course: CS216-00x
 * Project: Project 2
 * Purpose: the implementation of member functions for the Card class.
 * Author: (your name)
 */
#include <sstream>
#include <iomanip>
#include "card.h"

 // Default constructor marks card as invalid
Card::Card() {
    suit = 'I';
    point = 0;
}

// Alternate constructor
Card::Card(char s, int p)
{
    suit = s;
    point = p;
}

// access the card point value
int Card::getPoint() const
{
    return point;
}

// access the card suit value
char Card::getSuit() const
{
    return suit;
}

// compare with another Card object passed in as parameter: other
// if the object your are working on has higher point than other, return 1;
// if the object your are working on has lower point than other, return -1;
// otherwise, return 0
int Card::compareTo(Card other) const
{
    if (this->point < other.point)
        return -1;
    if (this->point > other.point)
        return 1;
    return 0;
}

// operator overloading for operators "<" and "<<"
// they are defined as friend functions

// define the operator "<" for Card class
bool operator<(Card C1, Card C2)
{
    if(C1.point < C2.point){
        return true;
    }
    else{
        return false;
    }
}

// define the operator "<<" for Card class
// to send the Card object to the cout
// Display a description of the Card object to standard output
// The output should look like:
//   the sign of suit, followed by the point(occupy two-character width), then followed by the sign of suit again
//   for example: ♥ 6♥ and ♣ A♣
ostream& operator<<(ostream &out, const Card& C)
{
    string face;
    if (C.point > 10){
        switch(C.point){
            case 11:
                    face = 'J';
                    break;
            case 12:
                    face = 'Q';
                    break;
            case 13:
                    face = 'K';
                    break;
            case 14:
                    face = 'A';
                    break;

        }
    }
    else{
        stringstream ss;
        ss << C.point;
        ss >> face;
    }
    
    switch (C.suit) {
    case 'H':
            cout << HEART << setw(2) << face << HEART;
            break;
     case 'C':
            cout << CLUB << setw(2) << face << CLUB;
            break;
     case 'D':
            cout << DIAMOND << setw(2) << face << DIAMOND;
            break;
     case 'S':
            cout << SPADE << setw(2) << face << SPADE;
            break;
            
    }
        
    
    return out;
}
