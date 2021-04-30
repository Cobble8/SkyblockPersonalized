package com.cobble.sbp.gui.screen.dungeons;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import org.lwjgl.input.Keyboard;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.gui.menu.settings.SettingGlobal;
import com.cobble.sbp.handlers.AnimationHandler;
import com.cobble.sbp.handlers.KeyBindingHandler;
import com.cobble.sbp.threads.dungeons.DownloadImageInBackgroundThread;
import com.cobble.sbp.utils.ColorUtils;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.SecretUtils;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class SecretImage extends Gui {
	ResourceLocation border = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
	public static int secretSize = 10;
	public static int imgHeight = 101*DataGetter.findInt("scrtSize")/10;
	public static int imgWidth = 192*DataGetter.findInt("scrtSize")/10;
	public static int imgX = DataGetter.findInt("scrtX");
	public static int imgY = DataGetter.findInt("scrtY");
	public static String bgColorID = DataGetter.findStr("scrtBgColor");
	public static String arrowColor = DataGetter.findStr("scrtArrowColor");
	public static int scrtColorID = DataGetter.findInt("scrtTextColor");
	
	public static int currentSecret = 0;
	public static int maxSecrets = 1;
	public static String roomShape = "1x1";
	public static String roomSecretsID = "NONE";
	public static List<String> currentSecretText = new ArrayList();
	public static String currDungeon = "catacombs";
	
	private static String currRoom = "NONE";
	
	private static int currSecret = 0;
	public static Boolean reloadImage = false;
	public static Boolean downloadImage = false;
	private static DynamicTexture dynam = null;
	public static ResourceLocation currImage = new ResourceLocation(Reference.MODID, "error");

	public SecretImage() {
		Minecraft mc = Minecraft.getMinecraft();
		
		if(!roomSecretsID.equals(currRoom) || currentSecret != currSecret) {
			reloadImage = true;
			currRoom = roomSecretsID;
			currSecret = currentSecret;
		}
		
		if(reloadImage) {
			reloadImage=false;
			//Utils.sendMessage("Reloaded Image");
			try {
				//Utils.sendMessage("New Image!");//this is just to show so you know its not constantly loading it
				//File img = 
				BufferedImage bufImg = ImageIO.read(new File("config/"+Reference.MODID+"/secrets/"+SecretImage.currDungeon+"/"+SecretImage.roomShape+"/"+SecretImage.roomSecretsID+"/"+SecretImage.currentSecret+".png"));
				
				if(dynam == null) {
					dynam = new DynamicTexture(bufImg);
				} else {
					bufImg.getRGB(0, 0, bufImg.getWidth(), bufImg.getHeight(), dynam.getTextureData(), 0, bufImg.getWidth());
					dynam.updateDynamicTexture();
				}
				currImage = mc.getTextureManager().getDynamicTextureLocation(" ", dynam);
				/*
				try {
					try {
						dynam.deleteGlTexture();
						mc.getTextureManager().deleteTexture(currImage);
						
					} catch(Exception e) { }
					dynam = new DynamicTexture(bufImg);
					bufImg.getRGB(0, 0, bufImg.getWidth(), bufImg.getHeight(), dynam.getTextureData(), 0, bufImg.getWidth());
					dynam.updateDynamicTexture();
					currImage = mc.getTextureManager().getDynamicTextureLocation(" ", dynam);
				} catch(Exception e) {
					e.printStackTrace();
				}*/
				
			} catch(IIOException e) {
				downloadImage=true;
				currImage = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
				new DownloadImageInBackgroundThread().start();
				
			} catch(Exception e2) {
				Utils.sendErrMsg("Failed to download room! Check logs for further support!");
				roomSecretsID="NONE";
				e2.printStackTrace(); return;
			}
		} else if(roomSecretsID.equals("NONE")) {return;}
		
	
		
		
		try {
			int scale = DataGetter.findInt("scrtSize");
			imgWidth = 192*scale/10;
			imgHeight = 101*scale/10;
			String text = currentSecretText.get(currentSecret);
			String color = Utils.getColorFromInt(scrtColorID);
			mc.getTextureManager().bindTexture(border);
			ColorUtils.setColor(bgColorID);
			this.drawModalRectWithCustomSizedTexture(imgX, imgY, 0, 0, imgWidth+4, imgHeight+42, imgWidth, imgHeight);
			
			
			ColorUtils.resetColor();
			
			
			//downloadImage=false;

			if(downloadImage) { GlStateManager.color(0, 0, 0); mc.getTextureManager().bindTexture(border); } else {
				mc.getTextureManager().bindTexture(currImage);
			}
			
			this.drawModalRectWithCustomSizedTexture(imgX+2, imgY+14+16, 0, 0, imgWidth, imgHeight, imgWidth, imgHeight);
			if(downloadImage) {
				String downloading = "Downloading";
				if(AnimationHandler.mainAnim < 250) {
					downloading += ".";
				} else if(AnimationHandler.mainAnim < 500) {
					downloading += "..";
				} else if(AnimationHandler.mainAnim < 750) {
					downloading += "...";
				}
				
				
				Utils.drawString(color+downloading, imgX+(imgWidth/2)-(mc.fontRendererObj.getStringWidth(downloading)/2), imgY+imgHeight/2+6+16);
			}
			
			
			mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/arrows.png"));
			ColorUtils.setInvertColor(bgColorID);
			try {
				String tmp = currentSecretText.get(currentSecret-1);
				this.drawModalRectWithCustomSizedTexture(imgX+2, imgY-14+16, 0, 0, 14, 11, 42, 11);
				Utils.drawString(color+Keyboard.getKeyName(KeyBindingHandler.prevSecret.getKeyCode()), imgX+2+16, imgY-12+16);
				mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/arrows.png"));
			} catch(Exception e) {}
			try {
				ColorUtils.setInvertColor(bgColorID);
				String tmp = currentSecretText.get(currentSecret+1);
				this.drawModalRectWithCustomSizedTexture(imgX-2+imgWidth-12, imgY-14+16, 14, 0, 14, 11, 42, 11);
				Utils.drawString(color+Keyboard.getKeyName(KeyBindingHandler.nextSecret.getKeyCode()), imgX-2+imgWidth-12-8, imgY-12+16);
			} catch(Exception e) {
				ColorUtils.resetColor();
				mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/arrows.png"));
				this.drawModalRectWithCustomSizedTexture(imgX-2+imgWidth-12, imgY-14+16, 28, 0, 14, 11, 42, 11);
				Utils.drawString(color+Keyboard.getKeyName(KeyBindingHandler.clearSecret.getKeyCode()), imgX-2+imgWidth-12-8, imgY-12+16);
			}
			
			int aO = 0;
			int aO2 = 0;
			int strWidth = mc.fontRendererObj.getStringWidth(text);
			if(imgX > SBP.width/2) {
				aO=imgWidth;
				aO2 = strWidth;
			}
			
			mc.getTextureManager().bindTexture(border);
			ColorUtils.setColor(bgColorID);
			this.drawModalRectWithCustomSizedTexture(imgX+2+aO-aO2-2, imgY+1+16, 0, 0, strWidth+4, 12, 1, 1);
			ColorUtils.resetColor();
			
			Utils.drawString(color+text, imgX+2+aO, imgY+3+16, SettingGlobal.textStyle, true);
			String roomName = "Room: "+SecretUtils.prettifyRoomname(roomSecretsID);
			Utils.drawString(color+roomName, imgX+(imgWidth/2)-(mc.fontRendererObj.getStringWidth(roomName)/2), imgY-11+16);
			
			int credX = imgX+4;
			int credY = imgY+imgHeight+16+16;

			Utils.drawConfinedString(color+"Images by DungeonSecretGuide Discord Server", credX, credY, 0, imgWidth-1);
		} catch(Exception e) {}
		
		
		GlStateManager.enableBlend();
	}
	
	
}
