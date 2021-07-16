package com.cobble.sbp.commands;

import java.util.ArrayList;
import java.util.List;

import com.cobble.sbp.core.ConfigList;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.utils.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

public class Main extends CommandBase {
	ArrayList aliases;
	public Main() {
		aliases = new ArrayList();
		aliases.add("skyblockpersonalized");
	}
	
	@Override
	public String getCommandName() {
		return Reference.MODID;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/"+getCommandName();
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		
		if(args.length > 0) {
			String args0 = args[0].toLowerCase();
			switch (args0) {
				case "dev":
					//DwarvenGui.manageCommissions(" Golden Goblin Slayer: 0%");
					return;
				case "reloadconfig":
					Utils.sendMessage("Reloaded Config!");
					ConfigHandler.updateConfig("main");

					return;
				case "game":
					RenderGuiEvent.currSettingMenu="game";
					RenderGuiEvent.helpMenu=true;
					return;
				case "life":
					RenderGuiEvent.currSettingMenu="life";
					RenderGuiEvent.helpMenu=true;
					return;
				case "update":

					new sbpUpdater().start();

					return;
				case "setconfig":
					try {
						setConfig(args[1], args[2], args[3]);
					} catch (Exception e) {
						Utils.sendErrMsg("Failed to set the config variable! Usage: /" + Reference.MODID + " setconfig newValueType configVariable newValue");
					}
					return;
				case "getconfig":
					try {
						String args1 = "";
						String args2 = "";
						if (args.length >= 3) {
							args2 = args[2];
						}
						if (args.length >= 2) {
							args1 = args[1];
						}
						getConfig(args1, args2, args.length - 1);
					} catch (Exception e) {
						e.printStackTrace();
						Utils.sendErrMsg("Failed to get the config variable! Usage: /" + Reference.MODID + " getconfig configVariable");
					}
					return;
				case "lagcheck":

					if(!sender.getDisplayName().getUnformattedText().equals("Cobble8")) {return;}

					if(!Minecraft.getMinecraft().isSingleplayer()) {
						Utils.sendMessage("Note: Updates on login");
						Utils.sendMessage("Total Player Count: "+Minecraft.getMinecraft().getCurrentServerData().populationInfo);
						Utils.sendMessage("Your Ping: "+Minecraft.getMinecraft().getCurrentServerData().pingToServer);
					}
					return;

			}
			
		}
		RenderGuiEvent.helpMenu=true;
		SettingMenu.fadeIn = 0;
		SettingMenu.fadeInFrames=0;


	}
	
	@Override
	public List<String> getCommandAliases() {
		return aliases;
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

	public static class sbpUpdater extends Thread {
		public void run() {
			Utils.sendMessage("Downloading new "+Reference.NAME+" version!");
			String file;
			try { file = HttpClient.readPage("https://raw.githubusercontent.com/Cobble8/SkyblockPersonalized/main/versioncheck.json");
			} catch (Exception e) { return; }

			JsonElement info = new JsonParser().parse(file);
			String updateUrl = info.getAsJsonObject().get("updateUrl").getAsString();
			Utils.sendMessage("Found update URL:");
			Utils.sendSpecificMessage(Colors.AQUA+updateUrl);
			new AutoUpdater(updateUrl);

			Utils.sendMessage("Finished Downloading! "+Colors.GOLD+"Restart Minecraft for the update to take effect!");
			Utils.playDingSound();
		}

	}
	
	
	private static void setConfig(String type, String configValue, String newValue) {
		if(type.equalsIgnoreCase("int")) {
			try {
				
				if(DataGetter.findInt(configValue) == -69) {
					Utils.sendErrMsg("That is not a valid config variable!");
					return;
				}
				
				int num = Integer.parseInt(newValue);
				ConfigHandler.newObject(configValue, num);
				Utils.sendMessage("Config Variable: "+Colors.AQUA+configValue+Colors.YELLOW+" has been set to "+Colors.AQUA+newValue+Colors.YELLOW+".");
			} catch(Exception e) {
				Utils.sendErrMsg("That is not a valid number!");
			}
		} else if(type.equalsIgnoreCase("str") || type.equalsIgnoreCase("string")) {
			
			
			try {
				
				if(DataGetter.findStr(configValue) == null) {
					Utils.sendErrMsg("That is not a valid config variable!");
				}
				
				ConfigHandler.newObject(configValue, newValue);
				Utils.sendMessage("Config Variable: "+Colors.AQUA+configValue+Colors.YELLOW+" has been set to "+Colors.AQUA+newValue+Colors.YELLOW+".");
			} catch(Exception e) {
				Utils.sendErrMsg("That is not a valid string!");
			}
			
		} else if(type.equalsIgnoreCase("bool") || type.equalsIgnoreCase("boolean")) {
			
			
			try {
				
				if(DataGetter.findBool(configValue) == null) {
					Utils.sendErrMsg("That is not a valid config variable!");
				}
				Boolean bool = Boolean.parseBoolean(newValue);
				
				ConfigHandler.newObject(configValue, bool);
				Utils.sendMessage("Config Variable: "+Colors.AQUA+configValue+Colors.YELLOW+" has been set to "+Colors.AQUA+newValue+Colors.YELLOW+".");
			} catch(Exception e) {
				Utils.sendErrMsg("That is not a valid boolean!");
			}
			
		}
	}
	
	private static void getConfig(String args0, String args1, int argslength) {
		String output = "";
		String string = "";
		if(argslength == 0) {
			Boolean currColor = false;
			String currPrefix;
			
			Utils.sendSpecificMessage(Colors.YELLOW+"--------------------");
			Utils.sendMessage(Colors.YELLOW+"Config List:");
			for(int i=0;i<ConfigList.c.size();i++) {
				
				if(currColor) {
					currPrefix = Colors.GREEN;
				} else {
					currPrefix = Colors.AQUA;
				}
				currColor = Utils.invertBoolean(currColor);
				
				ChatStyle hoverText = new ChatStyle();
				hoverText.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/"+Reference.MODID+" getconfig "+ConfigList.c.get(i)));
				hoverText.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+"Click to check the variable: "+currPrefix+ConfigList.c.get(i))));
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(currPrefix+ConfigList.c.get(i)).setChatStyle(hoverText));
				
			}
			Utils.sendSpecificMessage(Colors.YELLOW+"--------------------");
			return;
		}
		if(argslength == 1) {
			
			output = DataGetter.find(args0)+"";
			string = Colors.YELLOW+"Variable: "+Colors.AQUA+args0+Colors.YELLOW+", Output: "+Colors.AQUA+output;
			
		}
		if(argslength == 2) {
			output = ""+DataGetter.find(args0, args1);
			string = Colors.YELLOW+"File: "+Colors.AQUA+args0+Colors.YELLOW+", Variable: "+Colors.AQUA+args1+Colors.YELLOW+", Output: "+Colors.AQUA+output;
		
		} 
		
		ChatStyle hoverText = new ChatStyle();
		hoverText.setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, output));
		hoverText.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+"Click to put the output into chat")));
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(string).setChatStyle(hoverText));

	}
}
