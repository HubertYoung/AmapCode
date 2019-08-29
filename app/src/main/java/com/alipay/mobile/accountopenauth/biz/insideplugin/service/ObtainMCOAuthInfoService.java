package com.alipay.mobile.accountopenauth.biz.insideplugin.service;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthServiceManager;
import com.alipay.android.phone.inside.api.accountopenauth.IAccountOAuthCallback;
import com.alipay.android.phone.inside.api.accountopenauth.IAccountOAuthService;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.mobile.accountopenauth.common.OAuthBehaviorLogger;
import com.alipay.mobile.accountopenauth.common.OAuthTraceLogger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ObtainMCOAuthInfoService implements IInsideService<Bundle, Bundle> {
    private ExecutorService a = Executors.newCachedThreadPool();

    public /* synthetic */ void start(IInsideServiceCallback iInsideServiceCallback, Object obj) throws Exception {
        Bundle bundle = (Bundle) obj;
        String str = "";
        String str2 = "";
        String str3 = "";
        String str4 = "";
        String str5 = "";
        if (bundle != null) {
            str = bundle.getString("cAuthUUID");
            str2 = bundle.getString("needOpenAuth");
            str3 = bundle.getString("bizSource");
            str4 = bundle.getString("needRefreshToken");
            str5 = bundle.getString("obtainAuthInfoScene");
        }
        final String str6 = str;
        final String str7 = str2;
        final String str8 = str3;
        final String str9 = str4;
        final String str10 = str5;
        ExecutorService executorService = this.a;
        final IInsideServiceCallback iInsideServiceCallback2 = iInsideServiceCallback;
        AnonymousClass1 r5 = new Runnable() {
            public void run() {
                if (TextUtils.equals("Scene_ActiveLogin", str10)) {
                    ObtainMCOAuthInfoService.a(ObtainMCOAuthInfoService.this, iInsideServiceCallback2, AccountOAuthServiceManager.getInstance().getActiveOAuthService(), str9, str7, str6, str8, str10);
                    return;
                }
                ObtainMCOAuthInfoService.a(ObtainMCOAuthInfoService.this, iInsideServiceCallback2, AccountOAuthServiceManager.getInstance().getOAuthService(), str9, str7, str6, str8, str10);
            }
        };
        executorService.execute(r5);
    }

    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    public /* synthetic */ void start(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    static /* synthetic */ void a(ObtainMCOAuthInfoService obtainMCOAuthInfoService, final IInsideServiceCallback iInsideServiceCallback, IAccountOAuthService iAccountOAuthService, String str, String str2, String str3, String str4, String str5) {
        final Bundle bundle = new Bundle();
        StringBuilder sb = new StringBuilder("isNewOpenAuthFlow true , needRefreshToken = ");
        sb.append(str);
        sb.append(",obtainAuthInfoSource = ");
        sb.append(str5);
        OAuthTraceLogger.a((String) "ObtainMCOAuthInfoService", sb.toString());
        if (iAccountOAuthService != null) {
            try {
                OAuthTraceLogger.a((String) "ObtainMCOAuthInfoService", (String) "startGetAuthLoginInfo authService != null");
                iAccountOAuthService.getMCAuthLoginInfo(str, str2, str3, new IAccountOAuthCallback() {
                    public void onAuthResult(String str, String str2, String str3, Bundle bundle) {
                        StringBuilder sb = new StringBuilder("onAuthResult mcUid：");
                        sb.append(str);
                        sb.append("，alipayUid：");
                        sb.append(str2);
                        sb.append("，accessToken：");
                        sb.append(str3);
                        OAuthTraceLogger.a((String) "ObtainMCOAuthInfoService", sb.toString());
                        OAuthBehaviorLogger.a("action", "getMCAuthInfoSuccess", str2, str, str3, BehaviorType.EVENT);
                        bundle.putString("openMcUid", str);
                        bundle.putString("alipayUserId", str2);
                        bundle.putString("authToken", str3);
                        iInsideServiceCallback.onComplted(bundle);
                    }
                });
            } catch (Throwable th) {
                OAuthTraceLogger.a("ObtainMCOAuthInfoService", "getAuthLoginInfo error", th);
                iInsideServiceCallback.onComplted(bundle);
            }
        } else {
            OAuthBehaviorLogger.a("action", "getMCAuthInfoNoImpl", str4, str5, "", BehaviorType.EVENT);
            LoggerFactory.b();
            iInsideServiceCallback.onComplted(bundle);
        }
    }
}
