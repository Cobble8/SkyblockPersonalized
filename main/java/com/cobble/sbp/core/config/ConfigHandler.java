package com.cobble.sbp.core.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.ConfigList;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.gui.screen.dwarven.CrystalHollowsMap;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.gui.screen.misc.AbilityMessages;
import com.cobble.sbp.gui.screen.misc.ComboMessages;
import com.cobble.sbp.gui.screen.misc.JerryTimer;
import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;



public class ConfigHandler {
	
	public static Object configObj = null;

	public static Object oldConfig = null;
	
	public static JSONObject obj = new JSONObject();
	public static Boolean firstLaunch = false;
	
	public static ArrayList<String> forceDisabled = new ArrayList();
	public static ArrayList<String> forceEnabled = new ArrayList();
	
	public static void registerConfig() throws Exception {
		firstLaunch = !Utils.fileTest("config/"+Reference.MODID+"/main.cfg");
		if(firstLaunch || Utils.readFile("config/"+Reference.MODID+"/main.cfg").equals(" ") || Utils.readFile("config/"+Reference.MODID+"/main.cfg").equals("")) {
			firstLaunch();
		}
		ConfigHandler.oldConfig = Utils.readFile("config/"+Reference.MODID+"/main.cfg");
		ConfigList.loadConfig();
		if(firstLaunch) {
			Utils.print("FIRST LAUNCH!!!");
			ConfigHandler.resetConfig();
		}
		
	}
	
	private static void firstLaunch() throws IOException {
		SBP.firstLaunch=true;
		File configFolder = new File("config/"+Reference.MODID); configFolder.mkdirs();
		File config = new File("config/"+Reference.MODID+"/main.cfg");
		//File quests = new File("config/"+Reference.MODID+"/quests.cfg");
		try { if (config.createNewFile()) {Utils.print("The config file 'main' for "+Reference.NAME+" has been created at: "+config.getAbsolutePath());}} 
		catch (IOException e) {System.out.println("An error occurred.");}


		
		
	}
	
