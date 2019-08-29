package defpackage;

import android.graphics.Rect;

/* renamed from: aly reason: default package */
/* compiled from: GLClickObj */
public class aly {
    public int a;
    public int b;
    public Rect c = new Rect();
    public boolean d;
    public a e;

    /* renamed from: aly$a */
    /* compiled from: GLClickObj */
    public interface a {
        void a();
    }

    public aly(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public boolean a(int i, int i2, Rect rect, aly aly) {
        if (aly == null) {
            this.c.left = rect.left;
            this.c.top = rect.top;
            this.c.right = rect.left + this.a;
            this.c.bottom = rect.top + this.b;
        } else {
            this.c.left = aly.c.right;
            this.c.top = rect.top;
            this.c.right = this.c.left + this.a;
            this.c.bottom = rect.top + this.b;
        }
        if (this.e == null || !this.c.contains(i, i2)) {
            return false;
        }
        this.e.a();
        return true;
    }
}
