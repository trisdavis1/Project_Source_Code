/*
 This is a simulation program written in Java.  Each day the newsboy must order the papers for the next day.
 He buys the papers for 35 cents and sells them for 1 dollar.  The simulation runs for 1,000 days
 and generates the paper demand (from 15 to 20 per day) randomly for an observed distribution.
 Over the 1,000-day period, the simulation collects profit per day, average profit per day, and the average papers
 demanded per day. This is used to decipher the best reordering policy for the news boy.
 In this simulation the printout is produced from 3 different ordering policies that the newsboy is testing.
 A 10-day sample is selected from the 1,000-day period of each order policy. The ideal purpose of the simulation is to
 help the newsboy determine which ordering policy will generate the most profit.
 Each simulated ordering policy generates the average profit, papers sold, and demanded along
 with the variance, and the standard deviation.
 */

import java.io.*;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.NoSuchElementException;
import java.math.*;

public class NewsboySim {

    public static void main(String[] args)throws Exception {

        PrintWriter output;

        //now equate the name to the external file through the PrintWriter
        output = new PrintWriter(new File("NewsboyOut.txt"));

        int i, day, dayx, dayy;

        cStats statistics = new cStats(); //initiate statistics
        newsBoy TD = new newsBoy();//initiate the TD
        newsBoy TD1 = new newsBoy(); //initiate 2nd TD
        newsBoy TD2 = new newsBoy(); //initiate 3rd TD
        dmdProc needpaper = new dmdProc();

        int dmd, dmd1, dmd2;
        //double sales, money;

        //now start a loop to test TD's behavior for 100 days
        //have demand constant at 16
        //using the first ordering policy
        for(day = 1; day <= 1000; day++){
            //get demand for the day
            dmd = needpaper.dmdToday();
            //give TD the demand for today
            TD.setDemand(dmd);
            //TD1.setDemand1(dmd);
            //record the statistics for profit this day
            statistics.setProfit(TD.getProfit());
            //record the statistics for sold this day
            statistics.setAvsold(TD.getsold());
            //record the statistics for demanded that day
            statistics.setAvdemand(TD.getDemand());
            //order papers for tomorrow
            TD.order();

            if(day >= 500 && day <= 510){
                System.out.println("For day " + day + " demand " + dmd + " sold " + TD.getsold());

                output.println("For day " + day + " demand " + dmd + " sold " + TD.getsold());

                output.println(" Profit " + TD.getProfit() + " ordered " + TD.getordered());
            }

        } //end of timing loop

        //first ordering policy printout

        System.out.println("Sold "+ TD.getsold()+" ordered "+ TD.getordered());
        System.out.println("Profit " + TD.getProfit());
        System.out.println("**************Statics for 1000 Days of Sales***************\n");
        System.out.println("Average sold: " + statistics.getAverage1());
        System.out.println("Sold Variance: " + statistics.getVar1() + " st dev: " + statistics.getstdev1());
        System.out.println("Count: " + statistics.getCount1() + "\n");
        System.out.println("------------------------------------- \n");
        System.out.println("Average demanded: " + statistics.getAverage2());
        System.out.println("Demanded Variance: " + statistics.getVar2() + " st dev: " + statistics.getstdev2() + "\n");
        //System.out.println("Count: " + statistics.getCount1() + "\n");
        System.out.println("------------------------------------- \n");
        System.out.println("Average profit: " + statistics.getAverage());
        System.out.println("Profit Variance: "+ statistics.getVar() + " st dev: " + statistics.getstdev());
        System.out.println("Count: " + statistics.getCount() + "\n");
        System.out.println("--------------------------------- \n");

        //implementing the second ordering policy
        for(dayx = 1; dayx <= 1000; dayx++) {
            //get demand for the day

            dmd1 = needpaper.dmdToday();
            //give TD the demand for today
            //TD.setDemand(dmd);
            TD1.setDemand1(dmd1);
            //record the statistics for profit this day
            statistics.setProfit(TD1.getProfit());
            //record the statistics for sold this day
            statistics.setAvsold(TD1.getsold());
            //record the statistics for demanded that day
            statistics.setAvdemand(TD.getDemand());
            //order papers for tomorrow
            TD1.order1();

            if (dayx >= 500 && dayx <= 510) {
                System.out.println("For day " + dayx + " demand " + dmd1 + " sold " + TD1.getsold());

                output.println("For day " + dayx + " demand " + dmd1 + " sold " + TD1.getsold());

                output.println(" Profit " + TD1.getProfit() + " ordered " + TD1.getOrdered1());
            }
        }

        //second ordering policy printout

        System.out.println("Sold " + TD1.getsold() + " ordered " + TD1.getOrdered1());
        System.out.println("Profit " + TD1.getProfit());
        System.out.println("************** Statics for 1000 Days of Sales ***************\n");
        System.out.println("Average sold: " + statistics.getAverage1());
        System.out.println("Sold Variance: " + statistics.getVar1() + " st dev: " + statistics.getstdev1());
        System.out.println("Count: " + statistics.getCount1() + "\n");
        System.out.println("------------------------------------- \n");
        System.out.println("Average demanded: " + statistics.getAverage2());
        System.out.println("Demanded Variance: " + statistics.getVar2() + " st dev: " + statistics.getstdev2() + "\n");
        System.out.println("------------------------------------- \n");
        System.out.println("Average profit: " + statistics.getAverage());
        System.out.println("Profit Variance: "+ statistics.getVar() + " st dev: " + statistics.getstdev());
        System.out.println("Count: " + statistics.getCount() + "\n");
        System.out.println("--------------------------------- \n");

        //implementing the third ordering policy
        for(dayy = 1; dayy <= 1000; dayy++) {
            //get demand for the day
            dmd2 = needpaper.dmdToday();
            //give TD the demand for today
            TD2.setDemand2(dmd2);
            //record the statistics for profit this day
            statistics.setProfit(TD2.getProfit());
            //record the statistics for sold this day
            statistics.setAvsold(TD2.getsold());
            //record the statistics for demanded that day
            statistics.setAvdemand(TD.getDemand());
            //order papers for tomorrow
            TD2.order2();

            if (dayy >= 500 && dayy <= 510) {
                System.out.println("For day " + dayy + " demand " + dmd2 + " sold " + TD2.getsold());

                output.println("For day " + dayy + " demand " + dmd2 + " sold " + TD2.getsold());

                output.println(" Profit " + TD2.getProfit() + " ordered " + TD2.getOrdered2());
            }
        }

        //third ordering policy printout

        System.out.println("Sold " + TD2.getsold() + " ordered " + TD2.getOrdered2());
        System.out.println("Profit " + TD2.getProfit());
        System.out.println("************** Statics for 1000 Days of Sales ***************\n");
        System.out.println("Average sold: " + statistics.getAverage1());
        System.out.println("Sold Variance: " + statistics.getVar1() + " st dev: " + statistics.getstdev1());
        System.out.println("Count: " + statistics.getCount1() + "\n");
        System.out.println("------------------------------------- \n");
        System.out.println("Average demanded: " + statistics.getAverage2());
        System.out.println("Demanded Variance: " + statistics.getVar2() + " st dev: " + statistics.getstdev2() + "\n");
        System.out.println("------------------------------------- \n");
        System.out.println("Average profit: " + statistics.getAverage());
        System.out.println("Profit Variance: "+ statistics.getVar() + " st dev: " + statistics.getstdev());
        System.out.println("Count: " + statistics.getCount() + "\n");
        System.out.println("--------------------------------- \n");

        System.out.println("Math.random function test");
         // a test of the Math.random function
         int x;
          for ( i = 1;i <= 30; i++) {
              x = (int)(Math.random() * 100);

              System.out.println(x);
          }
    }//end of main
}//end of NewsboySim

