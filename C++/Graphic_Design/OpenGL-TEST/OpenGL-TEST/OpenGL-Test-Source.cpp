using namespace std;


#include<windows.h>
#include<GL/glut.h>
#include<stdlib.h>
#include<math.h>
#include<conio.h>
#include<stdio.h>
#include <iostream>
#include <iomanip>

 /*

 This is an example of a 2 dimensional animation.  Two icons, a square and trapezoid are loaded.  Then the program utilizes
 the MODELVIEW Matrix to rotate the figures before putting them into the graphics pipeline for rendering.
 The program also utilizes a small animation driver found in TimerFunction.  This driver changes the global variables
 (rotatet, x and y for the icons); before pushing the icons through the MODELVIEW
 MATRIX.
 //*********** Global values************************************************
 /* These values are global because the timing call back functions will only take certain parameters
 hence their needs to be global variables to communicate with these functions */
 float rotatet = 0.0;//global angular value for rotation
 int frame = 1;

 void init(void);//this is a function to initialize the window clear color
 void RenderScene(void);//this is a function to draw a square in an opened window
 void loadicon(float[], float[]);

 /*  loads the icons    */
 void DrawAllIcons(float[], float[],int, int[], int[], float[], float[], float[],
     float, float, float, float, float);
 
 /*draws the icon*/

 void SetupRC(void);//sets up the clear color
 void TimerFunction(int);//this call back function is call each 30 ms and changes the location,scale and rotation
                   // of the icons.

       //Main Program

 int main(int argc, char** argv)
 {//set up window title
   
        
    char header[] = "Square and Trapezoid by Tristan Davis";
    
   /*glutInit() initializes GLUT.  Takes the command line arguments which are used to initialize the native
    window system.  This function must be called before any other GLUT functions.  */

    glutInit(&argc, argv);
    // Set up the display mode with a single buffer and RGB colors
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB);
    //Initialize window size and position
    glutInitWindowSize(560, 440);
    glutInitWindowPosition(140, 20);
    //Initialize  background color in window to red
    SetupRC();
    //  Open and Label Window  
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


    float x[26], y[26], colorr[2], colorg[2], colorb[2]; /*  these variables hold the
                               pattern for the square, trapezoid and combined square and 
                               trapezoid icon.  Note that x,y hold the icons; colorr[], colorg[],
                               and colorb[] hold the color.  */
    int pointsperdraw[5];
    int drawtype[5];
    float ndraws = 0.0;
    float rotate = 0.0;
    float scalex = 2.0;
    float scaley = 2.0;
    float transx = 0.0;
    float transy = 0.0;

   //clear the window with the current background color

    cout << "in renderscene" << endl;
    //set the current drawing color to white
    glColor3f(1.0, 1.0, 1.0);
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    //set the viewport to the window dimensions
    glViewport(0, 0, 540, 440);
    //Establish the clipping volume in user coordinates
    glOrtho(-10.0, 10.0, -10.0, 10.0, 1.0, -1.0);
    loadicon(x, y);
    /*     draw the icon untransformed      */



    // Clear the window with the background color
    glClear(GL_COLOR_BUFFER_BIT);
    //set the current drawing color to white
    glColor3f(1.0, 1.0, 1.0);
    //Set the MODELVIEW MATRIX for the icons

    /*now draw the icons.  No need to transform 
    them, the MODELVIEW MATRIX  will transform them */
    DrawAllIcons(x, y, ndraws, pointsperdraw, drawtype, colorr, colorg, colorb,
        rotate, scalex, scaley, transx, transy); 


    glEnd();

    glutSwapBuffers();


    return;

 };//end of render scene
   //******************************Load Icon Function************************************
 void loadicon(float x[], float y[])
    /*   this procedure loads a square, and trapezoid icon             */
 {
    /*  Set the coordinates of the square          */

    x[0] = 1.0;   y[0] = 1.0;
    x[1] = 1.0;   y[1] = -1.0;
    x[2] = -1.0;  y[2] = -1.0;
    x[3] = -1.0;  y[3] = 1.0;
    x[4] = 1.0;   y[4] = 1.0;

    /*     set the line       */
    x[5] = 0.0;     y[5] = 2.0;
    x[6] = 0.0;     y[6] = -2.0;

    /* load the trapezoid */
    x[7] = 1.0;  y[7] = 1.0;
    x[8] = 1.5;  y[8] = -1.0;
    x[9] = -1.5; y[9] = -1.0;
    x[10] = -1.0; y[10] = 1.0;
    x[11] = 1.0;  y[11] = 1.0;

    /*  set the line    */
    x[12] = 0.0;     y[12] = 2.0;
    x[13] = 0.0;     y[13] = -2.0;


    /*  load the Square and Trapezoid on top of each other */
    //first the square
    x[14] = 1.0;   y[14] = 0.0;
    x[15] = -1.0;  y[15] = 0.0;
    x[16] = -1.0;  y[16] = 1.0;
    x[17] = 1.0;   y[17] = 1.0;
    x[18] = 1.0;   y[18] = 0.0;

    //now the trapezoid
    x[19] = 1.5;  y[19] = 0.0;
    x[20] = 2.0;  y[20] = -1.0;
    x[21] = -2.0; y[21] = -1.0;
    x[22] = -1.5; y[22] = 0.0;
    x[23] = 1.5;  y[23] = 0.0;

    //draw the line through both

    x[24] = 0.0;  y[24] = 3.0;
    x[25] = 0.0;  y[25] = -3.0;

    return;
 }    /*     end of load icon       */
     /************************* function DrawsAllIcons  ***************************/

 void DrawAllIcons(float x[], float y[], int ndraws, int pointsperdraw[], int drawtype[],
     float colorr[], float colorg[], float colorb[], float rotate, float scalex, float scaley,
     float transx, float transy)
 {
    /*this function draws the icons at the transformed position. The model view matrix set 
    will draw the icons with the transformations*/
    
    //color array values
    colorr[0] = 0.0;
    colorr[1] = 1.0;
    colorg[0] = 0.0;
    colorg[1] = 1.0;
    colorb[0] = 0.0;
    colorb[1] = 1.0;

    //designate drawtype
    drawtype[1] = GL_LINES;
    drawtype[2] = GL_LINE_STRIP;
    drawtype[3] = GL_POLYGON;

    //define scale(x,y)
    scalex = 2.0;
    scaley = 2.0;

    //center point of icon
    transx = 5.0;
    transy = 5.0;
    

    cout << "in drawallicons" << endl;

    //draw the square

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslatef(transx, transy, 0.0);
    glRotatef(rotatet, 0.0, 0.0, 1.0);// note that the angle rotatet is in degrees, not radians
    glScalef(scalex, scaley, 0.0);
    

    pointsperdraw[0] = 5;
    glBegin(drawtype[2]);
    //move to first point in ndraws
   
    glVertex2f(x[0], y[0]);
    //now draw the rest of the box
    for (ndraws = 0; ndraws <= 4; ndraws++)
       glVertex2f(x[ndraws], y[ndraws]);
    glEnd();
    glFlush();

    //now draw the line
    pointsperdraw[1] = 2;
    glBegin(drawtype[1]);
    glColor3f(colorr[1], colorg[1], colorb[1]);
    glVertex2f(x[5], y[5]);
    glVertex2f(x[6], y[6]);
    glEnd();
    glFlush();
    //now fill the square icon
    //set the shading color to red
    glColor3f(colorr[1], colorg[0], colorb[0]);
    glShadeModel(GL_FLAT);
    //redraw the polygon
    glBegin(drawtype[3]);
    // note the colored square must be redrawn to render it.
    //first point is where the line intersects the top part of the square
    glVertex2f(x[0], y[0]);
    //bottom right corner
    glVertex2f(x[1], y[1]); 
    //bottom left corner
    glVertex2f(x[2], y[2]);
    //upper left corner 
    glVertex2f(x[3], y[3]);
    //first point to complete it
    glVertex2f(x[4], y[4]);

    glEnd();
    glFlush();

    //trapezoid
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslatef(-transx, -transy, 0.0);
    glRotatef(rotatet, 0.0, 0.0, 1.0);// note that the angle rotate is in degrees, not radians
    glScalef(scalex, scaley, 0.0);

   
    glBegin(drawtype[2]);
    pointsperdraw[0] = 5;
    //move to first point in icon
    glVertex2f(x[7], y[7]);
    //change drawing colore to white
    glColor3f(colorr[1], colorg[1], colorb[1]);
    //now draw the rest of the box
    for (ndraws = 7; ndraws <= 11; ndraws++)
        glVertex2f(x[ndraws], y[ndraws]);
    glEnd();
    glFlush();

    //now draw the line
    glBegin(drawtype[1]);
    pointsperdraw[1] = 2;
    //make it white
    glColor3f(colorr[1], colorg[1], colorb[1]);
    glVertex2f(x[12], y[12]);
    glVertex2f(x[13], y[13]);
    glEnd();
    glFlush();
    //set the shading color to green
    glColor3f(colorr[0], colorg[1], colorb[0]);
    glShadeModel(GL_FLAT);
    //redraw the polygon
    glBegin(drawtype[3]);
    
    // note the colored trapezoid must be redrawn to render it.
    //first point is where the line intersects the top part of the trapezoid
    glVertex2f(x[7], y[7]);
    //bottom right corner
    glVertex2f(x[8], y[8]);
    //bottom left corner
    glVertex2f(x[9], y[9]);
    //upper left corner 
    glVertex2f(x[10], y[10]);
    //first to complete it 
    glVertex2f(x[11], y[11]);

    glEnd();
    glFlush();

    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslatef(0.0, 0.0, 0.0);
    glRotatef(rotatet, 0.0, 0.0, 1.0);

    //square2
    glBegin(drawtype[2]);
    pointsperdraw[0] = 5;
    //set drawing color to white
    glColor3f(colorr[1], colorg[1], colorb[1]);
    //move to first point in icon
    glVertex2f(x[14], y[14]);
    //now draw the rest of the box
    for (ndraws = 14; ndraws <= 23; ndraws++)
        glVertex2f(x[ndraws], y[ndraws]);
    glEnd();
    glFlush();
    
    //now draw the line
    glBegin(drawtype[1]);
    pointsperdraw[1] = 2;
    //make it white
    glColor3f(colorr[1], colorg[1], colorb[1]);
    glVertex2f(x[24], y[24]);
    glVertex2f(x[25], y[25]);
    glEnd();
    glFlush();

    //set the shading color to red
    glColor3f(colorr[1], colorg[0], colorb[0]);
    glShadeModel(GL_FLAT);
    //redraw the polygon
    glBegin(drawtype[3]);
    // note the colored square must be redrawn to render it.
    //first point is where the line intersects the top part of the square
    glVertex2f(x[14], y[14]);
    //bottom right corner
    glVertex2f(x[15], y[15]);
    //bottom left corner
    glVertex2f(x[16], y[16]);
    //upper left corner 
    glVertex2f(x[17], y[17]);
    //first point to complete it 
    glVertex2f(x[18], y[18]);

    glEnd();
    glFlush();

    // now the trapezoid2
    glColor3f(colorr[0], colorg[1], colorb[0]);
    glShadeModel(GL_FLAT);
    glBegin(drawtype[3]);

    //first point in the square2
    glVertex2f(x[15], y[15]);
    //first point in the trapezoid
    glVertex2f(x[19], y[19]);
    //bottom right corner
    glVertex2f(x[20], y[20]);
    //bottom left corner
    glVertex2f(x[21], y[21]);
    //upper left corner
    glVertex2f(x[22], y[22]);
    //first point to complete it 
    glVertex2f(x[23], y[23]);

    glEnd();
    glFlush();

    return;
    
 }//end of drawallicons



 //****************************Function SetupRC*************************************
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
 // of the square.
 {

    switch (frame)
    {
    case 1: //frame 1 square starts at (5, 5) and rotates around this point
             // parameters
       rotatet += 1.5; 
       //set the rotation in degrees for the icons
       if (rotatet >= 360.0)
       {
           frame = 1;
           rotatet = 0.0;
       }
       break;

    }

// Redraw the scene with new coordinates
glutPostRedisplay();
glutTimerFunc(30, TimerFunction, 1);
}
