package com.cobble.sbp.gui.screen.misc;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;

public class ComboMessages {
	public static Boolean abilityMsgToggle = DataGetter.findBool("comboMsgToggle");
	public static int x = DataGetter.findInt("comboMsgX");
	public static int y = DataGetter.findInt("comboMsgY");
	public static int delay = DataGetter.findInt("comboMsgPoof");
	public static String lastCombo = "";
	public static int comboMsgCount = 0;
	
	public ComboMessages(int posX, int posY) {
		if(lastCombo.equals("")) {return;}
		
		Minecraft mc = Minecraft.getMinecraft();
		
		Utils.drawString(lastCombo, posX, posY);
		
	}
}
