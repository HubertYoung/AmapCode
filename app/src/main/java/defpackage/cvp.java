package defpackage;

/* renamed from: cvp reason: default package */
/* compiled from: ClassFilter */
public final class cvp {
    private static final String[] a = {"java.", "com.android.", "android.", "sun."};

    public static final boolean a(String str) {
        if (str == null) {
            return false;
        }
        for (String startsWith : a) {
            if (str.startsWith(startsWith)) {
                return true;
            }
        }
        return false;
    }
}
