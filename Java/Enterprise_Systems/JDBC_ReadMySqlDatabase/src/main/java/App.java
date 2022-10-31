import com.noynaert.sqlCredentials.SqlCredentials;

import java.io.File;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

/**
 * todo: Implements JDBC Statement and PreparedStatement to read records from a Database and normalize the data
 *
 * @author: Tristan Davis
 * @since: February 2022
 */

public class App {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting MySql Read . . .");

        PrintWriter outp;//implements PrintWriter class
        //equates the name "outp" to an external file through the PrintWriter class
        outp = new PrintWriter(new File("UsCityDemographics.txt"));

        SqlCredentials credentials = new SqlCredentials("woz.xml"); //initializes the credentials xml file

        /* implements use of credentials dependency to extract user, host, and password for the database */
        String host = credentials.getHost();
        String user = credentials.getUser();
        String password = credentials.getPassword();

        //initializes list of city objects read from the database
        ArrayList<city> cityInfo = new ArrayList<>(600);

        //executes a statement query to pull city and state from database
        getCityInfo(cityInfo, user, host, password);
        //prints the column headers to the output txt file
        outp.println("city\tstate\tmedianage\tmales\tfemales\ttotal_population\tveterans\tforeign_born\tave_hh_size" +
                        "\tstate_abbr\tAmericanIndianandAlaskaNative\tBlackorAfricanAmerican\tHispanicorLatino\t" +
                "Asian\tWhite\n");
        //reads and stores values from the columns specified in the database
        showCityInfo(cityInfo, user, host, password);
        //forEach method to print each city and its info stored in the ArrayList objects
        for (city cities : cityInfo) {
            outp.println(cities);
        }

        System.out.println("\nDone!");
    }//end of main

    public static void getCityInfo(ArrayList<city> cityInfo, String user, String host, String password) {
        //specifies the database address
        String connectionString = String.format("jdbc:mariadb://%s:3306/misc", host);

        try {
            //establishes the connection to the database
            Connection connection = DriverManager.getConnection(connectionString, user, password);
            String query = "SELECT DISTINCT city, state FROM usCityDemographics"; //Statement query

            Statement statement = connection.createStatement(); //creates the Statement
            ResultSet rs = statement.executeQuery(query); //creates result set by executing the query Statement

            //while loop to retrieve and add result set values to ArrayList cityInfo
            while (rs.next()) {
                city cities = new city(rs.getString(1), rs.getString(2));
                cityInfo.add(cities);
            }//end while loop
            connection.close(); //close the connection

        } catch (SQLException e) { //throws SQLException is query cannot be run
            System.err.println("ERROR in getCityInfo(): " + e.getMessage());
            System.exit(1);
        }
    }//end of getCityInfo()

    public static void showCityInfo(ArrayList<city> cityInfo, String user, String host, String password) {

        String connectString = String.format("jdbc:mariadb://%s:3306/misc", host);

        try {
            Connection connection = DriverManager.getConnection(connectString, user, password);
            //PreparedStatement query
            String query = "SELECT * FROM usCityDemographics WHERE city LIKE ? AND state LIKE ? ORDER BY city";
            PreparedStatement statement = connection.prepareStatement(query); //creates the PreparedStatement
            System.out.println("In showCityInfo(). Reading records from the DB . . .");

            /*forEach() loop to execute a prepared statement to query the database
             and return the result set values for each record*/
            for (city record : cityInfo) {
                //inserts parameters into the PreparedStatement
                statement.setString(1, record.getCity());
                statement.setString(2, record.getState());
                ResultSet rs = statement.executeQuery();//creates result set by executing the query PreparedStatement

                //while loop to retrieve and set the result set values for each record in ArrayList cityInfo
                while (rs.next()) {
                    //initializes ResultSet values pulled from the database columns
                    float medage = rs.getFloat("medianage");
                    int males = rs.getInt("males");
                    int females = rs.getInt("females");
                    int tpop = rs.getInt("total_population");
                    int vets = rs.getInt("veterans");
                    int foreign = rs.getInt("foreign_born");
                    float avhhsize = rs.getFloat("ave_hh_size");
                    String stabbrev = rs.getString("state_abbr");

                    //sets the record info to the result set values pulled from the database
                    //sets all except race values
                    record.setMedianage(medage);
                    record.setMales(males);
                    record.setFemales(females);
                    record.setPopulation(tpop);
                    record.setVeterans(vets);
                    record.setForeign(foreign);
                    record.setAvhhsize(avhhsize);
                    record.setStateabbr(stabbrev);

                    //implements enhanced switch statement to avoid default fallthrough
                    //switches on type of race and sets the value to the count stored for each
                    switch (rs.getString("race")) {
                        case "American Indian and Alaska Native" ->
                                record.setAmericanIndian_AlaskaNative(rs.getInt("count"));
                        case "Black or African-American" ->
                                record.setBlack_AfricanAmerican(rs.getInt("count"));
                        case "Hispanic or Latino" ->
                                record.setHispanic_Latino(rs.getInt("count"));
                        case "Asian" ->
                                record.setAsian(rs.getInt("count"));
                        case "White" ->
                                record.setWhite(rs.getInt("count"));
                    }//end of enhanced switch
                }//end while loop
                System.out.println(record); //print each record
            }//end forEach() loop
            System.out.println("Records read successfully!!");
            connection.close(); //close the connection
            System.out.println("The connection is closed. Done with showCityInfo()");

        } catch (SQLException e) { //throws SQLException is query cannot be run
            System.err.println("Error in showCityInfo(): " + e.getMessage());
            System.exit(1);
        }
    }//end showCityInfo()
}//end of class App

