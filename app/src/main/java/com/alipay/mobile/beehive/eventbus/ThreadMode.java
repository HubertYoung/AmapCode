package com.alipay.mobile.beehive.eventbus;

public enum ThreadMode {
    UI("ui"),
    BACKGROUND(Subscribe.THREAD_BACKGROUND),
    CURRENT(Subscribe.THREAD_CURRENT);
    
    private String text;

    private ThreadMode(String text2) {
        this.text = text2;
    }

    public static ThreadMode fromString(String text2) {
        ThreadMode[] values;
        if (text2 != null) {
            for (ThreadMode b : values()) {
                if (text2.equals(b.text)) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text2 + " found");
    }
}
