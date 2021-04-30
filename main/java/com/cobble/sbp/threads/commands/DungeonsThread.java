package com.cobble.sbp.threads.commands;

import java.text.DecimalFormat;
import java.util.Arrays;

import com.cobble.sbp.commands.Dungeons;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.GetFromAPI;
import com.cobble.sbp.utils.HttpClient;
import com.cobble.sbp.utils.Utils;

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
		} catch (Exception e1) {Utils.sendErrMsg("Please supply a valid name!");}
		
		try {
			newInfo = HttpClient.readPage("https://api.hypixel.net/skyblock/profiles?key="+APIKey+"&uuid="+uuid);
		} catch (Exception e1) {Utils.sendErrMsg(Dungeons.args0+" does not have their API Enabled!");}
		
		String profileuuid = GetFromAPI.getCurrProfile(newInfo, uuid);
		
	DecimalFormat df2 = new DecimalFormat("#.##");
	
	int classLevel = 0; 
	try {
		classLevel = Dungeons.checkCataLevel(GetFromAPI.getClassXP(newInfo, uuid, profileuuid));
	} catch(NullPointerException e) {
		Utils.sendErrMsg("Invalid API Key! Type /api new for a new one!");
		return;
	}
	
	int cataLevel = 0; cataLevel = Dungeons.checkCataLevel(GetFromAPI.getCataXP(newInfo, uuid, profileuuid));
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
	int highestCompleted = GetFromAPI.getHighestFloor(newInfo, uuid, profileuuid);
	//Utils.print("Highest Floor Completed: "+highestCompleted);
	if(highestCompleted >= 0) {F0 = GetFromAPI.getFloorRuns(newInfo, uuid, profileuuid, "0" );}
	if(highestCompleted >= 1) {F1 = GetFromAPI.getFloorRuns(newInfo, uuid, profileuuid, "1" );}
	if(highestCompleted >= 2) {F2 = GetFromAPI.getFloorRuns(newInfo, uuid, profileuuid, "2" );}
	if(highestCompleted >= 3) {F3 = GetFromAPI.getFloorRuns(newInfo, uuid, profileuuid, "3" );}
	if(highestCompleted >= 4) {F4 = GetFromAPI.getFloorRuns(newInfo, uuid, profileuuid, "4" );}
	if(highestCompleted >= 5) {F5 = GetFromAPI.getFloorRuns(newInfo, uuid, profileuuid, "5" );}
	if(highestCompleted >= 6) {F6 = GetFromAPI.getFloorRuns(newInfo, uuid, profileuuid, "6" );}
	if(highestCompleted >= 7) {F7 = GetFromAPI.getFloorRuns(newInfo, uuid, profileuuid, "7" );}
	
	int FloorList[] = {F0, F1, F2, F3, F4, F5, F6, F7};
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
	String hFNumFin = "";
	if(hFNum != "") hFNumFin = Colors.BLUE+"("+Colors.AQUA+hFNum+Colors.BLUE+")";
	if(mostPlayedFloorNum == F0) floorNum = 0;
	else if(mostPlayedFloorNum == F1){ floorNum = 1;}
	else if(mostPlayedFloorNum == F2){ floorNum = 2;}
	else if(mostPlayedFloorNum == F3){ floorNum = 3;}
	else if(mostPlayedFloorNum == F4){ floorNum = 4;}
	else if(mostPlayedFloorNum == F5){ floorNum = 5;}
	else if(mostPlayedFloorNum == F6){ floorNum = 6;}
	else if(mostPlayedFloorNum == F7){ floorNum = 7;}
	int totalRuns = F0+F1+F2+F3+F4+F5+F6+F7;
	double secretsPerRun = (double) secretsFound / (double) totalRuns;
	
	Utils.sendSpecificMessage(Colors.DARK_RED+"-----------------------------------------------------");
	Utils.sendMessage(Colors.RED+"Dungeon stats for: "+Colors.AQUA+Dungeons.args0);
	Utils.sendSpecificMessage(Colors.AQUA+"Catacombs Level: "+Colors.GOLD+cataLevel);
	Utils.sendSpecificMessage(Colors.AQUA+"Main Class: "+Colors.GOLD+mainClass.substring(0, 1).toUpperCase() + mainClass.substring(1)+Colors.BLUE+" ("+Colors.AQUA+classLevel+Colors.BLUE+")");
	Utils.sendSpecificMessage(Colors.AQUA+"Total Dungeon Runs: "+Colors.GOLD+totalRuns);
	Utils.sendSpecificMessage(Colors.AQUA+"Most Played Floor: "+Colors.GOLD+"Floor "+floorNum+Colors.BLUE+" ("+Colors.AQUA+mostPlayedFloorNum+Colors.BLUE+")");
	Utils.sendSpecificMessage(Colors.AQUA+"Highest Floor: "+Colors.GOLD+"Floor "+highestFloor+" "+hFNumFin);
	Utils.sendSpecificMessage(Colors.AQUA+"Total Secrets: "+Colors.GOLD+secretsFound);
	Utils.sendSpecificMessage(Colors.AQUA+"Average Secrets Per Run: "+Colors.GOLD+df2.format(secretsPerRun));
	if(secretsFound > 10000) secretsFound = 10000;
	if(totalRuns > 1000) totalRuns = 1000;
	int overallScore = ((cataLevel*2)+(secretsFound/100)+(totalRuns/10)+(classLevel*2))/4;
	if(overallScore > 100) overallScore = 100;
	Utils.sendSpecificMessage(Colors.AQUA+"Overall Score: "+Colors.GOLD+overallScore+Colors.GREEN+"%");
	}

}
