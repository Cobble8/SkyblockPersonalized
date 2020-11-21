package cobble.sbp.utils;

import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.handlers.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class Utils {
	public static void sendMessage(String text) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
	}
	public static void enableMod() {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("The mod has now been"+ChatFormatting.GREEN+" enabled "+ChatFormatting.RESET+"due to you attempting to use it. Use the command again to show the information."));
		try {ConfigHandler.newObject("modToggle", true);} catch (IOException e) {e.printStackTrace();}
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
