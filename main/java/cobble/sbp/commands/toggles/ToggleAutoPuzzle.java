package cobble.sbp.commands.toggles;

import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import cobble.sbp.utils.config.ConfigHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class ToggleAutoPuzzle extends CommandBase {

	@Override
	public String getCommandName() {
		return "toggleautopuzzle";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/toggleautopuzzle";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		
		if((Boolean) DataGetter.find("modToggle")) {
			if((Boolean) DataGetter.find("autoPuzzleToggle") == null || (Boolean) DataGetter.find("autoPuzzleToggle") == false) {
				try { ConfigHandler.newObject("autoPuzzleToggle", true); 
				Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP] "+ChatFormatting.WHITE+"The "+ChatFormatting.AQUA+"Auto Puzzle Solver "+ChatFormatting.WHITE+"feature has been " +ChatFormatting.GREEN+"enabled");} catch (IOException e) { Utils.sendMessage(ChatFormatting.RED+"Failed to set value to the config"); }
			} else {try { ConfigHandler.newObject("autoPuzzleToggle", false); 
			Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP] "+ChatFormatting.WHITE+"The "+ChatFormatting.AQUA+"Auto Puzzle Solver "+ChatFormatting.WHITE+"feature has been " +ChatFormatting.RED+"disabled");} catch (IOException e) { Utils.sendMessage(ChatFormatting.RED+"Failed to set value to the config"); }}
		} else Utils.enableMod();
		
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

}
