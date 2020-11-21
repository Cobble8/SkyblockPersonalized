package cobble.sbp.utils;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Utils {
	private static final Gson gson = new Gson();
	
	public static boolean isJSONValid(String jsonInString) {
	      try {
	          gson.fromJson(jsonInString, Object.class);
	          return true;
	      } catch(com.google.gson.JsonSyntaxException ex) { 
	          return false;
	      }
	  }
	
	public static String formatNums(int num) {
		NumberFormat myFormat = NumberFormat.getInstance();
	    myFormat.setGroupingUsed(true);
	    return myFormat.format(num);
	}
	
	
	public static String secondsToTime(int input) { 
		long hours = TimeUnit.MILLISECONDS
			    .toHours(input);
			input -= TimeUnit.HOURS.toMillis(hours);

			long minutes = TimeUnit.MILLISECONDS
			    .toMinutes(input);
			input -= TimeUnit.MINUTES.toMillis(minutes);

			long seconds = TimeUnit.MILLISECONDS
			    .toSeconds(input);
			
			if(hours > 0) {
				return hours+"h"+minutes+"m"+seconds+"s";
			} else {
				return minutes+"m"+seconds+"s";
			}
	}
	
	
	public static boolean isNumeric(final String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
	
	
	public static void sendMessage(String text) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
	}
	public static void enableMod() {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("The mod has now been"+ChatFormatting.GREEN+" enabled "+ChatFormatting.RESET+"due to you attempting to use it. Use the command again to show the information."));
		try {ConfigHandler.newObject("modToggle", true);} catch (IOException e) {e.printStackTrace();}
	}
	public static void setApiKey() {
		sendMessage(ChatFormatting.RED+"Please set your "+ChatFormatting.GOLD+"SkyblockPersonalized "+ChatFormatting.RED+"API Key!");
	}
	
	private static Object configFind(String type) {
		return DataGetter.find(type);
	}
	static long time = System.currentTimeMillis()/20;
	static int i = 0;
	
	public static int getI() {
		return i;
	}
	
	public static void print(Object output) {
		System.out.println(output);
	}
	
	public static boolean toggleAnimation(String type) {
		boolean var = (Boolean) configFind(type);
        	if(var == true) {var = false;}
        	if(!(Boolean) DataGetter.find(type)) {
        	if(System.currentTimeMillis()/20 != time) {
        		time = System.currentTimeMillis()/20;
        		if(i == 9) {
        			var = true;
        			try {ConfigHandler.newObject(type, true);} catch (IOException e) {e.printStackTrace();}
        		} else i++;
        	}
        } else {
        	if(System.currentTimeMillis()/20 != time) {
        		time = System.currentTimeMillis()/20;
        		if(i == 0) {
        			try {ConfigHandler.newObject(type, false);} catch (IOException e) {e.printStackTrace();}
        			} else i--;
        		}
        	}
        	return var;
	}
}
