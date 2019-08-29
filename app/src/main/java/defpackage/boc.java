package defpackage;

/* renamed from: boc reason: default package */
/* compiled from: ThreeDes */
public final class boc {
    static final byte[] a = {17, 34, 79, 88, -120, 16, 64, 56, 40, 37, 121, 81, -53, -35, 85, 102, 119, 41, 116, -104, 48, 64, 54, -30};

    public static String a(String str) {
        try {
            return agx.a(agz.a(str, a, "CBC", "PKCS5Padding"));
        } catch (Exception e) {
            kf.a((Throwable) e);
            return null;
        }
    }
}
