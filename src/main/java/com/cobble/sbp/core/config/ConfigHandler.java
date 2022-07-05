package com.cobble.sbp.core.config;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.ConfigList;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.screen.dwarven.*;
import com.cobble.sbp.gui.screen.misc.JerryTimer;
import com.cobble.sbp.handlers.TransferConfig;
import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.utils.FileUtils;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import com.google.gson.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;



public class ConfigHandler {
	
	public static Object configObj = null;

	public static Object oldConfig = null;
	
	public static JSONObject obj = new JSONObject();
	public static Boolean firstLaunch = false;

	public static HashMap<String, Object> config = new HashMap<>();

	public static ArrayList<String> forceDisabled = new ArrayList<>();
	public static ArrayList<String> forceEnabled = new ArrayList<>();
	
	public static void registerConfig() throws Exception {
		boolean firstLaunch = !FileUtils.fileTest("config/"+Reference.MODID+"/sbp.cfg");
		if(firstLaunch) { firstLaunch(); }


		boolean transferConfig = FileUtils.fileTest("config/"+Reference.MODID+"/main.cfg");
		if(transferConfig) {
			System.out.println("Transferring Config...");
			new TransferConfig();

		} else {
			System.out.println("Loading Config...");
			ConfigList.loadConfig();
		}
		
	}
	
	private static void firstLaunch() {
		SBP.firstLaunch=true;
		File configFolder = new File("config/"+Reference.MODID); configFolder.mkdirs();
		File config = new File("config/"+Reference.MODID+"/sbp.cfg");
		try { if (config.createNewFile()) {Utils.print("The config file 'sbp' for "+Reference.NAME+" has been created at: "+config.getAbsolutePath());}}
		catch (IOException e) {e.printStackTrace();}
	}

	public static void newObject(String key, Object value, boolean saveToConfig) {
		ConfigHandler.config.put(key, value);
		if(saveToConfig) { try { rewriteConfig(); } catch(Exception ignored) {} }
	}

	public static void newObject(String key, Object value) {
		newObject(key, value, true);
	}


	public static void rewriteConfig() throws IOException {
		FileWriter file = new FileWriter("config/"+Reference.MODID+"/sbp.cfg");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		ArrayList<String> categories = new ArrayList<>();
		ArrayList<String> settings = new ArrayList<>();
		ArrayList<String> values = new ArrayList<>();

		for(String key : config.keySet()) {
			String[] split = key.split("\\.");
			String category;
			String setting;
			String value;
			try {
				category = split[0];
				setting = split[1];
				value = split[2];
			} catch(Exception e) { /*System.out.println("Invalid Config Variable Detected: '"+key+"'");*/ continue; }


			String currKey = category;

			if(arrayContains(categories, currKey)) { categories.add(currKey); }
			currKey+="."+setting;
			if(arrayContains(settings, currKey)) { settings.add(currKey); }
			currKey+="."+value;
			if(arrayContains(values, currKey)) { values.add(currKey); }
		}


		JSONObject cats = new JSONObject();
		for(String c : categories) {
			JSONObject sets = new JSONObject();
			for(String s : settings) {

				//System.out.println(s+" : "+vals);


				String[] split = s.split("\\.");
				String cat = split[0];
				String set = split[1];
				if(cat.equals(c)) {

					JSONObject vals = new JSONObject();
					for(String v : values) {


						String splitSet = s.split("\\.")[1];
						String[] vSplit = v.split("\\.");
						if(vSplit[1].equals(splitSet)) {
							String val = vSplit[2];


							//System.out.println(v+" : "+config.get(v));
							vals.put(val, config.get(v));
						}
					}

					sets.put(set, vals);
				}
			}
			cats.put(c, sets);
		}

		String tmp = cats.toString();
		JsonElement newCfg = new JsonParser().parse(tmp);
		String pretty = gson.toJson(newCfg);
		file.write(pretty);
		file.flush();
		file.close();

	}

	private static boolean arrayContains(ArrayList<String> arr, String currKey) {
		boolean found = false;
		for (String s : arr) {
			if (s.equals(currKey)) {
				found = true;
				break;
			}
		}
		return !found;
	}

	
	public static Object getDefaultValue(String id) {
		Object output = "null";
		
		for(int i=0;i<ConfigList.d.size();i++) {
			if(id.equals(ConfigList.c.get(i))) {
				output = ConfigList.d.get(i);
			}
		}
		
		return output;
	}
	
