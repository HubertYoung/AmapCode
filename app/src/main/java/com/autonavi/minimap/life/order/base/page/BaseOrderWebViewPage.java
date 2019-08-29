package com.autonavi.minimap.life.order.base.page;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.ExtendedWebView;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.LocationMode.LocationNone;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.OnWebViewEventListener;

public abstract class BaseOrderWebViewPage extends AbstractBasePage<dpv> implements LocationNone, OnWebViewEventListener {
    public JsAdapter a;
    protected TextView b;
    protected dpl c;
    protected String d;
    protected a e;
    private ExtendedWebView f;
    private View g;
    private View h;

    public interface a {
    }

    /* access modifiers changed from: protected */
    public abstract JsAdapter a(dpl dpl, ExtendedWebView extendedWebView);

    /* access modifiers changed from: protected */
    public abstract void a(TextView textView);

    /* access modifiers changed from: protected */
    public abstract void a(ExtendedWebView extendedWebView);

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.order_base_detail_layout);
        View contentView = getContentView();
        this.f = (ExtendedWebView) contentView.findViewById(R.id.movie_detail_webview);
        this.b = (TextView) contentView.findViewById(R.id.title_text_name);
        this.g = contentView.findViewById(R.id.title_btn_left);
        this.g.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                BaseOrderWebViewPage.this.a();
            }
        });
        this.h = contentView.findViewById(R.id.title_btn_right);
        this.f.setOnWebViewEventListener(this);
        this.h.setVisibility(8);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.c = (dpl) arguments.getObject("IOrderListEntityKey");
            this.d = arguments.getString("INTENT_KEY_H5_TEMPLATE_PATH");
            ExtendedWebView extendedWebView = this.f;
            this.a = a(this.c, extendedWebView);
            this.f.initializeWebView((Object) this.a, (Handler) null, true, false);
            this.f.setVisibility(0);
            this.f.clearView();
            this.f.clearHistory();
            a(extendedWebView);
            a(this.b);
            return;
        }
        this.f.clearView();
        this.f.clearHistory();
        a(this.f);
    }

    public final void a() {
        if (this.f.canGoBack()) {
            this.f.stopLoading();
            this.f.goBack();
            return;
        }
        this.f.clearView();
        this.f.clearHistory();
        finish();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dpv(this);
    }
}
