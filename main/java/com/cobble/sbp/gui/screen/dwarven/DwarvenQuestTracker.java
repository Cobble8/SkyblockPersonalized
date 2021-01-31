package com.cobble.sbp.gui.screen.dwarven;

import java.util.ArrayList;
import java.util.Collection;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;

public class DwarvenQuestTracker extends Gui {

	
	public static Boolean questTrackToggle = (Boolean) DataGetter.findBool("dwarvenTrackToggle");
	public static Boolean questTrackBarToggle = (Boolean) DataGetter.findBool("dwarvenTrackBarToggle");
	public static int questTrackX = Integer.parseInt(DataGetter.find("dwarvenTrackX")+"");
	public static int questTrackY = Integer.parseInt(DataGetter.find("dwarvenTrackY")+"");
	public static int borderColorID = Integer.parseInt(DataGetter.find("dwarvenTrackBorderColor")+"");
	public static int yesColorID = Integer.parseInt(DataGetter.find("dwarvenTrackYesColor")+"");
	public static int noColorID = Integer.parseInt(DataGetter.find("dwarvenTrackNoColor")+"");
	public static String currString = "";
	
	public DwarvenQuestTracker() {
		String[] stringArray = currString.split(";");
		
		for(int i=0;i<stringArray.length;i++) {
			String currStr = stringArray[i];
			Utils.drawString(currStr, questTrackX, questTrackY+(i*12), 0x10, true);
		}
		
	}
}
