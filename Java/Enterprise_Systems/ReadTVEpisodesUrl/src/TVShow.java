import java.io.*;
import java.net.*;
import java.util.*;

/** 
*   Demonstrates Reading from a URL and Exceptions: Hwk00-Review
*   @Name: Tristan Davis
*   @Course: CSC346 Enterprise Systems in Java
*   @Instructor: Dr. Noyneart
*   @Due: Jan. 25th, '22
*/

public class TVShow {
    public static void main(String[] args){
        //initialize the url address to a string
        final String ADDRESS = "https://raw.githubusercontent.com/noynaert/csc346handouts/main/dataFiles/episodes.txt";

        //creates arraylist for lines read and episode object
        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<Episode> episodes = new ArrayList<Episode>();

        //calls readAddress() method to read contents of url and store them in arrayList objects 
        readAddress(ADDRESS, lines, episodes);
        //this uses the collections.sort() method for ArrayList and sorts the list based on title
        Collections.sort(episodes);
        //calls printLines() method and prints contents of ArrayList episodes
        printLines(episodes);
        System.out.println("\nDone!\n"); //println to show program ran successfully
    }//end of main

    //iterates and prints the contents stored in the ArrayList episode
    public static void printLines(ArrayList<Episode> episodes) {
        int n = episodes.size();
        
        System.out.printf("\nPrinting %d lines:\n\n", n);
        for (int i = 0; i < episodes.size(); i++) {
            //gets string from ArrayList episodes and prints them
            System.out.println(episodes.get(i));
        }
    }//end of printLines

    //reads contents from a url address and stores them in ArrayList objects
    private static void readAddress(String address, ArrayList<String> lines, ArrayList<Episode> episodes) {
        //try block to read and store string contents read from a URL
        try {
            //creates the url address
            URL url = new URL(address);
            //input stream that calls the url's openStream() method to get a stream from which contents of the url are read
            InputStreamReader inStream = new InputStreamReader(url.openStream());
            BufferedReader input = new BufferedReader(inStream);  /*opens buffered reader on input stream to
            //reads the first line and discards it                                         read the open url stream */
            input.readLine();      

            String line = null; //initializes the String line to null
            while ((line = input.readLine()) != null){   //while line is not null read the line
                line = line.trim();
                //this creates an episode fields string array 
                //uses the split() method to seperate the line into specified fields 
                String[] epFields = line.split(":");  //line is colon delimited
                
                //add string to arraylist lines
                lines.add(line); 
                //this only reads lines with 4 fields
                //initializes variables for each episode field and stores them in an Episode object
                if(epFields.length == 4){

                    String epNum = epFields[0]; //episode number field 
                    String title = epFields[1]; //title field
                    String rDate = epFields[2]; //releasedate field
                    int rtime; //runtime field

                    //try block to parse the rtime string to an integer 
                    try{
                        rtime = Integer.parseInt(epFields[3]);
                    } catch(NumberFormatException e){
                        rtime = 0;
                    }// end try/catch block

                    //this creates a new episode object 
                    Episode ep = new Episode(epNum, title, rDate, rtime);
                    episodes.add(ep); //adds episode object to ArrayList episodes
                }
            }
            input.close(); //close the BufferedReader input
        //single generic catch() method to catch all exceptions
        //uses getMessage() to print a generic error message and then aborts processing
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }// end of try/catch block
    }//end of readAddress
}//end of TVShow

//class Episode that implements the Comparable method based off Episode
class Episode implements Comparable<Episode> {
    //initialize variables for class Episode
    protected String epNum;
    protected String title;
    protected String releaseDate;
    protected int runtime;

    //this creates the constructor for class Episode
    public Episode(String epNum, String title, String releaseDate, int runtime){
        setEpNum(epNum);
        setTitle(title);
        setReleaseDate(releaseDate);
        setRuntime(runtime);
    }//end of Episode constructor

    //getters and setters for class Episode
    public String getEpNum(){
        return epNum;
    }
    public void setEpNum(String epNum){
        this.epNum = epNum;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getReleaseDate(){
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }
    public int getRuntime(){
        return runtime;
    }
    public void setRuntime(int runtime){
        this.runtime = runtime;
    } //end of getters and setters for class Episode

    //override toString method to print episodes with specific format
    @Override
    public String toString() {
        String string;
        string = String.format("%s %s %s %d ", epNum, title, releaseDate, runtime);
        return string;
     } // end of toString

    //implementation of compareTo function to compare Episodes based on their title
    @Override
    public int compareTo(Episode o){
        int result = this.title.compareTo(o.title);
        if(result == 0){
            result = this.title.compareTo(o.title);
        }
        if(result == 0){
            result = this.epNum.compareTo(o.epNum);
        }
        return result;
    }//end of compareTo
}//end of class Episode