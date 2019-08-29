package com.autonavi.minimap.bundle.featureguide.api;

public enum GuideStartType {
    DEFAULT(0),
    BETA(1),
    RELEASE(2);
    
    private final int value;

    private GuideStartType(int i) {
        this.value = i;
    }

    public final int value() {
        return this.value;
    }

    public static GuideStartType valueOf(int i) {
        if (i == BETA.ordinal()) {
            return BETA;
        }
        if (i == RELEASE.ordinal()) {
            return RELEASE;
        }
        return DEFAULT;
    }
}
