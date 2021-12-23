package com.cobble.sbp.handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.HttpClient;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.SecretUtils;
import com.cobble.sbp.utils.Utils;

public class DownloadSecretsHandler extends Thread {
	public static Boolean running = false;
	public static int progress = 0;
	public static int total = 0;
	public static long startTime = 0;
	public static int timeElapsed = 0;
	public static String currText = "";
	public static ArrayList<String> dungeons = new ArrayList();
	
	public void run() {
		running = true;
		currText = "Starting download process...";
		try { Thread.sleep(250); } catch (InterruptedException ignored) { }
		
		File delOld = new File("config/"+Reference.MODID+"/secrets");
		currText = "Deleting old images...";
		try {
			FileUtils.deleteDirectory(delOld);
		} catch (IOException e2) {
			currText = "Deletion failed!";
		}
		
		
		Utils.print("Starting DungeonSecrets image download...");
		SecretUtils.updateDungeonList();
		progress=0;

		currText = "Downloading image descriptions...";
		for (String dungeon : dungeons) {
			File infoLoc = new File("config/" + Reference.MODID + "/secrets/" + dungeon + "/SecretImageText.json");
			infoLoc.getParentFile().mkdirs();
			Utils.saveImage("https://raw.githubusercontent.com/Cobble8/DungeonSecrets/master/secrets/" + dungeon + "/SecretImageText.json", infoLoc.getPath());
			Utils.print("Downloaded image description file for dungeon: " + dungeon);
		}
		
		
		
		
		
		startTime = System.currentTimeMillis();
		String[] imageURLs = new String[0];
		currText = "Getting list of images...";
		try { imageURLs = HttpClient.readPage("https://raw.githubusercontent.com/Cobble8/DungeonSecrets/master/imageurls.txt").split(","); } catch (Exception e1) { Utils.sendErrMsg("Failed to download images."); return; }
		total = imageURLs.length;

		for (String imageUrl : imageURLs) {
			if (imageUrl.startsWith("//") || (imageUrl.replace(",", "").equals(""))) {
				total--;
			}
		}


		for (String imageURL : imageURLs) {
			currText = "Images Downloaded: " + Colors.YELLOW + DownloadSecretsHandler.progress + "/" + (DownloadSecretsHandler.total - 1);
			//String imageUrl = "https://raw.githubusercontent.com/Cobble8/DungeonSecrets/master/src/main/resources/assets/dgnscrts/textures/gui/secrets/1x1/andesite/0.png";

			try {

				if (imageURL.startsWith("//") || (imageURL.replace(",", "").equals(""))) {
					continue;
				}
				String[] tmp = imageURL.split("/");
				String dungeonName = tmp[tmp.length - 4];
				String roomSize = tmp[tmp.length - 3];
				String roomName = tmp[tmp.length - 2];
				String imgName = tmp[tmp.length - 1];
				String loc = "config/" + Reference.MODID + "/secrets/" + dungeonName + "/" + roomSize + "/" + roomName;
				File imgFolder = new File(loc);
				imgFolder.mkdirs();
				loc += "/" + imgName;
				Utils.saveImage(imageURL, loc);
				Utils.print("Saved image: '" + imageURL + "' at: '" + loc + "'");
			} catch (Exception e) {
				continue;
			}
			progress++;
		}
		if(!SettingMenu.settingsMenuOpen) {
			Utils.sendMessage("Finished Downloading DungeonSecret Images!");
			Utils.sendMessage("Time Elapsed: "+Colors.AQUA+Utils.secondsToTime(timeElapsed));
		}
		
		Utils.playDingSound();
		Utils.print("Completed DungeonSecret image download!");
		try { Thread.sleep(3000); } catch (InterruptedException ignored) { }
		running = false;
		currText="";
	}
	
	
	
}
