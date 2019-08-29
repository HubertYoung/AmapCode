package com.alipay.mobile.security.bio.config;

public enum BisRuntimeInfoEnum {
    ASSETS_READY("bEva", 0),
    PROTOCOL_FORMAT("fmt", 1);
    
    private String mProduct;
    private int mProductID;

    private BisRuntimeInfoEnum(String str, int i) {
        this.mProduct = str;
        this.mProductID = i;
    }

    public final String getProduct() {
        return this.mProduct;
    }

    public final void setProduct(String str) {
        this.mProduct = str;
    }

    public final int getProductID() {
        return this.mProductID;
    }

    public final void setProductID(int i) {
        this.mProductID = i;
    }
}
