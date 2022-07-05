package com.cobble.sbp.utils;

import com.cobble.sbp.SBP;
import com.cobble.sbp.gui.menu.settings.SettingGlobal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;

public class GuiUtils {

    public static void drawRegularPolygon(double x, double y, int radius, int sides, int percent)
    {
        drawRegularPolygon(x, y, radius, sides, percent, 1, 1, 1, 1);
    }

    public static void drawAntiAliasPolygon(double x, double y, int radius, int sides, int percent, float r, float g, float b) {

        drawRegularPolygon(x, y, radius, sides, percent, r, g, b,1);
        drawRegularPolygon(x, y, radius+1, sides*2, percent, r, g, b,0.8F);
        drawRegularPolygon(x, y, radius-1, sides*2, percent, r, g, b,0.8F);
    }

    public static void drawRegularPolygon(double x, double y, int radius, int sides, int percent, float r, float g, float b, float a) {
        try {
        //GlStateManager.disableAlpha();
        //GlStateManager.disableBlend();
        GlStateManager.color(1, 1, 1);
        //int rad2 = radius/2;
        int per = percent*sides/100;
        GlStateManager.color(r, g, b, a);
        Utils.worldRenderer.begin(GL11.GL_QUAD_STRIP, DefaultVertexFormats.POSITION);
        for(int i=0;i<per+1;i++) {

            double angle = (Utils.TWICE_PI * i / sides) + Math.toRadians(180);

            Utils.worldRenderer.pos(x + Math.sin(angle) * radius / 4*3, y+ Math.cos(angle) * radius / 4*3, 0).endVertex();
            Utils.worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
        }


        Utils.tessellator.draw();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();

        } catch(Exception ignored) { }
    }

