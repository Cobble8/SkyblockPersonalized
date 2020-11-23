package cobble.sbp.threads;

import cobble.sbp.utils.Utils;

public class VersionCheckerThread extends Thread{
 public void run() {
	 try { Utils.checkAgainstLatestVersion(); } catch (Exception e) { e.printStackTrace(); }
 }
}
