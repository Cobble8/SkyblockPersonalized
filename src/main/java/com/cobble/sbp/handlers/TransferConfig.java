package com.cobble.sbp.handlers;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.simplejson.parser.JSONParser;
import com.cobble.sbp.utils.FileUtils;
import com.cobble.sbp.utils.Reference;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

public class TransferConfig {

    private static HashMap<String, String> tK = new HashMap<>();

    public TransferConfig() {

        try {
            String oldConfigStr = FileUtils.readFile("config/"+ Reference.MODID+"/main.cfg");
            JSONObject oldConfigJson = (JSONObject) new JSONParser().parse(oldConfigStr);
            Set<String> keyList = oldConfigJson.keySet();
            transferKeys();
            for(String key : keyList) {


                try {
                    String newKey = tK.get(key);
                    if(newKey == null) {continue;}
                    Object newValue;
                    try {
                        newValue = oldConfigJson.get(key);
                    } catch(Exception e) {
                        newValue = ConfigHandler.getDefaultValue(key);
                    }
                    ConfigHandler.config.put(newKey, newValue);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

            //System.out.println(tK);

            tK.clear();
            System.out.println(ConfigHandler.config);
            ConfigHandler.rewriteConfig();
            File oldConfig = new File("config/"+Reference.MODID+"/main.cfg");
            oldConfig.delete();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    static void l(String newKey, String oldKey) {
        tK.put(oldKey, newKey);
    }

    private static void transferKeys() {

        l("core.api.toggle", "APIKeyToggle");
        l("core.api.key", "APIKey");
        l("command.reparty.toggle", "repartyCommandToggle");
        l("command.reparty.delay", "pingDelay");
        l("core.launchCounter.toggle", "modLaunchToggle");
        l("core.launchCounter.count", "modLaunches");
        l("core.menu.theme", "currentTheme");
        l("core.skyblock.only", "onlyOnSkyblock");
        l("core.menu.gridLocking", "gridLockingToggle");
        l("core.menu.gridLockingPx", "gridLockingPx");
        l("core.gui.textStyle", "textStyle");
        l("core.gui.chromaSpeed", "chromaSpeed");
        l("core.easterEgg.coco", "cocoChievement");
        l("game.score.record", "gameRecord");
        l("music.all.toggle", "clientMusicToggle");
        l("music.inv.x", "musicInvX");
        l("music.inv.y", "musicInvY");
        l("music.esc.x", "musicEscX");
        l("music.esc.y", "musicEscY");
        l("music.all.volume", "musicVolume");
        l("dungeon.secretImage.vers", "dgnImgVers");
        l("dungeon.secretImage.x", "scrtX");
        l("dungeon.secretImage.y", "scrtY");
        l("dungeon.secretImage.size", "scrtSize");
        l("dungeon.secretImage.bgColor", "scrtBgColor");
        l("dungeon.secretImage.textColor", "scrtTextColor");
        l("dungeon.secretImage.toggle", "scrtToggle");
        l("dungeon.secretImage.autoDownload", "scrtAutoDownload");
        l("dungeon.secretImage.autoRemove", "scrtAutoRemove");
        l("command.dungeon.toggle", "dungeonsCommandToggle");
        l("dwarven.gui.x", "dwarvenGuiX");
        l("dwarven.gui.y", "dwarvenGuiY");
        l("dwarven.user.hotmLevel", "dwarvenHOTMLevel");
        l("dwarven.gui.powderToggle", "dwarvenMithrilDisplay");
        l("dwarven.gui.mithrilTextColor", "dwarvenMithrilTextColor");
        l("dwarven.gui.mithrilCountColor", "dwarvenMithrilCountColor");
        l("dwarven.gui.gemstoneTextColor", "dwarvenGemstoneTextColor");
        l("dwarven.gui.gemstoneCountColor", "dwarvenGemstoneCountColor");
        l("dwarven.eventTimer.toggle", "dwarvenTimerToggle");
        l("dwarven.eventTimer.textColor", "dwarvenTimerTextColor");
        l("dwarven.eventTimer.ding", "dwarvenTimerDing");
        l("dwarven.eventTimer.x", "dwarvenTimerX");
        l("dwarven.eventTimer.y", "dwarvenTimerY");
        l("dwarven.crystalDay.toggle", "crystalDay");
        l("dwarven.crystalDay.textColor", "crystalDayColor");
        l("dwarven.crystalDay.numColor", "crystalDayNumColor");
        l("dwarven.crystalDay.crystalOnly", "crystalDayOnly");
        l("dwarven.crystalDay.x", "crystalDayX");
        l("dwarven.crystalDay.y", "crystalDayY");
        l("dwarven.forgeReminder.toggle", "dwarvenForgeReminder");
        l("dwarven.forgeReminder.1", "dwarvenForgeSlot1");
        l("dwarven.forgeReminder.2", "dwarvenForgeSlot2");
        l("dwarven.forgeReminder.3", "dwarvenForgeSlot3");
        l("dwarven.forgeReminder.4", "dwarvenForgeSlot4");
        l("dwarven.forgeReminder.5", "dwarvenForgeSlot5");
        l("dwarven.gui.commissionToggle", "dwarvenTrackToggle");
        l("dwarven.gui.commissionNumsToggle", "dwarvenTrackNumsToggle");
        l("dwarven.gui.commissionWordColor", "dwarvenTrackCommissionColor");
        l("dwarven.gui.commissionQuestNameColor", "dwarvenTrackQuestName");
        l("dwarven.gui.commissionBarToggle", "dwarvenTrackBarToggle");
        l("dwarven.gui.commissionBorderColor", "dwarvenTrackBorderColor");
        l("dwarven.gui.commissionYesColor", "dwarvenTrackYesColor");
        l("dwarven.gui.commissionNoColor", "dwarvenTrackNoColor");
        l("dwarven.gui.commissionHideWord", "dwarvenHideCommissionWord");
        l("dwarven.gui.size", "dwarvenGuiScale");
        l("dwarven.fuel.toggle", "dwarvenFuelToggle");
        l("dwarven.starSentryHelper.toggle", "starSentryHelper");
        l("dwarven.starSentryHelper.x", "starSentryHelperX");
        l("dwarven.starSentryHelper.y", "starSentryHelperY");
        l("dwarven.starSentryHelper.commOnly", "starSentryOnlyWithComm");
        l("dwarven.crystalMap.toggle", "crystalMap");
        l("dwarven.crystalMap.x", "crystalMapX");
        l("dwarven.crystalMap.y", "crystalMapY");
        l("dwarven.crystalMap.size", "crystalMapSize");
        l("dwarven.crystalMap.coordsToggle", "crystalMapCoords");
        l("dwarven.crystalMap.headToggle", "crystalMapHead");
        l("dwarven.crystalMap.textColor", "crystalMapTextColor");
        l("dwarven.crystalWaypoint.color", "crystalWaypointColor");
        l("dwarven.windCompass.toggle", "windCompass");
        l("dwarven.fuel.drillColor", "dwarvenFuelDrillColor");
        l("dwarven.fuel.guiPrimeTenColor", "dwarvenFuelGuiPrimeTenColor");
        l("dwarven.fuel.guiSecondTenColor", "dwarvenFuelGuiSecondTenColor");
        l("dwarven.fuel.guiPrimeHalfColor", "dwarvenFuelGuiPrimeHalfColor");
        l("dwarven.fuel.guiSecondHalfColor", "dwarvenFuelGuiSecondHalfColor");
        l("dwarven.fuel.guiPrimeFullColor", "dwarvenFuelPrimeFullColor");
        l("dwarven.fuel.guiSecondFullColor", "dwarvenFuelSecondFullColor");
        l("dwarven.garryTP.toggle", "dwarvenTeleport");
        l("dwarven.garryTP.goblinToggle", "dwarvenTeleportGoblin");
        l("dwarven.garryTP.raffleToggle", "dwarvenTeleportRaffle");
        l("dwarven.garryTP.questToggle", "dwarvenTeleportQuest");
        l("dwarven.garryTP.notifToggle", "dwarvenTeleportNotif");
        l("dwarven.abilityTimer.toggle", "pickReminderToggle");
        l("dwarven.abilityTimer.holdingToggle", "pickTimerHolding");
        l("dwarven.abilityTimer.accuracy", "pickTimerCircleAcc");
        l("dwarven.abilityTimer.radius", "pickTimerCircleRadius");
        l("dwarven.abilityTimer.activeColor", "pickTimerCircleActive");
        l("dwarven.abilityTimer.cdColor", "pickTimerCircleCd");
        l("dwarven.abilityTimer.readyColor", "pickTimerCircleReady");
        l("dwarven.puzzlerSolver.toggle", "puzzlerSolver");
        l("dwarven.fetchurSolver.toggle", "fetchurSolver");
        l("qol.blockHoeClicks.toggle", "blockHoeClicks");
        l("qol.lockQuickCrafts.items", "blockedQuickCrafts");
        l("qol.lockQuickCrafts.toggle", "toggleBlockedQuickCrafts");
        l("dwarven.doneCommBg.toggle", "dwarvenCommBgToggle");
        l("dwarven.doneCommBg.color", "dwarvenCommBgColor");
        l("misc.jerryTimer.toggle", "jerryToggle");
        l("misc.jerryTimer.x", "jerryX");
        l("misc.jerryTimer.y", "jerryY");
        l("misc.jerryTimer.textColor", "jerryTextColor");
        l("misc.npcDialogue.toggle", "npcDialogueToggle");
        l("misc.updateNotifs.toggle", "updateNotifs");
    }



}
