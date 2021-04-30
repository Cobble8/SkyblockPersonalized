package com.cobble.sbp.gui.screen.dwarven;

import java.text.DecimalFormat;
import java.util.List;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.gui.menu.settings.SettingGlobal;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class DwarvenGui extends Gui{

	public static int posX = DataGetter.findInt("dwarvenGuiX");
	public static int posY = DataGetter.findInt("dwarvenGuiY");
	
	public static String currString = "";
	public static String currCommissions = "";
	
	//Commissions
	public static Boolean commTrackToggle = (Boolean) DataGetter.findBool("dwarvenTrackToggle");
	public static Boolean commTrackBarToggle = (Boolean) DataGetter.findBool("dwarvenTrackBarToggle");
	public static int commBorderColorID = Integer.parseInt(DataGetter.find("dwarvenTrackBorderColor")+"");
	public static int commYesColorID = Integer.parseInt(DataGetter.find("dwarvenTrackYesColor")+"");
	public static int commNoColorID = Integer.parseInt(DataGetter.find("dwarvenTrackNoColor")+"");
	public static int commNameID = DataGetter.findInt("dwarvenTrackQuestName");
	public static int commissionID = DataGetter.findInt("dwarvenTrackCommissionColor");
	
	//Drill Fuel
	public static Boolean fuelGui = DataGetter.findBool("dwarvenFuelGui");
	public static Boolean fuelDurr = DataGetter.findBool("dwarvenFuelDurr");
	public static Boolean fuelToggle =  DataGetter.findBool("dwarvenFuelToggle");
	public static Boolean fuelInDwarven = DataGetter.findBool("dwarvenOnlyFuel");
	
	public static int fuelGuiDrillFuel = DataGetter.findInt("dwarvenFuelDrillColor");
	public static int fuelGuiPrimaryFull = DataGetter.findInt("dwarvenFuelGuiPrimeFullColor");
	public static int fuelGuiSecondaryFull = DataGetter.findInt("dwarvenFuelGuiSecondFullColor");
	public static int fuelGuiPrimaryHalf = DataGetter.findInt("dwarvenFuelGuiPrimeHalfColor");
	public static int fuelGuiSecondaryHalf = DataGetter.findInt("dwarvenFuelGuiSecondHalfColor");
	public static int fuelGuiPrimaryTen = DataGetter.findInt("dwarvenFuelGuiPrimeTenColor");
	public static int fuelGuiSecondaryTen = DataGetter.findInt("dwarvenFuelGuiSecondTenColor");
	
	//Mithril Powder
	public static Boolean mithrilToggle = DataGetter.findBool("dwarvenMithrilDisplay");
	public static int mithrilTextColor = DataGetter.findInt("dwarvenMithrilTextColor");
	public static int mithrilCountColor = DataGetter.findInt("dwarvenMithrilCountColor");
	
	public static int searchForItem = 0;
	public static String heldName = "";
	
	
	
	public DwarvenGui(int x, int y) {
		String[] stringArray = currString.split(";");
		int aO = 0;
		if(x > SBP.width/2) {
			aO=108;
		}
		Utils.drawString(stringArray, posX+aO, posY, SettingGlobal.textStyle, true);
	}

	
	public static void manageDrillFuel() {
		try {
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			int fuelBorderColor = 0; int fuelFuelColor = 0; int fuelEmptyColor = 0;
			List<String> lore = player.getHeldItem().getTooltip(player, false);
			for(int u=0;u<lore.size();u++) { 
				String cLore = Utils.unformatAllText(lore.get(u)).toLowerCase();
				if(cLore.startsWith("fuel:")) { DwarvenGui.searchForItem=u; continue; }
			}
			
			
			if(DwarvenGui.searchForItem != 0) {
				
				String curr = Utils.unformatAllText(lore.get(DwarvenGui.searchForItem));
				lore.clear(); curr = curr.replace(",", "").replace("k", "000").replace("Fuel: ", "");
				Double fuelPercent; double currFuel; double totalFuel; DecimalFormat df = new DecimalFormat("#.##");
				try {
				String[] temp = curr.split("/"); 
				currFuel = Integer.parseInt(temp[0]); 
				totalFuel = Integer.parseInt(temp[1]);
				
				fuelPercent = currFuel/totalFuel*100; 
				} catch(Exception e) { fuelPercent = 0.0; currFuel = 0; totalFuel = 0; }
				
				int finalCurrFuel = (int) Math.round(currFuel); int finalTotalFuel = (int) Math.round(totalFuel); String finalFuelPercent = df.format((Math.round(fuelPercent))); int barProgress = Integer.parseInt(finalFuelPercent);
				if(barProgress <= 10) { fuelFuelColor = fuelGuiPrimaryTen; fuelBorderColor = fuelGuiSecondaryTen; } else if(barProgress > 50) { fuelFuelColor = fuelGuiPrimaryFull; fuelBorderColor = fuelGuiSecondaryFull; } else { fuelFuelColor = fuelGuiPrimaryHalf; fuelBorderColor = fuelGuiSecondaryHalf; }

				String color1 = Utils.getColorFromInt(fuelBorderColor); 
				String color2 = Utils.getColorFromInt(fuelFuelColor); 
				String barString = color1+"["+color2;
				
				
				if(DwarvenGui.fuelGui) {
				for(int f=0;f<50;f++) { if(barProgress > f*2) { barString+="|"; } else { if(barProgress == f*2 || barProgress == (f*2)-1) { barString+=Colors.DARK_GRAY; } barString+="|"; } } barString+=color1+"]";
				String drillFuelStringColor = Utils.getColorFromInt(fuelGuiDrillFuel);
				DwarvenGui.currString+=";"+drillFuelStringColor+"Drill Fuel: "+color2+barProgress+"% "+color1+"("+color2+finalCurrFuel+color1+"/"+color2+finalTotalFuel+color1+");";
				DwarvenGui.currString+=barString+Colors.WHITE;
				}
				
				if(DwarvenGui.fuelDurr) {
					ItemStack held = Minecraft.getMinecraft().thePlayer.getHeldItem();
					held.getItem().setDamage(held, (int) ((Math.round(totalFuel))-currFuel));
					held.getItem().setMaxDamage((int) Math.round(totalFuel));
				
				}
				
				
			}
			
	 		}catch(Exception e) {}
	}
	
	public static void manageCommissions(String name) {
		
		String name2 = name;
		name = name.toLowerCase(); 
		if(name.endsWith("%") || name.endsWith("done")) {
			
			if(name.contains("slayer") || name.contains("mithril") || name.contains("titanium") || name.contains("raffle") || name.contains("star") || name.contains("goblin") || name.contains("ghast")) {
				int isBarToggle = 2; int subtractOff = 0; String[] temp3 = name2.split(":"); String text = ""; String percentbar = "";
				int percent = 0;
				try { percent = (int) Double.parseDouble(temp3[1].replace("%","").replace(" ", "")); if(percent >= 1) {percent -=1;} } catch(NumberFormatException e) { percent = 100; }
				String nameColor = Utils.getColorFromInt(DwarvenGui.commNameID);
				text+=nameColor+temp3[0]+":"; 
				String color = Colors.RED; 
				if(name.contains("done") || percent > 74) { color = Colors.GREEN; 
				} else if(percent > 49) { color = Colors.YELLOW;
				} else if(percent > 24) { color = Colors.GOLD; }
				
				
				
				text+=color+temp3[1];
				DwarvenGui.currCommissions+=";"+text;
				if(commTrackToggle) { DwarvenGui.currString+=";"+text; }
				
				if(DwarvenGui.commTrackBarToggle && DwarvenGui.commTrackToggle) {
					
					
					String borderColor = Utils.getColorFromInt(DwarvenGui.commBorderColorID);
					String yesColor = Utils.getColorFromInt(DwarvenGui.commYesColorID);
					String noColor = Utils.getColorFromInt(DwarvenGui.commNoColorID);
					
					isBarToggle=1; subtractOff=12; 
					
					//HERE
					//if(percent != 0) {
						//tmp = percent;
					//}
					
					
					
					percentbar+=borderColor+"["+yesColor;
					for(int f=0;f<50;f++) { if(percent > f*2) { percentbar+="|"; } else { 
						
						
						if(percent == f*2 || percent == (f*2)-1) {
							percentbar+=noColor; 
						}
						
						
						percentbar+="|"; 
						} 
					} percentbar+=borderColor+"]";
					DwarvenGui.currString+=";"+percentbar;
					
					
					
				} 
				
				
				
				}
			}
	}
	
	public static String manageMithril(String name) {
			String color1 = Utils.getColorFromInt(mithrilTextColor);
			String color2 = Utils.getColorFromInt(mithrilCountColor);
			String num = name.replace(" Mithril Powder: ", "");
			return color1+"Mithril Powder: "+color2+num+Colors.WHITE;
		
	}
	
}
