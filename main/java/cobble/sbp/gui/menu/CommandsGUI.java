package cobble.sbp.gui.menu;

import java.awt.Color;
import java.io.IOException;


import cobble.sbp.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

public class CommandsGUI extends GuiScreen {
	String[] commandName = {"sbp", "sbpsetkey", "showconfig"};
	String[] commandArgs = {"", " <key>",""};
	String[] commandDesc = {"Opens the main GUI for this mod", "Sets your Hypixel API Key", "Displays the entire config in chat"};
	String[] commandFinal = {"", "", ""};
	
	@Override
	public void initGui() {
		for(int i = 0; i < commandName.length; i++) {
			commandFinal[i] = "/" + commandName[i]+commandArgs[i]+" - "+commandDesc[i];
			System.out.println(commandFinal[i]);
		}
		super.initGui();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		int scaleX = (width-100)/172;
		int scaleY = (height-100)/66;

		int x1 = width/2-(86*scaleX);
		int y1 = (height/2-(33*scaleY))-30;
		int x2 = x1+(172*scaleX);
		int y2 =y1+(66*scaleY);
		if(mouseX>x2-(35*scaleX)&&mouseX<x2-(2*scaleX)&&mouseY>y1+(50*scaleY)&&mouseY<y1+(64*scaleY))
		{{Minecraft.getMinecraft().displayGuiScreen(new MainGUI());}}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	ResourceLocation blank = new ResourceLocation(Reference.MODID, "textures/gui/SBPBlank.png");
	ResourceLocation back = new ResourceLocation(Reference.MODID, "textures/gui/buttonBack.png");
	
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
        for(int i=0; i < commandFinal.length; i++) {
        	drawString(Minecraft.getMinecraft().fontRendererObj, commandFinal[i], x1+10, (i*12)+y1+(20*scaleY), Color.CYAN.getRGB());
        }
        
	}
}
