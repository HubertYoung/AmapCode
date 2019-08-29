package defpackage;

import android.graphics.PointF;
import android.graphics.Rect;
import java.util.List;

/* renamed from: ame reason: default package */
/* compiled from: GLPointOverlayItem */
public final class ame {
    protected int a;
    protected int b;
    protected amc c = null;
    protected Rect d = new Rect();
    public long e;

    public final Rect a() {
        return this.d;
    }

    public ame(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public final void a(amc amc) {
        this.c = amc;
    }

    public final boolean a(int i, akq akq, List<aly> list, int i2, int i3) {
        if (this.c == null) {
            return false;
        }
        PointF c2 = akq.c(i, this.a, this.b);
        a(this.d, (int) c2.x, (int) c2.y, this.c, this.c.h);
        if (!(this.d == null || !this.d.contains(i2, i3) || list == null)) {
            int i4 = 0;
            while (i4 < list.size()) {
                aly aly = list.get(i4);
                if (aly != null) {
                    if (aly.a(i2, i3, this.d, i4 == 0 ? null : list.get(i4 - 1))) {
                        return true;
                    }
                }
                i4++;
            }
        }
        return false;
    }

    public static void a(Rect rect, int i, int i2, amc amc, int i3) {
        if (rect != null && amc != null) {
            switch (i3) {
                case 0:
                    rect.left = i;
                    rect.right = i + amc.d;
                    rect.top = i2;
                    rect.bottom = i2 + amc.e;
                    return;
                case 1:
                    rect.left = i - amc.d;
                    rect.right = i;
                    rect.top = i2;
                    rect.bottom = i2 + amc.e;
                    return;
                case 2:
                    rect.left = i;
                    rect.right = i + amc.d;
                    rect.top = i2 - amc.e;
                    rect.bottom = i2;
                    return;
                case 3:
                    rect.left = i - amc.d;
                    rect.right = i;
                    rect.top = i2 - amc.e;
                    rect.bottom = i2;
                    return;
                case 4:
                    rect.left = i - (amc.d / 2);
                    rect.right = i + (amc.d / 2);
                    rect.top = i2 - (amc.e / 2);
                    rect.bottom = i2 + (amc.e / 2);
                    return;
                case 5:
                    rect.left = i - (amc.d / 2);
                    rect.right = i + (amc.d / 2);
                    rect.top = i2 - amc.e;
                    rect.bottom = i2;
                    return;
                case 6:
                    rect.left = i - (amc.d / 2);
                    rect.right = i + (amc.d / 2);
                    rect.top = i2;
                    rect.bottom = i2 + amc.e;
                    return;
                case 7:
                    rect.left = i;
                    rect.right = i + amc.d;
                    rect.top = i2 - (amc.e / 2);
                    rect.bottom = i2 + (amc.e / 2);
                    return;
                case 8:
                    rect.left = i - amc.d;
                    rect.right = i;
                    rect.top = i2 - (amc.e / 2);
                    rect.bottom = i2 + (amc.e / 2);
                    return;
                case 9:
                    float f = (float) i;
                    rect.left = (int) (f - (((float) amc.d) * amc.f));
                    rect.top = i2 - amc.e;
                    rect.right = (int) (f + (((float) amc.d) * (1.0f - amc.f)));
                    rect.bottom = i2;
                    break;
            }
        }
    }
}
