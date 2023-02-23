package com.company;

import java.util.ArrayList;

class GenericManager<T extends Comparable>{
    /* NOTE: Must add this comparable in order to use compareTo function that
        comes along with T
     */
    protected ArrayList<T> myPatList = new ArrayList<T>();
    protected int patCount;

    public GenericManager() {
        patCount = 0; //next available value in array "myPatList"
    }

    //Adds the object to the front of the list
    public int addAtFront(T x) {
        myPatList.add(0, x);
        patCount++;

        return patCount;
    }//End of addAtFront
    //Places the Patient objects in order from smallest to largest
    public int addInOrder(T x) {
        int i;
        if((patCount == 0) || ((x.compareTo(myPatList.get(0))) == -1) || (x.compareTo(myPatList.get(0))) == 0) {
            myPatList.add(0, x); //this is less than or equal to the first entry

        } else if((x.compareTo(myPatList.get(patCount - 1)) == 1) || (x.compareTo(myPatList.get(patCount - 1)) == 0)){
            myPatList.add(patCount, x); //x is greater than the last entry

        } else {
            i = 0;
            while((i < patCount) && (x.compareTo(myPatList.get(i)) == 1)) {
                i++;
            }myPatList.add(i, x);
        }
        //Increment patCount
        patCount++;

        return patCount;
    }//End of addInOrder
    //Puts the values at the end of the ArrayList myPatList
    public int addAtEnd(T x) {
        myPatList.add(patCount++, x);

        return patCount;
    }//End of addAtEnd
    //Gets the values from the myPatList ArrayList
    public T getValue(int i) {
        if(i < patCount){
            return myPatList.get(i);
        } else {
            return myPatList.get(0);
        }
    }//End of getValue

    public int getPatCount() {
        return patCount;
    }

    //Removes the i'th value from the list
    public void removeIt(int i) {
        if((i >= 0) && (i <= patCount - 1)){
            myPatList.remove(i);
            patCount--;
        }
    }//End of removeIt
}//End of GenericManager
