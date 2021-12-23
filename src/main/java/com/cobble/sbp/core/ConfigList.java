package com.cobble.sbp.core;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.simplejson.parser.JSONParser;
import com.cobble.sbp.simplejson.parser.ParseException;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class ConfigList {
	public static ArrayList<String> c = new ArrayList();
	public static ArrayList<Object> d = new ArrayList();

	public static void l(String key, Object defaultValue) {
		c.add(key);
		d.add(defaultValue);
	}
	
	
	public static void loadConfig() throws Exception {

/*
		checkForMissingValues();*/

		
		l("command.reparty.delay", "pingDelay");
		l("command.dungeons.toggle", true);

		l("core.api.toggle", true);
		l("core.api.key", "NOT_SET");
		l("core.launchCounter.toggle", true);
		l("core.launchCounter.count", 0);
		l("core.menu.theme", 0);
		l("core.skyblock.only", true);
		l("core.menu.gridLocking", true);
		l("core.menu.gridLockingPx", 3);
		l("core.mod.textStyle", 0);
		l("core.mod.chromaSpeed", 4);
		l("core.easterEgg.coco", false);



		l("game.score.record", "d0");


		l("music.inv.x", SBP.width/2-100);
		l("music.inv.y", 10);
		l("music.esc.x", SBP.width/2-100);
		l("music.esc.y", 10);
		l("music.all.volume", 0.5);
		l("music.all.toggle", true);

		l("dungeon.secretImage.vers", 0);
		l("dungeon.secretImage.x", 0);
		l("dungeon.secretImage.y", 0);
		l("dungeon.secretImage.size", 10);
		l("dungeon.secretImage.bgColor", "0.0;0.0;0.0");
		l("dungeon.secretImage.textColor", "&f");
		l("dungeon.secretImage.toggle", true);
		l("dungeon.secretImage.autoDownload", false);
		l("dungeon.secretImage.autoRemove", true);




		l("dwarven.user.hotmLevel", 0);

		l("dwarven.gui.x", 0);
		l("dwarven.gui.y", 0);
		l("dwarven.gui.powderToggle", true);
		l("dwarven.gui.mithrilTextColor", "&f");
		l("dwarven.gui.mithrilCountColor", "&2");
		l("dwarven.gui.gemstoneTextColor", "&f");
		l("dwarven.gui.gemstoneCountColor", "&d");
		l("dwarven.gui.commissionToggle", true);
		l("dwarven.gui.commissionNumToggle", true);
		l("dwarven.gui.commissionWordColor", "&9&l");
		l("dwarven.gui.commissionQuestNameColor", "&f");
		l("dwarven.gui.commissionBarToggle", true);
		l("dwarven.gui.commissionBorderColor", "&2");
		l("dwarven.gui.commissionYesColor", "&a");
		l("dwarven.gui.commissionNoColor", "&8");
		l("dwarven.gui.commissionHideWord", false);
		l("dwarven.gui.size", 10);


		l("dwarven.eventTimer.toggle", true);
		l("dwarven.eventTimer.textColor", "&a");
		l("dwarven.eventTimer.ding", true);
		l("dwarven.eventTimer.x", 0);
		l("dwarven.eventTimer.y", 0);

		l("dwarven.crystalDay.toggle", true);
		l("dwarven.crystalDay.textColor", "&f");
		l("dwarven.crystalDay.numColor", "&f");
		l("dwarven.crystalDay.crystalOnly", true);
		l("dwarven.crystalDay.x", 0);
		l("dwarven.crystalDay.y", 0);

		l("dwarven.nucleusEntrance.toggle", true);
		l("dwarven.nucleusEntrance.textColor", "&d");
		l("dwarven.nucleusEntrance.beaconToggle", false);

		l("dwarven.sendMap.toggle", false);

		l("dwarven.chestHelper.toggle", true);
		l("dwarven.chestHelper.color", "0.19607843;0.9764706;0.9490196;0.9764706");

		l("dwarven.forgeReminder.toggle", true);
		l("dwarven.forgeReminder.1", -1);
		l("dwarven.forgeReminder.2", -1);
		l("dwarven.forgeReminder.3", -1);
		l("dwarven.forgeReminder.4", -1);
		l("dwarven.forgeReminder.5", -1);


		l("dwarven.starSentryHelper.toggle", true);
		l("dwarven.starSentryHelper.x", 0);
		l("dwarven.starSentryHelper.y", 0);
		l("dwarven.starSentryHelper.commOnly", true);

		l("dwarven.crystalMap.toggle", true);
		l("dwarven.crystalMap.x", 0);
		l("dwarven.crystalMap.y", 0);
		l("dwarven.crystalMap.size", 10);
		l("dwarven.crystalMap.coordsToggle", true);
		l("dwarven.crystalMap.headToggle", true);
		l("dwarven.crystalMap.textColor", "&b");

		l("dwarven.windCompass.toggle", true);

		l("dwarven.fuel.toggle", false);
		l("dwarven.fuel.drillColor", "&f");
		l("dwarven.fuel.guiPrimeTenColor", "&4");
		l("dwarven.fuel.guiSecondTenColor", "&c");
		l("dwarven.fuel.guiPrimeHalfColor", "&6");
		l("dwarven.fuel.guiSecondHalfColor", "&e");
		l("dwarven.fuel.guiPrimeFullColor", "&a");
		l("dwarven.fuel.guiSecondFullColor", "&2");

		l("dwarven.garryTP.toggle", true);
		l("dwarven.garryTP.goblinToggle", false);
		l("dwarven.garryTP.raffleToggle", false);
		l("dwarven.garryTP.questToggle", false);
		l("dwarven.garryTP.notifToggle", true);

		l("dwarven.abilityTimer.toggle", true);
		l("dwarven.abilityTimer.holdingToggle", true);
		l("dwarven.abilityTimer.accuracy", 30);
		l("dwarven.abilityTimer.radius", 11);
		l("dwarven.abilityTimer.activeColor", "0.0;1.0;0.0");
		l("dwarven.abilityTimer.cdColor", "0.0;1.0;1.0");
		l("dwarven.abilityTimer.readyColor", "1.0;1.0;0.0");

		l("dwarven.puzzlerSolver.toggle", true);

		l("dwarven.fetchurSolver.toggle", true);

		l("dwarven.doneCommBg.toggle", true);
		l("dwarven.doneCommBg.color", "1.0;0.0;0.0;1.0");

		l("qol.blockHoeClicks.toggle", true);

		l("qol.lockQuickCrafts.items", ";;;");
		l("qol.lockQuickCrafts.toggle", true);


		l("misc.jerryTimer.toggle", true);
		l("misc.jerryTimer.x", 0);
		l("misc.jerryTimer.y", 0);
		l("misc.jerryTimer.textColor", "&6");
		l("misc.npcDialogue.toggle", true);
		l("misc.updateNotifs.toggle", true);



		loadValues();
	}

	public static void loadValues() throws ParseException, IOException {

		JSONObject oldConfig;
		try {
			oldConfig = (JSONObject) new JSONParser().parse(Utils.readFile("config/"+ Reference.MODID+"/sbp.cfg"));
		} catch(Exception e) {
			oldConfig = (JSONObject) new JSONParser().parse("{}");
		}


		int i=0;
		for(String key : c) {
			i++;
			try {

				String[] split = key.split("\\.");
				JSONObject loadCat = (JSONObject) oldConfig.get(split[0]);
				JSONObject loadSet = (JSONObject) loadCat.get(split[1]);
				Object loadVal = loadSet.get(split[2]);
				if(loadVal == null) {throw new NullPointerException();}

				ConfigHandler.config.put(key, loadVal);
				//System.out.println("Setting '"+key+"' to '"+loadVal+"'");
			} catch(Exception e) {
				//System.out.println(e.getMessage());
				try {
					ConfigHandler.config.put(c.get(i), d.get(i));
					Utils.print("Failed to find config variable: '"+c.get(i)+"', resetting to default: '"+d.get(i)+"'");
				} catch(Exception e2) {/*e2.printStackTrace();*/}

			}
		}

		ConfigHandler.rewriteConfig();

	}
	
}
