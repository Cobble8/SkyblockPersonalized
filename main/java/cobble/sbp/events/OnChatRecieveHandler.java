package cobble.sbp.events;

import java.io.IOException;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import cobble.sbp.utils.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OnChatRecieveHandler {

	@SubscribeEvent
	public void chatRecieved(ClientChatReceivedEvent event) {
		if((Boolean) DataGetter.find("modToggle")) {
		String message = (event.message.getUnformattedText());
		if(message.startsWith("Your new API key is ")) {
			
				String apiKey = message.replace("Your new API key is ", "");
				ChatStyle hoverText = new ChatStyle();
	        	hoverText.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(ChatFormatting.GOLD+apiKey)));
				try {ConfigHandler.newObject("APIKey", apiKey+"");} catch (IOException e) {Utils.sendMessage(ChatFormatting.DARK_AQUA+"[SBP]"+ChatFormatting.RED+" Failed to upload the API Key to the config");;}
				String text = ChatFormatting.DARK_AQUA+"[SBP] "+ChatFormatting.AQUA+"Your Hypixel API Key has been set to: "+ChatFormatting.GOLD+""+ChatFormatting.OBFUSCATED+apiKey+ChatFormatting.AQUA+".";
				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(text).setChatStyle(hoverText));
			}
		}
	}
}
