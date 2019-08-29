package com.alipay.mobile.accountopenauth.biz.oauthstrategy;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthServiceManager;
import com.alipay.android.phone.inside.api.accountopenauth.IAccountOAuthService;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.behavior.BehaviorType;
import com.alipay.mobile.accountopenauth.api.OAuthStrategy;
import com.alipay.mobile.accountopenauth.common.CommonUtil;
import com.alipay.mobile.accountopenauth.common.OAuthBehaviorLogger;
import com.alipay.mobile.accountopenauth.common.OAuthTraceLogger;
import com.alipay.mobile.nebulacore.plugin.H5NotifyPlugin;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import java.util.Map;

public class H5OAuthStrategy implements OAuthStrategy {
    static final Object a = new Object();
    private String b = CommonUtil.b();
    private String c = CommonUtil.b();
    /* access modifiers changed from: private */
    public String d;
    private BroadcastReceiver e;
    /* access modifiers changed from: private */
    public Bundle f;
    /* access modifiers changed from: private */
    public String g;

    public H5OAuthStrategy(String str) {
        StringBuilder sb = new StringBuilder(H5NotifyPlugin.NOTIFY_PREFIX);
        sb.append(this.b);
        this.d = sb.toString();
        this.f = new Bundle();
        this.g = str;
    }

    public final Bundle a(String str, String str2, Bundle bundle) {
        try {
            Bundle bundle2 = new Bundle();
            bundle2.putString("url", str);
            Map<String, String> d2 = RunningConfig.d();
            d2.put(DictionaryKeys.DEV_APDIDTOKEN, CommonUtil.c());
            d2.put(DictionaryKeys.V2_APDID, CommonUtil.d());
            OAuthTraceLogger.a((String) "H5OAuthStrategy", "deviceEnv:".concat(String.valueOf(d2)));
            bundle2.putString("deviceEnv", CommonUtil.a(d2));
            bundle2.putString("insideEnv", RunningConfig.b(false));
            bundle2.putString("startAlipayForOauth", this.c);
            bundle2.putString("loginInfo", CommonUtil.a(bundle).toString());
            bundle2.putString("mqpNotifyName", this.b);
            bundle2.putString("mqpScheme", Token.SEPARATOR);
            Application a2 = LauncherApplication.a();
            this.e = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    OAuthTraceLogger.a((String) "H5OAuthStrategy", "receiver broadcast action is :".concat(String.valueOf(action)));
                    if (TextUtils.equals(action, H5OAuthStrategy.this.d)) {
                        Bundle extras = intent.getExtras();
                        OAuthTraceLogger.a((String) "H5OAuthStrategy", "h5auth result:".concat(String.valueOf(extras)));
                        if (extras != null && !TextUtils.isEmpty(extras.getString("auth_code"))) {
                            OAuthBehaviorLogger.a("action", "OpenAuthLogin_Web_Direct_Success", H5OAuthStrategy.this.g, "", "", BehaviorType.EVENT);
                            H5OAuthStrategy.this.f.putString("resultCode", "AUTH_SUCCESS");
                            H5OAuthStrategy.this.f.putAll(extras);
                        } else if (extras == null || !TextUtils.equals("USER_CANCEL_AUTH", extras.getString("result_code"))) {
                            OAuthBehaviorLogger.a("action", "OpenAuthLogin_Web_Direct_Failed", H5OAuthStrategy.this.g, "", "", BehaviorType.EVENT);
                            H5OAuthStrategy.this.f.putString("resultCode", "AUTH_FAILED");
                        } else {
                            OAuthBehaviorLogger.a("action", "OpenAuthLogin_Web_Direct_User_Cancel", H5OAuthStrategy.this.g, "", "", BehaviorType.EVENT);
                            H5OAuthStrategy.this.f.putString("resultCode", "AUTH_CANCELLED");
                        }
                        H5OAuthStrategy.a();
                        H5OAuthStrategy.d(H5OAuthStrategy.this);
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(this.d);
            LocalBroadcastManager.getInstance(a2).registerReceiver(this.e, intentFilter);
            IAccountOAuthService oAuthService = AccountOAuthServiceManager.getInstance().getOAuthService();
            IAccountOAuthService activeOAuthService = AccountOAuthServiceManager.getInstance().getActiveOAuthService();
            if (oAuthService != null) {
                OAuthTraceLogger.a((String) "H5OAuthStrategy", (String) "startH5Page, accountOAuthService is not null");
                oAuthService.openH5Page(bundle2);
                synchronized (a) {
                    try {
                        a.wait();
                    } catch (Throwable th) {
                        OAuthTraceLogger.b((String) "H5OAuthStrategy", th);
                    }
                }
                return this.f;
            }
            if (activeOAuthService != null) {
                OAuthTraceLogger.a((String) "H5OAuthStrategy", (String) "startH5Page,activeOAuthService is not null");
                activeOAuthService.openH5Page(bundle2);
                synchronized (a) {
                    try {
                        a.wait();
                    } catch (Throwable th2) {
                        OAuthTraceLogger.b((String) "H5OAuthStrategy", th2);
                    }
                }
            }
            return this.f;
        } catch (Throwable th3) {
            OAuthTraceLogger.a("H5OAuthStrategy", "doOAuth error", th3);
        }
    }

    static /* synthetic */ void a() {
        synchronized (a) {
            a.notifyAll();
        }
    }

    static /* synthetic */ void d(H5OAuthStrategy h5OAuthStrategy) {
        try {
            LocalBroadcastManager.getInstance(LauncherApplication.a()).unregisterReceiver(h5OAuthStrategy.e);
        } catch (Throwable th) {
            OAuthTraceLogger.a("H5OAuthStrategy", "unRegistBroadcastReceiver", th);
        }
    }
}
