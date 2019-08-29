package defpackage;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.webview.widget.AmapWebView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.inter.IAppUpDateManager;
import com.autonavi.minimap.component.BottomView;
import com.autonavi.minimap.component.CommonView;
import com.autonavi.minimap.component.ContainerView;
import com.autonavi.minimap.component.CopyrightView;
import com.autonavi.minimap.component.LogoView;
import com.autonavi.minimap.component.SearchButton;
import com.autonavi.minimap.component.SkipButton;
import com.autonavi.minimap.component.SplashVideoView;
import com.autonavi.minimap.landingpage.LandingPageHander$5;
import com.autonavi.minimap.landingpage.LandingPageHander$6;
import com.autonavi.minimap.widget.GifMovieView;
import com.autonavi.minimap.widget.GifMovieView.GifHandler;
import com.autonavi.minimap.widget.GifMovieView.ScaleType;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import com.autonavi.widget.ui.BalloonLayout;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.webview.inner.SafeWebView.b;
import com.uc.webview.export.WebBackForwardList;
import com.uc.webview.export.WebChromeClient;
import com.uc.webview.export.WebChromeClient.CustomViewCallback;
import com.uc.webview.export.WebHistoryItem;
import com.uc.webview.export.WebView;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: drt reason: default package */
/* compiled from: AfpSplashManager */
public final class drt implements dmj {
    public dmk a;
    chi b = null;
    SkipButton c = null;
    boolean d = false;
    boolean e = false;
    long f = 0;
    long g = BalloonLayout.DEFAULT_DISPLAY_DURATION;
    private WeakReference<Activity> h = null;
    private SplashVideoView i = null;
    private GifMovieView j = null;
    private drr k = null;
    private View l = null;
    private boolean m = false;
    private int n = -1;
    private int o = 1000;
    private Timer p = null;
    private TimerTask q = null;
    private Timer r = null;
    private TimerTask s = null;
    private boolean t = false;

    public drt(Activity activity) {
        this.h = new WeakReference<>(activity);
        this.k = new drq(this);
    }

    public final void a(dmk dmk) {
        this.a = dmk;
    }

    public final void a(boolean z) {
        if (z) {
            dsa.a();
            return;
        }
        MapSharePreference mapSharePreference = new MapSharePreference((String) FunctionSupportConfiger.SP_NAME_AfpSplashEvents);
        long longValue = mapSharePreference.getLongValue("afp_again_launch_splash_time", 0);
        if (longValue != 0) {
            mapSharePreference.remove("afp_again_launch_splash_time");
            if (System.currentTimeMillis() - longValue > ((long) ((cha.c() * 60) * 60)) * 1000) {
                dsa.a();
                StringBuilder sb = new StringBuilder("当前线程：");
                sb.append(Thread.currentThread().getName());
                sb.append(" re update splash ad");
                AMapLog.debug("basemap.splashscreen", "render", sb.toString());
            }
        }
    }

