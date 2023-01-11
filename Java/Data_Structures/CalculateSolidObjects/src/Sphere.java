import java.io.PrintWriter;

class Sphere extends Solids {

    protected int radius;
    //constructor for Sphere
    public Sphere(int len, int wid, int hig, int radius, PrintWriter outf){
        super(len, wid, hig, outf);
        this.radius = radius;
    }

    public void calcarea(){
        area = 3.1416 * 2 * radius * 2 * radius;
    }

    public void calcvol(){
        volume = 4.0 / 3.0 * 3.1416 * radius * radius * radius;
    }

    public String getName(){
        return "Sphere";
    }

    public void printsolid(PrintWriter outf){
        outf.println("Sphere with Radius: " + radius);
        System.out.println("Sphere with Radius: " + radius);
        outf.println("Area: " + area);
        System.out.println("Area: " + area);
        outf.println("Volume: " + volume);
        System.out.println("Volume: " + volume);
    }

}//end of Sphere
