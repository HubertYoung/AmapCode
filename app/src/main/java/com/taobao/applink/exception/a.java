package com.taobao.applink.exception;

public enum a {
    NULL_POINT(0),
    ITEMID_ILLEGAL(1),
    SHOPID_ILLEGAL(2),
    H5URL_ILLEGAL(3),
    SIGN_MISSING(4);
    
    public int f;

    private a(int i) {
        this.f = i;
    }
}
