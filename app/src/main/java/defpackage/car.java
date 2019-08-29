package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.bundle.searchresult.ajx.ModulePoi.c;

/* renamed from: car reason: default package */
/* compiled from: PoiDetailAdapter */
public final class car {
    public int a;
    public int b;
    public cas c = new cas();
    Handler d = new Handler(Looper.getMainLooper());
    public c e;
    int f = -1;
    int g;
    public c h = new c() {
        public final void a(final String str, final boolean z) {
            AMapLog.d("PoiDetailAdapter", "stateDidChange ".concat(String.valueOf(str)));
            car.this.d.post(new Runnable() {
                public final void run() {
                    car.this.c.a = str;
                    TextUtils.equals(str, ModulePoi.MOVE);
                    if (car.this.e != null) {
                        car.this.e.a(str, z);
                    }
                }
            });
        }

        public final void b(final String str, final boolean z) {
            AMapLog.d("PoiDetailAdapter", "onStartAnimateToState ".concat(String.valueOf(str)));
            car.this.d.post(new Runnable() {
                public final void run() {
                    if (car.this.e != null) {
                        car.this.e.b(str, z);
                    }
                }
            });
        }

        public final void a(final int i, final boolean z) {
            if (car.this.f == -1) {
                car.this.f = i;
            } else if (car.this.f != i) {
                car.this.f = i;
                car.this.d.post(new Runnable() {
                    public final void run() {
                        StringBuilder sb = new StringBuilder("offsetHeight ");
                        sb.append(car.this.c.b);
                        AMapLog.d("PoiDetailAdapter", sb.toString());
                        if (euk.a()) {
                            car.this.a = 0;
                        }
                        car.this.c.b = (car.this.b - i) - car.this.a;
                        if (car.this.c.b < car.this.g) {
                            car.this.c.b = car.this.g;
                            car.this.e.a(car.this.c.b, z);
                            return;
                        }
                        if (car.this.e != null) {
                            car.this.e.a(car.this.c.b, z);
                        }
                    }
                });
            }
        }
    };

    public final void a(int i) {
        this.c.b = i;
        this.g = i;
    }
}
