package com.cobble.sbp.threads.misc;

import java.util.ArrayList;

import com.cobble.sbp.SBP;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

public class RickRolledThread extends Thread {

	private static Boolean cancelLyrics = false;
	
	
	public void run() {
		
		
		if(cancelLyrics) {return;}
		
		int chance = (int) Math.round(Math.random()*10);
		Utils.print(chance);
		if(chance != 1) {return;}
		cancelLyrics = true;
		
		
		ArrayList<String> l = new ArrayList();
		
		l.add('[SBP] Turn off "Block Hoe Right Clicks" to view the crafting recipe of this farming hoe.');
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		for(int i=0;i<l.size();i++) {
			
			String color = "";
			if(i%6==0){color=Colors.RED;}else if(i%6==1){color=Colors.GOLD;}else if(i%6==2){color=Colors.YELLOW;}else if(i%6 == 3){color=Colors.GREEN;} else if(i%6==4){color=Colors.AQUA;}else if(i%6==5){color=Colors.LIGHT_PURPLE;}
			
			try {
				if(Utils.getSBID().contains("theoretical_hoe")) {
					SBP.titleScale = 3;
					SBP.titleString = color+l.get(i);
				} else {
					SBP.titleString = "";
					cancelLyrics = false;
					return;
				}
				
			} catch (Exception ignored) {  }
			try { Thread.sleep(3000); } catch (Exception e) { e.printStackTrace(); }
		} 
		SBP.titleString = "";
		SBP.titleScale = 4;
		l.clear();
		cancelLyrics = false;
	}
	
	
	
	
}
