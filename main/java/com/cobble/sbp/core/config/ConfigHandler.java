package com.cobble.sbp.core.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.ConfigList;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.screen.PuzzleImage;
import com.cobble.sbp.gui.screen.dwarven.DwarvenDrillFuel;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenQuestTracker;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;



public class ConfigHandler {
	
	public static Object configObj = null;
	
	public static JSONObject obj = new JSONObject();
	public static JSONObject obj2 = new JSONObject();
	public static Boolean firstLaunch = false;
	
	public static void registerConfig() throws Exception {
		firstLaunch = Utils.invertBoolean(Utils.fileTest("config/"+Reference.MODID+"/main.cfg"));
		if(firstLaunch || Utils.readFile("config/"+Reference.MODID+"/main.cfg").equals(" ") || Utils.readFile("config/"+Reference.MODID+"/main.cfg").equals("")) {
			firstLaunch();
		}
		ConfigList.loadConfig();
		if(firstLaunch) {
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
		//obj.put(varName, var);
		try {
	        FileWriter file = new FileWriter("config/"+Reference.MODID+"/main.cfg");
	        obj.put(varName, var);
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
			DwarvenTimer.dwarvenTimerToggle = DataGetter.findBool("dwarvenTimerToggle");
			DwarvenQuestTracker.questTrackToggle = DataGetter.findBool("dwarvenTrackToggle");
			DwarvenDrillFuel.fuelToggle =  DataGetter.findBool("dwarvenFuelToggle");
			SettingMenu.modLaunchToggle = DataGetter.findBool("modLaunchToggle");
			DwarvenPickaxeTimer.pickTimerToggle = DataGetter.findBool("pickReminderToggle");
			
			
			
		if(c.equals(""));
		
		else if(c.equals("boxSolverToggle") || c.equals("iceSolverToggle")) {
			PuzzleImage.xCoord = DataGetter.findInt("puzzleX"); 
			PuzzleImage.yCoord = DataGetter.findInt("puzzleY"); 
			PuzzleImage.puzzleColor = DataGetter.findStr("puzzleColor")+"";
			RenderGuiEvent.puzzleScale=126*(DataGetter.findInt("puzzleScale")/10);
		}
		
	
		//DWARVEN TIMER
		else if(DwarvenTimer.dwarvenTimerToggle && c.equals("dwarvenTimerToggle")) {
			DwarvenTimer.dwarvenTimerDing = DataGetter.findBool("dwarvenTimerDing");
			DwarvenTimer.textColorID = DataGetter.findInt("dwarvenTimerTextColor");
			DwarvenTimer.timerX = DataGetter.findInt("dwarvenTimerX");
			DwarvenTimer.timerY = DataGetter.findInt("dwarvenTimerY");
		}
		
		else if(DwarvenQuestTracker.questTrackToggle && c.equals("dwarvenTrackToggle")) {
			DwarvenQuestTracker.questTrackBarToggle = DataGetter.findBool("dwarvenTrackBarToggle");
			DwarvenQuestTracker.questTrackX = DataGetter.findInt("dwarvenTrackX");
			DwarvenQuestTracker.questTrackY = DataGetter.findInt("dwarvenTrackY");
			DwarvenQuestTracker.borderColorID = DataGetter.findInt("dwarvenTrackBorderColor");
			DwarvenQuestTracker.yesColorID = DataGetter.findInt("dwarvenTrackYesColor");
			DwarvenQuestTracker.noColorID = DataGetter.findInt("dwarvenTrackNoColor");
		}
		
		else if(DwarvenDrillFuel.fuelToggle && c.equals("dwarvenFuelToggle")) {
			DwarvenDrillFuel.fuelX = DataGetter.findInt("dwarvenFuelX");
			DwarvenDrillFuel.fuelY = DataGetter.findInt("dwarvenFuelY");
		}
		
		else if(DwarvenPickaxeTimer.pickTimerToggle && c.equals("pickReminderToggle")) {
			DwarvenPickaxeTimer.pickTimerX = DataGetter.findInt("pickTimerX");
			DwarvenPickaxeTimer.pickTimerY = DataGetter.findInt("pickTimerY");		
			DwarvenPickaxeTimer.pickTimerColorID = DataGetter.findInt("pickTimerTextColor");
			DwarvenPickaxeTimer.pickActiveTimerColorID = DataGetter.findInt("pickActiveTimerTextColor");
			DwarvenPickaxeTimer.onlyInDwarven = DataGetter.findBool("pickTimerDwarven");
			DwarvenPickaxeTimer.onlyWhenHolding = DataGetter.findBool("pickTimerHolding");
		}
		
		
		
		
		
		else if(c.equals("all")) {
			PuzzleImage.xCoord = DataGetter.findInt("puzzleX"); 
			PuzzleImage.yCoord = DataGetter.findInt("puzzleY"); 
			PuzzleImage.puzzleColor = DataGetter.findStr("puzzleColor");
			RenderGuiEvent.puzzleScale=126*DataGetter.findInt("puzzleScale")/10;
			
			DwarvenTimer.dwarvenTimerDing = DataGetter.findBool("dwarvenTimerDing");
			DwarvenTimer.textColorID = DataGetter.findInt("dwarvenTimerTextColor");
			DwarvenTimer.timerX = DataGetter.findInt("dwarvenTimerX");
			DwarvenTimer.timerY = DataGetter.findInt("dwarvenTimerY");
			
			DwarvenQuestTracker.questTrackBarToggle = DataGetter.findBool("dwarvenTrackBarToggle");
			DwarvenQuestTracker.questTrackX = DataGetter.findInt("dwarvenTrackX");
			DwarvenQuestTracker.questTrackY = DataGetter.findInt("dwarvenTrackY");
			DwarvenQuestTracker.borderColorID = DataGetter.findInt("dwarvenTrackBorderColor");
			DwarvenQuestTracker.yesColorID = DataGetter.findInt("dwarvenTrackYesColor");
			DwarvenQuestTracker.noColorID = DataGetter.findInt("dwarvenTrackNoColor");
			
			DwarvenDrillFuel.fuelX = DataGetter.findInt("dwarvenFuelX");
			DwarvenDrillFuel.fuelY = DataGetter.findInt("dwarvenFuelY");
			
			DwarvenPickaxeTimer.pickTimerX = DataGetter.findInt("pickTimerX");
			DwarvenPickaxeTimer.pickTimerY = DataGetter.findInt("pickTimerY");		
			DwarvenPickaxeTimer.pickTimerColorID = DataGetter.findInt("pickTimerTextColor");
			DwarvenPickaxeTimer.pickActiveTimerColorID = DataGetter.findInt("pickActiveTimerTextColor");
			DwarvenPickaxeTimer.onlyInDwarven = DataGetter.findBool("pickTimerDwarven");
			DwarvenPickaxeTimer.onlyWhenHolding = DataGetter.findBool("pickTimerHolding");
		}
		
		
		} catch(Exception e) {
			Utils.print("Failed to update "+Reference.MODID+" config settings");
		}
	}
	
	
	
	
	
	
}
