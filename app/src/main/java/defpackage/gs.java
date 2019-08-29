package defpackage;

import java.util.List;

/* renamed from: gs reason: default package */
/* compiled from: FontCharacter */
public final class gs {
    public final List<hv> a;
    public final double b;
    private final char c;
    private final int d;
    private final String e;
    private final String f;

    public static int a(char c2, String str, String str2) {
        return ((((c2 + 0) * 31) + str.hashCode()) * 31) + str2.hashCode();
    }

    public gs(List<hv> list, char c2, int i, double d2, String str, String str2) {
        this.a = list;
        this.c = c2;
        this.d = i;
        this.b = d2;
        this.e = str;
        this.f = str2;
    }

    public final int hashCode() {
        return a(this.c, this.f, this.e);
    }
}
