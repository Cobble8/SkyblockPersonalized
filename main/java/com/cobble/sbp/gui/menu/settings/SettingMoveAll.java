package com.cobble.sbp.gui.menu.settings;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.gui.screen.misc.AbilityMessages;
import com.cobble.sbp.gui.screen.misc.ComboMessages;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class SettingMoveAll extends GuiScreen {
	
	public static ArrayList<String> settingNames = new ArrayList();
	public static ArrayList<String> settingCoords = new ArrayList();
	public static ArrayList<String> settingIDs = new ArrayList();
	public static int selectedOption = settingCoords.size()+10;
	public static ArrayList<Integer> settingWidths = new ArrayList();
	public static ArrayList<Integer> settingHeights = new ArrayList();
	
	private static Boolean isMouseDown = false;
	Boolean movingOption = false;
	int selectedXCoord = 0;
	int selectedYCoord = 0;
	ArrayList<Integer> xList = new ArrayList();
	ArrayList<Integer> yList = new ArrayList();
	
	
	@Override
	public void initGui() {
		Minecraft mc = Minecraft.getMinecraft();
		
		xList.clear();
		yList.clear();
		
		selectedOption = settingCoords.size()+10;
		//settingWidths.clear();
		//settingHeights.clear();
		//for(int i=0;i<settingCoords.size();i++) {
			//String[] splitCoords = settingCoords.get(i).split(";");
			//settingWidths.add(Integer.parseInt(splitCoords[0]));
			//settingHeights.add(Integer.parseInt(splitCoords[1]));
		//}
		
		if(mc.gameSettings.guiScale == 2 && RenderGuiEvent.oldGuiScale != -1) {
			mc.gameSettings.guiScale=RenderGuiEvent.oldGuiScale;
			RenderGuiEvent.helpMenu=true;
			RenderGuiEvent.currSettingMenu="moveall";
			mc.thePlayer.closeScreen();
		}
		
		SettingMenu.settingsMenuOpen=false;
		super.initGui();
		//k=0;
	}
	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		if(SettingMenu.currTheme == 0) {
			this.drawDefaultBackground();
		}
		
		GlStateManager.enableBlend();
		
		Boolean gridLocking = (Boolean) DataGetter.findBool("gridLockingToggle");
		
		Minecraft mc = Minecraft.getMinecraft();
		
		
		ResourceLocation settingBorder = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
		mc.getTextureManager().bindTexture(settingBorder);
		GlStateManager.color(0.1F, 0.1F, 0.1F, 0.8F);
		int strWidth = fontRendererObj.getStringWidth("Click on a GUI element and click on its new location to move it.");
		this.drawModalRectWithCustomSizedTexture((width/2)-(strWidth/2)-6, this.height/2-30, 0, 0, strWidth+12, 43, strWidth+12, 43);
		
		String infoText = Colors.YELLOW+"Press "+Colors.AQUA+"ESCAPE "+Colors.YELLOW+"to go back.";
		mc.fontRendererObj.drawString(Colors.YELLOW+Colors.BOLD+"Edit GUI Locations", this.width/2-(fontRendererObj.getStringWidth(Colors.BOLD+"Edit GUI Locations")/2), this.height/2-24, 0x10, false);
		mc.fontRendererObj.drawString(Colors.YELLOW+"Click on a GUI element and click on its new location to move it.", this.width/2-(strWidth/2), this.height/2-12, 0x10, false);
		mc.fontRendererObj.drawString(infoText, this.width/2-(fontRendererObj.getStringWidth(infoText)/2), this.height/2, 0x10, false);
		
		String gridEnabled = Colors.RED+"Disabled";

		
		
		if(xList.size() < settingCoords.size()) {
			for(int i=0;i<settingCoords.size();i++) {
				String[] splitCoords = settingCoords.get(i).split(";");
				int imgWidth = 100;
				int imgHeight = 100;
				try {
					imgWidth = settingWidths.get(i);
					imgHeight = settingHeights.get(i);
				} catch(Exception e) { }
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
		
		Boolean hoveredOption = false;
		
		for(int i=0;i<settingCoords.size();i++) {
			
			
			
			
			
			String[] splitCoords = settingCoords.get(i).split(";");
			try {
				int imgWidth = 100;
				int imgHeight = 100;
				try {
					imgWidth = settingWidths.get(i);
					imgHeight = settingHeights.get(i);
				} catch(Exception e) { }
				int x = Integer.parseInt(splitCoords[0]);
				int y = Integer.parseInt(splitCoords[1]);
				int x2 = x+imgWidth;
				int y2 = y+imgHeight;

				String id = settingIDs.get(i);

				if(!movingOption) {
					if(mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2 ) {
						if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
							
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
							
							int px = Integer.parseInt(DataGetter.find("gridLockingPx")+"");
							
							GlStateManager.color(0.1F, 0.1F, 0.1F, 0.8F);
							this.drawModalRectWithCustomSizedTexture((width/2)-(strWidth/2)-6, this.height/2-30+43, 0, 0, strWidth+12, 12, strWidth+12, 12);
							mc.fontRendererObj.drawString(Colors.YELLOW+"Grid Snapping is currently "+Colors.GREEN+"Enabled "+Colors.YELLOW+"at "+Colors.AQUA+px+Colors.YELLOW+" pixels.", this.width/2-(fontRendererObj.getStringWidth("Grid Snapping is currently Enabled at "+px+" pixels")/2), this.height/2+12, 0x10, true);
							mc.getTextureManager().bindTexture(settingBorder);
							for(int j=0;j<xList.size();j++) {
								int xLock = xList.get(j);
								int yLock = yList.get(j);

								if(Utils.inRange(xLock, xOff, px)) {
										xOff = xLock;
										GlStateManager.color(1, 1, 0, 1);
										this.drawModalRectWithCustomSizedTexture(xOff, 0, 0, 0, 1, this.height, 1, this.height);
								}
								
								else if(Utils.inRange(xLock, xOff+imgWidth, px)) {
										xOff = xLock-imgWidth;
										GlStateManager.color(1, 1, 0, 1);
										this.drawModalRectWithCustomSizedTexture(xOff+imgWidth, 0, 0, 0, 1, this.height, 1, this.height);
								}
								
								else if(Utils.inRange(yLock, yOff, px)) {
										yOff = yLock;
										GlStateManager.color(1, 1, 0, 1);
										this.drawModalRectWithCustomSizedTexture(0, yOff, 0, 0, this.width, 1, this.width, 1);
								}
								else if(Utils.inRange(yLock, yOff+imgHeight, px)) {
										yOff = yLock-imgHeight;
										GlStateManager.color(1, 1, 0, 1);
										this.drawModalRectWithCustomSizedTexture(0, yOff+imgHeight, 0, 0, this.width, 1, this.width, 1);
								}
							}
						} else {
							
							GlStateManager.color(0.1F, 0.1F, 0.1F, 0.8F);
							this.drawModalRectWithCustomSizedTexture((width/2)-(strWidth/2)-6, this.height/2-30+43, 0, 0, strWidth+12, 12, strWidth+12, 12);
							mc.fontRendererObj.drawString(Colors.YELLOW+"Grid Snapping is currently "+Colors.RED+"Disabled"+Colors.YELLOW+".", this.width/2-(fontRendererObj.getStringWidth("Grid Snapping is currently Disabled.")/2), this.height/2+12, 0x10, true);
							mc.getTextureManager().bindTexture(settingBorder);
						}
						
						settingCoords.set(selectedOption, xOff+";"+yOff);
						ConfigHandler.newObject(id+"X", xOff);
						ConfigHandler.newObject(id+"Y", yOff);
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
				this.drawModalRectWithCustomSizedTexture(x, y, 0, 0, imgWidth, imgHeight, imgWidth, imgHeight);
			
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
				
				this.drawModalRectWithCustomSizedTexture(x, y, 0, 0, imgWidth, 1, imgWidth, 1);
				this.drawModalRectWithCustomSizedTexture(x, y+imgHeight-1, 0, 0, imgWidth, 1, imgWidth, 1);
				this.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 1, imgHeight, 1, imgHeight);
				this.drawModalRectWithCustomSizedTexture(x+imgWidth, y, 0, 0, 1, imgHeight, 1, imgHeight);
				
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
	
	
	protected void keyTyped(char par1, int par2) throws IOException {
		if(selectedOption > settingCoords.size()) {
			if(par2 == Keyboard.KEY_ESCAPE || par2 == Keyboard.KEY_E) {
				
				SettingMenu.settingsMenuOpen=true;
				
				if(RenderGuiEvent.oldGuiScale == 0 || RenderGuiEvent.oldGuiScale > 2) {
					mc.gameSettings.guiScale = 2;
				}


				DwarvenGui.posX = Integer.parseInt(DataGetter.find("dwarvenGuiX")+"");
				DwarvenGui.posY = Integer.parseInt(DataGetter.find("dwarvenGuiY")+"");

				DwarvenPickaxeTimer.pickTimerX = Integer.parseInt(DataGetter.find("pickTimerX")+"");
				DwarvenPickaxeTimer.pickTimerY = Integer.parseInt(DataGetter.find("pickTimerY")+"");
				
				DwarvenTimer.posX = DataGetter.findInt("dwarvenTimerX");
				DwarvenTimer.posY = DataGetter.findInt("dwarvenTimerY");
				
				AbilityMessages.x = DataGetter.findInt("abilityDamageX");
				AbilityMessages.y = DataGetter.findInt("abilityDamageY");
				
				ComboMessages.x = DataGetter.findInt("comboMsgX");
				ComboMessages.y = DataGetter.findInt("comboMsgY");
				
				SecretImage.imgX = DataGetter.findInt("scrtX");
				SecretImage.imgY = DataGetter.findInt("scrtY");
				
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
		String[] idList2 = idList.split(", ");
		Boolean passThrough = false;
		for(int i=0;i<idList2.length;i++) { if(DataGetter.findBool(idList2[i])) { passThrough = true; continue; } }
		if(!passThrough) { return; }
		int x = DataGetter.findInt(id+"X");
		int y = DataGetter.findInt(id+"Y");
		String coords = x+";"+y;
		settingIDs.add(id);
		settingCoords.add(coords);
		settingNames.add(name);
		settingWidths.add(width);
		settingHeights.add(height);
		
	}
	
	
}
