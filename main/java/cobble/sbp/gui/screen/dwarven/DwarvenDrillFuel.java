package com.cobble.sbp.gui.screen.dwarven;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class DwarvenDrillFuel extends Gui {

	public static int fuelX = Integer.parseInt(DataGetter.find("dwarvenFuelX")+"");
	public static int fuelY = Integer.parseInt(DataGetter.find("dwarvenFuelY")+"");
	public static Boolean fuelInDwarven = (Boolean) DataGetter.find("dwarvenOnlyFuel");
	public static Boolean fuelToggle = (Boolean) DataGetter.find("dwarvenFuelToggle");
	
	
	public DwarvenDrillFuel() {
		int fuelBorderColor = 0;
		int fuelFuelColor = 0;
		int fuelEmptyColor = 0;
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP player = mc.thePlayer;
		
		/*NBTTagCompound display = heldItem.getSubCompound("display", false);
		
        if (display == null || !display.hasKey("Lore")) {
            return;
        }

        NBTTagList lore = display.getTagList("Lore", Constants.NBT.TAG_STRING);*/
		try {
			ItemStack heldItem = player.getHeldItem();
			String color4 = Utils.getColorFromInt(fuelBorderColor-1);
        List<String> lore = heldItem.getTooltip(player, false);
		
        	for(int i=0;i<lore.size();i++) {
        		String curr = Utils.unformatText(lore.get(i));
        		if(curr.startsWith("Fuel:")) {
        			
        			curr = curr.replace(",", "").replace("k", "000").replace("Fuel: ", "");
        			String[] temp = curr.split("/");
        			double currFuel = Integer.parseInt(temp[0]);
        			double totalFuel = Integer.parseInt(temp[1]);
        			
        			Double fuelPercent = currFuel/totalFuel*100;
        			DecimalFormat df = new DecimalFormat("#.##");
        			
        			
        			int finalCurrFuel = (int) Math.round(currFuel);
        			int finalTotalFuel = (int) Math.round(totalFuel);
        			
        			String finalFuelPercent = df.format((Math.round(fuelPercent)));
        			int barProgress = Integer.parseInt(finalFuelPercent);
        			if(barProgress <= 10) {
        				fuelFuelColor = 2;
        				fuelBorderColor = 1;
        			} else if(barProgress > 50) {
        				fuelFuelColor = 5;
        				fuelBorderColor = 6;
        			} else {
        				fuelFuelColor = 4;
        				fuelBorderColor = 3;
        			}
        		
        			
        			
        			String color1 = Utils.getColorFromInt(fuelBorderColor);
        			String color2 = Utils.getColorFromInt(fuelFuelColor);
        			String color3 = Utils.getColorFromInt(fuelEmptyColor);
        			String barString = color1+"[";
        			for(int j=0;j<50;j++) {
        				if(barProgress > j*2) {
        					barString+=color2+"|";
        				} else {
        					barString+=Utils.getColorFromInt(DwarvenQuestTracker.noColorID)+"|";
        				}
        			}
        			barString+=color1+"]";
        			
        			mc.fontRendererObj.drawString(Colors.WHITE+"Drill Fuel: "+color2+barProgress+"% "+color1+"("+color2+finalCurrFuel+color1+"/"+color2+finalTotalFuel+color1+")", fuelX, fuelY, 0x10, true);
        			mc.fontRendererObj.drawString(Colors.WHITE+barString+Colors.WHITE, fuelX, fuelY+12, 0x10, true);
					
        		}
        	}
        
        
       
		} catch(Exception e) {
			return;
		}
		
		
	}
	
}
