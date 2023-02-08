#include<windows.h>
#include<GL/glut.h>
#include<stdlib.h>
#include<math.h>
#include<conio.h>
#include<stdio.h>
#include <iostream>
#include <iomanip>
#include <gl/glut.h>
using namespace std;

// This is CubeandBiezierStudentdemo.cpp

/* This program demonstrates rendering a three dimensional polyman in OpenGL.  The program renders the polyman in solid form
using the function "Enable(GL_DEPTH_TEST)" to activate the z-buffer to hide hidden surfaces.  The
surfaces of the square are rendered by glBegin(GL_POLYGON).  All faces have been loaded counterclockwise.

It also demmonstrates a Biezier path for the polyman to follow.  The Biezier path is generated with 8 control points along the
path from the Biezier function clacbiezu(float, int, float[])


/* These values are global because the timing call back functions will only take certain parameters
hence their needs to be global variables to communicate with these functions */
//global angular value for rotation
float static theta = 0.0;
float static theta2 = 0.0;
float static theta3 = 0.0;
float scale1 = 1.0;//global scaling value for polyman
float dx = 0.0, dy = 0.0, dz = 0.0;//global movement value for dx, dy, and dz/
float xctrl[8], yctrl[8], uval = 0.0; // these are for Biezier Control points for the path for the of the polyman.
float calcbiezu(float, int, float[]);//calclated biez at a point u 
int fact(int);//calclates factorial

void init(void);//this is a function to initialize the window clear color
void RenderScene(void);//this is a function to draw a polyman in an opened window
/*	 loads the polyman icon   */
void loadicon(float[][7], float[][7], float[][7]);

/*   draws the icon       */
void drawicon(float[][7], float[][7], float[][7]);

void settrans2(void);/* sets the translation matrix for the polyman
		  transformation matrix for desired scale, rotation,new pos*/


void SetupRC(void);//sets up the clear color
void TimerFunction(int);//this call back function is call each 30 ms and changes the location,scale and rotation
// of the square.

//Main Program

