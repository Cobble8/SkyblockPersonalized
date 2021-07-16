package com.cobble.sbp.gui.menu.settings;

import com.cobble.sbp.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class SettingMusic extends GuiScreen {

    @Override
    public void initGui() {
        super.initGui();
    }
    ResourceLocation musicButtons = new ResourceLocation(Reference.MODID, "textures/0/menu/musicbuttons.png");
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        mc.getTextureManager().bindTexture(SettingMenu.logo);
        drawModalRectWithCustomSizedTexture(this.width/2-200, 17+22, 0, 0, 400, 100, 400, 100);
        mc.fontRendererObj.drawString(Colors.YELLOW+"Version: "+Colors.AQUA+ Reference.VERSION, this.width/2+194-(mc.fontRendererObj.getStringWidth("Version: "+Reference.VERSION)), 17+22+100-14-11, 0x1f99fa, false);
        mc.fontRendererObj.drawString(Colors.YELLOW+"Made by "+Colors.RESET+"Cobble8", this.width/2+194-(mc.fontRendererObj.getStringWidth("Made by Cobble8")), 17+22+100-14, 0x1f99fa, false);


        int pauseOffset = 0;
        if(MusicUtils.songState.equals("playing")) {
            pauseOffset = 16;
        }
        ColorUtils.resetColor();
        mc.getTextureManager().bindTexture(musicButtons);
        drawModalRectWithCustomSizedTexture(this.width/2-8, 17+22+120, pauseOffset, 16, 16, 16, 48, 16);
        drawModalRectWithCustomSizedTexture(this.width/2+8, 17+22+120, 32, 16, 16, 16, 48, 16);
        int posX = this.width/2-250;
        int posY = 138-4;
        int scale = 40;
        mc.getTextureManager().bindTexture(SettingMenu.settingBg);
        GlStateManager.enableBlend();
        GlStateManager.color(1, 1, 1, 0.7F);
        drawModalRectWithCustomSizedTexture(posX-32, posY-90, 0, 0, 33, 33, 92, 55);
        drawModalRectWithCustomSizedTexture(posX-32+66-33, posY-90, 92-33, 0, 33, 33, 92, 55);
        drawModalRectWithCustomSizedTexture(posX-32, posY+4-33, 0, 22, 33, 33, 92, 55);
        drawModalRectWithCustomSizedTexture(posX-32+66-33, posY+4-33, 92-33, 22, 33, 33, 92, 55);
        drawModalRectWithCustomSizedTexture(posX-32, posY-90+33, 0, 16, 33, 28, 92, 55);
        drawModalRectWithCustomSizedTexture(posX-32+33, posY-90+33, 92-33, 16, 33, 28, 92, 55);
        mc.getTextureManager().bindTexture(SettingMenu.settingBorder);
        drawModalRectWithCustomSizedTexture(posX-32, posY-94+4, 0,0, 66, 2, 370, 223);
        drawModalRectWithCustomSizedTexture(posX-32, posY-94+4+2, 0,0, 2, 92, 370, 223);
        drawModalRectWithCustomSizedTexture(posX-32+64, posY-94+4+2, 0,0, 2, 92, 370, 223);
        drawModalRectWithCustomSizedTexture(posX-30, posY+2, 0,0, 62, 2, 370, 223);
        Utils.renderPlayer(posX, posY-5, scale, mouseX, mouseY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if(mouseX >= this.width/2-8 && mouseX <= this.width/2+8 && mouseY >= 17+22+120 && mouseY <= 17+22+120+16) {


            if(MusicUtils.songState.equals("playing")) {
                MusicUtils.pauseSong();
                Utils.playClickSound();
            } else if(MusicUtils.songState.equals("paused")) {
                MusicUtils.playSong();
            } else {
                MusicUtils.setSong("config/sbp/music/DwarvenMines.wav");
                MusicUtils.playSong();
            }

        } else if(mouseX >= this.width/2+8 && mouseX <= this.width/2+24 && mouseY >= 17+22+120 && mouseY <= 17+22+120+16) {

        MusicUtils.stopSong();
        Utils.playClickSound();

        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char par1, int par2) throws IOException {
        if(par2 == Keyboard.KEY_E || par2 == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(new SettingMenu());
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
