import java.io.PrintWriter;

class TruncCones extends Solids{
    //define 2 different radius so we can calculate the correct area and volume
    protected int radius1;
    protected int radius2;

    public TruncCones(int len, int wid, int hig, int radius1, int radius2, PrintWriter outf){
        super(len, wid, hig, outf);
        this.radius1 = radius1;
        this.radius2 = radius2;
    }

    public void calcarea(){
        area = 3.1416 * (radius1 + radius2) * Math.sqrt(hig * hig + Math.pow(radius2 - radius1, 2));
    }

    public void calcvol(){
        volume = 1.0/3.0 * 3.1416 * hig * (Math.pow(radius1, 2) + Math.pow(radius2, 2) + radius1 * radius2);
    }

    public String getName(){
        return "Truncated Cone";
    }

    public void printsolid(PrintWriter outf){
        outf.println("Truncated Cone with Height: " + hig + " Radius1: " + radius1 + " and Radius2: " + radius2);
        System.out.println("Truncated Cone with Height: " + hig + " Radius1: " + radius1 + " and Radius2: " + radius2);
        outf.println("Area: " + area);
        System.out.println("Area: " + area);
        outf.println("Volume: " + volume);
        System.out.println("Volume " + volume);
    }

}//end of TruncCones
