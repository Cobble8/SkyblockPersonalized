package com.cobble.sbp.threads.dwarven;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;

public class GarryTeleportThread extends Thread {

	public void run() {
		Boolean notif = DataGetter.findBool("dwarvenTeleportNotif");
		
		
		
		for(int i=0;i<10;i++) {
			int seconds = 10-i;
			if(notif) { SBP.titleScale=4;SBP.titleString = Colors.GOLD+Colors.BOLD+"Teleport in "+Colors.AQUA+Colors.BOLD+seconds+Colors.GOLD+Colors.BOLD+" seconds"; }
			if(seconds <= 3) {
				Utils.playDingSound();
			}
			try { GarryTeleportThread.sleep(1000);
			} catch (InterruptedException e) { Utils.sendErrMsg("Failed to use teleport delay. Teleporting now."); Minecraft.getMinecraft().thePlayer.sendChatMessage("/garry"); SBP.titleString = ""; continue;}
		}
		Minecraft.getMinecraft().thePlayer.sendChatMessage("/garry");
		SBP.titleString="";
	}
	
}
