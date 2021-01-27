package com.cobble.sbp.gui.menu;

import java.io.IOException;

import net.minecraft.client.gui.GuiScreen;

public class BlankGuiMenu extends GuiScreen {
	
	@Override
	public void initGui() {
		
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	protected void keyTyped(char par1, int par2) throws IOException {
		
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
