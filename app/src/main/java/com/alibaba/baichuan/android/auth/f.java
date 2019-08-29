package com.alibaba.baichuan.android.auth;

import android.text.TextUtils;
import com.alibaba.baichuan.android.trade.adapter.ut.AlibcUserTracker;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import com.alibaba.sdk.trade.container.utils.AlibcComponentTrack;
import com.taobao.tao.remotebusiness.auth.AuthListener;
import com.taobao.tao.remotebusiness.auth.IRemoteAuth;
import java.util.List;

public class f implements IRemoteAuth {
    private boolean a;

    static class a {
        public static f a = new f();
    }

    class b implements e {
        AuthListener a;

        b(AuthListener authListener) {
            this.a = authListener;
        }

        public void a() {
            f.this.a(false);
            if (this.a != null) {
                this.a.onAuthSuccess();
            }
        }

        public void a(String str, String str2) {
            f.this.a(false);
            if (this.a != null) {
                this.a.onAuthFail(str, str2);
            }
        }

        public void b() {
            f.this.a(false);
            if (this.a != null) {
                this.a.onAuthCancel(AlibcComponentTrack.MTOP_ERRNO_AUTH_CANCEL, "用户取消授权");
            }
        }

        public void c() {
            f.this.a(true);
        }
    }

    private f() {
        this.a = false;
    }

    public static f a() {
        return a.a;
    }

    /* access modifiers changed from: private */
    public synchronized void a(boolean z) {
        this.a = z;
    }

    public void authorize(String str, String str2, String str3, boolean z, AuthListener authListener) {
        StringBuilder sb = new StringBuilder("call authorize authParam = ");
        sb.append(str);
        sb.append(" api_v = ");
        sb.append(str2);
        sb.append(" errorInfo = ");
        sb.append(str3);
        AlibcLogger.d("Alibc", sb.toString());
        a(true);
        if (!TextUtils.isEmpty(str)) {
            List a2 = AlibcAuth.a(str);
            AlibcAuth.postHintList(str, str3);
            AlibcAuth.auth(a2, str3, z, (e) new b(authListener));
        } else {
            AlibcAuth.auth(str2, str3, z, (e) new b(authListener));
        }
        if (!TextUtils.isEmpty(str3)) {
            AlibcUserTracker.getInstance().sendUseabilityFailure("BCPCSDK", "Hint_List_Error", "190101", "权限列表配置错误");
        }
    }

    public String getAuthToken() {
        String b2 = d.a().b();
        AlibcLogger.d("Alibc", "getAuthToken = ".concat(String.valueOf(b2)));
        return b2;
    }

    public boolean isAuthInfoValid() {
        boolean d = d.a().d();
        AlibcLogger.d("Alibc", "isAuthInfoValid = ".concat(String.valueOf(d)));
        return d;
    }

    public synchronized boolean isAuthorizing() {
        StringBuilder sb = new StringBuilder("isAuthorizing = ");
        sb.append(this.a);
        AlibcLogger.d("Alibc", sb.toString());
        return this.a;
    }
}
