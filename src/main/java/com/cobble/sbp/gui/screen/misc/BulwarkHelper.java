package com.cobble.sbp.gui.screen.misc;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

public class BulwarkHelper extends Gui {

    public static long lastStart = 0;
    public static boolean reaperSet = false;
    public static boolean bulwark = false;
    public static void run() {
        try {
            Minecraft mc = Minecraft.getMinecraft();
            if(!DataGetter.findBool("qol.enrageDisplay.toggle")) { return; }
            int x = DataGetter.findInt("qol.enrageDisplay.x");
            int y = DataGetter.findInt("qol.enrageDisplay.y");
            if(x == -1 && y == -1) {
                ConfigHandler.newObject("qol.enrageDisplay.x", SBP.width/2);
                ConfigHandler.newObject("qol.enrageDisplay.y", 15);
            }


            reaperSet = SBUtils.getSBID(mc.thePlayer.getCurrentArmor(0)).equals("reaper_boots") && SBUtils.getSBID(mc.thePlayer.getCurrentArmor(1)).equals("reaper_leggings") && SBUtils.getSBID(mc.thePlayer.getCurrentArmor(2)).equals("reaper_chestplate");


            if(reaperSet) {
                boolean tempBulwark = mc.thePlayer.getCurrentArmor(0).getSubCompound("display",false).getInteger("color") == 16711680;
                if(!bulwark && tempBulwark) { lastStart = System.currentTimeMillis(); }
                bulwark = tempBulwark;
                long timeSince = 25000-(System.currentTimeMillis()-lastStart);
                if(DataGetter.findBool("qol.enrageDisplay.toggle")) {
                    String displayText;
                    if(timeSince < 0) { displayText = Colors.textColor(DataGetter.findStr("qol.enrageDisplay.availableTextColor"))+"Available!";
                    } else {  displayText = Colors.textColor(DataGetter.findStr("qol.enrageDisplay.textColor"))+TextUtils.secondsToTime(timeSince+1000); }

                    GlStateManager.pushMatrix();
                    GlStateManager.scale(2,2,2);
                    GuiUtils.drawString(displayText, (x-mc.fontRendererObj.getStringWidth(displayText))/2, y, 1);
                    GlStateManager.popMatrix();
                }


            } else {
                bulwark = false;
            }
            if(bulwark && DataGetter.findBool("qol.enrageDisplay.tintToggle")) {
                mc.getTextureManager().bindTexture(Resources.bulwarkTexture);
                GlStateManager.color(1,0,0, 0.25f);
                drawModalRectWithCustomSizedTexture(0,0,0,0,SBP.width,SBP.height,SBP.width,SBP.height);
            }

        } catch(Exception e) {
            Utils.printDev(e);
        }
    }

}
