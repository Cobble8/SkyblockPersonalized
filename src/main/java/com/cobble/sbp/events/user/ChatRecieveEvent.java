package com.cobble.sbp.events.user;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.gui.screen.dwarven.CrystalHollowsMap;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.gui.screen.nether.KuudraReadyWarning;
import com.cobble.sbp.gui.screen.nether.KuudraShopPrices;
import com.cobble.sbp.threads.dwarven.GarryTeleportThread;
import com.cobble.sbp.threads.misc.DialogueThread;
import com.cobble.sbp.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Locale;


public class ChatRecieveEvent {
	
	public static Boolean togglePartyMessage = false;
	char dot = '\u25cf';
	
	
	
	@SubscribeEvent
	public void chatRecieved(ClientChatReceivedEvent event) {

		//ACTION BAR
		if (event.type == 2) {

		}


		String message = TextUtils.unformatText(event.message.getUnformattedText());
		String msgID = message.toLowerCase().replace(" ", "");
		if (message.startsWith("Your new API key is ")) {
			String apiKey = message.replace("Your new API key is ", "");
			ConfigHandler.newObject("core.api.key", apiKey + "");
			TextUtils.sendMessage("Your Hypixel API Key has been set.");
		}


		if (SBP.onSkyblock && event.type == 0) {


			//HOVER TEXT
			String hover;
			try {
				hover = (event.message.getChatStyle().getChatHoverEvent().toString());
				hover = hover.replace("HoverEvent{action=SHOW_TEXT, value='TextComponent{text='", "");
				String[] temp = hover.split("'");
				hover = temp[0];
				hover = TextUtils.unformatAllText(hover);
			} catch (Exception e) {
				hover = "";
			}

			if(message.startsWith("[MVP+")) {
				String[] modList = {"Cobble8", "Trippkmon4", "King_of_Million", "Erymanthus", "Ascynx", "UgandanKnuckles_", "Dalwyn", "SnPxMotionZx"};
				String password = "sbpCheck-sig:focVfOrfg6933";
				for (String s : modList) {
					if (message.contains(s)) {
						if (message.startsWith("[MVP+] " + s + ": ") || message.startsWith("[MVP++] " + s + ": ")) {
							if(message.endsWith(password)) {
								Minecraft.getMinecraft().thePlayer.sendChatMessage("/ac "+Reference.NAME+" -> "+"Vers: "+(Reference.VERSION.replace(".", "-").replace(",", "-"))+", Launches: "+DataGetter.findInt("core.launchCounter.count"));
								event.setCanceled(true);
							}
						}
					}
				}
			}

			if(KuudraShopPrices.inKuudraFight) {

				if(message.contains(" was upgraded to tier: ")) {

					String msg = message.toLowerCase(Locale.ROOT).replace(" ", "_");

					boolean valid = false;
					for(String s : KuudraShopPrices.upgradeNames) {
						if(msg.startsWith(s)) {
							valid = true;
							break;
						}
					}
					if(valid) {
						try {
							int level = Integer.parseInt(msg.substring(msg.indexOf(":_")+2));
							String upgrade = msg.substring(0, msg.indexOf("_was_upgraded_to_tier:_"));
							KuudraShopPrices.upgrades.put(upgrade, level);
							KuudraShopPrices.upgradeCount.put(upgrade, KuudraShopPrices.upgradeCount.get(upgrade)+1);
						} catch(Exception ignored) { }
					}
				} else if(message.startsWith(Minecraft.getMinecraft().thePlayer.getDisplayNameString()+" is now ready!")) {
					KuudraReadyWarning.ready = true;
				} else if(message.startsWith(Minecraft.getMinecraft().thePlayer.getDisplayNameString()+" is no longer ready!")) {
					KuudraReadyWarning.ready = false;
				}
			}



			//PICKAXE ABILITY COOLDOWN
			if(DataGetter.findBool("dwarven.abilityTimer.toggle")) {
					//Ability Used
				if(message.startsWith("You used your ") && message.endsWith(" Pickaxe Ability!")) {
					DwarvenPickaxeTimer.abilityUsed = true;
					DwarvenPickaxeTimer.lastUsed = System.currentTimeMillis();

					switch (message) {
						case "You used your Mining Speed Boost Pickaxe Ability!":
							DwarvenPickaxeTimer.abilityName = "speed";
							break;
						case "You used your Pickobulus Pickaxe Ability!":
							DwarvenPickaxeTimer.abilityName = "piko";
							break;
						case "You used your Maniac Miner Pickaxe Ability!":
							DwarvenPickaxeTimer.abilityName = "maniac";
							break;
						case "You used your Vein Seeker Pickaxe Ability!":
							DwarvenPickaxeTimer.abilityName = "vein";
							break;
					}

					//Ability expired
				} else if(message.startsWith("Your ") && message.endsWith(" has expired!")) {
					DwarvenPickaxeTimer.lastUsed = System.currentTimeMillis();
					DwarvenPickaxeTimer.abilityUsed = false;

					//Ability Available
				} else if(message.endsWith(" is now available!") && (message.startsWith("Mining Speed Boost") || message.startsWith("Pickobulus") || message.startsWith("Maniac Miner") || message.startsWith("Vein Seeker"))) {
					try {
						DwarvenPickaxeTimer.abilityUsed = false;
						DwarvenPickaxeTimer.lastUsed = System.currentTimeMillis() - DwarvenPickaxeTimer.abilities.get(DwarvenPickaxeTimer.abilityName).getCooldown(DwarvenPickaxeTimer.miningAbilityLevel);
					} catch(Exception ignored) {}


				}
			}

			//DWARVEN EVENT TIMER
			if ((msgID.startsWith("2xpowderstarted!") || msgID.startsWith("goblinraidstarted!") || msgID.startsWith("rafflestarted!") || msgID.startsWith("bettertogetherstarted!") || msgID.startsWith("gonewiththewindstarted!") || msgID.startsWith("mithrilgourmandstarted!"))) {
				if (DataGetter.findBool("dwarven.eventTimer.ding")) {
					Utils.playDingSound();
				}
				/*File lastEvents = new File("config/"+Reference.MODID+"/lastDwarvenEvents.txt");
				if(!lastEvents.exists()) { try { lastEvents.createNewFile(); } catch(Exception ignored) {} }
				String lEI= Utils.readFile("config/"+Reference.MODID+"/lastDwarvenEvents.txt");
				String saveEvent = msgID.replace("started!", "");
				Utils.saveFile(saveEvent+";"+lEI, "config/"+Reference.MODID+"/lastDwarvenEvents.txt");*/

				DwarvenTimer.lastEvent = (int) (System.currentTimeMillis());
			}

		//AUTO TELEPORT TO GARRY
		else if (message.contains("event starts in 20 seconds!") && !(message.contains("["))) {
			if (DataGetter.findBool("dwarven.garryTP.toggle")) {

				if (message.toLowerCase().contains("goblin")) {
					if (DataGetter.findBool("dwarven.garryTP.goblinToggle")) {
					} else if (DwarvenGui.currCommissions.toLowerCase().replace(" ", "").contains("goblinraid")) {
					} else { return; }

				} else if (message.toLowerCase().contains("raffle")) {
					if (DataGetter.findBool("dwarven.garryTP.raffleToggle")) {
					} else if (DwarvenGui.currCommissions.toLowerCase().contains("raffle")) {
					} else { return; }

				} else {
					return;
				}
				Thread teleportThread = new GarryTeleportThread();
				teleportThread.start();
			}
		} else if (SBP.sbLocation.equals("dwarvenmines")) {
			char sentries = '\u272f';
			if (message.startsWith(sentries + "")) {
				DwarvenGui.sentryLast = message.substring(message.indexOf("at ") + 3, message.indexOf("! N"));
			}
			else if (message.startsWith("[NPC]")) {
				String newMsg = message.replace("[NPC] ", "");
				boolean isDial = true;

				if (newMsg.startsWith("Gwendolyn: One day I will be useful.")) {
					DialogueThread.dialType = Colors.DARK_PURPLE + "Gwendolyn";
				} else if (newMsg.startsWith("Castle Guard: I'm guarding the lava")) {
					DialogueThread.dialType = Colors.GOLD + "Castle Guard:0";
				} else if (newMsg.startsWith("Castle Guard: I am")) {
					DialogueThread.dialType = Colors.GOLD + "Castle Guard:1";
				} else if (newMsg.startsWith("Castle Guard: Last week")) {
					DialogueThread.dialType = Colors.GOLD + "Castle Guard:2";
				} else if (newMsg.startsWith("Castle Guard: This guy")) {
					DialogueThread.dialType = Colors.GOLD + "Castle Guard:3";
				} else if (newMsg.startsWith("Castle Guard: I'm guarding the guard")) {
					DialogueThread.dialType = Colors.GOLD + "Castle Guard:4";
				} else if (newMsg.startsWith("Castle Guard: I'm guarding the whole kingdom.")) {
					DialogueThread.dialType = Colors.GOLD + "Castle Guard:5";
				} else if (newMsg.startsWith("Castle Guard: I'm a guard.")) {
					DialogueThread.dialType = Colors.GOLD + "Castle Guard:6";
				} else if (newMsg.startsWith("Castle Guard: I, Murdohr, son of Murdohr")) {
					DialogueThread.dialType = Colors.GOLD + "Castle Guard:7";
				} else if (newMsg.startsWith("Castle Guard: I'm guarding nothing.")) {
					DialogueThread.dialType = Colors.GOLD + "Castle Guard:8";
				} else if (newMsg.startsWith("Bednom: But it makes no sense, I've never even heard of such treasure!!")) {
					DialogueThread.dialType = Colors.LIGHT_PURPLE + "Bednom";
				} else if (newMsg.startsWith("Dalbrek: They sometimes show up")) {
					DialogueThread.dialType = Colors.YELLOW + "Dalbrek";
				} else if (newMsg.startsWith("Bylma")) {
					DialogueThread.dialType = Colors.YELLOW + "Bylma";
				} else if (newMsg.startsWith("Dirt Guy")) {
					DialogueThread.dialType = Colors.YELLOW + "Dirt Guy";
				} else if (newMsg.startsWith("Royal Resident: My neighbour")) {
					DialogueThread.dialType = Colors.YELLOW + "Royal Resident";
					Utils.print("Cancelled Message: " + message);
					event.setCanceled(true);
				}


				//PUZZLER SOLVER
				else if (newMsg.startsWith("Puzzler") && DataGetter.findBool("dwarven.puzzlerSolver.toggle")) {
					char up = '\u25b2';
					char right = '\u25b6';
					char down = '\u25bc';
					char left = '\u25c0';
					char peace = '\u270c';
					isDial = false;
					if (newMsg.startsWith("Puzzler: " + right + up + "Come")) {
						RenderGuiEvent.puzzlerParticles = false;
						return;
					} else if (newMsg.startsWith("Puzzler: " + right + right + "Nice!")) {
						RenderGuiEvent.puzzlerParticles = false;
						return;
					} else if (newMsg.startsWith("Puzzler gave you 1,000 Mithril Powder for solving the puzzle!")) {
						RenderGuiEvent.puzzlerParticles = false;
						return;
					} else if (newMsg.contains(peace + "")) {
						RenderGuiEvent.puzzlerParticles = false;
						return;
					}
					;
					char[] msgSplit = newMsg.replace("Puzzler: ", "").toCharArray();
					int x = 0;
					int z = 0;
					for (char c : msgSplit) {
						if ((c + "").equals(up + "")) {
							z += 1;
						} else if ((c + "").equals(down + "")) {
							z -= 1;
						} else if ((c + "").equals(left + "")) {
							x += 1;
						} else if ((c + "").equals(right + "")) {
							x -= 1;
						}
					}
					int startX = 181;
					int startZ = 135;
					StringBuilder output = new StringBuilder();
					TextUtils.sendMessage("Puzzler Block Located");
					if (z >= 0) {
						for (int i = 0; i < z; i++) {
							output.append(up);
						}
					} else {
						for (int i = 0; i < Math.abs(z); i++) {
							output.append(down);
						}
					}
					if (x >= 0) {
						for (int i = 0; i < z; i++) {
							output.append(left);
						}
					} else {
						for (int i = 0; i < Math.abs(z); i++) {
							output.append(right);
						}
					}
					RenderGuiEvent.puzzlerX = startX + x;
					RenderGuiEvent.puzzlerZ = startZ + z;
					RenderGuiEvent.puzzlerParticles = true;
					Utils.print("Cancelled Message: " + message);
					return;


				}


				//FETCHUR SOLVER
				else if (newMsg.startsWith("Fetchur: ") && DataGetter.findBool("dwarven.fetchurSolver.toggle")) {
					String fetch = newMsg.replace("Fetchur: ", "").toLowerCase();
					String output;
					if (fetch.contains("yellow")) {
						output = "20 Yellow Stained Glass (Not Panes)";
					} else if (fetch.contains("circlular")) {
						output = "1 Compass";
					} else if (fetch.contains("minerals")) {
						output = "20 Mithril";
					} else if (fetch.contains("celebrations")) {
						output = "1 Firework Rocket";
					} else if (fetch.contains("hot")) {
						output = "1 Cheap or Decent Coffee";
					} else if (fetch.contains("tall")) {
						output = "1 Wooden Door";
					} else if (fetch.contains("explosive")) {
						output = "1 Superboom TNT";
					} else if (fetch.contains("wearable")) {
						output = "1 Pumpkin";
					} else if (fetch.contains("shiny")) {
						output = "1 Flint and Steel";
					} else if (fetch.contains("soft")) {
						output = "50 Red Wool";
					} else if (fetch.contains("red")) {
						output = "50 Nether Quartz Ore";
					} else if (fetch.contains("round")) {
						output = "16 Ender Pearls";
					} else if (fetch.contains("brown")) {
						output = "3 Rabbit Feet";
					} else {
						return;
					}


					TextUtils.sendMessage(Colors.AQUA + "Fetchur " + Colors.YELLOW + "wants: " + Colors.AQUA + output);
					return;
				} /*else if (newMsg.startsWith("Wizard: ")) {
					isDial = false;
					if (newMsg.contains("Hmmm...")) {
						Utils.sendMessage(Colors.LIGHT_PURPLE + "You're a wizard harry!");
						Minecraft.getMinecraft().thePlayer.sendChatMessage("/play SOLO_INSANE");
						event.setCanceled(true);
					}
				}*/ else {
					isDial = false;
				}

				if (isDial && DataGetter.findBool("misc.npcDialogue.toggle")) {
					DialogueThread.currMessages++;
					DialogueThread dial = new DialogueThread();
					dial.start();
				}

			}
		}

		//ADDITIONAL NPC DIALOUGE
		else if (message.startsWith("Puzzler gave you 1000")) {
			RenderGuiEvent.puzzlerParticles = false;
		} else if (SBP.sbLocation.equals("catacombs")) {

			//if(message.equals("The BLOOD DOOR has been opened!")) {MusicUtils.setSong("config/"+ Reference.MODID+"/music/The Watcher (The Catacombs).mp3"); MusicUtils.playSong();/* Utils.sendMessage("Started \"The Watcher\"");*/}
			//else if(message.equals("[BOSS] The Watcher: You have proven yourself. You may pass.")) {MusicUtils.stopSong(); /*Utils.sendMessage("Stopped \"The Watcher\"");*/}
		} /*else if(SBP.sbLocation.equals("crystalhollows")) {
			if(message.contains("$sbpMap-") && !message.contains(Minecraft.getMinecraft().thePlayer.getDisplayNameString()) && DataGetter.findBool("dwarven.sendMap.toggle")) {

				Thread thread = new Thread(() -> {
					String key = "null";
					try {
						key = message.substring(message.indexOf("$sbpMap-")+8);
						Utils.sendMessage("Downloading map from: "+Colors.AQUA+"https://hst.sh/raw/"+key);
						String decompressed = HttpClient.readPage("https://hst.sh/raw/"+key);
						int[][] out = WaypointUtils.decompress(decompressed, 40, 50);
						WaypointUtils.importMap(out);
					} catch(Exception e) {
						Utils.sendErrMsg("Something went wrong when importing the map from: "+Colors.AQUA+"https://hst.sh/raw/"+key+Colors.YELLOW+"!");
						e.printStackTrace();
					}
				});
				thread.start();
			}
		}*/
		if(message.startsWith("Sending to server ") && message.endsWith("...")) {
			CrystalHollowsMap.resetLocs();
			CrystalHollowsMap.waypoints.clear();
		}
		if (hover.startsWith("This happened thanks to")) {
			if (DataGetter.findBool("misc.jerryTimer.toggle")) {
				Utils.playDingSound();
				Utils.playDingSound();
				com.cobble.sbp.gui.screen.misc.JerryTimer.lastJerry = System.currentTimeMillis();
			}
		}
	}
	}

	
	//REMOVE RANKS FROM A STRING
	public static String removeRankFromString(String string) {
		String temp = string;
		temp = temp.replace("[VIP] ", "");
		temp = temp.replace("[VIP+] ", "");
		temp = temp.replace("[MVP] ", "");
		temp = temp.replace("[MVP+] ", "");
		temp = temp.replace("[MVP++] ", "");
		temp = temp.replace("[YOUTUBE] ", "");
		temp = temp.replace("[PIG+++] ", "");
		temp = temp.replace("[ADMIN] ", "");
		temp = temp.replace("[MINISTER] ", "");
		temp = temp.replace("[HELPER] ", "");
		temp = temp.replace("[MOD] ", "");
		temp = temp.replace("[BUILDER] ", "");
		temp = temp.replace("[MAYOR] ", "");
		temp = temp.replace("[OWNER] ", "");
		return temp;
	}
	
	
	//CHECK IF IN MINING AREA
	public static boolean inMines() {
		return SBP.sbLocation.equals("dwarvenmines") || SBP.sbLocation.equals("deepcaverns") || SBP.sbLocation.equals("goldmine") || SBP.sbLocation.equals("crystalhollows") || SBP.sbLocation.equals("crimsonisle");
	}
	
}