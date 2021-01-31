package com.cobble.sbp.events;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.screen.PuzzleImage;
import com.cobble.sbp.gui.screen.dwarven.DwarvenDrillFuel;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenQuestTracker;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.handlers.BoxPuzzleHandler;
import com.cobble.sbp.handlers.IcePathHandler;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
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
	public static int puzzleScale = DataGetter.findInt("puzzleScale")/10*126;
	int i=0;
	int j=0;
	int k=0;
	int m=0;
	public static Boolean msgThing = false;
	public static String msgType = "";
	public static Boolean egg2 = true;
	public static Boolean settingsOpen = false;
	
	public static Boolean puzzlerParticles = false;
	public static int puzzlerX = 181;
	public static int puzzlerZ = 135;
	public static String actionBar = "";
	public static ArrayList<NetworkPlayerInfo> tabNames = new ArrayList();
	
	
	
	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event) {
		
		
		
		i++;
		j++;
		k++;
		m++;
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP player = mc.thePlayer;
		World world = mc.theWorld;
		
		
		
		if(helpMenu && j==10) {
			Minecraft.getMinecraft().displayGuiScreen(new SettingMenu());
			helpMenu=false;
		}

		
		if(!mc.isSingleplayer()) {
			if(SBP.onSkyblock) {
				
				
				//TAB LIST INFO
				String heldItem = ""; try {  heldItem = player.getHeldItem().getDisplayName().toLowerCase(); } catch(Exception e) { }
				
				
				if(m==1) {
					Boolean foundPlace = false;
					Collection<NetworkPlayerInfo> playerList = Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap();
				 	tabNames = new ArrayList<NetworkPlayerInfo>(playerList);
				 	int g=0;
				 	DwarvenQuestTracker.currString="";
				 	DwarvenDrillFuel.currString="";
				 	DwarvenQuestTracker.currString+=Colors.BLUE+Colors.BOLD+"Commissions";
				 	for(int i=0;i<tabNames.size();i++) {
				 		
				 		String name = "";
				 		try { name = RenderGuiEvent.tabNames.get(i).getDisplayName().getUnformattedText(); } catch(Exception e) { continue; }
							if(!foundPlace) {
								String rawName = Utils.unformatText(tabNames.get(i).getDisplayName().getUnformattedText()).replace(" ", "").toLowerCase();
								if(name.toLowerCase().startsWith("area:")) {
									
									String loc = rawName.replace("area:", "");
									
									
									
									foundPlace = true;
									
									if(!(loc.equals(SBP.sbLocation))) {
										new LobbySwapEvent();
										SBP.sbLocation = loc;
									}
									
								} //else if(name.toLowerCase().startsWith("server:")) {
									//String oldLobby = rawName.replace("server:", "");
								
									//if(!LobbySwapEvent.currLobby.equals(oldLobby)) {
										//LobbySwapEvent.currLobby=oldLobby;
										//new LobbySwapEvent();
									//}
								//}
							}
							
					
					if(SBP.sbLocation.equals("dwarvenmines") && DwarvenQuestTracker.questTrackToggle) {
						String name2 = name;
						name = name.toLowerCase(); 
						if(name.endsWith("%") || name.endsWith("done") || name.startsWith("commissions")) {
							
							if(name.contains("slayer") || name.contains("mithril") || name.contains("titanium") || name.contains("raffle") || name.contains("star") || name.contains("goblin")) {
								g++;
								int isBarToggle = 2; int subtractOff = 0; String[] temp3 = name2.split(":"); String text = ""; String percentbar = "";
									
								text+=Colors.WHITE+temp3[0]+":"; String color = Colors.RED; if(name.contains("done")) { color = Colors.GREEN; } text+=color+temp3[1];
								DwarvenQuestTracker.currString+=";"+text;
								if(DwarvenQuestTracker.questTrackBarToggle) {
									String borderColor = Utils.getColorFromInt(DwarvenQuestTracker.borderColorID);
									String yesColor = Utils.getColorFromInt(DwarvenQuestTracker.yesColorID);
									String noColor = Utils.getColorFromInt(DwarvenQuestTracker.noColorID);
									isBarToggle=1; subtractOff=12; int percent = 0;
									try { percent = (int) Double.parseDouble(temp3[1].replace("%","").replace(" ", "")); if(percent >= 1) {percent -=1;} } catch(NumberFormatException e) { percent = 100; }
									percentbar+=borderColor+"["+yesColor;
									for(int f=0;f<50;f++) { if(percent > f*2) { percentbar+="|"; } else { 
										
										
										if(percent == f*2 || percent == (f*2)-1) {
											percentbar+=noColor; 
										}
										
										
										percentbar+="|"; 
										} 
									} percentbar+=borderColor+"]";
									
									DwarvenQuestTracker.currString+=";"+percentbar;
								} 
								}
							}
						} else {
							DwarvenQuestTracker.currString="";
						}
						
					}
				 	if(!foundPlace) {
						SBP.sbLocation = "privateworld";
					}
				 	
				 	
				 	if(SBP.sbLocation.equals("dwarvenmines") && DwarvenDrillFuel.fuelToggle && heldItem.contains("drill")) {
						int fuelBorderColor = 0;
						int fuelFuelColor = 0;
						int fuelEmptyColor = 0;
						List<String> lore = player.getHeldItem().getTooltip(player, false);
						String hName = Utils.unformatText(player.getHeldItem().getDisplayName()).replace(" ", "");
						
						//if(DwarvenDrillFuel.heldName.equals(hName)) { } else {
							DwarvenDrillFuel.heldName = hName;
							for(int u=0;u<lore.size();u++) {
								String cLore = Utils.unformatText(lore.get(u));
								if(cLore.startsWith("Fuel:")) {
									DwarvenDrillFuel.searchForItem=u;
									continue;
								}
							}
						//}
						
						if(DwarvenDrillFuel.searchForItem != 0) {
							String curr = Utils.unformatText(lore.get(DwarvenDrillFuel.searchForItem));
							lore.clear();
							curr = curr.replace(",", "").replace("k", "000").replace("Fuel: ", "");
							Double fuelPercent;
							double currFuel;
							double totalFuel;
							DecimalFormat df = new DecimalFormat("#.##");
							try {
							String[] temp = curr.split("/"); 
							currFuel = Integer.parseInt(temp[0]); 
							totalFuel = Integer.parseInt(temp[1]);
							
							fuelPercent = currFuel/totalFuel*100; 
							} catch(Exception e) {
								e.printStackTrace();
								fuelPercent = 0.0;
								currFuel = 0;
								totalFuel = 0;
							}
							
							int finalCurrFuel = (int) Math.round(currFuel); int finalTotalFuel = (int) Math.round(totalFuel); String finalFuelPercent = df.format((Math.round(fuelPercent)));
							int barProgress = Integer.parseInt(finalFuelPercent);
							if(barProgress <= 10) { fuelFuelColor = 2; fuelBorderColor = 1; } else if(barProgress > 50) { fuelFuelColor = 5; fuelBorderColor = 6; } else { fuelFuelColor = 4; fuelBorderColor = 3; }

							String color1 = Utils.getColorFromInt(fuelBorderColor); String color2 = Utils.getColorFromInt(fuelFuelColor);
							
							String barString = color1+"["+color2;
							//for(int f=0;f<50;f++) { if(barProgress > f*2) { barString+="|"; } else { if(barProgress == f) { barString+=Colors.DARK_GRAY; } barString+="|"; } } barString+=color1+"]";
							for(int f=0;f<50;f++) { if(barProgress > f*2) { barString+="|"; } else { 
								
								
								if(barProgress == f*2 || barProgress == (f*2)-1) {
									barString+=Colors.DARK_GRAY; 
								}
								
								
								barString+="|"; 
								} 
							} barString+=color1+"]";
							
							
							
							DwarvenDrillFuel.currString+=Colors.WHITE+"Drill Fuel: "+color2+barProgress+"% "+color1+"("+color2+finalCurrFuel+color1+"/"+color2+finalTotalFuel+color1+");";
							DwarvenDrillFuel.currString+=Colors.WHITE+barString+Colors.WHITE;
						}
					}
				
				}
				
				//GUI ELEMENTS
				
				if(DwarvenDrillFuel.fuelToggle && heldItem.contains("drill") && SBP.sbLocation.equals("dwarvenmines")) { if(DwarvenDrillFuel.fuelInDwarven) { if(ChatRecieveEvent.inMines()) { new DwarvenDrillFuel(); } } else { new DwarvenDrillFuel(); } }
				//if(DwarvenDrillFuel.fuelToggle && heldItem.contains("drill")) {
					//Utils.print("eee");
					//new DwarvenDrillFuel();
				//}
				
				if(DwarvenTimer.dwarvenTimerToggle) { new DwarvenTimer(Minecraft.getMinecraft()); }
				//Utils.print(DwarvenTimer.dwarvenTimerToggle);
				if(DwarvenQuestTracker.questTrackToggle && SBP.sbLocation.equals("dwarvenmines")) { new DwarvenQuestTracker(); }
				
				if(DwarvenPickaxeTimer.pickTimerToggle) { Boolean passThrough = true; if(DwarvenPickaxeTimer.onlyWhenHolding) { try { if(!(heldItem.contains("pickaxe") || heldItem.contains("drill"))) { passThrough=false; } } catch (NullPointerException e) { passThrough = false;}} if(DwarvenPickaxeTimer.onlyInDwarven) { if(!SBP.sbLocation.equals("dwarvenmines")) { passThrough=false; } } if(passThrough) { new DwarvenPickaxeTimer(); }}
				
				//PUZZLER PARTICLES
				if(puzzlerParticles && j == 1) { Random rand = new Random(); double motionY = rand.nextGaussian() * 0.02D; world.spawnParticle(EnumParticleTypes.SPELL, puzzlerX+0.5, 196, puzzlerZ+0.5, 0, motionY, 0); }
		
				//PUZZLE IMAGE
				if(imageID != "NONE") { new PuzzleImage(Minecraft.getMinecraft()); }
				
			}
		
		}
		
		
		if(i == 1) {
			String c = SettingMenu.currOptionName;
			
			
			if(SettingMenu.settingsMenuOpen) {
				ConfigHandler.updateConfig(c);
			}
			
			//PUZZLE SOLVERS
			if(Minecraft.getMinecraft().thePlayer.onGround) {
				if(DataGetter.findBool("iceSolverToggle")) {
					IcePathHandler.run();
				}
			
				if(!imageID.contains("box") && DataGetter.findBool("boxSolverToggle")) {
					BoxPuzzleHandler.run();
				}
			
			}

			
		}
		if(i == 200) {
			i=0;
		}
		if(j == 15) {
			j=0;
		}
		if(k >= Minecraft.getDebugFPS()*5) {
			k=0;
		}
		if(m >= (Minecraft.getDebugFPS()/2)) {
			Utils.checkIfOnSkyblock();
			m=0;
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
