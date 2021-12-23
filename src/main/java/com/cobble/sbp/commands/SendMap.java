package com.cobble.sbp.commands;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cobble.sbp.SBP;
import com.cobble.sbp.gui.screen.dwarven.CrystalHollowsMap;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.HttpClient;
import com.cobble.sbp.utils.Utils;
import com.cobble.sbp.utils.WaypointUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class SendMap extends CommandBase {
    ArrayList<String> aliases;
    public SendMap() {
        aliases = new ArrayList<>();
    }

    @Override
    public String getCommandName() {
        return "sendmap";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Usage: /sendmap";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        try {
            if(!SBP.sbLocation.equals("crystalhollows")) { Utils.sendErrMsg("You must be in the "+ Colors.LIGHT_PURPLE+"Crystal Hollows"+Colors.YELLOW+" to use this command!"); return; }









            try {
                Utils.sendMessage("Generating map...");
                new GenerateMap().start();
            } catch(Exception e) {e.printStackTrace();}


        } catch(Exception ignored) {}
    }



    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

}

class GenerateMap extends Thread {
    public void run() {


        try {
            String res = WaypointUtils.compress(CrystalHollowsMap.locs, 40);

            //System.out.println(Arrays.toString(CrystalHollowsMap.locs));
            for(int[] arr : CrystalHollowsMap.locs) { System.out.print(Arrays.toString(arr)); }
            System.out.println();
            /*int[][] decompTest = WaypointUtils.decompress(res, 40, 50);
            for(int[] arr : decompTest) { System.out.print(Arrays.toString(arr)); }
            System.out.println();/
            WaypointUtils.importMap(decompTest);*/
            //System.out.println(res);
            String key = WaypointUtils.post(res, true);
            key = key.substring(key.indexOf("/raw/")+5);
            key = "$sbpMap-"+key;

            //String drac = "'*+*'W+,'V+,'W+-'V+.'U+3'P+3'Q+2'W+,'W+,'V+,'W++';**'B+,'1),',*1'=++'0)4*/'<+,'0)4*.'=++'0)3**'**+'=+-'-)1'**+'+*+'=+-',)1'**+')*-'=+.))')))())1*2'<+*(*+,))(+))'))-')*1'=+)(,++')))(+'0*.'@+)(+++)*(+'1*)'E(,+)')))(,'P(1'R(/.-'N(../'M(/.-'M(.',.+'M(+'/.+'L(,'/.-')..'B(,'0.5'B(*-)'0.6'@-,'0.1'*.,'?-+0)'1.,').+'+.,'<0)-,0*'1.0'+.,':0*-+0+'2./',.,'90+-)0+'3./'-.,'90-'4..'/.+'90-'4..'0.*':0+'6.-'..+'M.+'..-'T..'U.-'Ɔ";

            Minecraft.getMinecraft().thePlayer.sendChatMessage(key);

        } catch (Exception e) { e.printStackTrace(); }

    }
}
