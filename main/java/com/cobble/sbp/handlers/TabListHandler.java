package com.cobble.sbp.handlers;

import java.util.ArrayList;
import java.util.Collection;

import com.cobble.sbp.SBP;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.events.skyblock.LobbySwapEvent;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;

public class TabListHandler {
	public TabListHandler() {
		Boolean foundPlace = false;
		Collection<NetworkPlayerInfo> playerList = Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap();
	 	RenderGuiEvent.tabNames = new ArrayList<NetworkPlayerInfo>(playerList);
	 	DwarvenGui.currString="";
	 	DwarvenGui.currCommissions = "";
	 	String commissionColor = Utils.getColorFromInt(DwarvenGui.commissionID);
	 	if(DwarvenGui.commTrackToggle) { DwarvenGui.currString+=commissionColor+Colors.BOLD+"Commissions"; }
	 	
	 	String mithrilPowder = "";
	 	String eventOffset = "";
	 	for(int i=0;i<RenderGuiEvent.tabNames.size();i++) {
	 		
	 		String name = "";
	 		try { name = RenderGuiEvent.tabNames.get(i).getDisplayName().getUnformattedText(); } catch(Exception e) { continue; }
				if(!foundPlace) {
					String rawName = Utils.unformatText(RenderGuiEvent.tabNames.get(i).getDisplayName().getUnformattedText()).replace(" ", "").toLowerCase();
					if(name.toLowerCase().startsWith("area:")) {
						
						String loc = rawName.replace("area:", ""); foundPlace = true;
						
						if(!(loc.equals(SBP.sbLocation))) { new LobbySwapEvent(); SBP.sbLocation = loc; }
						
					} else if((name.toLowerCase().replace(" ", "")).startsWith("dungeon:cata")) { foundPlace=true; SBP.sbLocation = "catacombs";}
				}
				
		if(SBP.sbLocation.equals("dwarvenmines")) { 
			DwarvenGui.manageCommissions(name); 
			if(DwarvenGui.mithrilToggle) { if(name.toLowerCase().contains("mithril powder: ")) { mithrilPowder = DwarvenGui.manageMithril(name);}}
			
			
		}
			
		}
	 	if(!foundPlace) { SBP.sbLocation = "privateworld"; }
	 	if(SBP.sbLocation.equals("dwarvenmines")) { 
	 		if(DwarvenGui.fuelToggle && Utils.getSBID().contains("drill")) { DwarvenGui.manageDrillFuel(); }
	 		if(DwarvenGui.commTrackToggle) {DwarvenGui.currString+=";";}
	 		DwarvenGui.currString+=mithrilPowder;
	 		
	 		if(DwarvenTimer.dwarvenTimerToggle) { DwarvenGui.currString+=eventOffset;}
	 		
	 		if(DwarvenTimer.dwarvenTimerToggle) { new DwarvenTimer(DwarvenTimer.posX, DwarvenTimer.posY); }
	 		
	 	}
	}
}
