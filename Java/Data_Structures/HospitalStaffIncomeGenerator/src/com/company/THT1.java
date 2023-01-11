package com.company;

//Using the class Structure (Employees->WLFUTeam->Boston(Regional City)->StaffCategory->(Admin,
//Docs, Nurses, and MedSupport). This program creates a set of employee objects and stores them in an array BostEmp[]
//for their regional hospital area Boston.
//Using the array, BostEmp[], it calculates the salary and taxes for each individual, and then sorts them based on salary
//(smaller to larger) and prints the newly sorted objects
//Developing and using a Generic Manager (WLFUManager<T>), this program stores the employee objects in an ArrayList
//and then sorts them based on Salary.
public class THT1 {

    public static void main(String[] args) {

        WLFUManager<Boston> BostEmps = new WLFUManager<Boston>();
        int i;

        //create employee objects
        Doctors doc1 = new Doctors("I.M. Bones", "455657890", "AA",100);
        Nurses nurse = new Nurses("U.R. Temp", "789302345", "CA", 3);
        Doctors doc2 = new Doctors("D.V.M. Frakes", "786456712", "CA", 120);
        Administration admin = new Administration("I.M. Boss", "543126787", "HS", 1);

        //store employee objects in array type Boston
        Boston[] BostEmp = new Boston[4];
        BostEmp[0] = doc1;
        BostEmp[1] = nurse;
        BostEmp[2] = doc2;
        BostEmp[3] = admin;

        //calculate and print total salary, and state/federal taxes
        System.out.println("WLFU Team employees:");
        System.out.println("-----------------------");
        doc1.calcSalary();
        doc1.calcStateTax();
        doc1.calcFedTax();
        doc1.print();

        nurse.calcSalary();
        nurse.calcStateTax();
        nurse.calcFedTax();
        nurse.print();

        doc2.calcSalary();
        doc2.calcStateTax();
        doc2.calcFedTax();
        doc2.print();

        admin.calcSalary();
        admin.calcStateTax();
        admin.calcFedTax();
        admin.print();

        //print array after sorted, smallest to largest by salary
        sortSalary(BostEmp);
        System.out.println("WLFU Team after sort:");
        System.out.println("-----------------------");
        for(i = 0; i <= BostEmp.length - 1; i++){
            BostEmp[i].print();
        }

        //add and store the student records using Generic Manager
        System.out.println("Employee objects added using WLFUManager...");
        System.out.println("-----------------------");
        for(i = 0; i < BostEmp.length; i++) {
            BostEmps.addvalue(BostEmp[i]);
        }

        //prints a specified element stored in the generic Arraylist
        System.out.println("Using the WLFUManager to print the 3rd element:");
        System.out.println("-----------------------");
        BostEmps.getvalue(3).print();

        //Sort using the WLFUManager class and its generic sort
        System.out.println("Using the WLFUManager to sort the employee objects:");
        System.out.println("-----------------------");

        for(i = 0; i < BostEmp.length; i++){
            BostEmps.sortTeam();
            BostEmp[i].print();
        }

        System.out.println("Proving the WLFUManager is generic:");
        System.out.println("-----------------------");

        //lets prove that the generic manager works by trying some integers
        WLFUManager<Integer> myInts = new WLFUManager<Integer>();

        myInts.addvalue(17);
        myInts.addvalue(21);
        myInts.addvalue(-34);
        myInts.addvalue(12);
        myInts.addvalue(6);
        myInts.addvalue(78);
        //sort integers using WLFUManager
        myInts.sortTeam();

        int num = myInts.mycount;
        for(i = 0; i < num; i++){
            System.out.println(myInts.getvalue(i));
        }

    }//end of driver class
    //sortSalary method to sort employee objects (smallest to largest)
    public static void sortSalary(Boston[] BostEmp){

        Boston xsave;

        int isw = 1;
        while(isw == 1){
            isw = 0;
            for(int i = 0; i < BostEmp.length - 1; i++){
                switch (BostEmp[i + 1].compareTo(BostEmp[i])){

                    case 1:

                        xsave = BostEmp[i];
                        BostEmp[i] = BostEmp[i + 1];
                        BostEmp[i + 1] = xsave;
                        isw = 1;
                        break;

                    case - 1:
                        break;

                    default:
                }
            }
        }


    }//end of sortSalary
}//end of main