class newsBoy {

    private int demand; //this is the demand for the day
    private int ordered; //The amount ordered for the day
    private int ordered1; //this is the second ordering policy
    private int ordered2; //this is the third ordering policy
    private int bought;  //this is the amount bought for day
    private int sold;  //this is the amount sold for the day
    private double profit; //this is the amount of profit made on the day
    private int returns; //this is the amount of paper not sold

    public newsBoy(){

        demand = 0;
        ordered = 15;
        ordered1 = demand;
        ordered2 = demand - 1;
        bought = 0;
        sold = 0;
        profit = 0.0;
        returns = 0;

    }//end of newsboy constructor

    public int order() {
        //This is a private policy function for how many the newsboy will order daily

        int x;
        x = 16;  //order 16 papers daily
        ordered = x;

        return x;
    }

    public int order1() {

        int y;
        y = demand; //order the same number of papers each day as demanded the last
        ordered1 = y;

        return y;
    }

    public int order2() {

        int z;
        z = demand - 1; //order one less paper each day than called for the previous day
        ordered2 = z;

        return z;
    }

    public int returns(){
        //this is a functions for calculating how many returns the newsboy has daily

        int x;
        x = ordered - sold;
        returns = x;

        return x;
    }

    private void behavior(){
        //this is the behavior of the newsboy
        //receive the papers ordered yesterday

        bought = ordered;

       //calculate papers sold
        if(demand >= bought)
            sold = bought;
        else
            sold = demand;
        //calculate the amount of returns
            returns = bought - sold;
        //calculate profit including returns
            profit = (0.05 * returns) + (1.00 * sold) - 0.35 * bought;
        //order for tomorrow
            ordered = order();

            returns = returns();

    } //this is the end of the behavior of the newsboy

    private void behavior1(){
        //this is the behavior for the newsboy
        //receive the papers ordered yesterday

        bought = ordered1;

        //calculate papers sold
        if(demand >= bought)
            sold = bought;
        else
            sold = demand;
        //calculate the amount of returns
        returns = bought - sold;
        //calculate profit including returns
        profit = (0.05 * returns) + (1.00 * sold) - 0.35 * bought;
        //order for tomorrow
        ordered1 = order1();

        returns = returns();

    } //this is the end of the behavior1 of the newsboy

