package com.cobble.sbp.gui.screen.dwarven;

import java.util.ArrayList;
import java.util.HashMap;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.*;
import com.cobble.sbp.SBP;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class DwarvenCompletedCommissions extends Gui {




	public static HashMap<Integer, Boolean> slots = new HashMap<>();
	
	public DwarvenCompletedCommissions() {
		Minecraft mc = Minecraft.getMinecraft();
		ArrayList<Float> color = Colors.getColor(DataGetter.findStr("dwarven.doneCommBg.color"));
		float r = color.get(0); float g = color.get(1); float b = color.get(2); float a = color.get(3);

		GlStateManager.enableBlend();


		GlStateManager.pushMatrix();
		GlStateManager.translate(0, 0, 200);
		for(Integer spot : slots.keySet()) {
			mc.getTextureManager().bindTexture(Resources.blank);
			GlStateManager.color(r, g, b, a);

			if(slots.get(spot)) {

				int x = SBP.width/2+(spot*18)-80;
				int y = SBP.height/2-57;
				drawModalRectWithCustomSizedTexture(x, y, 0, 0, 16, 16, 16, 16);
			}
		}
		GlStateManager.popMatrix();
		Colors.resetColor();
		GlStateManager.enableBlend();
	}
}