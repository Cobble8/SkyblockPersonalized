package com.cobble.sbp.gui.screen.dwarven;

import java.util.ArrayList;
import java.util.Collection;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class DwarvenTimer extends Gui {

	public static Boolean dwarvenTimerToggle = (Boolean) DataGetter.find("dwarvenTimerToggle");
	public static int textColorID = Integer.parseInt(""+DataGetter.find("dwarvenTimerTextColor"));
	public static int timerX = Integer.parseInt(""+DataGetter.find("dwarvenTimerX"));
	public static int timerY = Integer.parseInt(""+DataGetter.find("dwarvenTimerY"));
	public static Boolean dwarvenTimerDing = (Boolean) DataGetter.find("dwarvenTimerDing");
	Boolean ding = false;
	public static int lastEvent = 0;
	
	ResourceLocation goblin = new ResourceLocation(Reference.MODID, "textures/gui/goblin.png");
	
	public DwarvenTimer(Minecraft mc) {
		
		if(SBP.sbLocation.equals("dwarvenmines")) {
			String output = "";
			int currTime = (int) (System.currentTimeMillis());
			int timeSince = 1200000-(currTime-lastEvent);
			if(lastEvent == -69) {
				output = "N/A";
			} else {

				if(timeSince < 0) {timeSince=0;}
				output = Utils.secondsToTime(timeSince);
			}
			
			
			mc.getTextureManager().bindTexture(goblin);
			GlStateManager.enableBlend();
			this.drawModalRectWithCustomSizedTexture(timerX, timerY, 0, 0, 25, 25, 25, 25);
			GlStateManager.disableBlend();
			mc.fontRendererObj.drawString(Utils.getColorFromInt(textColorID)+output+Colors.WHITE, timerX+27, timerY+7, 0x10, true);
		}
	}
	
}
