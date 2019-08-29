package com.alipay.android.phone.inside.wallet.plugin.service;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.wallet.model.INotifyChecker;
import com.alipay.android.phone.inside.wallet.model.NotifyResult;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import org.json.JSONObject;

public class CashierAuthService extends AbstractInsideService<Bundle, Bundle> {
    static final String CASHIER_SERVICE_RESET_TID = "com.alipay.android.phone.inside.PHONE_CASHIER_RESET_TID";
    static final String CODE_FAILED = "FAILED";
    static final String CODE_SUCCESS = "SUCCESS";
    static final String CODE_TIMEOUT = "TIMEOUT";
    static final String LOGIN_PROCESS_ALIPAY_PARAMS_SERVICE = "LOGIN_PROCESS_ALIPAY_PARAMS_SERVICE";
    static final String RE_PARAMS_ARGS = "args";
    static final String RE_PARAMS_INSIDE_ENV = "insideEnv";
    static final int SEC_SERVICE_TIMEOUT = 20;

    /* JADX WARNING: Removed duplicated region for block: B:20:0x00cb  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x00f8  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00fb  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0112  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.Bundle startForResult(android.os.Bundle r9) throws java.lang.Exception {
        /*
            r8 = this;
            org.json.JSONObject r0 = new org.json.JSONObject
            java.lang.String r1 = "args"
            java.lang.String r1 = r9.getString(r1)
            r0.<init>(r1)
            java.lang.String r1 = "insideEnv"
            java.lang.String r9 = r9.getString(r1)
            android.content.Context r1 = r8.getContext()
            r2 = 121(0x79, float:1.7E-43)
            com.alipay.android.phone.inside.wallet.api.WalletStatusEnum r1 = com.alipay.android.phone.inside.wallet.api.ApkVerifyTool.checkAlipayStatus(r1, r2)
            com.alipay.android.phone.inside.wallet.api.WalletStatusEnum r2 = com.alipay.android.phone.inside.wallet.api.WalletStatusEnum.SUCCESS
            if (r1 == r2) goto L_0x003e
            java.lang.String r9 = "FAILED"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "{\"errorCode\":\""
            r0.<init>(r2)
            java.lang.String r1 = r1.name()
            r0.append(r1)
            java.lang.String r1 = "\"}"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            android.os.Bundle r9 = r8.buildResult(r9, r0)
            return r9
        L_0x003e:
            r8.dealUniformity(r0)
            java.lang.String r1 = ""
            java.lang.String r9 = r8.getAuthInfo(r9, r0)
            boolean r2 = r8.isLogin()
            if (r2 == 0) goto L_0x0053
            java.lang.String r0 = getLoginId()
        L_0x0051:
            r5 = r0
            goto L_0x009e
        L_0x0053:
            boolean r2 = r8.isThirdTokenLogin(r0)
            if (r2 == 0) goto L_0x0077
            android.os.Bundle r0 = r8.getOpenAuthTokenLoginParams(r0)
            java.lang.String r2 = "LOGIN_EXTERNAL_SERVICE"
            android.os.Bundle r0 = r8.startServiceForResult(r2, r0)
            boolean r0 = r8.isThirdTokenLoginSuccess(r0)
            if (r0 != 0) goto L_0x0072
            java.lang.Exception r9 = new java.lang.Exception
            java.lang.String r0 = "third token login failed, return!"
            r9.<init>(r0)
            throw r9
        L_0x0072:
            java.lang.String r0 = getLoginId()
            goto L_0x0051
        L_0x0077:
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            java.lang.String r1 = "requestType"
            java.lang.String r2 = "getJumpAlipayParams"
            r0.putString(r1, r2)
            java.lang.String r1 = "LOGIN_PROCESS_ALIPAY_PARAMS_SERVICE"
            android.os.Bundle r0 = r8.startServiceForResult(r1, r0)
            java.lang.String r1 = "loginId"
            java.lang.String r2 = ""
            java.lang.String r1 = r0.getString(r1, r2)
            java.lang.String r2 = "authLoginInfo"
            android.os.Bundle r0 = r0.getBundle(r2)
            java.lang.String r0 = r8.buildAuthLoginInfo(r0)
            r5 = r1
            r1 = r0
        L_0x009e:
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ TimeoutException -> 0x0134 }
            r6.<init>()     // Catch:{ TimeoutException -> 0x0134 }
            java.lang.String r0 = "loginId"
            r6.put(r0, r5)     // Catch:{ TimeoutException -> 0x0134 }
            java.lang.String r0 = "authLoginInfo"
            r6.put(r0, r1)     // Catch:{ TimeoutException -> 0x0134 }
            java.lang.String r0 = "authInfo"
            r6.put(r0, r9)     // Catch:{ TimeoutException -> 0x0134 }
            com.alipay.android.phone.inside.wallet.JumpAlipaySchemeProvider r2 = new com.alipay.android.phone.inside.wallet.JumpAlipaySchemeProvider     // Catch:{ TimeoutException -> 0x0134 }
            r2.<init>()     // Catch:{ TimeoutException -> 0x0134 }
            android.content.Context r3 = r8.getContext()     // Catch:{ TimeoutException -> 0x0134 }
            java.lang.String r4 = "buscode_auth"
            com.alipay.android.phone.inside.wallet.model.INotifyChecker r7 = r8.getNotifyChecker()     // Catch:{ TimeoutException -> 0x0134 }
            com.alipay.android.phone.inside.wallet.model.NotifyResult r9 = r2.jumpScheme(r3, r4, r5, r6, r7)     // Catch:{ TimeoutException -> 0x0134 }
            boolean r0 = r8.hasLoginResultToken(r9)
            if (r0 == 0) goto L_0x00e8
            java.lang.String r0 = "authLoginResult"
            android.os.Bundle r0 = r9.getBundle(r0)
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            java.lang.String r2 = "requestType"
            java.lang.String r3 = "processAuthLoginResponse"
            r1.putString(r2, r3)
            java.lang.String r2 = "authLoginResult"
            r1.putBundle(r2, r0)
            java.lang.String r0 = "LOGIN_PROCESS_ALIPAY_PARAMS_SERVICE"
            r8.startServiceForResult(r0, r1)
        L_0x00e8:
            java.lang.String r9 = r8.getAuthResult(r9)
            java.lang.String r0 = r8.getAuthResultCode(r9)
            java.lang.String r1 = "9000"
            boolean r1 = android.text.TextUtils.equals(r1, r0)
            if (r1 == 0) goto L_0x00fb
            java.lang.String r0 = "SUCCESS"
            goto L_0x010c
        L_0x00fb:
            java.lang.String r1 = "10000"
            boolean r0 = android.text.TextUtils.equals(r1, r0)
            if (r0 == 0) goto L_0x010a
            java.lang.String r9 = r8.resetAuthResult(r9)
            java.lang.String r0 = "SUCCESS"
            goto L_0x010c
        L_0x010a:
            java.lang.String r0 = "FAILED"
        L_0x010c:
            boolean r1 = r8.isTidIllegal(r9)
            if (r1 == 0) goto L_0x011c
            java.lang.String r1 = "com.alipay.android.phone.inside.PHONE_CASHIER_RESET_TID"
            android.os.Bundle r2 = new android.os.Bundle
            r2.<init>()
            com.alipay.android.phone.inside.framework.service.ServiceExecutor.a(r1, r2)
        L_0x011c:
            com.alipay.android.phone.inside.log.api.trace.TraceLogger r1 = com.alipay.android.phone.inside.log.api.LoggerFactory.f()
            java.lang.String r2 = "inside"
            java.lang.String r3 = "authResult:"
            java.lang.String r4 = java.lang.String.valueOf(r9)
            java.lang.String r3 = r3.concat(r4)
            r1.e(r2, r3)
            android.os.Bundle r9 = r8.buildResult(r0, r9)
            return r9
        L_0x0134:
            java.lang.String r9 = "TIMEOUT"
            java.lang.String r0 = ""
            android.os.Bundle r9 = r8.buildResult(r9, r0)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.inside.wallet.plugin.service.CashierAuthService.startForResult(android.os.Bundle):android.os.Bundle");
    }

    private INotifyChecker getNotifyChecker() {
        return new INotifyChecker() {
            public boolean illegel(Bundle bundle) {
                if (bundle == null || bundle.getBoolean("insideFlag", false)) {
                    return false;
                }
                LoggerFactory.e().a(DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, "AuthNotifyInsideFlagIllegel");
                return true;
            }
        };
    }

    private String getAuthResult(NotifyResult notifyResult) {
        return notifyResult.getString("authResult");
    }

    private String getAuthResultCode(String str) {
        try {
            return new JSONObject(str).optString("resultCode");
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            r4 = "";
            return "";
        }
    }

    private String resetAuthResult(String str) {
        JSONObject jSONObject;
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject = new JSONObject(str);
            try {
                jSONObject.put("result", "");
            } catch (Throwable th) {
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            jSONObject = jSONObject2;
            LoggerFactory.f().c((String) "inside", th);
            return jSONObject.toString();
        }
        return jSONObject.toString();
    }

    private boolean isTidIllegal(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            String optString = jSONObject.optString("resultCode");
            String optString2 = jSONObject.optString("result");
            if (!TextUtils.equals(optString, "4000") || TextUtils.isEmpty(optString2) || !optString2.contains("success=false") || !optString2.contains("result_code=907")) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
            return false;
        }
    }

    private boolean hasLoginResultToken(NotifyResult notifyResult) {
        return (notifyResult == null || notifyResult.getBundle("authLoginResult") == null) ? false : true;
    }

    private Bundle buildResult(String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putString("code", str);
        bundle.putString("result", str2);
        return bundle;
    }

    private Bundle startServiceForResult(String str, Bundle bundle) {
        final Object obj = new Object();
        final Bundle bundle2 = new Bundle();
        ServiceExecutor.a(str, bundle, new IInsideServiceCallback<Bundle>() {
            public void onComplted(Bundle bundle) {
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
                obj.wait(20000);
            } catch (Throwable th) {
                LoggerFactory.e().a((String) DIYMainMapPresenter.DIY_ENTRY_KEY_BUS_CARD, (String) "StartServiceWaitEx", th);
            }
        }
        return bundle2;
    }

    private String buildAuthLoginInfo(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            for (String str : bundle.keySet()) {
                jSONObject.put(str, bundle.getString(str));
            }
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return jSONObject.toString();
    }

    private String getAuthInfo(String str, JSONObject jSONObject) throws Exception {
        String optString = jSONObject.optString("authBizData", "");
        if (TextUtils.isEmpty(str)) {
            return optString;
        }
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(RE_PARAMS_INSIDE_ENV, str);
        StringBuilder sb = new StringBuilder();
        sb.append(optString);
        sb.append("&bizcontext=");
        sb.append(jSONObject2.toString());
        return sb.toString();
    }

    private void dealUniformity(JSONObject jSONObject) {
        try {
            jSONObject.put("reLogin", false);
            LoggerFactory.f().e("inside", "CashierAuthService::dealUniformity > ".concat(String.valueOf((Bundle) ServiceExecutor.b("COMMONBIZ_SERVICE_ACCOUNTUNIFORMITY", jSONObject))));
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
    }

    private boolean isLogin() throws Exception {
        return ((Bundle) ServiceExecutor.b("LOGIN_USERINFO_SERVICE", null)).getBoolean("isLogin");
    }

    private static String getLoginId() throws Exception {
        String string = ((Bundle) ServiceExecutor.b("LOGIN_USERINFO_SERVICE", null)).getString("loginId");
        if (!TextUtils.isEmpty(string)) {
            return string;
        }
        throw new Exception("loginId is empty!");
    }

    private Bundle getOpenAuthTokenLoginParams(JSONObject jSONObject) {
        Bundle bundle = new Bundle();
        try {
            bundle.putString("openAuthToken", jSONObject.optString("authToken"));
            bundle.putString("openAuthUserId", jSONObject.optString("alipayUserId"));
            bundle.putString("insideLoginType", "openAuthTokenLogin");
        } catch (Throwable th) {
            LoggerFactory.f().b((String) "inside", th);
        }
        return bundle;
    }

    private boolean isThirdTokenLoginSuccess(Bundle bundle) {
        return bundle != null && TextUtils.equals(bundle.getString("loginStatus"), "success");
    }

    private boolean isThirdTokenLogin(JSONObject jSONObject) {
        return jSONObject.optBoolean("isOpenAuthLogin", false);
    }
}
