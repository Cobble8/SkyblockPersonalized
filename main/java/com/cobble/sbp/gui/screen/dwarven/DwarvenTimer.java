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

	public static Boolean dwarvenTimerToggle = DataGetter.findBool("dwarvenTimerToggle");
	public static int textColorID = DataGetter.findInt("dwarvenTimerTextColor");
	public static Boolean dwarvenTimerDing = DataGetter.findBool("dwarvenTimerDing");
	
	public static int posX = DataGetter.findInt("dwarvenTimerX");
	public static int posY = DataGetter.findInt("dwarvenTimerY");
	
	Boolean ding = false;
	public static int lastEvent = 0;
	
	ResourceLocation goblin = new ResourceLocation(Reference.MODID, "textures/gui/goblin.png");
	
	public DwarvenTimer(Minecraft mc) {
		
		
			String output = ""; int currTime = (int) (System.currentTimeMillis()); int timeSince = 1200000-(currTime-lastEvent);
			if(lastEvent == -69) {
				output = "N/A";
			} else { if(timeSince < 0) {timeSince=0;} output = Utils.secondsToTime(timeSince); }
			
			GlStateManager.color(1, 1, 1, 1);
			mc.getTextureManager().bindTexture(goblin);
			GlStateManager.enableBlend();
			this.drawModalRectWithCustomSizedTexture(posX, posY, 0, 0, 16, 16, 16, 16);
			GlStateManager.disableBlend();
			
			String color = ""; Boolean chroma = true;
			color = Utils.getColorFromInt(textColorID);
			Utils.drawString(color+output, posX+19, posY+4);
	}
	
}
