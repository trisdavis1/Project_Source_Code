import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.Collections;

/**
 * todo: Implement maven, log4j, jsoup, and the POJO "Course"
 *
 * @author: Tristan Davis
 * @since: February 2022
 */

public class App {
    //implements log4j library to log significant events throughout the program
    private static final Logger Log = LogManager.getLogger(App.class);

    public static void main(String[] args){

        //logging the start of the program
        Log.info("INFO: Starting the program");
        //initializes the url address
        String address = "http://woz.cs.missouriwestern.edu/csc346/hmwk02.html";

        ArrayList<Course> courses = new ArrayList<Course>();  //creates ArrayList for Course object

        readAddress(address, courses); //call to readAddress() to connect and read courses
        Log.trace("Trace: Finished in readAddress, we have Read the URL");
        Collections.sort(courses); //call to the collection's method to sort the list of courses
        Log.trace("Trace: Finished in sort, courses have been sorted");
        printCourses(courses);  //call to printCourses() to print courses stored in the ArrayList courses
        Log.trace("Trace: Finished in printCourse, courses have been printed");

        System.out.println("\nDone!");
        Log.info("INFO: Done");
    }//end of main

    //iterates and prints the contents stored in the ArrayList<Course>
    public static void printCourses(ArrayList<Course> courses){
        int n = courses.size(); // sets n to the number of courses in the list
        Log.info("INFO: There are " + courses.size() + " courses");
        System.out.printf("\nPrinting %d Courses:\n\n", n); //printing the # of courses 'n' in the list

        for(int i = 0; i < courses.size(); i++) {
            //gets string from ArrayList episodes and prints them
            System.out.println(courses.get(i));
        }
    }//end of printCourses

    private static void readAddress(String url, ArrayList<Course> courses) {

        //try block to read and store string contents read from the url address
        try {
            Document doc = Jsoup.connect(url).get(); //uses jsoup library to connect to url
            Log.info("INFO: Successfully connected to the URL");
            Elements lines = doc.select("tr"); //gets the rows

            for(Element line : lines) {  //each line should be a singular course
                System.out.println(line.select("td"));
                Elements cells = line.select("td");  // the "td" elements are cells
                Log.debug("Cells in line: " + cells.size()); //when logged, there should be 4 cells per line

                //this if statement pulls the text from the cells in the line
                if(cells.size() == 4) { //checking the line for 4 cells
                    //pulling text from all 4 cells
                    String number = cells.get(0).text();  //course # cell
                    String title = cells.get(1).text();  //course title cell
                    String instructor = cells.get(2).text(); //course instructor cell
                    int hrs;  //course credits/hrs cell
                    //try/catch to parse the integer. If it is not an integer or is empty cell it returns 0
                    try {
                        hrs = Integer.parseInt(cells.get(3).text());
                    } catch(NumberFormatException e) {
                        hrs = 0;
                    }//end try/catch block

                    //creates new episode object
                    Course course = new Course(number, title, instructor, hrs);
                    courses.add(course); //adds the course object to ArrayList<Course>
                    Log.debug(course);
                }
            }
        //generic catch() method to catch all exceptions and print an error message
        } catch (Exception e) {
            Log.error("Could not connect... " + e.getMessage());
        }//end try/catch block
    }//end of readAddress
}//end of class App
