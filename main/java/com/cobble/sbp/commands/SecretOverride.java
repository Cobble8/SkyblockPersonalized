package com.cobble.sbp.commands;


import java.util.ArrayList;

import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.threads.commands.OverrideThread;
import com.cobble.sbp.utils.SecretUtils;
import com.cobble.sbp.utils.Utils;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class SecretOverride extends CommandBase {

	public static String args0;
	public static String args1;
	public static int argsLength;
	
	@Override
	public String getCommandName() {
		return "secretoverride";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/weirdchamp";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		SecretImage.roomSecretsID="NONE";
		SecretImage.maxSecrets=0;
		SecretImage.currentSecret=0;
		SecretImage.currentSecretText.clear();
		SecretImage.downloadImage=false;
		if(args.length == 2) {
			SecretImage.currDungeon="catacombs";
			SecretImage.roomShape=args[0];
			
			ArrayList<String> roomList = SecretUtils.getRoomNames(SecretImage.roomShape, SecretImage.currDungeon);
			for(String str : roomList) {
				if(str.startsWith(args[1])) {
					SecretImage.roomSecretsID = str;
					SecretImage.currentSecretText = SecretUtils.getRoomDesc(SecretImage.roomShape, SecretImage.roomSecretsID, SecretImage.currDungeon);
					SecretImage.maxSecrets=SecretImage.currentSecretText.size();
					SecretImage.reloadImage=true;
					return;
				}
			}
			
			
		} else if(args.length == 3) {
			SecretImage.currDungeon=args[0];
			SecretImage.roomShape=args[1];
			
			ArrayList<String> roomList = SecretUtils.getRoomNames(SecretImage.roomShape, SecretImage.currDungeon);
			for(String str : roomList) {
				if(str.substring(0, str.lastIndexOf("-")).equals(args[2])) {
					SecretImage.roomSecretsID = str;
					SecretImage.currentSecretText = SecretUtils.getRoomDesc(SecretImage.roomShape, SecretImage.roomSecretsID, SecretImage.currDungeon);
					SecretImage.maxSecrets=SecretImage.currentSecretText.size();
					SecretImage.reloadImage=true;
					return;
				}
			}
		}
		else {Utils.sendErrMsg("Invalid syntax: /secretoverride dungeonName roomSize roomID"); return;}
		
		//if(args.length > 0) args0 = args[0];
		//if(args.length > 1) args1 = args[1];
		//argsLength = args.length;
		Thread override = new OverrideThread();
		override.start();
		
		
	}
	
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}

}
