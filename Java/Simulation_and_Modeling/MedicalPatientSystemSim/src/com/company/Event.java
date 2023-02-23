package com.company;

//Creat the Event class
class Event implements Comparable {
    /*This is an Event class. Events hold and event type, time, and in the case of balk event, a pointer
        to the Patient when the balk event occurs.
     */
    protected int x; //Event type
    protected double time; //Time of the Event
    protected int myPat; //Balk Event. Represents the unique identifier (ID) of a balked patient
    protected int heartCount = 0;
    protected int gastroCount = 0;
    protected int bleedCount;

    Patient patient = new Patient(-9);

    public Event(int eventType, double eventTime, int balkPat) {
        x = eventType;
        time = eventTime;

        if(x == 7) {
            myPat = balkPat; //This is a balk event
        } else {
            myPat = -9; //This is not a balk event
        }
    }

    public int compareTo(Object o) {
        if(getTime() > ((Event) o).getTime()){ //if time a > time b, return 1
            return 1;
        } else if(getTime() < ((Event) o).getTime()) { //if time a < time b, return -1
            return -1;
        } else {
            return 0;
        }
    }

    public double getTime() {
        return time;
    }
    public int getEventType() {
        return x;
    }
    public int getPatient() {
        return myPat;
    }
    public String getBalkingPat(){
        String patType;

        if((myPat == patient.getBalked()) && (patient.getPatType().equals("Gastro"))){
            patType = "Gastro";
        } else if ((myPat == patient.getBalked()) && (patient.getPatType().equals("Heart"))){
            patType = "Heart";
        }else {
            patType = "Bleeding";
        } return patType;
    }
}//End of Event class
