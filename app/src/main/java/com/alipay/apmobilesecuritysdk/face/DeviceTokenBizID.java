package com.alipay.apmobilesecuritysdk.face;

public enum DeviceTokenBizID {
    DEFAULT("DEF"),
    PAYMENT("PAY"),
    LOGIN("LOGIN"),
    REGISTER("REG");
    
    private String desc;

    private DeviceTokenBizID(String str) {
        this.desc = str;
    }

    public final String getDesc() {
        return this.desc;
    }
}
