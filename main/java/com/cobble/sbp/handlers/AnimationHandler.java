package com.cobble.sbp.handlers;

import java.util.ArrayList;

public class AnimationHandler {

	public static long mainAnim = -1;
	public static long animLast = 0;
	private static long currAnimFrame = 0;
	
	public AnimationHandler() {
		if(currAnimFrame != System.currentTimeMillis()) {
			currAnimFrame = System.currentTimeMillis();
			long sub = currAnimFrame - (animLast);
			animLast = currAnimFrame;
			double tmp = sub/10;
			
			mainAnim+=sub;
			if(AnimationHandler.mainAnim > 1000) {mainAnim = 0;}
		}

		/*
		long currStage = AnimationHandler.mainAnim;
		int tmpX = 0;
		int tmpY = 0;
		if(AnimationHandler.mainAnim <= 1000) {
			tmpX = (int) (currStage/-10)+100;
			tmpY = 100;
			
		} else if(AnimationHandler.mainAnim <= 2000) {
			currStage-=1000;
			tmpY = (int) (currStage/-10)+100;
			
		} else if(AnimationHandler.mainAnim <= 3000) {
			currStage-=2000;
			tmpX = (int) (currStage/10);
			
		} else if(AnimationHandler.mainAnim <= 4000) {
			currStage-=3000;
			tmpY = (int) (currStage/10);
			tmpX = 100;
		}
		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png"));
		GlStateManager.enableBlend();
		GlStateManager.color(0, 0, 0, 0.8F);
		mc.currentScreen.drawModalRectWithCustomSizedTexture(145+280, 145+50, 0, 0, 110, 110, 110, 110);
		GlStateManager.color(0.3F, 0.7F, 1, 1);
		mc.currentScreen.drawModalRectWithCustomSizedTexture(150+tmpX+280, 150+50, 0, 0, 2, 100, 2, 100);
		mc.currentScreen.drawModalRectWithCustomSizedTexture(150+280, 150+tmpY+50, 0, 0, 100, 2, 100, 2);
		GlStateManager.color(1, 1, 1, 1);*/
	}
	
}
