import java.util.Objects;

public class city {
    //Initializes variables based on field names of columns found in the DB
    protected String city;
    protected String state;
    private float medianage;
    private int males;
    private int females;
    private int population;
    private int veterans;
    private int foreign;
    private float avhhsize;
    private String stateabbr;
    private int AmericanIndian_AlaskaNative;
    private int Black_AfricanAmerican;
    private int Hispanic_Latino;
    private int Asian;
    private int White;

    //initializes and creates the constructor for class city
    public city(String city, String state) {
        setCity(city);
        setState(state);
    }//end of constructor

    //initializes and creates the getters and setters for class city
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setMedianage(float medage) {
        this.medianage = medage;
    }
    public float getMedianage() {
        return medianage;
    }
    public int getMales() {
        return males;
    }
    public void setMales(int M) {
        this.males = M;
    }
    public int getFemales() {
        return females;
    }
    public void setFemales(int F) {
        this.females = F;
    }
    public int getPopulation() {
        return population;
    }
    public void setPopulation(int pop) {
        this.population = pop;
    }
    public int getVeterans() {
        return veterans;
    }
    public void setVeterans(int vets) {
        this.veterans = vets;
    }
    public int getForeign() {
        return foreign;
    }
    public void setForeign(int foreign) {
        this.foreign = foreign;
    }
    public float getAvhhsize() {
        return avhhsize;
    }
    public void setAvhhsize(float hhsize) {
        this.avhhsize = hhsize;
    }
    public String getStateabbr() {
        return stateabbr;
    }
    public void setStateabbr(String stabbr) {
        this.stateabbr = stabbr;
    }
    public int getAmericanIndian_AlaskaNative() {
        return AmericanIndian_AlaskaNative;
    }
    public void setAmericanIndian_AlaskaNative(int AI_AN) {
        this.AmericanIndian_AlaskaNative = AI_AN;
    }
    public int getBlack_AfricanAmerican() {
        return Black_AfricanAmerican;
    }
    public void setBlack_AfricanAmerican(int B_AA) {
        this.Black_AfricanAmerican = B_AA;
    }
    public int getHispanic_Latino() {
        return Hispanic_Latino;
    }
    public void setHispanic_Latino(int H_L) {
        this.Hispanic_Latino = H_L;
    }
    public int getAsian() {
        return Asian;
    }
    public void setAsian(int Asian) {
        this.Asian = Asian;
    }
    public int getWhite() {
        return White;
    }
    public void setWhite(int White) {
        this.White = White;
    }
    //end of getter and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof city city1)) return false;
        return Float.compare(city1.getMedianage(), getMedianage()) == 0 && getMales() == city1.getMales() && getFemales() == city1.getFemales() && getPopulation() == city1.getPopulation() && getVeterans() == city1.getVeterans() && getForeign() == city1.getForeign() && Float.compare(city1.getAvhhsize(), getAvhhsize()) == 0 && getAmericanIndian_AlaskaNative() == city1.getAmericanIndian_AlaskaNative() && getBlack_AfricanAmerican() == city1.getBlack_AfricanAmerican() && getHispanic_Latino() == city1.getHispanic_Latino() && getAsian() == city1.getAsian() && getWhite() == city1.getWhite() && getCity().equals(city1.getCity()) && getState().equals(city1.getState()) && Objects.equals(getStateabbr(), city1.getStateabbr());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCity(), getState(), getMedianage(), getMales(), getFemales(), getPopulation(), getVeterans(), getForeign(), getAvhhsize(), getStateabbr(), getAmericanIndian_AlaskaNative(), getBlack_AfricanAmerican(), getHispanic_Latino(), getAsian(), getWhite());
    }

    //override toString method to print city records to a tab delimited format
    @Override
    public String toString() {
        String string;
        string = String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
                city,
                state,
                medianage,
                males,
                females,
                population,
                veterans,
                foreign,
                avhhsize,
                stateabbr,
                AmericanIndian_AlaskaNative,
                Black_AfricanAmerican,
                Hispanic_Latino,
                Asian,
                White);
        return string;
    } // end of toString
}
