using namespace std;

#include<windows.h>
#include<GL/glut.h>
#include<stdlib.h>
#include<math.h>
#include<conio.h>
#include<stdio.h>
#include <iostream>
#include <iomanip>
#include <gl/glut.h>

/*This program demonstrates a three dimensional wire function in OpenGL.
The wire frame is then rotated about the x and then the y axis.
The polyman/polywoman has been loaded with only sides.  Hidden lines are not used in this program even though
the z-buffer is envolked.
*********** Global values************************************************
These values are global because the timing call back functions will only take certain parameters
hence their needs to be global variables to communicate with these functions */
float static theta = 0.0;//global angular value for rotation
float static thetal = 0.0;
float static thetag = 0.0;
float static thetalw = 0.0;
float static thetam = 0.0;
float static thetaw = 0.0;
float static thetasq = 0.0;
float static thetals = 0.0;
float scale1 = 1.0;//global scaling value for polyman
float scale2 = 1.0;//polywoman
float scale3 = 1.0;//squareman
float dx = 5.0, dy = -3.0, dz = 0.0;//global movement value for x and y axis/
float dxl = 5.0, dyl = -3.0, dzl = 0.0;
float dxw = -6.0, dyw = -3.0, dzw = 0.0;
float dxlw = -6.0, dylw = -3.0, dzlw = 0.0;
float dxs = 8.0, dys = -3.0, dzs = 0.0;
float dxls = 8.0, dyls = -3.0, dzls = 0.0;
int frame = 1.0;

void init(void);//this is a function to initialize the window clear color
void RenderScene(void);//this is a function to draw polyman and polywoman in an opened window
/*	 loads the polypeople icons            */
void loadicon(float[][7], float[][7], float[][7], float[][7], float[][7], float[][7],
				float[][5], float[][5], float[][5], float [], float [], float []);
/*draws the icon  */
void drawicon(float[][7], float[][7], float[][7]);
void drawiconlegs(float[][7], float[][7], float[][7]);
void drawiconw(float[][7], float[][7], float[][7]);
void drawiconlegsw(float[][7], float[][7], float[][7]);
void drawiconsq(float [][5], float[][5], float[][5], float [], float [], float []);
void drawiconsql(float [][5], float [][5], float [][5]);
void settrans6(void);
void settrans5(void);
void settrans4(void);
void settrans3(void);
void settrans2(void);
void settrans1(void);

/* sets the translation matrix for the polypeople transformation matrix for desired scale, rotation,new pos*/


 /*performs the transformation on the icons pattern      */

void SetupRC(void);//sets up the clear color
void TimerFunction(int);//this call back function is call each 30 ms and changes the location,scale and rotation
						// of the polypeople.

//Main Program

int main(int argc, char** argv)
{//set up window title


	char header[] = "Polyman/woman Squareman by Tristan Davis";

	glutInit(&argc, argv);
	// Set up the display mode with a single buffer and RGB colors
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
	//Initialize window size and position
	glutInitWindowSize(560, 440);
	glutInitWindowPosition(140, 20);
	//Initialize  background color in window to black
	SetupRC();
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
	float xdel = 0.25;

	float x[23][7], y[23][7], z[23][7]; /*  these variables hold the
											pattern for the polypeople icons.             */
	float x1[14][7], y1[14][7], z1[14][7];

	float x2[14][5], y2[14][5], z2[14][5], xl[2], yl[2], zl[2];

	//clear the window with the current background color
	cout << "in renderscene" << endl;
	//set the current drawing color to white
	glColor3f(1.0, 1.0, 1.0);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	//set the viewport to the window dimensions
	glViewport(0, 0, 540, 440);
	//Establish the clipping volume in user coordinates
	glOrtho(-7.0, 7.0, -7.0, 7.0, 5.0, -5.0);
	loadicon(x, y, z, x1, y1, z1, x2, y2, z2, xl, yl, zl);
	/*     draw the icon untransformed      */

	glEnable(GL_DEPTH_TEST);

	// Clear the window with the background color
	glClearColor(0.5, 0.5, 0.5, 0.0);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	//set the current drawing color to white
	//set the modeview matrixes for the polypeople
	settrans1();
	drawiconlegs(x, y, z);

	settrans2();
	drawicon(x, y, z);

	settrans4();
	drawiconlegsw(x1, y1, z1);

	settrans3();
	drawiconw(x1, y1, z1);

	settrans5();
	drawiconsq(x2, y2, z2, xl, yl, zl);

	settrans6();
	drawiconsql(x2, y2, z2);

	glEnd();

	glutSwapBuffers();


	return;

};//end of render scene
  //******************************Load Icon Function************************************
void loadicon(float x[][7], float y[][7], float z[][7], float x1[][7], float y1[][7], float z1[][7],
					float x2[][5], float y2[][5], float z2[][5], float xl[], float yl[], float zl[]) 
