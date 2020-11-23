package cobble.sbp.handlers;

import cobble.sbp.gui.menu.Puzzles;
import cobble.sbp.gui.screen.PuzzleImage;
import cobble.sbp.utils.DataGetter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderGuiHandler {
	
	public static Boolean PuzzleGUI = false;
	
	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event) {
		if(PuzzleGUI) {
			Minecraft.getMinecraft().displayGuiScreen(new Puzzles());
			PuzzleGUI = false;
		}
		if((Boolean) DataGetter.find("modToggle")) {
			new PuzzleImage(Minecraft.getMinecraft());
		}
	}
	
	
}
