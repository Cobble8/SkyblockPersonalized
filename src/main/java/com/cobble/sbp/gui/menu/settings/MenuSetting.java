package com.cobble.sbp.gui.menu.settings;

import java.util.ArrayList;

public class MenuSetting {

    String name;
    String categories;
    String id;
    String description;
    String version;

    ArrayList<String> subsettingTypes = new ArrayList<>();
    ArrayList<String> subsettingNames = new ArrayList<>();
    ArrayList<String> subsettingIDs = new ArrayList<>();

    public MenuSetting(String name, String categories, String id, String description, String version) {
        this.name = name;
        this.categories = categories;
        this.id = id;
        this.description = description;
        this.version = version;
    }

    public MenuSetting subsetting(String subName, String id, String type) {
        subsettingTypes.add(type);
        subsettingNames.add(subName);
        subsettingIDs.add(id);
        return this;
    }




    public void finish() {



        if(subsettingTypes.size() == 0) {
            SettingMenu.addSetting(this.name, this.categories, this.id, this.description, this.version);
        } else {

            if(subsettingTypes.size() > 9) { System.out.println("[WARN] "+this.name+" has more than 9 subsettings!"); }

            StringBuilder types = new StringBuilder();
            for(int i=0;i<subsettingTypes.size();i++) { types.append(subsettingTypes.get(i)); if(i != subsettingTypes.size()-1) { types.append(", "); } }
            StringBuilder names = new StringBuilder();
            for(int i=0;i<subsettingNames.size();i++) { names.append(subsettingNames.get(i)); if(i != subsettingNames.size()-1) { names.append(", "); } }
            StringBuilder IDs = new StringBuilder();
            for(int i=0;i<subsettingIDs.size();i++) { IDs.append(subsettingIDs.get(i)); if(i != subsettingIDs.size()-1) { IDs.append(", "); } }
            SettingMenu.addSetting(this.name, this.categories, this.id, this.description, types.toString(), names.toString(), IDs.toString(), this.version);
        }

    }



}
