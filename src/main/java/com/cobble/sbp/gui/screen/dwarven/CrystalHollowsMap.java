package com.cobble.sbp.gui.screen.dwarven;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.user.MenuClickEvent;
import com.cobble.sbp.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;


public class CrystalHollowsMap extends Gui {



    public static int[][] locs;

    public static int mapX = DataGetter.findInt("dwarven.crystalMap.x");
    public static int mapY = DataGetter.findInt("dwarven.crystalMap.y");
    public static boolean mapToggle = DataGetter.findBool("dwarven.crystalMap.toggle");
    public static float scale = DataGetter.findInt("dwarven.crystalMap.size")/10f;
    public static boolean showHead = DataGetter.findBool("dwarven.crystalMap.headToggle");
    public static boolean coords = DataGetter.findBool("dwarven.crystalMap.coordsToggle");
    public static boolean hovering = false;
    public static int xOff = 202;
    public static int zOff = 202;
    public static int width = 621;
    public static int height = 621;
    //public static ArrayList<ArrayList<Object>> markers = new ArrayList<>();
    public static ArrayList<ArrayList<Object>> waypoints = new ArrayList<>();
    public static ResourceLocation head = null;
    public static boolean foundSkin = false;
    public static String textColor = DataGetter.findStr("dwarven.crystalMap.textColor");


