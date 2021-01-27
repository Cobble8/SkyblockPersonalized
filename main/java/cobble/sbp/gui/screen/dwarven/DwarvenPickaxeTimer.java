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

	public static long lastUsed = 0;
	public static int pickTimerX = Integer.parseInt(DataGetter.find("pickTimerX")+"");
	public static int pickTimerY = Integer.parseInt(DataGetter.find("pickTimerY")+"");		
	public static int pickTimerColorID = Integer.parseInt(DataGetter.find("pickTimerTextColor")+"");
	public static int pickActiveTimerColorID = Integer.parseInt(DataGetter.find("pickActiveTimerTextColor")+"");
	public static Boolean onlyWhenHolding = (Boolean) DataGetter.find("pickTimerHolding");
	public static Boolean onlyInDwarven = (Boolean) DataGetter.find("pickTimerDwarven");
	public static Boolean abilityUsed = false;
	public static Boolean pickTimerToggle = (Boolean) DataGetter.find("pickTimerToggle");
	public static String barColor = DataGetter.find("pickTimerBarColor")+"";
	
	public DwarvenPickaxeTimer() {
		Minecraft mc = Minecraft.getMinecraft();
		int lastUsed2 = (int) lastUsed;
		int currTime = (int) System.currentTimeMillis();
		int timeSince = 0;
		ScaledResolution res = new ScaledResolution(mc);
        
	   	 int w = res.getScaledWidth();
	   	 int h = res.getScaledHeight();
		
		
		if(!abilityUsed) {
			timeSince = (105000-(currTime-lastUsed2));


		} else {
			timeSince = (15000-(currTime-lastUsed2));
			timeSince += 1000;
			
			
			
			long curr2 = System.currentTimeMillis();
			long time2 = (15000-(curr2-lastUsed))*3/250;
			
	 		String[] temp = barColor.split(";");
	 		float r = Float.parseFloat(temp[0]);
	 		float g = Float.parseFloat(temp[1]);
	 		float b = Float.parseFloat(temp[2]);
			
			GlStateManager.enableBlend();
			mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/progressBar.png"));
			mc.ingameGUI.drawModalRectWithCustomSizedTexture(w/2-91, h-29, 0, 5, 182, 5, 182, 10);
			GlStateManager.color(r, g, b);
			mc.ingameGUI.drawModalRectWithCustomSizedTexture(w/2-91, h-29, 0, 0, (int) time2, 5, 182, 10);
			GlStateManager.disableBlend();
			String text = mc.thePlayer.experienceLevel+"";
			int x = w/2-mc.fontRendererObj.getStringWidth(text)/2;
			int y = h-35;
           Utils.drawString(text, x, y, 8453920, true, true);
			
		}
		
		if(timeSince < 0) { timeSince = 0; }
		String output = Utils.secondsToTime(timeSince);
		
		GlStateManager.color(1, 1, 1);
		
		String activeColor = "";
		
		if(!abilityUsed) {
			mc.getTextureManager().bindTexture(new ResourceLocation("textures/items/iron_pickaxe.png"));
			activeColor = Utils.getColorFromInt(pickTimerColorID);
		} else {
			mc.getTextureManager().bindTexture(new ResourceLocation("textures/items/gold_pickaxe.png"));
			activeColor = Utils.getColorFromInt(pickActiveTimerColorID);
		}
		GlStateManager.enableBlend();
		if(output.equals("0:00")) {
			if(!abilityUsed) {
				output = "Available";
				mc.getTextureManager().bindTexture(new ResourceLocation("textures/items/diamond_pickaxe.png"));
				
			}
		}
		this.drawModalRectWithCustomSizedTexture(pickTimerX, pickTimerY, 0, 0, 16, 16, 16, 16);
		
		
		GlStateManager.disableBlend();
		
		mc.fontRendererObj.drawString(activeColor+output+Colors.WHITE, pickTimerX+20, pickTimerY+5, 0x10, true);
		
		
	}
	
}
