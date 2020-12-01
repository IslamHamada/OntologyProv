import com.mainproject.commons.GlobalVaribales;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadingCSV {

    static String jdbc_driver = GlobalVaribales.jdbc_driver;
    static String db_url = GlobalVaribales.db_url;
    static String username = GlobalVaribales.username;
    static String password = GlobalVaribales.password;

    static String status_path = GlobalVaribales.status_path;
    static int num_of_used_columns_of_status = GlobalVaribales.num_of_used_columns_of_status;
    static int num_of_used_rows_of_status = GlobalVaribales.num_of_used_rows_of_status;
    static String object_path = GlobalVaribales.object_path;

    public static void main(String args[]) throws IOException, SQLException, ClassNotFoundException {

        System.out.println("change the username and password accordingly");
        // read status.csv into the database
        status_to_database();
        // read object.csv into the database
//        object_to_database();
    }

    private static void status_to_database() throws ClassNotFoundException, SQLException, IOException {
        Connection c = null;
        Statement stmt = null;

        //add the postgres jar file to the project
        Class.forName(jdbc_driver);
        //connect to the postgres database.
        c = DriverManager
                .getConnection(db_url, username, password);

        // use setAutoCommit to true, if you want the database to be actually modified, false otherwise
        c.setAutoCommit(true);

        //read status.csv into a list
        List<List<String>> status = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(status_path))) {
            String line;
            int i = 0;

            //change the number of lines to be read as required
            int lines = num_of_used_rows_of_status;
            while ((line = br.readLine()) != null && i != lines + 1) {
                String[] values = line.split(",");
                status.add(Arrays.asList(values));
                i++;
            }
        }

        //create needed column types
        String weather_enum = "CREATE TYPE weather AS ENUM('sunny', 'rainy', 'gloomy', 'snowy', 'foggy', 'dark', 'unindentified') ";
        String frame_enum = "CREATE TYPE frame AS ENUM('normal frame', 'FrontRightPropeller damaged', 'FrontLeftPropeller damaged', 'RearRightPropeller damaged', 'RearLeftPropeller damaged', 'Multiple Propellers damaged') ";
        String ground_water_enum = "CREATE TYPE ground_water AS ENUM('ground', 'water') ";
        stmt = c.createStatement();
        stmt.executeUpdate(weather_enum);
        stmt.executeUpdate(frame_enum);
        stmt.executeUpdate(ground_water_enum);

        //define the original columns
        String create_status_query = "create table status (";
        List<String> columns_names = status.get(0);

        String[] columns_types = {"VARCHAR(255)", "VARCHAR(255)", "Integer", "Integer", "BOOL", "FLOAT", "Integer", "FLOAT", "Integer", "BOOL", "frame",
                "weather", "BOOL", "BOOL", "BOOL", "BOOL", "BOOL", "ground_water"};

        //define the computed columns
        String[] computed_columns_names = {"flying_altitude", "low_altitude", "high_altitude",
                "fast_speed", "EmptyBattery", "LowBattery", "NotWaterProofDrone", "inexperienced"};
        String[] computed_columns_types = {"BOOL", "BOOL", "BOOL",
                "BOOL", "BOOL", "BOOL", "BOOL", "BOOL"};
        String[] computed_expressions = {"Altitude > 0" ,"Altitude < .5", "Altitude > 70",
                "DroneSpeed >= 13", "Battery_level < 20", "Battery_level < 45", "NOT waterproof_drone", "NOT PilotExperienced"};

        int numOfOriginalColumns = num_of_used_columns_of_status;

