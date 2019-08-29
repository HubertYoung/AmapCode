package com.alipay.mobile.accountopenauth.biz;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.common.info.AppInfo;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.mobile.accountopenauth.api.rpc.facade.OauthServiceGw;
import com.alipay.mobile.accountopenauth.api.rpc.model.req.OauthConfirmReq;
import com.alipay.mobile.accountopenauth.api.rpc.model.req.OauthPrepareReq;
import com.alipay.mobile.accountopenauth.api.rpc.model.res.OauthPrepareResult;
import com.alipay.mobile.accountopenauth.api.rpc.model.res.TinyAppAuthExecuteResult;
import com.alipay.mobile.accountopenauth.common.OAuthTraceLogger;
import com.alipay.mobile.common.logging.util.LoggingSPCache;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class FastOAuthDataManager {
    private static FastOAuthDataManager a;
    private OauthPrepareResult b;
    private AuthResultListener c;

    public interface AuthResultListener {
        void a();

        void a(Bundle bundle);

        void b();

        void c();
    }

    private FastOAuthDataManager() {
    }

    public static FastOAuthDataManager a() {
        if (a == null) {
            synchronized (FastOAuthDataManager.class) {
                if (a == null) {
                    a = new FastOAuthDataManager();
                }
            }
        }
        return a;
    }

    public final OauthPrepareResult b() {
        return this.b;
    }

    public final OauthPrepareResult a(String str, Bundle bundle) {
        if (bundle == null) {
            try {
                OAuthTraceLogger.a((String) "FastOAuthDataManager", (String) "getFastOAuthInfo params is null");
                return null;
            } catch (Throwable th) {
                OAuthTraceLogger.a("FastOAuthDataManager", "getFastOAuthInfo error", th);
            }
        } else {
            if (bundle != null) {
                String string = bundle.getString("loginId");
                bundle.getString("avatar");
                String string2 = bundle.getString("token");
                if (!TextUtils.isEmpty(string) && !TextUtils.isEmpty(string2)) {
                    this.b = ((OauthServiceGw) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(OauthServiceGw.class)).prepare(a(str));
                }
            }
            return this.b;
        }
    }

    public static TinyAppAuthExecuteResult a(String str, String str2) {
        try {
            OauthConfirmReq oauthConfirmReq = new OauthConfirmReq();
            oauthConfirmReq.contextToken = str2;
            oauthConfirmReq.oauthScene = "MY_PASS_OAUTH";
            HashMap hashMap = new HashMap();
            hashMap.put("loginToken", str);
            hashMap.put("productId", AppInfo.a().e());
            hashMap.put(LoggingSPCache.STORAGE_PRODUCTVERSION, AppInfo.a().g());
            oauthConfirmReq.postParams = hashMap;
            return ((OauthServiceGw) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(OauthServiceGw.class)).confirm(oauthConfirmReq);
        } catch (Throwable th) {
            OAuthTraceLogger.a("FastOAuthDataManager", "doFastOAuthRpc error", th);
            return null;
        }
    }

    private static OauthPrepareReq a(String str) {
        OauthPrepareReq oauthPrepareReq = new OauthPrepareReq();
        try {
            HashMap hashMap = new HashMap();
            hashMap.putAll(b(str));
            HashMap hashMap2 = new HashMap();
            hashMap2.put("productId", AppInfo.a().e());
            hashMap2.put(LoggingSPCache.STORAGE_PRODUCTVERSION, AppInfo.a().g());
            oauthPrepareReq.urlParams = hashMap;
            oauthPrepareReq.extraParams = hashMap2;
            StringBuilder sb = new StringBuilder("buildOAuthReq urlParams:");
            sb.append(hashMap.toString());
            OAuthTraceLogger.b((String) "FastOAuthDataManager", sb.toString());
        } catch (Throwable th) {
            OAuthTraceLogger.a("FastOAuthDataManager", "buildOAuthReq error", th);
        }
        return oauthPrepareReq;
    }

    private static Map<String, String> b(String str) {
        HashMap hashMap = new HashMap();
        try {
            if (!TextUtils.isEmpty(str)) {
                String[] split = str.split("&");
                if (split.length > 0) {
                    for (String str2 : split) {
                        if (!TextUtils.isEmpty(str2)) {
                            String[] split2 = str2.split("=");
                            if (split2.length > 0) {
                                hashMap.put(split2[0], URLDecoder.decode(split2[1], "utf-8"));
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
            OAuthTraceLogger.a("FastOAuthDataManager", "transStrToMap error", th);
        }
        return hashMap;
    }

    public final void c() {
        this.b = null;
    }

    public final void a(AuthResultListener authResultListener) {
        this.c = authResultListener;
    }

    public final void a(Bundle bundle) {
        if (this.c != null) {
            this.c.a(bundle);
        }
    }

    public final void d() {
        if (this.c != null) {
            this.c.a();
        }
    }

    public final void e() {
        if (this.c != null) {
            this.c.b();
        }
    }

    public final void f() {
        if (this.c != null) {
            this.c.c();
        }
    }
}