/*   this procedure loads the polypeople icons Note that x, y, and z; x1, y1, and z1 hold the parameters of the
icons.  They have been structured x[face][vertex] to hold the top and
bottom vertices of the icon.          */
{
	/****************************** load the polyman ******************************/
		/* load front face*/
	x[0][0] = -1.125; y[0][0] = 0.0; z[0][0] = 0.5;
	x[0][1] = -0.625; y[0][1] = -0.75; z[0][1] = 0.5;
	x[0][2] = 0.625; y[0][2] = -0.75; z[0][2] = 0.5;
	x[0][3] = 1.125; y[0][3] = 0.0; z[0][3] = 0.5;
	x[0][4] = 0.625; y[0][4] = 0.75; z[0][4] = 0.5;
	x[0][5] = -0.625; y[0][5] = 0.75; z[0][5] = 0.5;
	/* load the color on the front face green*/
	x[0][6] = 0.0; y[0][6] = 1.0; z[0][6] = 0.0;

	/* load the right side (x) face*/
	x[1][0] = 1.125; y[1][0] = 0.0; z[1][0] = 0.5;
	x[1][1] = 0.625; y[1][1] = -0.75; z[1][1] = 0.5;
	x[1][2] = 0.625; y[1][2] = -0.75; z[1][2] = -0.5;
	x[1][3] = 1.125; y[1][3] = 0.0; z[1][3] = -0.5;
	x[1][4] = 0.625; y[1][4] = 0.75; z[1][4] = -0.5;
	x[1][5] = 0.625; y[1][5] = 0.75; z[1][5] = 0.5;
	/* load the color on the front face green*/
	x[1][6] = 0.0; y[1][6] = 1.0; z[1][6] = 0.5;

	/* load the back side face */
	x[2][0] = -1.125; y[2][0] = 0.0; z[2][0] = -0.5;
	x[2][1] = -0.625; y[2][1] = -0.75; z[2][1] = -0.5;
	x[2][2] = 0.625; y[2][2] = -0.75; z[2][2] = -0.5;
	x[2][3] = 1.125; y[2][3] = 0.0; z[2][3] = -0.5;
	x[2][4] = 0.625; y[2][4] = 0.75; z[2][4] = -0.5;
	x[2][5] = -0.625; y[2][5] = 0.75; z[2][5] = -0.5;
	/* load the color on the front face green*/
	x[2][6] = 0.0; y[2][6] = 1.0; z[2][6] = 0.0;

	/* load the left side x face */
	x[3][0] = -1.125; y[3][0] = 0.0; z[3][0] = -0.5;
	x[3][1] = -0.625; y[3][1] = -0.75; z[3][1] = -0.5;
	x[3][2] = -0.625; y[3][2] = -0.75; z[3][2] = 0.5;
	x[3][3] = -1.125; y[3][3] = 0.0; z[3][3] = 0.5;
	x[3][4] = -0.625; y[3][4] = 0.75; z[3][4] = 0.5;
	x[3][5] = -0.625; y[3][5] = 0.75; z[3][5] = -0.5;
	/*load the color on the left side green */
	x[3][6] = 0.0; y[3][6] = 1.0; z[3][6] = 0.5;

	/*load the top side*/
	x[4][0] = 0.625; y[4][0] = 0.75; z[4][0] = 0.5;
	x[4][1] = 0.625; y[4][1] = 0.75; z[4][1] = -0.5;
	x[4][2] = -0.625; y[4][2] = 0.75; z[4][2] = -0.5;
	x[4][3] = -0.625; y[4][3] = 0.75; z[4][3] = 0.5;
	/* load the color on the top green */
	x[4][4] = 0.0; y[4][4] = 0.5; z[4][4] = 0.0;

	/*load the bottom side */
	x[5][0] = 0.625; y[5][0] = -0.75; z[5][0] = 0.5;
	x[5][1] = 0.625; y[5][1] = -0.75; z[5][1] = -0.5;
	x[5][2] = -0.625; y[5][2] = -0.75; z[5][2] = -0.5;
	x[5][3] = -0.625; y[5][3] = -0.75; z[5][3] = 0.5;
	/* load the color on the bottom  green */
	x[5][4] = 0.0; y[5][4] = 0.5; z[5][4] = 0.0;

	/* load front eye*/
	x[6][0] = -0.5; y[6][0] = 0.5; z[6][0] = 0.51;
	// load color to black
	x[6][1] = 0.0; y[6][1] = 0.0; z[6][1] = 0.0;
	/* load back eye*/
	x[7][0] = -0.5; y[7][0] = 0.5; z[7][0] = -0.51;
	// load color to black
	x[7][1] = 0.0; y[7][1] = 0.0; z[7][1] = 0.0;

	//left leg
	x[8][0] = -0.25;  y[8][0] = -0.5;  z[8][0] = 0.5;
	x[8][1] = -0.25;  y[8][1] = -1.0;  z[8][1] = 0.5;
	x[8][2] = -0.5;   y[8][2] = -1.0;  z[8][2] = 0.5;
	//load color
	x[8][3] = 0.0;  y[8][3] = 0.0;  z[8][3] = 0.0;
	//shadow point of left leg
	x[9][0] = -0.25;  y[9][0] = -0.5;  z[9][0] = -0.5;
	x[9][1] = -0.25;  y[9][1] = -1.0;  z[9][1] = -0.5;
	x[9][2] = -0.5;   y[9][2] = -1.0;  z[9][2] = -0.5;
	//load color
	x[9][3] = 0.0;  y[9][3] = 0.0;  z[9][3] = 0.0;

	//right leg
	x[10][0] = 0.25;   y[10][0] = -0.5;  z[10][0] = 0.5;
	x[10][1] = 0.25;   y[10][1] = -1.0;  z[10][1] = 0.5;
	x[10][2] = 0.0;    y[10][2] = -1.0;  z[10][2] = 0.5;
	//load color
	x[10][3] = 0.0;  y[10][3] = 0.0;  z[10][3] = 0.0;

	//shadow point of right leg
	x[11][0] = 0.25;   y[11][0] = -0.5;  z[11][0] = -0.5;
	x[11][1] = 0.25;   y[11][1] = -1.0;  z[11][1] = -0.5;
	x[11][2] = 0.0;    y[11][2] = -1.0;  z[11][2] = -0.5;
	//load color
	x[11][3] = 0.0;  y[11][3] = 0.0;  z[11][3] = 0.0;
	//panels connecting legs--->break legs into sections (leg/foot)
	//left foot
	x[12][0] = -0.25;   y[12][0] = -1.0;  z[12][0] = 0.5;
	x[12][1] = -0.5;	y[12][1] = -1.0;  z[12][1] = 0.5;
	x[12][2] = -0.5;	y[12][2] = -1.0;  z[12][2] = -0.5;
	x[12][3] = -0.25;   y[12][3] = -1.0;  z[12][3] = -0.5;
	//color
	x[12][4] = 0.0;    y[12][4] = 0.0;  z[12][4] = 0.0;

	//right foot 
	x[13][0] = 0.25;   y[13][0] = -1.0;  z[13][0] = 0.5;
	x[13][1] = 0.0;    y[13][1] = -1.0;  z[13][1] = 0.5;
	x[13][2] = 0.0;    y[13][2] = -1.0;  z[13][2] = -0.5;
	x[13][3] = 0.25;   y[13][3] = -1.0;  z[13][3] = -0.5;
	//color
	x[13][4] = 0.0;    y[13][4] = 0.0;  z[13][4] = 0.0;

	/********* OPEN MOUTH COORD  *************/
	/* load the right side (x) face*/
	x[14][0] = 1.125; y[14][0] = 0.0; z[14][0] = 0.5;
	x[14][1] = 0.625; y[14][1] = -0.75; z[14][1] = 0.5;
	x[14][2] = 0.625; y[14][2] = -0.75; z[14][2] = -0.5;
	x[14][3] = 1.125; y[14][3] = 0.0; z[14][3] = -0.5;
	x[14][4] = 0.625; y[14][4] = 0.75; z[14][4] = -0.5;
	x[14][5] = 0.625; y[14][5] = 0.75; z[14][5] = 0.5;
	/* load the color on the front face red*/
	x[14][6] = 0.0; y[14][6] = 1.0; z[14][6] = 0.5;

	/* load the bottom front side*/
	x[15][0] = -0.375; y[15][0] = 0.0; z[15][0] = 0.5;
	x[15][1] = -0.875; y[15][1] = -0.5; z[15][1] = 0.5;
	x[15][2] = -0.625; y[15][2] = -0.75; z[15][2] = 0.5;
	x[15][3] = 0.625; y[15][3] = -0.75; z[15][3] = 0.5;
	x[15][4] = 1.125; y[15][4] = 0.0; z[15][4] = 0.5;
	//color bottom front side
	x[15][5] = 0.0; y[15][5] = 1.0; z[15][5] = 0.0;

	/* load the bottom back side*/
	x[16][0] = -0.375; y[16][0] = 0.0; z[16][0] = -0.5;
	x[16][1] = -0.875; y[16][1] = -0.5; z[16][1] = -0.5;
	x[16][2] = -0.625; y[16][2] = -0.75; z[16][2] = -0.5;
	x[16][3] = 0.625; y[16][3] = -0.75; z[16][3] = -0.5;
	x[16][4] = 1.125; y[16][4] = 0.0; z[16][4] = -0.5;
	//color bottom back side
	x[16][5] = 0.0; y[16][5] = 1.0; z[16][5] = 0.0;

	//load top front side face 
	x[17][0] = -1.125; y[17][0] = 0.0; z[17][0] = 0.5;
	x[17][1] = 1.125; y[17][1] = 0.0; z[17][1] = 0.5;
	x[17][2] = 0.625; y[17][2] = 0.75; z[17][2] = 0.5;
	x[17][3] = -0.625; y[17][3] = 0.75; z[17][3] = 0.5;
	/* load the color on the front face green*/
	x[17][4] = 0.0; y[17][4] = 1.0; z[17][4] = 0.0;

	//load top back side face
	x[18][0] = -1.125; y[18][0] = 0.0; z[18][0] = -0.5;
	x[18][1] = 1.125; y[18][1] = 0.0; z[18][1] = -0.5;
	x[18][2] = 0.625; y[18][2] = 0.75; z[18][2] = -0.5;
	x[18][3] = -0.625; y[18][3] = 0.75; z[18][3] = -0.5;
	/* load the color on the front face green*/
	x[18][4] = 0.0; y[18][4] = 1.0; z[18][4] = 0.0;

	/* load the left side x face */
	x[19][0] = -0.625; y[19][0] = 0.75; z[19][0] = -0.5;
	x[19][1] = -1.125; y[19][1] = 0.0; z[19][1] = -0.5;
	x[19][2] = -1.125; y[19][2] = 0.0; z[19][2] = 0.5;
	x[19][3] = -0.625; y[19][3] = 0.75; z[19][3] = 0.5;
	//color forehead
	x[19][4] = 0.0; y[19][4] = 1.0; z[19][4] = 0.5;

	x[20][0] = -0.375; y[20][0] = 0.0; z[20][0] = -0.5;
	x[20][1] = -0.375; y[20][1] = 0.0; z[20][1] = 0.5;
	x[20][2] = -1.125; y[20][2] = 0.0; z[20][2] = 0.5;
	x[20][3] = -1.125; y[20][3] = 0.0; z[20][3] = -0.5;
	//color top mouth
	x[20][4] = 1.0; y[20][4] = 0.0; z[20][4] = 0.0;

	x[21][0] = -0.875; y[21][0] = -0.5; z[21][0] = -0.5;
	x[21][1] = -0.875; y[21][1] = -0.5; z[21][1] = 0.5;
	x[21][2] = -0.375; y[21][2] = 0.0; z[21][2] = 0.5;
	x[21][3] = -0.375; y[21][3] = 0.0; z[21][3] = -0.5;
	//color bottom mouth
	x[21][4] = 1.0; y[21][4] = 0.0; z[21][4] = 0.0;

	x[22][0] = -0.625; y[22][0] = -0.75; z[22][0] = -0.5;
	x[22][1] = -0.625; y[22][1] = -0.75; z[22][1] = 0.5;
	x[22][2] = -0.875; y[22][2] = -0.5; z[22][2] = 0.5;
	x[22][3] = -0.875; y[22][3] = -0.5; z[22][3] = -0.5;
	//color chin
	x[22][4] = 0.0; y[22][4] = 1.0; z[22][4] = 0.5;

	//end drawing of polyman

	/****************************** load the polywoman ******************************/
	/* load front face*/
	x1[0][0] = -1.125; y1[0][0] = 0.0; z1[0][0] = 0.5;
	x1[0][1] = -0.625; y1[0][1] = -0.75; z1[0][1] = 0.5;
	x1[0][2] = 0.625; y1[0][2] = -0.75; z1[0][2] = 0.5;
	x1[0][3] = 1.125; y1[0][3] = 0.0; z1[0][3] = 0.5;
	x1[0][4] = 0.625; y1[0][4] = 0.75; z1[0][4] = 0.5;
	x1[0][5] = -0.625; y1[0][5] = 0.75; z1[0][5] = 0.5;
	/* load the color on the front face red*/
	x1[0][6] = 1.0; y1[0][6] = 0.0; z1[0][6] = 0.0;

	/* load the right side (x) face*/
	x1[1][0] = 1.125; y1[1][0] = 0.0; z1[1][0] = 0.5;
	x1[1][1] = 0.625; y1[1][1] = -0.75; z1[1][1] = 0.5;
	x1[1][2] = 0.625; y1[1][2] = -0.75; z1[1][2] = -0.5;
	x1[1][3] = 1.125; y1[1][3] = 0.0; z1[1][3] = -0.5;
	x1[1][4] = 0.625; y1[1][4] = 0.75; z1[1][4] = -0.5;
	x1[1][5] = 0.625; y1[1][5] = 0.75; z1[1][5] = 0.5;
	/* load the color on the front face red*/
	x1[1][6] = 1.0; y1[1][6] = 0.0; z1[1][6] = 0.5;

	/* load the back side face */
	x1[2][0] = -1.125; y1[2][0] = 0.0; z1[2][0] = -0.5;
	x1[2][1] = -0.625; y1[2][1] = -0.75; z1[2][1] = -0.5;
	x1[2][2] = 0.625; y1[2][2] = -0.75; z1[2][2] = -0.5;
	x1[2][3] = 1.125; y1[2][3] = 0.0; z1[2][3] = -0.5;
	x1[2][4] = 0.625; y1[2][4] = 0.75; z1[2][4] = -0.5;
	x1[2][5] = -0.625; y1[2][5] = 0.75; z1[2][5] = -0.5;
	/* load the color on the front face red*/
	x1[2][6] = 1.0; y1[2][6] = 0.0; z1[2][6] = 0.0;

	/* load the left side x face */
	x1[3][0] = -1.125; y1[3][0] = 0.0; z1[3][0] = -0.5;
	x1[3][1] = -0.625; y1[3][1] = -0.75; z1[3][1] = -0.5;
	x1[3][2] = -0.625; y1[3][2] = -0.75; z1[3][2] = 0.5;
	x1[3][3] = -1.125; y1[3][3] = 0.0; z1[3][3] = 0.5;
	x1[3][4] = -0.625; y1[3][4] = 0.75; z1[3][4] = 0.5;
	x1[3][5] = -0.625; y1[3][5] = 0.75; z1[3][5] = -0.5;
	/*load the color on the back side red */
	x1[3][6] = 1.0; y1[3][6] = 0.0; z1[3][6] = 0.5;

	/*load the top side*/
	x1[4][0] = 0.625; y1[4][0] = 0.75; z1[4][0] = 0.5;
	x1[4][1] = 0.625; y1[4][1] = 0.75; z1[4][1] = -0.5;
	x1[4][2] = -0.625; y1[4][2] = 0.75; z1[4][2] = -0.5;
	x1[4][3] = -0.625; y1[4][3] = 0.75; z1[4][3] = 0.5;
	/* load the color on the top  red */
	x1[4][4] = 0.5; y1[4][4] = 0.0; z1[4][4] = 0.0;

	/*load the bottom side */
	x1[5][0] = 0.625; y1[5][0] = -0.75; z1[5][0] = 0.5;
	x1[5][1] = 0.625; y1[5][1] = -0.75; z1[5][1] = -0.5;
	x1[5][2] = -0.625; y1[5][2] = -0.75; z1[5][2] = -0.5;
	x1[5][3] = -0.625; y1[5][3] = -0.75; z1[5][3] = 0.5;
	/* load the color on the top red */
	x1[5][4] = 0.5; y1[5][4] = 0.0; z1[5][4] = 0.0;

	/* load front eye*/
	x1[6][0] = -0.5; y1[6][0] = 0.5; z1[6][0] = 0.52;
	// load color to black
	x1[6][1] = 0.0; y1[6][1] = 0.0; z1[6][1] = 0.0;
	/* load back eye*/
	x1[7][0] = -0.5; y1[7][0] = 0.5; z1[7][0] = -0.52;
	// load color to black
	x1[7][1] = 0.0; y1[7][1] = 0.0; z1[7][1] = 0.0;

	//left leg
	x1[8][0] = -0.25;  y1[8][0] = -0.5;  z1[8][0] = 0.5;
	x1[8][1] = -0.25;  y1[8][1] = -1.0;  z1[8][1] = 0.5;
	x1[8][2] = -0.5;   y1[8][2] = -1.0;  z1[8][2] = 0.5;
	//load color
	x1[8][3] = 0.0;  y1[8][3] = 0.0;  z1[8][3] = 0.0;
	//shadow point of left leg
	x1[9][0] = -0.25;  y1[9][0] = -0.5;  z1[9][0] = -0.5;
	x1[9][1] = -0.25;  y1[9][1] = -1.0;  z1[9][1] = -0.5;
	x1[9][2] = -0.5;   y1[9][2] = -1.0;  z1[9][2] = -0.5;
	//load color
	x1[9][3] = 0.0;  y1[9][3] = 0.0;  z1[9][3] = 0.0;

	//right leg
	x1[10][0] = 0.25;   y1[10][0] = -0.5;  z1[10][0] = 0.5;
	x1[10][1] = 0.25;   y1[10][1] = -1.0;  z1[10][1] = 0.5;
	x1[10][2] = 0.0;    y1[10][2] = -1.0;  z1[10][2] = 0.5;
	//load color
	x1[10][3] = 0.0;  y1[10][3] = 0.0;  z1[10][3] = 0.0;

	//shadow point of right leg
	x1[11][0] = 0.25;   y1[11][0] = -0.5;  z1[11][0] = -0.5;
	x1[11][1] = 0.25;   y1[11][1] = -1.0;  z1[11][1] = -0.5;
	x1[11][2] = 0.0;    y1[11][2] = -1.0;  z1[11][2] = -0.5;
	//load color
	x1[11][3] = 0.0;  y1[11][3] = 0.0;  z1[11][3] = 0.0;
	//panels connecting legs--->break legs into sections (leg/foot)
	//left foot
	x1[12][0] = -0.25;   y1[12][0] = -1.0;  z1[12][0] = 0.5;
	x1[12][1] = -0.5;	y1[12][1] = -1.0;  z1[12][1] = 0.5;
	x1[12][2] = -0.5;	y1[12][2] = -1.0;  z1[12][2] = -0.5;
	x1[12][3] = -0.25;   y1[12][3] = -1.0;  z1[12][3] = -0.5;
	//color
	x1[12][4] = 0.0;    y1[12][4] = 0.0;  z1[12][4] = 0.0;

	//right foot 
	x1[13][0] = 0.25;   y1[13][0] = -1.0;  z1[13][0] = 0.5;
	x1[13][1] = 0.0;    y1[13][1] = -1.0;  z1[13][1] = 0.5;
	x1[13][2] = 0.0;    y1[13][2] = -1.0;  z1[13][2] = -0.5;
	x1[13][3] = 0.25;   y1[13][3] = -1.0;  z1[13][3] = -0.5;
	//color
	x1[13][4] = 0.0;    y1[13][4] = 0.0;  z1[13][4] = 0.0;
	//end draw of polywoman

	/**********************  load the squareman  *************************/
	/* load front face*/
	x2[0][0] = 0.0; y2[0][0] = 1.0; z2[0][0] = 1.0;
	x2[0][1] = -1.0; y2[0][1] = 0.0; z2[0][1] = 1.0;
	x2[0][2] = 0.0; y2[0][2] = -1.0; z2[0][2] = 1.0;
	x2[0][3] = 1.0; y2[0][3] = 0.0; z2[0][3] = 1.0;
	/* load the color on the front face red*/
	x2[0][4] = 0.0; y2[0][4] = 0.0; z2[0][4] = 1.0;

	/* load the right side (x) face*/
	x2[1][0] = 1.0; y2[1][0] = 0.0; z2[1][0] = 1.0;
	x2[1][1] = 0.0; y2[1][1] = -1.0; z2[1][1] = 1.0;
	x2[1][2] = 0.0; y2[1][2] = -1.0; z2[1][2] = -1.0;
	x2[1][3] = 1.0; y2[1][3] = 0.0; z2[1][3] = -1.0;
	/* load the color on the right side face green */
	x2[1][4] = 0.0; y2[1][4] = 0.5; z2[1][4] = 1.0;

	/* load the back side face */
	x2[2][0] = 0.0; y2[2][0] = 1.0; z2[2][0] = -1.0;
	x2[2][1] = -1.0; y2[2][1] = 0.0; z2[2][1] = -1.0;
	x2[2][2] = 0.0; y2[2][2] = -1.0; z2[2][2] = -1.0;
	x2[2][3] = 1.0; y2[2][3] = 0.0; z2[2][3] = -1.0;
	/*load the color on the back side blue */
	x2[2][4] = 0.0; y2[2][4] = 0.0; z2[2][4] = 1.0;

	/* load the left side x face */
	x2[3][0] = -1.0; y2[3][0] = 0.0; z2[3][0] = 1.0;
	x2[3][1] = 0.0; y2[3][1] = 1.0; z2[3][1] = 1.0;
	x2[3][2] = 0.0; y2[3][2] = 1.0; z2[3][2] = -1.0;
	x2[3][3] = -1.0; y2[3][3] = 0.0; z2[3][3] = -1.0;
	/* load the color on the back side white */
	x2[3][4] = 0.0; y2[3][4] = 0.5; z2[3][4] = 1.0;

	/*load the top side*/
	x2[4][0] = 0.0; y2[4][0] = 1.0; z2[4][0] = 1.0;
	x2[4][1] = 1.0; y2[4][1] = 0.0; z2[4][1] = 1.0;
	x2[4][2] = 1.0; y2[4][2] = 0.0; z2[4][2] = -1.0;
	x2[4][3] = 0.0; y2[4][3] = 1.0; z2[4][3] = -1.0;
	/* load the color on the top  black */
	x2[4][4] = 0.0; y2[4][4] = 0.0; z2[4][4] = 0.5;

	/*load the bottom side */
	x2[5][0] = -1.0; y2[5][0] = 0.0; z2[5][0] = 1.0;
	x2[5][1] = 0.0; y2[5][1] = -1.0; z2[5][1] = 1.0;
	x2[5][2] = 0.0; y2[5][2] = -1.0; z2[5][2] = -1.0;
	x2[5][3] = -1.0; y2[5][3] = 0.0; z2[5][3] = 1.0;
	/* load the color on bottom dark blue */
	x2[5][4] = 0.0; y2[5][4] = 0.0; z2[5][4] = 0.5;

	/*load the pos left leg */
	x2[6][0] = -0.25; y2[6][0] = -0.5; z2[6][0] = 1.0;
	x2[6][1] = -0.25; y2[6][1] = -1.25; z2[6][1] = 1.0;
	x2[6][2] = 0.0; y2[6][2] = -1.25; z2[6][2] = 1.0;
	//color pos left leg black
	x2[6][3] = 0.0; y2[6][3] = 0.0; z2[6][3] = 0.0;
	//neg left leg
	x2[7][0] = -0.25; y2[7][0] = -0.5; z2[7][0] = -1.0;
	x2[7][1] = -0.25; y2[7][1] = -1.25; z2[7][1] = -1.0;
	x2[7][2] = 0.0; y2[7][2] = -1.25; z2[7][2] = -1.0;
	//color pos left leg black
	x2[7][3] = 0.0; y2[7][3] = 0.0; z2[7][3] = 0.0;

	/*load the pos right leg */
	x2[8][0] = 0.25; y2[8][0] = -0.5; z2[8][0] = 1.0;
	x2[8][1] = 0.25; y2[8][1] = -1.25; z2[8][1] = 1.0;
	x2[8][2] = 0.5; y2[8][2] = -1.25; z2[8][2] = 1.0;
	//color pos right leg black
	x2[8][3] = 0.0; y2[8][3] = 0.0; z2[8][3] = 0.0;
	//neg right leg
	x2[9][0] = 0.25; y2[9][0] = -0.5; z2[9][0] = -1.0;
	x2[9][1] = 0.25; y2[9][1] = -1.25; z2[9][1] = -1.0;
	x2[9][2] = 0.5; y2[9][2] = -1.25; z2[9][2] = -1.0;
	//color neg right leg black
	x2[9][3] = 0.0; y2[9][3] = 0.0; z2[9][3] = 0.0;

	/*load and connect the left feet */
	x2[10][0] = -0.25; y2[10][0] = -1.25; z2[10][0] = 1.0;
	x2[10][1] = 0.0; y2[10][1] = -1.25; z2[10][1] = 1.0;
	x2[10][2] = 0.0; y2[10][2] = -1.25; z2[10][2] = -1.0;
	x2[10][3] = -0.25; y2[10][3] = -1.25; z2[10][3] = -1.0;
	/* color foot black */
	x2[10][4] = 0.0; y2[10][4] = 0.0; z2[10][4] = 0.0;

	/*load and connect the right feet */
	x2[11][0] = 0.25; y2[11][0] = -1.25; z2[11][0] = 1.0;
	x2[11][1] = 0.5; y2[11][1] = -1.25; z2[11][1] = 1.0;
	x2[11][2] = 0.5; y2[11][2] = -1.25; z2[11][2] = -1.0;
	x2[11][3] = 0.25; y2[11][3] = -1.25; z2[11][3] = -1.0;
	/* color foot black */
	x2[11][4] = 0.0; y2[11][4] = 0.0; z2[11][4] = 0.0;

	/* load front eye*/
	x2[12][0] = 0.25; y2[12][0] = 0.5; z2[12][0] = 1.0;
	// load color to black
	x2[12][1] = 0.0; y2[12][1] = 0.0; z2[12][1] = 0.0;
	/* load back eye*/
	x2[13][0] = 0.25; y2[13][0] = 0.5; z2[13][0] = -1.0;
	// load color to black
	x2[13][1] = 0.0; y2[7][1] = 0.0; z2[13][1] = 0.0;

	/*load the line for sword  */
	xl[0] = -0.25; yl[0] = -0.25; zl[0] = 1.0;
	xl[1] = 1.25; yl[1] = 1.25; zl[1] = 1.0;
	//end of draw squareman
	return;
}    /*     end of load icon       */

	 /************************* function drawicon  ***************************/

