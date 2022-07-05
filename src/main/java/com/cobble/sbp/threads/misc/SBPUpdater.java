package com.cobble.sbp.threads.misc;

import com.cobble.sbp.utils.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class SBPUpdater extends Thread {
        public void run() {
            TextUtils.sendMessage("Downloading new "+ Reference.NAME+" version!");
            String file;
            try { file = WebUtils.readPage("https://raw.githubusercontent.com/Cobble8/SkyblockPersonalized/main/versioncheck.json");
            } catch (Exception e) { return; }

            JsonElement info = new JsonParser().parse(file);
            String updateUrl = info.getAsJsonObject().get("updateUrl").getAsString();
            TextUtils.sendMessage("Found update URL:");
            TextUtils.sendSpecificMessage(Colors.AQUA+updateUrl);
            new AutoUpdater(updateUrl);

            TextUtils.sendMessage("Finished Downloading! "+Colors.GOLD+"Restart Minecraft for the update to take effect!");
            TextUtils.sendErrMsg("WARNING: You may crash after doing this but it will be resolved after another launch!");
            Utils.playDingSound();
        }
}
