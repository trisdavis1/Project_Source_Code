package com.company;
//create class Boston to represent Regional Cities that employees work in
class Boston extends WLFUTeam {

    //establish federal, state, and cost of living tax rate
    protected double StateTax = .05;
    protected double FedTax = .25;
    protected double COLA = .15;
    //Boston super constructor to determine employee object inputs
    public Boston(String EmpName, String EmpSSN, int EmpSpecInfo, String EmpRace) {
        super(EmpName, EmpSSN, EmpRace, EmpSpecInfo);
        this.EmpSpecInfo = EmpSpecInfo;
        Race();

    }

    //loop to compare string inputs to determine race
    public void Race() {
        if (EmpRace.equals("AA")) {
            EmpRace = "African American";

        } else if (EmpRace.equals("CA")) {
            EmpRace = "Caucasian";

        } else if (EmpRace.equals("AS")) {
            EmpRace = "Asian";

        } else if (EmpRace.equals("HS")) {
            EmpRace = "Hispanic";

        } else {
            EmpRace = "Other";
        }
    }

    //print method to print objects of type Boston
    public void print(){
        System.out.println("Boston");
    }
}//end of class Boston

