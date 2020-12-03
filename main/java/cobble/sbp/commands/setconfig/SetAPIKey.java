package cobble.sbp.commands.setconfig;

import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import cobble.sbp.utils.config.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class SetAPIKey extends CommandBase {

	@Override
	public String getCommandName() {
		return "sbpsetkey";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/sbpsetkey <hypixel api key>";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if((Boolean) DataGetter.find("modToggle")) {
			if(args.length > 0) {
			try {ConfigHandler.newObject("APIKey", args[0]);} catch (IOException e) {e.printStackTrace();}
			Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP] "+ChatFormatting.AQUA+"Your Hypixel API Key has been set to: "+ChatFormatting.GOLD+args[0]+ChatFormatting.AQUA+".");
			} else {Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP] "+ChatFormatting.RED+"You haven't supplied an API key!");}
		} else Utils.enableMod();
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

}
