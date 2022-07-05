package com.cobble.sbp.threads.misc;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.skyblock.LobbySwapEvent;
import com.cobble.sbp.events.user.PlayerLoginEvent;
import com.cobble.sbp.utils.*;

import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

public class LoginThread extends Thread {
	
	public static String updateType = "";
	
	public void run() {
		
		
		
		try { WebUtils.checkValidAPIKey(); } catch (Exception e1) { Utils.print("Invalid API Key"); }

		LobbySwapEvent.currLobby = "";
		try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
		try {
		
		if(!WebUtils.validAPIKey && !Minecraft.getMinecraft().isSingleplayer()) { PlayerLoginEvent.setApiKey(); }
		
		if(!(updateType.equals(""))) {
			Utils.print("There is a new update for "+Reference.NAME+"!");
			if(!DataGetter.findBool("misc.updateNotifs.toggle")) {return;}
			TextUtils.sendMessage(Colors.YELLOW+"There is a new update for "+Reference.NAME+"!");
			TextUtils.sendSpecificMessage(Colors.YELLOW+"Release type: "+Colors.AQUA+updateType);
			

			ChatStyle runCommand = new ChatStyle();
			runCommand.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sbp update"));
			runCommand.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+"Click to update "+Colors.AQUA+"SkyblockPersonalized"+Colors.YELLOW+"!")));
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(Colors.YELLOW+"Click "+Colors.GOLD+"[HERE]"+Colors.YELLOW+" to download the latest version!").setChatStyle(runCommand));



		}
		updateType = "";
		} catch(Exception e) {Utils.print("User took longer than 5 seconds to login!");}
		new LobbySwapEvent();
	}
}
