package com.cobble.sbp.handlers;


import org.lwjgl.input.Keyboard;

import com.cobble.sbp.utils.Reference;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindingHandler {


	public static KeyBinding lockQuickCraft;
	 
    public static void register()
    {	
    	lockQuickCraft = new KeyBinding("Locks the current quick crafting slot", Keyboard.KEY_L, "SkyBlock Personalized");
    	ClientRegistry.registerKeyBinding(lockQuickCraft);
    }
}
