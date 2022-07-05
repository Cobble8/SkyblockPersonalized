package com.cobble.sbp.threads.misc;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.handlers.UpdateCheckHandler;
import com.cobble.sbp.utils.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraftforge.fml.common.Loader;

public class LaunchThread extends Thread {
	
	public static boolean invalidVersion = false;
	public static int dgnImgVersLatest = 0;
	public static int dgnImgVersCurr = DataGetter.findInt("dungeon.secretImage.vers");
	
	public void run() {
		if(!Loader.isModLoaded("skyblockclientupdater")) {
			UpdateCheckHandler.check();
		}

		
		String file = "";
		try { file = WebUtils.readPage("https://raw.githubusercontent.com/Cobble8/SkyblockPersonalized/main/versioncheck.json");
		} catch (Exception e) { return; }
		JsonElement info = new JsonParser().parse(file);
		dgnImgVersLatest = info.getAsJsonObject().get("dgnImgVer").getAsInt();
		JsonArray tmp = info.getAsJsonObject().get("forceDisable").getAsJsonArray();
		ConfigHandler.forceDisabled.clear(); for(Object str : tmp) { ConfigHandler.forceDisabled.add((str+"").replace("\"", "")); }
		JsonArray tmp2 = info.getAsJsonObject().get("forceEnable").getAsJsonArray();
		ConfigHandler.forceEnabled.clear(); for(Object str : tmp2) { ConfigHandler.forceEnabled.add((str+"").replace("\"", "")); }
		JsonArray tmp3 = info.getAsJsonObject().get("disabledVersions").getAsJsonArray();
		for(Object str : tmp3) {
			if (((str + "").replace("\"", "")).equals(Reference.VERSION)) {
				invalidVersion = true;
				break;
			}
		}

		for(String key : ConfigHandler.forceDisabled) { ConfigHandler.newObject(key, false); }
		for(String key : ConfigHandler.forceEnabled) { ConfigHandler.newObject(key, true); }

		if(!invalidVersion) {
			Utils.print("This version of "+Reference.NAME+" is allowed! Current Version: "+Reference.VERSION+", Disabled Versions: "+tmp3);
		} else {
			Utils.print("This version of "+Reference.NAME+" is invalid! WARNING: Will crash on login!");
		}
		
	}


}
