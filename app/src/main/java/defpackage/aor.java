package defpackage;

import com.amap.bundle.logs.AMapLog;

/* renamed from: aor reason: default package */
/* compiled from: ThirdSDKHandler */
public abstract class aor {
    protected aox e;
    protected int f = 0;
    protected String g;
    protected int h;
    protected int i;
    protected int j;
    protected boolean k;

    /* access modifiers changed from: 0000 */
    public abstract void a();

    public final void a(String str, aox aox, boolean z) {
        if (aox == null) {
            AMapLog.e("accountTAG", "ThirdSDKHandler login callback is null");
            return;
        }
        this.e = aox;
        this.f = 0;
        this.g = str;
        this.k = z;
        a();
    }

    public final void a(int i2, aox aox) {
        if (aox == null) {
            AMapLog.e("accountTAG", "ThirdSDKHandler bind callback is null");
            return;
        }
        this.e = aox;
        this.f = 1;
        this.h = 1;
        this.i = i2;
        this.j = 0;
        a();
    }

    public final void b(int i2, aox aox) {
        this.e = aox;
        this.f = 2;
        this.h = 1;
        this.i = i2;
        this.j = 0;
        a();
    }
}
