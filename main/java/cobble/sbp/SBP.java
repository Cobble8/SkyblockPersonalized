package cobble.sbp;

import com.google.gson.JsonSyntaxException;

import cobble.sbp.commands.functional.Dungeons;
import cobble.sbp.commands.menus.AutoPuzzle;
import cobble.sbp.commands.menus.HelpCommand;
import cobble.sbp.commands.menus.SBPMain;
import cobble.sbp.commands.menus.Silverfish;
import cobble.sbp.commands.returntext.CheckLatestVersion;
import cobble.sbp.commands.returntext.GetForumLink;
import cobble.sbp.commands.returntext.ShowConfig;
import cobble.sbp.commands.returntext.TestingCommand;
import cobble.sbp.commands.setconfig.SetAPIKey;
import cobble.sbp.commands.setconfig.SetImageDelay;
import cobble.sbp.commands.setconfig.SetImageID;
import cobble.sbp.commands.toggles.EnableMod;
import cobble.sbp.commands.toggles.ToggleAutoPuzzle;
import cobble.sbp.events.OnChatRecieveHandler;
import cobble.sbp.events.PlayerLoginHandler;
import cobble.sbp.events.PressKeyEvent;
import cobble.sbp.handlers.KeyBindingHandler;
import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.config.ConfigHandler;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, clientSideOnly = true)
public class SBP 
{
	public static Boolean firstLaunch = false;
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) throws JsonSyntaxException, Exception
	{
		
		ConfigHandler.registerConfig(event);
		//Commands

		ClientCommandHandler.instance.registerCommand(new SetAPIKey());
		ClientCommandHandler.instance.registerCommand(new EnableMod());
		ClientCommandHandler.instance.registerCommand(new Dungeons());
		ClientCommandHandler.instance.registerCommand(new ShowConfig());
		ClientCommandHandler.instance.registerCommand(new CheckLatestVersion());
		ClientCommandHandler.instance.registerCommand(new AutoPuzzle());
		ClientCommandHandler.instance.registerCommand(new HelpCommand());
		ClientCommandHandler.instance.registerCommand(new SetImageDelay());
		ClientCommandHandler.instance.registerCommand(new GetForumLink());
		ClientCommandHandler.instance.registerCommand(new ToggleAutoPuzzle());
		ClientCommandHandler.instance.registerCommand(new SetImageID());
		ClientCommandHandler.instance.registerCommand(new TestingCommand());
		ClientCommandHandler.instance.registerCommand(new SBPMain());
		ClientCommandHandler.instance.registerCommand(new Silverfish());
		
		KeyBindingHandler.register();
		MinecraftForge.EVENT_BUS.register(new PressKeyEvent());
		
		
		
		
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		System.out.println("SBP Initialized");
		
	}
	
	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) throws Exception
	{
		
		MinecraftForge.EVENT_BUS.register(new RenderGuiHandler());
		MinecraftForge.EVENT_BUS.register(new OnChatRecieveHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerLoginHandler());
		
	}
	
	
	@EventHandler
	public void serverInit(FMLServerStartingEvent event)
	{
		
	}
	
	
	
}