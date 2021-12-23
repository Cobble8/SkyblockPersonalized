package com.cobble.sbp.gui.screen.dwarven;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.ColorUtils;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

public class DwarvenPickaxeTimer extends Gui {

	public static Boolean onlyWhenHolding = DataGetter.findBool("dwarven.abilityTimer.holdingToggle");
	public static Boolean abilityUsed = false;
	public static Boolean pickTimerToggle = DataGetter.findBool("dwarven.abilityTimer.toggle");
	
	//Mouse Thing Settings
	public static int circleRadius = DataGetter.findInt("dwarven.abilityTimer.radius");
	public static int circleAccuracy = DataGetter.findInt("dwarven.abilityTimer.accuracy");
	public static String circleActiveColor = DataGetter.findStr("dwarven.abilityTimer.activeColor");
	public static String circleCdColor = DataGetter.findStr("dwarven.abilityTimer.cdColor");
	public static String circleReadyColor = DataGetter.findStr("dwarven.abilityTimer.readyColor");
	
	
	public static long lastUsed = 0;	
	public static int HotMLevel = DataGetter.findInt("dwarven.user.hotmLevel");
	public static Boolean isSkymall = false;
	public static String abilityName = "";
	
	
	ResourceLocation settingBorder = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
	
	
	public DwarvenPickaxeTimer() {
		Minecraft mc = Minecraft.getMinecraft();
		int lastUsed2 = (int) lastUsed;
		int currTime = (int) System.currentTimeMillis();
		int timeSince = 0;
		ScaledResolution res = new ScaledResolution(mc);
        
	   	int w = res.getScaledWidth();
	   	int h = res.getScaledHeight();
		
	   	 
		int xC = w/2;
		int yC = h/2;
		//Timings contributed by Erymanthus
		int pickaxeCdTimeSpeed = 105000; //speed boost without hotm
		int pickaxeUsableTimeSpeed = 15000; //speed boost without hotm (pickobulus has no time, is instant use)

		int pickaxeCdTimePickob = 120000; //pickobulus without hotm

		int pickaxeCdTimeSpeedHotm = 100000; //speed boost with hotm cd
		int pickaxeUsableTimeSpeedHotm = 20000; //speed boost with hotm time

		int pickaxeCdTimePickobHotm = 110000; //pickobulus hotm

		int pickaxeCdTime = 0; //initalize time, will change later
		int pickaxeUsableTime = 0; //initalize time, will change later
		
		int lobbySwapCdTime = 60000;

		//isSkymall=true;
		//isSkymall = false;
		
		//idk do some nametag detection here via /hotm
		//*then* do some pickaxe ability detection via reading lore of held item

		// side note from ery: i think it would be better if you could change the values of pickaxeCdTime and pickaxeUsableTime as you
		// detect the item lore and hotm level but to prevent breaking the code i'm making independent boolean variables for now

		if(HotMLevel == 5)
		{
			if(abilityName.equals("speed"))
			{
				pickaxeCdTime = pickaxeCdTimeSpeedHotm;
				pickaxeUsableTime = pickaxeUsableTimeSpeedHotm;
			}
			else if(abilityName.equals("piko"))
			{
				pickaxeCdTime = pickaxeCdTimePickobHotm;
				pickaxeUsableTime = 0;
			}
		} else {
			if(abilityName.equals("speed"))
			{
				pickaxeCdTime = pickaxeCdTimeSpeed;
				pickaxeUsableTime = pickaxeUsableTimeSpeed;
			}
			else if(abilityName.equals("piko"))
			{
				pickaxeCdTime = pickaxeCdTimePickob;
				pickaxeUsableTime = 0;
			}
		}
		
		
		if(abilityName.equals("lobbySwap")) {pickaxeCdTime = lobbySwapCdTime;}
		
		if(isSkymall) { pickaxeCdTime *= 8; pickaxeCdTime /= 10; }
			

		if(!abilityUsed) {
			timeSince = (pickaxeCdTime-(currTime-lastUsed2));
			timeSince+=1000;
		} else {
			timeSince = (pickaxeUsableTime-(currTime-lastUsed2));
			timeSince += 1000;
		}
		if(timeSince < 0) { timeSince = 0; }
		
		
		
		String output = Utils.secondsToTime(timeSince);
		
		GlStateManager.color(1, 1, 1);

		GlStateManager.enableBlend();
		
		
		if(!abilityUsed) {


			
			GlStateManager.color(1, 1, 1, 1);
			if(!(output.equals("0:00"))) {
				try {
						int circlePercent = timeSince*100/pickaxeCdTime;

						ArrayList<Float> tmp = ColorUtils.getColor(circleCdColor);
						mc.getTextureManager().bindTexture(settingBorder);

						Utils.drawAntiAliasPolygon(xC+0.5, yC+0.5, circleRadius, circleAccuracy, 100-circlePercent, tmp.get(0), tmp.get(1), tmp.get(2));
						//Utils.drawRegularPolygon(xC+0.5, yC+0.5, circleRadius, circleAccuracy, circlePercent, r, g, b, 1);
			} catch(Exception ignored) {  }}
			
			mc.getTextureManager().bindTexture(new ResourceLocation("textures/items/iron_pickaxe.png"));
		} else {


			
			GlStateManager.color(1, 1, 1, 1);

				
				try {
					
					int circlePercent = timeSince*100/pickaxeUsableTime;
					ArrayList<Float> tmp = ColorUtils.getColor(circleActiveColor);
					mc.getTextureManager().bindTexture(settingBorder);
					Utils.drawAntiAliasPolygon(xC+0.5, yC+0.5, circleRadius, circleAccuracy, circlePercent, tmp.get(0), tmp.get(1), tmp.get(2));
					//Utils.drawRegularPolygon(xC+0.5, yC+0.5, circleRadius, circleAccuracy, circlePercent, r, g, b, 1);
				} catch(Exception ignored) {  }
				

			mc.getTextureManager().bindTexture(new ResourceLocation("textures/items/gold_pickaxe.png"));
			
		}
		
		if(output.equals("0:00")) {
			if(!abilityUsed) {
				
				GlStateManager.color(1, 1, 1, 1);

					try {
						ArrayList<Float> tmp = ColorUtils.getColor(circleReadyColor);
						
						mc.getTextureManager().bindTexture(settingBorder);
						Utils.drawAntiAliasPolygon(xC+0.5, yC+0.5, circleRadius, circleAccuracy, 100, tmp.get(0), tmp.get(1), tmp.get(2));
						//Utils.drawRegularPolygon(xC+0.5, yC+0.5, circleRadius, circleAccuracy, 100, r, g, b, 1);
						} catch(Exception e) { e.printStackTrace(); }

				mc.getTextureManager().bindTexture(new ResourceLocation("textures/items/diamond_pickaxe.png"));
				
			}
		}
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.color(1, 1, 1, 1);
		
	}
	
}
