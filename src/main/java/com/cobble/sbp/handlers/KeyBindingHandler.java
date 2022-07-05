package com.cobble.sbp.handlers;


import org.lwjgl.input.Keyboard;

import com.cobble.sbp.utils.Reference;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindingHandler {


	public static KeyBinding lockQuickCraft;
	public static KeyBinding addMiningPoint;
	public static KeyBinding undoMiningPoint;
    public static void register()
    {	
    	lockQuickCraft = new KeyBinding("Locks the current quick crafting slot", Keyboard.KEY_L, Reference.NAME);
    	ClientRegistry.registerKeyBinding(lockQuickCraft);
		/*addMiningPoint = new KeyBinding("Adds a point to the current Mining Path", Keyboard.KEY_P, Reference.NAME);
		ClientRegistry.registerKeyBinding(addMiningPoint);
		undoMiningPoint = new KeyBinding("Removes the most recent point in your Mining Path", Keyboard.KEY_O, Reference.NAME);
		ClientRegistry.registerKeyBinding(undoMiningPoint);*/


    }
}
