package com.cobble.sbp.threads.misc;

import com.cobble.sbp.gui.menu.settings.SettingMenu;

public class FixFadeThread extends Thread {
	
	public void run() {
		try { Thread.sleep(1000); } catch (InterruptedException ignored) { }
		SettingMenu.fadeIn=1;
	}

}
