package com.cobble.sbp.commands;

import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.handlers.DownloadSecretsHandler;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.SecretUtils;
import com.cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

import java.util.ArrayList;
import java.util.List;

public class SecretFinder extends CommandBase{
	private final ArrayList<String> aliases;
	//Minecraft MC;
	
	public static String args0;
	public static String args1;
	public static int argsLength;
	
	public SecretFinder() {
		aliases = new ArrayList<>();
	}
	
	@Override
	public List getCommandAliases() {
		
		aliases.add("scrt");
		aliases.add("scrts");
		aliases.add("secret");
		return aliases;
	}
	
	
	
	@Override
	public String getCommandName() {
		return "secrets";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/secrets [roomSize] [secretCount]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		
		SecretImage.maxSecrets=0;
		SecretImage.currentSecret=0;
		SecretImage.currentSecretText.clear();
		SecretImage.currDungeon="catacombs";
		SecretImage.downloadImage=false;
		int dungOffset = 0;

		if(DownloadSecretsHandler.dungeons.size() < 1) {
			SecretUtils.updateDungeonList();
		}
		if(DownloadSecretsHandler.dungeons.size() > 1) {
			dungOffset = 1;
		}

		if(dungOffset == 1) {
			
			
			//Dungeon Argument
			try {
				SecretImage.currDungeon=args[0];
			} catch(Exception e) {
				StringBuilder output = new StringBuilder();
				for(int i=0;i<DownloadSecretsHandler.dungeons.size();i++) {
					output.append(Colors.YELLOW).append(DownloadSecretsHandler.dungeons.get(i));
					if(i != DownloadSecretsHandler.dungeons.size()-1) { output.append(Colors.AQUA).append(", "); }
				}	
				Utils.sendErrMsg("You must supply a valid dungeon name! Examples: "+output);
				return;
			}
			
			//Room Shape Argument
			ArrayList<String> possibleRoomShapes = SecretUtils.getRoomShapes(SecretImage.currDungeon);
			try {
				for(String str : possibleRoomShapes) {
					if(str.toLowerCase().equals(args[0].toLowerCase())) {
						SecretImage.roomShape=str;
					}
				}
				
			} catch(Exception e) {
				
				StringBuilder output = new StringBuilder();
				for(int i=0;i<possibleRoomShapes.size();i++) {
					output.append(Colors.AQUA).append(possibleRoomShapes.get(i));
					if(i != possibleRoomShapes.size()-1) { output.append(Colors.YELLOW).append(", "); }
				}	
				
				Utils.sendErrMsg("You must provide a valid room shape! Examples: "+output);
				return;
			}
			
			//Secret Amount Argument
			try {
				ArrayList<String> possibleRooms = SecretUtils.getRoomName(SecretImage.roomShape, Integer.parseInt(args[2]), SecretImage.currDungeon);
				if(possibleRooms.size() == 0) {
					throw new Exception("This literally doesn't matter I'm pretty sure");
				} else if(possibleRooms.size() == 1) {
					SecretImage.roomSecretsID=possibleRooms.get(0);
					SecretImage.currentSecretText = SecretUtils.getRoomDesc(SecretImage.roomShape, SecretImage.roomSecretsID, SecretImage.currDungeon);
					Utils.sendMessage("Found room: '"+Colors.AQUA+possibleRooms.get(0)+Colors.YELLOW+"' for your provided arguments!");
					SecretImage.reloadImage=true;
				} else {
					int clr = 0;
					for(String str : possibleRooms) {
						clr++;
						String color = Colors.AQUA;
						if(clr == 2) { color=Colors.BLUE; clr=0;}
						String output = SecretUtils.prettifyRoomname(str);
						ChatStyle runCommand = new ChatStyle();
						runCommand.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/secretoverride "+SecretImage.roomShape+" "+str));
						runCommand.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+"Click to show secrets for the room: "+color+output)));
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(color+output).setChatStyle(runCommand));
					}
				}

			} catch(Exception e) {
				Utils.sendErrMsg("There are no rooms in the '"+args[0]+"' dungeon in room shape '"+args[1]+"' with '"+args[2]+"' secrets!");
			}
		} else {
			//Room Shape Argument
			ArrayList<String> possibleRoomShapes = SecretUtils.getRoomShapes(SecretImage.currDungeon);
			try {
				boolean passThru = false;
				for(String str : possibleRoomShapes) {
					if(str.toLowerCase().startsWith(args[0].toLowerCase())) {
						SecretImage.roomShape=str;
						passThru=true;
					}
				} if(!passThru) {
					throw new Exception("This literally doesn't matter iirc");
				}
				
			} catch(Exception e) {
				
				StringBuilder output = new StringBuilder();
				for(int i=0;i<possibleRoomShapes.size();i++) {
					output.append(Colors.AQUA).append(SecretUtils.prettifyRoomname(possibleRoomShapes.get(i)));
					if(i != possibleRoomShapes.size()-1) { output.append(Colors.YELLOW).append(", "); }
				}	
				
				Utils.sendErrMsg("You must provide a valid room shape! Examples: "+output);
				return;
			}
			
			//Secret Amount Argument
			try {
				ArrayList<String> possibleRooms = SecretUtils.getRoomName(SecretImage.roomShape, Integer.parseInt(args[1]), SecretImage.currDungeon);
				SecretImage.maxSecrets=Integer.parseInt(args[1]);
				if(possibleRooms.size() == 0) {
					throw new Exception("This literally doesn't matter I'm pretty sure");
				} else if(possibleRooms.size() == 1) {
					SecretImage.roomSecretsID=possibleRooms.get(0);
					SecretImage.currentSecretText = SecretUtils.getRoomDesc(SecretImage.roomShape, SecretImage.roomSecretsID, SecretImage.currDungeon);
					Utils.sendMessage("Found room: '"+Colors.AQUA+possibleRooms.get(0)+Colors.YELLOW+"' for your provided arguments!");
					SecretImage.reloadImage=true;
				} else {
					
					Utils.sendMessage("Found these possible rooms for your specified arguments:");
					int clr = 0;
					for(String room : possibleRooms) {
						
						String str = SecretUtils.prettifyRoomname(room).toLowerCase();
						clr++;
						String color = Colors.AQUA;
						if(clr == 2) { color=Colors.BLUE; clr=0;}
						String output = SecretUtils.prettifyRoomname(str);
						ChatStyle runCommand = new ChatStyle();
						runCommand.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/secretoverride "+SecretImage.currDungeon+" "+SecretImage.roomShape+" "+(str.replace(" ", "-"))));
						//runCommand.setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/secretoverride "+SecretImage.roomShape+" "+(str.replace(" ", "-"))));
						runCommand.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+"Click to show secrets for the room: "+color+output)));
						Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(color+output).setChatStyle(runCommand));
					}
				}
				
				
			} catch(Exception e) {
				try {
					Utils.sendErrMsg("There are no rooms in the '"+SecretImage.currDungeon+"' dungeon in room shape '"+args[0]+"' with '"+args[1]+"' secrets!");
				} catch(Exception e2) {
					Utils.sendErrMsg("You must provide a valid amount of secrets!");
					Utils.sendErrMsg("Command Usage: "+Colors.AQUA+"/"+Colors.GOLD+"secrets"+Colors.AQUA+" ["+Colors.GOLD+"roomSize"+Colors.AQUA+"] ["+Colors.GOLD+"secretCount"+Colors.AQUA+"]");
				}
				
				return;
			}
		}

		
				
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
}
