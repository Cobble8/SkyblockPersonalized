package cobble.sbp.commands.returntext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

public class ShowConfig extends CommandBase{

	private final ArrayList aliases;
	Minecraft MC;
	
	public ShowConfig() 
	{
		aliases = new ArrayList();
	}
	
	
	@Override
	public String getCommandName() {
		return "showconfig";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/showconfig";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if((Boolean) DataGetter.find("modToggle")) {
			
		try {
		      File myObj = new File("config/sbp/sbp.cfg");
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        
		        
		        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Config:"+"\n"+data));
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
			
		
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