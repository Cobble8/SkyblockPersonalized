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
		loadSetting("APIWarning", false);
		loadSetting("repartyCommandToggle", true);
		loadSetting("pingDelay", 1000);
		loadSetting("modLaunchToggle", true);
		loadSetting("modLaunches", 0);
		loadSetting("currentTheme", 0);
		loadSetting("onlyOnSkyblock", true);
		loadSetting("gridLockingToggle", true);
		loadSetting("gridLockingPx", 3);
		loadSetting("textStyle", 0);
		loadSetting("chromaSpeed", 4);
		loadSetting("dgnImgVers", 0);
		
		//DUNGEONS
		loadSetting("boxSolverToggle", false);
		loadSetting("iceSolverToggle", false);
		
		loadSetting("scrtSize", 10);
		loadCoords("scrt");
		loadSetting("scrtBgColor", "0.0;0.0;0.0");
		loadSetting("scrtTextColor", 0);
		loadSetting("scrtToggle", true);
		loadSetting("scrtAutoDownload", false);
		loadSetting("scrtAutoRemove", true);
		
		loadSetting("dungeonsCommandToggle", true);
		loadSetting("disableCommonDrops", false);
		loadSetting("commonDropList", "Carrot, Potato");
		loadSetting("disablePickMsgs", false);
		
		
		
		//DWARVEN MINES
		loadCoords("dwarvenGui");
		loadSetting("dwarvenHOTMLevel", 0);
		
		loadSetting("dwarvenMithrilDisplay", true);
		loadSetting("dwarvenMithrilTextColor", 0);
		loadSetting("dwarvenMithrilCountColor", 6);
		
		loadSetting("dwarvenTimerToggle", false);
		loadSetting("dwarvenTimerTextColor", 0);
		loadSetting("dwarvenTimerDing", false);
		loadCoords("dwarvenTimer");
		
		loadSetting("dwarvenTrackToggle", false);
		loadSetting("dwarvenTrackCommissionColor", 9);
		loadSetting("dwarvenTrackQuestName", 0);
		loadSetting("dwarvenTrackBarToggle", true);
		loadSetting("dwarvenTrackBorderColor", 6);
		loadSetting("dwarvenTrackYesColor", 5);
		loadSetting("dwarvenTrackNoColor", 14);
		
		loadSetting("dwarvenFuelToggle", false);
		loadSetting("dwarvenOnlyFuel", true);
		loadSetting("dwarvenFuelDurr", true);
		loadSetting("dwarvenFuelGui", true);
		
		loadSetting("dwarvenFuelDrillColor", 0);
		loadSetting("dwarvenFuelGuiPrimeTenColor", 1);
		loadSetting("dwarvenFuelGuiSecondTenColor", 2);
		loadSetting("dwarvenFuelGuiPrimeHalfColor", 3);
		loadSetting("dwarvenFuelGuiSecondHalfColor", 4);
		loadSetting("dwarvenFuelGuiPrimeFullColor", 5);
		loadSetting("dwarvenFuelGuiSecondFullColor", 6);
		
		loadSetting("dwarvenTeleport", false);
		loadSetting("dwarvenTeleportGoblin", false);
		loadSetting("dwarvenTeleportRaffle", false);
		loadSetting("dwarvenTeleportQuest", true);
		loadSetting("dwarvenTeleportNotif", true);
		
		loadSetting("pickReminderToggle", false);
		loadSetting("pickTimerGui", true);
		loadSetting("pickTimerTextColor", 7);
		loadSetting("pickActiveTimerTextColor", 3);
		loadCoords("pickTimer");
		loadSetting("pickTimerHolding", true);
		
		loadSetting("pickTimerCircle", true);
		loadSetting("pickTimerCircleAcc", 15);
		loadSetting("pickTimerCircleRadius", 11);
		loadSetting("pickTimerCircleActive", "0.0;1.0;0.0");
		loadSetting("pickTimerCircleCd", "0.0;1.0;1.0");
		loadSetting("pickTimerCircleReady", "1.0;1.0;0.0");
		

		loadSetting("puzzlerSolver", true);
		loadSetting("fetchurSolver", true);
		loadSetting("blockHoeClicks", true);
		loadSetting("blocksInWayMsgs", true);
		loadSetting("blockedQuickCrafts", ";;;");
		loadSetting("toggleBlockedQuickCrafts", true);
		
		loadSetting("dwarvenCommBgToggle", true);
		loadSetting("dwarvenCommBgColor", "0.0;1.0;0.0");
		
		//QOL
		loadSetting("jerryTimerToggle", false);
		
		loadSetting("disableSuperboomPickups", false);
		loadSetting("easterEggsFound", "???;???");
		loadSetting("npcDialogueToggle", true);
		loadSetting("disableChatMessages", true);
		loadSetting("compactToggle", false);
		loadSetting("pickReminderToggle", false);
		loadSetting("disableAutopetMsgs", false);
		
		loadSetting("abilityDamageToggle", false);
		loadCoords("abilityDamage", 100, 100);
		loadSetting("abilityDamagePoof", 5);
		
		loadSetting("comboMsgToggle", false);
		loadCoords("comboMsg", 100, 100);
		loadSetting("comboMsgPoof", 5);
		loadSetting("werewolfToggle", false);
		loadSetting("updateNotifs", true);
		
		
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
			else if(d.get(i) instanceof Boolean) { 
				
				currValue = DataGetter.findBool(c.get(i)); 
				
				if(currValue == null) { 
					currValue = d.get(i); 
				}
				
			}
			
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
	
	public static void loadCoords(String coordName, int x, int y) {
		loadSetting(coordName+"X", x);
		loadSetting(coordName+"Y", y);
	}
	public static void loadCoords(String coordName) {
		loadCoords(coordName, 0, 0);
	}
	
}
