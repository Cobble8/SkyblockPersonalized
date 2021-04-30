package com.cobble.sbp.events.user;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.handlers.KeyBindingHandler;
import com.cobble.sbp.threads.misc.RickRolledThread;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import com.google.common.eventbus.Subscribe;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MenuClickEvent {

	public static Boolean inMenu = false;
	public static ArrayList<String> itemList = new ArrayList();
	public static int mouseX = 0;
	public static int mouseY = 0;
	
	
	@SubscribeEvent
	public void onClick(PlayerInteractEvent event) {
		if(SBP.onSkyblock) {
			
			
			if(event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {;
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				String heldItem = Utils.getSBID();
				if(heldItem.contains("theoretical_hoe_") && DataGetter.findBool("blockHoeClicks")) {
					new RickRolledThread().start();
					event.setCanceled(true);
					Minecraft.getMinecraft().thePlayer.playSound("note.bass", 1, 0.2F);
				}
			}	
		}
	}
	
	
	
	@SubscribeEvent
	public void onMenuClick(GuiScreenEvent.MouseInputEvent.Pre event) {
		//Utils.print("e");
		if(Mouse.getEventButtonState()) {
			if(RenderGuiEvent.currMenu.equals("craft item") && DataGetter.findBool("toggleBlockedQuickCrafts")) {
				String[] blockedCraftList = DataGetter.findStr("blockedQuickCrafts").split(";");
				ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		        Minecraft mc = Minecraft.getMinecraft();
		        try {
		        	int w = mc.currentScreen.width;
				   	int h = mc.currentScreen.height;
				   	for(int i=0;i<3;i++) {
				   		
				   		int currSlot = 16+(i*9);
				   		String currItem = Utils.unformatAllText(itemList.get(currSlot)).toLowerCase().replace(" ", "_");
				   		
				   		
				   		if(mouseX >= w/2-9+54 && mouseX <= w/2+9+54 && mouseY >= h/2-3-72+(i*18) && mouseY <= h/2+15-72+(i*18)) {
				   			//Utils.sendMessage(currItem);
				   			Boolean passThrough = false;
				   			for(int j=0;j<blockedCraftList.length;j++) {
				   				if(blockedCraftList[j].equals(currItem)) { passThrough = true; continue; }
				   			}
				   			if(passThrough) {
				   				Minecraft.getMinecraft().thePlayer.playSound("note.pling", 1, 0.0F);
								if(event.isCancelable()) { event.setCanceled(true); return; }
				   			}
					   		
					   	}
				   	}
				   	
				   	
		        } catch(Exception e) { Utils.sendErrMsg("Something has gone wrong with the disable quick crafting recipes!"); e.printStackTrace();}
				
			}
		}
	}
	
	
	@SubscribeEvent
	public void onKeyPress(GuiScreenEvent.KeyboardInputEvent.Pre event) {
		if(!RenderGuiEvent.currMenu.equals("craft item") || !DataGetter.findBool("toggleBlockedQuickCrafts")) {return;}
    	if(Keyboard.isKeyDown(KeyBindingHandler.lockQuickCraft.getKeyCode())) {
    		
    		
    		String oldQuickCrafts = DataGetter.findStr("blockedQuickCrafts");
    		
    		String[] blockedCraftList = oldQuickCrafts.split(";");
    		ArrayList<String> lockedCrafts = new ArrayList();
    		for(int i=0;i<blockedCraftList.length;i++) { lockedCrafts.add(blockedCraftList[i]); }
    		
    		
			ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
    		Minecraft mc = Minecraft.getMinecraft();
	        try {
	        	int guiScale = mc.gameSettings.guiScale;
			   	int w = mc.currentScreen.width;
			   	int h = mc.currentScreen.height;
			   	int mouseX = Mouse.getX()/guiScale;
			   	
			   	int mouseY = h-(Mouse.getY()/guiScale);
			   	for(int i=0;i<3;i++) {
			   		
			   		int currSlot = 16+(i*9);
			   		String currItem = Utils.unformatAllText(MenuClickEvent.itemList.get(currSlot)).toLowerCase().replace(" ", "_");

		   			Utils.print(currItem);
			   		if(currItem.replace("_", "").equals("") || currItem.replace(" ", "").equals("quick_crafting_slot")) {
			   			continue;
			   		}
			   		
			   		if(mouseX >= w/2-9+54 && mouseX <= w/2+9+54 && mouseY >= h/2-3-72+(i*18) && mouseY <= h/2+15-72+(i*18)) {
			   			//Utils.sendMessage(currItem);
			   			Boolean passThrough = false;
			   			
			   			for(int j=0;j<lockedCrafts.size();j++) {
			   				if(lockedCrafts.get(j).equals(currItem)) {
			   					lockedCrafts.remove(j);
			   					passThrough = true; 
			   					continue; 
			   				}
			   			}
			   			
			   			if(!passThrough) {
			   				ConfigHandler.newObject("blockedQuickCrafts", oldQuickCrafts+currItem+";");
			   				Utils.playDingSound();
			   			} else {
			   				String output = "";
			   				for(String item : lockedCrafts) { output+=item+";"; }
			   				
			   				ConfigHandler.newObject("blockedQuickCrafts", output);
			   				Minecraft.getMinecraft().thePlayer.playSound("note.pling", 1, 0.0F);
			   			}

				   		
				   	}
			   	}
			   	
			   	
	        } catch(Exception e) { Utils.sendErrMsg("Something went wrong with the Key press event!"); e.printStackTrace(); }
    	}
	}
}
