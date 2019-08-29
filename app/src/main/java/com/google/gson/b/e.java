package com.google.gson.b;

/* compiled from: JavaVersion */
public final class e {
    private static final int a = c();

    private static int c() {
        return a(System.getProperty("java.version"));
    }

    static int a(String str) {
        int b = b(str);
        if (b == -1) {
            b = c(str);
        }
        if (b == -1) {
            return 6;
        }
        return b;
    }

    private static int b(String str) {
        try {
            String[] split = str.split("[._]");
            int parseInt = Integer.parseInt(split[0]);
            return (parseInt != 1 || split.length <= 1) ? parseInt : Integer.parseInt(split[1]);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    private static int c(String str) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                if (!Character.isDigit(charAt)) {
                    break;
                }
                sb.append(charAt);
            }
            return Integer.parseInt(sb.toString());
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    public static int a() {
        return a;
    }

    public static boolean b() {
        return a >= 9;
    }
}
