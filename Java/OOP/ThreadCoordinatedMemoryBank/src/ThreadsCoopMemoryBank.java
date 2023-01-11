/*
    This program is an example of creating threads that are both synced and coordinated.  The system
has two threads that simulate a system tries to store information in a limited
buffer space(Dstorage[]/ Cstorage[]). Specifically one thread presents integers for storage in a buffer (an array of
limited size) and the other thread takes a string of characters from the buffer.  The buffer of size 30 is initialized
to all 0s
Here are the rules;
    When Adding values you cannot add when there is not enough room for your number of values
    When Deleting values.  There must always be at least one value to delete or you must wait.
    If there are not enough to delete, you may delete to empty and then quit.
*/


import java.io.*;
import java.io.IOException;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;

import java.util.concurrent.locks.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.util.NoSuchElementException;
import java.lang.*;


public class ThreadsCoopMemoryBank {

    public static void main(String[] args)throws Exception {

        //create a print writer for the class
        PrintWriter output;

        output = new PrintWriter(new File("ThreadMemoryBankOut.txt"));

        //Create the thread pool
        ExecutorService executor = Executors.newFixedThreadPool(2);

        //Now create and launch some Store and Delete threads
        //Create a Buffer
        Buffer Buffer1 = new Buffer(output);

        //create the Store thread and give it priority
        Store toAdd = new Store(Buffer1);
        Delete toDelete = new Delete(Buffer1);

        executor.execute(toAdd);
        executor.execute(toDelete);

        System.out.println("Thread Store and Delete created and launched");
        System.out.flush();
        output.println("Thread Store and Delete created and launched");
        output.flush();

        //Shutdown the executor
        executor.shutdown();

        //let all threads shutdown and wait till all tasks are finished
        while(!executor.isTerminated());
            System.out.println("What do we have in the Buffer?");
            Buffer1.printData();
            System.out.flush();

    }//end of main

    public static class Buffer {
        private int[] Dstorage;
        private String[] Cstorage;
        private int fill;
        PrintWriter outp;
        //create the lock for this class
        private static Lock lockstor = new ReentrantLock();
        //create a condition for the lock
        private static Condition storageinfo = lockstor.newCondition();

        //create buffer constructor
        public Buffer(PrintWriter out1) {
            outp = out1;
            fill = 0;

            //set the buffer storage to 30 integers
            Dstorage = new int[30];
            //set the buffer storage to 30 3 char strings
            Cstorage = new String[30];

            //now initialize all integers to 0
            for(int i = 0; i <= 29; i++)
                Dstorage[i] = 0;

            for(int i = 0; i <= 29; i++)
                Cstorage[i] = " ";
        }//end of constructor

        public int getFill() {
            return fill;
        }

        public void printData() {
            //list the arrray storage
            for(int i = 0; i <= 29; i++)
                System.out.println("Computer " + Cstorage[i] + ": x["+i+"] = " + Dstorage[i]);
                System.out.flush();
        }

        public void storeData(String com, int amt, int value) {
            //first acquire the lock
            lockstor.lock();

            //now try to store the data
            try{

                System.out.println("In storeData: computer " + com + ", amount " + amt +
                        ", integer value " + value + ", fill " + fill);

                while((fill + amt - 1) > 29)
                    //wait till there is room for the data
                    storageinfo.await();
                    System.out.println("Now there is room: computer " + com + ", amount " + amt +
                        ", value " + value + ", fill " + fill);

                    System.out.println("In storeData: computer " + com + ", amount " + amt +
                            ", value " + value + ", fill " + fill);
                    outp.println("In storeData: computer " + com + ", amount " + amt +
                            ", value " + value + ", fill " + fill);
                    System.out.flush();
                    outp.flush();

                //now store the data
                for(int i = fill; i <= fill + amt - 1; i++) {
                    Dstorage[i] = value;
                    Cstorage[i] = com;
                }
                //fill always points to the next available spot
                fill = fill + amt;
                System.out.println("Now the array looks like:");

                for(int i = 0; i <= fill - 1; i++)
                    System.out.println("Computer " + Cstorage[i] + ": x[" + i + "] = " + Dstorage[i]);
                System.out.flush();

                //now that we have given the buffer a value tell conditional to all
            }//end of try block
            catch(InterruptedException ex) {
                System.out.println("Trouble with the catch in storeData");

                ex.printStackTrace();

            }//end catch block
            finally {
                //release the lock signal and release the lock
                storageinfo.signalAll();
                lockstor.unlock();
            }//end of finally block
        }//end of storeData

