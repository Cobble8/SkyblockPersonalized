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
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.screen.PuzzleImage;
import com.cobble.sbp.gui.screen.dwarven.DwarvenCompletedCommissions;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.handlers.BoxPuzzleHandler;
import com.cobble.sbp.handlers.IcePathHandler;
import com.cobble.sbp.handlers.KeyBindingHandler;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderGuiEvent {
	public static Boolean helpMenu = false;
	public static String imageID = "NONE";
	private final int puzzleUpdate = 250;
	public static int puzzleScale = DataGetter.findInt("puzzleScale")/10*126;
	int i=0;
	int j=0;
	int k=0;
	int m=0;
	int f=0;
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
	public static long currMillis = System.currentTimeMillis()/1000;
	
	private static Boolean menuClickAvailable = false;
	
	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event) {
		if (event.type == RenderGameOverlayEvent.ElementType.EXPERIENCE || event.type == RenderGameOverlayEvent.ElementType.JUMPBAR) {
		if((currMillis+"").equals((System.currentTimeMillis()/1000)+"")) {
		} else {
			f++;

			currMillis = System.currentTimeMillis()/1000;
		}


		
		
		i++;
		j++;
		k++;
		m++;
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP player = mc.thePlayer;
		World world = mc.theWorld;
		
		int w=0;
		int h=0;
		try { w = mc.currentScreen.width; h = mc.currentScreen.height; } catch(Exception e) { }

		//Utils.drawConfinedString(Colors.WHITE+"You suck people ", w/2-45, h/2-20, 3, 90);
		
		if(helpMenu && j == 1) {
			Minecraft.getMinecraft().displayGuiScreen(new SettingMenu());
			helpMenu=false;
		}

		if(!mc.isSingleplayer()) {
			if(SBP.onSkyblock) {
				if(!(SBP.titleString.equals(""))) { Utils.drawTitle(); }
				
				//TAB LIST INFO
				String heldItem = ""; try {  heldItem = player.getHeldItem().getDisplayName().toLowerCase(); } catch(Exception e) { }
				
				GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
				
		        if(guiScreen instanceof GuiChest) {
		        	


		            IInventory cc = ((ContainerChest) ((GuiChest) guiScreen).inventorySlots).getLowerChestInventory();
		            //Utils.print(cc.getDisplayName().getUnformattedText().trim().toLowerCase());
		            currMenu = cc.getDisplayName().getUnformattedText().trim().toLowerCase();
		            if(currMenu.equals("craft item")) { 
	            			

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
			            		
			            		if(locked) { addHighlightSlot(w/2-8+54, h/2-3+(k*18)-(72), 1, 1, 1, 0.7F, true); }
		            		} catch(Exception e2) {
		            			
		            		}
		            		
		            		
		            	}
		            	
		            
		            } else {PressKeyEvent.inCraftMenu=false; MenuClickEvent.itemList.clear();}
		            
        			
		            
		            if(SBP.sbLocation.equals("dwarvenmines")) {
		            	if(currMenu.equals("commissions")) {
			            	new DwarvenCompletedCommissions();
			            	
			            }
		            }
		            
		            
		            if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1) || Keyboard.isKeyDown(KeyBindingHandler.lockQuickCraft.getKeyCode())) {
		            	if(menuClickAvailable) {
		            		

		            		
		            		
		            		//new InsideMenuClick(currMenu, Mouse.getX(), Mouse.getY(), invList);
		            		
		            		menuClickAvailable = false;
		            	}
		            } else {
		            	menuClickAvailable = true;
		            }
		            
		        } else { currMenu = ""; }
		        
				if(m==1) {
					
					
					
					try {
						
				        if(guiScreen instanceof GuiChest) {
				            IInventory cc = ((ContainerChest) ((GuiChest) guiScreen).inventorySlots).getLowerChestInventory();
				            //currMenu = cc.getDisplayName().getUnformattedText().trim().toLowerCase();
				            
				            //Utils.print(menuName);
				            
				            
				            if(SBP.sbLocation.equals("dwarvenmines")) {
				            	if(currMenu.equals("heart of the mountain")) {
				            	
				            	if(Utils.unformatAllText(cc.getStackInSlot(10).getDisplayName()).equals("Sky Mall")) {
				            		ItemStack skymall = cc.getStackInSlot(10);
				            		List<String> desc  = skymall.getTooltip(player, false);
				            		int skyUnlocked = desc.size()-1;
				            		
				            		if(Utils.unformatAllText(desc.get(skyUnlocked)).equals("UNLOCKED")) {
				            			String isActive = Utils.unformatAllText(desc.get(skyUnlocked-2));
				            			if(isActive.contains("20%")) {
				            				DwarvenPickaxeTimer.isSkymall = true;
				            			} else {
				            				DwarvenPickaxeTimer.isSkymall=false;
				            			}
				            		} else {
				            			DwarvenPickaxeTimer.isSkymall=false;
				            		}
				            		
				            		
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
				            	
				            	else if(currMenu.equals("commissions") && DataGetter.findBool("dwarvenCommBgToggle")) {
				            		DwarvenCompletedCommissions.completedSlots.clear();
				            		for(int i=0;i<9;i++) {
				            			int currSlot = i+9;
				            			if(Utils.unformatAllText(cc.getStackInSlot(currSlot).getDisplayName()).contains("Commission")) {
				            				ItemStack comm = cc.getStackInSlot(currSlot);
				            				List<String> desc = comm.getTooltip(player, false);
				            				
				            				if(Utils.unformatAllText(desc.get(desc.size()-1)).contains("Click to claim rewards!")) {
				            					//Utils.print("w");
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
					
					Boolean foundPlace = false;
					Collection<NetworkPlayerInfo> playerList = Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap();
				 	tabNames = new ArrayList<NetworkPlayerInfo>(playerList);
				 	DwarvenGui.currString="";
				 	DwarvenGui.currCommissions = "";
				 	String commissionColor = Utils.getColorFromInt(DwarvenGui.commissionID);
				 	if(DwarvenGui.commTrackToggle) { DwarvenGui.currString+=commissionColor+Colors.BOLD+"Commissions"; }
				 	
				 	String mithrilPowder = "";
				 	String eventOffset = "";
				 	for(int i=0;i<tabNames.size();i++) {
				 		
				 		String name = "";
				 		try { name = RenderGuiEvent.tabNames.get(i).getDisplayName().getUnformattedText(); } catch(Exception e) { continue; }
							if(!foundPlace) {
								String rawName = Utils.unformatText(tabNames.get(i).getDisplayName().getUnformattedText()).replace(" ", "").toLowerCase();
								if(name.toLowerCase().startsWith("area:")) {
									
									String loc = rawName.replace("area:", ""); foundPlace = true;
									
									if(!(loc.equals(SBP.sbLocation))) { new LobbySwapEvent(); SBP.sbLocation = loc; }
									
								} else if((name.toLowerCase().replace(" ", "")).startsWith("dungeon:cata")) { foundPlace=true; SBP.sbLocation = "catacombs";}
							}
							
					if(SBP.sbLocation.equals("dwarvenmines")) { 
						DwarvenGui.manageCommissions(name); 
						if(DwarvenGui.mithrilToggle) { if(name.toLowerCase().contains("mithril powder: ")) { mithrilPowder = DwarvenGui.manageMithril(name);}}
						
						
					}
						
					}
				 	if(!foundPlace) { SBP.sbLocation = "privateworld"; }
				 	if(SBP.sbLocation.equals("dwarvenmines")) { 
				 		if(DwarvenGui.fuelToggle && Utils.getSBID().contains("drill")) { DwarvenGui.manageDrillFuel(); }
				 		if(DwarvenGui.commTrackToggle) {DwarvenGui.currString+=";";}
				 		DwarvenGui.currString+=mithrilPowder;
				 		
				 		if(DwarvenTimer.dwarvenTimerToggle) { DwarvenGui.currString+=eventOffset;}
				 		
				 		if(DwarvenTimer.dwarvenTimerToggle) { new DwarvenTimer(mc); }
				 		
				 	}
				}
				
				//GUI ELEMENTS
				
				if(SBP.sbLocation.equals("dwarvenmines")) {
					new DwarvenGui().drawGuiElements();
					if(DwarvenTimer.dwarvenTimerToggle) { new DwarvenTimer(Minecraft.getMinecraft()); }
					if(puzzlerParticles && j == 1) { Random rand = new Random(); double motionY = rand.nextGaussian() * 0.02D; world.spawnParticle(EnumParticleTypes.SPELL, puzzlerX+0.5, 196, puzzlerZ+0.5, 0, motionY, 0); }
					//if(DwarvenPickaxeTimer.pickTimerToggle) { Boolean passThrough = true; if(DwarvenPickaxeTimer.onlyWhenHolding) { try { if(!( Utils.getSBID().contains("pickaxe") ||  Utils.getSBID().contains("drill"))) { passThrough=false; } } catch (NullPointerException e) { passThrough = false;}} if(DwarvenPickaxeTimer.onlyInDwarven) { if(!SBP.sbLocation.equals("dwarvenmines")) { passThrough=false; } } if(passThrough) { new DwarvenPickaxeTimer(); }}
					//if(DwarvenPickaxeTimer.onlyInDwarven) { new DwarvenPickaxeTimer(); }
					
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
							new DwarvenPickaxeTimer();
						}
					}
				}
				
				//DwarvenGuiif(DwarvenDrillFuel.fuelToggle && Utils.getSBID().contains("drill") && SBP.sbLocation.equals("dwarvenmines")) { if(DwarvenDrillFuel.fuelInDwarven) { if(ChatRecieveEvent.inMines()) { new DwarvenDrillFuel(); } } else { new DwarvenDrillFuel(); } }
				
				//PUZZLER PARTICLES
				
		
				//PUZZLE IMAGE
				if(imageID != "NONE") { new PuzzleImage(Minecraft.getMinecraft()); }
				if(i == 1) {
					
					if(SettingMenu.settingsMenuOpen) { String c = SettingMenu.currOptionName; ConfigHandler.updateConfig(c); }
					//PUZZLE SOLVERS
					if(Minecraft.getMinecraft().thePlayer.onGround && SBP.sbLocation.equals("catacombs")) {
						if(DataGetter.findBool("iceSolverToggle")) { IcePathHandler.run(); }
						if(!imageID.contains("box") && DataGetter.findBool("boxSolverToggle")) { BoxPuzzleHandler.run(); }
					}
				}
			}
		
		}
		
		

		
		
		if(i >= 100) {
			i=0;
		}
		if(j >= Minecraft.getDebugFPS()/25) {
			j=0;
		}
		if(k >= Minecraft.getDebugFPS()*5) {
			k=0;
		}
		if(m >= (Minecraft.getDebugFPS()/2)) {
			Utils.checkIfOnSkyblock();
			m=0;
		}
		if(f >= 101) {
			f=0;
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
		
		Utils.drawString(Colors.WHITE, 0, 0);
		
		
		
		
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
			GlStateManager.translate(0, 0, -1*transInt);
			GlStateManager.popMatrix();
		}
		
		
		highlightSlots.clear();
		inFrontOfItem.clear();
		
	}
	
	
	
	//Calculates where to check for blocks in reference to the player based on direction of puzzle
	public static int gFX(String facing, int directionX, int directionZ, int xOffset, int zOffset) {
		
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
	}
}
