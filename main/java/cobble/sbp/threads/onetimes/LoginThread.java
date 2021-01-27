package com.cobble.sbp.threads.onetimes;

import com.cobble.sbp.events.LobbySwapEvent;
import com.cobble.sbp.events.PlayerLoginEvent;
import com.cobble.sbp.utils.CheckAPIKey;
import com.cobble.sbp.utils.Utils;

public class LoginThread extends Thread {
	public void run() {
		try { CheckAPIKey.checkValidAPIKey(); } catch (Exception e1) { Utils.print("Invalid API Key"); }

		LobbySwapEvent.currLobby = "";
		try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
		
		if(!CheckAPIKey.validAPIKey) {
			PlayerLoginEvent.setApiKey();
		}
		new LobbySwapEvent();
	}
}
