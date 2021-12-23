package com.cobble.sbp.handlers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AnimationHandler {

	public static long mainAnim = -1;
	public static long bpsAnim = -1;
	public static long animLast = 0;
	private static long currAnimFrame = 0;
	public static double bpsLastX = 0;
	public static double bpsLastY = 0;
	public static double bpsLastZ = 0;
	public static double playerSpeed = 0;
	private static final ArrayList<Double> recentSpeeds = new ArrayList<>();

	public AnimationHandler() {
		if(currAnimFrame != System.currentTimeMillis()) {
			currAnimFrame = System.currentTimeMillis();
			long sub = currAnimFrame - (animLast);
			animLast = currAnimFrame;
			//double tmp = sub/10;
			
			mainAnim+=sub;
			bpsAnim+=sub;
			if(AnimationHandler.mainAnim > 1000) {mainAnim = 0;}
			if(bpsAnim > 250) {
				bpsAnim=0;
				try {
					EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
					double xOff = player.posX-bpsLastX;
					double yOff = player.posY-bpsLastY;
					double zOff = player.posZ-bpsLastZ;
					if(xOff < 0) {xOff*=-1;}
					if(yOff < 0) {yOff*=-1;}
					if(zOff < 0) {zOff*=-1;}

					double tmpSpeed = (xOff+yOff+zOff)*4;
					try {
						recentSpeeds.add(Double.parseDouble(new DecimalFormat("#.##").format(tmpSpeed)));
					} catch(Exception ignored) {}

					while(recentSpeeds.size() > 5) {recentSpeeds.remove(0);}

					double speed1 = getMode(recentSpeeds);

					ArrayList<Double> tmp = new ArrayList<>();
					for (Double recentSpeed : recentSpeeds) {
						if (!(recentSpeed+"").equals(playerSpeed+"") && recentSpeed != 0) {
							tmp.add(recentSpeed);
						}

					}
					double speed2 = getMode(tmp);

					playerSpeed = Math.min(speed1, speed2);

					bpsLastX = player.posX;
					bpsLastY = player.posY;
					bpsLastZ = player.posZ;
				} catch(Exception ignored) {}


			}

		}
		String outputSpeed = new DecimalFormat("#.#").format(playerSpeed);
	}

	public static double getMode(ArrayList<Double> a) {
		double output = a.get(0);
		int maxSize = 0;
		for(int i=0;i<a.size();i++) {
			int count = 0;
			double currNum = a.get(i);

			for (Double aDouble : a) {
				if (aDouble.equals(currNum)) {
					count++;
				}
			}
			if(count > maxSize) {
				maxSize=count;
				output=currNum;
			}
		}

		return output;
	}
	
}
