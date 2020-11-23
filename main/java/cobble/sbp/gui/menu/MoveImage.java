package cobble.sbp.gui.menu;

import java.io.IOException;

import cobble.sbp.gui.screen.PuzzleImage;
import cobble.sbp.handlers.ConfigHandler;
import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class MoveImage extends GuiScreen 
{
	
	@Override
	public void initGui() {
		super.initGui();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		ConfigHandler.newObject("imageXCoord", mouseX);
		ConfigHandler.newObject("imageYCoord", mouseY);
		Minecraft.getMinecraft().displayGuiScreen(new Puzzles());
		
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	ResourceLocation border = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		mc.getTextureManager().bindTexture(border);
 		this.drawModalRectWithCustomSizedTexture(mouseX, mouseY, 0, 0, 126, 126, 126, 126);
 		if(PuzzleImage.puzzlePicture != "") {
 			ResourceLocation image = new ResourceLocation(Reference.MODID, "textures/gui/"+DataGetter.find("imageID")+".png");
 			mc.getTextureManager().bindTexture(image);
 	 		this.drawModalRectWithCustomSizedTexture(mouseX+2, mouseY+2, 0, 0, 122, 122, 122, 122);	
 		}
 		
	}
	
	@Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return false;
    }
	
	
}
