package com.cobble.sbp.events.user;

import com.cobble.sbp.SBP;
import com.cobble.sbp.events.skyblock.LobbySwapEvent;
import com.cobble.sbp.threads.misc.LoginThread;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.TextUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class PlayerLoginEvent {

	@SubscribeEvent
	public void onPlayerLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		

		new LobbySwapEvent();
		Thread playerLogin = new LoginThread();
		playerLogin.start();
	}
	
	public static void setApiKey() {
		
		if(!SBP.onSkyblock) {return;}
		ChatStyle runCommand = new ChatStyle();
		runCommand.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/api new"));
		runCommand.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+"Click to run "+Colors.AQUA+"/api new")));
		TextUtils.sendSpecificMessage(Colors.DARK_RED+"-----------------------------------------------------");
		TextUtils.sendMessage(Colors.YELLOW+"Your Hypixel API Key is not setup properly!");
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(Colors.YELLOW+"Type "+Colors.AQUA+"/api new "+Colors.YELLOW+"to set your API key!").setChatStyle(runCommand));
		TextUtils.sendSpecificMessage(Colors.DARK_RED+"-----------------------------------------------------");
		if(SBP.firstLaunch) {
			TextUtils.sendMessage("Hey! It seems like this is your first launch with SkyblockPersonalized! Use "+Colors.AQUA+"/sbp"+Colors.YELLOW+" to get started!");
		}
	
	}
}
