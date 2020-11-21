package cobble.sbp.utils;

import java.io.IOException;

import com.google.gson.Gson;

import cobble.sbp.handlers.ConfigHandler;
import cobble.sbp.simplejson.parser.ParseException;

public class SkyBlockAPIUtils
{
    private static final Gson GSON = new Gson();
    public static String MAX_FAIRY_SOULS;
    //public static String MINION_SLOTS;
    //public static SupportedPack PACKS;
    private static String API_KEY;
    public static String PLAYER_NAME;
    public static String PLAYER_UUID;
    public static String SKYBLOCK_PROFILE;
    public static String SKYBLOCK_PROFILES;
    public static String SKYBLOCK_AUCTION;
    public static String BAZAAR;
    public static String GUILD;

    public static void setApiKey()
    {
        SkyBlockAPIUtils.API_KEY = (String) DataGetter.find("APIKey");
        PLAYER_NAME = "https://api.hypixel.net/player?key=" + API_KEY + "&name=";
        PLAYER_UUID = "https://api.hypixel.net/player?key=" + API_KEY + "&uuid=";
        SKYBLOCK_PROFILE = "https://api.hypixel.net/skyblock/profile?key=" + API_KEY + "&profile=";
        SKYBLOCK_PROFILES = "https://api.hypixel.net/skyblock/profiles?key=" + API_KEY + "&uuid=";
        SKYBLOCK_AUCTION = "https://api.hypixel.net/skyblock/auction?key=" + API_KEY + "&profile=";
        BAZAAR = "https://api.hypixel.net/skyblock/bazaar?key=" + API_KEY;
        GUILD = "https://api.hypixel.net/guild?key=" + API_KEY + "&player=";
    }

    public static void setApiKeyFromServer(String uuid) throws IOException
    {
        //ConfigHandler.getProperty(ConfigHandler.MAIN_SETTINGS, "Hypixel API Key", ConfigHandler.APIKey).set(uuid);
        ConfigHandler.newObject("APIKey", uuid);
        
        SkyBlockAPIUtils.setApiKey();
    }
    
    public static String getFairySouls()
    {
        try
        {
        	MAX_FAIRY_SOULS = (String) DataGetter.findSpecific("src/main/resources/assets/sbp/api/stat_bonuses/misc/max_fairy_souls.json", "max_fairy_souls");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            MAX_FAIRY_SOULS = "Error";
        }
		return MAX_FAIRY_SOULS;
    }
    




}
