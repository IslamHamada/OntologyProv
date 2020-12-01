import com.mainproject.commons.GlobalVaribales;

import java.sql.*;

public class CreateFinalTable {
    static String jdbc_driver = GlobalVaribales.jdbc_driver;
    static String db_url = GlobalVaribales.db_url;
    static String username = GlobalVaribales.username;
    static String password = GlobalVaribales.password;
    static String[] ontology_added_concepts = GlobalVaribales.ontology_added_concepts;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        if(ontology_added_concepts.length == 0)
            return;

        Connection c = null;
        Statement stmt = null;

        //add the postgres jar file to the project
        Class.forName(jdbc_driver);
        //connect to the postgres database.
        c = DriverManager
                .getConnection(db_url, username, password);

        // use setAutoCommit to true, if you want the database to be actually modified, false otherwise
        c.setAutoCommit(true);

        DatabaseMetaData meta = c.getMetaData();

        boolean tables_exist[] = new boolean[ontology_added_concepts.length];
        for(int i = 0; i < ontology_added_concepts.length; i++){
            String concept = ontology_added_concepts[i];
            ResultSet res = meta.getTables(null, null, concept, null);
            if(res.next())
                tables_exist[i] = true;
        }

        stmt = c.createStatement();

        String table_name = "status_final";
        String createTable = "create table " + table_name +" as ( select * from status)";
        System.out.println(createTable);
        stmt.executeUpdate(createTable);

        String addColumns = "alter table " + table_name + "\n";
        for(int i = 0; i < ontology_added_concepts.length; i++){
            if(tables_exist[i]){
                String concept = ontology_added_concepts[i];
                addColumns += "add column " + concept + " boolean default false";
                if(i != ontology_added_concepts.length - 1)
                    addColumns += ",\n";
                else
                    addColumns += ";";
            }
        }
        System.out.println(addColumns);
        stmt = c.createStatement();
        stmt.executeUpdate(addColumns);

        for(int i = 0; i < ontology_added_concepts.length; i++){
            if(tables_exist[i]){
                String concept = ontology_added_concepts[i];
                String update_column = "update " + table_name + " set " + concept + " = \n" +
                        "( case when exists (select * from " + concept + " c where v1 = "+ table_name +".name) then true else false end);";
                System.out.println(update_column);
                stmt = c.createStatement();
                stmt.executeUpdate(update_column);
            }
        }
    }
}
