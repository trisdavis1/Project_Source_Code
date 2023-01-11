package com.company;

import java.util.ArrayList;

class GenericManagerStack<T> {

   protected ArrayList<T> myStack;
   protected int number;

   //generic constructor
   public GenericManagerStack(){

       number = 0;//next available value in array

       //create initial arraylist of 100
       myStack = new ArrayList<T>(100);

   }

   public int getNumber(){

       return number;

   }

   public int pushnode(T x){

       System.out.println("in pushnode " + number + "x is " + x);

       //this pushes a node on the stack. always adds to the front(top)
       myStack.add(number, x);

       number++;

       System.out.println("leaving pushnode...");

       return number;

   }//end pushnode

   public T popnode(){

       //returns first node in the list

       T nodevalue;//value in the node to be popped

       //finds the node at head of list
       nodevalue = myStack.get(number - 1);
       //pop node by taking it off the list and moving the head
       myStack.remove(number - 1 );
       number--;
       //return the value of this node
       return nodevalue;

   }

   public T peeknode(){

       //returns the contents of the top of the stack
       //it does not pop the node, just allows the user to look

       T nodevalue;//peek value

       nodevalue = myStack.get(number - 1);

       return nodevalue;

   }//end peeknode

   boolean emptystack(){
     if(number == 0) {
         return true;
     } else return false;
   }

}
