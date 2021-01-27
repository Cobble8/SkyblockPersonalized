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
	
	
	
	public static JSONObject obj = new JSONObject();
	public static JSONObject obj2 = new JSONObject();
	public static Boolean firstLaunch = false;
	
	public static void registerConfig() throws IOException {
		firstLaunch = Utils.invertBoolean(Utils.fileTest("config/"+Reference.MODID+"/main.cfg"));
		if(firstLaunch || Utils.readFile("config/"+Reference.MODID+"/main.cfg").equals(" ") || Utils.readFile("config/"+Reference.MODID+"/main.cfg").equals("")) {
			firstLaunch();
		}
		ConfigList.loadConfig();
	}
	
	private static void firstLaunch() throws IOException {
		SBP.firstLaunch=true;
		File configFolder = new File("config/"+Reference.MODID); configFolder.mkdirs();
		File config = new File("config/"+Reference.MODID+"/main.cfg");
		File quests = new File("config/"+Reference.MODID+"/quests.cfg");
		try { if (config.createNewFile()) {Utils.print("The config file 'main' for "+Reference.NAME+" has been created at: "+config.getAbsolutePath());}} 
		catch (IOException e) {System.out.println("An error occurred.");}
		try { if (quests.createNewFile()) {Utils.print("The config file 'quests' for "+Reference.NAME+" has been created at: "+quests.getAbsolutePath());}} 
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
            
	      } catch (IOException e) {
	        System.out.println("An error occurred from newObject().");
	        e.printStackTrace();
	      }
	}
	
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
		for(int i=0;i<ConfigList.c.size();i++) {
			if(!ConfigList.c.get(i).equals("modLaunches")) {
				ConfigHandler.newObject(ConfigList.c.get(i), ConfigList.d.get(i));
			}
			
			
		}
	}
	
	public static void updateConfig(String c) {
		try {
			DwarvenTimer.dwarvenTimerToggle = (Boolean) DataGetter.find("dwarvenTimerToggle");
			DwarvenQuestTracker.questTrackToggle = (Boolean) DataGetter.find("dwarvenTrackToggle");
			DwarvenDrillFuel.fuelToggle = (Boolean) DataGetter.find("dwarvenFuelToggle");
			SettingMenu.modLaunchToggle = (Boolean) DataGetter.find("modLaunchToggle");
			DwarvenPickaxeTimer.pickTimerToggle = (Boolean) DataGetter.find("pickTimerToggle");
			
			
			
		if(c.equals(""));
		
		else if(c.equals("boxSolverToggle") || c.equals("iceSolverToggle")) {
			PuzzleImage.xCoord = Integer.parseInt(DataGetter.find("puzzleX")+""); 
			PuzzleImage.yCoord = Integer.parseInt(DataGetter.find("puzzleY")+""); 
			PuzzleImage.puzzleColor = DataGetter.find("puzzleColor")+"";
			RenderGuiEvent.puzzleScale=126*(Integer.parseInt(DataGetter.find("puzzleScale")+""))/10;
		}
		
	
		//DWARVEN TIMER
		else if(DwarvenTimer.dwarvenTimerToggle && c.equals("dwarvenTimerToggle")) {
			DwarvenTimer.dwarvenTimerDing = (Boolean) DataGetter.find("dwarvenTimerDing");
			DwarvenTimer.textColorID = Integer.parseInt(DataGetter.find("dwarvenTimerTextColor")+"");
			DwarvenTimer.timerX = Integer.parseInt(DataGetter.find("dwarvenTimerX")+"");
			DwarvenTimer.timerY = Integer.parseInt(DataGetter.find("dwarvenTimerY")+"");
		}
		
		else if(DwarvenQuestTracker.questTrackToggle && c.equals("dwarvenTrackToggle")) {
			DwarvenQuestTracker.questTrackBarToggle = (Boolean) DataGetter.find("dwarvenTrackBarToggle");
			DwarvenQuestTracker.questTrackX = Integer.parseInt(DataGetter.find("dwarvenTrackX")+"");
			DwarvenQuestTracker.questTrackY = Integer.parseInt(DataGetter.find("dwarvenTrackY")+"");
			DwarvenQuestTracker.borderColorID = Integer.parseInt(DataGetter.find("dwarvenTrackBorderColor")+"");
			DwarvenQuestTracker.yesColorID = Integer.parseInt(DataGetter.find("dwarvenTrackYesColor")+"");
			DwarvenQuestTracker.noColorID = Integer.parseInt(DataGetter.find("dwarvenTrackNoColor")+"");
		}
		
		else if(DwarvenDrillFuel.fuelToggle && c.equals("dwarvenFuelToggle")) {
			DwarvenDrillFuel.fuelX = Integer.parseInt(DataGetter.find("dwarvenFuelX")+"");
			DwarvenDrillFuel.fuelY = Integer.parseInt(DataGetter.find("dwarvenFuelY")+"");
		}
		
		else if(DwarvenPickaxeTimer.pickTimerToggle && c.equals("pickReminderToggle")) {
			DwarvenPickaxeTimer.pickTimerX = Integer.parseInt(DataGetter.find("pickTimerX")+"");
			DwarvenPickaxeTimer.pickTimerY = Integer.parseInt(DataGetter.find("pickTimerY")+"");		
			DwarvenPickaxeTimer.pickTimerColorID = Integer.parseInt(DataGetter.find("pickTimerTextColor")+"");
			DwarvenPickaxeTimer.pickActiveTimerColorID = Integer.parseInt(DataGetter.find("pickActiveTimerTextColor")+"");
			DwarvenPickaxeTimer.onlyInDwarven = (Boolean) DataGetter.find("pickTimerDwarven");
			DwarvenPickaxeTimer.onlyWhenHolding = (Boolean) DataGetter.find("pickTimerHolding");
			DwarvenPickaxeTimer.barColor = DataGetter.find("pickTimerBarColor")+"";
		}
		
		
		
		
		
		else if(c.equals("all")) {
			PuzzleImage.xCoord = Integer.parseInt(DataGetter.find("puzzleX")+""); 
			PuzzleImage.yCoord = Integer.parseInt(DataGetter.find("puzzleY")+""); 
			PuzzleImage.puzzleColor = DataGetter.find("puzzleColor")+"";
			RenderGuiEvent.puzzleScale=126*(Integer.parseInt(DataGetter.find("puzzleScale")+""))/10;
			
			DwarvenTimer.dwarvenTimerDing = (Boolean) DataGetter.find("dwarvenTimerDing");
			DwarvenTimer.textColorID = Integer.parseInt(DataGetter.find("dwarvenTimerTextColor")+"");
			DwarvenTimer.timerX = Integer.parseInt(DataGetter.find("dwarvenTimerX")+"");
			DwarvenTimer.timerY = Integer.parseInt(DataGetter.find("dwarvenTimerY")+"");
			
			DwarvenQuestTracker.questTrackBarToggle = (Boolean) DataGetter.find("dwarvenTrackBarToggle");
			DwarvenQuestTracker.questTrackX = Integer.parseInt(DataGetter.find("dwarvenTrackX")+"");
			DwarvenQuestTracker.questTrackY = Integer.parseInt(DataGetter.find("dwarvenTrackY")+"");
			DwarvenQuestTracker.borderColorID = Integer.parseInt(DataGetter.find("dwarvenTrackBorderColor")+"");
			DwarvenQuestTracker.yesColorID = Integer.parseInt(DataGetter.find("dwarvenTrackYesColor")+"");
			DwarvenQuestTracker.noColorID = Integer.parseInt(DataGetter.find("dwarvenTrackNoColor")+"");
			
			DwarvenDrillFuel.fuelX = Integer.parseInt(DataGetter.find("dwarvenFuelX")+"");
			DwarvenDrillFuel.fuelY = Integer.parseInt(DataGetter.find("dwarvenFuelY")+"");
			
			DwarvenPickaxeTimer.pickTimerX = Integer.parseInt(DataGetter.find("pickTimerX")+"");
			DwarvenPickaxeTimer.pickTimerY = Integer.parseInt(DataGetter.find("pickTimerY")+"");		
			DwarvenPickaxeTimer.pickTimerColorID = Integer.parseInt(DataGetter.find("pickTimerTextColor")+"");
			DwarvenPickaxeTimer.pickActiveTimerColorID = Integer.parseInt(DataGetter.find("pickActiveTimerTextColor")+"");
			DwarvenPickaxeTimer.onlyInDwarven = (Boolean) DataGetter.find("pickTimerDwarven");
			DwarvenPickaxeTimer.onlyWhenHolding = (Boolean) DataGetter.find("pickTimerHolding");
			DwarvenPickaxeTimer.barColor = DataGetter.find("pickTimerBarColor")+"";
		}
		
		
		} catch(Exception e) {
			Utils.print("Failed to update "+Reference.MODID+" config settings");
		}
	}
	
	
	
	
	
	
}
