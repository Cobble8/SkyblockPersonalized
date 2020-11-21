package cobble.sbp.commands;

import java.util.ArrayList;
import java.util.List;

import cobble.sbp.gui.menu.CommandsGUI;
import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class SBPMain extends CommandBase {

	private final ArrayList aliases;
	Minecraft MC;
	
	public SBPMain() 
	{
		aliases = new ArrayList();
		aliases.add("skyblockpersonalized");
	}
	
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
		if((Boolean) DataGetter.find("modToggle")) RenderGuiHandler.SBPHelpGuiDisplay = true; else Utils.enableMod();
		
	}
	
	@Override
	public List<String> getCommandAliases() {
		return aliases;
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

}
