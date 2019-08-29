package com.ali.user.mobile.authlogin.auth.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import com.ali.user.mobile.authlogin.auth.IAlipaySSOAuthLoginAPI;
import com.ali.user.mobile.authlogin.auth.IAlipaySSOEventHandler;
import com.ali.user.mobile.authlogin.auth.IAlipaySSOPreHandler;
import com.ali.user.mobile.authlogin.common.AlipayDataResolver;
import com.ali.user.mobile.authlogin.common.AuthLoginSecurityEncryptor;
import com.ali.user.mobile.authlogin.common.AuthLoginSecuritySignature;
import com.ali.user.mobile.authlogin.common.AuthLoginUtils;
import com.ali.user.mobile.authlogin.exception.ParamNullException;
import com.ali.user.mobile.authlogin.exception.PreAuthLoginException;
import com.ali.user.mobile.info.AppInfo;
import com.ali.user.mobile.log.AliUserLog;
import com.ali.user.mobile.log.LoggerUtils;
import com.ali.user.mobile.utils.CommonUtil;
import com.alipay.android.phone.inside.framework.LauncherApplication;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AlipaySSOAuthLoginApiImpl implements IAlipaySSOAuthLoginAPI {
    private Context a;

    public AlipaySSOAuthLoginApiImpl(Context context) {
        LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "createSsoApi");
        this.a = context;
    }

    private boolean b() {
        boolean z = false;
        try {
            int a2 = new AlipayDataResolver(this.a).a(0);
            if (a2 >= 966042200) {
                try {
                    LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", "auth login supportVersion = ".concat(String.valueOf(a2)));
                    return true;
                } catch (Throwable th) {
                    th = th;
                    z = true;
                }
            } else {
                if (a2 == 0) {
                    PackageInfo packageInfo = this.a.getPackageManager().getPackageInfo("com.eg.android.AlipayGphone", 16384);
                    if (packageInfo != null && packageInfo.versionCode > 93) {
                        return true;
                    }
                }
                return false;
            }
        } catch (Throwable th2) {
            th = th2;
            LoggerFactory.f().b("AliAuth_AlipaySSOAuthLoginApiImpl", "isAlipayAppSurpportAPI error", th);
            return z;
        }
    }

    public final boolean a(Intent intent) {
        return (intent == null || intent.getExtras() == null || !intent.getExtras().containsKey("authStateKey")) ? false : true;
    }

    public final void a(Intent intent, IAlipaySSOEventHandler iAlipaySSOEventHandler) throws ParamNullException {
        LoggerUtils.a("event", "AliAuthLoginSDK_handleURL", "ALIAUTH_0415_03", "");
        if (intent == null) {
            LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "intent 为  null");
            throw new ParamNullException((String) "intent can not be null");
        } else if (iAlipaySSOEventHandler == null) {
            LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "eventHandler 为  null ,业务方需传递一个非空实例");
            throw new ParamNullException((String) "callabck IAlipaySSOEventHandler object can not be null");
        } else {
            Bundle extras = intent.getExtras();
            int i = 1004;
            if (extras != null) {
                String string = extras.getString("code");
                String string2 = extras.getString("msg");
                String string3 = extras.getString("token");
                boolean z = extras.getBoolean("success");
                int i2 = extras.getInt("authStateKey");
                LoggerUtils.a("event", "AliAuthLoginSDK_authCode", "ALIAUTH_0415_04", string3);
                if (!z || TextUtils.isEmpty(string3)) {
                    if (i2 == 3001) {
                        iAlipaySSOEventHandler.onAuthFailed(1001);
                        LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "用户取消返回码 -2");
                        i = 1001;
                    } else if (i2 == 3002) {
                        iAlipaySSOEventHandler.onAuthFailed(1002);
                        LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "用户更换账户 -3");
                        i = 1002;
                    } else {
                        iAlipaySSOEventHandler.onAuthFailed(1004);
                        LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "授权失败其他 -1");
                    }
                    LoggerUtils.a("event", "AliAuthLoginSDK_action", "ALIAUTH_0415_05", String.valueOf(i));
                } else {
                    LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "开始登录");
                    iAlipaySSOEventHandler.startLogin(string3);
                }
                TraceLogger f = LoggerFactory.f();
                StringBuilder sb = new StringBuilder("授权返回结果code=");
                sb.append(string);
                sb.append(" msg=");
                sb.append(string2);
                sb.append(" token=");
                sb.append(string3);
                sb.append(" success=");
                sb.append(z);
                f.a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", sb.toString());
                return;
            }
            LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "授权返回 intent.getExtras() is null");
            iAlipaySSOEventHandler.onAuthFailed(1004);
        }
    }

    public final Bundle a() {
        Bundle bundle = new Bundle();
        if (TextUtils.isEmpty(AppInfo.getInstance().getApdidToken())) {
            AliUserLog.c("AliAuth_AlipaySSOAuthLoginApiImpl", "getAuthLoginInfo apdidToken is null,sync fetch");
            if (TextUtils.isEmpty(AppInfo.getInstance().getApdidtokenResultTimeout())) {
                AliUserLog.c("AliAuth_AlipaySSOAuthLoginApiImpl", "getAuthLoginInfo syncgetapdidtoken also is null,return null");
                return null;
            }
        }
        String a2 = AuthLoginSecurityEncryptor.a(this.a, AuthLoginUtils.a(), AppInfo.getInstance().getApdidToken());
        if (TextUtils.isEmpty(a2)) {
            AliUserLog.c("AliAuth_AlipaySSOAuthLoginApiImpl", "getAuthLoginInfo aesEncryptApdidToken is null,return null");
            return null;
        }
        String valueOf = String.valueOf(System.currentTimeMillis());
        String a3 = AuthLoginSecuritySignature.a(this.a, AuthLoginUtils.a(), AuthLoginUtils.b(), a(AuthLoginUtils.a(), valueOf, a2));
        if (TextUtils.isEmpty(a3)) {
            AliUserLog.c("AliAuth_AlipaySSOAuthLoginApiImpl", "getAuthLoginInfo atlasSignData is null,return null");
            return null;
        }
        bundle.putString("packageName", this.a.getPackageName());
        bundle.putString("fullClassName", "com.ali.user.mobile.external.AuthLoginResultActivity");
        bundle.putString("uuid", a2);
        bundle.putString("timestamp", valueOf);
        bundle.putString("signData", a3);
        bundle.putString("appKey", AuthLoginUtils.a());
        bundle.putString("callbackIsBySelf", "callbackNotBySelf");
        return bundle;
    }

    /* access modifiers changed from: private */
    public void a(Activity activity, IAlipaySSOPreHandler iAlipaySSOPreHandler, String str, String str2, String str3, String str4, String str5) {
        LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "openAlipayLogin");
        String a2 = AuthLoginSecurityEncryptor.a(this.a, str, str4);
        String valueOf = String.valueOf(System.currentTimeMillis());
        String a3 = AuthLoginSecuritySignature.a(this.a, str, str5, a(str, valueOf, a2));
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("packagename=");
        sb.append(str2);
        sb.append(" classname=");
        sb.append(str3);
        sb.append(" apdidtoken=");
        sb.append(a2);
        sb.append(" time=");
        sb.append(valueOf);
        sb.append(" signdata=");
        sb.append(a3);
        sb.append(" appkey=");
        sb.append(str);
        sb.append("wbkey=");
        sb.append(str5);
        f.a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", sb.toString());
        if (TextUtils.isEmpty(a2) || TextUtils.isEmpty(a3)) {
            iAlipaySSOPreHandler.preAuthFailed(1006);
            return;
        }
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            String encode = URLEncoder.encode(a2, "utf-8");
            LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", "after encode aesEncryptApdidToken=".concat(String.valueOf(encode)));
            HashMap hashMap = new HashMap();
            hashMap.put("packageName", str2);
            hashMap.put("fullClassName", str3);
            hashMap.put("uuid", encode);
            hashMap.put("timestamp", valueOf);
            hashMap.put("signData", a3);
            hashMap.put("appKey", str);
            String a4 = AuthLoginUtils.a("alipays://platformapi/startApp?appId=20000267", hashMap);
            LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", a4);
            intent.setData(Uri.parse(a4));
            activity.startActivity(intent);
            iAlipaySSOPreHandler.openAlipaySuccess();
            LoggerUtils.a("event", "AliAuthLoginSDK_start", "ALIAUTH_0415_02", "scheme=".concat(String.valueOf(a4)));
        } catch (Throwable th) {
            LoggerFactory.f().b("AliAuth_AlipaySSOAuthLoginApiImpl", "openAlipayLogin error", th);
            iAlipaySSOPreHandler.preAuthFailed(2001);
        }
    }

    private static String a(String str, String str2, String str3) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("appKey", str);
        linkedHashMap.put("timestamp", str2);
        linkedHashMap.put("uuid", str3);
        return AuthLoginUtils.a(linkedHashMap);
    }

    public final void a(Activity activity, IAlipaySSOPreHandler iAlipaySSOPreHandler) throws ParamNullException, PreAuthLoginException {
        if (!CommonUtil.a(LauncherApplication.a().getApplicationContext())) {
            LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "alipay has not install");
            throw new PreAuthLoginException((String) "alipay has not install");
        } else if (!b()) {
            LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "alipay authlogin api not support");
            throw new PreAuthLoginException((String) "alipay authlogin api not support");
        } else {
            final String a2 = AuthLoginUtils.a();
            final String packageName = this.a.getPackageName();
            final String b = AuthLoginUtils.b();
            if (TextUtils.isEmpty(a2) || TextUtils.isEmpty(packageName) || TextUtils.isEmpty("com.ali.user.mobile.external.AuthLoginResultActivity") || TextUtils.isEmpty(b)) {
                LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "some key word is null or empty string");
                throw new ParamNullException((String) "one or some must need param is null");
            }
            LoggerUtils.a("event", "AliAuthLoginSDK_begin", "ALIAUTH_0415_12", "");
            String apdidToken = AppInfo.getInstance().getApdidToken();
            if (!TextUtils.isEmpty(apdidToken)) {
                AppInfo.getInstance().setAuthApdidToken(apdidToken);
                LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "first init apdidtoken success");
                a(activity, iAlipaySSOPreHandler, a2, packageName, "com.ali.user.mobile.external.AuthLoginResultActivity", apdidToken, b);
                return;
            }
            LoggerUtils.a("event", "AliAuthLoginSDK_initUuidFailure", "ALIAUTH_0415_07", "");
            LoggerFactory.f().a((String) "AliAuth_AlipaySSOAuthLoginApiImpl", (String) "begin syncHandleApdidtoken");
            iAlipaySSOPreHandler.showPreProgress();
            final IAlipaySSOPreHandler iAlipaySSOPreHandler2 = iAlipaySSOPreHandler;
            final Activity activity2 = activity;
            AnonymousClass1 r0 = new Runnable("com.ali.user.mobile.external.AuthLoginResultActivity") {
                public void run() {
                    try {
                        long elapsedRealtime = SystemClock.elapsedRealtime();
                        final String apdidtokenResultTimeout = AppInfo.getInstance().getApdidtokenResultTimeout();
                        LoggerUtils.a("event", "AliAuthLoginSDK_syncReqUuidTimeElapsed", "ALIAUTH_0415_14", String.valueOf(SystemClock.elapsedRealtime() - elapsedRealtime));
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            public void run() {
                                iAlipaySSOPreHandler2.dismissPreProgress();
                                if (!TextUtils.isEmpty(apdidtokenResultTimeout)) {
                                    AppInfo.getInstance().setAuthApdidToken(apdidtokenResultTimeout);
                                    LoggerUtils.a("event", "AliAuthLoginSDK_syncReqUuidSuccess", "ALIAUTH_0415_10", "");
                                    AlipaySSOAuthLoginApiImpl.this.a(activity2, iAlipaySSOPreHandler2, a2, packageName, "com.ali.user.mobile.external.AuthLoginResultActivity", apdidtokenResultTimeout, b);
                                    return;
                                }
                                LoggerUtils.a("event", "AliAuthLoginSDK_syncReqUuidFailure", "ALIAUTH_0415_09", "");
                                iAlipaySSOPreHandler2.preAuthFailed(1005);
                            }
                        });
                    } catch (Throwable th) {
                        LoggerFactory.f().b("AliAuth_AlipaySSOAuthLoginApiImpl", "syncHandleApdidtoken thread error", th);
                        iAlipaySSOPreHandler2.preAuthFailed(1005);
                    }
                }
            };
            new Thread(r0).start();
        }
    }
}
