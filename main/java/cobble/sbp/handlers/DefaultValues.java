package cobble.sbp.handlers;

import java.io.FileWriter;
import java.io.IOException;

public class DefaultValues 
{
	public static void init() throws IOException
	{
		ConfigHandler.newObject("APIKey", "NOT_SET");
		ConfigHandler.newObject("modToggle", true);
		//for(int i=1;i<11;i++) {
			//ConfigHandler.newObject("task"+i+"Done", "N/A");
			//ConfigHandler.newObject("task"+i+"Text", "N/A");
		//}
	
	}
}
