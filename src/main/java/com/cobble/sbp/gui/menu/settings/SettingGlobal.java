package com.cobble.sbp.gui.menu.settings;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;

public class SettingGlobal extends GuiScreen {
	private static ResourceLocation logo = new ResourceLocation(Reference.MODID, "textures/0/menu/logo.png");
	private static ResourceLocation settingBg = new ResourceLocation(Reference.MODID, "textures/0/menu/settingBg.png");
	private static ResourceLocation settingBorder = new ResourceLocation(Reference.MODID, "textures/0/menu/settingBorder.png");
	private static ResourceLocation plusMinus = new ResourceLocation(Reference.MODID, "textures/0/menu/plusminus.png");

	public static int textStyle = DataGetter.findInt("core.mod.textStyle");
	
	@Override
	public void initGui() {
		
		super.initGui();
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		try {
			//THEME COLOR
			String textColor = Colors.WHITE;

			//LOGO
			mc.getTextureManager().bindTexture(logo);
			drawModalRectWithCustomSizedTexture(this.width/2-200, 17+22, 0, 0, 400, 100, 400, 100);
			mc.fontRendererObj.drawString(Colors.YELLOW+"Version: "+Colors.AQUA+Reference.VERSION, this.width/2+194-(mc.fontRendererObj.getStringWidth("Version: "+Reference.VERSION)), 17+22+100-14-11, 0x1f99fa, false);
			mc.fontRendererObj.drawString(Colors.YELLOW+"Made by "+Colors.RESET+"Cobble8", this.width/2+194-(mc.fontRendererObj.getStringWidth("Made by Cobble8")), 17+22+100-14, 0x1f99fa, false);



			Utils.drawString(Colors.YELLOW+"Font Style:", this.width/2-200+4, 143, 0);
			mc.getTextureManager().bindTexture(plusMinus);
			GlStateManager.color(1, 1, 1, 0.8F);
			if(mouseX >= this.width/2-200 && mouseX <= this.width/2-200+20 && mouseY >= 142+12 && mouseY <= 142+20+12) { GlStateManager.color(1, 1, 1, 1); }
			drawModalRectWithCustomSizedTexture(this.width/2-200, 142+12, 0, 0, 20, 20, 118, 20);
			GlStateManager.color(1, 1, 1, 0.8F);

			if(mouseX >= this.width/2-200+80 && mouseX <= this.width/2-200+100 && mouseY >= 142+12 && mouseY <= 142+20+12) { GlStateManager.color(1, 1, 1, 1); }
			drawModalRectWithCustomSizedTexture(this.width/2-200+80, 142+12, 80, 0, 20, 20, 118, 20);
			GlStateManager.color(1, 1, 1, 0.8F);
			drawModalRectWithCustomSizedTexture(this.width/2-200+20, 142+12, 20, 0, 60, 20, 118, 20);

			//this.drawModalRectWithCustomSizedTexture(this.width/2-200, 142, 0, 0, 100, 20, 118, 20);

			ArrayList<String> textStyleNames = new ArrayList<>();
			textStyleNames.add("Shadowed");
			textStyleNames.add("Bordered");
			textStyleNames.add("Blank");
			textStyleNames.add("Background");
			textStyleNames.add("Tooltip");
			if(textStyle < 0) {textStyle = 0;}
			String currTextStyle = textStyleNames.get(textStyle);


			Utils.drawString(Colors.CHROMA+currTextStyle, this.width/2-150-(mc.fontRendererObj.getStringWidth(currTextStyle)/2), 148+12);

			int posX = this.width/2-250;
			int posY = 138-4;
			int scale = 40;

			mc.getTextureManager().bindTexture(settingBg);
			GlStateManager.enableBlend();
			GlStateManager.color(1, 1, 1, 0.7F);
			drawModalRectWithCustomSizedTexture(posX-32, posY-90, 0, 0, 33, 33, 92, 55);
			drawModalRectWithCustomSizedTexture(posX-32+66-33, posY-90, 92-33, 0, 33, 33, 92, 55);
			drawModalRectWithCustomSizedTexture(posX-32, posY+4-33, 0, 22, 33, 33, 92, 55);
			drawModalRectWithCustomSizedTexture(posX-32+66-33, posY+4-33, 92-33, 22, 33, 33, 92, 55);
			drawModalRectWithCustomSizedTexture(posX-32, posY-90+33, 0, 16, 33, 28, 92, 55);
			drawModalRectWithCustomSizedTexture(posX-32+33, posY-90+33, 92-33, 16, 33, 28, 92, 55);

			mc.getTextureManager().bindTexture(settingBorder);
			drawModalRectWithCustomSizedTexture(posX-32, posY-94+4, 0,0, 66, 2, 370, 223);
			drawModalRectWithCustomSizedTexture(posX-32, posY-94+4+2, 0,0, 2, 92, 370, 223);
			drawModalRectWithCustomSizedTexture(posX-32+64, posY-94+4+2, 0,0, 2, 92, 370, 223);
			drawModalRectWithCustomSizedTexture(posX-30, posY+2, 0,0, 62, 2, 370, 223);

			Utils.renderPlayer(posX, posY-5, scale, mouseX, mouseY);
		} catch(Exception e) {
			e.printStackTrace();
		}


	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		if(mouseX>=this.width/2-200 && mouseX<=this.width/2-200+18 && mouseY >= 142+12 && mouseY <= 142+20+12) {
			if(textStyle > 0) {
				textStyle--;
				Utils.playClickSound();
				ConfigHandler.newObject("core.mod.textStyle", textStyle);
			}
		}
		else if(mouseX>=this.width/2-100-18 && mouseX<=this.width/2-100 && mouseY >= 142+12 && mouseY <= 142+20+12) {
			if(textStyle < 4) {
				textStyle++;
				Utils.playClickSound();
				ConfigHandler.newObject("core.mod.textStyle", textStyle);
			}
		}
		
		/*else if(mouseX >= this.width/2+210 && mouseX <= this.width/2+210+32 && mouseY >= 17+22+13 && mouseY <= 17+22+13+32) {
			Utils.playClickSound();
			Utils.openURL("https://discord.gg/QXA3y5EbNA");
		}
		else if(mouseX >= this.width/2+210 && mouseX <= this.width/2+210+32 && mouseY >= 17+22+13+34 && mouseY <= 17+22+13+32+34) {
			Utils.playClickSound();
			Utils.openURL("https://github.com/Cobble8/SkyblockPersonalized");
		}*/
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	protected void keyTyped(char par1, int par2) throws IOException {
		if(par2 == Keyboard.KEY_ESCAPE) {
			SettingMenu.settingsMenuOpen = false;
			//mc.thePlayer.closeScreen();
			mc.displayGuiScreen(new SettingMenu());
		} else {
			super.keyTyped(par1, par2);
		}

	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}