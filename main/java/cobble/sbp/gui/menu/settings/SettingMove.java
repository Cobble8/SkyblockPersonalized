package com.cobble.sbp.gui.menu.settings;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
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
		
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		GlStateManager.enableBlend();
		mc.fontRendererObj.drawString(Colors.WHITE+"Press ESCAPE to cancel", this.width/2-(fontRendererObj.getStringWidth("Press ESCAPE to cancel")/2), this.height/4, 0x10, true);
		
		ResourceLocation settingBorder = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
		mc.getTextureManager().bindTexture(settingBorder);
		
		int oldX = Integer.parseInt(DataGetter.find(id+"X")+"");
		int oldY = Integer.parseInt(DataGetter.find(id+"Y")+"");
		GlStateManager.color(0.2F, 0.2F, 0.2F, 0.5F);
		this.drawModalRectWithCustomSizedTexture(oldX, oldY, 0, 0, imgWidth, imgHeight, imgWidth, imgHeight);
		
		GlStateManager.color(0.3F, 0.8F, 1, 1);
		this.drawModalRectWithCustomSizedTexture(oldX, oldY, 0, 0, imgWidth, 1, imgWidth, 1);
		this.drawModalRectWithCustomSizedTexture(oldX, oldY+imgHeight-1, 0, 0, imgWidth, 1, imgWidth, 1);
		this.drawModalRectWithCustomSizedTexture(oldX, oldY, 0, 0, 1, imgHeight, 1, imgHeight);
		this.drawModalRectWithCustomSizedTexture(oldX+imgWidth-1, oldY, 0, 0, 1, imgHeight, 1, imgHeight);
		
		
		
		GlStateManager.color(0.2F, 0.2F, 0.2F, 0.5F);
		this.drawModalRectWithCustomSizedTexture(mouseX, mouseY, 0, 0, imgWidth, imgHeight, imgWidth, imgHeight);
		GlStateManager.color(0, 1, 0, 1);
		this.drawModalRectWithCustomSizedTexture(mouseX, mouseY, 0, 0, imgWidth, 1, imgWidth, 1);
		this.drawModalRectWithCustomSizedTexture(mouseX, mouseY+imgHeight-1, 0, 0, imgWidth, 1, imgWidth, 1);
		this.drawModalRectWithCustomSizedTexture(mouseX, mouseY, 0, 0, 1, imgHeight, 1, imgHeight);
		this.drawModalRectWithCustomSizedTexture(mouseX+imgWidth-1, mouseY, 0, 0, 1, imgHeight, 1, imgHeight);
		GlStateManager.disableBlend();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		ConfigHandler.newObject(id+"X", mouseX);
		ConfigHandler.newObject(id+"Y", mouseY);
		Minecraft.getMinecraft().displayGuiScreen(new SettingOptions());
		Utils.playClickSound();
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	protected void keyTyped(char par1, int par2) throws IOException {
		if(par2 == Keyboard.KEY_ESCAPE) {
			Utils.playClickSound();
			Minecraft.getMinecraft().displayGuiScreen(new SettingOptions());
			
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}