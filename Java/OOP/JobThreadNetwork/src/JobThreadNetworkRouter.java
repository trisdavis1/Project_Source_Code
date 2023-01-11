
/*
This program is an example of creating threads that are synced with a Lock condition but in a "race condition".
The program creates 9 threads that calculate a Jobs data/print connection charges through a Router object.
The threads are synced through the addition of a thread lock Lock.
*/

import java.io.*;
import java.io.IOException;
import javax.swing.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.util.NoSuchElementException;
import java.lang.*;

//this allows the threads to be created as objects from the Thread class
//this allows for the creation of a thread pool that can all be launched by
//one executor Executor
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class JobThreadNetworkRouter {

    public static void main(String[] args)throws Exception{

        //create print writer for the tasks
        PrintWriter output;

        output = new PrintWriter(new File("ThreadNetworkRouterOut.txt"));

        //create thread pool
        ExecutorService executor = Executors.newFixedThreadPool(9);

        //now create and launch 9 Job threads
        //create your router
        Router cRouter = new Router(output);
        //execute the Production branch
        executor.execute(new Job(cRouter, "PB", 1, 'D', 60000));
        executor.execute(new Job(cRouter, "PB", 2, 'D', 75000));
        executor.execute(new Job(cRouter, "PB", 3, 'P', 100000));
        //execute the Financial branch
        executor.execute(new Job(cRouter, "FB", 1, 'P', 30000));
        executor.execute(new Job(cRouter, "FB", 2, 'D', 150000));
        executor.execute(new Job(cRouter, "FB", 3, 'P', 89000));
        //execute the marketing branch
        executor.execute(new Job(cRouter, "MB", 1, 'P', 200000));
        executor.execute(new Job(cRouter, "MB", 2, 'D', 140000));
        executor.execute(new Job(cRouter, "MB", 3, 'P', 135000));

        //print launched threads
        for(int i = 1; i <= 9; i++) {
            System.out.println("Thread " + i + " created and launched");
            System.out.flush();
            output.println("Thread " + i + " created and launched");
            output.flush();

        }
        //shutdown the executor
        executor.shutdown();

        //now let all the threads shutdown and wait till all tasks are finished
        while(!executor.isTerminated());
            System.out.println("What are the charges? " + cRouter.getsumCharges());
            System.out.flush();
            output.println("What are the charges? " + cRouter.getsumCharges());
            output.flush();

    }//end main
}// end of JobThreadNetworkRouter

class Router {

    private double charges; //total charges for num characters to process
    double sumcharges = 0.0;
    private static Lock lock = new ReentrantLock();
    //Job jobs;
    PrintWriter outp;

    //create router constructor
    public Router(PrintWriter out1){

        charges = 0.0;
        outp = out1;
    }//end router constructor

    public double getsumCharges(){
        return sumcharges;
    }

    //this is the function for the router to calculate job charges
    public void calcCharges(String branch, char chartype, int numchar){
        lock.lock(); //acquire lock for charges

        try {
            //switch statement to calculate charges by branch/connection
            switch (branch) {
                case "PB":
                    if (chartype == 'P')
                        charges = 0.007 * numchar;
                    else
                        charges = 0.008 * numchar;
                    break;
                case "FB":
                    if (chartype == 'P')
                        charges = 0.009 * numchar;
                    else
                        charges = 0.007 * numchar;
                    break;
                case "MB":
                    if (chartype == 'P')
                        charges = 0.0095 * numchar;
                    else
                        charges = 0.0082 * numchar;
                    break;
            }
            Thread.sleep(5); //This sleep is bad news if we are not locked

        }catch (InterruptedException ex){

        }
        //calculate total charges amongst branches
        sumcharges += charges;
        //calculate and print each jobs charges
        System.out.println("Branch " + branch + " charged " + charges);
        System.out.flush();
        outp.println("Branch " + branch + " charged " + charges);
        outp.flush();

        //this releases the lock so we avoid race conditions
        lock.unlock();

    }//end of calcCharges
}//end class Router

//now lets create a class for the Job task object
class Job implements Runnable{

    //this is the job to determine charges
    Router router; //router object
    private String branch; //job branch
    private int port; //computer port
    private char chartype; //print/data type connection
    private int numchar; // number characters to process

    //create job constructor
    public Job(Router x, String b, int cp, char ctype, int numc){

            router = x;
            branch = b;
            port = cp;
            chartype = ctype;
            numchar = numc;
    }//end Job constructor

    public void run(){
        //calc charges for each job branch
        router.calcCharges(branch, chartype, numchar);

    }//end of job run() task
}//end class Job