//        System.out.println(columns_names);
        // add the original columns to the table
        for(int i = 0; i < numOfOriginalColumns; i++){
            String[] splitted_column_name = columns_names.get(i).split(" ");
            String column_name = splitted_column_name[0];
            if(splitted_column_name.length == 2 && !splitted_column_name[1].startsWith("("))
                column_name += "_" + splitted_column_name[1];

//            System.out.println(column_name);
            column_name = column_name.replace("?","");
            create_status_query += column_name + " ";
            create_status_query += columns_types[i];
            if(i != columns_names.size() - 1 || computed_columns_names.length != 0)
                create_status_query += ",";
        }

        // adding the computed columns to the table
        for(int i = 0; i < computed_columns_names.length; i++){
            create_status_query += computed_columns_names[i];
            create_status_query += " ";
            create_status_query += computed_columns_types[i];
            create_status_query += " GENERATED ALWAYS AS ";
            create_status_query += "(" + computed_expressions[i] + ")";
            create_status_query += " stored";
            if(i != computed_columns_names.length - 1){
                create_status_query += ",";
            }
        }

        create_status_query += ")";

        stmt = c.createStatement();

        //execute the query
//        System.out.println(create_status_query);
        stmt.executeUpdate(create_status_query);


        //insert the values from the CSV into the table
        for(int i = 1; i < status.size(); i++){
            List<String> row = status.get(i);
            String insert_values = "INSERT INTO status values (";
            for(int j = 0; j < numOfOriginalColumns; j++){
                insert_values += "'"+row.get(j)+"'";
                if(j != numOfOriginalColumns - 1)
                    insert_values +=",";
            }
            insert_values += ")";
//            System.out.println(insert_values);
            stmt.executeUpdate(insert_values);
        }

        String[][] concepts = GlobalVaribales.concepts;
        boolean[] has_role = GlobalVaribales.has_role;
        String[] roles = GlobalVaribales.roles;
        boolean[] bool = GlobalVaribales.bool;
