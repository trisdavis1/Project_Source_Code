import java.io.PrintWriter;

class Cone extends Solids {

    protected int radius;
    protected double slantheight = Math.sqrt(radius * radius + hig * hig);//need this to calculate the correct area
    //create constructor for Cone
    public Cone(int len, int wid, int hig, int radius, PrintWriter outf){
        super(len, wid, hig, outf);
        this.radius = radius;
    }

    public void calcarea(){
        area = 3.1416 * radius * radius + 3.1416 * slantheight * radius;
    }

    public void calcvol(){
        volume = 1.0/3.0 * 3.1416 * radius * radius * hig;
    }

    public String getName(){
        return "Cone";
    }

    public void printsolid(PrintWriter outf){
        outf.println("Cone with Height: " + hig + " and Radius: " + radius);
        System.out.println("Cone with Height: " + hig + " and Radius: " + radius);
        outf.println("Area: " + area);
        System.out.println("Area " + area);
        outf.println("Volume: " + volume);
        System.out.println("Volume " + volume);
    }
}//end of Cone
