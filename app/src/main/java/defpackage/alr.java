package defpackage;

/* renamed from: alr reason: default package */
/* compiled from: MapGeoStateDefaultParams */
public final class alr {
    public int a;
    public int b;
    public int c;
    public int d;
    public int e;

    public alr() {
        a();
    }

    public final void a() {
        this.a = 0;
        this.b = 0;
        this.c = 16;
        this.d = 221010004;
        this.e = 101712921;
    }

    public alr(int i, int i2) {
        a(0, 0, 0, i, i2);
    }

    public final void a(int i, int i2, int i3, int i4, int i5) {
        this.a = i;
        this.b = i2;
        if (i4 <= 0 || i5 <= 0) {
            this.d = 221010004;
            this.e = 101712921;
        } else {
            this.d = i4;
            this.e = i5;
        }
        if (i3 <= 4 || i3 >= 21) {
            this.c = 16;
        } else {
            this.c = i3;
        }
        if (i2 >= 0) {
            this.b = i2;
        } else {
            this.b = 0;
        }
    }
}