//        boolean[] flipped = GlobalVaribales.flipped;
        boolean[] used = GlobalVaribales.used;


        ArrayList<String> created_tables = new ArrayList<String>();

        // create the required tables for the concept names and role names
        for(int i = 0; i < has_role.length; i++){
            if(used[i]){
                if(has_role[i]){
                    String role = roles[i];
                    if(!created_tables.contains(role)){
                        String create_role_table_query = "create table " + role + " (v1 varchar(255), v2 varchar(255)) ";
                        stmt = c.createStatement();
                        stmt.executeUpdate(create_role_table_query);
                        created_tables.add(role);
                    }
                    for(int j = 0; j < concepts[i].length; j++) {
                        String concept = concepts[i][j];
                        if (!created_tables.contains(concept)) {
                            String create_concept_table_query = "create table " + concept + " (v1 varchar(255)) ";
                            stmt = c.createStatement();
                            stmt.executeUpdate(create_concept_table_query);
                            created_tables.add(concept);
                        }
                    }
                } else {
                    String concept = concepts[i][0];
                    String create_concept_table_query = "create table " + concept + " (v1 varchar(255)) ";
                    stmt = c.createStatement();
                    stmt.executeUpdate(create_concept_table_query);
                }
            }
        }

        String all_rows_query = "select * from status";
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(all_rows_query);
        ResultSetMetaData rsMetaData = rs.getMetaData();

        // populate the created tables for concept name and role names accordingly
        while(rs.next()){
            String drone = rs.getString("Name");
            for(int i = 0; i < rsMetaData.getColumnCount(); i++){
                if(used[i]){
                    if(has_role[i]){
                        String role = roles[i];
                        if(bool[i]){
                            boolean value = rs.getBoolean(i + 1);
//                            System.out.println(drone + " : " + value);
                            if(value){
                                String concept = concepts[i][0];
                                String successor = drone + "_" + concept;
                                String insert_into_role_table = "insert into " + role + " values ('" + drone + "', '" + successor + "')";
                                stmt = c.createStatement();
                                stmt.executeUpdate(insert_into_role_table);
                                String insert_into_concept_table = "insert into " + concept + " values ('" + successor + "')";
                                stmt = c.createStatement();
                                stmt.executeUpdate(insert_into_concept_table);
                            }
                        }
                        else{
                            String concept = rs.getString(i+1);
                            String successor = drone + "_" + concept;
                            String insert_into_role_table = "insert into " + role + " values ('" + drone + "', '" + successor + "')";
                            stmt = c.createStatement();
                            stmt.executeUpdate(insert_into_role_table);
                            String insert_into_concept_table = "insert into " + concept + " values ('" + successor + "')";
                            stmt = c.createStatement();
                            stmt.executeUpdate(insert_into_concept_table);
                        }
                    } else{
                        if(bool[i]){
                            boolean value = rs.getBoolean(i + 1);
//                            System.out.println(drone + " : " + value);
                            if(value){
                                String concept = concepts[i][0];
                                String insert_into_concept_table = "insert into " + concept + " values ('" + drone + "')";
                                stmt = c.createStatement();
                                stmt.executeUpdate(insert_into_concept_table);
                            }
                        } else {
                            String insert_into_drone_table = "insert into drone values ('" + drone + "')";
                            stmt = c.createStatement();
                            stmt.executeUpdate(insert_into_drone_table);
                        }
                    }
                }
            }
        }

        stmt.close();
        c.close();
    }

    // the function might yet need to be modified
    private static void object_to_database() throws IOException, SQLException, ClassNotFoundException {

        Connection c = null;
        Statement stmt = null;

        //add the postgres jar file to the project
        Class.forName(jdbc_driver);
        //connect to the postgres database.
        c = DriverManager
                .getConnection(db_url,
                        username, password);

        // use setAutoCommit to true, if you want the database to be actually modified, false otherwise
        c.setAutoCommit(false);

        //read object.csv into a list
        List<List<String>> object = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(object_path))) {
            String line;
            int i = 0;

            //change the number of lines to be read as required
            int lines = 219;
            while ((line = br.readLine()) != null && i != lines+1) {
                String[] values = line.split(",");
                object.add(Arrays.asList(values));
                i++;
            }
        }

        List<String> columns_names = object.get(0);
        String[] columns_types = {"Integer","object_type","BOOL","BOOL","FLOAT","TIME","BOOL"};

        // create the column type object
        String object_type_enum = "CREATE TYPE object_type AS ENUM('Wall', 'Human', 'Animal', 'Tree', 'Statue', 'Post', 'Bridge', 'Forest', 'House', 'Branch_rope', 'Unindentified') ";
        stmt = c.createStatement();
        stmt.executeUpdate(object_type_enum);

        String table_name = "";
        boolean new_table = false;

        //create a table for each clip, which contains the objects detected by drone in each clip
        for(int i = 1; i < object.size(); i++){
            if(new_table) {
                if (object.get(i).size() == 0 || (object.get(i).get(2).equals("") && object.get(i).get(3).equals("")))
                    break;
            }

            new_table = object.get(i).size() == 0 || (object.get(i).get(2).equals("") && object.get(i).get(3).equals(""));
            if(new_table)
                continue;

            List<String> row = object.get(i);
            String clip_name = row.get(1);

            if(!clip_name.equals(""))
                clip_name = clip_name.split("\\.")[0];

            if(!clip_name.equals("") && !table_name.equals(clip_name)){
                table_name = clip_name;
                String create_table = "create table " + table_name + " (";
                for(int j = 0; j < columns_types.length; j++){
                    create_table += columns_names.get(j + 2);
                    create_table += " ";
                    create_table += columns_types[j];
                    if(j != columns_types.length - 1)
                        create_table += ",";
                }
                create_table += ")";
                stmt.executeUpdate(create_table);
            }
            String insert_values = "INSERT INTO ";
            insert_values += table_name;
            insert_values += " values (";

            String time = row.get(7);
            // fix the time format
            if(time.length() == 3){
                time = "00:00" + time;
            }
            row.set(7, time);

            String type = row.get(3);
            if(type.equals("Branch (Rope)"))
                type = "Branch_rope";
            row.set(3, type);

            for(int j = 2; j < row.size(); j++){
                insert_values += "'" + row.get(j) + "'";
                if(j != row.size()-1)
                    insert_values += ",";
            }
            insert_values += ")";

            stmt.executeUpdate(insert_values);
        }
        stmt.close();
        c.close();
    }
}
