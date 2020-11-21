package cobble.sbp.utils;

import java.util.Scanner;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
public class HttpClient {
   public static String readPage(String args) throws Exception{
      //Creating a HttpClient object
      CloseableHttpClient httpclient = HttpClients.createDefault();
      //Creating a HttpGet object
      HttpGet httpget = new HttpGet(args);
      //Executing the Get request
      HttpResponse httpresponse = httpclient.execute(httpget);
      @SuppressWarnings("resource")
	Scanner sc = new Scanner(httpresponse.getEntity().getContent());
      //Instantiating the StringBuffer class to hold the result
      StringBuffer sb = new StringBuffer();
      while(sc.hasNext()) {
         sb.append(sc.next());
         //System.out.println(sc.next());
      }
      //Retrieving the String from the String Buffer object
      String result = sb.toString();
      //System.out.println(result);
      return result;
      
   }
}