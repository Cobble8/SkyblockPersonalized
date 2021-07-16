package com.cobble.sbp.gui.menu.settings;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class SettingMove extends GuiScreen {
	
	public static int imgWidth = 0;
	public static int imgHeight = 0;
	public static String id = "";
	
	
	
	@Override
	public void initGui() {
		if(mc.gameSettings.guiScale == 2 && RenderGuiEvent.oldGuiScale != -1) {
			mc.gameSettings.guiScale=RenderGuiEvent.oldGuiScale;
			RenderGuiEvent.currSettingMenu="move";
			RenderGuiEvent.helpMenu=true;
			mc.thePlayer.closeScreen();
		}
		SettingMenu.settingsMenuOpen=false;
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		GlStateManager.enableBlend();
		mc.fontRendererObj.drawString(Colors.WHITE+"Press ESCAPE to cancel", this.width/2-(fontRendererObj.getStringWidth("Press ESCAPE to cancel")/2), this.height/4, 0x10, true);
		
		ResourceLocation settingBorder = SettingMenu.blank;
		mc.getTextureManager().bindTexture(settingBorder);
		
		int oldX = DataGetter.findInt(id+"X");
		int oldY = DataGetter.findInt(id+"Y");
		GlStateManager.color(0.2F, 0.2F, 0.2F, 0.5F);
		drawModalRectWithCustomSizedTexture(oldX, oldY, 0, 0, imgWidth, imgHeight, imgWidth, imgHeight);
		
		GlStateManager.color(0.3F, 0.8F, 1, 1);
		drawModalRectWithCustomSizedTexture(oldX, oldY, 0, 0, imgWidth, 1, imgWidth, 1);
		drawModalRectWithCustomSizedTexture(oldX, oldY+imgHeight-1, 0, 0, imgWidth, 1, imgWidth, 1);
		drawModalRectWithCustomSizedTexture(oldX, oldY, 0, 0, 1, imgHeight, 1, imgHeight);
		drawModalRectWithCustomSizedTexture(oldX+imgWidth-1, oldY, 0, 0, 1, imgHeight, 1, imgHeight);
		
		
		
		GlStateManager.color(0.2F, 0.2F, 0.2F, 0.5F);
		drawModalRectWithCustomSizedTexture(mouseX, mouseY, 0, 0, imgWidth, imgHeight, imgWidth, imgHeight);
		GlStateManager.color(0, 1, 0, 1);
		drawModalRectWithCustomSizedTexture(mouseX, mouseY, 0, 0, imgWidth, 1, imgWidth, 1);
		drawModalRectWithCustomSizedTexture(mouseX, mouseY+imgHeight-1, 0, 0, imgWidth, 1, imgWidth, 1);
		drawModalRectWithCustomSizedTexture(mouseX, mouseY, 0, 0, 1, imgHeight, 1, imgHeight);
		drawModalRectWithCustomSizedTexture(mouseX+imgWidth-1, mouseY, 0, 0, 1, imgHeight, 1, imgHeight);
		GlStateManager.disableBlend();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		ConfigHandler.newObject(id+"X", mouseX);
		ConfigHandler.newObject(id+"Y", mouseY);
		RenderGuiEvent.helpMenu=true;
		/*

		String[] findID = SettingOptions.settingOptions.get(SettingMenu.clickedSubOption).get(2);
		for(int i=0;i<findID.length;i++) {
			if(id.equals(findID[i])) {
				RenderGuiEvent.currSettingMenu="main:"+i;
				RenderGuiEvent.helpMenu=true;
				break;
			}
		}*/

		Utils.playClickSound();
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	protected void keyTyped(char par1, int par2) throws IOException {
		if(par2 == Keyboard.KEY_ESCAPE) {
			Utils.playClickSound();

			Minecraft.getMinecraft().displayGuiScreen(new SettingMenu());

			/*String[] findID = SettingOptions.settingOptions.get(SettingMenu.clickedSubOption).get(2);
			for(int i=0;i<findID.length;i++) {
				if(id.equals(findID[i])) {
					SettingMenu.clickedSubOption=i;
					break;
				}
			}*/
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}