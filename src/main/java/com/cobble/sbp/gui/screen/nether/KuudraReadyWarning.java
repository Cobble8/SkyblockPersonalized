package com.cobble.sbp.gui.screen.nether;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.GuiUtils;
import com.cobble.sbp.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class KuudraReadyWarning extends Gui {

    public static boolean ready = false;

    public static void draw() {
        if(KuudraShopPrices.inKuudraFight && !ready && DataGetter.findBool("nether.kuudraReadyWarning.toggle")) {
            try {
                int posX = Minecraft.getMinecraft().displayWidth/(Minecraft.getMinecraft().gameSettings.guiScale);
                int posY = Minecraft.getMinecraft().displayHeight/(Minecraft.getMinecraft().gameSettings.guiScale);
                GuiUtils.drawScaledString(Colors.textColor(DataGetter.findStr("nether.kuudraReadyWarning.textColor"))+"READY UP!", posX/2, posY/2, 0, 3, 0, true);
            } catch (Exception ignored) { }
        }
    }

}
