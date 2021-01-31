package com.cobble.sbp.core;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.utils.Colors;

public class SettingList {

	
	/** SETTING ARGUMENTS:
	 * Setting Display Name,
	 * List of category IDs,
	 * Setting Toggle ID,
	 * Setting Description,
	 * 
	 * ADDITIONAL:
	 * List of all option types (string, int, boolean, moveGUI: width;height),
	 * List of all option names,
	 * List of all option config IDs,
	 * */
	public static void loadSettings() {
		SettingMenu h = new SettingMenu();
		
		//BLANK SETTINGS
		// h.addSetting("", "", "", "", "", "", "");
		// h.addSetting("", "", "", "");
		
		//EXAMPLE SETTING
		//h.addSetting("Example Setting Name", "api, toggle, command, dungeon", "modToggle", "This is a test for all settings", "string, int, boolean, color, moveGUI: "+RenderGuiEvent.puzzleScale+";"+RenderGuiEvent.puzzleScale+", size, list, textColor", "Example String Variable, Example Int Variable, Example Boolean Variable, Example Change Color, Example Move Location, Example Size, Example List, Example Text Color", "APIKey, pingDelay, modToggle, puzzleColor, puzzle, puzzleScale, commonDropList, dwarvenTimerTextColor");
			
		//SETTING LIST
		
		//IMPORTANT
		h.addSetting("Mod Toggle", "misc", "modToggle", "Toggles the mod (REQUIRES RESTART)");
		h.addSetting("Only On Skyblock", "misc", "onlyOnSkyblock", "Makes it so the mod is only enabled on Skyblock");
		
		//API
		h.addSetting("Hypixel API Key", "api", "APIKeyToggle", "Toggles all API related features in the mod", "string", "APIKey", "APIKey");
		
		
		//DWARVEN MINES
		h.addSetting("Dwarven Event Timer", "dwarven", "dwarvenTimerToggle", "Shows a timer on screen for how long until the next Dwarven Mines event. Only works after one event in that lobby happens. Will display 'N/A' until an event is found", "moveGUI: 55;25, textColor, boolean", "Move Location, Text Color, Play Ding sound when an event happens", "dwarvenTimer, dwarvenTimerTextColor, dwarvenTimerDing");
		h.addSetting("Pickaxe Ability Timer", "dwarven", "pickReminderToggle", "Show on screen how long until your pickaxe ability is available", "moveGUI: 50;16, textColor, textColor, boolean, boolean", "Move Location, Default Text Color, Active Text Color, Only Show in Dwarven Mines, Only Show When Holding Pickaxe", "pickTimer, pickTimerTextColor, pickActiveTimerTextColor, pickTimerDwarven, pickTimerHolding");
		h.addSetting("Dwarven Commissions", "dwarven", "dwarvenTrackToggle", "Shows you your active Dwarven Mines comissions", "moveGUI: 128;56, boolean, textColor, textColor, textColor", "Move Location, Progress Bar, Bracket Color, Complete Color, Incomplete Color", "dwarvenTrack, dwarvenTrackBarToggle, dwarvenTrackBorderColor, dwarvenTrackYesColor, dwarvenTrackNoColor");
		h.addSetting("Puzzler Solver", "dwarven", "puzzlerSolver", "Puts particles on the block you need to mine for the Puzzler");
		h.addSetting("Show Drill Fuel", "dwarven", "dwarvenFuelToggle", "Shows your remaining drill fuel on screen while you are holding a drill", "moveGUI: 140;24, boolean", "Move Location, Only show on Mining Islands", "dwarvenFuel, dwarvenOnlyFuel");
		h.addSetting("Disable Mining Msgs", "dwarven, qol", "disablePickMsgs", "Disables pickaxe ability messages outside of mining islands");
		
		
		
		//DUNGEONS
		h.addSetting("Box Puzzle Solver", "dungeon", "boxSolverToggle", "Stand on the edge of the platform in the center to put an image on-screen for how to solve the box puzzle", "moveGUI: "+RenderGuiEvent.puzzleScale+";"+RenderGuiEvent.puzzleScale+", size, color", "Move Location, Image Scale, Background Color", "puzzle, puzzleScale, puzzleColor");
		h.addSetting("Ice Fill Solver", "dungeon", "iceSolverToggle", "Puts an image on-screen that shows how to solve the ice fill puzzle", "moveGUI: "+RenderGuiEvent.puzzleScale+";"+RenderGuiEvent.puzzleScale+", size, color", "Move Location, Image Scale, Background Color", "puzzle, puzzleScale, puzzleColor");
		h.addSetting("Secrets Command", "command, dungeon", "scrtToggle", Colors.AQUA+"NOTE:"+Colors.RED+" REQUIRES EXTERNAL MOD "+Colors.AQUA+"DungeonSecrets"+Colors.RED+" TO WORK!"+Colors.WHITE+" This command is basically an in-game Dungeon Secret Guide. A huge thank you to them for letting me use their images and text.", "moveGUI: 150;100, textColor, color, boolean", "Move Location, Text Color, Background Color, Transparent Background", "scrt, scrtTextColor, scrtBgColor, scrtTransparent");
		
		//COMMANDS
		h.addSetting("Dungeons Command", "command, dungeon, api", "dungeonsCommandToggle", "Toggles the command to resolve conflicting commands (REQUIRES RESTART)", "int", "Delay (ms)", "pingDelay");
		h.addSetting("Reparty Command", "command, dungeon", "repartyCommandToggle","Toggles the command to resolve conflicting commands (REQUIRES RESTART)", "int", "Delay (ms)", "pingDelay");
		
				
		//QOL
		h.addSetting("Mod Launch Counter", "misc", "modLaunchToggle", "Tracks how many times Minecraft has been launched with this mod");
		h.addSetting("Grid Snapping", "misc", "gridLockingToggle", "Applies Grid Snapping to the Edit GUI Locations Menu", "int", "Pixel Leniency", "gridLockingPx");
		h.addSetting("Additional NPC Dialogue", "misc", "npcDialogueToggle", "Sends additional Dialogue for some Skyblock NPCs. This can range from useful to just random text.");
		h.addSetting("Block Common Drops", "qol", "disableCommonDrops", "Blocks certain drop notifications from chat", "list", "List of blocked drops", "commonDropList");
		h.addSetting("Disable Compact Msgs", "qol", "compactToggle", "Disable Compact enchant messages");
		h.addSetting("Jerry Timer", "misc", "jerryTimerToggle", "Send a message in chat when you can get another Jerry during Mayor Jerry!");
		
	}
	
}
