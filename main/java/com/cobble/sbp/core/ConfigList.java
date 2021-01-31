package com.cobble.sbp.core;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class ConfigList {
	public static ArrayList<String> a = new ArrayList();
	public static ArrayList<String> c = new ArrayList();
	public static ArrayList<Object> d = new ArrayList();

	
	
	
	public static void loadConfig() throws Exception {
		//loadDefaultValues();
		
		loadSetting("modToggle", true);
		loadSetting("APIKeyToggle", true);
		loadSetting("APIKey", "NOT_SET");
		loadSetting("repartyCommandToggle", true);
		loadSetting("pingDelay", 1000);
		loadSetting("modLaunchToggle", true);
		loadSetting("modLaunches", 0);
		loadSetting("currentTheme", 0);
		loadSetting("onlyOnSkyblock", true);
		loadSetting("gridLockingToggle", true);
		loadSetting("gridLockingPx", 3);
		
		loadSetting("boxSolverToggle", false);
		loadSetting("iceSolverToggle", false);
		
		loadSetting("scrtSize", 10);
		loadSetting("scrtX", 0);
		loadSetting("scrtY", 0);
		loadSetting("scrtBgColor", "1.0;1.0;1.0");
		loadSetting("scrtTextColor", 0);
		loadSetting("scrtTransparent", false);
		loadSetting("scrtToggle", true);
		
		loadSetting("dungeonsCommandToggle", true);
		loadSetting("disableCommonDrops", false);
		loadSetting("commonDropList", "Carrot, Potato");
		loadSetting("disablePickMsgs", false);
		
		loadSetting("jerryTimerToggle", false);
		loadSetting("compactToggle", false);
		loadSetting("pickReminderToggle", false);
		
		loadSetting("puzzleX", 0);
		loadSetting("puzzleY", 0);
		loadSetting("puzzleScale", 10);
		loadSetting("puzzleDelay", 30);
		loadSetting("puzzleColor", "0.0;0.0;0.0");
		
		loadSetting("dwarvenTimerToggle", false);
		loadSetting("dwarvenTimerTextColor", 0);
		loadSetting("dwarvenTimerX", 0);
		loadSetting("dwarvenTimerY", 0);
		loadSetting("dwarvenTimerDing", false);
		
		loadSetting("dwarvenTrackToggle", false);
		loadSetting("dwarvenTrackX", 0);
		loadSetting("dwarvenTrackY", 0);
		loadSetting("dwarvenTrackBarToggle", true);
		loadSetting("dwarvenTrackBorderColor", 6);
		loadSetting("dwarvenTrackYesColor", 5);
		loadSetting("dwarvenTrackNoColor", 13);
		
		loadSetting("dwarvenFuelToggle", false);
		loadSetting("dwarvenOnlyFuel", true);
		loadSetting("dwarvenFuelX", 0);
		loadSetting("dwarvenFuelY", 0);
		
		loadSetting("pickTimerToggle", false);
		loadSetting("pickTimerTextColor", 7);
		loadSetting("pickActiveTimerTextColor", 3);
		loadSetting("pickTimerX", 0);
		loadSetting("pickTimerY", 0);
		loadSetting("pickTimerDwarven", true);
		loadSetting("pickTimerHolding", true);

		loadSetting("puzzlerSolver", true);
		
		loadSetting("easterEggsFound", "???;???");
		loadSetting("npcDialogueToggle", true);
		
		
		
		loadAchieve("fifty_launches");
		
		
		
		
		
		
		checkForMissingValues();
	}
	
	public static void loadSetting(String id, Object defaultValue) {
		c.add(id);
		d.add(defaultValue);
	}
	
	public static void loadAchieve(String id) {
		a.add(id);
	}
	
	private static void checkForMissingValues() throws Exception {
		for(int i=0;i<c.size();i++) {
			Object currValue = null;

			try {
			if(d.get(i) instanceof String) { currValue = DataGetter.findStr(c.get(i)); if(currValue.equals("null")) { currValue = d.get(i); }}
			else if(d.get(i) instanceof Boolean) { currValue = DataGetter.findBool(c.get(i)); if(currValue == null) { currValue = d.get(i); }}
			
			else if(d.get(i) instanceof Integer) { currValue = DataGetter.findInt(c.get(i)); if(Integer.parseInt(currValue+"") == -69) { currValue = d.get(i); }}
			} catch(Exception e) {currValue = d.get(i);}
			
			if(currValue == null) { currValue = d.get(i); }
			ConfigHandler.obj.put(c.get(i), currValue);
			
		}
		
		FileWriter file = new FileWriter("config/"+Reference.MODID+"/main.cfg");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(ConfigHandler.obj.toJSONString());
        String prettyJsonString = gson.toJson(je);
        
        file.write(prettyJsonString);
        file.flush();
        file.close();
        
        ConfigHandler.configObj = ConfigHandler.obj;
        /*FileWriter file2 = new FileWriter("config/"+Reference.MODID+"/quests.cfg");
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp2 = new JsonParser();
        JsonElement je2 = jp2.parse(ConfigHandler.obj2.toJSONString());
        String prettyJsonString2 = gson2.toJson(je2);
        
        file2.write(prettyJsonString2);
        file2.flush();
        file2.close();*/
        
        
        Utils.print("Finished checking for missing config values");
	}
	
	
	
}
