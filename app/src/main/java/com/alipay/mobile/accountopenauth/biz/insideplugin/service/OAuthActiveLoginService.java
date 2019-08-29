package com.alipay.mobile.accountopenauth.biz.insideplugin.service;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.api.accountopenauth.AccountOAuthHelper;
import com.alipay.android.phone.inside.framework.service.IInsideService;
import com.alipay.android.phone.inside.framework.service.IInsideServiceCallback;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.mobile.accountopenauth.common.OAuthBehaviorLogger;
import com.alipay.mobile.accountopenauth.common.OAuthTraceLogger;
import com.alipay.mobile.securitycommon.aliauth.AliAuthConstants.Key;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;
import org.json.JSONObject;

public class OAuthActiveLoginService implements IInsideService<JSONObject, String> {
    private Map<Long, String> a = AccountOAuthHelper.getInstance().getOAuthLoginTaskMap();

    public /* synthetic */ void start(IInsideServiceCallback iInsideServiceCallback, Object obj) throws Exception {
        JSONObject jSONObject = (JSONObject) obj;
        if (iInsideServiceCallback == null || jSONObject == null) {
            throw new IllegalArgumentException("oauth active loginparams error");
        }
        String optString = jSONObject.optString(Key.ALIPAY_UID);
        String optString2 = jSONObject.optString("mcUid");
        String optString3 = jSONObject.optString("accessToken");
        String optString4 = jSONObject.optString("bizSource");
        OAuthBehaviorLogger.a("action", "Enter_Active_OpenAuth_Login", optString4, "", "");
        StringBuilder sb = new StringBuilder("alipayUid:");
        sb.append(optString);
        sb.append("，mcUid:");
        sb.append(optString2);
        sb.append(",accessToken:");
        sb.append(optString3);
        sb.append("，bizSource：");
        sb.append(optString4);
        OAuthTraceLogger.a((String) "OAuthActiveLoginService", sb.toString());
        if (TextUtils.isEmpty(optString) || TextUtils.isEmpty(optString2) || TextUtils.isEmpty(optString3)) {
            OAuthTraceLogger.a((String) "OAuthActiveLoginService", (String) "active login some params null");
            OAuthTraceLogger.a((String) "OAuthActiveLoginService", String.format("doCallback 线程 key=%s", new Object[]{Long.valueOf(Thread.currentThread().getId())}));
            synchronized (this.a) {
                this.a.put(Long.valueOf(Thread.currentThread().getId()), "wait");
            }
            synchronized (AccountOAuthHelper.getInstance().getLock()) {
                String str = this.a.get(Long.valueOf(Thread.currentThread().getId()));
                OAuthTraceLogger.a((String) "OAuthActiveLoginService", String.format("syncDoCallback 当前线程被标记状态: %s", new Object[]{str}));
                if ("canceled".equals(str)) {
                    iInsideServiceCallback.onComplted("AUTH_LOGIN_PARAM_ILEEGAL");
                } else if ("thread_ok".equals(str)) {
                    iInsideServiceCallback.onComplted("AUTH_LOGIN_SUCCESS");
                } else {
                    a(iInsideServiceCallback, "NO", optString4);
                }
            }
            synchronized (this.a) {
                OAuthTraceLogger.a((String) "OAuthActiveLoginService", String.format("doCallback 移除等待线程 key=%s", new Object[]{Long.valueOf(Thread.currentThread().getId())}));
                this.a.remove(Long.valueOf(Thread.currentThread().getId()));
            }
            return;
        }
        OAuthTraceLogger.a((String) "OAuthActiveLoginService", (String) "active login params not null,begin do direct login");
        OAuthTraceLogger.a((String) "OAuthActiveLoginService", String.format("doDirectLogin 线程 key=%s", new Object[]{Long.valueOf(Thread.currentThread().getId())}));
        synchronized (this.a) {
            this.a.put(Long.valueOf(Thread.currentThread().getId()), "wait");
        }
        synchronized (AccountOAuthHelper.getInstance().getLock()) {
            String str2 = this.a.get(Long.valueOf(Thread.currentThread().getId()));
            OAuthTraceLogger.a((String) "OAuthActiveLoginService", String.format("syncDoDirectLogin 当前线程被标记状态: %s", new Object[]{str2}));
            if ("canceled".equals(str2)) {
                iInsideServiceCallback.onComplted("AUTH_LOGIN_COMMON_FAILED");
            } else if ("thread_ok".equals(str2)) {
                iInsideServiceCallback.onComplted("AUTH_LOGIN_SUCCESS");
            } else {
                a(iInsideServiceCallback, optString, optString2, optString3, optString4);
            }
        }
        synchronized (this.a) {
            OAuthTraceLogger.a((String) "OAuthActiveLoginService", String.format("doDirectLogin 移除等待线程 key=%s", new Object[]{Long.valueOf(Thread.currentThread().getId())}));
            this.a.remove(Long.valueOf(Thread.currentThread().getId()));
        }
    }

