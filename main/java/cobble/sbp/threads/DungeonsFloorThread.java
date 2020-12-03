package cobble.sbp.threads;

import java.util.Arrays;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.commands.functional.Dungeons;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.GetFromAPI;
import cobble.sbp.utils.HttpClient;
import cobble.sbp.utils.Utils;

public class DungeonsFloorThread extends Thread {

	public static String highestClass = "";
	
	public void run() {
		int selectedFloor = Integer.parseInt(Dungeons.args1);
		String APIKey = (String) DataGetter.find("APIKey");
		if(APIKey == "NOT_SET") {
			Utils.setApiKey();
			return;
		}
		String getuuid = "";
		try {
			getuuid = HttpClient.readPage("https://api.mojang.com/users/profiles/minecraft/"+Dungeons.args0+"?at=%3CcurrentUnixTimestampInSeconds%20-%2030%20days%3E");
		} catch (Exception e1) {e1.printStackTrace();}
		String uuid = GetFromAPI.getUUID(getuuid);
		String newInfo = "";
		try {
			newInfo = HttpClient.readPage("https://api.hypixel.net/skyblock/profiles?key="+APIKey+"&uuid="+uuid);
		} catch (Exception e1) {e1.printStackTrace();}
		Utils.print("https://api.hypixel.net/skyblock/profiles?key="+APIKey+"&uuid="+uuid);
		String profileuuid = GetFromAPI.getCurrProfile(newInfo, uuid);
		int highestFloor = GetFromAPI.getHighestFloor(newInfo, uuid, profileuuid);
		if(selectedFloor > highestFloor) {
			Utils.sendMessage(ChatFormatting.DARK_RED+"-----------------------------------------------------");
			Utils.sendMessage(ChatFormatting.AQUA+Dungeons.args0+ChatFormatting.RED+" has never completed Floor "+ChatFormatting.AQUA+selectedFloor+ChatFormatting.RED+"!");
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
		
		
		Utils.sendMessage(ChatFormatting.DARK_RED+"-----------------------------------------------------");
		Utils.sendMessage(ChatFormatting.RED+"Dungeons stats for "+ChatFormatting.AQUA+Dungeons.args0+ChatFormatting.RED+" on Floor "+ChatFormatting.AQUA+Dungeons.args1);
		Utils.sendMessage(ChatFormatting.AQUA+"Total Completions: "+ChatFormatting.GOLD+Utils.formatNums(totalFinishes).replace(",", ChatFormatting.BLUE+","+ChatFormatting.GOLD)+ChatFormatting.GREEN+"/"+ChatFormatting.GOLD+Utils.formatNums(totalAttempts).replace(",", ChatFormatting.BLUE+","+ChatFormatting.GOLD) + ChatFormatting.BLUE+" ("+ChatFormatting.AQUA+Utils.formatNums(watcherKills).replace(",", ChatFormatting.BLUE+","+ChatFormatting.AQUA)+ChatFormatting.GREEN+" Watcher "+ChatFormatting.AQUA+"Kills"+ChatFormatting.BLUE+")");
		Utils.sendMessage(ChatFormatting.AQUA+"Best Score: "+ChatFormatting.GOLD+bestScore);
		Utils.sendMessage(ChatFormatting.AQUA+"Most Mobs Killed: "+ChatFormatting.GOLD+Utils.formatNums(mostMobKills).replace(",", ChatFormatting.BLUE+","+ChatFormatting.GOLD));
		Utils.sendMessage(ChatFormatting.AQUA+"Most Damage: "+ChatFormatting.GOLD+Utils.formatNums(Integer.parseInt(mostDamage+"")).replace(",", ChatFormatting.BLUE+","+ChatFormatting.GOLD)+ChatFormatting.BLUE+" ("+ChatFormatting.AQUA+highestClass+ChatFormatting.BLUE+(")"));
		Utils.sendMessage(ChatFormatting.AQUA+"Most Healing: "+ChatFormatting.GOLD+Utils.formatNums(mostHealing).replace(",", ChatFormatting.BLUE+","+ChatFormatting.GOLD));
		Utils.sendMessage(ChatFormatting.AQUA+"Fastest Time: "+ChatFormatting.GOLD+fastestTime);
		Utils.sendMessage(ChatFormatting.AQUA+"Fastest S Time: "+ChatFormatting.GOLD+fastestSTime);
		Utils.sendMessage(ChatFormatting.AQUA+"Fastest S+ Time: "+ChatFormatting.GOLD+fastestSPLUSTime);
	}
}
