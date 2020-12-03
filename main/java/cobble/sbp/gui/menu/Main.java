package cobble.sbp.gui.menu;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import cobble.sbp.utils.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class Main extends GuiScreen {
	private GuiButton buttonClose;
	private GuiButton buttonHelp;
	private GuiButton toggleAutoPuzzle;
	private GuiButton setImageDelay;
	private GuiButton apiKey;
	private GuiButton autoPuzzle;
	private GuiButton modToggle;
	String drawLatestVersion;
	String versionType = "";
	
	@Override
	public void initGui() {
		this.buttonList.add(buttonClose = new GuiButton(0, this.width/2-80, this.height-40, 160, 20, ChatFormatting.RED+"Close"));
		this.buttonList.add(buttonHelp = new GuiButton(0, this.width/2-80, this.height-60, 160, 20, ChatFormatting.GOLD+"Help Menu"));
		this.buttonList.add(autoPuzzle = new GuiButton(0, this.width/2-80, this.height-80, 160, 20, ChatFormatting.GOLD+"Auto Puzzle Menu"));
		this.buttonList.add(toggleAutoPuzzle = new GuiButton(0, this.width/2-80, 40, 120, 20, ChatFormatting.YELLOW+"Auto Puzzles:"));
		this.buttonList.add(setImageDelay = new GuiButton(0, this.width/2-80, 60, 120, 20, ChatFormatting.YELLOW+"Image Delay:"));
		this.buttonList.add(apiKey = new GuiButton(0, this.width/2-80, 80, 120, 20, ChatFormatting.YELLOW+"API Key:"));
		this.buttonList.add(modToggle = new GuiButton(0, this.width/2-80, 20, 120, 20, ChatFormatting.YELLOW+"Mod Toggle:"));

		try { 
			if(Reference.VERSION.equals(Utils.getLatestDevVersion())) {
				versionType = ChatFormatting.BLUE+" Dev";
				drawLatestVersion=ChatFormatting.YELLOW+"You are on the latest"+ChatFormatting.BLUE+" Dev "+ChatFormatting.YELLOW+"version for "+ChatFormatting.GOLD+"SkyblockPersonalized"+ChatFormatting.YELLOW+"!";
			} else if(Reference.VERSION.equals(Utils.getLatestBetaVersion())) {
				versionType = ChatFormatting.AQUA+" Beta";
				drawLatestVersion=ChatFormatting.YELLOW+"You are on the latest"+ChatFormatting.AQUA+" Beta "+ChatFormatting.YELLOW+"version for "+ChatFormatting.GOLD+"SkyblockPersonalized"+ChatFormatting.YELLOW+"!";
			} else if(Reference.VERSION.equals(Utils.getLatestVersion())) {
				drawLatestVersion=ChatFormatting.YELLOW+"You are on the latest version for "+ChatFormatting.GOLD+"SkyblockPersonalized"+ChatFormatting.YELLOW+"!";
			} else {
				drawLatestVersion=ChatFormatting.YELLOW+"Your"+ChatFormatting.GOLD+" SkyblockPersonalized"+versionType+ChatFormatting.YELLOW+" is out of date!";
			}
		
		} catch (Exception e) { e.printStackTrace(); }
		super.initGui();
	}
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		//this.mc.getTextureManager().bindTexture(buttonTexture);
		//this.drawModalRectWithCustomSizedTexture(5, 5, 0, 0, 48, 24, 48, 24);
		
		
		String drawToggleAutoPuzzle;
		String drawModToggle;
		if((Boolean) DataGetter.find("autoPuzzleToggle")) {drawToggleAutoPuzzle = ChatFormatting.GREEN+"Enabled";} else {drawToggleAutoPuzzle = ChatFormatting.RED+"Disabled";}
		if((Boolean) DataGetter.find("modToggle")) {drawModToggle = ChatFormatting.GREEN+"Enabled";} else {drawModToggle = ChatFormatting.RED+"Disabled";}
		
		mc.fontRendererObj.drawString(drawModToggle, this.width/2+44, 26, 0x10, true);
		mc.fontRendererObj.drawString(drawToggleAutoPuzzle, this.width/2+44, 46, 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.GOLD+""+DataGetter.find("imageDelay")+ChatFormatting.AQUA+" Seconds", this.width/2+44, 66, 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+""+DataGetter.find("APIKey"), this.width/2+44, 86, 0x10, true);
		mc.fontRendererObj.drawString(drawLatestVersion, 2, 2, 0x10, true);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	 @Override
	    public boolean doesGuiPauseGame() {
	        return false;
	    }
	    
	    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
	        return false;
	    }
	    
	    protected void keyTyped(char par1, int par2) throws IOException {
	    	
	    	super.keyTyped(par1, par2);
	    }
	    
	    @Override
	    public void actionPerformed(GuiButton button) throws IOException {
	    	if(button == buttonClose) {
	    		mc.thePlayer.closeScreen();
	    	}
	    	else if(button == buttonHelp) {
	    		RenderGuiHandler.HelpGUI=true;
	    	}
	    	else if(button == autoPuzzle) {
	    		RenderGuiHandler.PuzzleGUI=true;
	    	}
	    	else if(button == toggleAutoPuzzle) {
	    		if((Boolean) DataGetter.find("autoPuzzleToggle")) {
	    			ConfigHandler.newObject("autoPuzzleToggle", false);
	    		} else {ConfigHandler.newObject("autoPuzzleToggle", true);}
	    	}
	    	
	    	else if(button == modToggle) {
	    		if((Boolean) DataGetter.find("modToggle")) {
	    			ConfigHandler.newObject("modToggle", false);
	    		} else {ConfigHandler.newObject("modToggle", true);}
	    	}
	    	else if(button == setImageDelay) {
	    		Minecraft.getMinecraft().displayGuiScreen(new SetImageDelay());
	    	}
	    	else if(button == apiKey) {
	    		Minecraft.getMinecraft().displayGuiScreen(new SetAPIKey());
	    	}
	    }
}
