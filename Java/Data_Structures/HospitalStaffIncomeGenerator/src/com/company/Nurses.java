package com.company;
//create class Nurses to determine employee special info, calculate bonuses, Federal/States taxes,
//total salary,and cost of living
public class Nurses extends StaffCategory {

    public Nurses(String EmpName, String EmpSSN, String EmpRace, int EmpSpecInfo) {
        super(EmpName, EmpSSN, EmpSpecInfo, EmpRace);
        calcStaffCategory();
        calcBaseSalary();
        calcCOLA();
    }
    //method to calculate staff category based of special info
    public void calcStaffCategory(){
        if(EmpSpecInfo == 1){
            StaffCat = "Clinical Nurse";
        }else if(EmpSpecInfo == 2){
            StaffCat = "Floor Nurse";
        }else if(EmpSpecInfo == 3){
            StaffCat = "Administrative Nurse";
        }
    }
    //method to calculate Basesalary and bonuses based off staff category
    public void calcBaseSalary(){

        if(StaffCat.equals("Clinical Nurse")){
            BaseSalary = 65000;
            bonus = BaseSalary * 0.1;
        }else if(StaffCat.equals("Floor Nurse")){
            BaseSalary = 65000;
            bonus = BaseSalary * 0.15;
        }else if(StaffCat.equals("Administrative Nurse")){
            BaseSalary = 65000;
            bonus = BaseSalary * .2;
        }

    }
    //method to calculate cost of living based of BaseSalary
    public void calcCOLA(){
        COLA = BaseSalary * COLA;
    }
    //print method to print employee object information
    public void print(){
        System.out.println(StaffCat + ": " + EmpName);
        System.out.println("Race: " + EmpRace);
        System.out.println("SSN: " + EmpSSN);
        System.out.println("Base Salary: " + BaseSalary);
        System.out.println("COLA: " + COLA);
        System.out.println("Compensation: " + bonus);
        System.out.println("Total Salary: " + Salary);
        System.out.println("Federal Tax: " + FedTax);
        System.out.println("Mass. State Tax: " + StateTax);
        System.out.println("-----------------------");
    }
}//end class Nurses
