package com.cobble.sbp.gui.screen.dwarven;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DwarvenPickaxeTimer extends Gui {

	
	public static int pickTimerX = DataGetter.findInt("pickTimerX");
	public static int pickTimerY = DataGetter.findInt("pickTimerY");
	
	public static int pickTimerColorID = Integer.parseInt(DataGetter.find("pickTimerTextColor")+"");
	public static int pickActiveTimerColorID = Integer.parseInt(DataGetter.find("pickActiveTimerTextColor")+"");
	public static Boolean onlyWhenHolding = DataGetter.findBool("pickTimerHolding");
	public static Boolean abilityUsed = false;
	public static Boolean pickTimerToggle = DataGetter.findBool("pickTimerToggle");
	public static Boolean pickTimerGui = DataGetter.findBool("pickTimerGui");;
	
	//Mouse Thing Settings
	public static Boolean circle = DataGetter.findBool("pickTimerCircle");
	public static int circleRadius = DataGetter.findInt("pickTimerCircleRadius");
	public static int circleAccuracy = DataGetter.findInt("pickTimerCircleAcc");
	public static String circleActiveColor = DataGetter.findStr("pickTimerCircleActive");
	public static String circleCdColor = DataGetter.findStr("pickTimerCircleCd");
	public static String circleReadyColor = DataGetter.findStr("pickTimerCircleReady");
	
	
	public static long lastUsed = 0;	
	public static int HotMLevel = DataGetter.findInt("dwarvenHOTMLevel");
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
		
		if(isSkymall) { pickaxeCdTime *= (80/100); }
			

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
		
		String activeColor = "";
		GlStateManager.enableBlend();
		
		
		if(!abilityUsed) {

			
			activeColor = Utils.getColorFromInt(pickTimerColorID);
			
			GlStateManager.color(1, 1, 1, 1);
			if(circle && !(output.equals("0:00"))) {
				try {
						int circlePercent = timeSince*100/pickaxeCdTime;
						String[] tmp = circleCdColor.split(";");
						float r = Float.parseFloat(tmp[0].replace(",", "."));
						float g = Float.parseFloat(tmp[1].replace(",", "."));
						float b = Float.parseFloat(tmp[2].replace(",", "."));
						mc.getTextureManager().bindTexture(settingBorder);
						Utils.drawRegularPolygon(xC+0.5, yC+0.5, circleRadius, circleAccuracy, circlePercent, r, g, b);
			} catch(Exception e) {  }}
			
			mc.getTextureManager().bindTexture(new ResourceLocation("textures/items/iron_pickaxe.png"));
		} else {

			
			activeColor = Utils.getColorFromInt(pickActiveTimerColorID);
			
			GlStateManager.color(1, 1, 1, 1);
			if(circle) {
				
				try {
					
					int circlePercent = timeSince*100/pickaxeUsableTime;
					String[] tmp = circleActiveColor.split(";");
					float r = Float.parseFloat(tmp[0].replace(",", "."));
					float g = Float.parseFloat(tmp[1].replace(",", "."));
					float b = Float.parseFloat(tmp[2].replace(",", "."));
					mc.getTextureManager().bindTexture(settingBorder);
					Utils.drawRegularPolygon(xC+0.5, yC+0.5, circleRadius, circleAccuracy, circlePercent, r, g, b);
				} catch(Exception e) {  }
				
			}
			mc.getTextureManager().bindTexture(new ResourceLocation("textures/items/gold_pickaxe.png"));
			
		}
		
		if(output.equals("0:00")) {
			if(!abilityUsed) {
				activeColor = Utils.getColorFromInt(pickActiveTimerColorID);
				
				GlStateManager.color(1, 1, 1, 1);
				if(circle) {
					try {
						String[] tmp = circleReadyColor.split(";");
						float r = Float.parseFloat(tmp[0].replace(",", "."));
						float g = Float.parseFloat(tmp[1].replace(",", "."));
						float b = Float.parseFloat(tmp[2].replace(",", "."));
						
						mc.getTextureManager().bindTexture(settingBorder);
						Utils.drawRegularPolygon(xC+0.5, yC+0.5, circleRadius, circleAccuracy, 100, r, g, b);
						} catch(Exception e) { e.printStackTrace(); }
				}
				output = "Available";
				mc.getTextureManager().bindTexture(new ResourceLocation("textures/items/diamond_pickaxe.png"));
				
			}
		}
		GlStateManager.color(1, 1, 1, 1);
		if(pickTimerGui) {
			GlStateManager.enableBlend();

			
			this.drawModalRectWithCustomSizedTexture(pickTimerX, pickTimerY, 0, 0, 16, 16, 16, 16);
			
			Utils.drawString(activeColor+output, pickTimerX+20, pickTimerY+5);
		}
		GlStateManager.color(1, 1, 1, 1);
		
	}
	
}
