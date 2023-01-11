package com.company;
//create class MedSupport to determine employee special info,calculate bonuses, Federal/States taxes,
//total salary,and cost of living
class MedSupport extends StaffCategory{

    public MedSupport(String EmpName, String EmpSSN, String EmpRace, int EmpSpecInfo) {
        super(EmpName, EmpSSN, EmpSpecInfo, EmpRace);
        calcStaffCategory();
        calcBaseSalary();
        calcCOLA();
    }
    //method to calculate staff category based off special info
    public void calcStaffCategory(){
        if(EmpSpecInfo == 1){
            StaffCat = "Medical Support Technician";
        }else if(EmpSpecInfo == 2){
            StaffCat = "Patient Care";
        }
    }
    //method to calculate Basesalary and bonuses based off staff category
    public void calcBaseSalary(){

        if(StaffCat.equals("Medical Support Technician")){
            BaseSalary = 45000;
        }else if(StaffCat.equals("Patient Care")){
            BaseSalary = 35000;
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
        System.out.println("Bonus: None");
        System.out.println("Total Salary: " + Salary);
        System.out.println("Federal Tax: " + FedTax);
        System.out.println("Mass. State Tax: " + StateTax);
        System.out.println("-----------------------");
    }
}//end of class MedSupport
