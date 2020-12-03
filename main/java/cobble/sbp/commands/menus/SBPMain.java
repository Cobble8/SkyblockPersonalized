package cobble.sbp.commands.menus;

import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class SBPMain extends CommandBase{

	@Override
	public String getCommandName() {
		return "sbp";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/sbp";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if((Boolean) DataGetter.find("modToggle")) {
			RenderGuiHandler.MainGUI=true;
			
			
		} else Utils.enableMod();	
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
}

