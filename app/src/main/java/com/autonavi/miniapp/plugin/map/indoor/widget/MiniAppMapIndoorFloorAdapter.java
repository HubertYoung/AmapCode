package com.autonavi.miniapp.plugin.map.indoor.widget;

import java.util.Collections;
import java.util.List;

public class MiniAppMapIndoorFloorAdapter implements MiniAppBaseFloorAdapter {
    private List<MiniAppMapIndoorFloor> mMapIndoorFloorList;

    public int getCurrentIndex() {
        return 0;
    }

    public MiniAppMapIndoorFloorAdapter(List<MiniAppMapIndoorFloor> list) {
        this.mMapIndoorFloorList = list;
        Collections.sort(this.mMapIndoorFloorList, new MiniAppSortComparator());
    }

    public int getItemsCount() {
        if (this.mMapIndoorFloorList == null) {
            return 0;
        }
        return this.mMapIndoorFloorList.size();
    }

    public Object getItem(int i) {
        if (this.mMapIndoorFloorList == null || i < 0 || i >= this.mMapIndoorFloorList.size()) {
            return null;
        }
        return this.mMapIndoorFloorList.get(i);
    }

    public int getMaximumLength() {
        int i = 0;
        for (MiniAppMapIndoorFloor next : this.mMapIndoorFloorList) {
            if (next.toString().length() > i) {
                i = next.toString().length();
            }
        }
        return i;
    }
}
