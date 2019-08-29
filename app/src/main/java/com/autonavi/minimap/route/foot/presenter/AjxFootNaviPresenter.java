package com.autonavi.minimap.route.foot.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.blutils.app.ConfigerHelper;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LOCATION_SCENE;
import com.autonavi.common.json.JsonUtil;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.pos.LocInfo;
import com.autonavi.jni.ae.pos.LocListener;
import com.autonavi.jni.ae.pos.LocManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.ajx3.views.AmapAjxViewInterface;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.feedback.FeedbackContract.ErrorReportType;
import com.autonavi.minimap.route.foot.page.AjxFootNaviPage;
import com.autonavi.minimap.route.ride.dest.util.AmapBroadcastReceiver;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.BalloonLayout;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.Tts;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONException;
import org.json.JSONObject;

public final class AjxFootNaviPresenter<Page extends AjxFootNaviPage> extends Ajx3PagePresenter implements axn, axo, LocListener, a {
    /* access modifiers changed from: private */
    public boolean A = false;
    /* access modifiers changed from: private */
    public boolean B = true;
    /* access modifiers changed from: private */
    public List<Long> C = new ArrayList();
    private HandlerThread D;
    private Handler E;
    private String F;
    private eda G;
    private c H;
    /* access modifiers changed from: private */
    public Handler I = new Handler() {
        public final void handleMessage(Message message) {
            if (message.what == 123) {
                AjxFootNaviPresenter.this.a.c(true);
            }
        }
    };
    private Runnable J = new Runnable() {
        public final void run() {
            while (!AjxFootNaviPresenter.this.A && !AjxFootNaviPresenter.this.z) {
                AjxFootNaviPresenter.this.A = AjxFootNaviPresenter.a(AjxFootNaviPresenter.this, (float) AjxFootNaviPresenter.this.v);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (AjxFootNaviPresenter.this.A) {
                AjxFootNaviPresenter.this.I.sendEmptyMessage(123);
                AjxFootNaviPresenter.this.b = true;
            }
            AjxFootNaviPresenter.this.C.clear();
            AjxFootNaviPresenter.this.y.clear();
        }
    };
    protected Page a;
    public boolean b = true;
    /* access modifiers changed from: private */
    public ctl c;
    private String d = "";
    private String e = "";
    private String f = "";
    private long g;
    /* access modifiers changed from: private */
    public long h;
    private long i;
    private long j;
    /* access modifiers changed from: private */
    public long k;
    private long l;
    private long m;
    /* access modifiers changed from: private */
    public boolean n;
    /* access modifiers changed from: private */
    public boolean o;
    private boolean p = true;
    private boolean q = false;
    private DestNaviSystemKeyCode r;
    private DestNaviSystemScreenCode s;
    /* access modifiers changed from: private */
    public IPageStateListener t = null;
    private float u;
    /* access modifiers changed from: private */
    public double v;
    private boolean w;
    private float x = 0.0f;
    /* access modifiers changed from: private */
    public Map<Long, Float> y = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    public boolean z = false;

    static class DestNaviSystemKeyCode extends AmapBroadcastReceiver<AjxFootNaviPresenter> {
        public DestNaviSystemKeyCode(AjxFootNaviPresenter ajxFootNaviPresenter) {
            super(ajxFootNaviPresenter);
        }

        public void onReceive(Context context, Intent intent) {
            if (((AjxFootNaviPresenter) a()) != null) {
                String action = intent.getAction();
                if (action != null) {
                    char c = 65535;
                    int hashCode = action.hashCode();
                    if (hashCode != -1940635523) {
                        if (hashCode != -1676458352) {
                            if (hashCode != 545516589) {
                                if (hashCode == 2070024785 && action.equals("android.media.RINGER_MODE_CHANGED")) {
                                    c = 2;
                                }
                            } else if (action.equals("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED")) {
                                c = 0;
                            }
                        } else if (action.equals("android.intent.action.HEADSET_PLUG")) {
                            c = 1;
                        }
                    } else if (action.equals("android.media.VOLUME_CHANGED_ACTION")) {
                        c = 3;
                    }
                    switch (c) {
                        case 0:
                            return;
                        case 1:
                            if (intent.hasExtra("state")) {
                                if (intent.getIntExtra("state", 2) != 0) {
                                    if (intent.getIntExtra("state", 2) == 1) {
                                        ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.route_foot_navi_headset_plug_in));
                                        break;
                                    }
                                } else if (!isInitialStickyBroadcast()) {
                                    ToastHelper.showToast(AMapPageUtil.getAppContext().getString(R.string.route_foot_navi_headset_plug_out));
                                    return;
                                }
                            }
                            break;
                    }
                }
            }
        }
    }

    static class DestNaviSystemScreenCode extends AmapBroadcastReceiver<AjxFootNaviPresenter> {
        public DestNaviSystemScreenCode(AjxFootNaviPresenter ajxFootNaviPresenter) {
            super(ajxFootNaviPresenter);
        }

        /* JADX WARNING: Code restructure failed: missing block: B:37:0x007d, code lost:
            if (r9.equals("homekey") == false) goto L_0x0094;
         */
        /* JADX WARNING: Removed duplicated region for block: B:28:0x005b  */
        /* JADX WARNING: Removed duplicated region for block: B:46:0x0098  */
        /* JADX WARNING: Removed duplicated region for block: B:51:0x00b2  */
        /* JADX WARNING: Removed duplicated region for block: B:53:0x00b6 A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:54:0x00b7  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onReceive(android.content.Context r8, android.content.Intent r9) {
            /*
                r7 = this;
                java.lang.Object r8 = r7.a()
                com.autonavi.minimap.route.foot.presenter.AjxFootNaviPresenter r8 = (com.autonavi.minimap.route.foot.presenter.AjxFootNaviPresenter) r8
                if (r8 != 0) goto L_0x0009
                return
            L_0x0009:
                java.lang.String r0 = r9.getAction()
                if (r0 != 0) goto L_0x0010
                return
            L_0x0010:
                int r1 = r0.hashCode()
                r2 = -2128145023(0xffffffff81271581, float:-3.0688484E-38)
                r3 = 0
                r4 = 1
                r5 = 2
                r6 = -1
                if (r1 == r2) goto L_0x004b
                r2 = -1454123155(0xffffffffa953d76d, float:-4.7038264E-14)
                if (r1 == r2) goto L_0x0041
                r2 = -403228793(0xffffffffe7f73787, float:-2.3348976E24)
                if (r1 == r2) goto L_0x0037
                r2 = 823795052(0x311a1d6c, float:2.2426674E-9)
                if (r1 == r2) goto L_0x002d
                goto L_0x0055
            L_0x002d:
                java.lang.String r1 = "android.intent.action.USER_PRESENT"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0055
                r0 = 2
                goto L_0x0056
            L_0x0037:
                java.lang.String r1 = "android.intent.action.CLOSE_SYSTEM_DIALOGS"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0055
                r0 = 3
                goto L_0x0056
            L_0x0041:
                java.lang.String r1 = "android.intent.action.SCREEN_ON"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0055
                r0 = 1
                goto L_0x0056
            L_0x004b:
                java.lang.String r1 = "android.intent.action.SCREEN_OFF"
                boolean r0 = r0.equals(r1)
                if (r0 == 0) goto L_0x0055
                r0 = 0
                goto L_0x0056
            L_0x0055:
                r0 = -1
            L_0x0056:
                switch(r0) {
                    case 0: goto L_0x00b7;
                    case 1: goto L_0x00b6;
                    case 2: goto L_0x00b2;
                    case 3: goto L_0x005b;
                    default: goto L_0x0059;
                }
            L_0x0059:
                goto L_0x00d1
            L_0x005b:
                java.lang.String r0 = "reason"
                java.lang.String r9 = r9.getStringExtra(r0)
                if (r9 == 0) goto L_0x00d1
                int r0 = r9.hashCode()
                r1 = 3327275(0x32c52b, float:4.662505E-39)
                if (r0 == r1) goto L_0x008a
                r1 = 350448461(0x14e36b4d, float:2.2963465E-26)
                if (r0 == r1) goto L_0x0080
                r1 = 1092716832(0x41218920, float:10.095978)
                if (r0 == r1) goto L_0x0077
                goto L_0x0094
            L_0x0077:
                java.lang.String r0 = "homekey"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0094
                goto L_0x0095
            L_0x0080:
                java.lang.String r0 = "recentapps"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0094
                r3 = 1
                goto L_0x0095
            L_0x008a:
                java.lang.String r0 = "lock"
                boolean r9 = r9.equals(r0)
                if (r9 == 0) goto L_0x0094
                r3 = 2
                goto L_0x0095
            L_0x0094:
                r3 = -1
            L_0x0095:
                if (r3 == 0) goto L_0x0098
                goto L_0x00d1
            L_0x0098:
                com.autonavi.minimap.route.foot.presenter.AjxFootNaviPresenter.b(r8)
                boolean r9 = r8.o
                if (r9 != 0) goto L_0x00d1
                boolean r9 = r8.n
                if (r9 != 0) goto L_0x00d1
                r8.o = true
                long r0 = java.lang.System.currentTimeMillis()
                r8.h = r0
                goto L_0x00d1
            L_0x00b2:
                r8.g()
                return
            L_0x00b6:
                return
            L_0x00b7:
                com.autonavi.minimap.route.foot.presenter.AjxFootNaviPresenter.b(r8)
                boolean r9 = r8.o
                if (r9 != 0) goto L_0x00d1
                boolean r9 = r8.n
                if (r9 != 0) goto L_0x00d1
                r8.n = true
                long r0 = java.lang.System.currentTimeMillis()
                r8.k = r0
                return
            L_0x00d1:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.route.foot.presenter.AjxFootNaviPresenter.DestNaviSystemScreenCode.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    public AjxFootNaviPresenter(Page page) {
        super(page);
        this.a = page;
    }

    public final void onPageCreated() {
        super.onPageCreated();
        PageBundle arguments = this.a.getArguments();
        if (arguments != null) {
            this.d = arguments.getString("bundle_key_obj_result");
            this.e = arguments.getString("weather");
        }
        if (this.a != null) {
            Page page = this.a;
            eca.a().b = new a() {
                public final void a(int i) {
                    AjxFootNaviPage.this.b(i);
                    AjxFootNaviPage.this.a(i);
                }
            };
        }
        this.m = System.currentTimeMillis();
        this.r = new DestNaviSystemKeyCode(this);
        this.w = j();
        this.D = new HandlerThread("checkChange");
        this.D.start();
        this.E = new Handler(this.D.getLooper());
        if (this.w) {
            anf.a();
            anf.a((LocListener) this, 0);
        }
        this.t = AMapPageUtil.getPageStateListener(this.a);
        this.c = (ctl) a.a.a(ctl.class);
        AMapPageUtil.setPageStateListener(this.a, new IPageStateListener() {
            public final void onCover() {
                if (AjxFootNaviPresenter.this.c != null) {
                    AjxFootNaviPresenter.this.c.a("12");
                }
                if (AjxFootNaviPresenter.this.t != null) {
                    AjxFootNaviPresenter.this.t.onCover();
                }
            }

            public final void onAppear() {
                if (AjxFootNaviPresenter.this.t != null) {
                    AjxFootNaviPresenter.this.t.onCover();
                }
            }
        });
        this.H = new c() {
            public final void a() {
            }

            public final void c() {
            }

            public final void b() {
                AjxFootNaviPresenter.b(AjxFootNaviPresenter.this);
            }
        };
        drm.a((a) this.H);
    }

    public final void onResume() {
        super.onResume();
        PlaySoundUtils.getInstance().setHandleInterruptEventObj(this.a);
        if (!this.q) {
            this.q = true;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.HEADSET_PLUG");
            intentFilter.addAction("android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_CONNECTED");
            intentFilter.addAction("android.bluetooth.device.action.ACL_DISCONNECTED");
            intentFilter.addAction("android.media.RINGER_MODE_CHANGED");
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            this.a.getActivity().registerReceiver(this.r, intentFilter);
        }
        this.s = new DestNaviSystemScreenCode(this);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.SCREEN_OFF");
        intentFilter2.addAction("android.intent.action.SCREEN_ON");
        intentFilter2.addAction("android.intent.action.USER_PRESENT");
        intentFilter2.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        this.a.getActivity().registerReceiver(this.s, intentFilter2);
        f();
        eau.a("performance-", "AjxFootNaviPage  onResume");
    }

    public final void onStart() {
        super.onStart();
        LocationInstrument.getInstance().subscribe(this.a.getContext(), LOCATION_SCENE.TYPE_FOOT_NAVIGATION);
    }

    public final void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        this.a.setArguments(pageBundle);
    }

    public final void onStop() {
        super.onStop();
        LocationInstrument.getInstance().unsubscribe(this.a.getContext());
        if (drl.a().b()) {
            LocManager.setScene(0, 2, m());
        }
    }

    private static boolean j() {
        String str;
        String a2 = lo.a().a((String) "foot_navi_compass");
        if (!TextUtils.isEmpty(a2)) {
            try {
                str = new JSONObject(a2).getString("is_compass_open");
            } catch (Exception unused) {
            }
            return "1".equals(str);
        }
        str = null;
        return "1".equals(str);
    }

    public final void d() {
        AMapPageUtil.removePageStateListener(this.a);
        AMapPageFramework.removeActivityStateListener(this.a);
        if (this.q) {
            this.q = false;
            try {
                this.a.getActivity().unregisterReceiver(this.r);
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
        try {
            if (this.s != null) {
                this.a.getActivity().unregisterReceiver(this.s);
                this.s = null;
            }
        } catch (Exception e3) {
            kf.a((Throwable) e3);
        }
        PlaySoundUtils.getInstance().setHandleInterruptEventObj(null);
        eac.a().a(2);
        anf.a((LocListener) this);
        if (this.D != null) {
            l();
            if (VERSION.SDK_INT >= 18) {
                this.D.quitSafely();
            } else {
                this.D.quit();
            }
            this.D = null;
        }
        if (this.a != null) {
            AjxFootNaviPage.c();
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        if (this.c != null) {
            this.c.a("12");
        }
        d();
        h();
        eca.a().a(1);
        if (this.G != null) {
            eda eda = this.G;
            eda.a.a = null;
            eda.a.b();
            eda.b = false;
        }
    }

    public final void onResult(int i2, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i2, resultType, pageBundle);
        if (i2 == 1 && resultType == ResultType.OK && pageBundle != null) {
            Object obj = pageBundle.get(ModuleHistory.AJX_BACK_RETURN_DATA_KEY);
            if (obj instanceof String) {
                String str = (String) obj;
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    a(jSONObject.getDouble(LocationParams.PARA_FLP_AUTONAVI_LON), jSONObject.getDouble("lat"));
                } catch (Exception unused) {
                }
                if (this.a != null) {
                    Page page = this.a;
                    avl avl = (avl) a.a.a(avl.class);
                    if (avl != null) {
                        avl.b().b((AmapAjxViewInterface) page.mAjxView, str);
                    }
                }
            }
        }
    }

    private String a(double d2, double d3) {
        JSONObject jSONObject = new JSONObject();
        try {
            int ordinal = ErrorReportType.ErrorTypeDefault.ordinal();
            int ordinal2 = RouteType.ONFOOT.ordinal();
            jSONObject.put("type", ordinal);
            jSONObject.put("route_type", ordinal2);
            jSONObject.put("zoom_level", 17);
            jSONObject.put("errorPageType", 1);
            jSONObject.put("routeID", this.f);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(LocationParams.PARA_FLP_AUTONAVI_LON, d2);
            jSONObject2.put("lat", d3);
            jSONObject.put("coor2d", jSONObject2);
        } catch (Exception unused) {
        }
        this.F = jSONObject.toString();
        return this.F;
    }

    public final String a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                this.f = new JSONObject(str).optString("routeID", "0");
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(this.F)) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            if (latestPosition != null) {
                return a(latestPosition.getLongitude(), latestPosition.getLatitude());
            }
        }
        return this.F;
    }

    public final String e() {
        boolean z2 = true;
        boolean a2 = edr.a((String) "foot_isindicatorhide", true);
        String a3 = edr.a((String) "foot_navi_mode_880", (String) "");
        if (TextUtils.isEmpty(a3)) {
            a3 = "1";
        }
        try {
            JSONObject jSONObject = new JSONObject(this.d);
            jSONObject.put("tabState", a2);
            jSONObject.put("directMode", a3);
            jSONObject.put("ttsType", edb.a());
            if (!TextUtils.isEmpty(this.e)) {
                jSONObject.put("weather", this.e);
            }
            jSONObject.put(ConfigerHelper.AOS_URL_KEY, ConfigerHelper.getInstance().getKeyValue(ConfigerHelper.AOS_URL_KEY));
            Context appContext = AMapPageUtil.getAppContext();
            if (appContext != null) {
                AudioManager audioManager = (AudioManager) appContext.getSystemService("audio");
                if (audioManager != null) {
                    if (audioManager.getStreamVolume(3) < audioManager.getStreamMaxVolume(3) / 3) {
                        jSONObject.put("isLowVoiceVolume", z2);
                        jSONObject.put("switchtodrive", ebm.e());
                        jSONObject.put("voiceName", ebm.d());
                        return JsonUtil.toString(jSONObject);
                    }
                }
            }
            z2 = false;
            jSONObject.put("isLowVoiceVolume", z2);
            jSONObject.put("switchtodrive", ebm.e());
            jSONObject.put("voiceName", ebm.d());
            return JsonUtil.toString(jSONObject);
        } catch (Exception e2) {
            e2.printStackTrace();
            return this.d;
        }
    }

    public final void a(boolean z2) {
        if (this.a != null && this.a.isAlive()) {
            Page page = this.a;
            avl avl = (avl) a.a.a(avl.class);
            if (avl != null) {
                avl.b().a((AmapAjxViewInterface) page.mAjxView, z2);
            }
        }
    }

    public final void updateNaviInfo(LocInfo locInfo) {
        this.u = locInfo.roadCourse;
        this.v = locInfo.CompassCourse;
        if (this.a.isResumed() && this.b) {
            this.x = (float) this.v;
            this.b = false;
            this.z = false;
            this.A = false;
            this.E.post(this.J);
        }
    }

    public final void a() {
        dys.a((String) "P00031", (String) "D004", (String) "location");
        k();
    }

    public final void b() {
        dys.a((String) "P00031", (String) "D004", (String) H5Param.MENU_ICON);
        k();
    }

    private void k() {
        if (this.w) {
            if (this.a.isResumed()) {
                this.a.a((float) this.v, this.u);
            } else {
                this.b = true;
            }
            l();
        }
    }

    public static void a(String str, String str2) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(TrafficUtil.KEYWORD, str2);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00275", str, jSONObject);
    }

    public final String a(long j2) {
        String str = "";
        if (j2 < 0) {
            return str;
        }
        long currentTimeMillis = System.currentTimeMillis() - this.m;
        if (currentTimeMillis <= 0) {
            return str;
        }
        double d2 = (((double) j2) / ((double) currentTimeMillis)) * 100.0d;
        if (d2 >= 0.0d && d2 <= 25.0d) {
            str = "[0,0.25]";
        } else if (d2 > 25.0d && d2 <= 50.0d) {
            str = "(0.25,0.5]";
        } else if (d2 > 50.0d && d2 <= 75.0d) {
            str = "(0.5,0.75]";
        } else if (d2 > 75.0d && d2 <= 100.0d) {
            str = "(0.75,1]";
        }
        return str;
    }

    public final long f() {
        if (this.o && !this.n) {
            this.i = System.currentTimeMillis();
            if (this.i > this.h) {
                this.g = (this.i - this.h) + this.g;
            }
            this.o = false;
        }
        return this.g;
    }

    public final long g() {
        if (this.n && !this.o) {
            this.l = System.currentTimeMillis();
            if (this.l > this.k) {
                this.j = (this.l - this.k) + this.j;
            }
            this.n = false;
        }
        return this.j;
    }

    private void l() {
        this.z = true;
        this.A = false;
    }

    public final void h() {
        if (this.H != null) {
            drm.b((a) this.H);
            this.H = null;
        }
    }

    public final void i() {
        LocManager.setScene(2, 2, m());
        if (this.G == null) {
            this.G = new eda(this.a.getContext(), this);
        }
        if (this.G != null && !this.G.b) {
            eda eda = this.G;
            eda.a.a = eda;
            eda.a.a();
            eda.b = true;
        }
    }

    private static int m() {
        try {
            int parseInt = Integer.parseInt(edr.a((String) "foot_navi_mode_880", (String) "1"));
            eau.a("song---", "setDirectiveToScene param = ".concat(String.valueOf(parseInt)));
            if (parseInt == 3) {
                parseInt = 0;
            }
            return parseInt;
        } catch (Exception e2) {
            e2.printStackTrace();
            return 1;
        }
    }

    public final void c() {
        if (this.B) {
            this.c.a("12", new Callback<ctm>() {
                public void error(Throwable th, boolean z) {
                }

                public void callback(ctm ctm) {
                    if (ctm != null && AjxFootNaviPresenter.this.a != null && AjxFootNaviPresenter.this.a.isStarted()) {
                        AjxFootNaviPresenter.this.B = false;
                        AjxFootNaviPresenter.this.c.a(AjxFootNaviPresenter.this.a, "12", ctm.c);
                    }
                }
            });
        }
    }

    static /* synthetic */ void b(AjxFootNaviPresenter ajxFootNaviPresenter) {
        if (ajxFootNaviPresenter.a != null && ajxFootNaviPresenter.a.isAlive()) {
            if (ajxFootNaviPresenter.p && ajxFootNaviPresenter.a.a && Tts.getInstance().JniIsPlaying() != 1) {
                PlaySoundUtils.getInstance().playSound(ajxFootNaviPresenter.a.getString(R.string.route_navi_continue_navi_text));
            }
            ajxFootNaviPresenter.p = false;
        }
    }

    static /* synthetic */ boolean a(AjxFootNaviPresenter ajxFootNaviPresenter, float f2) {
        float abs = Math.abs(f2 - ajxFootNaviPresenter.x);
        ajxFootNaviPresenter.x = f2;
        float f3 = 0.0f;
        if (abs != 0.0f) {
            if (abs > 45.0f) {
                abs = 10.0f;
            }
            ajxFootNaviPresenter.y.put(Long.valueOf(System.currentTimeMillis()), Float.valueOf(abs));
            Map<Long, Float> map = ajxFootNaviPresenter.y;
            long currentTimeMillis = System.currentTimeMillis();
            ajxFootNaviPresenter.C.clear();
            for (Long next : map.keySet()) {
                if (currentTimeMillis - next.longValue() > BalloonLayout.DEFAULT_DISPLAY_DURATION) {
                    ajxFootNaviPresenter.C.add(next);
                }
            }
            for (Long remove : ajxFootNaviPresenter.C) {
                map.remove(remove);
            }
            for (Entry<Long, Float> value : map.entrySet()) {
                f3 += ((Float) value.getValue()).floatValue();
                if (f3 > 120.0f) {
                    return true;
                }
            }
        }
        return false;
    }
}
