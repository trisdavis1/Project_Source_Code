import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * todo: Implements a TreeMap and reads a text file from a URL on the command line
 *
 *  Reads file name from command line and uses a TreeMap to
 *  calculate the number of times each word is used in a txt file.
 *
 * @author: Tristan Davis
 * @since: March 2022
 */

public class App {
    public static void main(String[] args){
        //prints out the Url in which we are reading from and adds the command line argument at the end
        System.out.println("\nReading from http://woz.cs.missouriwestern.edu/data/docs/" + args[0]);

        //initializes the command line argument with a string fileName
        String fileName = "No file name specified";
        TreeMap wordFrequency; //initializes a raw use of the parameterized class TreeMap

        if(args.length > 0){  //if args[0] is empty then print an error message and exit
            fileName = args[0];
            wordFrequency = readFile(fileName);
            printWords(wordFrequency);
        }else{
            System.err.println("No file name specified in args[0].");
            System.exit(1);
        }

        System.out.println("\nDone!");
    }//end of main()

    //print method that prints the TreeMap based on its keys and values
    public static void printWords(Map map){
        System.out.printf("\n-----------There are %d words in the Map-------------\n", map.size());
        for(var key : map.keySet()) {
            var value = map.get(key);
            System.out.printf("[%s]\t%s\n", key, value);
        }
    }//end of printWords()

    /* read file method that reads words from the command line, puts them in a TreeMap,
    and counts how many times they occur */
    public static TreeMap readFile(String filename) {
        //initializes a TreeMap object of type <String, Integer>
        TreeMap<String, Integer> wordFrequency = new TreeMap<>();
        Scanner input;

        //try/catch block to read file and throw an exception
        try{
            //implements a simple text scanner to read fileName provided on command line
            input = new Scanner(new File(filename));

            while(input.hasNextLine()) {

                String line = input.nextLine();
                String[] words = line.split("\\W"); //splits line based off any word character

                //forEach method to put words into TreeMap and count their occurrence
                for(String word : words){

                    word = word.toLowerCase(); //converts each word to lowercase for simplicity in output

                    if(!wordFrequency.containsKey(word)){
                        wordFrequency.put(word, 1);
                    }else {
                        wordFrequency.put(word, wordFrequency.get(word) + 1);
                    }

                }//end of forEach()
            }
            input.close();  //close the Scanner

        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }//end of try/catch block

        return wordFrequency; //returns the finalized TreeMap
    }//end of readFile()
}//end of class App
