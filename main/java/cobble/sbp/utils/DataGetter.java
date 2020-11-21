package cobble.sbp.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import cobble.sbp.simplejson.JSONObject;
import cobble.sbp.simplejson.parser.JSONParser;

public class DataGetter
{			
	public static Object find(String objectName) {
		JSONParser parser = new JSONParser();
		Object dataOutput = null;
		try {
			Object obj = parser.parse(new FileReader("config/sbp/sbp.cfg"));
 
			// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject data = (JSONObject) obj;
			
			dataOutput = data.get(objectName);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataOutput;
	}
	
	public static Object findSpecific(String fileName, String objectName) {
		JSONParser parser = new JSONParser();
		Object dataOutput = null;
		try {
			Object obj = parser.parse(new FileReader(fileName));
 
			// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject data = (JSONObject) obj;
			
			dataOutput = data.get(objectName);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataOutput;
	}
	
	public static BufferedReader get(String path) throws Exception
    {
        URL url = new URL("https://raw.githubusercontent.com/SteveKunG/SkyBlockcatia/1.8.9/" + path);

        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8));
            return in;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
