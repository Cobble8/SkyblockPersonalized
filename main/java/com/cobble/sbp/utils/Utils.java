package com.cobble.sbp.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.lwjgl.opengl.GL11;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.LobbySwapEvent;
import com.cobble.sbp.events.PlayerLoginEvent;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class Utils {

	public static void renderPlayer(int x, int y, int scaled, int lookingX, int lookingY) {
		int posX = x;
		int posY = y;
		int scale = scaled;
		int mouseX2 = lookingX-posX;
		int mouseY2 = lookingY-posY+(scale*4/3)+(scale/10);
		
		EntityLivingBase ent = Minecraft.getMinecraft().thePlayer;
		GlStateManager.enableBlend();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY2*-1 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX2*-1 / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX2*-1 / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY2*-1 / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(360.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GlStateManager.disableColorMaterial();
		GlStateManager.disableBlend();
	}
	
	public static void drawString(String text, int x, int y, int color, Boolean shadowed) {
		Minecraft mc = Minecraft.getMinecraft();
		mc.fontRendererObj.drawString(text, x, y, color, shadowed);
	}
	
	public static void drawString(String text, int x, int y, int color, Boolean shadowed, Boolean bolded) {
		Minecraft mc = Minecraft.getMinecraft();
		mc.fontRendererObj.drawString(text, x + 1, y, 0, false);
        mc.fontRendererObj.drawString(text, x - 1, y, 0, false);
        mc.fontRendererObj.drawString(text, x, y + 1, 0, false);
        mc.fontRendererObj.drawString(text, x, y - 1, 0, false);
        mc.fontRendererObj.drawString(text, x, y, 8453920, false);
	}
	
	public static Boolean inRange(int checkNum, int num, int range) {
		int side1 = num-range;
		int side2 = num+range;
		if(checkNum == num) {
			return false;
		}
		
		
		if(checkNum >= side1 && checkNum <= side2) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void checkIfOnSkyblock() {
		if((Boolean) DataGetter.find("onlyOnSkyblock")) {
			String title = getBoardTitle().toLowerCase();
			if(title.equals("skyblock")) {
				if(!SBP.onSkyblock) { Utils.print("Logged onto Skyblock"); LobbySwapEvent.currLobby="";} SBP.onSkyblock=true;
			} else { if(SBP.onSkyblock) { Utils.print("Logged off of Skyblock"); } SBP.onSkyblock=false; }
		
		} else {
			SBP.onSkyblock=true;
		}
	}
	
	public static String getBoardTitle() {
		String output = "null";
		try {
			
		ScoreObjective sidebarObjective = Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
		
		output = Utils.unformatText(sidebarObjective.getDisplayName());
		} catch(Exception e) {
			return "null";
		}
		//Utils.sendMessage(output);
		return output;
	}
	
	public static void setEasterEgg(int num, String input) {
		try {
			String[] temp = (DataGetter.find("easterEggsFound")+"").split(";");
			ArrayList<String> temp2 = new ArrayList();
			
			for(int i=0;i<temp.length;i++) {
				temp2.add(temp[i]);
			}
			
			temp2.set(num-1, input);
			String output = "";
			for(int i=0;i<temp2.size();i++) {
				output+=temp2.get(i)+";";
			}
			ConfigHandler.newObject("easterEggsFound", output);
			
		} catch(Exception e) {
			ConfigHandler.newObject("easterEggsFound", ConfigHandler.getDefaultValue("easterEggsFound"));
			return;
		}
	}
	
	public static String getEasterEgg(int num) {
		
		try {
		String output = "";
		String[] temp = (DataGetter.find("easterEggsFound")+"").split(";");
		ArrayList<String> temp2 = new ArrayList();
		
		for(int i=0;i<temp.length;i++) {
			temp2.add(temp[i]);
		}
		output = temp2.get(num);
		
		
		return output;
		} catch(Exception e) {
			return "null";
		}
	}
	
	
	public static String getColorFromInt(int colorID) {
		
		String[] colorList = {Colors.WHITE, Colors.DARK_RED, Colors.RED, Colors.GOLD, Colors.YELLOW, Colors.GREEN, Colors.DARK_GREEN, Colors.AQUA, Colors.DARK_AQUA, Colors.BLUE, Colors.DARK_BLUE, Colors.LIGHT_PURPLE, Colors.DARK_PURPLE, Colors.GRAY, Colors.DARK_GRAY, Colors.BLACK};
		String output = "";
		try {
			output = colorList[colorID];
		} catch(Exception e) {
			
		}
		return output;
		
		
	}
	
	public static void drawTexturedRectNoBlend(float x, float y, float width, float height, float uMin, float uMax, float vMin, float vMax, int filter) {
        GlStateManager.enableTexture2D();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer
                .pos(x, y+height, 0.0D)
                .tex(uMin, vMax).endVertex();
        worldrenderer
                .pos(x+width, y+height, 0.0D)
                .tex(uMax, vMax).endVertex();
        worldrenderer
                .pos(x+width, y, 0.0D)
                .tex(uMax, vMin).endVertex();
        worldrenderer
                .pos(x, y, 0.0D)
                .tex(uMin, vMin).endVertex();
        tessellator.draw();

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    }
	
	public static String getSystemMinute() {
		String output = "";

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm:ss");
		LocalDateTime date = LocalDateTime.now();
		output = dtf.format(date);
		//output = dtf+"";
		
		return output+"";
	}
	
	public static String unformatText(String string) {
		String output = "";
		char[] chars = string.toCharArray();
		Boolean prevChar = false;
		for(int i=0;i<chars.length;i++) {
			if(!((chars[i]+"").equals(Reference.COLOR_CODE_CHAR+""))) {
				if(!prevChar) {
					output+=chars[i];
				}
				prevChar = false;
				
			} else if((chars[i]+"").equals(Reference.COLOR_CODE_CHAR+"")) {
				prevChar = true;
			}
		}
		
		return output;
	}
	
	public static String formatNums(int num) {
		NumberFormat myFormat = NumberFormat.getInstance();
	    myFormat.setGroupingUsed(true);
	    return myFormat.format(num);
	}
	
	public static void playClickSound() {
		Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.3F, 1.0F);
	}
	
	public static void playDingSound() {
		Minecraft.getMinecraft().thePlayer.playSound("note.pling", 1.0F, 1.0F);
	}
	
	public static Boolean checkBlock(World world, int x, int y, int z, Block blockCheckAgainst) {
		if(world.getBlockState(new BlockPos(x, y, z)).getBlock().getRegistryName().equals(blockCheckAgainst.getRegistryName())) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String secondsToTime(int input) { 
		long hours = TimeUnit.MILLISECONDS
			    .toHours(input);
			input -= TimeUnit.HOURS.toMillis(hours);

			long minutes = TimeUnit.MILLISECONDS
			    .toMinutes(input);
			input -= TimeUnit.MINUTES.toMillis(minutes);

			long seconds = TimeUnit.MILLISECONDS.toSeconds(input);
			String secs = seconds+"";
			if(seconds < 10) {
				secs = "0"+seconds;
			}
			
			if(hours > 0) {
				return hours+"h"+minutes+"m"+secs+"s";
			} else {
				return minutes+":"+secs;
			}
	}
	
	public static ArrayList<Integer> sortIntArray(ArrayList<Integer> array) {

		ArrayList<Integer> returnArray = new ArrayList<Integer>();
		returnArray = array;
		Collections.sort(returnArray);
		
		return returnArray;
		
	}
	
	public static Boolean isNumeric(final String str) {
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
	
	public static void print(Object string) {
		System.out.println(string);
	}
	
	public static void sendMessage(String string) {
		sendSpecificMessage(Colors.DARK_AQUA+"["+Colors.AQUA+"SBP"+Colors.DARK_AQUA+"] "+Colors.YELLOW+string+Colors.WHITE);
	}
	
	public static void sendSpecificMessage(String string) {
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(string));
	}
	
	public static String removeLastChars(String str, int chars) {
		
		
		String finalString = str;
		for(int i=0;i<chars;i++) {
			char[] charList = finalString.toCharArray();
			finalString="";
			for(int j=0;i<charList.length;j++) {
				if(j != charList.length-1) {
					finalString+=charList[j];
				}
			}
		}
		
		return finalString;
		
	    //return str.substring(0, str.length() - chars);
	}
	
	public static Boolean checkIfArrayContains(ArrayList<String> arrayList, String stringCheck) {
		Boolean tempBoolean = false;
		for(int i=0;i<arrayList.size();i++) {
			if(stringCheck.equals(arrayList.get(i))) {
				tempBoolean = true;
			}
		}
		
		return tempBoolean;
	}
	
	public static Boolean checkIfArrayContains(String[] arrayList, String stringCheck) {
		Boolean tempBoolean = false;
		for(int i=0;i<arrayList.length;i++) {
			if(stringCheck.equals(arrayList[i])) {
				tempBoolean = true;
			}
		}
		
		return tempBoolean;
	}
	
	public static Boolean checkIfCharLetter(String string) {

		String charList = "abcdefghijklmnopqrstuvwxyz ?:;'<>/\\\".,0123456789!()*^%$@&-+_=[]{}~`|#";
		Boolean output = false;
		if(charList.contains(string.toLowerCase())) {output=true;}
		return output;
		
	}
	
	public static int stripNum(String input) {
		int output = 0;
		String charList = "abcdefghijklmnopqrstuvwxyz ?:;'<>/\\\".,!()*^%$@&-+_=[]{}~`|#";
		char[] temp = input.toLowerCase().toCharArray();
		String finalOutput = "";
		for(int i=0;i<temp.length;i++) {
			if(!(charList.contains(temp[i]+""))) {
				finalOutput+=temp[i];
			}
		}
		//Utils.print(finalOutput);
		try {
		output = Integer.parseInt(finalOutput);
		} catch(Exception e) {
			output = 0;
		}
		return output;
	}
	
	public static Boolean checkIfCharInt(String string) {

		String charList = "0123456789";
		Boolean output = false;
		if(charList.contains(string.toLowerCase())) {output=true;}
		return output;
		
	}
	
	public static void sendErrMsg(String string) {
		sendSpecificMessage(Colors.DARK_RED+"["+Colors.RED+"SBP"+Colors.DARK_RED+"] "+Colors.YELLOW+string);
	}
	
	public static boolean fileTest(String args) {
        File f = new File(args);
        //print(f + (f.exists()? " is found " : " is missing "));
        return f.exists()? true : false;
    }
	
	public static Boolean invertBoolean(Boolean input) {
		if(input == true) { return false; } else return true;
	}
	
	public static String readFile(String filePath) {
		String output = "";
		
		try {
		      File myObj = new File(filePath);
		      Scanner myReader = new Scanner(myObj);
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        output+=(data+" ");
		      }
		      myReader.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
		return output;
	}
	
	
}
