package com.company;
//create class StaffCategory to determine employee bonuses, taxes, and staff category
class StaffCategory extends Boston {

    protected String StaffCat;
    protected double BaseSalary;
    protected double bonus;
    protected double Salary;

    //StaffCategory constructor
    public StaffCategory(String EmpName, String EmpSSN, int EmpSpecInfo, String EmpRace) {
        super(EmpName, EmpSSN, EmpSpecInfo, EmpRace);

    }
    //return employee bonuses
    public double calcBonus(){
        return bonus;
    }

    public double getBonus(){
        return calcBonus();
    }
    //method to calculate employee total salary
    public void calcSalary(){
        Salary = BaseSalary + bonus + COLA;
    }

    public double getSalary(){
        return Salary;
    }

    //method to calculate Federal taxes based at .25 percent
    public void calcFedTax(){
        FedTax = Salary * FedTax;
    }
    //method to calculate State taxes based at .05 percent
    public void calcStateTax(){
        StateTax = Salary * StateTax;
    }

    public double getStateTax(){
        return StateTax;
    }

    public double getFedTax(){
        return FedTax;
    }

    public String getStaffCat(){
        return StaffCat;
    }

    public void setStaffCat(String StaffCat){
        this.StaffCat = StaffCat;
    }

}//end of StaffCategory
