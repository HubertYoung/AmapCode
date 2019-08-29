package com.alibaba.baichuan.android.trade;

import android.app.Activity;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alibaba.baichuan.android.trade.adapter.applink.AlibcApplink;
import com.alibaba.baichuan.android.trade.adapter.ut.AlibcUserTracker;
import com.alibaba.baichuan.android.trade.adapter.ut.UserTrackerCompoment;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.component.AlibcComponent;
import com.alibaba.baichuan.android.trade.config.AlibcConfig;
import com.alibaba.baichuan.android.trade.constants.AlibcConstants;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.AlibcTaokeParams;
import com.alibaba.baichuan.android.trade.model.InitResult;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.utils.AlibcTradeHelper;
import com.alibaba.baichuan.android.trade.utils.a;
import com.alibaba.baichuan.android.trade.utils.d;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.HashMap;
import java.util.Map;

public class AlibcTrade {
    public static final String ALI_URL = "^(?:https?):\\/\\/(([^/\\?#]+\\.)*((taobao|tmall|juhuasuan|xiami|amap|taobaocdn|alipay|etao|alibaba|aliyun|alimama|weibo|tanx|laiwang|alicdn|mmstat|yunos|alibaba-inc|alitrip|aliloan|kanbox|wirlesshare|dingtalk|alimei|cnzz|kuaidadi|autonavi|m\\.yintai|polyinno|spdyidea|h5util|h5tool|5945i|miaostreet|1688|cainiao|taohua|m\\.dfkhgj|m\\.edcdfg|liangxinyao|taopiaopiao)\\.com|(tb|tbcdn|weibo|mashort|mybank|ba\\.ugame\\.uc|game\\.uc)\\.cn|(fastidea|juzone)\\.(me|cc)|lwurl\\.to|(taobao|alipay|cnzz)\\.net|tdd\\.la|yao\\.95095\\.com|(tmall|alipay)\\.hk|ahd\\.so|atb\\.so|mshare\\.cc|juhs\\.me|xianyu\\.mobi)([\\?|#|/].*)?)$";
    public static final int SHOW_FAILURE = -1;
    public static final int SHOW_H5 = 1;
    public static final int SHOW_NATIVE = 0;
    private static AlibcShowParams a = null;
    private static AlibcTaokeParams b = null;
    private static Map c = null;
    /* access modifiers changed from: private */
    public static Map d = null;
    private static String e = "com.alibaba.baichuan.android.trade.AlibcTrade";

    private static void a(AlibcBasePage alibcBasePage) {
        if (alibcBasePage == null) {
            a.a(e, "sendUsabilityFailure", "tradePage is null!");
            AlibcLogger.e(e, "tradePage is null");
            return;
        }
        boolean isNativeOpen = alibcBasePage.isNativeOpen(a);
        String usabilityPageType = alibcBasePage.getUsabilityPageType();
        StringBuilder sb = new StringBuilder();
        sb.append(usabilityPageType);
        sb.append(isNativeOpen ? UserTrackerConstants.U_NATIVE : UserTrackerConstants.U_H5);
        UserTrackerCompoment.sendUseabilitySuccess(sb.toString());
    }

    private static void a(AlibcBasePage alibcBasePage, String str) {
        if (alibcBasePage == null) {
            a.a(e, "sendUsabilityFailure", "tradePage is null!");
            AlibcLogger.e(e, "tradePage is null");
            return;
        }
        boolean isNativeOpen = alibcBasePage.isNativeOpen(a);
        String usabilityPageType = alibcBasePage.getUsabilityPageType();
        StringBuilder sb = new StringBuilder();
        sb.append(usabilityPageType);
        sb.append(isNativeOpen ? UserTrackerConstants.U_NATIVE : UserTrackerConstants.U_H5);
        UserTrackerCompoment.sendUseabilityFailure(sb.toString(), str);
    }

