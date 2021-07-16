package com.cobble.sbp.gui.menu.settings;

import java.awt.Color;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.utils.ColorUtils;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class SettingOptions extends GuiScreen {
	

	//SELECTED SETTING
	public static int selectedOption = 0;
	public static String settingName = "";
	int settingNameWidth = 0;
	//MASTER SETTING OPTIONS ARRAYS
	public static ArrayList<ArrayList<String[]>> settingOptions = new ArrayList();
	
	//CLICK EVENT ARRAYS
	private static final ArrayList<Integer> strWidths = new ArrayList();
	private static final ArrayList<Integer> varWidths = new ArrayList();
	private static final ArrayList<String> typeList = new ArrayList();
	private static final ArrayList<String> idList = new ArrayList();
	private static final ArrayList<String> resetCoords = new ArrayList();
	
	public static ArrayList optionVals = new ArrayList();
	public static int clickedOption = 0;
	private static int clickedInList = 0;
	
	//GUI OPEN
	@Override
	public void initGui() {
		SettingMenu.settingsMenuOpen=true;
		
		typeList.clear();
		varWidths.clear();
		strWidths.clear();
		idList.clear();
		optionVals.clear();
		resetCoords.clear();
		
		settingName = SettingMenu.settingNames.get(selectedOption);
		settingNameWidth = fontRendererObj.getStringWidth(Colors.BOLD+settingName);
		
		clickedOption = 100;
		
		super.initGui();
	}
	

	
	//DRAWSCREEN
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		
		String textColor = Colors.DARK_BLUE;
		String resetColor = Colors.BLACK;
		String titleColor = Colors.BLUE;
		if(SettingMenu.currTheme == 0) {
			textColor = Colors.WHITE;
			resetColor = Colors.WHITE;
			titleColor = Colors.YELLOW;
		}
		
		//RESOURCES
		ResourceLocation suggestionBar = new ResourceLocation(Reference.MODID, "textures/"+SettingMenu.currTheme+"/menu/suggestionBar.png");
		ResourceLocation Switch = new ResourceLocation(Reference.MODID, "textures/"+SettingMenu.currTheme+"/menu/switch.png");
		ResourceLocation settingBorder = new ResourceLocation(Reference.MODID, "textures/"+SettingMenu.currTheme+"/menu/settingBorder.png");
		ResourceLocation blank = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
		ResourceLocation plusMinus = new ResourceLocation(Reference.MODID, "textures/"+SettingMenu.currTheme+"/menu/plusminus.png");
		
		
		int optionCount = settingOptions.get(selectedOption).get(0).length;
		
		//SHADED BACKGROUND
		if(resetCoords.size() >= 1) {
			ArrayList<Integer> sortShaded = new ArrayList();

			for (String resetCoord : resetCoords) {
				String[] temp2 = resetCoord.split(", ");
				sortShaded.add((Integer.parseInt(temp2[1]) - (this.width / 2 - 110)));
			}
			sortShaded.add((settingNameWidth+8));
			sortShaded = Utils.sortIntArray(sortShaded);
			
			mc.getTextureManager().bindTexture(settingBorder);
			GlStateManager.enableBlend();
			GlStateManager.color(1, 1, 1, 0.7F);
			drawModalRectWithCustomSizedTexture((this.width/2)-115, this.height/4-31, 0, 0, sortShaded.get(sortShaded.size()-1)+13, (optionCount*26)+35, 0, 0);
			
			
		}
		GlStateManager.color(1, 1, 1, 1);
		
		//SETTING NAME
		mc.getTextureManager().bindTexture(SettingMenu.subsetting);
		drawModalRectWithCustomSizedTexture(this.width/2-110, this.height/4-26, 0, 0, 10, 25, 100, 25);
		for(int i=0;i<settingNameWidth/10;i++) {
			drawModalRectWithCustomSizedTexture(this.width/2-100+(i*10), this.height/4-26, 10, 0, 10, 25, 100, 25);
		}
		
		drawModalRectWithCustomSizedTexture(this.width/2-110+settingNameWidth, this.height/4-26, 90, 0, 10, 25, 100, 25);
		mc.fontRendererObj.drawString(titleColor+Colors.BOLD+settingName+Colors.WHITE, this.width/2-105, this.height/4+8-26, 0x10, false);
		
		
		
		//BACK BUTTON
		mc.getTextureManager().bindTexture(SettingMenu.subsetting);
		drawModalRectWithCustomSizedTexture(this.width/2-50, this.height*7/8, 0, 0, 100, 25, 100, 25);
		mc.fontRendererObj.drawString(textColor+Colors.BOLD+"Back"+Colors.WHITE, this.width/2-(fontRendererObj.getStringWidth(Colors.BOLD+"Back")/2), this.height*7/8+9, 0x10, false);
		
		//# OF OPTIONS FOR SPECIFIC SETTING
		
		resetCoords.clear();
		for(int i=0;i<optionCount;i++) {
			resetCoords.add("0, 0, 0, 0");
		}

		//DRAW EACH OPTION
		for(int y=0;y<optionCount;y++) {
			
			//GENERAL VARIABLES FOR OPTIONS
			String type = settingOptions.get(selectedOption).get(0)[y];
			String name = settingOptions.get(selectedOption).get(1)[y];
			String id = settingOptions.get(selectedOption).get(2)[y];

			if(!(type.startsWith("moveGUI")) && !(type.equals("color"))) {
				optionVals.add(DataGetter.find(id));
			}
			else {
				optionVals.add("Click Here");
			}
			
			//IS OPTION CLICKED
			String isOptionPressed = "";
			if(y == clickedOption) {
				isOptionPressed = "_";
			}
			if(type.equals("size") || type.equals("textColor")) {
				isOptionPressed="";
			}
			
			//GENERAL INFO FOR THINGS
			String variable = optionVals.get(y)+"";
			int varWidth = fontRendererObj.getStringWidth(variable+isOptionPressed);
			//Utils.sendMessage(selectedOption+", "+y);
			
			//
			if(type.equals("list")) {
				String[] list = variable.split(", "); ArrayList<Integer> sort = new ArrayList<Integer>();
				for (String s : list) {
					sort.add(fontRendererObj.getStringWidth(s));
				}
				sort = Utils.sortIntArray(sort); varWidth = sort.get(sort.size()-1);
				if(clickedOption != y) { varWidth = fontRendererObj.getStringWidth("Click Here"); }
			}
			
			
			int strWidth = fontRendererObj.getStringWidth(name);
			
			
			
			//VARIABLE NAME BOXES
			
			if(type.equals("color")) {
				/*String[] temp = (DataGetter.find(id)+"").split(";");
				String colorColor = Colors.DARK_BLUE;
				
				try {
					float r=Float.parseFloat(temp[0]);
					float g=Float.parseFloat(temp[1]);
					float b=Float.parseFloat(temp[2]);
					GlStateManager.color(r, g, b, 1);
					mc.getTextureManager().bindTexture(blank);
					
					if(r+b+g <= 1.2) {
						colorColor = Colors.GOLD;
					}
				} catch(Exception e) {
					colorColor = Colors.BLUE;
				}*/
				mc.getTextureManager().bindTexture(blank);
				ColorUtils.setColor(DataGetter.findStr(id));
				drawModalRectWithCustomSizedTexture(this.width/2-110, this.height/4+(y*26), 0, 0, strWidth+8, 25, strWidth+8, 25);
				mc.getTextureManager().bindTexture(suggestionBar);
				
				GlStateManager.color(1, 1, 1, 1);
				drawModalRectWithCustomSizedTexture(this.width/2-110, this.height/4+(y*26), 0, 0, strWidth+8, 2, 100, 25);
				drawModalRectWithCustomSizedTexture(this.width/2-110, this.height/4+(y*26)+23, 0, 23, strWidth+8, 2, 100, 25);
				drawModalRectWithCustomSizedTexture(this.width/2-110, this.height/4+(y*26), 0, 0, 2, 23, 100, 25);
				
				int finTxtClr = 0xFFFFFF;
				try {
					ArrayList<Float> txtClr = ColorUtils.getColor(DataGetter.findStr(id));
					int txtR = (int) (255*(1-txtClr.get(0))); int txtG = (int) (255*(1-txtClr.get(1))); int txtB = (int) (255*(1-txtClr.get(2)));
					Color txtClrNew = new Color(txtR, txtG, txtB);
					finTxtClr = txtClrNew.getRGB();
				} catch(Exception ignored) {  }
				
				mc.fontRendererObj.drawString(name+": "+Colors.WHITE, this.width/2-106, this.height/4+8+(y*26), finTxtClr, false);
				
				
				GlStateManager.color(1, 1, 1, 1);
			} else {
				mc.getTextureManager().bindTexture(SettingMenu.subsetting);
				drawModalRectWithCustomSizedTexture(this.width/2-110, this.height/4+(y*26), 0, 0, 10, 25, 100, 25);
				for(int i=0;i<strWidth/10;i++) {
					drawModalRectWithCustomSizedTexture(this.width/2-100+(i*10), this.height/4+(y*26), 10, 0, 10, 25, 100, 25);
				}
				drawModalRectWithCustomSizedTexture(this.width/2-110+strWidth, this.height/4+(y*26), 90, 0, 10, 25, 100, 25);
				mc.fontRendererObj.drawString(textColor+name+": "+Colors.WHITE, this.width/2-106, this.height/4+8+(y*26), 0x10, false);

				
			}
			
			//RESET BOX
			int booleanOffset = 0;
			switch (type) {
				case "boolean":
					booleanOffset = 21;
					if ((optionVals.get(y) + "").equals("false")) {
						booleanOffset -= 4;
					}
					break;
				case "size":
					booleanOffset = 83;
					varWidth = fontRendererObj.getStringWidth("1.0") - 2;
					break;
				case "textColor":
					booleanOffset = 43;
					varWidth = fontRendererObj.getStringWidth("Text Color") - 2;
					break;
			}
			
			mc.getTextureManager().bindTexture(SettingMenu.subsetting);
			int resetTextWidth = fontRendererObj.getStringWidth("Reset")+12;
			for(int i=0;i<resetTextWidth;i++) {
				drawModalRectWithCustomSizedTexture(this.width/2-110+strWidth+varWidth+19+booleanOffset+i, this.height/4+(y*26)+2, 2, 2, 1, 21, 100, 25);
			}
			drawModalRectWithCustomSizedTexture(this.width/2-110+strWidth+varWidth+19+booleanOffset, this.height/4+(y*26)+2, 0, 0, 2, 21, 100, 25);
			drawModalRectWithCustomSizedTexture(this.width/2-110+strWidth+varWidth+19+booleanOffset+resetTextWidth, this.height/4+(y*26)+2, 98, 0, 2, 21, 100, 25);
			drawModalRectWithCustomSizedTexture(this.width/2-110+strWidth+varWidth+19+booleanOffset+2, this.height/4+(y*26)+2, 0, 0, resetTextWidth-2, 2, 100, 25);
			drawModalRectWithCustomSizedTexture(this.width/2-110+strWidth+varWidth+19+booleanOffset+2, this.height/4+(y*26)+2+19, 0, 23, resetTextWidth-2, 2, 100, 25);
			//this.drawModalRectWithCustomSizedTexture(this.width/2-110+strWidth+varWidth+19+booleanOffset, this.height/4+(y*26), 0, 0, resetTextWidth, 2, 100, 25);
			//this.drawModalRectWithCustomSizedTexture(this.width/2-110+strWidth+varWidth+19+booleanOffset, this.height/4+(y*26)+23, 0, 0, resetTextWidth, 2, 100, 25);
			//this.drawModalRectWithCustomSizedTexture(this.width/2-110+strWidth+varWidth+19+booleanOffset, this.height/4+(y*26), 0, 0, 2, 23, 100, 25);
			//this.drawModalRectWithCustomSizedTexture(this.width/2-110+strWidth+varWidth+19+booleanOffset+resetTextWidth-1, this.height/4+(y*26), 0, 0, 2, 23, 100, 25);
			
			
			
			mc.fontRendererObj.drawString(resetColor+"Reset"+Colors.WHITE, this.width/2-110+strWidth+varWidth+19+booleanOffset+7, this.height/4+(y*26)+8, 0x10, false);
			String resetCoord = (this.width/2-110+strWidth+varWidth+19+booleanOffset)+", "+(this.width/2-110+strWidth+varWidth+19+booleanOffset+resetTextWidth-1)+", "+(this.height/4+(y*26))+", "+(this.height/4+(y*26)+23);
			resetCoords.set(y, resetCoord);
			
				
			//STRING/INT VARIABLE BOXES AND TEXT
			if(type.equals("string") || type.equals("int")) {
					
				//SEND INFO TO CLICK EVENT
				if(typeList.size() < settingOptions.get(selectedOption).get(0).length) {
					typeList.add(type);
					varWidths.add(new Integer(varWidth));
					strWidths.add(new Integer(strWidth));
					idList.add(id);
						
				}
					
				//VARIABLE VALUE BOXES
				mc.getTextureManager().bindTexture(SettingMenu.subsetting);
				drawModalRectWithCustomSizedTexture(this.width/2-102+strWidth, this.height/4+(y*26), 0, 0, 10, 25, 100, 25);
				drawModalRectWithCustomSizedTexture(this.width/2+10-102+strWidth, this.height/4+(y*26), 10, 0, varWidth, 25, 10, 25);
				drawModalRectWithCustomSizedTexture(this.width/2+varWidth-102+strWidth, this.height/4+(y*26), 90, 0, 10, 25, 100, 25);
					
				//VARIABLE TEXT
				
				mc.fontRendererObj.drawString(textColor+(variable+isOptionPressed)+Colors.WHITE, this.width/2-97+strWidth, this.height/4+8+(y*26), 0x10, false);
				
			}
				
			//BOOLEAN VARIABLES BOXES AND TEXT
			else if(type.equals("boolean")) {
					
				//VARIABLE FOR TEXTURE OFFSET FOR SWITCH
				int toggleSwitch = 0;
					
				//SEND INFO TO CLICK EVENT
				if(typeList.size() < settingOptions.get(selectedOption).get(0).length) {
					typeList.add(type);
					varWidths.add(new Integer(varWidth));
					strWidths.add(new Integer(strWidth));
					idList.add(id);
				}
									
				//DRAW SWITCHES
				mc.getTextureManager().bindTexture(Switch);
					
				//CHECK IF SWITCH IS ON/OFF
				if(!(Boolean.parseBoolean(variable+""))) {toggleSwitch+=25;}
				drawModalRectWithCustomSizedTexture(this.width/2-99+strWidth, this.height/4+(y*26), 0, toggleSwitch, 50, 25, 50, 50);
					
				//DRAW TEXT FOR BOOLEAN VARIABLE NAMES
				//mc.fontRendererObj.drawString(Colors.DARK_BLUE+name+": "+Colors.WHITE, this.width/2-106, this.height/4+8+(y*26), 0x10, false);
				
			}
			
			//MOVE GUI
			else if(type.startsWith("moveGUI") || type.equals("color")) {
				//SEND INFO TO CLICK EVENT
				if(typeList.size() < settingOptions.get(selectedOption).get(0).length) {
					typeList.add(type);
					varWidths.add(new Integer(varWidth));
					strWidths.add(new Integer(strWidth));
					idList.add(id);
						
				}
				
				//VARIABLE VALUE BOXES
				mc.getTextureManager().bindTexture(SettingMenu.subsetting);
				drawModalRectWithCustomSizedTexture(this.width/2-102+strWidth, this.height/4+(y*26), 0, 0, 10, 25, 100, 25);
				drawModalRectWithCustomSizedTexture(this.width/2+10-102+strWidth, this.height/4+(y*26), 10, 0, varWidth, 25, 10, 25);
				drawModalRectWithCustomSizedTexture(this.width/2+varWidth-102+strWidth, this.height/4+(y*26), 90, 0, 10, 25, 100, 25);
				
				//VARIABLE TEXT
				
				
				mc.fontRendererObj.drawString(textColor+(variable)+Colors.WHITE, this.width/2-97+strWidth, this.height/4+8+(y*26), 0x10, false);
				
			}
			
			else if(type.equals("size")) {
				if(typeList.size() < settingOptions.get(selectedOption).get(0).length) {
					typeList.add(type);
					varWidths.add(100);
					strWidths.add(strWidth);
					idList.add(id);
					
				}
				mc.getTextureManager().bindTexture(plusMinus);
				drawModalRectWithCustomSizedTexture(this.width/2-98+strWidth, this.height/4+(y*26)+2, 0, 0, 100, 20, 118, 20);
				
				try {
				float currSize = (Float.parseFloat(variable.replace(",", ".")))/10;
				DecimalFormat df = new DecimalFormat("#.#");
				currSize = Float.parseFloat(df.format(currSize));
				
				mc.fontRendererObj.drawString(textColor+(currSize+"x")+Colors.WHITE, this.width/2-97+strWidth+50-(fontRendererObj.getStringWidth("x"+currSize)/2), this.height/4+8+(y*26), 0x10, false);
				} catch(Exception e) { mc.fontRendererObj.drawString(Colors.RED+"Size Failed", this.width/2-97+strWidth+50-(fontRendererObj.getStringWidth("Size Failed")/2), this.height/4+8+(y*26), 0x10, false);}
			}
			
			else if(type.equals("textColor")) {
				if(typeList.size() < settingOptions.get(selectedOption).get(0).length) {
					typeList.add(type);
					varWidths.add(100);
					strWidths.add(strWidth);
					idList.add(id);
					
				}
				mc.getTextureManager().bindTexture(plusMinus);
				drawModalRectWithCustomSizedTexture(this.width/2-98+strWidth, this.height/4+(y*26)+2, 0, 0, 100, 20, 118, 20);



				String colorTextColor = ColorUtils.textColor(variable);
				//Utils.print(colorTextColor);
				
				
				Utils.drawString(colorTextColor+"Text Color"+Colors.WHITE+"", this.width/2-97+strWidth+50-(fontRendererObj.getStringWidth("Text Color")/2), this.height/4+8+(y*26));
				
			}
			
			//LIST
			else if(type.equals("list")) {
				
				
				String[] list = variable.split(", ");

				if(typeList.size() < settingOptions.get(selectedOption).get(0).length) {
					typeList.add(type);
					varWidths.add(varWidth);
					strWidths.add(strWidth);
					idList.add(id);
					
				}
				
				//LIST VARIABLE VALUE BOXES
				if(clickedOption == y) {
					for(int i=0;i<list.length+1;i++) {
						String suffix = "";
						if(clickedInList == i) {
							suffix="_";
							//varWidths.set(y, fontRendererObj.getStringWidth(list[i]+suffix)+4);
							varWidth+=fontRendererObj.getStringWidth("_");
						}
						mc.getTextureManager().bindTexture(SettingMenu.subsetting);
						drawModalRectWithCustomSizedTexture(this.width/2-102+strWidth, this.height/4+(y*26)+(i*23), 0, 0, 10, 25, 100, 25);
						drawModalRectWithCustomSizedTexture(this.width/2+10-102+strWidth, this.height/4+(y*26)+(i*23), 10, 0, varWidth, 25, 10, 25);
						drawModalRectWithCustomSizedTexture(this.width/2+varWidth-102+strWidth, this.height/4+(y*26)+(i*23), 90, 0, 10, 25, 100, 25);
						varWidth = fontRendererObj.getStringWidth(variable+isOptionPressed);
						if(i < list.length) {
							
							
							mc.fontRendererObj.drawString(textColor+list[i]+suffix+Colors.WHITE, this.width/2-97+strWidth, this.height/4+8+(y*26)+(i*23), 0x10, false);
						}
						
						ArrayList<Integer> sort = new ArrayList<Integer>();
						for (String s : list) {
							sort.add(fontRendererObj.getStringWidth(s));
						}
						sort = Utils.sortIntArray(sort); varWidth = sort.get(sort.size()-1);
					}
				} else {
					String clickHere = "Click Here";
					int clickWidth = fontRendererObj.getStringWidth(clickHere);
					
					varWidths.set(y, clickWidth);
					
					mc.getTextureManager().bindTexture(SettingMenu.subsetting);
					drawModalRectWithCustomSizedTexture(this.width/2-102+strWidth, this.height/4+(y*26), 0, 0, 10, 25, 100, 25);
					drawModalRectWithCustomSizedTexture(this.width/2+10-102+strWidth, this.height/4+(y*26), 10, 0, clickWidth, 25, 10, 25);
					drawModalRectWithCustomSizedTexture(this.width/2+clickWidth-102+strWidth, this.height/4+(y*26), 90, 0, 10, 25, 100, 25);
				

					mc.fontRendererObj.drawString(textColor+clickHere, this.width/2-97+strWidth, this.height/4+8+(y*26), 0x10, false);
				}
			}
			
			
		}
	}
	
	//MOUSE CLICK EVENT
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		int optionCount = settingOptions.get(selectedOption).get(0).length;
		
		//BACK BUTTON CLICK EVENT
		if(mouseX >= this.width/2-50 && mouseX <= this.width/2+50 && mouseY >= this.height*7/8 && mouseY <= this.height*7/8+25) {
			RenderGuiEvent.helpMenu=true;
			Utils.playClickSound();
			DataGetter.updateConfig(SettingMenu.currOptionName);
		}
		
		//RESET BUTTON CLICK EVENT
		for(int i=0;i<resetCoords.size();i++) {
			String[] temp = resetCoords.get(i).split(", ");
			int x1 = Integer.parseInt(temp[0]);
			int x2 = Integer.parseInt(temp[1]);
			int y1 = Integer.parseInt(temp[2]);
			int y2 = Integer.parseInt(temp[3]);

			
			
			
			if(mouseX >= (x1) && mouseX <= (x2) && mouseY >= y1 && mouseY <= y2) {
				
				if(typeList.get(i).startsWith("moveGUI")) {
					Object defaultValueX = ConfigHandler.getDefaultValue(idList.get(i)+"X");
					ConfigHandler.newObject(idList.get(i)+"X", defaultValueX);
					Object defaultValueY = ConfigHandler.getDefaultValue(idList.get(i)+"Y");
					ConfigHandler.newObject(idList.get(i)+"Y", defaultValueY);
				} 
				
				else if(typeList.get(i).equals("color")) {
					ConfigHandler.newObject(idList.get(i), "0.0;0.0;0.0");
				}
				
				else {
					Object defaultValue = ConfigHandler.getDefaultValue(idList.get(i));
					optionVals.set(i, defaultValue);
					ConfigHandler.newObject(idList.get(i), defaultValue);
				}
				Utils.playClickSound();
			}
		}
		
		//OPTION CLICK EVENT
		boolean isAnyOptionClicked = false;
		for(int i=0;i<strWidths.size();i++) {
			
			
			
			//GENERAL VARIABLES FOR CLICK EVENT
			int strWidth = strWidths.get(i);
			int varWidth = varWidths.get(i);
			String type = typeList.get(i);
			String id = idList.get(i);
			
			//X VALUE DEPENDING ON BOOLEAN OR NOT
			int x1 = this.width/2-75+strWidth+8;
			int x2 = this.width/2-75+strWidth+17+varWidth;
			
			//ADJUST VALUES FOR SWITCHES
			int xOffset = 0;
			if(type.equals("boolean")) {
				x1+=3;
				x2=x1+49;
			}
			
			if(type.startsWith("moveGUI") || type.equals("color") || type.equals("boolean") || type.equals("list")) {
				xOffset = 10;
			}
			
			//CLICK ON LIST
			switch (type) {
				case "list":
					String[] list = (optionVals.get(i) + "").split(", ");
					if (clickedOption == i) {
						for (int j = 0; j < list.length + 1; j++) {
							if (mouseX >= x1 - 35 && mouseX <= x2 - 35 && mouseY >= this.height / 4 + (i * 26) + (j * 23) && mouseY <= this.height / 4 + (i * 26) + 23 + (j * 23)) {
								clickedInList = j;
								Utils.playClickSound();
								return;
							}
						}
					}
					break;
				case "size": {
					int val = Integer.parseInt(optionVals.get(i) + "");
					if (mouseX >= x1 - 32 && mouseX <= x1 + 20 - 32 && mouseY >= this.height / 4 + (i * 26) + 2 && mouseY <= this.height / 4 + (i * 26) + 22) {
						Utils.playClickSound();
						optionVals.set(i, val -= 1);
						ConfigHandler.newObject(id, val -= 1);
						return;
					} else if (mouseX >= x1 + 80 - 32 && mouseX <= x1 + 100 - 32 && mouseY >= this.height / 4 + (i * 26) + 2 && mouseY <= this.height / 4 + (i * 26) + 22) {
						Utils.playClickSound();
						optionVals.set(i, val += 1);
						ConfigHandler.newObject(id, val += 1);
						return;
					}
					//return;
					break;
				}
				case "textColor": {
					//ConfigHandler.newObject(id, 5);
					//optionVals.set(i, 5);
					int val = Integer.parseInt(optionVals.get(i) + "");
					if (mouseX >= x1 - 32 && mouseX <= x1 + 20 - 32 && mouseY >= this.height / 4 + (i * 26) + 2 && mouseY <= this.height / 4 + (i * 26) + 22) {
						Utils.playClickSound();

						if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
							if (val >= 5) {
								optionVals.set(i, val - 5);
								ConfigHandler.newObject(id, val - 5);
							} else {
								optionVals.set(i, 0);
								ConfigHandler.newObject(id, 0);
							}
						} else if (val > 0) {
							optionVals.set(i, val - 1);
							ConfigHandler.newObject(id, val - 1);
						}
						return;
					} else if (mouseX >= x1 + 80 - 32 && mouseX <= x1 + 100 - 32 && mouseY >= this.height / 4 + (i * 26) + 2 && mouseY <= this.height / 4 + (i * 26) + 22) {
						Utils.playClickSound();

						if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
							if (val < 11) {
								optionVals.set(i, val + 5);
								ConfigHandler.newObject(id, val + 5);
							} else {
								optionVals.set(i, 16);
								ConfigHandler.newObject(id, 16);
							}
						} else if (val < 16) {
							optionVals.set(i, val + 1);
							ConfigHandler.newObject(id, val + 1);
						}
						return;
					}
					//return;
					break;
				}
			}
			
			
			//CLICK ON OPTION
			if(mouseX >= x1-25-xOffset && mouseX <= x2-25-xOffset && mouseY >= this.height/4+(i*26) && mouseY <= this.height/4+(i*26)+23) {
				
				//IF ITS A BOOLEAN THEN FLICK THE SWITCH
				if(type.equals("boolean")) {
					ConfigHandler.newObject(id, Utils.invertBoolean(Boolean.parseBoolean(optionVals.get(i)+"")));
					optionVals.set(i, Utils.invertBoolean(Boolean.parseBoolean(optionVals.get(i)+"")));
				} 
				//MOVE GUI
				else if(type.startsWith("moveGUI")) {
					Utils.playClickSound();
					String[] moveInfo = (type.replace("moveGUI: ", "")).split(";");
					
					if(typeList.size() < settingOptions.get(selectedOption).get(0).length) {
						typeList.add(type);
						varWidths.add(new Integer(varWidth));
						strWidths.add(new Integer(strWidth));
						idList.add(id);
					}
					
					if(moveInfo[0].equals("moveGUI")) {
						moveInfo = new String[] {"100", "100"};
					}
					
					
					SettingMove.id=id;
					SettingMove.imgWidth=Integer.parseInt(moveInfo[0]);
					SettingMove.imgHeight=Integer.parseInt(moveInfo[1]);
					
					Minecraft.getMinecraft().displayGuiScreen(new SettingMove());
				}
				
				else if(type.equals("color")) {
					SettingColor.id=id;
					SettingColor.oldColor=DataGetter.find(id)+"";
					//clickedOption=i;
					Minecraft.getMinecraft().displayGuiScreen(new SettingColor());
					Utils.playClickSound();
				} 
				
				
				
				else {
					isAnyOptionClicked=true;
					clickedOption=i;
					
				}
				
				
				
				//PLAY CLICK SOUND
				Utils.playClickSound();
			}
			
		}
		
		if(!isAnyOptionClicked) {
			
			//for(int i=0;i<optionVals.size();i++) {
			if(clickedOption < idList.size()) {
				
				String type = settingOptions.get(selectedOption).get(0)[clickedOption];
				if(type.equals("int")) {
					try {
						//if(Integer.parseInt(optionVals.get(clickedOption)+"") < 2147483647) {
						ConfigHandler.newObject(idList.get(clickedOption), Integer.parseInt(optionVals.get(clickedOption)+""));
						//}
					} catch(NumberFormatException e) {
						//mc.thePlayer.closeScreen();
						Utils.sendErrMsg("Please enter a value smaller than the 32 bit integer limit and greater than 0!");
					}
				} else {
					ConfigHandler.newObject(idList.get(clickedOption), optionVals.get(clickedOption)+"");
				}
			}
			clickedOption = optionCount+1;
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	protected void keyTyped(char par1, int par2) throws IOException {
		
		
		//ESCAPE
		if(par2 == Keyboard.KEY_ESCAPE || (par2 == Keyboard.KEY_E && clickedOption >= (settingOptions.get(selectedOption).get(0).length)+1)) {
			SettingMenu.settingsMenuOpen=false;
			DataGetter.updateConfig(SettingMenu.currOptionName);
			mc.thePlayer.closeScreen();
		}
		
		//BACKSPACE
		
		
		//ENTER
		else if(par2 == Keyboard.KEY_RETURN) {
			clickedOption=100;
		}
		
		
		
		//TYPE KEYS
		else {
			
			if(clickedOption < idList.size()) {
				String type = typeList.get(clickedOption);
				if(type.equals("string")) {
					
					//CONTROL COMMANDS
					if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
						if(par2 == Keyboard.KEY_V) {
							optionVals.set(clickedOption, optionVals.get(clickedOption)+getClipboardString());
							return;
						}
						else if(par2 == Keyboard.KEY_BACK) {
							optionVals.set(clickedOption, "");
						}
					}
					
					//BACKSPACE
					else if(par2 ==Keyboard.KEY_BACK) {
						if((optionVals.get(clickedOption)+"").length() >=1) {
							optionVals.set(clickedOption, Utils.removeIntLast(optionVals.get(clickedOption)+"", 1));
							return;
						}
					}
					else if(Utils.checkIfCharLetter(par1+"")) {
						optionVals.set(clickedOption, optionVals.get(clickedOption)+""+par1);
					}
				} else if(type.equals("int")) {
					if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
						if(par2 == Keyboard.KEY_BACK) {
							optionVals.set(clickedOption, "");
						}
					}
					
					//TYPE NUM
					else if(Utils.checkIfCharInt(par1+"")) {
						optionVals.set(clickedOption, optionVals.get(clickedOption)+""+par1);
					}
					
					//BACKSPACE
					else if(par2 ==Keyboard.KEY_BACK) {
						if((optionVals.get(clickedOption)+"").length() >=1) {
							optionVals.set(clickedOption, Utils.removeIntLast(optionVals.get(clickedOption)+"", 1));
							return;
						}
					}
				} else if(type.equals("list")) {
					String valList = optionVals.get(clickedOption)+"";
					String[] list = valList.split(", ");
					if(clickedInList >= list.length) {
						if(Utils.checkIfCharLetter(par1+"") && par2 != Keyboard.KEY_COMMA) {
							
							if(!valList.endsWith(", ")) {
								valList += ", ";
							}
							
							valList+=par1;
							optionVals.set(clickedOption, valList);
							return;
						}
					}
					
					
					String finalString = "";
				
					for(int i=0;i<list.length;i++) {
						if(i == clickedInList) {
								
							if(par2 ==Keyboard.KEY_BACK) {
								if(list[i].length() >= 1) {
									list[i] = Utils.removeIntLast(list[i], 1);
								}
							}
							if(Utils.checkIfCharLetter(par1+"") && par2 != Keyboard.KEY_COMMA) {
								list[i] += par1;
							}
						}
						finalString += list[i]+", ";
					}
					
					
					
					optionVals.set(clickedOption, finalString);
					
				}
			}
			
		}
	}
	
	//DOES GUI PAUSE GAME
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	
}
