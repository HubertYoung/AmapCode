package com.xiaomi.xmpush.thrift;

public enum w {
    RegIdExpired(0),
    PackageUnregistered(1),
    Init(2);
    
    private final int d;

    private w(int i) {
        this.d = i;
    }

    public static w a(int i) {
        switch (i) {
            case 0:
                return RegIdExpired;
            case 1:
                return PackageUnregistered;
            case 2:
                return Init;
            default:
                return null;
        }
    }

    public final int a() {
        return this.d;
    }
}