int main(int argc, char** argv)
{//set up window title


	char header[] = "Biezier Polyman Movement by Tristan Davis";



	glutInit(&argc, argv);
	// Set up the display mode with a double buffer and a depth buffer and RGB colors
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
	SetupRC();
	//Initialize window size and position
	glutInitWindowSize(540, 440);
	glutInitWindowPosition(0, 0);
	//  Open and Label Window	
	glutCreateWindow(header);
	glutDisplayFunc(RenderScene);
	glutTimerFunc(500, TimerFunction, 1);
	//Now draw the scene

	glutMainLoop();

	return 0;
}
//*************************RenderScene Function*************************
void RenderScene(void)
{

	float x[10][7], y[10][7], z[10][7]; /*  these variables hold the pattern for the polyman icon.  
										Note that x,y,z hold the polyman  */
	float x1, y1, xdel = 0.25;
	float Uval;// Biezier u value going from  0 to 1 to drive the polyman.  The polyman values are x(u), y(u)
	// Set Up AThe Control Points---->

	xctrl[0] = 1.0; yctrl[0] = 10.0;//left end point
	xctrl[1] = 8.0; yctrl[1] = 2.0;//point 1
	xctrl[1] = 9.0; yctrl[1] = 3.0;//point 2
	xctrl[2] = 6.0; yctrl[2] = 11.0;//point 3
	xctrl[3] = 5.0; yctrl[3] = 12.0;//point 4
	xctrl[4] = 7.0; yctrl[4] = 8.0;//point 5
	xctrl[5] = 1.0; yctrl[5] = 8.0;//point 6
	xctrl[6] = 3.0; yctrl[6] = 10.0;//point 7
	xctrl[7] = 10.0; yctrl[7] = 1.5;//right end point
	int ncontrolpts = 8, i;
	//clear the window with the current background color
	cout << "in renderscene" << endl;
	glClearColor(0.2, 0.2, 1.0, 1.0);//set clear color to blue
	glClear(GL_COLOR_BUFFER_BIT);//note clear color was set inn SetupRC
	glEnable(GL_DEPTH_TEST);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	glLoadIdentity();
	//set the current drawing color to white
	glColor3f(1.0, 1.0, 1.0);
	//set the viewport to the window dimensions
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	//set the viewport to the window dimensions
	glViewport(0, 0, 540, 440);
	//Establish the clipping volume in user coordinates
	glOrtho(-2.0, 14.0, -2.0, 14.0, 5.0, -5.0);
	//************The following draws the biez curve created by the control points and the axis for the control points************

	//set the drawing color to white
	glColor3f(1.0, 1.0, 1.0);
	//  Draw x-axis and y-axis and place tic marks on each
	glBegin(GL_POLYGON);
	//draw ski slope
	glVertex2f(0.5, 0.5);
	glVertex2f(0.5, 9.5);
	glVertex2f(9.5, 0.5);

	glEnd();

	glBegin(GL_LINES);
	// Set end points of x-axis
	glVertex2f(-1.0, 0.0);//x left
	glVertex2f(10.0, 0.0);// x right
	// Now put tic marks on the axis
	for (x1 = -1.0; x1 <= 10.0; x1 += 1.0)
	{
		glVertex2f(x1, 0.0);
		glVertex2f(x1, 0.5);
	};
	// Set end points of y-axis
	glVertex2f(0.0, -1.0);// y bottom
	glVertex2f(0.0, 10.0);// y top
	//Now put tic marks on the axis
	for (y1 = -1.0; y1 <= 10.0; y1 += 1.0)
	{
		glVertex2f(-0.15, y1);
		glVertex2f(0.15, y1);
	};
	glEnd();

	// now draw the control points
	glPointSize(5.0);
	//loop through all the points
	glBegin(GL_POINTS);
	for (i = 0; i < ncontrolpts; i++)glVertex2f(xctrl[i], yctrl[i]);
	glEnd();
	// Draw the biezer curve for the polyman to follow----->
	// change the draw to red
	glColor3f(1.0, 0.0, 0.0);
	glBegin(GL_LINE_STRIP);
	Uval = 0.0;
	for (i = 0; i <= 20; i++)
	{//calculate the x,y coordinates for this uval
		glVertex2f(calcbiezu(Uval, 7, xctrl), calcbiezu(Uval, 7, yctrl));
		Uval += 0.05;
	}
	glEnd();
	//************** This concludes the drawing of the biez curve**************
	 //
	loadicon(x, y, z);
	/*     draw the polyman  NOTE: The biezer X(u), Y(u) points are calculated in settrans2()    */


	settrans2();
	//now draw the polyman
	drawicon(x, y, z);


	glEnd();

	glutSwapBuffers();


	return;

};//end of render scene

