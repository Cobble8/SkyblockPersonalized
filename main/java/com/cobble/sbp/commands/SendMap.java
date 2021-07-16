package com.cobble.sbp.commands;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

import com.cobble.sbp.SBP;
import com.cobble.sbp.gui.screen.dwarven.CrystalHollowsMap;
import com.cobble.sbp.handlers.RecieveCrystalMapHandler;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class SendMap extends CommandBase {
    ArrayList<String> aliases;
    public SendMap() {
        aliases = new ArrayList();
    }

    @Override
    public String getCommandName() {
        return "sendmap";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Usage: /sendmap";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        try {
            if(!SBP.sbLocation.equals("crystalhollows")) {return;}

            String testOne = testLength(1);
            String testTwo = testLength(2);



            String res;
            int trial;
            if(testOne.length() > testTwo.length()) {
                res = testTwo;
                trial=2;
            } else {
                res = testOne;
                trial=1;
            }
            Utils.print(testOne.length()+", "+testTwo.length()+" : "+trial);
            //Utils.sendSpecificMessage(Colors.YELLOW+res);
            //Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(res), null);
            new RecieveCrystalMapHandler(res);
            Utils.print("'"+res+"'");
            Utils.sendMessage("'"+res+"'");



        } catch(Exception ignored) {}



    }


    private static String testLength(int trial) {
        ArrayList<String> newLocs = new ArrayList<>();

        for(int x=0;x<50;x++) {
            for(int y=0;y<50;y++) {
                try {
                    if(trial == 1) {
                        newLocs.add(numToLet(CrystalHollowsMap.locs[x][y]));
                    } else {
                        newLocs.add(numToLet(CrystalHollowsMap.locs[y][x]));
                    }

                } catch(Exception e) {
                    newLocs.add(numToLet(-1));
                }

            }
        }

        String output = "";
        String lastChar = "";
        int charCount = 0;
        for(int i=0;i<newLocs.size();i++) { // for each character
            //output+=newLocs.get(i);
            if(lastChar.equals(newLocs.get(i))) { // check if same as last character
                charCount++; // amt of same char in a row
            } else {
                if(charCount != 0) { //no blank spaces
                    char tmp = (char) (charCount+50); // amt of next char OR blank space if NEXT is ALSO more than 50
                    output+=tmp;
                    if("fhklmn".contains(lastChar)) { // check if curr char is important
                        output+=lastChar;
                    }
                    charCount=0;
                }
                lastChar=newLocs.get(i);

            }

            /*if(lastChar.equals(newLocs.get(i))) {
                charCount++;
            } else {
                if(charCount != 0) {
                    char tmp = (char) (charCount+50);
                    output+=tmp;
                    if("fhklmn".contains(lastChar)) {
                        output+=lastChar+"<--";
                    }
                    charCount=0;
                }
                lastChar=newLocs.get(i);
            }*/
        }
        return "sbpMap"+trial+output;
    }


    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    public static String numToLet(int num) {
        String output = "";

        String[] letters = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        output=letters[num+3];

        return output;
    }

}
