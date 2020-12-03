package cobble.sbp.events;

import cobble.sbp.utils.DataGetter;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class PressKeyEvent
{
	//public static Boolean timerOn = false;
    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) throws InterruptedException
    {
    	if((Boolean) DataGetter.find("modToggle")) {
    		
    		//if (KeyBindingHandler.timer.isPressed())
            //{
                //if(timerOn) {
                	//timerOn = false;
                	//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Items/second counter is now "+ChatFormatting.RED+"disabled."));
                //} else {
                	//timerOn = true;
                	//Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Items/second counter is now "+ChatFormatting.GREEN+"enabled."));
                //}
            //}
    		
    	}
        
        
    }
}