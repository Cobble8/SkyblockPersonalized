package com.cobble.sbp.commands;

import java.util.ArrayList;
import java.util.List;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class SetConfig extends CommandBase {
	ArrayList<String> aliases;
	public SetConfig() {
		aliases = new ArrayList();
		//aliases.add("aliasHere");
	}
	
	@Override
	public String getCommandName() {
		return "setconfig";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if(args.length > 0) {
			if(args[0].toLowerCase().equals("int")) {
				try {
					
					if(DataGetter.findInt(args[1]) == -69) {
						Utils.sendErrMsg("That is not a valid config variable!");
						return;
					}
					
					int num = Integer.parseInt(args[2]);
					ConfigHandler.newObject(args[1], num);
					Utils.sendMessage("Config Variable: "+Colors.AQUA+args[1]+Colors.YELLOW+" has been set to "+Colors.AQUA+args[2]+Colors.YELLOW+".");
				} catch(Exception e) {
					Utils.sendErrMsg("That is not a valid number!");
				}
			} else if(args[0].toLowerCase().equals("str") || args[0].toLowerCase().equals("string")) {
				
				
				try {
					
					if(DataGetter.findStr(args[1]) == null) {
						Utils.sendErrMsg("That is not a valid config variable!");
					}
					
					ConfigHandler.newObject(args[1], args[2]);
					Utils.sendMessage("Config Variable: "+Colors.AQUA+args[1]+Colors.YELLOW+" has been set to "+Colors.AQUA+args[2]+Colors.YELLOW+".");
				} catch(Exception e) {
					Utils.sendErrMsg("That is not a valid string!");
				}
				
			} else if(args[0].toLowerCase().equals("bool") || args[0].toLowerCase().equals("boolean")) {
				
				
				try {
					
					if(DataGetter.findBool(args[1]) == null) {
						Utils.sendErrMsg("That is not a valid config variable!");
					}
					Boolean bool = Boolean.parseBoolean(args[2]);
					
					ConfigHandler.newObject(args[1], bool);
					Utils.sendMessage("Config Variable: "+Colors.AQUA+args[1]+Colors.YELLOW+" has been set to "+Colors.AQUA+args[2]+Colors.YELLOW+".");
				} catch(Exception e) {
					Utils.sendErrMsg("That is not a valid boolean!");
				}
				
			} else {
				Utils.sendErrMsg("Please provide a valid config type");
			}
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