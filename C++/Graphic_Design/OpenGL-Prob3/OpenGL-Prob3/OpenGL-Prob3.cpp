using namespace std;

#include<windows.h>
#include<GL/glut.h>
#include<stdlib.h>
#include<math.h>
#include<conio.h>
#include<stdio.h>
#include <iostream>
#include <iomanip>


 //*********** Global values************************************************
 /* These values are global because the timing call back
functions will only take certain parameters
 hence their needs to be global variables to communicate with
these functions */
float theta = 0.0;//global angular value for rotation
float scale1 = 1.0;//global scaling value for polyman
float dx = 7.0, dy = -3.0;//global movement value for dx and dy/
float thetat = 0.0, dxt = 7.0, dyt = -3.0;// global value for the triangle
float thetal = 0.0, dxl = 7.0, dyl = -3.0;//global value for the feet 
int frame = 1;


void init(void);//this is a function to initialize the window clear color
void RenderScene(void);//this is a function to draw a polyman in an opened window
void loadicon(float[], float[], float[], float[], float[], float[],float[], float[]); 
 /* loads the icons */
void drawicon(float[], float[]);

void drawicon1(float[], float[]);

void drawicon2(float[], float[]);
/*
 draws the icons
*/
void settrans4(void); /*sets the rotation / translation matrix
the MODELVIEW MATRIX for the feet*/

void settrans3(void);/*sets the rotation/translation matrix
the MODELVIEW MATRIX for the triangle*/

void settrans2(void);/* sets the rotation/translation matrix
the MODELVIEW MATRIX for the top of polyman*/

void settrans1(void);/*sets the rotation/translation matrix to the
MODELVIEW MATRIX for bottom of polyman*/

void drawtriangle(float[], float[]);

void SetupRC(void);//sets up the clear color
void TimerFunction(int);//this call back function is call
//each 30 ms and changes the location,scale and rotation
// of the square.

//Main Program

int main(int argc, char** argv)
{//set up window title


 char header[] = "Polyman by Tristan Davis";

/*glutInit() initializes GLUT. Takes the command line
arguments which are used to initialize the native
 window system. This function must be called before any
other GLUT functions. */

 glutInit(&argc, argv);
// Set up the display mode with a single buffer and RG colors
glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
//Initialize window size and position
glutInitWindowSize(560, 440);
glutInitWindowPosition(140, 20);
//Initialize background color in window to red
SetupRC();
// Open and Label Window
glutCreateWindow(header);
glutDisplayFunc(RenderScene);
glutTimerFunc(30, TimerFunction, 1);
//Now draw the scene

glutMainLoop();


return 0;
}
//*************************RenderScene Function*************************
void RenderScene(void)
{
float xdel = 0.25;

float px[9], py[9]; /* these variables
hold the pattern for the top icon.
Note that px,py hold the top icon
*/

float px1[5], py1[5];/*these variables hold the pattern for the bottom of polyman*/

float px2[6], py2[6];// these variables hold the values for the feet

float pxt1[3], pyt1[3];/*these
variables hold the pattern for the triangle */

//clear the window with the current background color
cout << "in renderscene" << endl;
//set the current drawing color to white
glColor3f(1.0, 1.0, 1.0);
glMatrixMode(GL_PROJECTION);
glLoadIdentity();
//set the viewport to the window dimensions
glViewport(0, 0, 540, 440);
//Establish the clipping volume in user coordinates
glOrtho(-7.0, 7.0, -7.0, 7.0, 1.0, -1.0);
loadicon(px, py, px1, py1, px2, py2, pxt1, pyt1);
/* draw the icons untransformed */

// Clear the window with the background color
glClear(GL_COLOR_BUFFER_BIT);
//set the current drawing color to white
glColor3f(1.0, 1.0, 1.0);

settrans1();

drawicon1(px1, py1);
//Set the MODELVIEW MATRIX for the top icon
settrans2();
/*now draw the top icon. No need to transform them, the MODELVIEW MATRIX set in settrans2() will
transform them */

drawicon(px,py);

settrans1();

drawicon1(px1, py1);

//Now Set the MODELVIEW MATRIX for the Triangle
settrans3();
/*Now Draw the Triangle again note the direct use of
pxt1,pyt1 from loadicon. Again we depend on the MODELVIEW MATRIX set in settrans3() to
transform the triangle pattern */
drawtriangle(pxt1, pyt1);

settrans4();
//now draw the feet
drawicon2(px2, py2);


glEnd();

glutSwapBuffers();

return;

};//end of render scene
//******************************Load Icon Function************************************
void loadicon(float px[], float py[], float px1[], float py1[], float px2[], float py2[], 
    float ptx1[], float pty1[])
