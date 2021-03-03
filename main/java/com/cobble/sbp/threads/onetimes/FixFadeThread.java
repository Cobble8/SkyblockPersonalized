package com.cobble.sbp.threads.onetimes;

import com.cobble.sbp.gui.menu.settings.SettingMenu;

public class FixFadeThread extends Thread {
	
	public void run() {
		try { Thread.sleep(1000); } catch (InterruptedException e) { }
		SettingMenu.fadeIn=1;
	}

}
