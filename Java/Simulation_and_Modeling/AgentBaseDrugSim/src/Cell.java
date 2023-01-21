package src;
public class Cell{
    protected int celli; //this is the i location of cell in the arrays
    protected int cellj; //this is the j location of cell in the arrays
    //cells can change under 3 conditions 1 = healthy, 2 = muscle wilt(DMD), 3 = youth 
    protected int cellcond;         
    protected int lasttendril;//this is the iteration since my last tendril
    protected int lastcure; //this is the iteration since my last cure
    protected int tendrildir; //tendril direction if DMD 1 = up, 2 = down, 3 = back, 4= front
    protected int irows; //rows of the curcell/healcell go from 0 - irow/ncol
    protected int jcols; //cols of the curcell/healcell go from 0 - jcol/ncol
    protected int cellage = 50;//age of cell

    //this is the constructor for a cell object it sets my location and
    //sets the original condition of the cell
    public Cell(int i, int j, int n, int m){
        //curcells i, j
        celli = i;
        cellj = j;

        irows = n - 1;
        jcols = m - 1;

        cellcond = 1;
        
        int x;
        x = (int) (Math.random() * 100);
            if(x <= 15)//tendrildir up
                tendrildir = 1;
                else
                if(x <= 30)//tendrildir down
                    tendrildir = 2;
                    else 
                    if(x <= 65)//tendrildir back
                        tendrildir = 3;
                        else
                            tendrildir = 4; //tendrildir front

        lasttendril = 1;
        lastcure = 1;

    }//end of cell constructor

    public int getcond(){
        return cellcond;
    }

    int getcellage(){
        return cellage;
    }

    int gettendrildir() {
        return tendrildir;
    }

    int getlasttendril(){
        return lasttendril;
    }

    int getlastcure(){
        return lastcure;
    }

    int getcelli(){
        return celli;
    }

    int getcellj(){
        return cellj;
    }
 
    public int updatelasttendril(){
        lasttendril++;
        return lasttendril;
    }
    public int updatelastcure(){
        lastcure++;
        return lastcure;
    }

