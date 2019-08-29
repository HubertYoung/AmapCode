package defpackage;

import android.graphics.Bitmap.Config;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;

/* renamed from: cfd reason: default package */
/* compiled from: MapScreenShotConfig */
public final class cfd {
    public boolean a;
    int b;
    int c;
    int d;
    int e;
    int f;
    int g;
    Config h;
    public boolean i;
    private int j;
    private int k;

    private cfd() {
    }

    public static cfd a(@NonNull bty bty, @NonNull GeoPoint geoPoint, int i2, int i3) {
        PointF f2 = bty.f(geoPoint.x, geoPoint.y);
        cfd a2 = a();
        a2.a = true;
        cfd d2 = a2.a((ags.a(bty.d()).width() - i2) / 2).b((int) (f2.y - ((float) (i3 / 2)))).c(i2).d(i3);
        d2.j = Math.max(0, geoPoint.x);
        d2.k = Math.max(0, geoPoint.y);
        return d2;
    }

    public final cfd a(int i2) {
        this.b = Math.max(0, i2);
        return this;
    }

    public final cfd b(int i2) {
        this.c = Math.max(0, i2);
        return this;
    }

    public final cfd c(int i2) {
        this.d = Math.max(0, i2);
        return this;
    }

    public final cfd d(int i2) {
        this.e = Math.max(0, i2);
        return this;
    }

    private cfd e(int i2) {
        this.f = Math.max(0, i2);
        return this;
    }

    private cfd f(int i2) {
        this.g = Math.max(0, i2);
        return this;
    }

    public static cfd a() {
        DisplayMetrics displayMetrics = AMapAppGlobal.getApplication().getResources().getDisplayMetrics();
        return new cfd().e(displayMetrics.widthPixels).f(displayMetrics.heightPixels);
    }
}
