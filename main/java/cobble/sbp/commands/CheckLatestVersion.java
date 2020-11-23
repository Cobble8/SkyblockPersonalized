package cobble.sbp.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cobble.sbp.threads.VersionCheckerThread;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CheckLatestVersion extends CommandBase {
	private final ArrayList aliases;
	Minecraft MC;
	
	public CheckLatestVersion() 
	{
		aliases = new ArrayList();
	}
	
	
	@Override
	public String getCommandName() {
		return "versioncheck";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/versioncheck";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if((Boolean) DataGetter.find("modToggle")) {
			
		Thread versionCheckerCommand = new VersionCheckerThread();
		versionCheckerCommand.start();
		
		} else Utils.enableMod();
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
