package com.xiaomi.xmpush.thrift;

public enum h {
    MISC_CONFIG(1),
    PLUGIN_CONFIG(2);
    
    private final int c;

    private h(int i) {
        this.c = i;
    }

    public static h a(int i) {
        switch (i) {
            case 1:
                return MISC_CONFIG;
            case 2:
                return PLUGIN_CONFIG;
            default:
                return null;
        }
    }

    public final int a() {
        return this.c;
    }
}
