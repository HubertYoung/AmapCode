package com.autonavi.map.search.photo.page;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.ExtendedWebView;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.search.page.AbstractSearchBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.OnWebViewEventListener;
import com.uc.webview.export.WebView;

public class PoiPhotoSuccessPage extends AbstractSearchBasePage<cad> implements anr, OnWebViewEventListener {
    public ExtendedWebView a;
    public JsAdapter b;

    public void onReceivedTitle(WebView webView, String str) {
    }

    public void onWebViewPageCanceled(WebView webView) {
    }

    public void onWebViewPageFinished(WebView webView) {
    }

    public void onWebViewPageRefresh(WebView webView) {
    }

    public void onWebViewPageStart(WebView webView) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.poi_photo_update_success_fragment);
        this.a = (ExtendedWebView) getContentView().findViewById(R.id.poi_photo_webview);
        this.a.setOnWebViewEventListener(this);
        this.b = new JsAdapter((bid) this, (a) this.a);
        this.b.setBundle(new PageBundle());
        this.a.initializeWebView((Object) this.b, (Handler) null, true, false);
        this.a.setVisibility(0);
        this.a.clearView();
        String string = getArguments().getString("url");
        if (!TextUtils.isEmpty(string)) {
            this.a.loadUrl(string);
        }
    }

    public final void a(boolean z) {
        if (!z) {
            finish();
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new cad(this);
    }
}
