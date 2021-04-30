package com.cobble.sbp.threads.misc;

import java.util.ArrayList;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.handlers.DownloadSecretsHandler;
import com.cobble.sbp.utils.HttpClient;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.SecretUtils;
import com.cobble.sbp.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import net.minecraft.client.renderer.GlStateManager;

public class LaunchThread extends Thread {
	
	public static Boolean invalidVersion = false;
	public static int dgnImgVersLatest = 0;
	public static int dgnImgVersCurr = DataGetter.findInt("dgnImgVers");
	
	public void run() {
		
		
		
		String file = "";
		try { file = HttpClient.readPage("https://raw.githubusercontent.com/Cobble8/SkyblockPersonalized/main/versioncheck.json");
		} catch (Exception e) { return; }
		JsonElement info = new JsonParser().parse(file);
		file="";
		dgnImgVersLatest = info.getAsJsonObject().get("dgnImgVer").getAsInt();
		JsonArray tmp = info.getAsJsonObject().get("forceDisable").getAsJsonArray();
		ConfigHandler.forceDisabled.clear(); for(Object str : tmp) { ConfigHandler.forceDisabled.add((str+"").replace("\"", "")); }
		JsonArray tmp2 = info.getAsJsonObject().get("forceEnable").getAsJsonArray();
		ConfigHandler.forceEnabled.clear(); for(Object str : tmp2) { ConfigHandler.forceEnabled.add((str+"").replace("\"", "")); }
		JsonArray tmp3 = info.getAsJsonObject().get("disabledVersions").getAsJsonArray();
		for(Object str : tmp3) {
			if(((str+"").replace("\"", "")).equals(Reference.VERSION)) {
				invalidVersion=true;
			}
		}
		if(!invalidVersion) {
			Utils.print("This version of "+Reference.NAME+" is allowed! Current Version: "+Reference.VERSION+", Disabled Versions: "+tmp3);
		} else {
			Utils.print("This version of "+Reference.NAME+" is invalid! WARNING: Will crash on login!");
		}
		
		SecretUtils.updateDungeonList();
		
		if(DataGetter.findBool("scrtAutoDownload")) {
			if(LaunchThread.dgnImgVersCurr != LaunchThread.dgnImgVersLatest) {
				if(LaunchThread.dgnImgVersCurr != 0) {
					new DownloadSecretsHandler().start();
					ConfigHandler.newObject("dgnImgVers", LaunchThread.dgnImgVersLatest);
					LaunchThread.dgnImgVersCurr=LaunchThread.dgnImgVersLatest;

				}
			}
		}
		
	}


}
