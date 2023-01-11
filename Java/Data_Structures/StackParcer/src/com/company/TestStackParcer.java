package com.company;

//This program demonstrates a parcer for the following binary operators @, %, *, /, +,-
//Parenthesis expression to be evaluated. All things in the () are evaluated first
//Then the order of operations are @ first, then %, *, / second and then +, - last

public class TestStackParcer {

    public static void main(String[] args) throws Exception {

        GenericManagerStack<Integer> stack = new GenericManagerStack<Integer>();

        int i, ival, ival1;

        //lets create a stack
        //lets push some numbers on the stack

        System.out.println("Pushing on 37");
        stack.pushnode(37);
        System.out.println("Pushing on 44");
        stack.pushnode(44);
        System.out.println("Pushing on 23");
        stack.pushnode(23);

        //now lets print some stack statistics

        for(i = 0; i <= 2; i++){

            System.out.println("We have the following stack number " + stack .getNumber());
            System.out.println("Peek " + stack.peeknode());
            System.out.println("Pop node " + stack.popnode());

        }

        //lets add more to the stack
        System.out.println("The stack is empty, we hope, lets check what number it is " + stack.getNumber());
        System.out.println("Lets add more to the stack...\r\n");

        stack.pushnode(45);
        stack.pushnode(23);
        stack.pushnode(17);
        stack.pushnode(21);
        stack.pushnode(76);

        for(i = 0; i <= 4; i++){

            System.out.println("We have the following stack number " + stack.getNumber());
            System.out.println("Peek " + stack.peeknode());
            System.out.println("Pop node " + stack.popnode());

        }

        System.out.println("This is the end of the GenericManagerStack Test ... test is Generic.");

        //*** Stack test complete ***

        //create array that contains all the character variables we might see in the expression
        char[] vart = {'A', 'B', 'C', 'D', 'E', 'F', '0','1', '2', '3', '4', '5', '6', '7', '8', '9'};
        //create an array that contains all the equivalent integer values of variables
        int[] ivalue = {8, 12, 2, 15, 3, 4, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        //create operator table array, contains all the character variables we might see for operator
        //symbols in the given expression
        char[] opert = {'@', '*', '/', '%', '+', '-', ')', '(', '#'};
        //create array that contains equivalent priority evaluation of the symbol
        int[] intvalpriority = {3, 2, 2, 2, 1, 1, 99, -99, -100};

        //create stacks for the integer operands
        //create stacks for the operators

        GenericManagerStack<Integer> opnd = new GenericManagerStack<Integer>();

        GenericManagerStack<Opertobject> oper = new GenericManagerStack<Opertobject>();

        GenericManagerStack<Integer> opnd1 = new GenericManagerStack<Integer>();

        GenericManagerStack<Opertobject> oper1 = new GenericManagerStack<Opertobject>();

        //create the operator object and push it on the stack
        System.out.println("Pushing operator # with priority -100");

            Opertobject pnode1 = new Opertobject('#', -100);
            Opertobject pnode2 = new Opertobject('#', -100);

            oper.pushnode(pnode1);
            oper.pushnode(pnode2);

        int oprior;
        int exvalue;

        int oprior1;
        int exvalue1;

        //create test expressions to evaluate parser

        char[] express = {'A', '@', '(', '2', '*', '(', 'A', '-', 'C', '*', 'D', ')', ')', '+', '(', '9', '*', 'B',
            '/', '(', '2', '*', 'C', '+', '1', ')', '-', 'B', '*', '3', ')', '+', 'E', '%', '(', 'F', '-', 'A', ')','#'};

        char[] express1 = {'B', '*', '(', '3', '@', '(', 'A', '-', 'D', ')', '%', '(', 'B', '-', 'C', '@', 'D', ')',
                ')', '+', '4', '@', 'D', '*', '2', '#'};

        i = 0;

        while(express[i] != '#'){

            System.out.println("Parsing... " + express[i]);

            if(((express[i] >= '0')&& (express[i] <= '9')) || ((express[i] >= 'A') && (express[i] <= 'F'))) {

            //check to see if this character is a variable or an operator \

                System.out.println("This is an operand " + express[i]);
                //find the character in the vart table that corresponds with the value
                ival = findVal(express[i], vart, ivalue, 15);

                if (ival == -99)
                    System.out.println("No value in the table for " + express[i]);

                //now that we have the values we need to place it on the operand stack

                System.out.println("Were pushing it onto the operand stack " + ival);

                opnd.pushnode(ival);

            }else{
                    System.out.println("This is an operator " + express[i]);
                    //left paranthesis push
                    if(express[i] == '('){

                        System.out.println("Pushing on operator stack " + express[i]);

                        //create a node on the stack
                        Opertobject pnodeo = new Opertobject(express[i], -99);
                        oper.pushnode(pnodeo);

                    }else
                        //right parenthesis push
                        if(express[i] == ')'){

                        while((oper.peeknode().operator != '(')){

                            popevalandpush(oper, opnd);

                        }
                        oper.popnode();

                    }else {
                            oprior = findVal(express[i], opert, intvalpriority, 5);
                            System.out.println("Peeking at the top of the stack " + (oper.peeknode()).priority);

                            while (oprior <= (oper.peeknode()).priority) {

                                popevalandpush(oper, opnd);
                            }
                            //push operator on the stack
                            System.out.println("Pushing operator " + express[i] + " with priority " + oprior);

                            Opertobject pnodeo = new Opertobject(express[i], oprior);
                            oper.pushnode(pnodeo);
                    }

            }//end of on operator
            i++;
        }//end of while express loop

        //we have found the # in the evaluation now we must evaluate the operator stack

        while((oper.peeknode()).operator != '#'){

            popevalandpush(oper, opnd);
        }

        exvalue = opnd.popnode();
        System.out.println("The value of this expression is " + exvalue + "\r\n");
        System.out.println("------------------------------------\r\n");

    }//end of main

    public static int intEval(int oper1, char oper, int oper2) {

    //this is an evaluator for binary operators operating on integers

        int result;

        switch (oper){
            case '+':
                result = oper1 + oper2;

                System.out.println("***Evaluate: " + oper1 + oper + oper2 + "**Result = " + result);

                return result;

            case '-':
                result = oper1 - oper2;

                System.out.println("***Evaluate: " + oper1 + oper + oper2 + "**Result = " + result);

                return result;

            case '*':
                result = oper1 * oper2;

                System.out.println("***Evaluate: " + oper1 + oper + oper2 + "**Result = " + result);

                return result;

            case '/':
                if (oper2 != 0) {

                    result = oper1 / oper2;

                    System.out.println("***Evaluate: " + oper1 + oper + oper2 + "**Result = " + result);

                    return result;

                } else {
                    System.out.println("Attempted to divide by zero not allowed");

                    return -99;
                }
                default:
                System.out.println("bad operator " + oper);

                return -99;

            case '%':

                result = oper1 % oper2;

                System.out.println("***Evaluate: " + oper1 + oper + oper2 + "**Result = " + result);

                return result;


            case '@':
                if(oper2 != 0){

                    result = (int) Math.pow(oper1, oper2);

                    System.out.println("***Evaluate: " + oper1 + oper + oper2 + "**Result = " + result);

                    return result;

                }else{

                    result = 1;

                    return result;

                }

        }//end of switch
    }//end of intEval

    public static int findVal(char x, char [] vtab, int [] valtb, int last){

        int i;
        int vreturn = -99;

        //find the character x in the value table vtab and returns the
        //corresponding integer value table from valtb

        for(i = 0; i <= last; i++)
            if(vtab[i] == x)
                vreturn = valtb[i];

                System.out.println("Found this char value " + x + " is " + vreturn);

                return vreturn;

    }//end of findval

    public static void popevalandpush(GenericManagerStack<Opertobject> x, GenericManagerStack<Integer> y){
        //start of pop and push
        int a, b, c;

        char operandx;

        operandx = (x.popnode()).Getopert();

            a = y.popnode();
            b = y.popnode();

        System.out.println("in pop Eval " + b + operandx + a);

        c = intEval(b, operandx, a);

        //push the value back on the stack for integers
        y.pushnode(c);

        return;

    }//end popevalandpush

}//end of TestStackParcer
