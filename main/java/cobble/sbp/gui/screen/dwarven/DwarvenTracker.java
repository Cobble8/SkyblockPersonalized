package com.cobble.sbp.gui.screen.dwarven;

import java.util.ArrayList;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class DwarvenTracker extends Gui {

	public static Boolean dwarvenLootToggle = false;
	public static int dwarvenLootX = 5;
	public static int dwarvenLootY = 5;
	public static ArrayList<String> dwarvenLootList = new ArrayList();
	public static ArrayList<Integer> dwarvenLootCount = new ArrayList();
	
	public DwarvenTracker(Minecraft mc) {
		
		//loadDwarvenLoot();
		
		for(int i=0;i<dwarvenLootList.size();i++) {
			String output = dwarvenLootList.get(i)+": "+dwarvenLootCount.get(i);
			mc.fontRendererObj.drawString(output, dwarvenLootX, dwarvenLootY+(i*12), 0x10, true);
		}
		mc.fontRendererObj.drawString(Colors.WHITE+"", dwarvenLootX, dwarvenLootY, 0x10, true);
		
		
		
		
	}
	
	public static void addOne(String configVal) {
		
		ConfigHandler.newObject(configVal, (Integer.parseInt(DataGetter.find(configVal)+""))+1);
		
	}
	
	public static void loadDwarvenLoot() {
		dwarvenLootList.clear();
		dwarvenLootCount.clear();
		
		dwarvenLootList.add(Colors.GOLD+"Treasurite");
		//dwarvenLootList.add(Colors.GREEN+"Goblin Egg");
		
		for(int i=0;i<dwarvenLootList.size();i++) {
			dwarvenLootCount.add(0);
		}
	}
	
}