    private static void a(AlibcBasePage alibcBasePage, Map map) {
        if (alibcBasePage.getApi() != null) {
            HashMap hashMap = new HashMap();
            hashMap.putAll(map);
            if (!TextUtils.isEmpty(AlibcContext.getAppKey())) {
                hashMap.put("appkey", AlibcContext.getAppKey());
            }
            hashMap.put(UserTrackerConstants.TAOKEPARAMS, b == null ? bny.c : b.toString());
            AlibcUserTracker.getInstance().sendCustomHit(alibcBasePage.getApi(), (String) "", (Map) hashMap);
        }
    }

    private static void a(Map map) {
        AlibcContext.isvCode = (map == null || map.get(AlibcConstants.ISV_CODE) == null) ? null : (String) map.get(AlibcConstants.ISV_CODE);
    }

    /* access modifiers changed from: private */
    public static void b(Activity activity, WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient, String str, AlibcBasePage alibcBasePage, com.alibaba.baichuan.android.trade.b.a aVar, boolean z) {
        com.alibaba.baichuan.android.trade.b.a aVar2 = aVar;
        HashMap hashMap = new HashMap();
        hashMap.put(AlibcConstants.TAOKE_PARAM, b);
        hashMap.put("ui_contextParams", d);
        AlibcBasePage alibcBasePage2 = alibcBasePage;
        hashMap.put(AlibcConstants.SHOW_BY_H5, String.valueOf(!alibcBasePage2.isNativeOpen(a)));
        hashMap.put(AlibcConstants.BACK_LOGIN_FAIL, String.valueOf(alibcBasePage2.isBackWhenLoginFail()));
        StringBuilder sb = new StringBuilder();
        sb.append(alibcBasePage2.getUsabilityPageType());
        sb.append(UserTrackerConstants.U_H5);
        hashMap.put(UserTrackerConstants.U_LABEL, sb.toString());
        AlibcLogger.d(e, "h5打开，上下文参数为params=".concat(String.valueOf(hashMap)));
        aVar2.a((String) UserTrackerConstants.PM_URL_LOAD_TIME);
        if (webView == null || AlibcContext.executorService == null) {
            AlibcLogger.i(e, "使用Trade的webview打开");
            AlibcComponent.INSTANCE.show(activity, str, hashMap, aVar2);
            return;
        }
        AlibcLogger.i(e, "使用第三方webview打开");
        AlibcComponent.INSTANCE.show(activity, str, webView, webViewClient, webChromeClient, hashMap, aVar2, alibcBasePage2.getAdditionalHttpHeaders(), z);
    }

    public static boolean isAliUrl(String str) {
        return !TextUtils.isEmpty(ALI_URL) && !TextUtils.isEmpty(str) && str.matches(ALI_URL);
    }

