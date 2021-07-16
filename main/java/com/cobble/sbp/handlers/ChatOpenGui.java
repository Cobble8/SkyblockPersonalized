package com.cobble.sbp.handlers;

import com.cobble.sbp.SBP;
import com.cobble.sbp.events.user.MenuClickEvent;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.gui.screen.dwarven.CrystalHollowsMap;
import com.cobble.sbp.utils.ColorUtils;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

public class ChatOpenGui extends Gui {

    private static boolean mouseDown = false;


    public ChatOpenGui() {
        if(!SBP.sbLocation.equals("crystalhollows")) {return;}

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        GuiScreen guiScreen = mc.currentScreen;

        if(guiScreen instanceof GuiChat) {






            float scale = CrystalHollowsMap.scale;
            int mouseX = MenuClickEvent.mouseX;
            int mouseY = MenuClickEvent.mouseY;
            if(mouseX > CrystalHollowsMap.mapX-1 && mouseX <= CrystalHollowsMap.mapX-1+(100*scale) && mouseY > CrystalHollowsMap.mapY-1 && mouseY <= CrystalHollowsMap.mapY-1+(100*scale)) {

                if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
                    CrystalHollowsMap.resetLocs();
                }



                if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
                    mouseDown = true;
                    chatMouseDown();
                } else {
                    if(mouseDown) {
                        chatMouseUpEvent();
                        mouseDown=false;
                    }
                }

                boolean markerHover = false;
                Object[] text = CrystalHollowsMap.getLocName(mouseX, mouseY);
                try {
                    int xCoord = (int) (((mouseX-CrystalHollowsMap.mapX)*CrystalHollowsMap.width/100/scale)+CrystalHollowsMap.xOff);
                    int yCoord = (int) (((mouseY-CrystalHollowsMap.mapY)*CrystalHollowsMap.height/100/scale)+CrystalHollowsMap.zOff);
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(0, 0, 100);
                    CrystalHollowsMap.drawEpicCenteredString(xCoord+" "+yCoord, Colors.AQUA, Math.round(CrystalHollowsMap.mapX+(50*CrystalHollowsMap.scale)), Math.round(CrystalHollowsMap.mapY+(105*CrystalHollowsMap.scale)));
                    GlStateManager.popMatrix();
                    int xPos = (int) ((mouseX-CrystalHollowsMap.mapX)/2/scale);
                    int yPos = (int) ((mouseY-CrystalHollowsMap.mapY)/2/scale);
                    int id = CrystalHollowsMap.locs[xPos][yPos];



                    for(int i=0;i<CrystalHollowsMap.markers.size();i++) {
                        try {
                            int markX = CrystalHollowsMap.markers.get(i).get(0);
                            int markY = CrystalHollowsMap.markers.get(i).get(1);
                            if(mouseX > (markX+3)*scale && mouseX <= (markX+12)*scale && mouseY > (markY)*scale && mouseY <= (markY+16)*scale) {

                                GlStateManager.pushMatrix();
                                GlStateManager.translate(0, 0, 200);
                                GlStateManager.scale(scale, scale, scale);
                                mc.getTextureManager().bindTexture(CrystalHollowsMap.marker);
                                GlStateManager.color(0, 0, 0, 1);
                                drawModalRectWithCustomSizedTexture(markX, markY, 0, 0, 16, 16, 16, 32);
                                drawModalRectWithCustomSizedTexture(markX, markY, 0, 16, 16, 16, 16, 32);
                                GlStateManager.color(0.5f, 0.5f, 1);
                                drawModalRectWithCustomSizedTexture(markX, markY-1, 0, 0, 16, 16, 16, 32);
                                ColorUtils.resetColor();
                                drawModalRectWithCustomSizedTexture(markX, markY-1, 0, 16, 16, 16, 16, 32);
                                GlStateManager.popMatrix();
                                markerHover=true;
                                text = CrystalHollowsMap.getLocName(markX+7, markY+16);
                                int textWidth = mc.fontRendererObj.getStringWidth(text[0]+"");
                                int textX = mouseX-(textWidth/2);
                                if(textX < 5) {textX = 5;}
                                if(textX+textWidth > SBP.width-5) { textX = SBP.width-5-textWidth; }
                                GlStateManager.pushMatrix();
                                GlStateManager.translate(0, 0, 100);
                                Utils.drawString(text, textX, mouseY+14, 4, false);
                                GlStateManager.popMatrix();

                            }

                        } catch(Exception ignored) {}
                    }


                    if(id >= 0 && !markerHover) {
                        mc.getTextureManager().bindTexture(SettingMenu.blank);



                        for(int i=0;i<50;i++) {
                            for(int j=0;j<50;j++) {
                                //GlStateManager.pushMatrix();
                                //GlStateManager.scale(scale, scale, scale);
                                int x = (int) (CrystalHollowsMap.mapX+(i*2*scale));
                                int y = (int) (CrystalHollowsMap.mapY+(j*2*scale));
                                if(x < CrystalHollowsMap.mapX || y < CrystalHollowsMap.mapY) {continue;}
                                //x, y, u, v, drawnWidth, drawnHeight, textureWidth, textureHeight
                                if(CrystalHollowsMap.locs[i][j] == id) {
                                    GlStateManager.pushMatrix();
                                    x+=(scale-1);
                                    x/=scale;

                                    y/=scale;
                                    GlStateManager.scale(scale, scale, scale);
                                    GlStateManager.translate(0, 0, 2);
                                    GlStateManager.color(0, 0, 0, 0.8f);
                                    drawModalRectWithCustomSizedTexture(x, y, 0, 0, 2, 2, 1, 1);
                                    CrystalHollowsMap.locColor(id);
                                    drawModalRectWithCustomSizedTexture(x, y-1, 0, 0, 2, 2, 1, 1);
                                    GlStateManager.popMatrix();
                                }
                                //GlStateManager.popMatrix();
                            }
                        }
                    }
                } catch(Exception ignored) { }
                ColorUtils.resetColor();
                GlStateManager.pushMatrix();
                GlStateManager.translate(0, 0, 100);

                int textWidth = mc.fontRendererObj.getStringWidth(text[0]+"");
                int textX = mouseX-(textWidth/2);
                if(textX < 5) {textX = 5;}
                if(textX+textWidth > SBP.width-5) {
                    textX = SBP.width-5-textWidth;
                }

                try {
                    if(!markerHover) {
                        Utils.drawString(text, textX, mouseY+14, 4, false);
                    } else {

                    }

                } catch(Exception ignored) { }

                GlStateManager.popMatrix();
            }

        }


    }


    private static void chatMouseDown() {
        Minecraft mc = Minecraft.getMinecraft();
        int mouseX = MenuClickEvent.mouseX;
        int mouseY = MenuClickEvent.mouseY;
        float scale = CrystalHollowsMap.scale;
        for(int i=0;i<CrystalHollowsMap.markers.size();i++) {
            try {
                int markX = CrystalHollowsMap.markers.get(i).get(0);
                int markY = CrystalHollowsMap.markers.get(i).get(1);

                if(mouseX > markX*scale && mouseX <= (markX+16)*scale && mouseY > markY*scale && mouseY <= (markY+16)*scale) {
                    return;
                }
            } catch(Exception ignored) {}
        }

        mc.getTextureManager().bindTexture(CrystalHollowsMap.marker);
        ColorUtils.resetColor();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 1000);
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.color(0, 0, 1);
        drawModalRectWithCustomSizedTexture(Math.round((mouseX-11)/scale), Math.round((mouseY-16)/scale), 0, 0, 16, 16, 16, 32);
        GlStateManager.color(1, 1, 1);
        drawModalRectWithCustomSizedTexture(Math.round((mouseX-11)/scale), Math.round((mouseY-16)/scale), 0, 16, 16, 16, 16, 32);
        GlStateManager.popMatrix();
    }

    private static void chatMouseUpEvent() {
        int mouseX = MenuClickEvent.mouseX;
        int mouseY = MenuClickEvent.mouseY;
        float scale = CrystalHollowsMap.scale;
        for(int i=0;i<CrystalHollowsMap.markers.size();i++) {
            try {
                int markX = CrystalHollowsMap.markers.get(i).get(0);
                int markY = CrystalHollowsMap.markers.get(i).get(1);

                if(mouseX > (markX*scale) && mouseX <= (markX+16)*scale && mouseY > markY*scale && mouseY <= (markY+16)*scale) {
                    CrystalHollowsMap.markers.remove(i);
                    return;
                }
            } catch(Exception ignored) {}
        }



        int xCoord = ((mouseX-CrystalHollowsMap.mapX)*CrystalHollowsMap.width/100)+CrystalHollowsMap.xOff;
        int yCoord = ((mouseY-CrystalHollowsMap.mapY)*CrystalHollowsMap.height/100)+CrystalHollowsMap.zOff;
        ArrayList<Integer> coords = new ArrayList<>();
        coords.add((int) ((mouseX-11)/scale));
        coords.add((int) ((mouseY-16)/scale));
        coords.add(xCoord);
        coords.add(yCoord);
        CrystalHollowsMap.markers.add(coords);
    }

}
