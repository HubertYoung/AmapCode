package defpackage;

import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.network.util.NetworkReachability.NetworkType;
import com.amap.bundle.network.util.NetworkReachability.a;

/* renamed from: aao reason: default package */
/* compiled from: ParamOptManager */
public final class aao implements aal, a {
    private static aao a;
    private long b;
    private long c = Long.MIN_VALUE;
    private aaq d = new aaq();
    private aam e = new aam();
    private aap f = new aap();

    private aao() {
    }

    public static synchronized aal d() {
        aao aao;
        synchronized (aao.class) {
            try {
                if (a == null) {
                    a = new aao();
                }
                aao = a;
            }
        }
        return aao;
    }

    public final void a() {
        if (f()) {
            e();
        }
    }

    public final aak b() {
        return this.e;
    }

    public final boolean a(String str) {
        return this.e.a(str);
    }

    private void e() {
        long currentTimeMillis = System.currentTimeMillis();
        if (Math.abs(currentTimeMillis - this.b) > 300000) {
            this.b = currentTimeMillis;
            this.f.b();
            this.d.d();
            this.f.a();
        }
    }

    public final boolean b(String str) {
        return this.d.a(str);
    }

    public final void a(NetworkType networkType) {
        if (NetworkReachability.b() && f()) {
            e();
        }
    }

    public final void c(String str) {
        if (!TextUtils.isEmpty(str) && Math.abs(this.c - System.currentTimeMillis()) >= 300000) {
            AMapLog.e("TokenManager OptStatus update", "onReceiveAmapPHeader ---> ".concat(String.valueOf(str)));
            try {
                int parseInt = Integer.parseInt(str);
                int c2 = this.d.c();
                StringBuilder sb = new StringBuilder("tokenStatus ---> ");
                sb.append(c2);
                sb.append(" status ");
                sb.append(parseInt);
                AMapLog.e("TokenManager OptStatus update", sb.toString());
                if (c2 != parseInt) {
                    if (parseInt != 0) {
                        this.c = System.currentTimeMillis();
                        this.d.a(1);
                        if (parseInt == 2) {
                            e();
                        }
                    } else if (this.c == Long.MIN_VALUE || Math.abs(this.c - System.currentTimeMillis()) >= 300000) {
                        this.d.a(0);
                    }
                }
            } catch (Exception e2) {
                if (bno.a) {
                    throw new RuntimeException(e2);
                }
            }
        }
    }

    public final boolean c() {
        if (this.e.a() && Math.abs(this.c - System.currentTimeMillis()) >= 300000) {
            return this.d.a();
        }
        return false;
    }

    private boolean f() {
        boolean z = this.e.a() && this.d.c() != 1 && (!this.d.a() || this.d.b());
        StringBuilder sb = new StringBuilder("needUploadParams ---> ");
        sb.append(z);
        sb.append(" mTokenManager.getOptStatus() ");
        sb.append(this.d.c());
        sb.append(" !mTokenManager.isTokenValid() ");
        sb.append(true ^ this.d.a());
        sb.append(" mTokenManager.isCacheParamChange() ");
        sb.append(this.d.b());
        AMapLog.e("TokenManager ParamUploader", sb.toString());
        return z;
    }
}
