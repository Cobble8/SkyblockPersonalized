package com.cobble.sbp.utils;

import java.util.Scanner;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
public class HttpClient {
   public static String readPage(String args) throws Exception{
      CloseableHttpClient httpclient = HttpClients.createDefault();
      HttpGet httpget = new HttpGet(args);
      HttpResponse httpresponse = httpclient.execute(httpget);
      @SuppressWarnings("resource")
	Scanner sc = new Scanner(httpresponse.getEntity().getContent());
      StringBuffer sb = new StringBuffer();
      while(sc.hasNext()) {
         sb.append(sc.next());
      }
      String result = sb.toString();
      return result;
      
   }
}
