package cobble.sbp.gui.menu;

import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class Puzzles extends GuiScreen {
	private GuiButton buttonClose;
	private GuiButton buttonMove;
	private GuiButton buttonDisable;
	
	@Override
	public void initGui() {
		
		this.buttonList.add(buttonDisable = new GuiButton(0, this.width/2-80, this.height-50, 160, 20, "Disable Current Image"));
		this.buttonList.add(buttonMove = new GuiButton(0, this.width/2-80, this.height-70, 160, 20, "Move Image"));
		this.buttonList.add(buttonClose = new GuiButton(0, this.width/2-80, this.height-30, 160, 20, "Close"));
		//this.buttonList.add(buttonClose = new GuiButton(0, xClose+160, yClose+20, 160, 20, "Hi :D"));
		super.initGui();
	}
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	ResourceLocation showBoxStart = new ResourceLocation(Reference.MODID, "textures/gui/box_starting_block.png");
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		
		this.mc.getTextureManager().bindTexture(showBoxStart);
		this.drawModalRectWithCustomSizedTexture(this.width-275, 29, 0, 0, 273, 141, 273, 141);
		
		String enableTest;
		if((Boolean) DataGetter.find("autoPuzzleToggle")) {
			enableTest = ChatFormatting.GREEN+"enabled";
		} else {enableTest = ChatFormatting.RED+"disabled";}
		
		mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"This feature is one that I (Cobble8) am proud of,", 2, 4, 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"It has taken many hours of work but it was worth it.", 2, 16, 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"This feature (when enabled) will AUTOMATICALLY put an image", 2, 28, 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"onto your screen telling you how to solve the puzzle.", 2, 40, 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.GOLD+"This currently supports the following puzzles:", 2, 64, 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"Box Puzzle", 2, 76, 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"Ice-Fill Puzzle", 2, 88, 0x10, true);
		
		mc.fontRendererObj.drawString(ChatFormatting.GOLD+"This feature is currently: "+enableTest, 2, 112, 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.GOLD+"The image for this feature will display on your screen for "+ChatFormatting.AQUA+DataGetter.find("imageDelay")+ChatFormatting.GOLD+" seconds", 2, 124, 0x10, true);
		
		mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"Toggle this feature with "+ChatFormatting.AQUA+"/toggleautopuzzle", 2, 148, 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"Set how long the image is on screen with "+ChatFormatting.AQUA+"/setimagedelay [seconds]", 2, 160, 0x10, true);
		

		
		
		mc.fontRendererObj.drawString(ChatFormatting.AQUA+"Auto Puzzle Solver", this.width/2-52, 5, 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"In order to activate the automatic box puzzle solver,", this.width-275, 5, 0x10, true);
		mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"please stand on the following block:", this.width-275, 17, 0x10, true);
		//mc.fontRendererObj.drawString(ChatFormatting.AQUA+"TIP:"+ChatFormatting.GREEN+" Use the clean stone block as a reference", this.width-275, 29, 0x10, true);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	 @Override
	    public boolean doesGuiPauseGame() {
	        return false;
	    }
	    
	    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
	        return false;
	    }
	    
	    @Override
	    public void actionPerformed(GuiButton button) throws IOException {
	    	if(button == buttonClose) {
	    		//if(puzzlePictureID != "" && puzzlePictureID != null) {RenderGuiHandler.imageID = puzzlePictureID;}
	    		mc.thePlayer.closeScreen();
	    	} else if(button == buttonMove) {
	    		//if(puzzlePictureID != "" && puzzlePictureID != null) {RenderGuiHandler.imageID = puzzlePictureID;}
	    		Minecraft.getMinecraft().displayGuiScreen(new MoveImage());
	    	} else if(button == buttonDisable) {
	    		RenderGuiHandler.imageID = "NONE";
	    	}
	    }
}
