package cobble.sbp.gui.screen;

import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class PuzzleImage extends Gui {
	
	public static String puzzlePicture = (String) DataGetter.find("imageID");
	public static int borderID = 1;
	
	ResourceLocation border = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
	
	 public PuzzleImage(Minecraft mc) {
		 
		 if(RenderGuiHandler.imageID != "" && RenderGuiHandler.imageID != null && RenderGuiHandler.imageID != "null" && RenderGuiHandler.imageID != "NONE") {
		 	//Utils.sendMessage(RenderGuiHandler.imageID);
		 	mc.getTextureManager().bindTexture(border);
		 	this.drawModalRectWithCustomSizedTexture(Integer.parseInt(DataGetter.find("imageXCoord")+""), Integer.parseInt(DataGetter.find("imageYCoord")+""), 0, 0, 126, 126, 126, 126);
		 	
		 	if(RenderGuiHandler.imageID != null && RenderGuiHandler.imageID != "NONE") {
		 		ResourceLocation image = new ResourceLocation(Reference.MODID, "textures/gui/"+RenderGuiHandler.imageID.toString()+".png");
		 		mc.getTextureManager().bindTexture(image);
		 		this.drawModalRectWithCustomSizedTexture(Integer.parseInt(DataGetter.find("imageXCoord")+"")+1, Integer.parseInt(DataGetter.find("imageYCoord")+"")+1, 0, 0, 124, 124, 124, 124);
		 	}
		 }
	  }
}
