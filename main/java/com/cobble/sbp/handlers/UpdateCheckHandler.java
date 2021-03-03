package com.cobble.sbp.handlers;

import com.cobble.sbp.threads.onetimes.LoginThread;
import com.cobble.sbp.utils.HttpClient;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class UpdateCheckHandler {

	
	public static void check() {
		//
		
		
		
		try {
			
			String siteContent = HttpClient.readPage("https://raw.githubusercontent.com/Cobble8/SkyblockPersonalized/main/versioncheck.json");
			
			JsonParser parser = new JsonParser();
			JsonElement update = parser.parse(siteContent.toString());
			
			
			
			
			String rel = update.getAsJsonObject().get("rel").getAsString().replace(",", ".");
			if(Reference.VERSION.equals(rel)) {
				Utils.print(Reference.NAME+" is up to date!");
				return;
			}
			Utils.print(rel);
			rel = rel.replace(".", "/");
			String[] relArr = rel.split("/");
			
			int a = Integer.parseInt(relArr[0]);
			int b = Integer.parseInt(relArr[1]);
			int c = Integer.parseInt(relArr[2]);
			int d = Integer.parseInt(relArr[3]);
			String relType = "Full Release";
			if(c != 0); {relType = "Beta Release";}
			if(d != 0) {relType+=" (Bug Fix)";}
			LoginThread.updateType = relType;
			Utils.print(rel+": "+relType);
		} catch (Exception e) { Utils.print("Failed to check for update!"); e.printStackTrace(); }
	}
	
}
