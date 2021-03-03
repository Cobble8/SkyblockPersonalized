package com.cobble.sbp.gui.screen;


import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.utils.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class PuzzleImage extends Gui {
	
	public static String puzzlePicture = (String) DataGetter.find("imageID");
	public static int puzzleCount = 0;
	public static int xCoord = Integer.parseInt(DataGetter.find("puzzleX")+"");
	public static int yCoord = Integer.parseInt(DataGetter.find("puzzleY")+"");
	public static String puzzleColor = DataGetter.find("puzzleColor")+"";
	ResourceLocation color = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
	
	
	 public PuzzleImage(Minecraft mc) {
		 
		 if(RenderGuiEvent.imageID != "" && RenderGuiEvent.imageID != null && RenderGuiEvent.imageID != "null" && RenderGuiEvent.imageID != "NONE") {

		 	
		 	
		 	if(RenderGuiEvent.imageID != null && RenderGuiEvent.imageID != "NONE") {
		 		ResourceLocation image = new ResourceLocation(Reference.MODID, "textures/gui/"+RenderGuiEvent.imageID.toString()+".png");
		 		
		 		mc.getTextureManager().bindTexture(color);
		 		String[] temp = puzzleColor.split(";");
		 		float r = Float.parseFloat(temp[0].replace(",", "."));
		 		float g = Float.parseFloat(temp[1].replace(",", "."));
		 		float b = Float.parseFloat(temp[2].replace(",", "."));
		 		GlStateManager.color(r, g, b, 1);
		 		this.drawModalRectWithCustomSizedTexture(xCoord-2, yCoord-2, 0, 0, RenderGuiEvent.puzzleScale+4, RenderGuiEvent.puzzleScale+4, RenderGuiEvent.puzzleScale+4, RenderGuiEvent.puzzleScale+4);
		 		GlStateManager.color(1, 1, 1, 1);
		 		mc.getTextureManager().bindTexture(image);
		 		this.drawModalRectWithCustomSizedTexture(xCoord, yCoord, 0, 0, RenderGuiEvent.puzzleScale, RenderGuiEvent.puzzleScale, RenderGuiEvent.puzzleScale, RenderGuiEvent.puzzleScale);
		 		ResourceLocation food = new ResourceLocation("minecraft:textures/gui/icons.png");
		 		mc.getTextureManager().bindTexture(food);
		 	}
		 }
	  }
}
