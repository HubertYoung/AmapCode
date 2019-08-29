package com.autonavi.map.common.page;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.TextView;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.ExtendedWebView;
import com.autonavi.annotation.PageAction;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.widget.ProgressDlg;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.OnWebViewEventListener;
import com.uc.webview.export.WebView;

@PageAction("amap.basemap.action.licenseconfirm_page")
public class LicenseConfirmPage extends AbstractBasePage<bri> implements OnWebViewEventListener {
    public ExtendedWebView a;
    private JsAdapter b;
    private TextView c;
    private String d;
    private String e;
    private String f;
    private ProgressDlg g;
    private boolean h = false;
    private String i;
    private Handler j = new Handler() {
        public final void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1000:
                    LicenseConfirmPage.a(LicenseConfirmPage.this, LicenseConfirmPage.this.getContext().getString(R.string.loading));
                    return;
                case 1001:
                    LicenseConfirmPage.a(LicenseConfirmPage.this);
                    break;
            }
        }
    };

    public void onWebViewPageCanceled(WebView webView) {
    }

    public void onWebViewPageRefresh(WebView webView) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.license_confirm_dialog_layout);
        this.c = (TextView) findViewById(R.id.title_text_name);
        findViewById(R.id.title_btn_left).setVisibility(8);
        findViewById(R.id.title_btn_right).setVisibility(8);
        this.a = (ExtendedWebView) findViewById(R.id.license_webview);
        this.a.setOnWebViewEventListener(this);
        this.c.setText("");
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.d = arguments.getString("license_url");
            this.e = arguments.getString("url");
            this.f = arguments.getString("website_name");
            this.h = arguments.getBoolean("native_web", false);
            this.i = arguments.getString("redirect_action");
            this.b = new JsAdapter(getPageContext(), (a) this.a);
            bkm bkm = new bkm(this.d, this.d, this.e, this.f, this.h, this.i);
            dmu dmu = new dmu();
            dmu.a = bkm;
            this.b.registerJsAction("licenseConfirm", dmu);
            this.a.initializeWebView((Object) this.b, (Handler) null, true, false);
            if (this.a != null && !TextUtils.isEmpty(this.d)) {
                this.a.loadUrl(this.d);
            }
        }
    }

    public void onWebViewPageFinished(WebView webView) {
        this.j.sendEmptyMessage(1001);
    }

    public void onWebViewPageStart(WebView webView) {
        this.j.sendEmptyMessage(1000);
    }

    public void onReceivedTitle(WebView webView, String str) {
        if (this.c != null) {
            String url = webView.getUrl();
            if (url == null || !url.contains("connect_error.html")) {
                this.c.setText(str);
            }
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new bri(this);
    }

    static /* synthetic */ void a(LicenseConfirmPage licenseConfirmPage, String str) {
        licenseConfirmPage.g = new ProgressDlg(licenseConfirmPage.getActivity(), str, "");
        licenseConfirmPage.g.setCancelable(true);
        licenseConfirmPage.g.show();
    }

    static /* synthetic */ void a(LicenseConfirmPage licenseConfirmPage) {
        if (licenseConfirmPage.g != null) {
            licenseConfirmPage.g.dismiss();
        }
    }
}
