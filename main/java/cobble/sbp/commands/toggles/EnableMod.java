package cobble.sbp.commands.toggles;

import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.utils.HttpClient;
import cobble.sbp.utils.Utils;
import cobble.sbp.utils.config.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class EnableMod extends CommandBase 
{

	@Override
	public String getCommandName() {
		return "disablesbp";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/disablesbp";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		
		try {ConfigHandler.newObject("modToggle", false);} catch (IOException e) {e.printStackTrace();}
		Utils.sendMessage("SkyBlock Personalized has been "+ChatFormatting.RED+"disabled"+ChatFormatting.RESET+".");
		
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

}
