package com.tencent.connect.auth;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.baichuan.android.trade.constants.UserTrackerConstants;
import com.tencent.connect.auth.AuthMap.Auth;
import com.tencent.connect.common.Constants;
import com.tencent.open.a;
import com.tencent.open.a.f;
import com.tencent.open.b.g;
import com.tencent.open.c.c;
import com.tencent.open.utils.Global;
import com.tencent.open.utils.ServerSetting;
import com.tencent.open.utils.SystemUtils;
import com.tencent.open.utils.Util;
import com.tencent.open.web.security.JniInterface;
import com.tencent.open.web.security.SecureJsInterface;
import com.tencent.open.web.security.b;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class AuthDialog extends Dialog {
    /* access modifiers changed from: private */
    public String a;
    /* access modifiers changed from: private */
    public OnTimeListener b;
    private IUiListener c;
    /* access modifiers changed from: private */
    public Handler d;
    private FrameLayout e;
    private LinearLayout f;
    /* access modifiers changed from: private */
    public FrameLayout g;
    private ProgressBar h;
    private String i;
    /* access modifiers changed from: private */
    public c j;
    /* access modifiers changed from: private */
    public Context k;
    /* access modifiers changed from: private */
    public b l;
    /* access modifiers changed from: private */
    public boolean m = false;
    /* access modifiers changed from: private */
    public int n;
    /* access modifiers changed from: private */
    public String o;
    /* access modifiers changed from: private */
    public String p;
    /* access modifiers changed from: private */
    public long q = 0;
    /* access modifiers changed from: private */
    public long r = StatisticConfig.MIN_UPLOAD_INTERVAL;
    /* access modifiers changed from: private */
    public HashMap<String, Runnable> s;

    /* compiled from: ProGuard */
    class LoginWebViewClient extends WebViewClient {
        private LoginWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            f.a("openSDK_LOG.AuthDialog", "-->Redirect URL: ".concat(String.valueOf(str)));
            if (str.startsWith(AuthConstants.REDIRECT_BROWSER_URI)) {
                JSONObject parseUrlToJson = Util.parseUrlToJson(str);
                AuthDialog.this.m = AuthDialog.this.e();
                if (!AuthDialog.this.m) {
                    if (parseUrlToJson.optString("fail_cb", null) != null) {
                        AuthDialog.this.callJs(parseUrlToJson.optString("fail_cb"), "");
                    } else if (parseUrlToJson.optInt("fall_to_wv") == 1) {
                        AuthDialog.a(AuthDialog.this, (Object) AuthDialog.this.a.indexOf("?") >= 0 ? "&" : "?");
                        AuthDialog.a(AuthDialog.this, (Object) "browser_error=1");
                        AuthDialog.this.j.loadUrl(AuthDialog.this.a);
                    } else {
                        String optString = parseUrlToJson.optString("redir", null);
                        if (optString != null) {
                            AuthDialog.this.j.loadUrl(optString);
                        }
                    }
                }
                return true;
            } else if (str.startsWith(ServerSetting.DEFAULT_REDIRECT_URI)) {
                AuthDialog.this.b.onComplete(Util.parseUrlToJson(str));
                AuthDialog.this.dismiss();
                return true;
            } else if (str.startsWith("auth://cancel")) {
                AuthDialog.this.b.onCancel();
                AuthDialog.this.dismiss();
                return true;
            } else if (str.startsWith("auth://close")) {
                AuthDialog.this.dismiss();
                return true;
            } else if (str.startsWith("download://")) {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(Uri.decode(str.substring(11))));
                    intent.addFlags(268435456);
                    AuthDialog.this.k.startActivity(intent);
                } catch (Exception e) {
                    f.b("openSDK_LOG.AuthDialog", "-->start download activity exception, e: ", e);
                }
                return true;
            } else if (str.startsWith(AuthConstants.PROGRESS_URI)) {
                try {
                    List<String> pathSegments = Uri.parse(str).getPathSegments();
                    if (pathSegments.isEmpty()) {
                        return true;
                    }
                    int intValue = Integer.valueOf(pathSegments.get(0)).intValue();
                    if (intValue == 0) {
                        AuthDialog.this.g.setVisibility(8);
                        AuthDialog.this.j.setVisibility(0);
                    } else if (intValue == 1) {
                        AuthDialog.this.g.setVisibility(0);
                    }
                    return true;
                } catch (Exception unused) {
                    return true;
                }
            } else if (str.startsWith(AuthConstants.ON_LOGIN_URI)) {
                try {
                    List<String> pathSegments2 = Uri.parse(str).getPathSegments();
                    if (!pathSegments2.isEmpty()) {
                        AuthDialog.this.p = pathSegments2.get(0);
                    }
                } catch (Exception unused2) {
                }
                return true;
            } else if (AuthDialog.this.l.a(AuthDialog.this.j, str)) {
                return true;
            } else {
                f.c("openSDK_LOG.AuthDialog", "-->Redirect URL: return false");
                return false;
            }
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
            StringBuilder sb = new StringBuilder("-->onReceivedError, errorCode: ");
            sb.append(i);
            sb.append(" | description: ");
            sb.append(str);
            f.c("openSDK_LOG.AuthDialog", sb.toString());
            if (!Util.checkNetWork(AuthDialog.this.k)) {
                AuthDialog.this.b.onError(new UiError(9001, "当前网络不可用，请稍后重试！", str2));
                AuthDialog.this.dismiss();
            } else if (!AuthDialog.this.o.startsWith(ServerSetting.DOWNLOAD_QQ_URL)) {
                long elapsedRealtime = SystemClock.elapsedRealtime() - AuthDialog.this.q;
                if (AuthDialog.this.n > 0 || elapsedRealtime >= AuthDialog.this.r) {
                    AuthDialog.this.j.loadUrl(AuthDialog.this.a());
                    return;
                }
                AuthDialog.this.n = AuthDialog.this.n + 1;
                AuthDialog.this.d.postDelayed(new Runnable() {
                    public void run() {
                        AuthDialog.this.j.loadUrl(AuthDialog.this.o);
                    }
                }, 500);
            } else {
                AuthDialog.this.b.onError(new UiError(i, str, str2));
                AuthDialog.this.dismiss();
            }
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            f.a("openSDK_LOG.AuthDialog", "-->onPageStarted, url: ".concat(String.valueOf(str)));
            super.onPageStarted(webView, str, bitmap);
            AuthDialog.this.g.setVisibility(0);
            AuthDialog.this.q = SystemClock.elapsedRealtime();
            if (!TextUtils.isEmpty(AuthDialog.this.o)) {
                AuthDialog.this.d.removeCallbacks((Runnable) AuthDialog.this.s.remove(AuthDialog.this.o));
            }
            AuthDialog.this.o = str;
            TimeOutRunable timeOutRunable = new TimeOutRunable(AuthDialog.this.o);
            AuthDialog.this.s.put(str, timeOutRunable);
            AuthDialog.this.d.postDelayed(timeOutRunable, 120000);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            f.a("openSDK_LOG.AuthDialog", "-->onPageFinished, url: ".concat(String.valueOf(str)));
            AuthDialog.this.g.setVisibility(8);
            if (AuthDialog.this.j != null) {
                AuthDialog.this.j.setVisibility(0);
            }
            if (!TextUtils.isEmpty(str)) {
                AuthDialog.this.d.removeCallbacks((Runnable) AuthDialog.this.s.remove(str));
            }
        }

        @TargetApi(8)
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.cancel();
            AuthDialog.this.b.onError(new UiError(sslError.getPrimaryError(), "请求不合法，请检查手机安全设置，如系统时间、代理等。", "ssl error"));
            AuthDialog.this.dismiss();
        }
    }

    /* compiled from: ProGuard */
    class OnTimeListener implements IUiListener {
        String a;
        String b;
        private String d;
        private IUiListener e;

        public OnTimeListener(String str, String str2, String str3, IUiListener iUiListener) {
            this.d = str;
            this.a = str2;
            this.b = str3;
            this.e = iUiListener;
        }

        /* access modifiers changed from: private */
        public void a(String str) {
            try {
                onComplete(Util.parseJson(str));
            } catch (JSONException e2) {
                e2.printStackTrace();
                onError(new UiError(-4, Constants.MSG_JSON_ERROR, str));
            }
        }

        public void onComplete(Object obj) {
            JSONObject jSONObject = (JSONObject) obj;
            g a2 = g.a();
            StringBuilder sb = new StringBuilder();
            sb.append(this.d);
            sb.append(UserTrackerConstants.U_H5);
            a2.a(sb.toString(), SystemClock.elapsedRealtime(), 0, 0, jSONObject.optInt("ret", -6), this.a, false);
            if (this.e != null) {
                this.e.onComplete(jSONObject);
                this.e = null;
            }
        }

        public void onError(UiError uiError) {
            String str;
            if (uiError.errorMessage != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(uiError.errorMessage);
                sb.append(this.a);
                str = sb.toString();
            } else {
                str = this.a;
            }
            g a2 = g.a();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.d);
            sb2.append(UserTrackerConstants.U_H5);
            a2.a(sb2.toString(), SystemClock.elapsedRealtime(), 0, 0, uiError.errorCode, str, false);
            AuthDialog.this.a(str);
            if (this.e != null) {
                this.e.onError(uiError);
                this.e = null;
            }
        }

        public void onCancel() {
            if (this.e != null) {
                this.e.onCancel();
                this.e = null;
            }
        }
    }

    /* compiled from: ProGuard */
    class THandler extends Handler {
        private OnTimeListener b;

        public THandler(OnTimeListener onTimeListener, Looper looper) {
            super(looper);
            this.b = onTimeListener;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    this.b.a((String) message.obj);
                    return;
                case 2:
                    this.b.onCancel();
                    return;
                case 3:
                    AuthDialog.b(AuthDialog.this.k, (String) message.obj);
                    break;
            }
        }
    }

    /* compiled from: ProGuard */
    class TimeOutRunable implements Runnable {
        String a = "";

        public TimeOutRunable(String str) {
            this.a = str;
        }

        public void run() {
            StringBuilder sb = new StringBuilder("-->timeoutUrl: ");
            sb.append(this.a);
            sb.append(" | mRetryUrl: ");
            sb.append(AuthDialog.this.o);
            f.a("openSDK_LOG.AuthDialog", sb.toString());
            if (this.a.equals(AuthDialog.this.o)) {
                AuthDialog.this.b.onError(new UiError(9002, "请求页面超时，请稍后重试！", AuthDialog.this.o));
                AuthDialog.this.dismiss();
            }
        }
    }

    static /* synthetic */ String a(AuthDialog authDialog, Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append(authDialog.a);
        sb.append(obj);
        String sb2 = sb.toString();
        authDialog.a = sb2;
        return sb2;
    }

    static {
        try {
            Context context = Global.getContext();
            if (context != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(context.getFilesDir().toString());
                sb.append("/");
                sb.append(AuthAgent.SECURE_LIB_NAME);
                if (new File(sb.toString()).exists()) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(context.getFilesDir().toString());
                    sb2.append("/");
                    sb2.append(AuthAgent.SECURE_LIB_NAME);
                    System.load(sb2.toString());
                    StringBuilder sb3 = new StringBuilder("-->load lib success:");
                    sb3.append(AuthAgent.SECURE_LIB_NAME);
                    f.c("openSDK_LOG.AuthDialog", sb3.toString());
                    return;
                }
                StringBuilder sb4 = new StringBuilder("-->fail, because so is not exists:");
                sb4.append(AuthAgent.SECURE_LIB_NAME);
                f.c("openSDK_LOG.AuthDialog", sb4.toString());
                return;
            }
            StringBuilder sb5 = new StringBuilder("-->load lib fail, because context is null:");
            sb5.append(AuthAgent.SECURE_LIB_NAME);
            f.c("openSDK_LOG.AuthDialog", sb5.toString());
        } catch (Exception e2) {
            StringBuilder sb6 = new StringBuilder("-->load lib error:");
            sb6.append(AuthAgent.SECURE_LIB_NAME);
            f.b("openSDK_LOG.AuthDialog", sb6.toString(), e2);
        }
    }

    public AuthDialog(Context context, String str, String str2, IUiListener iUiListener, QQToken qQToken) {
        super(context, 16973840);
        this.k = context;
        this.a = str2;
        OnTimeListener onTimeListener = new OnTimeListener(str, str2, qQToken.getAppId(), iUiListener);
        this.b = onTimeListener;
        this.d = new THandler(this.b, context.getMainLooper());
        this.c = iUiListener;
        this.i = str;
        this.l = new b();
        getWindow().setSoftInputMode(32);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        b();
        d();
        this.s = new HashMap<>();
    }

    public void onBackPressed() {
        if (!this.m) {
            this.b.onCancel();
        }
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
    }

    /* access modifiers changed from: private */
    public String a(String str) {
        StringBuilder sb = new StringBuilder(str);
        if (!TextUtils.isEmpty(this.p) && this.p.length() >= 4) {
            String substring = this.p.substring(this.p.length() - 4);
            sb.append("_u_");
            sb.append(substring);
        }
        return sb.toString();
    }

    /* access modifiers changed from: private */
    public String a() {
        String concat = ServerSetting.DOWNLOAD_QQ_URL.concat(String.valueOf(this.a.substring(this.a.indexOf("?") + 1)));
        f.c("openSDK_LOG.AuthDialog", "-->generateDownloadUrl, url: http://qzs.qq.com/open/mobile/login/qzsjump.html?");
        return concat;
    }

    private void b() {
        c();
        LayoutParams layoutParams = new LayoutParams(-1, -1);
        this.j = new c(this.k);
        this.j.setLayoutParams(layoutParams);
        this.e = new FrameLayout(this.k);
        layoutParams.gravity = 17;
        this.e.setLayoutParams(layoutParams);
        this.e.addView(this.j);
        this.e.addView(this.g);
        setContentView(this.e);
    }

    private void c() {
        TextView textView;
        this.h = new ProgressBar(this.k);
        this.h.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
        this.f = new LinearLayout(this.k);
        if (this.i.equals(SystemUtils.ACTION_LOGIN)) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 16;
            layoutParams.leftMargin = 5;
            textView = new TextView(this.k);
            if (Locale.getDefault().getLanguage().equals("zh")) {
                textView.setText("登录中...");
            } else {
                textView.setText("Logging in...");
            }
            textView.setTextColor(Color.rgb(255, 255, 255));
            textView.setTextSize(18.0f);
            textView.setLayoutParams(layoutParams);
        } else {
            textView = null;
        }
        LayoutParams layoutParams2 = new LayoutParams(-2, -2);
        layoutParams2.gravity = 17;
        this.f.setLayoutParams(layoutParams2);
        this.f.addView(this.h);
        if (textView != null) {
            this.f.addView(textView);
        }
        this.g = new FrameLayout(this.k);
        LayoutParams layoutParams3 = new LayoutParams(-1, -2);
        layoutParams3.leftMargin = 80;
        layoutParams3.rightMargin = 80;
        layoutParams3.topMargin = 40;
        layoutParams3.bottomMargin = 40;
        layoutParams3.gravity = 17;
        this.g.setLayoutParams(layoutParams3);
        this.g.setBackgroundResource(17301504);
        this.g.addView(this.f);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private void d() {
        this.j.setVerticalScrollBarEnabled(false);
        this.j.setHorizontalScrollBarEnabled(false);
        this.j.setWebViewClient(new LoginWebViewClient());
        this.j.setWebChromeClient(new WebChromeClient());
        this.j.clearFormData();
        this.j.clearSslPreferences();
        this.j.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                return true;
            }
        });
        this.j.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                    case 1:
                        if (!view.hasFocus()) {
                            view.requestFocus();
                            break;
                        }
                        break;
                }
                return false;
            }
        });
        WebSettings settings = this.j.getSettings();
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setCacheMode(-1);
        settings.setNeedInitialFocus(false);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        settings.setRenderPriority(RenderPriority.HIGH);
        settings.setJavaScriptEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDatabasePath(this.k.getDir("databases", 0).getPath());
        settings.setDomStorageEnabled(true);
        StringBuilder sb = new StringBuilder("-->mUrl : ");
        sb.append(this.a);
        f.a("openSDK_LOG.AuthDialog", sb.toString());
        this.o = this.a;
        this.j.loadUrl(this.a);
        this.j.setVisibility(4);
        this.j.getSettings().setSavePassword(false);
        this.l.a((a.b) new SecureJsInterface(), (String) "SecureJsInterface");
        SecureJsInterface.isPWDEdit = false;
        super.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialogInterface) {
                try {
                    JniInterface.clearAllPWD();
                } catch (Exception unused) {
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public boolean e() {
        AuthMap instance = AuthMap.getInstance();
        String makeKey = instance.makeKey();
        Auth auth = new Auth();
        auth.listener = this.c;
        auth.dialog = this;
        auth.key = makeKey;
        String str = instance.set(auth);
        String substring = this.a.substring(0, this.a.indexOf("?"));
        Bundle parseUrl = Util.parseUrl(this.a);
        parseUrl.putString("token_key", makeKey);
        parseUrl.putString("serial", str);
        parseUrl.putString("browser", "1");
        StringBuilder sb = new StringBuilder();
        sb.append(substring);
        sb.append("?");
        sb.append(Util.encodeUrl(parseUrl));
        this.a = sb.toString();
        return Util.openBrowser(this.k, this.a);
    }

    /* access modifiers changed from: private */
    public static void b(Context context, String str) {
        try {
            JSONObject parseJson = Util.parseJson(str);
            int i2 = parseJson.getInt("type");
            Toast.makeText(context.getApplicationContext(), parseJson.getString("msg"), i2).show();
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public void callJs(String str, String str2) {
        StringBuilder sb = new StringBuilder("javascript:");
        sb.append(str);
        sb.append("(");
        sb.append(str2);
        sb.append(");void(");
        sb.append(System.currentTimeMillis());
        sb.append(");");
        this.j.loadUrl(sb.toString());
    }

    public void dismiss() {
        this.s.clear();
        this.d.removeCallbacksAndMessages(null);
        if (isShowing()) {
            super.dismiss();
        }
        if (this.j != null) {
            this.j.destroy();
            this.j = null;
        }
    }
}
