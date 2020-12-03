package cobble.sbp.commands.setconfig;

import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import cobble.sbp.utils.config.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class SetImageDelay extends CommandBase {

	@Override
	public String getCommandName() {
		return "setimagedelay";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/setimagedelay [delay]";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		
		if((Boolean) DataGetter.find("modToggle")) {
			if(args.length == 0) {
				Utils.sendMessage(ChatFormatting.RED+"Please supply a number!");
			} else if(Utils.isNumeric(args[0])) {
				try { ConfigHandler.newObject("imageDelay", Integer.parseInt(args[0])); } catch (IOException e) { e.printStackTrace(); }
				Utils.sendMessage(ChatFormatting.GREEN+"Successfully set your Image Delay to "+ChatFormatting.AQUA+args[0]+ChatFormatting.GREEN+" seconds!");
			} else {
				Utils.sendMessage("Please supply an actual number!");
			}
		} else Utils.enableMod();
		
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

}