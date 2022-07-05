package com.cobble.sbp.events.skyblock;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.gui.screen.dwarven.*;
import com.cobble.sbp.utils.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

import java.util.ArrayList;

public class LobbySwapEvent {

	public static String currLobby = "";
	
	public LobbySwapEvent() {
		DwarvenTimer.lastEvent = -69;
		DwarvenPickaxeTimer.lastUsed = System.currentTimeMillis();
		DwarvenPickaxeTimer.abilityName="lobby";
		DwarvenGui.sentryLast="Unknown";
		CrystalHollowsMap.resetLocs();
		CrystalHollowsMap.waypoints.clear();

		if(DataGetter.findBool("dwarven.forgeReminder.toggle") && !SBP.sbLocation.equals("dwarvenmines")) {
			long time = System.currentTimeMillis();
			String forgeText = Colors.DARK_AQUA+"["+Colors.AQUA+"SBP"+Colors.DARK_AQUA+"] "+Colors.YELLOW+"Your forge item for the "+Colors.AQUA+"Dwarven Mines"+Colors.YELLOW+" in slot";
			ArrayList<Integer> slots = new ArrayList<>();

			for(int i=1;i<=5;i++) {
				long inp = DataGetter.findLong("dwarven.forgeReminder."+i);
				if(inp < time && inp != -1 && inp != -69) {
					slots.add(i);
				}
			}
			StringBuilder slotText = new StringBuilder();
			if(slots.size() > 0) {
				if(slots.size() > 1) {
					forgeText+="s";
					for(int i=0;i<slots.size()-1;i++) {
						slotText.append(Colors.BLUE).append(slots.get(i));
						if(i != slots.size()-2) {
							slotText.append(Colors.YELLOW).append(", ");
						}
					}
					slotText.append(Colors.YELLOW).append(" and ").append(Colors.BLUE).append(slots.get(slots.size() - 1));
				} else {
					slotText = new StringBuilder(Colors.BLUE + slots.get(0));
				}
				String is;
				if(slots.size() > 1) {
					is = " are ";
				} else {
					is = " is ";
				}

				try {
					ChatStyle runCommand = new ChatStyle();
					runCommand.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/warpforge"));
					runCommand.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+"Click to warp to the "+Colors.AQUA+"Forge"+Colors.YELLOW+" in the "+Colors.BLUE+"Dwarven Mines")));
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(forgeText+" "+slotText+Colors.YELLOW+is+ Colors.GREEN+"ready"+Colors.YELLOW+"!").setChatStyle(runCommand));

				} catch(Exception ignored) {}
			}


		}
	}
	
}
