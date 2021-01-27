package com.cobble.sbp.threads.onetimes;

import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

public class JerryTimer extends Thread{

	public void run() {
		try {
			Thread.sleep(360000);
		} catch (InterruptedException e) {
			Utils.sendErrMsg("Jerry Timer Failed");
			return;
		}
		try {
			Utils.sendSpecificMessage(Colors.DARK_RED+"-----------------------------------------------------");
			Utils.sendMessage("You can now get "+Colors.AQUA+"Jerrys"+Colors.YELLOW+" again.");
			Utils.playDingSound();
			Utils.sendSpecificMessage(Colors.DARK_RED+"-----------------------------------------------------");
		} catch(Exception e) {
			Utils.print("Jerry Timer Failed");
		}
	}
	
	
}
