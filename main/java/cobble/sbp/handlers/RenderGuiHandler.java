package cobble.sbp.handlers;

import cobble.sbp.gui.menu.MainGUI;
import cobble.sbp.gui.menu.TaskManagerGUI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderGuiHandler {
	
	public static boolean SBPHelpGuiDisplay = false;
	public static boolean TaskManagerDisplay = false;
	
	@SubscribeEvent
	public void onRenderGui(RenderGameOverlayEvent.Post event) {
		
		/*if(SBPHelpGuiDisplay) {
			Minecraft.getMinecraft().displayGuiScreen(new MainGUI());
			SBPHelpGuiDisplay = false;
		}
		if(TaskManagerDisplay) {
			Minecraft.getMinecraft().displayGuiScreen(new TaskManagerGUI());
			TaskManagerDisplay = false;
		}*/
	}
	
	
}
