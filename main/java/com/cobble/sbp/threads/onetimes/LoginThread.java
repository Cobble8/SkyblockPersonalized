package com.cobble.sbp.threads.onetimes;

import com.cobble.sbp.events.LobbySwapEvent;
import com.cobble.sbp.events.PlayerLoginEvent;
import com.cobble.sbp.utils.CheckAPIKey;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

public class LoginThread extends Thread {
	
	public static String updateType = "";
	
	public void run() {
		
		
		
		try { CheckAPIKey.checkValidAPIKey(); } catch (Exception e1) { Utils.print("Invalid API Key"); }

		LobbySwapEvent.currLobby = "";
		try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
		try {
		
		if(!CheckAPIKey.validAPIKey) { PlayerLoginEvent.setApiKey(); }
		
		if(!(updateType.equals(""))) {
			Utils.sendMessage(Colors.YELLOW+"There is a new update for "+Reference.NAME+"!");
			Utils.sendSpecificMessage(Colors.YELLOW+"Release type: "+Colors.AQUA+updateType);
			
			String updateUrl = "https://discord.gg/QXA3y5EbNA";
			ChatStyle runCommand = new ChatStyle();
			runCommand.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, updateUrl));
			runCommand.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+updateUrl)));
			Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(Colors.YELLOW+"Click "+Colors.GOLD+"[HERE]"+Colors.YELLOW+" to go to the download!").setChatStyle(runCommand));
		}
		updateType = "";
		} catch(Exception e) {Utils.print("User took longer than 5 seconds to login!");}
		new LobbySwapEvent();
	}
}