void drawicon(float x[][7], float y[][7], float z[][7])
{
	/*     this function draws the polyman icon at the transformed position              */
	int i, face;

	for (face = 0; face <= 3; face++)
	{
		if (dx != 1.0) {
			glColor3f(x[face][6], y[face][6], z[face][6]);
			//redraw the polygon
			glBegin(GL_POLYGON);
			// note the colored polygon must be redrawn to render it.
			//first point is where the line intersects the top part of the polyman
			for (i = 0; i <= 5; i++)
				glVertex3f(x[face][i], y[face][i], z[face][i]);
			// close the face using the first point
			glVertex3f(x[face][0], y[face][0], z[face][0]);
			glEnd();
			glFlush();
		}
	}
	for (face = 4; face <= 5; face++)
	{
		glColor3f(x[face][4], y[face][4], z[face][4]);
		//redraw the polygon
		glBegin(GL_POLYGON);
		for (i = 0; i <= 3; i++)
			glVertex3f(x[face][i], y[face][i], z[face][i]);
		// close the face using the first point
		glVertex3f(x[face][0], y[face][0], z[face][0]);
		glEnd();
		glFlush();

	}
	for (face = 6; face <= 7; face++) {
		glColor3f(x[face][1], y[face][1], z[face][1]);
		//draw eyes of polyman
		glPointSize(4.0);
		glBegin(GL_POINTS);
		glVertex3f(x[face][0], y[face][0], z[face][0]);
		glEnd();
		glFlush();

	}
	if (dx == 1.0) {
		face = 14;
		glColor3f(x[face][6], y[face][6], z[face][6]);
		//redraw the polygon
		glBegin(GL_POLYGON);
		for (i = 0; i <= 5; i++)
			glVertex3f(x[face][i], y[face][i], z[face][i]);
		// close the face using the first point
		glVertex3f(x[face][0], y[face][0], z[face][0]);
		glEnd();
		glFlush();
	}
	for (face = 15; face <= 16; face++) {
		if (dx == 1.0) {
			glColor3f(x[face][5], y[face][5], z[face][5]);
			//redraw the polygon
			glBegin(GL_POLYGON);
			for (i = 0; i <= 4; i++)
				glVertex3f(x[face][i], y[face][i], z[face][i]);
			//close the face using the first point
			glVertex3f(x[face][0], y[face][0], z[face][0]);
			glEnd();
			glFlush();
		}
	}
	for (face = 17; face <= 22; face++) {
		if (dx == 1.0) {
			glColor3f(x[face][4], y[face][4], z[face][4]);
			//redraw the polygon
			glBegin(GL_POLYGON);
			for (i = 0; i <= 3; i++)
				glVertex3f(x[face][i], y[face][i], z[face][i]);
			// close the face using the first point
			glVertex3f(x[face][0], y[face][0], z[face][0]);
			glEnd();
			glFlush();
		}
	}
	glFlush();
	return;
}//end of draw icon

