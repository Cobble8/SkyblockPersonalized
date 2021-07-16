package com.cobble.sbp.threads.dungeons;

import com.cobble.sbp.gui.screen.misc.AbilityMessages;
import com.cobble.sbp.gui.screen.misc.ComboMessages;
import com.cobble.sbp.utils.Utils;

public class AbilityDamageThread extends Thread {

	public void run() {
		
		int currMsg = AbilityMessages.damageMsgCount;
		int currCombo = ComboMessages.comboMsgCount;
		
		try {
			Thread.sleep(AbilityMessages.delay* 1000L);
		} catch (InterruptedException e) { Utils.sendErrMsg("Failed to start AbilityDamageThread");}
		
		if(currMsg == AbilityMessages.damageMsgCount) { AbilityMessages.lastDamage=""; } else {return;}
		if(currCombo == ComboMessages.comboMsgCount) { ComboMessages.lastCombo=""; }
	}
	
}
