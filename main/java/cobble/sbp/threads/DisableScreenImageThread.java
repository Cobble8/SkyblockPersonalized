package cobble.sbp.threads;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import cobble.sbp.utils.config.ConfigHandler;

public class DisableScreenImageThread extends Thread {

	public static long delay;
	
	public void run() {
		try {
		TimeUnit.SECONDS.sleep(delay);
		RenderGuiHandler.imageID = "NONE";
		} catch (InterruptedException e) {e.printStackTrace();}
		
	}
	
}
