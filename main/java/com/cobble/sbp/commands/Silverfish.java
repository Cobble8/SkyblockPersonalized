package com.cobble.sbp.commands;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.threads.onetimes.DisablePuzzleImageThread;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class Silverfish extends CommandBase{

	@Override
	public String getCommandName() {
		return "silverfish";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/silverfish";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
			DisablePuzzleImageThread.delay = Long.parseLong(DataGetter.find("puzzleDelay")+"");
			RenderGuiEvent.imageID="silverfish";
			Thread disableImage = new DisablePuzzleImageThread();
			disableImage.start();
			
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
}