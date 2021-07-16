package com.cobble.sbp.utils;

import java.awt.Color;
import java.util.ArrayList;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.renderer.GlStateManager;

/*public enum Colors {
	

	
	//private static char c = Reference.COLOR_CODE_CHAR;
	
	
	private String colorCode;
	private Color rgbColor;
	private boolean isFormat;
	private int colorID;
	
	Colors(String colorCode) {
		this.colorCode=colorCode;
	}
	

	
	Colors(String code, int colorID) {
		this(code, -1, colorID);
	}

	Colors(String code, int rgb, int colorID) {
		this(code, rgb, false, colorID);
	}
	
	Colors(String code, boolean isFormat) {
		this(code, -1, isFormat, -1);
	}
	Colors(String code, int rgb, boolean isFormat, int colorID) {
		this.colorCode=code;
		this.rgbColor=(this.isColor() ? new Color(rgb) : null);
		this.isFormat=isFormat;
		this.colorID=colorID;
	}
	
	
	public boolean isColor() {
		return !this.isFormat() && this != RESET;
	}
	
	public boolean isFormat() {
		return this.isFormat;
	}
}*/


public class Colors {

	


	public static final String RED = ChatFormatting.RED+"";
	public static final String DARK_RED = ChatFormatting.DARK_RED+"";
	public static final String GOLD = ChatFormatting.GOLD+"";
	public static final String YELLOW = ChatFormatting.YELLOW+"";
	public static final String GREEN = ChatFormatting.GREEN+"";
	public static final String DARK_GREEN = ChatFormatting.DARK_GREEN+"";
	public static final String AQUA = ChatFormatting.AQUA+"";
	public static final String DARK_AQUA = ChatFormatting.DARK_AQUA+"";
	public static final String BLUE = ChatFormatting.BLUE+"";
	public static final String DARK_BLUE = ChatFormatting.DARK_BLUE+"";
	public static final String LIGHT_PURPLE = ChatFormatting.LIGHT_PURPLE+"";
	public static final String DARK_PURPLE = ChatFormatting.DARK_PURPLE+"";
	public static final String WHITE = ChatFormatting.WHITE+"";
	public static final String GRAY = ChatFormatting.GRAY+"";
	public static final String DARK_GRAY = ChatFormatting.DARK_GRAY+"";
	public static final String BLACK = ChatFormatting.BLACK+"";
	public static final String CHROMA = Reference.COLOR_CODE_CHAR+"z";

	public static final String RESET = ChatFormatting.RESET+"";;
	public static final String STRIKETHROUGH = ChatFormatting.STRIKETHROUGH+"";
	public static final String BOLD = ChatFormatting.BOLD+"";
	public static final String UNDERLINE = ChatFormatting.UNDERLINE+"";
	public static final String ITALIC = ChatFormatting.ITALIC+"";
	public static final String OBFUSCATED = ChatFormatting.OBFUSCATED+"";
	
	
	
}
