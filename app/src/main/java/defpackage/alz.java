package defpackage;

import android.graphics.Rect;

/* renamed from: alz reason: default package */
/* compiled from: GLLTClickObj */
public final class alz extends aly {
    public int f;
    public int g;

    public alz(int i, int i2, int i3, int i4) {
        super(i, i2);
        this.f = i3;
        this.g = i4;
    }

    public final boolean a(int i, int i2, Rect rect, aly aly) {
        this.c.left = rect.left + this.f;
        this.c.top = rect.top + this.g;
        this.c.right = this.c.left + this.a;
        this.c.bottom = this.c.top + this.b;
        if (this.e == null || !this.c.contains(i, i2)) {
            return false;
        }
        this.e.a();
        return true;
    }
}
