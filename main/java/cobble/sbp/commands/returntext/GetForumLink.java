package cobble.sbp.commands.returntext;

import java.util.ArrayList;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.threads.VersionCheckerThread;
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

public class GetForumLink extends CommandBase {
	private final ArrayList aliases;
	Minecraft MC;
	
	public GetForumLink() 
	{
		aliases = new ArrayList();
	}
	
	
	@Override
	public String getCommandName() {
		return "sbpforum";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/sbpforum";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if((Boolean) DataGetter.find("modToggle")) {
			ChatStyle link = new ChatStyle();
			link.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://hypixel.net/threads/skyblockpersonalized.3557774/"));
			link.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(ChatFormatting.AQUA+"Click to copy"+ChatFormatting.GOLD+" https://hypixel.net/threads/skyblockpersonalized.3557774/")));
			
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.YELLOW+"https://hypixel.net/threads/skyblockpersonalized.3557774/").setChatStyle(link));
		
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
