package com.amap.bundle.webview.page;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ProgressBar;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.AmapWebView.a;
import com.autonavi.annotation.PageAction;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.map.fragmentcontainer.page.PageTheme.Transparent;
import com.autonavi.minimap.R;
import com.autonavi.widget.webview.MultiTabWebView;
import com.autonavi.widget.webview.inner.SafeWebView.b;
import com.uc.webview.export.WebView;

@PageAction("amap.common.action.webview.transparent")
public class TransparentWebViewPage extends AbstractBasePage<ajj> implements Transparent {
    public boolean a = false;
    /* access modifiers changed from: private */
    public MultiTabWebView b;
    /* access modifiers changed from: private */
    public JsAdapter c;

    public void onCreate(Context context) {
        super.onCreate(context);
        if (!aaw.c(getContext())) {
            finish();
            return;
        }
        setContentView(R.layout.webview_transparent_page);
        this.b = (MultiTabWebView) findViewById(R.id.webview);
        this.c = new JsAdapter((bid) this, this.b);
        JsAdapter jsAdapter = this.c;
        MultiTabWebView multiTabWebView = this.b;
        multiTabWebView.setSupportMultiTab(false);
        multiTabWebView.setJavaScriptEnable(true);
        multiTabWebView.setUICreator(new erz() {
            @Nullable
            public final ProgressBar a() {
                ProgressBar progressBar = new ProgressBar(TransparentWebViewPage.this.getContext());
                progressBar.setVisibility(8);
                return progressBar;
            }
        });
        multiTabWebView.setViewLayerType(1, null);
        multiTabWebView.setViewBackgroundColor(0);
        multiTabWebView.addJavaScriptInterface(new a(jsAdapter), "jsInterface");
        multiTabWebView.addJavaScriptInterface(new bnr(), "kvInterface");
        multiTabWebView.loadUrl(((aja) getArguments().getObject("h5_config")).a);
        multiTabWebView.postDelayed(new Runnable() {
            public final void run() {
                if (!TransparentWebViewPage.this.a) {
                    TransparentWebViewPage.this.b.setViewOnTouchListener(new OnTouchListener() {
                        public final boolean onTouch(View view, MotionEvent motionEvent) {
                            if (TransparentWebViewPage.this.isAlive()) {
                                TransparentWebViewPage.this.finish();
                            }
                            return false;
                        }
                    });
                }
            }
        }, 500);
        multiTabWebView.addWebViewClient(new b() {
            public final void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                if (!TransparentWebViewPage.this.a) {
                    webView.setOnTouchListener(null);
                    TransparentWebViewPage.this.a = true;
                }
            }

            public final void onReceivedError(WebView webView, int i, String str, String str2) {
                super.onReceivedError(webView, i, str, str2);
                if (!TransparentWebViewPage.this.a) {
                    TransparentWebViewPage.this.finish();
                }
            }
        });
    }

    public /* synthetic */ IPresenter createPresenter() {
        return new ajj(this);
    }
}