    private void a(IInsideServiceCallback iInsideServiceCallback, String str, String str2) {
        Bundle bundle = new Bundle();
        OAuthTraceLogger.a((String) "OAuthActiveLoginService", (String) "auth new flow");
        bundle.putString("needRefreshToken", str);
        bundle.putString("obtainAuthInfoScene", "Scene_ActiveLogin");
        try {
            Bundle a2 = a((String) "OBTAIN_MC_AUTHINFO_SERVICE", bundle);
            if (!(!TextUtils.isEmpty(a2.getString("openMcUid")) || !TextUtils.isEmpty(a2.getString("alipayUserId")) || !TextUtils.isEmpty(a2.getString("authToken")))) {
                OAuthTraceLogger.a((String) "OAuthActiveLoginService", (String) "params invalid");
                a(str2, (String) "getMCAuthInfoFailed");
                iInsideServiceCallback.onComplted("AUTH_LOGIN_COMMON_FAILED");
                b();
                return;
            }
            a(iInsideServiceCallback, a2.getString("alipayUserId"), a2.getString("openMcUid"), a2.getString("authToken"), str2);
        } catch (Throwable th) {
            if (th instanceof TimeoutException) {
                OAuthTraceLogger.a("OAuthActiveLoginService", "getMcAuthLoginInfo ex", th);
                a(str2, (String) "Intercepter_OpenAuth_Timeout");
                iInsideServiceCallback.onComplted("AUTH_LOGIN_TIMEOUT");
                b();
                return;
            }
            OAuthTraceLogger.a("OAuthActiveLoginService", "getMcAuthLoginInfo error", th);
            iInsideServiceCallback.onComplted("AUTH_LOGIN_COMMON_FAILED");
            b();
        }
    }

    private Bundle a(String str, Bundle bundle) throws Exception {
        final Object obj = new Object();
        final Bundle bundle2 = new Bundle();
        ServiceExecutor.a(str, bundle, new IInsideServiceCallback<Bundle>() {
            public /* synthetic */ void onComplted(Object obj) {
                Bundle bundle = (Bundle) obj;
                OAuthTraceLogger.a((String) "openauth", "get McAuthLoginInfo result：".concat(String.valueOf(bundle)));
                bundle2.putAll(bundle);
                synchronized (obj) {
                    obj.notifyAll();
                }
            }

            public void onException(Throwable th) {
                OAuthBehaviorLogger.a("openauth", "McAuthLoginInfo", th);
                synchronized (obj) {
                    obj.notifyAll();
                }
            }
        });
        long currentTimeMillis = System.currentTimeMillis();
        synchronized (obj) {
            try {
                obj.wait(300000);
            } catch (Throwable th) {
                OAuthBehaviorLogger.a("openauth", "McAuthLoginInfo", th);
            }
        }
        if (System.currentTimeMillis() - currentTimeMillis <= 299000) {
            return bundle2;
        }
        throw new TimeoutException();
    }

    private void a(IInsideServiceCallback iInsideServiceCallback, String str, String str2, String str3, String str4) {
        Bundle bundle = new Bundle();
        bundle.putString("alipayUserId", str);
        bundle.putString("openMcUid", str2);
        bundle.putString("authToken", str3);
        bundle.putBoolean("isNewOpenAuthFlow", true);
        bundle.putBoolean("isOpenAuthLogin", true);
        try {
            ServiceExecutor.b("COMMONBIZ_SERVICE_LOGIN_EXPIRE", bundle);
            iInsideServiceCallback.onComplted("AUTH_LOGIN_SUCCESS");
            a();
        } catch (Throwable th) {
            if (!(th instanceof IllegalStateException) || !"6601".equals(th.getMessage())) {
                OAuthTraceLogger.a((String) "OAuthActiveLoginService", (String) "openauth login failed");
                iInsideServiceCallback.onComplted("AUTH_LOGIN_COMMON_FAILED");
                b();
                return;
            }
            OAuthTraceLogger.a((String) "OAuthActiveLoginService", (String) "accesstoken invalid 6601");
            a(iInsideServiceCallback, "YES", str4);
        }
    }

    private void a() {
        synchronized (this.a) {
            for (Entry<Long, String> key : this.a.entrySet()) {
                Long l = (Long) key.getKey();
                OAuthTraceLogger.a((String) "OAuthActiveLoginService", String.format("登录完成且登录成功，设置 授权/登录 等待线程 为ok状态 key=%s", new Object[]{l}));
                this.a.put(l, "thread_ok");
            }
        }
    }

    private void b() {
        synchronized (this.a) {
            for (Entry<Long, String> key : this.a.entrySet()) {
                Long l = (Long) key.getKey();
                OAuthTraceLogger.a((String) "OAuthActiveLoginService", String.format("登录完成且登录失败，设置等待线程 为取消状态 key=%s", new Object[]{l}));
                this.a.put(l, "canceled");
            }
        }
    }

    private static void a(String str, String str2) {
        try {
            OAuthBehaviorLogger.a("action", str2, str, "Active_Login", "");
            OAuthTraceLogger.a((String) "OAuthActiveLoginService", "reportAuthBehavior:".concat(String.valueOf(str2)));
        } catch (Throwable th) {
            OAuthTraceLogger.b((String) "OAuthActiveLoginService", th);
        }
    }

    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }

    public /* synthetic */ void start(Object obj) throws Exception {
        throw new UnsupportedOperationException();
    }
}
