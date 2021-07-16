package com.cobble.sbp.gui.menu;

import com.cobble.sbp.SBP;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GameOfLife extends GuiScreen {

    private static int[][] prev = new int[][]{};
    private static final int rows = 40;
    private static final int columns = 40;

    long lastMove = 0;
    boolean paused = true;

    @Override
    public void initGui() {
        prev = resetBoard();
        lastMove=System.currentTimeMillis();

        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {

        if(System.currentTimeMillis() > lastMove+200) {
            lastMove+=200;
            if(!paused) {
                //progress
                prev = progress(prev);
            }

        }

        this.drawDefaultBackground();
        mc.getTextureManager().bindTexture(SettingMenu.blank);

        //Draw Grid
        int sX = SBP.width/2-220;
        int sY = SBP.height/2-220;
        if(paused) {
            for(int i=0;i<rows+1;i++) {
                drawModalRectWithCustomSizedTexture(sX, sY+i*11, 0, 0, rows*11, 1, 1, 1);
            }
            for(int i=0;i<columns+1;i++) {
                drawModalRectWithCustomSizedTexture(sX+i*11, sY, 0, 0, 1, columns*11, 1, 1);
            }
        }


        for(int x=0;x<rows;x++) {
            for(int y=0;y<columns;y++) {
                try {
                    if(prev[x][y] == 1) {
                        if(!paused) {
                            GlStateManager.color(0.2f, 1, 1, 1);
                        } else {
                            GlStateManager.color(1, 0.2f, 0.2f, 1);
                        }
                        String color = Colors.WHITE;
                        if(!paused) {
                            color = Colors.BLACK;
                        }


                        drawModalRectWithCustomSizedTexture(sX+(x*11)+1, sY+(y*11)+1, 0, 0, 9, 9, 1 ,1);
                        Utils.drawString(color+getNear(x, y), sX+(x*11)+3, sY+(y*11)+2, 2);
                        mc.getTextureManager().bindTexture(SettingMenu.blank);
                    }
                } catch(Exception ignored) {}

            }
        }



    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        int sX = SBP.width/2-220;
        int sY = SBP.height/2-220;
        //prev = resetBoard();
        int slotX = (mouseX-sX)/11;
        int slotY = (mouseY-sY)/11;
        if(mouseX > sX && mouseX <= sX+440 && mouseY > sY && mouseY <= sY+440) {
            try{
                if(prev[slotX][slotY] == 0) {
                    prev[slotX][slotY]=1;
                } else {
                    prev[slotX][slotY]=0;
                }
            } catch(Exception ignored) {}
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char par1, int par2) throws IOException {
        if(par2 == Keyboard.KEY_P) {
            paused = !paused;//
            return;
        } else if(par2 == Keyboard.KEY_R) {
            prev = resetBoard();
            return;
        }
        super.keyTyped(par1, par2);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    private static int getNear(int x, int y) {
        int output = 0;
        for(int i=-1;i<2;i++) {
            for(int j=-1;j<2;j++) {
                try {
                    output+=prev[i+x][j+y];
                } catch(Exception ignored) {}

            }
        }
        output-=prev[x][y];
        return output;
    }

    private static int[][] resetBoard() {

        int[][] output = new int[40][40];
        try {
            for(int i=0;i<rows;i++) {
                for(int j=0;j<columns;j++) {
                    output[i][j] = 0;
                }
            }
        } catch(Exception e) {e.printStackTrace();}


        return output;
    }

    private static int[][] progress(int[][] board) {

        for(int x=0;x<rows;x++) {
            for(int y=0;y<columns;y++) {
                int near = getNear(x, y);
                int curr = board[x][y];
                if(curr == 0) {
                    if(near == 3) {
                        board[x][y]=1;
                    }
                } else if(curr == 1) {
                    if(near < 2 || near > 3) {
                        board[x][y]=0;
                    }
                }


            }
        }

        return board;
    }

}
