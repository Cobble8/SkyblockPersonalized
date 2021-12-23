package com.cobble.sbp.events.skyblock;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.gui.screen.dwarven.*;
import com.cobble.sbp.utils.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

public class LobbySwapEvent {

	public static String currLobby = "";
	
	public LobbySwapEvent() {
		DwarvenTimer.lastEvent = -69;
		DwarvenPickaxeTimer.lastUsed = System.currentTimeMillis();
		DwarvenPickaxeTimer.abilityName="lobbySwap";
		SecretImage.roomSecretsID="NONE";
		DwarvenGui.sentryLast="Unknown";
		CrystalHollowsMap.resetLocs();
		CrystalHollowsMap.waypoints.clear();

		if(DataGetter.findBool("dwarven.forgeReminder.toggle") && !SBP.sbLocation.equals("dwarvenmines")) {
			long time = System.currentTimeMillis();
			for(int i=1;i<=5;i++) {
				long inp = DataGetter.findLong("dwarven.forgeReminder."+i);
				if(inp < time && inp != -1 && inp != -69) {
					try {
						ChatStyle runCommand = new ChatStyle();
						runCommand.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/warpforge"));
						runCommand.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+"Click to warp to the "+Colors.AQUA+"Forge"+Colors.YELLOW+" in the "+Colors.BLUE+"Dwarven Mines")));
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(Colors.DARK_AQUA+"["+Colors.AQUA+"SBP"+Colors.DARK_AQUA+"] "+Colors.YELLOW+"Your forge item for the "+Colors.AQUA+"Dwarven Mines"+Colors.YELLOW+" in slot "+Colors.BLUE+i+Colors.YELLOW+" is "+ Colors.GREEN+"ready"+Colors.YELLOW+"!").setChatStyle(runCommand));

					} catch(Exception ignored) {}
					}
			}
		}
	}
	
}
