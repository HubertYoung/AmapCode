package com.amap.bundle.webview;

import android.os.Handler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.login.LoginService;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.webview.util.BaichuanSDKProxy;
import com.amap.bundle.webview.util.IBaichuanSDKWebviewApi;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.account.api.IAccountService.AccountType;
import com.autonavi.bundle.account.api.IThirdAuth.IBaichuanSDKWebViewApi;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebViewVApp extends esh {
    private a a = new a() {
        public final void a(JSONObject jSONObject) {
            WebViewVApp.a();
        }
    };

    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        new Handler().post(new Runnable() {
            public final void run() {
                AMapLog.info("paas.webview", "WebViewVApp", "uc init");
                ajm.a(0);
            }
        });
        lt.a().a(this.a);
    }

    public void vAppDestroy() {
        super.vAppDestroy();
        lt.a().b(this.a);
    }

    public void vAppMapLoadCompleted() {
        super.vAppMapLoadCompleted();
        BaichuanSDKProxy.a((IBaichuanSDKWebviewApi) new IBaichuanSDKWebviewApi() {
            private IBaichuanSDKWebViewApi c;
            private IAccountService d = ((IAccountService) a.a.a(IAccountService.class));

            {
                if (this.d != null) {
                    this.c = this.d.c().b();
                }
            }

            public final void a(WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient, String str) {
                this.c.a(webView, webViewClient, webChromeClient, str);
            }

            public final boolean a(String str) {
                return this.c.a(str);
            }

            public final boolean a() {
                return this.c.a();
            }

            public final boolean b() {
                return this.d.a();
            }

            public final boolean c() {
                return this.d.a(AccountType.Taobao);
            }

            public final boolean b(String str) {
                LoginService loginService = (LoginService) MemberSDK.getService(LoginService.class);
                if (loginService == null) {
                    return false;
                }
                try {
                    return loginService.isLoginUrl(str);
                } catch (NullPointerException unused) {
                    return false;
                }
            }

            public final void a(anq anq) {
                this.d.a(AccountType.Taobao, anq);
            }

            public final void b(anq anq) {
                this.d.a(AMapPageUtil.getPageContext(), AccountType.Taobao, anq);
            }

            public final void c(anq anq) {
                this.d.a(anq);
            }
        });
    }

    static /* synthetic */ void a() {
        JSONObject jSONObject = lt.a().c.y;
        if (jSONObject != null) {
            if (jSONObject.optBoolean("update", false)) {
                String optString = jSONObject.optString("version", "");
                JSONArray optJSONArray = jSONObject.optJSONArray("data");
                if (optJSONArray != null) {
                    HashSet hashSet = new HashSet();
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        try {
                            hashSet.add(optJSONArray.getString(i));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    a.a.a.a((Set<String>) hashSet, optString);
                }
            }
        }
    }
}
