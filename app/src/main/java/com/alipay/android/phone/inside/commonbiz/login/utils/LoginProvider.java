package com.alipay.android.phone.inside.commonbiz.login.utils;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import com.alipay.android.phone.inside.bizadapter.service.InteractionManager;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.mobile.tinyappcustom.api.MiniProgramAuthService;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import org.json.JSONObject;

public class LoginProvider {
    public final String a(JSONObject jSONObject, boolean z) throws Exception {
        Bundle bundle;
        if (LoginUtils.d(jSONObject)) {
            bundle = LoginUtils.b(jSONObject);
        } else if (LoginUtils.a(jSONObject)) {
            bundle = LoginUtils.c();
        } else {
            bundle = LoginUtils.b();
        }
        String string = a((String) "LOGIN_EXTERNAL_SERVICE", bundle).getString("loginStatus", "");
        if (TextUtils.equals(string, "openAuthTokenInvalid") && z) {
            LoggerFactory.e().a((String) "commonbiz", (String) "OpenAuthTokenInvalid", Base64.encodeToString(bundle.toString().getBytes(), 2));
            try {
                Bundle bundle2 = new Bundle();
                bundle2.putString("KEY_TYPE", "BROADCAST");
                bundle2.putString("KEY_ACTION", MiniProgramAuthService.LOGIN_TOKEN_INVALID);
                bundle2.putBundle("KEY_VALUE", bundle);
                InteractionManager.a(bundle2);
                LoggerFactory.d().b("commonbiz", BehaviorType.EVENT, "OpenAuthTokenInvalidBr");
            } catch (Throwable th) {
                LoggerFactory.e().a((String) "commonbiz", (String) "OpenAuthTokenInvalidBrEx", th);
            }
        }
        LoggerFactory.f().e("inside", "LoginProvider::doLogin > loginStatus=".concat(String.valueOf(string)));
        return string;
    }

    private Bundle a(String str, Bundle bundle) {
        final Object obj = new Object();
        final Bundle bundle2 = new Bundle();
        ServiceExecutor.a(str, bundle, new IInsideServiceCallback<Bundle>() {
            public /* synthetic */ void onComplted(Object obj) {
                Bundle bundle = (Bundle) obj;
                LoggerFactory.f().e("auth", "startLogin result：".concat(String.valueOf(bundle)));
                bundle2.putAll(bundle);
                synchronized (obj) {
                    obj.notifyAll();
                }
            }

            public void onException(Throwable th) {
                LoggerFactory.e().a((String) "auth", (String) "LoginAuthEndEx", th);
                synchronized (obj) {
                    obj.notifyAll();
                }
            }
        });
        synchronized (obj) {
            try {
                obj.wait(90000);
            } catch (Throwable th) {
                LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "StartServiceWaitEx", th);
            }
        }
        return bundle2;
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x0017 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a() {
        /*
            r4 = this;
            java.lang.Object r0 = new java.lang.Object
            r0.<init>()
            java.lang.String r1 = "LOGOUT_EXTERNAL_SERVICE"
            com.alipay.android.phone.inside.commonbiz.login.utils.LoginProvider$1 r2 = new com.alipay.android.phone.inside.commonbiz.login.utils.LoginProvider$1
            r2.<init>(r0)
            r3 = 0
            com.alipay.android.phone.inside.framework.service.ServiceExecutor.a(r1, r3, r2)
            monitor-enter(r0)
            r0.wait()     // Catch:{ Throwable -> 0x0017 }
            goto L_0x0022
        L_0x0015:
            r1 = move-exception
            goto L_0x0024
        L_0x0017:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()     // Catch:{ all -> 0x0015 }
            java.lang.String r2 = "inside"
            java.lang.String r3 = ""
            r1.e(r2, r3)     // Catch:{ all -> 0x0015 }
        L_0x0022:
            monitor-exit(r0)     // Catch:{ all -> 0x0015 }
            return
        L_0x0024:
            monitor-exit(r0)     // Catch:{ all -> 0x0015 }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.commonbiz.login.utils.LoginProvider.a():void");
    }
}
