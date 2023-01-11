package com.company;

import java.util.ArrayList;

//build the WLFUTeam generic manager class type
public class WLFUManager<T extends Comparable> {
    //ArrayList to store employee objects
    protected ArrayList<T> mylist = new ArrayList<T>();
    protected int mycount;

    //create generic manager constructor
    public WLFUManager(){
        mycount = 0;
    }

    //add values into array
    public int addvalue(T x){
        mylist.add(mycount++, x);
        return mycount;
    }

    //get values from array
    public T getvalue(int x){
        if(x <= mycount)
            return mylist.get(x);
        else
            return mylist.get(0);
    }
    //This is my generic sort, it will sort anything that the manager manages
    //but making sure the objects support the the compareTo function
    public void sortTeam(){

        T xsave, ysave, a, b;
        int isw = 1, xlast = mylist.size();
        while(isw == 1){
                isw = 0;
                for(int i = 0; i <= xlast - 2; i++){
                    a = mylist.get(i);
                    b = mylist.get(i + 1);
                    switch(b.compareTo(a)){

                        case 1://objects are in the right order
                            break;

                        case - 1://objects are out of order they must be changed

                            xsave = mylist.get(i);
                            ysave = mylist.get(i + 1);
                            mylist.remove(i);
                            mylist.add(i, ysave);
                            mylist.remove(i + 1);
                            mylist.add(i + 1, xsave);

                            isw = 1;
                            break;
                        default:
                    }
                }
        }

    }
}
