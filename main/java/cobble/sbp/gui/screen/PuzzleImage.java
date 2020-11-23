package cobble.sbp.gui.screen;

import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class PuzzleImage extends Gui {
	
	public static String puzzlePicture = (String) DataGetter.find("imageID");
	public static int borderID = 1;
	
	ResourceLocation border = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
	
	 public PuzzleImage(Minecraft mc) {
		 
		 	if(DataGetter.find("imageID") != "" && DataGetter.find("imageID") != null) {
		 		ResourceLocation image = new ResourceLocation(Reference.MODID, "textures/gui/"+DataGetter.find("imageID")+".png");
		 		mc.getTextureManager().bindTexture(border);
		 		this.drawModalRectWithCustomSizedTexture(Integer.parseInt(DataGetter.find("imageXCoord")+""), Integer.parseInt(DataGetter.find("imageYCoord")+""), 0, 0, 126, 126, 126, 126);
		 		mc.getTextureManager().bindTexture(image);
		 		this.drawModalRectWithCustomSizedTexture(Integer.parseInt(DataGetter.find("imageXCoord")+"")+2, Integer.parseInt(DataGetter.find("imageYCoord")+"")+2, 0, 0, 122, 122, 122, 122);
		 	}
	    }
}
