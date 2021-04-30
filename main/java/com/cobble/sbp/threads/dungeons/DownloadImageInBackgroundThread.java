package com.cobble.sbp.threads.dungeons;

import java.io.File;

import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.HttpClient;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

public class DownloadImageInBackgroundThread extends Thread {

	public void run() {
		

		
		String[] imageURLs = new String[0];
		try { imageURLs = HttpClient.readPage("https://raw.githubusercontent.com/Cobble8/DungeonSecrets/master/imageurls.txt").split(","); } catch (Exception e) { Utils.sendErrMsg("Failed to download images."); return; }

		for(int i=0;i<imageURLs.length;i++) {
			if(imageURLs[i].startsWith("//")) {continue;}
			if(imageURLs[i].toLowerCase().contains((SecretImage.currDungeon+"/"+SecretImage.roomShape+"/"+SecretImage.roomSecretsID).toLowerCase()) && imageURLs[i].endsWith(".png")) {
				
			String loc = "config/"+Reference.MODID+"/secrets/"+SecretImage.currDungeon+"/"+SecretImage.roomShape+"/"+SecretImage.roomSecretsID;
			File imgFolder = new File(loc); imgFolder.mkdirs();
				for(int j=0;j<SecretImage.currentSecretText.size();j++) {
					String loc2 = (loc+"/"+j+".png");
					String download = imageURLs[i].replace("/0.png", "/"+j+".png");
					Utils.print("Saved image: '"+(download)+"' at: '"+loc2+"'");
					Utils.saveImage(download, imgFolder.getPath()+"/"+j+".png");
				}
					
				Utils.sendMessage("Downloaded "+SecretImage.currentSecretText.size()+" images for: "+Colors.AQUA+SecretImage.roomSecretsID);

				SecretImage.reloadImage=true;
				SecretImage.downloadImage=false;
				break;
			}
			
		}
		
		
		}
	
}