void drawiconw(float x1[][7], float y1[][7], float z1[][7])
{
	/*     this function draws the polywoman icon at the transformed position              */
	int i, face;

	for (face = 0; face <= 3; face++)
	{
		glColor3f(x1[face][6], y1[face][6], z1[face][6]);
		glShadeModel(GL_FLAT);
		//redraw the polygon

		glBegin(GL_POLYGON);
		for (i = 0; i <= 5; i++)
			glVertex3f(x1[face][i], y1[face][i], z1[face][i]);
		// close the face using the first point
		glVertex3f(x1[face][0], y1[face][0], z1[face][0]);
		glEnd();
		glFlush();

	}
	for (face = 4; face <= 5; face++)
	{
		glColor3f(x1[face][4], y1[face][4], z1[face][4]);
		//redraw the polygon
		glBegin(GL_POLYGON);
		for (i = 0; i <= 3; i++)
			glVertex3f(x1[face][i], y1[face][i], z1[face][i]);
		// close the face using the first point
		glVertex3f(x1[face][0], y1[face][0], z1[face][0]);
		glEnd();
		glFlush();

	}
	for (face = 6; face <= 7; face++) {
		glColor3f(x1[face][1], y1[face][1], z1[face][1]);
		//draw eyes of polywoman
		glPointSize(4.0);
		glBegin(GL_POINTS);
		glVertex3f(x1[face][0], y1[face][0], z1[face][0]);
		glEnd();
		glFlush();
	}
	glFlush();

	return;
}//end of draw icon

