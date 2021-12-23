package com.cobble.sbp.gui.screen.misc;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.ColorUtils;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class JerryTimer extends Gui {

    public static boolean jerryToggle = DataGetter.findBool("misc.jerryTimer.toggle");
    public static int jerryX = DataGetter.findInt("misc.jerryTimer.x");
    public static int jerryY = DataGetter.findInt("misc.jerryTimer.y");
    public static String jerryColor = DataGetter.findStr("misc.jerryTimer.textColor");

    public static long lastJerry = -1;

    public JerryTimer() {
        if(lastJerry == -1) {return;}
        long timeSince = System.currentTimeMillis()-lastJerry;
        long timeUntil = 360000-timeSince;
        String string = Utils.secondsToTime((int) timeUntil);
        if(timeUntil < 0) {string="Available!";}
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/jerry.png"));
        ColorUtils.resetColor();
        Gui.drawModalRectWithCustomSizedTexture(jerryX, jerryY, 0, 0, 25, 24, 25, 24);
        Utils.drawString(ColorUtils.textColor(jerryColor)+string, jerryX+28, jerryY+13-5);
    }

}
