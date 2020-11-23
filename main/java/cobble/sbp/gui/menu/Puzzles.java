package cobble.sbp.gui.menu;

import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.gui.screen.PuzzleImage;
import cobble.sbp.handlers.ConfigHandler;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class Puzzles extends GuiScreen {
	public static int puzzleID = 0;
	private GuiButton buttonClose;
	private GuiButton buttonMove;
	private GuiButton buttonDisable;
	String puzzlePictureID;
	@Override
	public void initGui() {
		//Utils.sendMessage("DEBUG");
		int xClose = this.width/2-(40);
		int yClose = 54;
		if(puzzleID == 1) {
			yClose = this.height/8+268;
			xClose = this.width/2-(80);
		}
		this.buttonList.add(buttonClose = new GuiButton(0, xClose, yClose, 160, 20, "Close"));
		this.buttonList.add(buttonMove = new GuiButton(0, xClose, yClose+22, 160, 20, "Move Image"));
		this.buttonList.add(buttonDisable = new GuiButton(0, xClose, yClose+44, 160, 20, "Disable"));
		super.initGui();
	}
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		//Ice Path
		if(puzzleID == 0) {
			//Ice Path 1-1
			if(mouseX > 50 && mouseX < 130 && mouseY > 2 && mouseY < 80) {puzzlePictureID = "ice_path_1_1";} 
			//Ice Path 1-2
			else if(mouseX > 130 && mouseX < 210 && mouseY > 2 && mouseY < 80) {puzzlePictureID = "ice_path_1_2";}
			//Ice Path 2-1
			else if(mouseX > 50 && mouseX < 162 && mouseY > 82 && mouseY < 196) {puzzlePictureID = "ice_path_2_1";}
			//Ice Path 2-2
			else if(mouseX > 162 && mouseX < 274 && mouseY > 82 && mouseY < 196) {puzzlePictureID = "ice_path_2_2";}
			//Ice Path 3-1
			else if(mouseX > 50 && mouseX < 194 && mouseY > 198 && mouseY < 342) {puzzlePictureID = "ice_path_3_1";}
			//Ice Path 3-2
			else if(mouseX > 196 && mouseX < 340 && mouseY > 198 && mouseY < 342) {puzzlePictureID = "ice_path_3_2";}
			//Ice Path 3-3
			else if(mouseX > 342 && mouseX < 486 && mouseY > 198 && mouseY < 342) {puzzlePictureID = "ice_path_3_3";}
			//Ice Path 3-4
			else if(mouseX > 488 && mouseX < 632 && mouseY > 198 && mouseY < 342) {puzzlePictureID = "ice_path_3_4";}
			
		//Silverfish
		} else if(puzzleID == 1) {
			if(mouseX > this.width/2-(120) && mouseX < this.width/2+(120) && mouseY > this.height/8 && mouseY < this.height/8+240) {puzzlePictureID = "silverfish";}
		}
		if(puzzlePictureID != "" && puzzlePictureID != null) {try {ConfigHandler.newObject("imageID", puzzlePictureID);} catch (IOException e) {e.printStackTrace();}}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	ResourceLocation icePath1 = new ResourceLocation(Reference.MODID, "textures/gui/ice_path_1.png");
	ResourceLocation icePath2 = new ResourceLocation(Reference.MODID, "textures/gui/ice_path_2.png");
	ResourceLocation icePath3 = new ResourceLocation(Reference.MODID, "textures/gui/ice_path_3.png");
	
	
	
	ResourceLocation border = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
	
	ResourceLocation silverfish = new ResourceLocation(Reference.MODID, "textures/gui/silverfish.png");
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		if(puzzleID == 0) {
			
			ResourceLocation preview = new ResourceLocation(Reference.MODID, "textures/gui/"+puzzlePictureID+".png");
			this.mc.getTextureManager().bindTexture(icePath1);
			this.drawModalRectWithCustomSizedTexture(50, 2, 0, 0, 160, 80, 160, 80);
			this.mc.getTextureManager().bindTexture(icePath2);
			this.drawModalRectWithCustomSizedTexture(50, 84, 0, 0, 226, 112, 226, 112);
			this.mc.getTextureManager().bindTexture(icePath3);
			this.drawModalRectWithCustomSizedTexture(50, 198, 0, 0, 582, 144, 582, 144);
			this.mc.getTextureManager().bindTexture(preview);
			mc.getTextureManager().bindTexture(border);
	 		this.drawModalRectWithCustomSizedTexture(490, 60, 0, 0, 126, 126, 126, 126);
	 		if(puzzlePictureID != "" && puzzlePictureID != null) {
	 			mc.getTextureManager().bindTexture(preview);
	 			this.drawModalRectWithCustomSizedTexture(490+2, 60+2, 0, 0, 122, 122, 122, 122);
	 		}
			mc.fontRendererObj.drawString(ChatFormatting.WHITE+"Ice Fill Puzzle", 212, 2, 0x10);
			mc.fontRendererObj.drawString(ChatFormatting.WHITE+"Note: This is probably incomplete, if you find one that isn't here,", 212, 12, 0x10);
			mc.fontRendererObj.drawString(ChatFormatting.WHITE+"please make sure to DM Cobble8#0881 on Discord with detailed images", 212, 24, 0x10);
			mc.fontRendererObj.drawString(ChatFormatting.WHITE+"showing the entire thing so he can add it!", 212, 36, 0x10);
			mc.fontRendererObj.drawString(ChatFormatting.WHITE+"Stage 1", 2, 4, 0x10);
			mc.fontRendererObj.drawString(ChatFormatting.WHITE+"Stage 2", 2, 86, 0x10);
			mc.fontRendererObj.drawString(ChatFormatting.WHITE+"Stage 3", 2, 200, 0x10);
			mc.fontRendererObj.drawString(ChatFormatting.YELLOW+"Preview:", 534, 50, 0x10);
		}
		else if(puzzleID == 1) {
			this.mc.getTextureManager().bindTexture(silverfish);
			this.drawModalRectWithCustomSizedTexture(this.width/2-(120), this.height/8, 0, 0, 240, 240, 240, 240);
			mc.fontRendererObj.drawString(ChatFormatting.WHITE+"Ice Silverfish Puzzle", this.width/2-(54), this.height/8-12, 0x10);
			mc.fontRendererObj.drawString(ChatFormatting.WHITE+"The "+ChatFormatting.GOLD+"Orange"+ChatFormatting.WHITE+" dot is the starting point.", this.width/2-(120), this.height/8+242, 0x10);
			mc.fontRendererObj.drawString(ChatFormatting.WHITE+"The "+ChatFormatting.GREEN+"Green"+ChatFormatting.WHITE+" line is the ideal path.", this.width/2-(120), this.height/8+254, 0x10);
			
		}
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
	    		if(puzzlePictureID != "" && puzzlePictureID != null) {try {ConfigHandler.newObject("imageID", puzzlePictureID);} catch (IOException e) {e.printStackTrace();}}
	    		mc.thePlayer.closeScreen();
	    	} else if(button == buttonMove) {
	    		if(puzzlePictureID != "" && puzzlePictureID != null) {try {ConfigHandler.newObject("imageID", puzzlePictureID);} catch (IOException e) {e.printStackTrace();}}
	    		Minecraft.getMinecraft().displayGuiScreen(new MoveImage());
	    	} else if(button == buttonDisable) {
	    		ConfigHandler.newObject("imageID", null);
	    	}
	    }
}
