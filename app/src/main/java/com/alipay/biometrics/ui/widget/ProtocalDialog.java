package com.alipay.biometrics.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import com.alipay.android.phone.mobilecommon.biometric.a.a.c;
import com.alipay.android.phone.mobilecommon.biometric.a.a.d;
import com.alipay.android.phone.mobilecommon.biometric.a.a.e;

public class ProtocalDialog extends Dialog {
    private String cacelButtonText;
    /* access modifiers changed from: private */
    public ClickListenerInterface clickListenerInterface;
    private String confirmButtonText;
    private Context context;
    private boolean showCloseButton;
    private boolean showProtocol;
    private String subTitle;
    private String title;

    public interface ClickListenerInterface {
        void doClosed();
    }

    public ProtocalDialog(Context context2) {
        super(context2, e.bio_custom_dialog_style);
        this.context = context2;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().requestFeature(1);
        getWindow().addFlags(1024);
        hideBottomUIMenu();
        setContentView(LayoutInflater.from(this.context).inflate(d.bio_protocal_dialog, null));
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().setLayout(-1, -1);
        WebView webView = (WebView) findViewById(c.title);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/html/agreement.html");
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView webView, String str) {
                webView.loadUrl(str);
                return super.shouldOverrideUrlLoading(webView, str);
            }
        });
        ((Button) findViewById(c.btn_x)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ProtocalDialog.this.clickListenerInterface.doClosed();
            }
        });
    }

    public void setOnClickListener(ClickListenerInterface clickListenerInterface2) {
        this.clickListenerInterface = clickListenerInterface2;
    }

    /* access modifiers changed from: protected */
    public void hideBottomUIMenu() {
        if (VERSION.SDK_INT > 11 && VERSION.SDK_INT < 19) {
            getWindow().getDecorView().setSystemUiVisibility(8);
        } else if (VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(4102);
        }
    }
}