/* this procedure loads a polyman icon */
{
/* Set the coordinates for the top portion of polyman */

px[0] = -0.625; py[0] = 0.75;
px[1] = 0.625;  py[1] = 0.75;
px[2] = 1.125;  py[2] = 0.0;
px[3] = -1.125; py[3] = 0.0;
px[4] = -0.625; py[4] = 0.75;

//load bottom portion of polyman

px1[0] = -1.125; py1[0] = 0.0;
px1[1] = -0.625; py1[1] = -0.75;
px1[2] = 0.625;  py1[2] = -0.75;
px1[3] = 1.125;  py1[3] = 0.0;
px1[4] = -1.125; py1[4] = 0.0;


/* load the mouth */
ptx1[0] = -1.125; pty1[0] = 0.0;
ptx1[1] = -0.375; pty1[1] = 0.0;
ptx1[2] = -0.625; pty1[2] = -0.75;

//load the eye
px[5] = -0.625;  py[5] = 0.5;
px[6] = -0.5;    py[6] = 0.625;
px[7] = -0.375;  py[7] = 0.5;
px[8] = -0.5;    py[8] = 0.375;

//load the feet-->>

//left leg
px2[0] = -0.25;  py2[0] = -0.5;
px2[1] = -0.25;  py2[1] = -1.0;
px2[2] = -0.5;   py2[2] = -1.0;
//right leg
px2[3] = 0.25;   py2[3] = -0.5;
px2[4] = 0.25;   py2[4] = -1.0;
px2[5] = 0.0;    py2[5] = -1.0;



return;
} /* end of load icon */

 /************************* function drawicon ***************************/
void drawicon(float pxp[], float pyp[]) 
{
/* this function draws the icon at the
transformed position. The model view matrix previously set
before we enter this function will draw the top polyman with the transformations*/
int i;

cout << "in drawicon" << endl;
//Draw the eye
glBegin(GL_LINE_LOOP);

//move to first point in icon
glColor3f(0.0, 0.0, 0.0);
glVertex2f(pxp[5], pyp[5]);

glEnd();
glFlush();

//draw the top icon of polyman
glBegin(GL_LINE_STRIP);
//move to first point in icon
glVertex2f(pxp[0], pyp[0]);
//now draw the rest of the icon
for (i = 0; i <= 4; i++)
glVertex2f(pxp[i], pyp[i]);
glEnd();
glFlush();


//set the shading color to blue
glColor3f(0.0, 0.0, 1.0);
glShadeModel(GL_FLAT);

//redraw the polygon

glBegin(GL_POLYGON);
// note the colored polygon must be redrawn to render it.
//first point is where the line intersects the top part of the polyman
glVertex2f(pxp[0], pyp[0]);
//right corner upper
glVertex2f(pxp[1], pyp[1]);
//right corner lower
glVertex2f(pxp[2], pyp[2]);
//left corner lower
glVertex2f(pxp[3], pyp[3]);
//first point 
glVertex2f(pxp[4], pyp[4]);

glEnd();
glFlush();

/*Now do the same for the eye*/

//set the shading color to black
glColor3f(0.0, 0.0, 0.0);
glShadeModel(GL_FLAT);
//redraw the polygon

glBegin(GL_POLYGON);
// note the colored polygon must be redrawn to render it.
//first point is where the line intersects the top part of eye
glVertex2f(pxp[5], pyp[5]);
//left corner upper
glVertex2f(pxp[6], pyp[6]);
//right corner lower
glVertex2f(pxp[7], pyp[7]);
//left corner lower
glVertex2f(pxp[8], pyp[8]);

glEnd();
glFlush();

return;
}//end of draw icon

/************************* function drawicon ***************************/

