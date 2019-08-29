package com.tencent.open;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.tencent.connect.auth.AuthConstants;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.open.a.f;
import com.tencent.open.b.g;
import com.tencent.open.c.b;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.Util;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.lang.ref.WeakReference;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class TDialog extends b {
    static final LayoutParams a = new LayoutParams(-1, -1);
    static Toast b;
    private static WeakReference<ProgressDialog> d;
    /* access modifiers changed from: private */
    public WeakReference<Context> c;
    private String e;
    /* access modifiers changed from: private */
    public OnTimeListener f;
    private IUiListener g;
    private FrameLayout h;
    /* access modifiers changed from: private */
    public b i;
    /* access modifiers changed from: private */
    public Handler j;
    private boolean k = false;
    private QQToken l = null;

    /* compiled from: ProGuard */
    class FbWebViewClient extends WebViewClient {
        private FbWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            f.a("openSDK_LOG.TDialog", "Redirect URL: ".concat(String.valueOf(str)));
            if (str.startsWith(ServerSetting.getInstance().getEnvUrl((Context) TDialog.this.c.get(), ServerSetting.DEFAULT_REDIRECT_URI))) {
                TDialog.this.f.onComplete((Object) Util.parseUrlToJson(str));
                if (TDialog.this.isShowing()) {
                    TDialog.this.dismiss();
                }
                return true;
            } else if (str.startsWith("auth://cancel")) {
                TDialog.this.f.onCancel();
                if (TDialog.this.isShowing()) {
                    TDialog.this.dismiss();
                }
                return true;
            } else if (str.startsWith("auth://close")) {
                if (TDialog.this.isShowing()) {
                    TDialog.this.dismiss();
                }
                return true;
            } else if (str.startsWith("download://")) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(str.substring(11))));
                intent.addFlags(268435456);
                if (!(TDialog.this.c == null || TDialog.this.c.get() == null)) {
                    ((Context) TDialog.this.c.get()).startActivity(intent);
                }
                return true;
            } else if (str.startsWith(AuthConstants.PROGRESS_URI)) {
                return true;
            } else {
                return false;
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            TDialog.this.f.onError(new UiError(i, str, str2));
            if (!(TDialog.this.c == null || TDialog.this.c.get() == null)) {
                Toast.makeText((Context) TDialog.this.c.get(), "网络连接异常或系统错误", 0).show();
            }
            TDialog.this.dismiss();
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            f.a("openSDK_LOG.TDialog", "Webview loading URL: ".concat(String.valueOf(str)));
            super.onPageStarted(webView, str, bitmap);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            TDialog.this.i.setVisibility(0);
        }
    }

    /* compiled from: ProGuard */
    class JsListener extends a.b {
        private JsListener() {
        }

        public void onAddShare(String str) {
            f.b("openSDK_LOG.TDialog", "JsListener onAddShare");
            onComplete(str);
        }

        public void onInvite(String str) {
            onComplete(str);
        }

        public void onCancelAddShare(String str) {
            f.e("openSDK_LOG.TDialog", "JsListener onCancelAddShare".concat(String.valueOf(str)));
            onCancel("cancel");
        }

        public void onCancelLogin() {
            onCancel("");
        }

        public void onCancelInvite() {
            f.e("openSDK_LOG.TDialog", "JsListener onCancelInvite");
            onCancel("");
        }

        public void onComplete(String str) {
            TDialog.this.j.obtainMessage(1, str).sendToTarget();
            f.e("openSDK_LOG.TDialog", "JsListener onComplete".concat(String.valueOf(str)));
            TDialog.this.dismiss();
        }

        public void onCancel(String str) {
            f.e("openSDK_LOG.TDialog", "JsListener onCancel --msg = ".concat(String.valueOf(str)));
            TDialog.this.j.obtainMessage(2, str).sendToTarget();
            TDialog.this.dismiss();
        }

        public void showMsg(String str) {
            TDialog.this.j.obtainMessage(3, str).sendToTarget();
        }

        public void onLoad(String str) {
            TDialog.this.j.obtainMessage(4, str).sendToTarget();
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
            StringBuilder sb = new StringBuilder("--handleMessage--msg.WHAT = ");
            sb.append(message.what);
            f.b("openSDK_LOG.TDialog", sb.toString());
            switch (message.what) {
                case 1:
                    this.mL.onComplete((String) message.obj);
                    return;
                case 2:
                    this.mL.onCancel();
                    return;
                case 3:
                    if (!(TDialog.this.c == null || TDialog.this.c.get() == null)) {
                        TDialog.c((Context) TDialog.this.c.get(), (String) message.obj);
                        return;
                    }
                case 4:
                    return;
                case 5:
                    if (!(TDialog.this.c == null || TDialog.this.c.get() == null)) {
                        TDialog.d((Context) TDialog.this.c.get(), (String) message.obj);
                        break;
                    }
            }
        }
    }

    public TDialog(Context context, String str, String str2, IUiListener iUiListener, QQToken qQToken) {
        super(context, 16973840);
        this.c = new WeakReference<>(context);
        this.e = str2;
        OnTimeListener onTimeListener = new OnTimeListener(context, str, str2, qQToken.getAppId(), iUiListener);
        this.f = onTimeListener;
        this.j = new THandler(this.f, context.getMainLooper());
        this.g = iUiListener;
        this.l = qQToken;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        a();
        b();
    }

    public void onBackPressed() {
        if (this.f != null) {
            this.f.onCancel();
        }
        super.onBackPressed();
    }

    private void a() {
        new TextView((Context) this.c.get()).setText("test");
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        this.i = new b((Context) this.c.get());
        this.i.setLayoutParams(layoutParams);
        this.h = new FrameLayout((Context) this.c.get());
        layoutParams.gravity = 17;
        this.h.setLayoutParams(layoutParams);
        this.h.addView(this.i);
        setContentView(this.h);
    }

    /* access modifiers changed from: protected */
    public void onConsoleMessage(String str) {
        f.b("openSDK_LOG.TDialog", "--onConsoleMessage--");
        try {
            this.jsBridge.a((WebView) this.i, str);
        } catch (Exception unused) {
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void b() {
        this.i.setVerticalScrollBarEnabled(false);
        this.i.setHorizontalScrollBarEnabled(false);
        this.i.setWebViewClient(new FbWebViewClient());
        this.i.setWebChromeClient(this.mChromeClient);
        this.i.clearFormData();
        WebSettings settings = this.i.getSettings();
        if (settings != null) {
            settings.setSavePassword(false);
            settings.setSaveFormData(false);
            settings.setCacheMode(-1);
            settings.setNeedInitialFocus(false);
            settings.setBuiltInZoomControls(true);
            settings.setSupportZoom(true);
            settings.setRenderPriority(RenderPriority.HIGH);
            settings.setJavaScriptEnabled(true);
            if (!(this.c == null || this.c.get() == null)) {
                settings.setDatabaseEnabled(true);
                settings.setDatabasePath(((Context) this.c.get()).getApplicationContext().getDir("databases", 0).getPath());
            }
            settings.setDomStorageEnabled(true);
            this.jsBridge.a((a.b) new JsListener(), (String) "sdk_js_if");
            this.i.loadUrl(this.e);
            this.i.setLayoutParams(a);
            this.i.setVisibility(4);
            this.i.getSettings().setSavePassword(false);
        }
    }

    /* access modifiers changed from: private */
    public static void c(Context context, String str) {
        try {
            JSONObject parseJson = Util.parseJson(str);
            int i2 = parseJson.getInt("type");
            String string = parseJson.getString("msg");
            if (i2 == 0) {
                if (b == null) {
                    b = Toast.makeText(context, string, 0);
                } else {
                    Toast toast = b;
                    toast.setView(toast.getView());
                    b.setText(string);
                    b.setDuration(0);
                }
                b.show();
                return;
            }
            if (i2 == 1) {
                if (b == null) {
                    b = Toast.makeText(context, string, 1);
                } else {
                    Toast toast2 = b;
                    toast2.setView(toast2.getView());
                    b.setText(string);
                    b.setDuration(1);
                }
                b.show();
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public static void d(Context context, String str) {
        if (context != null && str != null) {
            try {
                JSONObject parseJson = Util.parseJson(str);
                int i2 = parseJson.getInt("action");
                String string = parseJson.getString("msg");
                if (i2 == 1) {
                    if (d != null) {
                        if (d.get() != null) {
                            ((ProgressDialog) d.get()).setMessage(string);
                            if (!((ProgressDialog) d.get()).isShowing()) {
                                ((ProgressDialog) d.get()).show();
                                return;
                            }
                        }
                    }
                    ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage(string);
                    d = new WeakReference<>(progressDialog);
                    progressDialog.show();
                } else if (i2 == 0 && d != null && d.get() != null && ((ProgressDialog) d.get()).isShowing()) {
                    ((ProgressDialog) d.get()).dismiss();
                    d = null;
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }
}
