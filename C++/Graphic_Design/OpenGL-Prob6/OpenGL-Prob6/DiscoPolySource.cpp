#include<GL/glut.h>
#include<stdlib.h>
#include<math.h>
#include<conio.h>
#include<stdio.h>
#include <iostream>
#include<windows.h>
#include <iomanip>

 using namespace std;

 /* This program demonstrates rendering a three dimensional polyman in OpenGL.  The program renders the polyman in solid form
 using the function Enable(GL_DEPTH_TEST) to activate the z-buffer to hide hidden surfaces.  The
 surfaces of the polyman are rendered by glBegin(GL_POLYGON).

 //*********** Global values************************************************
 /* These values are global because the timing call back functions will only take certain parameters
 hence their needs to be global variables to communicate with these functions */
 float static theta = 0.0, theta2 = 0.0, thetal = 0.0;;//global angular value for rotation 
 float scale1 = 1.0;//global scaling value for polyman
 float dx = -6.0, dy = 0.0, dz = 0.0;//global movement value for dx and dy/
 int lightswitch = 1.0;//value for light animation
 int frame = 1.0;

 void init(void);//this is a function to initialize the window clear color
 void RenderScene(void);//this is a function to draw a square in an opened window
                        /*  loads the square icon */
 void loadicon(float[][7], float[][7], float[][7], float[][3], float[][3]);
 //load the normals 
 void calcNormals(float, float, float, float, float, float, float, float, float, float, float, float);

 /*draws the icon */
 void drawicon(float[][7], float[][7], float[][7], float[][3], float[][3]);

 void drawiconL(float[][7], float[][7], float[][7], float[][3], float[][3]);

 void settrans2(void);  /* sets the translation matrix for the polyman
                      transformation matrix for desired scale, rotation,new pos*/
void settrans3(void);
 /*performs the transformation on the icon pattern      */

 void SetupRC(void);//sets up the clear color
 void TimerFunction(int);//this call back function is call each 30 ms and changes the location,scale and rotation
                   // of the polyman.
