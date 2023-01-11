package com.company;

//create class Administration to determine employee special info,calculate bonuses, Federal/States taxes,
//total salary,and cost of living
class Administration extends StaffCategory{

    public Administration(String EmpName, String EmpSSN, String EmpRace, int EmpSpecInfo) {
        super(EmpName, EmpSSN, EmpSpecInfo, EmpRace);
        calcStaffCategory();
        calcBaseSalary();
        calcCOLA();
    }

    //method to calculate staff category based of special info
    public void calcStaffCategory(){
        if(EmpSpecInfo == 1){
            StaffCat = "Senior Executive";
        }else if(EmpSpecInfo == 2){
            StaffCat = "Junior Executive";
        }else if(EmpSpecInfo == 3){
            StaffCat = "Support";
        }
    }

    //method to calculate employee BaseSalary based off of staff category
    public void calcBaseSalary(){

        if(StaffCat.equals("Senior Executive")){
            BaseSalary = 400000;
            bonus = BaseSalary * 0.2;
        }else if(StaffCat.equals("Junior Executive")){
            BaseSalary = 175000;
            bonus = BaseSalary * 0.1;
        }else if(StaffCat.equals("Support")){
            BaseSalary = 40000;
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
        System.out.println("Bonus: " + bonus);
        System.out.println("Total Salary: " + Salary);
        System.out.println("Federal Tax: " + FedTax);
        System.out.println("Mass. State Tax: " + StateTax);
        System.out.println("-----------------------");
    }
}//end class Administration
