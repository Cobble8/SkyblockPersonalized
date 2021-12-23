package com.cobble.sbp.gui.menu.settings;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.gui.screen.dwarven.CrystalHollowsMap;
import com.cobble.sbp.gui.screen.dwarven.CrystalLobbyDay;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.gui.screen.misc.JerryTimer;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

public class SettingMoveAll extends GuiScreen {

	public static ArrayList<String> settingNames = new ArrayList<>();
	public static ArrayList<String> settingCoords = new ArrayList<>();
	public static ArrayList<String> settingIDs = new ArrayList<>();
	public static int selectedOption = settingCoords.size()+10;
	public static ArrayList<Integer> settingWidths = new ArrayList<>();
	public static ArrayList<Integer> settingHeights = new ArrayList<>();

	boolean movingOption = false;
	int selectedXCoord = 0;
	int selectedYCoord = 0;
	ArrayList<Integer> xList = new ArrayList<>();
	ArrayList<Integer> yList = new ArrayList<>();


	@Override
	public void initGui() {
		Minecraft mc = Minecraft.getMinecraft();

		xList.clear();
		yList.clear();

		selectedOption = settingCoords.size()+10;

		if(mc.gameSettings.guiScale == 2 && RenderGuiEvent.oldGuiScale != -1) {
			mc.gameSettings.guiScale=RenderGuiEvent.oldGuiScale;
			RenderGuiEvent.helpMenu=true;
			SettingMenu.currSettingMenu="moveall";
			mc.thePlayer.closeScreen();
		}

		SettingMenu.settingsMenuOpen=false;
		super.initGui();
		//k=0;
	}


	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		this.drawDefaultBackground();


		GlStateManager.enableBlend();

		Boolean gridLocking = DataGetter.findBool("core.menu.gridLocking");

		Minecraft mc = Minecraft.getMinecraft();


		ResourceLocation settingBorder = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
		mc.getTextureManager().bindTexture(settingBorder);
		GlStateManager.color(0.1F, 0.1F, 0.1F, 0.8F);
		int strWidth = fontRendererObj.getStringWidth("Click on a GUI element and click on its new location to move it.");
		drawModalRectWithCustomSizedTexture((width/2)-(strWidth/2)-6, this.height/2-30, 0, 0, strWidth+12, 43, strWidth+12, 43);

		String infoText = Colors.YELLOW+"Press "+Colors.AQUA+"ESCAPE "+Colors.YELLOW+"to go back.";
		mc.fontRendererObj.drawString(Colors.YELLOW+Colors.BOLD+"Edit GUI Locations", this.width/2-(fontRendererObj.getStringWidth(Colors.BOLD+"Edit GUI Locations")/2), this.height/2-24, 0x10, false);
		mc.fontRendererObj.drawString(Colors.YELLOW+"Click on a GUI element and click on its new location to move it.", this.width/2-(strWidth/2), this.height/2-12, 0x10, false);
		mc.fontRendererObj.drawString(infoText, this.width/2-(fontRendererObj.getStringWidth(infoText)/2), this.height/2, 0x10, false);


		if(xList.size() < settingCoords.size()) {
			for(int i=0;i<settingCoords.size();i++) {
				String[] splitCoords = settingCoords.get(i).split(";");
				int imgWidth = 100;
				int imgHeight = 100;
				try {
					imgWidth = settingWidths.get(i);
					imgHeight = settingHeights.get(i);
				} catch(Exception ignored) { }
				int x = Integer.parseInt(splitCoords[0]);
				int y = Integer.parseInt(splitCoords[1]);

				xList.add(x);
				yList.add(y);
				xList.add(x+imgWidth);
				yList.add(y+imgHeight);


			}
			xList.add(this.width/2);
			xList.add(this.width);
			xList.add(0);
			yList.add(this.height/2);
			yList.add(this.height);
			yList.add(0);
		}

		boolean hoveredOption = false;

