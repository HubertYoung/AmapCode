package com.autonavi.map.permission;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.autonavi.map.permission.SplashPrivacyPolicyLayout.b;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.webview.android.SafeWebView;
import com.autonavi.widget.webview.android.SafeWebView.WebViewClientEx;
import com.feather.support.SoftInputAdjustRootLinearLayout;
import java.lang.ref.WeakReference;

public final class SplashPrivacyPolicyDialog extends Dialog {
    public boolean a;
    private String b;
    /* access modifiers changed from: private */
    public boolean c = false;
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
            SplashPrivacyPolicyDialog.this.onBackPressed();
        }
    };
    private OnClickListener j = new OnClickListener() {
        public final void onClick(View view) {
            if (SplashPrivacyPolicyDialog.this.d != null) {
                SplashPrivacyPolicyDialog.this.d.reload();
            }
        }
    };

    static class MyWebChromClient extends WebChromeClient {
        private WeakReference<SplashPrivacyPolicyDialog> mHost;

        public MyWebChromClient(SplashPrivacyPolicyDialog splashPrivacyPolicyDialog) {
            this.mHost = new WeakReference<>(splashPrivacyPolicyDialog);
        }

        public void onReceivedTitle(WebView webView, String str) {
            if (str != null && !str.trim().matches("^[a-zA-Z0-9]+.*")) {
                SplashPrivacyPolicyDialog splashPrivacyPolicyDialog = (SplashPrivacyPolicyDialog) this.mHost.get();
                if (!(splashPrivacyPolicyDialog == null || splashPrivacyPolicyDialog.g == null)) {
                    splashPrivacyPolicyDialog.g.setTitle(str);
                }
            }
        }
    }

    static final class TermsWebViewClient extends WebViewClientEx {
        private WeakReference<SplashPrivacyPolicyDialog> mHost;
        private boolean mReceiveError;

        public TermsWebViewClient(SplashPrivacyPolicyDialog splashPrivacyPolicyDialog) {
            this.mHost = new WeakReference<>(splashPrivacyPolicyDialog);
        }

        public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            this.mReceiveError = false;
        }

        public final void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            SplashPrivacyPolicyDialog splashPrivacyPolicyDialog = (SplashPrivacyPolicyDialog) this.mHost.get();
            if (splashPrivacyPolicyDialog != null) {
                if (this.mReceiveError) {
                    SplashPrivacyPolicyDialog.c(splashPrivacyPolicyDialog);
                    return;
                }
                splashPrivacyPolicyDialog.f.setVisibility(8);
            }
        }

        public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
            SplashPrivacyPolicyDialog splashPrivacyPolicyDialog = (SplashPrivacyPolicyDialog) this.mHost.get();
            if (splashPrivacyPolicyDialog == null) {
                return true;
            }
            Uri parse = Uri.parse(str);
            if (!splashPrivacyPolicyDialog.c) {
                if ("http".equalsIgnoreCase(parse.getScheme()) || "https".equalsIgnoreCase(parse.getScheme())) {
                    new SplashPrivacyPolicyDialog(splashPrivacyPolicyDialog.getContext(), str).show();
                }
                return true;
            } else if ("http".equalsIgnoreCase(parse.getScheme()) || "https".equalsIgnoreCase(parse.getScheme())) {
                return super.shouldOverrideUrlLoading(webView, str);
            } else {
                return true;
            }
        }

        public final void onReceivedSslError(WebView webView, final SslErrorHandler sslErrorHandler, SslError sslError) {
            if (((SplashPrivacyPolicyDialog) this.mHost.get()) == null) {
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

    public SplashPrivacyPolicyDialog(Context context) {
        super(context, R.style.custom_declare_dlg);
        setCancelable(true);
        requestWindowFeature(1);
        euk.a((Dialog) this, Color.parseColor("#F8F8F8"));
        setContentView(R.layout.webview_dialog_notitlebar);
        LayoutParams attributes = getWindow().getAttributes();
        setCancelable(false);
        attributes.width = -1;
        attributes.height = -1;
        getWindow().setBackgroundDrawable(new ColorDrawable(-1));
        getWindow().setAttributes(attributes);
        getWindow().setWindowAnimations(R.style.PermissionDialogNoamin);
        View findViewById = getWindow().getDecorView().findViewById(16908290);
        findViewById.setPadding(findViewById.getPaddingLeft(), findViewById.getPaddingTop() + ags.d(getContext()), findViewById.getPaddingRight(), findViewById.getPaddingBottom());
    }

    SplashPrivacyPolicyDialog(Context context, String str) {
        super(context, R.style.custom_declare_dlg);
        setCancelable(true);
        requestWindowFeature(1);
        euk.a((Dialog) this, Color.parseColor("#F8F8F8"));
        setContentView(R.layout.webview_dialog);
        LayoutParams attributes = getWindow().getAttributes();
        attributes.width = -1;
        setCancelable(false);
        attributes.height = -1;
        getWindow().setBackgroundDrawable(new ColorDrawable(-1));
        getWindow().setAttributes(attributes);
        getWindow().setWindowAnimations(R.style.PermissionDialog);
        this.b = str;
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
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.webview_wrapper);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        if (this.c) {
            this.g = (TitleBar) findViewById(R.id.title_bar);
            this.g.setWidgetVisibility(33, 8);
            this.g.setWidgetVisibility(2, 8);
            this.g.setOnBackClickListener(this.i);
            this.f = (ViewGroup) findViewById(R.id.vg_terms_web_view_container);
            findViewById(R.id.tv_terms_refresh).setOnClickListener(this.j);
            try {
                this.d = new SafeWebView(getContext());
                this.d.getSettings().setTextZoom(100);
                frameLayout.addView(this.d, layoutParams);
                WebSettings settings = this.d.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setAppCacheEnabled(true);
                settings.setCacheMode(1);
                this.d.setWebViewClient(this.h);
                this.d.setWebChromeClient(new MyWebChromClient(this));
                this.d.loadUrl(this.b);
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
        } else {
            AnonymousClass3 r1 = new b() {
                public final void a() {
                    new SplashPrivacyPolicyDialog(SplashPrivacyPolicyDialog.this.getContext(), "https://cache.amap.com/h5/h5/publish/238/index.html").show();
                }

                public final void b() {
                    new SplashPrivacyPolicyDialog(SplashPrivacyPolicyDialog.this.getContext(), "https://cache.amap.com/h5/h5/publish/241/index.html").show();
                }

                public final void c() {
                    SplashPrivacyPolicyDialog.this.a = true;
                    SplashPrivacyPolicyDialog.this.dismiss();
                }

                public final void d() {
                    SplashPrivacyPolicyDialog.this.a = false;
                    SplashPrivacyPolicyDialog.this.dismiss();
                }
            };
            this.e = new SplashPrivacyPolicyLayout(getContext());
            this.e.setOnInternalClickListener(r1);
            frameLayout.addView(this.e, layoutParams);
        }
    }

    static /* synthetic */ void c(SplashPrivacyPolicyDialog splashPrivacyPolicyDialog) {
        new StringBuilder("loadError: ").append(splashPrivacyPolicyDialog.d.getUrl());
        splashPrivacyPolicyDialog.f.setVisibility(0);
    }
}
