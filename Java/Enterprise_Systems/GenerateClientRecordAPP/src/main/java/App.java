import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.noynaert.sqlCredentials.SqlCredentials;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategyBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * todo: Midterm Take Home Implementing packages and functions discussed thus far in Enterprise Systems Java
 *
 * @author: Tristan Davis
 * @since: March, 2022
 */

public class App {
    //initializing Logger access
    private static final Logger log = LogManager.getLogger(App.class);
    public static void main(String[] args) {
        //logging the start of the program
        log.info("Starting\n");
        //initialize credentials xml file
        SqlCredentials credentials = new SqlCredentials("woz.xml");
        List<Client> clients = new ArrayList<>();
        //initializes the command line arguments with a string FileName
        String fileName = "File name not specified";

        //initialize credential dependency for user, host, and password extraction
        String host = credentials.getHost();
        String user = credentials.getUser();
        String password = credentials.getPassword();

        //here we implement step 1
        log.trace("This is step 1!\n");
        for(int i = 0; i < args.length; i++) {
            if(args.length > 0){
                //logs the filenames in args[] array
                log.info("Step 1: args[" + i + "] is " + args[i]);
            }else{
                log.error("Error: No file name specified in args["+i+"]");
                System.err.println("No file name specified in args["+i+"]");
                System.exit(1);
            }
        }log.info("Done with step 1\n");
        //here we implement step 2 instructions
        testClient();
        //here we implement step 3 and 4 instructions
        if(args.length > 0){
            fileName = args[2];
            readCSV(clients, fileName);
        }else {
            System.err.println("No file name specified in args[2]");
            System.exit(1);
        }
        //here we implement steps 5 and 6 instructions
        if(args.length > 0){
            fileName = args[3];
            pullClient(clients, fileName);
            readDatabase(clients, user, host, password, fileName);
        }else {
            System.err.println("No file name specified in args[3]");
            System.exit(1);
        }
        //here we implement step 7 and 9 instructions
        if(args.length > 0){
            fileName = args[1];
            GenerateClientJson(clients, fileName, args[3]);
        }else {
            System.err.println("No file name specified in args[2]");
            System.exit(1);
        }

        log.info("Done!");
    }//end of main()

    public static void testClient(){
        log.trace("Moving on to step 2!\n");
        Client client = new Client(); //fill with dummy data to test object functionality

        client.setNumber(0);
        client.setF_name("?");
        client.setL_name("?");
        client.setCompany("?");
        client.setAddress("?");
        client.setCity("?");
        client.setCounty("?");
        client.setState("?");
        client.setPostal_code(0);
        client.setRisk_factor(0.0);
        client.setPhone1("?");
        client.setPhone2("?");
        client.setEmail("@");
        client.setWeb("http://");

        log.info("Step 2: " + client);
        log.info("Done with step 2\n");
    }//end of testClient()

