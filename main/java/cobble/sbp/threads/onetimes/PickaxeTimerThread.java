package com.cobble.sbp.threads.onetimes;

import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

public class PickaxeTimerThread extends Thread {

	public void run() {
		try {
			Thread.sleep(120000);
		} catch (InterruptedException e) {
			Utils.sendErrMsg("Pickaxe Timer Failed");
		}
		Utils.playDingSound();
		Utils.sendMessage("Your "+Colors.AQUA+"Pickaxe Ability "+Colors.YELLOW+"is now available!");
	}
	
}
