package com.cobble.sbp.handlers;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.events.user.MenuClickEvent;
import com.cobble.sbp.events.user.PressKeyEvent;
import com.cobble.sbp.gui.screen.dwarven.DwarvenCompletedCommissions;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class GuiChestHandler {
	public GuiChestHandler() {
		try {
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayerSP player = mc.thePlayer;
			GuiScreen guiScreen = mc.currentScreen;
			int w = SBP.width;
			int h = SBP.height;
			
	        if(guiScreen instanceof GuiChest) {
	            IInventory cc = ((ContainerChest) ((GuiChest) guiScreen).inventorySlots).getLowerChestInventory();
	            RenderGuiEvent.currMenu = cc.getDisplayName().getUnformattedText().trim().toLowerCase();
	            if(RenderGuiEvent.currMenu.equals("craft item")) { 
        			MenuClickEvent.itemList.clear();
        			for(int r=0;r<cc.getSizeInventory();r++) {
            			try { MenuClickEvent.itemList.add(cc.getStackInSlot(r).getDisplayName()); } catch(Exception e) { MenuClickEvent.itemList.add("");}
            		}
        		
            	String[] blockedList = DataGetter.findStr("blockedQuickCrafts").split(";");
            	
            	PressKeyEvent.inCraftMenu=true; 
            	for(int k=0;k<3;k++) {
            		int currSlot = 16+(k*9);
            		try {
            			String currItem = cc.getStackInSlot(currSlot).getDisplayName().toLowerCase().replace(" ", "_");
	            		currItem = Utils.unformatAllText(currItem);
	            		
	            		Boolean locked = false;
	            		
	            		for(String item : blockedList) {
	            			if(item.equals(currItem)) { locked = true; 
	            			continue; }
	            		}
	            		
	            		if(locked) { RenderGuiEvent.addHighlightSlot(w/2-8+54, h/2-3+(k*18)-(72), 1, 1, 1, 0.7F, true); }
            		} catch(Exception e2) { }
            	}
            } else {PressKeyEvent.inCraftMenu=false; MenuClickEvent.itemList.clear();}
	            
    			
	            
	            if(SBP.sbLocation.equals("dwarvenmines")) { if(RenderGuiEvent.currMenu.equals("commissions")) { new DwarvenCompletedCommissions(); } }
	            
	            
	            if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1) || Keyboard.isKeyDown(KeyBindingHandler.lockQuickCraft.getKeyCode())) {
	            	if(RenderGuiEvent.menuClickAvailable) { RenderGuiEvent.menuClickAvailable = false; }
	            } else { RenderGuiEvent.menuClickAvailable = true; }
	            
	        } else { RenderGuiEvent.currMenu = ""; }
			
			try {
				
			} catch(Exception e) {return;}
			
	        if(guiScreen instanceof GuiChest) {
	            IInventory cc = ((ContainerChest) ((GuiChest) guiScreen).inventorySlots).getLowerChestInventory();			            
	            if(SBP.sbLocation.equals("dwarvenmines")) {
	            	if(RenderGuiEvent.currMenu.equals("heart of the mountain")) {
	            	
	            	if(Utils.unformatAllText(cc.getStackInSlot(10).getDisplayName()).equals("Sky Mall")) {
	            		ItemStack skymall = cc.getStackInSlot(10);
	            		List<String> desc  = skymall.getTooltip(player, false);
	            		int skyUnlocked = desc.size()-1;
	            		
	            		if(Utils.unformatAllText(desc.get(skyUnlocked)).equals("UNLOCKED")) {
	            			String isActive = Utils.unformatAllText(desc.get(skyUnlocked-2));
	            			if(isActive.contains("20%")) { DwarvenPickaxeTimer.isSkymall = true;
	            			} else { DwarvenPickaxeTimer.isSkymall=false; }
	            		} else { DwarvenPickaxeTimer.isSkymall=false; }
	            		
	            		
	            	} 
	            	
	            	if(Utils.unformatAllText(cc.getStackInSlot(0).getDisplayName()).trim().replace(" ", "").contains("Tier5")) {
	            		ItemStack tier5 = cc.getStackInSlot(0);
	            		List<String> desc = tier5.getTooltip(player, false);
	            		int isTier5 = desc.size()-1;
	            		
	            		if(Utils.unformatAllText(desc.get(isTier5)).equals("UNLOCKED")) {
	            			if(!(DataGetter.findInt("dwarvenHOTMLevel") == 5)) {
	            				ConfigHandler.newObject("dwarvenHOTMLevel", 5);
	            				DwarvenPickaxeTimer.HotMLevel = 5;
	            			}
	            		}
	            	}
	            	}
	            	
	            	else if(RenderGuiEvent.currMenu.equals("commissions") && DataGetter.findBool("dwarvenCommBgToggle")) {
	            		DwarvenCompletedCommissions.completedSlots.clear();
	            		for(int i=0;i<9;i++) {
	            			int currSlot = i+9;
	            			if(Utils.unformatAllText(cc.getStackInSlot(currSlot).getDisplayName()).contains("Commission")) {
	            				ItemStack comm = cc.getStackInSlot(currSlot);
	            				List<String> desc = comm.getTooltip(player, false);
	            				
	            				if(Utils.unformatAllText(desc.get(desc.size()-1)).contains("Click to claim rewards!")) {
	            					DwarvenCompletedCommissions.completedSlots.add(currSlot+"");
	            				}
	            			}
	            		}
	            	}
	            }
	        }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