    public static void readCSV(List<Client> record, String filename) {

        try {
            // create a reader
            Reader reader = Files.newBufferedReader(Paths.get(filename));
            // create csv reader
            CSVReader csvReader = new CSVReader(reader);
            //creates mapping strategy based off Client class without annotations
            ColumnPositionMappingStrategy<Client> strat = new ColumnPositionMappingStrategyBuilder<Client>().build();
            //sets the class type being mapped.
            //throws CsvBadConverterException to avoid problems during bean creation
            strat.setType(Client.class);

            //these are the fields we bind to in our bean
            //they are used for our mapping strategy builder specified previously
            String[] columns = new String[] {"number", "first_name", "last_name", "company", "address", "city",
                    "county", "state", "postal_code", "risk_factor", "phone1", "phon2", "email", "web"};

            //set the mapping strategy based off the column headers specified in string[] columns
            strat.setColumnMapping(columns);
            //implement CsvBeanBuilder of type Client and parse the objects in record list
            record = new CsvToBeanBuilder<Client>(csvReader).withType(Client.class).build().parse();

            log.trace("Moving on to step 3!\n");
            log.info("Step 3: Printed Records: " + record.size()); //print # of records in Client List
            log.info("Step 3: " + record.get(0).toString()); //print first record in Client List
            log.trace("Done with step 3\n");

            log.trace("Moving on to step 4!\n");
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); //create Gson builder with pretty printing
            String clientlist = gson.toJson(record); //convert Client objects to Json
            dump("clients.json", clientlist);//dump client csv file to clients.json file
            log.info("Step 4: Client list dumped. Created Json file: 'clients.json'");
            log.trace("Done with step 4\n");

            // close readers
            csvReader.close();
            reader.close();
        }catch (IOException e) {
            log.error("Could not read file " + filename + ": " + e.getMessage());
            e.printStackTrace();
        }
    }//end of readCSV()

    public static void dump(String filename, String json) {
        try{
            FileWriter writer = new FileWriter(filename); //creates and names the file to be written
            writer.write(json); //writes to a json object
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error("Could not create file " + filename + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void pullClient(List<Client> record, String filename) {
        String url = "https://api.zippopotam.us/us/"; //initializes url string for reader

        try{
            log.trace("Moving on to step 5!\n");
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("clients.json"));//reads from clients.json file
            record = gson.fromJson(reader, new TypeToken<List<Client>>() {}.getType());//set typetoken to class client

            for(Client clients : record){
                if(clients.getNumber() == Integer.parseInt(filename)){
                    log.info(clients.toString());
                    log.trace("Done with step 5\n");

                    log.trace("Moving on to step 6!");
                    log.info("Successfully connected to URL...\n");
                    int postal_code = clients.getPostal_code(); //initialize and get client postal_code
                    String zip = String.format("%05d", postal_code); //formats postal_code with leading 0's if needed
                    String address = url + zip; //combine url and client postal_code
                    java.net.URL clientUrl = new java.net.URL(address);
                    BufferedReader bread = new BufferedReader(new InputStreamReader(clientUrl.openStream()));
                    JsonParser parser = new JsonParser(); //create Gson Json Parser object
                    String data = ""; //initialize a string to hold the json data that will be read
                    String line;
                    while((line = bread.readLine()) != null) { //read line from url as a string
                        data += line.trim(); //equate line to string data and trim
                    }
                    //creates a Json element and parses the data in the string read from url
                    JsonElement jsonElement = parser.parse(data); //parse() is deprecated but still works
                    JsonObject client = jsonElement.getAsJsonObject(); //creates a Json object for the new info

                    String postal = client.get("post code").getAsString();//get and read zip code as a string
                    log.info("Step 6: Postal Code = " + postal); //log the postal code

                    // pull json objects from places array
                    JsonArray places = client.getAsJsonArray("places");
                    for(JsonElement element : places){ //forEach element in places array pull long/lat as a double
                        JsonObject place = element.getAsJsonObject();
                        //set longitude for client json file
                        ProduceReport.setLongitude(place.get("longitude").getAsDouble());
                        //set latitude for client json file
                        ProduceReport.setLatitude(place.get("latitude").getAsDouble());

                        log.info("Step 6: Longitude = " + ProduceReport.getLongitude());
                        log.info("Step 6: Latitude = " + ProduceReport.getLatitude());

                    }bread.close(); //close the buffered read input stream

                    log.trace("Done with step 6\n");
                }
            }reader.close(); //close the reader

        } catch (Exception e) {
            log.error("Could not create file " + filename + ": " + e.getMessage());
            log.error("Could not connect to URL " + e.getMessage());
            e.printStackTrace();
        }
    }//end pullClient()

    public static void readDatabase(List<Client> record, String user, String host, String password, String filename){

        String connString = String.format("jdbc:mariadb://%s:3306/misc", host);//initializes database url to a string

        try{
            Connection conn = DriverManager.getConnection(connString, user, password); //establish connection to the DB
            //prepared query statement
            String query = "SELECT medianage, total_population FROM usCityDemographics WHERE city=? AND state_abbr=?";
            PreparedStatement statement = conn.prepareStatement(query); //creates prepared statement

            Gson gson = new Gson(); //implement new gson object
            Reader reader = Files.newBufferedReader(Paths.get("clients.json"));//read client.json file
            record = gson.fromJson(reader, new TypeToken<List<Client>>() {}.getType());//set typetoken to class client

            log.trace("Moving on to step 7!\n");
            log.info("In readDatabase(). Reading records from the database...");
            for(Client client : record){
                //inserts parameters into prepared statement
                statement.setString(1, client.getCity());
                statement.setString(2, client.getState());
                ResultSet rs = statement.executeQuery(); //initializes result set and executes the query string

                if(client.getNumber() == Integer.parseInt(filename)) {
                    //determine if query returned a result
                    boolean foundDemographic = rs.next();
                    if (foundDemographic) {
                        log.info("At least one demographic found");
                    } else {
                        log.info("No demographics found");
                    }//end of test on query
                    while (rs.next()) { //pull values from the database if found
                        double medage = rs.getDouble("medianage");
                        int population = rs.getInt("total_population");

                        ProduceReport.setMedianage(medage); //sets reports median age
                        ProduceReport.setPopulation(population); //sets reports population

                    }//end while loop
                    log.info("Step 7: Median Age = " + ProduceReport.getMedianage());
                    log.info("Step 7: Population Age = " + ProduceReport.getPopulation());
                }
            }conn.close();//close the connection

            log.trace("Done with step 7\n");
        } catch(SQLException | IOException e) {
            log.error("Error in readDatabase(): " + e.getMessage());
            System.exit(1);
            e.printStackTrace();
        }
    }//end readDatabase()

    public static void GenerateClientJson(List<Client> record, String filename, String num){

        try {
            log.trace("Skipping step 8, Moving on to step 9!\n");
            JsonParser parser = new JsonParser();//create Gson.JsonParser object
            Gson gson = new GsonBuilder().setPrettyPrinting().create(); //create Gson builder with pretty printing
            Reader reader = Files.newBufferedReader(Paths.get("clients.json"));
            record = gson.fromJson(reader, new TypeToken<List<Client>>() {}.getType());

            for(Client client : record) {
                if(client.getNumber() == Integer.parseInt(num)) {

                    ProduceReport clientData = new ProduceReport(filename, client); //creates report object
                    String data = clientData.toString(); //initializes report objects toString to a string()
                    //creates a Json element and parses the data in the string data
                    JsonElement jsonElement = parser.parse(data);
                    //gets elements from the jsonElement and converts them to json objects
                    JsonObject clientRecord = jsonElement.getAsJsonObject();
                    clientData.createJson(clientRecord); //creates the client .json file
                    String jsonFile = gson.toJson(clientRecord); //print to the console for visual log of .json file
                    log.info("Json file \"" + filename + "\" created!");
                    log.info("Below is what the Json file looks like!");
                    log.info("\n" + jsonFile);
                    log.trace("Done with step 9\n");
                }
            }
        } catch (Exception e) {
            log.error("Could not create file " + filename + ": " + e.getMessage());
            e.printStackTrace();
        }

    }//end GenerateClientJson()
}//end class App
