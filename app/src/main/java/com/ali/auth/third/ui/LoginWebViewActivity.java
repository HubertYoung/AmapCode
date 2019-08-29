package com.ali.auth.third.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebViewClient;
import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.core.context.KernelContext;
import com.ali.auth.third.core.model.ResultCode;
import com.ali.auth.third.login.LoginService;
import com.ali.auth.third.login.bridge.LoginBridge;
import com.ali.auth.third.login.util.LoginStatus;
import com.ali.auth.third.ui.webview.BaseWebViewActivity;
import com.alipay.mobile.nebula.appcenter.util.H5AppUtil;

public class LoginWebViewActivity extends BaseWebViewActivity {
    public static final String CALLBACK = "https://www.alipay.com/webviewbridge";
    public static final String TAG = BaseWebViewActivity.class.getSimpleName();
    public static Activity originActivity;
    public static String scene;
    public static String token;
    /* access modifiers changed from: private */
    public LoginService a;

    /* access modifiers changed from: private */
    public boolean a(Uri uri) {
        Bundle serialBundle = serialBundle(uri.getQuery());
        if (serialBundle == null) {
            serialBundle = new Bundle();
        }
        serialBundle.getString("havana_mobile_reg_otherWebView");
        String string = serialBundle.getString("action");
        serialBundle.getString("loginId");
        if (TextUtils.isEmpty(string) || "quit".equals(string)) {
            setResult(ResultCode.SUCCESS.code, getIntent().putExtra("iv_token", serialBundle.getString("havana_iv_token")));
        } else if ("relogin".equals(string)) {
            finish();
            return true;
        } else if ("mobile_confirm_login".equals(string) || "trustLogin".equals(string)) {
            return true;
        } else {
            if (!"continueLogin".equals(string)) {
                return false;
            }
            serialBundle.putString("aliusersdk_h5querystring", uri.getQuery());
            serialBundle.putString("token", token);
            serialBundle.putString(H5AppUtil.scene, scene);
            setResult(ResultCode.CHECK.code, getIntent().putExtras(serialBundle));
        }
        finish();
        return true;
    }

    public static Bundle serialBundle(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        String[] split = str.split("&");
        Bundle bundle = new Bundle();
        for (String str2 : split) {
            int indexOf = str2.indexOf("=");
            if (indexOf > 0 && indexOf < str2.length() - 1) {
                bundle.putString(str2.substring(0, indexOf), str2.substring(indexOf + 1));
            }
        }
        return bundle;
    }

    public boolean checkWebviewBridge(String str) {
        Uri parse = Uri.parse(str);
        StringBuilder sb = new StringBuilder();
        sb.append(parse.getAuthority());
        sb.append(parse.getPath());
        return "https://www.alipay.com/webviewbridge".contains(sb.toString());
    }

    public WebChromeClient createWebChromeClient() {
        return new c(this);
    }

    public WebViewClient createWebViewClient() {
        return new b(this);
    }

    public void onBackHistory() {
        if (!this.authWebView.canGoBack() || (!this.authWebView.getUrl().contains("authorization-notice") && !this.authWebView.getUrl().contains("agreement"))) {
            setResult(ResultCode.USER_CANCEL.code, new Intent());
            LoginStatus.resetLoginFlag();
            finish();
            return;
        }
        this.authWebView.goBack();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.authWebView.addBridgeObject("accountBridge", new LoginBridge());
        this.authWebView.addBridgeObject("loginBridge", new LoginBridge());
        this.a = (LoginService) MemberSDK.getService(LoginService.class);
        KernelContext.context = getApplicationContext();
    }
}
