package src;
import java.io.*;
import java.io.IOException;
import javax.lang.model.util.ElementScanner6;
import javax.swing.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.util.NoSuchElementException;

public class YHOW {
    public static void main(String args[]) throws Exception{

        //this is the currentcells[][] array that holds the cell objects
        Cell[][] currentcells = new Cell[10][10];

        int tint = 1, tint1 = 1;//this is the cell table iterator count
        //this is the cell table holding the cell condition for each cell
        int[][] nextcells = new int[10][10];

        int[] count = new int [11];

        int i, j, k, l, year;
        for(i = 0; i <= 9; i++){
            for(j = 0; j <= 9; j++){
                //create the cells in the currentcells and fill the nextcells with their condition
                currentcells[i][j] = new Cell(i, j, 10, 10);
                nextcells[i][j] = currentcells[i][j].getcond();
            }
        }
        //original cell table
        System.out.println("This is the original Muscle Cell table: Condition 1 = Healthy( HM )");
        //now print the nextcells table
        printtable(nextcells, tint, 9, 9);
        System.out.println("\n");

        System.out.println("Now we start the Tendril and Cure iterations:");
        System.out.println("------ BASE CASE -------");
        //now list the original table of cells
        //now change the cells in the table
        for(year = 1; year <= 10; year++){
            for(i = 0; i <= 9; i++){
                for(j = 0; j <= 9; j++){
                    //update all conditions for all cells
                    currentcells[i][j].celltendril(nextcells, i, j);
                    if((i == 0) && (j == 0)){                                  
                        System.out.println("This is for 0,0 cell condition is " + currentcells[0][0].getcond()
                        + " this is the last tendril " + currentcells[0][0].getlasttendril());
                        System.out.println("This is the tendril direction " + currentcells[0][0].gettendrildir());
                    }
                }
            }
            // must wait to until all cells have been updated because we updated them based 
            //on the old nextcells table 
            for (i = 0; i <= 9; i++)
                for(j = 0; j <= 9; j++){//now we can update the nextcell table and print it
                    nextcells[i][j] = currentcells[i][j].getcond(); 
                }
                printtable(nextcells,tint,9,9);
                counttable(nextcells,count);
                printcount(count);
                tint++; 
            //now lasttendril for all cells
            for (i = 0; i <= 9; i++){
                for(j = 0; j <= 9; j++){//now we can update the interation since last cell change for all Cells
                    currentcells[i][j].updatelasttendril(); 
                }
            }
            calcstatistics(count);
        }
        //calcstatistics(nextcells, count);
        System.out.println("------ Youth TEST -------");
        for(year = 1; year <= 10; year++){
            for(k = 0; k <= 9; k++){
                for(l = 0; l <= 9; l++){
                    if(year == 1 && Math.random() <= .05){
                            nextcells[k][l] = 3;
                    }else
                    if(year > 1 && Math.random() <= .03){
                            nextcells[k][l] = 3;
                    }
                }
            }
            for(k = 0; k <= 9; k++){
                for(l = 0; l <= 9; l++){
                    //update all conditions for all cells
                    currentcells[k][l].celltendril(nextcells, k, l);
                }
            }
            for (k = 0; k <= 9; k++){
                for(l = 0; l <= 9; l++){//now we can update the nextcell table
                    nextcells[k][l] = currentcells[k][l].getcond(); 
                }
            }
            for(k = 0; k <= 9; k++){
                for(l = 0; l <= 9; l++){
                    //update all conditions for all cells
                    currentcells[k][l].curecell(nextcells, k, l);
                    if((k == 0) && (l == 0)){
                        System.out.println("This is for 0,0 cell condition is " + currentcells[0][0].getcond()
                            + " this is last cure " + currentcells[0][0].getlastcure());
                    }
                }
            }
            // must wait to until all cells have been updated because we updated them based 
            //on the old nextcells table 
            for (k = 0; k <= 9; k++)
                for(l = 0; l <= 9; l++){//now we can update the nextcell table and print it
                    nextcells[k][l] = currentcells[k][l].getcond(); 
                }
                printtable(nextcells,tint1,9,9);
                counttable(nextcells,count);
                printcount(count);
                tint1++;
            //now lasttendril for all cells
            for (k = 0; k <= 9; k++){
                for(l = 0; l <= 9; l++){//now we can update the interation since last cell change for all Cells
                    currentcells[k][l].updatelastcure();           
                }
            }   
            calcstatistics(count);
        }
    }//end of main

    public static void printtable(int [][] nextcells, int tprint, int imax, int jmax){
        //this function prints the nextcell table
        System.out.println("This is the " + tprint + " Iteration of NextCells");
        //print table header
        System.out.println("C1, C2, C3, C4, C5, C6, C7, C8, C9, C10");
        for(int i = 0; i <= imax; i++){
            for(int j = 0; j <= jmax; j++){
                cellname(nextcells[i][j]);
            }//next rows of cells 
            //now skip a line
            System.out.println();
        }
    }//end of print table

    public static void cellname(int condnum){
        //this prints the name of the cells
        if(condnum == 1){
            System.out.print("HM  ");
        }
        else if(condnum == 2){
            System.out.print("MW  ");
        }else
            System.out.print("YW  ");
        return;
    }//end of cellname

    public static void printcount(int [] count){
        //this function prints the cell count
        System.out.println(" Healthy,  Muscle Wilt,  Youth");
        for(int i = 1; i <= 3; i++){
            System.out.print("    " + count[i] + "     ");
            
        }System.out.println();
    }//end print count

    public static void counttable(int [][] nextcells, int [] count){
        //this function updates count by counting the conditions in the table 
        for(int i = 0; i <= 9; i++)
            count[i] = 0;
        for(int irow = 0; irow <= 9; irow++)
            for(int jcol = 0; jcol <= 9; jcol++){
                count[nextcells[irow][jcol]]++;
            }
    }//end count table
            
    public static void calcstatistics(int [] count){
        double csum;
        double csum2;
        double average;
        double variance; 
        for(int i = 1; i <= 1; i++){
            csum = count[i];
            csum2 = count[i] * count[i];
            average = csum / 10;
            variance = csum2 / 10 - average * average;
            System.out.println("Average cells in healthy muscle mode: " + average);
            System.out.println("Variance of cells in healthy muscle mode: " + variance);
        }
        System.out.println();   
    }

}//end of class YHOW