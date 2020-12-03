package cobble.sbp.commands.menus;

import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.threads.DisableScreenImageThread;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
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
		if((Boolean) DataGetter.find("modToggle")) {
			DisableScreenImageThread.delay = Long.parseLong(DataGetter.find("imageDelay")+"");
			RenderGuiHandler.imageID="silverfish";
			Thread disableImage = new DisableScreenImageThread();
			disableImage.start();
			
		} else Utils.enableMod();	
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
}