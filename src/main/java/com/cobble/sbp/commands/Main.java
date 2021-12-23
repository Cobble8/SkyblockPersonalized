package com.cobble.sbp.commands;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.threads.misc.SBPUpdater;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends CommandBase {
	ArrayList<String> aliases;
	public Main() {
		aliases = new ArrayList<>();
		aliases.add("skyblockpersonalized");
	}

	public static ArrayList<ArrayList<Double>> boxes = new ArrayList<>();

	
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
					System.out.println(ConfigHandler.config.toString());

					return;
				case "reloadconfig":
					Utils.sendMessage("Reloaded Config!");
					ConfigHandler.updateConfig("main");
					try {
						ConfigHandler.rewriteConfig();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return;
				case "game":
					SettingMenu.currSettingMenu="game";
					RenderGuiEvent.helpMenu=true;
					return;
				case "life":
					SettingMenu.currSettingMenu="life";
					RenderGuiEvent.helpMenu=true;
					return;
				case "update":

					new SBPUpdater().start();

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

						if(args1.equals("getConfig:")) {
							getConfig(args2);
						} else {
							getConfig("");
						}

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

				DataGetter.findStr(configValue);

				ConfigHandler.newObject(configValue, newValue);
				Utils.sendMessage("Config Variable: "+Colors.AQUA+configValue+Colors.YELLOW+" has been set to "+Colors.AQUA+newValue+Colors.YELLOW+".");
			} catch(Exception e) {
				Utils.sendErrMsg("That is not a valid string!");
			}
			
		} else if(type.equalsIgnoreCase("bool") || type.equalsIgnoreCase("boolean")) {
			
			
			try {

				DataGetter.findBool(configValue);
				Boolean bool = Boolean.parseBoolean(newValue);
				
				ConfigHandler.newObject(configValue, bool);
				Utils.sendMessage("Config Variable: "+Colors.AQUA+configValue+Colors.YELLOW+" has been set to "+Colors.AQUA+newValue+Colors.YELLOW+".");
			} catch(Exception e) {
				Utils.sendErrMsg("That is not a valid boolean!");
			}
			
		}
	}
	
	private static void getConfig(String keyCode) {

		if(keyCode.equals("")) {
			HashMap<String, Integer> categories = new HashMap<>();
			for(String key : ConfigHandler.config.keySet()) {
				String[] keys = key.split("\\.");
				try {
					categories.put(keys[0], categories.get(keys[0])+1);
				} catch(Exception e) {
					categories.put(keys[0], 1);
				}
			}
			Utils.sendSpecificMessage(Colors.YELLOW+"--------------------");
			Utils.sendMessage(Colors.YELLOW+"Config List:");
			int i=0;
			for(String key : categories.keySet()) {
				i++;
				String color = Colors.GREEN;
				if(i%2==0) {color = Colors.AQUA;}
				ChatStyle clickHover = new ChatStyle();
				clickHover.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+"Click to show all config variables under the category of '"+Colors.AQUA+key+Colors.YELLOW+"' ("+Colors.AQUA+categories.get(key)+Colors.YELLOW+")")));
				clickHover.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sbp getConfig getConfig: "+key));
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(color+key).setChatStyle(clickHover));
			}
		} else {
			HashMap<String, Integer> vars = new HashMap<>();
			Utils.sendSpecificMessage(Colors.YELLOW+"--------------------");
			Utils.sendMessage(Colors.YELLOW+"Config List for "+Colors.AQUA+keyCode+Colors.YELLOW+":");
			for(String key : ConfigHandler.config.keySet()) {

				if(key.startsWith(keyCode)) {
					//dungeons
					//dungeons.setting
					//dungeons.setting.subsetting
					String tmpVar;
					try {
						tmpVar = key.substring(keyCode.length()+1);
					} catch(Exception e) {




						return;
					}

					try {
						tmpVar = tmpVar.substring(0, tmpVar.indexOf("."));
					} catch(Exception ignored) {}
					try {
						vars.put(tmpVar, vars.get(tmpVar)+1);
					} catch(Exception e) {
						vars.put(tmpVar, 1);
					}
				}
			}

			int i=0;
			for(String key : vars.keySet()) {
				i++;
				String color = Colors.GREEN;
				if(i%2==0) {color = Colors.AQUA;}

				if(keyCode.contains(".")) {
					ChatStyle clickHover = new ChatStyle();
					clickHover.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+"Click to put the value of '"+color+key+Colors.YELLOW+"' into chat!")));
					clickHover.setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, ConfigHandler.config.get(keyCode+"."+key)+""));
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(color+key).setChatStyle(clickHover));
				} else {
					ChatStyle clickHover = new ChatStyle();
					clickHover.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+"Click to show all config variables under the category of '"+Colors.AQUA+key+Colors.YELLOW+"' ("+Colors.AQUA+vars.get(key)+Colors.YELLOW+")")));
					clickHover.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sbp getConfig getConfig: "+keyCode+"."+key));
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(color+key).setChatStyle(clickHover));
				}


			}
		}


	}
		/*String output = "";
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

	}*/
}
