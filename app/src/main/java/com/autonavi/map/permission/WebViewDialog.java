package com.autonavi.map.permission;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager.LayoutParams;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.webview.android.SafeWebView;
import com.autonavi.widget.webview.android.SafeWebView.WebViewClientEx;
import com.feather.support.SoftInputAdjustRootLinearLayout;
import java.lang.ref.WeakReference;

final class WebViewDialog extends Dialog {
    private String a;
    /* access modifiers changed from: private */
    public boolean b;
    /* access modifiers changed from: private */
    public boolean c;
    /* access modifiers changed from: private */
    public SafeWebView d;
    private SplashPrivacyPolicyLayout e;
    /* access modifiers changed from: private */
    public ViewGroup f;
    /* access modifiers changed from: private */
    public TitleBar g;
    private TermsWebViewClient h = new TermsWebViewClient(this);
    private OnClickListener i = new OnClickListener() {
        public final void onClick(View view) {
            WebViewDialog.this.onBackPressed();
        }
    };
    private OnClickListener j = new OnClickListener() {
        public final void onClick(View view) {
            if (WebViewDialog.this.d != null) {
                WebViewDialog.this.d.reload();
            }
        }
    };

    static class MyWebChromClient extends WebChromeClient {
        private WeakReference<WebViewDialog> mHost;

        public MyWebChromClient(WebViewDialog webViewDialog) {
            this.mHost = new WeakReference<>(webViewDialog);
        }

        public void onReceivedTitle(WebView webView, String str) {
            if (str != null && !str.trim().matches("^[a-zA-Z0-9]+.*")) {
                WebViewDialog webViewDialog = (WebViewDialog) this.mHost.get();
                if (!(webViewDialog == null || webViewDialog.g == null)) {
                    webViewDialog.g.setTitle(str);
                }
            }
        }
    }

    static final class TermsWebViewClient extends WebViewClientEx {
        private WeakReference<WebViewDialog> mHost;
        private boolean mReceiveError;

        public TermsWebViewClient(WebViewDialog webViewDialog) {
            this.mHost = new WeakReference<>(webViewDialog);
        }

        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            this.mReceiveError = false;
        }

