package com.cobble.sbp.events.user;

import java.util.ArrayList;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.gui.screen.misc.AbilityMessages;
import com.cobble.sbp.gui.screen.misc.ComboMessages;
import com.cobble.sbp.handlers.UpdatePartyHandler;
import com.cobble.sbp.threads.commands.DungeonsPartyThread;
import com.cobble.sbp.threads.commands.RepartyThread;
import com.cobble.sbp.threads.dungeons.AbilityDamageThread;
import com.cobble.sbp.threads.dwarven.GarryTeleportThread;
import com.cobble.sbp.threads.misc.DialogueThread;
import com.cobble.sbp.threads.misc.JerryTimer;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;



public class ChatRecieveEvent {
	
	public static Boolean togglePartyMessage = false;
	char dot = '\u25cf';
	
	
	
	@SubscribeEvent
	public void chatRecieved(ClientChatReceivedEvent event) {
		
		//ACTION BAR
		if(event.type == 2) {
			try {
			RenderGuiEvent.actionBar = event.message.getUnformattedText();
			String[] strings = RenderGuiEvent.actionBar.split(" {3,}");
			String setActionBar = "";
			for(int i=0;i<strings.length;i++) {
				String currString = Utils.unformatText(strings[i].toLowerCase().replace(" ", ""));
				
				
				//Auto Remove Secret Image
				if(currString.endsWith("secrets") && !SecretImage.roomSecretsID.equals("NONE") && DataGetter.findBool("scrtAutoRemove")) {
					currString = currString.replace("secrets", "");
					try {
						String[] tmp = currString.split("/");
						int currScrts = Integer.parseInt(tmp[0]);
						int maxScrts = Integer.parseInt(tmp[1]);
						if(currScrts == maxScrts) { SecretImage.roomSecretsID="NONE"; }
						
					} catch(Exception e) { }
				}
			}
			} catch(Exception e) { }
		}
		
		
		
		
		Minecraft mc = Minecraft.getMinecraft();
		String message = Utils.unformatText(event.message.getUnformattedText());
		if(message.startsWith("Your new API key is ")) {
			String apiKey = message.replace("Your new API key is ", "");
			ConfigHandler.newObject("APIKey", apiKey+"");
			Utils.sendMessage("Your Hypixel API Key has been set.");
		}
		
		
		if(SBP.onSkyblock) {
		
			
		//HOVER TEXT
		String hover = "";
		try {
			hover = (event.message.getChatStyle().getChatHoverEvent().toString());
			hover = hover.replace("HoverEvent{action=SHOW_TEXT, value='TextComponent{text='", "");
			String temp[] = hover.split("'"); hover = temp[0]; hover = Utils.unformatText(hover);
		} catch(Exception e) { hover=""; }
		
		//DISABLE AUTOPET MESSAGES
		if(message.startsWith("Autopet")) { if(DataGetter.findBool("disableAutopetMsgs")) { event.setCanceled(true); Utils.print("Cancelled Message: "+message);} }
		
		else if(message.contains("has obtained Superboom TNT!") && SBP.sbLocation.equals("catacombs")) {
			if(DataGetter.findBool("disableSuperboomPickups") ) { event.setCanceled(true); Utils.print("Cancelled Message: "+message);}
		}
		
		//CANCEL COMMON DROPS
		else if(message.startsWith("RARE DROP!")) {
			String newMsg = message.replace("RARE DROP! ", "");
			if(DataGetter.findBool("disableCommonDrops")) {
				String[] drops = (DataGetter.find("commonDropList")+"").split(", ");
			
				for(int i=0;i<drops.length;i++) {
					if(newMsg.startsWith(drops[i])) {
						Utils.print("Cancelled Message: "+message);
						event.setCanceled(true);
					}
				}
			}
		}
		
		//ABILITY MESSAGES
		else if(message.startsWith("Your ") && message.contains(" hit ") && message.endsWith("damage.") && AbilityMessages.abilityMsgToggle) {
			AbilityMessages.lastDamage = event.message.getFormattedText();
			AbilityMessages.damageMsgCount++;
			Thread abilityDamage = new AbilityDamageThread();
			abilityDamage.start();
			Utils.print("Cancelled Message: "+message);
			event.setCanceled(true);
		}
		
		else if(message.startsWith("+") && message.contains(" Kill Combo") && ComboMessages.abilityMsgToggle) {
			ComboMessages.lastCombo = event.message.getFormattedText();
			ComboMessages.comboMsgCount++;
			Thread abilityDamage = new AbilityDamageThread();
			abilityDamage.start();
			Utils.print("Cancelled Message: "+message);
			event.setCanceled(true);
		} else if(message.startsWith("Your Kill Combo has expired!")) {
			ComboMessages.lastCombo = event.message.getFormattedText();
			ComboMessages.comboMsgCount++;
			Thread abilityDamage = new AbilityDamageThread();
			abilityDamage.start();
			Utils.print("Cancelled Message: "+message);
			event.setCanceled(true);
		}
		
		//PICKAXE ABILITY COOLDOWN
		else if(message.toLowerCase().contains("ability") || message.toLowerCase().endsWith("expired!") || message.toLowerCase().endsWith("available!")) {
			if(message.startsWith("You used your Mining Speed Boost Pickaxe Ability!")) {
				if(!inMines()) { if(DataGetter.findBool("disablePickMsgs")) { Utils.print("Cancelled Message: "+message); event.setCanceled(true); } }
				
				else if(DataGetter.findBool("pickReminderToggle")) {
					DwarvenPickaxeTimer.abilityName="speed";
					DwarvenPickaxeTimer.lastUsed = System.currentTimeMillis();
					DwarvenPickaxeTimer.abilityUsed=true;
					
				} }
			else if(message.startsWith("Your Mining Speed Boost has expired!")) {
				if(!inMines()) { if(DataGetter.findBool("disablePickMsgs")) { Utils.print("Cancelled Message: "+message); event.setCanceled(true); } }
				
				else if((Boolean) DataGetter.findBool("pickReminderToggle")) {
					DwarvenPickaxeTimer.lastUsed = System.currentTimeMillis();
					DwarvenPickaxeTimer.abilityUsed=false;
				}
				
				
			} else if(message.startsWith("This ability is on cooldown for") || message.startsWith("Your Mining Speed Boost has expired!")) {
				if(!inMines()) { if(DataGetter.findBool("disablePickMsgs")) { Utils.print("Cancelled Message: "+message); event.setCanceled(true); } }
				
				
			} else if(message.startsWith("Mining Speed Boost is now available!") || message.startsWith("Pikoblokus is now available!")) {
				if(!inMines()) { if(DataGetter.findBool("disablePickMsgs")) { Utils.print("Cancelled Message: "+message); event.setCanceled(true); } } 
				else if(DataGetter.findBool("pickReminderToggle")) {
					DwarvenPickaxeTimer.lastUsed = System.currentTimeMillis()-1000000;
					DwarvenPickaxeTimer.abilityUsed=false;
					Utils.playDingSound();
				}
			}
		}
		
		//DISABLE AOTE BLOCKS IN WAY MSGS
		else if(message.equals("There are blocks in the way!")) { if(DataGetter.findBool("blocksInWayMsgs")) { event.setCanceled(true); } }
		
		//DISABLE COMPACT MSGS
		else if(message.startsWith("COMPACT!")) { if(DataGetter.findBool("compactToggle")) { Utils.print("Cancelled Message: "+message); event.setCanceled(true); } } 
		
		//DWARVEN EVENT TIMER
		else if((message.replace(" ", "")).startsWith("2XPOWDERSTARTED!") || (message.replace(" ", "")).startsWith("GOBLINRAIDSTARTED!") || (message.replace(" ", "")).startsWith("RAFFLESTARTED!")) {
			if(DwarvenTimer.dwarvenTimerDing) { Utils.playDingSound(); } DwarvenTimer.lastEvent=(int) (System.currentTimeMillis()); }
		
		//WEREWOLF MESSAGES
		else if(message.startsWith("You healed ") && message.contains(" players for ") && message.endsWith("health!") && (!message.contains("[") && DataGetter.findBool("werewolfToggle"))) {Utils.print("Cancelled Message: "+message); event.setCanceled(true);
		} else if(message.contains(" healed you for ") && message.endsWith(" health!") && !message.contains("[") && DataGetter.findBool("werewolfToggle")) { Utils.print("Cancelled Message: "+message); event.setCanceled(true); }
		
		//AUTO TELEPORT TO GARRY
		else if(message.contains("event starts in 20 seconds!") && !(message.contains("["))) {
			if(DataGetter.findBool("dwarvenTeleport")) {
				
			if(message.toLowerCase().contains("goblin")) { 
				if(DataGetter.findBool("dwarvenTeleportGoblin")) {
				} else if(DwarvenGui.currCommissions.toLowerCase().replace(" ", "").contains("goblinraid")) {
				} else { return; }
				
			} else if(message.toLowerCase().contains("raffle")) { 
				if(DataGetter.findBool("dwarvenTeleportRaffle")) {
				} else if(DwarvenGui.currCommissions.toLowerCase().contains("raffle")) {
				} else { return; }
				
			} else { return; }
			Thread teleportThread = new GarryTeleportThread(); teleportThread.start();
		} }
		
		
		//ADDITIONAL NPC DIALOUGE
		else if(message.startsWith("[NPC]")) {
			
			String newMsg = message.replace("[NPC] ", "");
			Boolean isDial = true;
			
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
			
			
			
			//PUZZLER SOLVER
			else if(newMsg.startsWith("Puzzler") && DataGetter.findBool("puzzlerSolver")) {
				
				char up = '\u25b2'; char right = '\u25b6'; char down = '\u25bc'; char left = '\u25c0'; char peace = '\u270c';
				isDial=false;
				if(newMsg.startsWith("Puzzler: "+right+up+"Come")) {RenderGuiEvent.puzzlerParticles=false; return;}
				else if(newMsg.startsWith("Puzzler: "+right+right+"Nice!")) {RenderGuiEvent.puzzlerParticles=false; return;}
				else if(newMsg.startsWith("Puzzler gave you 1,000 Mithril Powder for solving the puzzle!")) {RenderGuiEvent.puzzlerParticles=false; return;}
				else if(newMsg.contains(peace+"")) {RenderGuiEvent.puzzlerParticles=false; return;};
				char[] msgSplit = newMsg.replace("Puzzler: ", "").toCharArray(); int x = 0; int z = 0;
				for(int i=0;i<msgSplit.length;i++) { if((msgSplit[i]+"").equals(up+"")) { z+=1; } else if((msgSplit[i]+"").equals(down+"")) { z-=1; } else if((msgSplit[i]+"").equals(left+"")) { x+=1; } else if((msgSplit[i]+"").equals(right+"")) { x-=1; } }
				int startX = 181; int startZ = 135; String output = "";
				Utils.sendMessage("Puzzler Block Located");
				if(z >=0) { for(int i=0;i<z;i++) { output+=up; } } else if( z < 0) { for(int i=0;i<Math.abs(z);i++) { output+=down; } } if(x >=0) { for(int i=0;i<z;i++) { output+=left; } } else if( x < 0) { for(int i=0;i<Math.abs(z);i++) { output+=right; } }
				isDial = false;
				RenderGuiEvent.puzzlerX=startX+x;
				RenderGuiEvent.puzzlerZ=startZ+z;
				RenderGuiEvent.puzzlerParticles=true;
				Utils.print("Cancelled Message: "+message);
				return;
				
				
			} 
			
			//FETCHUR SOLVER
			else if(newMsg.startsWith("Fetchur: ") && DataGetter.findBool("fetchurSolver")) {
				isDial=false;
				String fetch = newMsg.replace("Fetchur: ", "").toLowerCase();
				String output = "";
				if(fetch.contains("yellow")) { output = "20 Yellow Stained Glass (Not Panes)";
				} else if(fetch.contains("circlular")) { output = "1 Compass";
				} else if(fetch.contains("minerals")) { output = "20 Mithril";
				} else if(fetch.contains("celebrations")) { output = "1 Firework Rocket";
				} else if(fetch.contains("hot")) { output = "1 Cheap or Decent Coffee";
				} else if(fetch.contains("tall")) { output = "1 Wooden Door";
				} else if(fetch.contains("explosive")) { output = "1 Superboom TNT";
				} else if(fetch.contains("wearable")) { output = "1 Pumpkin";
				} else if(fetch.contains("shiny")) { output = "1 Flint and Steel";
				} else if(fetch.contains("soft")) { output = "50 Red Wool";
				} else if(fetch.contains("red")) { output = "50 Nether Quartz Ore";
				} else if(fetch.contains("round")) { output = "16 Ender Pearls";
				} else if(fetch.contains("brown")) { output = "3 Rabbit Feet";
				}else { return; }
				
				
				Utils.sendMessage(Colors.AQUA+"Fetchur "+Colors.YELLOW+"wants: "+Colors.AQUA+output);
				return;
			} else { isDial = false; }
			
			if(isDial && DataGetter.findBool("npcDialogueToggle")) { DialogueThread.currMessages++; DialogueThread dial = new DialogueThread(); dial.start();} return;
			
		} else if(message.startsWith("Puzzler gave you 1000")) { RenderGuiEvent.puzzlerParticles=false; return; }
		
		
		else if(hover.startsWith("This happened thanks toZ")) { if(DataGetter.findBool("jerryTimerToggle")) { Utils.playDingSound(); Utils.playDingSound(); Thread jerryTimer = new JerryTimer(); jerryTimer.start(); return; } }
		
		
		
		//REPARTY
		else if(togglePartyMessage) {

			
			if(message.startsWith("Party Leader:")) {
				String leaderName = removeRankFromString(message.replace(" "+dot, "").replace("Party Leader: ", "")); 
				Utils.print("Party Leader Name: "+leaderName);
				if(!Utils.checkIfArrayContains(UpdatePartyHandler.partyMembers, leaderName) && !leaderName.equals(Minecraft.getMinecraft().thePlayer.getDisplayNameString())) {
					UpdatePartyHandler.partyMembers.add(leaderName);
				}
				
				Utils.print("Cancelled Message: "+message);
				event.setCanceled(true);
			}
			else if(message.startsWith("Party Members:")) {
				String[] tempArray = removeRankFromString(message.replace("Party Members: ", "")).split(" "+dot+" ");
				for(int i=0;i<tempArray.length;i++) {
					Utils.print("Member "+i+" Name: "+tempArray[i]);
					if(!Utils.checkIfArrayContains(UpdatePartyHandler.partyMembers, tempArray[i]) && !tempArray[i].equals(Minecraft.getMinecraft().thePlayer.getDisplayNameString())) {
						UpdatePartyHandler.partyMembers.add(tempArray[i]);
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
					if(!Utils.checkIfArrayContains(UpdatePartyHandler.partyMembers, tempArray[i]) && !tempArray[i].equals(Minecraft.getMinecraft().thePlayer.getDisplayNameString())) { UpdatePartyHandler.partyMembers.add(tempArray[i]); } }
				Utils.print("Cancelled Message: "+message); event.setCanceled(true);
			}
			else if(message.replace(" ", "").equals("")) {
				Utils.print("Cancelled Message: "+message);
				event.setCanceled(true);
			} else if(message.startsWith("You are not in a party right now.") || message.startsWith("You are not currently in a party.")) {
				UpdatePartyHandler.partyMembers.clear();
				if(togglePartyMessage) { Utils.print("Cancelled Message: "+message); event.setCanceled(true); }
			}
		}

		}
	}
	
	//REMOVE RANKS FROM A STRING
	public static String removeRankFromString(String string) {
		String temp = string;
		temp = temp.replace("[VIP] ", ""); temp = temp.replace("[VIP+] ", ""); temp = temp.replace("[MVP] ", ""); temp = temp.replace("[MVP+] ", ""); temp = temp.replace("[MVP++] ", "");
		return temp;
	}
	
	
	//CHECK IF IN MINING AREA
	public static Boolean inMines() {
		Boolean output = false;
		if(SBP.sbLocation.equals("dwarvenmines") || SBP.sbLocation.equals("deepcaverns") || SBP.sbLocation.equals("goldmine")) { output = true; } return output;
	}
	
}
