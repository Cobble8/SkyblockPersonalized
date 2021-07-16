package com.cobble.sbp.threads.commands;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import com.cobble.sbp.commands.Dungeons;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.*;
import net.minecraft.util.EnumChatFormatting;

public class DungeonsThread extends Thread {
	
	public void run() {




		
		String getuuid = "";
		try {
			getuuid = HttpClient.readPage("https://api.mojang.com/users/profiles/minecraft/"+Dungeons.args0+"?at=%3CcurrentUnixTimestampInSeconds%20-%2030%20days%3E");
		} catch (Exception e1) {Utils.sendErrMsg(Dungeons.args0+" does not exist!");}
		String uuid = GetFromAPI.getUUID(getuuid);
		String achievementsInfo = "";
		String newInfo = "";
		String APIKey = (String) DataGetter.find("APIKey");
		try {
			achievementsInfo = HttpClient.readPage("https://api.hypixel.net/player?key="+APIKey+"&uuid="+uuid);
			Utils.print("https://api.hypixel.net/player?key="+APIKey+"&uuid="+uuid);
		} catch (Exception e1) {Utils.sendErrMsg("Please supply a valid name!");}


		
		try {
			newInfo = HttpClient.readPage("https://api.hypixel.net/skyblock/profiles?key="+APIKey+"&uuid="+uuid);
			Utils.print("https://api.hypixel.net/skyblock/profiles?key="+APIKey+"&uuid="+uuid);
		} catch (Exception e1) {Utils.sendErrMsg(Dungeons.args0+" does not have their API Enabled!");}
		
		String profileuuid = GetFromAPI.getCurrProfile(newInfo, uuid);


		String linkBase = "profiles.profile_id|"+profileuuid+";members."+uuid+".";

		DecimalFormat df2 = new DecimalFormat("#.##");

		ArrayList<Integer> floorCompletions = new ArrayList<>();
		for(int i=0;i<=7;i++) {
			try {
				Double currFloor = Double.parseDouble(DataGetter.lazyFind(linkBase+"dungeons.dungeon_types.catacombs.tier_completions."+i+".", newInfo)+"");
				floorCompletions.add(Integer.parseInt((currFloor+"").substring(0, (currFloor+"").indexOf("."))));
			} catch(Exception e) { floorCompletions.add(0); }
		}
			for(int i=1;i<=7;i++) {
				try {
					Double currFloor = Double.parseDouble(DataGetter.lazyFind(linkBase+"dungeons.dungeon_types.master_catacombs.tier_completions."+i+".", newInfo)+"");
					floorCompletions.add(Integer.parseInt((currFloor+"").substring(0, (currFloor+"").indexOf("."))));
				} catch(Exception e) { floorCompletions.add(0); }
			}

		int mostPlayed = getHighestSpot(floorCompletions);
		int highestPlayed = getHighestFloor(floorCompletions);
		int totalRuns=0; for (Integer floorCompletion : floorCompletions) { totalRuns += floorCompletion; }
		int cataLevel = Dungeons.checkCataLevel((int) Double.parseDouble(DataGetter.lazyFind(linkBase+"dungeons.dungeon_types.catacombs.experience.", newInfo)+""));
		int classLevel = Dungeons.checkCataLevel((int) Double.parseDouble(DataGetter.lazyFind(linkBase+"dungeons.player_classes.healer.experience.", newInfo)+""));
		int secretsFound = GetFromAPI.getAchiements(achievementsInfo, "skyblock_treasure_hunter");
		double secretsPerRun = (double) secretsFound / (double) totalRuns;
		String mainClass = DataGetter.lazyFind(linkBase+"dungeons.selected_dungeon_class.", newInfo)+"";
		String[] floorNames = {"Entrance", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "M1", "M2", "M3", "M4", "M5", "M6", "M7"};
		String playerName = DataGetter.lazyFind("player.displayname.", achievementsInfo)+"";
		String rank = DataGetter.lazyFind("player.newPackageRank.", achievementsInfo)+"";
		String rankColor = "";
		String plusColor = "";
		try {
			plusColor=DataGetter.lazyFind("player.rankPlusColor.", achievementsInfo)+"";
			String ifSuperstar = DataGetter.lazyFind("player.monthlyPackageRank.", achievementsInfo)+"";
			if(!ifSuperstar.equals("NONE")) { rank=ifSuperstar; }
			try { rank=DataGetter.lazyFind("player.rank.", achievementsInfo)+""; } catch(Exception ignored) {}
			rankColor=DataGetter.lazyFind("player.monthlyRankColor.", achievementsInfo)+"";
		} catch(Exception ignored) {}
		playerName=getRankFromRankID(rank, ColorUtils.getFromColorName(plusColor), ColorUtils.getFromColorName(rankColor))+" "+playerName;


		Utils.sendSpecificMessage(Colors.DARK_RED+"-----------------------------------------------------");
		Utils.sendMessage(Colors.RED+"Dungeon stats for: "+Colors.AQUA+playerName);
		Utils.sendSpecificMessage(Colors.AQUA+"Main Class: "+Colors.GOLD+mainClass.substring(0, 1).toUpperCase() + mainClass.substring(1)+Colors.BLUE+" ("+Colors.AQUA+classLevel+Colors.BLUE+")");

		Utils.sendSpecificMessage(Colors.AQUA+"Catacombs Level: "+Colors.GOLD+cataLevel);
		Utils.sendSpecificMessage(Colors.AQUA+"Total Dungeon Runs: "+Colors.GOLD+totalRuns);
		Utils.sendSpecificMessage(Colors.AQUA+"Most Played Floor: "+Colors.GOLD+floorNames[mostPlayed]+Colors.BLUE+" ("+Colors.AQUA+floorCompletions.get(mostPlayed)+Colors.BLUE+")");
		Utils.sendSpecificMessage(Colors.AQUA+"Highest Floor: "+Colors.GOLD+floorNames[highestPlayed]+Colors.BLUE+" ("+Colors.AQUA+floorCompletions.get(highestPlayed)+Colors.BLUE+")");
		Utils.sendSpecificMessage(Colors.AQUA+"Total Secrets: "+Colors.GOLD+secretsFound);
		Utils.sendSpecificMessage(Colors.AQUA+"Average Secrets Per Run: "+Colors.GOLD+df2.format(secretsPerRun));
		if(secretsFound > 10000) secretsFound = 10000;
		if(totalRuns > 1000) totalRuns = 1000;
		int overallScore = (((cataLevel*2)+(secretsFound/100)+(totalRuns/10)+(classLevel*2))/4);
		if(overallScore > 100) overallScore = 100;
		Utils.sendSpecificMessage(Colors.AQUA+"Overall Score: "+Colors.GOLD+overallScore+Colors.GREEN+"%");
	}

