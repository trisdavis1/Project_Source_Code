
import java.io.*;
import java.io.IOException;
import javax.swing.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.NoSuchElementException;
import java.math.*;


public class LumberCo {

    public static void main(String[] args)throws Exception {

        PrintWriter output;
        //now equate the internal name to an external file through
        //the PrintWriter

        output = new PrintWriter(new File("LumberCoOut.txt"));

        double dtreesrain1[] = {0.1, 0.05, 0.02};

        double dtreesrain2[] = {0.1, 0.05, 0.03};

        double dtreesrain3[] = {0.3, 0.05, 0.03};

        double dtreesrain4[] = {0.35, 0.05, 0.04};

        double ngtreesrain1[] = {0.70, 0.01, 0.02};

        double ngtreesrain2[] = {0.75, 0.02, 0.03};

        double ngtreesrain3[] = {0.6, 0.02, 0.03};

        double ngtreesrain4[] = {0.65, 0.01, 0.04};

        double beetles1[] = {0.1, 0.05, 0.0};

        double beetles2[] = {0.15, 0.05, 0.0};

        double beetles3[] = {0.3, 0.1, 0.02};

        double beetles4[] = {0.3, 0.1, 0.02};

        double fires1[] = {0.15, 0.1, 0.05};

        double fires2[] = {0.18, 0.12, 0.07};

        double fires3[] = {0.22, 0.15, 0.10};

        double fires4[] = {0.3, 0.2, 0.15};
/*
        //fraction of 1-4 year olds dying and not growing due to rainfall
        double rainfallI[][][] = {
                {{0.1, 0.1, 0.3, 0.35}, {0.70, 0.75, 0.60, 0.65}},
                {{0.05, 0.05, 0.05, 0.05}, {0.01, 0.02, 0.02, 0.01}},
                {{0.02, 0.03, 0.03, 0.04}, {0.02, 0.03, 0.03, 0.04}}
                };
        //fraction of 1-4 year olds killed by beetles
        double beetlesI[][] = { {0.1, 0.15, 0.3, 0.3},
                                    {0.05, 0.05, 0.1, 0.1},
                                        {0.0, 0.0, 0.02, 0.02} };
        //fraction of 1-4 year old killed by fire
        double firesI[][] = { {0.15, 0.18, 0.22, 0.30},
                                {0.1, 0.12, 0.15, 0.2},
                                    {0.05, 0.07, 0.1, 0.15} };*/

        //Study policy values:
        int sprayedtrees = 1500;
        int sprayedtrees3 = 1000;


        double spraytree = 0.5;

        //****** Variables in this simulation ********
        int rtype, rstype, gtype, age, year, i;
        double ttrees34, ttrees3, aspray, aspray3, sspray = 0.0, sspray2 = 0.0, sspray3 = 0.0, sspray33 = 0.0;
        double vart, average, stdev;
        double vart1, average1, stdev1;
        double vart2, average2, stdev2;
        double vart3, average3, stdev3;

        Trees trees1 = new Trees(2000.0, dtreesrain1, ngtreesrain1, beetles1, fires1);
        Trees trees2 = new Trees(1500.0, dtreesrain2, ngtreesrain2, beetles2, fires2);
        Trees trees3 = new Trees(1000.0, dtreesrain3, ngtreesrain3, beetles3, fires3);
        Trees trees4 = new Trees(500.0, dtreesrain4, ngtreesrain4, beetles4, fires4);

        for(year = 1; year <= 100; year++){

            trees1.addtrees(1.0);
            //determine the rain conditions for this year
            rtype = RainNoSeed();
            rstype = RainSeed();

            //calculate trees killed due to rain
            //dead tree percentages
            trees1.rainfall(rtype);
            trees2.rainfall(rtype);
            trees3.rainfall(rtype);
            trees4.rainfall(rtype);

            //trees not growing percentages
            trees1.rainfall1(rstype);
            trees2.rainfall1(rstype);
            trees3.rainfall1(rstype);
            trees4.rainfall1(rstype);

            //calculate trees killed due to beetles
            //dying trees
            trees1.survbeetles(rtype);
            trees2.survbeetles(rtype);
            trees3.survbeetles(rtype);
            trees4.survbeetles(rtype);
            //not growing trees
            trees1.survbeetles(rstype);
            trees2.survbeetles(rstype);
            trees3.survbeetles(rstype);
            trees4.survbeetles(rstype);

            //calculate the population of trees died to forest fire
            //dying trees
            trees1.survfire(rtype);
            trees2.survfire(rtype);
            trees3.survfire(rtype);
            trees4.survfire(rtype);
            //not growing trees
            trees1.survfire(rstype);
            trees2.survfire(rstype);
            trees3.survfire(rstype);
            trees4.survfire(rstype);

            //calculate the tree population statistics
            trees1.calcstats();
            trees2.calcstats();
            trees3.calcstats();
            trees4.calcstats();

            //print the tree populations for this year trees
            if((year >= 45) && (year <= 55)){
                System.out.println("For year: " + year + ", 1 yr old dying trees represented "
                        + trees1.getTrees() + " of the population. Rain no seed = " + rtype
                        + ", Rain seed = " + rstype);

                System.out.println("For year: " + year + ", 2 yr old dying trees represented "
                        + trees2.getTrees() + " of the population. Rain no seed = " + rtype
                        + ", Rain seed = " + rstype);
                System.out.println("For year: " + year + ", 3 yr old dying trees represented "
                        + trees3.getTrees() + " of the population. Rain no seed = " + rtype
                        + ", Rain seed = " + rstype);
                System.out.println("For year: " + year + ", 4 yr old dying trees represented "
                        + trees4.getTrees() + " of the population. Rain no seed = " + rtype
                        + ", Rain seed = " + rstype);

                System.out.println(trees1.getTrees());
            }

            //calculate total trees sprayed just 3 year olds
            ttrees3 = trees3.getTrees();
            aspray3 = ttrees3 * spraytree;

            //calculate total trees for spraying this year
            ttrees34 = trees3.getTrees() + trees4.getTrees();
            aspray = ttrees34 * spraytree;

            //System.out.println("For year: " + year + ", percent trees sprayed = " + spraytree +
                    //", total 3/4yr old trees = " + ttrees + ", possible trees sprayed = " + sprayedtrees);

            if((year >= 45) && (year <= 55)){
                System.out.println("For year: " + year + ", percent trees sprayed = " + spraytree +
                        ", total 3yr old trees = " + ttrees3 + ", possible trees sprayed = " + sprayedtrees3);
                System.out.println("For year: " + year + ", trees sprayed = " + aspray3 +
                        ", total trees 3 year old trees = " + ttrees3);

                System.out.println("For year: " + year + ", percent trees sprayed = " + spraytree +
                        ", total 3/4yr old trees = " + ttrees34 + ", possible trees sprayed = " + sprayedtrees);
                System.out.println("For year: " + year + ", trees sprayed = " + aspray +
                        ", total trees 3/4 year old trees = " + ttrees34);
            }

            //accumulate sprayed 3 yr trees statistics
            sspray3 += aspray3;
            sspray33 += aspray3 * aspray3;

            //accumulate sprayed trees statistics
            sspray += aspray;
            sspray2 += aspray * aspray;

            //Now kill the trees not sprayed
            trees3.killtrees(spraytree);
            trees4.killtrees(spraytree);

            //move back through the trees to age them
            trees4.settrees(trees3.getTrees());
            trees3.settrees(trees2.getTrees());
            trees2.settrees(trees1.getTrees());

        }//end of year loop
        System.out.println("Statistics for LumberCo: \n");

        //calculate variance and standard deviation for each age group
        //1 year olds
        average = trees1.getSumtrees() / 100;
        vart = trees1.getSumtrees2() / 100 - average * average;
        stdev = Math.sqrt(vart);
        System.out.println("1 yr old tree stats: ");
        System.out.println("Variance: " + vart + ", St. Dev: " + stdev + "\n");
        //2 year olds
        average1 = trees2.getSumtrees() / 100;
        vart1 = trees2.getSumtrees2() / 100 - average1 * average1;
        stdev1 = Math.sqrt(vart1);
        System.out.println("2 yr old tree stats: ");
        System.out.println("Variance: " + vart1 + ", St. Dev: " + stdev1 + "\n");
        //3 year olds
        average2 = trees3.getSumtrees() / 100;
        vart2 = trees3.getSumtrees2() / 100 - average2 * average2;
        stdev2 = Math.sqrt(vart2);
        System.out.println("3 yr old tree stats: ");
        System.out.println("Variance: " + vart2 + ", St. Dev: " + stdev2 + "\n");
        //4 year olds
        average3 = trees4.getSumtrees() / 100;
        vart3 = trees4.getSumtrees2() / 100 - average3 * average3;
        stdev3 = Math.sqrt(vart3);
        System.out.println("4 yr old tree stats: ");
        System.out.println("Variance: " + vart3 + ", St. Dev: " + stdev3);


    }

