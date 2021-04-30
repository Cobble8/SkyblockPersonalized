package com.cobble.sbp.gui.menu.settings;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.cobble.sbp.core.SettingList;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.handlers.AnimationHandler;
import com.cobble.sbp.handlers.DownloadSecretsHandler;
import com.cobble.sbp.threads.misc.FixFadeThread;
import com.cobble.sbp.threads.misc.LaunchThread;
import com.cobble.sbp.utils.ColorUtils;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import scala.actors.threadpool.Arrays;

public class SettingMenu extends GuiScreen {

	//SETTING ARRAYS
	public static ArrayList<String> settingNames = new ArrayList();
	public static ArrayList<String> settingIDs = new ArrayList();
	public static ArrayList<Boolean> settingToggles = new ArrayList();
	public static ArrayList<String> settingDesc = new ArrayList();
	public static ArrayList<Boolean> settingOptions = new ArrayList();
	public static ArrayList<String> settingVersions = new ArrayList();
	private static ArrayList<String> searchPresets = new ArrayList();
	private static ArrayList<String> actualPresets = new ArrayList();
	public static ArrayList<String> themeNames = new ArrayList();

	
	//GLOBAL VARIABLES
	public static String searchSettingString = "";
	private static Boolean isSearchFocused = false;
	
	//SLIDER VARIABLES
	private static int sliderYValue = 0;
	private static int mouseDWheel = 0;
	
	//THEME VALUES
	public static int currTheme = 0;
	private static int currActualTheme = 0;
	private static final int totalThemes = 3;
	
	//MISC
	private static int totalLaunches = 0;
	private static int resetTextWidth = 0;
	private static String resetConfigCoords = "0,0,0,0";
	private static String confirmCancelCoords = "0,0,0,0";
	private static Boolean isConfirmOpen = false;
	private static int langWidth = 0;
	private static int FPS = 1;
	
	public static float fadeIn = 0;
	public static int fadeInFrames = 0;
	private static int onOffClickFrames = 0;
	
	public static int modLaunches = DataGetter.findInt("modLaunches");
	public static Boolean modLaunchToggle = DataGetter.findBool("modLaunchToggle");
	
	public static Boolean settingsMenuOpen = false;
	public static String currOptionName = "";
	int l = 0;
	
	private static Boolean mouseDown = false;
	private static int mouseDownX = 0;
	private static int mouseDownY = 0;
	private static int sliderOffset = 0;
	private static Double guiScale = 1.0;
	
	private static ArrayList<String> buttonList = new ArrayList();
	
	//UPDATES THE SEARCH FOR FEATURES
	public static void updateSearch() {
		SettingMoveAll.settingCoords.clear(); SettingMoveAll.settingNames.clear(); SettingMoveAll.settingIDs.clear(); SettingMoveAll.settingWidths.clear(); SettingMoveAll.settingHeights.clear();
		settingNames.clear(); settingIDs.clear(); settingToggles.clear();  settingDesc.clear(); settingOptions.clear(); settingVersions.clear();
		SettingOptions.settingOptions.clear(); searchPresets.clear(); actualPresets.clear();
		buttonList.clear();
		
		
		
		sliderYValue = 0;
		sliderOffset = 0;
		
		reloadValues();
		}
	
	public static void reloadValues() {
		SettingList.loadSettings();
		buttonList.add("Global Settings");
		buttonList.add("Open Config Folder");
		buttonList.add("Edit GUI Locations");
		buttonList.add("Reset Config");
		buttonList.add("Download DSG Images");
	}
	
	@Override
	public void initGui() {
		l=0;
		//isSearchFocused = true;
		mouseDown=false;
		
		settingsMenuOpen=true; onOffClickFrames = 0; //fadeIn = 0; fadeInFrames=0;
		
		updateSearch();
		new FixFadeThread().start();
		
		//CATEGORIES/UPDATE SEARCH
		totalLaunches = DataGetter.findInt("modLaunches");
		themeNames.add("Dark");
		themeNames.add("Light");
		themeNames.add("Time");
		
		
		resetTextBoxes();
		String srchStr = Colors.GRAY+searchSettingString;
		if(searchSettingString.isEmpty()) { srchStr = Colors.GRAY+"Search Features..."; }
		addTextBox(0, Colors.GRAY+searchSettingString, this.width/2-188+2, 15+125+2, 375-42, 1);
		
		
		
		SettingOptions.settingOptions.clear();
		SettingOptions.selectedOption=0;
		
		//THEMES
		updateTheme();
		currActualTheme = DataGetter.findInt("currentTheme");
		int randNum = Integer.parseInt(Math.round(Math.random()*250)+"");
		if(randNum == 1) { 
			int randTheme = Integer.parseInt(Math.round(Math.random()*totalThemes)+"");
			currActualTheme=(randTheme-1); 
			if(!(currActualTheme == currTheme)) {
				Utils.setEasterEgg(1, Colors.GOLD+"You seem to be incredibly lucky!");
				//Utils.sendMessage(Colors.GREEN+"You have found Easter Egg #1");
			}
		}
		else { currActualTheme= DataGetter.findInt("currentTheme"); }
		
		
		FPS = Minecraft.getDebugFPS();
		
		//RESET CONFIG COORDS
		String resetConfig = "Reset Config";
		int resetConfigWidth = fontRendererObj.getStringWidth(resetConfig)+8;
		resetConfigCoords = (this.width/2-188-20-resetConfigWidth)+","+(this.width/2-188-20)+","+(this.height-22-sliderYValue)+","+(this.height-22-sliderYValue+20);
		
		super.initGui();
	}
	
	//RESOURCES
	private static ResourceLocation logo = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/logo.png");
	private static ResourceLocation settingBg = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/settingBg.png");
	private static ResourceLocation gear = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/gear.png");
	private static ResourceLocation Switch = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/switch.png");
	public static ResourceLocation searchBar = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/searchBar.png");
	private static ResourceLocation suggestionBar = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/suggestionBar.png");
	private static ResourceLocation settingBorder = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/settingBorder.png");
	private static ResourceLocation info = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/info.png");
	private static ResourceLocation sliderBar = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/sliderBar.png");
	private static ResourceLocation slider = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/slider.png");
	private static ResourceLocation plusMinus = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/plusminus.png");
	private static ResourceLocation discord = new ResourceLocation(Reference.MODID, "textures/gui/discord.png");
	private static ResourceLocation github = new ResourceLocation(Reference.MODID, "textures/gui/github.png");
	public static ResourceLocation blank = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		//guiScale=0.5;
		GlStateManager.pushMatrix();
		//GlStateManager.scale(guiScale, guiScale, guiScale);
		
		int sliderHeight = settingNames.size();
		int settingNumMinus = sliderHeight%3;
		if(settingNumMinus==0) {settingNumMinus=3;}
		sliderHeight = (sliderHeight-settingNumMinus)*68/3;
		
		/*(68/3);
		
		if(sliderHeight > 68) {
			sliderHeight-=45;
			//sliderHeight+=68;
			if(settingNames.size() % 3 == 1) { sliderHeight+=23;
			} else if(settingNames.size() % 3 == 0) { sliderHeight-=23; }
			
		}*/
		GlStateManager.color(1, 1, 1, 0.8F);
		drawTextBox(0, searchBar, 20);
		//updateTextBox(0, "", 5, 20);
		//Utils.print(settingNames.size());
		if(settingNames.size()<=3) { sliderHeight=0; }
		
		//SCROLL WHEEL
		if(sliderHeight > 0 && sliderHeight > this.height-(15+125+2)) {
			if(!isConfirmOpen) { if(mouseDWheel != 0) {if(mouseDWheel > 0) {sliderYValue-=15;} else {sliderYValue+=15;}} mouseDWheel = Mouse.getDWheel(); }
			//MAKE SURE SLIDER IS ACTUALLY ON BAR
			if(sliderYValue<0) sliderYValue=0;
			else if(sliderYValue>sliderHeight) sliderYValue=sliderHeight;
		}
		
		
		
