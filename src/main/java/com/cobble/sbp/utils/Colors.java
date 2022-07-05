package com.cobble.sbp.utils;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.GlStateManager;

import java.awt.*;
import java.util.ArrayList;

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


	public static void setChroma() {
	   setChroma(0, 0);
	}

	public static ArrayList<Float> getColor(String input) {

	   ArrayList<Float> colors = new ArrayList<>();
	   if(input.equals("transparent")) { colors.add(0F); colors.add(0F); colors.add(0F); colors.add(0F); }
	   if(input.equals("chroma")) {return getChroma(0, 0);}

	   String[] tmp = input.split(";");
	   if(tmp.length < 3) {
			 for(int i=0;i<4;i++) { colors.add(1.0F); }
	   }
	   for (String s : tmp) {
			 try {
				colors.add(Float.parseFloat(s.replace(",", ".")));
			 } catch (Exception ignored) {
			 }
	   }

	   if(tmp.length < 4) {
			 colors.add(1.0F);
	   }
	   return colors;
	}

	public static String textColor(String input) {
	   return input.replace("&", Reference.COLOR_CODE_CHAR+"");
	}

	public static void setColor(String color) {
	   GlStateManager.enableBlend();
	   GlStateManager.enableAlpha();
	   if(color.equals("transparent")) { GlStateManager.color(1, 1, 1, 0); return; }
	   else if(color.equals("chroma")) {
			 setChroma(); return;}
	   ArrayList<Float> newColor = getColor(color);
	   try {
			 GlStateManager.color(newColor.get(0), newColor.get(1), newColor.get(2), newColor.get(3));
	   } catch(Exception e) {
			 GlStateManager.color(0, 0, 0, 1);
	   }
	}

	public static String colorArrayToString(ArrayList<Float> c) {
	   StringBuilder output = new StringBuilder();

	   for(int i=0;i<c.size();i++) {
			 output.append(c.get(i));
			 if(i != c.size()-1) { output.append(";"); }
	   }
	   return output.toString();
	}

	public static ArrayList<Float> getRGBFromColorCode(String c) {
	   ArrayList<Float> output = new ArrayList<>();
	   String rgb = "75;20;160";
	   if(c.endsWith("0x")) rgb = "255;255;255";
	   else if(c.endsWith("1x")) rgb = "0;0;100";
	   else if(c.endsWith("2x")) rgb = "0;100;0";
	   else if(c.endsWith("3x")) rgb = "0;100;100";
	   else if(c.endsWith("4x")) rgb = "100;0;0";
	   else if(c.endsWith("5x")) rgb = "100;0;100";
	   else if(c.endsWith("6x")) rgb = "185;100;0";
	   else if(c.endsWith("7x")) rgb = "100;100;100";
	   else if(c.endsWith("8x")) rgb = "15;15;15";
	   else if(c.endsWith("9x")) rgb = "15;15;185";
	   else if(c.endsWith("ax")) rgb = "15;185;15";
	   else if(c.endsWith("bx")) rgb = "15;185;185";
	   else if(c.endsWith("cx")) rgb = "185;15;15";
	   else if(c.endsWith("dx")) rgb = "185;15;185";
	   else if(c.endsWith("ex")) rgb = "185;185;15";
	   else if(c.endsWith("fx")) rgb = "0;0;0";
	   else if(c.endsWith("zx")) return getChroma(25, 25);
	   else if(c.endsWith("0")) rgb = "0;0;0";
	   else if(c.endsWith("1")) rgb = "0;0;170";
	   else if(c.endsWith("2")) rgb = "0;170;0";
	   else if(c.endsWith("3")) rgb = "0;170;170";
	   else if(c.endsWith("4")) rgb = "170;0;0";
	   else if(c.endsWith("5")) rgb = "170;0;170";
	   else if(c.endsWith("6")) rgb = "255;170;0";
	   else if(c.endsWith("7")) rgb = "170;170;170";
	   else if(c.endsWith("8")) rgb = "85;85;85";
	   else if(c.endsWith("9")) rgb = "85;85;255";
	   else if(c.endsWith("a")) rgb = "85;255;85";
	   else if(c.endsWith("b")) rgb = "85;255;255";
	   else if(c.endsWith("c")) rgb = "255;85;85";
	   else if(c.endsWith("d")) rgb = "255;85;255";
	   else if(c.endsWith("e")) rgb = "255;255;85";
	   else if(c.endsWith("f")) rgb = "255;255;255";
	   else if(c.endsWith("z")) return getChroma(0, 0);
	   String[] rgb2 = rgb.split(";");
	   try {
			 double r = (Double.parseDouble(rgb2[0])/255d);
			 double g = (Double.parseDouble(rgb2[1])/255d);
			 double b = (Double.parseDouble(rgb2[2])/255d);

			 output.add((float) r); output.add((float) g); output.add((float) b); output.add(1F);
	   } catch(Exception e) {
			 output.add(1F); output.add(1F); output.add(1F); output.add(1F);
	   }
	   //Utils.print(output);
	   return output;
	}

	public static void resetColor() {
	   GlStateManager.enableBlend();
	   GlStateManager.color(1, 1, 1, 1);
	}

	public static Color configToColor(String color) {
	   return new Color(getRed(color), getGreen(color), getBlue(color), getAlpha(color));
	}

	public static void setChroma(int x, int y) {
	   ArrayList<Float> tmp = getChroma(x, y);
	   float r = tmp.get(0);
	   float g = tmp.get(1);
	   float b = tmp.get(2);
	   float a = tmp.get(3);
 GlStateManager.color(r, g, b, a);
	}

	public static float getRed(String color) {
	   try { return getColor(color).get(0);
	   } catch(Exception e) {return 1F;}
	}

	public static float getGreen(String color) {
	   try { return getColor(color).get(1);
	   } catch(Exception e) {return 1F;}
	}

	public static float getBlue(String color) {
	   try { return getColor(color).get(2);
	   } catch(Exception e) {return 1F;}
	}

	public static float getAlpha(String color) {
	   try { return getColor(color).get(3);
	   } catch(Exception e) {return 1F;}
	}

	public static ArrayList<Float> getChroma(int x, int y) {
	   ArrayList<Float> tmp = new ArrayList<>();
	   long t = System.currentTimeMillis() - (x * 10L + y * 10L);
 Color clr = Color.getHSBColor(t % (int) (Utils.chromaSpeed*500f) / (Utils.chromaSpeed*500f), 0.8f, 0.8f);
 try {
 double r = (double) clr.getRed()/ (double) 255;
 double g = (double) clr.getGreen()/ (double) 255;
 double b = (double) clr.getBlue()/ (double) 255;
 tmp.add((float) r);
 tmp.add((float) g);
 tmp.add((float) b);
 tmp.add(1F);
 } catch(Exception e) {
			tmp.add(1F);
			tmp.add(1F);
			tmp.add(1F);
			tmp.add(1F);
 }
 return tmp;
	}

    public static String getColorFromInt(int colorID) {

        String[] colorList = {WHITE, DARK_RED, RED, GOLD, YELLOW, GREEN, DARK_GREEN, AQUA, DARK_AQUA, BLUE, DARK_BLUE, LIGHT_PURPLE, DARK_PURPLE, GRAY, DARK_GRAY, BLACK, CHROMA};
        String output = WHITE;
        try {
            output = colorList[colorID];
        } catch(Exception ignored) { }
        return output;


    }
}
