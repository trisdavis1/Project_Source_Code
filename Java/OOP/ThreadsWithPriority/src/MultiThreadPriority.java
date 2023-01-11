
/*This program is a multithreaded JAVA Program Example.  The program creates four threads each printing
 something different. One thread prints a GUI Poll, two threads print a certain number of characters,
 and the last prints a certain number of lines with a specified number of characters on each line.
 The threads are given priorities at the beginning of the run.
 The GUI Poll thread has lowest priority. These threads do yield after
 certain number of prints*/


import java.io.*;
import java.io.IOException;
import javax.swing.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.util.NoSuchElementException;
import java.lang.*;


public class MultiThreadPriority {

    public static void main(String[] args)throws Exception{

        //create a PrintWriter
        PrintWriter output;

        output = new PrintWriter(new File("MultiThreadPriority1.txt"));

        //First we must create tasks. We need to pass these tasks to the threads for running.
        //GUI processor object
        Runnable GUI1 = new GUI( 300, output);
        Runnable WPx = new WPx('A', 50 * 10 * 2, output);
        Runnable DSx = new DSx(2500, output);
        Runnable Printer = new PrinterD(3600, output);

        //now create the Threads
        Thread thread1 = new Thread(GUI1);
        Thread thread2 = new Thread(WPx);
        Thread thread3 = new Thread(DSx);
        Thread thread4 = new Thread(Printer);

        //Now give them priorities
        thread1.setPriority(Thread.MIN_PRIORITY); /*GUI runs the entire period of sim.
                                                        Everything must shut down before the GUI.*/
        thread2.setPriority(Thread.MAX_PRIORITY); /*WP must be fast and responsive when given a load to process.
                                                        The faster it finishes, the happier the user is.*/
        thread3.setPriority(Thread.NORM_PRIORITY); /*Data Storage Device (DS) must finish before the GUI.
                                                        Is normally slower than the WP.*/
        thread4.setPriority(3); //Printer can finish anytime.

        //Now start the Threads
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        output.flush();
    }

}
//Now create the GUI class
class GUI implements Runnable{

    //private String strToPrint; //this is the "gui poll" string to print
    private int times; //number of times to repeat the string print
    private PrintWriter outputf;

    //Now create the GUI constructor
    public GUI(int t, PrintWriter outputf1){

        //strToPrint = stp;
        times = t;
        outputf = outputf1;

    }
    /* every task object must have a run() method.  This overrides the system run() method
       and tells the system what task to perform */
    public void run(){

        for(int i = 1; i <= times; i++){
            outputf.print("GUI POLL ");
            System.out.println("GUI POLL");

            if(i % 2 == 0)
                outputf.println();

            Thread.yield();
        }

        outputf.flush();
    }//end of GUI run()

}//end of GUI class

//Now lets create the WordProcessor class
class WPx implements Runnable{

    private int numToPros; //this represents the number of characters to process
    private PrintWriter outputf; //this is the output txt file from this thread
    private char xC; //this the character to process
    private int numcharpro; //this is the number of 10 character groups to process
    private int fchar; //this is the number of the beginning/first character on this process group
    private int lchar; // this is the number of the last character in this process group

    public WPx(char ctp, int num, PrintWriter outputf1){

        numToPros = num;
        xC = ctp;
        outputf = outputf1;

        //here we calculate how many 10 character groups to process
        numcharpro = numToPros / 10;

        if((numToPros % 10) != 0){
            numcharpro++;  //note that this tells us that if we have any other
                            //number of characters that are not a multiple of 10 we must add a line
        }
    }//end of constructor

    //now task the run() method
    /*NOTE that System.out.print is a system function that is synced with the
    processing thread so no flush is necessary to assure the output buffer is
    empty. BUT Printing to a text file from a PrintWriter is not synced.
    So IT IS NECESSARY TO ALWAYS FLUSH THE PrintWriter BUFFER after each print to
    assure it gets on the text file before the thread loses control.*/

