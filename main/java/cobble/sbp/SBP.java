package cobble.sbp;

import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.commands.Dungeons;
import cobble.sbp.commands.EnableMod;
import cobble.sbp.commands.SetAPIKey;
import cobble.sbp.handlers.ConfigHandler;
import cobble.sbp.handlers.KeyBindingHandler;
import cobble.sbp.handlers.KeyInputHandler;
import cobble.sbp.handlers.RenderGuiHandler;
import cobble.sbp.simplejson.parser.ParseException;
import cobble.sbp.utils.HttpClient;
import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
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

		//ClientCommandHandler.instance.registerCommand(new ShowConfig());
		//ClientCommandHandler.instance.registerCommand(new TaskList());
		ClientCommandHandler.instance.registerCommand(new SetAPIKey());
		//ClientCommandHandler.instance.registerCommand(new SBPMain());
		ClientCommandHandler.instance.registerCommand(new EnableMod());
		ClientCommandHandler.instance.registerCommand(new Dungeons());
		
		KeyBindingHandler.register();
		MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
		
		
	}
	
	/*@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
	    String message = event.message.getUnformattedText();
	    
	        if(message.contains("API")) {
	        		message.replace("Your new API key is ", "");
	                 Utils.sendMessage(ChatFormatting.GREEN+"[SBP] Your Hypixel API Key has been saved!");
	                 try {ConfigHandler.newObject("APIKey", message);} catch (IOException e) {e.printStackTrace();}
	        }
	}*/
	
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
	
	/*@EventHandler
	public static void loginEvent(PlayerLoggedInEvent event) {
		if(firstLaunch == true) {
			Utils.sendMessage(ChatFormatting.GOLD+"Thank you for downloading SkyblockPersonalized!");
			Utils.sendMessage(ChatFormatting.GOLD+"If you have any problems with the mod be sure to DM "+ChatFormatting.BLUE+"Cobble8#0881"+ChatFormatting.GOLD+" on "+ChatFormatting.BLUE+"Discord!");
			Utils.sendMessage(ChatFormatting.GOLD+"This message has appeared because this is either your first launch or you deleted your config file!");
			Utils.sendMessage("Make sure to set your "+ChatFormatting.RED+"API Key"+ChatFormatting.GOLD+" by typing: "+ChatFormatting.YELLOW+"/api new"+ChatFormatting.GOLD+" and using the key given type: "+ChatFormatting.YELLOW+"/sbpsetkey [Your Api Key]"+ChatFormatting.GOLD+"!");
			firstLaunch = false;
		}
	}*/
	
	@EventHandler
	public void serverInit(FMLServerStartingEvent event)
	{
		
	}
	
	
	
}