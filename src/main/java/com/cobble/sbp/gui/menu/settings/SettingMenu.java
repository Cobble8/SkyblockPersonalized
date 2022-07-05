package com.cobble.sbp.gui.menu.settings;

import com.cobble.sbp.core.SettingList;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.handlers.AnimationHandler;
import com.cobble.sbp.simplejson.JSONArray;
import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.simplejson.parser.JSONParser;
import com.cobble.sbp.threads.misc.LaunchThread;
import com.cobble.sbp.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SettingMenu extends GuiScreen {

	//SETTING ARRAYS
	public static ArrayList<String> settingNames = new ArrayList<>();
	public static ArrayList<String> settingIDs = new ArrayList<>();
	public static ArrayList<Boolean> settingToggles = new ArrayList<>();
	public static ArrayList<String> settingDesc = new ArrayList<>();
	public static ArrayList<Boolean> settingOptions = new ArrayList<>();
	public static ArrayList<String> settingVersions = new ArrayList<>();
	private static final ArrayList<String> searchPresets = new ArrayList<>();
	private static final ArrayList<String> actualPresets = new ArrayList<>();
	public static ArrayList<String> themeNames = new ArrayList<>();

	//SUBOPTIONS VARIABLES
	public static int clickedSubOption = -1;
    public static String currSettingMenu = "main";
    private static int openSubOption = -1;
	private static int openSubX = -1;
	private static int openSubY = -1;
	
	//GLOBAL VARIABLES
	public static String searchSettingString = "";
	private static final Boolean isSearchFocused = false;
	
	//SLIDER VARIABLES
	private static int sliderYValue = 0;
	private static int mouseDWheel = 0;


	private static String confirmCancelCoords = "0,0,0,0";
	private static Boolean isConfirmOpen = false;

	public static float fadeIn = 0;
	public static int fadeInFrames = 0;

	public static int modLaunches = DataGetter.findInt("core.launchCounter.count");
	public static Boolean modLaunchToggle = DataGetter.findBool("core.launchCounter.toggle");
	
	public static Boolean settingsMenuOpen = false;
	int l = 0;
	
	private static Boolean mouseDown = false;
	private static int mouseDownX = 0;
	private static int mouseDownY = 0;
	private static int sliderOffset = 0;
	
	private static final ArrayList<String> buttonList = new ArrayList<>();
	
	//UPDATES THE SEARCH FOR FEATURES
	public static void updateSearch() {
		SettingMoveAll.settingCoords.clear(); SettingMoveAll.settingNames.clear(); SettingMoveAll.settingIDs.clear(); SettingMoveAll.settingWidths.clear(); SettingMoveAll.settingHeights.clear();
		settingNames.clear(); settingIDs.clear(); settingToggles.clear();  settingDesc.clear(); settingOptions.clear(); settingVersions.clear();
		SettingOptions.settingOptions.clear(); searchPresets.clear(); actualPresets.clear();
		buttonList.clear();

		
		reloadValues();
		}
	
	public static void reloadValues() {
		SettingList.loadSettings();
		buttonList.add("Global Settings");
		buttonList.add("Open Config Folder");
		buttonList.add("Edit GUI Locations");
		buttonList.add("Reset Config");
		buttonList.add("Changelogs");
	}
	
	@Override
	public void initGui() {
		l=0;
		//isSearchFocused = true;
		mouseDown=false;
		
		settingsMenuOpen=true;

		updateSearch();
		sliderYValue = 0;
		sliderOffset = 0;
		clickedSubOption=-1;
		openSubOption=-1;
		
		//CATEGORIES/UPDATE SEARCH
		//MISC
		themeNames.add("Dark");
		themeNames.add("Light");
		themeNames.add("Time");
		
		
		resetTextBoxes();
		addTextBox(0, Colors.GRAY+searchSettingString, this.width/2-188+2, 15+125+2, 375-42);
		
		
		
		SettingOptions.settingOptions.clear();
		SettingOptions.selectedOption=0;


		reloadPopup();
		super.initGui();

	}
	
	//RESOURCES

	private static String popupText = "";


	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		GlStateManager.enableBlend();

		GlStateManager.pushMatrix();
		
		int sliderHeight = settingNames.size();
		int settingNumMinus = sliderHeight%3;
		if(settingNumMinus==0) {settingNumMinus=3;}
		sliderHeight = (sliderHeight-settingNumMinus)*68/3;

		GlStateManager.color(1, 1, 1, 0.8F);
		drawTextBox(0, Resources.searchBar, 20);
		if(settingNames.size()<=3) { sliderHeight=0; }
		
		//SCROLL WHEEL
		if(sliderHeight > 0 && sliderHeight > this.height-(15+125+2) && openSubOption == -1) {
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
		} else if(l >= Minecraft.getDebugFPS()*5) {
			l=0;
		}
		
		String textColor = Colors.WHITE;

		fadeIn=1;


		GlStateManager.enableBlend();
		String rawGetText = TextUtils.unformatAllText(getText(0));
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
			mc.getTextureManager().bindTexture(Resources.settingBg);
			
			try { GlStateManager.color(1, 1, 1, fadeIn-0.3F); } catch(Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
			drawModalRectWithCustomSizedTexture(0, 0, 0, 0, launchCountWidth+6, 16, 1, 1);
			GlStateManager.color(1, 1, 1, fadeIn);
			
			mc.fontRendererObj.drawString(launchCount, 3, 4, 0x10, true);
		
		}

		
		//DRAW PLAYER CHARACTER
		int posX = this.width/2-250;
		int posY = 138-4;
		int scale = 40;
		GlStateManager.enableBlend();
		
		try { GlStateManager.color(1, 1, 1, fadeIn-0.3F); } catch(Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		mc.getTextureManager().bindTexture(Resources.settingBg);
		
		drawModalRectWithCustomSizedTexture(posX-32, posY-90, 0, 0, 33, 33, 92, 55);
		drawModalRectWithCustomSizedTexture(posX-32+66-33, posY-90, 92-33, 0, 33, 33, 92, 55);
		drawModalRectWithCustomSizedTexture(posX-32, posY+4-33, 0, 22, 33, 33, 92, 55);
		drawModalRectWithCustomSizedTexture(posX-32+66-33, posY+4-33, 92-33, 22, 33, 33, 92, 55);
		drawModalRectWithCustomSizedTexture(posX-32, posY-90+33, 0, 16, 33, 28, 92, 55);
		drawModalRectWithCustomSizedTexture(posX-32+33, posY-90+33, 92-33, 16, 33, 28, 92, 55);
		
		mc.getTextureManager().bindTexture(Resources.settingBorder);
		drawModalRectWithCustomSizedTexture(posX-32, posY-94+4, 0,0, 66, 2, 370, 223);
		drawModalRectWithCustomSizedTexture(posX-32, posY-94+4+2, 0,0, 2, 92, 370, 223);
		drawModalRectWithCustomSizedTexture(posX-32+64, posY-94+4+2, 0,0, 2, 92, 370, 223);
		drawModalRectWithCustomSizedTexture(posX-30, posY+2, 0,0, 62, 2, 370, 223);
		

		
		//GENERAL TEXTURE ELEMENTS
		GlStateManager.enableBlend();
		//LOGO
		GlStateManager.color(1, 1, 1, fadeIn);
		mc.getTextureManager().bindTexture(Resources.logo);
		drawModalRectWithCustomSizedTexture(this.width/2-200, 17+22, 0, 0, 400, 100, 400, 100);
		mc.fontRendererObj.drawString(Colors.YELLOW+"Version: "+Colors.AQUA+Reference.VERSION, this.width/2+194-(mc.fontRendererObj.getStringWidth("Version: "+Reference.VERSION)), 17+22+100-14-11, 0x1f99fa, false);
		mc.fontRendererObj.drawString(Colors.YELLOW+"Made by "+Colors.RESET+"Cobble8", this.width/2+194-(mc.fontRendererObj.getStringWidth("Made by Cobble8")), 17+22+100-14, 0x1f99fa, false);
		
		
		
		//DISCORD AND GITHUB
		String hoveringText = "";
		if(mouseX >= this.width/2+200-20-17 && mouseX <= this.width/2+200-20-17+16 && mouseY >= 17+22+13-8 && mouseY <= 17+22+13-8+16) {
			GlStateManager.color(1, 1, 1, 1); hoveringText = "discord";
		} else {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.2F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		}
		mc.getTextureManager().bindTexture(Resources.discord);
		drawModalRectWithCustomSizedTexture(this.width/2+200-20-17, 17+22+13-8, 0, 0, 16, 16, 16, 16);
		
		
		if(mouseX >= this.width/2+200-20 && mouseX <= this.width/2+200-20+16 && mouseY >= 17+22+13-8 && mouseY <= 17+22+13-8+16) {
			GlStateManager.color(1, 1, 1, 1); hoveringText = "github";
		} else {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.2F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		}
		mc.getTextureManager().bindTexture(Resources.github);
		drawModalRectWithCustomSizedTexture(this.width/2+200-20, 17+22+13-8, 0, 0, 16, 16, 16, 16);
		
		
		
		//SLIDER BAR
		try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		mc.getTextureManager().bindTexture(Resources.sliderBar);
		drawModalRectWithCustomSizedTexture(this.width/2+210, 15+125, 0, 0, 25, 302, 25, 302);
		
		//PopupText
		GuiUtils.drawHangingString(popupText, 286, 45, 0);
		
		mc.getTextureManager().bindTexture(Resources.searchBar);
		
		//SEARCH BAR

		if(isSearchFocused || (mouseX >= this.width/2-188+2 && mouseX <= this.width/2-188+2+375-43 && mouseY >= 15+125+2 && mouseY <= 15+125+2+20)) {
			GlStateManager.color(1, 1, 1, 1);
		} else {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.1F);
			} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		}
		
		mc.getTextureManager().bindTexture(Resources.searchBar);

		//RESET BUTTON
		if(mouseX >= this.width/2-188+2+375-43 && mouseX <= this.width/2-188+2+375 && mouseY >= 15+125+2 && mouseY <= 15+125+2+20) {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.1F);
			} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		} else {
			try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
			} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		}
		drawModalRectWithCustomSizedTexture(this.width/2-188+2+375-43, 15+125+2, 332, 0, 43, 20, 375, 20);
		
		
		//DRAW SEARCH TEXT
		GuiUtils.drawString(Colors.WHITE+"Clear", this.width/2-188+3+375-36, 15+125+8, 0);
		
		
		//SLIDER
		try {

			int settingCount = settingNames.size()/3;
			if(mouseDownX >= this.width/2+210 && mouseDownX <= this.width/2+235 && mouseDownY >= 140 && mouseDownY <= 140+302 && mouseDown && sliderHeight > this.height-(15+125+2) && !isConfirmOpen && openSubOption == -1) {

				int mouseYOffset = 442-(mouseY-20);
					if(mouseYOffset < 0) {mouseYOffset = 0;} else if(mouseYOffset > 302) {mouseYOffset = 302;} mouseYOffset = 302-mouseYOffset;
					int mousePercent = 1000*mouseYOffset/(302-40);
					sliderOffset = (302-40)*mousePercent/1000;
					if(sliderOffset > (302-40)) {sliderOffset = 302-40;}
					sliderYValue = (68*settingCount)*mousePercent/1000;
				} else {
				if(!(settingCount % 3 == 0)) {settingCount++;}
					int totalSettingHeight = settingCount*68;
					int percentDown = 1000*sliderYValue/totalSettingHeight;
					sliderOffset = (302-40)*percentDown/1000;
					if(sliderOffset >= 302-40) {sliderOffset = 302-40;}

					
					try { GlStateManager.color(1, 1, 1, fadeIn-0.4F);
					} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
				}
				
					//SLIDER
					
					mc.getTextureManager().bindTexture(Resources.slider);
					
					drawModalRectWithCustomSizedTexture(this.width/2+210, 140+(sliderOffset), 0, 0, 25, 40, 25, 40);
					
					
				} catch(Exception e) { mc.getTextureManager().bindTexture(Resources.slider); drawModalRectWithCustomSizedTexture(this.width/2+210, 140+(sliderOffset), 0, 0, 25, 40, 25, 40);}
				//Utils.print(sliderHeight);
		//PRESET SEARCHES
		int lastButton = 0;
		for(int i=0;i<searchPresets.size();i++) {
			try { GlStateManager.color(0.8F, 0.8F, 0.8F, fadeIn-0.2F);
			} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
			if(mouseX >= this.width/2-188-120 && mouseX <= this.width/2-188-120+100 && mouseY >= 166+(i*12) && mouseY <= 164+(i*12)+12 && i !=0) {
				GlStateManager.color(1, 1, 1, fadeIn);
			}
			
			mc.getTextureManager().bindTexture(Resources.suggestionBar);
			drawModalRectWithCustomSizedTexture(this.width/2-188-120, 164+(i*12), 0, 0, 100, 14, 100, 14);
			String selected = "";

			if(searchSettingString.equals(actualPresets.get(i)) && i != 0) {
				selected = Colors.UNDERLINE;
			}

			mc.fontRendererObj.drawString(textColor+selected+searchPresets.get(i), this.width/2-188-120+4, 164+(i*12)+3, 0x10, false);
			lastButton = 164+(i*12)+16;
		}
		
		for(int i=0;i<buttonList.size();i++) {
			drawButton(textColor+buttonList.get(i), this.width/2-188-20, lastButton+(i*22), mouseX, mouseY);
		}
		

		
		
		
		//SETTING BOXES
		int x=0;
		int y=0;
		boolean foundClicked = false;
		String drawnSubOption = "";
		HashMap<String, String> typeInfo = new HashMap<>();
		for(int i=0;i<settingNames.size();i++) {
			int toggleSwitch = 0;



			//SETTING BACKGROUNDS
			
			
			int currYPos = y*68+164-sliderYValue;
			if(currYPos > this.height) { break; }
			
			else if(currYPos > 163) {
				
			
			
				//GlStateManager.enableBlend();
				try { GlStateManager.color(1, 1, 1, fadeIn-0.4F);
				} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
				mc.getTextureManager().bindTexture(Resources.settingBg);
			drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198), currYPos, 0,0, 128, 64, 128, 64);
			GlStateManager.color(1, 1, 1, fadeIn);
			
			//SETTING OPTIONS
			if(settingOptions.get(i)) {
				mc.getTextureManager().bindTexture(Resources.gear);
				
				if((mouseX >= x*135+(this.width/2-198)+96 && mouseX <= x*135+(this.width/2-198)+96+24 && mouseY >= y*68+184-sliderYValue && mouseY <= y*68+184+24-sliderYValue && settingOptions.get(i) && clickedSubOption==-1) || clickedSubOption==i) {
				GlStateManager.color(1, 1, 1, fadeIn); } else { try { GlStateManager.color(1, 1, 1, fadeIn-0.2F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); } }
				
				drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-198+96, (y*68)+184-sliderYValue, 0, 0, 24, 24, 24, 24);
				
			}
			
			
			//SETTING INFO
			mc.getTextureManager().bindTexture(Resources.info);
			if(mouseX >= x*135+(this.width/2)-130 && mouseX <= x*135+(this.width/2)-130+24 && mouseY >= (y*68)+184-sliderYValue && mouseY <= (y*68)+184+24-sliderYValue) {
				GlStateManager.color(1, 1, 1, fadeIn);

			} else {
				try { GlStateManager.color(1, 1, 1, fadeIn-0.2F);
				} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
			}
			drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-130, (y*68)+184-sliderYValue, 0, 0, 24, 24, 24, 24);
			GlStateManager.color(1, 1, 1, fadeIn);
			
			//SETTING TOGGLE
			mc.getTextureManager().bindTexture(Resources.Switch);
			if(!settingToggles.get(i)) {toggleSwitch+=20;}
			drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198)+40-20, y*68+164+22-sliderYValue, 0, toggleSwitch, 40, 20, 40, 40);
			
			//SETTING NAMES

				String subsettings = "";
				if(clickedSubOption == i) {subsettings = Colors.UNDERLINE;}
			GuiUtils.drawConfinedString(Colors.YELLOW+subsettings+settingNames.get(i)+Colors.WHITE, x*135+(this.width/2-198)+6, y*68+164+6-sliderYValue, 0, 116);
			
			//SETTING BORDERS
			mc.getTextureManager().bindTexture(Resources.settingBorder);
			drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198), y*68+164-sliderYValue, 0,0, 128, 64, 128, 64);
				//Utils.drawString(Colors.CHROMA+clickedSubOption+" : "+(clickedSubOption/3), x*135+(this.width/2-198)+4, y*68+164-sliderYValue+52);
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
					mc.getTextureManager().bindTexture(Resources.settingBg);
					drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198), 164, 0, 64-yOffset, 128, yOffset, 128, 64);
				
					GlStateManager.color(1, 1, 1, fadeIn);
					mc.getTextureManager().bindTexture(Resources.settingBorder);
					drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198), 164, 0, 64-yOffset, 128, yOffset, 128, 64);
					
					
					
					//SETTING INFO
					mc.getTextureManager().bindTexture(Resources.info);
					if(mouseX >= x*135+(this.width/2)-130 && mouseX <= x*135+(this.width/2)-130+24 && mouseY >= (y*68)+184-sliderYValue && mouseY <= (y*68)+184+24-sliderYValue && mouseY >= 164) {
					GlStateManager.color(1, 1, 1, fadeIn); } else { try { GlStateManager.color(1, 1, 1, fadeIn-0.2F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); } }
						
						if(yOffset > 20 && yOffset < 44) {
							int infoOffset = 164-((y*68)+184-sliderYValue);
							drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-130, (y*68)+184-sliderYValue+infoOffset, 0, infoOffset, 24, 24-infoOffset, 24, 24);
							GlStateManager.color(1, 1, 1, fadeIn);
						} else if(yOffset > 20 ) {
							drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-130, (y*68)+184-sliderYValue, 0, 0, 24, 24, 24, 24);
							GlStateManager.color(1, 1, 1, fadeIn);
						}
						
						
						if(settingOptions.get(i)) { mc.getTextureManager().bindTexture(Resources.gear);
							if(mouseX >= x*135+(this.width/2-198)+96 && mouseX <= x*135+(this.width/2-198)+96+24 && mouseY >= y*68+184-sliderYValue && mouseY <= y*68+184+24-sliderYValue && settingOptions.get(i) && mouseY >= 164) {
							GlStateManager.color(1, 1, 1, fadeIn); } else { try { GlStateManager.color(1, 1, 1, fadeIn-0.2F); } catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); } }
							//
							if(yOffset > 20 && yOffset < 44) {
								int infoOffset = 164-((y*68)+184-sliderYValue);
								drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-198+96, (y*68)+184-sliderYValue+infoOffset, 0, infoOffset, 24, 24-infoOffset, 24, 24);
								GlStateManager.color(1, 1, 1, fadeIn);
							} else if(yOffset > 20 ) {
								drawModalRectWithCustomSizedTexture(x*135+(this.width/2)-198+96, (y*68)+184-sliderYValue, 0, 0, 24, 24, 24, 24);
								GlStateManager.color(1, 1, 1, fadeIn);
							} }
						
						
						mc.getTextureManager().bindTexture(Resources.Switch);
						if(!settingToggles.get(i)) {toggleSwitch+=20;}
						if(yOffset > 20 && yOffset < 44) {
							int infoOffset = 164-((y*68)+184-sliderYValue+2);
							drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198)+40-20, (y*68)+184-sliderYValue+infoOffset+2, 0, infoOffset+toggleSwitch, 40, 20-infoOffset, 40, 40);
							GlStateManager.color(1, 1, 1, fadeIn);
						} else if(yOffset > 20 ) {
							drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198)+40-20, (y*68)+184-sliderYValue+2, 0, toggleSwitch, 40, 20, 40, 40);
							GlStateManager.color(1, 1, 1, fadeIn);
						}
					
						mc.getTextureManager().bindTexture(Resources.settingBorder);
						drawModalRectWithCustomSizedTexture(x*135+(this.width/2-198), 164, 0, 0, 128, 2, 128, 64);

						String subsettings = "";
						if(clickedSubOption == i) {subsettings = Colors.UNDERLINE;}
						if((y*68)+184-sliderYValue+30 > 166) { GuiUtils.drawConfinedString(Colors.YELLOW+subsettings+settingNames.get(i)+Colors.WHITE, x*135+(this.width/2-198)+6, (y*68)+184-sliderYValue+30, 0, 116);}
						
				}
				
			}
			//SETTING FORMATTER



			x++;
			if(x == 3) {
				x=0;y++;
				if(!foundClicked && y > clickedSubOption/3 && clickedSubOption != -1) {
					y++;
					foundClicked=true;
				}
			}

			//Utils.print(i+" : "+clickedSubOption);

			if(foundClicked && i/3 == clickedSubOption/3) {


				try {

					int cX = 0;
					int cY = 0;
					int max = 3;
					int tmpLengthFix = SettingOptions.settingOptions.get(clickedSubOption).get(0).length;
					while(tmpLengthFix > 9) {
						tmpLengthFix-=3;
						max++;
					}

					for(int j=0;j<SettingOptions.settingOptions.get(clickedSubOption).get(0).length;j++) {
						//163
						if((y*68)+(cY*20)+164-sliderYValue-66 < 163) {cY++; if(cY == max) { cY=0; cX++; }continue;}

						int checked = 0;
						String type = SettingOptions.settingOptions.get(clickedSubOption).get(0)[j];
						String id = SettingOptions.settingOptions.get(clickedSubOption).get(2)[j];
						GlStateManager.pushMatrix();

						if(type.equals("boolean") && DataGetter.findBool(id)) {
							checked=16;
						} else if(type.equals("color")) {
							mc.getTextureManager().bindTexture(Resources.blank);
							Colors.setColor(DataGetter.findStr(id));

							if(openSubOption == j) {
								drawnSubOption="color";
							}

							drawModalRectWithCustomSizedTexture(cX*135+(this.width/2-198), (y*68)+(cY*20)+164-sliderYValue-66, 0, checked, 16 ,16, 16 ,32);
						} else if(type.equals("textColor")) {
							mc.getTextureManager().bindTexture(Resources.blank);
							try {
								Colors.setColor(Colors.colorArrayToString(Colors.getRGBFromColorCode(DataGetter.findStr(id).substring(0, 2))));
							} catch(Exception ignored){}

							//openSubOption=i;
							if(openSubOption == j) {
								drawnSubOption="textColor";
							}

							drawModalRectWithCustomSizedTexture(cX*135+(this.width/2-198), (y*68)+(cY*20)+164-sliderYValue-66, 0, checked, 16 ,16, 16 ,32);
						} else if(type.equals("string")) {
							StringBuilder newStr = new StringBuilder();
							if(mc.fontRendererObj.getStringWidth(DataGetter.findStr(id)) < 13) {
								newStr.append(DataGetter.findStr(id));
							} else {
								char[] curr = DataGetter.findStr(id).toCharArray();
								int k=0;
								while(mc.fontRendererObj.getStringWidth(newStr+"...") < 12) {
									try {
										newStr.append(curr[k]);
										k++;
									} catch(Exception e) {
										break;
									}
								}
								newStr.append("...");
							}
							GuiUtils.drawString(Colors.YELLOW+newStr, cX*135+(this.width/2-198)+2, (y*68)+(cY*20)+164-sliderYValue-66+4, 1);
						} else if(type.startsWith("int")) {
							int num = DataGetter.findInt(id);
							String str = num+"";
							if(num >= 1000) {num/=1000; str = num+"k";}
							int digOff = 3;
							GlStateManager.pushMatrix();
							if(str.length() > 2) {
								digOff = 0;
								GlStateManager.translate(0, 0, 1);
							}
							GuiUtils.drawString(Colors.YELLOW+str, cX*135+(this.width/2-198)+digOff, (y*68)+(cY*20)+164-sliderYValue-66+4, 1);
							GlStateManager.popMatrix();

						} else if(type.equals("size")) {
							if(openSubOption == j) {
								drawnSubOption="size";
							}
							double size = (double)DataGetter.findInt(id)/10D;
							GuiUtils.drawConfinedString(Colors.YELLOW+size, cX*135+(this.width/2-198)+1, (y*68)+(cY*20)+164-sliderYValue-66+4, 1, 14);
						}
						if(openSubOption == j) {
							if(type.equals("string")) {drawnSubOption="string";}
							else if (type.startsWith("int")) {drawnSubOption="int"; typeInfo.put(id, type);}
						}
						if(mouseX >= cX*135+(this.width/2-198) && mouseX <= cX*135+(this.width/2-198)+16 && mouseY >= ((y*68)+(cY*20)+164-sliderYValue-66) && mouseY <= ((y*68)+(cY*20)+164-sliderYValue-66)+16) {
							GlStateManager.color(1, 1, 1, 1);
						} else {
							GlStateManager.color(1, 1, 1, 0.7f);
						}
						mc.getTextureManager().bindTexture(Resources.checkbox);
						drawModalRectWithCustomSizedTexture(cX*135+(this.width/2-198), (y*68)+(cY*20)+164-sliderYValue-66, 0, checked, 16 ,16, 16 ,32);
						GlStateManager.popMatrix();
						try {
							//Utils.drawString(Colors.YELLOW+SettingOptions.settingOptions.get(clickedSubOption).get(1)[j], cX*135+(this.width/2-198)+19, (y*68)+(cY*20)+164-sliderYValue-62);


							GuiUtils.drawConfinedString(Colors.YELLOW+SettingOptions.settingOptions.get(clickedSubOption).get(1)[j], cX*135+(this.width/2-198)+19, (y*68)+(cY*20)+164-sliderYValue-62, 1, 112);
						} catch(Exception e) {
							//e.printStackTrace();
						}

						cY++;
						if(cY == max) {
							cY=0; cX++;
						}
					}

				} catch(Exception e) {
					//e.printStackTrace();
				}




			}

			
			
		}
		try {
			String id = SettingOptions.settingOptions.get(clickedSubOption).get(2)[openSubOption];

			switch (drawnSubOption) {
				case "textColor":
					GlStateManager.pushMatrix();
					GlStateManager.translate(0, 0, 10);
					int clrX = openSubX;
					int clrY = openSubY;
					mc.getTextureManager().bindTexture(Resources.blank);
					GlStateManager.color(0, 0, 0, 0.8f);

					drawModalRectWithCustomSizedTexture(clrX - 2, clrY - 2, 0, 0, 92, 92, 1, 1);
					String currColor;
					try {
						currColor = DataGetter.findStr(id);
					} catch (Exception e) {
						currColor = "&f";
					}


					for (int h = 0; h < 5; h++) {
						for (int v = 0; v < 4; v++) {
							if (v * 5 + h <= 16) {

								mc.getTextureManager().bindTexture(Resources.blank);
								if (mouseX > clrX + (h * 18) && mouseX <= clrX + (h * 18) + 16 && mouseY > clrY + (v * 18) && mouseY <= clrY + (v * 18) + 16 || currColor.contains(Colors.getColorFromInt(v * 5 + h).substring(1))) {
									Colors.setColor(Colors.colorArrayToString(Colors.getRGBFromColorCode(Colors.getColorFromInt(v * 5 + h))));
								} else {
									float alpha = 0.7F;
									String clr = Colors.colorArrayToString(Colors.getRGBFromColorCode(Colors.getColorFromInt(v * 5 + h)));
									clr = clr.substring(0, clr.lastIndexOf(";"));
									clr += ";" + alpha;
									Colors.setColor(clr);
								}
								drawModalRectWithCustomSizedTexture(clrX + (h * 18), clrY + (v * 18), 0, 0, 16, 16, 16, 32);
							}
							Colors.resetColor();
							mc.getTextureManager().bindTexture(Resources.checkbox);
							drawModalRectWithCustomSizedTexture(clrX + (h * 18), clrY + (v * 18), 0, 0, 16, 16, 16, 32);
						}
					}
					String bold = Colors.RED;
					String italic = Colors.RED;
					String underline = Colors.RED;
					if (currColor.contains("&l")) {
						bold = Colors.GREEN;
					}
					if (currColor.contains("&o")) {
						italic = Colors.GREEN;
					}
					if (currColor.contains("&n")) {
						underline = Colors.GREEN;
					}
					int center = mc.fontRendererObj.getStringWidth(Colors.BOLD + "B") / 2;
					GuiUtils.drawString(bold + Colors.BOLD + "B", clrX + 44 - center, clrY + (18 * 3) + 4);
					GuiUtils.drawString(italic + Colors.ITALIC + "I", clrX + 63 - center, clrY + (18 * 3) + 4);
					GuiUtils.drawString(underline + Colors.UNDERLINE + "U", clrX + 80 - center, clrY + (18 * 3) + 4);
					currColor = currColor.replace("&", Reference.COLOR_CODE_CHAR + "");
					//Utils.print(currColor+"Sample Text");
					GuiUtils.drawString(currColor + "Sample Text", clrX + 45 - (mc.fontRendererObj.getStringWidth(currColor + "Sample Text") / 2), clrY + (18 * 4) + 4);
					//Utils.print(currColor);
					GlStateManager.popMatrix();
					break;
				case "string":
					mc.getTextureManager().bindTexture(Resources.basicTextBox);
					String currText = DataGetter.findStr(id);
					//if(drawnSubOption.equals("int")) { currText = DataGetter.findInt(id)+""; }

					int stringWidth = mc.fontRendererObj.getStringWidth(currText);
					if (stringWidth < 120) {
						drawModalRectWithCustomSizedTexture(openSubX, openSubY, 0, 0, 4, 20, 128, 20);
						drawModalRectWithCustomSizedTexture(openSubX + 4, openSubY, 4, 0, stringWidth, 20, 128, 20);
						drawModalRectWithCustomSizedTexture(openSubX + stringWidth + 4, openSubY, 124, 0, 4, 20, 128, 20);
						GuiUtils.drawString(Colors.WHITE + currText, openSubX + 4, openSubY + 6, 1);
					} else {
						drawModalRectWithCustomSizedTexture(openSubX, openSubY, 0, 0, 128, 20, 128, 20);
						GuiUtils.drawConfinedString(Colors.WHITE + currText, openSubX + 4, openSubY + 6, 1, 120);
					}


					break;

				case "int":
					try {
						GlStateManager.pushMatrix();
						String info = typeInfo.get(id);
						int numMin = Integer.parseInt(info.substring(info.indexOf(":")+1, info.indexOf(",")));
						int numMax = Integer.parseInt(info.substring(info.indexOf(",")+1));
						int numCur = DataGetter.findInt(id);
						double percent = ((double) (numCur-numMin)) / ((double) (numMax-numMin));
						int sliderXOff = (int) (72d*percent);

						GlStateManager.translate(0, 0, 10);
						mc.getTextureManager().bindTexture(Resources.blank);
						GlStateManager.color(0, 0, 0, 0.7f);
						drawModalRectWithCustomSizedTexture(openSubX, openSubY-15, 0, 0, 80, 15, 1, 1);
						String minStr = numMin+""; if(numMin > 1000) {minStr = minStr.substring(0, minStr.length()-4)+"k";}
						String maxStr = numMax+""; if(numMax > 1000) {maxStr = maxStr.substring(0, maxStr.length()-4)+"k";}
						GuiUtils.drawString(Colors.AQUA+minStr, openSubX+2, openSubY-11, 1);
						GuiUtils.drawString(Colors.AQUA+numCur, openSubX+40-(mc.fontRendererObj.getStringWidth(numCur+"")/2), openSubY-11, 1);
						GuiUtils.drawString(Colors.AQUA+maxStr, openSubX+78-mc.fontRendererObj.getStringWidth(maxStr), openSubY-11, 1);

						Colors.resetColor();
						mc.getTextureManager().bindTexture(Resources.numberSlider);
						drawModalRectWithCustomSizedTexture(openSubX, openSubY, 0, 0, 80, 20, 90, 20);
						if(sliderXOff > 70) {sliderXOff = 70;}
						drawModalRectWithCustomSizedTexture(openSubX + sliderXOff + 1, openSubY, 82, 20, 8, 20, 90, 20);
						GlStateManager.popMatrix();

						boolean down = Mouse.isButtonDown(0) || Mouse.isButtonDown(1);
						if(down) {
							if(mouseX >= openSubX && mouseX <= openSubX+80 && mouseY >= openSubY && mouseY <= openSubY+20) {
								int off = mouseX-openSubX;
								if(off < 4) {off = 4;}
								else if(off > 76) {off = 76;}
								off-=4;
								double perc = ((double) off)/72d;
								int fin = (int) ((perc*(numMax-numMin))+numMin);
								ConfigHandler.newObject(id, fin);
							}
						}

					} catch(Exception e) { e.printStackTrace(); }
					Colors.resetColor();
					break;
				case "size":
					mc.getTextureManager().bindTexture(Resources.plusMinus);


					if (mouseX > openSubX && mouseX <= openSubX + 16 && mouseY > openSubY && mouseY <= openSubY + 16) {
						GlStateManager.color(1, 1, 1, 1);
					} else {
						GlStateManager.color(1, 1, 1, 0.9f);
					}
					drawModalRectWithCustomSizedTexture(openSubX, openSubY, 0, 0, 16, 16, 94.4f, 16);

					if (mouseX > openSubX + 34 && mouseX <= openSubX + 16 + 34 && mouseY > openSubY && mouseY <= openSubY + 16) {
						GlStateManager.color(1, 1, 1, 1);
					} else {
						GlStateManager.color(1, 1, 1, 0.9f);
					}
					drawModalRectWithCustomSizedTexture(openSubX + 34, openSubY, 63.9f, 0, 16, 16, 94.4f, 16);
					break;
				case "color":

					try {


						mc.getTextureManager().bindTexture(Resources.blank);
						GlStateManager.color(0.2f, 0.2f, 0.2f, 0.8f);

						drawModalRectWithCustomSizedTexture(openSubX-5, openSubY-5, 0, 0, 95, 80, 1, 1);


						String[] testColor = DataGetter.findStr(id).split(";");
						float r = Float.parseFloat(testColor[0]);
						float g = Float.parseFloat(testColor[1]);
						float b = Float.parseFloat(testColor[2]);
						float a;
						try { a = Float.parseFloat(testColor[3]);
						} catch(Exception e) { a=1; }

						float[] tmp10 = {0.0f, 0.0f, 0.0f};
						float[] hsbHue = Color.RGBtoHSB((int) (r*255), (int) (g*255), (int) (b*255), tmp10);

						//HUE
						for(float i=0;i<87;i++) {
							Color hueClr = Color.getHSBColor(i/85f, hsbHue[1], hsbHue[2]);


							float r2 = hueClr.getRed()/255f;
							float g2 = hueClr.getGreen()/255f;
							float b2 = hueClr.getBlue()/255f;


							mc.getTextureManager().bindTexture(Resources.blank);
							GlStateManager.color(r2, g2, b2, 1);
							drawModalRectWithCustomSizedTexture((int) (openSubX-1+i), openSubY-1, 0, 0, 1, 12, 1, 1);
						}
						mc.getTextureManager().bindTexture(Resources.colorSelector);
						Colors.resetColor();
						drawModalRectWithCustomSizedTexture(openSubX-1+(int) (hsbHue[0]*87f), openSubY-1, 0, 0, 3, 12, 3, 12);
						mc.getTextureManager().bindTexture(Resources.colorBg);
						drawModalRectWithCustomSizedTexture(openSubX-2, openSubY-2, 0, 0, 89, 14, 89, 28);

						//BRIGHTNESS
						for(float i=0;i<87;i++) {

							mc.getTextureManager().bindTexture(Resources.blank);
							float r2 = r-(i/87f);
							float g2 = g-(i/87f);
							float b2 = b-(i/87f);
							if(r2 < 0) {r2=0;}
							if(g2 < 0) {g2=0;}
							if(b2 < 0) {b2=0;}

							GlStateManager.color(r2, g2, b2, 1);
							drawModalRectWithCustomSizedTexture((int) (openSubX+85-i), openSubY-1+15, 0, 0, 1, 12, 1, 1);
						}
						mc.getTextureManager().bindTexture(Resources.colorSelector);
						Colors.resetColor();
						drawModalRectWithCustomSizedTexture(openSubX-1+(int) (hsbHue[2]*87f), openSubY-1+15, 0, 0, 3, 12, 3, 12);
						mc.getTextureManager().bindTexture(Resources.colorBg);
						drawModalRectWithCustomSizedTexture(openSubX-2, openSubY-2+15, 0, 0, 89, 14, 89, 28);

						//SATURATION
						for(float i=0;i<87;i++) {

							mc.getTextureManager().bindTexture(Resources.blank);

							float r2 = r+(i/87f);
							float g2 = g+(i/87f);
							float b2 = b+(i/87f);
							if(r2 > 1) {r2=1f;}
							if(g2 > 1) {g2=1f;}
							if(b2 > 1) {b2=1f;}
							GlStateManager.color(r2, g2, b2, 1);
							drawModalRectWithCustomSizedTexture((int) (openSubX-1+86-i), openSubY-1+30, 0, 0, 1, 12, 1, 1);
						}
						mc.getTextureManager().bindTexture(Resources.colorSelector);
						Colors.resetColor();
						drawModalRectWithCustomSizedTexture(openSubX-1+(int) (hsbHue[1]*87f), openSubY-1+30, 0, 0, 3, 12, 3, 12);
						mc.getTextureManager().bindTexture(Resources.colorBg);
						drawModalRectWithCustomSizedTexture(openSubX-2, openSubY-2+30, 0, 0, 89, 14, 89, 28);
						drawModalRectWithCustomSizedTexture(openSubX-2, openSubY-2+45, 0, 14, 89, 14, 89, 28);
						//TRANSPARENCY
						for(float i=0;i<87;i++) {

							mc.getTextureManager().bindTexture(Resources.blank);

							GlStateManager.color(r, g, b, i/87f);
							drawModalRectWithCustomSizedTexture((int) (openSubX-1+i), openSubY-1+45, 0, 0, 1, 12, 1, 1);
						}
						mc.getTextureManager().bindTexture(Resources.colorSelector);//
						Colors.resetColor();
						drawModalRectWithCustomSizedTexture(openSubX-1+(int) (a*87f), openSubY-1+45, 0, 0, 3, 12, 3, 12);
						mc.getTextureManager().bindTexture(Resources.colorBg);
						drawModalRectWithCustomSizedTexture(openSubX-2, openSubY-2+45, 0, 0, 89, 14, 89, 28);
						GlStateManager.color(1, 1, 1, 1);
						drawModalRectWithCustomSizedTexture(openSubX-2, openSubY-2+60, 0, 14, 89, 14, 89, 28);
						mc.getTextureManager().bindTexture(Resources.blank);
						GlStateManager.color(r, g, b, a);
						drawModalRectWithCustomSizedTexture(openSubX-1, openSubY-1+60, 0, 0, 87, 12, 1, 1);
						mc.getTextureManager().bindTexture(Resources.colorBg);
						Colors.resetColor();
						drawModalRectWithCustomSizedTexture(openSubX-2, openSubY-2+60, 0, 0, 89, 14, 89, 28);

						String hovering = "";


							if(mouseX > openSubX-1 && mouseX <= openSubX+83 && mouseY > openSubY && mouseY <= openSubY+12) {
								hovering=Colors.CHROMA+"Hue";
								if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
									float diff = mouseX-openSubX;
									Color newColor = Color.getHSBColor(diff/85f, hsbHue[1], hsbHue[2]);
									r = newColor.getRed()/255f;
									g = newColor.getGreen()/255f;
									b = newColor.getBlue()/255f;
									String newColorString = r+";"+g+";"+b+";"+a;
									ConfigHandler.newObject(id, newColorString);
								}
							}
							else if(mouseX > openSubX-1 && mouseX <= openSubX+83 && mouseY > openSubY+15 && mouseY <= openSubY+12+15) {
								hovering=Colors.YELLOW+"Brightness";
								if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
									float diff = mouseX-openSubX;
									Color newColor = Color.getHSBColor(hsbHue[0], hsbHue[1], diff/85f);
									r = newColor.getRed()/255f;
									g = newColor.getGreen()/255f;
									b = newColor.getBlue()/255f;
									String newColorString = r+";"+g+";"+b+";"+a;
									ConfigHandler.newObject(id, newColorString);
								}

							}
							else if(mouseX > openSubX-1 && mouseX <= openSubX+83 && mouseY > openSubY+30 && mouseY <= openSubY+12+30) {
								hovering=Colors.WHITE+"Saturation";
								if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
									float diff = mouseX-openSubX;
									Color newColor = Color.getHSBColor(hsbHue[0], diff/85f, hsbHue[2]);
									r = newColor.getRed()/255f;
									g = newColor.getGreen()/255f;
									b = newColor.getBlue()/255f;
									String newColorString = r+";"+g+";"+b+";"+a;
									ConfigHandler.newObject(id, newColorString);
								}

							}
							else if(mouseX > openSubX-1 && mouseX <= openSubX+83 && mouseY > openSubY+45 && mouseY <= openSubY+12+45) {
								hovering=Colors.AQUA+"Transparency";
								if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
									float diff = mouseX-openSubX;
									a=diff/85f;
									String newColorString = r+";"+g+";"+b+";"+a;
									ConfigHandler.newObject(id, newColorString);
								}
							}
							else if(mouseX > openSubX-1 && mouseX <= openSubX+83 && mouseY > openSubY+60 && mouseY <= openSubY+12+60) {
								hovering=Colors.GOLD+"Preview";
							}

						if(!hovering.equals("")) {
							mc.getTextureManager().bindTexture(Resources.blank);
							GlStateManager.color(0.2f, 0.2f, 0.2f, 0.8f);

							drawModalRectWithCustomSizedTexture(openSubX-5, openSubY+75, 0, 0, 95, 11, 1, 1);
							GuiUtils.drawString(hovering, openSubX-5+47-mc.fontRendererObj.getStringWidth(hovering)/2, openSubY+75, 1);
						}

					} catch(Exception ignored) {}
					break;


			}
		} catch(Exception ignored) {}

		//SETTING INFO HOVER DISPLAY
		if(!isConfirmOpen && openSubOption==-1) {
			int x2=0;
			int y2=0;
			for(int i=0;i<settingNames.size();i++) {
				if(mouseX >= x2*135+(this.width/2)-130 && mouseX <= x2*135+(this.width/2)-130+24 && mouseY >= (y2*68)+184-sliderYValue && mouseY <= (y2*68)+184+24-sliderYValue && mouseY >= 164) {

						ArrayList<String> hoverText = new ArrayList<>();
						hoverText.add(settingDesc.get(i));
						hoverText.add(Colors.YELLOW+"Last updated in version: "+Colors.AQUA+settingVersions.get(i));
						this.drawHoveringText(hoverText, mouseX, mouseY);
					
				}

				x2++;
				if(x2 == 3) {
					x2=0;y2++;
					if(y2 > clickedSubOption/3 && clickedSubOption != -1) { y2++; }
				}
			}
		}
		
		
		//CONFIRM RESET CONFIG MENU
		else if(isConfirmOpen) {
			
			//CONFIRM STRING
			String confirmResetText = Colors.BOLD+"Are you sure you want to reset the entire config?";
			int confirmResetWidth = fontRendererObj.getStringWidth(confirmResetText);
			
			//SHADED BACKGROUND
			mc.getTextureManager().bindTexture(Resources.settingBorder);
			GlStateManager.enableBlend();
			GlStateManager.color(1, 1, 1, 0.8F);
			drawModalRectWithCustomSizedTexture(this.width/2-(confirmResetWidth/2)-10, this.height/2-32, 0, 0, confirmResetWidth+20, 60, 0, 0);
			GlStateManager.color(1, 1, 1, 0.8F);
			mc.getTextureManager().bindTexture(Resources.searchBar);
			
			//CONFIRM RESET ASK BOX
			drawModalRectWithCustomSizedTexture((this.width/2)-(confirmResetWidth/2)-4, this.height/2-22, 0, 0, 1, 20, 375, 20);
			for(int i=0;i<confirmResetWidth+8;i++) {
				drawModalRectWithCustomSizedTexture((this.width/2)-(confirmResetWidth/2)-3+i, this.height/2-22, 45, 0, 1, 20, 375, 20);
			}
			drawModalRectWithCustomSizedTexture((this.width/2)+(confirmResetWidth/2)+5, this.height/2-22, 0, 0, 1, 20, 375, 20);
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
			
			drawModalRectWithCustomSizedTexture((this.width/2)-2, this.height/2-1, 0, 0, 1, 20, 375, 20);
			for(int i=0;i<confirmWidth+8;i++) {
				drawModalRectWithCustomSizedTexture((this.width/2)-3-i, this.height/2-1, 45, 0, 1, 20, 375, 20);
			}
			drawModalRectWithCustomSizedTexture((this.width/2)-confirmWidth-11, this.height/2-1, 0, 0, 1, 20, 375, 20);
			
			
			
			if(mouseX >= n1 && mouseX <= n2 && mouseY >= this.height/2-1 && mouseY <= this.height/2-1+20) {
				GlStateManager.color(1, 1, 1, 1);
			} else {
				GlStateManager.color(1, 1, 1, 0.8F);
			}
			//CANCEL BOX
			String cancelText = Colors.BOLD+"Cancel";
			int cancelWidth = fontRendererObj.getStringWidth(cancelText);
			
			drawModalRectWithCustomSizedTexture((this.width/2)+2, this.height/2-1, 0, 0, 1, 20, 375, 20);
			for(int i=0;i<cancelWidth+8;i++) {
				drawModalRectWithCustomSizedTexture((this.width/2)+3+i, this.height/2-1, 45, 0, 1, 20, 375, 20);
			}
			drawModalRectWithCustomSizedTexture((this.width/2)+cancelWidth+11, this.height/2-1, 0, 0, 1, 20, 375, 20);
			
			String confirmCoordsString = ((this.width/2)-confirmWidth-11)+","+((this.width/2)-2);
			String cancelCoordsString = ((this.width/2)+2)+","+((this.width/2)+cancelWidth+11);
			confirmCancelCoords = confirmCoordsString+","+cancelCoordsString;
			
			//TEXT FOR BOXES
			mc.fontRendererObj.drawString(textColor+confirmText, ((this.width/2)-confirmWidth-6), this.height/2+5, 0x10, false);
			mc.fontRendererObj.drawString(textColor+cancelText, (this.width/2)+7, this.height/2+5, 0x10, false);
			mc.fontRendererObj.drawString(textColor+confirmResetText, (this.width/2)-(confirmResetWidth/2), this.height/2-17, 0x10, false);
		}
		
		
		
		if(hoveringText.equals("discord")) {
			ArrayList<String> discordArrayList = new ArrayList<>();
			discordArrayList.add(Colors.BLUE+"Discord:");
			discordArrayList.add(Colors.YELLOW+"https://discord.gg/QXA3y5EbNA"+Colors.WHITE);
			this.drawHoveringText(discordArrayList, mouseX, mouseY);
			GlStateManager.enableBlend();
		} else if(hoveringText.equals("github")) {
			ArrayList<String> githubArrayList = new ArrayList<>();
			githubArrayList.add(Colors.GOLD+"Github:");
			githubArrayList.add(Colors.YELLOW+"https://github.com/Cobble8/SkyblockPersonalized"+Colors.WHITE);
			this.drawHoveringText(githubArrayList, mouseX, mouseY);
			GlStateManager.enableBlend();
		}

		GuiUtils.renderPlayer(posX, posY-5, scale, mouseX, mouseY);
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		GlStateManager.popMatrix();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		//clickedSubOption=-1;
		//openSubOption=7;
		//openSubX=20;openSubY=20;
		reloadPopup();
		//PREVENT DOING THINGS WHILE CONFIRM MENU IS OPEN
		if(!isConfirmOpen) {
			if(openSubOption == -1) {
				textBoxClick(mouseX, mouseY);
				//+","++","++","+

				/*int x1 = (this.width/2-188-20-fontRendererObj.getStringWidth("Reset Config")+8);
				int x2 = (this.width/2-188-20);
				int y1 = (this.height-22-sliderYValue);
				int y2 = (this.height-22-sliderYValue+20);

				//RESET CONFIG BUTTON
				if(mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2) {
					Utils.playClickSound();
					isConfirmOpen=true;
					updateSearch();
					return;
				}*/

				//Github n Discord
				if(mouseX >= this.width/2+200-20-17 && mouseX <= this.width/2+200-20-17+16 && mouseY >= 17+22+13-8 && mouseY <= 17+22+13-8+16) {
					Utils.playClickSound();
					WebUtils.openURL("https://discord.gg/QXA3y5EbNA");
				}
				else if(mouseX >= this.width/2+200-20 && mouseX <= this.width/2+200-20+16 && mouseY >= 17+22+13-8 && mouseY <= 17+22+13-8+16) {
					Utils.playClickSound();
					WebUtils.openURL("https://github.com/Cobble8/SkyblockPersonalized");
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
					if(mouseX >= this.width / 2 - 188 - 120 && mouseX <= this.width / 2 - 188 - 120 + 100 && mouseY >= 166 + i * 12 && mouseY <= 164 + i * 12 + 12) {
						searchSettingString=actualPresets.get(i);
						updateTextBox(0, searchSettingString, getX(0), getY(0));
						Utils.playClickSound();
						sliderYValue=0;
						updateSearch();
						return;
					}
					//bottomY =  164+(i*23);
					bottomY = 164+(i*12)+16;
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
				boolean foundClicked = false;
				for(int i=0;i<settingNames.size();i++) {

					//SWITCH CLICK EVENT
					if(mouseX >= x*135+(this.width/2-198)+40-20 && mouseX <= x*135+(this.width/2-198)+80-20 && mouseY >= y*68+164+22-sliderYValue && mouseY <= y*68+164+22+20-sliderYValue && mouseY >= 164) {
						for(String str : ConfigHandler.forceDisabled) {
							if(str.equals(settingIDs.get(i))) {
								ConfigHandler.newObject(settingIDs.get(i), false);
								settingToggles.set(i, false);
								TextUtils.sendMessage("This setting has been forcefully disabled by a Developer!");
								return;
							}
						}
						for(String str : ConfigHandler.forceEnabled) {
							if(str.equals(settingIDs.get(i))) {
								ConfigHandler.newObject(settingIDs.get(i), true);
								settingToggles.set(i, true);
								TextUtils.sendMessage("This setting has been forcefully enabled by a Developer!");
								return;
							}
						}
						settingToggles.set(i, !settingToggles.get(i));
						ConfigHandler.newObject(settingIDs.get(i), settingToggles.get(i));
						Utils.playClickSound();
						//updateSearch();
						return;
					}

					//OPTIONS CLICK EVENT
					else if(mouseX >= x*135+(this.width/2-198)+96 && mouseX <= x*135+(this.width/2-198)+96+24 && mouseY >= y*68+184-sliderYValue && mouseY <= y*68+184+24-sliderYValue && settingOptions.get(i) && mouseY >= 164) {

						if(i == clickedSubOption) {
							clickedSubOption=-1;
							openSubOption=-1;
						} else if (clickedSubOption == -1){
							clickedSubOption=i;
							openSubOption=-1;
						} else {
							return;
						}
						Utils.playClickSound();
						updateSearch();
						return;
					}

					//SETTINGS CLICK FORMATTER

					x++;
					if(x == 3) {
						x=0;y++;
						if(!foundClicked && y > clickedSubOption/3 && clickedSubOption != -1) {
							y++;
							foundClicked=true;
						}
					}
					if(foundClicked && i/3 == clickedSubOption/3) {
						int cX = 0;
						int cY = 0;
						int max = 3;
						ArrayList<String[]> clickedSubInfo = SettingOptions.settingOptions.get(clickedSubOption);
						int tmpLengthFix = clickedSubInfo.get(0).length;
						while (tmpLengthFix > 9) {
							tmpLengthFix -= 3;
							max++;
						}

						for (int j = 0; j < clickedSubInfo.get(0).length; j++) {
							//163
							if ((y * 68) + (cY * 20) + 164 - sliderYValue - 66 < 163) {
								cY++;
								if (cY == max) {
									cY = 0;
									cX++;
								}
								continue;
							}


							String type = clickedSubInfo.get(0)[j];
							String id = clickedSubInfo.get(2)[j];
							if (mouseX >= cX * 135 + (this.width / 2 - 198) && mouseX <= cX * 135 + (this.width / 2 - 198) + 16 && mouseY >= ((y * 68) + (cY * 20) + 164 - sliderYValue - 66) && mouseY <= ((y * 68) + (cY * 20) + 164 - sliderYValue - 66) + 16) {
								//GlStateManager.color(1, 1, 1, 1);
								Utils.playClickSound();
								if (type.equals("boolean")) {
									ConfigHandler.newObject(id, !DataGetter.findBool(id));
								} else if (type.equals("textColor") || type.equals("string")) {
									openSubX = mouseX;
									openSubY = mouseY;
									openSubOption = j;
								} else if(type.startsWith("moveGUI")) {
									SettingMove.id=id;
									try {
										SettingMove.imgWidth=Integer.parseInt(type.substring(type.indexOf(":")+2, type.lastIndexOf(";")));
										SettingMove.imgHeight=Integer.parseInt(type.substring(type.lastIndexOf(";")+1));
									} catch(Exception ignored) {
										TextUtils.sendErrMsg("Error getting the correct config value for this. Resetting this to the default state.");
										ConfigHandler.newObject(id, ConfigHandler.getDefaultValue(id));
										return;
									}

									Minecraft.getMinecraft().displayGuiScreen(new SettingMove());
								} else if(type.equals("size")) {
									openSubX = cX * 135 + (this.width / 2 - 198)-17;
									openSubY = (y * 68) + (cY * 20) + 164 - sliderYValue-66;
									openSubOption=j;
								} else if(type.equals("color")) {
									openSubX = mouseX+5;
									openSubY = mouseY+5;
									openSubOption = j;
								} else if(type.startsWith("int")) {
									openSubX = mouseX+10;
									openSubY = mouseY;
									openSubOption = j;
								}
							}


							cY++;
							if (cY == max) {
								cY = 0;
								cX++;
							}
						}
					}
				}
			} else {
				try {


					String type = SettingOptions.settingOptions.get(clickedSubOption).get(0)[openSubOption];
					String id = SettingOptions.settingOptions.get(clickedSubOption).get(2)[openSubOption];
					if(type.startsWith("int")) { type = "int"; }
					switch (type) {
						case "string":

							if (!(mouseX > openSubX - 2 && mouseX <= openSubX + 128 + 2 && mouseY > openSubY - 2 && mouseY <= openSubY + 20 + 2)) {
								openSubOption = -1;
							}
							break;
						case "int":
							if(!(mouseX > openSubX-2 && mouseX <= openSubX+80+2 && mouseY > openSubY-2 && mouseY <= openSubY+16+2)) {
								openSubOption = -1;
							}
							break;
						case "size":
							if (!(mouseX > openSubX - 2 && mouseX <= openSubX + 52 + 2 && mouseY > openSubY - 2 && mouseY <= openSubY + 16 + 2)) {
								openSubOption = -1;
							} else {
								int oldSize = DataGetter.findInt(id);
								if(mouseX > openSubX && mouseX <= openSubX+16 && mouseY > openSubY && mouseY <= openSubY+16) {
									if(oldSize > 1) {
										oldSize--;
										ConfigHandler.newObject(id, oldSize);
										Utils.playClickSound();
									} else {
										Minecraft.getMinecraft().thePlayer.playSound("note.bass", 1F, 0F);
									}
								}
								else if(mouseX > openSubX+34 && mouseX <= openSubX+16+34 && mouseY > openSubY && mouseY <= openSubY+16) {
									oldSize++;
									ConfigHandler.newObject(id, oldSize);
									Utils.playClickSound();
								}
									break;
							}
							break;
						case "color":
								if(!(mouseX > openSubX-5 && mouseX <= openSubX+94 && mouseY > openSubY-5 && mouseY <= openSubY+79)) {
									openSubOption=-1;
								}

								break;
						case "textColor":

							if (!(mouseX > openSubX && mouseX <= openSubX + 92 && mouseY > openSubY && mouseY <= openSubY + 92)) {
								openSubOption = -1;
							} else {
								String oldColor = DataGetter.findStr(id);
								for (int x = 0; x < 5; x++) {
									for (int y = 0; y < 4; y++) {
										if (mouseX > openSubX + (x * 18) && mouseX <= openSubX + 16 + (x * 18) && mouseY > openSubY + (y * 18) && mouseY <= openSubY + 16 + (y * 18)) {
											Utils.print(y * 5 + x);
											int newColor = (y * 5 + x);
											if (newColor > 16) {
												switch (newColor) {
													case (17):
														if (oldColor.contains("&l")) {
															oldColor = oldColor.replace("&l", "");
														} else {
															oldColor += ("&l");
														}
														break;
													case (18):
														if (oldColor.contains("&o")) {
															oldColor = oldColor.replace("&o", "");
														} else {
															oldColor += ("&o");
														}
														break;
													case (19):
														if (oldColor.contains("&n")) {
															oldColor = oldColor.replace("&n", "");
														} else {
															oldColor += ("&n");
														}
														break;
												}
											} else {

												String tmpColor = Colors.getColorFromInt(newColor).replace(Reference.COLOR_CODE_CHAR + "", "&");
												oldColor = tmpColor + oldColor.substring(2);
											}
											Utils.playClickSound();
											ConfigHandler.newObject(SettingOptions.settingOptions.get(clickedSubOption).get(2)[openSubOption], oldColor);
											break;

										}
									}
								}
							}

							break;
					}

				} catch(Exception e) { e.printStackTrace(); }


				//openSubOption=-1;

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





		
		
		//clickedSubOption=-1;
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	protected void keyTyped(char par1, int par2) throws IOException {
		if(textBoxClicked != -1) {
			super.keyTyped(par1, par2);
		} else if(par2 == Keyboard.KEY_E && openSubOption == -1) {
			mc.thePlayer.closeScreen();
		}
		try {
			String type = SettingOptions.settingOptions.get(clickedSubOption).get(0)[openSubOption];
			if(clickedSubOption != -1 && openSubOption != -1 && (type.equals("string") || type.equals("int") )) {
				String id = SettingOptions.settingOptions.get(clickedSubOption).get(2)[openSubOption];
				String text = DataGetter.findStr(id);
				if(type.equals("string") && TextUtils.checkIfCharLetter(par1+"")) {
					ConfigHandler.newObject(id, text+par1);
					return;
				} else if(type.equals("int") && TextUtils.checkIfCharInt(par1+"")) {



					ConfigHandler.newObject(id, Integer.parseInt(text+par1));
					return;
				}
				else if(par2 == Keyboard.KEY_BACK && text.length() > 0) {
					ConfigHandler.newObject(id, text.substring(0, text.length()-1));
					return;
				}
			}
		} catch(Exception ignored) {}

		
		//ESCAPE
		if(par2 == Keyboard.KEY_ESCAPE) {
			settingsMenuOpen=false;
			updateSearch();
			mc.thePlayer.closeScreen();
			return;
		}
		if(textBoxClicked != -1) { textBoxKeyPress(par1, par2); sliderYValue = 0; sliderOffset = 0; }
		
		
		//SEARCH BAR FUNCTIONALITY
		if(isSearchFocused) {
			
			//BACKSPACE
			if(par2 ==Keyboard.KEY_BACK) {
				if(searchSettingString.length()>=1 ) {
					if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
						StringBuilder finalStr = new StringBuilder();
						
						String[] string = searchSettingString.split(" ");
						ArrayList<String> str = new ArrayList<>();
						Collections.addAll(str, string);
						
						str.remove(str.size()-1);

						for (String s : str) {
							finalStr.append(s).append(" ");
						}
						
						searchSettingString = finalStr.toString();
					} else {
						searchSettingString = TextUtils.removeIntLast(searchSettingString, 1);
					}
					
					
					
					
					
				}
				
				//TYPE CHARACTER INTO SEARCH BAR
			} else {
				if(TextUtils.checkIfCharLetter(par1+"")/* && mc.fontRendererObj.getStringWidth(searchSettingString) <= searchBarWidth-12*/) {
					
					searchSettingString+=(par1+"");
				}
			}
			updateSearch();
		}  /*if(par2 == Keyboard.KEY_E) {
				DataGetter.updateConfig("main");
				settingsMenuOpen=false;
				updateSearch();
				mc.thePlayer.closeScreen();
			}*/


	}
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	//WITHOUT SETTINGS FOR OPTIONS MENU
	public static void addSetting(String name, String categories, String id, String desc, String version) {
		
		String[] array = categories.split(", ");
		
		//ADD SETTING FUNCTION
		if(name.toLowerCase().contains(searchSettingString.toLowerCase()) || TextUtils.checkIfArrayContains(array, searchSettingString.toLowerCase())) {
			
			//GIVES ALL BASIC INFORMATION FOR SETTING
			settingNames.add(name);
			settingToggles.add(DataGetter.findBool(id));
			settingIDs.add(id);
			settingDesc.add(desc);
			settingOptions.add(false);
			settingVersions.add(version);
			//CREATE A BLANK SETTINGS PAGE AS A FILLER
			ArrayList<String[]> optionList = new ArrayList<>();
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
		if(name.toLowerCase().contains(searchSettingString.toLowerCase()) || TextUtils.checkIfArrayContains(array, searchSettingString.toLowerCase())) {
			

			//GIVES ALL BASIC INFORMATION FOR SETTING
			settingNames.add(name);
			settingToggles.add(DataGetter.findBool(id));
			settingIDs.add(id);
			settingDesc.add(desc);
			settingOptions.add(true);
			settingVersions.add(version);
			
			String[] optionName = optionNames.split(", ");
			
			
			//GIVES ALL CUSTOM OPTIONS INFORMATION FOR SETTING
			ArrayList<String[]> optionList = new ArrayList<>();
			optionList.add(type);
			optionList.add(optionName);
			optionList.add(optionID);
			
			SettingOptions.settingOptions.add(optionList);
		}
		
	}
	



	public static void addCategory(String displayString, String actualString) {
		searchPresets.add(displayString);
		actualPresets.add(actualString);
	}
	
	private static void drawButton(String text, int x, int y, int mouseX, int mouseY) {
		
		Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().bindTexture(Resources.searchBar);
		int openConfigWidth = mc.fontRendererObj.getStringWidth(text)+8;
		
		try { GlStateManager.color(0.6F, 0.6F, 0.6F, fadeIn-0.2F);
		} catch (Exception e) { GlStateManager.color(1, 1, 1, 0.0F); }
		
		if(mouseX >= x-openConfigWidth && mouseX <= x && mouseY >= y && mouseY <= y+20) {
			GlStateManager.color(1, 1, 1, 1);
		}
		mc.getTextureManager().bindTexture(Resources.searchBar);
		drawModalRectWithCustomSizedTexture(x, y, 0, 0, 1, 20, 375, 20);
		drawModalRectWithCustomSizedTexture(x-openConfigWidth, y, 0, 0, openConfigWidth, 20, 375, 20);
		drawModalRectWithCustomSizedTexture(x-openConfigWidth, y, 0, 0, 1, 20, 375, 20);
		mc.fontRendererObj.drawString(text, x-openConfigWidth+5, y+6, 0x10, false);
		GlStateManager.enableBlend();
		
		
		if(TextUtils.unformatAllText(text).equals("Download DSG Images")) {
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
			File file = new File("config/"+Reference.MODID);
			try { Desktop.getDesktop().open(file); } catch (IOException e) { e.printStackTrace(); }
		} else if(button == 2) {
			mc.displayGuiScreen(new SettingMoveAll());
		} else if(button == 3) {
			isConfirmOpen=true;
		} else if(button == 4) {
			mc.displayGuiScreen(new Changelogs());
		}
	}
	
	public static void drawRedBox(String str,int x ,int  y) {
		Minecraft mc = Minecraft.getMinecraft();
		
		int strWidth = mc.fontRendererObj.getStringWidth(str);
		mc.getTextureManager().bindTexture(Resources.blank);
		GlStateManager.enableBlend();
		GlStateManager.color(0.8F, 0.2F, 0.2F, 1);
		drawModalRectWithCustomSizedTexture(x, y, 0,0, strWidth+6, 14, strWidth+6, 14);
		GlStateManager.color(0.6F, 0.1F, 0.1F);
		drawModalRectWithCustomSizedTexture(x-1, y, 0,0, 1, 14, 1, 14);
		drawModalRectWithCustomSizedTexture(x, y-1, 0,0, strWidth+6, 1, strWidth+6, 1);
		drawModalRectWithCustomSizedTexture(x+strWidth+6, y, 0,0, 1, 14, 1, 14);
		drawModalRectWithCustomSizedTexture(x, y+14, 0,0, strWidth+6, 1, strWidth+6, 1);
		GuiUtils.drawString(Colors.WHITE+str, x+3, y+3, 1);
	}
	
	private static final ArrayList<Integer> textBoxIDs = new ArrayList<>();
	private static final ArrayList<String> textBoxTexts = new ArrayList<>();
	private static final ArrayList<Integer> textBoxXs = new ArrayList<>();
	private static final ArrayList<Integer> textBoxYs = new ArrayList<>();
	private static final ArrayList<Integer> textBoxWidths = new ArrayList<>();
	//private static final ArrayList<Integer> textBoxStyles = new ArrayList();
	private static int textBoxClicked = -1;
	private static int textBoxHighlightStart = 0;
	private static int textBoxHighlightEnd = 0;
	private static int textBoxTypingPos = 0;
	
	public static void addTextBox(int id, String text, int x, int y, int width) {

		for (Integer textBoxID : textBoxIDs) {
			if (textBoxID.equals(id)) {
				return;
			}
		}

		textBoxIDs.add(id);
		textBoxTexts.add(text);
		textBoxXs.add(x);
		textBoxYs.add(y);
		textBoxWidths.add(width);
	}
	
	public static void updateTextBox(int id, String newText, int newX, int newY) {
		try { textBoxTexts.set(id, newText); textBoxXs.set(id, newX); textBoxYs.set(id, newY);} catch(Exception ignored) {}
	}
	
	public static void resetTextBoxes() {
		textBoxIDs.clear();
		textBoxTexts.clear();
		textBoxXs.clear();
		textBoxWidths.clear();
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
			mc.getTextureManager().bindTexture(Resources.searchBar);
			drawModalRectWithCustomSizedTexture(x, y, 0, 0, 1, 20, 375, 20);
			drawModalRectWithCustomSizedTexture(x, y, 0, 0, width-2, 20, 375, 20);
			drawModalRectWithCustomSizedTexture(x+width-2, y, 0, 0, 1, 20, 375, 20);
			String isClicked = "";
			if(textBoxClicked == id) { if(AnimationHandler.mainAnim <= 250 || (AnimationHandler.mainAnim > 500 && AnimationHandler.mainAnim <= 750)) { isClicked = "_"; } }
			
			GuiUtils.drawConfinedString(Colors.WHITE+txt+isClicked, x+5, y+6, 0, width-12);
		}
	}
	
	public static void drawTextBox(int id) {
		drawTextBox(id, Resources.searchBar, 20);
	}
	
	public static void drawTextBox(int id, ResourceLocation resourceLocation, int height) {
		try {
			String txt = textBoxTexts.get(id);
			if(txt.length() < 2 && textBoxClicked != -1) {
				txt = Colors.WHITE;
				updateTextBox(textBoxClicked, Colors.WHITE, getX(textBoxClicked), getY(textBoxClicked));
			}
			String rawTxt = TextUtils.unformatText(txt);
			int x = textBoxXs.get(id);
			int y= textBoxYs.get(id);
			int width = textBoxWidths.get(id);
			Minecraft mc = Minecraft.getMinecraft();
			mc.getTextureManager().bindTexture(resourceLocation);
			drawModalRectWithCustomSizedTexture(x, y, 0, 0, 1, height, 375, height);
			drawModalRectWithCustomSizedTexture(x, y, 0, 0, width-2, height, 375, height);
			drawModalRectWithCustomSizedTexture(x+width-2, y, 0, 0, 1, height, 375, height);
			if(textBoxHighlightEnd > rawTxt.length()) { textBoxHighlightEnd = rawTxt.length(); }
			if(textBoxHighlightStart < 0) {textBoxHighlightStart=0;}
			String hiltStr = rawTxt.substring(textBoxHighlightStart, textBoxHighlightEnd);
			int beforeWidth = mc.fontRendererObj.getStringWidth(rawTxt.substring(0, textBoxHighlightStart));
			
			int highlightTextColor = 0xFFFFFF;
			
			if(txt.startsWith(Reference.COLOR_CODE_CHAR+"")) {
				ArrayList<Float> bgColor = Colors.getRGBFromColorCode(txt.substring(0, 2));
				float r = bgColor.get(0);
				float g = bgColor.get(1);
				float b = bgColor.get(2);
				float a = bgColor.get(3);
				float r2 = 1-r;
				float g2 = 1-g;
				float b2 = 1-b;
				//Utils.print(r+", "+g+", "+b+", "+a);
				mc.getTextureManager().bindTexture(Resources.blank);
				//textBoxHighlightStart=0;
				//textBoxHighlightEnd=1;
				//textBoxTypingPos=30;
				
				int durWidth = mc.fontRendererObj.getStringWidth(hiltStr);
				GlStateManager.color(r, g, b, a);
				drawModalRectWithCustomSizedTexture(x+5+beforeWidth, y+5, 0, 0, durWidth, 10, 1, 1);
				
				GuiUtils.drawString(Colors.WHITE+txt, x+5, y+6, 0);
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
				GuiUtils.drawString(Colors.WHITE+txt, x+5, y+6, 0);
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
		try {
			return textBoxTexts.get(id);
		} catch(Exception e) {
			return "";
		}

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
				String rawStr = TextUtils.unformatAllText(str);
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
					String rawStr = TextUtils.unformatAllText(str);
					if(rawStr.substring(textBoxTypingPos).contains(" ")) {
						boolean hitSpace = false;
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
						} catch(Exception ignored) { }
						while(!hitSpace) {
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
					} else {
						if(textBoxHighlightStart < textBoxTypingPos) {
							textBoxHighlightStart++; return;
						}
						textBoxHighlightEnd++;
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
					String rawStr = TextUtils.unformatAllText(str);
					
					boolean hitSpace = false;
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
						
					} catch(Exception ignored) {}
						while(!hitSpace) {
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
				
				
				
			}
			
			else if(textBoxTypingPos>=1) {textBoxTypingPos--; 
			if(textBoxHighlightStart != 0) {textBoxTypingPos=textBoxHighlightStart;}
			textBoxHighlightStart=0; textBoxHighlightEnd=0;}
			
		//BACKSPACE KEY
		} else if(par2 == Keyboard.KEY_BACK) {
			
			try {
				String str = textBoxTexts.get(textBoxClicked);
				String rawStr = TextUtils.unformatAllText(str);
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
					String output;
					boolean hitSpace = false;
					int startPos = textBoxTypingPos;
					int endPos = textBoxTypingPos-1;
					
					while(!hitSpace) { try { if(!(rawStr.charAt(endPos)+"").equals(" ")) { endPos--; } else {hitSpace = true;} } catch(Exception e) {break;} }
					if(startPos-endPos <= 1) {
						StringBuilder newStr = new StringBuilder(); char[] strChars = str.toCharArray();
						for(int i=0;i<strChars.length;i++) { if(i == textBoxTypingPos+1) {  textBoxTypingPos--; continue; } newStr.append(strChars[i]);  }
						updateTextBox(textBoxClicked, newStr.toString(), getX(textBoxClicked), getY(textBoxClicked));
						textBoxHighlightEnd = 0; textBoxHighlightStart = 0; return;
					}
					
					String beforeStr = str.substring(0, endPos+offset)+" ";
					String afterStr = str.substring(startPos+offset);
					output=beforeStr+afterStr; textBoxTypingPos=endPos+1;
					updateTextBox(textBoxClicked, output, getX(textBoxClicked), getY(textBoxClicked));
					return;
				}
				
				StringBuilder newStr = new StringBuilder();
				char[] strChars = str.toCharArray();
				for(int i=0;i<strChars.length;i++) { if(i == textBoxTypingPos+1) { 
					if(textBoxTypingPos == 0) {return;}
					textBoxTypingPos--; 
					continue;
					} newStr.append(strChars[i]);
					}
				updateTextBox(textBoxClicked, newStr.toString(), getX(textBoxClicked), getY(textBoxClicked));
				textBoxHighlightEnd = 0;
				textBoxHighlightStart = 0;
			} catch(Exception ignored) {}
			
		}
		
		
		else {
			String str = textBoxTexts.get(textBoxClicked);
			String rawStr = TextUtils.unformatAllText(str);
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
					StringBuilder output = new StringBuilder();
					for(int i=0;i<str.length()+1;i++) {
						Utils.print(i-offset+" : "+textBoxTypingPos);
						if(i-offset == textBoxTypingPos) { 
							output.append(getClipboardString());
							}
						try {
							output.append(str.charAt(i));
						} catch(Exception ignored) {}
						
					}
					
					if(str.length() == textBoxTypingPos || str.length() == 0) {
						output.append(getClipboardString());}
					
					updateTextBox(textBoxClicked, output.toString(), getX(textBoxClicked), getY(textBoxClicked));
					textBoxTypingPos+=getClipboardString().length();
					TextUtils.sendMessage(output+" : "+getClipboardString());
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
			
			if(TextUtils.checkIfCharLetter(par1+"")) {
				
				StringBuilder newStr = new StringBuilder();
				char[] strChars = textBoxTexts.get(textBoxClicked).toCharArray();
				for(int i=0;i<strChars.length;i++) { newStr.append(strChars[i]); if(i == textBoxTypingPos+1) { newStr.append(par1);  } }
				textBoxTypingPos++;
				if(mc.fontRendererObj.getStringWidth(newStr.toString()) < textBoxWidths.get(textBoxClicked)-8) {
					updateTextBox(textBoxClicked, newStr.toString(), getX(textBoxClicked), getY(textBoxClicked));
				} else {
					Minecraft.getMinecraft().thePlayer.playSound("note.bass", 1, 0.2F);
				}
			}

		}
	}

	private static void reloadPopup() {

		try {

			InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(Resources.hints).getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder hintText = new StringBuilder();
			String currLine = reader.readLine();
			while(currLine != null) {
				hintText.append(currLine);
				currLine=reader.readLine();
			}

			JSONObject obj = (JSONObject) new JSONParser().parse(hintText.toString());
			JSONArray arr = (JSONArray) obj.get("hints");

			ArrayList<String> hintList = new ArrayList<>();
			for(Object hint : arr) { hintList.add(hint+""); }
			String newHint = popupText;
			while(newHint.equals(popupText)) {
				newHint = hintList.get((int) (Math.random()*hintList.size()));
			}
			popupText = newHint;

		} catch (Exception e) {
			popupText = "Error!";
			e.printStackTrace();
		}

		//Utils.sendMessage(popupText);

	}
}