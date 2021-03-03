package com.cobble.sbp.core;

import org.lwjgl.input.Keyboard;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.menu.settings.SettingMoveAll;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.handlers.KeyBindingHandler;
import com.cobble.sbp.utils.Colors;

import net.minecraft.client.Minecraft;

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
		SettingMoveAll m = new SettingMoveAll();
		
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
		h.addSetting("Pickaxe Ability Timer", "dwarven", "pickReminderToggle", "Show on screen how long until your pickaxe ability is available. Contrib: Phoube (circle) and Erymanthus (times)", "boolean, boolean, moveGUI: 50;16, textColor, textColor, boolean, int, int, color, color, color", "Only Show When Holding Pickaxe, GUI Element, Move GUI Location, Default GUI Text Color, Active GUI Text Color, Circle Around Crosshair, Circle Radius, Circle Accuracy, Active Color, Cooldown Color, Ready Color", "pickTimerHolding, pickTimerGui, pickTimer, pickTimerTextColor, pickActiveTimerTextColor, pickTimerCircle, pickTimerCircleRadius, pickTimerCircleAcc, pickTimerCircleActive, pickTimerCircleCd, pickTimerCircleReady");
		h.addSetting("Dwarven Commissions", "dwarven", "dwarvenTrackToggle", "Shows you your active Dwarven Mines comissions", "moveGUI: 128;128, boolean, textColor, textColor, textColor, textColor, textColor", "Move Location, Progress Bar, Commission Word Color, Quest Name Color, Bracket Color, Complete Color, Incomplete Color", "dwarvenGui, dwarvenTrackBarToggle, dwarvenTrackCommissionColor, dwarvenTrackQuestName, dwarvenTrackBorderColor, dwarvenTrackYesColor, dwarvenTrackNoColor");
		h.addSetting("Puzzler Solver", "dwarven", "puzzlerSolver", "Puts particles on the block you need to mine for the Puzzler");
		h.addSetting("Fetchur Solver", "dwarven", "fetchurSolver", "Tells you the items you need to get for Fetchur each day.");
		h.addSetting("Show Drill Fuel", "dwarven", "dwarvenFuelToggle", "Shows your remaining drill fuel either on screen or on the drill durability.", "moveGUI: 128;128, boolean, boolean, boolean, textColor, textColor, textColor, textColor, textColor, textColor, textColor", "Move Location, Only show on Mining Islands, On Screen Gui, Durability, 'Drill Fuel' Text Color, Primary Over Half Color, Secondary Over Half Color, Primary Below Half Color, Secondary Below Half Color, Primary Below Ten Color, Secondary Below Ten Color", "dwarvenGui, dwarvenOnlyFuel, dwarvenFuelGui, dwarvenFuelDurr, dwarvenFuelDrillColor, dwarvenFuelGuiPrimeFullColor, dwarvenFuelGuiSecondFullColor, dwarvenFuelGuiPrimeHalfColor, dwarvenFuelGuiSecondHalfColor, dwarvenFuelGuiPrimeTenColor, dwarvenFuelGuiSecondTenColor");
		h.addSetting("Mithril Powder Display", "dwarven", "dwarvenMithrilDisplay", "Displays your current mithril powder count in the Dwarven Mines", "textColor, textColor", "'Mithril Powder:' Text Color, Mithril Powder Count Text Color", "dwarvenMithrilTextColor, dwarvenMithrilCountColor");
		h.addSetting("Finished Commission Background", "dwarven", "dwarvenCommBgToggle", "Puts a background behind your completed commissions", "color", "Background Color", "dwarvenCommBgColor");
		
		
		h.addSetting("Disable Mining Msgs", "dwarven, qol", "disablePickMsgs", "Disables pickaxe ability messages outside of mining islands");
		h.addSetting("Auto Teleport to Garry", "dwarven, qol", "dwarvenTeleport", "Automatically teleports you 10 seconds before an event starts with the /garry command", "boolean, boolean, boolean, boolean", "Goblin Event Teleport, Raffle Event Teleport, Teleport Based on Commissions, Notify when about to be teleported", "dwarvenTeleportGoblin, dwarvenTeleportRaffle, dwarvenTeleportQuest, dwarvenTeleportNotif");
		
		
		//DUNGEONS
		h.addSetting("Box Puzzle Solver", "dungeon", "boxSolverToggle", "Stand on the edge of the platform in the center to put an image on-screen for how to solve the box puzzle", "moveGUI: "+RenderGuiEvent.puzzleScale+";"+RenderGuiEvent.puzzleScale+", size, color", "Move Location, Image Scale, Background Color", "puzzle, puzzleScale, puzzleColor");
		h.addSetting("Ice Fill Solver", "dungeon", "iceSolverToggle", "Puts an image on-screen that shows how to solve the ice fill puzzle", "moveGUI: "+RenderGuiEvent.puzzleScale+";"+RenderGuiEvent.puzzleScale+", size, color", "Move Location, Image Scale, Background Color", "puzzle, puzzleScale, puzzleColor");
		h.addSetting("Secrets Command", "command, dungeon", "scrtToggle", Colors.AQUA+"NOTE:"+Colors.RED+" REQUIRES EXTERNAL MOD "+Colors.AQUA+"DungeonSecrets"+Colors.RED+" TO WORK!"+Colors.WHITE+" This command is basically an in-game Dungeon Secret Guide. A huge thank you to them for letting me use their images and text. Use keybinds to switch to the next and previous secrets, as well as clear the image. The default keybinds are 'B' for back, 'N' for next, and 'M' to clear.", "moveGUI: 150;100, textColor, color, size", "Move Location, Text Color, Background Color, Image Size", "scrt, scrtTextColor, scrtBgColor, scrtSize");
		
		//COMMANDS
		h.addSetting("Dungeons Command", "command, dungeon, api", "dungeonsCommandToggle", "Toggles the command to resolve conflicting commands (REQUIRES RESTART)", "int", "Delay (ms)", "pingDelay");
		h.addSetting("Reparty Command", "command, dungeon", "repartyCommandToggle","Toggles the command to resolve conflicting commands (REQUIRES RESTART)", "int", "Delay (ms)", "pingDelay");
		
				
		//QOL
		h.addSetting("Mod Launch Counter", "misc", "modLaunchToggle", "Tracks how many times Minecraft has been launched with this mod");
		h.addSetting("Grid Snapping", "misc, qol", "gridLockingToggle", "Applies Grid Snapping to the Edit GUI Locations Menu", "int", "Pixel Leniency", "gridLockingPx");
		h.addSetting("Additional NPC Dialogue", "misc", "npcDialogueToggle", "Sends additional Dialogue for some Skyblock NPCs. This can range from useful to just random text.");
		h.addSetting("Block Hoe Right Clicks", "misc, qol", "blockHoeClicks", "Blocks right clicks while holding hoes (This will prevent clicking things such as but are not limited to: NPCs, Chests, Minions, and Crafting tables while holding hoes)");
		h.addSetting("Jerry Timer", "misc", "jerryTimerToggle", "Send a message in chat when you can get another Jerry during Mayor Jerry!");
		h.addSetting("Disable Specific Messages", "qol", "disableChatMessages", "Cancels specifed messages (choose which ones in the subsettings)", "boolean, boolean, boolean, boolean, boolean, list", "Autopet Messages, Compact Messages, Blocks in Way Messages, Superboom Pickups, Common Drop Messages, Common Drop List", "disableAutopetMsgs, blocksInWayMsgs, compactToggle, disableSuperboomPickups, disableCommonDrops, commonDropList");
		h.addSetting("Lock Quick Craft Slots", "qol", "toggleBlockedQuickCrafts", "Prevents specific items from being quick crafted. Add an item to the quick craft list by pressing the key: "+Keyboard.getKeyName(KeyBindingHandler.lockQuickCraft.getKeyCode())+".");
		
		
		//MOVE GUI ELEMENTS
		m.addGui("Dungeon Puzzles", "puzzle", "boxSolverToggle, iceSolverToggle", RenderGuiEvent.puzzleScale, RenderGuiEvent.puzzleScale);
		m.addGui("Dwarven Mines Info", "dwarvenGui", "dwarvenTrackToggle, dwarvenFuelToggle", Minecraft.getMinecraft().fontRendererObj.getStringWidth("[||||||||||||||||||||||||||||||||||||||||||||||||||]"), DwarvenGui.currString.split(";").length*11);
		m.addGui("Dwarven Pickaxe Ability Cooldown", "pickTimer", "pickTimerGui", 32, 16);
		m.addGui("Secret Images", "scrt", "scrtToggle", 192, 101);
		m.addGui("Dwarven Event Timer", "dwarvenTimer", "dwarvenTimerToggle", 55, 16);
		
		
		//CATEGORIES AND ACTUAL SEARCHES
		h.addCategory("Categories", "");
		h.addCategory("Dwarven Mines", "dwarven");
		h.addCategory("Dungeons", "dungeon");
		h.addCategory("QOL", "qol");
		h.addCategory("Commands", "command");
		h.addCategory("API", "api");
		h.addCategory("Misc.", "misc");
		
		//THEMES
		
		
		
		
	}
	
}
