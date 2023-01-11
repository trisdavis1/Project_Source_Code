//This is a program that demonstrates abstract classes in java
//Class shape is an abstract class
//From it class solids inherits area, volume, and name
//From class Solids the classes Cubes, Sphere, Cone, Brick, and TruncCones are built
// Class Solids implements a comparable for subclasses stated above


import java.io.*;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.*;
import java.util.NoSuchElementException;

public class TestProb1 {
    public static void main(String[] args) throws Exception {
        //equate the name (outpt) to an external file through PrintWriter
        PrintWriter outpt;

        outpt = new PrintWriter(new File("SolidsOut.txt"));
        int i;

        //create your Solids

        Cubes cubes1 = new Cubes(4, 4, 4, outpt);
        Cubes cubes2 = new Cubes(8, 8, 8, outpt);
        Sphere sphere1 = new Sphere(0, 0, 0, 3, outpt);
        Sphere sphere2 = new Sphere(0, 0, 0, 6, outpt);
        Cone cone1 = new Cone(0, 0, 6, 5, outpt);
        Cone cone2 = new Cone(0, 0, 12, 3, outpt);
        Brick brick1 = new Brick(3, 6, 9, outpt);
        Brick brick2 = new Brick(2, 4, 6, outpt);
        TruncCones tcone1 = new TruncCones(0, 0, 6, 5, 3, outpt);
        TruncCones tcone2 = new TruncCones(0, 0, 4, 8, 6, outpt);

        outpt.println("These are the solids");
        System.out.println("These are the solids");


        //print Solids area and volume to external file



        //store Solids into array mySolids of type Solids

        Solids[] mySolids = new Solids[10];
        mySolids[0] = cubes1;
        mySolids[1] = cubes2;
        mySolids[2] = sphere1;
        mySolids[3] = sphere2;
        mySolids[4] = cone1;
        mySolids[5] = cone2;
        mySolids[6] = brick1;
        mySolids[7] = brick2;
        mySolids[8] = tcone1;
        mySolids[9] = tcone2;

        // print Solids area and volume

        for(i = 0; i <= 9; i++)
            mySolids[i].calcarea();
        for(i = 0; i <= 9; i++)
            mySolids[i].calcvol();

        cubes1.printsolid(outpt);
        cubes2.printsolid(outpt);
        sphere1.printsolid(outpt);
        sphere2.printsolid(outpt);
        cone1.printsolid(outpt);
        cone2.printsolid(outpt);
        brick1.printsolid(outpt);
        brick2.printsolid(outpt);
        tcone1.printsolid(outpt);
        tcone2.printsolid(outpt);

        //Calculate total area and volume of all Solids

        double totalArea = 0;
        double totalVol = 0;

        for (i = 0; i < mySolids.length; i++)
            totalArea += mySolids[i].area;
        outpt.println("The Total Area of all solids is: " + totalArea);
        System.out.println("The Total Area of all solids is: " + totalArea);


        for (i = 0; i < mySolids.length; i++)
            totalVol += mySolids[i].volume;
        outpt.println("The Total Volume of all solids is: " + totalVol);
        System.out.println("The Total Volume of all solids is: " + totalVol);

        //Calculate Maximum and Minimum area and volume

        double maxArea = mySolids[0].area;
        for (int j = 0; j < mySolids.length; j++) {
            if (mySolids[j].area > maxArea) {
                maxArea = mySolids[j].area;
            }
        }
        outpt.println("The Maximum Area is: " + maxArea);
        System.out.println("The Maximum Area is: " + maxArea);

        double minArea = mySolids[0].area;
        for (int j = 0; j < mySolids.length; j++) {
            if (mySolids[j].area < minArea) {
                minArea = mySolids[j].area;
            }
        }
        outpt.println("The Minimum Area is: " + minArea);
        System.out.println("The Minimum Area is: " + minArea);

        //call bubble sort sortVol

        sortVol(mySolids);
        //print array after sorted, greatest to smallest
        outpt.println("Array after Sort: ");
        System.out.println("Array after Sort:");
        for(int k = 0; k < mySolids.length; k++){
            System.out.print(mySolids[k].volume + " ");
            outpt.println(mySolids[k].volume);
        }

        //close PrintWriter
        //Must close PrintWriter or results will not print to external file
        outpt.close();
    }

    //Define bubble sort method sortVol
    // sort volume from greatest volume to smallest volume

    public static void sortVol(Solids[] mySolids){
        //method sorts through the array of mySolids
        // the array from first array_length - 1 position and compare the element with the next one.
        // Element is swapped with the next element if the next element is smaller.
        double sortvol;

        for(int k = 0; k < mySolids.length; k++){
            for(int j=1; j < (mySolids.length-k); j++){

                if(mySolids[j-1].volume < mySolids[j].volume){
                    //swap the elements
                    sortvol = mySolids[j-1].volume;
                    mySolids[j-1].volume = mySolids[j].volume;
                    mySolids[j].volume = sortvol;
                }

            }
        }
    }
} //end of main