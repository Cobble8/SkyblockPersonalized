package com.cobble.sbp.utils;

import com.cobble.sbp.core.config.DataGetter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class CheckAPIKey {
	
	public static Boolean validAPIKey = false;
	
	public static void checkValidAPIKey() throws Exception {
		String APIKey = DataGetter.findStr("APIKey");
		validAPIKey= !APIKey.equals("NOT_SET");
	}
	
	
}
