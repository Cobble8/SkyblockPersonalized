package cobble.sbp.handlers;

import java.io.IOException;

import cobble.sbp.events.BoxPuzzleEvent;
import cobble.sbp.events.IcePathEvent;
import cobble.sbp.gui.menu.Help;
import cobble.sbp.gui.menu.Main;
import cobble.sbp.gui.menu.Puzzles;
import cobble.sbp.gui.screen.PuzzleImage;
import cobble.sbp.threads.DisableScreenImageThread;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import cobble.sbp.utils.config.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderGuiHandler {
	
	public static Boolean PuzzleGUI = false;
	public static Boolean HelpGUI = false;
	public static Boolean MainGUI = false;
	public static String imageID = "NONE";
	
	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event) throws IOException {
		if(PuzzleGUI) {
			if((Boolean) DataGetter.find("autoPuzzleToggle") == null) {ConfigHandler.newObject("autoPuzzleToggle", false);}
			if(DataGetter.find("imageDelay") == null) {ConfigHandler.newObject("imageDelay", 45);}
			Minecraft.getMinecraft().displayGuiScreen(new Puzzles());
			PuzzleGUI = false;
		}
		else if(HelpGUI) {
			Minecraft.getMinecraft().displayGuiScreen(new Help());
			HelpGUI = false;
		}
		else if(MainGUI) {
			Minecraft.getMinecraft().displayGuiScreen(new Main());
			MainGUI = false;
		}
		//if((Boolean) DataGetter.find("modToggle")) {
		if(imageID != null && imageID != "NONE") {
			new PuzzleImage(Minecraft.getMinecraft());
		}
		
		if((Boolean) DataGetter.find("modToggle") && Minecraft.getMinecraft().thePlayer.onGround && (Boolean) DataGetter.find("autoPuzzleToggle").equals(true)) {
			IcePathEvent.run();
			if(!imageID.contains("box")) {
				BoxPuzzleEvent.run();
			}
		}
			
	}
	//Calculates where to check for blocks in reference to the player based on direction of puzzle
	public static int gFX(String facing, int directionX, int directionZ, int xOffset, int zOffset) {
		
		int finalX = directionX*xOffset;
		int finalZ = directionZ*zOffset;
		
		if(facing.equals("east")) {
			int temp = finalX;
			finalX = finalZ;
			finalZ = temp;
		}
		else if(facing.equals("west")) {
			int temp = finalX;
			finalX = finalZ;
			finalZ = temp;
		}
		
		return finalX;
	}
	
public static int gFZ(String facing, int directionX, int directionZ, int xOffset, int zOffset) {
		
		int finalX = directionX*xOffset;
		int finalZ = directionZ*zOffset;
		
		if(facing.equals("east")) {
			int temp = finalX;
			finalX = finalZ;
			finalZ = temp;
		}
		else if(facing.equals("west")) {
			int temp = finalX;
			finalX = finalZ;
			finalZ = temp;
		}
		
		return finalZ;
	}
	
	
}
