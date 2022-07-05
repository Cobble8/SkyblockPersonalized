package com.cobble.sbp.gui.screen.dwarven;

import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.simplejson.parser.JSONParser;
import com.cobble.sbp.utils.Resources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class NucleusEntrance extends Gui {


    public static int x = -1;
    public static int y = -1;
    public static int z = -1;

    HashMap<String, JSONObject> entrances = new HashMap<>();
    public NucleusEntrance() {
        if(!DataGetter.findBool("dwarven.nucleusEntrance.toggle")) {x = -1; return;}
        try {
            String closest = "";
            if(entrances.size() == 0) {
                InputStream in = Minecraft.getMinecraft().getResourceManager().getResource(Resources.nucleusLocations).getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder info = new StringBuilder();
                String currLine = reader.readLine();
                while(currLine != null) { info.append(currLine); currLine=reader.readLine(); }
                JSONObject obj = (JSONObject) new JSONParser().parse(info.toString());
                Object[] keylist = obj.keySet().toArray();

                for (Object o : keylist) {
                    JSONObject loc = (JSONObject) obj.get(o + "");
                    entrances.put(o+"", loc);
                }
            }
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            double dist = 800;
            for(String o : entrances.keySet()) {
                JSONObject loc = entrances.get(o);
                int x = Math.toIntExact((Long) loc.get("x"));
                int y = Math.toIntExact((Long) loc.get("y"));
                int z = Math.toIntExact((Long) loc.get("z"));
                int xDist = (int) Math.abs(player.posX - x);
                int yDist = (int) Math.abs(player.posY - y);
                int zDist = (int) Math.abs(player.posZ - z);
                int totalDist = xDist + yDist + zDist;
                if (totalDist < dist) {
                    dist = totalDist;
                    closest = o + "";
                }
            }

            JSONObject loc = entrances.get(closest);
            x = Math.toIntExact((Long) loc.get("x"));
            y = Math.toIntExact((Long) loc.get("y"));
            z = Math.toIntExact((Long) loc.get("z"));


        } catch(Exception ignored) { }

    }

}
