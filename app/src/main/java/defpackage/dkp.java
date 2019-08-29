package defpackage;

/* renamed from: dkp reason: default package */
/* compiled from: FalconHostHelper */
public final class dkp {
    public static String a(String str) {
        String b = aaf.b(str);
        if (b.endsWith("/")) {
            return b;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        sb.append("/");
        return sb.toString();
    }
}