void drawicon1(float p1xp[], float p1yp[])
{
    /* this function draws the bottom polyman at the
    transformed position. The model view matrix previously set
    before we enter this function will draw the bottom polyman with the transformations*/
    int i;

    cout << "in drawicon1" << endl;

    glBegin(GL_LINE_STRIP);
    //move to first point in icon
    glVertex2f(p1xp[0], p1yp[0]);
    //now draw the rest of the icon
    for (i = 0; i <= 4; i++)
        glVertex2f(p1xp[i], p1yp[i]);
    glEnd();
    glFlush();

    //set the shading color to blue
    glColor3f(0.0, 0.0, 1.0);
    glShadeModel(GL_FLAT);
    //redraw the polygon

    glBegin(GL_POLYGON);
    // note the colored polygon must be redrawn to render it.
    //first point is where the line intersects the bottom part of the polyman
    glVertex2f(p1xp[0], p1yp[0]);
    //right corner upper
    glVertex2f(p1xp[1], p1yp[1]);
    //left corner lower
    glVertex2f(p1xp[2], p1yp[2]);
    //right corner lower
    glVertex2f(p1xp[3], p1yp[3]);
    //first point
    glVertex2f(p1xp[4], p1yp[4]);
   
    glEnd();
    glFlush();

    return;
}//end of draw icon1

/************************* function drawicon ***************************/

void drawicon2(float p2xp[], float p2yp[])
{
    /* this function draws the feet icon at the
    transformed position. The model view matrix previously set
    before we enter this function will draw the feet with the transformations*/
    int i;

    cout << "in drawicon2" << endl;

    //Draw the left leg
    glBegin(GL_LINE_STRIP);

    //move to first point in icon
    //draw the line
    glColor3f(0.0, 0.0, 1.0);
    glVertex2f(p2xp[0], p2yp[0]);
    glVertex2f(p2xp[1], p2yp[1]);
    glVertex2f(p2xp[2], p2yp[2]);


    glEnd();
    glFlush();


    //Draw the right leg
    glBegin(GL_LINE_STRIP);

    //move to first point in icon
    //draw the line
    glColor3f(0.0, 0.0, 1.0);
    glVertex2f(p2xp[3], p2yp[3]);
    glVertex2f(p2xp[4], p2yp[4]);
    glVertex2f(p2xp[5], p2yp[5]);

    glEnd();
    glFlush();

    return;
}//end of drawicon2

/************************* function drawicon ***************************/

void drawtriangle(float ptx1[], float pty1[])
{
/* this function draws the square icon at the
transformed position because the triangle
MODELVIEW MATRIX is in effect */
int i;

cout << "in drawtriangle" << endl;
if (dx == 0.0) {
    glBegin(GL_LINE_LOOP);
    //move to first point in icon
    glVertex2f(ptx1[0], pty1[0]);
    //now draw the rest of the box
    for (i = 0; i <= 2; i++)
        glVertex2f(ptx1[i], pty1[i]);
    glEnd();
    glFlush();

    glColor3f(0.0, 0.0, 0.0);
    glShadeModel(GL_FLAT);
    //redraw the polygon
    glBegin(GL_POLYGON);
    // note the colored rectangle must be redrawn to render it.
    glVertex2f(ptx1[0], pty1[0]);
    //left corner upper
    glVertex2f(ptx1[1], pty1[1]);
    //right corner upper
    glVertex2f(ptx1[2], pty1[2]);

    glEnd();
    glFlush();

    return;
}
}//end of drawtriangle

/************************** function settrans1 **********************/
void settrans1(void)

/*Sets the MODELVIEW MATRIX for the icon. Note that the
calls are done backqards that is if we want to rotate and move the pattern, call
glTranslate first and then glRotate */

{
    cout << "in settrans1" << endl;

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslatef(dx, dy, 0.0);
    glRotatef(theta, 0.0, 0.0, 1.0);// angle theta in degrees not radians
    return;

}

/************************** function settrans2 **********************/
void settrans2(void)

/*Sets the MODELVIEW MATRIX for the icon. Note that the
calls are done backqards that is if we want to rotate and move the pattern, call
glTranslate first and then glRotate */
{
    cout << "in settrans2" << endl;
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslatef(dx, dy, 0.0);
    glRotatef(theta, 0.0, 0.0, 1.0);// note that the angle theta is in degrees, not radians
    return;
}

