package com.amap.bundle.drivecommon.navi.navidata;

import com.autonavi.minimap.basemap.favorites.fragment.FavoritesPointFragment;

public enum TmcColor {
    NOTRAFFIC(158, 181, 202),
    UNKNOWN(0, 145, 255),
    UNBLOCK(0, 186, 31),
    SLOW(255, 186, 0),
    BLOCK(FavoritesPointFragment.REQUEST_EDIT_POINT, 29, 32),
    GRIDLOCKED(168, 9, 11);
    
    private int blueValue;
    private int greenValue;
    private int redValue;

    private TmcColor(int i, int i2, int i3) {
        this.redValue = i;
        this.greenValue = i2;
        this.blueValue = i3;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("(");
        sb.append(this.redValue);
        sb.append("，");
        sb.append(this.greenValue);
        sb.append("，");
        sb.append(this.blueValue);
        sb.append(")");
        return sb.toString();
    }

    public final int R() {
        return this.redValue;
    }

    public final int G() {
        return this.greenValue;
    }

    public final int B() {
        return this.blueValue;
    }
}
