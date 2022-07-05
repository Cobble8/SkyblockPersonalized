package com.cobble.sbp.commands;

import com.cobble.sbp.SBP;
import com.cobble.sbp.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

public class CrystalWaypoint extends CommandBase {
    ArrayList<String> aliases;

    public CrystalWaypoint() {
        aliases = new ArrayList<>();
        aliases.add("sbpcw"); //SBP Crystal Waypoint
        aliases.add("chwp"); //Crystal Hollows Waypoint
        aliases.add("sbpwp"); //SBP Waypoint
        aliases.add("wp"); //Waypoint
        aliases.add("waypoint"); //Seriously
    }

    @Override
    public String getCommandName() {
        return "skyblockpersonalizedcrystalwaypoint";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(!SBP.sbLocation.equals("crystalhollows")) { TextUtils.sendErrMsg("You must be in the "+ Colors.LIGHT_PURPLE+"Crystal Hollows"+Colors.YELLOW+" to use this command!"); return; }
        EntityPlayerSP plyr = Minecraft.getMinecraft().thePlayer;
        StringBuilder name = new StringBuilder("loc");
        int x = (int) plyr.posX;
        int y = (int) plyr.posY;
        int z = (int) plyr.posZ;

        try {
            //if the first two args both aren't numbers, make it the name
            //else if they both are, check if there is a third, then make any argument after the first three args the name.
            //else make the y -1 (player height), make the stuff after the 2 coordinates the name.
            try {
                //3 coord
                //t = temp
                int tx = Integer.parseInt(args[0]);
                int ty = Integer.parseInt(args[1]);
                int tz = Integer.parseInt(args[2]);
                x = tx;
                y = ty;
                z = tz;
                StringBuilder tname = new StringBuilder();
                for(int i=3;i<args.length;i++) {  tname.append(args[i]).append(" "); }
                if(!tname.toString().equals("")) { name = new StringBuilder(tname.substring(0, tname.length() - 1));}


            } catch(Exception e) {
                int tx = Integer.parseInt(args[0]);
                int tz = Integer.parseInt(args[1]);
                x = tx;
                y = -1;
                z = tz;
                StringBuilder tname = new StringBuilder();
                for(int i=2;i<args.length;i++) {
                    tname.append(args[i]).append(" ");
                }
                if(!tname.toString().equals("")) {
                    name = new StringBuilder(tname.substring(0, tname.length() - 1));}
            }



        } catch(Exception ignored) {
            name = new StringBuilder();
            for (String arg : args) { name.append(arg).append(" "); }
        }
        if(!name.toString().contains("&")) { name.insert(0, Colors.WHITE);}
        name = new StringBuilder(name.toString().replace("&", Reference.COLOR_CODE_CHAR + ""));
        if(x < 202 || x > 823) {
            TextUtils.sendErrMsg("Invalid or no provided x coordinate, setting to your current x coordinate."); x = (int) plyr.posX; }
        if(y < 30 || y > 190) {
            TextUtils.sendErrMsg("Invalid y or no provided coordinate, setting to your current y coordinate."); y = (int) plyr.posY; }
        if(z < 202 || z > 823) {
            TextUtils.sendErrMsg("Invalid z or no provided coordinate, setting to your current z coordinate."); z = (int) plyr.posZ; }

        WorldUtils.addCrystalWaypoint(name.toString(), x, y, z);
        String tmp = name.toString(); if(tmp.length() == 0) { tmp = "Blank"; }
        TextUtils.sendMessage("Successfully created a waypoint at "+Colors.AQUA+x+Colors.YELLOW+", "+Colors.AQUA+y+Colors.YELLOW+", "+Colors.AQUA+z+Colors.YELLOW+" named '"+tmp+Colors.YELLOW+"'");
    }

    @Override
    public List<String> getCommandAliases() {
        return aliases;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

}
