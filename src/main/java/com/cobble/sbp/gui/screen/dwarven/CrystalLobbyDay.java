package com.cobble.sbp.gui.screen.dwarven;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.text.DecimalFormat;

public class CrystalLobbyDay extends Gui {

    public static boolean showDays = DataGetter.findBool("dwarven.crystalDay.toggle");
    public static String daysColor1 = DataGetter.findStr("dwarven.crystalDay.textColor");
    public static String daysColor2 = DataGetter.findStr("dwarven.crystalDay.numColor");
    public static boolean onlyInCrystal = DataGetter.findBool("crystalDayOnly");
    public static int daysX = DataGetter.findInt("dwarven.crystalDay.x");
    public static int daysY = DataGetter.findInt("dwarven.crystalDay.y");

    public CrystalLobbyDay() {

        showDays = DataGetter.findBool("dwarven.crystalDay.toggle");
        daysColor1 = DataGetter.findStr("dwarven.crystalDay.textColor");
        daysColor2 = DataGetter.findStr("dwarven.crystalDay.numColor");
        onlyInCrystal = DataGetter.findBool("crystalDayOnly");
        daysX = DataGetter.findInt("dwarven.crystalDay.x");
        daysY = DataGetter.findInt("dwarven.crystalDay.y");


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
            String clr1 = Colors.textColor(daysColor1);
            String clr2 = Colors.textColor(daysColor2);
            double time = Minecraft.getMinecraft().theWorld.getWorldTime()/24000d;
            String finalTime = new DecimalFormat("#.##").format(time);


            GuiUtils.drawString(clr1+"Day: "+clr2+finalTime, daysX, daysY);
        }


    }


}