	public static int getHighestSpot(ArrayList<Integer> array) {
		int lastHighest = 0;
		int lastSpot = 0;

		for(int i=0;i<array.size();i++) {
			if(array.get(i) > lastHighest) {
				lastHighest = array.get(i);
				lastSpot = i;
			}
		}

		return lastSpot;
	}

	public static int getHighestFloor(ArrayList<Integer> array) {
		for(int i=0;i<array.size();i++) {
			if(i > 0 && array.get(i) == 0) {
				return i-1;
			}
		}
		return 14;
	}

	public static String getRankFromRankID(String rankID, String plusColor, String superstarColor) {
		String output = "";
		switch(rankID) {
			case("VIP"): output=Colors.GREEN+"[VIP]";break;
			case("VIP_PLUS"): output=Colors.GREEN+"[VIP"+Colors.GOLD+"+"+Colors.GREEN+"]";break;
			case("MVP"): output=Colors.AQUA+"[MVP]";break;
			case("MVP_PLUS"): output=Colors.AQUA+"[MVP"+plusColor+"+"+Colors.AQUA+"]";break;
			case("SUPERSTAR"): output=superstarColor+"[MVP"+plusColor+"++"+superstarColor+"]";break;
			case("YOUTUBER"): output=Colors.RED+"["+Colors.WHITE+"YOUTUBE"+Colors.RED+"]";break;
			case("ADMIN"): output=Colors.RED+"[ADMIN]";break;
		}
		return ColorUtils.getFromColorName(superstarColor)+output;
	}
}
