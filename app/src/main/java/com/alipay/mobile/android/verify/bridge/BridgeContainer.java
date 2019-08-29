package com.alipay.mobile.android.verify.bridge;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.alipay.mobile.android.verify.bridge.b.a;
import com.alipay.mobile.android.verify.bridge.b.b;
import com.alipay.mobile.android.verify.bridge.b.c;
import com.alipay.mobile.android.verify.bridge.b.d;
import com.alipay.mobile.android.verify.bridge.b.g;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEventTypes;
import com.alipay.mobile.android.verify.bridge.protocol.IPlugin;
import com.alipay.mobile.android.verify.logger.Logger;
import com.alipay.mobile.android.verify.logger.PrettyFormatStrategy;
import com.alipay.mobile.android.verify.sdk.R;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.squareup.otto.Subscribe;
import java.util.ArrayList;
import java.util.List;

public class BridgeContainer extends Dialog implements IPlugin {
    private final String a = "BridgeContainer";
    private final String b;
    private BridgeWebView c;
    private TextView d;
    private TextView e;
    private View f;
    private boolean g;
    private List<IPlugin> h;

    public BridgeContainer(Activity activity, String str) {
        super(activity, R.style.fullscreen);
        b();
        setOwnerActivity(activity);
        this.b = str;
        getWindow().setWindowAnimations(R.style.dialogAnim);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Logger.t("BridgeContainer").i("bridge container create", new Object[0]);
        setContentView(R.layout.bridge_container);
        this.c = (BridgeWebView) findViewById(R.id.webView);
        this.d = (TextView) findViewById(R.id.content);
        this.e = (TextView) findViewById(R.id.button);
        this.e.setTypeface(a(getContext().getApplicationContext()));
        this.e.setOnClickListener(new b(this));
        this.f = findViewById(R.id.divider);
        this.g = true;
        a();
    }

    private void a() {
        if (this.h == null) {
            this.h = new ArrayList();
        }
        this.h.add(new b(this.c));
        this.h.add(new a(this.c));
        this.h.add(new g(this.e, this.d, this.f));
        this.h.add(new d(this.c));
        this.h.add(new c(getOwnerActivity()));
        this.h.add(this);
    }

    public void addPlugin(IPlugin iPlugin) {
        if (this.h == null) {
            this.h = new ArrayList();
        }
        this.h.add(iPlugin);
    }

    private Typeface a(Context context) {
        try {
            return Typeface.createFromAsset(context.getAssets(), "fonts/iconfont.ttf");
        } catch (Exception e2) {
            Typeface typeface = Typeface.DEFAULT;
            Logger.t("BridgeContainer").e(e2, "got error when got icon font", new Object[0]);
            return typeface;
        }
    }

    private void b() {
        Logger.clearLogAdapters();
        Logger.addLogAdapter(new com.alipay.mobile.android.verify.bridge.a.a(PrettyFormatStrategy.newBuilder().tag("JS_BRIDGE").build()));
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Logger.t("BridgeContainer").i("bridge container attached to window", new Object[0]);
        c();
        if (TextUtils.isEmpty(this.b)) {
            Logger.t("BridgeContainer").w("null or empty target url", new Object[0]);
            dismiss();
        } else if (this.b.startsWith(AjxHttpLoader.DOMAIN_HTTP) || this.b.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
            this.c.loadUrl(this.b);
        } else {
            Logger.t("BridgeContainer").w("invalid target url", new Object[0]);
            dismiss();
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Logger.t("BridgeContainer").i("bridge container detached from window", new Object[0]);
        d();
        this.c.destroy();
        this.g = true;
    }

    private void c() {
        try {
            if (this.h != null) {
                Logger.t("BridgeContainer").i("register plugins begin", new Object[0]);
                for (IPlugin register : this.h) {
                    BusProvider.getInstance().register(register);
                }
                Logger.t("BridgeContainer").i("register plugins end", new Object[0]);
            }
        } catch (Exception e2) {
            Logger.t("BridgeContainer").e(e2, "register plugin got error", new Object[0]);
        }
    }

    private void d() {
        try {
            if (this.h != null) {
                Logger.t("BridgeContainer").i("unregister plugins begin", new Object[0]);
                for (IPlugin unregister : this.h) {
                    BusProvider.getInstance().unregister(unregister);
                }
                this.h.clear();
                Logger.t("BridgeContainer").i("unregister plugins end", new Object[0]);
            }
        } catch (Exception e2) {
            Logger.t("BridgeContainer").e(e2, "unregister plugin got error", new Object[0]);
        }
    }

    @Subscribe
    public void handle(BridgeEvent bridgeEvent) {
        if (bridgeEvent == null || TextUtils.isEmpty(bridgeEvent.action)) {
            Logger.t("BridgeContainer").w("null or empty action", new Object[0]);
            return;
        }
        BridgeEvent cloneAsResponse = BridgeEvent.cloneAsResponse(bridgeEvent);
        cloneAsResponse.data = BridgeEvent.response();
        if (BridgeEventTypes.ALLOW_BACK_EVENT.equalsIgnoreCase(bridgeEvent.action)) {
            Logger.t("BridgeContainer").i("handle allow back event", new Object[0]);
            this.g = true;
            BusProvider.getInstance().post(cloneAsResponse);
            return;
        }
        if (BridgeEventTypes.DISALLOW_BACK_EVENT.equalsIgnoreCase(bridgeEvent.action)) {
            Logger.t("BridgeContainer").i("handle disallow back event", new Object[0]);
            this.g = false;
            BusProvider.getInstance().post(cloneAsResponse);
        }
    }

    public void onBackPressed() {
        e();
    }

    /* access modifiers changed from: private */
    public void e() {
        if (this.g && this.c != null) {
            if (this.c.canGoBack()) {
                this.c.goBack();
                return;
            }
            BridgeEvent bridgeEvent = new BridgeEvent();
            bridgeEvent.action = BridgeEventTypes.BACK_PRESSED;
            BusProvider.getInstance().post(bridgeEvent);
        }
    }
}
