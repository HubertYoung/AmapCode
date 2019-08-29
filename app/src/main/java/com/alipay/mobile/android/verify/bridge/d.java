package com.alipay.mobile.android.verify.bridge;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import com.alipay.mobile.android.verify.logger.Logger;
import com.alipay.mobile.android.verify.sdk.R;

/* compiled from: PopWebViewDialog */
public class d extends Dialog {
    private final String a = "PopWebViewDialog";
    private BridgeWebView b;
    /* access modifiers changed from: private */
    public TextView c;
    private TextView d;
    private String e;

    public d(Activity activity, String str) {
        super(activity, R.style.fullscreen);
        setOwnerActivity(activity);
        this.e = str;
        getWindow().setWindowAnimations(R.style.dialogAnim);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Logger.t("PopWebViewDialog").i("PopWebViewDialog create", new Object[0]);
        setContentView(R.layout.bridge_container);
        this.b = (BridgeWebView) findViewById(R.id.webView);
        this.c = (TextView) findViewById(R.id.content);
        this.d = (TextView) findViewById(R.id.button);
        this.d.setTypeface(a(getContext().getApplicationContext()));
        this.d.setOnClickListener(new e(this));
        a();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Logger.t("PopWebViewDialog").i("PopWebViewDialog attached to window", new Object[0]);
        if (TextUtils.isEmpty(this.e)) {
            Logger.t("PopWebViewDialog").w("null or empty target url", new Object[0]);
            dismiss();
            return;
        }
        this.b.loadUrl(this.e);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Logger.t("PopWebViewDialog").i("PopWebViewDialog detached from window", new Object[0]);
        this.b.destroy();
    }

    private void a() {
        this.b.setWebChromeClient(new f(this));
        this.b.setWebViewClient(new g(this));
    }

    private Typeface a(Context context) {
        try {
            return Typeface.createFromAsset(context.getAssets(), "fonts/iconfont.ttf");
        } catch (Exception e2) {
            Typeface typeface = Typeface.DEFAULT;
            Logger.t("PopWebViewDialog").e(e2, "got error when got icon font", new Object[0]);
            return typeface;
        }
    }

    public void onBackPressed() {
        b();
    }

    /* access modifiers changed from: private */
    public void b() {
        if (this.b == null || !this.b.canGoBack()) {
            dismiss();
        } else {
            this.b.goBack();
        }
    }
}
