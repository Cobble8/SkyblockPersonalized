package com.cobble.sbp.events.user;

import com.cobble.sbp.gui.screen.dwarven.MiningPath;
import com.cobble.sbp.handlers.KeyBindingHandler;
import com.cobble.sbp.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class PressKeyEvent
{
	public static boolean inCraftMenu = false;
    public static MiningPath path = new MiningPath();
	
    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {
        /*if(KeyBindingHandler.addMiningPoint.isPressed()) {
            try {
                MovingObjectPosition mop = Minecraft.getMinecraft().objectMouseOver;
                TextUtils.sendMessage(mop.getBlockPos());
                path.addPoint(mop);
            } catch(Exception e) {
                e.printStackTrace();
                TextUtils.sendErrMsg("No block found!");
            }
        } else if(KeyBindingHandler.undoMiningPoint.isPressed()) {
            path.clearPoints();
            TextUtils.sendErrMsg("Points cleared");
        }*/
    }
}
