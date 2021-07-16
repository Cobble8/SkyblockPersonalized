package com.cobble.sbp.core;

import java.io.FileWriter;
import java.sql.Ref;
import java.util.ArrayList;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.simplejson.parser.JSONParser;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;

public class ConfigList {
	public static ArrayList<String> a = new ArrayList();
	public static ArrayList<String> c = new ArrayList();
	public static ArrayList<Object> d = new ArrayList();

	
	
	
	public static void loadConfig() throws Exception {


		//loadDefaultValues();
		
		//loadSetting("modToggle", true);
		loadSetting("APIKeyToggle", true);
		loadSetting("APIKey", "NOT_SET");
		//loadSetting("APIWarning", false);
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


		loadSetting("gameRecord", "d0");

		//MUSIC
		loadSetting("clientMusicToggle", false);
		loadCoords("musicInv", Minecraft.getMinecraft().displayWidth/2-100, 10);
		loadCoords("musicEsc", Minecraft.getMinecraft().displayWidth/2-100, 10);
		loadSetting("musicVolume", 0.5);
		

		loadCoords("scrt");
		loadSetting("scrtSize", 10);
		loadSetting("scrtBgColor", "0.0;0.0;0.0");
		loadSetting("scrtTextColor", "&f");
		loadSetting("scrtToggle", true);
		loadSetting("scrtAutoDownload", false);
		loadSetting("scrtAutoRemove", true);
		
		loadSetting("dungeonsCommandToggle", true);
		loadSetting("disablePickMsgs", false);
		
		//loadSetting("TEMP", "TEMP");
		
		//DWARVEN MINES
		loadCoords("dwarvenGui");
		loadSetting("dwarvenHOTMLevel", 0);
		
		loadSetting("dwarvenMithrilDisplay", true);
		loadTextColor("dwarvenMithrilTextColor");
		loadTextColor("dwarvenMithrilCountColor", "&2");
		loadTextColor("dwarvenGemstoneTextColor");
		loadTextColor("dwarvenGemstoneCountColor", "&d");
		
		loadSetting("dwarvenTimerToggle", false);
		loadTextColor("dwarvenTimerTextColor");
		loadTextColor("dwarvenTimerTextColor");
		loadSetting("dwarvenTimerDing", false);
		loadCoords("dwarvenTimer");


		loadSetting("dwarvenTrackToggle", false);
		loadSetting("dwarvenTrackNumsToggle", true);
		loadTextColor("dwarvenTrackCommissionColor", "&9&l");
		loadTextColor("dwarvenTrackQuestName");
		loadSetting("dwarvenTrackBarToggle", true);
		loadTextColor("dwarvenTrackBorderColor", "&2");
		loadTextColor("dwarvenTrackYesColor", "&a");
		loadTextColor("dwarvenTrackNoColor", "&8");
		loadSetting("dwarvenHideCommissionWord", false);
		loadSetting("dwarvenGuiScale", 10);

		loadSetting("dwarvenFuelToggle", false);

		loadSetting("starSentryHelper", true);
		loadCoords("starSentryHelper", 100, 100);
		loadSetting("starSentryOnlyWithComm", true);

		loadCoords("crystalMap", 100, 100);
		loadSetting("crystalMap", true);
		loadSetting("crystalMapSize", 10);

		loadSetting("windCompass", true);

		loadTextColor("dwarvenFuelDrillColor");
		loadTextColor("dwarvenFuelGuiPrimeTenColor", "&4");
		loadTextColor("dwarvenFuelGuiSecondTenColor", "&c");
		loadTextColor("dwarvenFuelGuiPrimeHalfColor", "&6");
		loadTextColor("dwarvenFuelGuiSecondHalfColor", "&e");
		loadTextColor("dwarvenFuelGuiPrimeFullColor", "&a");
		loadTextColor("dwarvenFuelGuiSecondFullColor", "&2");
		
		loadSetting("dwarvenTeleport", false);
		loadSetting("dwarvenTeleportGoblin", false);
		loadSetting("dwarvenTeleportRaffle", false);
		loadSetting("dwarvenTeleportQuest", true);
		loadSetting("dwarvenTeleportNotif", true);
		
		loadSetting("pickReminderToggle", false);
		loadSetting("pickTimerHolding", true);

		loadSetting("pickTimerCircleAcc", 30);
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
		loadSetting("jerryToggle", true);
		loadCoords("jerry");
		loadTextColor("jerryTextColor", "&2");
		
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
		//Utils.print("loadSetting : "+id+" : "+defaultValue);
		c.add(id);
		d.add(defaultValue);
	}
	
	private static void checkForMissingValues() throws Exception {

		JSONObject oldConfig;
		try {
			oldConfig = (JSONObject) new JSONParser().parse(ConfigHandler.oldConfig.toString());
		} catch(Exception e) {
			ConfigHandler.resetConfig();
			ConfigHandler.oldConfig = Utils.readFile("config/"+Reference.MODID+"/main.cfg");
			oldConfig = (JSONObject) new JSONParser().parse(ConfigHandler.oldConfig.toString());
		}


		//Utils.print(oldConfig.toString());
		for(int i=0;i<c.size();i++) {
			Object currValue;

			try {
				currValue = oldConfig.get(c.get(i));
			} catch(Exception e) {
				e.printStackTrace();
				currValue = d.get(i);
			}
			
			if(currValue == null) {
				Utils.print("'"+c.get(i) + "' was not found, setting it to the default value of '"+d.get(i)+"'.");
				currValue = d.get(i);

			}
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

        Utils.print("Finished checking for missing config values");
	}
	
	public static void loadCoords(String coordName, int x, int y) {
		loadSetting(coordName+"X", x);
		loadSetting(coordName+"Y", y);
	}
	public static void loadCoords(String coordName) {
		loadCoords(coordName, 0, 0);
	}

	public static void loadTextColor(String textColorID) {
		loadTextColor(textColorID, "&f");
	}

	public static void loadTextColor(String textColorID, String defaultValue) {
		loadSetting(textColorID, defaultValue);
		if(!DataGetter.findStr(textColorID).contains("&")) {
			try {

				ConfigHandler.newObject(textColorID, defaultValue);

				/*JSONObject oldConfig = (JSONObject) new JSONParser().parse(ConfigHandler.oldConfig.toString());

				Utils.print("----------------------------------");
				Utils.print("TEXT COLOR IDS!");
				Utils.print(textColorID);
				Utils.print(oldConfig.get(textColorID));
				Utils.print(Utils.getColorFromInt(Integer.parseInt(oldConfig.get(textColorID)+"")));
				Utils.print((Utils.getColorFromInt(Integer.parseInt(oldConfig.get(textColorID)+""))).replace(Reference.COLOR_CODE_CHAR+"", "&"));
				String output = (Utils.getColorFromInt(Integer.parseInt(oldConfig.get(textColorID)+""))).replace(Reference.COLOR_CODE_CHAR+"", "&");
				ConfigHandler.newObject(textColorID, output);
				Utils.print("Result : "+ DataGetter.findStr(textColorID));
				Utils.print("Success!");
				Utils.print("");*/
			} catch(Exception e) {
				ConfigHandler.newObject(textColorID, defaultValue);
			}

		}


	}
	
}
