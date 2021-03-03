package com.cobble.sbp.gui.menu.settings;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.SettingList;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.threads.onetimes.FixFadeThread;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SettingMenu extends GuiScreen {

	//SETTING ARRAYS
	public static ArrayList<String> settingNames = new ArrayList();
	public static ArrayList<String> settingIDs = new ArrayList();
	public static ArrayList<Boolean> settingToggles = new ArrayList();
	public static ArrayList<String> settingDesc = new ArrayList();
	public static ArrayList<Boolean> settingOptions = new ArrayList();
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
	private static int searchBarWidth = 0;
	private static int searchTextWidth = 0;
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
	
	
	//UPDATES THE SEARCH FOR FEATURES
	public static void updateSearch() {
		SettingMoveAll.settingCoords.clear(); SettingMoveAll.settingNames.clear(); SettingMoveAll.settingIDs.clear(); SettingMoveAll.settingWidths.clear(); SettingMoveAll.settingHeights.clear();
		settingNames.clear(); settingIDs.clear(); settingToggles.clear();  settingDesc.clear(); settingOptions.clear();
		SettingOptions.settingOptions.clear(); searchPresets.clear(); actualPresets.clear();
		sliderYValue = 0;
		sliderOffset = 0;
		SettingList.loadSettings();
		
		}
	
	@Override
	public void initGui() {
		l=0;
		
		mouseDown=false;
		
		settingsMenuOpen=true; onOffClickFrames = 0; //fadeIn = 0; fadeInFrames=0;
		
		updateSearch();
		new FixFadeThread().start();
		
		//CATEGORIES/UPDATE SEARCH
		totalLaunches = DataGetter.findInt("modLaunches");
		themeNames.add("Dark");
		themeNames.add("Light");
		themeNames.add("Time");
		

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
	private static ResourceLocation searchBar = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/searchBar.png");
	private static ResourceLocation suggestionBar = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/suggestionBar.png");
	private static ResourceLocation settingBorder = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/settingBorder.png");
	private static ResourceLocation info = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/info.png");
	private static ResourceLocation sliderBar = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/sliderBar.png");
	private static ResourceLocation slider = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/slider.png");
	private static ResourceLocation plusMinus = new ResourceLocation(Reference.MODID, "textures/"+currTheme+"/menu/plusminus.png");
	private static ResourceLocation discord = new ResourceLocation(Reference.MODID, "textures/gui/discord.png");
	private static ResourceLocation github = new ResourceLocation(Reference.MODID, "textures/gui/github.png");
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		int sliderHeight = settingNames.size()*68/3;
		
		if(sliderHeight > 68) {
			sliderHeight-=45;
			//sliderHeight+=68;
			if(settingNames.size() % 3 == 1) { sliderHeight+=23;
			} else if(settingNames.size() % 3 == 0) { sliderHeight-=23; }
			
		}
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
		}
		fadeIn=1;
		//Utils.print(fadeIn);
		//GlStateManager.color(1, 1, 1, 1);
		
		GlStateManager.enableBlend();
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
		if(mouseX >= this.width/2+210 && mouseX <= this.width/2+210+32 && mouseY >= 17+22+13 && mouseY <= 17+22+13+32) {
			GlStateManager.color(1, 1, 1, 1);
			hoveringText = "discord";
		} else {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.3F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		}
		mc.getTextureManager().bindTexture(discord);
		this.drawModalRectWithCustomSizedTexture(this.width/2+210, 17+22+13, 0, 0, 32, 32, 32, 32);
		
		
		if(mouseX >= this.width/2+210 && mouseX <= this.width/2+210+32 && mouseY >= 17+22+13+34 && mouseY <= 17+22+13+32+34) {
			GlStateManager.color(1, 1, 1, 1);
			hoveringText = "github";
		} else {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.3F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		}
		mc.getTextureManager().bindTexture(github);
		this.drawModalRectWithCustomSizedTexture(this.width/2+210, 17+22+47, 0, 0, 32, 32, 32, 32);
		
		
		
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
		searchTextWidth = fontRendererObj.getStringWidth("Search")+4;
		resetTextWidth = fontRendererObj.getStringWidth("Reset")+12;
		
		
		//SEARCH TEXT
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		this.drawModalRectWithCustomSizedTexture(this.width/2-188, 15+125+2, 0, 0, 1, 20, 375, 20);
		for(int i=0;i<searchTextWidth;i++) {
			this.drawModalRectWithCustomSizedTexture(this.width/2-188+1+i, 15+125+2, 1, 0, 1, 20, 375, 20);
		}
		this.drawModalRectWithCustomSizedTexture(this.width/2-188+searchTextWidth+1, 15+125+2, 0, 0, 1, 20, 375, 20);
		mc.fontRendererObj.drawString(textColor+"Search:", this.width/2-188+3, 15+125+8, 0x10, false);
		
		//RESET
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		
		if(mouseX >= this.width/2-188+374-resetTextWidth-1 && mouseX <= this.width/2-188+374 && mouseY >= 15+125+2 && mouseY <= 15+125+2+20) {
			GlStateManager.color(1, 1, 1, 1);
		}
		
		
		mc.getTextureManager().bindTexture(searchBar);
		this.drawModalRectWithCustomSizedTexture(this.width/2-188+374, 15+125+2, 0, 0, 1, 20, 375, 20);
		for(int i=0;i<resetTextWidth;i++) {
			this.drawModalRectWithCustomSizedTexture(this.width/2-188+373-i, 15+125+2, 333, 0, 1, 20, 375, 20);
		}
		this.drawModalRectWithCustomSizedTexture(this.width/2-188+374-resetTextWidth-1, 15+125+2, 0, 0, 1, 20, 375, 20);
		mc.fontRendererObj.drawString(textColor+"Reset", this.width/2-188+375-resetTextWidth+5, 15+125+8, 0x10, false);
		GlStateManager.color(1, 1, 1, fadeIn);
		
		//SEARCH BAR
		searchBarWidth = 375-searchTextWidth-resetTextWidth-4;
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		
		if(isSearchFocused || (mouseX >= this.width/2-188+searchTextWidth+2 && mouseX <= this.width/2-187+searchTextWidth+searchBarWidth && mouseY >= 15+125+2 && mouseY <= 15+125+2+20)) {
			GlStateManager.color(1, 1, 1, 1);
		}
		
		mc.getTextureManager().bindTexture(searchBar);
		
		for(int i=0;i<searchBarWidth;i++) {
			this.drawModalRectWithCustomSizedTexture(this.width/2-188+searchTextWidth+2+i, 15+125+2, 45, 0, 1, 20, 375, 20);
		}
		
		//RESET CONFIG BUTTON
		String resetConfig = "Reset Config";
		int resetConfigWidth = fontRendererObj.getStringWidth(resetConfig)+8;
		
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		
		if(mouseX >= this.width/2-188-20-resetConfigWidth && mouseX <= this.width/2-188-20 && mouseY >= this.height-22 && mouseY <= this.height-22+20) {
			GlStateManager.color(1, 1, 1, 1);
		}
		this.drawModalRectWithCustomSizedTexture(this.width/2-188-20, this.height-22, 0, 0, 1, 20, 375, 20);
		for(int i=1;i<resetConfigWidth;i++) {
			this.drawModalRectWithCustomSizedTexture(this.width/2-188-20-i, this.height-22, 333, 0, 1, 20, 375, 20);
		}
		this.drawModalRectWithCustomSizedTexture(this.width/2-188-20-resetConfigWidth, this.height-22, 0, 0, 1, 20, 375, 20);
		
		//RESET CONFIG BUTTON
		String globalSettings = "Global Settings";
		int globalSettingsWidth = fontRendererObj.getStringWidth(globalSettings)+8;
		
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		
		if(mouseX >= this.width/2-188-20-globalSettingsWidth && mouseX <= this.width/2-188-20 && mouseY >= this.height-88 && mouseY <= this.height-88+20) {
			GlStateManager.color(1, 1, 1, 1);
		}
		this.drawModalRectWithCustomSizedTexture(this.width/2-188-20, this.height-88, 0, 0, 1, 20, 375, 20);
		for(int i=1;i<globalSettingsWidth;i++) {
			this.drawModalRectWithCustomSizedTexture(this.width/2-188-20-i, this.height-88, 333, 0, 1, 20, 375, 20);
		}
		this.drawModalRectWithCustomSizedTexture(this.width/2-188-20-globalSettingsWidth, this.height-88, 0, 0, 1, 20, 375, 20);
		
		
		//EDIT GUI LOCATIONS
				String editGui = "Edit GUI Locations";
				int editGuiWidth = fontRendererObj.getStringWidth(editGui)+8;
				
				try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
				} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
				
				if(mouseX >= this.width/2-188-20-editGuiWidth && mouseX <= this.width/2-188-20 && mouseY >= this.height-44 && mouseY <= this.height-44+20) {
					GlStateManager.color(1, 1, 1, 1);
				}
				this.drawModalRectWithCustomSizedTexture(this.width/2-188-20, this.height-44, 0, 0, 1, 20, 375, 20);
				for(int i=1;i<editGuiWidth;i++) {
					this.drawModalRectWithCustomSizedTexture(this.width/2-188-20-i, this.height-44, 333, 0, 1, 20, 375, 20);
				}
				this.drawModalRectWithCustomSizedTexture(this.width/2-188-20-editGuiWidth, this.height-44, 0, 0, 1, 20, 375, 20);
		
				//OPEN CONFIG FOLDER
				String openConfig = "Open Config Folder";
				int openConfigWidth = fontRendererObj.getStringWidth(openConfig)+8;
				
				try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
				} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
				
				if(mouseX >= this.width/2-188-20-openConfigWidth && mouseX <= this.width/2-188-20 && mouseY >= this.height-66 && mouseY <= this.height-66+20) {
					GlStateManager.color(1, 1, 1, 1);
				}
				this.drawModalRectWithCustomSizedTexture(this.width/2-188-20, this.height-66, 0, 0, 1, 20, 375, 20);
				for(int i=1;i<openConfigWidth;i++) {
					this.drawModalRectWithCustomSizedTexture(this.width/2-188-20-i, this.height-66, 333, 0, 1, 20, 375, 20);
				}
				this.drawModalRectWithCustomSizedTexture(this.width/2-188-20-openConfigWidth, this.height-66, 0, 0, 1, 20, 375, 20);
		
				try {
				
				
				if(mouseDownX >= this.width/2+210 && mouseDownX <= this.width/2+235 && mouseDownY >= 140 && mouseDownY <= 140+302 && mouseDown && sliderHeight > this.height-(15+125+2)) {
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
					int totalSettingHeight = settingCount*68;//-(67);
					int percentDown = 1000*sliderYValue/totalSettingHeight;
					sliderOffset = (302)*percentDown/1000;
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
		for(int i=0;i<searchPresets.size();i++) {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
			} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
			if(mouseX >= this.width/2-188-120 && mouseX <= this.width/2-188-120+100 && mouseY >= 166+(i*23) && mouseY <= 164+(i*23)+23) {
				GlStateManager.color(1, 1, 1, fadeIn);
			}
			
			mc.getTextureManager().bindTexture(suggestionBar);
			this.drawModalRectWithCustomSizedTexture(this.width/2-188-120, 164+(i*23), 0, 0, 100, 25, 100, 25);
			mc.fontRendererObj.drawString(textColor+searchPresets.get(i), this.width/2-188-120+4, 164+(i*23)+8, 0x10, false);
		}
		
		//STRINGS FOR TEXTURE ELEMENTS
		
		mc.fontRendererObj.drawString(textColor+resetConfig, this.width/2-188-20-resetConfigWidth+5, this.height-22+6, 0x10, false);
		mc.fontRendererObj.drawString(textColor+editGui, this.width/2-188-20-editGuiWidth+5, this.height-44+6, 0x10, false);
		mc.fontRendererObj.drawString(textColor+openConfig, this.width/2-188-20-openConfigWidth+5, this.height-66+6, 0x10, false);
		mc.fontRendererObj.drawString(textColor+globalSettings, this.width/2-188-20-globalSettingsWidth+5, this.height-88+6, 0x10, false);
		mc.fontRendererObj.drawString(textColor+themeNames.get(currActualTheme)+" Theme", this.width/2-308+50-(themeWidth/2), 15+125+8, 0x10, false);
		mc.fontRendererObj.drawString(textColor+(currActualTheme+1), this.width/2-308+109-(fontRendererObj.getStringWidth(currTheme+"")/2), 15+125+8, 0x10, false);
		//this.width/2-308, 120-sliderYValue
		
		//IF TEXTBOX IS SELECTED
		String selectedIndicator = "";
		if(isSearchFocused && onOffClickFrames < FPS/4) {
			selectedIndicator = "_";
		}
		if(onOffClickFrames >= FPS/2) {
			onOffClickFrames=0;
		}
		//IF TEXTBOX IS FULL
		if(!(mc.fontRendererObj.getStringWidth(searchSettingString) <= searchBarWidth-12)) {
			selectedIndicator = "";
		}
		
		//DRAW SEARCH TEXT
		mc.fontRendererObj.drawString(textColor+searchSettingString+selectedIndicator, this.width/2-188+6+searchTextWidth, 15+125+8, 0x10, false);
		
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
						
						if((y*68)+184-sliderYValue+30 > 166) { this.drawString(fontRendererObj, Colors.YELLOW+settingNames.get(i)+Colors.WHITE, x*135+(this.width/2-198)+6, (y*68)+184-sliderYValue+30, 0x10);}
						
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
			GlStateManager.color(1, 1, 1, 0.6F);
			this.drawModalRectWithCustomSizedTexture(this.width/2-(confirmResetWidth/2)-10, this.height/2-32, 0, 0, confirmResetWidth+20, 60, 0, 0);
			GlStateManager.color(1, 1, 1, 0.7F);
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
				this.drawModalRectWithCustomSizedTexture((this.width/2)-3-i, this.height/2-1, 1, 0, 1, 20, 375, 20);
			}
			this.drawModalRectWithCustomSizedTexture((this.width/2)-confirmWidth-11, this.height/2-1, 0, 0, 1, 20, 375, 20);
			
			
			
			if(mouseX >= n1 && mouseY <= n2 && mouseY >= this.height/2-1 && mouseY <= this.height/2-1+20) {
				GlStateManager.color(1, 1, 1, 1);
			} else {
				GlStateManager.color(1, 1, 1, 0.7F);
			}
			//CANCEL BOX
			String cancelText = Colors.BOLD+"Cancel";
			int cancelWidth = fontRendererObj.getStringWidth(cancelText);
			
			this.drawModalRectWithCustomSizedTexture((this.width/2)+2, this.height/2-1, 0, 0, 1, 20, 375, 20);
			for(int i=0;i<cancelWidth+8;i++) {
				this.drawModalRectWithCustomSizedTexture((this.width/2)+3+i, this.height/2-1, 333, 0, 1, 20, 375, 20);
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
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		//PREVENT DOING THINGS WHILE CONFIRM MENU IS OPEN
		if(!isConfirmOpen) {
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
			
			
			String editGui = "Edit GUI Locations";
			int editGuiWidth = fontRendererObj.getStringWidth(editGui)+8;
			String openConfig = "Open Config Folder";
			int openConfigWidth = fontRendererObj.getStringWidth(openConfig)+8;
			String globalSettings = "Global Settings";
			int globalSettingsWidth = fontRendererObj.getStringWidth(globalSettings)+8;
			
			if(mouseX >= this.width/2-188-20-editGuiWidth && mouseX <= this.width/2-188-20 && mouseY >= this.height-44 && mouseY <= this.height-44+20) {
				Utils.playClickSound();
				Minecraft.getMinecraft().displayGuiScreen(new SettingMoveAll());
			}
			else if(mouseX >= this.width/2+210 && mouseX <= this.width/2+210+32 && mouseY >= 17+22+13 && mouseY <= 17+22+13+32) {
				Utils.playClickSound();
				Utils.openURL("https://discord.gg/QXA3y5EbNA");
			}
			else if(mouseX >= this.width/2+210 && mouseX <= this.width/2+210+32 && mouseY >= 17+22+13+34 && mouseY <= 17+22+13+32+34) {
				Utils.playClickSound();
				Utils.openURL("https://github.com/Cobble8/SkyblockPersonalized");
			}
			
			else if(mouseX >= this.width/2-188-20-openConfigWidth && mouseX <= this.width/2-188-20 && mouseY >= this.height-66 && mouseY <= this.height-66+20) {
				Utils.playClickSound();
				File config = new File("config/"+Reference.MODID+"/");
				Desktop.getDesktop().open(config);
			}
			
			else if(mouseX >= this.width/2-188-20-globalSettingsWidth && mouseX <= this.width/2-188-20 && mouseY >= this.height-88 && mouseY <= this.height-88+20) {
				Utils.playClickSound();
				Minecraft.getMinecraft().displayGuiScreen(new SettingGlobal());
			}
			
			//
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
			
			//SLIDER CLICK CHECK
			//if(mouseX >= this.width/2+210 && mouseX <= this.width/2+210+50 && mouseY >=140 && mouseY <= 140+300) {
				//Utils.playClickSound();
				
				//sliderYValue=((mouseY-140)*25)-100;
				//return;
			//}
			
			
			//CHECK IF USER IS SEARCHING
			if(mouseX >= this.width/2-188+searchTextWidth+1 && mouseX <= this.width/2-188+375-resetTextWidth-2 && mouseY >= 15+125 && mouseY <= 15+125+21) {
				isSearchFocused=true;
				Utils.playClickSound();
			} else { isSearchFocused=false; }
			
			//CLEAR SEARCH
			if(mouseX >= this.width/2-188+375-resetTextWidth-1 && mouseX <= this.width/2-188+374 && mouseY >= 15+125+2 && mouseY <= 15+125+2+20) {
				searchSettingString="";
				Utils.playClickSound();
				updateSearch();
				return;
			}
			
			//PRESET SEARCH ACTIONS
			for(int i=1;i<searchPresets.size();i++) {
				if(mouseX >= this.width/2-188-120 && mouseX <= this.width/2-188-120+100 && mouseY >= 164+(i*23) && mouseY <= 164+(i*23)+25) {
					searchSettingString=actualPresets.get(i);
					Utils.playClickSound();
					updateSearch();
					return;
				}
			
			}
			
			//SETTING CLICK FUNCTIONS
			int x=0;
			int y=0;
			for(int i=0;i<settingNames.size();i++) {
				
				//SWITCH CLICK EVENT
				if(mouseX >= x*135+(this.width/2-198)+40-20 && mouseX <= x*135+(this.width/2-198)+80-20 && mouseY >= y*68+164+22-sliderYValue && mouseY <= y*68+164+22+20-sliderYValue && mouseY >= 164) {
					ConfigHandler.newObject(settingIDs.get(i), Utils.invertBoolean(settingToggles.get(i)));
					settingToggles.set(i, Utils.invertBoolean(settingToggles.get(i)));
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
		
		//ESCAPE
		if(par2 == Keyboard.KEY_ESCAPE) {
			settingsMenuOpen=false;
			DataGetter.updateConfig("main");
			mc.thePlayer.closeScreen();
			return;
		}
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
				if(Utils.checkIfCharLetter(par1+"") && mc.fontRendererObj.getStringWidth(searchSettingString) <= searchBarWidth-12) {
					
					searchSettingString+=(par1+"");
				}
			}
			updateSearch();
		} else {
			if(par2 == Keyboard.KEY_E) {
				DataGetter.updateConfig("main");
				settingsMenuOpen=false;
				mc.thePlayer.closeScreen();
			}
		}
		
		super.keyTyped(par1, par2);
	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	//WITHOUT SETTINGS FOR OPTIONS MENU
	public static void addSetting(String name, String categories, String id, String desc) {
		
		String[] array = categories.split(", ");
		
		//ADD SETTING FUNCTION
		if(name.toLowerCase().contains(searchSettingString.toLowerCase()) || Utils.checkIfArrayContains(array, searchSettingString.toLowerCase())) {
			
			//GIVES ALL BASIC INFORMATION FOR SETTING
			settingNames.add(name);
			settingToggles.add(DataGetter.findBool(id));
			settingIDs.add(id);
			settingDesc.add(desc);
			settingOptions.add(false);

			//CREATE A BLANK SETTINGS PAGE AS A FILLER
			ArrayList<String[]> optionList = new ArrayList();
			optionList.add(new String[] {"false"});
			optionList.add(new String[] {""});
			optionList.add(new String[] {""});
			
			SettingOptions.settingOptions.add(optionList);
			
			
			
		}
	}
	
	//WITH SETTINGS FOR OPTIONS MENU
	public static void addSetting(String name, String categories, String id, String desc, String types, String optionNames, String optionIDs) {
		
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
	}


	public static void addCategory(String displayString, String actualString) {
		searchPresets.add(displayString);
		actualPresets.add(actualString);
	}
	
}
