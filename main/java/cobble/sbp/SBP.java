package cobble.sbp;

import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.commands.CheckLatestVersion;
import cobble.sbp.commands.Dungeons;
import cobble.sbp.commands.EnableMod;
import cobble.sbp.commands.IcePathPuzzle;
import cobble.sbp.commands.SetAPIKey;
import cobble.sbp.commands.ShowConfig;
import cobble.sbp.commands.SilverfishPuzzle;
import cobble.sbp.handlers.ConfigHandler;
import cobble.sbp.handlers.KeyBindingHandler;
import cobble.sbp.handlers.KeyInputHandler;
import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.simplejson.parser.ParseException;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.HttpClient;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class SBP 
{
	public static Boolean firstLaunch = false;
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) throws IOException, ParseException
	{
		ConfigHandler.registerConfig(event);
		//Commands

		ClientCommandHandler.instance.registerCommand(new SetAPIKey());
		ClientCommandHandler.instance.registerCommand(new EnableMod());
		ClientCommandHandler.instance.registerCommand(new Dungeons());
		ClientCommandHandler.instance.registerCommand(new ShowConfig());
		ClientCommandHandler.instance.registerCommand(new CheckLatestVersion());
		ClientCommandHandler.instance.registerCommand(new SilverfishPuzzle());
		ClientCommandHandler.instance.registerCommand(new IcePathPuzzle());
		
		KeyBindingHandler.register();
		MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
		
		
		
		
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
		
		 
	}


	
	@EventHandler
	public void serverInit(FMLServerStartingEvent event)
	{
		
	}
	
	
	
}