    private void behavior2(){
        //this is the behavior for the newsboy
        //receive the papers ordered yesterday

        bought = ordered2;

        //calculate papers sold
        if(demand >= bought)
            sold = bought;
        else
            sold = demand;
        //calculate the amount of returns
        returns = bought - sold;
        //calculate profit including returns
        profit = (0.05 * returns) + (1.00 * sold) - 0.35 * bought;
        //order for tomorrow
        ordered2 = order2();

        returns = returns();

    } //this is the end of the behavior2 of the newsboy

    public void setDemand(int x){
        //we will give the newsboy a demand and then let him behave as appropriate
        demand = x;

        //given the demand for the day, activate the behavior of the newsboy object
        behavior();

    }//end of set demand

    public void setDemand1(int y) {
        //we will give the newsboy a demand and then let him behave accordingly
        demand = y;
        //given the demand for the day, activate the behavior1 for the newsboy object
        behavior1();
    }//end set demand 1

    public void setDemand2(int z) {
        //we will give the newsboy a demand and then let him behave accordingly
        demand = z;
        //given the demand for the day, activate the behavior2 for the newsboy object
        behavior2();
    }//end set demand 2

//***********Now Create the Utility functions to Interrograte the News Boy Objecct
    public double getProfit(){
        return profit;
    }
    public int getsold(){
        return sold;
    }
    public int getDemand(){
        return demand;
    }
    public  int  getordered(){
        return ordered;
    }
    public int getOrdered1() {
        return ordered1;
    }
    public int getOrdered2(){
        return ordered2;
    }

} //end of newsboy class

class cStats {

    private double profit; //profit for the day
    private double sold; // papers sold per day
    private double demanded; //papers demanded per day
    private double psum; //sum of the profit for all days
    private double psum2; //sum squared of profit
    private double ssum; //sum of sold for all days
    private double ssum2; // sum squared of sold
    private double dsum; //sum of demanded per day
    private double dsum2; // sum squared of demanded
    private double average, average1, average2; //average papers demanded
    private double stdev, stdev1, stdev2;// standard deviation for demanded
    private double variance, variance1, variance2; //variance
    private int count, count1, count2;

    //constructor for cStats
    public cStats() {

        profit = psum = psum2 = average = stdev = variance = 0;

        sold = ssum = ssum2 = average1 = stdev1 = variance1 = 0;

        demanded = dsum = average2 = stdev2 = variance2 = 0;

        count = 0;
        count1 = 0;
        count2 = 0;
    }

    public void setProfit(double x) {
        //this function sets profit and calculates stats for the day

        profit = x;
        psum += profit;
        psum2 += profit * profit;
        count++;
        average = psum / count;
        variance = psum2 / count - average * average;
        stdev = Math.sqrt(variance);

        return;

    } //end of setProfit

    public void setAvsold(double y){

        sold = y;
        ssum += sold;
        ssum2 += sold * sold;
        count1++;
        average1 = ssum / count1;
        variance1 = ssum2 / count1 - average1 * average1;
        stdev1 = Math.sqrt(variance1);

        return;

    }//end of setAvsold

    public void setAvdemand(double z){

        demanded = z;
        dsum += demanded;
        dsum2 += demanded * demanded;
        count2++;
        average2 = dsum / count2;
        variance2 = dsum2 / count2 - average2 * average2;
        stdev2 = Math.sqrt(variance2);

        return;

    }//end of setAvdemanded

    public double getProfit() {
        return profit;
    }
    public double getAverage() {
        return average;
    }
    public double getVar(){
        return variance;
    }
    public double getstdev() {
        return stdev;
    }
    public int getCount() {
        return count;
    }
    public double getAverage1() {
        return average1;
    }
    public double getVar1() {
        return variance1;
    }
    public double getstdev1() {
        return stdev1;
    }
    public int getCount1() {
        return count1;
    }
    public double getAverage2() {
        return average2;
    }
    public double getVar2() {
        return variance2;
    }
    public double getstdev2() {
        return stdev2;
    }
} // end of class cStats

class dmdProc {

    private int demand;
    //create the constructor for dmdproc
    public dmdProc() {

        demand = 0;
    }

    //this is the process generator for the demand
    public int dmdToday() {
        //the demand for papers is considered on a daily basis

        int x;  //this is the random variant

        x = (int) (Math.random() * 100);

        if (x <= 8) demand = 15;
        else
            if(x <= 16) demand = 16;
            else
                if(x <= 58) demand = 17;
                else
                    if(x <= 75) demand = 18;
                    else
                        if(x <= 92) demand = 19;
                        else
                            demand = 20;

                        return demand;

    }// end of dmdToday
}//end of class dmdProc

