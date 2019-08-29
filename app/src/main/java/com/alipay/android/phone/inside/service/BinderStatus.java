package com.alipay.android.phone.inside.service;

public enum BinderStatus {
    SUCCESS(1000),
    ILLEGAL(1001),
    NO_MATCH_ACTION(1002);
    
    final int mStatus;

    public final int getValue() {
        return this.mStatus;
    }

    private BinderStatus(int i) {
        this.mStatus = i;
    }
}
