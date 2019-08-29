package com.amap.bundle.blutils;

public final class SdCardInfo {
    public SDCardType a;
    public String b = null;
    public String c;
    public String d;
    public String e;

    public enum SDCardType {
        INNERCARD,
        EXTERNALCARD
    }

    public SdCardInfo() {
    }

    public SdCardInfo(SDCardType sDCardType, String str) {
        this.a = sDCardType;
        this.b = str;
    }
}
