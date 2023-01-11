package com.company;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

//This program allows for you to read student information from input txt files
//As the scanner reads each student record, an object is created for that student
//This program now creates an ArrayList Student for this Academic Class.
// As each student record is read in their object is created and stored in said ArrayList for the Academic Class.
//Sorts the student objects based on their subsequent percent test scores (high - low)
/*
Creates an ArrayList<Student> for this Academic Class. As each student record is read in their object is created
and stored in said ArrayList<Student> for the Academic Class.

This program is yet another adaptation of "StudentObjectsFromTxtFilePt3", however, it is now built to
calculate and store all data for each student record in an ArrayList rather than an Array

AddStudents2.txt file contains additional students and their test records.
This program also adds and demonstrates a method to delete students from the academicClass ArrayList
Objects
*/

public class TestProb2pt4 {
    public static void main(String[] args) throws Exception{
        //import and create file reader for txt file

        //read and print contents of file into ArrayList
        File file = new File("/Users/tristandavis/Desktop/StudentInput2.txt");
        Scanner input = new Scanner(file);
        ArrayList<Student> academicClass = new ArrayList<Student>();

        // read and load records from the txt file into a student object
        while ((input.hasNext())) {
            String stuID = input.next();
            String stuName = input.next();
            int sTest1 = input.nextInt();
            int sTest2 = input.nextInt();
            int sTest3 = input.nextInt();
            int nHours = input.nextInt();
            float GPA = input.nextFloat();

            Student students = new Student(stuID, stuName, sTest1, sTest2, sTest3, nHours, GPA);
            academicClass.add(students);

        }
        //Listing objects from the ArrayList Student including % score, letter grades,
        //academic Year, and New GPA
        System.out.println("Academic Class including student ID, test scores, letter grade, academic Year, "
                + "and New GPA:\r\n");
        for(int i = 0; i < academicClass.size(); i++) {
            System.out.println(academicClass.get(i));
        }

        //add new students from add student txt file
        System.out.println("---------------------------------------------");
        System.out.println("The following students have added the class: \r\n");
        AddStudent(academicClass);

        //add new students from add student txt file
        System.out.println("---------------------------------------------");
        System.out.println("New Academic Class including newly added students: \r\n");
        AddStudent(academicClass);

        SortLarge(academicClass);

        //print new list after sort; highest to lowest
        System.out.println("---------------------------------------------");
        System.out.println("The Academic Class is now sorted by grade, highest to lowest:\r\n");
        for(int i = 0; i < academicClass.size(); i++) {
            System.out.println(academicClass.get(i));
        }

        //listing students that have dropped the class
        System.out.println("---------------------------------------------");
        System.out.println("The following students have dropped the class:\r\n");
        DeleteStudent(academicClass,"45A3");
        DeleteStudent(academicClass,"42P4");
        System.out.println("---------------------------------------------");

        //print out a new list after dropped student records
        System.out.println("Academic Class after removing dropped students:\r\n ");
        for(int i = 0; i < academicClass.size(); i++) {
            System.out.println(academicClass.get(i));
        }

    }//end of driver class

    //Delete student method that deletes student based off matching Student ID
    public static void DeleteStudent(ArrayList<Student> academicClass , String StudentID){
        for(Student delstudent : academicClass){
            if(delstudent.getStudentID().equals(StudentID)){
                System.out.println(delstudent);
            }
        }
        academicClass.removeIf(objArr->objArr.getStudentID().equals(StudentID));

    } //end DeleteStudent

    //Add Student method to read in new student information, create its object, and store it to Student[]
    public static void AddStudent(ArrayList<Student> students) throws Exception{
        File file = new File("/Users/tristandavis/Desktop/AddStudents2.txt");
        Scanner input = new Scanner(file);
        //read and load records from the txt file into a student object
        while ((input.hasNext())) {
            String stuID = input.next();
            String stuName = input.next();
            int sTest1 = input.nextInt();
            int sTest2 = input.nextInt();
            int sTest3 = input.nextInt();
            int nHours = input.nextInt();
            float GPA = input.nextFloat();

            Student newStudents = new Student(stuID, stuName, sTest1, sTest2, sTest3, nHours, GPA);
            students.add(newStudents);


        }

        //prints out added student records
        for(int i = 0; i < students.size(); i++) {
            System.out.println(students.get(i));
        }
    }//end of AddStudent

    //SortLarge method to sort records based off highest percent score to lowest
    public static void SortLarge(ArrayList<Student> x){
        Student xsave;
        int isw = 1;
        while(isw == 1){
            isw = 0;
            for (int i = 0; i < x.size() - 1; i++){
                switch (x.get(i).compareTo(x.get(i + 1))) {
                    case 1: // the objects are in the right order
                        break;
                    case -1: // the objects are in the wrong order
                        xsave = x.get(i);
                        x.set(i, x.get(i + 1));
                        x.set(i + 1, xsave);
                        isw = 1;
                        break;
                    default: // objects are equal so no change
                }
            }
        }
    } // end of SortLarge

}//end of TestProb2pt4

