package com.alipay.mobile.beehive.rpc;

public enum LoadingMode {
    TITLEBAR_LOADING("titleBarLoading"),
    CANCELABLE_LOADING("cancelableLoading"),
    CANCELABLE_EXIT_LOADING("cancelableExitLoading"),
    BLOCK_LOADING("blockLoading"),
    SILENT("silent"),
    UNAWARE("unaware");
    
    private String text;

    private LoadingMode(String text2) {
        this.text = text2;
    }

    public static LoadingMode fromString(String text2) {
        LoadingMode[] values;
        if (text2 != null) {
            for (LoadingMode b : values()) {
                if (text2.equals(b.text)) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text2 + " found");
    }
}
