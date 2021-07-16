package com.cobble.sbp.gui.menu;

import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.gui.menu.settings.SettingMenu;
import com.cobble.sbp.utils.ColorUtils;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;
import net.minecraft.client.gui.GuiScreen;
import com.cobble.sbp.SBP;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;

public class GameThing extends GuiScreen {

    int posX = 0;
    int posY = 0;
    long lastMove = 0;
    int ePosX = 0;
    int ePosY = 0;
    int deaths = 0;
    ArrayList<Boolean> dotsFound = new ArrayList<>();
    int foundDots = 0;
    boolean paused = false;
    String record = DataGetter.findStr("gameRecord");
    long startTime = 0;

    @Override
    public void initGui() {
        if(record.equals("")) {
            record = "d0";
            ConfigHandler.newObject("gameRecord", record);
        }
        resetFoundDots();
        lastMove=System.currentTimeMillis();
        posX = 0;
        posY = 0;
        ePosX = 19;
        ePosY = 19;
        startTime=System.currentTimeMillis();
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        String mColor = "0.6;1;0.6;1";
        if(System.currentTimeMillis() > lastMove+250) {
            lastMove+=250;
            if(!paused) {
                int xDiff = posX-ePosX;
                int yDiff = posY-ePosY;



                if(Math.abs(xDiff) > Math.abs(yDiff)) {
                    if(xDiff > 0) {
                        ePosX++;
                    } else {
                        ePosX--;
                    }
                } else {
                    if(yDiff > 0) {
                        ePosY++;
                    } else {
                        ePosY--;
                    }
                }
                if((posX == ePosX && posY == ePosY) || foundDots == 400) {
                    if(foundDots != 400) {
                        deaths++;
                    }

                    posX=0;
                    posY=0;
                    ePosX=19;
                    ePosY=19;
                    long newRecord = System.currentTimeMillis()-startTime;
                    if(record.startsWith("w") && foundDots == 400) {
                        try {
                            long oldRecord = Long.parseLong(record.substring(1));

                            if(newRecord < oldRecord) {
                                record = "w"+newRecord;
                                ConfigHandler.newObject("gameRecord", record);
                            }
                        } catch(Exception ignored) {}
                    }
                    else if(record.startsWith("d")) {
                        if(foundDots==400) {
                            record = "w"+newRecord;
                            ConfigHandler.newObject("gameRecord", record);
                        }
                        try {
                            int oldRecord = Integer.parseInt(record.substring(1));
                            if(foundDots > oldRecord) {

                                record = "d"+foundDots;
                                ConfigHandler.newObject("gameRecord", record);
                            }
                        } catch(Exception ignored) {}

                    }
                    startTime=System.currentTimeMillis();
                    resetFoundDots();


                }
            }



        }


        String recordType = "Dots";
        if(record.startsWith("w")) {
            recordType="Seconds";
        }



        String finalRecord = "Record: "+record.substring(1)+" "+recordType;

        if(recordType.equals("Seconds")) {
            try {
                finalRecord = "Record: "+(Double.parseDouble(record.substring(1))/1000d)+" "+recordType;
            } catch(Exception ignored) {}
        }
        String time = Utils.secondsToTime((int) (System.currentTimeMillis()-startTime));
        Utils.drawString(Colors.LIGHT_PURPLE+"Score: "+foundDots, SBP.width/2-(mc.fontRendererObj.getStringWidth("Score: "+foundDots)/2), SBP.height/2+102, 1);
        Utils.drawString(Colors.GREEN+"Time: "+time, SBP.width/2-(mc.fontRendererObj.getStringWidth("Time: "+time)/2), SBP.height/2+114, 1);
        Utils.drawString(Colors.AQUA+finalRecord, SBP.width/2-(mc.fontRendererObj.getStringWidth(finalRecord)/2), SBP.height/2+126, 1);
        Utils.drawString(Colors.GOLD+"Deaths: "+deaths, SBP.width/2-(mc.fontRendererObj.getStringWidth("Deaths: "+deaths)/2), SBP.height/2+138, 1);


        String pause = Colors.YELLOW+"Click to pause the game!";
        if(paused) {pause = Colors.YELLOW+"Click to unpause the game!";}
        Utils.drawString(pause, SBP.width/2-(mc.fontRendererObj.getStringWidth(pause)/2), SBP.height/2+150, 1);
        if(paused) {
            Utils.drawString(Colors.RED+Colors.BOLD+"Paused!", SBP.width/2-(mc.fontRendererObj.getStringWidth(Colors.BOLD+"Paused!")/2), SBP.height/2+162, 1);
        }

        mc.getTextureManager().bindTexture(SettingMenu.blank);
        ColorUtils.setColor("0;0;0;1");
        drawModalRectWithCustomSizedTexture(SBP.width/2-100, SBP.height/2-100, 0, 0, 200, 200, 1, 1);


        //ColorUtils.setColor();

        for(int x=0;x<20;x++) {
            for(int y=0;y<20;y++) {
                try {
                    if(dotsFound.get((x*20)+y)) {continue;}
                } catch(Exception e) {
                    resetFoundDots();
                }


                ColorUtils.setChroma(SBP.width/2-96+(x*10), SBP.height/2-96+(y*10));
                double r = ((Math.abs(x-posX)+(Math.abs(y-posY)))/8d); r = 1-r; if(r<0){r=0;}
                double g = ((Math.abs(x-ePosX)+(Math.abs(y-ePosY)))/8d); g= 1-g; if(g<0){g=0;}


                ColorUtils.setColor(g+";"+r+";0.4;1");
                drawModalRectWithCustomSizedTexture(SBP.width/2-96+(x*10), SBP.height/2-96+(y*10), 0, 0, 2, 2, 1, 1);
            }
        }
        ColorUtils.setColor(mColor);
        drawModalRectWithCustomSizedTexture(SBP.width/2-98+(posX *10), SBP.height/2-98+(posY *10), 0, 0, 6, 6, 1, 1);
        ColorUtils.setColor("1;0.6;0.6;1");
        drawModalRectWithCustomSizedTexture(SBP.width/2-98+(ePosX*10), SBP.height/2-98+(ePosY*10), 0, 0, 6, 6, 1, 1);

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {

        paused = !paused;
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char par1, int par2) throws IOException {
        super.keyTyped(par1, par2);
        if(!paused) {
            if(par2 == Keyboard.KEY_D && posX < 19) {
                posX++; updateDot();
            } else if(par2 == Keyboard.KEY_A && posX > 0) {
                posX--; updateDot();
            } else if(par2 == Keyboard.KEY_S && posY < 19) {
                posY++; updateDot();
            } else if(par2 == Keyboard.KEY_W && posY > 0) {
                posY--; updateDot();
            }
        }

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    void resetFoundDots() {
        foundDots=1;//ss
        dotsFound.clear();
        dotsFound.add(true);
        for(int i=0;i<399;i++) {
            dotsFound.add(false);
        }
    }

    void updateDot() {
        if(!dotsFound.get(posX*20+posY)) {
            dotsFound.set(posX*20+posY, true);
            foundDots++;
        }
    }
}
