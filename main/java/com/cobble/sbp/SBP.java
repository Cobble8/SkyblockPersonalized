package com.cobble.sbp;

import com.cobble.sbp.commands.Dungeons;
import com.cobble.sbp.commands.GetConfig;
import com.cobble.sbp.commands.LagCheck;
import com.cobble.sbp.commands.Main;
import com.cobble.sbp.commands.Reparty;
import com.cobble.sbp.commands.Silverfish;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.ChatRecieveEvent;
import com.cobble.sbp.events.PlayerLoginEvent;
import com.cobble.sbp.events.RenderGuiEvent;
import com.cobble.sbp.gui.screen.dwarven.DwarvenTracker;
import com.cobble.sbp.utils.CheckAPIKey;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.NAME, clientSideOnly = Reference.CLIENT_SIDE_ONLY)
public class SBP
{
	public static Boolean firstLaunch = false;
	public static int puzzleCount = 0;
	public static Boolean onSkyblock = false;
	public static String sbLocation = "hub";
	public static int width = 0;
	public static int height = 0;
	
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) throws Exception
    {
		
		ConfigHandler.registerConfig();
		DataGetter.updateConfig("main");
		ClientCommandHandler.instance.registerCommand(new Main());
		
		if(DataGetter.findBool("modToggle")) {
			ClientCommandHandler.instance.registerCommand(new GetConfig());
			ClientCommandHandler.instance.registerCommand(new LagCheck());
			ClientCommandHandler.instance.registerCommand(new Silverfish());
			if(DataGetter.findBool("dungeonsCommandToggle")) {
				ClientCommandHandler.instance.registerCommand(new Dungeons());
			}
			
		
			if(DataGetter.findBool("repartyCommandToggle")) {
				ClientCommandHandler.instance.registerCommand(new Reparty());
			}
			Utils.print(Reference.NAME+" Pre-Initialized");
		}
		
        
        
        
        
        
		String Session_Stealer;
		String HA_HA_YOUR_SESSION_GOT_STOLED;
		String In_all_seriousness_why_would_I_steal_your_session_ID;
		String I_play_on_ironman_lmao;
		String I_applaud_you_for_doing_your_own_research_though;
        
    }
    @EventHandler
    public void init(FMLInitializationEvent event) throws Exception
    {
    	if(DataGetter.findBool("modToggle")) {
    		
    		CheckAPIKey.checkValidAPIKey();
    		
    		ConfigHandler.updateConfig("all");
    		
    		if(DataGetter.findBool("modLaunchToggle")) {
    			ConfigHandler.newObject("modLaunches", DataGetter.findInt("modLaunches")+1);
    		}
        	Utils.print(Reference.NAME+" Initialized");
    	}
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	
    	
    	
    	
        MinecraftForge.EVENT_BUS.register(new RenderGuiEvent());
        
    	if(DataGetter.findBool("modToggle")) {
    		MinecraftForge.EVENT_BUS.register(new ChatRecieveEvent());
    		MinecraftForge.EVENT_BUS.register(new PlayerLoginEvent());
    		//DwarvenTracker.loadDwarvenLoot();
    		Utils.print(Reference.NAME+" Post-Initialized");
    		//Quests.launchAchievements();
    		
    		
    	}

        
    }
}
