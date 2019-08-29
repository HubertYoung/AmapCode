package com.autonavi.bundle.account.impl;

import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.ali.auth.third.core.MemberSDK;
import com.ali.auth.third.login.LoginService;
import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.TradeResult;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.autonavi.bundle.account.api.IThirdAuth.IBaichuanSDKWebViewApi;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.regex.Pattern;

public class BaichuanSDKWebViewApiImpl implements IBaichuanSDKWebViewApi {
    private static Pattern a = Pattern.compile("^((https?:)?//([^/\\?#]+\\.)*(((taobao|tmall|juhuasuan|xiami|taohua|hitao|taobaocdn|alipay|etao|alibaba|alibaba-inc|aliyun|alimama|weibo|tanx|alicdn|tbcdn|mmstat|laiwang|lwurl|tb|t-jh|yunos|5317wan|tdd|95095|kuaidadi|ahd|alimei)\\.(com|cn|net|to|hk|la|so))|(mashort|mybank)\\.cn|atb\\.so|(juzone|fastidea)\\.(me|cc)|juhs\\.me|mshare\\.cc|(5945i|wirlesshare|dingtalk|kanbox|alitrip|aliloan|wrating|yintai|cnzz|h5tool|h5util|spdyidea|polyinno|miaostreet|1688)\\.com|xianyu\\.mobi)([\\?|#|/|:].*)?)$");
    private static int b = 1;

    static class a {
        static BaichuanSDKWebViewApiImpl a = new BaichuanSDKWebViewApiImpl(0);
    }

    /* synthetic */ BaichuanSDKWebViewApiImpl(byte b2) {
        this();
    }

    private BaichuanSDKWebViewApiImpl() {
    }

    public static IBaichuanSDKWebViewApi b() {
        return a.a;
    }

    public final void a(WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient, String str) {
        WebView webView2 = webView;
        WebViewClient webViewClient2 = webViewClient;
        WebChromeClient webChromeClient2 = webChromeClient;
        AlibcTrade.show(AMapPageUtil.getMVPActivityContext().a(), webView2, webViewClient2, webChromeClient2, new AlibcPage(str), new AlibcShowParams(), null, null, new AlibcTradeCallback() {
            public void onFailure(int i, String str) {
            }

            public void onTradeSuccess(TradeResult tradeResult) {
            }
        });
    }

    public final boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return a.matcher(str).matches();
    }

    public static void a(int i) {
        b = i;
    }

    public final boolean a() {
        LoginService loginService = (LoginService) MemberSDK.getService(LoginService.class);
        if (loginService == null || b != 3) {
            return false;
        }
        try {
            return loginService.checkSessionValid();
        } catch (NullPointerException unused) {
            return false;
        }
    }
}
