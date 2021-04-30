package com.cobble.sbp.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.cobble.sbp.commands.SecretFinder;
import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.handlers.DownloadSecretsHandler;
import com.cobble.sbp.simplejson.JSONArray;
import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.simplejson.parser.JSONParser;
import com.cobble.sbp.simplejson.parser.ParseException;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class SecretUtils {
	
	public static String prettifyRoomname(String roomID) {
		String output = "";
		String[] input = roomID.split("-");
		for(int j=0;j<input.length;j++) { output+=input[j].substring(0, 1).toUpperCase() + input[j].substring(1)+" "; }
		output = Utils.removeLastChars(output, 1);
		
		try {
			int scrtNum = Integer.parseInt(input[input.length-1]);
			output = Utils.removeLastChars(output, (scrtNum+"").length()+1);
		} catch(Exception e) {}
		
		return output;
	}
	
	public static ArrayList<String> getRoomNames(String roomShape, String dungeon) {
		
		ArrayList<String> output = new ArrayList();
		testForData(dungeon);
		try {
			JSONObject info = (JSONObject) ((JSONObject) new JSONParser().parse(Utils.readFile("config/"+Reference.MODID+"/secrets/"+dungeon+"/SecretImageText.json"))).get(roomShape);
		
			Object[] keyset = info.keySet().toArray();
			for(Object str : keyset) {
				output.add(str+"");
			}
		
		
			return output;
		} catch (Exception e) { return output; }

		
		
	}
	
	public static void updateDungeonList() {
		try {
				String file = HttpClient.readPage("https://raw.githubusercontent.com/Cobble8/SkyblockPersonalized/main/versioncheck.json");
				JsonParser parser = new JsonParser();
				JsonElement info = parser.parse(file);
				JsonArray tmp = info.getAsJsonObject().get("activeDungeons").getAsJsonArray();
				DownloadSecretsHandler.dungeons.clear(); for(Object str : tmp) { DownloadSecretsHandler.dungeons.add((str+"").replace("\"", "")); }
				
				//List<String> enableList = Arrays.asList(info.getAsJsonObject().get("forceEnable").getAsJsonArray().getAsString());
				//ConfigHandler.forceEnabled.clear(); for(Object str : enableList) { ConfigHandler.forceEnabled.add(str+"".replace("[\"", "").replace("\"]", ""));  ConfigHandler.newObject(str+"", true); }
				//List disableList = Arrays.asList(info.getAsJsonObject().get("forceDisable").getAsJsonArray().getAsString());
				//ConfigHandler.forceDisabled.clear(); for(Object str : disableList) { ConfigHandler.forceDisabled.add(str+"".replace("[\"", "").replace("\"]", "")); ConfigHandler.newObject(str+"", false);}
				//Utils.print(ConfigHandler.forceEnabled);
				//Utils.print(ConfigHandler.forceDisabled);
		} catch(Exception e) { e.printStackTrace(); return; }
	}
	
	public static ArrayList<String> getRoomDesc(String roomShape, String roomID, String dungeon) {
		ArrayList<String> output = new ArrayList();
		
		testForData(dungeon);


		try {
			JSONObject info = (JSONObject) ((JSONObject) new JSONParser().parse(Utils.readFile("config/"+Reference.MODID+"/secrets/"+dungeon+"/SecretImageText.json"))).get(roomShape);
			Object[] keyset = info.keySet().toArray();
			for(int i=0;i<keyset.length;i++) {
				if((keyset[i]+"").startsWith(roomID.toLowerCase())) {
					JSONArray tmp2 = (JSONArray) info.get(keyset[i]);
					for(Object str : tmp2) {
						output.add(str+"");
					}
					continue;
				}
			}
			
		} catch (ParseException e) { return output; }
		
		return output;
	}
	
	public static ArrayList<String> getRoomName(String roomShape, int secretCount, String dungeon) {
		ArrayList<String> output = new ArrayList();
		ArrayList<String> possibleRooms = getRoomNames(roomShape, dungeon);
		
		
		
		for(int i=0;i<possibleRooms.size();i++) {
			if(possibleRooms.get(i).endsWith(secretCount+"")) {
				output.add(possibleRooms.get(i));
			}
		}
		
		
		
		return output;
	}
	
	public static void testForData(String dungeon) {
		if(!Utils.fileTest("config/"+Reference.MODID+"/secrets/"+dungeon+"/SecretImageText.json")) {
			File infoLoc = new File("config/"+Reference.MODID+"/secrets/"+dungeon+"/SecretImageText.json"); infoLoc.getParentFile().mkdirs();
			Utils.saveImage("https://raw.githubusercontent.com/Cobble8/DungeonSecrets/master/secrets/"+dungeon+"/SecretImageText.json", infoLoc.getPath());
		}
	}
	
	public static ArrayList<String> getRoomShapes (String dungeon) {
		ArrayList<String> output = new ArrayList();
		testForData(dungeon);
		try {
			JSONObject info = (JSONObject) new JSONParser().parse(Utils.readFile("config/"+Reference.MODID+"/secrets/"+dungeon+"/SecretImageText.json"));
			Object[] keyset = info.keySet().toArray();
			for(Object str : keyset) { output.add(str+""); }
		
			return output;
		} catch (ParseException e) { return output; }
		
		
	}
}
