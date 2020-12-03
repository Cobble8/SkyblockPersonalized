package cobble.sbp.gui.menu;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.threads.DisableScreenImageThread;
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

public class Help extends GuiScreen {
	private GuiButton buttonClose;
	private static int scrollNum = 0;
	int mouseDWheel = 0;
	@Override
	public void initGui() {
		scrollNum = 0;
		mouseDWheel = 0;
		this.buttonList.add(buttonClose = new GuiButton(0, this.width/2-80, this.height-30, 160, 20, "Close"));
		super.initGui();
	}
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		if(mouseDWheel != 0) {if(mouseDWheel > 0) {scrollNum++;} else {scrollNum--;}} mouseDWheel = Mouse.getDWheel(); if(scrollNum > 0) {scrollNum = 0;} 
		
		this.drawDefaultBackground();
		String betaString = "";
		if(Reference.BETA) {betaString = ChatFormatting.AQUA+" Beta";}
		
		
		mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"Features for "+ChatFormatting.GOLD+"SkyblockPersonalized"+betaString, 2, (12*0)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.RED+"API Features:", 2, (12*1)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"/dungeons [user]"+ChatFormatting.YELLOW+" - Returns the general dungeons stats for a player", 2, (12*2)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"/dungeons [player] [floor]"+ChatFormatting.YELLOW+" - Returns the general dungeons stats for a player on a specific floor", 2, (12*3)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.RED+"Commands:", 2, (12*5)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"/sbphelp"+ChatFormatting.YELLOW+ " - Shows this menu", 2, (12*6)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"/autopuzzle"+ChatFormatting.YELLOW+" - Displays information on how to use the automatic puzzle solvers", 2, (12*7)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"/disablesbp"+ChatFormatting.YELLOW+" - Disables the mod until a command from the mod is attempted to be used", 2, (12*8)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"/versioncheck"+ChatFormatting.YELLOW+" - Manually check if the mod needs an update", 2, (12*9)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"/showconfig"+ChatFormatting.YELLOW+" - Shows the config for troubleshooting purposes", 2, (12*10)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"/setimagedelay"+ChatFormatting.YELLOW+" - Sets how long the image is on screen before it disappears", 2, (12*11)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"/sbpforum"+ChatFormatting.YELLOW+" - Gives the forum link to the mod so you can share it with your friends ("+ChatFormatting.GOLD+"Forum "+ChatFormatting.YELLOW+"post has link to"+ChatFormatting.BLUE+" Discord"+ChatFormatting.YELLOW+")", 2, (12*12)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"/sbpsetkey [key]"+ChatFormatting.YELLOW+" - Manually set your API key", 2, (12*13)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"/toggleautopuzzle"+ChatFormatting.YELLOW+" - Toggles the auto puzzle solver feature because it is somewhat resource intensive", 2, (12*14)+2+(scrollNum*12), 0x10, true);
		
		mc.fontRendererObj.drawString(ChatFormatting.RED+"Resource Heavy Features:", 2, (12*16)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"Auto Puzzle Solver Image"+ChatFormatting.YELLOW+" - Automatically tells you how to solve the "+ChatFormatting.AQUA+"Box Puzzle"+ChatFormatting.YELLOW+" and the "+ChatFormatting.AQUA+"Ice-Fill" +ChatFormatting.YELLOW+" Puzzles (when enabled)", 2, (12*17)+2+(scrollNum*12), 0x10, true);
		
		mc.fontRendererObj.drawString(ChatFormatting.RED+"Other Features:", 2, (12*19)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"When enabled, automatically shows how to solve both the "+ChatFormatting.AQUA+"Box Puzzle"+ChatFormatting.YELLOW+" and the "+ChatFormatting.AQUA+"Ice-Fill" +ChatFormatting.YELLOW+" Puzzles", 2, (12*20)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"Automatically sets your API key when you type "+ChatFormatting.AQUA+"/api new", 2, (12*21)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"Lets you know if there is an update to the mod on login to a server", 2, (12*22)+2+(scrollNum*12), 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"Lets you know if you need to set your API key so people will stop asking me why commands don't work on login to a server", 2, (12*23)+2+(scrollNum*12), 0x10, true);
		
		//mc.fontRendererObj.drawString(ChatFormatting.AQUA+""+ChatFormatting.YELLOW+" - ", 2, (12*21)+2+(scrollNum*12), 0x10, true);
		
		
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
	    	
	    	if(par2 == Keyboard.KEY_DOWN) {
	    		scrollNum--;
	    	}
	    	else if(par2 == Keyboard.KEY_UP) {
	    		scrollNum++;
	    	}
	    	super.keyTyped(par1, par2);
	    }
	    
	    @Override
	    public void actionPerformed(GuiButton button) throws IOException {
	    	if(button == buttonClose) {
	    		mc.thePlayer.closeScreen();
	    	} 
	    }
}
