package com.company;

//This program demonstrates Recursive functions. Demonstrating a Factorial series; N! = N(N-1)!,
//and a Fibonacci series; Fib(0) = 0, Fib(1) = 1, and Fib(index) = Fib(index - 1) + Fib(index - 2).
//Throughout this program we see the evaluation of values recursively.

public class TestRecursion {

    public static void main(String[] args){

        System.out.println("Testing recursive functions...");
        System.out.println("--------------------------------------------");

        //test values to test and print out recursive functions
        System.out.println("Computing Nfactorial number at 12 terms is: " + NFactorial(12));
        System.out.println("Computing Nfactorial number at 25 terms is: " + NFactorial(25));
        System.out.println("Computing Nfactorial number at -5 terms is: " + NFactorial(-5));
        System.out.println("Computing Fib number at 5 terms is: " + Fib(5));
        System.out.println("Computing Fib number at 12 terms is: " + Fib(12));
        System.out.println("Computing Fib number at 1 terms is: " + Fib(1));
        System.out.println("Computing Fib number at 8 terms is: " + Fib(8));

    }//end of main

    //define recursive factorial
    public static int NFactorial(int factorial){

        //this method is a recursive method calculating factorial of i terms where each term is (i - 1)
        System.out.println("In NFactorial calculating for factorial " + factorial);

        int N;

        //this is the stopping condition for the recursive function.
        //it is necessary for each recursive function
        //this is the 1st part of of the recursion process; if the stopping condition is met, we return.
        if(factorial < 0){

            //N or the factorial can only take on positive integer values N>=0 and 0!=1
            System.out.println("Cannot have negative factorial...");
            System.out.println("Factorial is Undefined...");
            return factorial;

        //0! = 1
        }else if(factorial == 0){

            System.out.println("In NFactorial returning factorial 0 = 1");

            return 1;

        }else{//if there is any body of the function it goes here as follows

            N = NFactorial(factorial - 1);//this is the recursive call with the iterator decremented: RECURSE (N-1)

            //this is where we finish our recursion; update our value and return
            System.out.println("Evaluation of " + factorial + " factorial for N " + (factorial - 1)+ " = " + N + "("
                    + factorial + ")");

            return N * factorial;
        }
    }//end of NFactorial

    //define recursive Fibonacci
    public static int Fib(int index){

        System.out.println("In Fib calculating for Fibonacci number " + index);

        int n;

        //Fib(0) = 0
        //Fib(1) = 1
        if(index == 0 || index == 1){

            System.out.println("In Fib returning fibonacci number ");

            return index;

        }else{

            n = Fib(index - 2);

            System.out.println("Evaluation of " + index + " fibonacci for n " + (index - 2) + " = " + n + " + ("
                    + index + " - 1)");

            return n + (index - 1);
        }
    }//end of Fib
}//end of TestRecursion
