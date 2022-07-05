package com.cobble.sbp.utils;

import com.cobble.sbp.SBP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

public class Utils {

	public static final double TWICE_PI = Math.PI*2;	
	public static final Tessellator tessellator = Tessellator.getInstance();
	public static final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
	public static int chromaSpeed = 4;


	public static boolean inRange(int checkNum, int num, int range) {
		int side1 = num-range;
		int side2 = num+range;
		if(checkNum == num) {
			return false;
		}


		return checkNum >= side1 && checkNum <= side2;
	}


	public static void playClickSound() {
		Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.3F, 1.0F);
	}
	
	public static void playDingSound() {
		Minecraft.getMinecraft().thePlayer.playSound("note.pling", 1.0F, 1.0F);
	}
	
	public static void print(Object string) {
		System.out.println(string);
	}

	public static void printDev(Exception e) { if(SBP.dev) { e.printStackTrace(); } }

	public static void printDev(Object o) { if(SBP.dev) { System.out.println(o); } }


}
