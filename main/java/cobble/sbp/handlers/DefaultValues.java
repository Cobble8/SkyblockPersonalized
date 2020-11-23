package cobble.sbp.handlers;

import java.io.FileWriter;
import java.io.IOException;

public class DefaultValues 
{
	public static void init() throws IOException
	{
		ConfigHandler.newObject("APIKey", "NOT_SET");
		ConfigHandler.newObject("modToggle", true);
		ConfigHandler.newObject("imageXCoord", 0);
		ConfigHandler.newObject("imageYCoord", 0);
		ConfigHandler.newObject("imageID", null);
	}
}
