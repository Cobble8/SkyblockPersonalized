package cobble.sbp.commands.returntext;

import java.util.ArrayList;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

public class TestingCommand extends CommandBase {
	private final ArrayList aliases;
	Minecraft MC;
	
	public TestingCommand() 
	{
		aliases = new ArrayList();
	}
	
	
	@Override
	public String getCommandName() {
		return "testcommand";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/testcommand";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if((Boolean) DataGetter.find("modToggle")) {
			if(Minecraft.getMinecraft().thePlayer.getDisplayNameString().equals("Cobble8")) {
				Minecraft.getMinecraft().ingameGUI.getTabList().setHeader(new ChatComponentText(ChatFormatting.AQUA+"You are playing on "+ChatFormatting.YELLOW+""+ChatFormatting.BOLD+"MC.HYPIXEL.NET"+ChatFormatting.AQUA+" with "+ChatFormatting.GOLD+"SkyblockPersonalized"));
			} else {return;}
		} else Utils.enableMod();
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
