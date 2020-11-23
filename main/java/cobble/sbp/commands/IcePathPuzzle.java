package cobble.sbp.commands;

import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.gui.menu.Puzzles;
import cobble.sbp.handlers.ConfigHandler;
import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class IcePathPuzzle extends CommandBase{

	@Override
	public String getCommandName() {
		return "icefill";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/icepath";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if((Boolean) DataGetter.find("modToggle")) {
			RenderGuiHandler.PuzzleGUI = true;
			Puzzles.puzzleID=0;
		} else Utils.enableMod();
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
}
