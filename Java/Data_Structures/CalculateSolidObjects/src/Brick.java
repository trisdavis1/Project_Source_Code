import java.io.PrintWriter;

class Brick extends Solids{
    //constructor for Brick
    public Brick(int len, int wid, int hig, PrintWriter outf){
        super(len, wid, hig, outf);
    }

    public void calcarea(){
        area = 2 * len * wid + 2 * wid * hig + 2 * len * hig;
    }

    public void calcvol(){
        volume = len * wid * hig;
    }

    public String getName(){
        return "Brick";
    }

    public void printsolid(PrintWriter outf){
        outf.println("Brick with Length: " + len + " Width: " + wid + " Height " + hig);
        System.out.println("Brick with Length: " + len + " Width: " + wid + " Height: " + hig);
        outf.println("Area: " + area);
        System.out.println("Area " + area);
        outf.println("Volume: " + volume);
        System.out.println("Volume: " + volume);
    }

}//end of Brick
