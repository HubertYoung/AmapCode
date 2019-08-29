package com.autonavi.map.search.page;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.utils.device.KeyboardUtil;
import com.amap.bundle.webview.widget.ExtendedWebView;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.OnWebViewEventListener;
import com.uc.webview.export.WebView;

@PageAction("amap.search.action.detial")
public class PoiDetailWebPage extends AbstractSearchBasePage<caz> implements OnClickListener, OnWebViewEventListener {
    public ExtendedWebView a;
    protected JsAdapter b;
    private TextView c;
    private View d;
    private ProgressBar e;
    private View f;
    private View g;
    private View h;
    private boolean i = true;
    private View j = null;

    public void onReceivedTitle(WebView webView, String str) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.channel);
        getContentView();
        this.c = (TextView) findView(R.id.title_text_name);
        this.c.setText(getResources().getString(R.string.lbs_drawer));
        this.d = findView(R.id.title_btn_left);
        this.d.setVisibility(0);
        this.j = findView(R.id.bottom_tool_bar);
        this.f = findView(R.id.page_back);
        this.g = findView(R.id.page_pre);
        this.h = findView(R.id.page_refresh);
        this.e = (ProgressBar) findView(R.id.page_loading);
        this.d.setOnClickListener(this);
        this.f.setOnClickListener(this);
        this.g.setOnClickListener(this);
        this.h.setOnClickListener(this);
        this.a = (ExtendedWebView) findView(R.id.web);
        this.b = new JsAdapter((bid) this, (a) this.a);
        this.a.initializeWebView((Object) this.b, (Handler) null, true, false);
        this.a.setOnWebViewEventListener(this);
        PageBundle arguments = getArguments();
        this.i = true;
        if (arguments != null) {
            String string = arguments.getString("url");
            String string2 = arguments.getString("title");
            if (string != null) {
                this.a.loadUrl(string);
                this.c.setText(string2);
                this.i = false;
            }
            a();
        }
    }

    public void onClick(View view) {
        if (view.equals(this.d)) {
            KeyboardUtil.hideKeyboard((View) this.a);
            this.a.clearHistory();
            finish();
        } else if (view.equals(this.f)) {
            this.a.goBack();
            a();
        } else if (view.equals(this.g)) {
            this.a.goForward();
            a();
        } else {
            if (view.equals(this.h)) {
                this.a.reload();
            }
        }
    }

    public final void a() {
        if (this.i) {
            this.j.setVisibility(0);
        } else {
            this.j.setVisibility(8);
        }
        if (this.a != null) {
            if (this.f != null) {
                this.f.setEnabled(this.a.canGoBack());
            }
            if (this.g != null) {
                this.g.setEnabled(this.a.canGoForward());
            }
        }
    }

    public void onWebViewPageFinished(WebView webView) {
        if (this.e != null) {
            this.e.setVisibility(4);
        }
        a();
    }

    public void onWebViewPageStart(WebView webView) {
        if (this.e != null) {
            this.e.setVisibility(0);
        }
    }

    public void onWebViewPageRefresh(WebView webView) {
        if (this.e != null) {
            this.e.setVisibility(0);
        }
    }

    public void onWebViewPageCanceled(WebView webView) {
        if (this.e != null) {
            this.e.setVisibility(4);
        }
        a();
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new caz(this);
    }
}
