package com.cobble.sbp.events.skyblock;

import com.cobble.sbp.gui.screen.dungeons.SecretImage;
import com.cobble.sbp.gui.screen.dwarven.CrystalHollowsMap;
import com.cobble.sbp.gui.screen.dwarven.DwarvenGui;
import com.cobble.sbp.gui.screen.dwarven.DwarvenPickaxeTimer;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTimer;
import com.cobble.sbp.utils.MusicUtils;

public class LobbySwapEvent {

	public static String currLobby = "";
	
	public LobbySwapEvent() {
		DwarvenTimer.lastEvent = -69;
		DwarvenPickaxeTimer.lastUsed = System.currentTimeMillis();
		DwarvenPickaxeTimer.abilityName="lobbySwap";
		SecretImage.roomSecretsID="NONE";
		MusicUtils.stopSong();
		DwarvenGui.sentryLast="Unknown";
		CrystalHollowsMap.resetLocs();
		CrystalHollowsMap.markers.clear();
	}
	
}
