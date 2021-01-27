package com.cobble.sbp.threads.onetimes;

import java.util.ArrayList;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.ChatRecieveEvent;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;

public class RepartyThread extends Thread {
public static ArrayList<String> nameList = new ArrayList();
public static int delay = 1000;
	
	public void run() {
		delay = Integer.parseInt(DataGetter.find("pingDelay")+"");
		Utils.sendMessage("Getting a list of players in your party...");
		nameList.clear();
		ChatRecieveEvent.currentPartyUsing="re";
		ChatRecieveEvent.togglePartyMessage=true;
		
		Minecraft.getMinecraft().thePlayer.sendChatMessage("/pl");
		try { Thread.sleep(delay); } catch (InterruptedException e) {e.printStackTrace();}
		
		
		Minecraft.getMinecraft().thePlayer.sendChatMessage("/p disband");
		
		ChatRecieveEvent.togglePartyMessage=false;
		try { Thread.sleep(delay/2); } catch (InterruptedException e) {e.printStackTrace();}
		
		if(nameList.size() == 0) {
			Utils.sendErrMsg("Couldn't find anybody in your party!");
		} else {
			for (int i=0;i<RepartyThread.nameList.size();i++) {
				Minecraft.getMinecraft().thePlayer.sendChatMessage("/p invite "+RepartyThread.nameList.get(i));
				try { Thread.sleep(delay/2); } catch (InterruptedException e) {e.printStackTrace();}
			}
		}
	}
}
