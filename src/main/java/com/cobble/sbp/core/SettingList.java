package com.cobble.sbp.core;

import com.cobble.sbp.gui.menu.settings.MenuSetting;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.menu.settings.SettingMoveAll;
import com.cobble.sbp.gui.screen.dwarven.CrystalHollowsMap;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.handlers.KeyBindingHandler;
import com.cobble.sbp.utils.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
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
				.Str("API Key", "core.api.key").finish();

		//DWARVEN MINES
		new MenuSetting("Dwarven Event Timer", "dwarven", "dwarven.eventTimer.toggle", "Shows a timer on screen for how long until the next Dwarven Mines event. Only works after one event in that lobby happens. Will display 'N/A' until an event is found", "1.5.0.0")
				.MoveGui("dwarven.eventTimer", 55, 25)
				.TextColor("Text Color", "dwarven.eventTimer.textColor")
				.Bool("Ding Sound on Event", "dwarven.eventTimer.ding").finish();

		new MenuSetting("Pickaxe Ability Timer", "dwarven", "dwarven.abilityTimer.toggle", "Show on screen how long until your pickaxe ability is available. "+Colors.AQUA+"Contrib: Phoube (circle) and Erymanthus (times)", "1.5.3.0")
				.Bool("Only Show When Holding Pickaxe", "dwarven.abilityTimer.holdingToggle")
				.Int("Circle Radius", "dwarven.abilityTimer.radius", 4, 25)
				.Int("Circle Accuracy", "dwarven.abilityTimer.accuracy", 30, 240)
				.Color("Active Color", "dwarven.abilityTimer.activeColor")
				.Color("Cooldown Color", "dwarven.abilityTimer.cdColor")
				.Color("Ready Color", "dwarven.abilityTimer.readyColor").finish();

		new MenuSetting("Dwarven Commissions", "dwarven", "dwarven.gui.commissionToggle", "Shows you your active Dwarven Mines comissions", "1.5.4.0")
				.Size("Change Scale", "dwarven.gui.size")
				.Bool("Progress Bar", "dwarven.gui.commissionBarToggle")
				.Bool("Numbers Instead of Percents", "dwarven.gui.commissionNumToggle")
				.Bool("Hide 'Commission' Word", "dwarven.gui.commissionHideWord")
				.TextColor("Commission Word Color", "dwarven.gui.commissionWordColor")
				.TextColor("Quest Name Color", "dwarven.gui.commissionQuestNameColor")
				.TextColor("Bracket Color", "dwarven.gui.commissionBorderColor")
				.TextColor("Complete Color", "dwarven.gui.commissionYesColor")
				.TextColor("Incomplete Color", "dwarven.gui.commissionNoColor").finish();

		new MenuSetting("Crystal Hollows Map", "dwarven", "dwarven.crystalMap.toggle", "Shows you where you have been in every Crystal Hollows lobby you enter. Includes labels, coords, and waypoints!", "1.6.0.0")
				.MoveGui("dwarven.crystalMap", 100, 100)
				.Size("Scale", "dwarven.crystalMap.size")
				.Bool("Show Player Coords", "dwarven.crystalMap.coordsToggle")
				.Color("Arrow Color", "dwarven.crystalMap.arrowColor")
				.Bool("Show Player Head", "dwarven.crystalMap.headToggle")
				.TextColor("Text Color", "dwarven.crystalMap.textColor").finish();

		new MenuSetting("Show Closest Nucleus Location", "dwarven", "dwarven.nucleusEntrance.toggle", "Shows the closest entrance the nucleus in the Crystal Hollows", "1.6.0.0")
				.TextColor("Text Color", "dwarven.nucleusEntrance.textColor")
				.Bool("Show Beacon on Point", "dwarven.nucleusEntrance.beaconToggle").finish();

		//new MenuSetting("Auto Download /sendmap commands", "dwarven", "dwarven.sendMap.toggle", "If enabled, whenever somebody else using SBP uses /sendmap, your crystal hollows map will be updated to align with theirs.", "1.5.5.0").finish();

		new MenuSetting("Chest Helper", "dwarven", "dwarven.chestHelper.toggle", "Will help you open pick the locks on chests in the Crystal Hollows. "+Colors.AQUA+"Contrib: kat#1131 on Discord (coords)", "1.6.0.0")
				.Color("Color", "dwarven.chestHelper.color").finish();

		new MenuSetting("Forge Complete Reminder", "dwarven", "dwarven.forgeReminder.toggle", "Reminds you when you have items in the forge to pick up (even if you're not in the Dwarven Mines!)", "1.5.4.2").finish();
		new MenuSetting("Wind Compass GUI", "dwarven", "dwarven.windCompass.toggle", "Puts the Wind Compass onto your screen during the 'Gone with the Wind' event in the Dwarven Caves.", "1.5.4.0").finish();
		new MenuSetting("Lobby Day", "dwarven, misc", "dwarven.crystalDay.toggle", "Shows your current lobbies day on screen", "1.5.4.2")
				.MoveGui("dwarven.crystalDay", 30, 12)
				.TextColor("'Day' Color", "dwarven.crystalDay.textColor")
				.TextColor("Day Number Color", "dwarven.crystalDay.numColor")
				.Bool("Only show in Crystal Hollows", "dwarven.crystalDay.crystalOnly").finish();



		new MenuSetting("Puzzler Solver", "dwarven", "dwarven.puzzlerSolver.toggle", "Puts particles on the block you need to mine for the Puzzler", "1.5.0.0").finish();
		new MenuSetting("Fetchur Solver", "dwarven", "dwarven.fetchurSolver.toggle", "Tells you the items you need to get for Fetchur each day.", "1.5.1.0").finish();
		new MenuSetting("Show Drill Fuel", "dwarven", "dwarven.fuel.toggle", "Shows your remaining drill fuel either on screen or on the drill durability.", "1.5.4.0")
				.MoveGui("dwarven.gui", 128, 128)
				.TextColor("'Drill Fuel' Text Color", "dwarven.fuel.drillColor")
				.TextColor("Primary Over Half Color", "dwarven.fuel.guiPrimeTenColor")
				.TextColor("Secondary Over Half Color", "dwarven.fuel.guiSecondTenColor")
				.TextColor("Primary Below Half Color", "dwarven.fuel.guiPrimeHalfColor")
				.TextColor("Secondary Below Half Color", "dwarven.fuel.guiSecondHalfColor")
				.TextColor("Primary Below Ten Color", "dwarven.fuelguiPrimeFullColor")
				.TextColor("Secondary Below Ten Color", "dwarven.fuel.guiSecondFullColor").finish();

		new MenuSetting("Mithril/Gemstone Powder Display", "dwarven", "dwarven.gui.powderToggle", "Displays your current mithril and gemstone powder count in the Dwarven Caves", "1.5.4.0")
				.TextColor("'Mithril Powder:' Text Color", "dwarven.gui.mithrilTextColor")
				.TextColor("Mithril Powder Count Text Color", "dwarven.gui.mithrilCountColor")
				.TextColor("'Gemstone Powder' Text Color", "dwarven.gui.gemstoneTextColor")
				.TextColor("Gemstone Powder Count Text Color", "dwarven.gui.gemstoneCountColor").finish();

		new MenuSetting("Finished Commission Background", "dwarven", "dwarven.doneCommBg.toggle", "Puts a background behind your completed commissions", "1.5.1.0")
				.Color( "Background Color", "dwarven.doneCommBg.color").finish();

		new MenuSetting("Auto Teleport to Garry", "dwarven, qol", "dwarven.garryTP.toggle", "Automatically teleports you 10 seconds before an event starts with the /garry command",  "1.5.1.0")
				.Bool("Goblin Event Teleport", "dwarven.garryTP.goblinToggle")
				.Bool("Raffle Event Teleport", "dwarven.garryTP.raffleToggle")
				.Bool("Teleport Based on Commissions", "dwarven.garryTP.questToggle")
				.Bool("Notify when about to be teleported", "dwarven.garryTP.notifToggle").finish();

		new MenuSetting("Star Sentry Helper", "dwarven", "dwarven.starSentryHelper.toggle", "Shows the last known location of Star Sentries in your lobby. If it says 'Unknown', it hasn't found star sentries in your lobby.", "1.5.4.0")
				.MoveGui("dwarven.starSentryHelper", 100, 12)
				.Bool("Show it only with commission", "dwarven.starSentryHelper.commOnly").finish();

		new MenuSetting("Kuudra Shop Prices", "nether", "nether.kuudraShopPrices.toggle", "Shows you how many tokens you need for upgrades in the Kuudra fight", "1.7.0.0")
				.MoveGui("nether.kuudraShopPrices", 75, 75)
				.Bool("Show Gui Title", "nether.kuudraShopPrices.titleToggle")
				.TextColor("Gui Title Color", "nether.kuudraShopPrices.titleTextColor")
				.TextColor("Available Text Color", "nether.kuudraShopPrices.yesTextColor")
				.TextColor("Unavailable Text Color", "nether.kuudraShopPrices.noTextColor")
				.TextColor("Maxed Text Color", "nether.kuudraShopPrices.maxTextColor")
				.Bool("Token Display", "nether.kuudraShopPrices.tokenToggle")
				.TextColor("Token Color", "nether.kuudraShopPrices.tokenColor")
				.TextColor("Token Number Color", "nether.kuudraShopPrices.tokenNumColor").finish();

		new MenuSetting("Kuudra Ready Warning", "nether", "nether.kuudraReadyWarning.toggle", "Warns you if you aren't ready in the Kuudra fight", "1.7.0.0")
				.TextColor("Text Color", "nether.kuudraReadyWarning.textColor").finish();


		//COMMANDS
		new MenuSetting("Dungeons Command", "command, api", "command.dungeon.toggle", "Toggles the command to resolve conflicting commands (REQUIRES RESTART)", "1.2.0.0")
				.Int("Delay (ms)", "command.reparty.delay", 100, 30000).finish();
				
		//QOL
		new MenuSetting("Mod Launch Counter", "misc", "core.launchCounter.toggle", "Tracks how many times Minecraft has been launched with this mod", "1.5.0.0").finish();
		new MenuSetting("Grid Snapping", "misc, qol", "core.menu.gridLocking", "Applies Grid Snapping to the Edit GUI Locations Menu", "1.5.0.0")
				.Int("Pixel Leniency", "core.menu.gridLockingPx", 0, 10).finish();

		new MenuSetting("Event Time Preview", "misc", "misc.eventTimes.toggle", "Shows you what time the events in the event menu will be", "1.7.0.0")
				.Bool("Military Time", "misc.eventTimes.military").finish();
		new MenuSetting("Reaper Armor Enrage Display", "qol", "qol.enrageDisplay.toggle", "Puts a red tint on your screen while using the Reaper Armor Enrage ability along with an optional timer at the top of your screen to show the cooldown", "1.7.0.0")
				.MoveGui("qol.enrageDisplay", 50, 12)
				.Bool("Tint Toggle", "qol.enrageDisplay.tintToggle")
				.Bool("Cooldown Timer", "qol.enrageDisplay.textToggle")
				.TextColor("Unavailable Timer Color", "qol.enrageDisplay.textColor")
				.TextColor("Available Timer Color", "qol.enrageDisplay.availableTextColor").finish();

		new MenuSetting("Additional NPC Dialogue", "misc", "misc.npcDialogue.toggle", "Sends additional Dialogue for some Skyblock NPCs. This can range from useful to just random text.", "1.5.0.0").finish();
		new MenuSetting("Block Hoe Right Clicks", "misc, qol", "qol.blockHoeClicks.toggle", "Blocks right clicks while holding hoes (This will prevent clicking things such as but are not limited to: NPCs, Chests, Minions, and Crafting tables while holding hoes) "+Colors.AQUA+"Contribs: Erymanthus (helping transfer from old rick roll)", "1.5.1.0").finish();
		new MenuSetting("Jerry Timer", "misc", "misc.jerryTimer.toggle", "Send a message in chat when you can get another Jerry during Mayor Jerry!", "1.5.3.0")
				.MoveGui("misc.jerryTimer", 50, 30)
				.TextColor("Text Color", "misc.jerryTimer.textColor").finish();

		new MenuSetting("Lock Quick Craft Slots", "qol", "qol.lockQuickCrafts.toggle", "Prevents specific items from being quick crafted. Add an item to the quick craft list by pressing the key: "+Keyboard.getKeyName(KeyBindingHandler.lockQuickCraft.getKeyCode())+".", "1.5.1.0").finish();
		new MenuSetting("Update Notifications", "qol, misc", "misc.updateNotifs.toggle", "Toggle mod update notifications", "1.5.3.0").finish();

		//MOVE GUI ELEMENTS
		SettingMoveAll.addGui("Dwarven Mines Info", "dwarven.gui", "dwarven.gui.commissionToggle, dwarven.fuel.toggle, dwarven.gui.powderToggle", 108, DwarvenGui.currString.split(";").length*11);
		SettingMoveAll.addGui("Dwarven Event Timer", "dwarven.eventTimer", "dwarven.eventTimer.toggle", 55, 16);
		SettingMoveAll.addGui("Jerry Timer", "misc.jerryTimer", "misc.jerryTimer.toggle", 50, 30);
		SettingMoveAll.addGui("Star Sentry Helper", "dwarven.starSentryHelper", "dwarven.starSentryHelper.toggle", 100, 12);
		SettingMoveAll.addGui("Crystal Hollows Map", "dwarven.crystalMap", "dwarven.crystalMap.toggle", Math.round(100*CrystalHollowsMap.scale), Math.round(100*CrystalHollowsMap.scale));
		SettingMoveAll.addGui("Lobby Day", "dwarven.crystalDay", "dwarven.crystalDay.toggle", 30, 12);
		SettingMoveAll.addGui("Kuudra Shop Prices", "nether.kuudraShopPrices", "nether.kuudraShopPrices.toggle", 75, 75);
		SettingMoveAll.addGui("Enrage Display", "qol.enrageDisplay", "qol.enrageDisplay.toggle", 50, 12);

		//CATEGORIES AND ACTUAL SEARCHES
		SettingMenu.addCategory("Categories:", "");
		SettingMenu.addCategory("Dwarven Caves", "dwarven");
		SettingMenu.addCategory("Nether", "nether");
		SettingMenu.addCategory("QOL", "qol");
		SettingMenu.addCategory("Commands", "command");
		SettingMenu.addCategory("API", "api");
		SettingMenu.addCategory("Misc.", "misc");
		
		//THEMES
		
		
		
		
	}
	
}
