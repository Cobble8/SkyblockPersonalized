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
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class DwarvenDrillFuel extends Gui {

	public static int fuelX = DataGetter.findInt("dwarvenFuelX");
	public static int fuelY = DataGetter.findInt("dwarvenFuelY");
	public static Boolean fuelInDwarven = DataGetter.findBool("dwarvenOnlyFuel");
	public static Boolean fuelToggle =  DataGetter.findBool("dwarvenFuelToggle");
	public static int searchForItem = 0;
	public static String heldName = "";
	public static String currString = "";
	
	
	public DwarvenDrillFuel() {
		
		String[] stringArray = currString.split(";");
		
		for(int i=0;i<stringArray.length;i++) {
			String currStr = stringArray[i];
			Utils.drawString(currStr+Colors.WHITE, fuelX, fuelY+(i*12), 0x10, true);
		}
	}
	
}
