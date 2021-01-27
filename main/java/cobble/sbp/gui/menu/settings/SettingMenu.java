package com.cobble.sbp.gui.menu.settings;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.SettingList;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
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
	ArrayList<String> themeNames = new ArrayList();

	
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
	
	public static int modLaunches = Integer.parseInt(DataGetter.find("modLaunches")+"");
	public static Boolean modLaunchToggle = (Boolean) DataGetter.find("modLaunchToggle");
	
	public static Boolean settingsMenuOpen = false;
	public static String currOptionName = "";
	
	
	
	//UPDATES THE SEARCH FOR FEATURES
	public static void updateSearch() {
		SettingMoveAll.settingCoords.clear();
		SettingMoveAll.settingNames.clear();
		SettingMoveAll.settingIDs.clear();
		settingNames.clear(); settingIDs.clear(); settingToggles.clear(); 
		settingDesc.clear(); settingOptions.clear();
		SettingOptions.settingOptions.clear();
		SettingList.loadSettings();
		
		}
	
	@Override
	public void initGui() {
		SBP.width = this.width;
		SBP.height = this.height;
		
		
		
		settingsMenuOpen=true;
		onOffClickFrames = 0;
		fadeIn = 0;
		fadeInFrames=0;
		
		if(width<340) {
			Utils.sendErrMsg("Your window isn't big enough for this menu! Try making a bigger window or turn down your GUI scale!");
			mc.thePlayer.closeScreen();
		}
		
		
		//CATEGORIES/UPDATE SEARCH
		totalLaunches = Integer.parseInt(""+DataGetter.find("modLaunches"));
		
		
		sliderYValue = 0;
		SettingOptions.settingOptions.clear();
		SettingOptions.selectedOption=0;
		
		//THEMES
		updateTheme();
		currActualTheme = Integer.parseInt(DataGetter.find("currentTheme")+"");
		int randNum = Integer.parseInt(Math.round(Math.random()*250)+"");
		if(randNum == 1) { 
			int randTheme = Integer.parseInt(Math.round(Math.random()*totalThemes)+"");
			currActualTheme=(randTheme-1); 
			if(!(currActualTheme == currTheme)) {
				Utils.setEasterEgg(1, Colors.GOLD+"You seem to be incredibly lucky!");
				//Utils.sendMessage(Colors.GREEN+"You have found Easter Egg #1");
			}
		}
		else { currActualTheme=Integer.parseInt(DataGetter.find("currentTheme")+""); }
		
		updateSearch();
		FPS = Minecraft.getDebugFPS();
		
		
		
		//THEME NAMES
		themeNames.clear();
		themeNames.add("Dark");
		themeNames.add("Light");
		themeNames.add("Time");
		
		//PRESET CATEGORIES
		searchPresets.clear();
		searchPresets.add("Categories");
		searchPresets.add("API");
		searchPresets.add("QOL");
		searchPresets.add("Commands");
		searchPresets.add("Dungeons");
		searchPresets.add("Dwarven Mines");
		searchPresets.add("Misc.");

		
		actualPresets.clear();
		actualPresets.add("");
		actualPresets.add("api");
		actualPresets.add("qol");
		actualPresets.add("command");
		actualPresets.add("dungeon");
		actualPresets.add("dwarven");
		actualPresets.add("misc");

		
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
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
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
		
		//Utils.print(fadeIn);
		
		int posX = this.width/2-250;
		int posY = 138-4-sliderYValue;
		int scale = 42;
		GlStateManager.enableBlend();
		try {
			GlStateManager.color(1, 1, 1, fadeIn-0.3F);
		} catch(Exception e) {
			GlStateManager.color(1, 1, 1, 0.0F);
		}
		mc.getTextureManager().bindTexture(settingBg);
		this.drawModalRectWithCustomSizedTexture(posX-32, posY-94+4, 0,0, 64, 96, 64, 96);
		this.drawModalRectWithCustomSizedTexture(posX-32, posY-94+4, 0,0, 64, 2, 64, 2);
		this.drawModalRectWithCustomSizedTexture(posX-32, posY-94+4+2, 0,0, 2, 92, 2, 92);
		this.drawModalRectWithCustomSizedTexture(posX-32+62, posY-94+4+2, 0,0, 2, 92, 2, 92);
		this.drawModalRectWithCustomSizedTexture(posX-32, posY-94+4+94, 0,0, 64, 2, 64, 2);
		

		
		
		
		
		
		
		
		
		//GlStateManager.color(1, 1, 1, fadeIn);
		
		
		//DISPLAY MOD LAUNCHES
		if(modLaunchToggle) {
			
			String launchCount = Colors.GREEN+"You have launched Minecraft with this mod "+Colors.AQUA+modLaunches+Colors.GREEN+" times!";
			int launchCountWidth = fontRendererObj.getStringWidth(launchCount);
			
			mc.getTextureManager().bindTexture(settingBorder);
			GlStateManager.enableBlend();
			try {
				GlStateManager.color(1, 1, 1, fadeIn-0.3F);
			} catch(Exception e) {
				e.printStackTrace();
				GlStateManager.color(1, 1, 1, 0.0F);
			}
			this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, launchCountWidth+6, 16, 0, 0);
			GlStateManager.color(1, 1, 1, fadeIn);
			
			mc.fontRendererObj.drawString(launchCount, 3, 4, 0x10, true);
		
		}

		
		//SCROLL WHEEL
		if(!isConfirmOpen) {
			if(mouseDWheel != 0) {if(mouseDWheel > 0) {sliderYValue-=25;} else {sliderYValue+=25;}} mouseDWheel = Mouse.getDWheel();
		}

		
		//MAKE SURE SLIDER IS ACTUALLY ON BAR
		if(sliderYValue<0) sliderYValue=0;
		else if(sliderYValue>5626) sliderYValue=5625;
		
		
		//GENERAL TEXTURE ELEMENTS
		
		//LOGO
		GlStateManager.color(1, 1, 1, fadeIn);
		mc.getTextureManager().bindTexture(logo);
		this.drawModalRectWithCustomSizedTexture(this.width/2-200, 17-sliderYValue+22, 0, 0, 400, 100, 400, 100);
		
		//SLIDER BAR
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		mc.getTextureManager().bindTexture(sliderBar);
		this.drawModalRectWithCustomSizedTexture(this.width/2+210, 15+125, 0, 0, 50, 302, 50, 302);
		
		//THEMES
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		if(mouseX >= this.width/2-308 && mouseX <= this.width/2-308+118 && mouseY >= 142-sliderYValue && mouseY <= 142-sliderYValue+20) {
			GlStateManager.color(1, 1, 1, 1);
		}

		
		
		mc.getTextureManager().bindTexture(plusMinus);
		this.drawModalRectWithCustomSizedTexture(this.width/2-308, 142-sliderYValue, 0, 0, 118, 20, 118, 20);
		int themeWidth = fontRendererObj.getStringWidth(themeNames.get(currActualTheme)+" Theme");

		
		mc.getTextureManager().bindTexture(searchBar);
		searchTextWidth = fontRendererObj.getStringWidth("Search")+4;
		resetTextWidth = fontRendererObj.getStringWidth("Reset")+12;
		
		
		//SEARCH TEXT
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		this.drawModalRectWithCustomSizedTexture(this.width/2-188, 15+125+2-sliderYValue, 0, 0, 1, 20, 375, 20);
		for(int i=0;i<searchTextWidth;i++) {
			this.drawModalRectWithCustomSizedTexture(this.width/2-188+1+i, 15+125+2-sliderYValue, 1, 0, 1, 20, 375, 20);
		}
		this.drawModalRectWithCustomSizedTexture(this.width/2-188+searchTextWidth+1, 15+125+2-sliderYValue, 0, 0, 1, 20, 375, 20);
		mc.fontRendererObj.drawString(textColor+"Search:", this.width/2-188+3, 15+125+8-sliderYValue, 0x10, false);
		
		//RESET
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		
		if(mouseX >= this.width/2-188+374-resetTextWidth-1 && mouseX <= this.width/2-188+374 && mouseY >= 15+125+2-sliderYValue && mouseY <= 15+125+2-sliderYValue+20) {
			GlStateManager.color(1, 1, 1, 1);
		}
		
		
		mc.getTextureManager().bindTexture(searchBar);
		this.drawModalRectWithCustomSizedTexture(this.width/2-188+374, 15+125+2-sliderYValue, 0, 0, 1, 20, 375, 20);
		for(int i=0;i<resetTextWidth;i++) {
			this.drawModalRectWithCustomSizedTexture(this.width/2-188+373-i, 15+125+2-sliderYValue, 333, 0, 1, 20, 375, 20);
		}
		this.drawModalRectWithCustomSizedTexture(this.width/2-188+374-resetTextWidth-1, 15+125+2-sliderYValue, 0, 0, 1, 20, 375, 20);
		mc.fontRendererObj.drawString(textColor+"Reset", this.width/2-188+375-resetTextWidth+5, 15+125+8-sliderYValue, 0x10, false);
		GlStateManager.color(1, 1, 1, fadeIn);
		
		//SEARCH BAR
		searchBarWidth = 375-searchTextWidth-resetTextWidth-4;
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		
		if(isSearchFocused || (mouseX >= this.width/2-188+searchTextWidth+2 && mouseX <= this.width/2-187+searchTextWidth+searchBarWidth && mouseY >= 15+125+2-sliderYValue && mouseY <= 15+125+2-sliderYValue+20)) {
			GlStateManager.color(1, 1, 1, 1);
		}
		
		mc.getTextureManager().bindTexture(searchBar);
		
		for(int i=0;i<searchBarWidth;i++) {
			this.drawModalRectWithCustomSizedTexture(this.width/2-188+searchTextWidth+2+i, 15+125+2-sliderYValue, 45, 0, 1, 20, 375, 20);
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
		
		//SLIDER
		try { GlStateManager.color(1, 1, 1, fadeIn-0.4F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		mc.getTextureManager().bindTexture(slider);
		this.drawModalRectWithCustomSizedTexture(this.width/2+210, 140+(sliderYValue/25), 0, 0, 50, 79, 50, 79);
		
		
		//PRESET SEARCHES
		for(int i=0;i<searchPresets.size();i++) {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
			} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
			if(mouseX >= this.width/2-188-120 && mouseX <= this.width/2-188-120+100 && mouseY >= 166+(i*23)-sliderYValue && mouseY <= 164+(i*23)+23-sliderYValue) {
				GlStateManager.color(1, 1, 1, fadeIn);
			}
			
			mc.getTextureManager().bindTexture(suggestionBar);
			this.drawModalRectWithCustomSizedTexture(this.width/2-188-120, 164+(i*23)-sliderYValue, 0, 0, 100, 25, 100, 25);
			mc.fontRendererObj.drawString(textColor+searchPresets.get(i), this.width/2-188-120+4, 164+(i*23)+8-sliderYValue, 0x10, false);
		}
		
		//STRINGS FOR TEXTURE ELEMENTS
		
		mc.fontRendererObj.drawString(textColor+editGui, this.width/2-188-20-editGuiWidth+5, this.height-44+6, 0x10, false);
		mc.fontRendererObj.drawString(textColor+resetConfig, this.width/2-188-20-resetConfigWidth+5, this.height-22+6, 0x10, false);
		mc.fontRendererObj.drawString(textColor+themeNames.get(currActualTheme)+" Theme", this.width/2-308+50-(themeWidth/2), 15+125+8-sliderYValue, 0x10, false);
		mc.fontRendererObj.drawString(textColor+(currActualTheme+1), this.width/2-308+109-(fontRendererObj.getStringWidth(currTheme+"")/2), 15+125+8-sliderYValue, 0x10, false);
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
		mc.fontRendererObj.drawString(textColor+searchSettingString+selectedIndicator, this.width/2-188+6+searchTextWidth, 15+125+8-sliderYValue, 0x10, false);
		
		//SETTING BOXES
		int x=0;
		int y=0;
		for(int i=0;i<settingNames.size();i++) {
			int toggleSwitch = 0;
			
			//SETTING BACKGROUNDS
			GlStateManager.enableBlend();
			try {
				GlStateManager.color(1, 1, 1, fadeIn-0.4F);
			} catch (Exception e) {
				GlStateManager.color(1, 1, 1, 0.0F);
			}
			mc.getTextureManager().bindTexture(settingBg);
			this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198), y*68+164-sliderYValue, 0,0, 128, 64, 128, 64);
			GlStateManager.color(1, 1, 1, fadeIn);
			
			//SETTING OPTIONS
			if(settingOptions.get(i)) {
				mc.getTextureManager().bindTexture(gear);
				
				if(mouseX >= x*135+(this.width/2-198)+96 && mouseX <= x*135+(this.width/2-198)+96+24 && mouseY >= y*68+184-sliderYValue && mouseY <= y*68+184+24-sliderYValue && settingOptions.get(i)) {
					GlStateManager.color(1, 1, 1, fadeIn);
				} else {
					try {
						GlStateManager.color(1, 1, 1, fadeIn-0.2F);
					} catch (Exception e) {
						GlStateManager.color(1, 1, 1, 0.0F);
					}
				}
				
				this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-198+96, (y*68)+184-sliderYValue, 0, 0, 24, 24, 24, 24);
			}
			
			
			//SETTING INFO
			mc.getTextureManager().bindTexture(info);
			if(mouseX >= x*135+(this.width/2)-130 && mouseX <= x*135+(this.width/2)-130+24 && mouseY >= (y*68)+184-sliderYValue && mouseY <= (y*68)+184+24-sliderYValue) {
				GlStateManager.color(1, 1, 1, fadeIn);
			} else {
				try {
					GlStateManager.color(1, 1, 1, fadeIn-0.2F);
				} catch (Exception e) {
					GlStateManager.color(1, 1, 1, 0.0F);
				}
			}
			this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-130, (y*68)+184-sliderYValue, 0, 0, 24, 24, 24, 24);
			GlStateManager.color(1, 1, 1, fadeIn);
			
			//SETTING TOGGLE
			mc.getTextureManager().bindTexture(Switch);
			if(!settingToggles.get(i)) {toggleSwitch+=20;}
			this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198)+40-20, y*68+164+22-sliderYValue, 0, toggleSwitch, 40, 20, 40, 40);
			
			//SETTING NAMES
			this.drawString(fontRendererObj, Colors.YELLOW+settingNames.get(i)+Colors.WHITE, x*135+(this.width/2-198)+6, y*68+164+6-sliderYValue, 0x10);
			
			//SETTING BORDERS
			mc.getTextureManager().bindTexture(settingBorder);
			this.drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198), y*68+164-sliderYValue, 0,0, 128, 64, 128, 64);
			
			
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
				if(mouseX >= x2*135+(this.width/2)-130 && mouseX <= x2*135+(this.width/2)-130+24 && mouseY >= (y2*68)+184-sliderYValue && mouseY <= (y2*68)+184+24-sliderYValue) {
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
			
			
			GlStateManager.color(1, 1, 1, 0.7F);
			if(mouseX >= n1 && mouseY <= n2 && mouseY >= this.height/2-1 && mouseY <= this.height/2-1+20) {
				GlStateManager.color(1, 1, 1, 1);
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
		
		
		
		
		//DRAW PLAYER CHARACTER
		Utils.renderPlayer(posX, posY, scale, mouseX, mouseY);
		
		
		//Utils.print(fadeIn);
		GlStateManager.disableBlend();
		
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
			if(mouseX >= this.width/2-188-20-editGuiWidth && mouseX <= this.width/2-188-20 && mouseY >= this.height-44 && mouseY <= this.height-44+20) {
				Utils.playClickSound();
				Minecraft.getMinecraft().displayGuiScreen(new SettingMoveAll());
			}
			
			//
			//THEME DOWN BUTTON
			else if(mouseX >= this.width/2-308 && mouseX <= this.width/2-308+20 && mouseY >= 142-sliderYValue && mouseY <= 142+20-sliderYValue) {
				if(currActualTheme > 0) {
					currTheme--;
					currActualTheme--;
					
					ConfigHandler.newObject("currentTheme", currActualTheme);
					updateTheme();
					//Utils.sendMessage(currActualTheme+" "+currTheme);
				}
				Utils.playClickSound();
			}
			
			//THEME UP BUTTON
			else if(mouseX >= this.width/2-308+80 && mouseX <= this.width/2-308+20+80 && mouseY >= 142-sliderYValue && mouseY <= 142+20-sliderYValue) {
				

				if(currActualTheme < totalThemes-1) {
					currTheme++;
					currActualTheme++;
					ConfigHandler.newObject("currentTheme", currActualTheme);
					updateTheme();
					//Utils.sendMessage(currActualTheme+" "+currTheme);
				}

				Utils.playClickSound();
			}
			
			//SLIDER CLICK CHECK
			if(mouseX >= this.width/2+210 && mouseX <= this.width/2+210+50 && mouseY >=140 && mouseY <= 140+300) {
				Utils.playClickSound();
				
				sliderYValue=((mouseY-140)*25)-100;
				return;
			}
			
			
			//CHECK IF USER IS SEARCHING
			if(mouseX >= this.width/2-188+searchTextWidth+1 && mouseX <= this.width/2-188+375-resetTextWidth-2 && mouseY >= 15+125+8-sliderYValue && mouseY <= 15+125+8+20-sliderYValue) {
				isSearchFocused=true;
				Utils.playClickSound();
			} else {
				isSearchFocused=false;
			}
			
			//CLEAR SEARCH
			if(mouseX >= this.width/2-188+375-resetTextWidth-1 && mouseX <= this.width/2-188+374 && mouseY >= 15+125+2-sliderYValue && mouseY <= 15+125+2+20-sliderYValue) {
				searchSettingString="";
				Utils.playClickSound();
				updateSearch();
				return;
			}
			
			//PRESET SEARCH ACTIONS
			for(int i=1;i<searchPresets.size();i++) {
				if(mouseX >= this.width/2-188-120 && mouseX <= this.width/2-188-120+100 && mouseY >= 164+(i*23)-sliderYValue && mouseY <= 164+(i*23)+25-sliderYValue) {
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
				if(mouseX >= x*135+(this.width/2-198)+40-20 && mouseX <= x*135+(this.width/2-198)+80-20 && mouseY >= y*68+164+22-sliderYValue && mouseY <= y*68+164+22+20-sliderYValue) {
					ConfigHandler.newObject(settingIDs.get(i), Utils.invertBoolean(settingToggles.get(i)));
					settingToggles.set(i, Utils.invertBoolean(settingToggles.get(i)));
					Utils.playClickSound();
					updateSearch();
					return;
				}
				
				//OPTIONS CLICK EVENT
				else if(mouseX >= x*135+(this.width/2-198)+96 && mouseX <= x*135+(this.width/2-198)+96+24 && mouseY >= y*68+184-sliderYValue && mouseY <= y*68+184+24-sliderYValue && settingOptions.get(i)) {
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
			mc.thePlayer.closeScreen();
			return;
		}
		//SEARCH BAR FUNCTIONALITY
		if(isSearchFocused) {
			
			//BACKSPACE
			if(par2 ==Keyboard.KEY_BACK) {
				if(searchSettingString.length()>=1 ) {
					searchSettingString = Utils.removeLastChars(searchSettingString, 1);
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
			settingToggles.add((Boolean) DataGetter.find(id));
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
		
		for(int i=0;i<type.length;i++) {
			if(type[i].startsWith("moveGUI:")) {
				
				if((Boolean) DataGetter.find(id)) {
					try {
						String coords = type[i].replace("moveGUI: ", "");
					
						String id1 = optionID[i];
						
						if(id1.equals("boxSolverToggle") || id1.equals("iceSolverToggle")) {
							id1 = "puzzle";
						}
							
						
						
						
						int x = Integer.parseInt(DataGetter.find(id1+"X")+"");
						int y = Integer.parseInt(DataGetter.find(id1+"Y")+"");
						coords+=";"+x+";"+y;
						SettingMoveAll.settingCoords.add(coords);
						SettingMoveAll.settingNames.add(name);
						SettingMoveAll.settingIDs.add(id1);
					} catch(Exception e) {Utils.sendErrMsg("ERRR"); e.printStackTrace();}
				} 

			}
		}
		//Utils.sendErrMsg(Colors.RED+"-------------------------");
		if(name.toLowerCase().contains(searchSettingString.toLowerCase()) || Utils.checkIfArrayContains(array, searchSettingString.toLowerCase())) {
			

			//GIVES ALL BASIC INFORMATION FOR SETTING
			settingNames.add(name);
			settingToggles.add((Boolean) DataGetter.find(id));
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
		
		currActualTheme = Integer.parseInt(DataGetter.find("currentTheme")+"");
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


	
	
}
