package com.alipay.mobile.accountopenauth.biz.oauthstrategy;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthServiceManager;
import com.alipay.android.phone.inside.api.accountopenauth.IAccountOAuthService;
import com.alipay.android.phone.inside.api.accountopenauth.IFastOAuthService;
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

public class H5RegOAuthStrategy implements OAuthStrategy {
    private String a = CommonUtil.b();
    private String b = CommonUtil.b();
    /* access modifiers changed from: private */
    public String c;
    /* access modifiers changed from: private */
    public String d;
    private final Object e;
    private BroadcastReceiver f;
    /* access modifiers changed from: private */
    public String g;
    /* access modifiers changed from: private */
    public Bundle h;
    /* access modifiers changed from: private */
    public String i;

    public H5RegOAuthStrategy(String str) {
        StringBuilder sb = new StringBuilder(H5NotifyPlugin.NOTIFY_PREFIX);
        sb.append(this.a);
        this.c = sb.toString();
        StringBuilder sb2 = new StringBuilder(H5NotifyPlugin.NOTIFY_PREFIX);
        sb2.append(this.b);
        this.d = sb2.toString();
        this.e = new Object();
        this.h = new Bundle();
        this.i = str;
    }

    public final Bundle a(String str, String str2, Bundle bundle) {
        try {
            Bundle bundle2 = new Bundle();
            String e2 = CommonUtil.e();
            Object[] objArr = new Object[2];
            objArr[0] = Uri.encode(str);
            objArr[1] = bundle != null ? bundle.getString("phoneNumber") : "";
            String format = String.format(e2, objArr);
            bundle2.putString("url", format);
            OAuthTraceLogger.a((String) "H5RegOAuthStrategy", "regUrl=".concat(String.valueOf(format)));
            this.g = str;
            Map<String, String> d2 = RunningConfig.d();
            d2.put(DictionaryKeys.DEV_APDIDTOKEN, CommonUtil.c());
            d2.put(DictionaryKeys.V2_APDID, CommonUtil.d());
            OAuthTraceLogger.a((String) "H5RegOAuthStrategy", "deviceEnv:".concat(String.valueOf(d2)));
            bundle2.putString("deviceEnv", CommonUtil.a(d2));
            bundle2.putString("insideEnv", RunningConfig.b(false));
            bundle2.putString("startAlipayForOauth", this.b);
            bundle2.putString("mqpNotifyName", this.a);
            bundle2.putString("mqpScheme", Token.SEPARATOR);
            Application a2 = LauncherApplication.a();
            this.f = new BroadcastReceiver() {
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    OAuthTraceLogger.a((String) "H5RegOAuthStrategy", "receiver broadcast action is :".concat(String.valueOf(action)));
                    if (TextUtils.equals(action, H5RegOAuthStrategy.this.c)) {
                        Bundle extras = intent.getExtras();
                        OAuthTraceLogger.a((String) "H5RegOAuthStrategy", "h5regauth result:".concat(String.valueOf(extras)));
                        if (extras != null && !TextUtils.isEmpty(extras.getString("auth_code"))) {
                            OAuthBehaviorLogger.a("action", "OpenAuthLogin_Web_Reg_Success", H5RegOAuthStrategy.this.i, "", "", BehaviorType.EVENT);
                            H5RegOAuthStrategy.this.h.putString("resultCode", "AUTH_SUCCESS");
                            H5RegOAuthStrategy.this.h.putAll(extras);
                        } else if (extras == null || !TextUtils.equals("USER_CANCEL_AUTH", extras.getString("result_code"))) {
                            OAuthBehaviorLogger.a("action", "OpenAuthLogin_Web_Reg_Failed", H5RegOAuthStrategy.this.i, "", "", BehaviorType.EVENT);
                            H5RegOAuthStrategy.this.h.putString("resultCode", "AUTH_FAILED");
                        } else {
                            OAuthBehaviorLogger.a("action", "OpenAuthLogin_Web_Reg_User_Cancel", H5RegOAuthStrategy.this.i, "", "", BehaviorType.EVENT);
                            H5RegOAuthStrategy.this.h.putString("resultCode", "AUTH_CANCELLED");
                        }
                        H5RegOAuthStrategy.d(H5RegOAuthStrategy.this);
                        H5RegOAuthStrategy.e(H5RegOAuthStrategy.this);
                        return;
                    }
                    if (TextUtils.equals(action, H5RegOAuthStrategy.this.d)) {
                        new Thread(new Runnable() {
                            public void run() {
                                H5RegOAuthStrategy.this.h = new StrategyContext(new WalletOAuthStrategy(H5RegOAuthStrategy.this.i)).a(H5RegOAuthStrategy.this.g, null, null);
                                H5RegOAuthStrategy.d(H5RegOAuthStrategy.this);
                                H5RegOAuthStrategy.e(H5RegOAuthStrategy.this);
                            }
                        }, "startAlipayAppAuth_h5regauth").start();
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(this.c);
            intentFilter.addAction(this.d);
            LocalBroadcastManager.getInstance(a2).registerReceiver(this.f, intentFilter);
            IAccountOAuthService oAuthService = AccountOAuthServiceManager.getInstance().getOAuthService();
            IAccountOAuthService activeOAuthService = AccountOAuthServiceManager.getInstance().getActiveOAuthService();
            IFastOAuthService fastOAuthService = AccountOAuthServiceManager.getInstance().getFastOAuthService();
            if (oAuthService != null) {
                OAuthTraceLogger.a((String) "H5RegOAuthStrategy", (String) "startH5Page,accountOAuthService is not null");
                oAuthService.openH5Page(bundle2);
                synchronized (this.e) {
                    try {
                        this.e.wait();
                    } catch (Throwable th) {
                        OAuthTraceLogger.b((String) "H5RegOAuthStrategy", th);
                    }
                }
                return this.h;
            } else if (activeOAuthService != null) {
                OAuthTraceLogger.a((String) "H5RegOAuthStrategy", (String) "startH5Page,activeOAuthService is not null");
                activeOAuthService.openH5Page(bundle2);
                synchronized (this.e) {
                    try {
                        this.e.wait();
                    } catch (Throwable th2) {
                        OAuthTraceLogger.b((String) "H5RegOAuthStrategy", th2);
                    }
                }
                return this.h;
            } else {
                if (fastOAuthService != null) {
                    OAuthTraceLogger.a((String) "H5RegOAuthStrategy", (String) "startH5Page,fastOAuthService is not null");
                    fastOAuthService.openH5Page(bundle2);
                    synchronized (this.e) {
                        try {
                            this.e.wait();
                        } catch (Throwable th3) {
                            OAuthTraceLogger.b((String) "H5RegOAuthStrategy", th3);
                        }
                    }
                }
                return this.h;
            }
        } catch (Throwable th4) {
            OAuthTraceLogger.a("H5RegOAuthStrategy", "doOAuth error", th4);
        }
    }

    static /* synthetic */ void d(H5RegOAuthStrategy h5RegOAuthStrategy) {
        synchronized (h5RegOAuthStrategy.e) {
            h5RegOAuthStrategy.e.notifyAll();
        }
    }

    static /* synthetic */ void e(H5RegOAuthStrategy h5RegOAuthStrategy) {
        try {
            LocalBroadcastManager.getInstance(LauncherApplication.a()).unregisterReceiver(h5RegOAuthStrategy.f);
        } catch (Throwable th) {
            OAuthTraceLogger.a("H5RegOAuthStrategy", "unRegistBroadcastReceiver", th);
        }
    }
}
