import java.io.PrintWriter;
//Create class Solids
public class Solids extends Shapes implements Comparable{
    //Solids is the parent class of Cubes, Sphere, Cone, Brick, and TruncCones,
    //these classes are compared based on their areas and their must be only one comparison.
    //The comparison must exist at class level above all the subclass levels so their areas can be compared

    protected int len;
    protected int wid;
    protected int hig;
    protected double area;
    protected double volume;
    protected PrintWriter outf;
    //create the constructor for Solids
    public Solids(int len, int wid, int hig, PrintWriter outf) {
        this.len = len;
        this.wid = wid;
        this.hig = hig;
        this.outf = outf;

    }//end Solids constructor
    //implement compareTo function for Solids
    public int compareTo(Object o){
        if(getarea() > ((Solids)o).getarea())
            return 1;
        else
        if(getarea() < ((Solids)o).getarea())
            return - 1;
        else
            return 0;

    }//end compareTo function
    public void calcarea(){
        return; //is a placeholder so we can calculate the area of other Solids
    }
    public double getarea() {
        return 0.0; //need this for compareTo
    } //need this because we use a get area from lower classes
    public void calcvol(){
        return;
    }// need this for calculation of other Solids from subclasses

    public double getvol() {
        return volume;
    }
    public String getName(){
        return "Solid";
    }

}//end of Solids