//******************************Load Icon Function************************************
void loadicon(float x[][7], float y[][7], float z[][7])
/*   This procedure loads a polyman icon             */
{/* load front face*/
	x[0][0] = -1.125; y[0][0] = 0.0; z[0][0] = 0.5;
	x[0][1] = -0.625; y[0][1] = -0.75; z[0][1] = 0.5;
	x[0][2] = 0.625; y[0][2] = -0.75; z[0][2] = 0.5;
	x[0][3] = 1.125; y[0][3] = 0.0; z[0][3] = 0.5;
	x[0][4] = 0.625; y[0][4] = 0.75; z[0][4] = 0.5;
	x[0][5] = -0.625; y[0][5] = 0.75; z[0][5] = 0.5;
	/* load the color on the front face red*/
	x[0][6] = 0.0; y[0][6] = 1.0; z[0][6] = 0.0;

	/* load the right side (x) face*/
	x[1][0] = 1.125; y[1][0] = 0.0; z[1][0] = 0.5;
	x[1][1] = 0.625; y[1][1] = -0.75; z[1][1] = 0.5;
	x[1][2] = 0.625; y[1][2] = -0.75; z[1][2] = -0.5;
	x[1][3] = 1.125; y[1][3] = 0.0; z[1][3] = -0.5;
	x[1][4] = 0.625; y[1][4] = 0.75; z[1][4] = -0.5;
	x[1][5] = 0.625; y[1][5] = 0.75; z[1][5] = 0.5;
	/* load the color on the front face red*/
	x[1][6] = 0.0; y[1][6] = 1.0; z[1][6] = 0.5;

	/* load the back side face */
	x[2][0] = -1.125; y[2][0] = 0.0; z[2][0] = -0.5;
	x[2][1] = -0.625; y[2][1] = -0.75; z[2][1] = -0.5;
	x[2][2] = 0.625; y[2][2] = -0.75; z[2][2] = -0.5;
	x[2][3] = 1.125; y[2][3] = 0.0; z[2][3] = -0.5;
	x[2][4] = 0.625; y[2][4] = 0.75; z[2][4] = -0.5;
	x[2][5] = -0.625; y[2][5] = 0.75; z[2][5] = -0.5;
	/* load the color on the front face red*/
	x[2][6] = 0.0; y[2][6] = 1.0; z[2][6] = 0.0;

	/* load the left side x face */
	x[3][0] = -1.125; y[3][0] = 0.0; z[3][0] = -0.5;
	x[3][1] = -0.625; y[3][1] = -0.75; z[3][1] = -0.5;
	x[3][2] = -0.625; y[3][2] = -0.75; z[3][2] = 0.5;
	x[3][3] = -1.125; y[3][3] = 0.0; z[3][3] = 0.5;
	x[3][4] = -0.625; y[3][4] = 0.75; z[3][4] = 0.5;
	x[3][5] = -0.625; y[3][5] = 0.75; z[3][5] = -0.5;
	/*load the color on the back side blue */
	x[3][6] = 0.0; y[3][6] = 1.0; z[3][6] = 0.5;

	/*load the top side*/
	x[4][0] = 0.625; y[4][0] = 0.75; z[4][0] = 0.5;
	x[4][1] = 0.625; y[4][1] = 0.75; z[4][1] = -0.5;
	x[4][2] = -0.625; y[4][2] = 0.75; z[4][2] = -0.5;
	x[4][3] = -0.625; y[4][3] = 0.75; z[4][3] = 0.5;
	/* load the color on the top  black */
	x[4][4] = 0.0; y[4][4] = 0.5; z[4][4] = 0.0;

	/*load the bottom side */
	x[5][0] = 0.625; y[5][0] = -0.75; z[5][0] = 0.5;
	x[5][1] = 0.625; y[5][1] = -0.75; z[5][1] = -0.5;
	x[5][2] = -0.625; y[5][2] = -0.75; z[5][2] = -0.5;
	x[5][3] = -0.625; y[5][3] = -0.75; z[5][3] = 0.5;
	/* load the color on the top  black */
	x[5][4] = 0.0; y[5][4] = 0.5; z[5][4] = 0.0;

	/* load front eye*/
	x[6][0] = -0.5; y[6][0] = 0.5; z[6][0] = 0.51;
	// load color to black
	x[6][1] = 0.0; y[6][1] = 0.0; z[6][1] = 0.0;

	/* load back eye*/
	x[7][0] = -0.5; y[7][0] = 0.5; z[7][0] = -0.51;
	// load color to black
	x[7][1] = 0.0; y[7][1] = 0.0; z[7][1] = 0.0;

	/*load front leg */
	x[8][0] = 0.0; y[8][0] = -0.5; z[8][0] = 0.49;
	x[8][1] = 0.0; y[8][1] = -1.0; z[8][1] = 0.49;
	x[8][2] = -1.0;	 y[8][2] = -1.0; z[8][2] = 0.49;
	x[8][3] = 1.0; y[8][3] = -1.0; z[8][3] = 0.49;
	/* load the color black */
	x[8][4] = 0.0; y[8][4] = 0.0; z[8][4] = 0.0;

	/*load back leg */
	x[9][0] = 0.0; y[9][0] = -0.5; z[9][0] = -0.49;
	x[9][1] = 0.0; y[9][1] = -1.0; z[9][1] = -0.49;
	x[9][2] = -1.0;	 y[9][2] = -1.0; z[9][2] = -0.49;
	x[9][3] = 1.0; y[9][3] = -1.0; z[9][3] = -0.49;
	/* load the color black */
	x[9][4] = 0.0; y[9][4] = 0.0; z[9][4] = 0.0;

	return;
}    /*     end of load icon       */

