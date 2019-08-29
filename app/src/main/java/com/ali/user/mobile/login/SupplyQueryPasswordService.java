package com.ali.user.mobile.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.login.ui.LoginQuerypwdActivity;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;

public class SupplyQueryPasswordService {
    private static SupplyQueryPasswordService a;
    private int b = -1;
    private Activity c = null;
    private final Object d = new Object();

    private SupplyQueryPasswordService() {
    }

    public static SupplyQueryPasswordService a() {
        if (a == null) {
            synchronized (SupplyQueryPasswordService.class) {
                try {
                    if (a == null) {
                        a = new SupplyQueryPasswordService();
                    }
                }
            }
        }
        return a;
    }

    public final void a(int i) {
        this.b = i;
        AliUserLog.c("SupplyQueryPasswordService", String.format("notify supply query password result:%s", new Object[]{Integer.valueOf(i)}));
        synchronized (this.d) {
            try {
                this.d.notifyAll();
            } catch (Exception e) {
                AliUserLog.a((String) "SupplyQueryPasswordService", (Throwable) e);
            }
        }
    }

    public final boolean a(Context context, String str, String str2) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new RuntimeException("can not call supplyQueryPassword in main thread");
        }
        Intent intent = new Intent(context, LoginQuerypwdActivity.class);
        intent.putExtra("loginId", str);
        intent.putExtra(H5AppUtil.scene, str2);
        if (!(context instanceof Activity)) {
            intent.addFlags(268435456);
        }
        context.startActivity(intent);
        this.b = -1;
        synchronized (this.d) {
            try {
                this.d.wait();
            } catch (Exception e) {
                AliUserLog.a((String) "SupplyQueryPasswordService", (Throwable) e);
            }
        }
        return this.b == 1000;
    }

    public final void a(LoginQuerypwdActivity loginQuerypwdActivity) {
        this.c = loginQuerypwdActivity;
    }

    public final void b() {
        if (this.c != null) {
            this.c.finish();
            this.c = null;
        }
    }
}
