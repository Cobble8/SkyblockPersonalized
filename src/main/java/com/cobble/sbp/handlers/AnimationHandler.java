package com.cobble.sbp.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import java.text.DecimalFormat;
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
			mainAnim+=sub;
			if(AnimationHandler.mainAnim > 1000) {mainAnim = 0;}
		}
	}
	
}
