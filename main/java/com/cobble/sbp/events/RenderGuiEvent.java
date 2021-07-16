package com.cobble.sbp.events;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import com.cobble.sbp.events.user.ChatRecieveEvent;
import com.cobble.sbp.gui.menu.GameOfLife;
import com.cobble.sbp.gui.menu.GameThing;
import com.cobble.sbp.gui.menu.settings.*;
import com.cobble.sbp.gui.screen.dwarven.CrystalHollowsMap;
import com.cobble.sbp.gui.screen.misc.JerryTimer;
import com.cobble.sbp.handlers.*;
import com.cobble.sbp.utils.*;
import net.minecraft.client.gui.Gui;


import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.user.MenuClickEvent;
import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.gui.screen.misc.AbilityMessages;
import com.cobble.sbp.gui.screen.misc.ComboMessages;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import scala.Int;

public class RenderGuiEvent {
	public static Boolean helpMenu = false;
	public static String currSettingMenu = "main";
	public static int puzzleScale = DataGetter.findInt("puzzleScale")/10*126;

	int m=0;
	public static String currMenu = "";
	
	
	public static Boolean puzzlerParticles = false;
	public static int puzzlerX = 181;
	public static int puzzlerZ = 135;
	public static String actionBar = "";
	public static ArrayList<NetworkPlayerInfo> tabNames = new ArrayList();
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
		try { SBP.width = mc.currentScreen.width; SBP.height = mc.currentScreen.height; } catch(Exception ignored) { }
		
