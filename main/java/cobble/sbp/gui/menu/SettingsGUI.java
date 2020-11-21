package cobble.sbp.gui.menu;

import java.awt.Color;
import java.io.IOException;

import cobble.sbp.handlers.ConfigHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class SettingsGUI extends GuiScreen {

	@Override
	public void initGui() {
		super.initGui();
	}
	public boolean toggleAnimate = false;
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		int scaleX = (width-100)/172;
		int scaleY = (height-100)/66;

		int x1 = width/2-(86*scaleX);
		int y1 = (height/2-(33*scaleY))-30;
		int x2 = x1+(172*scaleX);
		int y2 =y1+(66*scaleY);
		
		toggleAnimate = true;
		
		if(mouseX>x2-(35*scaleX)&&mouseX<x2-(2*scaleX)&&mouseY>y1+(50*scaleY)&&mouseY<y1+(64*scaleY))
		{{Minecraft.getMinecraft().displayGuiScreen(new MainGUI());}}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	ResourceLocation blank = new ResourceLocation(Reference.MODID, "textures/gui/SBPBlank.png");
	ResourceLocation back = new ResourceLocation(Reference.MODID, "textures/gui/buttonBack.png");
	ResourceLocation toggleAnim = new ResourceLocation(Reference.MODID, "textures/gui/toggleAnim.png");

	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		int scaleX = (width-100)/172;
		int scaleY = (height-100)/66;
		int iWidth = 172*scaleX;
		int iHeight = 66*scaleY;
		
		int x1 = width/2-(86*scaleX);
		int y1 = (height/2-(33*scaleY))-30;
		int x2 = x1+(172*scaleX);
		int y2 =y1+(66*scaleY);
		
		
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(blank);
        this.drawModalRectWithCustomSizedTexture(x1, y1, 0, 0, iWidth, iHeight, iWidth, iHeight);
        this.mc.getTextureManager().bindTexture(back);
        this.drawModalRectWithCustomSizedTexture(x2-(35*scaleX), y1+(50*scaleY), 0, 0, 33*scaleX, 14*scaleY, 33*scaleX, 14*scaleY);
        this.mc.getTextureManager().bindTexture(toggleAnim);
        
        
        /*if(toggleAnimate) {
        	if(var == true) {var = false;}
        	if(!(Boolean) DataGetter.find("modToggle")) {
        	if(System.currentTimeMillis()/20 != time) {
        		time = System.currentTimeMillis()/20;
        		if(i == 9) {
        			var = true;
        			toggleAnimate = false;
        			try {ConfigHandler.newObject("modToggle", true);} catch (IOException e) {e.printStackTrace();}
        		} else i++;
        	}
        } else {
        	if(System.currentTimeMillis()/20 != time) {
        		time = System.currentTimeMillis()/20;
        		if(i == 0) {
        			try {ConfigHandler.newObject("modToggle", false);} catch (IOException e) {e.printStackTrace();}
        			toggleAnimate = false;
        			} else i--;
        		}
        	}
        }*/
        boolean var = false;
        if(toggleAnimate) {var = Utils.toggleAnimation("modToggle");}
        this.drawModalRectWithCustomSizedTexture(x1+10*scaleX, y1+30*scaleX, 0, Utils.getI()*18, 34*scaleX/3, 18*scaleY/3, 34*scaleX/3, 162*scaleY/3);
        if(var) {this.drawModalRectWithCustomSizedTexture(x1+10*scaleX, y1+30*scaleX, 0, 144, 34*scaleX/3, 18*scaleY/3, 34*scaleX/3, 162*scaleY/3);}
	}
	
	
	
	
}
