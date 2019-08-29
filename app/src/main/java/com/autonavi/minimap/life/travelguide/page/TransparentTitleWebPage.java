package com.autonavi.minimap.life.travelguide.page;

import android.content.Context;
import android.view.View;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.ExtendedWebView;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.OnWebViewEventListener;
import com.uc.webview.export.WebView;

public class TransparentTitleWebPage extends AbstractBasePage<drk> implements OnWebViewEventListener {
    public View a;
    public View b;
    public JsAdapter c;
    public ExtendedWebView d;
    public String e;

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
        setContentView(R.layout.transparent_title_web_fragment_layout);
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new drk(this);
    }
}
