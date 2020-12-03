package cobble.sbp.threads;

import com.mojang.realmsclient.gui.ChatFormatting;

import cobble.sbp.utils.Reference;
import cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

public class LoginThread extends Thread {
	
	public static Boolean goodApiKey;
	
	public void run() {
		try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
		if(!goodApiKey) {
			Utils.setApiKey();
		}
		try { LoginThread.checkAgainstLatestVersion(); } catch (Exception e) { e.printStackTrace(); }
	}
	
	public static void checkAgainstLatestVersion() throws Exception {
		if(Reference.VERSION.equals(Utils.getLatestBetaVersion())) {
			return;
		} else if(Reference.VERSION.equals(Utils.getLatestVersion())) {
			return;
		} else if(Reference.VERSION.equals(Utils.getLatestDevVersion())) {
			return;
		} else {
			Utils.sendMessage(ChatFormatting.DARK_RED+"-----------------------------------------------------");
			ChatStyle copyText = new ChatStyle();
	        copyText.setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(ChatFormatting.AQUA+"Click here to go to the "+ChatFormatting.BLUE+"Discord"+ChatFormatting.AQUA+" server!")));
	        copyText.setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/QXA3y5EbNA"));
	        if(Reference.BETA) {Utils.sendMessage(ChatFormatting.YELLOW+"Your"+ChatFormatting.GOLD+" SkyblockPersonalized"+ChatFormatting.AQUA+" Beta" +ChatFormatting.YELLOW+" is out of date!");}
	        else {Utils.sendMessage(ChatFormatting.YELLOW+"Your"+ChatFormatting.GOLD+" SkyblockPersonalized"+ChatFormatting.YELLOW+" is out of date!");}
	        String versionStr = Utils.getLatestVersion();
	        if(Reference.BETA.equals(true)) {versionStr = Utils.getLatestBetaVersion();}
	        Utils.sendMessage(ChatFormatting.YELLOW+"Version "+ChatFormatting.AQUA+versionStr+ChatFormatting.YELLOW+" is now available!");
	        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(ChatFormatting.YELLOW+"Click here to go to the "+ChatFormatting.BLUE+"Discord "+ChatFormatting.YELLOW+"server \n"+ChatFormatting.YELLOW+"to download the latest version!").setChatStyle(copyText));
	        Utils.sendMessage(ChatFormatting.DARK_RED+"-----------------------------------------------------");
	        return;
		}
}

}
