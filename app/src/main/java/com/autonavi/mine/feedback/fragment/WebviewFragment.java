package com.autonavi.mine.feedback.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.widget.AmapWebView;
import com.amap.bundle.webview.widget.AmapWebView.a;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.OnWebViewEventListener;
import com.autonavi.widget.webview.MultiTabWebView;
import com.autonavi.widget.webview.inner.SafeWebView.b;
import com.uc.webview.export.WebView;

public class WebviewFragment extends Fragment implements OnWebViewEventListener {
    public static final String KEY_SUPPORT_ZOOM = "support_zoom";
    public static final String KEY_URL = "url";
    private boolean isAllowHorizontal = true;
    private JsAdapter mJavaScriptMethods;
    private boolean mSupportZoom = false;
    public String url;
    private AmapWebView webView;

    public void onReceivedTitle(WebView webView2, String str) {
    }

    public void onWebViewPageCanceled(WebView webView2) {
    }

    public void onWebViewPageFinished(WebView webView2) {
    }

    public void onWebViewPageRefresh(WebView webView2) {
    }

    public static WebviewFragment create(String str, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString("url", str);
        bundle.putBoolean("support_zoom", z);
        WebviewFragment webviewFragment = new WebviewFragment();
        webviewFragment.setArguments(bundle);
        return webviewFragment;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.webview_fragment, null);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.webView = (AmapWebView) view.findViewById(R.id.webView);
        setData();
    }

    private void initWebView(JsAdapter jsAdapter, MultiTabWebView multiTabWebView) {
        multiTabWebView.setSupportMultiTab(false);
        multiTabWebView.getWebSettings().b(true);
        adapteFoldScreen();
        multiTabWebView.setUICreator(new erz() {
            @Nullable
            public final ProgressBar a() {
                ProgressBar progressBar = new ProgressBar(WebviewFragment.this.getContext());
                progressBar.setVisibility(8);
                return progressBar;
            }
        });
        multiTabWebView.getCurrentWebView().setLayerType(1, null);
        multiTabWebView.getCurrentWebView().setBackgroundColor(0);
        multiTabWebView.addJavaScriptInterface(new a(jsAdapter), "jsInterface");
        multiTabWebView.addJavaScriptInterface(new bnr(), "kvInterface");
        multiTabWebView.loadUrl(getArguments().getString("url"));
        multiTabWebView.addWebViewClient(new b());
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        adapteFoldScreen();
    }

    private void adapteFoldScreen() {
        int width = ags.a(getContext()).width();
        int height = ags.a(getContext()).height();
        LayoutParams layoutParams = this.webView.getLayoutParams();
        if (height * 3 < width * 4) {
            layoutParams.width = (height * 9) / 16;
            ((ViewGroup) this.webView.getParent()).setBackgroundColor(-16777216);
            if (layoutParams instanceof LinearLayout.LayoutParams) {
                ((LinearLayout.LayoutParams) layoutParams).gravity = 1;
            } else if (layoutParams instanceof FrameLayout.LayoutParams) {
                ((FrameLayout.LayoutParams) layoutParams).gravity = 1;
            } else if (layoutParams instanceof RelativeLayout.LayoutParams) {
                ((RelativeLayout.LayoutParams) layoutParams).addRule(14);
            }
            this.webView.setLayoutParams(layoutParams);
            this.webView.invalidate();
            return;
        }
        layoutParams.width = -1;
        this.webView.setLayoutParams(layoutParams);
        this.webView.invalidate();
    }

    /* access modifiers changed from: protected */
    public void setData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.url = arguments.getString("url");
            this.mSupportZoom = arguments.getBoolean("support_zoom", false);
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext == null) {
                throw new IllegalArgumentException("Unable to find a pageContext!");
            }
            this.mJavaScriptMethods = new JsAdapter(pageContext, (MultiTabWebView) this.webView);
            initWebView(this.mJavaScriptMethods, this.webView);
            if (!TextUtils.isEmpty(this.url)) {
                this.webView.loadUrl(this.url);
            }
        }
    }

    public void onWebViewPageStart(WebView webView2) {
        this.mJavaScriptMethods.closeTimeToast();
    }

    public void onStop() {
        super.onStop();
        this.webView.stopLoading();
    }

    public void reload() {
        if (this.webView != null) {
            this.webView.reload();
        }
    }
}
