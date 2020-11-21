package cobble.sbp.gui.menu;

import java.awt.Color;
import java.io.IOException;

import cobble.sbp.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

public class MainGUI extends GuiScreen{
	
	
	
	@Override
	public void initGui() {
		
		
		super.initGui();
		
	}
	
	/*@Override
	public void mouseClicked(MouseEvent e) {
		int mouseX = e.getXOnScreen();
		int mouseY = e.getYOnScreen();
		System.out.println(mouseX+" "+mouseY);
		//if(mouseX > (width/2-32)/3 && mouseX < ((width/2-32)/3)+(86*6) && mouseY > (height/2-32)/3 && mouseY < (height/2-32)/3+(33*6)){
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Worked :D"));
			mc.thePlayer.closeScreen();
		//}
		
	}*/
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		int scaleX = (width-100)/172;
		int scaleY = (height-100)/66;
		int x1 = width/2-(86*scaleX);
		int y1 = (height/2-(33*scaleY))-30;
		int x2 = x1+(172*scaleX);
		int y2 =y1+(66*scaleY);
		if(mouseX > x1+4*scaleX && mouseX < x1+4*scaleX+80*scaleX && mouseY > y1+22*scaleY && mouseY < y1+22*scaleY+18*scaleY) 
		{Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("GUI"));}
		if(mouseX > x1+4*scaleX && mouseX < x1+4*scaleX+80*scaleX && mouseY > y1+44*scaleY && mouseY < y1+22*scaleY+40*scaleY) 
		{Minecraft.getMinecraft().displayGuiScreen(new CommandsGUI());}
		if(mouseX > x1+88*scaleX && mouseX < x1+88*scaleX+80*scaleX && mouseY > y1+22*scaleY && mouseY < y1+22*scaleY+18*scaleY) 
		{Minecraft.getMinecraft().displayGuiScreen(new SettingsGUI());}
		if(mouseX > x1+88*scaleX && mouseX < x1+88*scaleX+80*scaleX && mouseY > y1+44*scaleY && mouseY < y1+22*scaleY+40*scaleY) 
		{Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText("Misc."));}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	ResourceLocation menu = new ResourceLocation(Reference.MODID, "textures/gui/SBPMenu.png");
	ResourceLocation colors = new ResourceLocation(Reference.MODID, "textures/gui/colors.png");
	ResourceLocation connections = new ResourceLocation(Reference.MODID, "textures/gui/connections.png");
	
	@Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		
		int scaleX = (width-100)/172;
		int scaleY = (height-100)/66;
		int x1 = width/2-(86*scaleX);
		int y1 = (height/2-(33*scaleY))-30;
		int x2 = x1+(172*scaleX);
		int y2 =y1+(66*scaleY);
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(menu);
        this.drawModalRectWithCustomSizedTexture(x1, y1, 0, 0, 172*scaleX, 66*scaleY, 172*scaleX, 66*scaleY);

        //this.mc.getTextureManager().bindTexture(connections);
        //this.drawModalRectWithCustomSizedTexture(x1+2*scaleX, y2+1*scaleX, 0, 0, 260*scaleX/15, 75*scaleY/15, 260*scaleX/15, 75*scaleY/15);
        this.drawString(Minecraft.getMinecraft().fontRendererObj, "Made by Cobble8#0881 on Discord", x1+2*scaleX, y2-3*scaleX, Color.CYAN.getRGB());
        
        
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

	
	
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return false;
    }


}
