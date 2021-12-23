package com.cobble.sbp.gui.screen.misc;

import com.cobble.sbp.SBP;

import com.cobble.sbp.utils.ColorUtils;
import com.cobble.sbp.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;


import java.util.HashMap;

public class LockedSlots extends Gui {

    public static HashMap<Integer, Boolean> slots = new HashMap<>();
    public static ResourceLocation lock = new ResourceLocation(Reference.MODID, "textures/gui/lock.png");

    public LockedSlots() {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(lock);
        GlStateManager.enableBlend();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 260);
        GlStateManager.color(1, 1, 1, 0.85f);
        for(Integer i : slots.keySet()) {
            if(slots.get(i)) {
                int x = SBP.width/2-8+54;
                int y = SBP.height/2-3+(i*18)-72;

                drawModalRectWithCustomSizedTexture(x, y, 0, 0, 16, 16, 16, 16);
            }
        }
        GlStateManager.popMatrix();
        ColorUtils.resetColor();
    }

}