		for(int i=0;i<settingCoords.size();i++) {





			String[] splitCoords = settingCoords.get(i).split(";");
			try {
				int imgWidth = 100;
				int imgHeight = 100;
				try {
					imgWidth = settingWidths.get(i);
					imgHeight = settingHeights.get(i);
				} catch(Exception ignored) { }
				int x = Integer.parseInt(splitCoords[0]);
				int y = Integer.parseInt(splitCoords[1]);
				int x2 = x+imgWidth;
				int y2 = y+imgHeight;
				String id = settingIDs.get(i);

				if(!movingOption) {
					if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
						if(mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2 ) {
							int newX = mouseX-x;
							int newY = mouseY-y;
							selectedOption = i;
							selectedXCoord=newX;
							selectedYCoord=newY;
							movingOption = true;
						}
					}



				}

				mc.getTextureManager().bindTexture(settingBorder);


				if((selectedOption == i || !movingOption) && selectedOption < settingCoords.size()) {
					if((Mouse.isButtonDown(0) || Mouse.isButtonDown(1))) {
						int xOff = (mouseX)-selectedXCoord;
						int yOff = (mouseY)-selectedYCoord;

						//mc.getTextureManager().bindTexture(settingBorder);
						if(gridLocking) {

							int px = DataGetter.findInt("core.menu.gridLockingPx");

							GlStateManager.color(0.1F, 0.1F, 0.1F, 0.8F);
							drawModalRectWithCustomSizedTexture((width/2)-(strWidth/2)-6, this.height/2-30+43, 0, 0, strWidth+12, 12, strWidth+12, 12);
							mc.fontRendererObj.drawString(Colors.YELLOW+"Grid Snapping is currently "+Colors.GREEN+"Enabled "+Colors.YELLOW+"at "+Colors.AQUA+px+Colors.YELLOW+" pixels.", this.width/2-(fontRendererObj.getStringWidth("Grid Snapping is currently Enabled at "+px+" pixels")/2), this.height/2+12, 0x10, true);
							mc.getTextureManager().bindTexture(settingBorder);
							for(int j=0;j<xList.size();j++) {
								int xLock = xList.get(j);
								int yLock = yList.get(j);

								if(Utils.inRange(xLock, xOff, px)) {
										xOff = xLock;
										GlStateManager.color(1, 1, 0, 1);
										drawModalRectWithCustomSizedTexture(xOff, 0, 0, 0, 1, this.height, 1, this.height);
								}

								else if(Utils.inRange(xLock, xOff+imgWidth, px)) {
										xOff = xLock-imgWidth;
										GlStateManager.color(1, 1, 0, 1);
										drawModalRectWithCustomSizedTexture(xOff+imgWidth, 0, 0, 0, 1, this.height, 1, this.height);
								}

								else if(Utils.inRange(yLock, yOff, px)) {
										yOff = yLock;
										GlStateManager.color(1, 1, 0, 1);
										drawModalRectWithCustomSizedTexture(0, yOff, 0, 0, this.width, 1, this.width, 1);
								}
								else if(Utils.inRange(yLock, yOff+imgHeight, px)) {
										yOff = yLock-imgHeight;
										GlStateManager.color(1, 1, 0, 1);
										drawModalRectWithCustomSizedTexture(0, yOff+imgHeight, 0, 0, this.width, 1, this.width, 1);
								}
							}
						} else {

							GlStateManager.color(0.1F, 0.1F, 0.1F, 0.8F);
							drawModalRectWithCustomSizedTexture((width/2)-(strWidth/2)-6, this.height/2-30+43, 0, 0, strWidth+12, 12, strWidth+12, 12);
							mc.fontRendererObj.drawString(Colors.YELLOW+"Grid Snapping is currently "+Colors.RED+"Disabled"+Colors.YELLOW+".", this.width/2-(fontRendererObj.getStringWidth("Grid Snapping is currently Disabled.")/2), this.height/2+12, 0x10, true);
							mc.getTextureManager().bindTexture(settingBorder);
						}

						settingCoords.set(selectedOption, xOff+";"+yOff);
						ConfigHandler.newObject(id+".x", xOff);
						ConfigHandler.newObject(id+".y", yOff);
					} else {

						xList.clear();
						yList.clear();

						selectedOption = settingCoords.size()+10;
						movingOption = false;
					}
				}

				String text = settingNames.get(i);



				if(selectedOption == i) {

					GlStateManager.color(0.2F, 0.2F, 0.2F, 0.6F);

				} else {

					GlStateManager.color(0.3F, 0.3F, 0.3F, 0.4F);
				}
				drawModalRectWithCustomSizedTexture(x, y, 0, 0, imgWidth, imgHeight, imgWidth, imgHeight);

				String textColor = Colors.WHITE;
				if(selectedOption == i) {
					GlStateManager.color(0.3F, 0.8F, 1, 1);
					textColor = Colors.YELLOW;

				} else {
					GlStateManager.color(0.3F, 0.8F, 1, 1);
				}


				if(mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2 && !hoveredOption) {
					hoveredOption=true;
					textColor = Colors.YELLOW;
					GlStateManager.color(1, 1, 0, 1);
				}

				drawModalRectWithCustomSizedTexture(x, y, 0, 0, imgWidth, 1, imgWidth, 1);
				drawModalRectWithCustomSizedTexture(x, y+imgHeight-1, 0, 0, imgWidth, 1, imgWidth, 1);
				drawModalRectWithCustomSizedTexture(x, y, 0, 0, 1, imgHeight, 1, imgHeight);
				drawModalRectWithCustomSizedTexture(x+imgWidth, y, 0, 0, 1, imgHeight, 1, imgHeight);
				/*if(mouseX > x-5 && mouseX <= x2+5 && mouseY > y-5 && mouseY <= y2+5) {
					drawModalRectWithCustomSizedTexture(x-1, y-1, 0, 0, 3, 3, 1 , 1);
					drawModalRectWithCustomSizedTexture(x2-1, y-1, 0, 0, 3, 3, 1 , 1);
					drawModalRectWithCustomSizedTexture(x-1, y2-2, 0, 0, 3, 3, 1 , 1);
					drawModalRectWithCustomSizedTexture(x2-1, y2-2, 0, 0, 3, 3, 1 , 1);

				}*/

				int textX = x+(imgWidth/2)-(fontRendererObj.getStringWidth(text) / 2);
				int textY = y-4+(imgHeight/2);

				//new MoveGuiCore(id, x, y);
				mc.fontRendererObj.drawString(textColor+text, textX, textY, 0x10, true);

			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		GlStateManager.disableBlend();

	}