    void celltendril(int curcells[][], int celli, int cellj){
        /*This is the primary behavior of the MW cell. 
        This function will be assessed for each cell once per year*/
        //first some integers for later
        //int i, j;
        
        //determine if this cell can change condition
        if(lasttendril >= 1){
            //this cell can change, lets see if neighbouring cells have acceptable change conditions
            //first set the cell condition to healthy
            //make 5% of the cells muscle wilt cells
            int x;
            x = (int) (Math.random() * 100);
            if(x <= 5){
                cellcond = 2;
            }else{
                cellcond = 1;
            }
            lasttendril = 1;

            //there are many special conditions for the position of the cell in the curcell array
            //first we check to see if this cell is in one of the corners of the curcell
            if((celli == 0) && (cellj == 0)){
                //we are in the upper left corner at curcells[0][0]
                //check for HM to the right(front) to infect cell
                if(tendrildir == 4 && curcells[0][1] == 1){
                    cellcond = 2;
                    curcells[0][1] = cellcond;
                    //now set change cell condition count
                    lasttendril = 1;
                }
                //check for HM below(down) to infect cell
                if(tendrildir == 2 && curcells[1][0] == 1){
                    cellcond = 2;
                    curcells[1][0] = cellcond;
                    //now set change cell condition count
                    lasttendril = 1;
                }
            }//done with upper left corner
            else 
            if((celli == 0) && (cellj == jcols - 1)){
                //we are in the upper right corner of curcells
                //check for HM to the left(back) to infect cell
                if(tendrildir == 3 && curcells[0][jcols - 2] == 1) {
                    cellcond = 2;
                    curcells[0][jcols - 2] = cellcond;
                    //now set change cell condition count
                    lasttendril = 1;
                }
                //check for HM below(down) to infect cell
                if(tendrildir == 2 && curcells[1][jcols - 1] == 1) {
                    cellcond = 2;
                    curcells[1][jcols - 1] = cellcond;
                    //now set change cell condition count
                    lasttendril = 1;
                }
            }//done with upper left corner
            else 
            if((celli == irows - 1) && (cellj == 0)){
                //we are in the lower left corner
                //check for HM to the right(front) to infect cell
                if(tendrildir == 4 && curcells[irows - 1][1] == 1){
                    cellcond = 2;
                    curcells[irows - 1][1] = cellcond;
                    //now set change cell condition count
                    lasttendril = 1;
                }
                //check for HM above(up) to infect cell
                if(tendrildir == 1 && curcells[irows - 2][0] == 1){
                    cellcond = 2;
                    curcells[irows - 2][0] = cellcond;
                    //now set change cell condition count
                    lasttendril = 1;
                }
            }//done with lower left corner
            else 
            if((celli == irows - 1) && (cellj == jcols - 1)){
                //we are in the lower right corner
                //check for HM to the left(back) to infect cell
                if(tendrildir == 3 && curcells[irows - 1][jcols - 2] == 1){
                    cellcond = 2;
                    curcells[irows - 1][jcols - 2] = cellcond;
                    //now set change cell condition
                    lasttendril = 1;
                }
                //check for HM above(up) to infect cell
                if(tendrildir == 1 && curcells[irows - 2][jcols - 1] == 1){
                    cellcond = 2;
                    curcells[irows - 2][jcols - 1] = cellcond;
                    //now set change cell condition
                    lasttendril = 1;
                }
            }//done with lower right corner
            else                    //now we check the upper row of curcells
            if((celli == 0) && (cellj > 0) && (cellj < jcols - 1)){ 
                //look for match on the right(front)
                if(tendrildir == 4 && curcells[0][cellj + 1] == 1){
                    //they match on the right(front)
                    cellcond = 2;
                    curcells[0][cellj + 1] = cellcond;
                    //now set change cell condition
                    lasttendril = 1;
                }
                else //look for match on the left(back)
                if(tendrildir == 3 && curcells[0][cellj - 1] == 1){
                    cellcond = 2;
                    curcells[0][cellj - 1] = cellcond;
                    //set change condition count
                    lasttendril = 1;
                }
                else //look for match on the bottom(down)
                if(tendrildir == 2 && curcells[1][cellj + 1] == 1){
                    cellcond = 2;
                    curcells[1][cellj + 1] = cellcond;
                    //set change condition count
                    lasttendril = 1;
                }
            }//done with first row of curcells
            else                    //now we check the bottom row of curcells
            if((celli == (irows)) && (cellj > 0) && (cellj < jcols)){
                //look for match on right(front)
                if(tendrildir == 4 && curcells[irows][cellj + 1] == 1){
                    cellcond = 2;
                    curcells[irows][cellj + 1] = cellcond;
                    //set change condition count
                    lasttendril = 1;
                }
                else //look for match on left(back)
                if(tendrildir == 3 && curcells[irows][cellj - 1] == 1){
                    cellcond = 2;
                    curcells[irows][cellj - 1] = cellcond;
                    //set change condition count
                    lasttendril = 1;
                }
                else //look for match on top(up)
                if(tendrildir == 1 && curcells[irows - 1][cellj + 1] == 1){
                    cellcond = 2;
                    curcells[irows - 1][cellj + 1] = cellcond;
                    //set change condition count
                    lasttendril = 1;
                }
            }//done with bottom row of curcells
            else                    //now we check the left column of curcells
            if((cellj == 0) && (celli > 0) && (celli < irows - 1)){ 
                //look for match on above(up)
                if(tendrildir == 1 && curcells[celli - 1][0] == 1){
                    //they match above(up)
                    cellcond = 2;
                    curcells[celli - 1][0] = cellcond;
                    //now set change cell condition
                    lasttendril = 1;
                }
                else //look for match below(down)
                if(tendrildir == 2 && curcells[celli + 1][0] == 1){
                    //they match below(down)
                    cellcond = 2;
                    curcells[celli + 1][0] = cellcond;
                    //now set change cell condition
                    lasttendril = 1;
                }
                else //look for match on the right(front)
                if(tendrildir == 4 && curcells[celli + 1][1] == 1){
                    //they match on the right(front)
                    cellcond = 2;
                    curcells[celli + 1][1] = cellcond;
                    //set change condition count
                    lasttendril = 1;
                }
            }//done with left column of curcells
            else                    //now we check the right column of curcells
            if((cellj == (jcols - 1)) && (celli > 0) && (celli < irows - 1)){
                //look for match above(top)
                if(tendrildir == 1 && curcells[celli - 1][cellj] == 1){
                    //they match above(top)
                    cellcond = 2;
                    curcells[celli - 1][cellj] = cellcond;
                    //set change condition count
                    lasttendril = 1;
                }
                else //look for match below(down)
                if(tendrildir == 2 && curcells[celli + 1][cellj] == 1){
                    //they match below(down)
                    cellcond = 2;
                    curcells[celli + 1][cellj] = cellcond;
                    //set change condition count
                    lasttendril = 1;
                }
                else //look for match to the left(back)
                if(tendrildir == 3 && curcells[celli][cellj - 1] == 1){
                    //they match to the left(back)
                    cellcond = 2;
                    curcells[celli][cellj - 1] = cellcond;
                    //set change condition count
                    lasttendril = 1;
                }
            }//done with right column curcells
            else                //we have cells in the middle of the array away from rows,columns, and corners
            if((celli > 0) && (celli < irows - 1) && (cellj > 0) && (cellj < jcols - 1)){
                //we have a possible cell match with 4 conditions
                //CONDITION 1: match above the cell 
                if(tendrildir == 1 && curcells[celli - 1][cellj] == 1){
                    //they matched above(up)
                    cellcond = 2;
                    curcells[celli - 1][cellj] = cellcond;
                    //set change condition count
                    lasttendril = 1;
                }
                else //CONDITION 2: match below the cell 
                if(tendrildir == 2 && curcells[celli + 1][cellj] == 1){
                    //they matched below(down)
                    cellcond = 2;
                    curcells[celli + 1][cellj] = cellcond;
                    //set change condition count
                    lasttendril = 1;
                }
                else //CONDITION 3: match to left(back) of the cell 
                if(tendrildir == 3 && curcells[celli][cellj - 1] == 1){
                    //they matched left(back)
                    cellcond = 2;
                    curcells[celli][cellj - 1] = cellcond;
                    //set change condition count
                    lasttendril = 1;
                }
                else //CONDITION 4: match to right(front) of the cell 
                if(tendrildir == 4 && curcells[celli][cellj + 1] == 1){
                    //they matched left(back)
                    cellcond = 2;
                    curcells[celli][cellj + 1] = cellcond;
                    //set change condition count
                    lasttendril = 1;
                }
            }//done with cell in middle
        }//end of if(lasttendril >= 1)
        return;
    }//end of function celltendril