    public static int show(Activity activity, WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient, AlibcBasePage alibcBasePage, AlibcShowParams alibcShowParams, AlibcTaokeParams alibcTaokeParams, Map map, AlibcTradeCallback alibcTradeCallback) {
        Activity activity2 = activity;
        AlibcBasePage alibcBasePage2 = alibcBasePage;
        AlibcTaokeParams alibcTaokeParams2 = alibcTaokeParams;
        AlibcTradeCallback alibcTradeCallback2 = alibcTradeCallback;
        String str = e;
        StringBuilder sb = new StringBuilder("AlibcTrade.show(): AlibcPage: ");
        sb.append(alibcBasePage2 == null ? "null" : alibcBasePage.toString());
        sb.append(" taoke.pid: ");
        sb.append(alibcTaokeParams2 == null ? "null" : alibcTaokeParams2.pid);
        sb.append(" activity:");
        sb.append(activity2 == null ? "null" : activity2.toString());
        AlibcLogger.i(str, sb.toString());
        InitResult initResult = AlibcTradeSDK.initResult;
        if (initResult != null && !initResult.isSuccess()) {
            StringBuilder sb2 = new StringBuilder("初始化失败, ");
            sb2.append(initResult.errorMessage);
            d.a(alibcTradeCallback2, 1001, sb2.toString());
            return -1;
        } else if (AlibcTradeSDK.initState.isInitializing()) {
            d.a(alibcTradeCallback2, 1002, "初始化未完成,请稍后!");
            return -1;
        } else {
            a = alibcShowParams;
            if (alibcShowParams == null) {
                a = new AlibcShowParams();
            }
            com.alibaba.baichuan.android.trade.b.a aVar = new com.alibaba.baichuan.android.trade.b.a(alibcBasePage2, a);
            aVar.a((String) UserTrackerConstants.PM_ALL_TIME);
            aVar.a((String) UserTrackerConstants.PM_ANALYSIS_TIME);
            if (!AlibcTradeHelper.checkParams(alibcBasePage2, activity2, aVar, alibcTradeCallback2)) {
                AlibcLogger.e(e, "page检测参数出错，流程中止");
                if (alibcBasePage2 == null) {
                    alibcBasePage2 = new AlibcBasePage();
                }
                a(alibcBasePage2, (String) UserTrackerConstants.EM_PARAM_ERROR);
                return -1;
            }
            AlibcContext.isShowTitleBar = alibcShowParams.isShowTitleBar();
            a(map);
            c = map;
            d = AlibcTradeHelper.createUrlParams(map);
            String str2 = e;
            StringBuilder sb3 = new StringBuilder("url 参数为 mUrlParams=");
            sb3.append(d);
            AlibcLogger.d(str2, sb3.toString());
            aVar.e = alibcTradeCallback2;
            b = alibcTaokeParams2;
            if (alibcTaokeParams2 == null && AlibcConfig.getInstance().getNBTTradeTaokeParams() != null) {
                b = AlibcConfig.getInstance().getTaokeParams();
                AlibcLogger.d(e, "使用全局淘客参数,taokeParams=".concat(String.valueOf(alibcTaokeParams)));
            }
            boolean isNativeOpen = alibcBasePage2.isNativeOpen(a);
            AlibcApplink.getInstance();
            if (!AlibcApplink.isApplinkSupported(a.getClientType())) {
                AlibcLogger.i(e, "不支持applink，通过H5打开");
                isNativeOpen = false;
            }
            aVar.b(UserTrackerConstants.PM_ANALYSIS_TIME);
            if (isNativeOpen) {
                String str3 = e;
                StringBuilder sb4 = new StringBuilder("通过native打开，打开参数为taokeparamers=");
                sb4.append(b);
                sb4.append(" showparamers=");
                sb4.append(a);
                sb4.append(" urlParamers=");
                sb4.append(d);
                AlibcLogger.d(str3, sb4.toString());
                aVar.a((String) UserTrackerConstants.PM_GO_TAOBAO_TIME);
                if (alibcBasePage2.tryNativeJump(b, a, d, activity2)) {
                    AlibcLogger.i(e, "通过applink打开手淘成功");
                    aVar.b(UserTrackerConstants.PM_GO_TAOBAO_TIME);
                    aVar.b(UserTrackerConstants.PM_ALL_TIME);
                    UserTrackerCompoment.sendPerfomancePoint(aVar.b);
                    a(alibcBasePage2, d);
                    a(alibcBasePage);
                    return 0;
                }
                a(alibcBasePage2, (String) UserTrackerConstants.EM_APPLINK_REQUEST_ERROR);
                aVar.a(alibcBasePage2);
            }
            if (alibcBasePage2.needTaoke(b)) {
                AlibcLogger.i(e, "进行淘客打点");
                AlibcTaokeParams alibcTaokeParams3 = b;
                a aVar2 = new a(alibcBasePage2, activity2, webView, webViewClient, webChromeClient, aVar, alibcShowParams);
                alibcBasePage2.taokeTraceAndGenUrl(alibcTaokeParams3, aVar, aVar2);
            } else {
                b(activity2, webView, webViewClient, webChromeClient, alibcBasePage2.getAddParamsUrl(d, aVar), alibcBasePage2, aVar, alibcShowParams.isProxyWebview());
            }
            a(alibcBasePage2, d);
            return 1;
        }
    }

    public static int show(Activity activity, AlibcBasePage alibcBasePage, AlibcShowParams alibcShowParams, AlibcTaokeParams alibcTaokeParams, Map map, AlibcTradeCallback alibcTradeCallback) {
        return show(activity, null, null, null, alibcBasePage, alibcShowParams, alibcTaokeParams, map, alibcTradeCallback);
    }
}
