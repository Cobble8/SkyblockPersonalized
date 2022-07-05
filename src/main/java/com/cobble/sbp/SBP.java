package com.cobble.sbp;

import com.cobble.sbp.commands.*;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.events.user.*;
import com.cobble.sbp.handlers.KeyBindingHandler;
import com.cobble.sbp.threads.misc.LaunchThread;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import com.cobble.sbp.utils.WebUtils;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.NAME, clientSideOnly = Reference.CLIENT_SIDE_ONLY)
public class SBP
{
	public static boolean firstLaunch = false;
	public static boolean onSkyblock = false;
	public static boolean dev = false;
	public static String sbLocation = "N/A";
	public static String subLocation = "N/A";
	public static String titleString = "";
	public static double titleScale = 4.0;
	public static int width = 0;
	public static int height = 0;
	public static File modFile;

	@EventHandler
    public void preInit(FMLPreInitializationEvent event) throws Exception
    {

		modFile = event.getSourceFile();
		ConfigHandler.registerConfig();

		new LaunchThread().start();

		KeyBindingHandler.register();
		MinecraftForge.EVENT_BUS.register(new PressKeyEvent());
		ClientCommandHandler.instance.registerCommand(new Main());
		ClientCommandHandler.instance.registerCommand(new Bedwars());
		ClientCommandHandler.instance.registerCommand(new CrystalWaypoint());


		Utils.print(Reference.NAME+" Pre-Initialized");
        
        
        
        
        
		String Session_Stealer;
		String HA_HA_YOUR_SESSION_GOT_STOLED;
		String In_all_seriousness_why_would_I_steal_your_session_ID;
		String I_play_on_ironman_lmao;
		String I_applaud_you_for_doing_your_own_research_though;
        
    }
    @EventHandler
    public void init(FMLInitializationEvent event) throws Exception
	{
		WebUtils.checkValidAPIKey();
		ConfigHandler.newObject("core.launchCounter.count", DataGetter.findInt("core.launchCounter.count")+1);
		Utils.print(Reference.NAME+" Initialized");

    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) throws Exception
    {
    	
    	
		if(LaunchThread.invalidVersion) {
			throw new Exception("This version of "+Reference.NAME+" has been forcefully disabled! Please update to the latest version or beta version at https://discord.gg/QXA3y5EbNA!");
		}


        MinecraftForge.EVENT_BUS.register(new RenderGuiEvent());
    	MinecraftForge.EVENT_BUS.register(new ChatRecieveEvent());
    	MinecraftForge.EVENT_BUS.register(new PlayerLoginEvent());
    	MinecraftForge.EVENT_BUS.register(new MenuClickEvent());
		MinecraftForge.EVENT_BUS.register(new TooltipEvent());
    	Utils.print(Reference.NAME+" Post-Initialized");

        
    }


}