//Main Program

 int main(int argc, char** argv)
 {//set up window title

    char header[] = "PolyDisco by Tristan Davis";

    glutInit(&argc, argv);
    // Set up the display mode with a double buffer and a depth buffer and RGB colors
    glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
    SetupRC();
    //Initialize window size and position
    glutInitWindowSize(560, 440);
    glutInitWindowPosition(140, 20);
    //  Open and Label Window  
    glutCreateWindow(header);
    //Enable the texture state

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

    float x[14][7], y[14][7], z[14][7], fcolor[14][3], nvector[14][3]; /*  these variables hold the pattern for the polyman icon. */

    // set up light parameters
    float ambientlight[] = { 1.0,0.0,0.0,1.0 };//strong red ambient light
    float diffuselight[] = { 1.0,0.0,0.0,1.0 };//diffuse lighting
    float specular[] = { 1.0,1.0,1.0,1.0 };//specular lighting
    float lightpos[] = { -2.0,4.0,4.0,1.0 };
    float specref[] = { 1.0,1.0,1.0,1.0 };//set the reflectance of the material all is plastic
    float spotdir[] = { 2.0,-4.0,-4.0 };//shine spot down on polyman the light must shine toward the origin
    //second light
    float ambientlight1[] = { 0.0,0.0,1.0,1.0 };//strong blue ambient light
    float diffuselight1[] = { 0.0,0.0,1.0,1.0 };//diffuse lighting
    float specular1[] = { 1.0,1.0,1.0,1.0 };//specular lighting
    float lightpos1[] = { 2.0,4.0,4.0,1.0 };
    float specref1[] = { 1.0,1.0,1.0,1.0 };//set the reflectance of the material all is plastic
    float spotdir1[] = { -2.0,-4.0,-4.0 };//shine spot down on cube the light must shine toward the origin

    //clear the window with the current background color
    cout << "in renderscene" << endl;
    glMatrixMode(GL_PROJECTION);
    glLoadIdentity();
    //set the viewport to the window dimensions
    glViewport(0, 0, 540, 440);
    //Establish the clipping volume in user coordinates
    glOrtho(-7.0, 7.0, -7.0, 7.0, -5.0, 5.0);
    loadicon(x, y, z, fcolor, nvector);
    /*     draw the polyman      */
   glEnable(GL_DEPTH_TEST);
   
    /*******************************CAUTION DANGER!!!! DANGER!!!************
    YOU MUST SWITCH TO MODELVIEW MATRIX MODE BEFORE YOU ENABLE THE LIGHT AND YOU MUST LOAD A NEW IDENTITY
    IDENTITY MATRIX.  IF YOU DO NOT DO THIS AND YOU MOVE THE ICON LATER.  THE LIGHT WILL FOLLOW T
    THE ICON.  ALSO NOTE THAT THE COORDINATE SYSTEM FOR THE SPOTLIGHT IS SCREWED UP.
    POSITIVE X IS TO THE LEFT, POSITIVE Y IS DOWN AND POSITIVE Z IS AWAY FROM THE VIEWER OUT OF THE
    SCREEN
    *****************************IGNORE THESE AT YOUR OWN RISK*******************************/
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity(); 
    //enable lighting
    glEnable(GL_LIGHTING);
    // set light position, ambient, diffuse and specular strength
    glLightfv(GL_LIGHT0, GL_POSITION, lightpos);
    glLightfv(GL_LIGHT1, GL_POSITION, lightpos1);
    glLightfv(GL_LIGHT0, GL_AMBIENT, ambientlight);
    glLightfv(GL_LIGHT1, GL_AMBIENT, ambientlight1);
    glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuselight);
    glLightfv(GL_LIGHT1, GL_DIFFUSE, diffuselight1);
    glLightfv(GL_LIGHT0, GL_SPECULAR, specular);
    //glLightfv(GL_LIGHT1, GL_SPECULAR, specular1);
    //focused spotlight with only 25 degrees one way
    glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 45.0);
    glLightf(GL_LIGHT1, GL_SPOT_CUTOFF, 45.0);
    glLightf(GL_LIGHT0, GL_SPOT_EXPONENT, 15.0);
    glLightf(GL_LIGHT1, GL_SPOT_EXPONENT, 15.0);
    // point the light back to the origin
    glLightfv(GL_LIGHT0, GL_SPOT_DIRECTION, spotdir);
    glLightfv(GL_LIGHT1, GL_SPOT_DIRECTION, spotdir1);
    //enable the light
    if (dx != -1.5) {
        glDisable(GL_LIGHT0);
        glDisable(GL_LIGHT1);
    }
    if (dx >= -1.5) {
        if (lightswitch % 6 == 0) {
            glEnable(GL_LIGHT0);
            glDisable(GL_LIGHT1);
            lightswitch++;
        }
        else {
            glEnable(GL_LIGHT1);
            glDisable(GL_LIGHT0);
            lightswitch++;
        }
    }
    //now define the material properties
    glEnable(GL_COLOR_MATERIAL);
    glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
    glMaterialfv(GL_FRONT, GL_SPECULAR, specref);
    glMateriali(GL_FRONT, GL_SHININESS, 128);

    glClearColor(0.3, 0.3, 0.3, 1.0);
    // Clear the window and the z buffer with the background color
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    settrans2();
    //now draw the polyman
    drawicon(x, y, z, fcolor, nvector);

    settrans3();

    drawiconL(x, y, z, fcolor, nvector);

    glFlush();

    glEnd();

    glutSwapBuffers();

    return;

 };//end of render scene
   //******************************Load Icon Function************************************
 void loadicon(float x[][7], float y[][7], float z[][7], float fcolor[][3], float nvector[][3])
 /*   this procedure loads a polyman icon             */
 {
     /* load front face*/
     float ui{}, uj{}, uk{};
     x[0][0] = -1.125; y[0][0] = 0.0; z[0][0] = 0.5;
     x[0][1] = -0.625; y[0][1] = -0.75; z[0][1] = 0.5;
     x[0][2] = 0.625; y[0][2] = -0.75; z[0][2] = 0.5;
     x[0][3] = 1.125; y[0][3] = 0.0; z[0][3] = 0.5;
     x[0][4] = 0.625; y[0][4] = 0.75; z[0][4] = 0.5;
     x[0][5] = -0.625; y[0][5] = 0.75; z[0][5] = 0.5;
     /* load the color on the front face green*/
     fcolor[0][0] = 0.5; fcolor[0][1] = 0.9; fcolor[0][2] = 0.5;
     //load the normal to this face
     calcNormals(x[0][1], y[0][1], z[0][1], x[0][2], y[0][2], z[0][2], x[0][0], y[0][0], z[0][0], ui, uj, uk);
     nvector[0][0] = ui; nvector[0][1] = uj; nvector[0][2] = uk;

     /* load the right side (x) face*/
     x[1][0] = 1.125; y[1][0] = 0.0; z[1][0] = 0.5;
     x[1][1] = 0.625; y[1][1] = -0.75; z[1][1] = 0.5;
     x[1][2] = 0.625; y[1][2] = -0.75; z[1][2] = -0.5;
     x[1][3] = 1.125; y[1][3] = 0.0; z[1][3] = -0.5;
     x[1][4] = 0.625; y[1][4] = 0.75; z[1][4] = -0.5;
     x[1][5] = 0.625; y[1][5] = 0.75; z[1][5] = 0.5;
     /* load the color on the front face green*/
     fcolor[1][0] = 0.5; fcolor[1][1] = 0.9; fcolor[1][2] = 0.5;
     //load the normal to this face pos x axis
     nvector[1][0] = -1.0; nvector[1][1] = 0.0; nvector[1][2] = 0.0;

     /* load the back side face */
     x[2][0] = -1.125; y[2][0] = 0.0; z[2][0] = -0.5;
     x[2][1] = -0.625; y[2][1] = -0.75; z[2][1] = -0.5;
     x[2][2] = 0.625; y[2][2] = -0.75; z[2][2] = -0.5;
     x[2][3] = 1.125; y[2][3] = 0.0; z[2][3] = -0.5;
     x[2][4] = 0.625; y[2][4] = 0.75; z[2][4] = -0.5;
     x[2][5] = -0.625; y[2][5] = 0.75; z[2][5] = -0.5;
     /* load the color on the front face green*/
     fcolor[2][0] = 0.5; fcolor[2][1] = 0.9; fcolor[2][2] = 0.5;
     //load the normal to this face neg z axis
     nvector[2][0] = 0.0; nvector[2][1] = 0.0; nvector[2][2] = -1.0;

     /* load the left side x face */
     x[3][0] = -1.125; y[3][0] = 0.0; z[3][0] = -0.5;
     x[3][1] = -0.625; y[3][1] = -0.75; z[3][1] = -0.5;
     x[3][2] = -0.625; y[3][2] = -0.75; z[3][2] = 0.5;
     x[3][3] = -1.125; y[3][3] = 0.0; z[3][3] = 0.5;
     x[3][4] = -0.625; y[3][4] = 0.75; z[3][4] = 0.5;
     x[3][5] = -0.625; y[3][5] = 0.75; z[3][5] = -0.5;
     /*load the color on the back side green */
     fcolor[3][0] = 0.5; fcolor[3][1] = 0.9; fcolor[3][2] = 0.5;
     //load the normal to this face neg x axis
     nvector[3][0] = 1.0; nvector[3][1] = 0.0; nvector[3][2] = 0.0;

     /*load the top side*/
     x[4][0] = 0.625; y[4][0] = 0.75; z[4][0] = 0.5;
     x[4][1] = 0.625; y[4][1] = 0.75; z[4][1] = -0.5;
     x[4][2] = -0.625; y[4][2] = 0.75; z[4][2] = -0.5;
     x[4][3] = -0.625; y[4][3] = 0.75; z[4][3] = 0.5;
     /* load the color on the top  green */
     fcolor[4][0] = 0.5; fcolor[4][1] = 0.9; fcolor[4][2] = 0.5;
     // load the normal to this face pos y axis
     nvector[4][0] = 0.0; nvector[4][1] = 1.0; nvector[4][2] = 0.0;

     /*load the bottom side */
     x[5][0] = 0.625; y[5][0] = -0.75; z[5][0] = 0.5;
     x[5][1] = 0.625; y[5][1] = -0.75; z[5][1] = -0.5;
     x[5][2] = -0.625; y[5][2] = -0.75; z[5][2] = -0.5;
     x[5][3] = -0.625; y[5][3] = -0.75; z[5][3] = 0.5;
     /* load the color on the top  green */
     fcolor[5][0] = 0.5; fcolor[5][1] = 0.9; fcolor[5][2] = 0.5;
     // load the normal to this face neg y axis
     nvector[5][0] = 0.0; nvector[5][1] = -1.0; nvector[5][2] = 0.0;

     /* load front eye*/
     x[6][0] = -0.5; y[6][0] = 0.5; z[6][0] = 0.51;
     // load color to black
     fcolor[6][0] = 0.0; fcolor[6][1] = 0.0; fcolor[6][2] = 0.0;
     /* load back eye*/
     x[7][0] = -0.5; y[7][0] = 0.5; z[7][0] = -0.51;
     // load color to black
     fcolor[7][0] = 0.0; fcolor[7][1] = 0.0; fcolor[7][2] = 0.0;
    
     //left leg
     x[8][0] = -0.25;  y[8][0] = -0.5;  z[8][0] = 0.5;
     x[8][1] = -0.25;  y[8][1] = -1.0;  z[8][1] = 0.5;
     x[8][2] = -0.5;   y[8][2] = -1.0;  z[8][2] = 0.5;
     //load color
     fcolor[8][0] = 0.0;  fcolor[8][1] = 0.0;  fcolor[8][2] = 0.0;
     
     //shadow point of left leg
     x[9][0] = -0.25;  y[9][0] = -0.5;  z[9][0] = -0.5;
     x[9][1] = -0.25;  y[9][1] = -1.0;  z[9][1] = -0.5;
     x[9][2] = -0.5;   y[9][2] = -1.0;  z[9][2] = -0.5;
     //load color
     fcolor[9][0] = 0.0;  fcolor[9][1] = 0.0;  fcolor[9][2] = 0.0;
     
     //right leg
     x[10][0] = 0.25;   y[10][0] = -0.5;  z[10][0] = 0.5;
     x[10][1] = 0.25;   y[10][1] = -1.0;  z[10][1] = 0.5;
     x[10][2] = 0.0;    y[10][2] = -1.0;  z[10][2] = 0.5;
     //load color
     fcolor[10][0] = 0.0;  fcolor[10][1] = 0.0;  fcolor[10][2] = 0.0;
     
     //shadow point of right leg
     x[11][0] = 0.25;   y[11][0] = -0.5;  z[11][0] = -0.5;
     x[11][1] = 0.25;   y[11][1] = -1.0;  z[11][1] = -0.5;
     x[11][2] = 0.0;    y[11][2] = -1.0;  z[11][2] = -0.5;
     //load color
     fcolor[11][0] = 0.0;  fcolor[11][1] = 0.0;  fcolor[11][2] = 0.0;
     
     //panels connecting legs--->break legs into sections (leg/foot)
     //left foot
     x[12][0] = -0.25;   y[12][0] = -1.0;  z[12][0] = 0.5;
     x[12][1] = -0.5;	y[12][1] = -1.0;  z[12][1] = 0.5;
     x[12][2] = -0.5;	y[12][2] = -1.0;  z[12][2] = -0.5;
     x[12][3] = -0.25;   y[12][3] = -1.0;  z[12][3] = -0.5;
     //color
     fcolor[12][0] = 0.0;    fcolor[12][1] = 0.0;  fcolor[12][2] = 0.0;
    
     //right foot 
     x[13][0] = 0.25;   y[13][0] = -1.0;  z[13][0] = 0.5;
     x[13][1] = 0.0;    y[13][1] = -1.0;  z[13][1] = 0.5;
     x[13][2] = 0.0;    y[13][2] = -1.0;  z[13][2] = -0.5;
     x[13][3] = 0.25;   y[13][3] = -1.0;  z[13][3] = -0.5;
     //color
     fcolor[13][0] = 0.0;    fcolor[13][1] = 0.0;  fcolor[13][2] = 0.0;
     
 //end drawing of polyman
    return;
}    /*     end of load icon       */

     /******************************function calcNormal*********************/
 void calcNormals(float x1, float y1, float z1, float x2, float y2, float z2,
                    float x3, float y3, float z3, float ui, float uj, float uk)
 {/* this function calculates a normal vector to P1(x,y,z)-P2(x,y,z) and P1(x,y,z)-P3(x,y,z) */
    float a1, a2, a3, b1, b2, b3, ix, jy, kz, ul;
    a1 = x2 - x1;
    a2 = y2 - y1;
    a3 = z2 - z1;
    b1 = x3 - x1;
    b2 = y3 - y1;
    b3 = z3 - z1;
    ix = (a2 * b3 - a3 * b2);
    jy = (a3 * b1 - a1 * b3);
    kz = (a1 * b2 - a2 * b1);

     // now calculate the values of the unit vector
    ul = sqrtf(ix * ix + jy * jy + kz * kz);
    ui = ix / ul;
    uj = jy / ul;
    uk = kz / ul;
    return;
 }
     /************************* function drawicon  ***************************/
 void drawicon(float x[][7], float y[][7], float z[][7], float fcolor[][3], float nvector[][3])
 {
    /*     this function draws the polyman at the transformed position              */
    
    int i, face;

    for (face = 0; face <= 3; face++)
    {// note that the polyman is with front face counterclockwise
        // render each face of the polyman
        glColor3f(fcolor[face][0], fcolor[face][1], fcolor[face][2]);
        glBegin(GL_POLYGON);
        glNormal3f(nvector[face][0], nvector[face][1], nvector[face][2]);
        for (i = 0; i <= 5; i++) {  
        glVertex3f(x[face][i], y[face][i], z[face][i]);
        }
        glEnd();
    }//top and bottom
    for (face = 4; face <= 5; face++) {
        glColor3f(fcolor[face][0], fcolor[face][1], fcolor[face][2]);
        glBegin(GL_POLYGON);
        glNormal3f(nvector[face][0], nvector[face][1], nvector[face][2]);
        for (i = 0; i <= 3; i++) {
            glVertex3f(x[face][i], y[face][i], z[face][i]);
        }
        glEnd();
    }//eyes
    for (face = 6; face <= 7; face++) {
        glColor3f(fcolor[face][0], fcolor[face][1], fcolor[face][2]);
        glPointSize(4.0);
        glBegin(GL_POINTS);
        glVertex3f(x[face][0], y[face][0], z[face][0]);
        glEnd();
    }
    glMatrixMode(GL_MODELVIEW);
    glLoadIdentity();
    glTranslatef(0.0, -1.0, 0.0);
    glRotatef(25.0, 1.0, 0.0, 0.0);
    glScalef(2.0, 2.0, 2.0);
    glBegin(GL_POLYGON);
    glColor3f(0.6, 0.6, 0.6);
    //draw the dance floor
    glVertex3f(-2.0, 0.0, 1.0);
    glVertex3f(2.0, 0.0, 1.0);
    glVertex3f(1.5, 0.0, -1.0);
    glVertex3f(-1.5, 0.0, -1.0);
    glEnd();

    glFlush();
    return;
 }//end of draw icon

 void drawiconL(float x[][7], float y[][7], float z[][7], float fcolor[][3], float nvector[][3]) {
     /*     this function draws the polyman legs at the transformed position              */

     int i, face;
     //legs 
    for (face = 8; face <= 11; face++) {
        glColor3f(fcolor[face][0], fcolor[face][1], fcolor[face][2]);
        glBegin(GL_LINE_STRIP);
        for (i = 0; i <= 2; i++) glVertex3f(x[face][i], y[face][i], z[face][i]);
        glEnd();
    }//feet-->
    for (face = 12; face <= 13; face++) {
        glColor3f(fcolor[face][0], fcolor[face][1], fcolor[face][2]);
        glBegin(GL_LINE_STRIP);
        for (i = 0; i <= 3; i++) glVertex3f(x[face][i], y[face][i], z[face][i]);
        glVertex3f(x[face][0], y[face][0], z[face][0]);
        glEnd();
    }
    glFlush();
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
    glRotatef(theta, 0.0, 1.0, 0.0);// note that the angle theta is in degrees, not radians
    glRotatef(theta2, 1.0, 0.0, 1.0);
    glRotatef(-180.0, 0.0, 1.0, 0.0);
    return;
 }

 /**************************  function settrans3  ***********************/
 void settrans3(void)

     //Sets the translation matrix for the polyman
 {
     cout << "in settrans3" << endl;
     glMatrixMode(GL_MODELVIEW);
     glLoadIdentity();
     glTranslatef(dx, dy, dz);
     glRotatef(thetal, 0.0, 0.0, 1.0);
     glRotatef(theta, 0.0, 1.0, 0.0);// note that the angle theta is in degrees, not radians
     glRotatef(theta2, 1.0, 0.0, 1.0);
     glRotatef(-180.0, 0.0, 1.0, 0.0);
     return;
 }

 //****************************Function SetupRC*************************************
 // Setup the rendering state
 void SetupRC(void)
 {// this function sets the clear color of an open window and clears the open window
  // Set clear color to green
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
     case 1://discopoly moves to center stage
         theta += 0.0;
         thetal -= 5.0;
         dx += 0.15;

         if (thetal <= -25.0) {
             thetal = 0.0;
         }
         if (dx >= 0.0) {
             dx = 0.0;
             thetal += 0.0;
             frame = 2;
         }
         
         break;

     case 2://discopoly turns left
         theta += 5.0;
         if (theta >= 90.0) {
             theta = 90.0;
             frame = 3;
         }
         break;

     case 3://discopoly turns right
         theta -= 5.0;
         if (theta <= -90.0) {
             theta = -90.0;
             frame = 4;
         }
         break;

     case 4://discopoly turns back to original position
         theta += 5.0;
         if (theta >= 0.0) {
             theta = 0.0;
             frame = 5;
         }
         break;

     case 5://discopoly jumps and does a couple flips
         dy += 0.2;

         if (dy >= 2.0) {
             dy = 2.0;
             theta2 += 5.0;
         }
         if (theta2 >= 360.0) {
             theta2 = 0.0;
             
             frame = 6;
         }

         break;
     case 6://discopoly lands 
         dy -= 0.2;
         if (dy <= 0.0) {
             dy = 0.0;
             
         }
         break;
     }
    // Redraw the scene with new coordinates
    glutPostRedisplay();
    glutTimerFunc(33, TimerFunction, 1);
 }

