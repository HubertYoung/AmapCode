package com.autonavi.miniapp.plugin.map.indoor.widget;

import java.util.Comparator;

public class MiniAppSortComparator implements Comparator<MiniAppMapIndoorFloor> {
    public int compare(MiniAppMapIndoorFloor miniAppMapIndoorFloor, MiniAppMapIndoorFloor miniAppMapIndoorFloor2) {
        return miniAppMapIndoorFloor2.getmFloor_number() - miniAppMapIndoorFloor.getmFloor_number();
    }
}
