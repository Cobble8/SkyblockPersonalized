package com.cobble.sbp.commands;

import java.util.ArrayList;
import java.util.List;

import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class LagCheck extends CommandBase {
	ArrayList<String> aliases;
	public LagCheck() {
		aliases = new ArrayList();
		//aliases.add("aliasHere");
	}
	
	@Override
	public String getCommandName() {
		return "lagcheck";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/lagcheck";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		Utils.sendMessage("Note: Updates on login");
		Utils.sendMessage("Total Player Count: "+Minecraft.getMinecraft().getCurrentServerData().populationInfo);
		Utils.sendMessage("Your Ping: "+Minecraft.getMinecraft().getCurrentServerData().pingToServer);
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