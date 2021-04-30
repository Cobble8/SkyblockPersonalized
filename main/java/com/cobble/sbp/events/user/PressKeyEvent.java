package com.cobble.sbp.events.user;


import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.handlers.KeyBindingHandler;
import com.cobble.sbp.utils.Utils;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class PressKeyEvent
{
	public static Boolean inCraftMenu = false;

	
    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) throws InterruptedException
    {
    	if(KeyBindingHandler.nextSecret.isPressed()) {
			
				if(SecretImage.currentSecret < SecretImage.maxSecrets-1) {
					SecretImage.currentSecret++;
				}
			else Utils.sendErrMsg("There are no more secrets for this room!");
			
		} else if(KeyBindingHandler.prevSecret.isPressed()) {
				if(SecretImage.currentSecret >= 1) {
					SecretImage.currentSecret--;
				} 
			else Utils.sendErrMsg("There are no rooms with negative secrets!");
		}
		else if(KeyBindingHandler.clearSecret.isPressed()) {
			SecretImage.roomSecretsID="NONE";
		}
    	

    }
}
