package com.alipay.ma.common.b;

/* compiled from: MaType */
public enum e {
    PRODUCT(0, 255),
    MEDICINE(2, 255),
    EXPRESS(2, 255),
    QR(1, 512),
    TB_ANTI_FAKE(1, 512),
    TB_4G(1, 2048),
    DM(1, 1024),
    GEN3(1, 32768),
    ARCODE(65536, 65536);
    
    private int j;
    private int k;

    private e(int type, int discernType) {
        this.j = type;
        this.k = discernType;
    }

    public final int a() {
        return this.k;
    }
}
