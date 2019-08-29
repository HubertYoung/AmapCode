package com.alipay.mobile.accountopenauth.biz.oauthstrategy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthServiceManager;
import com.alipay.android.phone.inside.api.accountopenauth.IFastOAuthService;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.mobile.accountopenauth.api.OAuthStrategy;
import com.alipay.mobile.accountopenauth.biz.FastOAuthDataManager;
import com.alipay.mobile.accountopenauth.biz.FastOAuthDataManager.AuthResultListener;
import com.alipay.mobile.accountopenauth.common.CommonUtil;
import com.alipay.mobile.accountopenauth.common.OAuthBehaviorLogger;
import com.alipay.mobile.accountopenauth.common.OAuthTraceLogger;
import com.alipay.mobile.accountopenauth.ui.FastOpenAuthActivity;
import com.autonavi.bundle.feedback.ajx.ModuleFeedBack;

public class FastOAuthStrategy implements OAuthStrategy {
    /* access modifiers changed from: private */
    public final Object a = new Object();
    /* access modifiers changed from: private */
    public Bundle b = new Bundle();

    public final Bundle a(String str, String str2, Bundle bundle) {
        if (CommonUtil.a()) {
            OAuthTraceLogger.a((String) "FastOAuthStrategy", (String) "startAlipayFastOpenAuth double click");
            return null;
        }
        FastOAuthDataManager a2 = FastOAuthDataManager.a();
        a2.c();
        boolean z = bundle.getBoolean("isRecommend");
        long j = bundle.getLong("authUUID");
        OAuthTraceLogger.a((String) "FastOAuthStrategy", "isRecommend: ".concat(String.valueOf(z)));
        IFastOAuthService fastOAuthService = AccountOAuthServiceManager.getInstance().getFastOAuthService();
        if (fastOAuthService == null || fastOAuthService.canShowFastPage(j)) {
            if (z) {
                a2.a(str2, bundle);
                if (fastOAuthService == null || fastOAuthService.canShowFastPage(j)) {
                    OAuthBehaviorLogger.a("action", "Enter_OpenAuthLogin_Native_Fast", "fastoauth", ModuleFeedBack.RECOMMEND, "", BehaviorType.EVENT);
                    a(str2, bundle, (String) ModuleFeedBack.RECOMMEND);
                } else {
                    OAuthTraceLogger.a((String) "FastOAuthStrategy", (String) "canShowFastPage false step 3");
                    this.b.putString("resultCode", "AUTH_MC_CANCELLED");
                    return this.b;
                }
            } else {
                OAuthBehaviorLogger.a("action", "Enter_OpenAuthLogin_Native_Fast", "fastoauth", "not_recommend", "", BehaviorType.EVENT);
                a(str2, bundle, (String) "not_recommend");
            }
            synchronized (this.a) {
                try {
                    this.a.wait();
                } catch (Throwable th) {
                    OAuthTraceLogger.b((String) "FastOAuthStrategy", th);
                }
            }
            return this.b;
        }
        OAuthTraceLogger.a((String) "FastOAuthStrategy", (String) "canShowFastPage false step 2");
        this.b.putString("resultCode", "AUTH_MC_CANCELLED");
        return this.b;
    }

    private void a(String str, Bundle bundle, final String str2) {
        FastOAuthDataManager.a().a((AuthResultListener) new AuthResultListener() {
            public final void a(Bundle bundle) {
                if (bundle == null || TextUtils.isEmpty(bundle.getString("auth_code"))) {
                    OAuthBehaviorLogger.a("action", "OpenAuthLogin_Native_Fast_Failed", "fastoauth", str2, "", BehaviorType.EVENT);
                    FastOAuthStrategy.this.b.putString("resultCode", "AUTH_FAILED");
                } else {
                    OAuthBehaviorLogger.a("action", "OpenAuthLogin_Native_Fast_Success", "fastoauth", str2, "", BehaviorType.EVENT);
                    bundle.putString("resultCode", "AUTH_SUCCESS");
                    FastOAuthStrategy.this.b.putAll(bundle);
                }
                synchronized (FastOAuthStrategy.this.a) {
                    FastOAuthStrategy.this.a.notifyAll();
                }
            }

            public final void a() {
                OAuthBehaviorLogger.a("action", "OpenAuthLogin_Native_Fast_Failed", "fastoauth", str2, "", BehaviorType.EVENT);
                FastOAuthStrategy.this.b.putString("resultCode", "AUTH_FAILED");
                synchronized (FastOAuthStrategy.this.a) {
                    FastOAuthStrategy.this.a.notifyAll();
                }
            }

            public final void b() {
                OAuthBehaviorLogger.a("action", "OpenAuthLogin_Native_Fast_Cancelled", "fastoauth", str2, "", BehaviorType.EVENT);
                FastOAuthStrategy.this.b.putString("resultCode", "AUTH_CANCELLED");
                synchronized (FastOAuthStrategy.this.a) {
                    FastOAuthStrategy.this.a.notifyAll();
                }
            }

            public final void c() {
                OAuthBehaviorLogger.a("action", "OpenAuthLogin_Native_Fast_MC_Cancelled", "fastoauth", str2, "", BehaviorType.EVENT);
                FastOAuthStrategy.this.b.putString("resultCode", "AUTH_MC_CANCELLED");
                synchronized (FastOAuthStrategy.this.a) {
                    FastOAuthStrategy.this.a.notifyAll();
                }
            }
        });
        Intent intent = new Intent(LauncherApplication.a(), FastOpenAuthActivity.class);
        if (bundle != null) {
            bundle.putString("authUrlParams", str);
            bundle.putLong("sourceUUID", bundle.getLong("authUUID"));
        }
        intent.putExtras(bundle);
        intent.setFlags(268435456);
        LauncherApplication.a().startActivity(intent);
    }
}