/************************* function drawicon  ***************************/
void drawicon(float x[][7], float y[][7], float z[][7])
{
	/*     this function draws the polyman at the transformed position              */
	int i, face;
	
	for (face = 0; face <= 3; face++)
	{// note that the cube is with front face counterclockwise

		// render each face of the cube
		glColor3f(x[face][6], y[face][6], z[face][6]);
		glBegin(GL_POLYGON);

		for (i = 0; i <= 5; i++) glVertex3f(x[face][i], y[face][i], z[face][i]);

		glEnd();

	}
	for (face = 4; face <= 5; face++) {
		glColor3f(x[face][4], y[face][4], z[face][4]);
		glBegin(GL_POLYGON);

		for (i = 0; i <= 3; i++) glVertex3f(x[face][i], y[face][i], z[face][i]);

		glEnd();
	}
	for (face = 6; face <= 7; face++) {
		glColor3f(x[face][1], y[face][1], z[face][1]);
		glPointSize(4.0);
		glBegin(GL_POINTS);
			glVertex3f(x[face][0], y[face][0], z[face][0]);
		glEnd();
	}
	for (face = 8; face <= 9; face++) {
		glColor3f(x[face][4], y[face][4], z[face][4]);
		//glPointSize(4.0);
		glBegin(GL_LINES);
		for (i = 0; i <= 3; i++) glVertex3f(x[face][i], y[face][i], z[face][i]);
		glEnd();
	}
	
	glFlush();
	
	return;
}//end of draw icon

/**************************  function settrans2  ***********************/
void settrans2(void)

//Sets the translation matrix for the cube
{
	cout << "in settrans2" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	// Set the Biezier location for the x,y, draw dx(uval), dy(uval), The annimation for movement is in the TimerFunction
	dx = calcbiezu(uval, 7, xctrl);
	dy = calcbiezu(uval, 7, yctrl);
	glTranslatef(dx, dy, dz);
	glRotatef(theta, 0.0, 1.0, 1.0);// note that the angle theta is in degrees, not radians
	glRotatef(-45.0, 0.0, 0.0, 1.0);//offsets the polyman rotation 45 degrees along the Z-axis 
	glRotatef(180.0, 0.0, 1.0, 0.0);//offsets the polyman rotation 180 along the Y-axis
	return;

}

//****************************Function SetupRC*************************************
// Setup the rendering state
void SetupRC(void)
{// this function sets the clear color of an open window and clears the open window
// Set clear color to green
	glClearColor(0.0, 0.0, 0.0, 1.0);

	return;
}//end of SetupRC

/******************************** Functioner Timer****************************************/
void TimerFunction(int value)
//this call back function is call each 30 ms and changes the location,scale and rotation
// The uval is used to update the Biezier positon of the Cube x(uval),y(uaval)
// The calls to the Biezier function are in settrans2() of the polyman.
{
	//rotate the polyman
	theta += 5.0;

	if (dy <= 7.4) {
		theta = 0.0;
	}
	
	uval += 0.01;
	if (uval >= 1.0) uval = 1.0;

	// Redraw the scene with new coordinates
	glutPostRedisplay();
	glutTimerFunc(33, TimerFunction, 1);
}
float calcbiezu(float u, int n, float cp[])
{//This function calculates the biezier value at u for the control points cp
	float val = 0.0;
	int i;
	for (i = 0; i <= n; i++)
	{
		val += cp[i] * float(fact(n)) / float((fact(i) * fact(n - i))) * pow(u, float(i)) * pow(float(1.0 - u), float(n - i));
	}
	return val;
}

int fact(int n)
{
	// Variable Declaration
	//This function calculates n

	int counter, fct = 1;
	if (n == 0)return 1;

	//for Loop Block
	for (int counter = 1; counter <= n; counter++)
	{
		fct = fct * counter;
	}
	return fct;
}
