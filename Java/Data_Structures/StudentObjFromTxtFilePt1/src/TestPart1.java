/*This program demonstrates reading a student record from a .txt file and store that record in an
Student object array named "academicClass" */
//As each record is read, an object is created for each student
//Student names are printed to show that they exist in the Student array
/*Includes a method where each object calculates the percentage test scores and letter grade
for each student and stores them in an integer. However, this method is not called in this program*/
//Creates an array Student[] for an academic class in which the student objects are stored
import java.io.*;
import java.util.*;

public class TestPart1 {
    public static Student[] academicClass = new Student[11];

    public static void main(String[] args) throws Exception {
        //import and create file reader for txt file
        // FileReader file = new FileReader("StudentsInput.txt");

        //print contents of read file
        File file = new File("/Users/tristandavis/IdeaProjects/StudentObjectsFromTxtFilePt1/src/StudentsInput.txt");
        Scanner input = null;
        input = new Scanner(file);

        int i = 0;

        while ((input.hasNext())) {
            String stuID = input.next();
            String stuName = input.next();
            int stest1 = input.nextInt();
            int stest2 = input.nextInt();
            int stest3 = input.nextInt();

            Student students = new Student(stuID, stuName, stest1, stest2, stest3);
            academicClass[i] = students;
            i++;

        }
        for(int k = 0; k <= 7; k++) {
            System.out.println(academicClass[k].getStudentName());
        }

    }
}//end of TestPart1


