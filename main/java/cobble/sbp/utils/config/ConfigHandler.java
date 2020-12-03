package cobble.sbp.utils.config;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import cobble.sbp.SBP;
import cobble.sbp.simplejson.JSONObject;
import cobble.sbp.simplejson.parser.ParseException;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.FileTest;
import cobble.sbp.utils.HttpClient;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
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
	        System.out.println("An error occurred from newObject().");
	        e.printStackTrace();
	      }
    	}
	
	public static void registerConfig(FMLPreInitializationEvent event) throws JsonSyntaxException, Exception
	{
		
		//
		
		try {
			Boolean versionCheck = new JsonParser().parse(HttpClient.readPage("https://cobble8.github.io/sbpAPI.html").toString()).getAsJsonObject().get("MOD_VERSION_"+Reference.VERSION).getAsBoolean();
			if(!versionCheck) {
				throw new Exception("This version of SkyblockPersonalized is discontinued! Please update to the latest version!");
			}
		
		} catch(NullPointerException e){
			throw new Exception("This version of SkyblockPersonalized is discontinued! Please update to the latest version!");
		}
		
		
		final File config;
		config = new File(event.getModConfigurationDirectory() + "/" + Reference.MODID);
		config.mkdir();
		if(FileTest.main("config/sbp/sbp.cfg") == false) {
			SBP.firstLaunch = true;
			DefaultValues.init();
			}
		
		//else if((DataGetter.find("configVersion").toString() != new JsonParser().parse(HttpClient.readPage("https://cobble8.github.io/sbpAPI.html").toString()).getAsJsonObject().get("CONFIG_VERSION_"+Reference.VERSION).getAsString())) {
			
		//}
		 else {
			LoadValues.loadValues();	
		}
		
		Utils.print("[SBP] Updating config...");
		String APIKey = (String) DataGetter.find("APIKey");
		Boolean modToggle = (Boolean) DataGetter.find("modToggle");
		Boolean iceFillToggle = (Boolean) DataGetter.find("autoPuzzleToggle");
		long imageXCoord = Utils.stringToLong(DataGetter.find("imageXCoord")+"");
		long imageYCoord = Utils.stringToLong(DataGetter.find("imageYCoord")+"");
		long configVersion = Utils.stringToLong(DataGetter.find("configVersion")+"");
		long imageDelay = Utils.stringToLong(DataGetter.find("imageDelay")+"");
	
		Utils.deleteFile("config/sbp/sbp.cfg");
		config.mkdir();
		newObject("APIKey", APIKey);
		newObject("modToggle", modToggle);
		newObject("autoPuzzleToggle", iceFillToggle);
		newObject("imageXCoord", imageXCoord);
		newObject("imageYCoord", imageYCoord);
		newObject("configVersion", Reference.CONFIG_VERSION);
		newObject("imageDelay", imageDelay);
		Utils.print("[SBP] Sucessfully updated config!");
		
		
		file = new File("config/sbp/sbp.cfg");
		file.createNewFile();
	}
	
	public static void loadValue(String key) {
		obj.put(key, DataGetter.find(key));
	}
	
}