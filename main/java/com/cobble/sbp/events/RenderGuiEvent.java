package com.cobble.sbp.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.gui.IcePathHandler;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.screen.PuzzleImage;
import com.cobble.sbp.gui.screen.dwarven.DwarvenDrillFuel;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenQuestTracker;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.handlers.BoxPuzzleHandler;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderGuiEvent {
	public static Boolean helpMenu = false;
	public static String imageID = "NONE";
	private final int puzzleUpdate = 250;
	public static int puzzleScale = Integer.parseInt(DataGetter.find("puzzleScale")+"")/10*126;
	int i=0;
	int j=0;
	int k=0;
	public static Boolean msgThing = false;
	public static String msgType = "";
	public static Boolean egg2 = true;
	public static Boolean settingsOpen = false;
	
	public static Boolean puzzlerParticles = false;
	public static int puzzlerX = 181;
	public static int puzzlerZ = 135;
	public static String actionBar = "";
	
	
	
	
	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event) {
		i++;
		j++;
		k++;
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP player = mc.thePlayer;
		World world = mc.theWorld;
		
		
		
		if(helpMenu && j == 1) {
			Minecraft.getMinecraft().displayGuiScreen(new SettingMenu());
			helpMenu=false;
		}

		
		if(!mc.isSingleplayer()) {
			if(SBP.onSkyblock) {
				
				
				//TAB LIST INFO
				String heldItem = ""; try {  heldItem = player.getHeldItem().getDisplayName().toLowerCase(); } catch(Exception e) { }
				
				Boolean foundPlace = false;
				Collection<NetworkPlayerInfo> playerList = Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap();
				ArrayList<NetworkPlayerInfo> tabNames = new ArrayList<NetworkPlayerInfo>(playerList);
				for(int i=0;i<tabNames.size();i++) {
					try { 
						if(!foundPlace) {
							String name = Utils.unformatText(tabNames.get(i).getDisplayName().getUnformattedText()).replace(" ", "").toLowerCase();
							if(name.startsWith("area:")) {
								SBP.sbLocation = name.replace("area:", "");
								foundPlace = true;
							} else if(name.startsWith("server:")) {
								String oldLobby = name.replace("server:", "");
								
								if(!LobbySwapEvent.currLobby.equals(oldLobby)) {
									LobbySwapEvent.currLobby=oldLobby;
									new LobbySwapEvent();
								}
							}
						}
					} catch (Exception e) { }
				}
				if(!foundPlace) {
					SBP.sbLocation = "privateworld";
				}
				
				
				//GUI ELEMENTS
				if(DwarvenDrillFuel.fuelToggle && heldItem.contains("drill")) { if(DwarvenDrillFuel.fuelInDwarven=true) { if(ChatRecieveEvent.inMines()) { new DwarvenDrillFuel(); } } else { new DwarvenDrillFuel(); } }
				if(DwarvenTimer.dwarvenTimerToggle) { new DwarvenTimer(Minecraft.getMinecraft()); }
				if(DwarvenQuestTracker.questTrackToggle) { new DwarvenQuestTracker(); }
				if(DwarvenPickaxeTimer.pickTimerToggle) { Boolean passThrough = true; if(DwarvenPickaxeTimer.onlyWhenHolding) { try { if(!(heldItem.contains("pickaxe") || heldItem.contains("drill"))) { passThrough=false; } } catch (NullPointerException e) { passThrough = false;}} if(DwarvenPickaxeTimer.onlyInDwarven) { if(!SBP.sbLocation.equals("dwarvenmines")) { passThrough=false; } } if(passThrough) { new DwarvenPickaxeTimer(); }}
				
				//PUZZLER PARTICLES
				if(puzzlerParticles && j == 1) { Random rand = new Random(); double motionY = rand.nextGaussian() * 0.02D; world.spawnParticle(EnumParticleTypes.SPELL, puzzlerX+0.5, 196, puzzlerZ+0.5, 0, motionY, 0); }
		
				//PUZZLE IMAGE
				if(imageID != "NONE") { new PuzzleImage(Minecraft.getMinecraft()); }
			}
		
		}
		
		
		if(i == 1) {
			String c = SettingMenu.currOptionName;
			Utils.checkIfOnSkyblock();
			
			
			
			if(SettingMenu.settingsMenuOpen) {
				ConfigHandler.updateConfig(c);
			}
			
			//PUZZLE SOLVERS
			if(Minecraft.getMinecraft().thePlayer.onGround) {
				if((Boolean) DataGetter.find("iceSolverToggle")) {
					IcePathHandler.run();
				}
			
				if(!imageID.contains("box") && (Boolean) DataGetter.find("boxSolverToggle")) {
					BoxPuzzleHandler.run();
				}
			
			}
			
			if(egg2) {
			int x = (int) player.posX;
			int y = (int) player.posY;
			int z = (int) player.posZ;
				if(x == 34 && y == 102 && z == 87) {
					egg2 = false;
					Utils.sendSpecificMessage(Colors.LIGHT_PURPLE+"From "+Colors.GOLD+"[MVP"+Colors.DARK_GREEN+"++"+Colors.GOLD+"] Cobble8"+Colors.GRAY+": I see you've found my little spot ;D");
					Utils.sendMessage(Colors.GREEN+"You have found Easter Egg #2");
					Utils.setEasterEgg(2, Colors.LIGHT_PURPLE+"Looks like a fairy decided to die underneath a wall, how smart!");
				}
			}
			
		}
		if(i == puzzleUpdate) {
			i=0;
		}
		if(j == 15) {
			j=0;
		}
		if(k >= Minecraft.getDebugFPS()*5) {
			k=0;
		}
		
		
		
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
