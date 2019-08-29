package com.alipay.zoloz.toyger.algorithm;

public abstract class ToygerBlobConfig {
    public String pubkey;

    public abstract float getCompressRate();

    public Integer getDesiredWidth() {
        return Integer.valueOf(-1);
    }
}
