package cobble.sbp.utils.config;

public class LoadValues {
	public static void loadValues() {
		ConfigHandler.loadValue("APIKey");
		ConfigHandler.loadValue("modToggle");
		ConfigHandler.loadValue("imageXCoord");
		ConfigHandler.loadValue("imageYCoord");
		ConfigHandler.loadValue("autoPuzzleToggle");
		ConfigHandler.loadValue("configVersion");
		ConfigHandler.loadValue("imageDelay");
	}
}
