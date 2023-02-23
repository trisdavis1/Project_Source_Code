package com.company;

import java.lang.*;

/* This is a simulation program written in Java. It represents a multiserver queuing situation.
    A patient arrives at a service facility which has two service bays. If either bay is empty patients
    immediately move into that bay for service. If both bays are full, patients move into line. After
    patients have spent a random time in line, they automatically balk and leave the line.
    The simulation has the following events.

    Event Type              Description
    1                       Patient arrives at facility
    2                       Patient enters the line at the facility
    3                       Patient enters service bay 1 (Dr. James S. Smith)
    4                       Patient enters service bay 2 (Dr. Randolf Sims)
    5                       Patient leaves service bay 1
    6                       Patient leaves service bay 2
    7                       Patient reaches "Critical Period" (balks) and leaves waiting line
    8                       Simulation shut down

    This simulation has two main structures:
    The event queue, a set of ordered (low to high) of linked list event
    objects.
    The waiting line, a set of linked (by a linked list) objects representing the patients
 */

public class Main {

    public static void main(String[] args) throws Exception {

        double bigTime = 0.0; //Main clock in seconds
        double eventTime; //Event time
        double deltaTime;
        double balkTime;
        
        //Here we create an event manager "eventQueue"
        GenericManager<Event> eventQueue = new GenericManager<>();
        //Here we create a queue for patients waiting for service
        GenericManager<Patient> patientQueue = new GenericManager<>();

        int patBalkId = 0; //unique balk ID
        int numInQueue;

        int totalThruSystem = 0;
        int totalThruLine = 0;
        int totalHeartThruLine = 0;
        int totalGastroThruLine = 0;
        int totalBleedThruLine = 0;

        double totalTimeInLine1 = 0.0;
        double totalTimeHeartInLine = 0.0;
        double totalTimeGastroInLine = 0.0;
        double totalTimeBleedInLine = 0.0;

        double totalTimeInServer1 = 0.0;

        double ttil; //total time in queue
        double ttis; //total time in queue server
        double tthil; //total time heart patient spent in queue
        double ttgil; //total time gastro patient spent in queue
        double ttbil; //total time bleeding patient spent in queue

        int patID; //patient id from a balk event
        int balkedPat = 0; //the number of patients that balked
        int balkedGastro = 0; //number of Gastro patients that balk
        int balkedHeart = 0; //number of Heart patients that balk
        int balkedBleeding = 0; //number of Bleeding patients that balk

        boolean bay1Busy = false; //Service bay 1, false = empty
        boolean bay2Busy = false; //Service bay 2, false = empty

        Patient treated1 = new Patient(-9); //Patients in service bay 1
        Patient treated2 = new Patient(-9); //Patients in service bay 2

        double deltaServiceTime; //This is the delta service time
        double deltaArrivalTime; //this is the delta arrival time

        Patient newPatient; //a work patient object for patients entering the queue
        Patient workPatient; //a work patient object for patients leaving the queue

        //This is the last event of the simulation
        Event workEvent = new Event(8, 6000.0, 0);
        //Adding the event to the queue
        eventQueue.addInOrder(workEvent);
        //Add the arrival of the first patient
        deltaArrivalTime = timeOfArrivalAndService(0.05); //patients arrive at the rate of 3/60 min
        //The event time is the current time + delta time
        eventTime = bigTime + deltaArrivalTime;
        System.out.println("The first patient has arrived at " + eventTime);
        //create the event time is the current time plus delta time
        workEvent = new Event(1, eventTime, 0);
        //stores this event in the Queue
        eventQueue.addInOrder(workEvent);

        //Now we start processing the events. Get the first event off the Event queue
        workEvent = eventQueue.getValue(0);


        while (workEvent.getEventType() != 8) {
            //Updating the time because this is a valid event
            deltaTime = workEvent.getTime() - bigTime;
            //update everybody with this delta time
            ttil = updatePatient(patientQueue, deltaTime);
            totalTimeInLine1 += ttil;

            //update the total time in line for each patient type
            if(workEvent.getBalkingPat().equals("Heart")){
                tthil = updatePatient(patientQueue, deltaTime);
                totalTimeHeartInLine += tthil;
            }else if(workEvent.getBalkingPat().equals("Gastro")){
                ttgil = updatePatient(patientQueue, deltaTime);
                totalTimeGastroInLine += ttgil;
            }else{
                ttbil = updatePatient(patientQueue, deltaTime);
                totalTimeBleedInLine += ttbil;
            }

            //Update everybody in the servers
            ttis = updateServers(treated1, bay1Busy, treated2, bay2Busy, deltaTime);
            //get the event type and process it. First we'll update the time
            totalTimeInServer1 += ttis;
            bigTime = workEvent.getTime();

            if ((bigTime >= 1000.0) && (bigTime <= 6000.0))
                System.out.println("******************* THE TIME IS NOW ********************** " + bigTime);

            //Get the number in the patient queue at this time
            numInQueue = patientQueue.getPatCount();
            //determine patient type and service rate
            //myPatientType = generatePatientType();
            switch (workEvent.getEventType()) {

                case 1 -> { //patient arrives at the facility
                    /*Generate the object for the patient. If the servers are busy put the patient
                        generated in a patient has entered the line event
                      If the servers are not busy, generate a patient object and put the patient in
                      the server.
                     */
                    if ((bay1Busy == false) && (numInQueue <= 0)) {
                        //server 1 is not busy and there is no one in the patient queue
                        //put the patient in the first server

                        //create the patient object
                        newPatient = new Patient(-9);

                        if ((1000.0 <= bigTime) && (bigTime <= 6000.0))
                            System.out.println(newPatient.getPatType() + " patient enters server 1");

                        //set the time of arrival for this patient
                        newPatient.setTimeOfArrival(bigTime);
                        //newPatient = patientType();
                        //place patient in server 1
                        bay1Busy = true;
                        treated1 = newPatient;
                        /*Generate the finished server event for this patient.
                            The patient type and service rate is determined by "generatePatientType" method
                            */
                        deltaServiceTime = timeOfArrivalAndService(newPatient.getRate());
                        eventTime = deltaServiceTime + bigTime;

                        workEvent = new Event(5, eventTime, -9);

                        //placing this event in the eventQueue
                        eventQueue.addInOrder(workEvent);

                    } else if ((bay1Busy == true) && (bay2Busy == false) && (numInQueue <= 0)) {
                        /*Server 2 is open and there is no one in the patient queue, we can send the
                            patient to server 2
                         */
                        //Creating the patient object
                        newPatient = new Patient(-9);

                        if ((bigTime >= 1000.0) && (bigTime <= 6000.0))
                            System.out.println(newPatient.getPatType() + " patient enters server 2");

                        //set the time of arrival for this patient
                        newPatient.setTimeOfArrival(bigTime);
                        //put this patient is server 2
                        bay2Busy = true;
                        treated2 = newPatient;
                        //generate the finished server event for this patient
                        deltaServiceTime = timeOfArrivalAndService(newPatient.getRate());//patient served at specified rate
                        eventTime = deltaServiceTime + bigTime;

                        workEvent = new Event(6, eventTime, -9);
                        //put this event in the eventQueue
                        eventQueue.addInOrder(workEvent);

                        //done in server 2
                    } else if ((bay1Busy == true) && (bay2Busy == true)) {
                        //If both servers are busy we must put the patient into the waiting line
                        //first we'll generate the patient, they must have a unique ID
                        patBalkId++;
                        newPatient = new Patient(patBalkId);

                        if ((bigTime >= 1000.0) && (bigTime <= 6000.0))
                            System.out.println(newPatient.getPatType() + " patient has entered the line...");

                        //set the time of arrival for this patient
                        newPatient.setTimeOfArrival(bigTime);
                        //put patient at the end of the line
                        patientQueue.addAtEnd(newPatient);
                        /*Here we will create the patient balk event.
                          Patients will balk (die) based on their specified "critical period":
                              Heart patient = 35 min
                              Gastro patient = 80 min
                              Bleeding patient = 60 min
                          */
                        if(newPatient.getPatType().equals("Bleeding")) {
                            balkTime = bigTime + 60.0;
                        } else if (newPatient.getPatType().equals("Gastro")) {
                            balkTime = bigTime + 80.0;
                        } else {
                            balkTime = bigTime + 35.0;
                        }
                        //here we create this event
                        workEvent = new Event(7, balkTime, patBalkId);
                        //here we add the even to the eventQueue
                        eventQueue.addInOrder(workEvent); //patient is in the line

                    }
                    //generate the next event for the next patient to arrive
                    /*Patient arrives at rate of 3/60 min. This is the standard Distribution based
                        on the Poission process.
                    */
                    deltaArrivalTime = timeOfArrivalAndService(0.05);
                    //Event time is the current time + delta time
                    eventTime = bigTime + deltaArrivalTime;

                    if ((bigTime >= 1000.0) && (bigTime <= 6000.0))
                        System.out.println("The next patient is a " + workEvent.getBalkingPat() +
                                ". Patient is arriving at " + eventTime);

                    //create the event for the next patient to arrive
                    workEvent = new Event(1, eventTime, 0);
                    //Store this event in the queue
                    eventQueue.addInOrder(workEvent);

                }
                case 2 -> //Patient enters the line at the facility
                        //Generate the balk event for this patient and put them in line
                        System.out.println("this is the 2nd Event, we have already incorporated it into the arrival " +
                                "event specified by case 1. If we are here were in trouble!");
                case 3 -> { //Patient enters service bay 1 (Dr. James S. Smith)
                    /*Here we will decrement the number of patients in line.
                        We will also generate a completed service time and a departure event for the patient
                     */
                    //Set server 1 to busy
                    numInQueue = patientQueue.getPatCount();
                    if ((bay1Busy == false) && (numInQueue > 0)) {
                        //The patient can enter bay 1
                        //Well pull that patient from the front of the line
                        workPatient = patientQueue.getValue(0);

                        if ((bigTime >= 1000.0) && (bigTime <= 6000.0))
                            System.out.println("The first " + workPatient.getPatType() +
                                    " patient in line has entered server 1");

                        patID = workPatient.getBalked(); //get the patient balk event
                        balkEvent(eventQueue, patID); //remove the event from the Queue
                        totalThruLine++; //This patient just came through the line
                        //keep track of patient type coming through the line
                        if(workPatient.getPatType().equals("Heart")){
                            totalHeartThruLine++;
                        }else if(workPatient.getPatType().equals("Gastro")){
                            totalGastroThruLine++;
                        } else {
                            totalBleedThruLine++;
                        }
                        //remove this patient from the queue and put them in the server
                        patientQueue.removeIt(0);
                        //put this patient in server 1
                        bay1Busy = true;
                        treated1 = workPatient;
                        //generate the finished server event for this patient
                        //patients service rate
                        deltaServiceTime = timeOfArrivalAndService(workPatient.getRate());
                        eventTime = deltaServiceTime + bigTime;
                        workEvent = new Event(5, eventTime, -9);
                        //here we put this event in the event queue
                        eventQueue.addInOrder(workEvent);

                        //End of "enter service bay 1"
                    } else { /*either the server is busy, and we experienced an event collision
                         or there is no one in the line
                         */
                        System.out.println("In Event 3... Patient has entered service bay 1 but we are unable " +
                                "to process the event!");
                    }
                }
                case 4 -> { //patient enters service bay 2
                    /*Here we will decrement the number of patients in line.
                        We will also generate a completed service time and a departure event for the patient.
                     */
                    //set this server to busy
                    numInQueue = patientQueue.getPatCount();
                    if ((bay2Busy == false) && (numInQueue > 0)) {
                        //The patient can enter bay 2
                        //Well pull that patient from the front of the line
                        workPatient = patientQueue.getValue(0);

                        if ((bigTime >= 1000.0) && (bigTime <= 6000.0))
                            System.out.println(workPatient.getPatType() +
                                    " patient has entered service bay 2 from the line");

                        patID = workPatient.getBalked(); //get the patient balk event
                        balkEvent(eventQueue, patID); //remove the event from the queue
                        totalThruLine++; //this patient just came through the line
                        //keep track of patient type coming through the line
                        if(workPatient.getPatType().equals("Heart")){
                            totalHeartThruLine++;
                        }else if(workPatient.getPatType().equals("Gastro")){
                            totalGastroThruLine++;
                        } else {
                            totalBleedThruLine++;
                        }
                        //remove the patient from the queue and put them in the server
                        patientQueue.removeIt(0);
                        //put the patient in server 2
                        bay2Busy = true;
                        treated2 = workPatient;
                        //generate the finished server event for the patient
                        deltaServiceTime = timeOfArrivalAndService(workPatient.getRate()); //patients served at rate of 2/60min
                        eventTime = deltaServiceTime + bigTime;
                        workEvent = new Event(6, eventTime, -9);
                        //put this event in the eventQueue
                        eventQueue.addInOrder(workEvent);

                        //end of service bay 2
                    } else { /*either the service is busy, and we experienced an event collision
                                    or there is no one in the line
                                  */
                        System.out.println("In Event 4... Patient has entered service bay 2 but we are unable " +
                                "to process the event");
                    }
                }
                case 5 -> { //patient leaves service bay 1
                    /*Here we will update the number of customers through the system
                        We will als set the server to not busy
                        If there are patients in the waiting line we will generate a service bay 1 event
                     */
                    bay1Busy = false;
                    totalThruSystem++;
                    numInQueue = patientQueue.getPatCount();

                    if (numInQueue > 0) {
                        /*If there are patients in the line we will generate a patient entered service
                            bay 1 event now at bigTime
                            NOTE: Possible problems with collision events
                         */
                        workEvent = new Event(3, bigTime + 0.01, -9);
                        //put this event in the eventQueue
                        eventQueue.addInOrder(workEvent);
                    }
                    if ((bigTime >= 1000.0) && (bigTime <= 6000.0))
                        System.out.println(workEvent.getBalkingPat() + " patient has left server 1. Number in Queue: "
                                + numInQueue);
                }
                case 6 -> { //patient leaves service bay 2
                    /*set the server to not busy. If there are people in the line and generate a patient
                        has entered a service bay 2 event
                     */
                    bay2Busy = false;
                    totalThruSystem++;
                    numInQueue = patientQueue.getPatCount();

                    if (numInQueue > 0) {
                        /* There are patients in the line. Here we will generate a patient has entered
                            service bay 2 now at bigTime
                         */
                        workEvent = new Event(4, bigTime + 0.01, -9);
                        //put this event in the event queue
                        eventQueue.addInOrder(workEvent);
                    }
                    if ((bigTime >= 1000.0) && (bigTime <= 6000.0))
                        System.out.println(workEvent.getBalkingPat() + " patient has left server 2");
                }
                case 7 -> { //Patient balks and leaves the waiting line
                    //delete the patient from the line
                    //delete the patient from the queue
                    patID = workEvent.getPatient();
                    //remove this patient from the line
                    patientBalked(patientQueue, patID);
                    //Adding the patient to the patients that have gone through the system
                    balkedPat++;
                    totalThruLine++;
                    //keep track and count of patient type through the line
                    if(workEvent.getBalkingPat().equals("Gastro")) {
                        balkedGastro++;
                    } else if(workEvent.getBalkingPat().equals("Heart")){
                        balkedHeart++;
                    } else {
                        balkedBleeding++;
                    }

                }
                case 8 -> { //This is the shutdown event
                    System.out.println("This is event 8. We are in the switch statement, Trouble!");
                    continue;
                }
                default -> System.out.println("This is a bad event " + workEvent.getEventType() + " at time " +
                        workEvent.getTime());
            }//End of switch
            //This event is processed. Delete it from the eventQueue
            eventQueue.removeIt(0);
            //now lets get the next event
            if ((bigTime >= 1000.0) && (bigTime <= 6000.0))
                System.out.println("*********************** The time is " + bigTime + "*************************");

            workEvent = eventQueue.getValue(0);

        }//end of while loop

        //Now we print the Statistics
        System.out.println("Printing the Statistics for this Run:");
        //Total number of patients through the line, average time in line, and patients balking
        System.out.println("There were a total of " + totalThruLine + " patients going through the line.");
        System.out.println("These patients spent an average time in line of " + totalTimeInLine1 / totalThruLine);
        System.out.println("There was a total of " + balkedPat + " patients balking.");
        System.out.println("Patients spent an average of " + totalTimeInServer1 / totalThruSystem + " in the servers.");
        //Number of different patient types serviced
        System.out.println("There were a total of " + totalHeartThruLine + " Hearts patients serviced.");
        System.out.println("There were a total of " + totalGastroThruLine + " Gastro patients serviced.");
        System.out.println("There were a total of " + totalBleedThruLine + " Bleeding patients serviced.");
        //Number of different patient types balking
        System.out.println("There were a total of " + balkedHeart + " Heart patients balking.");
        System.out.println("There were a total of " + balkedGastro + " Gastro patients balking.");
        System.out.println("There were a total of " + balkedBleeding + " Heart patients balking.");
        //Average time different patient types spend in Queue
        //Heart patients
        System.out.println("Heart patients spent an average time in queue of "
                + totalTimeHeartInLine / totalHeartThruLine );
        //Gastro patients
        System.out.println("Gastro patients spent an average time in queue of "
                + totalTimeGastroInLine / totalGastroThruLine );
        //Bleeding patients
        System.out.println("Bleeding patients spent an average time in queue of "
                + totalTimeBleedInLine / totalBleedThruLine );
    }//End of Main

