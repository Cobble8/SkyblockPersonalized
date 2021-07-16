package com.cobble.sbp.commands;

import java.util.ArrayList;
import java.util.List;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.threads.commands.DungeonsFloorThread;
import com.cobble.sbp.threads.commands.DungeonsPartyThread;
import com.cobble.sbp.threads.commands.DungeonsThread;
import com.cobble.sbp.utils.Utils;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class Dungeons extends CommandBase {	
	public static String args0 = "";
	public static String args1 = "";
	private ArrayList aliases;
	
		public Dungeons() {
			aliases = new ArrayList();
			aliases.add("dgns");
			aliases.add("dngs");
			
		}
	
		@Override
		public String getCommandName() {
			return "dungeons";
		}

		@Override
		public String getCommandUsage(ICommandSender sender) {
			return "/dungeons {player}";
		}
		
		@Override
		public List<String> getCommandAliases() {
			return aliases;
		}

		@Override
		public void processCommand(ICommandSender sender, String[] args) throws CommandException {
			if((Boolean) DataGetter.findBool("APIKeyToggle")) {
				if(args.length == 0) {
					Utils.sendErrMsg("Please supply a name to look up!");
				}
				else if(args.length == 1 && args[0].toLowerCase().equals("party")) {
					Thread dungeonsParty = new DungeonsPartyThread();
					dungeonsParty.start();
				}
				
				
				else if(args.length == 2) {
					args0 = args[0];
					args1 = args[1];
					if(Utils.isNumeric(args1)) {
						if(parseInt(args1) < 8 && parseInt(args1) > -1) {
							Thread dungeonsFloorCommand = new DungeonsFloorThread();
							dungeonsFloorCommand.start();
						} else {
							Utils.sendErrMsg("Please supply an actual floor!");
						}
					} else {
						Utils.sendErrMsg("Please supply an actual number to select a floor!");
					}
					
				} else {
					Thread dungeonsCommand = new DungeonsThread();
					args0 = args[0];
					dungeonsCommand.start();
				}
			} else {
				Utils.sendErrMsg("You need to enable API usage to use this command!");
			}
		} 
		@Override
		public boolean canCommandSenderUseCommand(ICommandSender sender) {
			return true;
		}
		
		public static int checkCataLevel(int xp) {
			int level = 0;
			int[] levelReq = {0, 50, 125, 235, 395, 625, 955, 1425, 2095, 3045, 4385, 6275, 8940, 12700, 17960, 25340, 35640, 50040, 70040, 97640, 135640, 188140, 259640, 356640, 488640, 668640, 911640, 1239640, 1684640, 2284640, 3084640, 4149640, 5559640, 7459640, 9959640, 13259640, 17559640, 23159640, 30359640, 39559640, 51559640, 66559640, 85559640, 109559640, 139559640, 117559640, 225559640, 285559640, 360559640, 453559640, 569809640};
			
			for(int i=0; i<levelReq.length;i++) {
				if(xp >= levelReq[i] && i !=0) level++;
			}
			
			return level;
		}
	}