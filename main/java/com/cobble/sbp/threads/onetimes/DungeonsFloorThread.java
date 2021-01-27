package com.cobble.sbp.threads.onetimes;

import com.cobble.sbp.commands.Dungeons;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.GetFromAPI;
import com.cobble.sbp.utils.HttpClient;
import com.cobble.sbp.utils.Utils;

public class DungeonsFloorThread extends Thread {

	public static String highestClass = "";
	
	public void run() {
		int selectedFloor = Integer.parseInt(Dungeons.args1);
		String APIKey = (String) DataGetter.find("APIKey");
		String getuuid = "";
		try {
			getuuid = HttpClient.readPage("https://api.mojang.com/users/profiles/minecraft/"+Dungeons.args0+"?at=%3CcurrentUnixTimestampInSeconds%20-%2030%20days%3E");
		} catch (Exception e1) {Utils.sendErrMsg(Dungeons.args0+" does not exist!");}
		String uuid = GetFromAPI.getUUID(getuuid);
		String newInfo = "";
		try {
			newInfo = HttpClient.readPage("https://api.hypixel.net/skyblock/profiles?key="+APIKey+"&uuid="+uuid);
		} catch (Exception e1) {Utils.sendErrMsg(Dungeons.args0+" does not have their API Enabled!");}
		Utils.print("https://api.hypixel.net/skyblock/profiles?key="+APIKey+"&uuid="+uuid);
		String profileuuid = GetFromAPI.getCurrProfile(newInfo, uuid);
		int highestFloor = GetFromAPI.getHighestFloor(newInfo, uuid, profileuuid);
		if(selectedFloor > highestFloor) {
			Utils.sendSpecificMessage(Colors.DARK_RED+"-----------------------------------------------------");
			Utils.sendMessage(Colors.AQUA+Dungeons.args0+Colors.RED+" has never completed Floor "+Colors.AQUA+selectedFloor+Colors.RED+"!");
			return;
		}
		int totalAttempts = GetFromAPI.getFloorAttempts(newInfo, uuid, profileuuid, Dungeons.args1);
		int totalFinishes = GetFromAPI.getFloorRuns(newInfo, uuid, profileuuid, Dungeons.args1);
		int watcherKills = GetFromAPI.getWatcherKills(newInfo, uuid, profileuuid, Dungeons.args1);
		int bestScore = GetFromAPI.getBestScore(newInfo, uuid, profileuuid, Dungeons.args1);
		int mostMobKills = GetFromAPI.getMostKills(newInfo, uuid, profileuuid, Dungeons.args1);
		//int mostDamage = GetFromAPI.getMostDamage(newInfo, uuid, profileuuid, Dungeons.args1);
		int mostHealing = GetFromAPI.getMostHealing(newInfo, uuid, profileuuid, Dungeons.args1);
		String fastestTime = Utils.secondsToTime(GetFromAPI.getFastestTime(newInfo, uuid, profileuuid, Dungeons.args1));
		String fastestSTime = "";//
		String fastestSPLUSTime = "";//Utils.secondsToTime(GetFromAPI.getFastestSPlusTime(newInfo, uuid, profileuuid, Dungeons.args1));
		if(bestScore >= 270) {
			fastestSTime = Utils.secondsToTime(GetFromAPI.getFastestSTime(newInfo, uuid, profileuuid, Dungeons.args1));
		} else {
			fastestSTime = "None";
		}
		if(bestScore >= 300) {
			fastestSPLUSTime = Utils.secondsToTime(GetFromAPI.getFastestSPlusTime(newInfo, uuid, profileuuid, Dungeons.args1));
		} else {
			fastestSPLUSTime = "None";
		}
		
		
		long mostDamage = GetFromAPI.getHighestFloorDamage(newInfo, uuid, profileuuid, Dungeons.args1);
		
		//
		Utils.sendSpecificMessage(Colors.DARK_RED+"-----------------------------------------------------");
		Utils.sendMessage(Colors.RED+"Dungeons stats for "+Colors.AQUA+Dungeons.args0+Colors.RED+" on Floor "+Colors.AQUA+Dungeons.args1);
		Utils.sendSpecificMessage(Colors.AQUA+"Total Completions: "+Colors.GOLD+Utils.formatNums(totalFinishes).replace(",", Colors.BLUE+","+Colors.GOLD)+Colors.GREEN+"/"+Colors.GOLD+Utils.formatNums(totalAttempts).replace(",", Colors.BLUE+","+Colors.GOLD) + Colors.BLUE+" ("+Colors.AQUA+Utils.formatNums(watcherKills).replace(",", Colors.BLUE+","+Colors.AQUA)+Colors.GREEN+" Watcher "+Colors.AQUA+"Kills"+Colors.BLUE+")");
		Utils.sendSpecificMessage(Colors.AQUA+"Best Score: "+Colors.GOLD+bestScore);
		Utils.sendSpecificMessage(Colors.AQUA+"Most Mobs Killed: "+Colors.GOLD+Utils.formatNums(mostMobKills).replace(",", Colors.BLUE+","+Colors.GOLD));
		Utils.sendSpecificMessage(Colors.AQUA+"Most Damage: "+Colors.GOLD+Utils.formatNums(Integer.parseInt(mostDamage+"")).replace(",", Colors.BLUE+","+Colors.GOLD)+Colors.BLUE+" ("+Colors.AQUA+highestClass+Colors.BLUE+(")"));
		Utils.sendSpecificMessage(Colors.AQUA+"Most Healing: "+Colors.GOLD+Utils.formatNums(mostHealing).replace(",", Colors.BLUE+","+Colors.GOLD));
		Utils.sendSpecificMessage(Colors.AQUA+"Fastest Time: "+Colors.GOLD+fastestTime);
		Utils.sendSpecificMessage(Colors.AQUA+"Fastest S Time: "+Colors.GOLD+fastestSTime);
		Utils.sendSpecificMessage(Colors.AQUA+"Fastest S+ Time: "+Colors.GOLD+fastestSPLUSTime);
	}
}
