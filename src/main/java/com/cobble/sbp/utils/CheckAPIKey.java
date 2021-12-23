package com.cobble.sbp.utils;

import com.cobble.sbp.core.config.DataGetter;

public class CheckAPIKey {
	
	public static Boolean validAPIKey = false;
	
	public static void checkValidAPIKey() throws Exception {
		String APIKey = DataGetter.findStr("core.api.key");
		validAPIKey= !APIKey.equals("NOT_SET");
	}
	
	
}
