package defpackage;

import android.graphics.Bitmap.Config;
import android.net.Uri;
import com.alipay.mobile.common.transport.monitor.RPCDataParser;
import com.autonavi.common.imageloader.ImageLoader.Priority;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* renamed from: bjz reason: default package */
/* compiled from: Request */
public final class bjz {
    private static final long u = TimeUnit.SECONDS.toNanos(5);
    int a;
    long b;
    int c;
    public final Uri d;
    public final int e;
    public final String f;
    public final List<bjo> g;
    public final int h;
    public final int i;
    public final boolean j;
    public final boolean k;
    public final boolean l;
    public final float m;
    public final float n;
    public final float o;
    public final boolean p;
    public final boolean q;
    public final Config r;
    public final Priority s;
    public final Map<String, String> t;

    bjz(Uri uri, int i2, String str, List<bjo> list, int i3, int i4, boolean z, boolean z2, boolean z3, float f2, float f3, float f4, boolean z4, boolean z5, Config config, Priority priority, Map<String, String> map) {
        this.d = uri;
        this.e = i2;
        this.f = str;
        if (list == null) {
            this.g = null;
        } else {
            this.g = Collections.unmodifiableList(list);
        }
        this.h = i3;
        this.i = i4;
        this.j = z;
        this.k = z2;
        this.l = z3;
        this.m = f2;
        this.n = f3;
        this.o = f4;
        this.p = z4;
        this.q = z5;
        this.r = config;
        this.s = priority;
        this.t = map;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Request{");
        if (this.e > 0) {
            sb.append(this.e);
        } else {
            sb.append(this.d);
        }
        if (this.g != null && !this.g.isEmpty()) {
            for (bjo a2 : this.g) {
                sb.append(' ');
                sb.append(a2.a());
            }
        }
        if (this.f != null) {
            sb.append(" stableKey(");
            sb.append(this.f);
            sb.append(')');
        }
        if (this.h > 0) {
            sb.append(" resize(");
            sb.append(this.h);
            sb.append(',');
            sb.append(this.i);
            sb.append(')');
        }
        if (this.j) {
            sb.append(" centerCrop");
        }
        if (this.k) {
            sb.append(" centerInside");
        }
        if (this.m != 0.0f) {
            sb.append(" rotation(");
            sb.append(this.m);
            if (this.p) {
                sb.append(" @ ");
                sb.append(this.n);
                sb.append(',');
                sb.append(this.o);
            }
            sb.append(')');
        }
        if (this.q) {
            sb.append(" purgeable");
        }
        if (this.r != null) {
            sb.append(' ');
            sb.append(this.r);
        }
        sb.append('}');
        return sb.toString();
    }

    public final String a() {
        long nanoTime = System.nanoTime() - this.b;
        if (nanoTime > u) {
            StringBuilder sb = new StringBuilder();
            sb.append(b());
            sb.append('+');
            sb.append(TimeUnit.NANOSECONDS.toSeconds(nanoTime));
            sb.append('s');
            return sb.toString();
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(b());
        sb2.append('+');
        sb2.append(TimeUnit.NANOSECONDS.toMillis(nanoTime));
        sb2.append(RPCDataParser.TIME_MS);
        return sb2.toString();
    }

    public final String b() {
        StringBuilder sb = new StringBuilder("[R");
        sb.append(this.a);
        sb.append(']');
        return sb.toString();
    }

    public final boolean c() {
        return (this.h == 0 && this.i == 0) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    public final boolean d() {
        return c() || this.m != 0.0f;
    }

    /* access modifiers changed from: 0000 */
    public final boolean e() {
        return this.g != null;
    }
}