        public void clearData (String com, int amt){
            //first acquire the lock
            lockstor.lock();

            try{

                System.out.println("In clearData: Computer " + com + ", amount = " + amt + ", fill = " + fill);
                System.out.flush();

                //trying to clear the data, there must be at least one to clear
                while(fill < 1)
                    storageinfo.await();//there must be at least one value to clear

                //now clear this amount (amt) of data or less
                if(fill - amt - 1 < 0){
                    //the Buffer is completely cleared
                    int ifmet = fill - 1;

                    System.out.println("Computer " + com + " clearing data from " + ifmet + " to 0");
                    System.out.flush();

                    for(int i = fill - 1; i >= 0; i--){
                        //clearing the data from the fill - 1 down
                        Dstorage[i] = 0;
                        Cstorage[i] = " ";
                        System.out.flush();

                    }//end of for

                    fill = 0;

                }//end of true
                else{
                    //we can clear the amt and still have data
                    int ifmet = fill - amt;
                    int ifmet1 = fill - 1;

                    System.out.println("Computer " + com + ", clearing from " + ifmet1 + " to " + ifmet);
                    System.out.flush();

                    for(int i = fill - 1; i >= fill - amt; i--) {
                        //clearing data from the fill - 1 down
                        System.out.println("Clearing computer " + com + ": x[" + i + "]= " + Dstorage[i]);
                        Dstorage[i] = 0;
                        Cstorage[i] = " ";
                        System.out.flush();

                    }//end of for

                    fill = fill - amt;

                    System.out.println("Now the fill is: " + fill);
                    System.out.flush();

                }//end of false

            }//end of try
            catch (InterruptedException ex){

                System.out.println("Trouble with catch in clearData");
                ex.printStackTrace();
            }//end of catch
            finally{
                //now the Buffer is cleared, tell conditional to all
                storageinfo.signalAll();

                //release the lock
                lockstor.unlock();
            }//end of finally

        }//end of Delete

    }//end of class Buffer

    public static class Store implements Runnable {
        //this is the buffer to add integers to the storage arrays
        Buffer Bufi;

        //this is the add constructor
        public Store(Buffer store){

            Bufi = store;

        }

        public void run() {
            //add 3 character strings to the buffer
            String [] addcom = {"PB1", "FB2", "PB1", "MB3", "FB4"};
            //add integers to the buffer
            int [] addamt  = {5, 6, 8, 10, 6};
            int [] addval = {-3, 78, 13, 22, 75};

            for(int i = 0; i <= 4; i ++){
                Bufi.storeData(addcom[i], addamt[i], addval[i]);
                System.out.println("Just added computer " + addcom[i] + " amount " + addamt[i] +
                        " to the Buffer int stored " + addval[i]);
                System.out.println("Buffer fill is: " + Bufi.getFill());
                System.out.flush();

                Thread.yield();
            }//end of for

        }//end of run
    }//end of Store

    //Now lets create the class for deleting the integers from the Buffer
    public static class Delete implements Runnable {
        //this is the Buffer to delete integers
        Buffer Bufj;

        //this is the constructor
        public Delete(Buffer delete){

            Bufj = delete;

        }

        public void run() {

            //sub 3 character strings from the Buffer
            String [] subcom = {"PB1", "FB2", "PB1", "MB3", "FB4"};
            //sub integers from the buffer
            int [] subamt = {2, 2, 3, 4, 0};

            for(int i = 0; i <= 4; i++){
                Bufj.clearData(subcom[i], subamt[i]);
                System.out.println("Just cleared computer " + subcom[i] + " with " + subamt[i] +
                        " from the Buffer amount");
                System.out.println("Buffer fill is: " + Bufj.getFill());
                System.out.flush();

                Thread.yield();
            }//end of for

        }//end of run

    }//end of Delete

}//end of ThreadsCoopMemoryBank
