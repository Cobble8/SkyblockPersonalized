package com.cobble.sbp.threads.misc;

import com.cobble.sbp.utils.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class SBPUpdater extends Thread {
        public void run() {
            Utils.sendMessage("Downloading new "+ Reference.NAME+" version!");
            String file;
            try { file = HttpClient.readPage("https://raw.githubusercontent.com/Cobble8/SkyblockPersonalized/main/versioncheck.json");
            } catch (Exception e) { return; }

            JsonElement info = new JsonParser().parse(file);
            String updateUrl = info.getAsJsonObject().get("updateUrl").getAsString();
            Utils.sendMessage("Found update URL:");
            Utils.sendSpecificMessage(Colors.AQUA+updateUrl);
            new AutoUpdater(updateUrl);

            Utils.sendMessage("Finished Downloading! "+Colors.GOLD+"Restart Minecraft for the update to take effect!");
            Utils.sendErrMsg("WARNING: You may crash after doing this but it will be resolved after another launch!");
            Utils.playDingSound();
        }
}
