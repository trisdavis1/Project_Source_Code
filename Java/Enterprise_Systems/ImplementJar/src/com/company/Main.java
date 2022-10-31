package com.company;

import CSC346Utility.Logger;
import java.io.*;
import java.net.*;

/**
 *  Demonstrates the implementation of a jar file Logger
 *  @Name: Tristan Davis
 *  @Course: CSC346 Enterprise Systems in Java
 *  @Instructor: Dr. Noyneart
 *  @Due: Jan. 31st, '22
 */

public class Main {
    //initializes Logger access
    static Logger log = new Logger("Main");

    public static void main(String[] args) {
        //logging the start of the program
        log.info("Starting");
        //printing that read has started
        System.out.println("\nPrinting lines from URL:\n");

        //initializes the url address to string
        final String urlAddress = "https://wttr.in/64507?u";

        //calls readURl() method to read and print contents of the url as a string
        ///includes all Http and CSS bulk
        readURL(urlAddress);

        System.out.println("\nDone!\n");  //print to show program ran accordingly
        log.done(); //logs when the program is complete

    }//end of driver main

    private static void readURL(String address){
        //try block to read and store string contents read from the URL
        try{
            //creates and associates the url address to read
            URL logUrl = new URL(address);

            //input stream that calls the Url's openStream() method and receives contents read from the url
            InputStreamReader inStream = new InputStreamReader(logUrl.openStream());
            //opens a reader on InputStream to read the url stream
            BufferedReader input = new BufferedReader(inStream);
            log.info("Connected");  //log the connection to the url

            String line;//initializes the string line

            while ((line = input.readLine()) != null) { //while line is not null read the line
                System.out.println(line);//prints the line read from the BufferedReader

            }//end while loop
            input.close(); //close the BufferedReader

        //single generic catch() method to call all exceptions
        //uses getMessage to print a generic error message and then aborts processing
        } catch (Exception e){
            log.exception(e); //logs exception in logger
            System.err.println(e.getMessage()); //prints generic error message
            System.exit(1); //exit code to signal error
        }//end of try/catch block
    }//end of readUrl
}//end of class main