    public CrystalHollowsMap() {
        mapToggle = DataGetter.findBool("dwarven.crystalMap.toggle");
        if(!mapToggle) {return;}
        textColor = DataGetter.findStr("dwarven.crystalMap.textColor");
        mapX = DataGetter.findInt("dwarven.crystalMap.x");
        mapY = DataGetter.findInt("dwarven.crystalMap.y");
        showHead = DataGetter.findBool("dwarven.crystalMap.headToggle");
        coords = DataGetter.findBool("dwarven.crystalMap.coordsToggle");
        scale = DataGetter.findInt("dwarven.crystalMap.size")/10f;




        String clr = Colors.textColor(textColor);

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if(showHead) {
            try {
                if(!foundSkin) {
                    if(player.hasSkin()) {
                        head = mc.getNetHandler().getPlayerInfo(player.getUniqueID()).getLocationSkin();
                        foundSkin = true;
                    } else {
                        head = new ResourceLocation("minecraft:textures/entity/steve.png");
                    }

                }
            } catch(Exception ignored) {}
        }

        double plyrX = (player.posX-xOff)*100d/width;
        double plyrZ = (player.posZ-zOff)*100d/height;

        float angle = player.rotationYawHead-180;

        GlStateManager.pushMatrix();
        GL11.glScalef(scale, scale, scale);
        mc.getTextureManager().bindTexture(Resources.map);
        mapX = Math.round(mapX/scale);
        mapY = Math.round(mapY/scale);
        int finX = (int) Math.round(plyrX)+mapX;
        int finZ = (int) Math.round(plyrZ)+mapY;

        Colors.resetColor();

        drawModalRectWithCustomSizedTexture(mapX, mapY, 0, 0, 100, 100, 100, 200);

        manageLoc();
        if(locs.length == 0) { resetLocs(); }
        mc.getTextureManager().bindTexture(Resources.blank);

        for(int i=0;i<50;i++) {
            for(int j=0;j<50;j++) {
                locColor(locs[i][j], mapX+(2*i), mapY+(2*j));
            }
        }
        Colors.resetColor();

        GlStateManager.pushMatrix();
        mc.getTextureManager().bindTexture(Resources.map);
        GlStateManager.translate(0, 0, 0.01);
        drawModalRectWithCustomSizedTexture(mapX, mapY, 0, 100, 100, 100, 100, 200);

        for(ArrayList<Object> wp : waypoints) {
            try {
                String name = (String) wp.get(0);
                name = TextUtils.unformatAllText(name);
                int wpX = (int) wp.get(1)+(mapX);
                int wpY = (int) wp.get(2)+(mapY);
                int locX = ((int) wp.get(1))/2;
                int locY = ((int) wp.get(2))/2;
                if(locX > 49) {locX = 49;} if(locY > 49) {locY = 49;} if(locX < 0) {locX = 0;} if(locY < 0) {locY = 0;}
                int locID = locs[locX][locY];
                if(name.equals("loc")) { name = locName(locID, ((int) wp.get(1))/2, ((int) wp.get(2))/2)[0]; }



                if(locID == -1) { if(locX > 21 && locX < 29 && locY > 21 && locY < 29) { locID = 0; } else if(locX < 25 && locY < 25 ) { locID = 3; } else if(locX > 24 && locY <= 25) { locID = 5; } else if(locX <= 24) { locID = 1; } else { locID = 6; }}

                mc.getTextureManager().bindTexture(Resources.marker);
                Colors.resetColor();
                drawModalRectWithCustomSizedTexture(wpX-7, wpY-14, 16*locID, 0, 16, 16, 192, 16);
                if(mc.currentScreen instanceof GuiChat) {

                    int mX = (int) (MenuClickEvent.mouseX/scale);
                    int mY = (int) (MenuClickEvent.mouseY/scale);
                    if(mX > wpX-7+2 && mX < wpX-7+16-2 && mY > wpY-14 && mY < wpY-14+16) {
                        //TextUtils.sendMessage(Colors.GREEN+mX+", "+mY);
                        GlStateManager.color(0,0,0,1);
                        drawModalRectWithCustomSizedTexture(wpX-7, wpY-14, 16*locID, 0, 16, 16, 192, 16);
                        Colors.resetColor();
                        drawModalRectWithCustomSizedTexture(wpX-7, wpY-15, 16*locID, 0, 16, 16, 192, 16);
                    } else {
                        //TextUtils.sendMessage(Colors.RED+mX+", "+mY);
                    }
                    //TextUtils.sendMessage(wpX+", "+wpY);
                }
                GlStateManager.pushMatrix();
                GlStateManager.translate(0,0,1.2);
                GuiUtils.drawBoldScaledString(name, wpX+3, wpY-6,0.5d, 2);
                GlStateManager.popMatrix();
            } catch(Exception ignored) {}
        }

        GlStateManager.popMatrix();






        GlStateManager.pushMatrix();

        try {
            if(showHead) {
                GlStateManager.translate(finX-1, finZ-1, 0);
                GlStateManager.rotate(angle, 0, 0, 1);
                GlStateManager.translate(-finX+1, -finZ+1, 0);
                GlStateManager.translate(0, 0, 0.03);
                GlStateManager.color(0,0,0,1);
                mc.getTextureManager().bindTexture(Resources.blank);
                drawModalRectWithCustomSizedTexture(finX-6, finZ-6, 0, 0, 10, 10, 1, 1);
                Colors.resetColor();
                mc.getTextureManager().bindTexture(head);
                drawModalRectWithCustomSizedTexture(finX-5, finZ-5, 8, 8, 8, 8, 64, 64);
            }
            else {
                GlStateManager.translate(finX+1, finZ+1, 0);
                GlStateManager.rotate(angle, 0, 0, 1);
                GlStateManager.translate(-finX-1, -finZ-1, 0);
                GlStateManager.translate(0, 0, 0.03);
                mc.getTextureManager().bindTexture(Resources.icons);
                //System.out.println(icons.getResourcePath());
                Colors.setColor(DataGetter.findStr("dwarven.crystalMap.arrowColor"));
                drawModalRectWithCustomSizedTexture(finX-6, finZ-6, 0, 0, 12, 12, 48, 48);
            }
        } catch(Exception ignored) {}

        GlStateManager.popMatrix();


        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 0.02);
        drawEpicCenteredString("N", clr, mapX+50, mapY-4);
        drawEpicCenteredString("E", clr, mapX+101, mapY-3+50);
        drawEpicCenteredString("S", clr, mapX+50, mapY-3+100);
        drawEpicCenteredString("W", clr, mapX, mapY-3+50);

        GlStateManager.popMatrix();