    /*This function removes a balking event from the eventQueue in "eventQueue" arraylist.
        The function traverses the line and finds the event with the balkID and removes
        it from the array
     */
    public static void patientBalked(GenericManager<Patient> patientLine, int balkID) {
        int i;
        int numInLine;
        int patBalkID;

        Patient workPatient;

        //getting ready to traverse the patient line
        numInLine = patientLine.getPatCount();
        workPatient = patientLine.getValue(0);
        patBalkID = workPatient.getBalked();

        i = 0;
        while ((patBalkID != balkID) && (i <= (numInLine - 1))) {
            workPatient = patientLine.getValue(i);
            patBalkID = workPatient.getBalked();

            i++;
        }
        //Remove patient i from the line
        /*NOTE: Possibly manipulate "removeIt" function in GenericManager class to support the different
                critical periods for the different patients.
            */
        if (i == 0) {
            patientLine.removeIt(0);
        } else if ((patBalkID == balkID) && (i > 0)) {
            patientLine.removeIt(i - 1);
        }
    }//End of patientBalked

    /*This function removes a balking event from the event queue. It traverses the EventQue
        and finds the event with the corresponding balk ID and removes it
     */
    public static void balkEvent(GenericManager<Event> eventQue, int balkID) {
        int i;
        int numInQueue;
        int eventBalkID;

        Event workEvent = new Event(1, 1.0, 1);

        //getting ready to traverse the event queue
        numInQueue = eventQue.getPatCount();
        workEvent = eventQue.getValue(0);
        eventBalkID = workEvent.getPatient();

        i = 0;
        while ((eventBalkID != balkID) && (i <= (numInQueue - 1))) {
            workEvent = eventQue.getValue(i);
            eventBalkID = workEvent.getPatient();

            i++;
        }
        //removing event i from the Queue line
        /*NOTE: Possibly manipulate "removeIt" function in GenericManager class to support the different
                critical periods for the different patients.
            */
        if (eventBalkID == balkID) {
            eventQue.removeIt(i - 1);
        }
    }//End of balkEvent

