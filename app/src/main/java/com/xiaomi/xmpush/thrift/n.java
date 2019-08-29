package com.xiaomi.xmpush.thrift;

public enum n {
    Circle(0),
    Polygon(1);
    
    private final int c;

    private n(int i) {
        this.c = i;
    }

    public static n a(int i) {
        switch (i) {
            case 0:
                return Circle;
            case 1:
                return Polygon;
            default:
                return null;
        }
    }

    public final int a() {
        return this.c;
    }
}
