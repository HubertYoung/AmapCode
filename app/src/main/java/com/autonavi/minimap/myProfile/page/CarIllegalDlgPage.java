package com.autonavi.minimap.myProfile.page;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.AbstractBaseWebView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.core.LocationMode.LocationNetworkOnly;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.OnWebViewEventListener;
import com.uc.webview.export.WebView;

@PageAction("amap.basemap.action.weizhang")
public class CarIllegalDlgPage extends AbstractBasePage<dry> implements OnClickListener, LocationNetworkOnly, OnWebViewEventListener {
    public AbstractBaseWebView a;
    public View b;
    public TextView c;
    public LinearLayout d;
    public JsAdapter e;
    private Button f;
    private ImageButton g;
    private TextView h;
    private Button i;

    public void onWebViewPageCanceled(WebView webView) {
    }

    public void onWebViewPageRefresh(WebView webView) {
    }

    public void onWebViewPageStart(WebView webView) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.illegal_webview_layout);
        View contentView = getContentView();
        this.f = (Button) contentView.findViewById(R.id.title_btn_left_illegal);
        this.g = (ImageButton) contentView.findViewById(R.id.title_btn_img_left);
        this.h = (TextView) contentView.findViewById(R.id.title_text_name);
        this.i = (Button) contentView.findViewById(R.id.title_btn_right);
        this.i.setVisibility(4);
        this.b = contentView.findViewById(R.id.loading);
        this.c = (TextView) contentView.findViewById(R.id.loading_text);
        this.d = (LinearLayout) contentView.findViewById(R.id.loading_layout);
        this.f.setOnClickListener(this);
        this.g.setOnClickListener(this);
        this.i.setOnClickListener(this);
        this.f.setVisibility(8);
        this.a = (AbstractBaseWebView) contentView.findViewById(R.id.webView);
        this.i.setVisibility(4);
        this.h.setText(AMapAppGlobal.getApplication().getString(R.string.car_iileage));
        this.e = new JsAdapter(getPageContext(), (a) this.a);
        this.e.setRightBtn(this.i);
        this.a.initializeWebView((Object) this.e, (Handler) null, true, false);
        this.a.setShowTopProress(true);
        this.a.setOnWebViewEventListener(this);
        this.a.clearView();
        this.a.clearCache(false);
        this.a.loadUrl(dry.a());
    }

    public void onClick(View view) {
        if (!view.equals(this.f)) {
            if (view.equals(this.g) && !this.e.onKeyBackPressed()) {
                finish();
            }
        } else if (!this.e.onKeyBackPressed()) {
            finish();
        }
    }

    public void onWebViewPageFinished(WebView webView) {
        if (((dry) this.mPresenter).a) {
            this.b.setVisibility(8);
            this.a.setVisibility(0);
            this.a.requestFocus();
        }
    }

    public void onReceivedTitle(WebView webView, String str) {
        if (((dry) this.mPresenter).b) {
            this.h.setText(str);
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new dry(this);
    }
}
