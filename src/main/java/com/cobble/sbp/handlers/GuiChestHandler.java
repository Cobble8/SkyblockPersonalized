package com.cobble.sbp.handlers;

import java.util.List;

import com.cobble.sbp.gui.screen.misc.LockedSlots;
import com.cobble.sbp.utils.TextUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.user.MenuClickEvent;
import com.cobble.sbp.events.user.PressKeyEvent;
import com.cobble.sbp.gui.screen.dwarven.DwarvenCompletedCommissions;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class GuiChestHandler {


	public static String menuName = "";

	public GuiChestHandler() {
		try {
			Minecraft mc = Minecraft.getMinecraft();
			EntityPlayerSP player = mc.thePlayer;
			GuiScreen guiScreen = mc.currentScreen;
			
	        if(guiScreen instanceof GuiChest) {
	            IInventory cc = ((ContainerChest) ((GuiChest) guiScreen).inventorySlots).getLowerChestInventory();
	            menuName = cc.getDisplayName().getUnformattedText().trim().toLowerCase().replace(" ", "_");





	        }
			
	        if(guiScreen instanceof GuiChest) {
				boolean inCraft = false;



	            IInventory cc = ((ContainerChest) ((GuiChest) guiScreen).inventorySlots).getLowerChestInventory();
	            menuName = cc.getDisplayName().getUnformattedText().trim().toLowerCase().replace(" ", "_");

				if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1) || Keyboard.isKeyDown(KeyBindingHandler.lockQuickCraft.getKeyCode())) { if(MenuClickEvent.menuClickAvailable) { MenuClickEvent.menuClickAvailable = false; }} else { MenuClickEvent.menuClickAvailable = true; }

					switch(menuName) {
						case "calendar_and_events":



							return;


						case "heart_of_the_mountain":
							if(TextUtils.unformatAllText(cc.getStackInSlot(10).getDisplayName()).equals("Sky Mall")) {
								ItemStack skymall = cc.getStackInSlot(10);
								List<String> desc  = skymall.getTooltip(player, false);
								int skyUnlocked = desc.size()-1;

								if(TextUtils.unformatAllText(desc.get(skyUnlocked)).equals("UNLOCKED")) {
									String isActive = TextUtils.unformatAllText(desc.get(skyUnlocked-2));
									DwarvenPickaxeTimer.isSkymall = isActive.contains("20%");
								} else { DwarvenPickaxeTimer.isSkymall=false; }
							}

							if(TextUtils.unformatAllText(cc.getStackInSlot(0).getDisplayName()).trim().replace(" ", "").contains("Tier5")) {
								ItemStack tier5 = cc.getStackInSlot(0);
								List<String> desc = tier5.getTooltip(player, false);
								int isTier5 = desc.size()-1;

								if(TextUtils.unformatAllText(desc.get(isTier5)).equals("UNLOCKED")) {
									if(!(DataGetter.findInt("dwarven.user.hotmLevel") == 5)) {
										ConfigHandler.newObject("dwarven.user.hotmLevel", 5);
										DwarvenPickaxeTimer.hotmLevel = 5;
									}
								}
							}
							break;

						case "commissions":

							if(!DataGetter.findBool("dwarven.doneCommBg.toggle")) {return;}
							for(int i=0;i<9;i++) {
								int currSlot = i+9;
								//DwarvenCompletedCommissions.slots.clear();
								if(TextUtils.unformatAllText(cc.getStackInSlot(currSlot).getDisplayName()).contains("Commission")) {
									ItemStack comm = cc.getStackInSlot(currSlot);
									List<String> desc = comm.getTooltip(player, false);

									if(TextUtils.unformatAllText(desc.get(desc.size()-1)).contains("Click to claim rewards!")) {
										DwarvenCompletedCommissions.slots.put(i, true);
									} else {
										DwarvenCompletedCommissions.slots.put(i, false);
									}
								} else {
									DwarvenCompletedCommissions.slots.put(i, false);
								}
							}
							break;

						case "forge":
							try {
								for(int i=11;i<16;i++) {
									String name2;
									try {
										name2 = cc.getStackInSlot(i).getDisplayName();
									} catch(Exception e) {
										continue;
									}
									int forgeSlot = i-10;
									if(!name2.contains("Slot")) {

										List<String> lore = cc.getStackInSlot(i).getTooltip(Minecraft.getMinecraft().thePlayer, false);
										for (String s : lore) {
											String curr = TextUtils.unformatAllText(s); //get rid of formatting

											if (curr.startsWith("Time Remaining: ")) { //make sure it's the right line

												curr = curr.substring(curr.indexOf(":") + 2);
												String[] timeArray = curr.split(" ");
												long output = 0;
												for (String inp : timeArray) {
													if (inp.endsWith("s")) {
														inp = inp.replace("s", "");
														try { output += (1000L * Integer.parseInt(inp)); } catch (Exception ignored) { }
													} else if (inp.endsWith("m")) {
														inp = inp.replace("m", "");
														try { output += (60000L * Integer.parseInt(inp)); } catch (Exception ignored) { }
													} else if (inp.endsWith("h")) {
														inp = inp.replace("h", "");
														try { output += (3600000L * Integer.parseInt(inp)); } catch (Exception ignored) { }
													} else if (inp.endsWith("d")) {
														inp = inp.replace("d", "");
														try { output += (86400000L * Integer.parseInt(inp)); } catch (Exception ignored) { }
													}
												}


												long currSlot = DataGetter.findLong("dwarven.forgeReminder."+forgeSlot);
												if(currSlot == -1 || currSlot < System.currentTimeMillis()) {
													long inp = output+System.currentTimeMillis();
													ConfigHandler.newObject("dwarven.forgeReminder."+forgeSlot, inp);
												}
											}
										}
									} else { ConfigHandler.newObject("dwarven.forgeReminder."+forgeSlot, -1); }
								}
							} catch(Exception ignored) {}
							break;

						case "craft_item":
							if(!DataGetter.findBool("qol.lockQuickCrafts.toggle")) {return;}
							inCraft=true;
							MenuClickEvent.itemList.clear();
							for(int r=0;r<cc.getSizeInventory();r++) { try { MenuClickEvent.itemList.add(cc.getStackInSlot(r).getDisplayName()); } catch(Exception e) { MenuClickEvent.itemList.add("");}}

							String[] blockedList = DataGetter.findStr("qol.lockQuickCrafts.items").split(";");

							PressKeyEvent.inCraftMenu=true;
							for(int k=0;k<3;k++) {
								int currSlot = 16+(k*9);
								try {
									String currItem = cc.getStackInSlot(currSlot).getDisplayName().toLowerCase().replace(" ", "_");
									currItem = TextUtils.unformatAllText(currItem);

									boolean locked = false;

									for(String item : blockedList) {
										if (item.equals(currItem)) {
											locked = true;
											break;
										}
									}

									if(locked) { LockedSlots.slots.put(k, true); } else { LockedSlots.slots.put(k, false); }
								} catch(Exception ignored) { }
							}
							break;

					}
					if(!inCraft) { PressKeyEvent.inCraftMenu=false; MenuClickEvent.itemList.clear(); }


	        } else { menuName = ""; }
		} catch(Exception ignored) {  }
	}
}
