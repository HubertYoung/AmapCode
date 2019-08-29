package com.amap.bundle.utils.device;

public enum DisplayType {
    NORMAL(0),
    CUTOUT(1);
    
    private int value;

    private DisplayType(int i) {
        this.value = i;
    }

    public static DisplayType valueOf(int i) {
        switch (i) {
            case 0:
                return NORMAL;
            case 1:
                return CUTOUT;
            default:
                return null;
        }
    }

    public final int value() {
        return this.value;
    }
}