void drawiconsq(float x2[][5], float y2[][5], float z2[][5], float xl[], float yl[], float zl[])
{
	/*     this function draws the cube at the transformed position              */
	int i, face;

	for (face = 0; face <= 5; face++)
	{
		// render each face of the cube
		glColor3f(x2[face][4], y2[face][4], z2[face][4]);
		glBegin(GL_POLYGON);
		for (i = 0; i <= 3; i++)
			glVertex3f(x2[face][i], y2[face][i], z2[face][i]);
		glVertex3f(x2[face][0], y2[face][0], z2[face][0]);
		glEnd();
		glFlush();
	}
	for (face = 12; face <= 13; face++) {
		glColor3f(x2[face][1], y2[face][1], z2[face][1]);
		//draw eyes of squareman
		glPointSize(4.0);
		glBegin(GL_POINTS);
		glVertex3f(x2[face][0], y2[face][0], z2[face][0]);
		glEnd();
		glFlush();
	}
	glColor3f(0.0, 0.0, 0.0);
	//render the line through the cube
	glBegin(GL_LINES);
	glVertex3f(xl[0], yl[0], zl[0]);
	glVertex3f(xl[1], yl[1], zl[1]);
	glEnd();
	return;
}//end of draw icon

void drawiconlegs(float x[][7], float y[][7], float z[][7]) {

	int i, face;

	/*************  legs for polyman *************/
	for (face = 8; face <= 11; face++) {
		glColor3f(x[face][3], y[face][3], z[face][3]);
		glBegin(GL_LINE_STRIP);
		//now draw the legs
		for (i = 0; i <= 2; i++)
			glVertex3f(x[face][i], y[face][i], z[face][i]);
		glEnd();
		glFlush();
	}
	for (face = 12; face <= 13; face++) {

		glColor3f(x[face][4], y[face][4], z[face][4]);

		glBegin(GL_LINE_STRIP);
		//now draw the panels connecting feet 
		for (i = 0; i <= 3; i++)
			glVertex3f(x[face][i], y[face][i], z[face][i]);

		glVertex3f(x[face][0], y[face][0], z[face][0]);
		glEnd();
		glFlush();
	}
	glFlush();
	return;
}

