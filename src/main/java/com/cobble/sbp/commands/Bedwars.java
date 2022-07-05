package com.cobble.sbp.commands;

import java.util.ArrayList;
import java.util.List;

import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.TextUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class Bedwars extends CommandBase {
	ArrayList<String> aliases;
	public Bedwars() {
		aliases = new ArrayList<>();
		aliases.add("bedwars");
	}
	
	@Override
	public String getCommandName() {
		return "bw";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/bw <gamemode>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) {
		if(args.length >= 1) {
			String arg = args[0].toLowerCase();
			if(arg.equals("solo") || arg.equals("solos") || arg.equals("1") || arg.equals("1v1")) {
				Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_eight_one");
			} else if(arg.replace("s", "").equals("double") || arg.equals("2") || arg.equals("2v2")) {
				Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_eight_two");
			} else if(arg.replace("s", "").equals("triple") || arg.equals("3") || arg.equals("3v3")) {
				Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_four_three");
			} else if(arg.replace("s", "").equals("four") || arg.equals("4") || arg.equals("4v4")) {
				Minecraft.getMinecraft().thePlayer.sendChatMessage("/play bedwars_four_four");
			}
		} else {
			TextUtils.sendErrMsg(Colors.RED+"Missing arguments: '"+Colors.DARK_RED+"GAMEMODE"+Colors.RED+"'");
			TextUtils.sendSpecificMessage(Colors.RED+"Gamemodes:");
			TextUtils.sendSpecificMessage(Colors.RED+"Solos: '1v1', '1', 'solo', 'solos'");
			TextUtils.sendSpecificMessage(Colors.RED+"Doubles: '2v2', '2', 'double', 'doubles'");
			TextUtils.sendSpecificMessage(Colors.RED+"Triples: '3v3', '3', 'triple', 'triples'");
			TextUtils.sendSpecificMessage(Colors.RED+"Fours: '4v4', '4', 'four', 'fours'");
		}
		
		
	}
	
	@Override
	public List<String> getCommandAliases() {
		return aliases;
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

}
