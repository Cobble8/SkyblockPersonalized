package com.cobble.sbp.gui.screen.dwarven;

import com.cobble.sbp.utils.TextUtils;
import com.cobble.sbp.utils.WorldUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;


public class MiningPath {

    public static boolean toggleMiningPath = true;//Get it from configs, I also didn't import it because I'm too dumb to understand
    public static int distFrom = 20;
    public ArrayList<HashMap<String, Integer>> points = new ArrayList<>();
    public BlockPos center = new BlockPos(-1, -1, -1);

    public void draw(RenderWorldLastEvent event) {
        if(!toggleMiningPath) return;
        if(points.size() <= 1) return;

        for(int i = 0 ; i < points.size() - 1 ; i++) {
            HashMap<String, Integer> point = points.get(i);

            float x1 = point.get("x");
            float y1 = point.get("y");
            float z1 = point.get("z");

            float x2 = points.get(i+1).get("x");
            float y2 = points.get(i+1).get("y");
            float z2 = points.get(i+1).get("z");
            //DUNSWE
            switch(point.get("f")) {
                case 0: //DOWN
                    y1 -= 0.55f; break;
                case 1: //UP
                    y1 += 0.55f; break;
                case 2: //NORTH
                    z1 -= 0.55f; break;
                case 3: //SOUTH
                    z1 += 0.55f; break;
                case 4: //WEST
                    x1 -= 0.55f; break;
                case 5: //EAST
                    x1 += 0.55f; break;
            }

            switch(points.get(i+1).get("f")) {
                case 0: //DOWN
                    y2 -= 0.55f; break;
                case 1: //UP
                    y2 += 0.55f; break;
                case 2: //NORTH
                    z2 -= 0.55f; break;
                case 3: //SOUTH
                    z2 += 0.55f; break;
                case 4: //WEST
                    x2 -= 0.55f; break;
                case 5: //EAST
                    x2 += 0.55f; break;
            }
            EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
            try {
                if(Math.sqrt(Math.pow(center.getX()-p.posX,2)+Math.pow(center.getY(), 2)+Math.pow(center.getZ(), 2)) > distFrom) {return;}
            } catch(Exception e) {
                //e.printStackTrace();
            }
            GlStateManager.color(1, 0.64f, 0);
            WorldUtils.drawLine(new Vec3(x1+0.5,y1+0.5,z1+0.5), new Vec3(x2+0.5,y2+0.5,z2+0.5), new Color(100, 100, 255), event.partialTicks, true);
            //WorldUtils.drawLine(x1, y1, z1, x2, y2, z2, 1, event);
            //WorldUtils.drawCube(x1, y1, z1, 0.2d,0.4d,0.4d,0.4d,new Color(255, 163, 0), event);

            /*GlStateManager.disableCull();
            //GlStateManager.blendFunc(770, 771);
            //GlStateManager.enableBlend();
            GL11.glLineWidth(5);
            GlStateManager.disableTexture2D();
            GlStateManager.pushMatrix();
            GlStateManager.disableDepth();

            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            worldrenderer.begin(3, DefaultVertexFormats.POSITION);

                    //COLOR HERE I'd assume you'd do it with Settings but forcing orange color works

            worldrenderer.pos(x1, y1, z1).endVertex();
            worldrenderer.pos(x2, y2, z2).endVertex();
            tessellator.draw();

            GlStateManager.enableCull();
            GlStateManager.popMatrix();
            GlStateManager.enableTexture2D();
            //GlStateManager.disableBlend();
            GlStateManager.enableDepth();//*/
        }

    }


    public void addPoint(MovingObjectPosition mop) {

        if(mop == null) {

            TextUtils.sendErrMsg("Please add a valid block to the Mining Helper");
        }

        HashMap<String, Integer> point = new HashMap<>();


        point.put("x", mop.getBlockPos().getX());
        point.put("y", mop.getBlockPos().getY());
        point.put("z", mop.getBlockPos().getZ());
        point.put("f", mop.sideHit.getIndex());

        points.add(point);




        reloadCenter();
    }

    public void clearPoints() {
        points.clear();
        reloadCenter();
    }

    public void deleteLastPoint() {
        points.remove(points.size()-1);

        reloadCenter();
    }

    public void reloadCenter() {
        int totalX = 0;
        int totalY = 0;
        int totalZ = 0;
        for(HashMap<String, Integer> point : points) {
            totalX+=point.get("x");
            totalY+=point.get("y");
            totalZ+=point.get("z");
        }
        center = new BlockPos(totalX/points.size(), totalY/points.size(), totalZ/points.size());
    }
}

//Add the main function to RenderWorldEvent in events
//You need to run the clear method in a lobbyswapevent.

//keybindings are pretty simple so I won't add them here since they are in a separate file
