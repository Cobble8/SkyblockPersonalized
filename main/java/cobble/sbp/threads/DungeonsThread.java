package cobble.sbp.threads;

import java.text.DecimalFormat;
import java.util.Arrays;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.commands.Dungeons;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.GetFromAPI;
import cobble.sbp.utils.HttpClient;
import cobble.sbp.utils.Utils;

public class DungeonsThread extends Thread {
	
	public void run() {
		int cataXP = 0;

		String getuuid = "";
		try {
			getuuid = HttpClient.readPage("https://api.mojang.com/users/profiles/minecraft/"+Dungeons.args0+"?at=%3CcurrentUnixTimestampInSeconds%20-%2030%20days%3E");
		} catch (Exception e1) {e1.printStackTrace();}
		String uuid = GetFromAPI.getUUID(getuuid);
		String info = "";
		String achievementsInfo = "";
		String newInfo = "";
		String APIKey = (String) DataGetter.find("APIKey");
		try {
			achievementsInfo = HttpClient.readPage("https://api.hypixel.net/player?key="+APIKey+"&uuid="+uuid);
		} catch (Exception e1) {Utils.sendMessage(ChatFormatting.RED+"Please supply a valid name!");}
		
		try {
			newInfo = HttpClient.readPage("https://api.hypixel.net/skyblock/profiles?key="+APIKey+"&uuid="+uuid);
		} catch (Exception e1) {e1.printStackTrace();}
		
		String profileuuid = GetFromAPI.getCurrProfile(newInfo, uuid);
		
	DecimalFormat df2 = new DecimalFormat("#.##");
	int classLevel = Dungeons.checkCataLevel(GetFromAPI.getClassXP(newInfo, uuid, profileuuid));
	int cataLevel = Dungeons.checkCataLevel(GetFromAPI.getCataXP(newInfo, uuid, profileuuid));
	String mainClass = "none";
	mainClass = GetFromAPI.getSelectedDungeonClass(newInfo, uuid, profileuuid);
	
	int secretsFound = GetFromAPI.getAchiements(achievementsInfo, "skyblock_treasure_hunter");
	int F0 = 0;
	int F1 = 0;
	int F2 = 0;
	int F3 = 0;
	int F4 = 0;
	int F5 = 0;
	int F6 = 0;
	int F7 = 0;
	int F8 = 0;
	int F9 = 0;
	int F10 = 0;
	int highestCompleted = GetFromAPI.getHighestFloor(newInfo, uuid, profileuuid);
	Utils.print("Highest Floor Completed: "+highestCompleted);
	if(highestCompleted >= 0) {F0 = GetFromAPI.getFloorRuns(newInfo, "0", uuid, profileuuid);}
	if(highestCompleted >= 1) {F1 = GetFromAPI.getFloorRuns(newInfo, "1", uuid, profileuuid);}
	if(highestCompleted >= 2) {F2 = GetFromAPI.getFloorRuns(newInfo, "2", uuid, profileuuid);}
	if(highestCompleted >= 3) {F3 = GetFromAPI.getFloorRuns(newInfo, "3", uuid, profileuuid);}
	if(highestCompleted >= 4) {F4 = GetFromAPI.getFloorRuns(newInfo, "4", uuid, profileuuid);}
	if(highestCompleted >= 5) {F5 = GetFromAPI.getFloorRuns(newInfo, "5", uuid, profileuuid);}
	if(highestCompleted >= 6) {F6 = GetFromAPI.getFloorRuns(newInfo, "6", uuid, profileuuid);}
	if(highestCompleted >= 7) {F7 = GetFromAPI.getFloorRuns(newInfo, "7", uuid, profileuuid);}
	if(highestCompleted >= 8) {F8 = GetFromAPI.getFloorRuns(newInfo, "8", uuid, profileuuid);}
	if(highestCompleted >= 9) {F9 = GetFromAPI.getFloorRuns(newInfo, "9", uuid, profileuuid);}
	if(highestCompleted >= 10) {F10 = GetFromAPI.getFloorRuns(newInfo, "10", uuid, profileuuid);}
	
	int FloorList[] = {F0, F1, F2, F3, F4, F5, F6, F7, F8, F9, F10};
	Arrays.sort(FloorList);
	int mostPlayedFloorNum = FloorList[FloorList.length-1];
	int floorNum = 0;
	String highestFloor = "0";
	String hFNum = "";
	if(F0 == 0) highestFloor = "None";
	if(F0 > 0) {highestFloor = "0"; hFNum = F0+"";}
	if(F1 > 0) {highestFloor = "1"; hFNum = F1+"";}
	if(F2 > 0) {highestFloor = "2"; hFNum = F2+"";}
	if(F3 > 0) {highestFloor = "3"; hFNum = F3+"";}
	if(F4 > 0) {highestFloor = "4"; hFNum = F4+"";}
	if(F5 > 0) {highestFloor = "5"; hFNum = F5+"";}
	if(F6 > 0) {highestFloor = "6"; hFNum = F6+"";}
	if(F7 > 0) {highestFloor = "7"; hFNum = F7+"";}
	if(F8 > 0) {highestFloor = "8"; hFNum = F8+"";}
	if(F9 > 0) {highestFloor = "9"; hFNum = F9+"";}
	if(F10 > 0) {highestFloor = "10"; hFNum = F10+"";}
	String hFNumFin = "";
	if(hFNum != "") hFNumFin = ChatFormatting.BLUE+"("+ChatFormatting.AQUA+hFNum+ChatFormatting.BLUE+")";
	if(mostPlayedFloorNum == F0) floorNum = 0;
	else if(mostPlayedFloorNum == F1){ floorNum = 1;}
	else if(mostPlayedFloorNum == F2){ floorNum = 2;}
	else if(mostPlayedFloorNum == F3){ floorNum = 3;}
	else if(mostPlayedFloorNum == F4){ floorNum = 4;}
	else if(mostPlayedFloorNum == F5){ floorNum = 5;}
	else if(mostPlayedFloorNum == F6){ floorNum = 6;}
	else if(mostPlayedFloorNum == F7){ floorNum = 7;}
	else if(mostPlayedFloorNum == F8){ floorNum = 8;}
	else if(mostPlayedFloorNum == F9){ floorNum = 9;}
	else if(mostPlayedFloorNum == F10){ floorNum = 10;}
	int totalRuns = F0+F1+F2+F3+F4+F5+F6+F7+F8+F9+F10;
	double secretsPerRun = (double) secretsFound / (double) totalRuns;
	
	Utils.sendMessage(ChatFormatting.DARK_RED+"-----------------------------------------------------");
	Utils.sendMessage(ChatFormatting.RED+"Dungeons stats for "+ChatFormatting.AQUA+Dungeons.args0);
	Utils.sendMessage(ChatFormatting.AQUA+"Catacombs Level: "+ChatFormatting.GOLD+cataLevel);
	Utils.sendMessage(ChatFormatting.AQUA+"Main Class: "+ChatFormatting.GOLD+mainClass.substring(0, 1).toUpperCase() + mainClass.substring(1)+ChatFormatting.BLUE+" ("+ChatFormatting.AQUA+classLevel+ChatFormatting.BLUE+")");
	Utils.sendMessage(ChatFormatting.AQUA+"Total Dungeon Runs: "+ChatFormatting.GOLD+totalRuns);
	Utils.sendMessage(ChatFormatting.AQUA+"Most Played Floor: "+ChatFormatting.GOLD+"Floor "+floorNum+ChatFormatting.BLUE+" ("+ChatFormatting.AQUA+mostPlayedFloorNum+ChatFormatting.BLUE+")");
	Utils.sendMessage(ChatFormatting.AQUA+"Highest Beaten Floor: "+ChatFormatting.GOLD+"Floor "+highestFloor+" "+hFNumFin);
	Utils.sendMessage(ChatFormatting.AQUA+"Total Secrets Found: "+ChatFormatting.GOLD+secretsFound);
	Utils.sendMessage(ChatFormatting.AQUA+"Average Secrets Per Run: "+ChatFormatting.GOLD+df2.format(secretsPerRun));
	if(secretsFound > 10000) secretsFound = 10000;
	if(totalRuns > 1000) totalRuns = 1000;
	int overallScore = ((cataLevel*2)+(secretsFound/100)+(totalRuns/10)+(classLevel*2))/4;
	if(overallScore > 100) overallScore = 100;
	Utils.sendMessage(ChatFormatting.AQUA+"Overall Score: "+ChatFormatting.GOLD+overallScore+ChatFormatting.GREEN+"%");
	}

}