		//Allows for /sbp GUI to show up on servers by adding 1 frame delay
		if(helpMenu) {
			helpMenu=false;
			if(!currSettingMenu.equals("main")) {

				switch(currSettingMenu) {
					case "moveall": mc.displayGuiScreen(new SettingMoveAll()); break;
					case "move": mc.displayGuiScreen(new SettingMove()); break;
					case "suboptions": mc.displayGuiScreen(new SettingOptions()); break;
					case "game": mc.displayGuiScreen(new GameThing()); break;
					case "life": mc.displayGuiScreen(new GameOfLife()); break;
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
				} catch(Exception ignored) {}
			}
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
				
			} catch(Exception ignored) {}
		}

		new SecretImage();

		//if(m==1) { if(SBP.onSkyblock) { MusicUtils.manageSong(); } else { MusicUtils.stopSong(); } }

		if(!mc.isSingleplayer()) {
			if(SBP.onSkyblock) {
				if(!(SBP.titleString.equals(""))) { Utils.drawTitle(); }
				
				
				//String heldItem = ""; try {  heldItem = player.getHeldItem().getDisplayName().toLowerCase(); } catch(Exception ignored) { }

				//CrystalHollowsMap.renderWayPoint("Text", new Vector3f(0, 100, 0).translate(0.5f, 2.488f, 0.5f), event.partialTicks);

				new GuiChestHandler();


		        //Called once every 1/2 a second
				if(m==1) {
					new TabListHandler();
				}


				//GUI ELEMENTS
				if(ChatRecieveEvent.inMines() && DwarvenPickaxeTimer.pickTimerToggle) {
					boolean passThrough = false;
					if(!DwarvenPickaxeTimer.onlyWhenHolding) {
						passThrough = true;
					} else {
						try {
							String pickID = Utils.getSBID();
							if(pickID.contains("pickaxe") || pickID.contains("drill")) {
								passThrough = true;
							}
						} catch(Exception ignored) {}

					}

					if(passThrough) {
						new DwarvenPickaxeTimer();
					}
				}


				if(SBP.sbLocation.equals("dwarvenmines") || SBP.sbLocation.equals("crystalhollows")) {
					new DwarvenGui(DwarvenGui.posX, DwarvenGui.posY);
					if(DwarvenTimer.dwarvenTimerToggle) { new DwarvenTimer(DwarvenTimer.posX, DwarvenTimer.posY); }
					if(puzzlerParticles) { Random rand = new Random(); double motionY = rand.nextGaussian() * 0.02D; world.spawnParticle(EnumParticleTypes.SPELL, puzzlerX+0.5, 196, puzzlerZ+0.5, 0, motionY, 0); }
					

				}
				if(SBP.sbLocation.equals("crystalhollows")) {
					new CrystalHollowsMap();
					new ChatOpenGui();
				}
				/* else if(SBP.sbLocation.equals("privateworld")) {
					try {
						if(Utils.getSBID().equals("pumpkin_dicer")) {
							float cameraYaw = player.rotationYawHead;
							if(cameraYaw < 0) {cameraYaw *= -1;}
							while(cameraYaw > 90) { cameraYaw-=90; }

							StringBuilder outputYaw = new StringBuilder(new DecimalFormat("#.##").format(cameraYaw));
							while(outputYaw.substring(outputYaw.indexOf(".")).length() < 3) {
								outputYaw.append("0");
							}
							Utils.drawString(Colors.CHROMA+"Yaw: "+outputYaw+"°", 10, 22);
							String adjustArrow = "->";
							int finalPos = SBP.width/2-(mc.fontRendererObj.getStringWidth("-")/2)+12;
							if(AnimationHandler.playerSpeed > 9.5 && AnimationHandler.playerSpeed < 10.1) {
								adjustArrow="";
							} else if( AnimationHandler.playerSpeed > 9.7 ) {
								adjustArrow = "<-";
								finalPos-=(mc.fontRendererObj.getStringWidth("<"));
							}

							//Utils.drawString(Colors.AQUA+adjustArrow, finalPos, SBP.height/2-10);
							Utils.drawScaledString(Colors.AQUA+adjustArrow, finalPos, SBP.height/2-10, 0x000000, 3, SettingGlobal.textStyle, true);
						}
					} catch(Exception ignored) {}

				}*/
				
				
				
				if(AbilityMessages.abilityMsgToggle) {
					new AbilityMessages(AbilityMessages.x, AbilityMessages.y);
				}
				if(ComboMessages.abilityMsgToggle) {
					new ComboMessages(ComboMessages.x, ComboMessages.y);
				}

				if(JerryTimer.jerryToggle) {
					new JerryTimer();
				}
				
			}
		
		}






			Utils.checkIfOnSkyblock();
		if(m >= (Minecraft.getDebugFPS()/4)) {

			if(SettingMenu.settingsMenuOpen) {
				try {
					String c = SettingMenu.settingIDs.get(SettingMenu.clickedSubOption);
					ConfigHandler.updateConfig(c);
				} catch(Exception ignored) {
					ConfigHandler.updateConfig("");
				}

			}
			m=0;
		}
		GlStateManager.enableBlend();
		}
	}
	
	private static final ArrayList<ArrayList<Float>> highlightSlots = new ArrayList();
	private static final ArrayList<Boolean> inFrontOfItem = new ArrayList();
	
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
		SBP.width = event.gui.width;
		SBP.height = event.gui.height;
	}
	
	private static boolean mouseDown = false;
	private static String wantsToMove = "";
	private static boolean volumePling = false;
	@SubscribeEvent
	public void renderChestMenu(GuiScreenEvent.DrawScreenEvent.Post event) {
		if(!SBP.onSkyblock) {return;}
		Minecraft mc = Minecraft.getMinecraft();

		GlStateManager.pushMatrix();
		/*try {
			String songName;
			try {
				songName = new File(MusicUtils.getSong()).getName();
				songName = songName.substring(0, songName.lastIndexOf("."));
			} catch(Exception e) { songName = "None!"; }

			int songNameWidth = mc.fontRendererObj.getStringWidth("Now Playing: "+songName)/2;
			int mouseX = MenuClickEvent.mouseX;
			int mouseY = MenuClickEvent.mouseY;


			String menuLoc = "";
			if(mc.currentScreen instanceof GuiInventory) {
				menuLoc="musicInv";
			} else if(mc.currentScreen instanceof GuiIngameMenu) {
				menuLoc="musicEsc";
			} else { throw new Exception("Doesn't matter lol"); }
			int musicX = DataGetter.findInt(menuLoc+"X")+100;
			int musicY = DataGetter.findInt(menuLoc+"Y");



			boolean b1 = mouseX >= musicX && mouseX < musicX + 16 && mouseY >= musicY + 30 && mouseY <= musicY + 30 + 16;
			boolean b = mouseX >= musicX - 16 && mouseX < musicX && mouseY >= musicY + 30 && mouseY <= musicY + 30 + 16;
			if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {

				if(mouseX >= musicX-songNameWidth && mouseX < musicX+songNameWidth && mouseY >= musicY-4 && mouseY < musicY+14 && wantsToMove.equals("move")) {
					ConfigHandler.newObject(menuLoc+"X", mouseX-100);
					ConfigHandler.newObject(menuLoc+"Y", mouseY-4);
				} else if(mouseX >= musicX-70 && mouseX < musicX+70 && mouseY >= musicY+12 && mouseY < musicY+30 && wantsToMove.equals("volume")) {
					int xOff = mouseX-(musicX-59);
					float volPerc = ((float) xOff)/118;
					if(volPerc < 0) {volPerc=0;}
					else if(volPerc > 1) {volPerc=1;}
					MusicUtils.setVolume(volPerc);

				}

				if(!mouseDown) {
					//CLICK EVENT
					wantsToMove= "";
					if(mouseX >= musicX - songNameWidth && mouseX < musicX + songNameWidth && mouseY >= musicY - 4 && mouseY < musicY + 14) {
						wantsToMove="move";

					} else if(mouseX >= musicX-70 && mouseX < musicX+70 && mouseY >= musicY+12 && mouseY < musicY+30) {
						wantsToMove="volume";
						volumePling=true;
					}
					if(b) {
						if(MusicUtils.songState.equals("playing")) {
							MusicUtils.pauseSong();
							Utils.playClickSound();
						} else if(MusicUtils.songState.equals("paused")) {
							MusicUtils.playSong();
						} else {
							MusicUtils.stopSong();
						}
					} else if(b1) {
						MusicUtils.stopSong();
						Utils.playClickSound();
					}
					mouseDown=true;
				}
			} else {
				if(volumePling && !MusicUtils.songState.equals("playing")) {
					Minecraft.getMinecraft().thePlayer.playSound("note.pling", (float) MusicUtils.getVolume(), 1.0F);
					volumePling=false;
				}
				mouseDown=false; }

			GlStateManager.translate(0, 0, 10);
			ResourceLocation musicButtons = new ResourceLocation(Reference.MODID, "textures/0/menu/musicbuttons.png");
			int pauseOffset = 0;
			if(MusicUtils.songState.equals("playing")) { pauseOffset = 16; }
			ColorUtils.resetColor();
			mc.getTextureManager().bindTexture(musicButtons);
			Gui.drawModalRectWithCustomSizedTexture(musicX-64, musicY+12, 0, 16, 128, 16, 128, 32);
			int sliderPos =  (int) (MusicUtils.getVolume()*118);

			Gui.drawModalRectWithCustomSizedTexture(musicX-67+sliderPos, musicY+12, 48, 0, 16, 16, 128, 32);
			Gui.drawModalRectWithCustomSizedTexture(musicX-16, musicY +30, pauseOffset, 0, 16, 16, 128, 32);
			Gui.drawModalRectWithCustomSizedTexture(musicX, musicY +30, 32, 0, 16, 16, 128, 32);
			Utils.drawString(Colors.GOLD+"Now Playing: "+Colors.AQUA+songName, musicX -songNameWidth, musicY, 4);
			//Utils.drawString(Colors.YELLOW+"Client Music", musicX-21-(mc.fontRendererObj.getStringWidth("Client Music")), musicY +35);
			//Utils.drawString(Colors.YELLOW+"By SBP", musicX+21, musicY +35);

		} catch(Exception e) {
			//e.printStackTrace();
		}*/
		GlStateManager.popMatrix();

		
		//HIGHLIGHT SLOTS
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
			Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 16, 16, 16, 16);
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
