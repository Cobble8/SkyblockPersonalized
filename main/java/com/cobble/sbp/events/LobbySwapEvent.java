package com.cobble.sbp.events;

import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;

public class LobbySwapEvent {

	public static String currLobby = "";
	
	public LobbySwapEvent() {
		DwarvenTimer.lastEvent = -69;
		DwarvenPickaxeTimer.lastUsed = (System.currentTimeMillis())-45000;
	}
	
}
