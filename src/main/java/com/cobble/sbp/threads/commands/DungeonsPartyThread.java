package com.cobble.sbp.threads.commands;

import com.cobble.sbp.commands.Dungeons;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.handlers.UpdatePartyHandler;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.GetFromAPI;
import com.cobble.sbp.utils.HttpClient;
import com.cobble.sbp.utils.Utils;

import java.util.ArrayList;

public class DungeonsPartyThread extends Thread {
	
	public void run() {

		Utils.sendMessage(Colors.YELLOW+"Getting your parties dungeons stats...");
		int delay = DataGetter.findInt("command.reparty.delay");
		UpdatePartyHandler.delay = delay;
		new UpdatePartyHandler().start();
		try { Thread.sleep(delay); } catch (InterruptedException e) {e.printStackTrace();}
		ArrayList<String> nameList = new ArrayList();
		ArrayList<String> classList = new ArrayList();
		ArrayList<Integer> cataLevelList = new ArrayList();
		ArrayList<Integer> classLevelList = new ArrayList();
		ArrayList<Integer> overallScoreList = new ArrayList();
		ArrayList<Boolean> apiEnabled = new ArrayList();
		//Utils.sendMessage(partyMemberList.size()+"");
		for(int i=0; i<UpdatePartyHandler.partyMembers.size();i++) {
			apiEnabled.add(true);
			String currMember = UpdatePartyHandler.partyMembers.get(i);//tempArray[0];
			//Utils.sendMessage(currMember);
			String getuuid = "";
			try {
				getuuid = HttpClient.readPage("https://api.mojang.com/users/profiles/minecraft/"+currMember+"?at=%3CcurrentUnixTimestampInSeconds%20-%2030%20days%3E");
			} catch (Exception e1) {apiEnabled.set(i, false); continue;}
			String uuid = GetFromAPI.getUUID(getuuid);
			String newInfo = "";
			
			
			try {
				newInfo = HttpClient.readPage("https://api.hypixel.net/skyblock/profiles?key="+DataGetter.findStr("core.api.key")+"&uuid="+uuid);
			} catch (Exception e1) {apiEnabled.set(i, false); continue;}
			String achievementsInfo = "";
			try {
				achievementsInfo = HttpClient.readPage("https://api.hypixel.net/player?key="+DataGetter.findStr("core.api.key")+"&uuid="+uuid);
			} catch (Exception e1) {apiEnabled.set(i, false); continue;}
			
			
			nameList.add(currMember);
			String profileuuid = GetFromAPI.getCurrProfile(newInfo, GetFromAPI.getUUID(getuuid));
			int classLevel = Dungeons.checkCataLevel(GetFromAPI.getClassXP(newInfo, uuid, profileuuid));
			classLevelList.add(classLevel);
			int cataLevel = Dungeons.checkCataLevel(GetFromAPI.getCataXP(newInfo, GetFromAPI.getUUID(getuuid), profileuuid));
			cataLevelList.add(cataLevel);
			String selectedClass = GetFromAPI.getSelectedDungeonClass(newInfo, GetFromAPI.getUUID(getuuid), profileuuid);
			classList.add(selectedClass);
			
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
			
			int totalRuns = F0+F1+F2+F3+F4+F5+F6+F7;
			if(secretsFound > 10000) secretsFound = 10000;
			if(totalRuns > 1000) totalRuns = 1000;
			int overallScore = ((cataLevel*2)+(secretsFound/100)+(totalRuns/10)+(classLevel*2))/4;
			overallScoreList.add(overallScore);
			
		}
		char healerSymbol = '\u2764';
		char tankSymbol = '\u2748';
		char berserkSymbol = '\u2741';
		char archerSymbol = '\u2623';
		char mageSymbol = '\u270e';


        byte[] bytesH;
        byte[] bytesT;
        byte[] bytesB;
        byte[] bytesA;
        byte[] bytesM;

		
		for(int i=0; i<nameList.size();i++) {
			switch (classList.get(i)) {
				case "healer":
					classList.set(i, Colors.YELLOW + "" + healerSymbol + Colors.GREEN + " Healer");
					break;
				case "tank":
					classList.set(i, Colors.YELLOW + "" + tankSymbol + Colors.GRAY + " Tank");
					break;
				case "berserk":
					classList.set(i, Colors.YELLOW + "" + berserkSymbol + Colors.RED + " Berserk");
					break;
				case "archer":
					classList.set(i, Colors.YELLOW + "" + archerSymbol + Colors.GOLD + " Archer");
					break;
				case "mage":
					classList.set(i, Colors.YELLOW + "" + mageSymbol + Colors.AQUA + " Mage");
					break;
			}
			
			Utils.sendSpecificMessage(Colors.DARK_RED+"-----------------------------------------------------");
			
			if(apiEnabled.get(i)) {
				Utils.sendSpecificMessage(Colors.AQUA+nameList.get(i)+Colors.BLUE+" ("+classList.get(i)+Colors.YELLOW+" "+classLevelList.get(i)+Colors.BLUE+")");
				Utils.sendSpecificMessage(Colors.AQUA+"Cata Level: "+Colors.GOLD+cataLevelList.get(i));
				Utils.sendSpecificMessage(Colors.AQUA+"Overall Score: "+Colors.GOLD+overallScoreList.get(i)+Colors.GREEN+"%");
			} else {
				Utils.sendSpecificMessage(Colors.AQUA+nameList.get(i)+Colors.YELLOW+":"+Colors.RED+" API Disabled");
			}
			
		}
		if(nameList.size() > 0) {Utils.sendSpecificMessage(Colors.DARK_RED+"-----------------------------------------------------");}
		else {
			Utils.sendErrMsg("Couldn't find anybody else in your party!");
		}
	}

}
