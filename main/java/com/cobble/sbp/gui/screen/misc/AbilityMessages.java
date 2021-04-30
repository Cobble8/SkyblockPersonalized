package com.cobble.sbp.gui.screen.misc;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class AbilityMessages extends Gui {

	public static Boolean abilityMsgToggle = DataGetter.findBool("abilityDamageToggle");
	public static int x = DataGetter.findInt("abilityDamageX");
	public static int y = DataGetter.findInt("abilityDamageY");
	public static int delay = DataGetter.findInt("abilityDamagePoof");
	public static String lastDamage = "";
	public static int damageMsgCount = 0;
	
	public AbilityMessages(int posX, int posY) {
		if(lastDamage.equals("")) {return;}
		
		Minecraft mc = Minecraft.getMinecraft();
		
		Utils.drawString(lastDamage, posX, posY);
		
	}
	
	
}