    public static void renderPlayer(int x, int y, int scaled, int lookingX, int lookingY) {
        int mouseX2 = lookingX- x;
        int mouseY2 = lookingY- y +(scaled *4/3)+(scaled /10);

        EntityLivingBase ent = Minecraft.getMinecraft().thePlayer;
        GlStateManager.enableBlend();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableColorMaterial();
GlStateManager.pushMatrix();
GlStateManager.translate((float) x, (float) y, 50.0F);
GlStateManager.scale((float)(-scaled), (float) scaled, (float) scaled);
GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
float f = ent.renderYawOffset;
float f1 = ent.rotationYaw;
float f2 = ent.rotationPitch;
float f3 = ent.prevRotationYawHead;
float f4 = ent.rotationYawHead;
GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
RenderHelper.enableStandardItemLighting();
GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
GlStateManager.rotate(-((float)Math.atan((mouseY2*-1 / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
ent.renderYawOffset = (float)Math.atan((mouseX2*-1 / 40.0F)) * 20.0F;
ent.rotationYaw = (float)Math.atan((mouseX2*-1 / 40.0F)) * 40.0F;
ent.rotationPitch = -((float)Math.atan((mouseY2*-1 / 40.0F))) * 20.0F;
ent.rotationYawHead = ent.rotationYaw;
ent.prevRotationYawHead = ent.rotationYaw;

GlStateManager.translate(0.0F, 0.0F, 0.0F);
RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
rendermanager.setPlayerViewY(360.0F);
rendermanager.setRenderShadow(false);
rendermanager.renderEntityWithPosYaw(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
rendermanager.setRenderShadow(true);
ent.renderYawOffset = f;
ent.rotationYaw = f1;
ent.rotationPitch = f2;
ent.prevRotationYawHead = f3;
ent.rotationYawHead = f4;
GlStateManager.popMatrix();
RenderHelper.disableStandardItemLighting();
GlStateManager.disableRescaleNormal();
GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
GlStateManager.disableTexture2D();
GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.disableColorMaterial();
    }

    public static void drawString(String text, int x, int y) {
        drawString(text, x, y, SettingGlobal.textStyle);
    }

    public static void drawString(ArrayList<String> text, int x, int y, int textStyle) {
        if(text.isEmpty()) {return;}
        drawString(text.toArray(), x, y, textStyle);
    }

    public static void drawString(Object[] texts, int x, int y, int textStyle, boolean align) {
        if(texts.length == 0) {return;}
        int finXOff = 0;
        Minecraft mc = Minecraft.getMinecraft();
        int finTextStyle = textStyle;

        ArrayList<String> text = new ArrayList<>();
        for(Object curr : texts) {
            text.add(curr+"");
        }
        int maxStrWidth = 0;
        for (String s : text) {
            int strWidth = mc.fontRendererObj.getStringWidth(s);
            if (maxStrWidth < strWidth) {
                maxStrWidth = strWidth;
            }
        }
        int aO = 0;
        if(align) {
            if(x > SBP.width/2) {
                aO = maxStrWidth;
            }
        }



        if(textStyle == 3) {


            finTextStyle = 2;
            ResourceLocation bg = new ResourceLocation(Reference.MODID, "textures/gui/imageBorder_1.png");
            mc.getTextureManager().bindTexture(bg);

            maxStrWidth+=8;
            GlStateManager.enableBlend();
            GlStateManager.color(0, 0, 0, 0.6F);
            Gui.drawModalRectWithCustomSizedTexture(x-4-aO, y-4, 0, 0, maxStrWidth, 7+(11*text.size()), 5, 5);

        } else if(textStyle == 4) {
            finTextStyle = 2;

            int strHeight = text.size()*10+1;

            GlStateManager.enableBlend();
            mc.getTextureManager().bindTexture(Resources.color);
            String str = text.get(0);
            if(str.startsWith(Reference.COLOR_CODE_CHAR+"")) {
                ArrayList<Float> clr = Colors.getRGBFromColorCode(str.substring(0, 2));
                try {
                    GlStateManager.color(clr.get(0), clr.get(1), clr.get(2), clr.get(3));
                } catch(Exception e) {
                    Colors.setColor("0.0;0.0;0.6;");}

            } else {
                Colors.setColor("0.0;0.0;0.6;");
            }
            //Color
            Gui.drawModalRectWithCustomSizedTexture(x-2-aO, y-3, 0, 0, maxStrWidth+6, 1, maxStrWidth+6, 16);
            Gui.drawModalRectWithCustomSizedTexture(x-2-aO, y-1+strHeight, 0, 15, maxStrWidth+6, 1, maxStrWidth+6, 16);
            Gui.drawModalRectWithCustomSizedTexture(x-2-aO, y-2, 0, 0, 1, strHeight+1, 16, strHeight+1);
            Gui.drawModalRectWithCustomSizedTexture(x+maxStrWidth+2-aO, y-2, 15, 0, 1, strHeight+1, 16, strHeight+1);



            //Text Background
            mc.getTextureManager().bindTexture(Resources.blank);
            GlStateManager.color(0.05f, 0, 0, 0.9F);
            Gui.drawModalRectWithCustomSizedTexture(x+1-aO, y, 4, 4, maxStrWidth-1, strHeight-3, 1, 1);

            mc.getTextureManager().bindTexture(Resources.tooltip);
            GlStateManager.color(1, 1, 1, 0.98F);

            //Corners
            Gui.drawModalRectWithCustomSizedTexture(x-3-aO, y-4, 0, 0, 4, 4, 16, 16);
            Gui.drawModalRectWithCustomSizedTexture(x+maxStrWidth-aO, y-4, 12, 0, 4, 4, 16, 16);
            Gui.drawModalRectWithCustomSizedTexture(x-3, y+strHeight-3-aO, 0, 12, 4, 4, 16, 16);
            Gui.drawModalRectWithCustomSizedTexture(x+maxStrWidth-aO, y+strHeight-3, 12, 12, 4, 4, 16, 16);



            //Top and Bottom Sides
            for(int i=0;i<maxStrWidth-1;i++) {
                Gui.drawModalRectWithCustomSizedTexture(x+i+1-aO, y-4, 4, 0, 1, 4, 16, 16);
                Gui.drawModalRectWithCustomSizedTexture(x+i+1-aO, y+strHeight-3, 4, 12, 1, 4, 16, 16);
            }
            //Left and Right Sides
            for(int i=0;i<strHeight-3;i++) {
                Gui.drawModalRectWithCustomSizedTexture(x-3-aO, y+(i), 0, 4, 4, 1 , 16, 16);
                Gui.drawModalRectWithCustomSizedTexture(x+maxStrWidth-aO, y+(i), 12, 4, 4, 1 , 16, 16);
            }

            finXOff = 1;
        }

        for(int i=0;i<text.size();i++) {
            //String currString = text+"";
            int aO2 = 0;
            if(aO > 0) {
                aO2 = mc.fontRendererObj.getStringWidth(text.get(i));
            }
            drawString(text.get(i), x+finXOff-(aO2), y+(10*i), finTextStyle);

        }
    }

    public static void drawString(Object[] texts, int x, int y, int textStyle) {
        drawString(texts, x, y, textStyle, false);
    }

    public static void drawString(String text, int x, int y, int textStyle, Boolean align) {
        int alignment = 1;
        if(align) {
            if(x > SBP.width/2) {
                alignment=-1;
            }
        }


        if(text.equals("")) {return;}
        int xOffset = 0;


        //Utils.print(text);
        Minecraft mc = Minecraft.getMinecraft();
        int strWidth = mc.fontRendererObj.getStringWidth(text);
        if(strWidth <= 0) {return;}
        int aO = 0;
        if(alignment==-1) {
            aO=strWidth;
        }
        String str = text;
        String str2 = TextUtils.unformatText(text);

        if(str.contains(Colors.CHROMA)) {
            String strTypes = "";
            if(str.contains(Colors.BOLD)) {strTypes+=Colors.BOLD;}
            if(str.contains(Colors.ITALIC)) {strTypes+=Colors.ITALIC;}
            if(str.contains(Colors.UNDERLINE)) {strTypes+=Colors.UNDERLINE;}
            if(str.contains(Colors.STRIKETHROUGH)) {strTypes+=Colors.STRIKETHROUGH;}
            if(str.contains(Colors.OBFUSCATED)) {strTypes+=Colors.OBFUSCATED;}
            str = Colors.CHROMA+ TextUtils.unformatAllText(str)+strTypes;
        }



        Boolean shadows = false;
        if(textStyle == 0) { shadows = true; }
        else if(textStyle == 1) { drawBoldedString(str2, x-aO, y); }
        else if(textStyle == 3) {
            GlStateManager.enableBlend();
            GlStateManager.color(0, 0, 0, 0.6F);
            Gui.drawModalRectWithCustomSizedTexture(x-2-aO, y-2, 0, 0, strWidth+3, 11, 1, 1);
            GlStateManager.color(1, 1, 1, 1);
        }
        else if(textStyle == 4) {

            mc.getTextureManager().bindTexture(Resources.color);
            GlStateManager.enableBlend();
            if(str.startsWith(Colors.CHROMA)) {
                Colors.setChroma(x, y);
            }
            else if(str.startsWith(Reference.COLOR_CODE_CHAR+"")) {
                ArrayList<Float> clr = Colors.getRGBFromColorCode(str.substring(0, 2));
                try {
                    GlStateManager.color(clr.get(0), clr.get(1), clr.get(2), clr.get(3));
                } catch(Exception e) {
                    Colors.setColor("0.0;0.0;0.6;");}

            } else {
                Colors.setColor("0.0;0.0;0.6;");
            }

            Gui.drawModalRectWithCustomSizedTexture(x-2-aO, y-3, 0, 0, 1, 13, 16, 16);
            Gui.drawModalRectWithCustomSizedTexture(x+2+strWidth-aO, y-3, 15, 0, 1, 13, 16, 16);
            Gui.drawModalRectWithCustomSizedTexture(x-2-aO, y-3, 0, 0, strWidth+4, 1, strWidth+4, 16);
            Gui.drawModalRectWithCustomSizedTexture(x-2-aO, y+9, 0, 15, strWidth+4, 1, strWidth+4, 16);

            mc.getTextureManager().bindTexture(Resources.tooltip);
            GlStateManager.enableBlend();
            GlStateManager.color(1, 1, 1, 0.9F);
            Gui.drawModalRectWithCustomSizedTexture(x-3-aO, y-4, 0, 0, 4, 11, 16, 16);
            Gui.drawModalRectWithCustomSizedTexture(x-3-aO, y+7, 0, 12, 4, 4, 16, 16);
            Gui.drawModalRectWithCustomSizedTexture(x+strWidth-aO, y-4, 12, 0, 4, 11, 16, 16);
            Gui.drawModalRectWithCustomSizedTexture(x+strWidth-aO, y+7, 12, 12, 4, 4, 16, 16);
            Gui.drawModalRectWithCustomSizedTexture(x+1-aO, y, 4, 0, strWidth-1, 7, 1, 1);
            for(int i=0;i<strWidth/2;i++) { Gui.drawModalRectWithCustomSizedTexture(x+i*2+1-aO, y-4, 4, 0, 2, 4, 16, 16); }
            for(int i=0;i<strWidth/2;i++) { Gui.drawModalRectWithCustomSizedTexture(x+i*2+1-aO, y+7, 4, 12, 2, 4, 16, 16); }
            xOffset = 1;

        }
        if(!str.contains(Colors.CHROMA)) {
            mc.fontRendererObj.drawString(str, x+xOffset-aO, y, 0, shadows);
        }



        int chromaCount = 0;
        char[] charArray = str.toCharArray();
        if(str.contains(Reference.COLOR_CODE_CHAR+"z")) {

            for(int i=0;i<charArray.length;i++) {
                try {
                    if((charArray[i]+"").equals(Reference.COLOR_CODE_CHAR+"") && (charArray[i+1]+"").equals("z")) {
                        chromaCount++;
                    }
                } catch(Exception ignored) { }
            }
        }
        StringBuilder outputStr = new StringBuilder(str);

        for(int j=0;j<chromaCount;j++) {

        if(outputStr.toString().contains(Colors.CHROMA)) {
            int beginChroma = -1;
            int endChroma = -1;
            charArray = outputStr.toString().toCharArray();

            ArrayList<Character> chArr= new ArrayList<>();
            for(char curr : charArray) {
                chArr.add(curr);
            }


            for(int i=0;i<chArr.size();i++) {


                if(beginChroma == -1 && (chArr.get(i)+"").equals(Reference.COLOR_CODE_CHAR+"") && (chArr.get(i+1)+"").equals("z")) { beginChroma = i; }

                else if(beginChroma != -1 && endChroma == -1) {
                    if((chArr.get(i)+"").equals(Reference.COLOR_CODE_CHAR+"") || i >= chArr.size()-1) {

                        endChroma = i;
                        if(j == chromaCount-1) { endChroma+=1; }

                    }
                }
            }
            try {



                String preStr = outputStr.substring(0, beginChroma);
                String subStr = outputStr.substring(beginChroma+2, endChroma);
                //Utils.print("String: "+outputStr+"  |  SubString: "+subStr);
                String postStr = outputStr.substring(endChroma);
                int preWidth = mc.fontRendererObj.getStringWidth(preStr);
                //if(str.contains(Colors.BOLD)) {
                    //subStr = "&l"+subStr;
                //}
                String strTypes = "";
                if(str.contains(Colors.BOLD)) {strTypes+=Colors.BOLD;}
                if(str.contains(Colors.ITALIC)) {strTypes+=Colors.ITALIC;}
                if(str.contains(Colors.UNDERLINE)) {strTypes+=Colors.UNDERLINE;}
                if(str.contains(Colors.STRIKETHROUGH)) {strTypes+=Colors.STRIKETHROUGH;}
                if(str.contains(Colors.OBFUSCATED)) {strTypes+=Colors.OBFUSCATED;}

                drawChromaString(subStr, x+preWidth+xOffset-aO, y, shadows, strTypes, align);
                outputStr = new StringBuilder(preStr + subStr + postStr);
            } catch(Exception ignored) {

            }


        }
        }
        GlStateManager.enableBlend();
    }

    public static void drawString(String text, int x, int y, int textStyle) {

        drawString(text, x, y, textStyle, false);

    }

    public static void drawHangingString(String text, int x, int y, int textStyle) {
        int color = new Color(0x00EAFF).getRGB();

        char[] chars = text.toCharArray();

        int widthSoFar = 0;
        for(int i=0;i<chars.length;i++) {

            long time = System.currentTimeMillis()/50;
            int yOff = 0;
            if(time % text.length() == i || time % text.length() == i/2) {
                yOff+=1;
                color = new Color(0x00BBFF).getRGB();
            }

            if(time % text.length() == text.length()-i || time % text.length() == (text.length()-i)/2) {
                yOff=1;
                color = new Color(0x00BBFF).getRGB();
            }


            Minecraft.getMinecraft().fontRendererObj.drawString(chars[i]+"", x+widthSoFar, y+yOff, color);

            color = new Color(0x00EAFF).getRGB();
            widthSoFar+=Minecraft.getMinecraft().fontRendererObj.getCharWidth(chars[i]);
        }
    }

    public static void drawConfinedString(String text, int x, int y, int textStyle, int maxWidth) {
        Minecraft mc = Minecraft.getMinecraft();
        int strWidth = mc.fontRendererObj.getStringWidth(text);

        if(strWidth <= maxWidth) {
            drawString(text, x, y, textStyle);
        } else {
            int scaledWidth = strWidth;
            int percent = 100;
            while(scaledWidth > maxWidth) { percent-=1; scaledWidth = strWidth*percent/100; }
            double scale = Double.parseDouble(percent+"")/100;
            int heightScale = (int) (12*scale);
            int heightOffset = 8*percent/200;
            drawScaledString(text, x +(scaledWidth/2), y +(heightScale/2)+heightOffset, 0, scale, textStyle, false);
        }








    }

    public static void drawTitle() {

        //int posX = Minecraft.getMinecraft().
        //int posY = 200;
        try {
            int posX = Minecraft.getMinecraft().displayWidth/(Minecraft.getMinecraft().gameSettings.guiScale);
            int posY = Minecraft.getMinecraft().displayHeight/(Minecraft.getMinecraft().gameSettings.guiScale);
            drawScaledString(SBP.titleString, posX/2, posY/2, 0, SBP.titleScale, 1, true);
        } catch (Exception ignored) { }

        //Utils.print(SBP.width);

    }

    public static void drawScaledString(String string, int x, int y, int color, double scale, int textStyle, Boolean centered) {

        Minecraft mc = Minecraft.getMinecraft();
        int strWidth = mc.fontRendererObj.getStringWidth(string);
        int posX = x;
        int posY = y;


        posX-=(strWidth*scale/2);


        posY-=(6*scale);
        GlStateManager.pushMatrix();

        GlStateManager.scale(scale, scale, scale);

        drawString(string, (int) (posX/scale), (int) (posY/scale), textStyle);
        GlStateManager.popMatrix();
    }

    public static void drawBoldScaledString(String string, int x, int y, double scale, int textStyle) {

        Minecraft mc = Minecraft.getMinecraft();
        int strWidth = mc.fontRendererObj.getStringWidth(string);
        int posX = x;
        int posY = y;

        String cleanString = Colors.BLACK+ TextUtils.unformatText(string);

        posX-=(strWidth*scale/2);


        posY-=(6*scale);
        GlStateManager.pushMatrix();

        GlStateManager.scale(scale, scale, scale);
        drawString(cleanString, (int) (posX/scale)-1, (int) (posY/scale), textStyle);
        drawString(cleanString, (int) (posX/scale)+1, (int) (posY/scale), textStyle);
        drawString(cleanString, (int) (posX/scale), (int) (posY/scale)-1, textStyle);
        drawString(cleanString, (int) (posX/scale), (int) (posY/scale)+1, textStyle);
        drawString(Colors.WHITE+ TextUtils.unformatText(string), (int) (posX/scale), (int) (posY/scale), textStyle);
        GlStateManager.popMatrix();
    }

    public static void drawChromaString(String text, int x, int y, Boolean shadow, String strType, Boolean align) {



        Minecraft mc = Minecraft.getMinecraft();
int tmpX = x;
        char[] chArr = text.toCharArray();

        for (char tc : chArr) {
            String output = tc + "";
            if (tc != Reference.COLOR_CODE_CHAR) {

                long t = System.currentTimeMillis() - (tmpX * 10L + y * 10L);
                int i = Color.HSBtoRGB(t % (int) (Utils.chromaSpeed * 500f) / (Utils.chromaSpeed * 500f), 0.8f, 0.8f);

                mc.fontRendererObj.drawString(strType + output, tmpX, y, i, shadow);
                tmpX += mc.fontRendererObj.getStringWidth(strType + output);

            }
        }
mc.fontRendererObj.drawString(Colors.WHITE, x, y, tmpX);

}

    public static void drawBoldedString(String text, int x, int y) {
        Minecraft mc = Minecraft.getMinecraft();

        mc.fontRendererObj.drawString(text, x+1, y, 0, false);
mc.fontRendererObj.drawString(text, x-1, y, 0, false);
mc.fontRendererObj.drawString(text, x, y+1, 0, false);
mc.fontRendererObj.drawString(text, x, y-1, 0, false);
    }

    public static void drawTexturedRectNoBlend(float x, float y, float width, float height, float uMin, float uMax, float vMin, float vMax, int filter) {
GlStateManager.enableTexture2D();

GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);

Tessellator tessellator = Tessellator.getInstance();
WorldRenderer worldrenderer = tessellator.getWorldRenderer();
worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
worldrenderer
.pos(x, y+height, 0.0D)
.tex(uMin, vMax).endVertex();
worldrenderer
.pos(x+width, y+height, 0.0D)
.tex(uMax, vMax).endVertex();
worldrenderer
.pos(x+width, y, 0.0D)
.tex(uMax, vMin).endVertex();
worldrenderer
.pos(x, y, 0.0D)
.tex(uMin, vMin).endVertex();
tessellator.draw();

GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
}
}
