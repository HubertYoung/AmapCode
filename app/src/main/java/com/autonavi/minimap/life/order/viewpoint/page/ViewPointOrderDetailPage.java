package com.autonavi.minimap.life.order.viewpoint.page;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.ExtendedWebView;
import com.autonavi.common.js.action.LifeEntity;
import com.autonavi.minimap.R;
import com.autonavi.minimap.life.order.base.page.BaseOrderWebViewPage;
import com.autonavi.minimap.life.order.base.page.BaseOrderWebViewPage.a;
import com.uc.webview.export.WebView;

public class ViewPointOrderDetailPage extends BaseOrderWebViewPage implements a {
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
        this.e = this;
    }

    public final JsAdapter a(dpl dpl, ExtendedWebView extendedWebView) {
        JsAdapter jsAdapter = new JsAdapter(getPageContext(), (a) extendedWebView);
        if (dpl != null) {
            LifeEntity lifeEntity = new LifeEntity();
            lifeEntity.jsonStr = dpl.j();
            bkq.a(lifeEntity);
        }
        return jsAdapter;
    }

    public final void a(TextView textView) {
        textView.setText(R.string.order_detail_title);
    }

    public final void a(ExtendedWebView extendedWebView) {
        if (ConfigerHelper.getInstance().isLoadPoiPageFromInternet()) {
            extendedWebView.loadUrl("https://group.testing.amap.com/public/activity/life/viewpoint/exViewpointDetail.html");
        } else if (!TextUtils.isEmpty(this.d)) {
            bgx bgx = (bgx) a.a.a(bgx.class);
            if (bgx != null) {
                extendedWebView.loadUrl(bgx.getUrl(this.d));
            }
        } else {
            bgx bgx2 = (bgx) a.a.a(bgx.class);
            if (bgx2 != null) {
                extendedWebView.loadUrl(bgx2.getUrl("life/viewpoint/exViewpointDetail.html"));
            }
        }
    }

    public void onReceivedTitle(WebView webView, String str) {
        if (!TextUtils.isEmpty(str)) {
            this.b.setText(str);
        } else {
            this.b.setText(getContext().getString(R.string.order_detail_title));
        }
    }
}
