package cobble.sbp.commands.setconfig;

import java.util.ArrayList;
import java.util.List;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.FileTest;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

public class SetImageID extends CommandBase {
	private final ArrayList aliases;
	Minecraft MC;
	
	public SetImageID() 
	{
		aliases = new ArrayList();
	}
	
	
	@Override
	public String getCommandName() {
		return "devcommandimageid";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/sbpforum";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if(Minecraft.getMinecraft().thePlayer.getDisplayNameString().equals("Cobble8")) {
			if((Boolean) DataGetter.find("modToggle")) {
				if(args.length > 0) {
					if(FileTest.main("src/main/resources/assets/sbp/textures/gui/"+args[0]+".png")) {
						RenderGuiHandler.imageID=args[0];
						Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.YELLOW+" Your image ID has been set to "+ChatFormatting.AQUA+args[0]);
					} else if(args[0].toLowerCase().equals("none") || args[0].toLowerCase().equals("reset")){
						RenderGuiHandler.imageID="NONE";
						Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.YELLOW+" Your image ID has been reset!");
					} else {
						Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.RED+" That file does not exist!");
						}
	
				} else {
					Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.RED+" Please supply an image ID!");
					}
				
			} else {
				Utils.enableMod();
				}
			
		} else {
			Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.RED+" This command is restricted to SkyblockPersonalized developers only!");
			}
			
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
