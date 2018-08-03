import com.opencsv.*;
import org.apache.commons.csv.*;

import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderExample {
    public static void main(String[] args) {
        for(int i = 0;i<args.length;i++) {
            System.out.println("Arg["+i+"]:"+args[i]);
        }
        if(args.length >= 4) { // at least 3 args
            
            System.out.println("Reading "+args[0] + " cols:" +args[1] + " gold:" + args[2] + " name:" + args[3] );
            try {
                
                String fpath=args[0];
                int cols=Integer.parseInt( args[1] );
                int goldCol=Integer.parseInt( args[2] );
                int nameCol=Integer.parseInt( args[3] );
                
                // Apache Commons CSV lib
                // read with tab and ignore headers
                List list = readCSV( fpath, "\t", false );

                System.out.println("Read lines " + list.size());

                // Display the list contents to console output.
                for (int i = 0; i < list.size(); i++) {
                    
                    String[] row = (String[])list.get(i);
                    
                    if( row[goldCol].length() > 9000 ||  row[goldCol].length() <= 30 ) {
                        //System.out.println( ">>>>>>>>>>> ROW " + i + " ID " + row[0]  + "\tLEN " + row[goldCol].length() + "\tNAME " + row[nameCol] );
                    }
                    // checkpoint every 100 lines
                    if( i % 100 == 0 ) System.out.println( "ROW " + i + " ID " + row[0] + "\tLEN " + row[goldCol].length() + "\tNAME " + row[nameCol] );
                }

                List newlist = parseCSV( fpath, "\t", false );

            }
            catch (IOException ex) { System.out.println(ex.getMessage()); }
        } else {
            System.out.println("Usage: CSVFILEPATH COLS GOLD_COLUMN (TEXT)");
            System.exit(0);
        }
        
    }//main

    /**
     * Read CSV file with Double Buffering
     * @see Apache Commons - http://commons.apache.org/proper/commons-csv/apidocs/ 
     */
    public static List parseCSV(String csvFilePath, String sep, boolean useHeader) throws FileNotFoundException, IOException {
        
        BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
        org.apache.commons.csv.CSVFormat csvFormat = 
            org.apache.commons.csv.CSVFormat.RFC4180.withHeader().withDelimiter('\t');

        org.apache.commons.csv.CSVParser csvParser = 
            new org.apache.commons.csv.CSVParser(new BufferedReader(new FileReader(csvFilePath)), csvFormat);
        
        List<CSVRecord> list = csvParser.getRecords();
        
        System.out.println("---------- apache read "+ list.size() );

        List<CSVRecord> sublist=list.subList(0, list.size());

        for (CSVRecord csvRecord : sublist) {
            int i = csvRecord.size();
            String col="";
            for (int j = 1; j < i; j ++) {
                if( j <=2 ) col += "\t"+csvRecord.get(j);
            }
            System.out.println("---------- record ------" + col );
        }

        return list;

    }//parseCSV

    /**
     * Read CSV file with Double Buffering
     * @see Apache Commons - http://commons.apache.org/proper/commons-csv/apidocs/ 
     */
    public static List readCSV(String csvFilePath, String sep, boolean useHeader) throws FileNotFoundException, IOException {
        
        List list = new ArrayList<String[]>();
        BufferedReader br = new BufferedReader(new FileReader(csvFilePath));

        // Reading header. Ignoring the contents if false was supplied to ingnoreHeader.
        String line = br.readLine(); 
        String[] fieldTitles = {};
        if (useHeader) { fieldTitles = line.split(sep); }

        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                String[] fields = line.split(sep);
                String strg = "";
                for (int i = 0; i < fields.length; i++) {
                    if (useHeader) {
                        if (strg.equals("")) { strg = fieldTitles[i] + ": " + fields[i]; }
                        else { strg+= " | " + fieldTitles[i] + ": " + fields[i]; }
                    }
                    else {
                        if (strg.equals("")) { strg = "" + (i+1) + " " + fields[i]; }
                        else { strg+= " | " + (i+1) + " " + fields[i]; }
                    }
                }
                list.add(fields);
            }
        }
        br.close();
        
        return list;
    }//readCSV
}