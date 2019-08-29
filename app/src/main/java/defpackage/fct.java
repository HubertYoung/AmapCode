package defpackage;

/* renamed from: fct reason: default package */
/* compiled from: WifiNameCoder */
final class fct {
    private static String a = "Xcar_";
    private static String b = "IOV_";

    public static boolean a(String str) {
        if (str.indexOf(a) == 0 || str.indexOf(b) == 0 || str.equals("<unknown ssid>") || str.equals("unknown ssid")) {
            return true;
        }
        return false;
    }
}
