import com.mainproject.commons.GlobalVaribales;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mainproject.commons.GlobalVaribales;


public class CreateProvenance {

    static String jdbc_driver = GlobalVaribales.jdbc_driver;
    static String db_url = GlobalVaribales.db_url;
    static String username = GlobalVaribales.username;
    static String password = GlobalVaribales.password;
    static String[][] concepts = GlobalVaribales.concepts;
    static boolean[] used = GlobalVaribales.used;
    static boolean[] has_role = GlobalVaribales.has_role;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection c = null;
        Statement stmt = null;

        Class.forName(jdbc_driver);
        c = DriverManager
                .getConnection(db_url,
                        username, password);
        c.setAutoCommit(true);

        stmt = c.createStatement();
        stmt.executeUpdate("SET search_path TO public, provsql");

        //tables that we will add provenance columns to
//        String[] add_provenance = {"goingbackwards", "indoor", "low", "high", "dirtylenses", "emptybattery", "fast", "ground",
//                "inexperienced", "lowbattery", "water", "waterproofdrone", "drone", "notwaterproofdrone"};

        //boolean table for which concepts are related to the drone with a role
//        boolean[] has_role = {false, true, true, true, false, true, true, true,
//                            true, true, true, false, true};

        // add provenance to tables, and create a mapping on the column, which contains the individuals names
        for(int i = 0; i < concepts.length; i++){
            if(used[i]){
                stmt = c.createStatement();
                String add_prov = "select add_provenance('" + concepts[i][0] + "')";
                System.out.println(add_prov);
                stmt.execute(add_prov);
                String add_prov_col = "select create_provenance_mapping('p" + i + "','" + concepts[i][0] + "','v1')";
                System.out.println(add_prov_col);
                stmt.execute(add_prov_col);
            }
        }


        //add the table name at the end to know which table the values come from
        String create_main_mapping_table = "create table p (value varchar(255), provenance uuid)";
        System.out.println(create_main_mapping_table);
        stmt = c.createStatement();
        stmt.executeUpdate(create_main_mapping_table);

        //add all provenance tokens to one table, so the semiring function can be called on all tables
        for(int i = 0; i < concepts.length; i++){
            if(used[i]){
                //add the table name at the end to know which table the values come from. Only needed for the the concepts that the drones belong to.
                if(!has_role[i]) {
                    String updateProvQuery = "update p" + i + " set value = concat(value, " + "'_" + concepts[i][0] + "')";
                    System.out.println(updateProvQuery);
                    stmt = c.createStatement();
                    stmt.executeUpdate(updateProvQuery);
                }
                String query = "Insert into p select * from p" + i;
                stmt = c.createStatement();
                stmt.execute(query);
            }
        }
    }
}
