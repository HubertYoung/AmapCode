package com.amap.bundle.webview.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.HttpUrlCollector;
import com.amap.bundle.statistics.HttpUrlCollector.SCENE;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.webview.util.BaichuanSDKProxy;
import com.amap.bundle.webview.widget.AmapWebView;
import com.autonavi.annotation.PageAction;
import com.autonavi.bundle.vui.entity.VSysStateResultMap;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.inter.IMultipleServiceLoader;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IPresenter;
import com.autonavi.minimap.R;
import com.autonavi.widget.ui.LoadingViewBL;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.webview.MultiTabWebView;
import com.autonavi.widget.webview.android.SafeWebView.WebViewClientEx;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import com.uc.webview.export.WebBackForwardList;
import com.uc.webview.export.WebChromeClient;
import com.uc.webview.export.WebChromeClient.CustomViewCallback;
import com.uc.webview.export.WebHistoryItem;
import com.uc.webview.export.WebView;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@PageAction("amap.common.action.webview")
public class WebViewPage extends AbstractBasePage<ajh> implements ajb, ajc, OnClickListener, com.autonavi.widget.webview.MultiTabWebView.a {
    anq a = new anq() {
        public final void onComplete(boolean z) {
            if (z) {
                bim.aa().i(bim.aa().G());
                cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
                if (cuh != null) {
                    cuh.p();
                }
                WebViewPage.this.d.reload();
            }
        }

        public final void loginOrBindCancel() {
            WebViewPage.s(WebViewPage.this);
            WebViewPage.t(WebViewPage.this);
        }
    };
    private long b = 0;
    /* access modifiers changed from: private */
    public List<String> c = new ArrayList();
    /* access modifiers changed from: private */
    public MultiTabWebView d;
    /* access modifiers changed from: private */
    public String e;
    /* access modifiers changed from: private */
    public TitleBar f;
    /* access modifiers changed from: private */
    public String g;
    private View h;
    /* access modifiers changed from: private */
    public ImageButton i;
    /* access modifiers changed from: private */
    public ImageButton j;
    private ImageButton k;
    /* access modifiers changed from: private */
    public LoadingViewBL l;
    /* access modifiers changed from: private */
    public LoadingViewBL m;
    /* access modifiers changed from: private */
    public boolean n = false;
    private boolean o = false;
    private RelativeLayout p;
    private boolean q = false;
    private boolean r = false;
    private OnClickListener s = new OnClickListener() {
        public final void onClick(View view) {
            WebViewPage.this.mPresenter;
            WebViewPage.this.k();
            WebViewPage.a(view);
            if (WebViewPage.this.d.canGoBack()) {
                WebViewPage.this.d.goBack();
                WebViewPage.b(WebViewPage.this.d, (View) WebViewPage.this.i, (View) WebViewPage.this.j);
                return;
            }
            WebViewPage.this.finish();
        }
    };
    private OnClickListener t = new OnClickListener() {
        public final void onClick(View view) {
            WebViewPage.a(view);
            WebViewPage.this.finish();
        }
    };
    private OnClickListener u = new OnClickListener() {
        public final void onClick(View view) {
            defpackage.ajh.a l_ = ((ajh) WebViewPage.this.mPresenter).l_();
            if ((l_ == null || !l_.b()) && !WebViewPage.this.v.doRightBtnFunction()) {
                WebViewPage.this.d.reload();
            }
        }
    };
    /* access modifiers changed from: private */
    public JsAdapter v;
    private bfl w;
    private bfn x;
    private bfm y;
    private boolean z = false;

    class a implements bfn {
        private a() {
        }

        /* synthetic */ a(WebViewPage webViewPage, byte b) {
            this();
        }