	public static void newObject(String varName, Object var) {
		Object val = var;
		boolean isBool = false;
		try { Boolean tmp = Boolean.parseBoolean(var+""); isBool=true; } catch(Exception ignored) {}
		if(isBool) {
			for (String s : forceDisabled) {
				if (varName.equals(s)) {
					val = false;
					break;
				}
			}
			for (String s : forceEnabled) {
				if (varName.equals(s)) {
					val = true;
					break;
				}
			}
		}
		
		//obj.put(varName, var);
		try {
	        FileWriter file = new FileWriter("config/"+Reference.MODID+"/main.cfg");
	        obj.put(varName, val);
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(obj.toJSONString());
            String prettyJsonString = gson.toJson(je);
            file.write(prettyJsonString);
            file.flush();
            file.close();
            configObj=obj;
            
	      } catch (IOException e) {
	        System.out.println("An error occurred from newObject().");
	        e.printStackTrace();
	      }
	}
	/*
	public static void setQuest(String varName, Boolean var) {
		//obj2.put(varName, var);
		try {
	        FileWriter file = new FileWriter("config/"+Reference.MODID+"/quests.cfg");
	        obj2.put(varName, var);
	        Gson gson = new GsonBuilder().setPrettyPrinting().create();
	        JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(obj2.toJSONString());
            String prettyJsonString = gson.toJson(je);
            
            file.write(prettyJsonString);
            file.flush();
            file.close();
            
	      } catch (IOException e) {
	        System.out.println("An error occurred from setAchieve().");
	        e.printStackTrace();
	      }
	}*/
	
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
		for(int i=0;i<ConfigList.c.size();i++) {
			if(!ConfigList.c.get(i).equals("modLaunches")) {
				ConfigHandler.newObject(ConfigList.c.get(i), ConfigList.d.get(i));
			}
			
			
		}
		DataGetter.updateConfig("main");
	}
	
	public static void updateConfig(String c) {
		try {
			CrystalHollowsMap.mapToggle = DataGetter.findBool("crystalMap");
			DwarvenTimer.dwarvenTimerToggle = DataGetter.findBool("dwarvenTimerToggle");
			DwarvenGui.commTrackToggle = DataGetter.findBool("dwarvenTrackToggle");
			DwarvenGui.fuelToggle =  DataGetter.findBool("dwarvenFuelToggle");
			SettingMenu.modLaunchToggle = DataGetter.findBool("modLaunchToggle");
			DwarvenPickaxeTimer.pickTimerToggle = DataGetter.findBool("pickReminderToggle");
			DwarvenGui.mithrilToggle = DataGetter.findBool("dwarvenMithrilDisplay");
			DwarvenGui.sentryToggle = DataGetter.findBool("starSentryHelper");
			DwarvenTimer.dwarvenTimerToggle = DataGetter.findBool("dwarvenTimerToggle");
			AbilityMessages.abilityMsgToggle = DataGetter.findBool("abilityDamageToggle");
			ComboMessages.abilityMsgToggle = DataGetter.findBool("comboMsgToggle");
			JerryTimer.jerryToggle = DataGetter.findBool("jerryToggle");


		if(c.equals("scrtToggle")) {
			SecretImage.imgX = DataGetter.findInt("scrtX");
			SecretImage.imgY = DataGetter.findInt("scrtY");
			SecretImage.bgColorID = DataGetter.findStr("scrtBgColor");
			SecretImage.secretSize = DataGetter.findInt("scrtSize");
			SecretImage.scrtColorID = DataGetter.findStr("scrtTextColor");
		}

		else if(c.equals("crystalMap")) {
			CrystalHollowsMap.mapX = DataGetter.findInt("crystalMapX");
			CrystalHollowsMap.mapY = DataGetter.findInt("crystalMapY");
			CrystalHollowsMap.scale = DataGetter.findInt("crystalMapSize")/10f;

		}

		else if(c.equals("starSentryHelper")) {
			DwarvenGui.sentryX = DataGetter.findInt("starSentryHelperX");
			DwarvenGui.sentryY = DataGetter.findInt("starSentryHelperY");
		}

		else if(c.equals("jerryToggle")) {
			JerryTimer.jerryX = DataGetter.findInt("jerryX");
			JerryTimer.jerryY = DataGetter.findInt("jerryY");
			JerryTimer.jerryColor = DataGetter.findStr("jerryTextColor");
		}
		
		//ABILITY MESSAGES
		else if(AbilityMessages.abilityMsgToggle && c.equals("abilityDamageToggle")) {
			AbilityMessages.x = DataGetter.findInt("abilityDamageX");
			AbilityMessages.y = DataGetter.findInt("abilityDamageY");
			AbilityMessages.delay = DataGetter.findInt("abilityDamagePoof");
		}
		
		//COMBO MESSAGES
		else if(ComboMessages.abilityMsgToggle && c.equals("comboMsgToggle")) {
			ComboMessages.x = DataGetter.findInt("comboMsgX");
			ComboMessages.y = DataGetter.findInt("comboMsgY");
			ComboMessages.delay = DataGetter.findInt("comboMsgPoof");
		}
	
		//DWARVEN TIMER
		else if(DwarvenTimer.dwarvenTimerToggle && c.equals("dwarvenTimerToggle")) {
			DwarvenTimer.dwarvenTimerDing = DataGetter.findBool("dwarvenTimerDing");
			DwarvenTimer.textColorID = DataGetter.findStr("dwarvenTimerTextColor");
			DwarvenTimer.posX = DataGetter.findInt("dwarvenTimerX");
			DwarvenTimer.posY = DataGetter.findInt("dwarvenTimerY");
		}


		else if(DwarvenGui.commTrackToggle && c.equals("dwarvenTrackToggle")) {
			DwarvenGui.commissionID = DataGetter.findStr("dwarvenTrackCommissionColor");
			DwarvenGui.commNums = DataGetter.findBool("dwarvenTrackNumsToggle");
			DwarvenGui.commNameID = DataGetter.findStr("dwarvenTrackQuestName");
			DwarvenGui.commTrackBarToggle = DataGetter.findBool("dwarvenTrackBarToggle");
			DwarvenGui.posX = DataGetter.findInt("dwarvenGuiX");
			DwarvenGui.posY = DataGetter.findInt("dwarvenGuiY");
			DwarvenGui.commBorderColorID = DataGetter.findStr("dwarvenTrackBorderColor");
			DwarvenGui.commYesColorID = DataGetter.findStr("dwarvenTrackYesColor");
			DwarvenGui.commNoColorID = DataGetter.findStr("dwarvenTrackNoColor");
			DwarvenGui.scale = DataGetter.findInt("dwarvenGuiScale")/10d;
		}
		
		else if(DwarvenGui.fuelToggle && c.equals("dwarvenFuelToggle")) {
			DwarvenGui.posX = DataGetter.findInt("dwarvenGuiX");
			DwarvenGui.posY = DataGetter.findInt("dwarvenGuiY");
			
			DwarvenGui.fuelGuiDrillFuel = DataGetter.findStr("dwarvenFuelDrillColor");
			DwarvenGui.fuelGuiPrimaryFull = DataGetter.findStr("dwarvenFuelGuiPrimeFullColor");
			DwarvenGui.fuelGuiSecondaryFull = DataGetter.findStr("dwarvenFuelGuiSecondFullColor");
			DwarvenGui.fuelGuiPrimaryHalf = DataGetter.findStr("dwarvenFuelGuiPrimeHalfColor");
			DwarvenGui.fuelGuiSecondaryHalf = DataGetter.findStr("dwarvenFuelGuiSecondHalfColor");
			DwarvenGui.fuelGuiPrimaryTen = DataGetter.findStr("dwarvenFuelGuiPrimeTenColor");
			DwarvenGui.fuelGuiSecondaryTen = DataGetter.findStr("dwarvenFuelGuiSecondTenColor");
			
		} else if(DwarvenGui.mithrilToggle && c.equals("dwarvenMithrilDisplay")) {
			DwarvenGui.posX = DataGetter.findInt("dwarvenGuiX");
			DwarvenGui.posY = DataGetter.findInt("dwarvenGuiY");
			DwarvenGui.mithrilTextColor = DataGetter.findStr("dwarvenMithrilTextColor");
			DwarvenGui.mithrilCountColor = DataGetter.findStr("dwarvenMithrilCountColor");
			DwarvenGui.gemTextColor = DataGetter.findStr("dwarvenGemstoneTextColor");
			DwarvenGui.gemCountColor = DataGetter.findStr("dwarvenGemstoneCountColor");
		}
		
		else if(DwarvenPickaxeTimer.pickTimerToggle && c.equals("pickReminderToggle")) {
			DwarvenPickaxeTimer.HotMLevel = DataGetter.findInt("dwarvenHOTMLevel");
			DwarvenPickaxeTimer.onlyWhenHolding = DataGetter.findBool("pickTimerHolding");
			DwarvenPickaxeTimer.circleRadius = DataGetter.findInt("pickTimerCircleRadius");
			DwarvenPickaxeTimer.circleAccuracy = DataGetter.findInt("pickTimerCircleAcc");
			DwarvenPickaxeTimer.circleActiveColor = DataGetter.findStr("pickTimerCircleActive");
			DwarvenPickaxeTimer.circleCdColor = DataGetter.findStr("pickTimerCircleCd");
			DwarvenPickaxeTimer.circleReadyColor = DataGetter.findStr("pickTimerCircleReady");
		}
		
		
		} catch(Exception e) {
			Utils.print("Failed to update "+Reference.MODID+" config settings");
		}
	}
	
	
	
	
	
	
}
