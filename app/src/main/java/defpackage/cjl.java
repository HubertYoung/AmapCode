package defpackage;

import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import java.util.ArrayList;
import java.util.Iterator;

/* renamed from: cjl reason: default package */
/* compiled from: MapZoomHelper */
public final class cjl {
    public int a;
    public int b;
    public int c;
    public int d;
    private bty e;
    private cdz f;
    private boolean g = false;
    private int h;
    private int i;

    public cjl(bty bty) {
        this.e = bty;
        cde suspendManager = DoNotUseTool.getSuspendManager();
        if (suspendManager != null) {
            this.f = suspendManager.d();
        }
    }

    public final void a(int i2, int i3, int i4, int i5) {
        this.a = i2;
        this.b = i3;
        this.c = i4;
        this.d = i5;
        this.h = agn.a(this.e.d(), (float) (this.b + this.d));
        this.i = agn.a(this.e.d(), (float) (this.a + this.c));
        this.g = true;
    }

    public final void a(ArrayList<GeoPoint> arrayList) {
        if (arrayList != null && arrayList.size() != 0) {
            Iterator<GeoPoint> it = arrayList.iterator();
            int i2 = 999999999;
            int i3 = 999999999;
            int i4 = -999999999;
            int i5 = -999999999;
            while (it.hasNext()) {
                GeoPoint next = it.next();
                i2 = Math.min(i2, next.x);
                i3 = Math.min(i3, next.y);
                i4 = Math.max(i4, next.x);
                i5 = Math.max(i5, next.y);
            }
            a(new Rect(i2, i3, i4, i5));
        }
    }

    private void a(Rect rect) {
        a();
        float b2 = b(rect);
        GeoPoint a2 = a(rect, b2);
        this.e.a(400, b2 == -1.0f ? -9999.0f : b2, 0, 0, a2.x, a2.y, true);
    }

    public final void a() {
        if (this.f != null) {
            this.f.f();
        }
    }

    private float b(Rect rect) {
        if (rect == null) {
            return 0.0f;
        }
        if (!this.g) {
            throw new IllegalStateException("缩放前需要先通过 setScreenDisplayMargin 设置屏幕区域");
        }
        float U = this.e.U();
        return Math.min(19.0f, Math.max(3.0f, Math.min((float) a((double) ((((float) this.e.al()) - ((float) this.i)) * U), (double) rect.width()), (float) a((double) ((((float) this.e.am()) - ((float) this.h)) * U), (double) rect.height()))));
    }

    private static double a(double d2, double d3) {
        return 20.0d - (Math.log(d3 / d2) / Math.log(2.0d));
    }

    private GeoPoint a(Rect rect, float f2) {
        float U = this.e.U();
        float a2 = ((float) agn.a(this.e.d(), (float) (this.d - this.b))) * U;
        double d2 = (double) (19.0f - f2);
        return new GeoPoint(rect.centerX() + ((int) (((double) (((float) agn.a(this.e.d(), (float) (this.c - this.a))) * U)) * Math.pow(2.0d, d2))), rect.centerY() + ((int) (((double) a2) * Math.pow(2.0d, d2))));
    }
}
