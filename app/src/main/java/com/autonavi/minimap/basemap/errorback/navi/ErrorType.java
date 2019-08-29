package com.autonavi.minimap.basemap.errorback.navi;

public enum ErrorType {
    LONG_ROAD(0),
    ELE_EYE(1),
    REPORT(2),
    ROAD_WORNG(3),
    OTHER(-1);
    
    private int type;

    private ErrorType(int i) {
        this.type = i;
    }

    public final int getType() {
        return this.type;
    }
}
