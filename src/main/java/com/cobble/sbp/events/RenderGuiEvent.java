package com.cobble.sbp.events;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.user.ChatRecieveEvent;
import com.cobble.sbp.events.user.MenuClickEvent;
import com.cobble.sbp.events.user.PressKeyEvent;
import com.cobble.sbp.gui.menu.GameThing;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.menu.settings.SettingMove;
import com.cobble.sbp.gui.menu.settings.SettingMoveAll;
import com.cobble.sbp.gui.screen.dwarven.*;
import com.cobble.sbp.gui.screen.misc.BulwarkHelper;
import com.cobble.sbp.gui.screen.misc.JerryTimer;
import com.cobble.sbp.gui.screen.misc.LockedSlots;
import com.cobble.sbp.gui.screen.misc.ThreeDimensionalRendering;
import com.cobble.sbp.gui.screen.nether.KuudraReadyWarning;
import com.cobble.sbp.gui.screen.nether.KuudraShopPrices;
import com.cobble.sbp.handlers.*;
import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.simplejson.parser.JSONParser;
import com.cobble.sbp.simplejson.parser.ParseException;
import com.cobble.sbp.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.event.HoverEvent;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class RenderGuiEvent {
	public static boolean helpMenu = false;

	long lastUpdate = System.currentTimeMillis();

	public static boolean puzzlerParticles = false;
	public static int puzzlerX = 181;
	public static int puzzlerZ = 135;
	public static String actionBar = "";
	public static ArrayList<NetworkPlayerInfo> tabNames = new ArrayList<>();

	public static int oldGuiScale = -1;
	
	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event) {
		if (event.type == RenderGameOverlayEvent.ElementType.EXPERIENCE || event.type == RenderGameOverlayEvent.ElementType.JUMPBAR) {

		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayerSP player = mc.thePlayer;
		World world = mc.theWorld;



		new AnimationHandler();


		//Gets width/height of the screen while in a menu
		try { SBP.width = mc.currentScreen.width; SBP.height = mc.currentScreen.height; } catch(Exception ignored) { }
		
		//Allows for /sbp GUI to show up on servers by adding 1 frame delay
		if(helpMenu) {
			helpMenu=false;
			if(!SettingMenu.currSettingMenu.equals("main")) {

				switch(SettingMenu.currSettingMenu) {
					case "moveall": mc.displayGuiScreen(new SettingMoveAll()); break;
					case "move": mc.displayGuiScreen(new SettingMove()); break;
					case "game": mc.displayGuiScreen(new GameThing()); break;
				}
				SettingMenu.currSettingMenu = "main";
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

		if(!mc.isSingleplayer()) {
			if(SBP.onSkyblock) {
				if(!(SBP.titleString.equals(""))) { GuiUtils.drawTitle(); }

				new CrystalLobbyDay();
				BulwarkHelper.run();
				//GUI ELEMENTS
				if(ChatRecieveEvent.inMines() && DwarvenPickaxeTimer.pickTimerToggle) {
					boolean passThrough = false;
					if(!DwarvenPickaxeTimer.onlyWhenHolding) {
						passThrough = true;
					} else {
						try {
							String pickID = SBUtils.getSBID();
							if(pickID.contains("pickaxe") || pickID.contains("drill") || pickID.contains("gauntlet")) { passThrough = true; }
						} catch(Exception ignored) {}

					}

					if(passThrough) {
						new DwarvenPickaxeTimer();
					}
				}


				if(SBP.sbLocation.equals("dwarvenmines") || SBP.sbLocation.equals("crystalhollows")) {
					new DwarvenGui();
					if(DwarvenTimer.dwarvenTimerToggle) { new DwarvenTimer(DwarvenTimer.posX, DwarvenTimer.posY); }
					if(puzzlerParticles) { Random rand = new Random(); double motionY = rand.nextGaussian() * 0.02D; world.spawnParticle(EnumParticleTypes.SPELL, puzzlerX+0.5, 196, puzzlerZ+0.5, 0, motionY, 0); }
					

				}
				if(SBP.sbLocation.equals("crystalhollows")) {
					new CrystalHollowsMap();
					new ChatOpenGui();
					new NucleusEntrance();
				}

				if(JerryTimer.jerryToggle) { new JerryTimer(); }
				
			}
		
		}


			KuudraShopPrices.draw();
			KuudraReadyWarning.draw();

			SBUtils.checkIfOnSkyblock();

			if(System.currentTimeMillis() > lastUpdate+250) {
				lastUpdate+=250;

				try { if(SBUtils.getSBID().equals("coco_chopper")) { for(String ench : Objects.requireNonNull(SBUtils.getEnchants(player.getHeldItem())).getKeySet()) { if(ench.equals("replenish")) { if(!DataGetter.findBool("core.easterEgg.coco")) { ChatStyle achievement = new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("§aFormer Glory\n§rRestore the Coco Chopper to\nits former glory\n\n§7Reward:\n§8+ §3[§bSBP§3] §bEaster Egg!"))); player.addChatMessage(new ChatComponentText("§e§ka§a>>   §aAchievement Unlocked: §6§6Former Glory§a   <<§e§ka").setChatStyle(achievement)); ConfigHandler.newObject("core.easterEgg.coco", true); } } } } } catch(Exception ignored) {}

				if(SettingMenu.settingsMenuOpen) {
					try {
						String c = SettingMenu.settingIDs.get(SettingMenu.clickedSubOption);
						ConfigHandler.updateConfig(c);
					} catch(Exception ignored) {
						ConfigHandler.updateConfig("");
					}
				}
				if(SBP.onSkyblock) {
					new TabListHandler();
					new GuiChestHandler();
				}
			}
			GlStateManager.enableBlend();


			//ThreeDimensionalRendering.draw(5,5);





		}
	}
	
	
	@SubscribeEvent
	public void renderChestMenu(GuiScreenEvent.DrawScreenEvent.Pre event) {
		MenuClickEvent.mouseX = event.mouseX;
		MenuClickEvent.mouseY = event.mouseY;
		SBP.width = event.gui.width;
		SBP.height = event.gui.height;
	}

	@SubscribeEvent
	public void renderChestMenu(GuiScreenEvent.DrawScreenEvent.Post event) {
		if(!SBP.onSkyblock) {return;}
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.currentScreen instanceof GuiChest) {

			switch(GuiChestHandler.menuName) {
				case "commissions":
					new DwarvenCompletedCommissions(); return;
				case "craft_item":
					new LockedSlots(); return;
				case "heart_of_the_mountain":
					return;


			}

		}
		try {
			GuiScreen guiScreen = mc.currentScreen;

			if(guiScreen instanceof GuiChest) {
				IInventory cc = ((ContainerChest) ((GuiChest) guiScreen).inventorySlots).getLowerChestInventory();
				if(GuiChestHandler.menuName.equals("trick_or_treat_bag")) {
					Colors.resetColor();
					int green = 0;
					int purple = 0;
					for(int i=0;i<cc.getSizeInventory();i++) {
						try {
							String currID = SBUtils.getSBID(cc.getStackInSlot(i));
							if(currID.equals("green_candy")) {
								green+=cc.getStackInSlot(i).stackSize;
							} else if(currID.equals("purple_candy")) {
								purple+=cc.getStackInSlot(i).stackSize;
							}
						} catch(Exception ignored) {}
					}
					GlStateManager.pushMatrix();
					GlStateManager.translate(0, 0, 100);
					String[] str = new String[]{Colors.GOLD+"  "+Colors.GOLD+Colors.UNDERLINE+"Candy:", Colors.GREEN+"Green: "+Colors.AQUA+green, Colors.DARK_PURPLE+"Purple: "+Colors.AQUA+purple};
					GlStateManager.disableLighting();
					GuiUtils.drawString(str, SBP.width/2+90, SBP.height/2-80, 4);
					GlStateManager.popMatrix();

				}
			}
		} catch(Exception ignored) { }

		GlStateManager.pushMatrix();
		GlStateManager.popMatrix();




		
		//HIGHLIGHT SLOTS
		/*for(int i=0;i<highlightSlots.size();i++) {
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
		inFrontOfItem.clear();*/
		
	}

	HashMap<Integer, Color> wpColors = new HashMap<>();

	@SubscribeEvent
	public void onWorldRender(RenderWorldLastEvent event) throws IOException, ParseException {

		if(!SBP.onSkyblock) {return;}
		//PressKeyEvent.path.draw(event);
		GlStateManager.disableDepth();
		GlStateManager.disableCull();
		GlStateManager.enableLighting();

		if(wpColors.size() == 0) {

				ResourceLocation colors = new ResourceLocation(Reference.MODID, "data/crystal_waypoint_colors.json");
				InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(colors).getInputStream();

				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder colorJson = new StringBuilder();
				String currLine = reader.readLine();
				while(currLine != null) {
					colorJson.append(currLine.replace(" ", ""));
					currLine=reader.readLine();
				}

				JSONObject obj = (JSONObject) new JSONParser().parse(colorJson.toString());
				for(Object clr : obj.keySet()) {
					int clrID = Integer.parseInt(clr+"");
					JSONObject rgb = (JSONObject) obj.get(clr+"");
					int r = Math.toIntExact((long) rgb.get("r"));
					int g = Math.toIntExact((long) rgb.get("g"));
					int b = Math.toIntExact((long) rgb.get("b"));
					wpColors.put(clrID, new Color(r, g, b, 255));
				}
		}



		GlStateManager.enableDepth();
		GlStateManager.enableCull();
		GlStateManager.disableLighting();
		if(SBP.sbLocation.equals("crystalhollows")) {
			for(ArrayList<Object> wp : CrystalHollowsMap.waypoints) {
				try {
					String name = (String) wp.get(0);
					int mapXPos = ((int) wp.get(1))/2;
					int mapZPos = ((int) wp.get(2))/2;
					int locID = CrystalHollowsMap.locs[mapXPos][mapZPos];
					if(name.equals("loc")) { name = CrystalHollowsMap.locName(locID, ((int) wp.get(1))/2, ((int) wp.get(2))/2)[0]; }
					if(locID == -1) {
						if(mapXPos > 21 && mapXPos < 29 && mapZPos > 21 && mapZPos < 29) { locID = 0; } else if(mapXPos < 25 && mapZPos < 25) { locID = 3; } else if(mapXPos >= 25 && mapZPos < 25) { locID = 5; } else if(mapXPos < 25) { locID =  1; } else { locID = 6; } }
					double wpX = (int) wp.get(3);
					double wpY = (int) wp.get(4);
					double wpZ = (int) wp.get(5);
					if(wpY == -1) {wpY = Minecraft.getMinecraft().thePlayer.posY;}
					WorldUtils.renderWaypoint(name, wpX, wpY, wpZ, wpColors.get(locID), event);
				} catch(Exception e) { e.printStackTrace(); }
			}

			if(NucleusEntrance.x != -1) {
				String textColor = Colors.textColor(DataGetter.findStr("dwarven.nucleusEntrance.textColor"));



				if(DataGetter.findBool("dwarven.nucleusEntrance.beaconToggle")) {
					if(wpColors.size() == 0) {

						ResourceLocation colors = new ResourceLocation(Reference.MODID, "data/crystal_waypoint_colors.json");
						InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(colors).getInputStream();

						BufferedReader reader = new BufferedReader(new InputStreamReader(in));
						StringBuilder colorJson = new StringBuilder();
						String currLine = reader.readLine();
						while(currLine != null) {
							colorJson.append(currLine.replace(" ", ""));
							currLine=reader.readLine();
						}

						JSONObject obj = (JSONObject) new JSONParser().parse(colorJson.toString());
						for(Object clr : obj.keySet()) {
							int clrID = Integer.parseInt(clr+"");
							JSONObject rgb = (JSONObject) obj.get(clr+"");
							int r = Math.toIntExact((long) rgb.get("r"));
							int g = Math.toIntExact((long) rgb.get("g"));
							int b = Math.toIntExact((long) rgb.get("b"));
							wpColors.put(clrID, new Color(r, g, b, 255));
						}
					}
					WorldUtils.renderWaypoint(textColor+"Closest Nucleus Entrance", NucleusEntrance.x, NucleusEntrance.y, NucleusEntrance.z, wpColors.get(0), event);
				} else {
					WorldUtils.renderWaypointText(textColor+"Closest Nucleus Entrance", new BlockPos(NucleusEntrance.x, NucleusEntrance.y, NucleusEntrance.z), event.partialTicks);
				}
			}

			new ChestHelper(event);

		}




	}
}