	public static void resetConfig() {
		for(int i=0;i<ConfigList.c.size();i++) {ConfigHandler.newObject(ConfigList.c.get(i), ConfigList.d.get(i)); }

		try {ConfigHandler.rewriteConfig(); } catch(Exception ignored) {}
	}
	
	public static void updateConfig(String c) {
		try {
			CrystalHollowsMap.mapToggle = DataGetter.findBool("dwarven.crystalMap.toggle");
			DwarvenTimer.dwarvenTimerToggle = DataGetter.findBool("dwarven.eventTimer.toggle");
			DwarvenGui.commTrackToggle = DataGetter.findBool("dwarven.gui.commissionToggle");
			DwarvenGui.fuelToggle =  DataGetter.findBool("dwarven.gui.fuelToggle");
			SettingMenu.modLaunchToggle = DataGetter.findBool("core.launchCounter.toggle");
			DwarvenPickaxeTimer.pickTimerToggle = DataGetter.findBool("dwarven.abilityTimer.toggle");
			DwarvenGui.mithrilToggle = DataGetter.findBool("dwarven.gui.powderToggle");
			DwarvenGui.sentryToggle = DataGetter.findBool("dwarven.starSentryHelper.toggle");
			DwarvenTimer.dwarvenTimerToggle = DataGetter.findBool("dwarven.eventTimer.toggle");
			JerryTimer.jerryToggle = DataGetter.findBool("misc.jerryTimer.toggle");
			CrystalLobbyDay.showDays = DataGetter.findBool("dwarven.crystalDay.toggle");

		if(c.equals("dwarven.crystalMap.toggle")) {
			CrystalHollowsMap.mapX = DataGetter.findInt("dwarven.crystalMap.x");
			CrystalHollowsMap.mapY = DataGetter.findInt("dwarven.crystalMap.y");
			CrystalHollowsMap.scale = DataGetter.findInt("dwarven.crystalMap.size")/10f;
			CrystalHollowsMap.showHead = DataGetter.findBool("dwarven.crystalMap.headToggle");
			CrystalHollowsMap.coords = DataGetter.findBool("dwarven.crystalMap.coordsToggle");
			CrystalHollowsMap.textColor = DataGetter.findStr("dwarven.crystalMap.textColor");
		}

		else if(c.equals("dwarven.crystalDay.toggle")) {
			CrystalLobbyDay.daysX = DataGetter.findInt("dwarven.crystalDay.x");
			CrystalLobbyDay.daysY = DataGetter.findInt("dwarven.crystalDay.y");
			CrystalLobbyDay.daysColor1 = DataGetter.findStr("dwarven.crystalDay.textColor");
			CrystalLobbyDay.daysColor2 = DataGetter.findStr("dwarven.crystalDay.numColor");
			CrystalLobbyDay.onlyInCrystal = DataGetter.findBool("dwarven.crystalDay.crystalOnly");
		}

		else if(c.equals("dwarven.starSentryHelper.toggle")) {
			DwarvenGui.sentryX = DataGetter.findInt("dwarven.starSentryHelper.x");
			DwarvenGui.sentryY = DataGetter.findInt("dwarven.starSentryHelper.y");
		}

		else if(c.equals("misc.jerryTimer.toggle")) {
			JerryTimer.jerryX = DataGetter.findInt("misc.jerryTimer.x");
			JerryTimer.jerryY = DataGetter.findInt("misc.jerryTimer.y");
			JerryTimer.jerryColor = DataGetter.findStr("misc.jerryTimer.textColor");
		}
	
		//DWARVEN TIMER
		else if(DwarvenTimer.dwarvenTimerToggle && c.equals("dwarven.eventTimer.toggle")) {
			DwarvenTimer.textColorID = DataGetter.findStr("dwarven.eventTimer.textColor");
			DwarvenTimer.posX = DataGetter.findInt("dwarven.eventTimer.x");
			DwarvenTimer.posY = DataGetter.findInt("dwarven.eventTimer.y");
		}


		else if(DwarvenGui.commTrackToggle && c.equals("dwarven.gui.commissionToggle")) {
			DwarvenGui.hideCommTitle = DataGetter.findBool("dwarven.gui.commissionHideWord");
			DwarvenGui.commissionID = DataGetter.findStr("dwarven.gui.commissionWordColor");
			DwarvenGui.commNums = DataGetter.findBool("dwarven.gui.commissionNumToggle");
			DwarvenGui.commNameID = DataGetter.findStr("dwarven.gui.commissionQuestNameColor");
			DwarvenGui.commTrackBarToggle = DataGetter.findBool("dwarven.gui.commissionBarToggle");
			DwarvenGui.posX = DataGetter.findInt("dwarven.gui.x");
			DwarvenGui.posY = DataGetter.findInt("dwarven.gui.y");
			DwarvenGui.commBorderColorID = DataGetter.findStr("dwarven.gui.commissionBorderColor");
			DwarvenGui.commYesColorID = DataGetter.findStr("dwarven.gui.commissionYesColor");
			DwarvenGui.commNoColorID = DataGetter.findStr("dwarven.gui.commissionNoColor");
			DwarvenGui.scale = DataGetter.findInt("dwarven.gui.size")/10d;
		}
		
		else if(DwarvenGui.fuelToggle && c.equals("dwarven.gui.fuelToggle")) {
			DwarvenGui.posX = DataGetter.findInt("dwarven.gui.x");
			DwarvenGui.posY = DataGetter.findInt("dwarven.gui.y");
			
			DwarvenGui.fuelGuiDrillFuel = DataGetter.findStr("dwarvenFuelDrillColor");
			DwarvenGui.fuelGuiPrimaryFull = DataGetter.findStr("dwarvenFuelGuiPrimeFullColor");
			DwarvenGui.fuelGuiSecondaryFull = DataGetter.findStr("dwarvenFuelGuiSecondFullColor");
			DwarvenGui.fuelGuiPrimaryHalf = DataGetter.findStr("dwarvenFuelGuiPrimeHalfColor");
			DwarvenGui.fuelGuiSecondaryHalf = DataGetter.findStr("dwarvenFuelGuiSecondHalfColor");
			DwarvenGui.fuelGuiPrimaryTen = DataGetter.findStr("dwarvenFuelGuiPrimeTenColor");
			DwarvenGui.fuelGuiSecondaryTen = DataGetter.findStr("dwarvenFuelGuiSecondTenColor");
			
		} else if(DwarvenGui.mithrilToggle && c.equals("dwarven.gui.powderToggle")) {
			DwarvenGui.posX = DataGetter.findInt("dwarven.gui.x");
			DwarvenGui.posY = DataGetter.findInt("dwarven.gui.y");
			DwarvenGui.mithrilTextColor = DataGetter.findStr("dwarven.gui.mithrilTextColor");
			DwarvenGui.mithrilCountColor = DataGetter.findStr("dwarven.gui.mithrilCountColor");
			DwarvenGui.gemTextColor = DataGetter.findStr("dwarven.gui.gemstoneTextColor");
			DwarvenGui.gemCountColor = DataGetter.findStr("dwarven.gui.gemstoneCountColor");
		}
		
		else if(DwarvenPickaxeTimer.pickTimerToggle && c.equals("dwarven.abilityTimer.toggle")) {
			DwarvenPickaxeTimer.hotmLevel = DataGetter.findInt("dwarven.user.hotmLevel");
			DwarvenPickaxeTimer.onlyWhenHolding = DataGetter.findBool("dwarven.abilityTimer.holdingToggle");
			DwarvenPickaxeTimer.circleRadius = DataGetter.findInt("dwarven.abilityTimer.radius");
			DwarvenPickaxeTimer.circleAccuracy = DataGetter.findInt("dwarven.abilityTimer.accuracy");
			DwarvenPickaxeTimer.circleActiveColor = DataGetter.findStr("dwarven.abilityTimer.activeColor");
			DwarvenPickaxeTimer.circleCdColor = DataGetter.findStr("dwarven.abilityTimer.cdColor");
			DwarvenPickaxeTimer.circleReadyColor = DataGetter.findStr("dwarven.abilityTimer.readyColor");
		}
		
		
		} catch(Exception e) {
			Utils.print("Failed to update "+Reference.MODID+" config settings");
		}
	}
	
	
	
	
	
	
}
