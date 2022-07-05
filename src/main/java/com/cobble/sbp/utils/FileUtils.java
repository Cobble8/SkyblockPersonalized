package com.cobble.sbp.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class FileUtils {

    public static void saveFile(String text, String destinationFile) {
        try {
            //Utils.print("Saved file: '"+imageUrl+"' at: '"+destinationFile+"'");

            //
            File loc = new File(destinationFile);
            loc.getParentFile().mkdirs(); loc.createNewFile();
            FileWriter writer = new FileWriter(loc);

            writer.write(text);
            writer.flush();
            writer.close();




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean fileTest(String args) {
return new File(args).exists();
}

    public static String readFile(String filePath) {
        StringBuilder output = new StringBuilder();

        try {
              File myObj = new File(filePath);
              Scanner myReader = new Scanner(myObj);
              while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                output.append(data).append(" ");
              }
              myReader.close();
            } catch (FileNotFoundException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
            }

        return output.toString();
    }
}
