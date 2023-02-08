#include<windows.h>
#include<GL\glut.h>
#include<stdlib.h>
#include<math.h>
#include<cmath>
#include<conio.h>
#include<stdio.h>
#include <iostream>
#include <iomanip>

//using namespace std;

//void init(void);//this is a function to initialize the window clear color
void RenderScene(void);//this is a function to draw a function in an opened window
void SetupRC(void);//this function sets the clear color used to set the state of the OpenGL system
				   //Main Program

int main(int argc, char** argv)
{//set up window title
	char header[] = "graph of g(x)=x3+x2-20x, h(y)=10cos(y)+3, f(x)=g(x)-h(x) by Tristan Davis";
	// initialize the glopen utility toolkit
	glutInit(&argc, argv);
	// Set up the display mode with a single buffer and RGB colors
	glutInitDisplayMode(GLUT_SINGLE | GLUT_RGBA);
	//Initialize window size and position
	glutInitWindowSize(1016, 680);
	glutInitWindowPosition(0, 0);
	//  Open and Label Window
	glutCreateWindow(header);
	//Now point to the function that will draw the scene
	glutDisplayFunc(RenderScene);
	// Now set the state of the rendering machine
	SetupRC();
	// Now execute OpenGL using RenderScene with state as set in RenderScene
	glutMainLoop();
	return 0;
}
//****************************Function SetupRC*************************************
// Setup the rendering state
void SetupRC(void)
{// this function sets the clear color of an open window and clears the open window
 // Set clear color to lavender
	glClearColor(0.6f, 0.4f, 0.70f, 1.0f);

	return;
}//end of SetupRC 


 //*************************RenderScene Function*************************
void RenderScene(void)
{
	double g, h, f, x, y, xdel = 0.25;
	//clear the window 
	// Now clear the open window
	glClear(GL_COLOR_BUFFER_BIT);
	
	//note clear color was set inn SetupRC
	glLoadIdentity();
	//set the current drawing color to white
	glColor3f(1.0, 1.0, 1.0);
	//set the viewport to the window dimensions
	glViewport(100, 100, 900, 500);
	//Establish the clipping volumn in user units

	glOrtho(-11.0, 11.0, -11.0, 26.0, 1.0, -1.0);
	//  Draw x-axis and y-axis and place tic marks on each
	glBegin(GL_LINES);
	// Set end points of x-axis
	glVertex2f(-10.0, 0.0);//x left
	glVertex2f(10.0, 0.0);// x right
						  // Now put tic marks on the axis
	for (x = -10.0; x <= 10.0; x += 1.0)
	{
		glVertex2f(x, 0.0);
		glVertex2f(x, 0.5);
	};
	// Set end points of y-axis
	glVertex2f(0.0, -40.0);// y bottom
	glVertex2f(0.0, 40.0);// y top
						  //Now put tic marks on the axis
	for (y = -10.0; y <= 25.0; y += 1.0)
	{
		glVertex2f(-0.15, y);
		glVertex2f(0.15, y);
	};
	glEnd();
	// Now draw the cubic function
	glBegin(GL_LINE_STRIP);
	for (x = -10.0; x <= 10.0; x += xdel)
	{
		glColor3f(0.0, 0.0, 1.0);
		y = pow(x, 3) * pow(x, 2) - (20 * x);
		glVertex2f(x, y);
	};
	glEnd();
	//draw cosine function
	glBegin(GL_LINE_STRIP);
	for (x = -10.0; x <= 10.0; x += xdel)
	{
		glColor3f(1.0, 1.0, 0.0);
		y = 10 * cos(x) + 3;
		glVertex2f(x, y);
	};
	glEnd();
	//draw function
	glBegin(GL_LINE_STRIP);
	for (x = -10.0; x <= 10.0; x += xdel)
	{
		glColor3f(0.98, 0.04, 0.7);
		y = (pow(x, 3) * pow(x, 2) - (20 * x))-(10 * cos(x) + 3);
		glVertex2f(x, y);
	};
	glEnd();

	//clear all the buffers
	glFlush();
	return;
};//end of render scene