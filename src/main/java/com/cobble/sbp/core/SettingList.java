package com.cobble.sbp.core;

import com.cobble.sbp.gui.menu.settings.MenuSetting;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.menu.settings.SettingMoveAll;
import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.gui.screen.dwarven.CrystalHollowsMap;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.handlers.KeyBindingHandler;
import com.cobble.sbp.utils.Colors;
import org.lwjgl.input.Keyboard;

public class SettingList {

	
	/** SETTING ARGUMENTS:
	 * Setting Display Name,
	 * List of category IDs,
	 * Setting Toggle ID,
	 * Setting Description,
	 * 
	 * ADDITIONAL:
	 * List of all option types (string, int:min,max, boolean, moveGUI: width;height),
	 * List of all option names,
	 * List of all option config IDs,
	 * */
	public static void loadSettings() {
		

		//SETTING LIST
		
		//IMPORTANT
		new MenuSetting("Only On Skyblock", "misc", "core.skyblock.only", "Makes it so the mod is only enabled on Skyblock", "1.5.0.0").finish();


		//API
		new MenuSetting("Hypixel API Key", "api", "core.api.toggle", "Toggles all API related features in the mod", "1.5.0.0")
				.subsetting("API Key", "core.api.key", "string").finish();

		//DWARVEN MINES
		new MenuSetting("Dwarven Event Timer", "dwarven", "dwarven.eventTimer.toggle", "Shows a timer on screen for how long until the next Dwarven Mines event. Only works after one event in that lobby happens. Will display 'N/A' until an event is found", "1.5.0.0")
				.subsetting("Move Location", "dwarven.eventTimer", "moveGUI: 55;25")
				.subsetting("Text Color", "dwarven.eventTimer.textColor", "textColor")
				.subsetting("Ding Sound on Event", "dwarven.eventTimer.ding", "boolean").finish();

		new MenuSetting("Pickaxe Ability Timer", "dwarven", "dwarven.abilityTimer.toggle", "Show on screen how long until your pickaxe ability is available. "+Colors.AQUA+"Contrib: Phoube (circle) and Erymanthus (times)", "1.5.3.0")
				.subsetting("Only Show When Holding Pickaxe", "dwarven.abilityTimer.holdingToggle", "boolean")
				.subsetting("Circle Radius", "dwarven.abilityTimer.radius", "int:11,22")
				.subsetting("Circle Accuracy", "dwarven.abilityTimer.accuracy", "int:30,240")
				.subsetting("Active Color", "dwarven.abilityTimer.activeColor", "color")
				.subsetting("Cooldown Color", "dwarven.abilityTimer.cdColor", "color")
				.subsetting("Ready Color", "dwarven.abilityTimer.readyColor", "color").finish();

		new MenuSetting("Dwarven Commissions", "dwarven", "dwarven.gui.commissionToggle", "Shows you your active Dwarven Mines comissions", "1.5.4.0")
				.subsetting("Change Scale", "dwarven.gui.size", "size")
				.subsetting("Progress Bar", "dwarven.gui.commissionBarToggle", "boolean")
				.subsetting("Numbers Instead of Percents", "dwarven.gui.commissionNumToggle", "boolean")
				.subsetting("Hide 'Commission' Word", "dwarven.gui.commissionHideWord", "boolean")
				.subsetting("Commission Word Color", "dwarven.gui.commissionWordColor", "textColor")
				.subsetting("Quest Name Color", "dwarven.gui.commissionQuestNameColor", "textColor")
				.subsetting("Bracket Color", "dwarven.gui.commissionBorderColor", "textColor")
				.subsetting("Complete Color", "dwarven.gui.commissionYesColor", "textColor")
				.subsetting("Incomplete Color", "dwarven.gui.commissionNoColor", "textColor").finish();

		new MenuSetting("Crystal Hollows Map", "dwarven", "dwarven.crystalMap.toggle", "Shows you where you have been in every Crystal Hollows lobby you enter. Includes labels, coords, and waypoints!", "1.6.0.0")
				.subsetting("Move Location", "dwarven.crystalMap", "moveGUI: 100;100")
				.subsetting("Scale", "dwarven.crystalMap.size", "size")
				.subsetting("Show Player Coords", "dwarven.crystalMap.coordsToggle", "boolean")
				.subsetting("Show Player Head", "dwarven.crystalMap.headToggle", "boolean")
				.subsetting("Text Color", "dwarven.crystalMap.textColor", "textColor").finish();

		new MenuSetting("Show Closest Nucleus Location", "dwarven", "dwarven.nucleusEntrance.toggle", "Shows the closest entrance the nucleus in the Crystal Hollows", "1.6.0.0")
				.subsetting("Text Color", "dwarven.nucleusEntrance.textColor", "textColor")
				.subsetting("Show Beacon on Point", "dwarven.nucleusEntrance.beaconToggle", "boolean").finish();

		//new MenuSetting("Auto Download /sendmap commands", "dwarven", "dwarven.sendMap.toggle", "If enabled, whenever somebody else using sbp uses /sendmap, your crystal hollows map will be updated to align with theirs.", "1.5.5.0").finish();

		new MenuSetting("Chest Helper", "dwarven", "dwarven.chestHelper.toggle", "Will help you open pick the locks on chests in the Crystal Hollows. "+Colors.AQUA+"Contrib: kat#1131 on Discord (coords)", "1.6.0.0")
				.subsetting("Color", "dwarven.chestHelper.color", "color").finish();

		new MenuSetting("Forge Complete Reminder", "dwarven", "dwarven.forgeReminder.toggle", "Reminds you when you have items in the forge to pick up (even if you're not in the Dwarven Mines!)", "1.5.4.2").finish();
		new MenuSetting("Wind Compass GUI", "dwarven", "dwarven.windCompass.toggle", "Puts the Wind Compass onto your screen during the 'Gone with the Wind' event in the Dwarven Caves.", "1.5.4.0").finish();
		new MenuSetting("Lobby Day", "dwarven, misc", "dwarven.crystalDay.toggle", "Shows your current lobbies day on screen", "1.5.4.2")
				.subsetting("Move Location", "dwarven.crystalDay","moveGUI: 30;12")
				.subsetting("'Day' Color", "dwarven.crystalDay.textColor", "textColor")
				.subsetting("Day Number Color", "dwarven.crystalDay.numColor", "textColor")
				.subsetting("Only show in Crystal Hollows", "dwarven.crystalDay.crystalOnly", "boolean").finish();



		new MenuSetting("Puzzler Solver", "dwarven", "dwarven.puzzlerSolver.toggle", "Puts particles on the block you need to mine for the Puzzler", "1.5.0.0").finish();
		new MenuSetting("Fetchur Solver", "dwarven", "dwarven.fetchurSolver.toggle", "Tells you the items you need to get for Fetchur each day.", "1.5.1.0").finish();
		new MenuSetting("Show Drill Fuel", "dwarven", "dwarven.fuel.toggle", "Shows your remaining drill fuel either on screen or on the drill durability.", "1.5.4.0")
				.subsetting("Move Location", "dwarven.gui", "moveGUI: 128;128")
				.subsetting("'Drill Fuel' Text Color", "dwarven.fuel.drillColor", "textColor")
				.subsetting("Primary Over Half Color", "dwarven.fuel.guiPrimeTenColor", "textColor")
				.subsetting("Secondary Over Half Color", "dwarven.fuel.guiSecondTenColor", "textColor")
				.subsetting("Primary Below Half Color", "dwarven.fuel.guiPrimeHalfColor", "textColor")
				.subsetting("Secondary Below Half Color", "dwarven.fuel.guiSecondHalfColor", "textColor")
				.subsetting("Primary Below Ten Color", "dwarven.fuelguiPrimeFullColor", "textColor")
				.subsetting("Secondary Below Ten Color", "dwarven.fuel.guiSecondFullColor", "textColor").finish();

		new MenuSetting("Mithril/Gemstone Powder Display", "dwarven", "dwarven.gui.powderToggle", "Displays your current mithril and gemstone powder count in the Dwarven Caves", "1.5.4.0")
				.subsetting("'Mithril Powder:' Text Color", "dwarven.gui.mithrilTextColor", "textColor")
				.subsetting("Mithril Powder Count Text Color", "dwarven.gui.mithrilCountColor", "textColor")
				.subsetting("'Gemstone Powder' Text Color", "dwarven.gui.gemstoneTextColor", "textColor")
				.subsetting("Gemstone Powder Count Text Color", "dwarven.gui.gemstoneCountColor", "textColor").finish();

		new MenuSetting("Finished Commission Background", "dwarven", "dwarven.doneCommBg.toggle", "Puts a background behind your completed commissions", "1.5.1.0")
				.subsetting( "Background Color", "dwarven.doneCommBg.color", "color").finish();

		new MenuSetting("Auto Teleport to Garry", "dwarven, qol", "dwarven.garryTP.toggle", "Automatically teleports you 10 seconds before an event starts with the /garry command",  "1.5.1.0")
				.subsetting("Goblin Event Teleport", "dwarven.garryTP.goblinToggle", "boolean")
				.subsetting("Raffle Event Teleport", "dwarven.garryTP.raffleToggle", "boolean")
				.subsetting("Teleport Based on Commissions", "dwarven.garryTP.questToggle", "boolean")
				.subsetting("Notify when about to be teleported", "dwarven.garryTP.notifToggle", "boolean").finish();

		new MenuSetting("Star Sentry Helper", "dwarven", "dwarven.starSentryHelper.toggle", "Shows the last known location of Star Sentries in your lobby. If it says 'Unknown', it hasn't found star sentries in your lobby.", "1.5.4.0")
				.subsetting("Move Location", "dwarven.starSentryHelper", "moveGUI: 100;12")
				.subsetting("Show it only with commission", "dwarven.starSentryHelper.commOnly", "boolean").finish();


		//DUNGEONS
		new MenuSetting("Secrets Command", "command, dungeon", "dungeon.secretImage.toggle", "This command is basically an in-game Dungeon Secret Guide. A huge thank you to them for letting me use their images and text. Use keybinds to switch to the next and previous secrets, as well as clear the image. Your keybinds are '"+Keyboard.getKeyName(KeyBindingHandler.prevSecret.getKeyCode())+"' for back, '"+Keyboard.getKeyName(KeyBindingHandler.nextSecret.getKeyCode())+"' for next, and '"+Keyboard.getKeyName(KeyBindingHandler.clearSecret.getKeyCode())+"' to clear.", "1.5.2.0")
				.subsetting("Move Location", "dungeon.secretImage", "moveGUI: "+(SecretImage.imgWidth+2)+";"+(SecretImage.imgHeight+24))
				.subsetting("Text Color", "dungeon.secretImage.textColor", "textColor")
				.subsetting("Background Color", "dungeon.secretImage.bgColor", "color")
				.subsetting("Image Scale", "dungeon.secretImage.size", "size").finish();

		new MenuSetting("Automatic Secret Image Updates", "dungeon", "dungeon.secretImage.autoDownload", "This will automatically download secret images to update every time there are new images to download.", "1.5.2.0").finish();
		
		//COMMANDS
		new MenuSetting("Dungeons Command", "command, dungeon, api", "command.dungeon.toggle", "Toggles the command to resolve conflicting commands (REQUIRES RESTART)", "1.2.0.0")
				.subsetting("Delay (ms)", "command.reparty.delay", "int:100,30000").finish();
				
		//QOL
		new MenuSetting("Mod Launch Counter", "misc", "core.launchCounter.toggle", "Tracks how many times Minecraft has been launched with this mod", "1.5.0.0").finish();
		new MenuSetting("Grid Snapping", "misc, qol", "core.menu.gridLocking", "Applies Grid Snapping to the Edit GUI Locations Menu", "1.5.0.0")
				.subsetting("Pixel Leniency", "core.menu.gridLockingPx", "int:0,10").finish();

		new MenuSetting("Additional NPC Dialogue", "misc", "misc.npcDialogue.toggle", "Sends additional Dialogue for some Skyblock NPCs. This can range from useful to just random text.", "1.5.0.0").finish();
		new MenuSetting("Block Hoe Right Clicks", "misc, qol", "qol.blockHoeClicks.toggle", "Blocks right clicks while holding hoes (This will prevent clicking things such as but are not limited to: NPCs, Chests, Minions, and Crafting tables while holding hoes)", "1.5.1.0").finish();
		new MenuSetting("Jerry Timer", "misc", "misc.jerryTimer.toggle", "Send a message in chat when you can get another Jerry during Mayor Jerry!", "1.5.3.0")
				.subsetting("Move Location", "misc.jerryTimer", "moveGUI: 50;30")
				.subsetting("Text Color", "misc.jerryTimer.textColor", "textColor").finish();

		new MenuSetting("Lock Quick Craft Slots", "qol", "qol.lockQuickCrafts.toggle", "Prevents specific items from being quick crafted. Add an item to the quick craft list by pressing the key: "+Keyboard.getKeyName(KeyBindingHandler.lockQuickCraft.getKeyCode())+".", "1.5.1.0").finish();
		new MenuSetting("Update Notifications", "qol, misc", "misc.updateNotifs.toggle", "Toggle mod update notifications", "1.5.3.0").finish();

		//MOVE GUI ELEMENTS
		SettingMoveAll.addGui("Dwarven Mines Info", "dwarven.gui", "dwarven.gui.commissionToggle, dwarven.fuel.toggle, dwarven.gui.powderToggle", 108, DwarvenGui.currString.split(";").length*11);
		SettingMoveAll.addGui("Secret Images", "dungeon.secretImage", "dungeon.secretImage.toggle", SecretImage.imgWidth+2, SecretImage.imgHeight+24+16);
		SettingMoveAll.addGui("Dwarven Event Timer", "dwarven.eventTimer", "dwarven.eventTimer.toggle", 55, 16);
		SettingMoveAll.addGui("Jerry Timer", "misc.jerryTimer", "misc.jerryTimer.toggle", 50, 30);
		SettingMoveAll.addGui("Star Sentry Helper", "dwarven.starSentryHelper", "dwarven.starSentryHelper.toggle", 100, 12);
		SettingMoveAll.addGui("Crystal Hollows Map", "dwarven.crystalMap", "dwarven.crystalMap.toggle", Math.round(100*CrystalHollowsMap.scale), Math.round(100*CrystalHollowsMap.scale));
		SettingMoveAll.addGui("Lobby Day", "dwarven.crystalDay", "dwarven.crystalDay.toggle", 30, 12);


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
