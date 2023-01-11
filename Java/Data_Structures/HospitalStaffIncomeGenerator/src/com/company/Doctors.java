package com.company;
//create class Doctors to determine employee special info, calculate bonuses, Federal/States taxes,
//total salary,and cost of living
public class Doctors extends StaffCategory {
    //insert this to lessen confusion
    protected int numPatients = EmpSpecInfo;

    public Doctors(String EmpName, String EmpSSN, String EmpRace, int EmpSpecInfo) {
        super(EmpName, EmpSSN, EmpSpecInfo, EmpRace);
        calcBaseSalary();
        calcCOLA();
    }

    //method to calculate Doctor BaseSalary
    public void calcBaseSalary(){
        BaseSalary = 155000;
        bonus = BaseSalary * .0025 * numPatients;
    }

    //method to calculate cost of living based of BaseSalary
    public void calcCOLA(){
        COLA = BaseSalary * COLA;
    }
    //print method to print employee object information
    public void print(){
        System.out.println("Dr. " + EmpName);
        System.out.println("Race: " + EmpRace);
        System.out.println("SSN: " + EmpSSN);
        System.out.println("Base Salary: " + BaseSalary);
        System.out.println("COLA: " + COLA);
        System.out.println("Patient Adjustment: " + bonus);
        System.out.println("Total Salary: " + Salary);
        System.out.println("Federal Tax: " + FedTax);
        System.out.println("Mass. State Tax: " + StateTax);
        System.out.println("-----------------------");
    }
}//end class Doctors
