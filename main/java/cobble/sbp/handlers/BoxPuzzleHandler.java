package com.cobble.sbp.handlers;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.threads.onetimes.DisablePuzzleImageThread;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class BoxPuzzleHandler {

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
		RenderGuiEvent r = new RenderGuiEvent();
		
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
			
			SBP.puzzleCount++;
			DisablePuzzleImageThread.delay = Long.parseLong(DataGetter.find("puzzleDelay")+"");
			Thread disableImage = new DisablePuzzleImageThread();
			disableImage.start();
			
			if(Utils.checkBlock(w, x+r.gFX(dR, dX, dZ, -8, 2), y-3, z+r.gFZ(dR, dX, dZ, -8, 2), p) && Utils.checkBlock(w, x+r.gFX(dR, dX, dZ, 8, 2), y-3, z+r.gFZ(dR, dX, dZ, 8, 2), p)) {
				if(Utils.checkBlock(w, x+r.gFX(dR, dX, dZ, -2, 2), y-3, z+r.gFZ(dR, dX, dZ, -2, 2), p)) {
					
					r.imageID="box_6";
					return;
					
				} else {
					
					r.imageID="box_3";
					return;
					
				}
			}
			else if(Utils.checkBlock(w, x+r.gFX(dR, dX, dZ, 2, 2), y-3, z+r.gFZ(dR, dX, dZ, 2, 2), p) && Utils.checkBlock(w, x+r.gFX(dR, dX, dZ, 5, 2), y-3, z+r.gFZ(dR, dX, dZ, 5, 2), p)) {
				
				r.imageID="box_1";
				return;
				
			}
			else if(Utils.checkBlock(w, x+r.gFX(dR, dX, dZ, -8, 5), y-3, z+r.gFZ(dR, dX, dZ, -8, 5), p) && Utils.checkBlock(w, x+r.gFX(dR, dX, dZ, 8, 5), y-3, z+r.gFZ(dR, dX, dZ, 8, 5), p)) {
				if(Utils.checkBlock(w, x+r.gFX(dR, dX, dZ, -5, 5), y-3, z+r.gFZ(dR, dX, dZ, -5, 5), p)) {
					
					r.imageID="box_4";
					return;
					
				} else {
					
					r.imageID="box_2";
					return;
					
				}
			} else if(Utils.checkBlock(w, x+r.gFX(dR, dX, dZ, -5, 8), y-3, z+r.gFZ(dR, dX, dZ, -5, 8), p) && Utils.checkBlock(w, x+r.gFX(dR, dX, dZ, -5, 14), y-3, z+r.gFZ(dR, dX, dZ, -5, 14), p)) {
				
				r.imageID="box_5";
				return;
				
			} else if(Utils.checkBlock(w, x+r.gFX(dR, dX, dZ, -8, 14), y-3, z+r.gFZ(dR, dX, dZ, -8, 14), p) && Utils.checkBlock(w, x+r.gFX(dR, dX, dZ, 8, 14), y-3, z+r.gFZ(dR, dX, dZ, 8, 14), p)) {
				
				r.imageID="box_7";
				return;
				
			}
			
			
			
			
			else {
				Utils.sendMessage("Failed to find box puzzle combination, please screenshot "+Colors.YELLOW+"this and send it to "+Colors.AQUA+"Cobble8#0881 "+Colors.YELLOW+"on "+Colors.BLUE+"Discord"+Colors.YELLOW+"!");
				r.imageID="box_starting_block";
				return;
			}
			
			
			

		} 
		
		
		
	}
	
}