    /*This is the random process generator for delta time to determine the time of arrival rate or the service rate.
        The parameter rate corresponds to the arrival rate or service rate
     */
    public static double timeOfArrivalAndService(double rate) {
        double deltaTime;
        double bigX;
        String patientType;

        bigX = Math.random();
        if (bigX > 0.9) {
            bigX = Math.random();
        }
        deltaTime = -Math.log(1.0 - bigX) / rate;

        return deltaTime;
    }//End of timeOfArrivalAndService (random process generator)

    /*This function sums up all the time spent for a patient in the line for this deltaTime
     */
    public static double updatePatient(GenericManager<Patient> patientLine, double deltaTime) {
        double lineTime = 0.0;
        int patientInLine;
        patientInLine = patientLine.getPatCount();

        if (patientInLine == 0) {
            return lineTime;
        } else {
            lineTime = deltaTime + patientInLine;
            return lineTime;
        }
    }//End of updatePatient

    //This functions updates the amount of time for patients in a specific server
    public static double updateServers(Patient server1, boolean bay1, Patient server2, boolean bay2,
                                       double deltaTime) {
        double serverTime = 0.0;

        if (bay1 && bay2) {
            serverTime = 2 * deltaTime;
            return serverTime;
        } else if (bay1 || bay2) {
            serverTime = deltaTime;
        }return serverTime;

    }//End of updateServers
}