package com.autonavi.link.adapter.b;

/* compiled from: PacketDefiniens */
public class b {
    public static final String a = "1.9";
    public static byte b;

    static {
        a(a);
    }

    private static void a(String str) {
        b = (byte) (Integer.valueOf(str.split("\\.")[1]).intValue() | (Integer.valueOf(str.split("\\.")[0]).intValue() << 4));
    }
}
