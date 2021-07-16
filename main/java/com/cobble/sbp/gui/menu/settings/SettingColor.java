package com.cobble.sbp.gui.menu.settings;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.utils.ColorUtils;
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
		try {
			String[] temp = oldColor.split(";");
			r=Float.parseFloat(temp[0]);
			g=Float.parseFloat(temp[1]);
			b=Float.parseFloat(temp[2]);
		} catch(Exception e) {}
		oldColorSave = oldColor;
		super.initGui();
	}
	private static int mouseDWheel = 0;
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		
		if(mouseDWheel != 0) {if(mouseDWheel > 0) {b-=0.03F;
		} else {b+=0.03F;
		}
			Utils.playClickSound();
		} mouseDWheel = Mouse.getDWheel();
		DecimalFormat df = new DecimalFormat("#.##");
		ResourceLocation color = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
		
		int clickAnywhereWidth = fontRendererObj.getStringWidth("Click anywhere or scroll to see the next set of colors");
		
		//BACKGROUND SHADING
		GlStateManager.enableBlend();
		mc.getTextureManager().bindTexture(color);
		GlStateManager.color(0.1F, 0.1F, 0.1F, 0.7F);
		drawModalRectWithCustomSizedTexture((this.width/2)-(clickAnywhereWidth/2)-5, this.height/2-103, 0, 0, clickAnywhereWidth+10, 212, clickAnywhereWidth+10, 176);
		GlStateManager.color(1, 1, 1, 1);
		
		drawButton("Transparent", (this.width/2)-70-1, this.height/2-61-1+144, mouseX, mouseY, 73, oldColorSave);
		drawButton("Chroma", (this.width/2), this.height/2-61-1+144, mouseX, mouseY, 73, oldColorSave);

		
		
		
		
		//PREVIEW COLOR BORDER MANAGER
		mc.getTextureManager().bindTexture(color);
		try {
			ArrayList<Float> tmp = ColorUtils.getColor(oldColorSave);
			float r = tmp.get(0); float g = tmp.get(1); float b = tmp.get(2);
			GlStateManager.color(flipFloat(r), flipFloat(g), flipFloat(b), 1);
		} catch(Exception e) { ColorUtils.resetColor(); }
		
		//PREVIEW BORDER
		drawModalRectWithCustomSizedTexture((this.width/2)-(clickAnywhereWidth/2)+13-1, this.height/2+3-20-1, 0, 0, 42, 1, 1, 1);
		drawModalRectWithCustomSizedTexture((this.width/2)-(clickAnywhereWidth/2)+13-1, this.height/2+3-20-1, 0, 0, 1, 42, 1, 1);
		drawModalRectWithCustomSizedTexture((this.width/2)-(clickAnywhereWidth/2)+13+40, this.height/2+3-20-1, 0, 0, 1, 42, 1, 1);
		drawModalRectWithCustomSizedTexture((this.width/2)-(clickAnywhereWidth/2)+12, this.height/2+3-20+40, 0, 0, 42, 1, 1, 1);


		//PREVIEW
		ColorUtils.setColor(oldColorSave);
		drawModalRectWithCustomSizedTexture((this.width/2)-(clickAnywhereWidth/2)+13, this.height/2+3-20, 0, 0, 40, 40, 1, 1);

		//MAKE SURE THE PAGE EXISTS
		if(b >= 1.01F) { b=0.0F; } else if(b < 0) { b=1.0F; }
		int currPage = (int) (b*10);

		/*for(int i=0;i<100;i++) {
			for(int j=0;j<100;j++) {
				float r = i/100f;
				float g = j/100f;
				r = Float.parseFloat(df.format(r));
				g = Float.parseFloat(df.format(g));
				mc.getTextureManager().bindTexture(color);
				int x = this.width/2-50+i;
				int y = this.height/2-50+j;
				GlStateManager.color(r, g, b);
				//Utils.print(r+", "+g);
				//drawModalRectWithCustomSizedTexture(x, y, 0, 0, 1, 1, 1, 1);
			}
		}*/

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
				} catch(Exception e) { }
				
				
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
					GlStateManager.color(flipFloat(r), flipFloat(g), flipFloat(b), 1);
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
		
		this.drawCenteredString(fontRendererObj, Colors.AQUA+"Transparent", this.width/2-(fontRendererObj.getStringWidth("Transparent"))+8, this.height/2+89, 0x10);
		this.drawCenteredString(fontRendererObj, Colors.YELLOW+Colors.BOLD+colorName, this.width/2, this.height/2-75-24, 0x10);
		this.drawCenteredString(fontRendererObj, Colors.AQUA+"Scroll to see the next set of colors", this.width/2, this.height/2-75-12, 0x10);
		this.drawCenteredString(fontRendererObj, Colors.GREEN+"Page: "+currPage+"/10", this.width/2, this.height/2-75, 0x10);
	
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		/*if(mouseX >= this.width/2-20 && mouseX <= this.width/2+20 && mouseY >= this.height/2+this.height/6 && mouseY <= this.height/2+this.height/6+20) {
			Utils.playClickSound();
			if(!DataGetter.findStr(id).equals("transparent")) {
				ConfigHandler.newObject(id, "transparent");
				oldColorSave = "transparent";
			} else {
				ConfigHandler.newObject(id, "0.0;0.0;0.0;");
				oldColorSave = "0.0;0.0;0.0;";
			}
			
			return;
		}*/
		//drawButton(Colors.WHITE+"Transparent", (this.width/2)-70-1, this.height/2-61-1+144, mouseX, mouseY, 73);
		//drawButton(Colors.WHITE+"Chroma", (this.width/2), this.height/2-61-1+144, mouseX, mouseY, 73);
		if(mouseX >= (this.width/2)-70 && mouseX <= (this.width/2)-1 && mouseY >= this.height/2-61+144 && mouseY <= this.height/2-61+144+18-1) {
			ConfigHandler.newObject(id, "transparent");
			oldColorSave="transparent";
			Utils.playClickSound();
		} else if(mouseX >= (this.width/2)+1 && mouseX <= (this.width/2)+70 && mouseY >= this.height/2-61+144 && mouseY <= this.height/2-61+144+18-1) {
			ConfigHandler.newObject(id, "chroma");
			oldColorSave="chroma";
			Utils.playClickSound();
		} 
		
		
		
		DecimalFormat df = new DecimalFormat("#.#");
		for(r=0; r<1.1F; r += 0.1) {
			for(g=0; g<1.1F; g += 0.1) {
				
				int r2 = (int) (r*10);
				int g2 = (int) (g*10);
				int xCoord = (this.width/2)+(r2*13)-72;
				int yCoord = (this.height/2)+(g2*13)-61-2;
				
				if(mouseX >= xCoord && mouseX <= xCoord+12 && mouseY >= yCoord && mouseY <= yCoord+12) {
					r = Float.parseFloat(df.format(r).replace(",", "."));
					g = Float.parseFloat(df.format(g).replace(",", "."));
					b = Float.parseFloat(df.format(b).replace(",", "."));
					
					oldColor = r+";"+g+";"+b;
					oldColorSave = r+";"+g+";"+b;
					ConfigHandler.newObject(id, oldColor);
					
					Utils.playClickSound();
					//Minecraft.getMinecraft().displayGuiScreen(new SettingOptions());
					return;
				}
				
			}
		}
		
		//b+=0.1F;
		//Utils.playClickSound();
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
	
private static void drawButton(String text, int x, int y, int mouseX, int mouseY, int width, String selected) {
		
		Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().bindTexture(SettingMenu.searchBar);
		
		GlStateManager.color(0.6F, 0.6F, 0.6F, 0.8F);
		
		if(mouseX >= x+1 && mouseX <= x+width-3 && mouseY >= y+1 && mouseY <= y+18) {
			GlStateManager.color(1, 1, 1, 1);
			//Utils.print(text.toLowerCase()+", "+(selected));
		} else if(text.toLowerCase().equals(selected)) {
			GlStateManager.color(1, 1, 1, 1);
		}
		drawModalRectWithCustomSizedTexture(x, y, 0, 0, 1, 20, 375, 20);
		drawModalRectWithCustomSizedTexture(x, y, 0, 0, width-2, 20, 375, 20);
		drawModalRectWithCustomSizedTexture(x+width-2, y, 0, 0, 1, 20, 375, 20);
		Utils.drawString(Colors.WHITE+text, (x+(width/2))-mc.fontRendererObj.getStringWidth(text)/2, y+6, 2);
		GlStateManager.enableBlend();
		

		
	}
}
