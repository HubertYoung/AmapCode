package com.alibaba.baichuan.android.trade.adapter.alipay;

import android.app.Activity;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebView;
import com.alibaba.baichuan.android.trade.AlibcContext;
import com.alibaba.baichuan.android.trade.a.d;
import com.alibaba.baichuan.android.trade.adapter.mtop.NetworkResponse;
import com.alibaba.baichuan.android.trade.adapter.ut.UserTrackerCompoment;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.constants.MessageConstants;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.alibaba.baichuan.android.trade.model.AliPayResult;
import com.alibaba.baichuan.android.trade.ui.activity.AlibcWebViewActivity;
import com.alibaba.baichuan.android.trade.utils.c.a;
import com.alibaba.baichuan.android.trade.utils.g;
import com.alibaba.baichuan.android.trade.utils.log.AlibcLogger;
import java.util.ArrayList;
import java.util.List;

public class AlibcAlipay implements IAlibcAlipay {
    public static final String ALIPASDK_CLASS_NAME = "com.alipay.sdk.app.PayTask";
    public static final String PAY_SUCCESS_CODE = "9000";
    private static volatile AlibcAlipay a;
    public final String TAG = "AlibcAlipay";

    private AlibcAlipay() {
    }

    /* access modifiers changed from: private */
    public AliPayResult a(ArrayList arrayList) {
        String str;
        NetworkResponse a2 = new d().a((List) arrayList);
        if (a2 == null || !a2.isSuccess()) {
            if (a2 == null) {
                str = UserTrackerConstants.EM_QUERY_FAILURE;
            } else {
                StringBuilder sb = new StringBuilder("code = ");
                sb.append(a2.errorCode);
                sb.append(" ,errmsg=");
                sb.append(a2.errorMsg);
                str = sb.toString();
            }
            a((String) UserTrackerConstants.ERRCODE_PAY_QUERY_FAIL, str);
        }
        if (a2 == null) {
            return null;
        }
        return d.a(a2);
    }

    /* access modifiers changed from: private */
    public String a(AlibcTradeCallback alibcTradeCallback, WebView webView, String str) {
        try {
            return (String) a.a(ALIPASDK_CLASS_NAME, "pay", new String[]{"java.lang.String", "boolean"}, a.a((String) ALIPASDK_CLASS_NAME, new String[]{"android.app.Activity"}, new Object[]{webView.getContext()}), new Object[]{str, Boolean.TRUE});
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder("调用ApliPaySDK失败");
            sb.append(e.getMessage());
            AlibcLogger.e("AlibcAlipay", sb.toString());
            if (alibcTradeCallback != null) {
                alibcTradeCallback.onFailure(MessageConstants.PAY_SDK_FAILED, "使用Apliay SDK调用方法失败，请下载最新的ApliaySDK lib包或者使用H5方式付款");
            }
            if (webView.getContext() instanceof AlibcWebViewActivity) {
                ((AlibcWebViewActivity) webView.getContext()).finish();
            } else if (webView.getContext() instanceof Activity) {
                ((Activity) webView.getContext()).finish();
            }
            return null;
        }
    }

    /* access modifiers changed from: private */
    public String a(String str, String str2, String str3) {
        int indexOf = str.indexOf(str2) + str2.length();
        if (str3 == null) {
            return str.substring(indexOf);
        }
        try {
            return str.substring(indexOf, str.indexOf(str3));
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    /* access modifiers changed from: private */
    public ArrayList a(Uri uri) {
        String queryParameter = uri.getQueryParameter("pay_order_id");
        ArrayList arrayList = new ArrayList();
        if (!TextUtils.isEmpty(queryParameter)) {
            for (String parseLong : queryParameter.split(",")) {
                arrayList.add(Long.valueOf(Long.parseLong(parseLong)));
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    public void a() {
        UserTrackerCompoment.sendUseabilitySuccess(UserTrackerConstants.U_PAY_RESULT);
    }

    /* access modifiers changed from: private */
    public void a(AlibcTradeCallback alibcTradeCallback, WebView webView, AliPayResult aliPayResult) {
        if (alibcTradeCallback != null) {
            AlibcContext.executorService.b(new c(this, aliPayResult, alibcTradeCallback, webView));
        }
    }

    /* access modifiers changed from: private */
    public void a(AlibcTradeCallback alibcTradeCallback, String str, WebView webView) {
        AlibcLogger.e("AlibcAlipay", "alipay支付失败");
        AlibcContext.executorService.b(new b(this, str, alibcTradeCallback, webView));
    }

    /* access modifiers changed from: private */
    public void a(String str, String str2) {
        UserTrackerCompoment.sendUseabilityFailureForOtherErrmsg(UserTrackerConstants.U_PAY_RESULT, str2, str);
    }

    private String b(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder("trade_no=\"");
        sb.append(str);
        sb.append("\"&extern_token=\"");
        sb.append(str2);
        sb.append("\"&partner=\"PARTNER_TAOBAO_ORDER\"");
        if (!TextUtils.isEmpty(str3)) {
            sb.append("&payPhaseId=\"");
            sb.append(str3);
            sb.append("\"");
        }
        return sb.toString();
    }

    public static AlibcAlipay getInstance() {
        if (a == null) {
            synchronized (AlibcAlipay.class) {
                if (a == null) {
                    a = new AlibcAlipay();
                }
            }
        }
        return a;
    }

    public boolean isAlipaySDKAvailable() {
        try {
            Class.forName(ALIPASDK_CLASS_NAME);
            return true;
        } catch (ClassNotFoundException unused) {
            AlibcLogger.e("AlibcAlipay", "Alipay SDK is not available");
            return false;
        }
    }

    public boolean openAlipay(AlibcTradeCallback alibcTradeCallback, WebView webView, String str) {
        if (!isAlipaySDKAvailable()) {
            return false;
        }
        Uri parse = Uri.parse(str);
        String queryParameter = parse.getQueryParameter("alipay_trade_no");
        if (queryParameter == null) {
            queryParameter = parse.getQueryParameter("trade_nos");
        }
        String queryParameter2 = parse.getQueryParameter("payPhaseId");
        if (queryParameter2 == null) {
            queryParameter2 = parse.getQueryParameter("pay_phase_id");
        }
        String b = b(queryParameter, parse.getQueryParameter("s_id"), queryParameter2);
        g gVar = AlibcContext.executorService;
        a aVar = new a(this, webView, alibcTradeCallback, b, parse);
        gVar.a(aVar);
        return true;
    }
}