        if(!hovering && coords) {
            int xCoord = (int) Math.floor(mc.thePlayer.posX);
            int yCoord = (int) Math.floor(mc.thePlayer.posY);
            int zCoord = (int) Math.floor(mc.thePlayer.posZ);
            CrystalHollowsMap.drawEpicCenteredString(xCoord+" "+yCoord+" "+zCoord, clr, (int) ((CrystalHollowsMap.mapX+(50f))), (int) ((CrystalHollowsMap.mapY+(108f))));
        }
        GlStateManager.popMatrix();
    }

    public static void drawEpicCenteredString(String string, String color, int x, int y) {
        try {

            int scndClr=0;
            Minecraft mc = Minecraft.getMinecraft();
            String bIU = TextUtils.unformatText(color);
            int offset = mc.fontRendererObj.getStringWidth(string)/2;
            mc.fontRendererObj.drawString(bIU+string, x-1-offset, y-1, scndClr, false);
            mc.fontRendererObj.drawString(bIU+string, x-1-offset, y+1, scndClr, false);
            mc.fontRendererObj.drawString(bIU+string, x+1-offset, y-1, scndClr, false);
            mc.fontRendererObj.drawString(bIU+string, x+1-offset, y+1, scndClr, false);
            mc.fontRendererObj.drawString(bIU+string, x-1-offset, y, scndClr, false);
            mc.fontRendererObj.drawString(bIU+string, x+1-offset, y, scndClr, false);
            mc.fontRendererObj.drawString(bIU+string, x-offset, y-1, scndClr, false);
            mc.fontRendererObj.drawString(bIU+string, x-offset, y+1, scndClr, false);
            mc.fontRendererObj.drawString(color+string, x-offset, y, 0, false);
        } catch(Exception ignored) {}

    }

    public static void resetLocs() {
        locs = new int[50][50];
        for(int i=0;i<50;i++) {
            for(int j=0;j<50;j++) {
                locs[i][j] = -1;
            }
        }
    }

    public static void locColor(int id, int x, int y) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(Resources.colors);
        if(id < 0) {return;}
        Colors.resetColor();
        drawModalRectWithCustomSizedTexture(x, y, id*2, 0, 2, 2, 24, 2);
    }

    private static int getLocID(String loc) {
        switch(loc) {
            case "precursor remnants":
            case "mithril deposits":
            case "jungle":
            case "goblin holdout":
            case "N/A":

                return 1;

            case "crystal nucleus": return 0;
            case "goblin queen's den": return 2;
            case "jungle temple": return 4;
            case "lost precursor city": return 7;
            case "mines of divan": return 8;
            case "khazaddm": return 9;
            case "fairy grotto": return 10;
            case "magma fields": return 11;
        }
        return 1;
    }

    private static int fixLocID(int id, int mapXPos, int mapZPos) {
        if(id == 1) {
            if(mapXPos < 25 && mapZPos < 25) { return 3;
            } else if(mapXPos >= 25 && mapZPos < 25) { return 5;
            } else if(mapXPos < 25) { return 1;
            } else { return 6; }
        } else {
            return id;
        }
    }

    private static void manageLoc() {

        double div = 621d/50d;
        try {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            int xPos = (int) Math.round((player.posX-202)/div);
            int zPos = (int) Math.round((player.posZ-202)/div);
            int id = fixLocID(getLocID(SBP.subLocation), xPos, zPos);



            setSpot(xPos, zPos, id);
            setSpot(xPos-1, zPos, id);
            setSpot(xPos+1, zPos, id);
            setSpot(xPos, zPos-1, id);
            setSpot(xPos, zPos+1, id);


        } catch(Exception ignored) { }

    }

    public static String[] getLocName(int mouseX, int mouseY) {
        try {
            int xPos = (int) ((mouseX-(mapX*scale))/2/scale);
            int yPos = (int) ((mouseY-(mapY*scale))/2/scale);
            int id = locs[xPos][yPos];
            return locName(id, xPos, yPos);
        } catch(Exception e) {
            return new String[]{"Location:", Colors.CHROMA+"Unknown"};
        }
    }

    public static String[] locName(int id, int xPos, int yPos) {
        switch(id) {
            case -2: return new String[]{"Unknown"};
            case -1:
                if(xPos > 20 && xPos <= 28 && yPos > 20 && yPos <= 28) {
                    return new String[]{Colors.LIGHT_PURPLE+"Undiscovered Nucleus"};
                }
                else if(xPos < 25 && yPos < 25) {
                    return new String[]{Colors.GREEN+"Undiscovered Jungle", Colors.AQUA+"Gem: "+Colors.DARK_PURPLE+"Amethyst", Colors.RED+"Mobs: "+Colors.GREEN+"Sludge"};
                } else if(xPos >= 25 && yPos < 25) {
                    return new String[]{Colors.YELLOW+"Undiscovered Mithril Deposits", Colors.AQUA+"Gem: "+Colors.GREEN+"Jade", Colors.RED+"Mobs: "+Colors.YELLOW+"Grunt, "+Colors.RED+"Boss Corleone"};
                } else if(xPos < 25) {
                    return new String[]{Colors.GOLD+"Undiscovered Goblin Holdout", Colors.AQUA+"Gem: "+Colors.GOLD+"Amber", Colors.RED+"Mobs: "+Colors.DARK_GREEN+"Goblin"};
                } else {
                    return new String[]{Colors.AQUA+"Undiscovered Precursor Remnants", Colors.AQUA+"Gem: "+Colors.BLUE+"Sapphire", Colors.RED+"Mobs: "+Colors.AQUA+"Automaton"};
                }



            case 0: return new String[]{Colors.LIGHT_PURPLE+"Crystal Nucleus"};
            case 1: return new String[]{Colors.GOLD+"Goblin Holdout", Colors.AQUA+"Gem: "+Colors.GOLD+"Amber", Colors.RED+"Mobs: "+Colors.DARK_GREEN+"Goblin"};
            case 2: return new String[]{Colors.DARK_PURPLE+"Goblin Queen's Den", Colors.WHITE+"Crystal: "+Colors.GOLD+"Amber"};
            case 3: return new String[]{Colors.GREEN+"Jungle", Colors.AQUA+"Gem: "+Colors.DARK_PURPLE+"Amethyst", Colors.RED+"Mobs: "+Colors.GREEN+"Sludge"};
            case 4: return new String[]{Colors.DARK_GREEN+"Jungle Temple", Colors.WHITE+"Crystal: "+Colors.DARK_PURPLE+"Amethyst"};
            case 5: return new String[]{Colors.YELLOW+"Mithril Deposits", Colors.AQUA+"Gem: "+Colors.GREEN+"Jade", Colors.RED+"Mobs: "+Colors.YELLOW+"Grunt, "+Colors.RED+"Boss Corleone"};
            case 6: return new String[]{Colors.AQUA+"Precursor Remnants", Colors.AQUA+"Gem: "+Colors.BLUE+"Sapphire", Colors.RED+"Mobs: "+Colors.AQUA+"Automaton"};
            case 7: return new String[]{Colors.BLUE+"Lost Precursor City", Colors.WHITE+"Crystal: "+Colors.BLUE+"Sapphire"};
            case 8: return new String[]{Colors.AQUA+"Mines of Divan", Colors.WHITE+"Crystal: "+Colors.DARK_GREEN+"Jade"};
            case 9: return new String[]{Colors.RED+"Khazad-dûm", Colors.WHITE+"Crystal: "+Colors.YELLOW+"Topaz", Colors.RED+"Mobs: "+Colors.RED+"Bal Boss"};
            case 10: return new String[]{Colors.LIGHT_PURPLE+"Fairy Grotto", Colors.AQUA+"Gem: "+Colors.LIGHT_PURPLE+"Jasper", Colors.RED+"Mobs: "+Colors.BLUE+"Butterfly"};
            case 11: return new String[]{Colors.RED+"Magma Fields", Colors.AQUA+"Gem: "+Colors.YELLOW+"Topaz", Colors.RED+"Mobs: "+Colors.DARK_RED+"Yog"};
        }
        return new String[]{"Location:", Colors.CHROMA+"Unknown"};
    }

    public static void setSpot(int x, int y, int id) {
        try {
            int old = locs[x][y];
            if(old == -1 || old == 11) {
                locs[x][y] = id;
            }
        } catch(Exception e) {
            //
        }

    }


}
