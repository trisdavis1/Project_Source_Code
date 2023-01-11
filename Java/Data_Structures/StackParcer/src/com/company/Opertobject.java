package com.company;

public class Opertobject {

    //create operator class that will hold a character operator and its stack
    protected char operator;
    protected int priority;
    //create operator object constructor
    public Opertobject(char opert, int pri){

        operator = opert;
        priority = pri;

    }

    public int Getprior(){
        return priority;
    }

    public char Getopert(){
        return operator;
    }
}//end operator class