        public final void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            WebViewDialog webViewDialog = (WebViewDialog) this.mHost.get();
            if (webViewDialog != null) {
                if (this.mReceiveError) {
                    WebViewDialog.c(webViewDialog);
                    return;
                }
                webViewDialog.f.setVisibility(8);
            }
        }

        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            WebViewDialog webViewDialog = (WebViewDialog) this.mHost.get();
            if (webViewDialog == null) {
                return true;
            }
            Uri parse = Uri.parse(str);
            if (!webViewDialog.b) {
                if ("http".equalsIgnoreCase(parse.getScheme()) || "https".equalsIgnoreCase(parse.getScheme())) {
                    new WebViewDialog(webViewDialog.getContext(), str).show();
                }
                return true;
            } else if ("http".equalsIgnoreCase(parse.getScheme()) || "https".equalsIgnoreCase(parse.getScheme())) {
                return super.shouldOverrideUrlLoading(webView, str);
            } else {
                return true;
            }
        }

        public final void onReceivedSslError(WebView webView, final SslErrorHandler sslErrorHandler, SslError sslError) {
            if (((WebViewDialog) this.mHost.get()) == null) {
                sslErrorHandler.cancel();
                return;
            }
            final Dialog dialog = new Dialog(webView.getContext(), R.style.dialog_terms);
            dialog.setContentView(R.layout.dialog_terms_ssl_error);
            View findViewById = dialog.findViewById(R.id.bt_terms_left);
            if (findViewById != null) {
                findViewById.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        dialog.dismiss();
                        sslErrorHandler.cancel();
                    }
                });
            }
            View findViewById2 = dialog.findViewById(R.id.bt_terms_right);
            if (findViewById2 != null) {
                findViewById2.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        dialog.dismiss();
                        sslErrorHandler.proceed();
                    }
                });
            }
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        public final void onReceivedError(WebView webView, int i, String str, String str2) {
            this.mReceiveError = true;
        }
    }

    public final void setContentView(View view) {
        SoftInputAdjustRootLinearLayout softInputAdjustRootLinearLayout = new SoftInputAdjustRootLinearLayout(getContext());
        softInputAdjustRootLinearLayout.addView(view);
        view.setFitsSystemWindows(true);
        super.setContentView(softInputAdjustRootLinearLayout);
    }

    public final void setContentView(int i2) {
        SoftInputAdjustRootLinearLayout softInputAdjustRootLinearLayout = new SoftInputAdjustRootLinearLayout(getContext());
        LayoutInflater.from(getContext()).inflate(i2, softInputAdjustRootLinearLayout, true).setFitsSystemWindows(true);
        super.setContentView(softInputAdjustRootLinearLayout);
    }

    WebViewDialog(Context context, String str) {
        super(context, R.style.custom_declare_dlg);
        setCancelable(true);
        requestWindowFeature(1);
        euk.a((Dialog) this, 0);
        setContentView(R.layout.webview_dialog);
        LayoutParams attributes = getWindow().getAttributes();
        int width = ags.a(context).width();
        int height = ags.a(context).height();
        if (height * 3 < width * 4) {
            attributes.width = (height * 9) / 16;
            setCancelable(false);
        } else {
            attributes.width = -1;
            setCancelable(true);
        }
        attributes.height = -1;
        getWindow().setBackgroundDrawable(new ColorDrawable(-1));
        getWindow().setAttributes(attributes);
        getWindow().setWindowAnimations(R.style.PermissionDialog);
        this.a = str;
        this.b = true;
    }

    public final void onWindowFocusChanged(boolean z) {
        if (z) {
            euk.a((Dialog) this);
        }
        super.onWindowFocusChanged(z);
    }

    public final void onBackPressed() {
        if (!aaw.c(getContext())) {
            dismiss();
        } else if (this.d == null || !this.d.canGoBack()) {
            dismiss();
        } else {
            this.d.goBack();
        }
    }

    public final void onStop() {
        super.onStop();
        if (this.d != null) {
            try {
                ViewParent parent = this.d.getParent();
                if (parent != null) {
                    ((ViewGroup) parent).removeView(this.d);
                }
                this.d.stopLoading();
                this.d.getSettings().setJavaScriptEnabled(false);
                this.d.clearHistory();
                this.d.clearView();
                this.d.removeAllViews();
                this.d.destroy();
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: protected */
    public final void onCreate(Bundle bundle) {
        if (this.b) {
            this.g = (TitleBar) findViewById(R.id.title_bar);
            this.g.setWidgetVisibility(33, 8);
            this.g.setWidgetVisibility(2, 8);
            this.g.setOnBackClickListener(this.i);
        } else {
            new OnClickListener() {
                public final void onClick(View view) {
                    if (view.getId() == R.id.agree) {
                        WebViewDialog.this.c = true;
                    } else {
                        WebViewDialog.this.c = false;
                    }
                    WebViewDialog.this.dismiss();
                }
            };
        }
        this.f = (ViewGroup) findViewById(R.id.vg_terms_web_view_container);
        findViewById(R.id.tv_terms_refresh).setOnClickListener(this.j);
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.webview_wrapper);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        try {
            this.d = new SafeWebView(getContext());
            this.e = new SplashPrivacyPolicyLayout(getContext());
            frameLayout.addView(this.e, layoutParams);
        } catch (Exception e2) {
            e2.printStackTrace();
            frameLayout.removeAllViews();
            TextView textView = new TextView(getContext().getApplicationContext());
            textView.setBackgroundColor(-1);
            textView.setMovementMethod(ScrollingMovementMethod.getInstance());
            textView.setText(Html.fromHtml(buv.a));
            ScrollView scrollView = new ScrollView(getContext().getApplicationContext());
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(-1, -1);
            int dimension = (int) getContext().getResources().getDimension(R.dimen.f_s_10);
            layoutParams2.setMargins(dimension, dimension, dimension, 0);
            scrollView.addView(textView, layoutParams2);
            frameLayout.addView(scrollView, layoutParams);
        }
    }

    static /* synthetic */ void c(WebViewDialog webViewDialog) {
        new StringBuilder("loadError: ").append(webViewDialog.d.getUrl());
        webViewDialog.f.setVisibility(0);
    }
}
