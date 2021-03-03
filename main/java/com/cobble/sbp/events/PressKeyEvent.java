package com.cobble.sbp.events;


import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.handlers.KeyBindingHandler;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import scala.swing.event.Key;

public class PressKeyEvent
{
	public static Boolean inCraftMenu = false;

	
    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) throws InterruptedException
    {
    	
    	

    }
}
