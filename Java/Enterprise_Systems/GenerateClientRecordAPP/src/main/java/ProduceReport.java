import com.google.gson.*;
import com.google.gson.JsonObject;
import java.io.FileWriter;

public class ProduceReport {

    static String fileName;
    static Client client;
    static double latitude;
    static double longitude;
    public static double medianage;
    public static int population;

    public ProduceReport(String fileName, Client client) {
        ProduceReport.fileName = fileName;
        setClient(client);
        setLatitude(latitude);
        setLongitude(longitude);
        setMedianage(medianage);
        setPopulation(population);
    }

    public void createJson(JsonObject object) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer = new FileWriter(fileName);
        writer.write(gson.toJson(object));
        writer.flush();
        writer.close();
    }

    public static Client getClient() {
        return client;
    }
    public static void setClient(Client client) {
        ProduceReport.client = client;
    }
    public static double getLatitude() {
        return latitude;
    }
    public static void setLatitude(double latitude) {
        ProduceReport.latitude = latitude;
    }
    public static double getLongitude() {
        return longitude;
    }
    public static void setLongitude(double longitude) {
        ProduceReport.longitude = longitude;
    }
    public static double getMedianage() {
        return medianage;
    }
    public static void setMedianage(double medianage) {
        ProduceReport.medianage = medianage;
    }
    public static int getPopulation() {
        return population;
    }
    public static void setPopulation(int population) {
        ProduceReport.population = population;
    }

    @Override
    public String toString() {
        String result = String.format("{%s, \"longitude\": \"%s\", \"latitude\": \"%s\", " +
                        "\"medianage\": \"%s\",\"population\": \"%d\"}",
                client, longitude, latitude, medianage, population);
        return result;
    }
}//end of class ProduceReport
