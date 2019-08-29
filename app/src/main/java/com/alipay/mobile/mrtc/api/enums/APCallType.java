package com.alipay.mobile.mrtc.api.enums;

public enum APCallType {
    CALL_TYPE_LOOKBACK(0),
    CALL_TYPE_WEBRTC(1),
    CALL_TYPE_ALIPAY_CALLER(2),
    CALL_TYPE_ALIPAY_CALLEE(3),
    CALL_TYPE_STOCK_CALLER(4),
    CALL_TYPE_STOCK_CALLEE(5);
    
    private int type;

    private APCallType(int v) {
        this.type = v;
    }

    public final int getType() {
        return this.type;
    }
}