void drawiconlegsw(float x1[][7], float y1[][7], float z1[][7]) {

	int i, face;

	/*************** legs for polywoman ******************/
	for (face = 8; face <= 11; face++) {

		glColor3f(x1[face][3], y1[face][3], z1[face][3]);
		glBegin(GL_LINE_STRIP);
		//now draw the legs
		for (i = 0; i <= 2; i++)
			glVertex3f(x1[face][i], y1[face][i], z1[face][i]);
		glEnd();
		glFlush();
	}
	for (face = 12; face <= 13; face++) {
		glColor3f(x1[face][4], y1[face][4], z1[face][4]);
		glBegin(GL_LINE_STRIP);
		//now draw the panel 
		for (i = 0; i <= 3; i++)
			glVertex3f(x1[face][i], y1[face][i], z1[face][i]);

		glVertex3f(x1[face][0], y1[face][0], z1[face][0]);
		glEnd();
		glFlush();
	}
	glFlush();
	return;
}

void drawiconsql(float x2[][5], float y2[][5], float z2[][5])
{
	/*     this function draws the cube legs at the transformed position              */
	int i, face;

	for (face = 6; face <= 9; face++) {
		glColor3f(x2[face][3], y2[face][3], z2[face][3]);
		glBegin(GL_LINES);
		//now draw the legs
		for (i = 0; i <= 2; i++)
			glVertex3f(x2[face][i], y2[face][i], z2[face][i]);
		glEnd();
		glFlush();
	}
	for (face = 10; face <= 11; face++) {
		glColor3f(x2[face][4], y2[face][4], z2[face][4]);
		glBegin(GL_LINE_STRIP);
		//now draw the panels connecting the feet
		for (i = 0; i <= 3; i++)
			glVertex3f(x2[face][i], y2[face][i], z2[face][i]);

		glVertex3f(x2[face][0], y2[face][0], z2[face][0]);
		glEnd();
		glFlush();
	}
	return;
}

