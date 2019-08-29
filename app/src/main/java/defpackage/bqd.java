package defpackage;

import android.content.SharedPreferences;
import com.alibaba.wireless.security.securitybodysdk.BuildConfig;
import com.alipay.mobile.security.bio.workspace.Env;
import com.autonavi.amap.app.AMapAppGlobal;

/* renamed from: bqd reason: default package */
/* compiled from: NetworkEnvConfig */
public final class bqd {
    public SharedPreferences a;

    /* renamed from: bqd$a */
    /* compiled from: NetworkEnvConfig */
    public static class a {
        public static bqd a = new bqd(0);
    }

    /* synthetic */ bqd(byte b) {
        this();
    }

    private bqd() {
    }

    public final int a() {
        int i = 0;
        if (this.a == null) {
            this.a = AMapAppGlobal.getApplication().getSharedPreferences("network_env_cfg", 0);
        }
        String b = bqc.a().b();
        if (BuildConfig.FLAVOR.equals(b)) {
            i = 1;
        } else if (Env.NAME_PRE.equals(b)) {
            i = 2;
        }
        return this.a.getInt("package_type", i);
    }
}