    public static int RainNoSeed(){

        int x, rtype;

        x = (int) (Math.random() * 100);

        if(x < 11) rtype = 0;
        else
            if(x < 93) rtype = 1;
            else
                rtype = 2;

            System.out.println("No Seeding occurance X was %" + x + " and rain type was " + rtype);

            return rtype;

    }

    public static int RainSeed(){
        int x, rstype;

        x = (int) (Math.random() * 100);

        if(x <= 3) rstype = 0;
        else
            if(x <= 85) rstype = 1;
            else
                rstype = 2;

            System.out.println("Seeding occurane X was %" + x + " and rain type was " + rstype);

            return rstype;
    }
}//end of LumberCo

class Trees{

    private double trees; //this is the number trees
    private double sumtrees; //the sum of trees for this group
    private double sumtrees2; //the sum of trees squared

    //the percent of trees that are dying
    private double rain[] = {0.0, 0.0, 0.0};
                    //{0.0, 0.0, 0.0, 0.0}},
            //{{0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}},
            //{{0.0, 0.0, 0.0, 0.0}, {0.0, 0.0, 0.0, 0.0}}
            //{{0.0, 0.0}, {0.0, 0.0}, {0.0, 0.0}}
    //};
    //the percent of trees that are not growing
    private double rain1[] = {0.0, 0.0, 0.0};