void settrans1(void)
//Sets the translation matrix for the polyman legs
{
	cout << "in settrans1" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dxl, dyl, dzl);
	// note that the angle theta is in degrees, not radians
	glRotatef(thetal, 0.0, 0.0, 1.0);
	glRotatef(thetam, 1.0, 0.0, 0.0);
	return;
}

/**************************  function settrans2  ***********************/
void settrans2(void)
//Sets the translation matrix for the polyman
{
	cout << "in settrans2" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dx, dy, dz);
	glRotatef(theta, 0.0, 0.0, 1.0);
	glRotatef(thetam, 1.0, 0.0, 0.0);
	return;
}
/**************************  function settrans3  ***********************/
void settrans3(void)
//Sets the translation matrix for the polywoman
{
	cout << "in settrans3" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dxw, dyw, dzw);
	// note that the angle theta is in degrees, not radians
	glRotatef(thetag, 0.0, 0.0, 1.0);
	glRotatef(thetaw, 0.0, 1.0, 0.0);
	glRotatef(-180.0, 0.0, 1.0, 0.0);
	return;
}
/**************************  function settrans4  ***********************/
void settrans4(void)
//Sets the translation matrix for the polywoman legs
{
	cout << "in settrans4" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dxlw, dylw, dzlw);
	// note that the angle theta is in degrees, not radians
	glRotatef(thetalw, 0.0, 0.0, 1.0);
	glRotatef(thetaw, 0.0, 1.0, 0.0);
	glRotatef(-180.0, 0.0, 1.0, 0.0);
	return;
}
/**************************  function settrans5  ***********************/
void settrans5(void)
//Sets the translation matrix for the squareman
{
	cout << "in settrans5" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dxs, dys, dzs);
	// note that the angle theta is in degrees, not radians
	glRotatef(thetasq, 0.0, 0.0, 1.0);
	glRotatef(180.0, 0.0 , 1.0, .0);
	return;
}
/**************************  function settrans6  ***********************/
void settrans6(void)
//Sets the translation matrix for the squareman legs
{
	cout << "in settrans6" << endl;
	glMatrixMode(GL_MODELVIEW);
	glLoadIdentity();
	glTranslatef(dxls, dyls, dzls);
	// note that the angle theta is in degrees, not radians
	glRotatef(thetals, 0.0, 0.0, 1.0);
	glRotatef(180.0, 0.0, 1.0, .0);
	return;
}
//****************************Function SetupRC*************************************
// Setup the rendering state
void SetupRC(void)
{// this function sets the clear color of an open window and clears the open window
 // Set clear color to 
	glClearColor(1.0, 1.0, 1.0, 1.0);

	return;
}//end of SetupRC
 /******************************** Functioner Timer****************************************/
