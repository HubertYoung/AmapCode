package com.alipay.mobile.mrtc.api.enums;

public enum APScalingType {
    SCALE_ASPECT_FIT(0),
    SCALE_ASPECT_FILL(1),
    SCALE_ASPECT_BALANCED(2);
    
    private int type;

    private APScalingType(int v) {
        this.type = v;
    }

    public final int getType() {
        return this.type;
    }
}