    public final void a() {
        ckb.a((String) "AfpSplashManager#initAfpSplash");
        StringBuilder sb = new StringBuilder("当前线程：");
        sb.append(Thread.currentThread().getName());
        sb.append(" ------------------------------------Start  #initAfpSplash Start------------");
        AMapLog.debug("basemap.splashscreen", "render", sb.toString());
        if (h()) {
            this.b = chc.a();
            if (this.b == null) {
                StringBuilder sb2 = new StringBuilder("当前线程：");
                sb2.append(Thread.currentThread().getName());
                sb2.append(" ------------------------------------get splash ad failed------------");
                AMapLog.debug("basemap.splashscreen", "render", sb2.toString());
                a(false, false);
                return;
            }
            StringBuilder sb3 = new StringBuilder("当前线程：");
            sb3.append(Thread.currentThread().getName());
            sb3.append(" ------------------------------------get splash ad succeeded------------");
            AMapLog.debug("basemap.splashscreen", "render", sb3.toString());
            ahm.c(new Runnable(this.b) {
                final /* synthetic */ chi a;

                {
                    this.a = r1;
                }

                public final void run() {
                    JSONObject jSONObject;
                    try {
                        jSONObject = new JSONObject(chh.a());
                    } catch (Throwable th) {
                        th.printStackTrace();
                        jSONObject = null;
                    }
                    if (jSONObject != null) {
                        JSONArray optJSONArray = jSONObject.optJSONArray("ad");
                        if (optJSONArray != null) {
                            int length = optJSONArray.length();
                            if (length > 0) {
                                for (int i = 0; i < length; i++) {
                                    JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                                    if (optJSONObject != null) {
                                        JSONArray optJSONArray2 = optJSONObject.optJSONArray("creative");
                                        if (optJSONArray2 != null && optJSONArray2.length() > 0) {
                                            for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                                                JSONObject optJSONObject2 = optJSONArray2.optJSONObject(i2);
                                                if (optJSONObject2 != null) {
                                                    String optString = optJSONObject2.optString("cid");
                                                    if (!TextUtils.isEmpty(optString)) {
                                                        String optString2 = optJSONObject2.optString(GirfFavoriteRoute.JSON_FIELD_ROUTE_START_TIME);
                                                        if (!TextUtils.isEmpty(optString2)) {
                                                            String optString3 = optJSONObject2.optString(GirfFavoriteRoute.JSON_FIELD_ROUTE_END_TIME);
                                                            if (!TextUtils.isEmpty(optString3) && optString.equals(this.a.e) && Integer.valueOf(optString2).equals(Integer.valueOf(this.a.b)) && Integer.valueOf(optString3).equals(Integer.valueOf(this.a.c))) {
                                                                optJSONArray2.remove(i2);
                                                                chh.a(jSONObject.toString());
                                                                return;
                                                            }
                                                        } else {
                                                            continue;
                                                        }
                                                    } else {
                                                        continue;
                                                    }
                                                }
                                            }
                                            continue;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
            StringBuilder sb4 = new StringBuilder("当前线程：");
            sb4.append(Thread.currentThread().getName());
            sb4.append(" ------------------------------------render start------------");
            AMapLog.debug("basemap.splashscreen", "render", sb4.toString());
            chi chi = this.b;
            drr drr = this.k;
            Application application = AMapAppGlobal.getApplication();
            View view = null;
            String str = chi.w;
            String str2 = chi.x;
            boolean z = chi.h;
            if ("static_image".equals(str)) {
                StringBuilder sb5 = new StringBuilder("当前线程：");
                sb5.append(Thread.currentThread().getName());
                sb5.append(" init img renderer");
                AMapLog.debug("basemap.splashscreen", "render", sb5.toString());
                view = new CommonView(application, drr, BitmapFactory.decodeFile(str2));
            } else if ("video".equals(str)) {
                StringBuilder sb6 = new StringBuilder("当前线程：");
                sb6.append(Thread.currentThread().getName());
                sb6.append(" init video renderer");
                AMapLog.debug("basemap.splashscreen", "render", sb6.toString());
                this.i = new SplashVideoView(application, drr, str2, z);
                view = this.i;
            } else if ("dynamic_image".equals(str)) {
                StringBuilder sb7 = new StringBuilder("当前线程：");
                sb7.append(Thread.currentThread().getName());
                sb7.append(" init gif renderer");
                AMapLog.debug("basemap.splashscreen", "render", sb7.toString());
                LayoutParams layoutParams = new LayoutParams(-1, -1);
                this.j = new GifMovieView(application);
                this.j.setListener((GifHandler) drr);
                this.j.setLayoutParams(layoutParams);
                this.j.setBackgroundResource(R.color.white_frame);
                this.j.setScaleType(ScaleType.FIT_XY);
                this.j.setRunOnce(true);
                this.j.setMovie(str2);
                view = this.j;
            }
            LayoutParams layoutParams2 = new LayoutParams(-1, -1);
            Activity activity = (Activity) this.h.get();
            if (activity != null) {
                activity.addContentView(view, layoutParams2);
            }
            chi chi2 = this.b;
            drr drr2 = this.k;
            Application application2 = AMapAppGlobal.getApplication();
            boolean z2 = chi2.a;
            String str3 = chi2.l;
            String str4 = chi2.m;
            boolean z3 = chi2.n;
            String str5 = chi2.o;
            ContainerView containerView = new ContainerView(application2, drr2);
            BottomView bottomView = new BottomView(application2, drr2);
            this.c = new SkipButton(application2, drr2, z2);
            bottomView.addView(this.c);
            if ("part".equals(str3)) {
                bottomView.addView(new CopyrightView(application2, drr2));
            }
            containerView.addView(bottomView);
            if (!"0".equals(str4)) {
                containerView.addView(new LogoView(application2, drr2, str4));
            }
            if (z3) {
                containerView.addView(new SearchButton(application2, drr2, str5));
            }
            this.l = containerView;
            long j2 = this.b.k;
            if (j2 > 0) {
                this.g = j2;
            }
            this.r = new Timer();
            this.s = new TimerTask() {
                public final void run() {
                    if (drt.this.d || drt.this.e) {
                        return;
                    }
                    if (drt.this.g <= 0) {
                        StringBuilder sb = new StringBuilder("当前线程：");
                        sb.append(Thread.currentThread().getName());
                        sb.append(" ------------------------------------skip timer time over------------");
                        AMapLog.debug("basemap.splashscreen", "render", sb.toString());
                        String a2 = chi.a();
                        StringBuilder sb2 = new StringBuilder("afp");
                        sb2.append(drt.this.b.e);
                        emd.a(a2, sb2.toString(), (String) LogConstant.SPLASH_SCREEN_COUNTDOWN_OVER, (String) null, (String) null, (String) null);
                        drt.this.a(false, false);
                        return;
                    }
                    String valueOf = String.valueOf(drt.this.g / 1000);
                    drt drt = drt.this;
                    if (drt.c != null) {
                        drt.c.setCountDownText(valueOf);
                    }
                    drt.this.g -= 1000;
                }
            };
            if (!"video".equals(this.b.w)) {
                b();
            }
            emd.a(this.b);
            cke.a((String) "2");
            new MapSharePreference((String) FunctionSupportConfiger.SP_NAME_AfpSplashEvents).putLongValue("afp_splash_show_to_skip_time", System.currentTimeMillis());
            this.f = cha.b();
            this.p = new Timer();
            this.q = new TimerTask() {
                public final void run() {
                    if (drt.this.d || drt.this.e) {
                        return;
                    }
                    if (drt.this.f <= 0) {
                        StringBuilder sb = new StringBuilder("当前线程：");
                        sb.append(Thread.currentThread().getName());
                        sb.append(" ------------------------------------main timer time over------------");
                        AMapLog.debug("basemap.splashscreen", "render", sb.toString());
                        String a2 = chi.a();
                        StringBuilder sb2 = new StringBuilder("afp");
                        sb2.append(drt.this.b.e);
                        emd.a(a2, sb2.toString(), (String) LogConstant.SPLASH_SCREEN_TIMEOVER, (String) null, (String) null, (String) null);
                        drt.this.a(false, false);
                        return;
                    }
                    StringBuilder sb3 = new StringBuilder("当前线程：");
                    sb3.append(Thread.currentThread().getName());
                    sb3.append(" main timer left：");
                    sb3.append(drt.this.f);
                    sb3.append(" ms");
                    AMapLog.debug("basemap.splashscreen", "render", sb3.toString());
                    drt.this.f -= 1000;
                }
            };
            this.p.schedule(this.q, 0, (long) this.o);
            StringBuilder sb8 = new StringBuilder("当前线程：");
            sb8.append(Thread.currentThread().getName());
            sb8.append(" ------------------------------------main timer start------------");
            AMapLog.debug("basemap.splashscreen", "render", sb8.toString());
            return;
        }
        StringBuilder sb9 = new StringBuilder("当前线程：");
        sb9.append(Thread.currentThread().getName());
        sb9.append(" new user skip splash ad");
        AMapLog.debug("basemap.splashscreen", "render", sb9.toString());
        a(false, false);
    }

    public final void b() {
        if (!this.d && !this.t) {
            Activity activity = (Activity) this.h.get();
            if (activity != null && this.l != null) {
                this.t = true;
                activity.addContentView(this.l, new ViewGroup.LayoutParams(-1, -1));
                if (!(this.r == null || this.s == null)) {
                    this.r.schedule(this.s, 0, (long) this.o);
                    StringBuilder sb = new StringBuilder("当前线程：");
                    sb.append(Thread.currentThread().getName());
                    sb.append(" ------------------------------------skip timer start------------");
                    AMapLog.debug("basemap.splashscreen", "render", sb.toString());
                }
            }
        }
    }

    public final void c() {
        if (this.i != null) {
            this.i.start();
        }
    }

    public final void d() {
        if (!this.d) {
            StringBuilder sb = new StringBuilder("当前线程：");
            sb.append(Thread.currentThread().getName());
            sb.append(" skip ad");
            AMapLog.debug("basemap.splashscreen", "render", sb.toString());
            emd.c(this.b);
            a(false, true);
        }
    }

    public final void e() {
        if (!this.d && !this.m) {
            this.m = true;
            StringBuilder sb = new StringBuilder("当前线程：");
            sb.append(Thread.currentThread().getName());
            sb.append(" click ad");
            AMapLog.debug("basemap.splashscreen", "render", sb.toString());
            String str = this.b.q;
            if (!TextUtils.isEmpty(str)) {
                emd.b(this.b);
                IAppUpDateManager iAppUpDateManager = (IAppUpDateManager) ank.a(IAppUpDateManager.class);
                if (iAppUpDateManager != null) {
                    iAppUpDateManager.setLegalDownloadUrl(str);
                }
                if ("webview_url".equals(this.b.p) && this.b.r) {
                    Activity activity = (Activity) this.h.get();
                    if (activity != null) {
                        dnj a2 = dnj.a();
                        drr drr = this.k;
                        drr drr2 = this.k;
                        a2.i = drr;
                        a2.a = LayoutInflater.from(AMapAppGlobal.getApplication()).inflate(R.layout.landingpage_webview, null);
                        a2.a.setFocusableInTouchMode(true);
                        a2.a.setFocusable(true);
                        a2.a.requestFocus();
                        a2.a.setOnKeyListener(drr2);
                        a2.b = (RelativeLayout) a2.a.findViewById(R.id.load_webview_layout);
                        a2.c = new AmapWebView((Context) activity, true);
                        ((AmapWebView) a2.c).setIsRequestFocusOnPageFinished(false);
                        a2.c.setBackgroundColor(-1);
                        a2.b.addView(a2.c, 0, new RelativeLayout.LayoutParams(-1, -1));
                        a2.d = (TitleBar) a2.a.findViewById(R.id.title);
                        a2.d.setWidgetVisibility(1, 0);
                        a2.d.setWidgetVisibility(2, 0);
                        a2.d.setWidgetVisibility(17, 0);
                        a2.d.setWidgetVisibility(33, 0);
                        a2.d.setOnBackClickListener(a2.j);
                        a2.d.setOnExBackClickListener(a2.k);
                        a2.e = a2.a.findViewById(R.id.id_web_bottom);
                        a2.e.setVisibility(0);
                        a2.f = (ImageButton) a2.a.findViewById(R.id.page_last);
                        a2.g = (ImageButton) a2.a.findViewById(R.id.page_next);
                        a2.h = (ImageButton) a2.a.findViewById(R.id.page_reload);
                        a2.f.setOnClickListener(a2);
                        a2.g.setOnClickListener(a2);
                        a2.h.setOnClickListener(a2);
                        a2.c.addWebChromeClient(new WebChromeClient() {
                            public final void onReceivedTitle(WebView webView, String str) {
                                dnj.this.m = str;
                                if (!TextUtils.isEmpty(str)) {
                                    dnj.a(dnj.this, webView.getUrl(), str);
                                }
                            }

                            public final void onShowCustomView(View view, CustomViewCallback customViewCallback) {
                                dnj.this.a.setVisibility(4);
                            }

                            public final void onHideCustomView() {
                                dnj.this.a.setVisibility(0);
                            }
                        });
                        a2.c.addWebViewClient(new b() {
                            public final boolean shouldOverrideUrlLoading(WebView webView, String str) {
                                return false;
                            }

                            public final void onPageStarted(WebView webView, String str, Bitmap bitmap) {
                                if (VERSION.SDK_INT >= 16 && VERSION.SDK_INT <= 18) {
                                    webView.setLayerType(1, null);
                                    webView.setDrawingCacheEnabled(false);
                                }
                                super.onPageStarted(webView, str, bitmap);
                            }

                            public final void onPageFinished(WebView webView, String str) {
                                super.onPageFinished(webView, str);
                                dnj.a(dnj.this.c, (View) dnj.this.f, (View) dnj.this.g);
                                WebBackForwardList copyBackForwardList = webView.copyBackForwardList();
                                if (copyBackForwardList != null) {
                                    WebHistoryItem currentItem = copyBackForwardList.getCurrentItem();
                                    if (currentItem != null && str.equals(currentItem.getUrl())) {
                                        String title = currentItem.getTitle();
                                        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(dnj.this.m) && !title.equals(dnj.this.m)) {
                                            dnj.a(dnj.this, webView.getUrl(), title);
                                        }
                                    }
                                }
                            }
                        });
                        a2.c.addAndroidWebChromeClient(new LandingPageHander$5(a2));
                        a2.c.addAndroidWebViewClient(new LandingPageHander$6(a2));
                        a2.c.loadUrl(str);
                        dnj.a(a2.c, (View) a2.f, (View) a2.g);
                        activity.addContentView(a2.a, new LayoutParams(-1, -1));
                        g();
                        return;
                    }
                }
                a(true, false);
            }
        }
    }

    public final void a(int i2, int i3) {
        if (!this.d) {
            if (this.b != null) {
                String a2 = chi.a();
                StringBuilder sb = new StringBuilder("afp");
                sb.append(this.b.e);
                emd.a(a2, sb.toString(), (String) null, (String) "renderfail", (String) null, (String) null);
                StringBuilder sb2 = new StringBuilder(" render error,session id: ");
                sb2.append(chi.a());
                sb2.append(" cid: ");
                sb2.append(this.b.e);
                sb2.append(" material type: ");
                sb2.append(this.b.w);
                sb2.append(" what: ");
                sb2.append(i2);
                sb2.append(" extra: ");
                sb2.append(i3);
                AMapLog.error("basemap.splashscreen", "render", sb2.toString());
            }
            i();
            a(false, true);
        }
    }

    public final void a(int i2, String str, String str2) {
        if (!this.d) {
            if (this.b != null) {
                String a2 = chi.a();
                StringBuilder sb = new StringBuilder("afp");
                sb.append(this.b.e);
                emd.a(a2, sb.toString(), (String) null, (String) "renderfail", (String) null, (String) null);
                StringBuilder sb2 = new StringBuilder(" render error,session id: ");
                sb2.append(chi.a());
                sb2.append(" cid: ");
                sb2.append(this.b.e);
                sb2.append(" material type: ");
                sb2.append(this.b.w);
                sb2.append(" errorType: ");
                sb2.append(i2);
                sb2.append(" msg: ");
                sb2.append(str);
                sb2.append(" errorMsg: ");
                sb2.append(str2);
                AMapLog.error("basemap.splashscreen", "render", sb2.toString());
            }
            i();
            a(false, true);
        }
    }

    public final void a(final boolean z, final boolean z2) {
        if (!this.d) {
            this.d = true;
            g();
            if (Looper.myLooper() == Looper.getMainLooper()) {
                b(z, z2);
                return;
            }
            aho.a(new Runnable() {
                public final void run() {
                    drt.this.b(z, z2);
                }
            });
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(boolean z, boolean z2) {
        String str;
        if (this.a != null) {
            if (z2) {
                Activity activity = (Activity) this.h.get();
                if (activity != null && !new MapSharePreference((String) FunctionSupportConfiger.SP_NAME_AfpSplashEvents).getBooleanValue("afp_splash_again_show", false)) {
                    activity.setContentView(new View(activity.getApplicationContext()));
                }
            }
            StringBuilder sb = new StringBuilder("当前线程：");
            sb.append(Thread.currentThread().getName());
            sb.append(" ------------------------------------Over  #startMapView Over------------");
            AMapLog.debug("basemap.splashscreen", "render", sb.toString());
            String str2 = null;
            if (!z || this.b == null) {
                str = null;
            } else {
                str2 = this.b.p;
                str = this.b.q;
            }
            this.a.a(str2, str);
        }
    }

    public final void b(boolean z) {
        if (z) {
            this.e = z;
            if (this.i != null && this.i.isPlaying()) {
                this.i.pause();
                this.n = this.i.getCurrentPosition();
            }
        }
    }

    public final void c(boolean z) {
        if (z) {
            this.e = !z;
            if (this.i != null) {
                this.i.seekTo(this.n == -1 ? 0 : this.n);
                this.i.start();
            }
        }
    }

    public final void f() {
        if (this.h != null) {
            this.h.clear();
        }
    }

    private void g() {
        if (this.q != null) {
            this.q.cancel();
        }
        if (this.p != null) {
            this.p.cancel();
            this.p.purge();
        }
        if (this.s != null) {
            this.s.cancel();
        }
        if (this.r != null) {
            this.r.cancel();
            this.r.purge();
        }
        if (this.j != null) {
            this.j.setPaused(true);
        }
        if (this.i != null) {
            this.i.stopPlayback();
        }
    }

    private static boolean h() {
        int i2;
        int intValue = new MapSharePreference((String) FunctionSupportConfiger.SP_NAME_AfpSplashEvents).getIntValue("afp_splash_first_launch_time", -1);
        if (intValue == -1) {
            return false;
        }
        try {
            i2 = Integer.parseInt(String.format("%02d", new Object[]{Integer.valueOf(Calendar.getInstance(Locale.CHINA).get(6))}));
        } catch (NumberFormatException unused) {
            i2 = 0;
        }
        if (Math.abs(i2 - intValue) >= cha.a()) {
            return true;
        }
        return false;
    }

    private boolean i() {
        if (this.b == null) {
            return false;
        }
        String str = this.b.x;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (!file.exists()) {
            return false;
        }
        file.delete();
        if (!file.exists()) {
            return true;
        }
        return false;
    }
}
