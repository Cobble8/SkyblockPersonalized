package cobble.sbp.commands.menus;

import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.gui.menu.Puzzles;
import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import cobble.sbp.utils.config.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class AutoPuzzle extends CommandBase{

	@Override
	public String getCommandName() {
		return "autopuzzle";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/autopuzzle";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if((Boolean) DataGetter.find("modToggle")) {
			RenderGuiHandler.PuzzleGUI = true;
		} else Utils.enableMod();
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
}
