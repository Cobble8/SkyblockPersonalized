package com.cobble.sbp.gui.screen.dwarven;

import com.cobble.sbp.SBP;
import com.cobble.sbp.events.user.MenuClickEvent;
import com.cobble.sbp.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;

public class ChatOpenGui extends Gui {

    private static boolean mouseDown = false;
    private static boolean sKeyDown = false;

    public ChatOpenGui() {
        if(!SBP.sbLocation.equals("crystalhollows")) {return;}

        Minecraft mc = Minecraft.getMinecraft();
        GuiScreen guiScreen = mc.currentScreen;

        if(guiScreen instanceof GuiChat) {




            if(CrystalHollowsMap.mapToggle) {
                float scale = CrystalHollowsMap.scale;
                int mouseX = MenuClickEvent.mouseX;
                int mouseY = MenuClickEvent.mouseY;
                int tmpX = (int) ((CrystalHollowsMap.mapX-1)*scale);
                int tmpY = (int) ((CrystalHollowsMap.mapY-1)*scale);
                if(mouseX > tmpX && mouseX <= tmpX+(100*scale) && mouseY > tmpY && mouseY <= tmpY+(100*scale)) {
                    if(Keyboard.isKeyDown(Keyboard.KEY_R)) {
                        CrystalHollowsMap.resetLocs();
                        CrystalHollowsMap.waypoints.clear();
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
                        GlStateManager.translate(0, 0, 0.04d);
                        GlStateManager.scale(scale, scale, scale);
                        CrystalHollowsMap.drawEpicCenteredString(xCoord+", "+yCoord, Colors.AQUA, Math.round(CrystalHollowsMap.mapX+(50)), Math.round(CrystalHollowsMap.mapY+(108)));
                        GlStateManager.popMatrix();
                        int xPos = (int) ((mouseX-(CrystalHollowsMap.mapX*scale))/2/scale);
                        int yPos = (int) ((mouseY-(CrystalHollowsMap.mapY*scale))/2/scale);
                        int id = CrystalHollowsMap.locs[xPos][yPos];
                        for(ArrayList<Object> wp : CrystalHollowsMap.waypoints) {
                            int x1 = (int) (((int) (wp.get(1))*scale) + (CrystalHollowsMap.mapX*scale)-(3*scale));
                            int x2 = (int) (x1+(9*scale));
                            int y1 = (int) (((int) (wp.get(2))*scale) + (CrystalHollowsMap.mapY*scale)-(14*scale));
                            int y2 = (int) (y1+(17*scale));

                            if(mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2) {

                                int wpXPos = (int) wp.get(3);
                                int wpYPos = (int) wp.get(4);
                                int wpZPos = (int) wp.get(5);
                                int wpY = (int) (((int) wp.get(2))/scale);
                                wpY+=(CrystalHollowsMap.mapY/scale);
                                int locX = ((int) wp.get(1))/2; int locY = ((int) wp.get(2))/2;
                                if(locX > 49) {locX = 49;} if(locY > 49) {locY = 49;} if(locX < 0) {locX = 0;} if(locY < 0) {locY = 0;}
                                int locID = CrystalHollowsMap.locs[locX][locY];
                                if(locID == -1) { if(locX > 21 && locX < 29 && locY > 21 && locY < 29) { locID = 0; } else if(locX <= 25 && locY <= 25 ) { locID = 3; } else if(locX > 25 && locY <= 25) { locID = 5; } else if(locX <= 25) { locID = 1; } else { locID = 6; }}
                                GlStateManager.pushMatrix();
                                GlStateManager.translate(0, 0, 5d);
                                String name = wp.get(0).toString();
                                locID = CrystalHollowsMap.locs[locX][locY];
                                if(name.equals("loc")) { name = CrystalHollowsMap.locName(locID, (int) (wp.get(1))/2, (int) (wp.get(2))/2)[0]; }
                                String coords = CrystalHollowsMap.textColor.replace("&", Reference.COLOR_CODE_CHAR+"");
                                int offset = Math.max(mc.fontRendererObj.getStringWidth(coords)/2, mc.fontRendererObj.getStringWidth(name)/2);

                                if(wpYPos != -1) { coords+="X: "+wpXPos+", Y: "+wpYPos+", Z: "+wpZPos; } else { coords+="X: "+wpXPos+", Z: "+wpZPos; }
                                GuiUtils.drawString(new String[]{name, coords}, mouseX-offset, (int) ((wpY+6)*scale), 4);

                                if(!sKeyDown) {

                                    if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
                                        sKeyDown = true;
                                        Minecraft.getMinecraft().thePlayer.sendChatMessage(TextUtils.unformatAllText(name+": "+coords));
                                    }
                                } else { sKeyDown = Keyboard.isKeyDown(Keyboard.KEY_S); }
                                GlStateManager.popMatrix();
                                markerHover=true;
                                break;
                            }

                        }

                        if(id >= 0 && !markerHover) {
                            mc.getTextureManager().bindTexture(Resources.blank);



                            for(int i=0;i<50;i++) {
                                for(int j=0;j<50;j++) {
                                    //GlStateManager.pushMatrix();
                                    //GlStateManager.scale(scale, scale, scale);
                                    int x = (int) ((CrystalHollowsMap.mapX*scale)+(i*2*scale))+1;
                                    int y = (int) ((CrystalHollowsMap.mapY*scale)+(j*2*scale))+1;
                                    if(x < CrystalHollowsMap.mapX || y < CrystalHollowsMap.mapY) {continue;}
                                    //x, y, u, v, drawnWidth, drawnHeight, textureWidth, textureHeight
                                    if(CrystalHollowsMap.locs[i][j] == id) {
                                        GlStateManager.pushMatrix();
                                        x+=(scale-1);
                                        x/=scale;

                                        y/=scale;
                                        GlStateManager.scale(scale, scale, scale);
                                        GlStateManager.translate(0, 0, 0.02);
                                        GlStateManager.color(0, 0, 0, 0.8f);
                                        drawModalRectWithCustomSizedTexture(x, y, 0, 0, 2, 2, 1, 1);
                                        CrystalHollowsMap.locColor(id, x, y-1);
                                        GlStateManager.popMatrix();
                                    }
                                    //GlStateManager.popMatrix();
                                }
                            }
                        }
                    } catch(Exception ignored) { }
                    Colors.resetColor();
                    GlStateManager.pushMatrix();
                    GlStateManager.translate(0, 0, 0.04);

                    int textWidth = mc.fontRendererObj.getStringWidth(text[0]+"");
                    int textX = mouseX-(textWidth/2);
                    if(textX < 5) {textX = 5;}
                    if(textX+textWidth > SBP.width-5) {
                        textX = SBP.width-5-textWidth;
                    }
                    int textY = mouseY+14;
                    if(textY+(text.length*12) > CrystalHollowsMap.mapX+(CrystalHollowsMap.scale*100)) {
                        textY-=(text.length*12)+14;
                        if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1)) {
                            textY-=24;
                        }
                    }
                    try {
                        if(!markerHover) {

                            GuiUtils.drawString(text, textX, textY, 4, false);
                        }
                    } catch(Exception ignored) { }
                    GlStateManager.popMatrix();
                    CrystalHollowsMap.hovering=true;
                } else {
                    CrystalHollowsMap.hovering=false;
                }

            } else {
                CrystalHollowsMap.hovering=false;
            }
        }
    }


    private static void chatMouseDown() {
        Minecraft mc = Minecraft.getMinecraft();
        int mouseX = MenuClickEvent.mouseX;
        int mouseY = MenuClickEvent.mouseY;
        float scale = CrystalHollowsMap.scale;

        mc.getTextureManager().bindTexture(Resources.marker);
        Colors.resetColor();
        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 0.03);
        GlStateManager.scale(scale, scale, scale);
        GlStateManager.color(0, 0, 1);

        double xOff = 8d*scale;
        double zOff = 16d*scale;
        int x = (int) ((mouseX-xOff)/scale);
        int y = (int) ((mouseY-zOff)/scale);

        for(ArrayList<Object> wp : CrystalHollowsMap.waypoints) {
            int x1 = (int) (((int) (wp.get(1))*scale) + (CrystalHollowsMap.mapX*scale)-(3*scale));
            int x2 = (int) (x1+(9*scale));
            int y1 = (int) (((int) (wp.get(2))*scale) + (CrystalHollowsMap.mapY*scale)-(14*scale));
            int y2 = (int) (y1+(17*scale));

            if(mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(0, 0, 1.1d);
                mc.getTextureManager().bindTexture(Resources.blank);
                GlStateManager.color(1,0,0,0.5f);
                drawModalRectWithCustomSizedTexture((int) ((x1-1)/scale), (int) ((y1-1)/scale), 0, 0, 10, 18, 1, 1);
                GlStateManager.popMatrix();
                GlStateManager.popMatrix();
                return;
            }

        }


        int locID = 0;
        int mX = (int) ((mouseX-(CrystalHollowsMap.mapX*scale))/2/scale);
        int mY = (int) ((mouseY-(CrystalHollowsMap.mapY*scale))/2/scale);
        try {
            locID = CrystalHollowsMap.locs[mX][mY];
            if(locID == -1) {
                if(mX > 21 && mX < 29 && mY > 21 && mY < 29) { locID = 0; }
                else if(mX < 25 && mY <25) { locID = 3;
                } else if(mX >= 25 && mY < 25) { locID = 5;
                } else if(mX >= 25) { locID = 6;
                } else { locID = 1; }
            }
        } catch(Exception ignored) { }
        drawModalRectWithCustomSizedTexture(x, y, 0, 0, 16, 16, 192, 16);
        GlStateManager.color(1, 1, 1);
        drawModalRectWithCustomSizedTexture(x, y, locID*16, 16, 16, 16, 192, 16);
        GlStateManager.popMatrix();
    }

    private static void chatMouseUpEvent() {
        int mouseX = MenuClickEvent.mouseX;
        int mouseY = MenuClickEvent.mouseY;
        float scale = CrystalHollowsMap.scale;
        Minecraft mc = Minecraft.getMinecraft();



        for(int i=0;i<CrystalHollowsMap.waypoints.size();i++) {

            //Remove Waypoint on Click
            ArrayList<Object> wp = CrystalHollowsMap.waypoints.get(i);
            int x1 = (int) (((int) (wp.get(1))*scale) + (CrystalHollowsMap.mapX*scale)-(3*scale));
            int x2 = (int) (x1+(9*scale));
            int y1 = (int) (((int) (wp.get(2))*scale) + (CrystalHollowsMap.mapY*scale)-(14*scale));
            int y2 = (int) (y1+(17*scale));

            if(mouseX >= x1 && mouseX <= x2 && mouseY >= y1 && mouseY <= y2) {
                CrystalHollowsMap.waypoints.remove(i);
                return;
            }

        }



        //Add a new waypoint if it didn't previously remove one.
        String name = "loc";
        double mX = (int) ((mouseX-(CrystalHollowsMap.mapX*scale))/scale); mX*=621d; mX/=100d; mX+=202;
        double mY = (int) ((mouseY-(CrystalHollowsMap.mapY*scale))/scale); mY*=621d; mY/=100d; mY+=202;
        WorldUtils.addCrystalWaypoint(name, (int) mX, -1, (int) mY);
    }

}
