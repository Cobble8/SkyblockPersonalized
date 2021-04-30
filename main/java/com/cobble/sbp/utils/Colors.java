package com.cobble.sbp.utils;

import java.awt.Color;
import java.util.ArrayList;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.renderer.GlStateManager;

/*public enum Colors {
	
	WHITE("§f", 0xFFFFFF, 0),
	DARK_RED("§4", 0xAA0000, 1),
	RED("§c", 0xFF5555, 2),
	GOLD("§6", 0xFFAA00, 3),
	YELLOW("§e", 0xFFFF55, 4),
	GREEN("§a", 0x55FF55, 5),
	DARK_GREEN("§2", 0x00AA00, 6),
	AQUA("§b", 0x55FFFF, 7),
	DARK_AQUA("§3", 0x00AAAA, 8),
	BLUE("§9", 0x5555FF, 9),
	DARK_BLUE("§1", 0x0000AA, 10),
	LIGHT_PURPLE("§d", 0xFF55FF, 11),
	DARK_PURPLE("§5", 0xAA00AA, 12),
	GRAY("§7", 0xAAAAAA, 13),
	DARK_GRAY("§8", 0x555555, 14),
	BLACK("§0", 0x000000, 15),
	CHROMA("§z", 16),
	
	RESET("§r", false),
	STRIKETHROUGH("§m", true),
	BOLD("§l", true),
	UNDERLINE("§n", true),
	ITALIC("§o", true),
	OBFUSCATED("§k", true);
	WHITE("§f"),
	DARK_RED("§4"),
	RED("§c"),
	GOLD("§6"),
	YELLOW("§e"),
	GREEN("§a"),
	DARK_GREEN("§2"),
	AQUA("§b"),
	DARK_AQUA("§3"),
	BLUE("§9"),
	DARK_BLUE("§1"),
	LIGHT_PURPLE("§d"),
	DARK_PURPLE("§5"),
	GRAY("§7"),
	DARK_GRAY("§8"),
	BLACK("§0"),
	CHROMA("§z"),
	
	RESET("§r"),
	STRIKETHROUGH("§m"),
	BOLD("§l"),
	UNDERLINE("§n"),
	ITALIC("§o"),
	OBFUSCATED("§k");
	
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

	

	
	/*public static final String RED = "§c";
	public static final String DARK_RED = "§4";
	public static final String GOLD = "§6";
	public static final String YELLOW = "§e";
	public static final String GREEN = "§a";
	public static final String DARK_GREEN = "§2";
	public static final String AQUA = "§b";
	public static final String DARK_AQUA = "§3";
	public static final String BLUE = "§9";
	public static final String DARK_BLUE = "§1";
	public static final String LIGHT_PURPLE = "§d";
	public static final String DARK_PURPLE = "§5";
	public static final String WHITE = "§f";
	public static final String GRAY = "§7";
	public static final String DARK_GRAY = "§8";
	public static final String BLACK = "§0";
	public static final String CHROMA = "§z";

	public static final String RESET = "§r";
	public static final String STRIKETHROUGH = "§m";
	public static final String BOLD = "§l";
	public static final String UNDERLINE = "§n";
	public static final String ITALIC = "§o";
	public static final String OBFUSCATED = "§k";*/
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
