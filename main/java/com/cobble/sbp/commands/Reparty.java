package com.cobble.sbp.commands;

import java.util.ArrayList;
import java.util.List;

import com.cobble.sbp.threads.onetimes.RepartyThread;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class Reparty extends CommandBase {
	ArrayList<String> aliases;
	public Reparty() {
		aliases = new ArrayList();
		aliases.add("rp");
	}
	
	@Override
	public String getCommandName() {
		return "reparty";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/reparty";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		Thread runCmd = new RepartyThread();
		runCmd.start();
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
