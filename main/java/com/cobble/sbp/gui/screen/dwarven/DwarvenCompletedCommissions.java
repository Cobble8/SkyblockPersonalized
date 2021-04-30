package com.cobble.sbp.gui.screen.dwarven;

import java.util.ArrayList;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.utils.ColorUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class DwarvenCompletedCommissions extends Gui {


	public static ArrayList<String> completedSlots = new ArrayList();
	String bgColor = DataGetter.findStr("dwarvenCompletedColor");
	
	
	public DwarvenCompletedCommissions() {
		Minecraft mc = Minecraft.getMinecraft();
		int w = mc.currentScreen.width;
		int h = new ScaledResolution(mc).getScaledHeight();
		
		for(int i=0;i<completedSlots.size();i++) {
			//
			//Utils.print(completedSlots.get(i));
			try {
				int currSlot = Integer.parseInt(completedSlots.get(i))-8;
				
				ArrayList<Float> color = ColorUtils.getColor(DataGetter.findStr("dwarvenCommBgColor"));
				float r = color.get(0);
				float g = color.get(1);
				float b = color.get(2);
				
				RenderGuiEvent.addHighlightSlot(w/2+(currSlot*18)-98, h/2-21-36, r, g, b, 1, false);
			} catch(Exception e) { }
			
		}
		
		
		
		
	}

}
