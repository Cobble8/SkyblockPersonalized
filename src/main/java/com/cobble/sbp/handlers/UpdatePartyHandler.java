package com.cobble.sbp.handlers;

import java.util.ArrayList;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.user.ChatRecieveEvent;
import com.cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;

public class UpdatePartyHandler extends Thread {
	public static ArrayList<String> partyMembers = new ArrayList();
	public static int delay = DataGetter.findInt("command.reparty.delay");
			
	
	
	public void run() {
		if(SBP.onSkyblock) {
			Utils.print("Updating Party Member List...");
			UpdatePartyHandler.partyMembers.clear();
			ChatRecieveEvent.togglePartyMessage=true;
			Minecraft.getMinecraft().thePlayer.sendChatMessage("/pl");
			try { Thread.sleep(delay); } catch (InterruptedException e) {e.printStackTrace();}
			ChatRecieveEvent.togglePartyMessage=false;
			
			
		}
	}
	
	
}
