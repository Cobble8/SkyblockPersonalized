package com.cobble.sbp.gui.screen.dwarven;

public class MiningAbility {


    int cd1, cd2, cd3;
    int dr1, dr2, dr3;

    public MiningAbility(int cd1, int dr1, int cd2, int dr2, int cd3, int dr3) {
        this.dr1 = dr1;
        this.cd1 = cd1;
        this.dr2 = dr2;
        this.cd2 = cd2;
        this.dr3 = dr3;
        this.cd3 = cd3;
    }

    public MiningAbility(int cd, int dr) {
        this.dr1 = dr;
        this.dr2 = dr;
        this.dr3 = dr;
        this.cd1 = cd;
        this.cd2 = cd;
        this.cd3 = cd;
    }

    public int getCooldown(int level) {
        switch(level) {
            case 1: return cd1;
            case 2: return cd2;
            case 3: return cd3;
        }
        return cd1;
    }

    public int getDuration(int level) {
        switch(level) {
            case 1: return dr1;
            case 2: return dr2;
            case 3: return dr3;
        }
        return dr1;
    }



}
