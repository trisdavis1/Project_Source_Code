package com.company;
//create WLFU Team and specified parameters for employee objects
public class WLFUTeam extends Employees implements Comparable{

    protected String EmpName;
    protected String EmpSSN;
    protected String EmpRace;
    protected int EmpSpecInfo;

    //WLFUTeam constructor with specified parameters
    public WLFUTeam(String EmpName, String EmpSSN, String EmpRace, int EmpSpecInfo){
        this.EmpName = EmpName;
        this.EmpSSN = EmpSSN;
        this.EmpRace = EmpRace;
        this.EmpSpecInfo = EmpSpecInfo;

    }
    //implement compareTo function to compare objects based on salary
    public int compareTo(Object o){
        if(getSalary() < ((WLFUTeam)o).getSalary())
            return 1;
        else
        if(getSalary() > ((WLFUTeam)o).getSalary())
            return - 1;
        else
            return 0;

    }//end compareTo function

    public void setEmpName(String empName){
        return;
    }

    public double getSalary(){
        return 0.0;
    }

    public double getFedTax() {
        return 0.0;
    }

    public String getEmpName(){
        return EmpName;
    }

    public String getEmpSSN(){
        return EmpSSN;
    }

    public String getEmpRace(){
        return EmpRace;
    }


}//end of WLFUTeam class
