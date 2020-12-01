import com.mainproject.commons.GlobalVaribales;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.mainproject.commons.GlobalVaribales;


public class CreateTables {
    static String datalog_path = GlobalVaribales.datalog_path;

    static String jdbc_driver = GlobalVaribales.jdbc_driver;
    static String db_url = GlobalVaribales.db_url;
    static String username = GlobalVaribales.username;
    static String password = GlobalVaribales.password;

    static String[][] concepts = GlobalVaribales.concepts;
    static boolean[] used = GlobalVaribales.used;
    static boolean[] has_role  = GlobalVaribales.has_role;


    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        BufferedReader br = new BufferedReader(new FileReader(datalog_path));
        ArrayList<DatalogRule> allRules = new ArrayList<DatalogRule>();

        String line = br.readLine();
        // a loop to parse the rules
        while(line != null){
            if(line.charAt(0) == '%')
                break;
            if(!includesBinaryClause(line)){
                allRules.add(new Conjunction(line));
            } else if (!roleInclusion(line)){
                allRules.add(new Existential(line));
            } else  if (roleInclusion(line)){
                allRules.add(new RuleInclusion(line));
            }
            line = br.readLine();
        }

        String answer_rule_line = br.readLine();
        DatalogRule answer_rule;

        if(!includesBinaryClause(answer_rule_line))
            answer_rule = new Conjunction(answer_rule_line);
        else if(!roleInclusion(answer_rule_line))
            answer_rule = new Existential(answer_rule_line);
        else{
            System.err.println("answer rule as a role inclusion !!");
        }

        ArrayList<String> created_tables = new ArrayList<String>();

        //these tables are already defined
        String[] given_tables = {"drone", "inexperienced", "dirtylenses", "goingbackwards", "indoor", "waterproofdrone", "ground", "water", "low", "high",
                "fast", "emptybattery", "lowbattery", "pilotedby", "environment", "flyingabove", "hasaltitude", "hasspeed", "haspart", "notwaterproofdrone", "experienced"};

        created_tables.addAll(Arrays.asList(given_tables));


        // map all rules to their heads
        Map<String, ArrayList<DatalogRule>> headToRules = new HashMap<String, ArrayList<DatalogRule>>();


        for(int i = 0; i < allRules.size(); i++){
            DatalogRule rule = allRules.get(i);
            ArrayList<DatalogRule> rulesOfHead = headToRules.get(rule.head);

            if(rulesOfHead == null) {
                rulesOfHead = new ArrayList<DatalogRule>();
                headToRules.put(rule.head, rulesOfHead);
            }

            rulesOfHead.add(rule);
        }

        Connection c = null;
        Statement stmt = null;
        Class.forName(jdbc_driver);
        c = DriverManager
                .getConnection(db_url,
                        username, password);
        stmt = c.createStatement();
        stmt.executeUpdate("SET search_path TO public, provsql");
        c.setAutoCommit(true);

        boolean change = false;
        Iterator<Map.Entry<String, ArrayList<DatalogRule>>> it = headToRules.entrySet().iterator();

        change = false;
        it = headToRules.entrySet().iterator();

        System.out.println();

        //create tables in proper order
        while (headToRules.size() > 0){
            if(!it.hasNext()) {
                if (change) {
                    it = headToRules.entrySet().iterator();
                    change = false;
                } else {
                    break;
                }
            }
            Map.Entry<String, ArrayList<DatalogRule>> pair = (Map.Entry<String, ArrayList<DatalogRule>>) it.next();
            String head = pair.getKey();

            //skip if the table already exists
            if(created_tables.contains(pair.getKey())) {
                System.out.println(pair.getKey() + ":" + pair.getValue());
                continue;
            }

            ArrayList<DatalogRule> datalogRules = pair.getValue();

            //a boolean to indicate whether the table should be created now
            //because the table may depend on another that should be created first
            boolean create_table = true;
            for (int i = 0; i < datalogRules.size() && create_table; i++) {
                DatalogRule rule = datalogRules.get(i);
                String[] body = datalogRules.get(i).body;

                for(int j = 0; j < body.length && create_table; j++)
                    //if the table depends on another that's not created yet then don't create the table now
                    if(headToRules.keySet().contains(body[j]) && !created_tables.contains(body[j]))
                        create_table = false;
            }

            if(!create_table)
                continue;

            String selectQuery = "";
            for (int i = 0; i < datalogRules.size(); i++) {
                DatalogRule rule = datalogRules.get(i);
                String[] body = datalogRules.get(i).body;
                boolean addRule = true;

                for(int j = 0; j < body.length && addRule; j++)
                    if(!created_tables.contains(body[j]))
                        addRule = false;

                if(addRule){
                    if(selectQuery.length() != 0)
                        selectQuery += "\nUnion\n";
                    selectQuery += rule.selectQuery();
                }
            }
            if(selectQuery.length() != 0){
                stmt = c.createStatement();
                String createTableQuery = "create table " + head + " as (" + selectQuery + ")";
                System.out.println(createTableQuery);
                stmt.execute(createTableQuery);
                change = true;
                created_tables.add(head);
            }
            it.remove();
        }

        stmt.close();
        c.close();
    }

    public static boolean includesBinaryClause(String line){
        return line.contains("(X,Y)") || line.contains("(Y,X)");
    }

    public static boolean roleInclusion(String rule){
        if(!includesBinaryClause(rule))
            return false;
        String[] splittedRule = rule.split(" :- ");
        return splittedRule[0].contains("(X,Y)") || splittedRule[0].contains("(Y,X)");
    }
}
