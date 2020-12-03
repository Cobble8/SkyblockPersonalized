package cobble.sbp.commands.menus;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.gui.menu.Puzzles;
import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class HelpCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "sbphelp";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/sbphelp";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if((Boolean) DataGetter.find("modToggle")) {
			RenderGuiHandler.HelpGUI=true;
			
			
		} else Utils.enableMod();	
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
}
