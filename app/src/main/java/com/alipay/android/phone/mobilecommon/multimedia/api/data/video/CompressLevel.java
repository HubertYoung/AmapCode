package com.alipay.android.phone.mobilecommon.multimedia.api.data.video;

public enum CompressLevel {
    V320P(0),
    V540P(1),
    V720P(2),
    V1080P(3);
    
    private int value;

    public final int getValue() {
        return this.value;
    }

    private CompressLevel(int value2) {
        this.value = value2;
    }
}
