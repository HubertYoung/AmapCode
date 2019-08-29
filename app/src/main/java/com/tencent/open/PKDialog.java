package com.tencent.open;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.open.a.f;
import com.tencent.open.b.g;
import com.tencent.open.c.a;
import com.tencent.open.c.a.C0067a;
import com.tencent.open.c.b;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.uc.webview.export.extension.UCCore;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class PKDialog extends b implements C0067a {
    private static final int MSG_CANCEL = 2;
    private static final int MSG_COMPLETE = 1;
    private static final int MSG_ON_LOAD = 4;
    private static final int MSG_SHOW_PROCESS = 5;
    private static final int MSG_SHOW_TIPS = 3;
    private static final String TAG = "openSDK_LOG.PKDialog";
    private static final int WEBVIEW_HEIGHT = 185;
    static Toast sToast;
    private IUiListener listener;
    private a mFlMain;
    /* access modifiers changed from: private */
    public Handler mHandler;
    /* access modifiers changed from: private */
    public OnTimeListener mListener;
    private String mUrl;
    /* access modifiers changed from: private */
    public WeakReference<Context> mWeakContext;
    /* access modifiers changed from: private */
    public b mWebView;
    private int mWebviewHeight;

    /* compiled from: ProGuard */
    class FbWebViewClient extends WebViewClient {
        private FbWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            f.a(PKDialog.TAG, "Redirect URL: ".concat(String.valueOf(str)));
            if (str.startsWith(ServerSetting.getInstance().getEnvUrl((Context) PKDialog.this.mWeakContext.get(), ServerSetting.DEFAULT_REDIRECT_URI))) {
                PKDialog.this.mListener.onComplete((Object) Util.parseUrlToJson(str));
                PKDialog.this.dismiss();
                return true;
            } else if (str.startsWith("auth://cancel")) {
                PKDialog.this.mListener.onCancel();
                PKDialog.this.dismiss();
                return true;
            } else if (!str.startsWith("auth://close")) {
                return false;
            } else {
                PKDialog.this.dismiss();
                return true;
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            PKDialog.this.mListener.onError(new UiError(i, str, str2));
            if (!(PKDialog.this.mWeakContext == null || PKDialog.this.mWeakContext.get() == null)) {
                Toast.makeText((Context) PKDialog.this.mWeakContext.get(), "网络连接异常或系统错误", 0).show();
            }
            PKDialog.this.dismiss();
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            f.a(PKDialog.TAG, "Webview loading URL: ".concat(String.valueOf(str)));
            super.onPageStarted(webView, str, bitmap);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            PKDialog.this.mWebView.setVisibility(0);
        }
    }

    /* compiled from: ProGuard */
    class JsListener extends a.b {
        private JsListener() {
        }

        public void onComplete(String str) {
            PKDialog.this.mHandler.obtainMessage(1, str).sendToTarget();
            f.e(PKDialog.TAG, "JsListener onComplete".concat(String.valueOf(str)));
            PKDialog.this.dismiss();
        }

        public void onCancel(String str) {
            PKDialog.this.mHandler.obtainMessage(2, str).sendToTarget();
            PKDialog.this.dismiss();
        }

        public void showMsg(String str) {
            PKDialog.this.mHandler.obtainMessage(3, str).sendToTarget();
        }

        public void onLoad(String str) {
            PKDialog.this.mHandler.obtainMessage(4, str).sendToTarget();
        }
    }

    /* compiled from: ProGuard */
    static class OnTimeListener implements IUiListener {
        private String mAction;
        String mAppid;
        String mUrl;
        private WeakReference<Context> mWeakCtx;
        private IUiListener mWeakL;

        public OnTimeListener(Context context, String str, String str2, String str3, IUiListener iUiListener) {
            this.mWeakCtx = new WeakReference<>(context);
            this.mAction = str;
            this.mUrl = str2;
            this.mAppid = str3;
            this.mWeakL = iUiListener;
        }

        /* access modifiers changed from: private */
        public void onComplete(String str) {
            try {
                onComplete((Object) Util.parseJson(str));
            } catch (JSONException e) {
                e.printStackTrace();
                onError(new UiError(-4, Constants.MSG_JSON_ERROR, str));
            }
        }

        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            g a = g.a();
            StringBuilder sb = new StringBuilder();
            sb.append(this.mAction);
            sb.append(UserTrackerConstants.U_H5);
            a.a(sb.toString(), SystemClock.elapsedRealtime(), 0, 0, jSONObject.optInt("ret", -6), this.mUrl, false);
            if (this.mWeakL != null) {
                this.mWeakL.onComplete(jSONObject);
                this.mWeakL = null;
            }
        }

        public void onError(UiError uiError) {
            String str;
            if (uiError.errorMessage != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(uiError.errorMessage);
                sb.append(this.mUrl);
                str = sb.toString();
            } else {
                str = this.mUrl;
            }
            String str2 = str;
            g a = g.a();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.mAction);
            sb2.append(UserTrackerConstants.U_H5);
            a.a(sb2.toString(), SystemClock.elapsedRealtime(), 0, 0, uiError.errorCode, str2, false);
            if (this.mWeakL != null) {
                this.mWeakL.onError(uiError);
                this.mWeakL = null;
            }
        }

        public void onCancel() {
            if (this.mWeakL != null) {
                this.mWeakL.onCancel();
                this.mWeakL = null;
            }
        }
    }

    /* compiled from: ProGuard */
    class THandler extends Handler {
        private OnTimeListener mL;

        public THandler(OnTimeListener onTimeListener, Looper looper) {
            super(looper);
            this.mL = onTimeListener;
        }

        public void handleMessage(Message message) {
            StringBuilder sb = new StringBuilder("msg = ");
            sb.append(message.what);
            f.b(PKDialog.TAG, sb.toString());
            switch (message.what) {
                case 1:
                    this.mL.onComplete((String) message.obj);
                    return;
                case 2:
                    this.mL.onCancel();
                    return;
                case 3:
                    if (!(PKDialog.this.mWeakContext == null || PKDialog.this.mWeakContext.get() == null)) {
                        PKDialog.showTips((Context) PKDialog.this.mWeakContext.get(), (String) message.obj);
                        return;
                    }
                case 4:
                    return;
                case 5:
                    if (!(PKDialog.this.mWeakContext == null || PKDialog.this.mWeakContext.get() == null)) {
                        PKDialog.showProcessDialog((Context) PKDialog.this.mWeakContext.get(), (String) message.obj);
                        break;
                    }
            }
        }
    }

    public PKDialog(Context context, String str, String str2, IUiListener iUiListener, QQToken qQToken) {
        super(context, 16973840);
        this.mWeakContext = new WeakReference<>(context);
        this.mUrl = str2;
        OnTimeListener onTimeListener = new OnTimeListener(context, str, str2, qQToken.getAppId(), iUiListener);
        this.mListener = onTimeListener;
        this.mHandler = new THandler(this.mListener, context.getMainLooper());
        this.listener = iUiListener;
        this.mWebviewHeight = Math.round(context.getResources().getDisplayMetrics().density * 185.0f);
        StringBuilder sb = new StringBuilder("density=");
        sb.append(context.getResources().getDisplayMetrics().density);
        sb.append("; webviewHeight=");
        sb.append(this.mWebviewHeight);
        f.e(TAG, sb.toString());
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        getWindow().setSoftInputMode(16);
        getWindow().setSoftInputMode(1);
        createViews();
        initViews();
    }

    private void createViews() {
        this.mFlMain = new a((Context) this.mWeakContext.get());
        this.mFlMain.setBackgroundColor(1711276032);
        this.mFlMain.setLayoutParams(new LayoutParams(-1, -1));
        this.mWebView = new b((Context) this.mWeakContext.get());
        this.mWebView.setBackgroundColor(0);
        this.mWebView.setBackgroundDrawable(null);
        if (VERSION.SDK_INT >= 11) {
            try {
                View.class.getMethod("setLayerType", new Class[]{Integer.TYPE, Paint.class}).invoke(this.mWebView, new Object[]{Integer.valueOf(1), new Paint()});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LayoutParams layoutParams = new LayoutParams(-1, this.mWebviewHeight);
        layoutParams.addRule(13, -1);
        this.mWebView.setLayoutParams(layoutParams);
        this.mFlMain.addView(this.mWebView);
        this.mFlMain.a(this);
        setContentView(this.mFlMain);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void initViews() {
        this.mWebView.setVerticalScrollBarEnabled(false);
        this.mWebView.setHorizontalScrollBarEnabled(false);
        this.mWebView.setWebViewClient(new FbWebViewClient());
        this.mWebView.setWebChromeClient(this.mChromeClient);
        this.mWebView.clearFormData();
        WebSettings settings = this.mWebView.getSettings();
        if (settings != null) {
            settings.setSavePassword(false);
            settings.setSaveFormData(false);
            settings.setCacheMode(-1);
            settings.setNeedInitialFocus(false);
            settings.setBuiltInZoomControls(true);
            settings.setSupportZoom(true);
            settings.setRenderPriority(RenderPriority.HIGH);
            settings.setJavaScriptEnabled(true);
            if (!(this.mWeakContext == null || this.mWeakContext.get() == null)) {
                settings.setDatabaseEnabled(true);
                settings.setDatabasePath(((Context) this.mWeakContext.get()).getApplicationContext().getDir("databases", 0).getPath());
            }
            settings.setDomStorageEnabled(true);
            this.jsBridge.a((a.b) new JsListener(), (String) "sdk_js_if");
            this.mWebView.clearView();
            this.mWebView.loadUrl(this.mUrl);
            this.mWebView.getSettings().setSavePassword(false);
        }
    }

    public void callJs(String str, String str2) {
        StringBuilder sb = new StringBuilder("javascript:");
        sb.append(str);
        sb.append("(");
        sb.append(str2);
        sb.append(")");
        this.mWebView.loadUrl(sb.toString());
    }

    /* access modifiers changed from: private */
    public static void showTips(Context context, String str) {
        try {
            JSONObject parseJson = Util.parseJson(str);
            int i = parseJson.getInt("type");
            String string = parseJson.getString("msg");
            if (i == 0) {
                if (sToast == null) {
                    sToast = Toast.makeText(context, string, 0);
                } else {
                    Toast toast = sToast;
                    toast.setView(toast.getView());
                    sToast.setText(string);
                    sToast.setDuration(0);
                }
                sToast.show();
                return;
            }
            if (i == 1) {
                if (sToast == null) {
                    sToast = Toast.makeText(context, string, 1);
                } else {
                    Toast toast2 = sToast;
                    toast2.setView(toast2.getView());
                    sToast.setText(string);
                    sToast.setDuration(1);
                }
                sToast.show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public static void showProcessDialog(Context context, String str) {
        if (context != null && str != null) {
            try {
                JSONObject parseJson = Util.parseJson(str);
                parseJson.getInt("action");
                parseJson.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadUrlWithBrowser(String str, String str2, String str3) throws Exception {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(str, str2));
        intent.setAction("android.intent.action.VIEW");
        intent.addFlags(UCCore.VERIFY_POLICY_QUICK);
        intent.addFlags(268435456);
        intent.setData(Uri.parse(str3));
        if (this.mWeakContext != null && this.mWeakContext.get() != null) {
            ((Context) this.mWeakContext.get()).startActivity(intent);
        }
    }

    public void onKeyboardShown(int i) {
        if (!(this.mWeakContext == null || this.mWeakContext.get() == null)) {
            if (i >= this.mWebviewHeight || 2 != ((Context) this.mWeakContext.get()).getResources().getConfiguration().orientation) {
                this.mWebView.getLayoutParams().height = this.mWebviewHeight;
            } else {
                this.mWebView.getLayoutParams().height = i;
            }
        }
        f.e(TAG, "onKeyboardShown keyboard show");
    }

    public void onKeyboardHidden() {
        this.mWebView.getLayoutParams().height = this.mWebviewHeight;
        f.e(TAG, "onKeyboardHidden keyboard hide");
    }

    /* access modifiers changed from: protected */
    public void onConsoleMessage(String str) {
        f.b(TAG, "--onConsoleMessage--");
        try {
            this.jsBridge.a((WebView) this.mWebView, str);
        } catch (Exception unused) {
        }
    }
}
