package com.cobble.sbp.utils;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.lwjgl.opengl.GL11;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.skyblock.LobbySwapEvent;
import com.cobble.sbp.gui.menu.settings.SettingGlobal;
import com.ibm.icu.text.DecimalFormat;
import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

public class Utils {

	public static final double TWICE_PI = Math.PI*2;	
	private static Tessellator tessellator = Tessellator.getInstance();
	private static WorldRenderer worldRenderer = tessellator.getWorldRenderer();
	public static ResourceLocation tooltip = new ResourceLocation(Reference.MODID, "textures/gui/tooltip.png");
	public static ResourceLocation color = new ResourceLocation(Reference.MODID, "textures/gui/tooltip_color.png");
	public static int chromaSpeed = 4;
	public static void chatMsgRunCmd(String string, String command) {
		ChatStyle runCommand = new ChatStyle();
		runCommand.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
		runCommand.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(ChatFormatting.AQUA+"Click to show secrets for the "+string+ChatFormatting.AQUA+" room!")));
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(string).setChatStyle(runCommand));
	}
	
	public static void openURL(String url) {
		try {
			Desktop.getDesktop().browse(URI.create(url));
		} catch (IOException e) { Utils.sendErrMsg("Failed to open URL: "+url);}
	}
	
	public static void saveImage(String imageUrl, String destinationFile) {
		try {
			
			new File(destinationFile).getParentFile().mkdirs();
			
			URL url = new URL(imageUrl);
			InputStream is = url.openStream();
			
			/*String destin = destinationFile;
			if(!destin.endsWith("\\")) {
				destin+="\\";
			}*/
			
			OutputStream os = new FileOutputStream(destinationFile);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();
		} catch(Exception e) {
			e.printStackTrace();
			//Utils.sendErrMsg("It failed at: "+destinationFile);
			
			//String[] locFolder = destinationFile.split("/");
			
			
			//File imgFolder = new File(destinationFile); imgFolder.mkdirs();
			//saveImage(imageUrl, destinationFile);
			Utils.print("Download failed for file: '"+imageUrl+"' at '"+destinationFile+"'");
		}
		
	}
	
	public static void saveFile(String text, String destinationFile) {
		try {
			//Utils.print("Saved file: '"+imageUrl+"' at: '"+destinationFile+"'");
			
			//
			File loc = new File(destinationFile);
			loc.getParentFile().mkdirs(); loc.createNewFile();
			Boolean exists = loc.exists();
			FileWriter writer = new FileWriter(loc);
			
			writer.write(text);
			writer.flush();
			writer.close();
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveFileFromUrl(String imageUrl, String destinationFile) {
		try { saveFile(HttpClient.readPage(imageUrl), destinationFile); } catch (Exception e) { }
	}
	
	public static String getSBID() {
        ItemStack heldItem = Minecraft.getMinecraft().thePlayer.getHeldItem();
        return getSBID(heldItem);
    }
	
	public static String getSBID(ItemStack item) {
        if (item == null) { return ""; }
        else if (!item.hasTagCompound()) { return ""; }

        NBTTagCompound skyBlockData = item.getSubCompound("ExtraAttributes", false);

        if (skyBlockData != null) { String itemId = skyBlockData.getString("id");  if (!itemId.equals("")) { return itemId.toLowerCase(); } }  return "";
    }
	
	public static void drawRegularPolygon(double x, double y, int radius, int sides, int percent)
	{
		drawRegularPolygon(x, y, radius, sides, percent, 1, 1, 1);
	}
	
	public static void drawRegularPolygon(double x, double y, int radius, int sides, int percent, float r, float g, float b) {
		try {
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.color(1, 1, 1);
		int rad2 = radius/2;
		int per = percent*sides/100;
		GlStateManager.color(r, g, b);
		worldRenderer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION);
		for(int i=0;i<per+1;i++) {

			double angle = (TWICE_PI * i / sides) + Math.toRadians(180);
			
			worldRenderer.pos(x + Math.sin(angle) * radius / 4*3, y+ Math.cos(angle) * radius / 4*3, 0).endVertex();
			worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
		}
		
		
		tessellator.draw();
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		
		} catch(Exception e) { }
	}
	
	
	public static void renderPlayer(int x, int y, int scaled, int lookingX, int lookingY) {
		int posX = x;
		int posY = y;
		int scale = scaled;
		int mouseX2 = lookingX-posX;
		int mouseY2 = lookingY-posY+(scale*4/3)+(scale/10);
		
		EntityLivingBase ent = Minecraft.getMinecraft().thePlayer;
		GlStateManager.enableBlend();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY2*-1 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX2*-1 / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX2*-1 / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY2*-1 / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(360.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GlStateManager.disableColorMaterial();
	}
	
	public static void drawString(String text, int x, int y) {
		drawString(text, x, y, SettingGlobal.textStyle);
	}
	
	
	public static void drawString(ArrayList<String> text, int x, int y, int textStyle) {
		if(text.isEmpty()) {return;}
		drawString(text.toArray(), x, y, textStyle);
	}
	
	public static void drawString(Object[] texts, int x, int y, int textStyle, Boolean align) {
		if(texts.length == 0) {return;}
		int finXOff = 0;
		Minecraft mc = Minecraft.getMinecraft();
		int finTextStyle = textStyle;
		
		ArrayList<String> text = new ArrayList();
		for(Object curr : texts) {
			text.add(curr+"");
		}
		int maxStrWidth = 0;
		for(int i=0;i<text.size();i++) {
			int strWidth = mc.fontRendererObj.getStringWidth(text.get(i).toString());
			if(maxStrWidth < strWidth) { maxStrWidth = strWidth; }
		}
		int aO = 0;
		if(align) {
			if(x > SBP.width/2) {
				aO = maxStrWidth;
			}
		}
		
		
		
		if(textStyle == 3) {
			
			
			finTextStyle = 2;
			ResourceLocation bg = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
			mc.getTextureManager().bindTexture(bg); 
			
			maxStrWidth+=8;
			GlStateManager.enableBlend();
			GlStateManager.color(0, 0, 0, 0.6F);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x-4-aO, y-4, 0, 0, maxStrWidth, 7+(11*text.size()), 5, 5);
			
		} else if(textStyle == 4) {
			finTextStyle = 2;

			int strHeight = text.size()*11;

			GlStateManager.enableBlend();
			mc.getTextureManager().bindTexture(color);
			String str = text.get(0);
			if(str.startsWith(Reference.COLOR_CODE_CHAR+"")) {
				ArrayList<Float> clr = ColorUtils.getRGBFromColorCode(str.substring(0, 2));
				try {
					GlStateManager.color(clr.get(0), clr.get(1), clr.get(2), clr.get(3));
				} catch(Exception e) {ColorUtils.setColor("0.0;0.0;0.6;");}
				
			} else {
				ColorUtils.setColor("0.0;0.0;0.6;");
			}
			//Color
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x-2-aO, y-3, 0, 0, maxStrWidth+6, 1, maxStrWidth+6, 16);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x-2-aO, y-1+strHeight, 0, 15, maxStrWidth+6, 1, maxStrWidth+6, 16);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x-2-aO, y-2, 0, 0, 1, strHeight+1, 16, strHeight+1);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x+maxStrWidth+2-aO, y-2, 15, 0, 1, strHeight+1, 16, strHeight+1);
			
			mc.getTextureManager().bindTexture(tooltip);
			GlStateManager.color(1, 1, 1, 0.9F);
			
			
			//Corners
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x-3-aO, y-4, 0, 0, 4, 4, 16, 16);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x+maxStrWidth-aO, y-4, 12, 0, 4, 4, 16, 16);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x-3, y+strHeight-3-aO, 0, 12, 4, 4, 16, 16);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x+maxStrWidth-aO, y+strHeight-3, 12, 12, 4, 4, 16, 16);
			
			//Text Background
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x+1-aO, y, 4, 4, maxStrWidth-1, strHeight-3, 1, 1);
			//Top and Bottom Sides
			for(int i=0;i<maxStrWidth-1;i++) { 
				mc.currentScreen.drawModalRectWithCustomSizedTexture(x+i+1-aO, y-4, 4, 0, 1, 4, 16, 16);
				mc.currentScreen.drawModalRectWithCustomSizedTexture(x+i+1-aO, y+strHeight-3, 4, 12, 1, 4, 16, 16);
			}
			//Left and Right Sides
			for(int i=0;i<strHeight-3;i++) {
				mc.currentScreen.drawModalRectWithCustomSizedTexture(x-3-aO, y+(i), 0, 4, 4, 1 , 16, 16);
				mc.currentScreen.drawModalRectWithCustomSizedTexture(x+maxStrWidth-aO, y+(i), 12, 4, 4, 1 , 16, 16);
			}
			
			finXOff = 1;
		}
		
		for(int i=0;i<text.size();i++) {
			//String currString = text+"";
			int aO2 = 0;
			if(aO > 0) {
				aO2 = mc.fontRendererObj.getStringWidth(text.get(i));
			}
			Utils.drawString(text.get(i), x+finXOff-(aO2), y+(11*i), finTextStyle);
			
		}
	}
	
	public static void drawString(Object[] texts, int x, int y, int textStyle) {
		drawString(texts, x, y, textStyle, false);
	}
	
	public static void drawString(String text, int x, int y, int textStyle, Boolean align) {
		int alignment = 1;
		if(align) {
			if(x > SBP.width/2) {
				alignment=-1;
			}
		}
		
		
		if(text.equals("")) {return;}
		int xOffset = 0;
		
		
		//Utils.print(text);
		Minecraft mc = Minecraft.getMinecraft();
		int strWidth = mc.fontRendererObj.getStringWidth(text);
		if(strWidth <= 0) {return;}
		int aO = 0;
		if(alignment==-1) {
			aO=strWidth;
		}
		String str = text;
		String str2 = Utils.unformatText(text);
		
		if(str.contains(Colors.CHROMA)) {
			String strTypes = "";
			if(str.contains(Colors.BOLD)) {strTypes+=Colors.BOLD;}
			if(str.contains(Colors.ITALIC)) {strTypes+=Colors.ITALIC;}
			if(str.contains(Colors.UNDERLINE)) {strTypes+=Colors.UNDERLINE;}
			if(str.contains(Colors.STRIKETHROUGH)) {strTypes+=Colors.STRIKETHROUGH;}
			if(str.contains(Colors.OBFUSCATED)) {strTypes+=Colors.OBFUSCATED;}
			str = Colors.CHROMA+Utils.unformatAllText(str)+strTypes;
		}
		
		
		
		Boolean shadows = false;
		if(textStyle == 0) { shadows = true; }
		else if(textStyle == 1) { drawBoldedString(str2, x, y); }
		else if(textStyle == 3) {
			ResourceLocation bg = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
			GlStateManager.enableBlend();
			GlStateManager.color(0, 0, 0, 0.6F);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x-2, y-2, 0, 0, strWidth+3, 11, 1, 1);
			GlStateManager.color(1, 1, 1, 1);
		}
		else if(textStyle == 4) {
			
			mc.getTextureManager().bindTexture(color);
			GlStateManager.enableBlend();
			if(str.startsWith(Reference.COLOR_CODE_CHAR+"")) {
				ArrayList<Float> clr = ColorUtils.getRGBFromColorCode(str.substring(0, 2));
				try {
					GlStateManager.color(clr.get(0), clr.get(1), clr.get(2), clr.get(3));
				} catch(Exception e) {ColorUtils.setColor("0.0;0.0;0.6;");}
				
			} else {
				ColorUtils.setColor("0.0;0.0;0.6;");
			}
            
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x-2-aO, y-3, 0, 0, 1, 13, 16, 16);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x+2+strWidth-aO, y-3, 15, 0, 1, 13, 16, 16);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x-2-aO, y-3, 0, 0, strWidth+4, 1, strWidth+4, 16);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x-2-aO, y+9, 0, 15, strWidth+4, 1, strWidth+4, 16);
			
			mc.getTextureManager().bindTexture(tooltip);
			GlStateManager.enableBlend();
			GlStateManager.color(1, 1, 1, 0.9F);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x-3-aO, y-4, 0, 0, 4, 11, 16, 16);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x-3-aO, y+7, 0, 12, 4, 4, 16, 16);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x+strWidth-aO, y-4, 12, 0, 4, 11, 16, 16);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x+strWidth-aO, y+7, 12, 12, 4, 4, 16, 16);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x+1-aO, y, 4, 0, strWidth-1, 7, 1, 1);
			for(int i=0;i<strWidth/2;i++) { mc.currentScreen.drawModalRectWithCustomSizedTexture(x+i*2+1-aO, y-4, 4, 0, 2, 4, 16, 16); }
			for(int i=0;i<strWidth/2;i++) { mc.currentScreen.drawModalRectWithCustomSizedTexture(x+i*2+1-aO, y+7, 4, 12, 2, 4, 16, 16); }
			xOffset = 1;
			
		}
		String bold = "";
		mc.fontRendererObj.drawString(str.replace(Colors.CHROMA, ""), x+xOffset-aO, y, 0, shadows);
		
		
		int chromaCount = 0;
		char[] charArray = str.toCharArray();
		if(str.contains(Reference.COLOR_CODE_CHAR+"z")) {
		
			for(int i=0;i<charArray.length;i++) {
				try {
					if((charArray[i]+"").equals(Reference.COLOR_CODE_CHAR+"") && (charArray[i+1]+"").equals("z")) {
						chromaCount++;
					}
				} catch(Exception e) { }
			}
		}
		String outputStr = str;
		
		for(int j=0;j<chromaCount;j++) {
		
		if(outputStr.contains(Colors.CHROMA)) {
			int beginChroma = -1;
			int endChroma = -1;
			charArray = outputStr.toCharArray();
			
			ArrayList chArr= new ArrayList();
			for(char curr : charArray) {
				chArr.add(curr);
			}
			
			
			for(int i=0;i<chArr.size();i++) {
				
				
				if(beginChroma == -1 && (chArr.get(i)+"").equals(Reference.COLOR_CODE_CHAR+"") && (chArr.get(i+1)+"").equals("z")) { beginChroma = i; }
				
				else if(beginChroma != -1 && endChroma == -1) {
					if((chArr.get(i)+"").equals(Reference.COLOR_CODE_CHAR+"") || i >= chArr.size()-1) {
						
						endChroma = i;
						if(j == chromaCount-1) { endChroma+=1; }
						
						continue;
					}
				}
			}
			try {
				
				outputStr+="f";
				
				String preStr = outputStr.substring(0, beginChroma);
				String subStr = outputStr.substring(beginChroma+2, endChroma);
				//Utils.print("String: "+outputStr+"  |  SubString: "+subStr);
				String postStr = outputStr.substring(endChroma);
				int preWidth = mc.fontRendererObj.getStringWidth(preStr);
				int subWidth = mc.fontRendererObj.getStringWidth(subStr);
				//if(str.contains(Colors.BOLD)) {
					//subStr = "&l"+subStr;
				//}
				String strTypes = "";
				if(str.contains(Colors.BOLD)) {strTypes+=Colors.BOLD;}
				if(str.contains(Colors.ITALIC)) {strTypes+=Colors.ITALIC;}
				if(str.contains(Colors.UNDERLINE)) {strTypes+=Colors.UNDERLINE;}
				if(str.contains(Colors.STRIKETHROUGH)) {strTypes+=Colors.STRIKETHROUGH;}
				if(str.contains(Colors.OBFUSCATED)) {strTypes+=Colors.OBFUSCATED;}
				
				drawChromaString(subStr, x+preWidth+xOffset-aO, y, shadows, strTypes, align);
				outputStr = preStr+subStr+postStr;
			} catch(Exception e) {
				
			}
			
			
		}
		}
	}
	
	
	public static void drawString(String text, int x, int y, int textStyle) {
		
		drawString(text, x, y, textStyle, false);
		
	}
	
	
	public static void drawConfinedString(String text, int x, int y, int textStyle, int maxWidth) {
		Minecraft mc = Minecraft.getMinecraft();
		int strWidth = mc.fontRendererObj.getStringWidth(text);
		
		int posX = x;
		int posY = y;
		
		if(strWidth <= maxWidth) {
			drawString(text, x, y, textStyle);
		} else {
			int scaledWidth = strWidth;
			int percent = 100;
			while(scaledWidth > maxWidth) { percent-=1; scaledWidth = strWidth*percent/100; }
			Double scale = Double.parseDouble(percent+"")/100;
			int heightScale = (int) (12*scale);
			
			
			drawScaledString(text, posX+(scaledWidth/2), posY+(heightScale/2), 0, scale, textStyle, false);
		}
		
		
		
		
		
		
		
		
	}
	
	public static void drawTitle() {
		
		//int posX = Minecraft.getMinecraft().
		//int posY = 200;
		try {
			int posX = Minecraft.getMinecraft().displayWidth/(Minecraft.getMinecraft().gameSettings.guiScale);
			int posY = Minecraft.getMinecraft().displayHeight/(Minecraft.getMinecraft().gameSettings.guiScale);
			drawScaledString(SBP.titleString, posX/2, posY/2, 0, SBP.titleScale, 0, true);
		} catch (Exception e) { }
		
		//Utils.print(SBP.width);
		
	}
	
	public static void drawScaledString(String string, int x, int y, int color, double scale, int textStyle, Boolean centered) {

		Minecraft mc = Minecraft.getMinecraft();
		int strWidth = mc.fontRendererObj.getStringWidth(string);
		int posX = x;
		int posY = y;
		
		
		posX-=(strWidth*scale/2);

		
		posY-=(6*scale);
		GlStateManager.pushMatrix();
		
		GlStateManager.scale(scale, scale, scale);
		  
		Utils.drawString(string, (int) (posX/scale), (int) (posY/scale), textStyle);
		GlStateManager.popMatrix();
	}
	
	public static void drawChromaString(String text, int x, int y, Boolean shadow, String strType, Boolean align) {
		
		if(!strType.equals("")) {
			//Utils.print(text);
		}
		
		
		//Utils.print(strType+text);
		Minecraft mc = Minecraft.getMinecraft();
        int tmpX = x; 
        String str = text;
        char[] chArr = str.toCharArray();
        
        for (int j=0;j<chArr.length;j++) {
        	char tc = chArr[j];
        	String output = tc+"";
        	if(tc != Reference.COLOR_CODE_CHAR) {
        		
        		long t = System.currentTimeMillis() - (tmpX * 10 + y * 10);
                int i = Color.HSBtoRGB(t % (int) (chromaSpeed*500f) / (chromaSpeed*500f), 0.8f, 0.8f);
                
                mc.fontRendererObj.drawString(strType+output, tmpX, y, i, shadow);
                tmpX += mc.fontRendererObj.getStringWidth(strType+output);
                
        	}
        }
        mc.fontRendererObj.drawString(Colors.WHITE, x, y, tmpX);
        
    }
	public static void drawBoldedString(String text, int x, int y) {
		Minecraft mc = Minecraft.getMinecraft();
		
		mc.fontRendererObj.drawString(text, x+1, y, 0, false);
        mc.fontRendererObj.drawString(text, x-1, y, 0, false);
        mc.fontRendererObj.drawString(text, x, y+1, 0, false);
        mc.fontRendererObj.drawString(text, x, y-1, 0, false);
	}
	
	public static Boolean inRange(int checkNum, int num, int range) {
		int side1 = num-range;
		int side2 = num+range;
		if(checkNum == num) {
			return false;
		}
		
		
		if(checkNum >= side1 && checkNum <= side2) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void checkIfOnSkyblock() {
		if(DataGetter.findBool("onlyOnSkyblock")) {
			String title = getBoardTitle().toLowerCase();
			if(title.contains("skyblock")) {
				if(!SBP.onSkyblock) { Utils.print("Logged onto Skyblock"); LobbySwapEvent.currLobby="";} SBP.onSkyblock=true;
			} else { if(SBP.onSkyblock) { Utils.print("Logged off of Skyblock"); } SBP.onSkyblock=false; }
		
		} else {
			SBP.onSkyblock=true;
		}
	}
	
	public static String getBoardTitle() {
		String output = "null";
		try {
			
		ScoreObjective sidebarObjective = Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
		
		output = Utils.unformatAllText(sidebarObjective.getDisplayName());
		} catch(Exception e) {
			return "null";
		}
		//Utils.sendMessage(output);
		return output;
	}
	
	public static void setEasterEgg(int num, String input) {
		try {
			String[] temp = DataGetter.findStr("easterEggsFound").split(";");
			ArrayList<String> temp2 = new ArrayList();
			
			for(int i=0;i<temp.length;i++) {
				temp2.add(temp[i]);
			}
			
			temp2.set(num-1, input);
			String output = "";
			for(int i=0;i<temp2.size();i++) {
				output+=temp2.get(i)+";";
			}
			ConfigHandler.newObject("easterEggsFound", output);
			
		} catch(Exception e) {
			ConfigHandler.newObject("easterEggsFound", ConfigHandler.getDefaultValue("easterEggsFound"));
			return;
		}
	}
	
	public static String getEasterEgg(int num) {
		
		try {
		String output = "";
		String[] temp = DataGetter.findStr("easterEggsFound").split(";");
		ArrayList<String> temp2 = new ArrayList();
		
		for(int i=0;i<temp.length;i++) {
			temp2.add(temp[i]);
		}
		output = temp2.get(num);
		
		
		return output;
		} catch(Exception e) {
			return "null";
		}
	}
	
	
	public static String getColorFromInt(int colorID) {
		
		String[] colorList = {Colors.WHITE, Colors.DARK_RED, Colors.RED, Colors.GOLD, Colors.YELLOW, Colors.GREEN, Colors.DARK_GREEN, Colors.AQUA, Colors.DARK_AQUA, Colors.BLUE, Colors.DARK_BLUE, Colors.LIGHT_PURPLE, Colors.DARK_PURPLE, Colors.GRAY, Colors.DARK_GRAY, Colors.BLACK, Colors.CHROMA};
		String output = Colors.WHITE;
		try {
			output = colorList[colorID];
		} catch(Exception e) { }
		return output;
		
		
	}
	
	public static void drawTexturedRectNoBlend(float x, float y, float width, float height, float uMin, float uMax, float vMin, float vMax, int filter) {
        GlStateManager.enableTexture2D();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer
                .pos(x, y+height, 0.0D)
                .tex(uMin, vMax).endVertex();
        worldrenderer
                .pos(x+width, y+height, 0.0D)
                .tex(uMax, vMax).endVertex();
        worldrenderer
                .pos(x+width, y, 0.0D)
                .tex(uMax, vMin).endVertex();
        worldrenderer
                .pos(x, y, 0.0D)
                .tex(uMin, vMin).endVertex();
        tessellator.draw();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    }
	
	public static String getSystemMinute() {
		String output = "";

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm:ss");
		LocalDateTime date = LocalDateTime.now();
		output = dtf.format(date);
		//output = dtf+"";
		
		return output+"";
	}
	
	public static String unformatText(String string) {
		String output = "";
		char[] chars = string.toCharArray();
		Boolean prevChar = false;
		for(int i=0;i<chars.length;i++) {
			
			try {
				if((chars[i]+"").equals(Reference.COLOR_CODE_CHAR+"")) {
					if("mlnok".contains(chars[i+1]+"")) {
						output+=chars[i];
						continue;
					}
				}
			}catch(Exception e) {}
			
			
			if(!((chars[i]+"").equals(Reference.COLOR_CODE_CHAR+""))) {
				if(!prevChar) {
					output+=chars[i];
				}
				prevChar = false;
				
			} else if((chars[i]+"").equals(Reference.COLOR_CODE_CHAR+"")) {
				prevChar = true;
			}
		}
		
		return output;
	}
	
	public static String unformatAllText(String string) {
		String output = "";
		char[] chars = string.toCharArray();
		Boolean prevChar = false;
		for(int i=0;i<chars.length;i++) {
			
			
			if(!((chars[i]+"").equals(Reference.COLOR_CODE_CHAR+""))) {
				if(!prevChar) {
					output+=chars[i];
				}
				prevChar = false;
				
			} else if((chars[i]+"").equals(Reference.COLOR_CODE_CHAR+"")) {
				prevChar = true;
			}
		}
		
		return output;
	}
	
	public static String formatNums(int num) {
		NumberFormat myFormat = NumberFormat.getInstance();
	    myFormat.setGroupingUsed(true);
	    return myFormat.format(num);
	}
	
	public static void playClickSound() {
		Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.3F, 1.0F);
	}
	
	public static void playDingSound() {
		Minecraft.getMinecraft().thePlayer.playSound("note.pling", 1.0F, 1.0F);
	}
	
	public static Boolean checkBlock(World world, int x, int y, int z, Block blockCheckAgainst) {
		if(world.getBlockState(new BlockPos(x, y, z)).getBlock().getRegistryName().equals(blockCheckAgainst.getRegistryName())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String secondsToTime(int input) { 
		long hours = TimeUnit.MILLISECONDS
			    .toHours(input);
			input -= TimeUnit.HOURS.toMillis(hours);

			long minutes = TimeUnit.MILLISECONDS
			    .toMinutes(input);
			input -= TimeUnit.MINUTES.toMillis(minutes);

			long seconds = TimeUnit.MILLISECONDS.toSeconds(input);
			String secs = seconds+"";
			if(seconds < 10) {
				secs = "0"+seconds;
			}
			
			if(hours > 0) {
				return hours+"h"+minutes+"m"+secs+"s";
			} else {
				return minutes+":"+secs;
			}
	}
	
	public static ArrayList<Integer> sortIntArray(ArrayList<Integer> array) {

		ArrayList<Integer> returnArray = new ArrayList<Integer>();
		returnArray = array;
		Collections.sort(returnArray);
		
		return returnArray;
		
	}
	
	public static Boolean isNumeric(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
	
	public static void print(Object string) {
		System.out.println(string);
	}
	
	public static void sendMessage(String string) {
		sendSpecificMessage(Colors.DARK_AQUA+"["+Colors.AQUA+"SBP"+Colors.DARK_AQUA+"] "+Colors.YELLOW+string+Colors.WHITE);
	}
	
	public static void sendSpecificMessage(String string) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(string));
	}
	
	public static String removeLastChars(String str, int chars) {
		String finalString = str;
		finalString = finalString.substring(0, finalString.length()-chars);
		
		/*
		for(int i=0;i<chars;i++) {
			char[] charList = finalString.toCharArray();
			finalString="";
			for(int j=0;i<charList.length;j++) {
				if(j != charList.length-1) {
					finalString+=charList[j];
				}
			}
		}*/
		
		return finalString;
		
	    //
	}
	
	public static String removeIntLast(String str, int chars) {
		return str.substring(0, str.length() - chars);
	}
	
	public static Boolean checkIfArrayContains(ArrayList<String> arrayList, String stringCheck) {
		Boolean tempBoolean = false;
		for(int i=0;i<arrayList.size();i++) {
			if(stringCheck.equals(arrayList.get(i))) {
				tempBoolean = true;
			}
		}
		
		return tempBoolean;
	}
	
	public static Boolean checkIfArrayContains(String[] arrayList, String stringCheck) {
		Boolean tempBoolean = false;
		for(int i=0;i<arrayList.length;i++) {
			if(stringCheck.equals(arrayList[i])) {
				tempBoolean = true;
			}
		}
		
		return tempBoolean;
	}
	
	public static Boolean checkIfCharLetter(String string) {

		String charList = "abcdefghijklmnopqrstuvwxyz ?:;'<>/\\\".,0123456789!()*^%$@&-+_=[]{}~`|#";
		Boolean output = false;
		if(charList.contains(string.toLowerCase())) {output=true;}
		return output;
		
	}
	
	public static int stripNum(String input) {
		int output = 0;
		String charList = "abcdefghijklmnopqrstuvwxyz ?:;'<>/\\\".,!()*^%$@&-+_=[]{}~`|#";
		char[] temp = input.toLowerCase().toCharArray();
		String finalOutput = "";
		for(int i=0;i<temp.length;i++) {
			if(!(charList.contains(temp[i]+""))) {
				finalOutput+=temp[i];
			}
		}
		//Utils.print(finalOutput);
		try {
		output = Integer.parseInt(finalOutput);
		} catch(Exception e) {
			output = 0;
		}
		return output;
	}
	
	public static Boolean checkIfCharInt(String string) {

		String charList = "0123456789";
		Boolean output = false;
		if(charList.contains(string.toLowerCase())) {output=true;}
		return output;
		
	}
	
	public static void sendErrMsg(String string) {
		sendSpecificMessage(Colors.DARK_RED+"["+Colors.RED+"SBP"+Colors.DARK_RED+"] "+Colors.YELLOW+string);
	}
	
	public static boolean fileTest(String args) {
        File f = new File(args);
        //print(f + (f.exists()? " is found " : " is missing "));
        return f.exists()? true : false;
    }
	
	public static Boolean invertBoolean(Boolean input) {
		if(input == true) { return false; } else return true;
	}
	
	public static String readFile(String filePath) {
		String output = "";
		
		try {
		      File myObj = new File(filePath);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        output+=(data+" ");
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		return output;
	}
	
	
}
