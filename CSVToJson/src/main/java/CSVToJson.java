import java.io.*;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.csv.*;

import com.mainproject.commons.GlobalVaribales;

public class CSVToJson {
    public static String status_path = GlobalVaribales.status_path;
    public static String status_prepared_for_json_path = GlobalVaribales.status_prepared_for_json_path;
    public static String status_json_path = GlobalVaribales.status_json_path;

    static int num_of_used_columns_of_status = GlobalVaribales.num_of_used_columns_of_status;
    static int num_of_used_rows_of_status = GlobalVaribales.num_of_used_rows_of_status;

    public static String status_updated_path = GlobalVaribales.status_updated_path;
    public static String statusUpdated_prepared_for_json_path = GlobalVaribales.statusUpdated_prepared_for_json_path;
    public static String status_updated_json_path = GlobalVaribales.status_updated_json_path;

    public static String status_final_path = GlobalVaribales.status_final_path;
    public static String statusFinal_prepared_for_json_path = GlobalVaribales.statusFinal_prepared_for_json_path;
    public static String status_final_json_path = GlobalVaribales.status_final_json_path;

    public static void main(String[] args) throws IOException {
        status_to_json();
        updated_status_to_json();
        final_status_to_json();
    }

    public static void status_to_json() throws IOException {
        File status = new File(status_path);
        BufferedReader br = new BufferedReader(new FileReader(status));
        PrintWriter pw = new PrintWriter(status_prepared_for_json_path);
        String line = br.readLine();
        String[] columns_names = line.split(",");
        //print line
        for(int i = 0; i < num_of_used_columns_of_status; i++){
            String[] splitted_column_name = columns_names[i].split(" ");
            String column_name = splitted_column_name[0];
            if(splitted_column_name.length == 2 && !splitted_column_name[1].startsWith("("))
                column_name += "_" + splitted_column_name[1];
            column_name = column_name.replace("?","");
            columns_names[i] = column_name;
            pw.print(columns_names[i].toLowerCase());
            if(i != num_of_used_columns_of_status - 1)
                pw.print(",");
        }
        pw.println();
        line = br.readLine();
        int i = 0;
        //printing the needed columns of each row
        while(line != null && i != num_of_used_rows_of_status){
            pw.println(toCSVLine(line.split(","), num_of_used_columns_of_status));
            line =  br.readLine();
            i++;
        }
        pw.flush();
        pw.close();
        br.close();

        File status_prepared_for_json = new File(status_prepared_for_json_path);

        CsvSchema csv_schema = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<Status> mappingIterator = csvMapper.readerFor(Status.class).with(csv_schema).readValues(status_prepared_for_json);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(new File(status_json_path), mappingIterator.readAll());
    }

    public static void updated_status_to_json() throws IOException {
        File updated_status = new File(status_updated_path);
        BufferedReader br = new BufferedReader(new FileReader(updated_status));
        PrintWriter pw = new PrintWriter(statusUpdated_prepared_for_json_path);

        String line = br.readLine();
        while(line != null){
            String[] values = line.split(",");
            pw.println(toCSVLine(values));
            line = br.readLine();
        }

        pw.flush();
        pw.close();
        br.close();

        File statusUpdated_prepared_for_json = new File(statusUpdated_prepared_for_json_path);

        CsvSchema csv_schema = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<Status> mappingIterator = csvMapper.readerFor(StatusUpdated.class).with(csv_schema).readValues(statusUpdated_prepared_for_json);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(new File(status_updated_json_path), mappingIterator.readAll());
    }

    public static void final_status_to_json() throws IOException {
        File final_status = new File(status_final_path);
        BufferedReader br = new BufferedReader(new FileReader(final_status));
        PrintWriter pw = new PrintWriter(statusFinal_prepared_for_json_path);

        String line = br.readLine();
        while(line != null){
            String[] values = line.split(",");
            pw.println(toCSVLine(values));
            line = br.readLine();
        }

        pw.flush();
        pw.close();
        br.close();

        File statusFinal_prepared_for_json = new File(statusFinal_prepared_for_json_path);

        CsvSchema csv_schema = CsvSchema.emptySchema().withHeader();
        CsvMapper csvMapper = new CsvMapper();
        MappingIterator<Status> mappingIterator = csvMapper.readerFor(StatusFinal.class).with(csv_schema).readValues(statusFinal_prepared_for_json);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true).writeValue(new File(status_final_json_path), mappingIterator.readAll());
    }

    public static String toCSVLine(String[] array){
        return toCSVLine(array, array.length);
    }

    public static String toCSVLine(String[] array, int num_of_columns){
        String output = "";
        for(int i = 0; i < num_of_columns; i++){
            output += fixTrueAndFalseValues(array[i]);
            if(i != num_of_columns - 1)
                output += ",";
        }
        return output;
    }

    public static String fixTrueAndFalseValues(String s){
        if(s.equals("f"))
            return "false";
        else if (s.equals("t"))
            return "true";
        else
            return s;
    }
}