        public final void a(VSysStateResultMap vSysStateResultMap) {
            JSONObject jSONObject = new JSONObject();
            bfo bfo = (bfo) defpackage.esb.a.a.a(bfo.class);
            if (bfo != null) {
                try {
                    jSONObject.put("isVUIAvailable", bfo.e());
                    JSONObject jSONObject2 = new JSONObject();
                    for (String str : vSysStateResultMap.keySet()) {
                        boolean z = false;
                        if ("isVUICardVisible".equals(str)) {
                            if (vSysStateResultMap.get(str).intValue() == 1) {
                                z = true;
                            }
                            jSONObject.put("isVUICardVisible", z);
                        } else {
                            if (vSysStateResultMap.get(str).intValue() == 1) {
                                z = true;
                            }
                            jSONObject2.put(str, z);
                        }
                    }
                    jSONObject2.put("isVUISwitchOn", bfo.a());
                    jSONObject.put("VUIRelatedState", jSONObject2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            WebViewPage.b(WebViewPage.this, (String) "onVUIStateChanged", jSONObject.toString());
        }
    }

    class b implements bfm {
        private b() {
        }

        /* synthetic */ b(WebViewPage webViewPage, byte b) {
            this();
        }

        public final void a(boolean z) {
            JSONObject jSONObject = new JSONObject();
            bfo bfo = (bfo) defpackage.esb.a.a.a(bfo.class);
            if (bfo != null) {
                try {
                    jSONObject.put("isVUIAvailable", bfo.e());
                    VSysStateResultMap b = bfo.b();
                    JSONObject jSONObject2 = new JSONObject();
                    for (String str : b.keySet()) {
                        boolean z2 = false;
                        if ("isVUICardVisible".equals(str)) {
                            if (b.get(str).intValue() == 1) {
                                z2 = true;
                            }
                            jSONObject.put("isVUICardVisible", z2);
                        } else {
                            if (b.get(str).intValue() == 1) {
                                z2 = true;
                            }
                            jSONObject2.put(str, z2);
                        }
                    }
                    jSONObject2.put("isVUISwitchOn", bfo.a());
                    jSONObject.put("VUIRelatedState", jSONObject2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            WebViewPage.b(WebViewPage.this, (String) "onVUIStateChanged", jSONObject.toString());
        }
    }

    class c implements bfl {
        private c() {
        }

        /* synthetic */ c(WebViewPage webViewPage, byte b) {
            this();
        }

        public final boolean a(bgb bgb) {
            if (bgb != null) {
                String str = bgb.k;
                String str2 = bgb.d;
                StringBuilder sb = new StringBuilder("voiceCMD: ");
                sb.append(bgb.b);
                AMapLog.d("WebViewH5", sb.toString());
                if (TextUtils.equals(str2, "doActionInWeb")) {
                    if (WebViewPage.this.c.contains(str)) {
                        WebViewPage.b(WebViewPage.this, (String) "onRecVUICommand", bgb.b);
                        return true;
                    }
                    bfq bfq = defpackage.bfr.c.a;
                    StringBuilder sb2 = new StringBuilder("unSupportCmd taskId=");
                    sb2.append(bgb.f);
                    bfp.b(bfq, 1, sb2.toString());
                }
            }
            return false;
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(LayoutInflater.from(getContext()).inflate(R.layout.new_webview_fragment, null));
        PageBundle arguments = getArguments();
        if (!aaw.c(getContext()) && arguments != null) {
            if (!arguments.containsKey("h5_config") || !((aja) arguments.getObject("h5_config")).a.startsWith("file")) {
                finish();
                startPage(WebErrorPage.class, arguments);
                return;
            }
        }
        this.z = ajm.c();
        this.p = (RelativeLayout) findViewById(R.id.load_webview_layout);
        if (i()) {
            this.d = new AmapWebView(getContext(), true);
        } else {
            this.d = new AmapWebView(getContext());
        }
        this.d.setBackgroundColor(-1);
        this.p.addView(this.d, 0, new LayoutParams(-1, -1));
        if (!this.q) {
            a((ViewGroup) this.p);
        }
        this.f = (TitleBar) findViewById(R.id.title);
        this.f.setWidgetVisibility(33, 8);
        this.f.setWidgetVisibility(2, 8);
        this.f.setOnBackClickListener(this.s);
        this.f.setOnExBackClickListener(this.t);
        this.f.setOnActionClickListener(this.u);
        this.h = findViewById(R.id.id_web_bottom);
        this.i = (ImageButton) findViewById(R.id.page_last);
        this.j = (ImageButton) findViewById(R.id.page_next);
        this.k = (ImageButton) findViewById(R.id.page_reload);
        this.i.setOnClickListener(this);
        this.j.setOnClickListener(this);
        this.k.setOnClickListener(this);
        this.l = (LoadingViewBL) findViewById(R.id.loading_view_normal_style);
        this.m = (LoadingViewBL) findViewById(R.id.loading_view_modal_style);
        this.v = new JsAdapter((bid) this, this.d);
        JsAdapter jsAdapter = this.v;
        MultiTabWebView multiTabWebView = this.d;
        com.amap.bundle.webview.widget.AmapWebView.a aVar = new com.amap.bundle.webview.widget.AmapWebView.a(jsAdapter);
        if (multiTabWebView instanceof AmapWebView) {
            ((AmapWebView) multiTabWebView).setIsRequestFocusOnPageFinished(true);
        }
        multiTabWebView.addJavaScriptInterface(aVar, "jsInterface");
        multiTabWebView.addJavaScriptInterface(new bnr(), "kvInterface");
        multiTabWebView.addWebChromeClient(new WebChromeClient() {
            public final void onReceivedTitle(WebView webView, String str) {
                WebViewPage.this.g = str;
                if (!TextUtils.isEmpty(str)) {
                    WebViewPage.a(WebViewPage.this, webView.getUrl(), str);
                }
            }

            public final void onShowCustomView(View view, CustomViewCallback customViewCallback) {
                WebViewPage.this.getContentView().setVisibility(4);
                DoNotUseTool.getMapManager().getMapView().d(4);
                WebViewPage.this.n = true;
            }

            public final void onHideCustomView() {
                WebViewPage.this.n = false;
                WebViewPage.this.getContentView().setVisibility(0);
                DoNotUseTool.getMapManager().getMapView().d(0);
            }
        });
        multiTabWebView.addWebViewClient(new com.autonavi.widget.webview.inner.SafeWebView.b() {
            boolean a = false;

            public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 18) {
                    webView.setLayerType(1, null);
                    webView.setDrawingCacheEnabled(false);
                }
                super.onPageStarted(webView, str, bitmap);
                if (str.equals("file:///android_asset/connect_error.html") || str.equals("file:///android_asset/not_found_error.html")) {
                    if (!((ajh) WebViewPage.this.mPresenter).g()) {
                        WebViewPage.this.f.setVisibility(0);
                        this.a = true;
                    }
                } else if (this.a && !((ajh) WebViewPage.this.mPresenter).g()) {
                    WebViewPage.this.f.setVisibility(8);
                    this.a = false;
                }
            }

            public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
                WebViewPage.this.k();
                HttpUrlCollector.a(SCENE.WEB_VIEW, str);
                if (TextUtils.isEmpty(str) || !BaichuanSDKProxy.d().b(str)) {
                    return false;
                }
                WebViewPage.k(WebViewPage.this);
                return true;
            }

            public final void onPageFinished(WebView webView, String str) {
                super.onPageFinished(webView, str);
                WebViewPage.b(WebViewPage.this.d, (View) WebViewPage.this.i, (View) WebViewPage.this.j);
                WebBackForwardList copyBackForwardList = webView.copyBackForwardList();
                if (copyBackForwardList != null) {
                    WebHistoryItem currentItem = copyBackForwardList.getCurrentItem();
                    if (currentItem != null && str.equals(currentItem.getUrl())) {
                        String title = currentItem.getTitle();
                        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(WebViewPage.this.g) && !title.equals(WebViewPage.this.g)) {
                            WebViewPage.a(WebViewPage.this, webView.getUrl(), title);
                        }
                    }
                }
            }
        });
        if (i()) {
            multiTabWebView.addAndroidWebChromeClient(new android.webkit.WebChromeClient() {
                public void onReceivedTitle(android.webkit.WebView webView, String str) {
                    WebViewPage.this.g = str;
                    if (!TextUtils.isEmpty(str)) {
                        WebViewPage.a(WebViewPage.this, webView.getUrl(), str);
                    }
                }

                public void onShowCustomView(View view, android.webkit.WebChromeClient.CustomViewCallback customViewCallback) {
                    WebViewPage.this.getContentView().setVisibility(4);
                    DoNotUseTool.getMapManager().getMapView().d(4);
                    WebViewPage.this.n = true;
                }

                public void onHideCustomView() {
                    WebViewPage.this.n = false;
                    WebViewPage.this.getContentView().setVisibility(0);
                    DoNotUseTool.getMapManager().getMapView().d(0);
                }
            });
            multiTabWebView.addAndroidWebViewClient(new WebViewClientEx() {
                boolean showTitleBySystem = false;

                public void onPageStarted(android.webkit.WebView webView, String str, Bitmap bitmap) {
                    if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 18) {
                        webView.setLayerType(1, null);
                        webView.setDrawingCacheEnabled(false);
                    }
                    super.onPageStarted(webView, str, bitmap);
                    if (str.equals("file:///android_asset/connect_error.html") || str.equals("file:///android_asset/not_found_error.html")) {
                        if (!((ajh) WebViewPage.this.mPresenter).g()) {
                            WebViewPage.this.f.setVisibility(0);
                            this.showTitleBySystem = true;
                        }
                    } else if (this.showTitleBySystem && !((ajh) WebViewPage.this.mPresenter).g()) {
                        WebViewPage.this.f.setVisibility(8);
                        this.showTitleBySystem = false;
                    }
                }

                public boolean shouldOverrideUrlLoading(android.webkit.WebView webView, String str) {
                    WebViewPage.this.k();
                    HttpUrlCollector.a(SCENE.WEB_VIEW, str);
                    if (TextUtils.isEmpty(str) || !BaichuanSDKProxy.d().b(str)) {
                        return false;
                    }
                    WebViewPage.k(WebViewPage.this);
                    return true;
                }

                public void onPageFinished(android.webkit.WebView webView, String str) {
                    super.onPageFinished(webView, str);
                    WebViewPage.b(WebViewPage.this.d, (View) WebViewPage.this.i, (View) WebViewPage.this.j);
                    android.webkit.WebBackForwardList copyBackForwardList = webView.copyBackForwardList();
                    if (copyBackForwardList != null) {
                        android.webkit.WebHistoryItem currentItem = copyBackForwardList.getCurrentItem();
                        if (currentItem != null && str.equals(currentItem.getUrl())) {
                            String title = currentItem.getTitle();
                            if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(WebViewPage.this.g) && !title.equals(WebViewPage.this.g)) {
                                WebViewPage.a(WebViewPage.this, webView.getUrl(), title);
                            }
                        }
                    }
                }
            });
        }
        aja aja = (aja) getArguments().getObject("h5_config");
        if (((ajh) this.mPresenter).e()) {
            this.d.setSupportZoom(true);
        }
        this.d.setSupportMultiTab(((ajh) this.mPresenter).f());
        this.e = aja.a;
        if (c(this.e)) {
            String str = this.e;
            this.o = true;
            if (this.d instanceof AmapWebView) {
                ((AmapWebView) this.d).setBaiChuanMode(true);
            }
            android.webkit.WebView androidWebView = this.d.getAndroidWebView();
            WebViewClient androidWebViewClient = this.d.getAndroidWebViewClient();
            android.webkit.WebChromeClient androidWebChromeClient = this.d.getAndroidWebChromeClient();
            if (!(androidWebView == null || androidWebViewClient == null || androidWebChromeClient == null)) {
                BaichuanSDKProxy.d().a(androidWebView, androidWebViewClient, androidWebChromeClient, str);
            }
        } else if (!this.r) {
            this.d.loadUrl(this.e);
        } else {
            this.d.postDelayed(new Runnable() {
                public final void run() {
                    WebViewPage.this.d.loadUrl(WebViewPage.this.e);
                }
            }, 150);
        }
        this.r = false;
        if (this.e.contains("trafficViolations/index.html")) {
            this.v.addGoBackListener(new com.amap.bundle.jsadapter.JsAdapter.b() {
                public final void a() {
                    if (WebViewPage.this.d.getUrl().contains("trafficViolations/index.html")) {
                        WebViewPage.this.finish();
                    }
                }
            });
        }
        HttpUrlCollector.a(SCENE.WEB_VIEW, this.e);
        if (((ajh) this.mPresenter).d()) {
            this.h.setVisibility(0);
        } else {
            this.h.setVisibility(8);
        }
        if (((ajh) this.mPresenter).h()) {
            this.f.setWidgetVisibility(2, 0);
        } else {
            this.f.setWidgetVisibility(2, 8);
        }
        if (!((ajh) this.mPresenter).g()) {
            this.f.setVisibility(8);
        } else if (!TextUtils.isEmpty(((ajh) this.mPresenter).b())) {
            this.f.setTitle(((ajh) this.mPresenter).b());
        }
        defpackage.ajh.a l_ = ((ajh) this.mPresenter).l_();
        this.v.setRightBtn(this.f.findViewById(R.id.title_action_text));
        this.f.setWidgetVisibility(33, 4);
        if (l_ != null) {
            this.f.setWidgetVisibility(33, 0);
            if (!TextUtils.isEmpty(l_.a())) {
                this.f.setActionText(l_.a());
            }
        } else {
            this.f.setWidgetVisibility(33, 8);
        }
        defpackage.ajh.b c2 = ((ajh) this.mPresenter).c();
        if (c2 != null) {
            long j2 = 1000;
            if (c2.a()) {
                this.l.setVisibility(0);
                LoadingViewBL loadingViewBL = this.l;
                AnonymousClass2 r1 = new Runnable() {
                    public final void run() {
                        WebViewPage.this.l.setVisibility(8);
                    }
                };
                if (c2.c() > 0) {
                    j2 = c2.c();
                }
                loadingViewBL.postDelayed(r1, j2);
            } else {
                this.m.setVisibility(0);
                String string = getResources().getString(R.string.common_webview_take_to);
                StringBuilder sb = new StringBuilder();
                sb.append(string);
                sb.append(c2.b());
                SpannableString spannableString = new SpannableString(sb.toString());
                spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.f_c_6)), string.length(), spannableString.length(), 33);
                this.m.setLoadingText(spannableString.toString());
                LoadingViewBL loadingViewBL2 = this.m;
                AnonymousClass3 r12 = new Runnable() {
                    public final void run() {
                        WebViewPage.this.m.setVisibility(8);
                    }
                };
                if (c2.c() > 0) {
                    j2 = c2.c();
                }
                loadingViewBL2.postDelayed(r12, j2);
            }
        } else {
            this.l.setVisibility(8);
            this.m.setVisibility(8);
        }
        this.d.setMultiTabHandle(this);
        b(this.d, (View) this.i, (View) this.j);
    }

    public final void e() {
        if (!this.q) {
            a((ViewGroup) this.p);
        }
    }

    private void a(ViewGroup viewGroup) {
        int width = ags.a(getContext()).width();
        int height = ags.a(getContext()).height();
        ViewGroup.LayoutParams layoutParams = viewGroup.getLayoutParams();
        if (height * 3 < width * 4) {
            this.r = true;
            layoutParams.width = (height * 9) / 16;
            ((ViewGroup) viewGroup.getParent()).setBackgroundColor(-16777216);
            if (layoutParams instanceof LinearLayout.LayoutParams) {
                ((LinearLayout.LayoutParams) layoutParams).gravity = 1;
            } else if (layoutParams instanceof FrameLayout.LayoutParams) {
                ((FrameLayout.LayoutParams) layoutParams).gravity = 1;
            } else if (layoutParams instanceof LayoutParams) {
                ((LayoutParams) layoutParams).addRule(14);
            }
            viewGroup.setLayoutParams(layoutParams);
            viewGroup.invalidate();
            return;
        }
        layoutParams.width = -1;
        viewGroup.setLayoutParams(layoutParams);
        viewGroup.invalidate();
    }

    public final boolean c() {
        return this.n;
    }

    /* access modifiers changed from: private */
    public static void b(MultiTabWebView multiTabWebView, View view, View view2) {
        if (multiTabWebView != null && view != null && view2 != null) {
            view.setEnabled(multiTabWebView.canGoBack());
            view2.setEnabled(multiTabWebView.canGoForward());
        }
    }

    public final void d() {
        k();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            public final void run() {
                if (WebViewPage.this.v != null) {
                    WebViewPage.this.v.onDestory();
                    WebViewPage.this.v = null;
                }
                if (WebViewPage.this.d != null) {
                    WebViewPage.this.d.destroy();
                }
                List<Class<? extends T>> loadServices = ((IMultipleServiceLoader) bqn.a(IMultipleServiceLoader.class)).loadServices(aiy.class);
                if (loadServices != null) {
                    for (Class newInstance : loadServices) {
                        try {
                            ((aiy) newInstance.newInstance()).a();
                        } catch (Exception e) {
                            if (bno.a) {
                                throw new IllegalArgumentException(e);
                            }
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, 500);
    }

    public void onClick(View view) {
        if (view == this.i) {
            this.d.goBack();
            b(this.d, (View) this.i, (View) this.j);
        } else if (view == this.j) {
            this.d.goForward();
            b(this.d, (View) this.i, (View) this.j);
        } else {
            if (view == this.k) {
                this.d.reload();
            }
        }
    }

    public final ON_BACK_TYPE f() {
        if (this.d != null && this.d.canGoBack()) {
            String url = this.d.getUrl();
            if (ajp.a(url)) {
                this.d.loadUrl("javascript:(function () {\n        window.activeEvent = function () {\n        var event = document.createEvent('HTMLEvents');\n        event.initEvent('webViewGoBack', true, true);\n        return document.dispatchEvent(event);\n    };\n    window.activeEvent();\n})();");
            } else if (TextUtils.isEmpty(url) || !url.contains("trafficViolations/index.html")) {
                this.d.goBack();
            } else {
                finish();
            }
            b(this.d, (View) this.i, (View) this.j);
            return ON_BACK_TYPE.TYPE_IGNORE;
        } else if (this.d == null || !ajp.a(this.d.getUrl())) {
            return ON_BACK_TYPE.TYPE_NORMAL;
        } else {
            this.d.loadUrl("javascript:(function () {\n        window.activeEvent = function () {\n        var event = document.createEvent('HTMLEvents');\n        event.initEvent('webViewGoBack', true, true);\n        return document.dispatchEvent(event);\n    };\n    window.activeEvent();\n})();");
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
    }

    private static boolean c(String str) {
        return BaichuanSDKProxy.d().a(str);
    }

    @NonNull
    public final MultiTabWebView a() {
        if (this.o) {
            StringBuilder sb = new StringBuilder();
            sb.append("get webview in baichuan mode\n");
            StackTraceElement[] stackTrace = new Exception(this.e == null ? "" : this.e).getStackTrace();
            if (stackTrace != null && stackTrace.length > 0) {
                for (StackTraceElement stackTraceElement : stackTrace) {
                    if (stackTraceElement != null && !TextUtils.isEmpty(stackTraceElement.toString())) {
                        sb.append(stackTraceElement.toString());
                        sb.append("\n");
                    }
                }
            }
            AMapLog.logNormalNative(AMapLog.GROUP_COMMON, "P0018", ALCTtsConstant.EVENT_ID_TTS_JNI_ERROR, sb.toString());
        }
        return this.d;
    }

    @NonNull
    public final JsAdapter b() {
        return this.v;
    }

    public final boolean a(String str) {
        if (!str.contains("trafficViolations/index.html") && !str.contains("carAchieve/rank.html")) {
            return true;
        }
        return false;
    }

    private boolean i() {
        return j() || !this.z;
    }

    private boolean j() {
        return c(((aja) getArguments().getObject("h5_config")).a);
    }

    public final bfl g() {
        if (this.w == null) {
            this.w = new c(this, 0);
        }
        return this.w;
    }

    public final long h() {
        return this.b;
    }

    public final void a(long j2, List<String> list) {
        this.b = j2;
        this.c = list;
        if (this.b != 0) {
            bfo bfo = (bfo) defpackage.esb.a.a.a(bfo.class);
            if (bfo != null) {
                if (this.x == null) {
                    this.x = new a(this, 0);
                }
                if (this.y == null) {
                    this.y = new b(this, 0);
                }
                bfo.a(this.x);
                bfo.a(this.y);
            }
        }
    }

    /* access modifiers changed from: private */
    public void k() {
        this.b = 0;
        this.c.clear();
        bfo bfo = (bfo) defpackage.esb.a.a.a(bfo.class);
        if (bfo != null) {
            bfo.b(0);
            bfo.c();
            bfo.b(this.y);
        }
    }

    public /* synthetic */ IPresenter createPresenter() {
        ajh ajh;
        PageBundle arguments = getArguments();
        if (arguments == null || !arguments.containsKey("h5_config")) {
            throw new IllegalArgumentException("使用WebViewPage，必须通过Bundle传入配置");
        }
        aja aja = (aja) arguments.getObject("h5_config");
        if (aja != null) {
            ajh = aja.b;
        } else {
            ajh = null;
        }
        if (ajh == null) {
            ajh = new ajg();
        }
        ajh.a(this);
        return ajh;
    }

    static /* synthetic */ void a(View view) {
        if (view != null) {
            ((InputMethodManager) view.getContext().getSystemService("input_method")).hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006f, code lost:
        if (r0.find() == false) goto L_0x0073;
     */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x007b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(com.amap.bundle.webview.page.WebViewPage r7, java.lang.String r8, java.lang.String r9) {
        /*
            com.autonavi.map.fragmentcontainer.page.IPresenter r0 = r7.mPresenter
            ajh r0 = (defpackage.ajh) r0
            boolean r0 = r0.g()
            if (r0 == 0) goto L_0x009d
            if (r9 == 0) goto L_0x009d
            com.autonavi.map.fragmentcontainer.page.IPresenter r0 = r7.mPresenter
            ajh r0 = (defpackage.ajh) r0
            java.lang.String r0 = r0.b()
            if (r0 != 0) goto L_0x009d
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x0072
            boolean r0 = android.text.TextUtils.isEmpty(r8)
            if (r0 == 0) goto L_0x0025
            goto L_0x0072
        L_0x0025:
            java.lang.String r0 = r9.substring(r2, r1)
            boolean r3 = android.text.TextUtils.isEmpty(r0)
            if (r3 == 0) goto L_0x0031
        L_0x002f:
            r0 = 0
            goto L_0x0058
        L_0x0031:
            char[] r0 = r0.toCharArray()
            int r3 = r0.length
            r4 = 0
        L_0x0037:
            if (r4 >= r3) goto L_0x002f
            char r5 = r0[r4]
            java.lang.Character$UnicodeBlock r5 = java.lang.Character.UnicodeBlock.of(r5)
            java.lang.Character$UnicodeBlock r6 = java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
            if (r5 == r6) goto L_0x0057
            java.lang.Character$UnicodeBlock r6 = java.lang.Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            if (r5 == r6) goto L_0x0057
            java.lang.Character$UnicodeBlock r6 = java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
            if (r5 == r6) goto L_0x0057
            java.lang.Character$UnicodeBlock r6 = java.lang.Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
            if (r5 == r6) goto L_0x0057
            java.lang.Character$UnicodeBlock r6 = java.lang.Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
            if (r5 != r6) goto L_0x0054
            goto L_0x0057
        L_0x0054:
            int r4 = r4 + 1
            goto L_0x0037
        L_0x0057:
            r0 = 1
        L_0x0058:
            if (r0 == 0) goto L_0x005b
            goto L_0x0073
        L_0x005b:
            java.lang.String r0 = "((http|https)://)?[A-Za-z0-9_]+([.][A-Za-z0-9_]+)*(/[A-Za-z0-9_]+)*([?][A-Za-z0-9_]+=[A-Za-z0-9_]+)?([&][A-Za-z0-9_]+=[A-Za-z0-9_]+)*([.](html|htm))*$"
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r0)
            java.util.regex.Matcher r0 = r0.matcher(r9)
            boolean r8 = r8.equals(r9)
            if (r8 != 0) goto L_0x0072
            boolean r8 = r0.find()
            if (r8 != 0) goto L_0x0072
            goto L_0x0073
        L_0x0072:
            r1 = 0
        L_0x0073:
            if (r1 == 0) goto L_0x007b
            com.autonavi.widget.ui.TitleBar r7 = r7.f
            r7.setTitle(r9)
            return
        L_0x007b:
            java.lang.String r8 = "file:///android_asset/connect_error.html"
            boolean r8 = r8.contains(r9)
            if (r8 != 0) goto L_0x0094
            java.lang.String r8 = "file:///android_asset/not_found_error.html"
            boolean r8 = r8.contains(r9)
            if (r8 == 0) goto L_0x008c
            goto L_0x0094
        L_0x008c:
            com.autonavi.widget.ui.TitleBar r7 = r7.f
            java.lang.String r8 = ""
            r7.setTitle(r8)
            goto L_0x009d
        L_0x0094:
            com.autonavi.widget.ui.TitleBar r7 = r7.f
            java.lang.String r8 = "出错了"
            r7.setTitle(r8)
            return
        L_0x009d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.webview.page.WebViewPage.a(com.amap.bundle.webview.page.WebViewPage, java.lang.String, java.lang.String):void");
    }

    static /* synthetic */ void k(WebViewPage webViewPage) {
        if (!BaichuanSDKProxy.d().b()) {
            BaichuanSDKProxy.d().a(webViewPage.a);
        } else if (!BaichuanSDKProxy.d().c()) {
            BaichuanSDKProxy.d().b(webViewPage.a);
        } else {
            if (!BaichuanSDKProxy.d().a()) {
                BaichuanSDKProxy.d().c(webViewPage.a);
            }
        }
    }

    static /* synthetic */ void s(WebViewPage webViewPage) {
        if (webViewPage.isAlive()) {
            ToastHelper.showLongToast(webViewPage.getString(R.string.please_bind_taobao_first));
        }
    }

    static /* synthetic */ void t(WebViewPage webViewPage) {
        if (webViewPage.e == null || (!webViewPage.e.contains("http://detail.tmall.com/item") && !webViewPage.e.contains("https://h5.m.taobao.com/trip/train-amap/train-detail/index.html"))) {
            webViewPage.d.postDelayed(new Runnable() {
                public final void run() {
                    WebViewPage.a((View) WebViewPage.this.d);
                    WebViewPage.this.finish();
                }
            }, 500);
        } else {
            webViewPage.d.postDelayed(new Runnable() {
                public final void run() {
                    WebViewPage.a((View) WebViewPage.this.d);
                }
            }, 500);
        }
    }

    static /* synthetic */ void b(WebViewPage webViewPage, String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("action", "activeEvent");
            jSONObject.put("type", str);
            jSONObject.put("data", new JSONObject(str2));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        if (webViewPage.v != null) {
            StringBuilder sb = new StringBuilder("sendDataToH5: ");
            sb.append(jSONObject.toString());
            AMapLog.d("WebViewH5", sb.toString());
            webViewPage.v.callJs("activeEvent", jSONObject.toString());
        }
    }
}
