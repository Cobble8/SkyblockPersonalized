package com.cobble.sbp.utils;

import com.cobble.sbp.core.config.DataGetter;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public class WebUtils {
   public static boolean validAPIKey = false;

   public static String readPage(String args, boolean spaces) throws Exception {

       CloseableHttpClient httpclient = HttpClients.createDefault();
       HttpGet httpget = new HttpGet(args);

       HttpResponse httpresponse = httpclient.execute(httpget);
       @SuppressWarnings("resource")
       Scanner sc = new Scanner(httpresponse.getEntity().getContent());
       StringBuilder sb = new StringBuilder();
       while(sc.hasNext()) {
          sb.append(sc.next());
          if(spaces) {
              sb.append(" ");
          }
       }
       return sb.toString();

    }

    public static String readPage(String args) throws Exception {
       return readPage(args, false);
    }

   public static void checkValidAPIKey() throws Exception {
      String APIKey = DataGetter.findStr("core.api.key");
      validAPIKey = !APIKey.equals("NOT_SET");
   }

    public static void openURL(String url) {
        try {
            Desktop.getDesktop().browse(URI.create(url));
        } catch (IOException e) { TextUtils.sendErrMsg("Failed to open URL: "+url);}
    }

    public static void saveImage(String imageUrl, String destinationFile) {
        try {

            new File(destinationFile).getParentFile().mkdirs();

            URL url = new URL(imageUrl);
            InputStream is = url.openStream();

            /*String destin = destinationFile;
            if(!destin.endsWith("\\")) {
                destin+="\\";
            }*/

            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch(Exception e) {
            e.printStackTrace();
            //Utils.sendErrMsg("It failed at: "+destinationFile);

            //String[] locFolder = destinationFile.split("/");


            //File imgFolder = new File(destinationFile); imgFolder.mkdirs();
            //saveImage(imageUrl, destinationFile);
            Utils.print("Download failed for file: '"+imageUrl+"' at '"+destinationFile+"'");
        }

    }

    public static void saveFileFromUrl(String imageUrl, String destinationFile) {
        try { FileUtils.saveFile(readPage(imageUrl), destinationFile); } catch (Exception ignored) { }
    }
}
