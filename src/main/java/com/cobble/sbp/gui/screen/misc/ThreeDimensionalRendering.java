package com.cobble.sbp.gui.screen.misc;

import com.cobble.sbp.utils.Resources;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;

import java.util.ArrayList;

public class ThreeDimensionalRendering {

    public static void draw(int posX, int posY) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(Resources.blank);
        GlStateManager.color(0,0,0);
        Gui.drawModalRectWithCustomSizedTexture(posX,posY,0,0,144,144,0,0);


        ArrayList<Point3D> p = new ArrayList<>();

        p.add(new Point3D(-1,1,-1));
        p.add(new Point3D(1,1,-1));
        p.add(new Point3D(-1,-1,-1));
        p.add(new Point3D(1,-1,-1));
        p.add(new Point3D(-1,1,1));
        p.add(new Point3D(1,1,1));
        p.add(new Point3D(-1,-1,1));
        p.add(new Point3D(1,-1,1));
        p.get(0).connect(p.get(1));
        p.get(0).connect(p.get(2));
        p.get(0).connect(p.get(4));
        p.get(3).connect(p.get(1));
        p.get(3).connect(p.get(2));
        p.get(3).connect(p.get(7));
        p.get(5).connect(p.get(1));
        p.get(5).connect(p.get(4));
        p.get(5).connect(p.get(7));
        p.get(6).connect(p.get(2));
        p.get(6).connect(p.get(4));
        p.get(6).connect(p.get(7));

        for(int i=0;i<p.size();i++) {
            p.get(i).rotate((System.currentTimeMillis()/250d)%360);
        }
        Point3D.drawConnections(p);
        GlStateManager.color(0.0f,0.85f,1);
        for(Point3D point : p) { point.draw(posX+82, posY+82); }



    }


}

class Point3D {
    public double x, y, z;
    public ArrayList<Point3D> c;
    public Point3D() {
        this(0,0,0);
    }

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.c = new ArrayList<>();

    }

    public void draw(int x2D, int y2D) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(Resources.blank);



        double finX = x2D+((x+(z/4d))*20);
        double finY = y2D+((y+(z/4d))*20);
        Gui.drawModalRectWithCustomSizedTexture((int) finX,(int) finY,0,0,1,1,0,0);
    }

    public static void drawConnections(ArrayList<Point3D> points) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(Resources.blank);
        GlStateManager.color(1,0.85f,0);

        for(int i=0;i<points.size();i++) {
            for(int j=0;j<points.size();j++) {
                Point3D p1 = points.get(i);
                Point3D p2 = points.get(j);
                if(p1.isConnected(p2)) {
                    for(int h=1;h<8;h++) {
                        new Point3D(Math.min(p1.x,p2.x)+(Math.abs(p1.x-p2.x)*h/8), Math.min(p1.y,p2.y)+(Math.abs(p1.y-p2.y)*h/8), Math.min(p1.z,p2.z)+(Math.abs(p1.z-p2.z)*h/8)).draw(87,87);
                    }

                }
            }
        }

    }

    public boolean isConnected(Point3D p) {
        for(Point3D p2 : c) {
            if(p2.equals(p)) {
                return true;
            }
        }
        return false;
    }

    public void connect(Point3D p) {
        c.add(p);
    }

    public void rotate(double a) {
        x = (x*Math.cos(a))-(z*Math.sin(a));
        //z = (z*Math.cos(a))+(x*Math.sin(a));

    }

    public String toString() {
        return "["+x+", "+y+", "+z+"]";
    }


}
