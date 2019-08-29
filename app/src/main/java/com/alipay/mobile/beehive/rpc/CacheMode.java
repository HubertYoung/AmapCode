package com.alipay.mobile.beehive.rpc;

import com.autonavi.minimap.ajx3.util.Constants;

public enum CacheMode {
    NONE(Constants.ANIMATOR_NONE),
    CACHE_AND_RPC("cacheAndRpc"),
    RPC_OR_CACHE("rpcOrCache"),
    RPC_AND_SAVE_CACHE("rpcAndSaveCache");
    
    private String text;

    private CacheMode(String text2) {
        this.text = text2;
    }

    public static CacheMode fromString(String text2) {
        CacheMode[] values;
        if (text2 != null) {
            for (CacheMode b : values()) {
                if (text2.equals(b.text)) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text2 + " found");
    }
}