void TimerFunction(int value)
//this call back function is call each 30 ms and changes the location,scale and rotation
// of the square.
{
	switch (frame)
	{
	case 1:
		//polyman parameters
		theta += 0.0;//start at zero degress for the polyman rotation
		thetag += 0.0;
		dx -= 0.15;
		dxw += 0.15;

		thetal += 2.5;
		dxl -= 0.15;

		thetalw += 2.5;
		dxlw += 0.15;
		//we key on the polyman icons position to change the frame
		//including the movement of the feet
		if (dx <= 1.0) {
			dx = 1.0;
			frame = 2;
		}
		if (dxw >= -1.0) {
			dxw = -1.0;
			frame = 2;
		}
		if (dxlw >= -1.0) {
			dxlw = -1.0;
			frame = 2;
		}
		//this is the animation of the feet
		if (thetalw >= 25.0) {
			thetalw = 0.0;
		}
		if (dxl <= 1.0) {
			dxl = 1.0;
			frame = 2;
		}
		//this is the animation of the feet
		if (thetal >= 25.0) {
			thetal = 0.0;
		}
		break;
	case 2:// frame 2 the polyman rises
		// first the polyman
		dy += 0.2;
		//now the feet
		dyl += 0.2;

		if (dy > 5.0)
		{
			dy = 5.0;
			frame = 3;
		}
		if (dyl > 5.0) {
			dyl = 5.0;
			frame = 3;
		}
		break;
	case 3:// frame 3 polyman rotates polywoman knods
	//first the polyman
		theta += 0.0;
		//now the feet
		thetal += 0.0;
		thetam += 5.0;
		thetag += 2.5;
		thetalw += 0.0;
		if (thetag >= 35.0) {
			thetag = 0.0;
		}

		if (thetam >= 360.0) {
			thetam = 0.0;
			frame = 4;
		}
		break;
	case 4: // frame 4 polyman moves down, squareman enters and stabs polyman
			//first the polyman
		dy -= 0.2;
		//now the feet
		dyl -= 0.2;
		thetals += 5.0;
		if (dxs <= 3.5) {
			thetaw -= 5.0;
		}
		if (thetaw <= -180.0) {
			thetaw = -180.0;
		}
		if (dy <= -3.0)
		{
			dy = -3.0;
			dyl = -3.0;
			dxs -= 0.15;
			dxls -= 0.15;
		}
		if (thetals >= 25.0) {
			thetals = 0.0;
		}
		if (dxs <= 4.0) {
			thetasq += 2.5;
		}
		if (thetasq >= 45.0) {
			thetasq = 0.0;
		}
		if (dxs <= 3.25) {
			theta -= 5.0;
			thetal -= 5.0;
		}
		if (theta <= -180.0) {
			theta = -180.0;
		}
		if (dxs <= 0.0) {
			dxs = 0.0;
			dxls = 0.0;
		}
		if (thetal <= -180.0) {
			thetal = -180.0;
			frame = 5;
		}
		break;
	case 5:// frame 5 polywoman and squareman exits off stage left
	// first the polywoman
	//then squareman
		theta += 0.0;

		dxw -= 0.15;
		thetag += 0.0;

		dxlw -= 0.15;
		thetalw += 5.0;

		dxs -= 0.15;
		thetasq -= 5.0;

		dxls -= 0.15;
		thetals += 5.0;
		//set end destination of icons
		if (dxw <= -6.5)dxw = -6.5;
		if (dxlw <= -6.5)dxlw = -6.5;
		if (dxs <= -6.5)dxs = -6.5;
		if (dxls <= -6.5)dxls = -6.5;
		//this animates the feet 
		if (thetasq >= -45.0) {
			thetasq = 0.0;
		}
		if (thetalw >= 25.0) {
			thetalw = 0.0;
		}
		if (thetals >= 25.0) {
			thetals = 0.0;
		}
		break;
	}
	// Redraw the scene with new coordinates
	glutPostRedisplay();
	glutTimerFunc(33, TimerFunction, 1);
}