	protected void keyTyped(char par1, int par2) {
		if(selectedOption > settingCoords.size()) {
			if(par2 == Keyboard.KEY_ESCAPE || par2 == Keyboard.KEY_E) {

				SettingMenu.settingsMenuOpen=true;

				if(RenderGuiEvent.oldGuiScale == 0 || RenderGuiEvent.oldGuiScale > 2) {
					mc.gameSettings.guiScale = 2;
				}


				DwarvenGui.posX = DataGetter.findInt("dwarven.gui.x");
				DwarvenGui.posY = DataGetter.findInt("dwarven.gui.y");

				DwarvenTimer.posX = DataGetter.findInt("dwarven.eventTimer.x");
				DwarvenTimer.posY = DataGetter.findInt("dwarven.eventTimer.y");

				JerryTimer.jerryX = DataGetter.findInt("misc.jerryTimer.x");
				JerryTimer.jerryY = DataGetter.findInt("misc.jerryTimer.y");

				SecretImage.imgX = DataGetter.findInt("dungeon.secretImage.x");
				SecretImage.imgY = DataGetter.findInt("dungeon.secretImage.y");

				CrystalHollowsMap.mapX = DataGetter.findInt("dwarven.crystalMap.x");//
				CrystalHollowsMap.mapY = DataGetter.findInt("dwarven.crystalMap.y");

				CrystalLobbyDay.daysX = DataGetter.findInt("dwarven.crystalDay.x");
				CrystalLobbyDay.daysY = DataGetter.findInt("dwarven.crystalDay.y");

				Minecraft.getMinecraft().displayGuiScreen(new SettingMenu());
				Utils.playClickSound();
			}
		}

	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public static void addGui(String name, String id, String idList, int width, int height) {
		try {
			String[] idList2 = idList.split(", ");
			boolean passThrough = false;
			for (String s : idList2) {
				if (DataGetter.findBool(s)) {
					passThrough = true;
				}
			}
			if(!passThrough) {  return; }
			int x = DataGetter.findInt(id+".x");
			int y = DataGetter.findInt(id+".y");
			String coords = x+";"+y;//+";"+size;
			settingIDs.add(id);
			settingCoords.add(coords);
			settingNames.add(name);
			settingWidths.add(width);
			settingHeights.add(height);
		} catch(Exception e) {
			e.printStackTrace();
		}


	}
}
