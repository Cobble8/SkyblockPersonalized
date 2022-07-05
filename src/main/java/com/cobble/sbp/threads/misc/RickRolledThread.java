package com.cobble.sbp.threads.misc;

import com.cobble.sbp.SBP;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

public class RickRolledThread extends Thread {


	
	
	public void run() {

		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(Colors.DARK_AQUA+"["+Colors.AQUA+"SBP"+Colors.DARK_AQUA+"] "+Colors.YELLOW+"Click ").appendSibling(new ChatComponentText(Colors.GREEN+"[HERE]").setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sbp disableHoeWarning")).setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.RED+"[WARNING]: This will permanently disable the warning!"))))).appendSibling(new ChatComponentText(Colors.YELLOW+" to disable the warning!")));
		SBP.titleScale = 1.5;
		SBP.titleString = Colors.DARK_AQUA+"["+Colors.AQUA+"SBP"+Colors.DARK_AQUA+"] "+Colors.YELLOW+"Turn off 'Block Hoe Right Clicks' to view the crafting recipe of this farming hoe.";

		try { Thread.sleep(10000); } catch(Exception ignored) {}
		SBP.titleScale = 4;
		SBP.titleString = "";

	}
	
	
	
	
}
