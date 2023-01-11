import java.io.PrintWriter;

class Cubes extends Solids {
    //constructor for Cubes
    public Cubes(int len, int wid, int hig, PrintWriter outf){
        super(len, wid, hig, outf);
    }

    public void calcarea(){
        area = 6 * Math.pow(len, 2);
    }

    public void calcvol(){
        volume = Math.pow(len, 3);
    }

    public String getName(){
        return "Cube";
    }

    public void printsolid(PrintWriter outf){
        outf.println("Cube with length: " + len);
        System.out.println("Cube with length: " + len);
        outf.println("Area: " + area);
        System.out.println("Area: " + area);
        outf.println("Volume: " + volume);
        System.out.println("Volume: " + volume);
    }
}//end of Cube
