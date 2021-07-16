package com.cobble.sbp.core;

import com.cobble.sbp.gui.screen.dwarven.CrystalHollowsMap;
import com.cobble.sbp.utils.Reference;
import org.lwjgl.input.Keyboard;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.menu.settings.SettingMoveAll;
import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.gui.screen.misc.AbilityMessages;
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
		
		//BLANK SETTINGS
		// h.addSetting("", "", "", "", "", "", "");
		// h.addSetting("", "", "", "");
		
		//EXAMPLE SETTING
		//SettingMenu.addSetting("Example Setting", "misc", "dwarvenTimerToggle", "This is a test for settings", "string, int, boolean, color, moveGUI: 50;50, size, textColor", "String, Int, Boolean, Color, Move, Size, Text Color", "APIKey, pingDelay, modLaunchToggle, scrtBgColor, scrt, scrtSize, testColor, ", Reference.VERSION);
		//SETTING LIST
		
		//IMPORTANT
		//SettingMenu.addSetting("Mod Toggle", "misc", "modToggle", "Toggles the mod (REQUIRES RESTART)", "1.5.0.0");
		SettingMenu.addSetting("Only On Skyblock", "misc", "onlyOnSkyblock", "Makes it so the mod is only enabled on Skyblock", "1.5.0.0");
		
		//API
		SettingMenu.addSetting("Hypixel API Key", "api", "APIKeyToggle", "Toggles all API related features in the mod", "string", "APIKey", "APIKey", "1.5.0.0");
		
		//
		//DWARVEN MINES
		SettingMenu.addSetting("Dwarven Event Timer", "dwarven", "dwarvenTimerToggle", "Shows a timer on screen for how long until the next Dwarven Mines event. Only works after one event in that lobby happens. Will display 'N/A' until an event is found", "moveGUI: 55;25, textColor, boolean", "Move Location, Text Color, Play Ding sound when an event happens", "dwarvenTimer, dwarvenTimerTextColor, dwarvenTimerDing", "1.5.0.0");
		SettingMenu.addSetting("Pickaxe Ability Timer", "dwarven", "pickReminderToggle", "Show on screen how long until your pickaxe ability is available. "+Colors.AQUA+"Contrib: Phoube (circle) and Erymanthus (times)", "boolean, int, int, color, color, color", "Only Show When Holding Pickaxe, Circle Radius, Circle Accuracy, Active Color, Cooldown Color, Ready Color", "pickTimerHolding, pickTimerCircleRadius, pickTimerCircleAcc, pickTimerCircleActive, pickTimerCircleCd, pickTimerCircleReady", "1.5.3.0");
		SettingMenu.addSetting("Dwarven Commissions", "dwarven", "dwarvenTrackToggle", "Shows you your active Dwarven Mines comissions", "size, boolean, boolean, boolean, textColor, textColor, textColor, textColor, textColor", "Change Scale, Progress Bar, Numbers Instead of Percents, Hide 'Commission' Word, Commission Word Color, Quest Name Color, Bracket Color, Complete Color, Incomplete Color", "dwarvenGuiScale, dwarvenTrackBarToggle, dwarvenTrackNumsToggle, dwarvenHideCommissionWord, dwarvenTrackCommissionColor, dwarvenTrackQuestName, dwarvenTrackBorderColor, dwarvenTrackYesColor, dwarvenTrackNoColor", "1.5.4.0");
		SettingMenu.addSetting("Crystal Hollows Map", "dwarven", "crystalMap", "Shows you where you have been in every Crystal Hollows lobby you enter. Includes labels, coords, and waypoints!", "moveGUI: 100;100, size", "Move Location, Scale", "crystalMap, crystalMapSize", "1.5.4.0");
		SettingMenu.addSetting("Wind Compass GUI", "dwarven", "windCompass", "Puts the Wind Compass onto your screen during the 'Gone with the Wind' event in the Dwarven Caves.", "1.5.4.0");
		SettingMenu.addSetting("Puzzler Solver", "dwarven", "puzzlerSolver", "Puts particles on the block you need to mine for the Puzzler", "1.5.0.0");
		SettingMenu.addSetting("Fetchur Solver", "dwarven", "fetchurSolver", "Tells you the items you need to get for Fetchur each day.", "1.5.1.0");
		SettingMenu.addSetting("Show Drill Fuel", "dwarven", "dwarvenFuelToggle", "Shows your remaining drill fuel either on screen or on the drill durability.", "moveGUI: 128;128, textColor, textColor, textColor, textColor, textColor, textColor, textColor", "Move Location, 'Drill Fuel' Text Color, Primary Over Half Color, Secondary Over Half Color, Primary Below Half Color, Secondary Below Half Color, Primary Below Ten Color, Secondary Below Ten Color", "dwarvenGui, dwarvenFuelDrillColor, dwarvenFuelGuiPrimeFullColor, dwarvenFuelGuiSecondFullColor, dwarvenFuelGuiPrimeHalfColor, dwarvenFuelGuiSecondHalfColor, dwarvenFuelGuiPrimeTenColor, dwarvenFuelGuiSecondTenColor", "1.5.4.0");
		SettingMenu.addSetting("Mithril/Gemstone Powder Display", "dwarven", "dwarvenMithrilDisplay", "Displays your current mithril and gemstone powder count in the Dwarven Caves", "textColor, textColor, textColor, textColor", "'Mithril Powder:' Text Color, Mithril Powder Count Text Color, 'Gemstone Powder' Text Color, Gemstone Powder Count Text Color", "dwarvenMithrilTextColor, dwarvenMithrilCountColor, dwarvenGemstoneTextColor, dwarvenGemstoneCountColor", "1.5.4.0");
		SettingMenu.addSetting("Finished Commission Background", "dwarven", "dwarvenCommBgToggle", "Puts a background behind your completed commissions", "color", "Background Color", "dwarvenCommBgColor", "1.5.1.0");
		SettingMenu.addSetting("Auto Teleport to Garry", "dwarven, qol", "dwarvenTeleport", "Automatically teleports you 10 seconds before an event starts with the /garry command", "boolean, boolean, boolean, boolean", "Goblin Event Teleport, Raffle Event Teleport, Teleport Based on Commissions, Notify when about to be teleported", "dwarvenTeleportGoblin, dwarvenTeleportRaffle, dwarvenTeleportQuest, dwarvenTeleportNotif", "1.5.1.0");
		SettingMenu.addSetting("Star Sentry Helper", "dwarven", "starSentryHelper", "Shows the last known location of Star Sentries in your lobby. If it says 'Unknown', it hasn't found star sentries in your lobby.", "moveGUI: 100;12, boolean", "Move Location, Show it only with commission.", "starSentryHelper, starSentryOnlyWithComm", "1.5.4.0");
		SettingMenu.addSetting("Disable Mining Msgs", "dwarven, qol", "disablePickMsgs", "Disables pickaxe ability messages outside of mining islands", "1.5.0.0");

		//DUNGEONS
		SettingMenu.addSetting("Secrets Command", "command, dungeon", "scrtToggle", "This command is basically an in-game Dungeon Secret Guide. A huge thank you to them for letting me use their images and text. Use keybinds to switch to the next and previous secrets, as well as clear the image. Your keybinds are '"+Keyboard.getKeyName(KeyBindingHandler.prevSecret.getKeyCode())+"' for back, '"+Keyboard.getKeyName(KeyBindingHandler.nextSecret.getKeyCode())+"' for next, and '"+Keyboard.getKeyName(KeyBindingHandler.clearSecret.getKeyCode())+"' to clear.", "moveGUI: "+(SecretImage.imgWidth+2)+";"+(SecretImage.imgHeight+24)+", textColor, color, size", "Move Location, Text Color, Background Color, Image Size", "scrt, scrtTextColor, scrtBgColor, scrtSize", "1.5.2.0");
		SettingMenu.addSetting("Ability Message Condenser", "qol, dungeon", "abilityDamageToggle", "Gets rid of ability messages from chat and instead puts them onto a GUI element.", "moveGUI: 200;12, int", "Move Location, Seconds on Screen", "abilityDamage, abilityDamagePoof", "1.5.2.0");
		SettingMenu.addSetting("Automatic Secret Image Updates", "dungeon", "scrtAutoDownload", "This will automatically download secret images to update every time there are new images to download.", "1.5.2.0");
		
		//COMMANDS
		SettingMenu.addSetting("Dungeons Command", "command, dungeon, api", "dungeonsCommandToggle", "Toggles the command to resolve conflicting commands (REQUIRES RESTART)", "int", "Delay (ms)", "pingDelay", "1.2.0.0");
		SettingMenu.addSetting("Reparty Command", "command, dungeon", "repartyCommandToggle","Toggles the command to resolve conflicting commands (REQUIRES RESTART)", "int", "Delay (ms)", "pingDelay", "1.4.0.0");
		
				
		//QOL
		SettingMenu.addSetting("Mod Launch Counter", "misc", "modLaunchToggle", "Tracks how many times Minecraft has been launched with this mod", "1.5.0.0");
		//SettingMenu.addSetting("Client Side Music", "qol, music", "clientMusicToggle", "Plays Skyblock Note Block Music from your computer instead of from the server to prevent server lag interfering along with additional settings", "moveGUI: 200;30, moveGUI: 200;30", "Inventory Location, Pause Menu Location", "musicInv, musicEsc", "1.5.3.0");
		SettingMenu.addSetting("Grid Snapping", "misc, qol", "gridLockingToggle", "Applies Grid Snapping to the Edit GUI Locations Menu", "int", "Pixel Leniency", "gridLockingPx", "1.5.0.0");
		SettingMenu.addSetting("Additional NPC Dialogue", "misc", "npcDialogueToggle", "Sends additional Dialogue for some Skyblock NPCs. This can range from useful to just random text.", "1.5.0.0");
		SettingMenu.addSetting("Block Hoe Right Clicks", "misc, qol", "blockHoeClicks", "Blocks right clicks while holding hoes (This will prevent clicking things such as but are not limited to: NPCs, Chests, Minions, and Crafting tables while holding hoes)", "1.5.1.0");
		SettingMenu.addSetting("Jerry Timer", "misc", "jerryToggle", "Send a message in chat when you can get another Jerry during Mayor Jerry!", "moveGUI: 50;30, textColor", "Move Location, Text Color", "jerry, jerryTextColor", "1.5.3.0");
		SettingMenu.addSetting("Disable Specific Messages", "qol", "disableChatMessages", "Cancels specifed messages (choose which ones in the subsettings)", "boolean, boolean, boolean, boolean, boolean", "Werewolf Messages, Autopet Messages, Compact Messages, Blocks in Way Messages, Superboom Pickups", "werewolfToggle, disableAutopetMsgs, blocksInWayMsgs, compactToggle, disableSuperboomPickups", "1.5.1.0");
		SettingMenu.addSetting("Lock Quick Craft Slots", "qol", "toggleBlockedQuickCrafts", "Prevents specific items from being quick crafted. Add an item to the quick craft list by pressing the key: "+Keyboard.getKeyName(KeyBindingHandler.lockQuickCraft.getKeyCode())+".", "1.5.1.0");
		SettingMenu.addSetting("Combo Message Condenser", "qol", "comboMsgToggle", "Gets rid of combo messages from chat and instead puts them onto a GUI element.", "moveGUI: 200;12, int", "Move Location, Seconds on Screen", "comboMsg, comboMsgPoof", "1.5.2.0");
		SettingMenu.addSetting("Update Notifications", "qol, misc", "updateNotifs", "Toggle mod update notifications", "1.5.3.0");
		//SettingMenu.addSetting("Disable Invalid API Key Warnings", "misc", "APIWarning", "Gets rid of warnings if your API key is found to be invalid due to some weird issue I can't figure out.", "1.5.2.0");
		
		//MOVE GUI ELEMENTS
		SettingMoveAll.addGui("Dungeon Puzzles", "puzzle", "boxSolverToggle, iceSolverToggle", RenderGuiEvent.puzzleScale, RenderGuiEvent.puzzleScale);
		SettingMoveAll.addGui("Dwarven Mines Info", "dwarvenGui", "dwarvenTrackToggle, dwarvenFuelToggle", 108, DwarvenGui.currString.split(";").length*11);
		//SettingMoveAll.addGui("Dwarven Pickaxe Ability Cooldown", "pickTimer", "pickTimerGui", 32, 16);
		SettingMoveAll.addGui("Secret Images", "scrt", "scrtToggle", SecretImage.imgWidth+2, SecretImage.imgHeight+24+16);
		SettingMoveAll.addGui("Dwarven Event Timer", "dwarvenTimer", "dwarvenTimerToggle", 55, 16);
		SettingMoveAll.addGui("Ability Messages", "abilityDamage", "abilityDamageToggle", 200, 12);
		SettingMoveAll.addGui("Combo Messages", "comboMsg", "comboMsgToggle", 200, 12);
		SettingMoveAll.addGui("Jerry Timer", "jerry", "jerryToggle", 50, 30);
		SettingMoveAll.addGui("Star Sentry Helper", "starSentryHelper", "starSentryHelper", 100, 12);
		SettingMoveAll.addGui("Crystal Hollows Map", "crystalMap", "crystalMap", Math.round(100*CrystalHollowsMap.scale), Math.round(100*CrystalHollowsMap.scale));

		//CATEGORIES AND ACTUAL SEARCHES
		SettingMenu.addCategory("Categories:", "");
		SettingMenu.addCategory("Dwarven Caves", "dwarven");
		SettingMenu.addCategory("Dungeons", "dungeon");
		SettingMenu.addCategory("QOL", "qol");
		SettingMenu.addCategory("Commands", "command");
		SettingMenu.addCategory("API", "api");
		SettingMenu.addCategory("Misc.", "misc");
		
		//THEMES
		
		
		
		
	}
	
}
