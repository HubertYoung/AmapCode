package com.autonavi.miniapp.plugin.map.indoor.widget;

public class MiniAppMapIndoorFloor {
    public String mFloor_name;
    public int mFloor_number;

    public int getmFloor_number() {
        return this.mFloor_number;
    }

    public void setmFloor_number(int i) {
        this.mFloor_number = i;
    }

    public String getmFloor_name() {
        return this.mFloor_name;
    }

    public void setmFloor_name(String str) {
        this.mFloor_name = str;
    }

    public String toString() {
        return String.valueOf(this.mFloor_name);
    }
}
