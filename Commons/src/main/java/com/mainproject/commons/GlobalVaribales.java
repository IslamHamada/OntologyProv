package com.mainproject.commons;

import java.util.ArrayList;

//the file contains variables used across the project
public class GlobalVaribales {
    public static String status_path = "src/main/resources/status.csv";
    //prepared status for exporting as json
    public static String status_prepared_for_json_path = "src/main/resources/status_prepared_for_json.csv";
    public static String status_json_path = "src/main/resources/status.json";

    public static int num_of_used_columns_of_status = 18;
    public static int num_of_used_rows_of_status = 11;

    public static String object_path = "src/main/resources/object.csv";

    //the status table exported from the database after adding mapping columns
    public static String status_updated_path = "src/main/resources/status_updated.csv";
    //prepared status_updated for exporting as json
    public static String statusUpdated_prepared_for_json_path = "src/main/resources/statusUpdated_prepared_for_json.csv";
    public static String status_updated_json_path = "src/main/resources/status_updated.json";

    //the status table exported from the database after adding mapping columns and columns computed by the ontology
    public static String status_final_path = "src/main/resources/status_final.csv";
    //prepared final_status for exporting as json
    public static String statusFinal_prepared_for_json_path = "src/main/resources/statusFinal_prepared_for_json.csv";
    public static String status_final_json_path = "src/main/resources/status_final.json";

    public static String jdbc_driver = "org.postgresql.Driver";
    public static String db_url = "jdbc:postgresql://localhost:5432/postgres";
    public static String username = "postgres";
    public static String password = "a";

    public static String ontology_path = "src/main/resources/drone.owl";
    public static String datalog_path = "src/main/resources/drone.dl";

    public static String abox_datalog_path = "src/main/resources/ABox.dl";


    ////note that the arrays: concepts, has_role, roles, bool, used have to be of the same size and the values of the arrays at a specific index are related
    
    // the concepts names corresponding to the table columns. also they are the names of the tables that will be created
    //it's defined as 2D array because some columns in the table Status can cause multiple tables to be created such as flying_above because it can be flying above water or ground, etc.
    public static String[][] concepts = {{}, {"drone"}, {}, {}, {"experienced"}, {}, {}, {}, {}, {"dirtylenses"}, {}, {}, {}, {}, {"goingbackwards"}, {"indoor"}, {"waterproofdrone"}, {"ground", "water"}, {},
            {"low"}, {"high"}, {"fast"}, {"emptybattery"}, {"lowbattery"}, {"notwaterproofdrone"}, {"inexperienced"}};

    // a boolean array for whether the concept name of each column is related to the drone using a role name in the ontology or not
    public static boolean[] has_role = {false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, true, false, true, false, true, true, true, true, true, false, true};

    // the role name that connects the concept name to the drone if any is required
    public static String[] roles = {"", "", "", "", "pilotedby", "", "", "", "", "", "", "", "", "", "", "environment", "", "flyingabove", "",
            "hasaltitude", "hasaltitude", "hasspeed", "haspart", "haspart", "", "pilotedby"};

    // whether each column is of type boolean
    public static boolean[] bool = {false, false, false, false, true, false, false, false, false, true, false, false, false, false, true, true, true, false, false, true, true, true, true, true, true, true};

    // some columns aren't yet used in the ontology. So this array indicates whether the value of each column is utilized
    public static boolean[] used =   {false, true, false, false, true, false, false, false, false, true, false, false, false, false, true, true, true, true, false, true, true, true, true, true, true, true};

    //the concepts(columns) that can be added to the status table from the ontology
    public static String[] ontology_added_concepts = {"riskofphysicaldamage", "riskofhumandamage", "riskofinternaldamage", "lostconnection", "almostoutofrcrange", "flying"};
}
