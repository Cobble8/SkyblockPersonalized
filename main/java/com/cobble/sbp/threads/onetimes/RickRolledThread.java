package com.cobble.sbp.threads.onetimes;

import java.util.ArrayList;

import com.cobble.sbp.SBP;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class RickRolledThread extends Thread {

	private static Boolean cancelLyrics = false;
	
	
	public void run() {
		
		
		if(cancelLyrics) {return;}
		
		int chance = (int) Math.round(Math.random()*10);
		Utils.print(chance);
		if(chance != 1) {return;}
		cancelLyrics = true;
		
		
		ArrayList<String> l = new ArrayList();
		
		l.add("We're no strangers to love");
		l.add("You know the rules and so do I");
		l.add("A full commitment's what I'm thinking of");
		l.add("You wouldn't get this from any other guy");
		l.add("I just wanna tell you how I'm feeling");
		l.add("Gotta make you understand");
		l.add("Never gonna give you up");
		l.add("Never gonna let you down");
		l.add("Never gonna run around and desert you");
		l.add("Never gonna make you cry");
		l.add("Never gonna say goodbye");
		l.add("Never gonna tell a lie and hurt you");
		l.add("We've known each other for so long");
		l.add("Your heart's been aching, but you're too shy to say it");
		l.add("Inside, we both know what's been going on");
		l.add("We know the game, and we're gonna play it");
		l.add("And if you ask me how I'm feeling");
		l.add("Don't tell me you're too blind to see");
		l.add("Never gonna give you up");
		l.add("Never gonna let you down");
		l.add("Never gonna run around and desert you");
		l.add("Never gonna make you cry");
		l.add("Never gonna say goodbye");
		l.add("Never gonna tell a lie and hurt you");
		l.add("Never gonna give you up");
		l.add("Never gonna let you down");
		l.add("Never gonna run around and desert you");
		l.add("Never gonna make you cry");
		l.add("Never gonna say goodbye");
		l.add("Never gonna tell a lie and hurt you");
		l.add("Ooh (Give you up)");
		l.add("Ooh-ooh");
		l.add("Never gonna give, never gonna give (Give you up)");
		l.add("Ooh-ooh");
		l.add("Never gonna give, never gonna give (Give you up)");
		l.add("We've known each other for so long");
		l.add("Your heart's been aching, but you're too shy to say it");
		l.add("Inside, we both know what's been going on");
		l.add("We know the game, and we're gonna play it");
		l.add("I just wanna tell you how I'm feeling");
		l.add("Gotta make you understand");
		l.add("Never gonna give you up");
		l.add("Never gonna let you down");
		l.add("Never gonna run around and desert you");
		l.add("Never gonna make you cry");
		l.add("Never gonna say goodbye");
		l.add("Never gonna tell a lie and hurt you");
		l.add("Never gonna give you up");
		l.add("Never gonna let you down");
		l.add("Never gonna run around and desert you");
		l.add("Never gonna make you cry");
		l.add("Never gonna say goodbye");
		l.add("Never gonna tell a lie and hurt you");
		l.add("Never gonna give you up");
		l.add("Never gonna let you down");
		l.add("Never gonna run around and desert you");
		l.add("Never gonna make you cry");
		l.add("Never gonna say goodbye");
		l.add("Never gonna tell a lie and hurt you");
		l.add("Thank you and good night!");
		
		for(int i=0;i<l.size();i++) {
			
			String color = "";
			if(i%6==0){color=Colors.RED;}else if(i%6==1){color=Colors.GOLD;}else if(i%6==2){color=Colors.YELLOW;}else if(i%6 == 3){color=Colors.GREEN;} else if(i%6==4){color=Colors.AQUA;}else if(i%6==5){color=Colors.LIGHT_PURPLE;}
			
			try {
				if(Utils.getSBID().contains("theoretical_hoe")) {
					SBP.titleScale = 3;
					SBP.titleString = color+l.get(i);
				} else {
					SBP.titleString = "";
					return;
				}
				
			} catch (Exception e) {  }
			try { Thread.sleep(3000); } catch (Exception e) { e.printStackTrace(); }
		} 
		SBP.titleString = "";
		SBP.titleScale = 4;
		l.clear();
		cancelLyrics = false;
		return;
	}
	
	
	
	
}
