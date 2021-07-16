package com.cobble.sbp.gui.screen.dwarven;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.utils.ColorUtils;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Reference;
import com.cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;


public class CrystalHollowsMap extends Gui {


    private static final ResourceLocation map = new ResourceLocation(Reference.MODID, "textures/gui/crystal_map.png");
    private static final ResourceLocation icons = new ResourceLocation("minecraft:textures/map/map_icons.png");
    public static ResourceLocation marker = new ResourceLocation(Reference.MODID, "textures/gui/map_marker.png");
    public static int[][] locs;

    public static int mapX = DataGetter.findInt("crystalMapX");
    public static int mapY = DataGetter.findInt("crystalMapY");
    public static Boolean mapToggle = DataGetter.findBool("crystalMap");
    public static float scale = DataGetter.findInt("crystalMapSize")/10f;
    public static int xOff = 202;
    public static int zOff = 202;
    public static int width = 621;
    public static int height = 621;
    public static ArrayList<ArrayList<Integer>> markers = new ArrayList<>();


    public CrystalHollowsMap() {
        if(!mapToggle) {return;}
        int x = mapX;
        int y = mapY;
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        double plyrX = (player.posX-xOff)*100d/width;
        double plyrZ = (player.posZ-zOff)*100d/height;

        float angle = player.rotationYawHead-180;

        GlStateManager.pushMatrix();
        GL11.glScalef(scale, scale, scale);
        mc.getTextureManager().bindTexture(map);
        x = Math.round(x/scale);
        y = Math.round(y/scale);
        int finX = (int) Math.round(plyrX)+x;
        int finZ = (int) Math.round(plyrZ)+y;

        ColorUtils.resetColor();

        drawModalRectWithCustomSizedTexture(x, y, 0, 0, 100, 100, 100, 200);

        manageLoc();
        if(locs.length == 0) { resetLocs(); }
        mc.getTextureManager().bindTexture(SettingMenu.blank);

        for(int i=0;i<50;i++) {
            for(int j=0;j<50;j++) {
                locColor(locs[i][j]);


                drawModalRectWithCustomSizedTexture(x+(2*i), y+(2*j), 0, 0, 2, 2, 1, 1);
            }
        }
        ColorUtils.resetColor();

        GlStateManager.pushMatrix();
        mc.getTextureManager().bindTexture(map);
        GlStateManager.translate(0, 0, 1);
        drawModalRectWithCustomSizedTexture(x, y, 0, 100, 100, 100, 100, 200);
        for (ArrayList<Integer> integers : markers) {
            try {
                int markX = integers.get(0);
                int markY = integers.get(1);
                mc.getTextureManager().bindTexture(marker);
                GlStateManager.color(1, 0, 0);
                drawModalRectWithCustomSizedTexture(markX, markY, 0, 0, 16, 16, 16, 32);
                ColorUtils.resetColor();
                drawModalRectWithCustomSizedTexture(markX, markY, 0, 16, 16, 16, 16, 32);
            } catch (Exception ignored) { }
        }
        GlStateManager.popMatrix();



        mc.getTextureManager().bindTexture(icons);
        GlStateManager.pushMatrix();
        GlStateManager.translate(finX+1, finZ+1, 0);
        GlStateManager.rotate(angle, 0, 0, 1);
        GlStateManager.translate(-finX-1, -finZ-1, 0);
        GlStateManager.translate(0, 0, 10);
        drawModalRectWithCustomSizedTexture(finX-6, finZ-6, 24, 0, 12, 12, 48, 48);
        GlStateManager.translate(0, 0, -10);
        GlStateManager.popMatrix();


        GlStateManager.pushMatrix();
        GlStateManager.translate(0, 0, 2);
        drawEpicCenteredString("N", Colors.AQUA, x+50, y-4);
        drawEpicCenteredString("E", Colors.AQUA, x+101, y-3+50);
        drawEpicCenteredString("S", Colors.AQUA, x+50, y-3+100);
        drawEpicCenteredString("W", Colors.AQUA, x, y-3+50);
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    public static void drawEpicCenteredString(String string, String color, int x, int y) {
        try {

            int scndClr=0;
            Minecraft mc = Minecraft.getMinecraft();
            int offset = mc.fontRendererObj.getStringWidth(string)/2;
            mc.fontRendererObj.drawString(string, x-1-offset, y-1, scndClr, false);
            mc.fontRendererObj.drawString(string, x-1-offset, y+1, scndClr, false);
            mc.fontRendererObj.drawString(string, x+1-offset, y-1, scndClr, false);
            mc.fontRendererObj.drawString(string, x+1-offset, y+1, scndClr, false);
            mc.fontRendererObj.drawString(string, x-1-offset, y, scndClr, false);
            mc.fontRendererObj.drawString(string, x+1-offset, y, scndClr, false);
            mc.fontRendererObj.drawString(string, x-offset, y-1, scndClr, false);
            mc.fontRendererObj.drawString(string, x-offset, y+1, scndClr, false);
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

    public static void locColor(int id) {
        switch(id) {
            case -1: GlStateManager.color(0, 0, 0, 0); return; //Nothing
            case 0: GlStateManager.color(0.66f, 0, 0.66f, 1); return; //Nucleus
            case 1: GlStateManager.color(0.73f, 0.40f, 0, 1); return; //Goblin Holdout
            case 2: GlStateManager.color(1, 0.33f, 1, 1); return; //Goblin Queen's Den
            case 3: GlStateManager.color(0, 1, 0, 1); return; //Jungle
            case 4: GlStateManager.color(0, 0.33f, 0, 1); return; //Jungle Temple
            case 5: GlStateManager.color(1, 1, 0, 1); return; //Mithril Deposits
            case 6: GlStateManager.color(0.66f, 0.66f, 0.66f, 1); return; //Precursor Remnants
            case 7: GlStateManager.color(0.33f, 1, 1, 1); return; //Lost Precursor City
            case 8: GlStateManager.color(0.33f, 0.66f, 1, 1); return; //Mines of Divan
            case 9: GlStateManager.color(1, 0, 0, 1); return; //Khazad-dum
            case 10: GlStateManager.color(1, 0.66f, 1, 1); return; //Fairy Grotto
        }
    }

    private static int getLocID(String loc) {
        switch(loc) {
            case "crystal nucleus": return 0; //no
            case "goblin holdout": return 1; //no
            case "goblin queen's den": return 2; //yes - fhklmn
            case "jungle": return 3; //no
            case "jungle temple": return 4; //yes - h
            case "mithril deposits": return 5; //no
            case "precursor remnants": return 6; //no
            case "lost precursor city": return 7; //yes - k
            case "mines of divan": return 8; //yes - l
            case "khazaddm": return 9; //yes - m
            case "fairy grotto": return 10; //YES - n
            case "magma fields": return -3; //no duh

        }

        return -2;
    }

    private static void manageLoc() {

        double div = 621d/50d;
        try {
            EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
            int xPos = (int) Math.round((player.posX-202)/div);
            int zPos = (int) Math.round((player.posZ-202)/div);
            int id = getLocID(SBP.subLocation);
            if(id == -3) {return;}

            if(locs[xPos][zPos] == -9 ) {
                if(id != -9) {
                    return;
                }

            }
            locs[xPos][zPos] = id;
            try {
                locs[xPos-1][zPos] = id;
                locs[xPos+1][zPos] = id;
                locs[xPos][zPos-1] = id;
                locs[xPos][zPos+1] = id;
            } catch(Exception ignored) { }


        } catch(Exception e) { }

    }

    public static String[] getLocName(int mouseX, int mouseY) {
        try {
            int xPos = (int) ((mouseX-mapX)/2/scale);
            int yPos = (int) ((mouseY-mapY)/2/scale);
            int id = locs[xPos][yPos];
            //Utils.print(id);
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
                    } else if(xPos < 25 && yPos >= 25) {
                        return new String[]{Colors.GOLD+"Undiscovered Goblin Holdout", Colors.AQUA+"Gem: "+Colors.GOLD+"Amber", Colors.RED+"Mobs: "+Colors.DARK_GREEN+"Goblin"};
                    } else {
                        return new String[]{Colors.AQUA+"Undiscovered Precursor Remnants", Colors.AQUA+"Gem: "+Colors.BLUE+"Sapphire", Colors.RED+"Mobs: "+Colors.AQUA+"Automaton"};
                    }



                case 0: return new String[]{Colors.LIGHT_PURPLE+"Crystal Nucleus"};
                case 1: return new String[]{Colors.GOLD+"Goblin Holdout", Colors.AQUA+"Gem: "+Colors.GREEN+"Jade", Colors.RED+"Mobs: "+Colors.DARK_GREEN+"Goblin"};
                case 2: return new String[]{Colors.DARK_PURPLE+"Goblin Queen's Den", Colors.WHITE+"Crystal: "+Colors.GOLD+"Amber"};
                case 3: return new String[]{Colors.GREEN+"Jungle", Colors.AQUA+"Gem: "+Colors.DARK_PURPLE+"Amethyst", Colors.RED+"Mobs: "+Colors.GREEN+"Sludge"};
                case 4: return new String[]{Colors.DARK_GREEN+"Jungle Temple", Colors.WHITE+"Crystal: "+Colors.DARK_PURPLE+"Amethyst"};
                case 5: return new String[]{Colors.YELLOW+"Mithril Deposits", Colors.AQUA+"Gem: "+Colors.GOLD+"Amber", Colors.RED+"Mobs: "+Colors.YELLOW+"Grunt, "+Colors.RED+"Boss Corleone"};
                case 6: return new String[]{Colors.AQUA+"Precursor Remnants", Colors.AQUA+"Gem: "+Colors.BLUE+"Sapphire", Colors.RED+"Mobs: "+Colors.AQUA+"Automaton"};
                case 7: return new String[]{Colors.BLUE+"Lost Precursor City", Colors.WHITE+"Crystal: "+Colors.BLUE+"Sapphire"};
                case 8: return new String[]{Colors.AQUA+"Mines of Divan", Colors.WHITE+"Crystal: "+Colors.DARK_GREEN+"Jade"};
                case 9: return new String[]{Colors.RED+"Khazad-dûm", Colors.WHITE+"Crystal: "+Colors.YELLOW+"Topaz", Colors.RED+"Mobs: "+Colors.RED+"Bal Boss"};
                case 10: return new String[]{Colors.LIGHT_PURPLE+"Fairy Grotto", Colors.AQUA+"Gem: "+Colors.LIGHT_PURPLE+"Jasper", Colors.RED+"Mobs: "+Colors.BLUE+"Butterfly"};
                case -3: return new String[]{Colors.RED+"Magma Fields"};
            }
        } catch(Exception e) {
            e.printStackTrace();
            return new String[]{"Location:", "Unknown"};

        }


        return new String[]{"Location:", "Unknown"};
    }




}
