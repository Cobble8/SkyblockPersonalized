package com.cobble.sbp.utils;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.events.skyblock.LobbySwapEvent;
import com.cobble.sbp.gui.screen.nether.KuudraShopPrices;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SBUtils {


    public static String getSBID() {
ItemStack heldItem = Minecraft.getMinecraft().thePlayer.getHeldItem();
return getSBID(heldItem);
}

    public static String getSBID(ItemStack item) {
        if (item == null) { return ""; }
        else if (!item.hasTagCompound()) { return ""; }

        NBTTagCompound skyBlockData = item.getSubCompound("ExtraAttributes", false);

        if (skyBlockData != null) { String itemId = skyBlockData.getString("id");  if (!itemId.equals("")) { return itemId.toLowerCase(); } }  return "";
}

    public static NBTTagCompound getEnchants(ItemStack item) {
        NBTTagCompound extraAttributes = getExtraAttributes(item);
        return extraAttributes == null ? null : extraAttributes.getCompoundTag("enchantments");
    }

    public static NBTTagCompound getExtraAttributes(ItemStack item) {
        if (item == null || !item.hasTagCompound()) {
            return null;
        }

        return item.getSubCompound("ExtraAttributes", false);
    }

    public static void checkIfOnSkyblock() {
        boolean wind = false;
        if(DataGetter.findBool("core.skyblock.only")) {
            String title = getBoardTitle().toLowerCase(Locale.ENGLISH);

            if(title.contains("skyblock")) {
                if(!SBP.onSkyblock) { Utils.print("Logged onto Skyblock"); LobbySwapEvent.currLobby="";}
                SBP.onSkyblock=true;
                Minecraft mc = Minecraft.getMinecraft();
                Scoreboard scoreboard = Minecraft.getMinecraft().theWorld.getScoreboard();
                ScoreObjective sidebarObjective = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1);
                Collection<Score> scoreboardLines = scoreboard.getSortedScores(sidebarObjective);
                List<Score> list = scoreboardLines.stream().filter(p_apply_1_ -> p_apply_1_.getPlayerName() != null && !p_apply_1_.getPlayerName().startsWith("#")).collect(Collectors.toList());
                if (list.size() > 15) { scoreboardLines = Lists.newArrayList(Iterables.skip(list, scoreboardLines.size() - 15)); } else { scoreboardLines = list; }

                int i=0;
                for(Score line : scoreboardLines) {
                    ScorePlayerTeam scorePlayerTeam = scoreboard.getPlayersTeam(line.getPlayerName());
                    String strippedLine = ScorePlayerTeam.formatPlayerName(scorePlayerTeam, line.getPlayerName());
                    boolean loc = strippedLine.indexOf('\u23E3') != -1;
                    strippedLine = TextUtils.unformatAllText(strippedLine);
                    strippedLine = Pattern.compile("[^a-z A-Z:0-9/'.]").matcher(strippedLine).replaceAll("").trim();
                    if(loc) {SBP.subLocation = strippedLine.toLowerCase(Locale.ENGLISH); continue;}
                    boolean tokens = strippedLine.contains("Tokens: ");
                    if(tokens) {
                        KuudraShopPrices.tokens = Integer.parseInt(strippedLine.substring(strippedLine.indexOf(": ")+2));
                        continue;
                    }

                    if(strippedLine.contains("Wind Compass")) {
                        try {
                            if(DataGetter.findBool("dwarven.windCompass.toggle")) {
                                wind=true;
                                Score score2 = (Score) scoreboardLines.toArray()[i-1];
                                ScorePlayerTeam scorePlayerTeam2 = scoreboard.getPlayersTeam(score2.getPlayerName());
                                String strippedLine2 = ScorePlayerTeam.formatPlayerName(scorePlayerTeam2, score2.getPlayerName());
                                SBP.titleString=Colors.WHITE+strippedLine2;
                            }

                        } catch(Exception ignored) {}
                    }
                    i++;
                }


            } else {
                SBP.sbLocation = "N/A"; if(SBP.onSkyblock) { Utils.print("Logged off of Skyblock"); } SBP.onSkyblock=false;
            }

        } else {
            SBP.onSkyblock=true;
        }
        if(wind) {SBP.titleScale=2;} else if(SBP.titleScale==2) {SBP.titleScale=4; SBP.titleString="";}
    }

    public static String getBoardTitle() {
        String output;
        try {

        ScoreObjective sidebarObjective = Minecraft.getMinecraft().theWorld.getScoreboard().getObjectiveInDisplaySlot(1);

        output = TextUtils.unformatAllText(sidebarObjective.getDisplayName());

        } catch(Exception e) {
            return "null";
        }
        return output;
    }
}
