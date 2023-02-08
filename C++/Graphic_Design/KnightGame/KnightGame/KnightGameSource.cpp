#include<GL/glut.h>
#include<stdlib.h>
#include<math.h>
#include<conio.h>
#include<stdio.h>
#include <iostream>
#include<windows.h>
#include <iomanip>
#include <string>
using namespace std;


/********************************************Global Values****************************************
 these values are global because the timing call back functions will only take certian
 parameters therefore they must be global to communicate with these functions */


 /*************************** User manipulated variables and function prototypes ***************************/
int nextMoveKnight[2] = { 12, 12 }; //Knight's next move (each knight) this should be an integer value with a range of 0 to 11
float knightHealth[2] = { 1,1 }; //Knight's health (each)
//bool knightAlive[2] = { true, true };  //Knight is alive (true) or dead (false)
//float knightColor[2][3] = { .25,.95,.25, .95,.15,.25 };  // Defines colors of both knights
void getNextMoves0();  /* User function that generates the next moves for knight 0 */
//void getNextMoves1();  /* User function that generates the next moves for knight 1 */
//void adjucitation(); /* User function the calculates the health/alive status of both knights */
//float perceivedHealth[2] = { 0,0 };//Perceived Health

//int lastMoves[2][3] = { 12,12,12,12,12,12 }; // Last three moves, at beginning all are "stop"
/*
float pkProb[11][11][2] =
//															PLAYER 1
//	Lunge	Thrust		O/H			SRH			SLH			SRL			SLL			BRH			BLH			BRL			BLL
{ { {.3,.3},{.3,.2},	{.4,.1},	{.1,.2},	{.1,.2},	{.1,.15},	{.1,.15},	{.05,.0},	{.05,.0},	{.3,.0},	{.3,.0} },	//Lunge		P
{ {.2,.3},	{.3,.3},	{.3,.1},	{.3,.05},	{.3,.05},	{.2,.15},	{.2,.15},	{.3,.0},	{.3,.0},	{.05,.0},	{.05,.0} },	//Thrust	L
{ {.1,.4},	{.1,.3},	{.1,.1},	{.3,.2},	{.3,.2},	{.3,.1},	{.3,.1},	{.05,.0},	{.05,.0},	{.4,.0},	{.4,.0} },	//O/H		A
{ {.2,.1},	{.05,.3},	{.2,.3},	{.5,.5},	{.05,.05},	{.4,.3},	{.4,.3},	{.05,.0},	{.1,.0},	{.5,.0},	{.5,.0} },	//SRH		Y
{ {.2,.1},	{.05,.3},	{.2,.3},	{.05,.05},	{.5,.5},	{.4,.3},	{.4,.3},	{.1,.0},	{.05,.0},	{.5,.0},	{.5,.0} },	//SLH		E
{ {.15,.1},	{.15,.2},	{.1,.3},	{.3,.4},	{.3,.4},	{.2,.2},	{.15,.15},	{.2,.0},	{.2,.0},	{.05,.0},	{.1,.0} },	//SRL		R
{ {.15,.1},	{.15,.2},	{.1,.3},	{.3,.4},	{.3,.4},	{.15,.15},	{.3,.3},	{.2,.0},	{.2,.0},	{.1,.0},	{.05,.0} },	//SLL		
{ {.0,.05},	{.0,.3},	{.0,.05},	{.0,.05},	{.0,.1},	{.0,.2},	{.0,.2},	{.0,.0},	{.0,.0},	{.0,.0},	{.0,.0} },	//BRH		0
{ {.0,.05},	{.0,.3},	{.0,.05},	{.0,.1},	{.0,.05},	{.0,.2},	{.0,.2},	{.0,.0},	{.0,.0},	{.0,.0},	{.0,.0} },	//BLH
{ {.0,.3},	{.0,.05},	{.0,.4},	{.0,.5},	{.0,.5},	{.0,.05},	{.0,.1},	{.0,.0},	{.0,.0},	{.0,.0},	{.0,.0} },	//BRL
{ {.0,.3},	{.0,.05},	{.0,.4},	{.0,.5},	{.0,.5},	{.0,.1},	{.0,.05},	{.0,.0},	{.0,.0},	{.0,.0},	{.0,.0} } };//BLL

void adjucitation() {
    //Sample adjucitation...just decrease health untill one is dead
    if (nextMoveKnight[0] < 11 && nextMoveKnight[1] < 11) {
        knightHealth[0] -= pkProb[nextMoveKnight[0]][nextMoveKnight[1]][1] / 4.0;
        knightHealth[1] -= pkProb[nextMoveKnight[0]][nextMoveKnight[1]][0] / 4.0;

        if (pkProb[nextMoveKnight[0]][nextMoveKnight[1]][1] > rand() / (RAND_MAX + 1.0))
            knightHealth[0] = 0;
        if (pkProb[nextMoveKnight[0]][nextMoveKnight[1]][0] > rand() / (RAND_MAX + 1.0))
            knightHealth[1] = 0;
    }
    if (knightHealth[0] <= 0) { knightAlive[0] = false; }
    if (knightHealth[1] <= 0) { knightAlive[1] = false; }

}


string moves[13] = { "Lung", "Thrust", "O/H Bash", "Slash Right High", "Slash Left High", "Slash Right Low", "Slash Left Low", "Block Right High", "Block Left High", "Block Right Low", "Block Left Low", "Move", "Stop" };
*/
//					  Lng,Thr,O/H,SRH,SLH,SRL,SLL,BRH,BLH,BRL,BLL,Mov,Stp
int states[13][13] = { 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1,  //Lung Matrix move table
                       0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, //Thrust
                       0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, //O/H
                       0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, //SRH
                       0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, //SLH
                       0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, //SRL
                       0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, //SLL
                       1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, //BRH
                       1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, //BLH
                       0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, //BRL
                       0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, //BLL
                       1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, //Move
                       1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };//Stop


void getNextMoves0() {
    int me = 0;
    int prevMove = nextMoveKnight[me];
 
    nextMoveKnight[me] = 0;
    while (knightHealth != 0) {
        //nextMoveKnight[me] = (rand() % 12);
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                //if next move valid
                if (states[i][j] == 1) {
                    nextMoveKnight[me] = i;
                    prevMove = j;
                }
                else {
                    i++;
                    j++;
                }
            }
        }

    }

}//end of getNextMoves0