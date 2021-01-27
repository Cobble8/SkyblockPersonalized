package com.cobble.sbp.gui.menu.settings;

import java.io.IOException;
import java.text.DecimalFormat;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class SettingColor extends GuiScreen {
	
	public static String oldColor = "1.0;1.0;1.0";
	public static String id = "";
	float r = 0.0F;
	float g = 0.0F;
	float b = 0.0F;
	private static String oldColorSave = oldColor;
	
	@Override
	public void initGui() {
		String[] temp = oldColor.split(";");
		r=Float.parseFloat(temp[0]);
		g=Float.parseFloat(temp[1]);
		b=Float.parseFloat(temp[2]);
		
		oldColorSave = oldColor;
		super.initGui();
	}
	private static int mouseDWheel = 0;
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if(mouseDWheel != 0) {if(mouseDWheel > 0) {b-=0.1F;Utils.playClickSound();} else {b+=0.1F;Utils.playClickSound();}} mouseDWheel = Mouse.getDWheel();
		DecimalFormat df = new DecimalFormat("#.#");
		ResourceLocation color = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
		
		int clickAnywhereWidth = fontRendererObj.getStringWidth("Click anywhere or scroll to see the next set of colors");
		
		//BACKGROUND SHADING
		mc.getTextureManager().bindTexture(color);
		GlStateManager.color(0.1F, 0.1F, 0.1F, 0.7F);
		this.drawModalRectWithCustomSizedTexture((this.width/2)-(clickAnywhereWidth/2)-5, this.height/2-103, 0, 0, clickAnywhereWidth+10, 200, clickAnywhereWidth+10, 176);
		
		//MAKE SURE THE PAGE EXISTS
		if(b >= 1.1F) {
			b=0.0F;
		} else if(b < 0) {
			b=1.0F;
		}
		int currPage = (int) (b*10);
		//DRAW EACH COLOR
		for(r=0; r<1.1;r+=0.1F) {
			for(g=0; g<1.1;g+=0.1F) {
				int r2 = (int) (r*10);
				int g2 = (int) (g*10);
				int b2 = (int) (b*10);
				
				try {
				r=Float.parseFloat(df.format(r));
				g=Float.parseFloat(df.format(g));
				b=Float.parseFloat(df.format(b));
				} catch(Exception e) {
					
				}
				
				
				mc.getTextureManager().bindTexture(color);
				
				int xCoord = (this.width/2)+(r2*13)-63;
				int yCoord = (this.height/2)+(g2*13)-61;
				
				if((r+";"+g+";"+b).equals(oldColorSave)) {
					GlStateManager.color(flipFloat(r), flipFloat(g), flipFloat(b), 1);
					this.drawModalRectWithCustomSizedTexture((this.width/2)+(r2*13)-70-1, this.height/2+(g2*13)-61-1, 0, 0, 1, 12, 1, 12);
					this.drawModalRectWithCustomSizedTexture((this.width/2)+(r2*13)-70-1, this.height/2+(g2*13)-61-1, 0, 0, 12, 1, 12, 1);
					this.drawModalRectWithCustomSizedTexture((this.width/2)+(r2*13)-70-1, this.height/2+(g2*13)-61+10, 0, 0, 12, 1, 12, 1);
					this.drawModalRectWithCustomSizedTexture((this.width/2)+(r2*13)-70+10, this.height/2+(g2*13)-61-1, 0, 0, 1, 12, 1, 12);
				}
				
				if(mouseX >= xCoord-7 && mouseX <= xCoord+10-7 && mouseY >= yCoord && mouseY <= yCoord+10) {
					GlStateManager.color(1, 1, 1, 1);
					this.drawModalRectWithCustomSizedTexture((this.width/2)+(r2*13)-70-2, this.height/2+(g2*13)-61-2, 0, 0, 14, 14, 14, 14);
					GlStateManager.color(r, g, b, 1);
					this.drawModalRectWithCustomSizedTexture((this.width/2)+(r2*13)-70-1, this.height/2+(g2*13)-61-1, 0, 0, 12, 12, 12, 12);
				} else {
					
					GlStateManager.color(r, g, b, 0.7F);
					this.drawModalRectWithCustomSizedTexture((this.width/2)+(r2*13)-70, this.height/2+(g2*13)-61, 0, 0, 10, 10, 10, 10);
				}
				
				
				
				
			}
		}
		String colorName = SettingOptions.settingName+" Colors";
		
		this.drawCenteredString(fontRendererObj, Colors.YELLOW+Colors.BOLD+colorName, this.width/2, this.height/2-75-24, 0x10);
		this.drawCenteredString(fontRendererObj, Colors.AQUA+"Click anywhere or scroll to see the next set of colors", this.width/2, this.height/2-75-12, 0x10);
		this.drawCenteredString(fontRendererObj, Colors.GREEN+"Page: "+currPage+"/10", this.width/2, this.height/2-75, 0x10);
	
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		DecimalFormat df = new DecimalFormat("#.#");
		for(r=0; r<1.1F; r += 0.1) {
			for(g=0; g<1.1F; g += 0.1) {
				
				int r2 = (int) (r*10);
				int g2 = (int) (g*10);
				int xCoord = (this.width/2)+(r2*13)-72;
				int yCoord = (this.height/2)+(g2*13)-70;
				
				if(mouseX >= xCoord && mouseX <= xCoord+14 && mouseY >= yCoord && mouseY <= yCoord+14) {
					r = Float.parseFloat(df.format(r));
					g = Float.parseFloat(df.format(g));
					b = Float.parseFloat(df.format(b));
					
					oldColor = r+";"+g+";"+b;
					
					ConfigHandler.newObject(id, oldColor);
					
					Utils.playClickSound();
					Minecraft.getMinecraft().displayGuiScreen(new SettingOptions());
					return;
				}
				
			}
		}
		
		b+=0.1F;
		Utils.playClickSound();
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	protected void keyTyped(char par1, int par2) throws IOException {
		if(par2 == Keyboard.KEY_ESCAPE) {
			Minecraft.getMinecraft().displayGuiScreen(new SettingOptions());
			Utils.playClickSound();
		} else if(par2 == Keyboard.KEY_RIGHT || par2 == Keyboard.KEY_D) {
			b+=0.1F;
			Utils.playClickSound();
		} else if(par2 == Keyboard.KEY_LEFT || par2 == Keyboard.KEY_A) {
			b-=0.1F;
			Utils.playClickSound();
		}
		
		
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	private static float flipFloat(float floatVal) {
		return 1-floatVal;
	}
}
