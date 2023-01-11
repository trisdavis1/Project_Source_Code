
import java.io.*;
import java.io.IOException;
import javax.swing.*;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.util.NoSuchElementException;
import java.lang.*;

//this allows the threads to be created as objects from the Thread class
//this allows for the creation of a thread pool that can all be launched by
//one executor Executor
import java.util.concurrent.*;
import java.util.concurrent.locks.*;



public class EbayBidSystem {

    public static void main(String[] args)throws Exception {

        //create the printwriter for the tasks
        PrintWriter output;

        output = new PrintWriter(new File("THTBidEbayOut.txt"));

        //create the thread pool
        ExecutorService executor = Executors.newFixedThreadPool(6);

        //Now create and launch the item for sale thread and
        //the Bids threads
        ForSale painting = new ForSale("Dutch Masters Oil Painting", 5000.0, 60000.0,
                18490, 17050, 4827, output);

        //execute the bid object thread pool
        executor.execute(new MyBid(painting, 4827, 5283, 4000, 18481));
        executor.execute(new MyBid(painting, 4827, 4681, 15000, 18482));
        executor.execute(new MyBid(painting, 4827, 5283, 14500, 18483));
        executor.execute(new MyBid(painting, 4827, 5283, 17500, 18485));
        executor.execute(new MyBid(painting, 4827, 4681, 25000, 18489));
        executor.execute(new MyBid(painting, 4827, 5283, 32000, 18495));

        //shutdown the executor
        executor.shutdown();

        //now let all the threads shut down and wait till all tasks are finished
        while(!executor.isTerminated());
        System.out.println("ITEM SOLD!!");
        output.println("ITEM SOLD!!"); //write to the txt file
        System.out.println("Bidder " + painting.getBidNo() + " with a bid of " + painting.getBid());
        output.println("Bidder " + painting.getBidNo() + " with a bid of " + painting.getBid());//write to the txt file
        System.out.flush();
        output.flush();

    }

    public static class ForSale {

        private String itemDes; //this is the character string representing the item
        private double buyNow; //this is the price to buy the item now
        private double currBid; //this is the current bid on the object
        private double minBid; //this is the min opening bid on the object
        private int tDone; //this is the time that the auction will close
        private int ctime; //current time
        private int itemNo; //unique item num given to an item sold on ebay
        private int bidNo; //unique bidder number for the bidder holding the current highest bid
        private int sold; //item sold indicator 0 = not sold, 1 = sold
        PrintWriter output;

        //create the lock for this class
        private static Lock mybidlock = new ReentrantLock();

        //for sale constructor
        public ForSale(String item, double mbid, double buyit, int timed, int ct, int inum, PrintWriter outputf) {

            currBid = 0;

            itemDes = item;
            minBid = mbid;
            buyNow = buyit;
            tDone = timed;
            ctime = ct;
            itemNo = inum;
            output = outputf;

        }//end for sale constructor

        public double getBid() {
            return currBid;
        }

        public int getBidNo() {
            return bidNo;
        }

        public void myBid(int itemNo, int bidNo, double bidamt, int ctime) {
            //acquire lock for Bids
            mybidlock.lock();

            try {

                if(bidamt == buyNow){ //if the bidder pays the buy it now price they have won the bid

                    System.out.println("Great job " + bidNo + " you just bought " + itemNo +
                            ", " + itemDes + " for, " + bidamt + " amount!");

                    //Write to the txt file
                    output.println("Great job " + bidNo + " you just bought " + itemNo +
                            ", " + itemDes + " for, " + bidamt + " amount!");

                }else if(bidamt < minBid){ //a bidder cannot bid less than the min bid amount

                    System.out.println("Sorry " + bidNo + ", bid could not be processed... the minimum bid is "
                            + minBid);
                    System.out.println("You only bidded " + bidamt);

                    //Write to the txt file
                    output.println("Sorry " + bidNo + ", bid could not be processed... the minimum bid is "
                            + minBid);
                    output.println("You only bidded " + bidamt);

                }
                else if(bidamt < currBid){ //a bidder cannot bid less than the current bid on the item

                    System.out.println("Sorry " + bidNo + ", this is not enough for " + itemNo + ", "
                            + itemDes);

                    //Write to the txt file
                    output.println("Sorry " + bidNo + ", this is not enough for " + itemNo + ", "
                            + itemDes);

                }else if(ctime >= tDone){ //a bid can not be processed if it has been submitteed after the timedone

                    System.out.println("Sorry " + bidNo + " time has expired. Bid of " + bidamt
                            + " could not be processed");

                    //Write to the txt file
                    output.println("Sorry " + bidNo + " time has expired. Bid of " + bidamt
                            + " could not be processed");

                }else{
                    if(bidamt > currBid)
                        currBid = bidamt; //update the current bid amount
                    this.bidNo = bidNo; //associate bidder ids

                    System.out.println("Congratulations " + bidNo + ", you are currently the highest bidder for item "
                            + itemNo + ": " + itemDes + ", but keep watching good stuff goes fast on eBay!");

                    //Write to the txt file
                    output.println("Congratulations " + bidNo + ", you are currently the highest bidder for item "
                            + itemNo + ": " + itemDes + ", but keep watching good stuff goes fast on eBay!");
                }
                Thread.sleep(10);

            }//end of try block
            catch (InterruptedException ex) {

                System.out.println("Trouble with Bid System");
                output.println("Trouble with Bid System");

            }//end catch block

            output.flush();

            //release the lock to avoid race conditions
            mybidlock.unlock();

        }//end of myBid

    }//end class ForSale

    public static class MyBid implements Runnable {

        ForSale sale;
        private int itemno; //item id
        private int bidno; //bidder id
        private int bidamt; //bidder amount
        private int ctime; //current time bidder bid

        public MyBid(ForSale item, int inum, int bno, int bamt, int ct){

            sale = item;
            itemno = inum;
            bidno = bno;
            bidamt = bamt;
            ctime = ct;

        }//end of bid constructor

        public void run() {
            //now task the MyBid system
            sale.myBid(itemno, bidno, bidamt, ctime);

        }//end MyBid run task

    }//end of class MyBid

}//end class EbayBidSystem
