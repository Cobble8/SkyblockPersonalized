package com.cobble.sbp.threads.commands;

import java.io.File;

import com.cobble.sbp.commands.SecretFinder;
import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.handlers.DownloadSecretsHandler;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.SecretUtils;
import com.cobble.sbp.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


public class OverrideThread extends Thread {
	public void run() {
		
		
		/*
		SecretImage.currentSecret=0;
		SecretImage.currentSecretText.clear();
		
		try {
			JsonElement roomDescJson = new JsonParser().parse(Utils.readFile("config/"+Reference.MODID+"/secrets/catacombs/SecretImageText.json"));
			JsonArray tmp = roomDescJson.getAsJsonObject().get(SecretFinder.args0).getAsJsonObject().get(SecretImage.roomSecretsID).getAsJsonArray();
			for(int j=0;j<tmp.size();j++) { SecretImage.currentSecretText.add((tmp.get(j)+"").replace("\"", "")); }
		} catch(Exception e) {
			
			SecretUtils.updateDungeonList();
			for(int i=0;i<DownloadSecretsHandler.dungeons.size();i++) {
				File infoLoc = new File("config/"+Reference.MODID+"/secrets/"+DownloadSecretsHandler.dungeons.get(i)+"/SecretImageText.json"); infoLoc.getParentFile().mkdirs();
				try {
					Utils.saveImage("https://raw.githubusercontent.com/Cobble8/DungeonSecrets/master/secrets/"+DownloadSecretsHandler.dungeons.get(i)+"/SecretImageText.json", infoLoc.getPath());
					Utils.print("Downloading image description file for dungeon: "+DownloadSecretsHandler.dungeons.get(i));
				} catch(Exception e2) {
					Utils.print("Failed to download image description file for dungeon: "+DownloadSecretsHandler.dungeons.get(i));
					DownloadSecretsHandler.dungeons.remove(i);
				}
				
			}
			
			JsonElement roomDescJson = new JsonParser().parse(Utils.readFile("config/"+Reference.MODID+"/secrets/"+SecretImage.currDungeon+"/SecretImageText.json"));
			JsonArray tmp = roomDescJson.getAsJsonObject().get(SecretFinder.args0).getAsJsonObject().get(SecretImage.roomSecretsID).getAsJsonArray();
			for(int j=0;j<tmp.size();j++) { SecretImage.currentSecretText.add((tmp.get(j)+"").replace("\"", "")); }
		}
		
		
		
		
		
		/*
		
		if(SecretOverride.argsLength == 0) {
			Utils.sendErrMsg("[sys]: Please supply a valid room size!");
			return;
		} else if(SecretOverride.argsLength == 1) {
			Utils.sendErrMsg("[sys]: Please supply a valid room ID!");
			return;
		} else if(SecretOverride.args0.toLowerCase().equals("1x1") ) {
			SecretImage.roomShape="1x1";
			//1 Secret
			switch (SecretOverride.args1) {
			
				case "dueces":
					SecretImage.roomSecretsID = "dueces";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 & 2 - Item and bat behind superboom");
					SecretImage.currentSecretText.add("Secret 3 - Chest behind superboom wall (that first chest is a trap chest that will drop tnt)");
					break;
			
				case "small-stairs":
					SecretImage.roomSecretsID = "small-stairs";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Item");
					SecretImage.currentSecretText.add("Secret 2 - Chest behind superboom (up the stairs)");
					break;
					
				case "overgrown":
					SecretImage.roomSecretsID = "overgrown";
					SecretImage.maxSecrets = 5;
					SecretImage.currentSecretText.add("Secret 1 - Item behind superboom");
					SecretImage.currentSecretText.add("Secret 2 (1/2) - Pull lever under green skull");
					SecretImage.currentSecretText.add("Secret 2 (2/2) - Go through here to chest");
					SecretImage.currentSecretText.add("Secret 2 (2/2) - Go through here to chest");
					SecretImage.currentSecretText.add("Secret 3 - Wither essence");
					break;
					
				case "locked-away":
					SecretImage.roomSecretsID = "locked-away";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Doorway on the left");
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					break;
				case "silvers-sword":
					SecretImage.roomSecretsID = "silvers-sword";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Chest behind superboom");
					break;
					
					
				case "carpets":
					SecretImage.roomSecretsID = "carpets";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					break;
					
			
				case "arrow-trap":
					SecretImage.roomSecretsID = "arrow-trap";
					SecretImage.maxSecrets = 3;
					SecretImage.currentSecretText.add("Secret 1 (1/2) - Lever behind superboom crypt");
					SecretImage.currentSecretText.add("Secret 1 (1/2) - Lever behind superboom crypt");
					SecretImage.currentSecretText.add("Secret 1 (2/2) - Chest");
					break;
				case "banners":
					SecretImage.roomSecretsID = "banners";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Chest behind painting");
					break;
				case "blue-skulls":
					SecretImage.roomSecretsID = "blue-skulls";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Item");
					break;
				case "cage":
					SecretImage.roomSecretsID = "cage";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Entrance to chest");
					break;
				case "cell":
					SecretImage.roomSecretsID = "cell";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Item behind superboom");
					break;
				case "duncan":
					SecretImage.roomSecretsID = "duncan";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Item Pickup");
					SecretImage.currentSecretText.add("Secret 1 - How to get there");
					break;
				case "golden-oasis":
					SecretImage.roomSecretsID = "golden-oasis";
					SecretImage.maxSecrets = 6;
					SecretImage.currentSecretText.add("Secret 1/2/3 - Right click on Redstone Skull");
					SecretImage.currentSecretText.add("Secret 1/2/3 cont - Lever on Redstone Grave");
					SecretImage.currentSecretText.add("Secret 1/2/3 cont - Lever opens room to fight Miniboss");
					SecretImage.currentSecretText.add("Secret 1/2/3 cont - Place Redstone Skull on Redstone Block for Secret 1 (Opens Secret 2/3)");
					SecretImage.currentSecretText.add("Secret 2/3 cont - Go back to the now open Redstone Grave and walk down the stairs");
					SecretImage.currentSecretText.add("Secret 2/3 cont - Chests");
					break;
				case "hanging-vines": //CONTINUE HERE
					SecretImage.roomSecretsID = "hanging-vines";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					break;
				case "jumping-skulls":
					SecretImage.roomSecretsID = "jumping-skulls";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					break;
				case "leaves":
					SecretImage.roomSecretsID = "leaves";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Chest behind superboom wall");
					break;
				case "mirror":
					SecretImage.roomSecretsID = "mirror";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Chest behind superboom wall");
					break;
				case "multicolored":
					SecretImage.roomSecretsID = "multicolored";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Chest behind superboom wall");
					break;
				case "mural":
					SecretImage.roomSecretsID = "mural";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Chest behind superboom wall");
					break;
				case "mushroom":
					SecretImage.roomSecretsID = "mushroom";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Click on mushroom for chest");
					SecretImage.currentSecretText.add("Secret 1 cont - Click on chest quickly before you are teleported back");
					break;
				case "pillars":
					SecretImage.roomSecretsID = "pillars";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					break;
				case "prison-cell":
					SecretImage.roomSecretsID = "prison-cell";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					break;
				case "sanctuary":
					SecretImage.roomSecretsID = "sanctuary";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - lever to chest");
					SecretImage.currentSecretText.add("Secret 1 cont - Chest");
					break;
				case "sand-dragon":
					SecretImage.roomSecretsID = "sand-dragon";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Behind the wood");
					break;
				case "sloth":
					SecretImage.roomSecretsID = "sloth";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Chest behind superboom wall");
					break;
				case "steps":
					SecretImage.roomSecretsID = "steps";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Bat");
					break;
				case "three-floors":
					SecretImage.roomSecretsID = "three-floors";
					SecretImage.maxSecrets = 4;
					SecretImage.currentSecretText.add("Secret 1 - Walk up these stairs");
					SecretImage.currentSecretText.add("Secret 1 cont - Turn right");
					SecretImage.currentSecretText.add("Secret 1 cont - Go through the hole");
					SecretImage.currentSecretText.add("Secret 1 cont - Chest");
					break;
				// 2 secrets
				case "andesite":
					SecretImage.roomSecretsID = "andesite";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 & 2 - wither essence and item pickup behind superboom wall");
					SecretImage.currentSecretText.add("Secret 1 & 2 - wither essence and item pickup behind superboom wall");
					break;
				case "beams":
					SecretImage.roomSecretsID = "beams";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Item");
					break;
				case "big-red-flag":
					SecretImage.roomSecretsID = "big-red-flag";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 & 2 - Item");
					break;
				case "black-flag":
					SecretImage.roomSecretsID = "black-flag";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Chest (upstairs)");
					break;
				case "chains":
					SecretImage.roomSecretsID = "chains";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Superboom wall with stuff inside (There's a trap when you immediately blow it up)");
					SecretImage.currentSecretText.add("Secret 2 - There are stairs which lead up to a chest area");
					break;
				case "cobble-wall-pillar":
					SecretImage.roomSecretsID = "cobble-wall-pillar";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Lever");
					SecretImage.currentSecretText.add("Secret 1 cont - Chest/Secret 2 - Chest");
					break;
				case "dip":
					SecretImage.roomSecretsID = "dip";
					SecretImage.maxSecrets = 3;
					SecretImage.currentSecretText.add("Secret 1 - Lever behind superboom crypt");
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Chest");
					break;
				case "dome":
					SecretImage.roomSecretsID = "dome";
					SecretImage.maxSecrets = 3;
					SecretImage.currentSecretText.add("Secret 1/2 - bat and lever");
					SecretImage.currentSecretText.add("Secret 1/2 - bat and lever");
					SecretImage.currentSecretText.add("Secret 2 - chest behind lever wall");
					break;
				case "drop":
					SecretImage.roomSecretsID = "drop";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Superboom to chest");
					break;
				case "granite":
					SecretImage.roomSecretsID = "granite";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secrets 1 & 2 - Wither essence & item");
					break;
				case "painting":
					SecretImage.roomSecretsID = "painting";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - item pickup, behind painting");
					SecretImage.currentSecretText.add("Secret 2 - wither essence in the wall");
					break;
				case "perch":
					SecretImage.roomSecretsID = "perch";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Item");
					SecretImage.currentSecretText.add("Secret 2 - Chest");
					break;
				case "quad-lava":
					SecretImage.roomSecretsID = "quad-lava";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Chest, Secret 2 - Chest in lava");
					break;
				case "scaffolding":
					SecretImage.roomSecretsID = "scaffolding";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Item drop");
					break;
				case "slabs":
					SecretImage.roomSecretsID = "slabs";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 & 2: Chest (Right Click both side of the chest)");
					break;
				case "water":
					SecretImage.roomSecretsID = "water";
					SecretImage.maxSecrets = 3;
					SecretImage.currentSecretText.add("Secret 1 - Lever");
					SecretImage.currentSecretText.add("Secret 1 cont - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Bat (may escape)");
					break;
				case "waterfall":
					SecretImage.roomSecretsID = "waterfall";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1/2 - Wither & Bat");
					break;
				//3 Secrets
				case "bootleg-melon":
					SecretImage.roomSecretsID = "bootleg-melon";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secrets 1/2 - Item and bat behind superboom");
					SecretImage.currentSecretText.add("Secret 3 - Chest behind superboom wall (that first chest is a trap chest that will drop tnt)");
					break;
				case "knight":
					SecretImage.roomSecretsID = "knight";
					SecretImage.maxSecrets = 3;
					SecretImage.currentSecretText.add("Secrets 1 & 2 - Lever");
					SecretImage.currentSecretText.add("Secrets 1 & 2 - Chest (Click both sides)");
					SecretImage.currentSecretText.add("Secret 3 - Bat (Usually flies out)");
					break;
				case "lava-pool":
					SecretImage.roomSecretsID = "lava-pool";
					SecretImage.maxSecrets = 4;
					SecretImage.currentSecretText.add("Secret 1 - Chest (behind superboom wall)");
					SecretImage.currentSecretText.add("Secret 2 - Bat");
					SecretImage.currentSecretText.add("Secret 3 - Chest");
					SecretImage.currentSecretText.add("Secret 3 cont - Chest");
					break;
				case "lava-skull":
					SecretImage.roomSecretsID = "lava-skull";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Bat / Secret 2a and 3a - Lever");
					SecretImage.currentSecretText.add("Secret 2b - Bat / Secret 3b - Chest");
					break;
				case "lots-of-floors":
					SecretImage.roomSecretsID = "lots-of-floors";
					SecretImage.maxSecrets = 4;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Chest behind superboom wall");
					SecretImage.currentSecretText.add("Secret 3 - Lever");
					SecretImage.currentSecretText.add("Secret 3 cont - Chest");
					break;
				case "mini-rail-track":
					SecretImage.roomSecretsID = "mini-rail-track";
					SecretImage.maxSecrets = 4;
					SecretImage.currentSecretText.add("Secret 1 - Item pickup");
					SecretImage.currentSecretText.add("Secret 2 - Bat");
					SecretImage.currentSecretText.add("Secret 3 (1/2) - Chest (way to get there)");
					SecretImage.currentSecretText.add("Secret 3 (2/2) - Chest is in cave area behind the lava");
					break;
				case "mossy":
					SecretImage.roomSecretsID = "mossy";
					SecretImage.maxSecrets = 5;
					SecretImage.currentSecretText.add("Secret 1 - Item drop behind superboom wall");
					SecretImage.currentSecretText.add("Secret 2 (1/2) - Pull lever under green skull");
					SecretImage.currentSecretText.add("Secret 2 (2/2) - Go through here to chest");
					SecretImage.currentSecretText.add("Secret 2 - Chest");
					SecretImage.currentSecretText.add("Secret 3 - Wither essence");
					break;
				case "redstone-key":
					SecretImage.roomSecretsID = "redstone-key";
					SecretImage.maxSecrets = 6;
					SecretImage.currentSecretText.add("Secret 1 - Right click on redstone skull");
					SecretImage.currentSecretText.add("Secret 2 - Item behind superboom crypt");
					SecretImage.currentSecretText.add("Secret 1 cont - Lever");
					SecretImage.currentSecretText.add("Secret 1 cont - Place the redstone key on the redstone block");
					SecretImage.currentSecretText.add("Secret 1 cont - Skull");
					SecretImage.currentSecretText.add("Secret 3 - Chest will be in newly opened passage after redstone key is placed");
					break;
				case "sarcophagus":
					SecretImage.roomSecretsID = "sarcophagus";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1: Chest behind Superboom Wall");
					SecretImage.currentSecretText.add("Secrets 2 & 3: Wither Essence & Bat (Note: the bat flew out into the middle)");
					break;
				case "spikes":
					SecretImage.roomSecretsID = "spikes";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2/3 - Bat & Wither essence");
					break;
				case "temple":
					SecretImage.roomSecretsID = "temple";
					SecretImage.maxSecrets = 4;
					SecretImage.currentSecretText.add("Secret 1 - Item");
					SecretImage.currentSecretText.add("Secret 2 - Bat");
					SecretImage.currentSecretText.add("Secret 3 - Entrance");
					SecretImage.currentSecretText.add("Secret 3 - Chest");
					break;
				//4 Secrets
				case "logs":
					SecretImage.roomSecretsID = "logs";
					SecretImage.maxSecrets = 3;
					SecretImage.currentSecretText.add("Secret 1 & 2 - Lever");
					SecretImage.currentSecretText.add("Secret 1 & 2 - Double Chest (Click both sides)");
					SecretImage.currentSecretText.add("Secret 3 & 4 - Double Chest (Click both sides)");
					break;
				case "raccoon":
					SecretImage.roomSecretsID = "raccoon";
					SecretImage.maxSecrets = 3;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 & 3 - 2 Chests through the superboom");
					SecretImage.currentSecretText.add("Secret 4 - Chest");
					break;
				case "trinity":
					SecretImage.roomSecretsID = "trinity";
					SecretImage.maxSecrets = 3;
					SecretImage.currentSecretText.add("Secret 1 - Item");
					SecretImage.currentSecretText.add("Secret 2 & 3 - 2 Bats");
					SecretImage.currentSecretText.add("Secret 4 - Chest");
					break;
			}
			
		} else if(SecretOverride.args0.toLowerCase().equals("1x2") ) {
			SecretImage.roomShape="1x2";
			switch (SecretOverride.args1.toLowerCase()) {
				case "gold":
					SecretImage.roomSecretsID = "gold";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Wither essence");
					break;
				case "skull":
					SecretImage.roomSecretsID = "skull";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Chest behind vines");
					break;
				case "archway":
					SecretImage.roomSecretsID = "archway";
					SecretImage.maxSecrets = 3;
					SecretImage.currentSecretText.add("Secret 1 - Chest behind superboom wall");
					SecretImage.currentSecretText.add("Secret 2 - Item");
					SecretImage.currentSecretText.add("Secret 3 - Item");
					break;
				case "redstone-warrior":
					SecretImage.roomSecretsID = "redstone-warrior";
					SecretImage.maxSecrets = 3;
					SecretImage.currentSecretText.add("Secret 1 - Item drop behind superboom wall");
					SecretImage.currentSecretText.add("Secret 2 - Chest");
					SecretImage.currentSecretText.add("Secret 3 - Lever");
					break;
				case "balcony":
					SecretImage.roomSecretsID = "balcony";
					SecretImage.maxSecrets = 5;
					SecretImage.currentSecretText.add("Secret 1 - Chest under superboom");
					SecretImage.currentSecretText.add("Secret 2 - Bat");
					SecretImage.currentSecretText.add("Secret 3/4 - Entrances");
					SecretImage.currentSecretText.add("Secret 3 - Item");
					SecretImage.currentSecretText.add("Secret 4 - Chest");
					break;
				case "mage":
					SecretImage.roomSecretsID = "mage";
					SecretImage.maxSecrets = 3;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Chest");
					SecretImage.currentSecretText.add("Secret 3/4 - Bat & wither essence");
					break;
				case "crypt":
					SecretImage.roomSecretsID = "crypt";
					SecretImage.maxSecrets = 6;
					SecretImage.currentSecretText.add("Secret 1 - Chest below superboom crypt");
					SecretImage.currentSecretText.add("Secret 2 - Chest");
					SecretImage.currentSecretText.add("Secret 3 - Item");
					SecretImage.currentSecretText.add("Secrets 4/5 Entrance - Go up stairs");
					SecretImage.currentSecretText.add("Secret 4 - Chest");
					SecretImage.currentSecretText.add("Secret 5 - Chest behind superboom wall");
					break;
				case "doors":
					SecretImage.roomSecretsID = "doors";
					SecretImage.maxSecrets = 6;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Chest beneath the superboom crypt");
					SecretImage.currentSecretText.add("Secret 3/4 - Lever");
					SecretImage.currentSecretText.add("Secret 3/4 cont - Lever behind superboom wall");
					SecretImage.currentSecretText.add("Secret 3/4 cont - Lever, Secret 5 - Bat");
					SecretImage.currentSecretText.add("Secret 3/4 cont - 2 Chests");
					break;
				case "pedestal":
					SecretImage.roomSecretsID = "pedestal";
					SecretImage.maxSecrets = 4;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Chest");
					SecretImage.currentSecretText.add("Secret 3 - Chest");
					SecretImage.currentSecretText.add("Secret 4/5 - Item & Wither essence");
					break;
				case "purple-flags":
					SecretImage.roomSecretsID = "purple-flags";
					SecretImage.maxSecrets = 7;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Wither essence");
					SecretImage.currentSecretText.add("Secret 3 - Superboom wall");
					SecretImage.currentSecretText.add("Secret 3 cont - Lever");
					SecretImage.currentSecretText.add("Secret 3 cont - Chest");
					SecretImage.currentSecretText.add("Secret 4 - Item");
					SecretImage.currentSecretText.add("Secret 5 - Chest");
					break;
				case "bridges":
					SecretImage.roomSecretsID = "bridges";
					SecretImage.maxSecrets = 6;
					SecretImage.currentSecretText.add("Secret 1 - Lever");
					SecretImage.currentSecretText.add("Secret 1 cont - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Item drop");
					SecretImage.currentSecretText.add("Secret 3 - Bat behind superboom");
					SecretImage.currentSecretText.add("Secret 4/5/6 - Wither essence & chest");
					SecretImage.currentSecretText.add("Secret 4/5/6 - Wither essence & chest");
					break;
				case "pressure-plate":
					SecretImage.roomSecretsID = "pressure-plate";
					SecretImage.maxSecrets = 9;
					SecretImage.currentSecretText.add("Secret 1 - Item pickup behind superboom wall");
					SecretImage.currentSecretText.add("Secret 2 - Chest behind door, opened by lever shown in picture");
					SecretImage.currentSecretText.add("Secret 3 - Chest behind superboom wall");
					SecretImage.currentSecretText.add("Secret 4 (1/2) - Item pickup below superboom floor");
					SecretImage.currentSecretText.add("Secret 4 - (2/2) - Area you fall into");
					SecretImage.currentSecretText.add("Secret 5 (1/2) - Chest");
					SecretImage.currentSecretText.add("Secret 5 (2/2) - You get there by going down this hole");
					SecretImage.currentSecretText.add("Secret 6 - Chest behind this door. To open the door, you need to activate 2 pressure plates.");
					SecretImage.currentSecretText.add("The pressure plates open the door and are located in this image and where I am standing");
					break;
			}
			
		} else if(SecretOverride.args0.toLowerCase().equals("1x3") ) {
			SecretImage.roomShape="1x3";
			switch (SecretOverride.args1) {
				case "diagonal":
					SecretImage.roomSecretsID = "diagonal";
					SecretImage.maxSecrets = 4;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Bat & fairy soul");
					SecretImage.currentSecretText.add("Secret 3 - Chest");
					SecretImage.currentSecretText.add("Secret 4 - Chest");
					break;
				case "red-blue":
					SecretImage.roomSecretsID = "red-blue";
					SecretImage.maxSecrets = 5;
					SecretImage.currentSecretText.add("Secret 1 - Lever");
					SecretImage.currentSecretText.add("Secret 1 cont - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Wither Essence");
					SecretImage.currentSecretText.add("Secret 3 - Item");
					SecretImage.currentSecretText.add("Secret 4 - Chest");
					break;
				case "wizard":
					SecretImage.roomSecretsID = "wizard";
					SecretImage.maxSecrets = 6;
					SecretImage.currentSecretText.add("Secret 1 - Item Drop behind superboom");
					SecretImage.currentSecretText.add("Secret 2 - Bat");
					SecretImage.currentSecretText.add("Secret 3 - Chest behind superboom");
					SecretImage.currentSecretText.add("Secret 4 - Chest behind superboom");
					SecretImage.currentSecretText.add("To get the buff from the wizard you need to find a crystal located near the lava in this room.");
					SecretImage.currentSecretText.add("Then bring the crystal to wizard located on the second floor near the third secret.");
					break;
				case "catwalk":
					SecretImage.roomSecretsID = "catwalk";
					SecretImage.maxSecrets = 5;
					SecretImage.currentSecretText.add("Secret 1 - Chest behind superboom wall");
					SecretImage.currentSecretText.add("Secret 2 - Chest");
					SecretImage.currentSecretText.add("Secret 3/4 - Chest & Wither essence behind superboom wall");
					SecretImage.currentSecretText.add("Secret 5 - Chest");
					SecretImage.currentSecretText.add("Secret 6 - Chest");
					break;
				case "deathmite":
					SecretImage.roomSecretsID = "deathmite";
					SecretImage.maxSecrets = 6;
					SecretImage.currentSecretText.add("Secrets 1/2 - Bat & Chest");
					SecretImage.currentSecretText.add("Secret 3 - Chest");
					SecretImage.currentSecretText.add("Secret 4 - Lever");
					SecretImage.currentSecretText.add("Secret 4 cont - Chest");
					SecretImage.currentSecretText.add("Secret 5 - Chest");
					SecretImage.currentSecretText.add("Secret 6 - Item behind superboom wall");
					break;
				case "gravel":
					SecretImage.roomSecretsID = "gravel";
					SecretImage.maxSecrets = 5;
					SecretImage.currentSecretText.add("Secret 1 - Lever");
					SecretImage.currentSecretText.add("Secret 1 cont - Chest");
					SecretImage.currentSecretText.add("Secret 2/3 - Item & Wither essence");
					SecretImage.currentSecretText.add("Secret 4/5 - Item & Wither essence");
					SecretImage.currentSecretText.add("Secret 6 - Bat");
					break;
			}

		} else if(SecretOverride.args0.toLowerCase().equals("1x4") ) {
			SecretImage.roomShape="1x4";
			switch (SecretOverride.args1) {
				case "hallway":
					SecretImage.roomSecretsID = "hallway";
					SecretImage.maxSecrets = 3;
					SecretImage.currentSecretText.add("Secret 1 - Chest under superboom floor");
					SecretImage.currentSecretText.add("Secret 2 - Lever opens door to chest");
					SecretImage.currentSecretText.add("Secret 3 - Chest");
					break;
				case "mossy":
					SecretImage.roomSecretsID = "mossy";
					SecretImage.maxSecrets = 5;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Lever");
					SecretImage.currentSecretText.add("Secret 2 cont - Chest");
					SecretImage.currentSecretText.add("Secret 3 - Chest behind superboom");
					SecretImage.currentSecretText.add("Secret 4 - Bat");
					break;
				case "pit":
					SecretImage.roomSecretsID = "pit";
					SecretImage.maxSecrets = 4;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Chest");
					SecretImage.currentSecretText.add("Secrets 3/4 - Bat + item behind superboom wall");
					SecretImage.currentSecretText.add("Secret 5 - Chest");
					break;
				case "quartz-knight":
					SecretImage.roomSecretsID = "quartz_knight";
					SecretImage.maxSecrets = 7;
					SecretImage.currentSecretText.add("Secret 1 - Item");
					SecretImage.currentSecretText.add("Secret 2 - Chest behind superboom wall");
					SecretImage.currentSecretText.add("Secret 3 - Bat");
					SecretImage.currentSecretText.add("Secret 4 - Chest");
					SecretImage.currentSecretText.add("Secret 5 - Chest");
					SecretImage.currentSecretText.add("Secret 6 - Chest");
					SecretImage.currentSecretText.add("Secret 7 - Item");
					break;
			}
			
		} else if(SecretOverride.args0.toLowerCase().equals("2x2") ) {
			SecretImage.roomShape="2x2";
			switch (SecretOverride.args1) {
				case "stairs":
					SecretImage.roomSecretsID = "stairs";
					SecretImage.maxSecrets = 4;
					SecretImage.currentSecretText.add("Secret 1 - Chest behind superboom wall");
					SecretImage.currentSecretText.add("Secret 2 - Chest");
					SecretImage.currentSecretText.add("Secret 3 - Chest");
					SecretImage.currentSecretText.add("Secret 4 - Item");
					break;
				case "buttons":
					SecretImage.roomSecretsID = "buttons";
					SecretImage.maxSecrets = 7;
					SecretImage.currentSecretText.add("Secret 1 - Superboom");
					SecretImage.currentSecretText.add("Secret 2 - Superboom");
					SecretImage.currentSecretText.add("Secret 3 - Lever");
					SecretImage.currentSecretText.add("Secret 3 cont - Chest");
					SecretImage.currentSecretText.add("Secret 4 - Lever");
					SecretImage.currentSecretText.add("Secret 4 - Chest");
					SecretImage.currentSecretText.add("Secret 5 - Chest (Button Puzzle opens door)");
					break;
				case "museum":
					SecretImage.roomSecretsID = "museum";
					SecretImage.maxSecrets = 6;
					SecretImage.currentSecretText.add("Secret 1 - Lever");
					SecretImage.currentSecretText.add("Secret 1 cont - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Chest under superboom");
					SecretImage.currentSecretText.add("Secret 3 - Chest");
					SecretImage.currentSecretText.add("Secret 4 - Wither essence");
					SecretImage.currentSecretText.add("Secret 5 - Chest behind superboom");
					break;
				case "atlas":
					SecretImage.roomSecretsID = "atlas";
					SecretImage.maxSecrets = 8;
					SecretImage.currentSecretText.add("Lever opens the bottom of the cage (which has a lost adventurer inside)");
					SecretImage.currentSecretText.add("Secret 1 & 2 - Item drop & Lever");
					SecretImage.currentSecretText.add("Secret 1 & 2 cont - Item drop & Lever");
					SecretImage.currentSecretText.add("Secret 2 cont - Chest");
					SecretImage.currentSecretText.add("Secret 3 - Chest");
					SecretImage.currentSecretText.add("Secret 4 - Wither essence");
					SecretImage.currentSecretText.add("Secret 5 - Chest");
					SecretImage.currentSecretText.add("Secret 6 - Bat");
					break;
				case "super-tall":
					SecretImage.roomSecretsID = "super-tall";
					SecretImage.maxSecrets = 8;
					SecretImage.currentSecretText.add("Secret 1 - Bat");
					SecretImage.currentSecretText.add("Use this lever to get to the second floor.");
					SecretImage.currentSecretText.add("Secret 2 - Item drop");
					SecretImage.currentSecretText.add("Then use this lever to get to the next area.");
					SecretImage.currentSecretText.add("Secret 3 - Bat");
					SecretImage.currentSecretText.add("Use this lever to get to the fourth, fifth, and sixth secret. You can drop down through an iron trap door to get to the door that the lever opens.");
					SecretImage.currentSecretText.add("Use this lever to get to the fourth, fifth, and sixth secret. You can drop down through an iron trap door to get to the door that the lever opens.");
					SecretImage.currentSecretText.add("Secret 4/5/6 - Chests");
					break;
				case "flags":
					SecretImage.roomSecretsID = "flags";
					SecretImage.maxSecrets = 6;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Bat");
					SecretImage.currentSecretText.add("Secret 3 - Item");
					SecretImage.currentSecretText.add("Secret 4/5 - 2 Chests");
					SecretImage.currentSecretText.add("Secret 6 - Chest");
					SecretImage.currentSecretText.add("Secret 7 - Chest");
					break;
				case "cathedral":
					SecretImage.roomSecretsID = "cathedral";
					SecretImage.maxSecrets = 8;
					SecretImage.currentSecretText.add("Secret 1 - Chest behind superboom");
					SecretImage.currentSecretText.add("Secret 2 - Chest behind superboom");
					SecretImage.currentSecretText.add("Secret 3 - Bat");
					SecretImage.currentSecretText.add("Secret 4 - Bat");
					SecretImage.currentSecretText.add("Secret 5 - Chest underneath crypt");
					SecretImage.currentSecretText.add("Secret 6 - Item");
					SecretImage.currentSecretText.add("Secret 7 - Item");
					SecretImage.currentSecretText.add("Secret 8 - Item");
					break;
				case "rail-track":
					SecretImage.roomSecretsID = "rail-track";
					SecretImage.maxSecrets = 8;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Bat");
					SecretImage.currentSecretText.add("Secret 3/4 - 2 Wither Essence");
					SecretImage.currentSecretText.add("Secret 5 - Superboom to chest");
					SecretImage.currentSecretText.add("Secret 6 - Chest");
					SecretImage.currentSecretText.add("Secret 7 - Lever");
					SecretImage.currentSecretText.add("Secret 7 cont - Chest");
					SecretImage.currentSecretText.add("Secret 8/9 - Wither Essence & Item");
					break;
			}
		} else if(SecretOverride.args0.toUpperCase().equals("L") ) {
			SecretImage.roomShape="L";
			switch (SecretOverride.args1) {
				case "dino-dig-site":
					SecretImage.roomSecretsID = "dino-dig-site";
					SecretImage.maxSecrets = 6;
					SecretImage.currentSecretText.add("Secret 1 - Chest behind superboom wall");
					SecretImage.currentSecretText.add("Secret 2 - Item pickup");
					SecretImage.currentSecretText.add("Photo from different angle");
					SecretImage.currentSecretText.add("Secret 3 - Chest");
					SecretImage.currentSecretText.add("Secret 4 - Bat");
					SecretImage.currentSecretText.add("Photo from inside the room");
					break;
				case "withermancers":
					SecretImage.roomSecretsID = "withermancers";
					SecretImage.maxSecrets = 7;
					SecretImage.currentSecretText.add("Secret 1 - Superboom");
					SecretImage.currentSecretText.add("Secret 1 cont - Lever");
					SecretImage.currentSecretText.add("Secret 1 cont - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Wither essence");
					SecretImage.currentSecretText.add("Secret 3 - Chest");
					SecretImage.currentSecretText.add("Secret 4 - Superboom");
					SecretImage.currentSecretText.add("Secret 4 cont - Chest");
					break;
				case "chambers":
					SecretImage.roomSecretsID = "chambers";
					SecretImage.maxSecrets = 7;
					SecretImage.currentSecretText.add("Secret 1 - Superboom wall on either side leading to a chest");
					SecretImage.currentSecretText.add("Secret 1 cont - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Bat");
					SecretImage.currentSecretText.add("Secret 3 - Lever");
					SecretImage.currentSecretText.add("Secret 3 cont - Chest");
					SecretImage.currentSecretText.add("Secret 4 - Go down water stream to chest");
					SecretImage.currentSecretText.add("Secret 5 - Chest behind superboom wall");
					break;
				case "market":
					SecretImage.roomSecretsID = "market";
					SecretImage.maxSecrets = 4;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Chest");
					SecretImage.currentSecretText.add("Secret 3/4 - Bat & wither essence under superboom");
					SecretImage.currentSecretText.add("Secret 5 - Chest");
					break;
				case "lava-ravine":
					SecretImage.roomSecretsID = "lava-ravine";
					SecretImage.maxSecrets = 6;
					SecretImage.currentSecretText.add("Secret 1 - Chest behind superboom");
					SecretImage.currentSecretText.add("Secret 2 - Chest");
					SecretImage.currentSecretText.add("Secret 3 - Item drop");
					SecretImage.currentSecretText.add("Secret 4/5 - Lever");
					SecretImage.currentSecretText.add("Secret 4/5 cont - Chests");
					SecretImage.currentSecretText.add("Secret 6 - Bat");
					break;
				case "melon":
					SecretImage.roomSecretsID = "melon";
					SecretImage.maxSecrets = 6;
					SecretImage.currentSecretText.add("Secret 1 - Chest inside pile of melons (Break the melons)");
					SecretImage.currentSecretText.add("Secret 2/3 - Bat & Chest behind superboom wall");
					SecretImage.currentSecretText.add("Secret 4 - Chest");
					SecretImage.currentSecretText.add("Secret 5 - Chest");
					SecretImage.currentSecretText.add("Secret 6 - Chest");
					SecretImage.currentSecretText.add("Secret 7 - Chest");
					break;
				case "well":
					SecretImage.roomSecretsID = "well";
					SecretImage.maxSecrets = 5;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2/3 - 2 Wither essences");
					SecretImage.currentSecretText.add("Secret 4 - Item (left), Secret 5 - Chest (right)");
					SecretImage.currentSecretText.add("Secret 6 - Item");
					SecretImage.currentSecretText.add("Secret 7 - Chest behind superboom wall");
					break;
				case "spider":
					SecretImage.roomSecretsID = "spider";
					SecretImage.maxSecrets = 9;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Chest");
					SecretImage.currentSecretText.add("Secret 3/4 - Chest & Bat");
					SecretImage.currentSecretText.add("Secret 5 - Chest behind superboom");
					SecretImage.currentSecretText.add("Secret 6 - Chest");
					SecretImage.currentSecretText.add("Secret 7 - Item");
					SecretImage.currentSecretText.add("Secret 7 cont, Secret 8 - Lever");
					SecretImage.currentSecretText.add("Secret 8 cont - Chest");
					SecretImage.currentSecretText.add("Secret 9 - Item");
					break;
			}
		} else if(SecretOverride.args0.toLowerCase().equals("puzzles") ) {
			SecretImage.roomShape="puzzles";
			switch (SecretOverride.args1) {
				case "blaze":
					SecretImage.roomSecretsID = "blaze";
					SecretImage.maxSecrets = 1;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					break;
				case "tictactoe":
					SecretImage.roomSecretsID = "tictactoe";
					SecretImage.maxSecrets = 2;
					SecretImage.currentSecretText.add("Secret 1 - Lever");
					SecretImage.currentSecretText.add("Secret 1 cont - Chest");
					break;
				case "crusher":
					SecretImage.roomSecretsID = "crusher";
					SecretImage.maxSecrets = 4;
					SecretImage.currentSecretText.add("Lever 1 - Lever");
					SecretImage.currentSecretText.add("Secret 1 cont - Chest");
					SecretImage.currentSecretText.add("Lever for room completion");
					SecretImage.currentSecretText.add("Secret 2 - Bat");
					break;
				case "arrow":
					SecretImage.roomSecretsID = "arrow";
					SecretImage.maxSecrets = 5;
					SecretImage.currentSecretText.add("Secret 1 - Chest");
					SecretImage.currentSecretText.add("Secret 2 - Lever");
					SecretImage.currentSecretText.add("Secret 2 cont - Chest");
					SecretImage.currentSecretText.add("Secret 3 - Bat");
					SecretImage.currentSecretText.add("Lever for room completion");
					break;
		
			}
		} else {
			Utils.sendErrMsg("[sys]: Failed to find secret!");
			return;
		}
		*/
	}
}
