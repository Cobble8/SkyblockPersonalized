package com.cobble.sbp.utils;

import com.cobble.sbp.core.config.DataGetter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class CheckAPIKey {
	
	public static Boolean validAPIKey = false;
	
	public static void checkValidAPIKey() throws Exception {
		String APIKey = (String) DataGetter.find("APIKey");
		String file = "{\"success\":false,\"cause\":\"Invalid API key\"}";
		try {
		file = HttpClient.readPage("https://api.hypixel.net/player?key="+APIKey+"&uuid=c9385237ccc74843b2c6c19385bce60a");
		
		} catch(NullPointerException e) {
			return;
		} catch(IllegalArgumentException e) {
			return;
		} catch(Exception e) {
			validAPIKey = false;
		}
		
		JsonParser parser = new JsonParser();
		JsonElement fileAsJson = parser.parse(file.toString());
		Boolean success = fileAsJson.getAsJsonObject().get("success").getAsBoolean();
		if(success) { validAPIKey = true; }
	}
	
	
}