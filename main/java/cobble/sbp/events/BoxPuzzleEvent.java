package cobble.sbp.events;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.threads.DisableScreenImageThread;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BoxPuzzleEvent {

	public static void run() {
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		World w = Minecraft.getMinecraft().theWorld;
		int x = (int) Math.floor(player.posX);
		int y = (int) Math.floor(player.posY);
		int z = (int) Math.floor(player.posZ);
		String dR;
		int dX = 1;
		int dZ = 1;
		Block p = Blocks.planks;
		
		if(Utils.checkBlock(w, x, y-1, z, Blocks.stonebrick)) {
			
			if(Utils.checkBlock(w, x+2, y-2, z-1, Blocks.cobblestone_wall) && Utils.checkBlock(w, x-2, y-2, z-1, Blocks.cobblestone_wall) && Utils.checkBlock(w, x, y-5, z+2, Blocks.cobblestone_wall)) {
				dR = "north"; dX = 1; dZ = -1;
			} else if(Utils.checkBlock(w, x+1, y-2, z-2, Blocks.cobblestone_wall) && Utils.checkBlock(w, x+1, y-2, z+2, Blocks.cobblestone_wall) && Utils.checkBlock(w, x-2, y-5, z, Blocks.cobblestone_wall)) {
				dR = "east"; dX = 1; dZ = 1;
			} else if(Utils.checkBlock(w, x+2, y-2, z+1, Blocks.cobblestone_wall) && Utils.checkBlock(w, x-2, y-2, z+1, Blocks.cobblestone_wall) && Utils.checkBlock(w, x, y-5, z-2, Blocks.cobblestone_wall)) {
				dR = "south"; dX = -1; dZ = 1;
			}  else if(Utils.checkBlock(w, x-1, y-2, z-2, Blocks.cobblestone_wall) && Utils.checkBlock(w, x-1, y-2, z+2, Blocks.cobblestone_wall) && Utils.checkBlock(w, x+2, y-5, z, Blocks.cobblestone_wall)) {
				dR = "west"; dX = -1; dZ = -1;
			} else {return;}
			
			Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.YELLOW+" Locating box room combination...");
			//Utils.sendMessage(dR);
			//Utils.checkBlock(w, x+RenderGuiHandler.gFX(dR, dX, dZ, 1, 1), y, z+RenderGuiHandler.gFZ(dR, dX, dZ, 1, 1), p)
			
			
			if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(dR, dX, dZ, -8, 2), y-3, z+RenderGuiHandler.gFZ(dR, dX, dZ, -8, 2), p) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(dR, dX, dZ, 8, 2), y-3, z+RenderGuiHandler.gFZ(dR, dX, dZ, 8, 2), p)) {
				//Utils.sendMessage("works");
				//Utils.sendMessage("works (2)");
				if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(dR, dX, dZ, -2, 2), y-3, z+RenderGuiHandler.gFZ(dR, dX, dZ, -2, 2), p)) {
					RenderGuiHandler.imageID="box_6";
					Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.YELLOW+" Found: "+ChatFormatting.AQUA+"box_6");
					DisableScreenImageThread.delay = Long.parseLong(DataGetter.find("imageDelay")+"");
					Thread disableImage = new DisableScreenImageThread();
					disableImage.start();
					return;
				} else {
					RenderGuiHandler.imageID="box_3";
					Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.YELLOW+" Found: "+ChatFormatting.AQUA+"box_3");
					DisableScreenImageThread.delay = Long.parseLong(DataGetter.find("imageDelay")+"");
					Thread disableImage = new DisableScreenImageThread();
					disableImage.start();
					return;
				}
			}
			else if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(dR, dX, dZ, 2, 2), y-3, z+RenderGuiHandler.gFZ(dR, dX, dZ, 2, 2), p) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(dR, dX, dZ, 5, 2), y-3, z+RenderGuiHandler.gFZ(dR, dX, dZ, 5, 2), p)) {
				RenderGuiHandler.imageID="box_1";
				Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.YELLOW+" Found: "+ChatFormatting.AQUA+"box_1");
				DisableScreenImageThread.delay = Long.parseLong(DataGetter.find("imageDelay")+"");
				Thread disableImage = new DisableScreenImageThread();
				disableImage.start();
				return;
			}
			else if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(dR, dX, dZ, -8, 5), y-3, z+RenderGuiHandler.gFZ(dR, dX, dZ, -8, 5), p) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(dR, dX, dZ, 8, 5), y-3, z+RenderGuiHandler.gFZ(dR, dX, dZ, 8, 5), p)) {
				//Utils.sendMessage("works");
				if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(dR, dX, dZ, -5, 5), y-3, z+RenderGuiHandler.gFZ(dR, dX, dZ, -5, 5), p)) {
					RenderGuiHandler.imageID="box_4";
					Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.YELLOW+" Found: "+ChatFormatting.AQUA+"box_4");
					DisableScreenImageThread.delay = Long.parseLong(DataGetter.find("imageDelay")+"");
					Thread disableImage = new DisableScreenImageThread();
					disableImage.start();
					return;
				} else {
					RenderGuiHandler.imageID="box_2";
					Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.YELLOW+" Found: "+ChatFormatting.AQUA+"box_2");
					DisableScreenImageThread.delay = Long.parseLong(DataGetter.find("imageDelay")+"");
					Thread disableImage = new DisableScreenImageThread();
					disableImage.start();
					return;
				}
			} else if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(dR, dX, dZ, -5, 8), y-3, z+RenderGuiHandler.gFZ(dR, dX, dZ, -5, 8), p) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(dR, dX, dZ, -5, 14), y-3, z+RenderGuiHandler.gFZ(dR, dX, dZ, -5, 14), p)) {
				RenderGuiHandler.imageID="box_5";
				Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.YELLOW+" Found: "+ChatFormatting.AQUA+"box_5");
				DisableScreenImageThread.delay = Long.parseLong(DataGetter.find("imageDelay")+"");
				Thread disableImage = new DisableScreenImageThread();
				disableImage.start();
				return;
			} else {
				Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.YELLOW+" Failed to find box puzzle combination, please screenshot "+ChatFormatting.YELLOW+"this and send it to "+ChatFormatting.AQUA+"Cobble8#0881 "+ChatFormatting.YELLOW+"on "+ChatFormatting.BLUE+"Discord"+ChatFormatting.YELLOW+"!");
				RenderGuiHandler.imageID="box_starting_block";
				DisableScreenImageThread.delay = Long.parseLong(DataGetter.find("imageDelay")+"");
				Thread disableImage = new DisableScreenImageThread();
				disableImage.start();
				return;
			}
			
			
			

		} 
		
		
		
	}
	
}
