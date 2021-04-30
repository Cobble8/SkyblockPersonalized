package com.cobble.sbp.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.skyblock.LobbySwapEvent;
import com.cobble.sbp.events.user.MenuClickEvent;
import com.cobble.sbp.events.user.PressKeyEvent;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.menu.settings.SettingMove;
import com.cobble.sbp.gui.menu.settings.SettingMoveAll;
import com.cobble.sbp.gui.menu.settings.SettingOptions;
import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.gui.screen.dwarven.DwarvenCompletedCommissions;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.gui.screen.misc.AbilityMessages;
import com.cobble.sbp.gui.screen.misc.ComboMessages;
import com.cobble.sbp.handlers.AnimationHandler;
import com.cobble.sbp.handlers.DownloadSecretsHandler;
import com.cobble.sbp.handlers.GuiChestHandler;
import com.cobble.sbp.handlers.KeyBindingHandler;
import com.cobble.sbp.handlers.TabListHandler;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderGuiEvent {
	public static Boolean helpMenu = false;
	public static String currSettingMenu = "main";
	public static String imageID = "NONE";
	private final int puzzleUpdate = 250;
	public static int puzzleScale = DataGetter.findInt("puzzleScale")/10*126;

	int m=0;
	public static Boolean msgThing = false;
	public static String msgType = "";
	public static Boolean egg2 = true;
	public static Boolean settingsOpen = false;
	public static String currMenu = "";
	
	
	public static Boolean puzzlerParticles = false;
	public static int puzzlerX = 181;
	public static int puzzlerZ = 135;
	public static String actionBar = "";
	public static ArrayList<NetworkPlayerInfo> tabNames = new ArrayList();
	int tmp = 0;
	//public static long currMillis = System.currentTimeMillis()/1000;
	
	public static Boolean menuClickAvailable = false;
	public static int oldGuiScale = -1;
	
	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event) {
		if (event.type == RenderGameOverlayEvent.ElementType.EXPERIENCE || event.type == RenderGameOverlayEvent.ElementType.JUMPBAR) {
		/*if((currMillis+"").equals((System.currentTimeMillis()/1000)+"")) {
		} else {
			f++;
			currMillis = System.currentTimeMillis()/1000;
		}*/
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP player = mc.thePlayer;
		World world = mc.theWorld;
		
		
		
		new AnimationHandler();
		m++;
		
		
		//Gets width/height of the screen while in a menu
		try { SBP.width = mc.currentScreen.width; SBP.height = mc.currentScreen.height; } catch(Exception e) { }
		int w=SBP.width;
		int h=SBP.height;
		
		//Allows for /sbp GUI to show up on servers by adding 1 frame delay
		if(helpMenu) {
			helpMenu=false;
			if(!currSettingMenu.equals("main")) {
				
				switch(currSettingMenu) {
					case "moveall": mc.displayGuiScreen(new SettingMoveAll()); break;
					case "move": mc.displayGuiScreen(new SettingMove()); break;
					case "suboptions": mc.displayGuiScreen(new SettingOptions()); break;
				}
				currSettingMenu = "main";
			} else {
				mc.displayGuiScreen(new SettingMenu());
			}
			
			
		}

		//Fixes GUI Scale in SBP menu
		if(SettingMenu.settingsMenuOpen) {
			if(mc.gameSettings.guiScale > 2 || mc.gameSettings.guiScale == 0) {
				oldGuiScale = mc.gameSettings.guiScale;
				mc.gameSettings.guiScale = 2;
				
				try {
					player.closeScreen();
					helpMenu=true;
				} catch(Exception e) {}
			} else { }
		} else {
			if(oldGuiScale != -1) {
				mc.gameSettings.guiScale = oldGuiScale;
				oldGuiScale = -1;
			}
		}

		
		
		if(DownloadSecretsHandler.running) {
			long timePassed = System.currentTimeMillis()-DownloadSecretsHandler.startTime;
			try {
				if(DownloadSecretsHandler.progress != DownloadSecretsHandler.total) {
					DownloadSecretsHandler.timeElapsed = Integer.parseInt(timePassed+"");
				}
				
			} catch(Exception e) {}
		}
		
		new SecretImage();
		if(!mc.isSingleplayer()) {
			if(SBP.onSkyblock) {
				if(!(SBP.titleString.equals(""))) { Utils.drawTitle(); }
				
				
				String heldItem = ""; try {  heldItem = player.getHeldItem().getDisplayName().toLowerCase(); } catch(Exception e) { }
				
				GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
				new GuiChestHandler();

		        //Called once every 1/2 a second
				if(m==1) {
					new TabListHandler();
				}
				
				//GUI ELEMENTS
				
				if(SBP.sbLocation.equals("dwarvenmines")) {
					new DwarvenGui(DwarvenGui.posX, DwarvenGui.posY);
					if(DwarvenTimer.dwarvenTimerToggle) { new DwarvenTimer(DwarvenTimer.posX, DwarvenTimer.posY); }
					if(puzzlerParticles) { Random rand = new Random(); double motionY = rand.nextGaussian() * 0.02D; world.spawnParticle(EnumParticleTypes.SPELL, puzzlerX+0.5, 196, puzzlerZ+0.5, 0, motionY, 0); }
					
					if(DwarvenPickaxeTimer.pickTimerToggle) {
						Boolean passThrough = false;
						if(!DwarvenPickaxeTimer.onlyWhenHolding) {
							passThrough = true;
						} else {
							try {
								String pickID = player.getHeldItem().getDisplayName().toLowerCase();//Utils.getSBID();
								if(pickID.contains("pickaxe") || pickID.contains("drill")) {
									passThrough = true;
								}	
							} catch(Exception e) {}
							
						}
						
						if(passThrough) {
							new DwarvenPickaxeTimer(DwarvenPickaxeTimer.pickTimerX, DwarvenPickaxeTimer.pickTimerY);
						}
					}
				}
				
				
				
				if(AbilityMessages.abilityMsgToggle) {
					new AbilityMessages(AbilityMessages.x, AbilityMessages.y);
				}
				if(ComboMessages.abilityMsgToggle) {
					new ComboMessages(ComboMessages.x, ComboMessages.y);
				}
				
				
			}
		
		}
		
		

		
		


		if(m >= (Minecraft.getDebugFPS()/2)) {
			Utils.checkIfOnSkyblock();
			if(SettingMenu.settingsMenuOpen) { String c = SettingMenu.currOptionName; ConfigHandler.updateConfig(c); }
			m=0;
		}
		
		}
	}
	
	private static ArrayList<ArrayList<Float>> highlightSlots = new ArrayList();
	private static ArrayList<Boolean> inFrontOfItem = new ArrayList();
	
	public static void addHighlightSlot(float x, float y, float r, float g, float b, float a, Boolean infrontOfItem) {
		ArrayList<Float> coords = new ArrayList();
		coords.add(x);
		coords.add(y);
		coords.add(r);
		coords.add(g);
		coords.add(b);
		coords.add(a);
		
		highlightSlots.add(coords);
		inFrontOfItem.add(infrontOfItem);
	}
	
	
	@SubscribeEvent
	public void renderChestMenu(GuiScreenEvent.DrawScreenEvent.Pre event) {
		MenuClickEvent.mouseX = event.mouseX;
		MenuClickEvent.mouseY = event.mouseY;
	}
	
	
	@SubscribeEvent
	public void renderChestMenu(GuiScreenEvent.DrawScreenEvent.Post event) {
		Minecraft mc = Minecraft.getMinecraft();
		
		//Utils.drawString(Colors.WHITE, 0, 0);
		
		
		
		
		for(int i=0;i<highlightSlots.size();i++) {
			GlStateManager.pushMatrix();
			GlStateManager.enableBlend();
			GlStateManager.enableDepth();
			
			int transInt = 200;
			if(inFrontOfItem.get(i)) { mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/lock.png")); transInt = 260; } else {
				mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png"));
			}
			int x = highlightSlots.get(i).get(0).intValue();
			int y = highlightSlots.get(i).get(1).intValue();
			float r = highlightSlots.get(i).get(2);
			float g = highlightSlots.get(i).get(3);
			float b = highlightSlots.get(i).get(4);
			float a = highlightSlots.get(i).get(5);
			
			GlStateManager.color(r, g, b, a);
			GlStateManager.translate(0, 0, transInt);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 16, 16, 16, 16);
			GlStateManager.popMatrix();
		}
		
		
		highlightSlots.clear();
		inFrontOfItem.clear();
		
	}
	
	
	
	//Calculates where to check for blocks in reference to the player based on direction of puzzle
	/*public static int gFX(String facing, int directionX, int directionZ, int xOffset, int zOffset) {
		
		int finalX = directionX*xOffset;
		int finalZ = directionZ*zOffset;
		
		if(facing.equals("east")) {
			int temp = finalX;
			finalX = finalZ;
			finalZ = temp;
		}
		else if(facing.equals("west")) {
			int temp = finalX;
			finalX = finalZ;
			finalZ = temp;
		}
		
		return finalX;
	}
	
	public static int gFZ(String facing, int directionX, int directionZ, int xOffset, int zOffset) {
		
		int finalX = directionX*xOffset;
		int finalZ = directionZ*zOffset;
		
		if(facing.equals("east")) {
			int temp = finalX;
			finalX = finalZ;
			finalZ = temp;
		}
		else if(facing.equals("west")) {
			int temp = finalX;
			finalX = finalZ;
			finalZ = temp;
		}
		
		return finalZ;
	}*/
}