    //the percent of trees attacked by beetles
    private double beetles[] = {0.0, 0.0, 0.0};
            //{0.0, 0.0, 0.0, 0.0},
            //{0.0, 0.0, 0.0, 0.0}
    //};
    //percent of trees susceptible to fire
    private double fire[] = {0.0, 0.0, 0.0,};
            //{0.0, 0.0, 0.0, 0.0},
            //{0.0, 0.0, 0.0, 0.0}
    //};

    public double getTrees(){

        return trees;
    }
    public double getSumtrees(){

        return sumtrees;
    }
    public double getSumtrees2(){

        return sumtrees2;
    }

    public void calcstats(){
        //this calculates the sum of trees
        sumtrees += trees;

        sumtrees2 += trees * trees;
    }//end of calcstats

    public Trees(double origt, double growrain[], double growrain1[],
                 double growbeetles[], double growfire[]){

        int i;
        int j;
        int k;
        //set the number of trees in this group
        trees = origt;

        //set the percentage of trees growing under different conditions
        for(i = 0; i <= 2; i++){
            rain[i] = growrain[i];
            rain1[i] = growrain1[i];
            beetles[i] = growbeetles[i];
            fire[i] = growfire[i];
        }

       /* for(i = 0; i < rain.length; i++){
            for(j = 0; j < rain[i].length; j++) {
                for (k = 0; k < rain[i][j].length; k++)
                    rain[i][j][k] = growrain[i][j][k];
            }
        }
        for(i = 0; i < beetles.length; i++) {
            for (j = 0; j < beetles[i].length; j++) {
                beetles[i][j] = growbeetles[i][j];
            }
        }
        for(i = 0; i < fire.length; i++) {
            for (j = 0; j < fire[i].length; j++) {
                fire[i][j] = growfire[i][j];
            }
        }*/

        //now set the statistics for this year group;
        sumtrees = sumtrees2 = 0.0;

    }//end of constructor Trees

    public void rainfall(int rtype){
        //this calculates the number of trees dying and not growing from rain

        trees -= trees * rain[rtype];//[gtype][age]
        //strees *= 1.0 - rain[rtype];//[gtype][age]
        //trees *= 1.0 - rain[rtype][gtype][age];

        return;

    }//end rainfall

    public void rainfall1(int rtype){
        //this calculates the number of trees dying and not growing from rain

        trees -= trees * rain1[rtype];//[gtype][age]
        //strees *= 1.0 - rain1[rtype];//[gtype][age]
        //trees *= 1.0 - rain[rtype][gtype][age];

        return;

    }//end rainfall1

    public void survbeetles(int rtype){
        //this is function computes the number of trees surviving rain

        trees *= 1.0 - beetles[rtype];//[age]
        //ngtrees *=  (1.0 - beetles[rtype][age]);

        return;
    }//end of beetles

    public void survfire(int rtype){
        //this is the number of trees that survive forest fires
        trees *= 1.0 - fire[rtype];//[age]
        //ngtrees *=  (1.0 - fire[rtype][age]);

        return;
    }//end of survfire

    public void killtrees(double regt){

        //kill trees by rainfall
        trees *= (1.0 - regt);
       // strees *= (1.0 - spt);

        return;

    }//end of killtrees

    public void addtrees(double reg){
        //this functions adds trees to the dying and not growing groups
        trees += reg;
        //strees += spt;

        return;

    }// end addtrees

    public void settrees(double reg){
        //this function sets the number of trees in the class for dying and growing trees

        trees = reg;
        //strees = spt;
        return;

    }//end of set trees


}//end of class trees
