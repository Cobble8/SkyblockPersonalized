package cobble.sbp.handlers;

import org.lwjgl.input.Keyboard;

import cobble.sbp.utils.Reference;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindingHandler {
	
	public static KeyBinding timer;
	public static KeyBinding temp;
	 
    public static void register()
    {
        //timer = new KeyBinding("Toggles Items Timer", Keyboard.KEY_O, Reference.NAME);
 
        //ClientRegistry.registerKeyBinding(timer);
    }
}