/************************** function settrans3 **********************/
void settrans3(void)

/*Sets the MODELVIEW MATRIX for the triangle. Note again
that the calls are done backward.
Further note that we must have a MODELVIEW MATRIX for each
figure */
{
cout << "in settrans3" << endl;
glMatrixMode(GL_MODELVIEW);
glLoadIdentity();
glTranslatef(dxt, dyt, 0.0);
glRotatef(thetat, 0.0, 0.0, 1.0);// note that the angle thetat is in degrees, not radians
return;
}

/************************** function settrans4 **********************/
void settrans4(void) 

/*Sets the MODELVIEW MATRIX for the feet. Note again
that the calls are done backward.
Further note that we must have a MODELVIEW MATRIX for each
figure */

{
    cout << "in settrans4" << endl;

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslatef(dxl, dyl, 0.0);
    glRotatef(thetal, 0.0, 0.0, 1.0);// angle thetal in degrees not radians
    return;
}

//****************************Function SetupRC************************************
// Setup the rendering state
void SetupRC(void)
{// this function sets the clear color of an open window and clears the open window
// Set clear color to blue
glClearColor(0.0, 0.0, 1.0, 1.0);

return;
}//end of SetupRC

/******************************** Functioner Timer****************************************/
void TimerFunction(int value)
//this call back function is call each 30 ms and changes the location,scale and rotation
// of the polyman.
{

switch (frame)
{
case 1: //frame 1 square starts at right (7, -3) and rolls the square to middle right (3.5,-3)
// triangle starts from the left)(-7.-3)
//polyman parameters
theta += 0.0;//start at zero degress for the polyman rotation
dx -= 0.15;
//triangle parameters
dxt -= 0.15;

thetat += 0.0;//start at zero degrees for the polyman rotation

//feet parameters
thetal += 5.0;
dxl -= 0.15;

//we key on the polyman icons position to change the frame
//including the movement of the feet
if (dx <= 0.0) {
dx = 0.0;
frame = 2;
}
if (dxt <= 0.0){
dxt = 0.0;
frame = 2;
}
if (dxl <= 0.0) {
    dxl = 0.0;
    frame = 2;
}
//this is the animation of the feet
if (thetal >= 25.0) {
    thetal = 0.0;
}
break;


case 2:// frame 2 the polyman rises to y=5 triangle also rises to yt=5
    // first the polyman
dy += 0.2;
// now the triangle
dyt += 0.2;
//now the feet
dyl += 0.2;
if (dy > 5.0)
{
dy = 5.0;
frame = 3;
}
if (dyt > 5.0) {
dyt = 5.0;
frame = 3;
}
if (dyl > 5.0) {
    dyl = 5.0;
    frame = 3;
}

break;
case 3:// frame 3 polyman rotates at x=3.5,y=5.0 triangle also rotates
//first the polyman
theta += 5.0;
// now the triangle
thetat += 5.0;
//now the feet
thetal += 5.0;
if (theta >= 360.0)
{
frame = 4;
theta = 0.0;

}

if (thetat >= 360.0)
{
   
    frame = 4;
    thetat = 0.0;

}

if (thetal >= 360.0)
{
    frame = 4;
    thetal = 0.0;

}

break;
case 4: // frame 4 polyman moves down to x=0.0, y=-3.0, triangle also comes back down
        //first the polyman
dy -= 0.2;
//now the triangle
dyt -= 0.2;
//now the feet
dyl -= 0.2;

if (dy <= -3.0)
{
dy = -3.0;
frame = 5;
}

break;
case 5:// frame 5 polyman rolls off stage to left triangle rolls to right
// first the polyman
dx -= 0.15;

theta += 0.0;
// now the triangle
dxt -= 0.15;
thetat += 0.0;
//now the feet
dxl -= 0.15;
thetal += 5.0; 

if (dx <= -6.5)dx = -6.5;
if (dxt <= -6.5)dxt = -6.5;
if (dxl <= -6.5)dxl = -6.5;
//this animates the feet 
if (thetal >= 25.0) {
    thetal = 0.0;
}

break;
}



// Redraw the scene with new coordinates
glutPostRedisplay();
glutTimerFunc(30, TimerFunction, 1);
}