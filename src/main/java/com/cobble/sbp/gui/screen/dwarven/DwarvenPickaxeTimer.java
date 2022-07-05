package com.cobble.sbp.gui.screen.dwarven;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;

public class DwarvenPickaxeTimer extends Gui {

	public static boolean onlyWhenHolding = DataGetter.findBool("dwarven.abilityTimer.holdingToggle");
	public static boolean abilityUsed = false;
	public static boolean pickTimerToggle = DataGetter.findBool("dwarven.abilityTimer.toggle");
	
	//Mouse Thing Settings
	public static int circleRadius = DataGetter.findInt("dwarven.abilityTimer.radius");
	public static int circleAccuracy = DataGetter.findInt("dwarven.abilityTimer.accuracy");
	public static String circleActiveColor = DataGetter.findStr("dwarven.abilityTimer.activeColor");
	public static String circleCdColor = DataGetter.findStr("dwarven.abilityTimer.cdColor");
	public static String circleReadyColor = DataGetter.findStr("dwarven.abilityTimer.readyColor");
	
	
	public static long lastUsed = 0;	
	public static int hotmLevel = DataGetter.findInt("dwarven.user.hotmLevel");
	public static boolean isSkymall = false;
	public static String abilityName = "";
	
	
	ResourceLocation settingBorder = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
	public static final HashMap<String,MiningAbility> abilities = new HashMap<>();
	public static int miningAbilityLevel = 1;
	
	public DwarvenPickaxeTimer() {


		onlyWhenHolding = DataGetter.findBool("dwarven.abilityTimer.holdingToggle");
		pickTimerToggle = DataGetter.findBool("dwarven.abilityTimer.toggle");
		circleRadius = DataGetter.findInt("dwarven.abilityTimer.radius");
		circleAccuracy = DataGetter.findInt("dwarven.abilityTimer.accuracy");
		circleActiveColor = DataGetter.findStr("dwarven.abilityTimer.activeColor");
		circleCdColor = DataGetter.findStr("dwarven.abilityTimer.cdColor");
		circleReadyColor = DataGetter.findStr("dwarven.abilityTimer.readyColor");
		hotmLevel = DataGetter.findInt("dwarven.user.hotmLevel");

		Minecraft mc = Minecraft.getMinecraft();
		int lastUsed2 = (int) lastUsed;
		int currTime = (int) System.currentTimeMillis();
		int timeSince = 0;
		ScaledResolution res = new ScaledResolution(mc);
        
	   	int w = res.getScaledWidth();
	   	int h = res.getScaledHeight();
		
	   	 
		int xC = w/2;
		int yC = h/2;

		if(abilities.size() == 0) {
			loadValues();
		}

		miningAbilityLevel = 1;
		if(hotmLevel == 5) { miningAbilityLevel++; }
		try {
			String omelette = SBUtils.getExtraAttributes(mc.thePlayer.getHeldItem()).getString("drill_part_upgrade_module");
			//System.out.println(omelette);
			if(omelette.equals("goblin_omelette_blue_cheese")) { miningAbilityLevel++; }
		} catch(Exception ignored) {}



		int pickaxeCdTime = abilities.get(abilityName).getCooldown(miningAbilityLevel);
		int pickaxeUsableTime = abilities.get(abilityName).getDuration(miningAbilityLevel);



		if(isSkymall) { pickaxeCdTime *= 8; pickaxeCdTime /= 10; }
			

		if(!abilityUsed) {
			timeSince = (pickaxeCdTime-(currTime-lastUsed2));
			timeSince+=1000;
		} else {
			timeSince = (pickaxeUsableTime-(currTime-lastUsed2));
			timeSince += 1000;
		}
		if(timeSince < 0) { timeSince = 0; }
		
		
		
		String output = TextUtils.secondsToTime(timeSince);
		
		GlStateManager.color(1, 1, 1);

		GlStateManager.enableBlend();
		
		
		if(!abilityUsed) {


			
			GlStateManager.color(1, 1, 1, 1);
			if(!(output.equals("0:00"))) {
				try {
						int circlePercent = timeSince*100/pickaxeCdTime;

						ArrayList<Float> tmp = Colors.getColor(circleCdColor);
						mc.getTextureManager().bindTexture(settingBorder);

						GuiUtils.drawAntiAliasPolygon(xC+0.5, yC+0.5, circleRadius, circleAccuracy, 100-circlePercent, tmp.get(0), tmp.get(1), tmp.get(2));

			} catch(Exception ignored) {  }}
			
			mc.getTextureManager().bindTexture(new ResourceLocation("textures/items/iron_pickaxe.png"));
		} else {


			
			GlStateManager.color(1, 1, 1, 1);

				
				try {
					
					int circlePercent = timeSince*100/pickaxeUsableTime;
					ArrayList<Float> tmp = Colors.getColor(circleActiveColor);
					mc.getTextureManager().bindTexture(settingBorder);
					GuiUtils.drawAntiAliasPolygon(xC+0.5, yC+0.5, circleRadius, circleAccuracy, circlePercent, tmp.get(0), tmp.get(1), tmp.get(2));

				} catch(Exception ignored) {  }
				

			mc.getTextureManager().bindTexture(new ResourceLocation("textures/items/gold_pickaxe.png"));
			
		}
		
		if(output.equals("0:00")) {
			if(!abilityUsed) {
				
				GlStateManager.color(1, 1, 1, 1);

					try {
						ArrayList<Float> tmp = Colors.getColor(circleReadyColor);
						
						mc.getTextureManager().bindTexture(settingBorder);
						GuiUtils.drawAntiAliasPolygon(xC+0.5, yC+0.5, circleRadius, circleAccuracy, 100, tmp.get(0), tmp.get(1), tmp.get(2));

						} catch(Exception e) { e.printStackTrace(); }

				mc.getTextureManager().bindTexture(new ResourceLocation("textures/items/diamond_pickaxe.png"));
				
			}
		}
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.color(1, 1, 1, 1);
		
	}

	private static void loadValues() {
		abilities.put("lobby", new MiningAbility(60000, 0));
		abilities.put("speed", new MiningAbility(105000, 15000, 100000, 20000, 95000, 25000));
		abilities.put("piko", new MiningAbility(120000, 0, 110000, 0, 110000, 0));
		abilities.put("maniac", new MiningAbility(50000, 10000, 44000, 15000, 39000, 20000));
		abilities.put("vein", new MiningAbility(48000, 12000, 46000, 14000, 44000, 16000));
	}
	
}
