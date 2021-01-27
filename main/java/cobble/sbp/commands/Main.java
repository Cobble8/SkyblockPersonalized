package com.cobble.sbp.commands;

import java.util.ArrayList;
import java.util.List;

import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.utils.Reference;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class Main extends CommandBase {
	ArrayList<String> aliases;
	public Main() {
		aliases = new ArrayList();
		aliases.add("skyblockpersonalized");
	}
	
	@Override
	public String getCommandName() {
		return Reference.MODID;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/cobblehelp";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		

		RenderGuiEvent.helpMenu=true;
		SettingMenu.fadeIn = 0;
		SettingMenu.fadeInFrames=0;
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
