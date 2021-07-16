package com.cobble.sbp.threads.misc;

import java.util.ArrayList;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;

public class DialogueThread extends Thread {

	public static String dialType = "";
	public static int currMessages = 0;
	
	
	public void run() {
		if(DataGetter.findBool("npcDialogueToggle")) {
			String[] splitDial = dialType.split(":");
			String dialName = splitDial[0]+"";
			int dialID = 0;
			if(splitDial.length > 1) {
				dialID = Integer.parseInt(splitDial[1]);
			}
		
			char dogDeath = '\u2620';
			ArrayList<String> d = new ArrayList();
			String name = Minecraft.getMinecraft().thePlayer.getName();
			String nameShort = Colors.YELLOW+"[SBP] "+Colors.GOLD+name+Colors.WHITE+": ";
			String npcShort = Colors.YELLOW+"[SBP] "+dialName+Colors.WHITE+": ";
			boolean doDelay = true;
		
			int currMsg = currMessages;
			
			
			if(dialID == 0) {
				if(dialName.equals(Colors.DARK_PURPLE+"Gwendolyn")) {
					d.add(nameShort+"Will you though?");
					d.add(npcShort+"Probably not...");
				} else if(dialName.equals(Colors.GOLD+"Castle Guard")) {
					d.add(nameShort+"I don't believe you...");
					d.add(npcShort+"No really! I swear!");
					d.add(nameShort+"You LIAR!");
					d.add(npcShort+"Nooo! I have a family!");
					d.add(Colors.RED+" "+dogDeath+" "+dialName+Colors.GRAY+" was killed by "+Colors.GOLD+name+Colors.GRAY+".");
				} else if(dialName.equals(Colors.LIGHT_PURPLE+"Bednom")) {
					d.add(npcShort+Colors.GREEN+"There is a chest if you break the block at: "+Colors.AQUA+"3, 177, -70"+Colors.GREEN+".");
				} else if(dialName.equals(Colors.YELLOW+"Bylma")) {
					d.add(nameShort+"Just how wide is this here bridge?");
					d.add(npcShort+"It's pretty wide, maybe a mile!");
					d.add(nameShort+"You're exaggerating right?");
					d.add(npcShort+"Not sure... I can't move to go measure it.");
				} else if(dialName.equals(Colors.YELLOW+"Dirt Guy")) {
					d.add(nameShort+"Oh you do huh?");
					d.add(npcShort+Colors.RED+"DIRT"+Colors.GOLD+" DIRT"+Colors.YELLOW+" DIRT"+Colors.GREEN+" DIRT"+Colors.DARK_GREEN+" DIRT");
					d.add(nameShort+"Wonderful");
					d.add(npcShort+Colors.AQUA+"DIRT"+Colors.DARK_AQUA+" DIRT"+Colors.BLUE+" DIRT"+Colors.DARK_PURPLE+" DIRT"+Colors.LIGHT_PURPLE+" DIRT");
					d.add(nameShort+"Ok, goodbye.");
				} else if(dialName.equals(Colors.YELLOW+"Dalbrek")) {
					d.add(npcShort+Colors.GREEN+"At the beginning of the 7th, 14th, 21st, and 28th days of each season, the Cult of the Fallen Star can be found at "+Colors.AQUA+"-26 198 40"+Colors.GREEN+".");
				} else if(dialName.equals(Colors.YELLOW+"Royal Resident")) {
					d.add(Colors.GRAY+"[1/5] "+Colors.DARK_GREEN+"Royal Resident: "+Colors.GREEN+"My neighbour is the most narcissistic guy I have ever seen.");
					d.add(Colors.GRAY+"[2/5] "+Colors.DARK_GREEN+"Royal Resident: "+Colors.GREEN+"Wait what happend? Why are we suddenly in WynnCraft?");
					d.add(Colors.GRAY+"[3/5] "+Colors.DARK_GREEN+"Royal Resident: "+Colors.GREEN+"At least the Gavel Reborn update just came out!");
					d.add(npcShort+"Wait why is Cinfras so different?");
					d.add(npcShort+"Oh! Looks like it fixed itself.");
					doDelay = false;
				} 
			}
			else if(dialID == 1) {
		
				if(dialName.equals(Colors.GOLD+"Castle Guard")) {
					d.add(nameShort+"I'm not so sure about that...");
					d.add(npcShort+"I am guarding the lava.");
					d.add(Colors.RED+" "+dogDeath+" "+dialName+Colors.GRAY+" burnt to death.");
					d.add(nameShort+"I was right.");
				}
			} else if(dialID == 2) {
				if(dialName.equals(Colors.GOLD+"Castle Guard")) {
					d.add(nameShort+"You aren't the smartest guy, are you?");
					d.add(npcShort+"*GASP* How DARE you! I am the smartest person I know!");
					d.add(nameShort+"Right...");
					d.add(nameShort+"Whats 9+10?");
					d.add(npcShort+"tWeNtY oNe!");
				}
			} else if(dialID == 3) {
				if(dialName.equals(Colors.GOLD+"Castle Guard")) {
					d.add(nameShort+"What's a 'smithy'?");
					d.add(npcShort+"No idea.");
					d.add(nameShort+"Figures.");
				}
			} else if(dialID == 4) {
				if(dialName.equals(Colors.GOLD+"Castle Guard")) {
					d.add(nameShort+"So you're guarding the right of left guard, right?");
					d.add(npcShort+"Left.");
					d.add(nameShort+"What?");
					d.add(npcShort+"idk.");
				}
			} else if(dialID == 5) {
				if(dialName.equals(Colors.GOLD+"Castle Guard")) {
					d.add(nameShort+"And we're all SUPER proud of you! Right guys?");
					d.add(Colors.YELLOW+"[SBP] "+Colors.AQUA+"Erymanthus"+Colors.WHITE+": Yep.");
					d.add(Colors.YELLOW+"[SBP] "+Colors.RED+"Minikloon"+Colors.WHITE+": Mhm.");
					d.add(Colors.YELLOW+"[SBP] "+Colors.LIGHT_PURPLE+"Technoblade"+Colors.WHITE+": Of course.");
					d.add(npcShort+"Aww, thanks guys!");
				}
			} else if(dialID == 6) {
				if(dialName.equals(Colors.GOLD+"Castle Guard")) {
					d.add(Colors.RED+" "+dogDeath+" "+dialName+Colors.GRAY+" was killed by "+Colors.GOLD+"twasnt"+Colors.GRAY+".");
					d.add(Colors.YELLOW+"[SBP] "+Colors.GOLD+"twasnt"+Colors.WHITE+": NOT ANYMORE!");
					d.add(nameShort+"Well then.");
				}
			} else if(dialID == 7) {
				if(dialName.equals(Colors.GOLD+"Castle Guard")) {
					d.add(nameShort+"Nice intro! I am "+name+" the 3rd, son of "+ name + " jr, son of "+name+".");
					d.add(npcShort+"Thanks! You too!");
				}
			} else if(dialID == 8) {
				if(dialName.equals(Colors.GOLD+"Castle Guard")) {
					d.add(nameShort+"Nice intro! I am "+name+" the 3rd, son of "+ name + " jr, son of "+name+".");
					d.add(npcShort+"Thanks! You too!");
				}
			}
		
		
		
		
		
			for(int i=0;i<d.size();i++) {
				if(doDelay) {
					try { Thread.sleep(1400); } catch (InterruptedException e) { Utils.sendErrMsg("Dialogue Failed"); }
				} else {
					if(i != 0) {
						try { Thread.sleep(1400); } catch (InterruptedException e) { Utils.sendErrMsg("Dialogue Failed"); }
					}
				}
				if(currMsg == currMessages) {
					Utils.sendSpecificMessage(d.get(i));
					Minecraft.getMinecraft().thePlayer.playSound("mob.villager.haggle", 0.5F, 1.0F);
				}
			}

			
		}
		
	}
	
}
