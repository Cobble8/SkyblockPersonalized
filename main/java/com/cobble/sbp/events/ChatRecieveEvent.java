package com.cobble.sbp.events;

import java.util.ArrayList;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.gui.screen.dwarven.DwarvenDrillFuel;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTracker;
import com.cobble.sbp.threads.onetimes.DialogueThread;
import com.cobble.sbp.threads.onetimes.DungeonsPartyThread;
import com.cobble.sbp.threads.onetimes.JerryTimer;
import com.cobble.sbp.threads.onetimes.PickaxeTimerThread;
import com.cobble.sbp.threads.onetimes.RepartyThread;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;



public class ChatRecieveEvent {
	
	public static Boolean togglePartyMessage = false;
	public static String currentPartyUsing = "";
	char dot = '\u25cf';
	
	@SubscribeEvent
	public void chatRecieved(ClientChatReceivedEvent event) {
		
		if(event.type == 2) {
			try {
			RenderGuiEvent.actionBar = event.message.getUnformattedText();
			//ACTION BAR
			String[] strings = RenderGuiEvent.actionBar.split(" {3,}");
			String setActionBar = "";
			for(int i=0;i<strings.length;i++) {
				String currString = Utils.unformatText(strings[i].toLowerCase());
				if(currString.endsWith("drill fuel")) {
					String drillFuel = strings[i];
				} else {
					setActionBar+=strings[i]+"   ";
				}
				
				setActionBar = Utils.removeLastChars(setActionBar, 3);
			}
			
			if(DwarvenDrillFuel.fuelToggle) {
				event.message = new ChatComponentText(setActionBar);
			}
			} catch(Exception e) { }
		}
		
		
		
		
		Minecraft mc = Minecraft.getMinecraft();
		String message = Utils.unformatText(event.message.getUnformattedText());
		
		if(SBP.onSkyblock) {
		
		String hover = "";
		try {
			hover = (event.message.getChatStyle().getChatHoverEvent().toString());
			hover = hover.replace("HoverEvent{action=SHOW_TEXT, value='TextComponent{text='", "");
			String temp[] = hover.split("'");
			hover = temp[0];
			hover = Utils.unformatText(hover);
		} catch(Exception e) {
			hover="";
		}
		
		
		
		
		
		if(message.startsWith("Your new API key is ")) {
			String apiKey = message.replace("Your new API key is ", "");
			ConfigHandler.newObject("APIKey", apiKey+"");
			Utils.sendMessage(Colors.YELLOW+"Your Hypixel API Key has been set.");
			//event.setCanceled(true);
			
		}
		
		
		//CANCEL COMMON DROPS
		else if(message.startsWith("RARE DROP!")) {
			String newMsg = message.replace("RARE DROP! ", "");
			for(int j=0;j<DwarvenTracker.dwarvenLootList.size();j++) {
				String input = Utils.unformatText(DwarvenTracker.dwarvenLootList.get(j));
				if((newMsg.startsWith(input))) {
					DwarvenTracker.dwarvenLootCount.set(j, DwarvenTracker.dwarvenLootCount.get(j)+1);
				}
			}
			
			
			
			if((Boolean) DataGetter.find("disableCommonDrops")) {
			
				
				String[] drops = (DataGetter.find("commonDropList")+"").split(", ");
			
				for(int i=0;i<drops.length;i++) {
					if(newMsg.startsWith(drops[i])) {
						Utils.print("Cancelled Message: "+message);
						event.setCanceled(true);
					}
				}
			}
		}
		
		else if(message.toLowerCase().contains("ability") || message.toLowerCase().endsWith("expired!") || message.toLowerCase().endsWith("available!")) {
			if(message.startsWith("You used your Mining Speed Boost Pickaxe Ability!") || message.startsWith("You used your Pikobulus Pickaxe Ability!")) {
				if((Boolean) DataGetter.find("pickReminderToggle")) {
					DwarvenPickaxeTimer.lastUsed = System.currentTimeMillis();
					DwarvenPickaxeTimer.abilityUsed=true;
				}
				
				
				if(!inMines()) {
					if((Boolean) DataGetter.find("disablePickMsgs")) {
						Utils.print("Cancelled Message: "+message);
						event.setCanceled(true);
					}
				}
			}
			else if(message.startsWith("Your Mining Speed Boost has expired!") || message.startsWith("Your Pikobulus has expired!")) {
				if((Boolean) DataGetter.find("pickReminderToggle")) {
					DwarvenPickaxeTimer.lastUsed = System.currentTimeMillis();
					DwarvenPickaxeTimer.abilityUsed=false;
				}
				
				if(!inMines()) {
					if((Boolean) DataGetter.find("disablePickMsgs")) {
						Utils.print("Cancelled Message: "+message);
						event.setCanceled(true);
					}
				}
			} else if(message.startsWith("This ability is on cooldown for") || message.startsWith("Your Mining Speed Boost has expired!")) {
				if(!inMines()) {
					if((Boolean) DataGetter.find("disablePickMsgs")) {
						Utils.print("Cancelled Message: "+message);
						event.setCanceled(true);
					}
				}
			} else if(message.startsWith("Mining Speed Boost is now available!") || message.startsWith("Pikobulus is now available!")) {
				if(!inMines()) {
					if((Boolean) DataGetter.find("disablePickMsgs")) {
						Utils.print("Cancelled Message: "+message);
						event.setCanceled(true);
					}
				} else {
					if((Boolean) DataGetter.find("pickReminderToggle")) {
						DwarvenPickaxeTimer.lastUsed = System.currentTimeMillis()+1000000;
						DwarvenPickaxeTimer.abilityUsed=false;
						Utils.playDingSound();
					}
				}
			}
			
			/*else if(message.startsWith("This ability is on cooldown for")) {
				try {
					if(mc.thePlayer.inventory.player.getHeldItem().getUnlocalizedName().contains("pickaxe")) {
						
					}
				} catch (Exception e) {
					
				}
			}*/
		}
		
		
		
		
		else if(message.startsWith("COMPACT!")) {	
			if((Boolean) DataGetter.find("compactToggle")) {
				Utils.print("Cancelled Message: "+message);
				event.setCanceled(true);
			}
		} else if((message.replace(" ", "")).startsWith("2XPOWDERSTARTED!") || (message.replace(" ", "")).startsWith("GOBLINRAIDSTARTED!") || (message.replace(" ", "")).startsWith("RAFFLESTARTED!")) {
			
			if(DwarvenTimer.dwarvenTimerDing) {
				Utils.playDingSound();
			}
			
			DwarvenTimer.lastEvent=(int) (System.currentTimeMillis());
		}
		
		
		else if(message.startsWith("[NPC]")) {
			
			String newMsg = message.replace("[NPC] ", "");
			Boolean isDial = true;
			
			//if((Boolean) DataGetter.find("npcDialogueToggle")) {
			
			if(newMsg.startsWith("Gwendolyn: One day I will be useful.")) {
				DialogueThread.dialType=Colors.DARK_PURPLE+"Gwendolyn";
			} else if(newMsg.startsWith("Castle Guard: I'm guarding the lava")) {
				DialogueThread.dialType=Colors.GOLD+"Castle Guard:0";
			} else if(newMsg.startsWith("Castle Guard: I am")) {
				DialogueThread.dialType=Colors.GOLD+"Castle Guard:1";
			} else if(newMsg.startsWith("Castle Guard: Last week")) {
				DialogueThread.dialType=Colors.GOLD+"Castle Guard:2";
			} else if(newMsg.startsWith("Castle Guard: This guy")) {
				DialogueThread.dialType=Colors.GOLD+"Castle Guard:3";
			} else if(newMsg.startsWith("Castle Guard: I'm guarding the guard")) {
				DialogueThread.dialType=Colors.GOLD+"Castle Guard:4";
			} else if(newMsg.startsWith("Castle Guard: I'm guarding the whole kingdom.")) {
				DialogueThread.dialType=Colors.GOLD+"Castle Guard:5";
			} else if(newMsg.startsWith("Castle Guard: I'm a guard.")) {
				DialogueThread.dialType=Colors.GOLD+"Castle Guard:6";
			} else if(newMsg.startsWith("Castle Guard: I, Murdohr, son of Murdohr")) {
				DialogueThread.dialType=Colors.GOLD+"Castle Guard:7";
			} else if(newMsg.startsWith("Castle Guard: I'm guarding nothing.")) {
				DialogueThread.dialType=Colors.GOLD+"Castle Guard:8";
			} else if(newMsg.startsWith("Bednom: But it makes no sense, I've never even heard of such treasure!!")) {
				DialogueThread.dialType=Colors.LIGHT_PURPLE+"Bednom";
			} else if(newMsg.startsWith("Dalbrek: They sometimes show up")) {
				DialogueThread.dialType=Colors.YELLOW+"Dalbrek";
			} else if(newMsg.startsWith("Bylma")) {
				DialogueThread.dialType=Colors.YELLOW+"Bylma";
			} else if(newMsg.startsWith("Dirt Guy")) {
				DialogueThread.dialType=Colors.YELLOW+"Dirt Guy";
			} else if(newMsg.startsWith("Royal Resident: My neighbour")) {
				DialogueThread.dialType=Colors.YELLOW+"Royal Resident";
				Utils.print("Cancelled Message: "+message);
				event.setCanceled(true);
			} 
			
			//}
			
			
			
			
			else if(newMsg.startsWith("Puzzler") && (Boolean) DataGetter.find("puzzlerSolver")) {
				
				char up = '\u25b2'; char right = '\u25b6'; char down = '\u25bc'; char left = '\u25c0';
				if(newMsg.startsWith("Puzzler: "+right+up+"Come")) {RenderGuiEvent.puzzlerParticles=false; return;}
				else if(newMsg.startsWith("Puzzler: "+right+right+"Nice!")) {RenderGuiEvent.puzzlerParticles=false; return;}
				else if(newMsg.startsWith("Puzzler gave you 1,000 Mithril Powder for solving the puzzle!")) {RenderGuiEvent.puzzlerParticles=false; return;}
				
				char[] msgSplit = newMsg.replace("Puzzler: ", "").toCharArray(); int x = 0; int z = 0;
				for(int i=0;i<msgSplit.length;i++) { if((msgSplit[i]+"").equals(up+"")) { z+=1; } else if((msgSplit[i]+"").equals(down+"")) { z-=1; } else if((msgSplit[i]+"").equals(left+"")) { x+=1; } else if((msgSplit[i]+"").equals(right+"")) { x-=1; } }
				int startX = 181; int startZ = 135;
				/*event.setCanceled(true);*/ String output = "";
				Utils.sendMessage("Puzzler Block Located");
				if(z >=0) { for(int i=0;i<z;i++) { output+=up; } } else if( z < 0) { for(int i=0;i<Math.abs(z);i++) { output+=down; } } if(x >=0) { for(int i=0;i<z;i++) { output+=left; } } else if( x < 0) { for(int i=0;i<Math.abs(z);i++) { output+=right; } }
				isDial = false;
				RenderGuiEvent.puzzlerX=startX+x;
				RenderGuiEvent.puzzlerZ=startZ+z;
				RenderGuiEvent.puzzlerParticles=true;
				Utils.print("Cancelled Message: "+message);
			}
			
			
			
			else {
				isDial = false;
			}
			
			if(isDial && (Boolean) DataGetter.find("npcDialogueToggle")) {
				
				DialogueThread.currMessages++;
				DialogueThread dial = new DialogueThread();
				dial.start();
			}
			return;
			
		} else if(message.startsWith("Puzzler gave you 1000")) {
			RenderGuiEvent.puzzlerParticles=false;
			return;
		}
		
		
		else if(hover.startsWith("This happened thanks toZ")) {
			if((Boolean) DataGetter.find("jerryTimerToggle")) {
				Utils.playDingSound();
				Utils.playDingSound();
				
				Thread jerryTimer = new JerryTimer();
				jerryTimer.start();
				return;
			}
		}
		
		
		
		//REPARTY
		else if(togglePartyMessage) {
			
			ArrayList<String> curArrList;
			if(currentPartyUsing.equals("")) {
				return;
			} else if(currentPartyUsing.equals("dungeons")) {
				curArrList = DungeonsPartyThread.partyMemberList;
			} else if(currentPartyUsing.equals("re")) {
				curArrList = RepartyThread.nameList;
			} else {
				return;
			}
			
			if(message.startsWith("Party Leader:")) {
				String leaderName = removeRankFromString(message.replace(" "+dot, "").replace("Party Leader: ", "")); 
				Utils.print("Party Leader Name: "+leaderName);
				if(!Utils.checkIfArrayContains(curArrList, leaderName) && !leaderName.equals(Minecraft.getMinecraft().thePlayer.getDisplayNameString())) {
					curArrList.add(leaderName);
				}
				
				Utils.print("Cancelled Message: "+message);
				event.setCanceled(true);
			}
			else if(message.startsWith("Party Members:")) {
				String[] tempArray = removeRankFromString(message.replace("Party Members: ", "")).split(" "+dot+" ");
				for(int i=0;i<tempArray.length;i++) {
					Utils.print("Member "+i+" Name: "+tempArray[i]);
					if(!Utils.checkIfArrayContains(curArrList, tempArray[i]) && !tempArray[i].equals(Minecraft.getMinecraft().thePlayer.getDisplayNameString())) {
						curArrList.add(tempArray[i]);
					}
				}
				Utils.print("Cancelled Message: "+message);
				event.setCanceled(true);
			}
			else if(message.startsWith("-")) {
				Utils.print("Cancelled Message: "+message);
				event.setCanceled(true);
			}
			else if(message.startsWith("Party Members") && togglePartyMessage) {
				Utils.print("Cancelled Message: "+message);
				event.setCanceled(true);
			}
			else if(message.startsWith("Party Moderators")) {
				String[] tempArray = removeRankFromString(message.replace("Party Moderators: ", "")).split(" "+dot+" ");
				for(int i=0;i<tempArray.length;i++) {
					Utils.print("Moderator "+i+" Name: "+tempArray[i]);
					if(!Utils.checkIfArrayContains(curArrList, tempArray[i]) && !tempArray[i].equals(Minecraft.getMinecraft().thePlayer.getDisplayNameString())) {
						curArrList.add(tempArray[i]);
					}
				}
				Utils.print("Cancelled Message: "+message);
				event.setCanceled(true);
			}
			else if(message.replace(" ", "").equals("")) {
				Utils.print("Cancelled Message: "+message);
				event.setCanceled(true);
			} else if(message.startsWith("You are not in a party right now.") || message.startsWith("You are not currently in a party.")) {
				DungeonsPartyThread.partyMemberList.clear();
				RepartyThread.nameList.clear();
				//Utils.sendMessage(""+togglePartyMessage);
				if(togglePartyMessage) {
					Utils.print("Cancelled Message: "+message);
					event.setCanceled(true);
				}
			}
		}

		}
	}
	
	public static String removeRankFromString(String string) {
		String temp = string;
		temp = temp.replace("[VIP] ", "");
		temp = temp.replace("[VIP+] ", "");
		temp = temp.replace("[MVP] ", "");
		temp = temp.replace("[MVP+] ", "");
		temp = temp.replace("[MVP++] ", "");
		return temp;
	}
	
	public static Boolean inMines() {
		Boolean output = false;
		if(SBP.sbLocation.equals("dwarvenmines") || SBP.sbLocation.equals("deepcaverns") || SBP.sbLocation.equals("goldmine")) {
			output = true;
		}
		return output;
		
	}
	
}
