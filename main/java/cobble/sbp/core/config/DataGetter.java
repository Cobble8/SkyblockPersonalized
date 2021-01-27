package com.cobble.sbp.core.config;

import java.io.FileReader;

import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.simplejson.parser.JSONParser;
import com.cobble.sbp.utils.Reference;

public class DataGetter
{			

	private static String inEclipse = "";
	
	
	
	public static Object find(String objectName) {
		JSONParser parser = new JSONParser();
		Object dataOutput = "null";
		try {
			Object obj = parser.parse(new FileReader("config/"+Reference.MODID+"/main.cfg"));
 
			// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
			//JSONObject jsonObject = (JSONObject) obj;
			JSONObject data = (JSONObject) obj;
			
			dataOutput = data.get(objectName);
			

		} catch (Exception e) {
			//Utils.sendErrMsg("Couldn't find key: \""+objectName+"\" in the config");
			return "NOT FOUND";
		}
		return dataOutput;
	}
	
	public static Object find(String fileName, String objectName) {
		JSONParser parser = new JSONParser();
		Object dataOutput = "null";
		try {
			Object obj = parser.parse(new FileReader("config/"+Reference.MODID+"/"+fileName+".cfg"));

			JSONObject data = (JSONObject) obj;
			
			dataOutput = data.get(objectName);
			

		} catch (Exception e) {
			e.printStackTrace();
			return "NOT FOUND";
		}
		return dataOutput;
	}
	
	
}
