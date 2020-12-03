package cobble.sbp.events;

import java.util.List;

import cobble.sbp.threads.LoginThread;
import cobble.sbp.utils.DataGetter;
import cobble.sbp.utils.Utils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class PlayerLoginHandler {
	
	@SubscribeEvent
	public void onPlayerLoggedIn(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		if((Boolean) DataGetter.find("modToggle")) {
			   Thread playerLogin = new LoginThread();
			   if((DataGetter.find("APIKey").toString().equals("NOT_SET")) || DataGetter.find("APIKey") == null) {LoginThread.goodApiKey = false;}
			   else LoginThread.goodApiKey = true;
			   //Utils.print(LoginThread.goodApiKey);
			   playerLogin.start();
		}
	}
}
