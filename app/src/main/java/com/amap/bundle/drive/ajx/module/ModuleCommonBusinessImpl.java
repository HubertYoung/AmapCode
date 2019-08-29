package com.amap.bundle.drive.ajx.module;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogItem;
import com.alipay.mobile.tinyappcustom.h5plugin.ocr.tools.BehavorReporter;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.drive.ajx.inter.IMotorActivityCallback;
import com.amap.bundle.drive.ajx.inter.INaviUiActionListener;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.webview.widget.WebViewEx;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.uc.webview.export.SslErrorHandler;
import com.uc.webview.export.WebResourceError;
import com.uc.webview.export.WebResourceRequest;
import com.uc.webview.export.WebResourceResponse;
import com.uc.webview.export.WebView;
import com.uc.webview.export.WebViewClient;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

public class ModuleCommonBusinessImpl {
    private final String TAG = "ModuleCommonBusinessImpl";
    private INaviUiActionListener mINaviUiActionListener = null;
    private IMotorActivityCallback mMotorEndActivityCallback;
    private IMotorActivityCallback mMotorResultActivityCallback;

    public String getCarrierInfo() {
        String str;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        StringBuffer stringBuffer3 = new StringBuffer();
        a[] b = kx.b(AMapAppGlobal.getApplication());
        log("getCarrierInfo   infos.length:1");
        for (int i = 0; i <= 0; i++) {
            a aVar = b[i];
            switch (aVar.c) {
                case 0:
                    str = "";
                    break;
                case 1:
                    str = "cu";
                    break;
                case 2:
                    str = LogItem.MM_C43_K4_CAMERA_TIME;
                    break;
                case 3:
                    str = "cm";
                    break;
                default:
                    str = "";
                    break;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(",");
            stringBuffer.append(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append(aVar.a);
            sb2.append(",");
            stringBuffer2.append(sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append(aVar.b);
            sb3.append(",");
            stringBuffer3.append(sb3.toString());
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        stringBuffer2.deleteCharAt(stringBuffer2.length() - 1);
        stringBuffer3.deleteCharAt(stringBuffer3.length() - 1);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("carrier", stringBuffer.toString());
            jSONObject.put("carrierName", stringBuffer2.toString());
            jSONObject.put("carrierCode", stringBuffer3.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder sb4 = new StringBuilder("getCarrierInfo:  ");
        sb4.append(jSONObject.toString());
        log(sb4.toString());
        return jSONObject.toString();
    }

    public String getAosUrl() {
        return ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.DRIVE_AOS_URL_KEY);
    }

    public void startAlipay() {
        WebViewEx webViewEx = new WebViewEx(AMapAppGlobal.getApplication());
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        webViewEx.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                if (TextUtils.isEmpty(str) || !str.startsWith("alipays://")) {
                    return false;
                }
                if (new Intent("android.intent.action.VIEW", Uri.parse("alipays://platformapi/startApp")).resolveActivity(AMapAppGlobal.getApplication().getPackageManager()) != null) {
                    Uri parse = Uri.parse(str);
                    Intent intent = new Intent();
                    intent.setData(parse);
                    if (AMapAppGlobal.getTopActivity() == null) {
                        return false;
                    }
                    try {
                        AMapAppGlobal.getTopActivity().startActivity(intent);
                    } catch (Exception unused) {
                        ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.navi_alipay_not_install));
                    }
                    return true;
                }
                ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.navi_alipay_not_install));
                return true;
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                super.onReceivedError(webView, i, str, str2);
                tq.b("NaviMonitor", BehavorReporter.PROVIDE_BY_ALIPAY, "onReceivedError");
                onCommonErrorHandler();
            }

            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                tq.b("NaviMonitor", BehavorReporter.PROVIDE_BY_ALIPAY, "onReceivedError");
                onCommonErrorHandler();
            }

            public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
                super.onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
                tq.b("NaviMonitor", BehavorReporter.PROVIDE_BY_ALIPAY, "onReceivedHttpError");
                onCommonErrorHandler();
            }

            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
                super.onReceivedSslError(webView, sslErrorHandler, sslError);
                tq.b("NaviMonitor", BehavorReporter.PROVIDE_BY_ALIPAY, "onReceivedSslError");
                onCommonErrorHandler();
            }

            public void onCommonErrorHandler() {
                if (!atomicBoolean.get()) {
                    atomicBoolean.set(true);
                    ToastHelper.showLongToast("网络请求失败，请稍后重试");
                }
            }
        });
        webViewEx.loadUrl("https://m.alipay.com/VCDZA5R");
    }

    public void jumpToMainPage() {
        if (this.mINaviUiActionListener != null) {
            this.mINaviUiActionListener.onFinishNaviPage();
        }
        PageBundle pageBundle = new PageBundle();
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.finish();
            pageContext.startPage((String) "amap.basemap.action.default_page", pageBundle);
        }
    }

    private void log(String str) {
        AMapLog.i("ModuleCommonBusinessImpl", "module_opt ".concat(String.valueOf(str)));
    }

    public void setMotorEndActivityCallback(IMotorActivityCallback iMotorActivityCallback) {
        this.mMotorEndActivityCallback = iMotorActivityCallback;
    }

    public void setMotorResultActivityCallback(IMotorActivityCallback iMotorActivityCallback) {
        this.mMotorResultActivityCallback = iMotorActivityCallback;
    }

    public void fetchActivityWithSceneType(String str) {
        if ("26".equals(str)) {
            if (this.mMotorEndActivityCallback != null) {
                this.mMotorEndActivityCallback.onActivityCallback();
            }
        } else if ("25".equals(str) && this.mMotorResultActivityCallback != null) {
            this.mMotorResultActivityCallback.onActivityCallback();
        }
    }

    public void setINaviUiActionListener(INaviUiActionListener iNaviUiActionListener) {
        this.mINaviUiActionListener = iNaviUiActionListener;
    }
}
