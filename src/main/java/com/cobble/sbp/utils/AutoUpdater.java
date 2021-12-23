package com.cobble.sbp.utils;

import com.cobble.sbp.SBP;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class AutoUpdater {

    public AutoUpdater(String updateUrl) {
        String downloadFileName = updateUrl.substring(updateUrl.lastIndexOf("/"));
        File tmpSaveLoc = new File("config/"+Reference.MODID+"/update/"+downloadFileName);
        if(tmpSaveLoc.getParentFile().exists()) {
            try {
                tmpSaveLoc.delete();
            } catch(Exception ignored) {}
            try {
                tmpSaveLoc.getParentFile().delete();
            } catch(Exception ignored) {}

        }
        try {
            tmpSaveLoc.getParentFile().delete();
        } catch(Exception ignored) { }
        Utils.saveImage(updateUrl, tmpSaveLoc.getPath());

        Utils.print("Mod File Name: "+SBP.modFile);
        Utils.print(new File(tmpSaveLoc.getParentFile()+"/"+SBP.modFile.getName()).getAbsolutePath());
        tmpSaveLoc.renameTo(new File(tmpSaveLoc.getParentFile()+"/"+SBP.modFile.getName()));
        tmpSaveLoc = new File(tmpSaveLoc.getParentFile()+"/"+SBP.modFile.getName());
        File modFolderLoc;
        File vers = new File("mods/1.8.9/"+SBP.modFile.getName());
        if(vers.exists()) {modFolderLoc=vers;}
        else {modFolderLoc = new File("mods/"+SBP.modFile.getName()+"");}
        try {
            FileUtils.copyFile(tmpSaveLoc, modFolderLoc);
            tmpSaveLoc.delete();
            tmpSaveLoc.getParentFile().delete();
        } catch(Exception e) {e.printStackTrace();}

    }

}
