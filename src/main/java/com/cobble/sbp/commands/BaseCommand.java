package com.cobble.sbp.commands;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class BaseCommand extends CommandBase {
	ArrayList aliases;
	public BaseCommand() {
		aliases = new ArrayList();
		//aliases.add("aliasHere");
	}
	
	@Override
	public String getCommandName() {
		return "commandName";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "Usage";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		//Do everything here
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
