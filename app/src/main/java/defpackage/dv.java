package defpackage;

/* renamed from: dv reason: default package */
/* compiled from: BasicHeader */
public final class dv implements dc {
    private final String a;
    private final String b;

    public dv(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        this.a = str;
        this.b = str2;
    }

    public final String a() {
        return this.a;
    }

    public final String b() {
        return this.b;
    }
}
