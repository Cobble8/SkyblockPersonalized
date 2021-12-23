package com.cobble.sbp.gui.screen.dwarven;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.ColorUtils;
import com.cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.text.DecimalFormat;

public class CrystalLobbyDay extends Gui {

    public static Boolean showDays = DataGetter.findBool("dwarven.crystalDay.toggle");
    public static String daysColor1 = DataGetter.findStr("dwarven.crystalDay.textColor");
    public static String daysColor2 = DataGetter.findStr("dwarven.crystalDay.numColor");
    public static Boolean onlyInCrystal = DataGetter.findBool("crystalDayOnly");
    public static int daysX = DataGetter.findInt("dwarven.crystalDay.x");
    public static int daysY = DataGetter.findInt("dwarven.crystalDay.y");

    public CrystalLobbyDay() {

        boolean pass = false;
        if(onlyInCrystal) {
            if(SBP.sbLocation.equals("crystalhollows")) {
                pass=true;
            }
        } else {
            pass=true;
        }
        if(!showDays) {pass=false;}
        if(pass) {
            String clr1 = ColorUtils.textColor(daysColor1);
            String clr2 = ColorUtils.textColor(daysColor2);
            double time = Minecraft.getMinecraft().theWorld.getWorldTime()/24000d;
            String finalTime = new DecimalFormat("#.##").format(time);
            Utils.drawString(clr1+"Day: "+clr2+finalTime, daysX, daysY);
        }


    }


}
