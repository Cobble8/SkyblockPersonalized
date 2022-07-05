package com.cobble.sbp.gui.screen.nether;

import com.cobble.sbp.SBP;
import com.cobble.sbp.core.config.ConfigHandler;
import com.cobble.sbp.core.config.DataGetter;
import com.cobble.sbp.utils.Colors;
import com.cobble.sbp.utils.GuiUtils;
import com.cobble.sbp.utils.Utils;
import net.minecraft.client.gui.Gui;

import java.util.*;

public class KuudraShopPrices extends Gui {

    public static HashMap<String, Integer> upgrades = new HashMap<>();
    public static HashMap<String, Integer> upgradeCount = new HashMap<>();
    public static final String[] upgradeNames = {"rapid_fire", "bonus_damage", "multi_shot", "accelerated", "blast_radius"};
    public static final int[][] upgradeCosts = {{0, 40, 80, 120, 160, 200}, {0, 50, 80, 120, 140, 170}, {0, 50, 80, 110, 140, 170}, {0, 10, 20, 30, 40, 50, 60}, {0, 20, 40, 60, 80, 100, 120}};
    public static boolean inKuudraFight = false;
    public static int tokens = 0;

    public static void draw() {
        try {

            //This is here, so for the people who's sub-location is broken, it will still work but its more likely that it will break because why did Hypixel name it 'Instance' and it will probably be changed
            boolean inFight;
            if(SBP.subLocation.equals("N/A")) {
                inFight = SBP.sbLocation.equals("instanced");
            } else {
                inFight = SBP.subLocation.equals("kuudra's end");
            }

            if(!inFight && inKuudraFight) { newFight(); }
            inKuudraFight = inFight;
            if(upgradeCount.isEmpty()) {newFight();}
            if(!inKuudraFight || !DataGetter.findBool("nether.kuudraShopPrices.toggle")) { return; }
            ArrayList<KuudraShopUpgrade> u = new ArrayList<>();
            ArrayList<KuudraShopUpgrade> o = new ArrayList<>();
            for (String upgradeName : upgradeNames) { u.add(new KuudraShopUpgrade(upgradeName)); }

            for(int i=u.size()-1;i>=0;i--) {
                int highest = -100;
                int spot = 0;
                for(int j=0;j<u.size();j++) {
                    if(u.get(j).count > highest) {
                        highest = u.get(j).count;
                        spot = j;
                    }
                }
                o.add(u.remove(spot));
            }
            int offset = 0;//false;
            boolean titleToggle = DataGetter.findBool("nether.kuudraShopPrices.titleToggle");
            boolean tokenToggle = DataGetter.findBool("nether.kuudraShopPrices.tokenToggle");
            if(titleToggle) { offset++; }
            if(tokenToggle) { offset++; }

            String[] output = new String[o.size()+offset];
            for(int i=offset;i<output.length;i++) {
                int price = getPrice(o.get(i-offset).id, upgrades.get(o.get(i-offset).id));
                int tier = upgrades.get(o.get(i-offset).id)+1;
                String name = o.get(i-offset).name;
                String color;
                if(price <= tokens) {
                    color = Colors.textColor(DataGetter.findStr("nether.kuudraShopPrices.yesTextColor"));
                } else {
                    color = Colors.textColor(DataGetter.findStr("nether.kuudraShopPrices.noTextColor"));
                }

                if(price == -1) {
                    output[i] = Colors.textColor(DataGetter.findStr("nether.kuudraShopPrices.maxTextColor"))+name+": MAXED!";
                } else {
                    output[i] = color+name+" ("+(tier-1)+"➡"+tier+"): "+price;
                }

            }
            if(titleToggle) {
                output[0] = Colors.textColor(DataGetter.findStr("nether.kuudraShopPrices.titleTextColor"))+"Kuudra Shop Prices";
                if(tokenToggle) {
                    output[1] = Colors.textColor(DataGetter.findStr("nether.kuudraShopPrices.tokenColor"))+"Tokens:"+Colors.textColor(DataGetter.findStr("nether.kuudraShopPrices.tokenNumColor"))+" "+tokens;
                }
            } else if(tokenToggle) {
                output[0] = Colors.textColor(DataGetter.findStr("nether.kuudraShopPrices.tokenColor"))+"Tokens:"+Colors.textColor(DataGetter.findStr("nether.kuudraShopPrices.tokenNumColor"))+" "+tokens;
            }
            GuiUtils.drawString(output, DataGetter.findInt("nether.kuudraShopPrices.x"), DataGetter.findInt("nether.kuudraShopPrices.y"), 4, true);
            String username = "Cobble8".replaceAll("/^\\w+$/i", "");

        } catch(Exception e) {
            //e.printStackTrace();
            //Utils.printDev(e.getMessage());
            //System.out.println(upgradeCount);
        }


    }

    public static int getPrice(String upgrade, int tier) {
        int spot = -1;
        for(int i=0;i<upgradeNames.length;i++) {
            if(upgrade.equals(upgradeNames[i])) {
                spot = i;
                break;
            }
        }
        if(spot == -1) {return -1;}
        try {
            return upgradeCosts[spot][tier];
        } catch(Exception e) {
            //System.out.println("Missing Price: "+upgrade);
            return -1;
        }

    }

    public static void newFight() {
        KuudraReadyWarning.ready = false;
        upgrades.clear();
        tokens = 0;
        for(String upgrade : upgradeNames) {
            upgrades.put(upgrade, 1);
            if(!upgradeCount.isEmpty()) {
                ConfigHandler.newObject("nether.kuudraShopPrices.count_"+upgrade, upgradeCount.get(upgrade));
            }
            upgradeCount.put(upgrade, DataGetter.findInt("nether.kuudraShopPrices.count_"+upgrade));
        }

    }

}

class KuudraShopUpgrade {
    public String id;
    public String name;
    public int count;

    public KuudraShopUpgrade(String id) {
        String[] words = id.split("_");
        for(int j=0;j<words.length;j++) { words[j] = words[j].substring(0,1).toUpperCase(Locale.ROOT)+words[j].substring(1).toLowerCase(Locale.ROOT); }
        StringBuilder displayName = new StringBuilder();
        for(int j=0;j<words.length;j++) {
            displayName.append(words[j]);
            if(j != words.length-1) {
                displayName.append(" ");
            }
        }
        this.id = id;
        this.name = displayName.toString();
        //System.out.println(id+" : "+name);
        this.count = KuudraShopPrices.upgradeCount.get(id);
    }

    public String config() {
        return "nether.kuudraShopPrices.count_"+id;
    }

}
