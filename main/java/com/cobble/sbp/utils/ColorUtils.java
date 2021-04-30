package com.cobble.sbp.utils;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.client.renderer.GlStateManager;

public class ColorUtils {
	public static ArrayList<Float> getColor(String input) {
		
		ArrayList<Float> colors = new ArrayList();
		if(input.equals("transparent")) { colors.add(0F); colors.add(0F); colors.add(0F); colors.add(0F); }
		if(input.equals("chroma")) {return getChroma(0, 0);}
		
		String[] tmp = input.split(";");
		if(tmp.length < 3) {
			for(int i=0;i<4;i++) { colors.add(1.0F); }
		}
		for(int i=0;i<tmp.length;i++) {
			try { colors.add(Float.parseFloat(tmp[i].replace(",", "."))); } catch(Exception e) {continue;}
		}
		
		if(tmp.length < 4) {
			colors.add(1.0F);
		}
		return colors;
	}
	
	public static void setColor(String color) {
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		if(color.equals("transparent")) { GlStateManager.color(1, 1, 1, 0); return; }
		else if(color.equals("chroma")) {setChroma(); return;}
		ArrayList<Float> newColor = getColor(color);
		try {
			GlStateManager.color(newColor.get(0), newColor.get(1), newColor.get(2), newColor.get(3));
		} catch(Exception e) {
			GlStateManager.color(0, 0, 0, 1);
		}
	}
	
	public static ArrayList<Float> getRGBFromColorCode(String c) {
		ArrayList<Float> output = new ArrayList();
		String rgb = "";
		if(c.endsWith("0")) rgb = "0;0;0";
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
			Double r = (Double.parseDouble(rgb2[0])/255);
			Double g = (Double.parseDouble(rgb2[1])/255);
			Double b = (Double.parseDouble(rgb2[2])/255);
			
			output.add(r.floatValue()); output.add(g.floatValue()); output.add(b.floatValue()); output.add(1F);
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
	
	public static String toColor(Object r, Object g, Object b) {
		return toColor(r, g, b, 1.0F);
		
	}
	
	public static void setInvertColor(String color) {
		ArrayList<Float> newColor = getColor(color);
		Float r = 1-newColor.get(0);
		Float g = 1-newColor.get(1);
		Float b = 1-newColor.get(2);
		Float a = 1F;
		if(r < 0.5) {r+=0.5F;}
		if(g < 0.5) {g+=0.5F;}
		if(b < 0.5) {b+=0.5F;}
		setColor(toColor(r, g, b, a));
	}
	
	public static String toColor(Object r, Object g, Object b, Object a) {
		
		return r+";"+g+";"+b+";"+a;
		
		
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
		ArrayList<Float> tmp = new ArrayList();
		long t = System.currentTimeMillis() - (x * 10 + y * 10);
        Color clr = Color.getHSBColor(t % (int) (Utils.chromaSpeed*500f) / (Utils.chromaSpeed*500f), 0.8f, 0.8f);
        try {
        Double r = (double) clr.getRed()/ (double) 255;
        Double g = (double) clr.getGreen()/ (double) 255;
        Double b = (double) clr.getBlue()/ (double) 255;
        tmp.add(r.floatValue());
        tmp.add(g.floatValue());
        tmp.add(b.floatValue());
        tmp.add(1F);
        } catch(Exception e) {
        	tmp.add(1F);
        	tmp.add(1F);
        	tmp.add(1F);
        	tmp.add(1F);
        }
        return tmp;
	}
	
	
	public static void setChroma() {
		setChroma(0, 0);
	}
}
