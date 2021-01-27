package com.cobble.sbp.core;

import java.util.ArrayList;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;

public class Quests {

	
	public static void launchAchievements() {

		if(Integer.parseInt(DataGetter.find("modLaunches")+"") >= 50) { ConfigHandler.setQuest("fifty_launches", true); }
		
		
	}
	
}
