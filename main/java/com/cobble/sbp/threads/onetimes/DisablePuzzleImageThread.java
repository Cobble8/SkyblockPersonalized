package com.cobble.sbp.threads.onetimes;

import java.util.concurrent.TimeUnit;

import com.cobble.sbp.SBP;
import com.cobble.sbp.events.RenderGuiEvent;

public class DisablePuzzleImageThread extends Thread {

	public static long delay;
	
	
	public void run() {
		int currentCount = SBP.puzzleCount;
		try {
		TimeUnit.SECONDS.sleep(delay);
		//Utils.sendMessage("Current Count:" + currentCount);
		//Utils.sendMessage("Actual Count:" + SBP.puzzleCount);
		if(SBP.puzzleCount == currentCount) {
			RenderGuiEvent.imageID = "NONE";
		}
		} catch (InterruptedException e) {e.printStackTrace();}
		
	}
	
}
