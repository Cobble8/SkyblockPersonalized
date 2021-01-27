package com.cobble.sbp.commands;

import java.util.ArrayList;
import java.util.List;

import com.cobble.sbp.core.ConfigList;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

public class GetConfig extends CommandBase {
	ArrayList<String> aliases;
	public GetConfig() {
		aliases = new ArrayList();
		//aliases.add("aliasHere");
	}
	
	@Override
	public String getCommandName() {
		return "getconfig";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/getconfig";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		
		String output = "";
		String string = "";
		if(args.length == 0) {
			Boolean currColor = false;
			String currPrefix = Colors.AQUA;
			
			
			Utils.sendMessage(Colors.YELLOW+"Config List:");
			for(int i=0;i<ConfigList.c.size();i++) {
				
				if(currColor) {
					currPrefix = Colors.GREEN;
				} else {
					currPrefix = Colors.AQUA;
				}
				currColor = Utils.invertBoolean(currColor);
				
				//Utils.sendSpecificMessage(Colors.AQUA+ConfigList.c.get(i));
				ChatStyle hoverText = new ChatStyle();
				hoverText.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/getconfig "+ConfigList.c.get(i)));
				hoverText.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+"Click to check the variable: "+currPrefix+ConfigList.c.get(i))));
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(currPrefix+ConfigList.c.get(i)).setChatStyle(hoverText));
				
			}
			Utils.sendSpecificMessage(Colors.YELLOW+"--------------------");
			return;
		}
		if(args.length == 1) {
			
			output = DataGetter.find(args[0])+"";
			string = Colors.YELLOW+"Variable: "+Colors.AQUA+args[0]+Colors.YELLOW+", Output: "+Colors.AQUA+output;
			
		}
		if(args.length == 2) {
			output = ""+DataGetter.find(args[0], args[1]);
			string = Colors.YELLOW+"File: "+Colors.AQUA+args[0]+Colors.YELLOW+", Variable: "+Colors.AQUA+args[1]+Colors.YELLOW+", Output: "+Colors.AQUA+output;
		
		} 
		
		ChatStyle hoverText = new ChatStyle();
		hoverText.setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, output));
		hoverText.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(Colors.YELLOW+"Click to put the output into chat")));
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(string).setChatStyle(hoverText));
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