    public void run(){

        for(int numgp = 1; numgp <= numcharpro; numgp++) {
            //this is the group loop for the WP.
            //The WP Processes numToPros in groups of 10 char at a time

            fchar = (numgp - 1) * 10 + 1;  //This is the first character to
            // be processed in this group
            lchar = fchar + 9;  //This is the last character to
            // be processed in this group
            if ((numgp == numcharpro) && (numToPros % 10) != 0)
                lchar = numToPros;

            for (int icr = fchar; icr <= lchar; icr++)
                outputf.print(" WP" + icr + " ");
                outputf.println();
                //System.out.print(" WP" + icr + " ");
                //System.out.println();
                Thread.yield();

        }//end of this  processing group

        outputf.flush();

    }//end of  WPx run()
}//end of class WPx

//Create the Data Storage class
class DSx implements Runnable {

    private int charNum; //this is the number of characters to retrieve
    //private int times;  //this is the number of times to print
    //private char ch; //this is the character to retrieve
    private PrintWriter outputf;
    private int numctp; //this is the number of 20 character groups to retrieve
    private int fchar; // this is the number of the beginning character in this retrieval group
    private int lchar;  //this is the number of the last character in this retrieval group

    public DSx(int numch, PrintWriter outputf1) {

        charNum = numch;
        outputf = outputf1;

        //calculate how many 20 character groups to retrieve
        numctp = charNum / 20;

        if((charNum % 20) != 0){     //note that this tells us that if we have any other
           numctp++;                 //number of characters that are not a multiple of 20 we must add a line
        }

    } //end of constructor

     //Now task the DS run method
    public void run(){

        for(int ctr = 1; ctr <= numctp; ctr++){

            fchar = (ctr -1) * 20 + 1;  //first character to be retrieved

            lchar = fchar + 19;  //last character to be retrieved

            if((ctr == numctp) && (charNum % 20) != 0)
                lchar = charNum;
                //outputf.print("\n");
                //System.out.print("\n");

            for(int icr = fchar; icr <= lchar; icr++) {
                outputf.print(" DS" + icr + " ");
                //System.out.print(" DS" + icr + " ");

                //System.out.println();
                if (icr % 20 == 0)
                    outputf.println();
                    //System.out.println();
                    Thread.yield();
            }
        }  //end of this retrieval group

        outputf.flush(); //flush the PrintWriter

    }  //end of DSx run()
} //end of class DSx

//Now create the Printer Device class
class PrinterD implements Runnable {

    private int printNum; //this is the number of characters to be printed
    private PrintWriter outputf; //this is the print writer output
    private int numprtc; //this is the number of 60 character groups to print
    private int numltp;  //this is the number of lines to print
    private int fchar; //this is the number of the first character in this print group
    private int lchar;  //this is the number of the last character in this print group

    public PrinterD(int numprt, PrintWriter outputf1) {

        printNum = numprt;
        outputf = outputf1;

        //calculate how many 60 character groups to print
        numprtc = printNum / 60;
        //calculate how many 6 line groups to print
        numltp = printNum / 6;

        if((printNum % 60) != 0){
            numprtc++;             //note that this tells us that if we have any other
        }                    //number of characters that are not a multiple of 60 we must add a line

        if((printNum % 6) != 0) {
            numltp++;         //note that this tells us that if we have any other
        }               //number of characters that are not a multiple of 20 we must add a line

    }  //end of constructor

    //Now task the printer run method
    public void run(){

        for (int pctp = 1; pctp <= numprtc; pctp++) {

            fchar = (pctp - 1) * 60 + 1;  //first character to printed

            lchar = fchar + 59;  //last character to be printed

            numltp = printNum / 6; //number of lines to be printed

            if ((pctp == numprtc) && (printNum % 60) != 0)
                lchar = printNum;
                outputf.println();
                //System.out.println();

            //this tells the processor to skip a line and yield if
            //the amount of characters to print is a multiple of 6
            if(pctp % 6 == 0)
                outputf.println();
                //System.out.println();
                Thread.yield();
            //tells us the print line # if number lines to print are multiples of 6
            if (numltp % 6 == 0)
                outputf.print("Print Line " + pctp + ": ");
                //System.out.print("Print Line " + pctp + ": ");
                //Thread.yield();

            for (int icr = fchar; icr <= lchar; icr++)
                if (icr % 60 == 0)
                    outputf.print(fchar + "-" + lchar + " ");
                    //System.out.print(fchar + "-" + lchar + " ");
                    Thread.yield();

        }
         outputf.flush();  //flush the PrintWriter

    }  //end of PrinterD run()
} //end of class PrinterD