		if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
			if(!mouseDown) {
				mouseDownX = mouseX;
				mouseDownY = mouseY;
			}
			mouseDown=true;
		} else {
			mouseDown=false;
			mouseDownX=0;
			mouseDownY=0;
		}
		
		
		
		l++;
		if(l == 1) {
			DataGetter.updateConfig("main");
		} else if(l >= Minecraft.getDebugFPS()*5) {
			l=0;
		}
		
		//Utils.print(fadeIn);
		onOffClickFrames++;
		//currTheme = 2;
		if(currActualTheme == 2) {
			World world = Minecraft.getMinecraft().theWorld;
			long worldTime = world.getWorldTime();
			while(worldTime > 24000) {
				worldTime-=24000;
			}
			if(worldTime > 12500) { if(currTheme != 1) { currTheme = 1; if(currTheme < 0) { currTheme = 0; } updateTheme(); } } else { if(currTheme != 0) { currTheme = 0; if(currTheme < 0) { currTheme = 0; } updateTheme(); } }
			
		} else { if(currTheme != currActualTheme) { currTheme = currActualTheme; updateTheme(); }
			
			
		}
		
		String textColor = Colors.DARK_BLUE;
		if(currTheme == 0) {
			textColor = Colors.WHITE;
		}
		
		
		//FADE IN
		/*
		fadeInFrames++;
		if(fadeIn < 1 && fadeIn > -0.01) {
			if(fadeInFrames == (FPS/50)) {
				fadeIn = (float) (Double.parseDouble((fadeIn+0.05)+""));
				DecimalFormat df = new DecimalFormat("#.##");
				fadeIn = Float.parseFloat(df.format(fadeIn).replace(",", "."));
				
				fadeInFrames=0;
			}
		} else if(0 > fadeIn) {
			fadeIn = 0;
		} else if(fadeIn >= 1.0) {
			fadeIn = 1;
		}
		else {
			fadeIn = 1;
		}*/
		fadeIn=1;
		//Utils.print(fadeIn);
		//GlStateManager.color(1, 1, 1, 1);
		
		
		
		
		GlStateManager.enableBlend();
		String rawGetText = Utils.unformatAllText(getText(0));
		if(!rawGetText.equals("Search Features...") || rawGetText.isEmpty()) {
			
			
			if(!searchSettingString.equals(rawGetText)) {
				searchSettingString = rawGetText;
				updateSearch();
			}
			
		}
		if(searchSettingString.isEmpty()) {
			if(textBoxClicked !=0) {
				updateTextBox(0, Colors.GRAY+"Search Features...", getX(0), getY(0));
			} else { updateTextBox(0, Colors.WHITE+searchSettingString, getX(0), getY(0)); }
		} else {
			if(textBoxClicked != 0) { updateTextBox(0, Colors.GRAY+rawGetText, getX(0), getY(0));
			} else { updateTextBox(0, Colors.WHITE+rawGetText, getX(0), getY(0)); }
		}
		
		
		
		
		
		
		//DISPLAY MOD LAUNCHES
		if(modLaunchToggle) {
			
			String launchCount = Colors.GREEN+"You have launched Minecraft with this mod "+Colors.AQUA+modLaunches+Colors.GREEN+" times!";
			int launchCountWidth = fontRendererObj.getStringWidth(launchCount);
			//
			mc.getTextureManager().bindTexture(settingBg);
			
			try { GlStateManager.color(1, 1, 1, fadeIn-0.3F); } catch(Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
			this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, launchCountWidth+6, 16, 1, 1);
			GlStateManager.color(1, 1, 1, fadeIn);
			
			mc.fontRendererObj.drawString(launchCount, 3, 4, 0x10, true);
		
		}

		
		//DRAW PLAYER CHARACTER
		int posX = this.width/2-250;
		int posY = 138-4;
		int scale = 40;
		GlStateManager.enableBlend();
		
		try { GlStateManager.color(1, 1, 1, fadeIn-0.3F); } catch(Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		mc.getTextureManager().bindTexture(settingBg);
		
		//posX-32
		//posY-90
		
		this.drawModalRectWithCustomSizedTexture(posX-32, posY-90, 0, 0, 33, 33, 92, 55);
		this.drawModalRectWithCustomSizedTexture(posX-32+66-33, posY-90, 92-33, 0, 33, 33, 92, 55);
		this.drawModalRectWithCustomSizedTexture(posX-32, posY+4-33, 0, 22, 33, 33, 92, 55);
		this.drawModalRectWithCustomSizedTexture(posX-32+66-33, posY+4-33, 92-33, 22, 33, 33, 92, 55);
		this.drawModalRectWithCustomSizedTexture(posX-32, posY-90+33, 0, 16, 33, 28, 92, 55);
		this.drawModalRectWithCustomSizedTexture(posX-32+33, posY-90+33, 92-33, 16, 33, 28, 92, 55);
		
		mc.getTextureManager().bindTexture(settingBorder);
		this.drawModalRectWithCustomSizedTexture(posX-32, posY-94+4, 0,0, 66, 2, 370, 223);
		this.drawModalRectWithCustomSizedTexture(posX-32, posY-94+4+2, 0,0, 2, 92, 370, 223);
		this.drawModalRectWithCustomSizedTexture(posX-32+64, posY-94+4+2, 0,0, 2, 92, 370, 223);
		this.drawModalRectWithCustomSizedTexture(posX-30, posY+2, 0,0, 62, 2, 370, 223);
		
		//this.drawModalRectWithCustomSizedTexture(posX-32, posY+4-32, 0, 24, 32, 32, 92, 55);
		//this.drawModalRectWithCustomSizedTexture(posX-32+70-32, posY+4-32, 92-32, 24, 32, 32, 92, 55);
		//this.drawModalRectWithCustomSizedTexture(posX-32, posY-90, 0, 0, 30, 30, 92, 55);
		/*
		this.drawModalRectWithCustomSizedTexture(posX-32, posY-94+4, 0,0, 64, 96, 370, 223);
		*/
		
		//GlStateManager.enableBlend();
		
		if(DownloadSecretsHandler.running) {
			String progress = DownloadSecretsHandler.currText;//"Images Downloaded: "+Colors.YELLOW+DownloadSecretsHandler.progress+"/"+DownloadSecretsHandler.total;
			ArrayList<String> prog = new ArrayList();
			prog.add("");
			prog.add(Colors.AQUA+progress);
			try {
				
				int barProg = (DownloadSecretsHandler.progress*100)/(DownloadSecretsHandler.total)*3/4;
				String bar = Colors.GREEN;
				for(int a=0;a<barProg;a++) {
					bar+="|";
				}
				bar+=Colors.GRAY;
				for(int a=0;a<(75-barProg);a++) {
					bar+="|";
				}
				prog.add(bar);
			} catch(Exception e) {
				prog.add(Colors.GRAY+"|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
			}
			try {

				
				prog.add(Colors.YELLOW+"Time Elapsed: "+Colors.AQUA+Utils.secondsToTime(DownloadSecretsHandler.timeElapsed));
			} catch(Exception e) {}
			
			prog.add("");
			Utils.drawString(prog, 4, 20, 3);
		}
		
		//GENERAL TEXTURE ELEMENTS
		GlStateManager.enableBlend();
		//LOGO
		GlStateManager.color(1, 1, 1, fadeIn);
		mc.getTextureManager().bindTexture(logo);
		this.drawModalRectWithCustomSizedTexture(this.width/2-200, 17+22, 0, 0, 400, 100, 400, 100);
		mc.fontRendererObj.drawString(Colors.YELLOW+"Version: "+Colors.AQUA+Reference.VERSION, this.width/2+194-(mc.fontRendererObj.getStringWidth("Version: "+Reference.VERSION)), 17+22+100-14-11, 0x1f99fa, false);
		mc.fontRendererObj.drawString(Colors.YELLOW+"Made by "+Colors.RESET+"Cobble8", this.width/2+194-(mc.fontRendererObj.getStringWidth("Made by Cobble8")), 17+22+100-14, 0x1f99fa, false);
		
		
		
		//DISCORD AND GITHUB
		String hoveringText = "";
		if(mouseX >= this.width/2+200-20-17 && mouseX <= this.width/2+200-20-17+16 && mouseY >= 17+22+13-8 && mouseY <= 17+22+13-8+16) {
			GlStateManager.color(1, 1, 1, 1); hoveringText = "discord";
		} else {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.2F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		}
		mc.getTextureManager().bindTexture(discord);
		this.drawModalRectWithCustomSizedTexture(this.width/2+200-20-17, 17+22+13-8, 0, 0, 16, 16, 16, 16);
		
		
		if(mouseX >= this.width/2+200-20 && mouseX <= this.width/2+200-20+16 && mouseY >= 17+22+13-8 && mouseY <= 17+22+13-8+16) {
			GlStateManager.color(1, 1, 1, 1); hoveringText = "github";
		} else {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.2F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		}
		mc.getTextureManager().bindTexture(github);
		this.drawModalRectWithCustomSizedTexture(this.width/2+200-20, 17+22+13-8, 0, 0, 16, 16, 16, 16);
		
		
		
		//SLIDER BAR
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		mc.getTextureManager().bindTexture(sliderBar);
		this.drawModalRectWithCustomSizedTexture(this.width/2+210, 15+125, 0, 0, 25, 302, 25, 302);
		
		
		
		//THEMES
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		if(mouseX >= this.width/2-308 && mouseX <= this.width/2-308+20 && mouseY >= 142 && mouseY <= 142+20) { GlStateManager.color(1, 1, 1, 1); }
		mc.getTextureManager().bindTexture(plusMinus);
		this.drawModalRectWithCustomSizedTexture(this.width/2-308, 142, 0, 0, 20, 20, 118, 20);
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		
		if(mouseX >= this.width/2-308+80 && mouseX <= this.width/2-308+100 && mouseY >= 142 && mouseY <= 142+20) { GlStateManager.color(1, 1, 1, 1); }
		this.drawModalRectWithCustomSizedTexture(this.width/2-308+80, 142, 80, 0, 20, 20, 118, 20);
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		this.drawModalRectWithCustomSizedTexture(this.width/2-308+20, 142, 20, 0, 60, 20, 118, 20);
		this.drawModalRectWithCustomSizedTexture(this.width/2-308+100, 142, 100, 0, 18, 20, 118, 20);
		int themeWidth = fontRendererObj.getStringWidth(themeNames.get(currActualTheme)+" Theme");

		
		mc.getTextureManager().bindTexture(searchBar);
		
		
		//SEARCH TEXT
		/*try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		this.drawModalRectWithCustomSizedTexture(w/2-188, 15+125+2, 0, 0, 1, 20, 375, 20);
		for(int i=0;i<searchTextWidth;i++) {
			this.drawModalRectWithCustomSizedTexture(w/2-188+1+i, 15+125+2, 1, 0, 1, 20, 375, 20);
		}
		this.drawModalRectWithCustomSizedTexture(w/2-188+searchTextWidth+1, 15+125+2, 0, 0, 1, 20, 375, 20);
		mc.fontRendererObj.drawString(textColor+"Search:", w/2-188+3, 15+125+8, 0x10, false);*/
		
		/*//RESET
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		
		if(mouseX >= w/2-188+374-resetTextWidth-1 && mouseX <= w/2-188+374 && mouseY >= 15+125+2 && mouseY <= 15+125+2+20) {
			GlStateManager.color(1, 1, 1, 1);
		}
		
		
		mc.getTextureManager().bindTexture(searchBar);
		this.drawModalRectWithCustomSizedTexture(w/2-188+374, 15+125+2, 0, 0, 1, 20, 375, 20);
		for(int i=0;i<resetTextWidth;i++) {
			this.drawModalRectWithCustomSizedTexture(w/2-188+373-i, 15+125+2, 333, 0, 1, 20, 375, 20);
		}
		this.drawModalRectWithCustomSizedTexture(w/2-188+374-resetTextWidth-1, 15+125+2, 0, 0, 1, 20, 375, 20);
		mc.fontRendererObj.drawString(textColor+"Reset", w/2-188+375-resetTextWidth+5, 15+125+8, 0x10, false);
		GlStateManager.color(1, 1, 1, fadeIn);*/
		
		//SEARCH BAR

		int searchTextWidth = 0;
		int searchBarWidth = 0;
		if(isSearchFocused || (mouseX >= this.width/2-188+2 && mouseX <= this.width/2-188+2+375-43 && mouseY >= 15+125+2 && mouseY <= 15+125+2+20)) {
			GlStateManager.color(1, 1, 1, 1);
		} else {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.1F);
			} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		}
		
		mc.getTextureManager().bindTexture(searchBar);
		//this.drawModalRectWithCustomSizedTexture(this.width/2-188+2, 15+125+2, 0, 0, 375-43, 20, 375, 20);
		
		
		//IF TEXTBOX IS SELECTED
		String selectedIndicator = "";
		int searchConfine = 320;
		
		
		//RESET BUTTON
		if(mouseX >= this.width/2-188+2+375-43 && mouseX <= this.width/2-188+2+375 && mouseY >= 15+125+2 && mouseY <= 15+125+2+20) {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.1F);
			} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		} else {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
			} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		}
		this.drawModalRectWithCustomSizedTexture(this.width/2-188+2+375-43, 15+125+2, 332, 0, 43, 20, 375, 20);
		
		
		//DRAW SEARCH TEXT
		if(isSearchFocused) {
			if(AnimationHandler.mainAnim < 250 || (AnimationHandler.mainAnim > 500 && AnimationHandler.mainAnim < 750)) {
				selectedIndicator="_";
			} else {
				searchConfine = 314;
			}
			//Utils.drawConfinedString(textColor+searchSettingString+selectedIndicator, this.width/2-188+6, 15+125+8, 0, searchConfine);
		} else {
			String srchClr = Colors.GRAY;
			if(mc.fontRendererObj.getStringWidth(searchSettingString) <= 0) {
				
				//Utils.drawString(srchClr+"Search Features...", this.width/2-188+6, 15+125+8, 0);
			} else {
				//Utils.drawString(srchClr+searchSettingString, this.width/2-188+6, 15+125+8, 0);
			}
		}
		Utils.drawString(Colors.WHITE+"Clear", this.width/2-188+3+375-36, 15+125+8, 0);
		
		
		
		try {
				
				
				if(mouseDownX >= this.width/2+210 && mouseDownX <= this.width/2+235 && mouseDownY >= 140 && mouseDownY <= 140+302 && mouseDown && sliderHeight > this.height-(15+125+2) && !isConfirmOpen) {
					int settingCount = settingNames.size()/3;
					
					int mouseYOffset = 442-(mouseY-20);
					if(mouseYOffset < 0) {mouseYOffset = 0;} else if(mouseYOffset > 302) {mouseYOffset = 302;} mouseYOffset = 302-mouseYOffset;
					int mousePercent = 1000*mouseYOffset/(302-40);
					sliderOffset = (302-40)*mousePercent/1000;
					if(sliderOffset > (302-40)) {sliderOffset = 302-40;}
					sliderYValue = (68*settingCount)*mousePercent/1000;
				} else {
					int settingCount = settingNames.size()/3;
					if(!(settingCount % 3 == 0)) {settingCount++;}
					int totalSettingHeight = settingCount*68;
					int percentDown = 1000*sliderYValue/totalSettingHeight;
					sliderOffset = (302-40)*percentDown/1000;
					if(sliderOffset >= 302-40) {sliderOffset = 302-40;}

					
					try { GlStateManager.color(1, 1, 1, fadeIn-0.4F);
					} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
				}
				
					//SLIDER
					
					mc.getTextureManager().bindTexture(slider);
					
					this.drawModalRectWithCustomSizedTexture(this.width/2+210, 140+(sliderOffset), 0, 0, 25, 40, 25, 40);
					
					
				} catch(Exception e) { mc.getTextureManager().bindTexture(slider); this.drawModalRectWithCustomSizedTexture(this.width/2+210, 140+(sliderOffset), 0, 0, 25, 40, 25, 40);}
				//Utils.print(sliderHeight);
		//PRESET SEARCHES
		int lastButton = 0;
		for(int i=0;i<searchPresets.size();i++) {
			try { GlStateManager.color(0.8F, 0.8F, 0.8F, fadeIn-0.2F);
			} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
			if(mouseX >= this.width/2-188-120 && mouseX <= this.width/2-188-120+100 && mouseY >= 166+(i*23) && mouseY <= 164+(i*23)+23 && i !=0) {
				GlStateManager.color(1, 1, 1, fadeIn);
			}
			
			mc.getTextureManager().bindTexture(suggestionBar);
			this.drawModalRectWithCustomSizedTexture(this.width/2-188-120, 164+(i*23), 0, 0, 100, 25, 100, 25);
			mc.fontRendererObj.drawString(textColor+searchPresets.get(i), this.width/2-188-120+4, 164+(i*23)+8, 0x10, false);
			lastButton = 164+(i*23)+4+23;
		}
		
		for(int i=0;i<buttonList.size();i++) {
			drawButton(textColor+buttonList.get(i), this.width/2-188-20, lastButton+(i*22), "right", mouseX, mouseY);
		}
		
		mc.fontRendererObj.drawString(textColor+themeNames.get(currActualTheme)+" Theme", this.width/2-308+50-(themeWidth/2), 15+125+8, 0x10, false);
		mc.fontRendererObj.drawString(textColor+(currActualTheme+1), this.width/2-308+109-(fontRendererObj.getStringWidth(currTheme+"")/2), 15+125+8, 0x10, false);
		//w/2-308, 120-sliderYValue
		

		
		
		
		//SETTING BOXES
		int x=0;
		int y=0;
		for(int i=0;i<settingNames.size();i++) {
			int toggleSwitch = 0;
			
			//SETTING BACKGROUNDS
			
			
			int currYPos = y*68+164-sliderYValue;
			if(currYPos > this.height) { break; }
			
			else if(currYPos > 163) {
				
			
			
				//GlStateManager.enableBlend();
				try { GlStateManager.color(1, 1, 1, fadeIn-0.4F);
				} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
				mc.getTextureManager().bindTexture(settingBg);
			this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198), currYPos, 0,0, 128, 64, 128, 64);
			GlStateManager.color(1, 1, 1, fadeIn);
			
			//SETTING OPTIONS
			if(settingOptions.get(i)) {
				mc.getTextureManager().bindTexture(gear);
				
				if(mouseX >= x*135+(this.width/2-198)+96 && mouseX <= x*135+(this.width/2-198)+96+24 && mouseY >= y*68+184-sliderYValue && mouseY <= y*68+184+24-sliderYValue && settingOptions.get(i)) {
				GlStateManager.color(1, 1, 1, fadeIn); } else { try { GlStateManager.color(1, 1, 1, fadeIn-0.2F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); } }
				
				this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-198+96, (y*68)+184-sliderYValue, 0, 0, 24, 24, 24, 24);
				
			}
			
			
			//SETTING INFO
			mc.getTextureManager().bindTexture(info);
			if(mouseX >= x*135+(this.width/2)-130 && mouseX <= x*135+(this.width/2)-130+24 && mouseY >= (y*68)+184-sliderYValue && mouseY <= (y*68)+184+24-sliderYValue) {
				GlStateManager.color(1, 1, 1, fadeIn);

			} else {
				try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
				} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
			}
			this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-130, (y*68)+184-sliderYValue, 0, 0, 24, 24, 24, 24);
			GlStateManager.color(1, 1, 1, fadeIn);
			
			//SETTING TOGGLE
			mc.getTextureManager().bindTexture(Switch);
			if(!settingToggles.get(i)) {toggleSwitch+=20;}
			this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198)+40-20, y*68+164+22-sliderYValue, 0, toggleSwitch, 40, 20, 40, 40);
			
			//SETTING NAMES
			Utils.drawConfinedString(Colors.YELLOW+settingNames.get(i)+Colors.WHITE, x*135+(this.width/2-198)+6, y*68+164+6-sliderYValue, 0, 116);
			
			//SETTING BORDERS
			mc.getTextureManager().bindTexture(settingBorder);
			this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198), y*68+164-sliderYValue, 0,0, 128, 64, 128, 64);
			
			String redBoxString = "";
			
			if(settingVersions.get(i).equals(Reference.VERSION)) { redBoxString="New!"; }
			if(ConfigHandler.forceDisabled.toString().contains(settingIDs.get(i))) { redBoxString="Force Disabled!"; }
			else if(ConfigHandler.forceEnabled.toString().contains(settingIDs.get(i))) { redBoxString="Force Enabled!"; }
			
			if(!(redBoxString.isEmpty())) { drawRedBox(redBoxString, x*135+(this.width/2-198)+4, y*68+164-sliderYValue+46); }
			
			} else {
				
				//Utils.drawString(Colors.CHROMA+"--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", 0, 161);
				
				int yOffset = currYPos+64-164;
				if(yOffset > 0 && yOffset <164+68) {
					//Utils.drawString(Colors.GOLD+yOffset, 10, 30);
					//163, 0, 64-yOffset, 128, yOffset, 128, 64
					
					GlStateManager.enableBlend();
					try { GlStateManager.color(1, 1, 1, fadeIn-0.4F);
					} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
					mc.getTextureManager().bindTexture(settingBg);
					this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198), 164, 0, 64-yOffset, 128, yOffset, 128, 64);
				
					GlStateManager.color(1, 1, 1, fadeIn);
					mc.getTextureManager().bindTexture(settingBorder);
					this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198), 164, 0, 64-yOffset, 128, yOffset, 128, 64);
					
					
					
					//SETTING INFO
					mc.getTextureManager().bindTexture(info);
					if(mouseX >= x*135+(this.width/2)-130 && mouseX <= x*135+(this.width/2)-130+24 && mouseY >= (y*68)+184-sliderYValue && mouseY <= (y*68)+184+24-sliderYValue && mouseY >= 164) {
					GlStateManager.color(1, 1, 1, fadeIn); } else { try { GlStateManager.color(1, 1, 1, fadeIn-0.2F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); } }
						
						if(yOffset > 20 && yOffset < 44) {
							int infoOffset = 164-((y*68)+184-sliderYValue);
							this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-130, (y*68)+184-sliderYValue+infoOffset, 0, infoOffset, 24, 24-infoOffset, 24, 24);
							GlStateManager.color(1, 1, 1, fadeIn);
						} else if(yOffset > 20 ) {
							this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-130, (y*68)+184-sliderYValue, 0, 0, 24, 24, 24, 24);
							GlStateManager.color(1, 1, 1, fadeIn);
						}
						
						
						if(settingOptions.get(i)) { mc.getTextureManager().bindTexture(gear);
							if(mouseX >= x*135+(this.width/2-198)+96 && mouseX <= x*135+(this.width/2-198)+96+24 && mouseY >= y*68+184-sliderYValue && mouseY <= y*68+184+24-sliderYValue && settingOptions.get(i) && mouseY >= 164) {
							GlStateManager.color(1, 1, 1, fadeIn); } else { try { GlStateManager.color(1, 1, 1, fadeIn-0.2F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); } }
							//
							if(yOffset > 20 && yOffset < 44) {
								int infoOffset = 164-((y*68)+184-sliderYValue);
								this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-198+96, (y*68)+184-sliderYValue+infoOffset, 0, infoOffset, 24, 24-infoOffset, 24, 24);
								GlStateManager.color(1, 1, 1, fadeIn);
							} else if(yOffset > 20 ) {
								this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-198+96, (y*68)+184-sliderYValue, 0, 0, 24, 24, 24, 24);
								GlStateManager.color(1, 1, 1, fadeIn);
							} }
						
						
						mc.getTextureManager().bindTexture(Switch);
						if(!settingToggles.get(i)) {toggleSwitch+=20;}
						if(yOffset > 20 && yOffset < 44) {
							int infoOffset = 164-((y*68)+184-sliderYValue+2);
							this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198)+40-20, (y*68)+184-sliderYValue+infoOffset+2, 0, infoOffset+toggleSwitch, 40, 20-infoOffset, 40, 40);
							GlStateManager.color(1, 1, 1, fadeIn);
						} else if(yOffset > 20 ) {
							this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198)+40-20, (y*68)+184-sliderYValue+2, 0, toggleSwitch, 40, 20, 40, 40);
							GlStateManager.color(1, 1, 1, fadeIn);
						}
					
						mc.getTextureManager().bindTexture(settingBorder);
						this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198), 164, 0, 0, 128, 2, 128, 64);
						int infoOffset = 164-((y*68)+184-sliderYValue+2);
						
						if((y*68)+184-sliderYValue+30 > 166) { Utils.drawConfinedString(Colors.YELLOW+settingNames.get(i)+Colors.WHITE, x*135+(this.width/2-198)+6, (y*68)+184-sliderYValue+30, 0, 116);}
						
				}
				
			}
			//SETTING FORMATTER
			x++;
			if(x == 3) {
				x=0;y++;
			}
			
			
		}
		
		//SETTING INFO HOVER DISPLAY
		if(!isConfirmOpen) {
			int x2=0;
			int y2=0;
			for(int i=0;i<settingNames.size();i++) {
				if(mouseX >= x2*135+(this.width/2)-130 && mouseX <= x2*135+(this.width/2)-130+24 && mouseY >= (y2*68)+184-sliderYValue && mouseY <= (y2*68)+184+24-sliderYValue && mouseY >= 164) {

						ArrayList<String> hoverText = new ArrayList();
						hoverText.add(settingDesc.get(i));
						hoverText.add(Colors.YELLOW+"Last updated in version: "+Colors.AQUA+settingVersions.get(i));
						this.drawHoveringText(hoverText, mouseX, mouseY);
					
				}
				x2++;
				if(x2 == 3) {
					x2=0;y2++;
				}
			}
		}
		
		
		//CONFIRM RESET CONFIG MENU
		else if(isConfirmOpen) {
			
			//CONFIRM STRING
			String confirmResetText = Colors.BOLD+"Are you sure you want to reset the entire config?";
			int confirmResetWidth = fontRendererObj.getStringWidth(confirmResetText);
			
			//SHADED BACKGROUND
			mc.getTextureManager().bindTexture(settingBorder);
			GlStateManager.enableBlend();
			GlStateManager.color(1, 1, 1, 0.8F);
			this.drawModalRectWithCustomSizedTexture(this.width/2-(confirmResetWidth/2)-10, this.height/2-32, 0, 0, confirmResetWidth+20, 60, 0, 0);
			GlStateManager.color(1, 1, 1, 0.8F);
			mc.getTextureManager().bindTexture(searchBar);
			
			//CONFIRM RESET ASK BOX
			this.drawModalRectWithCustomSizedTexture((this.width/2)-(confirmResetWidth/2)-4, this.height/2-22, 0, 0, 1, 20, 375, 20);
			for(int i=0;i<confirmResetWidth+8;i++) {
				this.drawModalRectWithCustomSizedTexture((this.width/2)-(confirmResetWidth/2)-3+i, this.height/2-22, 45, 0, 1, 20, 375, 20);
			}
			this.drawModalRectWithCustomSizedTexture((this.width/2)+(confirmResetWidth/2)+5, this.height/2-22, 0, 0, 1, 20, 375, 20);
			String[] tmp = confirmCancelCoords.split(",");
			int y1 = Integer.parseInt(tmp[0]);
			int y2 = Integer.parseInt(tmp[1]);
			int n1 = Integer.parseInt(tmp[2]);
			int n2 = Integer.parseInt(tmp[3]);

				
			if(mouseX >= y1 && mouseX <= y2 && mouseY >= this.height/2-1 && mouseY <= this.height/2-1+20) {
					GlStateManager.color(1, 1, 1, 1);
			} 
			//CONFIRM BOX
			String confirmText = Colors.BOLD+"Confirm";
			int confirmWidth = fontRendererObj.getStringWidth(confirmText);
			
			this.drawModalRectWithCustomSizedTexture((this.width/2)-2, this.height/2-1, 0, 0, 1, 20, 375, 20);
			for(int i=0;i<confirmWidth+8;i++) {
				this.drawModalRectWithCustomSizedTexture((this.width/2)-3-i, this.height/2-1, 45, 0, 1, 20, 375, 20);
			}
			this.drawModalRectWithCustomSizedTexture((this.width/2)-confirmWidth-11, this.height/2-1, 0, 0, 1, 20, 375, 20);
			
			
			
			if(mouseX >= n1 && mouseX <= n2 && mouseY >= this.height/2-1 && mouseY <= this.height/2-1+20) {
				GlStateManager.color(1, 1, 1, 1);
			} else {
				GlStateManager.color(1, 1, 1, 0.8F);
			}
			//CANCEL BOX
			String cancelText = Colors.BOLD+"Cancel";
			int cancelWidth = fontRendererObj.getStringWidth(cancelText);
			
			this.drawModalRectWithCustomSizedTexture((this.width/2)+2, this.height/2-1, 0, 0, 1, 20, 375, 20);
			for(int i=0;i<cancelWidth+8;i++) {
				this.drawModalRectWithCustomSizedTexture((this.width/2)+3+i, this.height/2-1, 45, 0, 1, 20, 375, 20);
			}
			this.drawModalRectWithCustomSizedTexture((this.width/2)+cancelWidth+11, this.height/2-1, 0, 0, 1, 20, 375, 20);
			
			String confirmCoordsString = ((this.width/2)-confirmWidth-11)+","+((this.width/2)-2);
			String cancelCoordsString = ((this.width/2)+2)+","+((this.width/2)+cancelWidth+11);
			confirmCancelCoords = confirmCoordsString+","+cancelCoordsString;
			
			//TEXT FOR BOXES
			mc.fontRendererObj.drawString(textColor+confirmText, ((this.width/2)-confirmWidth-6), this.height/2+5, 0x10, false);
			mc.fontRendererObj.drawString(textColor+cancelText, (this.width/2)+7, this.height/2+5, 0x10, false);
			mc.fontRendererObj.drawString(textColor+confirmResetText, (this.width/2)-(confirmResetWidth/2), this.height/2-17, 0x10, false);
		}
		
		
		
		if(hoveringText.equals("discord")) {
			ArrayList<String> discordArrayList = new ArrayList();
			discordArrayList.add(Colors.BLUE+"Discord:");
			discordArrayList.add(Colors.YELLOW+"https://discord.gg/QXA3y5EbNA"+Colors.WHITE);
			this.drawHoveringText(discordArrayList, mouseX, mouseY);
			GlStateManager.enableBlend();
		} else if(hoveringText.equals("github")) {
			ArrayList<String> githubArrayList = new ArrayList();
			githubArrayList.add(Colors.GOLD+"Github:");
			githubArrayList.add(Colors.YELLOW+"https://github.com/Cobble8/SkyblockPersonalized"+Colors.WHITE);
			this.drawHoveringText(githubArrayList, mouseX, mouseY);
			GlStateManager.enableBlend();
		}

		Utils.renderPlayer(posX, posY-5, scale, mouseX, mouseY);
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		GlStateManager.popMatrix();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		//PREVENT DOING THINGS WHILE CONFIRM MENU IS OPEN
		if(!isConfirmOpen) {
			textBoxClick(mouseX, mouseY);
			String[] rCT = resetConfigCoords.split(",");
			int x1 = Integer.parseInt(rCT[0]);
			int x2 = Integer.parseInt(rCT[1]);
			int y1 = Integer.parseInt(rCT[2]);
			int y2 = Integer.parseInt(rCT[3]);
			
			//RESET CONFIG BUTTON
			if(mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2) {
				Utils.playClickSound();
				isConfirmOpen=true;
				updateSearch();
				return;
			}
			//this.width/2+200-20-17   17+22+13-8
			//Github n Discord
			//Utils.sendMessage(mouseX+", "+mouseY);
			//Utils.sendMessage((this.width/2+200-20-17)+", "+(this.width/2+200-20-17+16));
			if(mouseX >= this.width/2+200-20-17 && mouseX <= this.width/2+200-20-17+16 && mouseY >= 17+22+13-8 && mouseY <= 17+22+13-8+16) {
				Utils.playClickSound();
				Utils.openURL("https://discord.gg/QXA3y5EbNA");
			}
			else if(mouseX >= this.width/2+200-20 && mouseX <= this.width/2+200-20+16 && mouseY >= 17+22+13-8 && mouseY <= 17+22+13-8+16) {
				Utils.playClickSound();
				Utils.openURL("https://github.com/Cobble8/SkyblockPersonalized");
			}

			//THEME DOWN BUTTON
			else if(mouseX >= this.width/2-308 && mouseX <= this.width/2-308+20 && mouseY >= 142 && mouseY <= 142+20) {
				if(currActualTheme > 0) {
					currTheme--;
					currActualTheme--;
					
					ConfigHandler.newObject("currentTheme", currActualTheme);
					updateTheme();
					SettingGlobal.updateTheme();
				}
				Utils.playClickSound();
			}
			
			//THEME UP BUTTON
			else if(mouseX >= this.width/2-308+80 && mouseX <= this.width/2-308+20+80 && mouseY >= 142 && mouseY <= 142+20) {
				

				if(currActualTheme < totalThemes-1) {
					currTheme++;
					currActualTheme++;
					ConfigHandler.newObject("currentTheme", currActualTheme);
					updateTheme();
					SettingGlobal.updateTheme();
				}

				Utils.playClickSound();
			}
			
			//CHECK IF USER IS SEARCHING
			//if(mouseX >= this.width/2-188+2 && mouseX <= this.width/2-188+2+375-43 && mouseY >= 15+125+2 && mouseY <= 15+125+2+20) {
				//isSearchFocused=true;
				//Utils.playClickSound();
			//} else { isSearchFocused=false; }
			
			//CLEAR SEARCH
			if(mouseX >= this.width/2-188+2+375-43 && mouseX <= this.width/2-188+2+375 && mouseY >= 15+125+2 && mouseY <= 15+125+2+20) {
				searchSettingString="";
				updateTextBox(0, "", getX(0), getY(0));
				Utils.playClickSound();
				updateSearch();
				return;
			}
			
			//PRESET SEARCH ACTIONS
			int bottomY = 0;
			for(int i=1;i<searchPresets.size();i++) {
				if(mouseX >= this.width/2-188-120 && mouseX <= this.width/2-188-120+100 && mouseY >= 164+(i*23) && mouseY <= 164+(i*23)+25) {
					searchSettingString=actualPresets.get(i);
					updateTextBox(0, searchSettingString, getX(0), getY(0));
					Utils.playClickSound();
					updateSearch();
					return;
				}
				//bottomY =  164+(i*23);
				bottomY = 164+(i*23)+4+23;
			}
			
			//Button Clicking
			for(int j=0;j<buttonList.size();j++) {
				String buttonName = buttonList.get(j);
				int buttonWidth = mc.fontRendererObj.getStringWidth(buttonName);
				
				
				if(mouseX >= this.width/2-188-20-buttonWidth-8 && mouseX <= this.width/2-188-20 && mouseY >= bottomY+(j*22) && mouseY <= bottomY+(j*22)+20) {
					Utils.playClickSound();
					buttonClickEvent(j);
					//
				}
			}
			
			//SETTING CLICK FUNCTIONS
			int x=0;
			int y=0;
			for(int i=0;i<settingNames.size();i++) {
				
				//SWITCH CLICK EVENT
				if(mouseX >= x*135+(this.width/2-198)+40-20 && mouseX <= x*135+(this.width/2-198)+80-20 && mouseY >= y*68+164+22-sliderYValue && mouseY <= y*68+164+22+20-sliderYValue && mouseY >= 164) {
					for(String str : ConfigHandler.forceDisabled) {
						if(str.equals(settingIDs.get(i))) {
							ConfigHandler.newObject(settingIDs.get(i), false);
							settingToggles.set(i, false);
							Utils.sendMessage("This setting has been forcefully disabled by a Developer!");
							return;
						}
					}
					for(String str : ConfigHandler.forceEnabled) {
						if(str.equals(settingIDs.get(i))) {
							ConfigHandler.newObject(settingIDs.get(i), true);
							settingToggles.set(i, true);
							Utils.sendMessage("This setting has been forcefully enabled by a Developer!");
							return;
						}
					}
					settingToggles.set(i, Utils.invertBoolean(settingToggles.get(i)));
					ConfigHandler.newObject(settingIDs.get(i), settingToggles.get(i));
					Utils.playClickSound();
					//updateSearch();
					return;
				}
				
				//OPTIONS CLICK EVENT
				else if(mouseX >= x*135+(this.width/2-198)+96 && mouseX <= x*135+(this.width/2-198)+96+24 && mouseY >= y*68+184-sliderYValue && mouseY <= y*68+184+24-sliderYValue && settingOptions.get(i) && mouseY >= 164) {
					Utils.playClickSound();
					updateSearch();
					SettingOptions.selectedOption=i;
					currOptionName=settingIDs.get(i);
					Minecraft.getMinecraft().displayGuiScreen(new SettingOptions());
					return;
				}
				
				//SETTINGS CLICK FORMATTER
				x++;
				if(x == 3) {
					x=0;y++;
				}
			}
		} else {
			//IF CONFIRM MENU IS OPEN
			String[] tmp = confirmCancelCoords.split(",");
			int y1 = Integer.parseInt(tmp[0]);
			int y2 = Integer.parseInt(tmp[1]);
			int n1 = Integer.parseInt(tmp[2]);
			int n2 = Integer.parseInt(tmp[3]);
			if(mouseY >= this.height/2-1 && mouseY <= this.height/2-1+20) {
				
				if(mouseX >= y1 && mouseX <= y2) {
					
					ConfigHandler.resetConfig();
					isConfirmOpen=false;
					updateSearch();
					Utils.playClickSound();
				} else if(mouseX >= n1 && mouseY <= n2) {

					isConfirmOpen=false;
					Utils.playClickSound();
				} else {

					return;
				}
			}
			
		}
		
		
		
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	protected void keyTyped(char par1, int par2) throws IOException {
		if(textBoxClicked != -1) {
			super.keyTyped(par1, par2);
		} else if(par2 == Keyboard.KEY_E) {
			mc.thePlayer.closeScreen();
		}
		
		//ESCAPE
		if(par2 == Keyboard.KEY_ESCAPE) {
			settingsMenuOpen=false;
			DataGetter.updateConfig("main");
			updateSearch();
			mc.thePlayer.closeScreen();
			return;
		}
		if(textBoxClicked != -1) { textBoxKeyPress(par1, par2); }
		
		
		//SEARCH BAR FUNCTIONALITY
		if(isSearchFocused) {
			
			//BACKSPACE
			if(par2 ==Keyboard.KEY_BACK) {
				if(searchSettingString.length()>=1 ) {
					if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
						String finalStr = "";
						
						String[] string = searchSettingString.split(" ");
						ArrayList<String> str = new ArrayList();
						for(int i=0;i<string.length; i++) {
							str.add(string[i]);
						}
						
						str.remove(str.size()-1);
						
						for(int i=0;i<str.size();i++) {
							finalStr+=str.get(i)+" ";
						}
						
						searchSettingString = finalStr;
					} else {
						searchSettingString = Utils.removeIntLast(searchSettingString, 1);
					}
					
					
					
					
					
				}
				
				//TYPE CHARACTER INTO SEARCH BAR
			} else {
				if(Utils.checkIfCharLetter(par1+"")/* && mc.fontRendererObj.getStringWidth(searchSettingString) <= searchBarWidth-12*/) {
					
					searchSettingString+=(par1+"");
				}
			}
			updateSearch();
		} else {
			/*if(par2 == Keyboard.KEY_E) {
				DataGetter.updateConfig("main");
				settingsMenuOpen=false;
				updateSearch();
				mc.thePlayer.closeScreen();
			}*/
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	//WITHOUT SETTINGS FOR OPTIONS MENU
	public static void addSetting(String name, String categories, String id, String desc, String version) {
		
		String[] array = categories.split(", ");
		
		//ADD SETTING FUNCTION
		if(name.toLowerCase().contains(searchSettingString.toLowerCase()) || Utils.checkIfArrayContains(array, searchSettingString.toLowerCase())) {
			
			//GIVES ALL BASIC INFORMATION FOR SETTING
			settingNames.add(name);
			settingToggles.add(DataGetter.findBool(id));
			settingIDs.add(id);
			settingDesc.add(desc);
			settingOptions.add(false);
			settingVersions.add(version);
			//CREATE A BLANK SETTINGS PAGE AS A FILLER
			ArrayList<String[]> optionList = new ArrayList();
			optionList.add(new String[] {"false"});
			optionList.add(new String[] {""});
			optionList.add(new String[] {""});
			
			SettingOptions.settingOptions.add(optionList);
			
			
			
		}
	}
	
	//WITH SETTINGS FOR OPTIONS MENU
	public static void addSetting(String name, String categories, String id, String desc, String types, String optionNames, String optionIDs, String version) {
		
		String[] array = categories.split(", ");
		
		
		String[] type = types.split(", ");
		String[] optionID = optionIDs.split(", ");
		if(name.toLowerCase().contains(searchSettingString.toLowerCase()) || Utils.checkIfArrayContains(array, searchSettingString.toLowerCase())) {
			

			//GIVES ALL BASIC INFORMATION FOR SETTING
			settingNames.add(name);
			settingToggles.add(DataGetter.findBool(id));
			settingIDs.add(id);
			settingDesc.add(desc);
			settingOptions.add(true);
			settingVersions.add(version);
			
			String[] optionName = optionNames.split(", ");
			
			
			//GIVES ALL CUSTOM OPTIONS INFORMATION FOR SETTING
			ArrayList<String[]> optionList = new ArrayList();
			optionList.add(type);
			optionList.add(optionName);
			optionList.add(optionID);
			
			SettingOptions.settingOptions.add(optionList);
		}
		
	}
	
	//UPDATE THEME
	public static void updateTheme() {
		
		if(currTheme < 0) {
			currTheme = 0;
		}
		
		currActualTheme = DataGetter.findInt("currentTheme");
		logo = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/logo.png");
		settingBg = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/settingBg.png");
		gear = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/gear.png");
		Switch = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/switch.png");
		searchBar = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/searchBar.png");
		suggestionBar = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/suggestionBar.png");
		settingBorder = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/settingBorder.png");
		info = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/info.png");
		sliderBar = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/sliderBar.png");
		slider = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/slider.png");
		plusMinus = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/plusminus.png");
		blank = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
	}


	public static void addCategory(String displayString, String actualString) {
		searchPresets.add(displayString);
		actualPresets.add(actualString);
	}
	
	private static void drawButton(String text, int x, int y, String align, int mouseX, int mouseY) {
		
		Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().bindTexture(searchBar);
		String openConfig = text;
		int openConfigWidth = mc.fontRendererObj.getStringWidth(openConfig)+8;
		
		try { GlStateManager.color(0.6F, 0.6F, 0.6F, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		
		if(mouseX >= x-openConfigWidth && mouseX <= x && mouseY >= y && mouseY <= y+20) {
			GlStateManager.color(1, 1, 1, 1);
		}
		mc.getTextureManager().bindTexture(searchBar);
		mc.currentScreen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 1, 20, 375, 20);
		mc.currentScreen.drawModalRectWithCustomSizedTexture(x-openConfigWidth, y, 0, 0, openConfigWidth, 20, 375, 20);
		mc.currentScreen.drawModalRectWithCustomSizedTexture(x-openConfigWidth, y, 0, 0, 1, 20, 375, 20);
		mc.fontRendererObj.drawString(openConfig, x-openConfigWidth+5, y+6, 0x10, false);
		GlStateManager.enableBlend();
		
		
		if(Utils.unformatAllText(text).equals("Download DSG Images")) {
			if(LaunchThread.dgnImgVersCurr != LaunchThread.dgnImgVersLatest) {
				if(LaunchThread.dgnImgVersCurr != 0) {
					
					GlStateManager.pushMatrix();
					GlStateManager.scale(0.85, 0.85, 0.85);
					drawRedBox("Update Available!", (x-openConfigWidth)*118/100, (y+22)*118/100);
					GlStateManager.popMatrix();
				}
			}
		}
		
	}
	
	private static void buttonClickEvent(int button) {
		Minecraft mc = Minecraft.getMinecraft();
		if(button == 0) {
			mc.displayGuiScreen(new SettingGlobal());
		} else if(button == 1) {
			File file = new File("config/sbp");
			try { Desktop.getDesktop().open(file); } catch (IOException e) { e.printStackTrace(); }
		} else if(button == 2) {
			mc.displayGuiScreen(new SettingMoveAll());
		} else if(button == 3) {
			ConfigHandler.resetConfig();
			isConfirmOpen=true;
		} else if(button == 4) {
			if(!DownloadSecretsHandler.running) {
				new DownloadSecretsHandler().start();
				ConfigHandler.newObject("dgnImgVers", LaunchThread.dgnImgVersLatest);
				LaunchThread.dgnImgVersCurr=LaunchThread.dgnImgVersLatest;
			} else {
				Utils.sendErrMsg("Your download is already in progress!");
			}
			
		}
	}
	
	public static void drawRedBox(String str,int x ,int  y) {
		Minecraft mc = Minecraft.getMinecraft();
		
		int strWidth = mc.fontRendererObj.getStringWidth(str);
		mc.getTextureManager().bindTexture(blank);
		GlStateManager.enableBlend();
		GlStateManager.color(0.8F, 0.2F, 0.2F, 1);
		if(currTheme == 1) { GlStateManager.color(0.2F, 0.2F, 0.6F, 1); }
		mc.currentScreen.drawModalRectWithCustomSizedTexture(x, y, 0,0, strWidth+6, 14, strWidth+6, 14);
		GlStateManager.color(0.6F, 0.1F, 0.1F);
		mc.currentScreen.drawModalRectWithCustomSizedTexture(x-1, y, 0,0, 1, 14, 1, 14);
		mc.currentScreen.drawModalRectWithCustomSizedTexture(x, y-1, 0,0, strWidth+6, 1, strWidth+6, 1);
		mc.currentScreen.drawModalRectWithCustomSizedTexture(x+strWidth+6, y, 0,0, 1, 14, 1, 14);
		mc.currentScreen.drawModalRectWithCustomSizedTexture(x, y+14, 0,0, strWidth+6, 1, strWidth+6, 1);
		Utils.drawString(Colors.WHITE+str, x+3, y+3, 1);
	}
	
	private static ArrayList<Integer> textBoxIDs = new ArrayList();
	private static ArrayList<String> textBoxTexts = new ArrayList();
	private static ArrayList<Integer> textBoxXs = new ArrayList();
	private static ArrayList<Integer> textBoxYs = new ArrayList();
	private static ArrayList<Integer> textBoxWidths = new ArrayList();
	private static ArrayList<Integer> textBoxStyles = new ArrayList();
	private static int textBoxClicked = -1;
	private static int textBoxHighlightStart = 0;
	private static int textBoxHighlightEnd = 0;
	private static int textBoxTypingPos = 0;
	
	public static void addTextBox(int id, String text, int x, int y, int width, int textStyle) {
		textBoxIDs.add(id);
		textBoxTexts.add(text);
		textBoxXs.add(x);
		textBoxYs.add(y);
		textBoxWidths.add(width);
		textBoxStyles.add(textStyle);
		/*Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().bindTexture(searchBar);
		mc.currentScreen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 1, 20, 375, 20);
		mc.currentScreen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width-2, 20, 375, 20);
		mc.currentScreen.drawModalRectWithCustomSizedTexture(x+width-2, y, 0, 0, 1, 20, 375, 20);
		Utils.drawConfinedString(confirmCancelCoords, x, y, y, width);*/
		//mc.fontRendererObj.drawString(x+6, x-openConfigWidth+5, y+6, 0x10, false);
	}
	
	public static void updateTextBox(int id, String newText, int newX, int newY) {
		try { textBoxTexts.set(id, newText); textBoxXs.set(id, newX); textBoxYs.set(id, newY);} catch(Exception e) {}
	}
	
	public static void resetTextBoxes() {
		textBoxIDs.clear();
		textBoxTexts.clear();
		textBoxXs.clear();
		textBoxWidths.clear();
		textBoxStyles.clear();
		textBoxClicked = -1;
		textBoxHighlightStart = 0;
		textBoxHighlightEnd = 0;
	}
	
	public static void drawTextBoxes() {
		for(int i=0;i<textBoxIDs.size();i++) {
			int id = textBoxIDs.get(i);
			String txt = textBoxTexts.get(i);
			int x = textBoxXs.get(i);
			int y= textBoxYs.get(i);
			int width = textBoxWidths.get(i);
			Minecraft mc = Minecraft.getMinecraft();
			mc.getTextureManager().bindTexture(searchBar);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 1, 20, 375, 20);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width-2, 20, 375, 20);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x+width-2, y, 0, 0, 1, 20, 375, 20);
			String isClicked = "";
			if(textBoxClicked == id) { if(AnimationHandler.mainAnim <= 250 || (AnimationHandler.mainAnim > 500 && AnimationHandler.mainAnim <= 750)) { isClicked = "_"; } }
			
			Utils.drawConfinedString(Colors.WHITE+txt+isClicked, x+5, y+6, 0, width-12);
		}
	}
	
	public static void drawTextBox(int id) {
		drawTextBox(id, searchBar, 20);
	}
	
	public static void drawTextBox(int id, ResourceLocation resourceLocation, int height) {
		try {
			String txt = textBoxTexts.get(id);
			if(txt.length() < 2 && textBoxClicked != -1) {
				txt = Colors.WHITE;
				updateTextBox(textBoxClicked, Colors.WHITE, getX(textBoxClicked), getY(textBoxClicked));
			}
			String rawTxt = Utils.unformatText(txt);
			int x = textBoxXs.get(id);
			int y= textBoxYs.get(id);
			int width = textBoxWidths.get(id);
			Minecraft mc = Minecraft.getMinecraft();
			mc.getTextureManager().bindTexture(resourceLocation);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, 1, height, 375, height);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width-2, height, 375, height);
			mc.currentScreen.drawModalRectWithCustomSizedTexture(x+width-2, y, 0, 0, 1, height, 375, height);
			if(textBoxHighlightEnd > rawTxt.length()) { textBoxHighlightEnd = rawTxt.length(); }
			if(textBoxHighlightStart < 0) {textBoxHighlightStart=0;}
			String hiltStr = rawTxt.substring(textBoxHighlightStart, textBoxHighlightEnd);
			int beforeWidth = mc.fontRendererObj.getStringWidth(rawTxt.substring(0, textBoxHighlightStart));
			
			int highlightTextColor = 0xFFFFFF;
			
			if(txt.startsWith(Reference.COLOR_CODE_CHAR+"")) {
				ArrayList<Float> bgColor = ColorUtils.getRGBFromColorCode(txt.substring(0, 2));
				float r = bgColor.get(0);
				float g = bgColor.get(1);
				float b = bgColor.get(2);
				float a = bgColor.get(3);
				float r2 = 1-r;
				float g2 = 1-g;
				float b2 = 1-b;
				//Utils.print(r+", "+g+", "+b+", "+a);
				mc.getTextureManager().bindTexture(blank);
				//textBoxHighlightStart=0;
				//textBoxHighlightEnd=1;
				//textBoxTypingPos=30;
				
				int durWidth = mc.fontRendererObj.getStringWidth(hiltStr);
				GlStateManager.color(r, g, b, a);
				mc.currentScreen.drawModalRectWithCustomSizedTexture(x+5+beforeWidth, y+5, 0, 0, durWidth, 10, 1, 1);
				
				Utils.drawString(Colors.WHITE+txt, x+5, y+6, 0);
				int txtR = (int) (255*(r2)); int txtG = (int) (255*(b2)); int txtB = (int) (255*(g2));
				Color txtClr = new Color(txtR, txtG, txtB);
				if(textBoxTypingPos > textBoxHighlightStart && textBoxTypingPos < textBoxHighlightEnd) {
					int clikR = txtR-124;
					int clikG = txtG-124;
					int clikB = txtB-124;
					if(clikR < 0) {clikR=txtR+124;}
					if(clikG < 0) {clikG=txtG+124;}
					if(clikB < 0) {clikB=txtB+124;}
					highlightTextColor=new Color(clikR, clikG, clikB).getRGB();
				}
				
				mc.fontRendererObj.drawString(hiltStr, x+beforeWidth+5, y+6, txtClr.getRGB());
			} else {
				Utils.drawString(Colors.WHITE+txt, x+5, y+6, 0);
			}
			
			if(textBoxClicked == id) { if(AnimationHandler.mainAnim <= 250 || (AnimationHandler.mainAnim > 500 && AnimationHandler.mainAnim <= 750)) {
				String isClicked = '\u2503'+""; 
				if(textBoxTypingPos >= rawTxt.length()) { 
					if(mc.fontRendererObj.getStringWidth(getText(id)) <= width-10) { isClicked = "_"; }
					
					}
				if(textBoxTypingPos > rawTxt.length()) { textBoxTypingPos = rawTxt.length(); }
				
				int beforeTypingWidth = mc.fontRendererObj.getStringWidth(rawTxt.substring(0, textBoxTypingPos));
				mc.fontRendererObj.drawString(isClicked, x+4+beforeTypingWidth, y+6, highlightTextColor, false);
			}}
			
			} catch(Exception e) {
				//e.printStackTrace();
			}
	}
	
	public static int getX(int id) {
		return textBoxXs.get(id);
	}
	
	public static int getY(int id) {
		return textBoxYs.get(id);
	}
	
	public static String getText(int id) {
		return textBoxTexts.get(id);
	}
	
	public static void textBoxClick(int mouseX, int mouseY) {
		Minecraft mc = Minecraft.getMinecraft();
		textBoxHighlightEnd = 0;
		textBoxHighlightStart = 0;
		for(int i=0;i<textBoxIDs.size();i++) {
			if(mouseX >= textBoxXs.get(i) && mouseX <= textBoxXs.get(i)+textBoxWidths.get(i)-2 && mouseY >= textBoxYs.get(i) && mouseY <= textBoxYs.get(i)+20) {
				textBoxClicked = i;
				Utils.playClickSound();
				String str = textBoxTexts.get(i);
				String rawStr = Utils.unformatAllText(str);
				int xClick = mouseX-textBoxXs.get(i)-5;
				//Utils.sendMessage(xClick+"");
				if(xClick > mc.fontRendererObj.getStringWidth(rawStr)) {
					textBoxTypingPos = rawStr.length()+1;
				}
				int prevWidth = 0;
				
				for(int j=0;j<rawStr.length();j++) {
					
					int preWidth = mc.fontRendererObj.getCharWidth(rawStr.charAt(j));
					if(xClick >= prevWidth) {
						prevWidth+=preWidth;
						continue;
					} else {
						if(j < 1) {j=1;}
						textBoxTypingPos=j-1;
						return;
					}
				}
				return;
			}
		}
		textBoxClicked = -1;
	}
	
	public static void textBoxKeyPress(char par1, int par2) {
		Minecraft mc = Minecraft.getMinecraft();
		
		//RIGHT ARROW KEY
		if(par2 == Keyboard.KEY_RIGHT) {
			
			//RIGHT ARROW HIGHLIGHTING
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					String str = textBoxTexts.get(textBoxClicked);
					String rawStr = Utils.unformatAllText(str);
					if(rawStr.substring(textBoxTypingPos).contains(" ")) {
						Boolean hitSpace = false;
						int startPos = textBoxTypingPos;
						int endPos = textBoxTypingPos+1;
						int skipSpace = 0;
						try {
							if(textBoxHighlightStart != 0 || textBoxHighlightEnd != 0) {
								String highlightStr = rawStr.substring(textBoxHighlightStart, textBoxHighlightEnd+1);
								for(char chr : highlightStr.toCharArray()) {
									if((chr+"").equals(" ")) {skipSpace++;}
								}
							} 
						} catch(StringIndexOutOfBoundsException e) {
							return;
						} catch(Exception e) { }
						while(hitSpace == false) { 
							try { 
								if(!(rawStr.charAt(endPos)+"").equals(" ")) { 
									endPos++; 
								} else {
									if(skipSpace > 0) {
										skipSpace--;
										endPos++;
										continue;
									}
									hitSpace = true;
								} 
							} catch(Exception e) {
								break;
							} 
						}
						textBoxHighlightEnd=endPos;
						textBoxHighlightStart=startPos;
					} else {
						textBoxHighlightStart=textBoxTypingPos;
						textBoxHighlightEnd=rawStr.length();
					}
					
				
				} else {
					
					if(textBoxHighlightEnd == 0) {
						textBoxHighlightStart=textBoxTypingPos;
						textBoxHighlightEnd=textBoxTypingPos+1;
						return;
					} else {
						if(textBoxHighlightStart < textBoxTypingPos) {
							textBoxHighlightStart++; return;
						}
						textBoxHighlightEnd++; return;
					}
				}
				
				
			} else { textBoxTypingPos++; 
			if(textBoxHighlightEnd != 0) {textBoxTypingPos=textBoxHighlightEnd;}
			textBoxHighlightStart=0; textBoxHighlightEnd=0; }
			
			//LEFT ARROW KEY
		} else if(par2 == Keyboard.KEY_LEFT) {
			
			//LEFT ARROW HIGHLIGHTING
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					String str = textBoxTexts.get(textBoxClicked);
					String rawStr = Utils.unformatAllText(str);
					
					Boolean hitSpace = false;
					int startPos = textBoxTypingPos;
					int endPos = textBoxTypingPos-1;
					int skipSpace = 0;
					try {
						if(textBoxHighlightStart != 0 && textBoxHighlightEnd != 0) {
							String highlightStr = rawStr.substring(textBoxHighlightStart-1, textBoxHighlightEnd);
							for(char chr : highlightStr.toCharArray()) {
								if((chr+"").equals(" ")) {skipSpace++;}
							}
						} else if(textBoxHighlightStart == 0 && textBoxHighlightStart-textBoxHighlightEnd != 0) {return;}
						
					} catch(Exception e) {}
						while(hitSpace == false) {
							try {
								if(!(rawStr.charAt(endPos)+"").equals(" ")) {
									
									endPos--; 
								} else {
									if(skipSpace > 0) {
										skipSpace--;
										endPos--;
										continue;
									} 
									hitSpace = true;
									
								} 
							} catch(Exception e) {
								break;
							} 
						}
						textBoxHighlightEnd=startPos;
						textBoxHighlightStart=endPos+1;
					//}
					
				
				} else {
					if(textBoxHighlightEnd > textBoxTypingPos) {textBoxHighlightEnd--;}
					
					else if(textBoxTypingPos >= textBoxHighlightStart) {
						if(textBoxHighlightStart == 0) { textBoxHighlightStart = textBoxTypingPos-1; 
						} else { textBoxHighlightStart--; }
						textBoxHighlightEnd = textBoxTypingPos;
					} else {
						textBoxHighlightStart++;
					}
				}
				
				
				
			} else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				
			}
			
			else if(textBoxTypingPos>=1) {textBoxTypingPos--; 
			if(textBoxHighlightStart != 0) {textBoxTypingPos=textBoxHighlightStart;}
			textBoxHighlightStart=0; textBoxHighlightEnd=0;}
			
		//BACKSPACE KEY
		} else if(par2 == Keyboard.KEY_BACK) {
			
			try {
				String str = textBoxTexts.get(textBoxClicked);
				String rawStr = Utils.unformatAllText(str);
				int offset = str.length()-rawStr.length();
				//DELETE HIGHLIGHTED TEXT
				if(textBoxHighlightStart != 0 || textBoxHighlightEnd != 0) {
					String beforeString = str.substring(0, textBoxHighlightStart+offset);
					String afterString = str.substring(textBoxHighlightEnd+offset);
					updateTextBox(textBoxClicked, beforeString+afterString, getX(textBoxClicked), getY(textBoxClicked));
					textBoxTypingPos=textBoxHighlightStart;
					textBoxHighlightStart=0;
					textBoxHighlightEnd=0;
					
					return;
					
				//DELETE FULL WORD
				} else if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					char[] chrList = str.toCharArray();
					String output = "";
					Boolean hitSpace = false;
					int startPos = textBoxTypingPos;
					int endPos = textBoxTypingPos-1;
					
					while(hitSpace == false) { try { if(!(rawStr.charAt(endPos)+"").equals(" ")) { endPos--; } else {hitSpace = true;} } catch(Exception e) {break;} }
					if(startPos-endPos <= 1) {
						String newStr = ""; char[] strChars = str.toCharArray();
						for(int i=0;i<strChars.length;i++) { if(i == textBoxTypingPos+1) {  textBoxTypingPos--; continue; } newStr+=strChars[i];  }
						updateTextBox(textBoxClicked, newStr, getX(textBoxClicked), getY(textBoxClicked));
						textBoxHighlightEnd = 0; textBoxHighlightStart = 0; return;
					}
					
					String beforeStr = str.substring(0, endPos+offset)+" ";
					String afterStr = str.substring(startPos+offset);
					output=beforeStr+afterStr; textBoxTypingPos=endPos+1;
					updateTextBox(textBoxClicked, output, getX(textBoxClicked), getY(textBoxClicked));
					return;
				}
				
				String newStr = "";
				char[] strChars = str.toCharArray();
				for(int i=0;i<strChars.length;i++) { if(i == textBoxTypingPos+1) { 
					if(textBoxTypingPos == 0) {return;}
					textBoxTypingPos--; 
					continue;
					} newStr+=strChars[i];  
					}
				updateTextBox(textBoxClicked, newStr, getX(textBoxClicked), getY(textBoxClicked));
				textBoxHighlightEnd = 0;
				textBoxHighlightStart = 0;
			} catch(Exception e) {}
			
		}
		
		
		else {
			String str = textBoxTexts.get(textBoxClicked);
			String rawStr = Utils.unformatAllText(str);
			int offset = str.length()-rawStr.length();
			if(par2 == Keyboard.KEY_C) {
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					
					if(!(textBoxHighlightStart == 0 && textBoxHighlightEnd == 0)) {
						setClipboardString(rawStr.substring(textBoxHighlightStart, textBoxHighlightEnd));
					}
					return;
				}
			} else if(par2 == Keyboard.KEY_V) {
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					String output = "";
					for(int i=0;i<str.length()+1;i++) {
						Utils.print(i-offset+" : "+textBoxTypingPos);
						if(i-offset == textBoxTypingPos) { 
							output+=getClipboardString();
							}
						try {
							output+=str.charAt(i);
						} catch(Exception e) {}
						
					}
					
					if(str.length() == textBoxTypingPos || str.length() == 0) {output+=getClipboardString();}
					
					updateTextBox(textBoxClicked, output, getX(textBoxClicked), getY(textBoxClicked));
					textBoxTypingPos+=getClipboardString().length();
					Utils.sendMessage(output+" : "+getClipboardString());
					return;
				}
			} else if(par2 == Keyboard.KEY_X) {
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					if(!(textBoxHighlightStart == 0 && textBoxHighlightEnd == 0)) {
						setClipboardString(rawStr.substring(textBoxHighlightStart, textBoxHighlightEnd));
						String beforeString = str.substring(0, textBoxHighlightStart+offset);
						String afterString = str.substring(textBoxHighlightEnd+offset);
						updateTextBox(textBoxClicked, beforeString+afterString, getX(textBoxClicked), getY(textBoxClicked));
						textBoxTypingPos=textBoxHighlightStart;
						textBoxHighlightStart=0;
						textBoxHighlightEnd=0;
					}
					return;
				}
			} else if(par2 == Keyboard.KEY_A) {
				if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					textBoxHighlightStart=0;
					textBoxHighlightEnd=rawStr.length();
				}
			}
			
			if(Utils.checkIfCharLetter(par1+"")) {
				
				String newStr = "";
				char[] strChars = textBoxTexts.get(textBoxClicked).toCharArray();
				for(int i=0;i<strChars.length;i++) { newStr+=strChars[i]; if(i == textBoxTypingPos+1) { newStr+=par1;  } }
				textBoxTypingPos++;
				if(mc.fontRendererObj.getStringWidth(newStr) < textBoxWidths.get(textBoxClicked)-8) {
					updateTextBox(textBoxClicked, newStr, getX(textBoxClicked), getY(textBoxClicked));
				} else {
					Minecraft.getMinecraft().thePlayer.playSound("note.bass", 1, 0.2F);
				}
			}
			
			return;
		}
	}
}
