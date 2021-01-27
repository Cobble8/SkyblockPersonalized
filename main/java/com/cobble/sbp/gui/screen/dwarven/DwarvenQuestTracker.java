package com.cobble.sbp.gui.screen.dwarven;

import java.util.ArrayList;
import java.util.Collection;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;

public class DwarvenQuestTracker extends Gui {

	
	public static Boolean questTrackToggle = (Boolean) DataGetter.find("dwarvenTrackToggle");
	public static Boolean questTrackBarToggle = (Boolean) DataGetter.find("dwarvenTrackBarToggle");
	public static int questTrackX = Integer.parseInt(DataGetter.find("dwarvenTrackX")+"");
	public static int questTrackY = Integer.parseInt(DataGetter.find("dwarvenTrackY")+"");
	public static int borderColorID = Integer.parseInt(DataGetter.find("dwarvenTrackBorderColor")+"");
	public static int yesColorID = Integer.parseInt(DataGetter.find("dwarvenTrackYesColor")+"");
	public static int noColorID = Integer.parseInt(DataGetter.find("dwarvenTrackNoColor")+"");
	
	
	public DwarvenQuestTracker() {
		
		String borderColor = Utils.getColorFromInt(borderColorID);
		String yesColor = Utils.getColorFromInt(yesColorID);
		String noColor = Utils.getColorFromInt(noColorID);
		
		Minecraft mc = Minecraft.getMinecraft();
		
		
		
		
		
		
		
		
		
		Collection<NetworkPlayerInfo> temp = Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap();
		ArrayList<NetworkPlayerInfo> tabNames = new ArrayList<NetworkPlayerInfo>(temp);
		int g = 0;
		for(int h=0;h<tabNames.size();h++) {
			String name = "";
			try {
				try {
				name = tabNames.get(h).getDisplayName().getUnformattedText();
				} catch(Exception e) {
					
				}
				String nameWithoutSpace = Utils.unformatText(name.replace(" ", ""));
				//Utils.print(nameWithoutSpace);
				if(nameWithoutSpace.startsWith("Commissions")) {
					mc.fontRendererObj.drawString(Colors.BLUE+Colors.BOLD+name+Colors.WHITE, questTrackX, questTrackY, 0x10, true);
				} else if(nameWithoutSpace.endsWith("%") || nameWithoutSpace.endsWith("DONE")) {
					if(nameWithoutSpace.toLowerCase().contains("slayer") || nameWithoutSpace.toLowerCase().contains("mithril") || nameWithoutSpace.toLowerCase().contains("titanium") || nameWithoutSpace.toLowerCase().contains("raffle") || nameWithoutSpace.toLowerCase().contains("starsentry") || nameWithoutSpace.toLowerCase().contains("goblin")) {
						g++;
						int isBarToggle = 2;
						int subtractOff = 0;
						String[] temp3 = name.split(":");
						String text = "";
						String percentbar = "";
						
						
							try { text+=Colors.WHITE+temp3[0]+":"; } catch (Exception e) {  }
						
							try { String color = Colors.RED; if(nameWithoutSpace.contains("DONE")) { color = Colors.GREEN; } text+=color+temp3[1]; } catch (Exception e) {  }

						
							if(questTrackBarToggle) {
								isBarToggle=1;
								subtractOff=12;
								int percent = 0;
								try {
								percent = (int) Double.parseDouble(temp3[1].replace("%","").replace(" ", ""));
								if(percent >= 1) {percent -=1;}
								} catch(NumberFormatException e) {
									percent = 100;
								}
								percentbar+=borderColor+"[";
								for(int f=0;f<50;f++) { if(percent > f*2) { percentbar+=yesColor+"|"; } else { percentbar+=noColor+"|"; } }
								percentbar+=borderColor+"]";
								mc.fontRendererObj.drawString(Colors.WHITE+percentbar+Colors.WHITE, questTrackX, questTrackY+(g*24), 0x10, true);
								
						
							}
						
						
						
						
						mc.fontRendererObj.drawString(Colors.WHITE+text+Colors.WHITE, questTrackX, questTrackY+(g*24/isBarToggle)-subtractOff, 0x10, true);
					}
					
				}
			} catch(Exception e) {  }
			//
		}
	}
	
}