//create class Student and implement comparable based off Student
class Student implements Comparable<Student> {

    protected String StudentID;
    protected int ScoreTest1;
    protected int ScoreTest2;
    protected int ScoreTest3;
    protected String StudentName;
    protected int testPercent;
    protected String letterGrade;
    protected int numHour;
    protected float GPA;
    protected float newGPA;
    protected String classYr;
    protected double classGP;

    //Constructor for class Student
    public Student(String StudentID, String StudentName, int ScoreTest1, int ScoreTest2, int ScoreTest3, int numHour, float GPA){
        this.StudentID = StudentID;
        this.StudentName = StudentName;
        this.ScoreTest1 = ScoreTest1;
        this.ScoreTest2 = ScoreTest2;
        this.ScoreTest3 = ScoreTest3;
        this.numHour = numHour;
        this.GPA = GPA;
        percentTestScore();
        calcLetterGrade();
        calcNewGPA();
        studentYear();
    }
    //calculate average test score
    public void percentTestScore(){
        testPercent = (int) ((ScoreTest1 + ScoreTest2 + ScoreTest3) / 3.0);
    }
    //calculate letter grade in class
    public void calcLetterGrade(){
        if (testPercent >= 90){

            letterGrade = "A";

        }
        else if(testPercent >= 80){

            letterGrade = "B";

        }
        else if(testPercent >= 70){

            letterGrade = "C";

        }
        else if(testPercent >= 60 ){

            letterGrade = "D";

        }
        else{
            letterGrade = "F";
        }
    }

    //calculate new GPA using the following equation:
    //newGPA = (float) ((GPA * numHour + 2.0 * classGP) / numHour + 2.0);
    //Note that the current class hours are 2 and Class GPs are (4-A, 3-B, 2-C, 1-D, F-0)

    public void calcNewGPA(){
        switch (letterGrade) {
            case "A":

                classGP = 4;

                break;
            case "B":

                classGP = 3;

                break;
            case "C":

                classGP = 2;

                break;
            case "D":

                classGP = 1;

                break;
            default:
                classGP = 0;
                break;
        }
        newGPA = (float) ((GPA * numHour + 2.0 * classGP) / (numHour + 2.0));
    }

    //determine Student academic year based off number of hours
    public void studentYear(){
        if (numHour < 0) {
            classYr = "New Student";
        } else if (numHour < 30) {
            classYr = "FR";
        } else if (numHour < 60) {
            classYr = "SO";
        } else if (numHour < 90) {
            classYr = "JR";
        } else {
            classYr = "SR";
        }

    }

    public String getStudentID(){
        return StudentID;
    }
    public void setStudentID(String StudentID){
        this.StudentID = StudentID;
    }

    public String getStudentName(){
        return StudentName;
    }

    public int getScoreTest1(){
        return ScoreTest1;
    }

    public int getScoreTest2(){
        return ScoreTest2;
    }

    public int getScoreTest3(){
        return ScoreTest3;
    }

    public void setTestPercent(int testPercent){
        this.testPercent = testPercent;
    }
    public int getTestPercent(){
        return testPercent;
    }

    public void setLetterGrade(String letterGrade){
        this.letterGrade = letterGrade;
    }

    public String getLetterGrade(){
        return letterGrade;
    }

    public int getNumHour(){
        return numHour;
    }

    public float getGPA(){
        return GPA;
    }

    public float getNewGPA(){
        return newGPA;
    }

    public void setNewGPA(float newGPA){
        this.newGPA = newGPA;
    }

    public String getClassYr(){
        return classYr;
    }

    public void setClassYr(String classYr){
        this.classYr = classYr;
    }

    //override method to print out Students with specific format
    @Override
    public String toString() {
        String result;
        result = String.format("Student ID: %s\r\n Student Name: %s\r\n Test 1: %d\r\n Test 2: %d\r\n Test 3: %d\r\n" +
                        " Percent Grade: %d\r\n Letter Grade: %s\r\n Year: %s\r\n New GPA: %f\r\n" + "---------------------------" + "\r\n",
                StudentID, StudentName, ScoreTest1, ScoreTest2, ScoreTest3, testPercent, letterGrade, classYr, newGPA);
        return result;
    } // end of toString

    //compareTo function to compare students based on their average test scores
    public int compareTo(Student o){
        if (this.testPercent > o.testPercent) {
            return 1;
        } else if (this.testPercent < o.testPercent) {
            return -1;
        } else {
            return 0;
        }
    } // end of compareTo

}//end of Student

