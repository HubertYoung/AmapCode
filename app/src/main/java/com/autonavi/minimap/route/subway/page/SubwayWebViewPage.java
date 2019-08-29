package com.autonavi.minimap.route.subway.page;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.amap.bundle.jsadapter.JsAdapter;
import com.amap.bundle.webview.page.BaseExtendWebViewPage;
import com.amap.bundle.webview.widget.ExtendedWebView;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTopAllowDuplicate;
import com.autonavi.minimap.R;
import com.autonavi.minimap.widget.OnWebViewEventListener;
import com.uc.webview.export.WebView;
import java.io.File;

public class SubwayWebViewPage extends BaseExtendWebViewPage<Object> implements launchModeSingleTopAllowDuplicate, OnWebViewEventListener {
    public String d;
    private PageBundle e;
    private String f;
    /* access modifiers changed from: private */
    public View g;
    /* access modifiers changed from: private */
    public View h;

    public int maxDuplicateCount() {
        return 3;
    }

    public void onReceivedTitle(WebView webView, String str) {
    }

    public void onWebViewPageCanceled(WebView webView) {
    }

    public void onWebViewPageRefresh(WebView webView) {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.subway_webview);
        View contentView = getContentView();
        requestScreenOrientation(1);
        if (euk.a()) {
            contentView.setPadding(contentView.getPaddingLeft(), euk.a(getContext()), contentView.getPaddingRight(), contentView.getPaddingBottom());
        }
        this.b = (ExtendedWebView) contentView.findViewById(R.id.webView);
        this.b.setOnWebViewEventListener(this);
        this.g = findViewById(R.id.subway_loading);
        this.h = findViewById(R.id.subway_network_error_retry);
        this.h.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                SubwayWebViewPage.this.d();
            }
        });
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.d = arguments.getString("url");
            this.f = arguments.getString("adCode");
        }
        this.b.clearHistory();
        if (this.a == null) {
            this.a = new JsAdapter((bid) this, (a) this.b);
        }
        if (this.e == null) {
            this.e = new PageBundle();
        }
        JsAdapter.registerGlobalJsAction("searchRouteDetail", eic.class);
        JsAdapter.registerGlobalJsAction("openSubway", eid.class);
        this.b.initializeWebView((Object) this.a, (Handler) null, true, false, true);
        this.b.setVisibility(0);
        this.b.clearView();
        this.b.clearCache(false);
        d();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0076, code lost:
        if (r2.contains(r3.toString()) != false) goto L_0x0078;
     */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0083  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onWebViewPageFinished(com.uc.webview.export.WebView r36) {
        /*
            r35 = this;
            if (r36 == 0) goto L_0x0089
            r0 = r35
            java.lang.String r1 = r0.f
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L_0x007a
            java.lang.String r3 = "110000"
            java.lang.String r4 = "310000"
            java.lang.String r5 = "440100"
            java.lang.String r6 = "440300"
            java.lang.String r7 = "420100"
            java.lang.String r8 = "120000"
            java.lang.String r9 = "320100"
            java.lang.String r10 = "810000"
            java.lang.String r11 = "500000"
            java.lang.String r12 = "330100"
            java.lang.String r13 = "210100"
            java.lang.String r14 = "210200"
            java.lang.String r15 = "510100"
            java.lang.String r16 = "220100"
            java.lang.String r17 = "320500"
            java.lang.String r18 = "440600"
            java.lang.String r19 = "530100"
            java.lang.String r20 = "610100"
            java.lang.String r21 = "410100"
            java.lang.String r22 = "230100"
            java.lang.String r23 = "430100"
            java.lang.String r24 = "330200"
            java.lang.String r25 = "320200"
            java.lang.String r26 = "370200"
            java.lang.String r27 = "360100"
            java.lang.String r28 = "350100"
            java.lang.String r29 = "441900"
            java.lang.String r30 = "450100"
            java.lang.String r31 = "340100"
            java.lang.String r32 = "130100"
            java.lang.String r33 = "520100"
            java.lang.String r34 = "350200"
            java.lang.String[] r2 = new java.lang.String[]{r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28, r29, r30, r31, r32, r33, r34}
            java.util.List r2 = java.util.Arrays.asList(r2)
            if (r2 == 0) goto L_0x007a
            java.lang.String r3 = r1.trim()
            boolean r3 = r2.contains(r3)
            if (r3 != 0) goto L_0x0078
            java.lang.StringBuffer r3 = new java.lang.StringBuffer
            java.lang.String r1 = r1.trim()
            r3.<init>(r1)
            java.lang.String r1 = "00"
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            boolean r1 = r2.contains(r1)
            if (r1 == 0) goto L_0x007a
        L_0x0078:
            r1 = 1
            goto L_0x007b
        L_0x007a:
            r1 = 0
        L_0x007b:
            if (r1 == 0) goto L_0x0083
            r1 = 10000(0x2710, float:1.4013E-41)
            defpackage.eko.a(r1)
            return
        L_0x0083:
            r1 = 20045(0x4e4d, float:2.8089E-41)
            defpackage.eko.a(r1)
            return
        L_0x0089:
            r0 = r35
            r1 = 10020(0x2724, float:1.4041E-41)
            defpackage.eko.a(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.subway.page.SubwayWebViewPage.onWebViewPageFinished(com.uc.webview.export.WebView):void");
    }

    public void onWebViewPageStart(WebView webView) {
        this.a.closeTimeToast();
    }

    /* access modifiers changed from: private */
    public void d() {
        md mdVar = (md) a.a.a(md.class);
        if (mdVar != null) {
            this.h.setVisibility(8);
            this.g.setVisibility(0);
            mdVar.a("subway", new mc() {
                public final void a(String str) {
                    StringBuilder sb = new StringBuilder("file://");
                    sb.append(new File(str, "index.html").getAbsolutePath());
                    final String sb2 = sb.toString();
                    SubwayWebViewPage.this.b.post(new Runnable() {
                        public final void run() {
                            ViewGroup viewGroup = (ViewGroup) SubwayWebViewPage.this.findViewById(R.id.subway_webview_container);
                            viewGroup.removeView(SubwayWebViewPage.this.g);
                            viewGroup.removeView(SubwayWebViewPage.this.h);
                            SubwayWebViewPage.this.b.loadUrlInNewWebView(sb2);
                        }
                    });
                }

                public final void a() {
                    SubwayWebViewPage.this.b.post(new Runnable() {
                        public final void run() {
                            SubwayWebViewPage.this.g.setVisibility(8);
                            SubwayWebViewPage.this.h.setVisibility(0);
                        }
                    });
                }
            });
        }
    }
}
