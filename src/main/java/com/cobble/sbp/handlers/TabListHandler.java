package com.cobble.sbp.handlers;

import java.util.*;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.events.skyblock.LobbySwapEvent;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.SBUtils;
import com.cobble.sbp.utils.TextUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public class TabListHandler {



	public TabListHandler() {
		boolean foundPlace = false;
		Collection<NetworkPlayerInfo> playerList = Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap();
	 	RenderGuiEvent.tabNames = new ArrayList<>(playerList);
	 	DwarvenGui.currString="";
	 	DwarvenGui.currCommissions = "";
	 	if(!DataGetter.findBool("dwarven.gui.commissionHideWord")) {
			if(DwarvenGui.commTrackToggle) { DwarvenGui.currString+= Colors.textColor(DwarvenGui.commissionID)+"Commissions;"; }
		}

	 	
	 	String mithrilPowder = "";
	 	String eventOffset = "";
	 	String gemPowder = "";
	 	for(int i=0;i<RenderGuiEvent.tabNames.size();i++) {
	 		
	 		String name;
	 		try { name = RenderGuiEvent.tabNames.get(i).getDisplayName().getUnformattedText(); } catch(Exception e) { continue; }
				if(!foundPlace) {
					String rawName = TextUtils.unformatText(RenderGuiEvent.tabNames.get(i).getDisplayName().getUnformattedText()).replace(" ", "").toLowerCase();
					if(name.toLowerCase().startsWith("area:")) {
						
						String loc = rawName.replace("area:", ""); foundPlace = true;
						
						if(!(loc.equals(SBP.sbLocation))) { SBP.sbLocation = loc; }
						
					} else if((name.toLowerCase().replace(" ", "")).startsWith("dungeon:cata")) { foundPlace=true; SBP.sbLocation = "catacombs";}
					else if(name.toLowerCase().replace(" ", "").startsWith("server:")) {
						String serv = name.toLowerCase().replace(" ", "").replace("server:", "");
						if(!serv.equals(LobbySwapEvent.currLobby)) {
							//Utils.print("'"+LobbySwapEvent.currLobby+"' : '"+serv+"'");
							LobbySwapEvent.currLobby = serv;

							new LobbySwapEvent();
						}
					}
				}//
				
		if(SBP.sbLocation.equals("dwarvenmines") || SBP.sbLocation.equals("crystalhollows")) {
			DwarvenGui.manageCommissions(name);
			manageForge(name);
			if(DwarvenGui.mithrilToggle) {
				if(name.toLowerCase().contains("mithril powder: ")) {
					mithrilPowder = DwarvenGui.manageMithril(name);
				}
				else if(name.toLowerCase().contains("gemstone powder: ")) {
					gemPowder = DwarvenGui.manageGemstone(name);
				}
			}
			
			
		}
			
		}
	 	if(!foundPlace) { SBP.sbLocation = "privateworld"; }
	 	if(SBP.sbLocation.equals("dwarvenmines") || SBP.sbLocation.equals("crystalhollows")) {

	 		//if(DwarvenGui.commTrackToggle) {DwarvenGui.currString+=";";}
	 		DwarvenGui.currString+=mithrilPowder+";";
	 		DwarvenGui.currString+=gemPowder+";";
			if(DwarvenGui.fuelToggle && SBUtils.getSBID().contains("drill")) { DwarvenGui.manageDrillFuel(); }
	 		if(DwarvenTimer.dwarvenTimerToggle) { DwarvenGui.currString+=eventOffset;}
	 		
	 		if(DwarvenTimer.dwarvenTimerToggle) { new DwarvenTimer(DwarvenTimer.posX, DwarvenTimer.posY); }
	 		//Utils.sendMessage(DwarvenGui.currString);
	 	}
	}

	public static void manageForge(String input) {
		for(int i=0;i<5;i++) {
			if(!input.startsWith(" "+(i+1)+")")) {
				continue;
			}
			if(input.contains("EMPTY")) {
				if(DataGetter.findLong("dwarven.forgeReminder."+(i+1)) != -1) {
					ConfigHandler.newObject("dwarven.forgeReminder."+(i+1), -1);
				}
				continue;
			}
			try {
				long output = 0;
				String timeLeft = input.substring(input.indexOf(": ")+2);
				String[] timeArray = timeLeft.split(" ");
				for(String inp : timeArray) {
					if(inp.endsWith("s")) {
						inp = inp.replace("s", "");
						try { output += (1000L *Integer.parseInt(inp)); } catch(Exception ignored) { }
					}
					else if(inp.endsWith("m")) {
						inp = inp.replace("m", "");
						try { output += (60000L *Integer.parseInt(inp)); } catch(Exception ignored) { }
					}
					else if(inp.endsWith("h")) {
						inp = inp.replace("h", "");
						try { output += (3600000L *Integer.parseInt(inp)); } catch(Exception ignored) { }
					}
					else if(inp.endsWith("d")) {
						//Utils.print("DAYS");
						inp = inp.replace("d", "");
						try { output += (86400000L *Integer.parseInt(inp)); } catch(Exception ignored) { }
					}
				}




				/*long timeOfDay = System.currentTimeMillis();
				while(timeOfDay > 86400000) {
					timeOfDay-=86400000;
				}

				timeOfDay+=TimeZone.getDefault().getOffset(timeOfDay);
				timeOfDay+=3600000;*/
				//Utils.print(i);
				long inp = output+System.currentTimeMillis();
				long currSlot = DataGetter.findLong("dwarven.forgeReminder."+(i+1));
				//Utils.print(inp);
				if(currSlot == -1) {
					ConfigHandler.newObject("dwarven.forgeReminder."+(i+1), inp);
				}
				//Utils.print(inp);
				/*long days = TimeUnit.MILLISECONDS.toDays(inp);
				inp-=TimeUnit.DAYS.toMillis(days);
				long hours = TimeUnit.MILLISECONDS.toHours(inp);
				inp-=TimeUnit.HOURS.toMillis(hours);
				long minutes = TimeUnit.MILLISECONDS.toMinutes(inp);
				inp-=TimeUnit.MINUTES.toMillis(minutes);
				long seconds = TimeUnit.MILLISECONDS.toSeconds(inp);
				String amPM = "AM";
				while(days > 0) {
					days--;
					hours -= 24;
				}
				if(hours > 12) {
					amPM = "PM";
					hours-=12;
				}
				String finMin = minutes+"";
				if(finMin.length() < 2) {
					finMin = "0"+finMin;
				}
				Utils.print(input.substring(0, input.indexOf(":"))+" >>> "+hours+":"+finMin+" "+amPM);*/
			} catch(Exception ignored) { }
		}






	}



}
