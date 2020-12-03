package cobble.sbp.events;

import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.threads.DisableScreenImageThread;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class IcePathEvent {

	public static void run() {
		EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
		World w = Minecraft.getMinecraft().theWorld;
		int x = (int) Math.floor(player.posX);
		int y = (int) Math.floor(player.posY);
		int z = (int) Math.floor(player.posZ);
		
		if(w.getBlockState(new BlockPos(x, y-1, z)).getBlock().getRegistryName().contains("stairs")) {
			BlockStairs blockStandingOn = (BlockStairs) w.getBlockState(new BlockPos(x, y-1, z)).getBlock();
			if(blockStandingOn == null) {return;}
				if(blockStandingOn.getRegistryName().equals(Blocks.stone_brick_stairs.getRegistryName())) {

					
					String stairsFacing = "";
					
					int directionX = 1;
					int directionZ = 1;
					
					//gets direction of puzzle
					if(Utils.checkBlock(w, x, y-1, z-1, Blocks.ice)) {
						stairsFacing = "north"; directionX = 1; directionZ = -1;
					}
					else if(Utils.checkBlock(w, x+1, y-1, z, Blocks.ice)) {
						stairsFacing = "east"; directionX = 1; directionZ = 1;
					}
					else if(Utils.checkBlock(w, x, y-1, z+1, Blocks.ice)) {
						stairsFacing = "south"; directionX = -1; directionZ = 1;
					}
					else if(Utils.checkBlock(w, x-1, y-1, z, Blocks.ice)) {
						stairsFacing = "west"; directionX = -1; directionZ = -1;
					} else {return;}
					String sF = stairsFacing;
					int dX = directionX;
					int dZ = directionZ;
					Block s = Blocks.stone;
					
					//Checks block layout around player
					DisableScreenImageThread.delay = Long.parseLong(DataGetter.find("imageDelay")+"");
					Thread startDelay = new DisableScreenImageThread();
					startDelay.start();
					if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, 1, 1), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, 1, 1), s) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, -1, 3), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, -1, 3), s)) {
						RenderGuiHandler.imageID = "ice_path_1_1";
					} else if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, -1, 1), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, -1, 1), s) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, 1, 3), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, 1, 3), s)) {
						RenderGuiHandler.imageID = "ice_path_1_2";
					} else if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, 0, 2), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, 0, 2), s) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, 1, 4), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, 1, 4), s) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, -2, 1), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, -2, 1), Blocks.air)) {
						RenderGuiHandler.imageID = "ice_path_2_1";
					} else if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, 0, 2), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, 0, 2), s) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, 1, 4), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, 1, 4), s) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, -2, 1), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, -2, 1), s)) {
						RenderGuiHandler.imageID = "ice_path_2_2";
					} else if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, 0, 3), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, 0, 3), s) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, -1, 3), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, -1, 3), s)) {
						RenderGuiHandler.imageID = "ice_path_2_3";
					} else if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, -1, 2), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, -1, 2), s) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, 1, 3), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, 1, 3), s)) {
						RenderGuiHandler.imageID = "ice_path_2_4";
					} else if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, -3, 1), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, -3, 1), s) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, 1, 5), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, 1, 5), s)) {
						RenderGuiHandler.imageID = "ice_path_3_1";
					} else if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, -1, 3), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, -1, 3), s) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, 1, 3), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, 1, 3), s)) {
						RenderGuiHandler.imageID = "ice_path_3_2";
					} else if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, 1, 1), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, 1, 1), s) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, 1, 6), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, 1, 6), s)) {
						RenderGuiHandler.imageID = "ice_path_3_3";
					} else if(Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, 2, 2), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, 2, 2), s) && Utils.checkBlock(w, x+RenderGuiHandler.gFX(sF, dX, dZ, -2, 4), y, z+RenderGuiHandler.gFZ(sF, dX, dZ, -2, 4), s)) {
						RenderGuiHandler.imageID = "ice_path_3_4";
					} 
					
			}
		}
	}
	
}