    public void curecell(int [][] healcells, int celli, int cellj){
        /*This is the primary behavior of the Youth cell. 
                        This function will be assessed for each cell once per year*/

        //determine if this cell can change condition
        if(lastcure >= 1){
            //this cell can change, lets see if neighbouring cells have acceptable change conditions
            //first set the cell condition to muscle wilt
            int x;
            x = (int) (Math.random() * 100);
            if(x <= 85){
                cellcond = 1;
            }else{
                cellcond = 2;
            }
            lastcure = 1;

            //there are many special conditions for the position of the cell in the healcells array
            //first we check to see if this cell is in one of the corners of the curcell
            if((celli == 0) && (cellj == 0)){
                //we are in the upper left corner at curcells[0][0]
                //check for HM to the right(front) to infect cell
                if(healcells[0][2] == 2){
                    cellcond = 3;
                    healcells[0][2] = cellcond;
                    //now set change cell condition count
                    lastcure = 1;
                }
                //check for HM below(down) to infect cell
                if(healcells[2][0] == 2){
                    cellcond = 3;
                    healcells[2][0] = cellcond;
                    //now set change cell condition count
                    lastcure = 1;
                }
            }//done with upper left corner
            else 
            if((celli == 0) && (cellj == jcols - 1)){
                //we are in the upper right corner of curcells
                //check for HM to the left(back) to infect cell
                if(healcells[0][jcols - 3] == 2) {
                    cellcond = 3;
                    healcells[0][jcols - 3] = cellcond;
                    //now set change cell condition count
                    lastcure = 1;
                }
                //check for HM below(down) to infect cell
                if(healcells[2][jcols - 2] == 2) {
                    cellcond = 3;
                    healcells[2][jcols - 2] = cellcond;
                    //now set change cell condition count
                    lastcure = 1;
                }
            }//done with upper left corner
            else 
            if((celli == irows - 1) && (cellj == 0)){
                //we are in the lower left corner
                //check for HM to the right(front) to infect cell
                if(healcells[irows - 1][2] == 2){
                    cellcond = 3;
                    healcells[irows - 1][2] = cellcond;
                    //now set change cell condition count
                    lastcure = 1;
                }
                //check for HM above(up) to infect cell
                if(healcells[irows - 3][0] == 2){
                    cellcond = 3;
                    healcells[irows - 3][0] = cellcond;
                    //now set change cell condition count
                    lastcure = 1;
                }
            }//done with lower left corner
            else 
            if((celli == irows - 1) && (cellj == jcols - 1)){
                //we are in the lower right corner
                //check for HM to the left(back) to infect cell
                if(healcells[irows - 1][jcols - 3] == 2){
                    cellcond = 3;
                    healcells[irows - 1][jcols - 3] = cellcond;
                    //now set change cell condition
                    lastcure = 1;
                }
                //check for HM above(up) to infect cell
                if(healcells[irows - 3][jcols - 1] == 2){
                    cellcond = 3;
                    healcells[irows - 3][jcols - 1] = cellcond;
                    //now set change cell condition
                    lastcure = 1;
                }
            }//done with lower right corner
            else                    //now we check the upper row of healcells
            if((celli == 0) && (cellj > 0) && (cellj < jcols - 1)){ 
                //look for match on the right(front)
                if(healcells[0][cellj + 2] == 2){
                    //they match on the right(front)
                    cellcond = 3;
                    healcells[0][cellj + 2] = cellcond;
                    //now set change cell condition
                    lastcure = 1;
                }
                else //look for match on the left(back)
                if(healcells[0][cellj] == 2){
                    cellcond = 3;
                    healcells[0][cellj] = cellcond;
                    //set change condition count
                    lastcure = 1;
                }
                else //look for match on the bottom(down)
                if(healcells[0][cellj + 2] == 2){
                    cellcond = 3;
                    healcells[0][cellj + 2] = cellcond;
                    //set change condition count
                    lastcure = 1;
                }
            }//done with first row of healcells
            else                    //now we check the bottom row of healcells
            if(celli == (irows) && (cellj > 0) && (cellj < jcols)){
                //look for match on right(front)
                if(healcells[irows][cellj] == 2){
                    cellcond = 3;
                    healcells[irows][cellj + 2] = cellcond;
                    //set change condition count
                    lastcure = 1;
                }
                else //look for match on left(back)
                if(healcells[irows][cellj - 1] == 2){
                    cellcond = 3;
                    healcells[irows][cellj - 2] = cellcond;
                    //set change condition count
                    lastcure = 1;
                }
                else //look for match on top(up)
                if(healcells[irows - 2][cellj] == 2){
                    cellcond = 3;
                    healcells[irows - 2][cellj] = cellcond;
                    //set change condition count
                    lastcure = 1;
                }
            }//done with bottom row of curcells
            else                    //now we check the left column of curcells
            if((cellj == 0) && (celli > 0) && (celli < irows - 1)){ 
                //look for match on above(up)
                if(healcells[celli][0] == 2){
                    //they match above(up)
                    cellcond = 3;
                    healcells[celli][0] = cellcond;
                    //now set change cell condition
                    lastcure = 1;
                }
                else //look for match below(down)
                if(healcells[celli + 2][0] == 2){
                    //they match below(down)
                    cellcond = 3;
                    healcells[celli + 2][0] = cellcond;
                    //now set change cell condition
                    lastcure = 1;
                }
                else //look for match on the right(front)
                if(healcells[celli][2] == 2){
                    //they match on the right(front)
                    cellcond = 3;
                    healcells[celli][2] = cellcond;
                    //set change condition count
                    lastcure = 1;
                }
            }//done with left column of curcells
            else                    //now we check the right column of curcells
            if((cellj == (jcols - 1)) && (celli > 0) && (celli < irows - 1)){
                //look for match above(top)
                if(healcells[celli - 1][cellj] == 2){
                    //they match above(top)
                    cellcond = 3;
                    healcells[celli - 2][cellj] = cellcond;
                    //set change condition count
                    lastcure = 1;
                }
                else //look for match below(down)
                if(healcells[celli + 2][cellj] == 2){
                    //they match below(down)
                    cellcond = 3;
                    healcells[celli + 2][cellj] = cellcond;
                    //set change condition count
                    lastcure = 1;
                }
                else //look for match to the left(back)
                if(healcells[celli][cellj - 2] == 2){ 
                    //they match to the left(back)
                    cellcond = 3;
                    healcells[celli][cellj - 2] = cellcond;
                    //set change condition count
                    lastcure = 1;
                }
            }//done with right column curecells
            else                //we have cells in the middle of the array away from rows,columns, and corners
            if((celli > 0) && (celli < irows - 1) && (cellj > 0) && (cellj < jcols - 1)){
                //we have a possible cell match with 4 conditions
                //CONDITION 1: match above the cell 
                if(healcells[celli - 1][cellj] == 2){
                    //they matched above(up)
                    cellcond = 3;
                    healcells[celli - 1][cellj] = cellcond;
                    //set change condition count
                    lastcure = 1;
                }
                else //CONDITION 2: match below the cell 
                if(healcells[celli + 2][cellj] == 2){
                    //they matched below(down)
                    cellcond = 3;
                    healcells[celli + 2][cellj] = cellcond;
                    //set change condition count
                    lastcure = 1;
                }
                else //CONDITION 3: match to left(back) of the cell 
                if(healcells[celli][cellj - 1] == 2){
                    //they matched left(back)
                    cellcond = 3;
                    healcells[celli][cellj - 2] = cellcond;
                    //set change condition count
                    lastcure = 1;
                }
                else //CONDITION 4: match to right(front) of the cell 
                if(healcells[celli][cellj + 2] == 2){
                    //they matched left(back)
                    cellcond = 3;
                    healcells[celli][cellj + 2] = cellcond;
                    //set change condition count
                    lastcure = 1;
                }
            }//done with cell in middle
        }//end of if(lastcure >= 1)
        return;
    }//end of function curecell
}//end of class Cell