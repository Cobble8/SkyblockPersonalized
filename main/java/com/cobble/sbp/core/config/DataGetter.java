package com.cobble.sbp.core.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.cobble.sbp.SBP;
import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.simplejson.parser.JSONParser;
import com.cobble.sbp.simplejson.parser.ParseException;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DataGetter
{			
	/** Returns a boolean at the specified config value
	 * Returns false if fails
	 */
	public static Boolean findBool(String boolName) {
		try {
		return Boolean.parseBoolean(find(boolName));
		} catch (Exception e) {
			try {
			return (Boolean) ConfigHandler.getDefaultValue(boolName);
			} catch(Exception e2) {
				return null;
			}
		}
	}
	
	/** Returns a string at the specified config value
	 * Returns "false" if fails
	 */
	public static String findStr(String strName) {
		try {
		return find(strName);
		} catch (Exception e) {
			try {
				return ConfigHandler.getDefaultValue(strName)+"";
				} catch(Exception e2) {
					return null;
				}
		}
	}
	
	/** Returns an int at the specified config value
	 * Returns -1 if fails
	 */
	public static int findInt(String intName) {
		try {
			//Utils.print(intName+": "+find(intName));
		return Integer.parseInt(find(intName));
		} catch (Exception e) {
			try {
				return Integer.parseInt(ConfigHandler.getDefaultValue(intName)+"");
				} catch(Exception e2) {
					return -69;
				}
		}
	}
	
	
	
	public static String find(String objectName) {
		/*
		if(objectName.contains(".")) {
			
			String trialConfig = "{ \"temp1\": { \"temp2\": { \"temp3\": true } } }";
			
			try {
				String[] keyList = objectName.replace(".", "/").split("/");
				
				String currSearch = trialConfig;
				Utils.print(currSearch);
				JsonParser parser = new JsonParser();
				for(int i=0;i<keyList.length-1;i++) {
					
					//Going through each key
					JsonElement searching = parser.parse(currSearch);
					currSearch = searching.getAsJsonObject().get(keyList[i])+"";
					
				}
				
				
				//Output
				return parser.parse(currSearch).getAsJsonObject().get(keyList[keyList.length-1])+"";
			} catch(Exception e) { return "N/A";}
			
			
		}*/
		
		
		
		if(ConfigHandler.configObj == null) { updateConfig("main"); }
		
		try {
		JSONObject data = (JSONObject) ConfigHandler.configObj;
		String dataOutput = data.get(objectName)+"";
		
		//Utils.print("Found: "+objectName+": "+dataOutput);
		
		return dataOutput;
		} catch(Exception e) {
			//Utils.print("Failed to find: "+objectName);
			return null;
		}
	}
	
	public static Object find(String fileName, String objectName) {
		if(ConfigHandler.configObj == null) {
			updateConfig(fileName);
		}
		try {
			JSONObject data = (JSONObject) ConfigHandler.configObj;
			Object dataOutput = data.get(objectName);
			return dataOutput;
		} catch(NullPointerException e) {
			
		} catch(Exception e) {
			return "failed";
		}
		return "failed";
	}
	
	public static void updateConfig(String fileName) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("config/"+Reference.MODID+"/"+fileName+".cfg"));
			ConfigHandler.configObj = obj;
		} catch (Exception e) {
			SBP.firstLaunch=true;
		}
	}
	
	
}
