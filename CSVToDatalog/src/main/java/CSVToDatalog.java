import com.mainproject.commons.GlobalVaribales;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVToDatalog {
    static String updated_status_path = GlobalVaribales.status_updated_path;
    static String abox_path = GlobalVaribales.abox_datalog_path;

    public static void main(String[] args) throws IOException {
        List<List<String>> status = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(updated_status_path));
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            status.add(Arrays.asList(values));
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(abox_path));
        List<String> columns_names = status.get(0);

        String[][] concepts = GlobalVaribales.concepts;
        boolean[] has_role = GlobalVaribales.has_role;
        String[] roles = GlobalVaribales.roles;
        boolean[] bool = GlobalVaribales.bool;
        boolean[] used =   GlobalVaribales.used;

        for(int j = 1; j < status.size(); j++){
            List<String> row = status.get(j);
            String drone_id = row.get(1);
            for (int k = 0; k < row.size(); k++){
                if(used[k]){
                    if(has_role[k]){
                        String role = roles[k];
                        if(bool[k]){
                            if(row.get(k).equals("t")){
                                String concept = concepts[k][0];
                                String successor = drone_id + "_" + concept;
                                String assertion1 = concept + "(\"";
                                assertion1 += successor;
                                assertion1 += "\").";
                                bw.write(assertion1);
                                bw.newLine();
                                System.out.println(assertion1);
                                String assertion2 = roles[k] + "(\"";
                                assertion2 += drone_id + "\", \"" + successor + "\").";
                                bw.write(assertion2);
                                bw.newLine();
                                System.out.println(assertion2);
                                System.out.println();
                            }
                        }else{
                            String concept = row.get(k);
                            String successor = drone_id + "_" + concept;
                            String assertion1 = concept + "(\"" + successor + "\").";
                            bw.write(assertion1);
                            bw.newLine();
                            System.out.println(assertion1);
                            String assertion2 = roles[k] + "(\"";
                            assertion2 += drone_id + "\", \"" + successor + "\").";
                            bw.write(assertion2);
                            bw.newLine();
                            System.out.println(assertion2);
                            System.out.println();
                        }
                    }else{
                        if(bool[k]){
                            if(row.get(k).equals("t")){
                                String assertion1 = concepts[k][0] + "(\"";
                                assertion1 += drone_id;
                                assertion1 += "\").";
                                System.out.println(assertion1);
                                System.out.println();
                                bw.write(assertion1);
                                bw.newLine();
                            }
                        } else {
                            if(concepts[k][0].equals("drone")){
                                String assertion1 = concepts[k][0] + "(\"";
                                assertion1 += drone_id;
                                assertion1 += "\").";
                                System.out.println(assertion1);
                                System.out.println();
                                bw.write(assertion1);
                                bw.newLine();
                            }
                        }
                    }
                }
            }
            System.out.println("=====================================================================");
        }

        bw.close();
    }
}
