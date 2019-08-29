package com.xiaomi.channel.commonutils.misc;

public class a {
    public static final String a;
    public static final boolean b;
    public static final boolean c;
    public static final boolean d = "LOGABLE".equalsIgnoreCase(a);
    public static final boolean e = a.contains("YY");
    public static boolean f = a.equalsIgnoreCase("TEST");
    public static final boolean g = "BETA".equalsIgnoreCase(a);
    public static final boolean h;
    private static int i = 1;

    static {
        int i2;
        String str = d.a ? "ONEBOX" : "@SHIP.TO.2A2FE0D7@";
        a = str;
        boolean contains = str.contains("2A2FE0D7");
        b = contains;
        boolean z = false;
        c = contains || "DEBUG".equalsIgnoreCase(a);
        if (a != null && a.startsWith("RC")) {
            z = true;
        }
        h = z;
        if (a.equalsIgnoreCase("SANDBOX")) {
            i2 = 2;
        } else if (a.equalsIgnoreCase("ONEBOX")) {
            i2 = 3;
        } else {
            i = 1;
            return;
        }
        i = i2;
    }

    public static void a(int i2) {
        i = i2;
    }

    public static boolean a() {
        return i == 2;
    }

    public static boolean b() {
        return i == 3;
    }

    public static int c() {
        return i;
    }
}
