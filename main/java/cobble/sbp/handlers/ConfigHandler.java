package cobble.sbp.handlers;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cobble.sbp.SBP;
import cobble.sbp.simplejson.JSONObject;
import cobble.sbp.simplejson.parser.ParseException;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.FileTest;
import cobble.sbp.utils.Reference;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler 
{
	public static JSONObject obj = new JSONObject();
	static File file;
	public static FileWriter myWriter;
	public static void newObject(String objectName, Object newValue) throws IOException {
	    try {
	        myWriter = new FileWriter("config/sbp/sbp.cfg");
	        obj.put(objectName, newValue);
	        myWriter.write(obj.toJSONString());
	        myWriter.close();
	      } catch (IOException e) {
	        System.out.println("An error occurred from newStringObject().");
	        e.printStackTrace();
	      }
    	}
	
	public static void registerConfig(FMLPreInitializationEvent event) throws IOException, ParseException
	{
		final File config;
		config = new File(event.getModConfigurationDirectory() + "/" + Reference.MODID);
		config.mkdir();
		if(FileTest.main("config/sbp/sbp.cfg") == false) {
			SBP.firstLaunch = true;
			DefaultValues.init();
			}
		else {
			
			
			loadValue("APIKey");
			loadValue("modToggle");
			loadValue("imageXCoord");
			loadValue("imageYCoord");
			loadValue("imageID");
			
		}
		
		file = new File("config/sbp/sbp.cfg");
		file.createNewFile();
	}
	public static void registerConfig(FMLPreInitializationEvent event, String type) throws IOException, ParseException
	{
		final File config;
		config = new File(event.getModConfigurationDirectory() + "/" + Reference.MODID);
		config.mkdir();
		if(FileTest.main("config/sbp/sbp.cfg") == false) {
			DefaultValues.init();
			}
		else {
			
		}
		file = new File("config/sbp/sbp.cfg");
		file.createNewFile();
	}
	
	public static void loadValue(String key) {
		obj.put(key, DataGetter.find(key));
	}
	
}