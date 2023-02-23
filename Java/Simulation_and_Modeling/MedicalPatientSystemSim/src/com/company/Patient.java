package com.company;

//Create the Patient class
class Patient implements Comparable {
    /* This is a Patient class. It stores the time the patient gets in line,
        the time they are in the server, and the time they are in the system.
        Additionally, it keeps an ID for the balk event associated with the patient.
     */
    protected double timeInLine;
    protected double timeInServer;
    protected double timeInSystem;
    protected double timeOfArrival;
    protected int patNum;
    protected int patBalk; //this is the unique identifier (ID) for the balk event
    protected int percent;
    protected double rate; //service rate
    protected String patType; //patient type: Heart, Gastro, Bleeding

    public Patient(int x) {
        //initializing object variables to zero
        timeInLine = 0;
        timeInServer = 0;
        timeInSystem = 0;
        //Initializing patient number and Patient Balk ID to x
        patNum = x;
        patBalk = x;

        percent = (int) (Math.random() * 100);

        if(percent <= 20){
            rate = 0.067; //service rate of 8 per hour (8/60min) .133
            patType = "Gastro";
        }else if(percent <= 50){
            rate = 0.033; //service rate of 4 per hour (4/60min) .067
            patType = "Heart";
        }else {
            rate = 0.10; //service rate of 12 per hour (12/60min) .20
            patType = "Bleeding";
        }
    }
    //Must implement a comparable for the Patient class in order to properly use the queue manager
    public int compareTo(Object o) {
        if(getTimeInLine() > ((Patient) o).getTimeInLine()) {
            return 1;
        } else if(getTimeInLine() < ((Patient) o).getTimeInLine()) {
            return -1;
        } else {
            return 0;
        }
    }//End of Patient compareTo()

    //Initialize getters
    public double getTimeInLine() {
        return timeInLine;
    }
    public double getTimeInServer() {
        return timeInServer;
    }
    public double getTimeInSystem() {
        return timeInSystem;
    }
    public double getTimeOfArrival() {
        return timeOfArrival;
    }
    public int getBalked() {
        return patBalk;
    }
    public double getRate() {
        return rate;
    }
    public String getPatType(){
        return patType;
    }

    //Initialize setters
    public void setTimeInLine(double x) {
        timeInLine += x; //NOTE: We need to add to the value of x, it is out Delta time
    }
    public void setTimeInServer(double x) {
        timeInServer += x; //NOTE: We need to add to the value of x, it is out Delta time
    }
    public void setTimeInSystem() {
        timeInSystem = timeInLine + timeInServer;
    }
    //The time the patient arrives is set directly from x
    public void setTimeOfArrival(double x) {
        timeOfArrival = x;
    }
    //x represents the balk event
    public void SetBalked(int x) {
        patBalk = x;
    }
}//End of Patient class
