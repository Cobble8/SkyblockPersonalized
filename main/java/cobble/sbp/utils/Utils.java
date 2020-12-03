package cobble.sbp.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.utils.config.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.world.World;
import scala.Int;

public class Utils {
	private static final Gson gson = new Gson();
	
	
	
	public static Boolean checkBlock(World world, int x, int y, int z, Block blockCheckAgainst) {
		if(world.getBlockState(new BlockPos(x, y, z)).getBlock().getRegistryName().equals(blockCheckAgainst.getRegistryName())) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public static boolean isJSONValid(String jsonInString) {
	      try {
	          gson.fromJson(jsonInString, Object.class);
	          return true;
	      } catch(com.google.gson.JsonSyntaxException ex) { 
	          return false;
	      }
	  }
	
	public static long stringToLong(String num) {
		String temp1 = num+"";
		Long temp2 = Long.parseLong(temp1);
		return temp2;
	}
	
	public static String getLatestVersion() throws Exception {
		String sbpAPI = HttpClient.readPage("https://cobble8.github.io/sbpAPI.html").toString();
		return new JsonParser().parse(sbpAPI.toString()).getAsJsonObject().get("LATEST_VERSION").getAsString();
	}
	
	public static String getLatestBetaVersion() throws Exception {
		String sbpAPI = HttpClient.readPage("https://cobble8.github.io/sbpAPI.html").toString();
		return new JsonParser().parse(sbpAPI.toString()).getAsJsonObject().get("LATEST_BETA_VERSION").getAsString();
	}
	
	public static String getLatestDevVersion() throws Exception {
		String sbpAPI = HttpClient.readPage("https://cobble8.github.io/sbpAPI.html").toString();
		return new JsonParser().parse(sbpAPI.toString()).getAsJsonObject().get("LATEST_DEV_VERSION").getAsString();
	}
	
	public static void checkAgainstLatestVersion() throws Exception {
			if(Reference.VERSION.equals(getLatestBetaVersion())) {
				Utils.sendMessage(ChatFormatting.DARK_RED+"-----------------------------------------------------");
				Utils.sendMessage(ChatFormatting.YELLOW+"You are on the latest"+ChatFormatting.AQUA+" Beta "+ChatFormatting.YELLOW+"version for "+ChatFormatting.GOLD+"SkyblockPersonalized"+ChatFormatting.YELLOW+"!");
		        return;
			} else if(Reference.VERSION.equals(getLatestVersion())) {
				Utils.sendMessage(ChatFormatting.DARK_RED+"-----------------------------------------------------");
				Utils.sendMessage(ChatFormatting.YELLOW+"You are on the latest "+ChatFormatting.YELLOW+"version for "+ChatFormatting.GOLD+"SkyblockPersonalized"+ChatFormatting.YELLOW+"!");
			} else if(Reference.VERSION.equals(getLatestDevVersion())) {
				Utils.sendMessage(ChatFormatting.DARK_RED+"-----------------------------------------------------");
				Utils.sendMessage(ChatFormatting.YELLOW+"You are on the latest"+ChatFormatting.BLUE+" Dev "+ChatFormatting.YELLOW+"version for "+ChatFormatting.GOLD+"SkyblockPersonalized"+ChatFormatting.YELLOW+"!");
			} else {
				Utils.sendMessage(ChatFormatting.DARK_RED+"-----------------------------------------------------");
				ChatStyle copyText = new ChatStyle();
		        copyText.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(ChatFormatting.AQUA+"Click here to go to the "+ChatFormatting.BLUE+"Discord"+ChatFormatting.AQUA+" server!")));
		        copyText.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/QXA3y5EbNA"));
		        if(Reference.BETA) {Utils.sendMessage(ChatFormatting.YELLOW+"Your"+ChatFormatting.GOLD+" SkyblockPersonalized"+ChatFormatting.AQUA+" Beta" +ChatFormatting.YELLOW+" is out of date!");}
		        else {Utils.sendMessage(ChatFormatting.YELLOW+"Your"+ChatFormatting.GOLD+" SkyblockPersonalized"+ChatFormatting.YELLOW+" is out of date!");}
		        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.YELLOW+"Click here to go to the "+ChatFormatting.BLUE+"Discord "+ChatFormatting.YELLOW+"server \n"+ChatFormatting.YELLOW+"to download the latest version!").setChatStyle(copyText));
				return;
			}
	}
	
	public static void setApiKey() {
		ChatStyle runCommand = new ChatStyle();
		runCommand.setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/api new"));
		runCommand.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(ChatFormatting.YELLOW+"Click to run "+ChatFormatting.AQUA+"/api new")));
		Utils.sendMessage(ChatFormatting.DARK_RED+"-----------------------------------------------------");
		Utils.sendMessage(ChatFormatting.YELLOW+"Your "+ChatFormatting.GOLD+"SkyblockPersonalized "+ChatFormatting.YELLOW+"API key is not setup properly!");
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.YELLOW+"Type "+ChatFormatting.AQUA+"/api new"+ChatFormatting.YELLOW+" to set your API key!").setChatStyle(runCommand));
		Utils.sendMessage(ChatFormatting.DARK_RED+"-----------------------------------------------------");
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
	
	public static void deleteFile(String args) 
    { 
        File file = new File(args); 
          
        try
        { 
            Files.deleteIfExists(Paths.get(args)); 
        } 
        catch(NoSuchFileException e) 
        { 
            System.out.println("No such file/directory exists"); 
        } 
        catch(DirectoryNotEmptyException e) 
        { 
            System.out.println("Directory is not empty."); 
        } 
        catch(IOException e) 
        { 
            //System.out.println("Invalid permissions."); 
        } 
          
        System.out.println("Deletion successful."); 
    }
	
	
	public static void sendMessage(String text) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text));
	}
	public static void enableMod() {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.GOLD+"SkyblockPersonalized "+ChatFormatting.RESET+"has now been"+ChatFormatting.GREEN+" enabled "+ChatFormatting.RESET+"due to you attempting to use it. Use the command again to show the information."));
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
