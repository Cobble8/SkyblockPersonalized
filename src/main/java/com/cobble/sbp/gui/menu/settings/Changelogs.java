package com.cobble.sbp.gui.menu.settings;


import com.cobble.sbp.simplejson.JSONArray;
import com.cobble.sbp.simplejson.JSONObject;
import com.cobble.sbp.simplejson.parser.JSONParser;
import com.cobble.sbp.utils.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;
import java.util.*;

public class Changelogs extends GuiScreen {

    private static int yOffset = 0;
    private static int mouseDWheel = 0;

    private static HashMap<String, ArrayList<String>> changelogs = new HashMap<>();
    private static ArrayList<String> versions = new ArrayList<>();

    @Override
    public void initGui() {
        yOffset = 0;

        if(changelogs.size() == 0) {
            loadChangelog();
        }

        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if(mouseDWheel != 0) {if(mouseDWheel > 0) {yOffset-=12;} else {yOffset+=12;}} mouseDWheel = Mouse.getDWheel();
        if(yOffset < 0) {yOffset = 0;}
        this.drawDefaultBackground();
        if(changelogs.size() == 0) {
            GuiUtils.drawString(Colors.YELLOW+"Loading Changelogs...", 8, 10);
        } else {
            int i=0;
            for(String vers : versions) {
                GuiUtils.drawString(Colors.YELLOW+vers, 8, 8+(12*i)-yOffset);
                ArrayList<String> changes = changelogs.get(vers);
                for(String change : changes) {
                    i++;
                    mc.fontRendererObj.drawString(Colors.AQUA+change.replace("\"",""), 10, 8+(12*i)-yOffset, 0);
                }
                i++;
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {


        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char par1, int par2) throws IOException {
        if(par2 == Keyboard.KEY_ESCAPE) {
            changelogs.clear();
            versions.clear();
            mc.displayGuiScreen(new SettingMenu());
        } else {
            super.keyTyped(par1, par2);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private static void loadChangelog() {
        HashMap<String, ArrayList<String>> hash = new HashMap<>();
        Thread t1 = new Thread(() -> {
            try {
                String content = WebUtils.readPage("https://raw.githubusercontent.com/Cobble8/SkyblockPersonalized/main/versioncheck.json", true);
                JSONObject obj = (JSONObject) new JSONParser().parse(content);
                JSONArray cl = (JSONArray) obj.get("changelog");
                for(int i=0;i<cl.size();i++) {
                    JsonArray obj2 = new JsonParser().parse(cl.toString()).getAsJsonArray();
                    for(int j=0;j<obj2.size();j++) {
                        JsonObject tmp = obj2.get(j).getAsJsonObject();
                        Set<Map.Entry<String, JsonElement>> entries = tmp.entrySet();
                        for(Map.Entry<String, JsonElement> entry : entries) {
                            ArrayList<String> currChangeLog = new ArrayList<>();
                            JsonArray arr = (JsonArray) tmp.get(entry.getKey());
                            for(int k=0;k<arr.size();k++) {
                                currChangeLog.add(arr.get(k).toString());
                            }
                            hash.put(entry.getKey(), currChangeLog);
                            versions.add(entry.getKey());

                        }
                    }
                }
            } catch (Exception e) {
                TextUtils.sendErrMsg("An error has occured while loading the changelogs!");
                changelogs.clear();
                Minecraft.getMinecraft().displayGuiScreen(new SettingMenu());
                Utils.printDev(e);
            }


            Collections.sort(versions);
            Collections.reverse(versions);
            changelogs = hash;
        });
        t1.start();


    }

}
