package com.cobble.sbp.threads.commands;

import java.util.ArrayList;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.user.ChatRecieveEvent;
import com.cobble.sbp.handlers.UpdatePartyHandler;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;

public class RepartyThread extends Thread {
	
	public void run() {

		Utils.sendMessage("Getting a list of players in your party...");
		int delay = Integer.parseInt(DataGetter.find("pingDelay")+"");
		UpdatePartyHandler.delay = delay;
		new UpdatePartyHandler().start();
		

		try { Thread.sleep(delay); } catch (InterruptedException e) {e.printStackTrace();}
		
		
		Minecraft.getMinecraft().thePlayer.sendChatMessage("/p disband");
		
		ChatRecieveEvent.togglePartyMessage=false;
		try { Thread.sleep(delay/2); } catch (InterruptedException e) {e.printStackTrace();}
		
		if(UpdatePartyHandler.partyMembers.size() == 0) {
			Utils.sendErrMsg("Couldn't find anybody in your party!");
		} else {
			for (int i=0;i<UpdatePartyHandler.partyMembers.size();i++) {
				Minecraft.getMinecraft().thePlayer.sendChatMessage("/p invite "+UpdatePartyHandler.partyMembers.get(i));
				try { Thread.sleep(delay/2); } catch (InterruptedException e) {e.printStackTrace();}
			}
		}
	}
}
