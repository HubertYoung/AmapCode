package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.inputmethod.InputMethodManager;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.jni.route.health.TraceStatistics;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.order.param.BikeOrderDetailRequest;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory;
import com.autonavi.minimap.route.sharebike.model.BaseNetResult;
import com.autonavi.minimap.route.sharebike.model.OrderDetail;
import com.autonavi.minimap.route.sharebike.model.RideState;
import com.autonavi.minimap.route.sharebike.net.request.BaseRequest.a;
import com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage;
import com.autonavi.minimap.route.sharebike.page.ShareRidingMapPage;
import com.autonavi.minimap.statusbar.StatusBarManager;
import com.autonavi.minimap.statusbar.StatusBarManager.FeatureType;
import com.autonavi.widget.ui.AlertView;

/* renamed from: ehz reason: default package */
/* compiled from: ShareBikeDialogPolicy */
public final class ehz extends ehw {
    public static ehz c;
    public HandlerThread a;
    public Handler b;
    /* access modifiers changed from: private */
    public IViewLayer d = null;
    private SharedPreferences e;
    private Editor f;
    private Context g;
    /* access modifiers changed from: private */
    public RideState h;
    /* access modifiers changed from: private */
    public TraceStatistics i = null;
    /* access modifiers changed from: private */
    public RideTraceHistory j = null;
    private String k;
    private String l;
    private MapSharePreference m;
    private String n;
    private String o;
    /* access modifiers changed from: private */
    public boolean p = false;
    private volatile boolean q = false;
    private Runnable r = new Runnable() {
        public final void run() {
            char c = Boolean.parseBoolean(ehs.b("share_bike_unlocking_status_id")) ? (char) 2 : 0;
            if (ehz.this.h != null && ehz.this.h.bikeId == null && c == 2) {
                StringBuilder sb = new StringBuilder("No real status=");
                sb.append(ehz.this.h.status);
                eao.d("wbsww", sb.toString());
            } else if (ehz.this.h == null || ehz.this.h.status != 3) {
                if (!ehz.this.e()) {
                    if (ehz.this.h == null || ehz.this.h.orderId == null || "".equals(ehz.this.h.orderId.trim())) {
                        StringBuilder sb2 = new StringBuilder("sp bike=");
                        sb2.append(ehs.b("share_bike_order_id"));
                        eao.d("wbsww", sb2.toString());
                        ehz.a(ehz.this, ehs.b("share_bike_cp_source"), ehs.b("share_bike_order_id"));
                    } else {
                        StringBuilder sb3 = new StringBuilder("bike=");
                        sb3.append(ehz.this.h.orderId);
                        eao.d("wbsww", sb3.toString());
                        ehz.a(ehz.this, ehs.b("share_bike_cp_source"), ehz.this.h.orderId);
                    }
                }
            } else {
                ehz.this.a(true);
                eao.d("wbsww", "Dont show dialog from 2 to 3");
            }
        }
    };
    private a s = new a() {
        public final void onClick(AlertView alertView, int i) {
            bid pageContext = AMapPageFramework.getPageContext();
            if (pageContext != null) {
                pageContext.dismissViewLayer(ehz.this.d);
                ehz.this.i = null;
                ehz.this.p = false;
                ehz.this.j = null;
            }
        }
    };

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x00e9, code lost:
        r1 = 2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ehz() {
        /*
            r7 = this;
            r7.<init>()
            r0 = 0
            r7.d = r0
            r7.i = r0
            r7.j = r0
            r0 = 0
            r7.p = r0
            r7.q = r0
            ehz$2 r1 = new ehz$2
            r1.<init>()
            r7.r = r1
            ehz$7 r1 = new ehz$7
            r1.<init>()
            r7.s = r1
            java.lang.String r1 = "wbsww"
            java.lang.String r2 = "init dialog"
            defpackage.eao.d(r1, r2)
            android.content.Context r1 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework.getAppContext()
            r7.g = r1
            com.amap.bundle.mapstorage.MapSharePreference r1 = new com.amap.bundle.mapstorage.MapSharePreference
            com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r2 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.SharedPreferences
            r1.<init>(r2)
            r7.m = r1
            android.content.Context r1 = r7.g
            int r2 = com.autonavi.minimap.R.string.dialog_prefix_text
            java.lang.String r1 = r1.getString(r2)
            r7.k = r1
            android.content.Context r1 = r7.g
            int r2 = com.autonavi.minimap.R.string.dialog_suffix_text
            java.lang.String r1 = r1.getString(r2)
            r7.n = r1
            android.content.Context r1 = r7.g
            int r2 = com.autonavi.minimap.R.string.dialog_button_text
            java.lang.String r1 = r1.getString(r2)
            r7.o = r1
            android.content.Context r1 = r7.g
            int r2 = com.autonavi.minimap.R.string.share_bike_busniess_name
            java.lang.String r1 = r1.getString(r2)
            r7.l = r1
            android.content.Context r1 = r7.g
            java.lang.String r2 = "dialog"
            android.content.SharedPreferences r1 = r1.getSharedPreferences(r2, r0)
            r7.e = r1
            ehj r1 = defpackage.ehj.a()
            ehz$1 r2 = new ehz$1
            r2.<init>()
            java.util.Vector<ehj$a> r1 = r1.a
            r1.add(r2)
            android.content.SharedPreferences r1 = r7.e
            android.content.SharedPreferences$Editor r1 = r1.edit()
            r7.f = r1
            android.os.HandlerThread r1 = new android.os.HandlerThread
            java.lang.Class r2 = r7.getClass()
            java.lang.String r2 = r2.getName()
            r1.<init>(r2)
            r7.a = r1
            android.os.HandlerThread r1 = r7.a
            r1.start()
            android.os.Handler r1 = new android.os.Handler
            android.os.HandlerThread r2 = r7.a
            android.os.Looper r2 = r2.getLooper()
            r1.<init>(r2)
            r7.b = r1
            java.lang.String r1 = ""
            java.lang.String r2 = "share_bike_riding_status_id"
            java.lang.String r2 = defpackage.ehs.b(r2)
            java.lang.String r2 = r2.trim()
            boolean r1 = r1.equals(r2)
            if (r1 != 0) goto L_0x00f4
            java.lang.String r1 = "share_bike_riding_status_id"
            java.lang.String r1 = defpackage.ehs.b(r1)
            boolean r1 = java.lang.Boolean.parseBoolean(r1)
            java.lang.String r2 = "share_bike_unlocking_status_id"
            java.lang.String r2 = defpackage.ehs.b(r2)
            boolean r2 = java.lang.Boolean.parseBoolean(r2)
            r3 = 2
            if (r2 == 0) goto L_0x00c8
            r2 = 2
            goto L_0x00c9
        L_0x00c8:
            r2 = 0
        L_0x00c9:
            java.lang.String r4 = "wbsww"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Riding status is "
            r5.<init>(r6)
            r5.append(r1)
            java.lang.String r6 = ", Unlocking status is "
            r5.append(r6)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            defpackage.eao.d(r4, r5)
            if (r1 != 0) goto L_0x00ea
            if (r2 != r3) goto L_0x00ea
            r1 = 2
        L_0x00ea:
            if (r1 != 0) goto L_0x00f0
            r7.d()
            goto L_0x00f8
        L_0x00f0:
            r7.a(r0)
            goto L_0x00f8
        L_0x00f4:
            r0 = 1
            r7.a(r0)
        L_0x00f8:
            c = r7
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ehz.<init>():void");
    }

    private void d() {
        StringBuilder sb = new StringBuilder("Is there any need to show dialog ? ");
        sb.append(e() ? "No" : "Yes");
        eao.d("wbsww", sb.toString());
        this.b.post(this.r);
    }

    /* access modifiers changed from: private */
    public boolean e() {
        try {
            return Boolean.parseBoolean(this.e.getString("need_show", "true"));
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public static void a(int i2) {
        eao.d("wbsww", "page changed value=".concat(String.valueOf(i2)));
        if (c != null) {
            RideState rideState = new RideState();
            rideState.bikeId = null;
            rideState.status = i2;
            c.a(rideState);
        }
    }

    public final void a(boolean z) {
        StringBuilder sb = new StringBuilder("CHANGE SHOW BEFORE=");
        sb.append(z);
        sb.append("mLock=");
        sb.append(this.q);
        eao.d("wbsww", sb.toString());
        this.f.putString("need_show", String.valueOf(z));
        this.f.apply();
    }

    public final void a(RideState rideState) {
        StringBuilder sb = new StringBuilder("Dialog state=");
        sb.append(rideState != null ? rideState.status : 0);
        eao.d("wbsww", sb.toString());
        this.h = rideState;
        if (rideState == null || rideState.status != 0) {
            if (rideState != null && (rideState.status == 1 || rideState.status == 2)) {
                a(false);
            }
            return;
        }
        d();
    }

    /* access modifiers changed from: protected */
    public final boolean b() {
        boolean z = false;
        try {
            dfm dfm = (dfm) ank.a(dfm.class);
            StringBuilder sb = new StringBuilder("FootNaviStateTunnel.getFootNaviState()=");
            sb.append(egc.a());
            sb.append("drive navi=");
            sb.append(dfm != null ? dfm.b() : false);
            eao.d("wbsww", sb.toString());
            if ((dfm != null && dfm.b()) || egc.a()) {
                z = true;
            }
            return z;
        } catch (Throwable th) {
            AMapLog.e(ehw.class.getName(), "Happen error during get status of drive navigation");
            th.printStackTrace();
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public final boolean a(String str) {
        aww aww = (aww) a.a.a(aww.class);
        String str2 = null;
        Object simpleName = aww != null ? aww.a().a(1).getSimpleName() : null;
        avi avi = (avi) a.a.a(avi.class);
        Object simpleName2 = avi != null ? avi.c().a(1).getSimpleName() : null;
        avo avo = (avo) a.a.a(avo.class);
        if (avo != null) {
            str2 = avo.a().a(1).getSimpleName();
        }
        if (str.equals(simpleName2) || str.equals(ShareRidingMapPage.class.getSimpleName()) || str.equals(str2) || str.equals(simpleName) || str.equals(NodeAlertDialogPage.class.getSimpleName())) {
            return true;
        }
        return false;
    }

    private boolean f() {
        char c2 = Boolean.parseBoolean(ehs.b("share_bike_unlocking_status_id")) ? (char) 2 : 0;
        if (this.i == null && !this.p && c2 != 2) {
            return false;
        }
        return true;
    }

    static /* synthetic */ void a(ehz ehz, String str, String str2) {
        StringBuilder sb = new StringBuilder("cpSource=");
        sb.append(str);
        sb.append("bike=");
        sb.append(str2);
        sb.append("getTrackRecordData()=");
        sb.append(ehz.f());
        eao.d("wbsww", sb.toString());
        Class<?> topPageClass = AMapPageUtil.getTopPageClass();
        if (topPageClass == null || ehz.a(topPageClass.getSimpleName()) || ehz.b() || !ehz.f()) {
            aww aww = (aww) a.a.a(aww.class);
            String str3 = null;
            Object simpleName = aww != null ? aww.a().a(1).getSimpleName() : null;
            avi avi = (avi) a.a.a(avi.class);
            Object simpleName2 = avi != null ? avi.c().a(1).getSimpleName() : null;
            avo avo = (avo) a.a.a(avo.class);
            if (avo != null) {
                str3 = avo.a().a(1).getSimpleName();
            }
            if (topPageClass == null || (!topPageClass.getSimpleName().equals(simpleName2) && !topPageClass.getSimpleName().equals(ShareRidingMapPage.class.getSimpleName()) && !topPageClass.getSimpleName().equals(str3) && !topPageClass.getSimpleName().equals(simpleName) && !topPageClass.getSimpleName().equals(ShareRidingFinishPage.class.getSimpleName()) && !ehz.b())) {
                ehz.a(false);
                ehz.b.post(ehz.r);
                return;
            }
            ehz.a(true);
            return;
        }
        BikeOrderDetailRequest bikeOrderDetailRequest = new BikeOrderDetailRequest();
        bikeOrderDetailRequest.b = str;
        bikeOrderDetailRequest.c = str2;
        egu.a(bikeOrderDetailRequest, (a) new a() {
            public final void a(BaseNetResult baseNetResult) {
                if (baseNetResult != null && baseNetResult.result) {
                    OrderDetail orderDetail = (OrderDetail) baseNetResult;
                    StringBuilder sb = new StringBuilder("OrderDetail ");
                    sb.append(orderDetail.status);
                    sb.append(", ");
                    sb.append(orderDetail.isPay);
                    eao.a((String) "sbdp", sb.toString());
                    int i = -1;
                    try {
                        i = Integer.parseInt(orderDetail.status);
                    } catch (NumberFormatException unused) {
                    }
                    if (i == 4 || i == 6 || i == 3) {
                        if (ehz.a()) {
                            StatusBarManager.d().a(FeatureType.TYPE_BICYCLE);
                            eht.e();
                            ehz.c(ehz.this);
                            ehz.c();
                        } else if (orderDetail.isPay == null || !"1".equals(orderDetail.isPay.trim())) {
                            ehz.b(ehz.this, false);
                        } else {
                            ehz.b(ehz.this, true);
                        }
                    }
                }
            }
        });
    }

    static /* synthetic */ boolean a() {
        if (AMapPageFramework.getPageContext() instanceof Ajx3Page) {
            String ajx3Url = ((Ajx3Page) AMapPageFramework.getPageContext()).getAjx3Url();
            if (ajx3Url != null && ajx3Url.endsWith("ShareBikeScanResult.page.js")) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x00b6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void c(defpackage.ehz r10) {
        /*
            r0 = -1
            java.lang.String r2 = "share_bike_start_time_id"
            java.lang.String r2 = defpackage.ehs.b(r2)     // Catch:{ Exception -> 0x0019 }
            long r2 = java.lang.Long.parseLong(r2)     // Catch:{ Exception -> 0x0019 }
            java.lang.String r4 = "share_bike_end_time_id"
            java.lang.String r4 = defpackage.ehs.b(r4)     // Catch:{ Exception -> 0x0017 }
            long r4 = java.lang.Long.parseLong(r4)     // Catch:{ Exception -> 0x0017 }
            goto L_0x001f
        L_0x0017:
            r4 = move-exception
            goto L_0x001b
        L_0x0019:
            r4 = move-exception
            r2 = r0
        L_0x001b:
            r4.printStackTrace()
            r4 = r0
        L_0x001f:
            java.lang.String r6 = "wbsww"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "startime="
            r7.<init>(r8)
            r7.append(r2)
            java.lang.String r8 = "endtime="
            r7.append(r8)
            r7.append(r4)
            java.lang.String r8 = "trackrecord ="
            r7.append(r8)
            com.autonavi.jni.route.health.TraceStatistics r8 = r10.i
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            defpackage.eao.d(r6, r7)
            bid r6 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework.getPageContext()
            if (r6 == 0) goto L_0x00b6
            com.autonavi.common.PageBundle r7 = new com.autonavi.common.PageBundle
            r7.<init>()
            java.lang.String r8 = "share_bike_order_id"
            java.lang.String r8 = defpackage.ehs.b(r8)
            com.autonavi.jni.route.health.TraceStatistics r9 = r10.i
            if (r9 == 0) goto L_0x0093
            int r9 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r9 == 0) goto L_0x0093
            int r0 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
            if (r0 == 0) goto L_0x0093
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r0 = r10.j
            if (r0 != 0) goto L_0x0082
            eaa r1 = defpackage.eaa.a()
            eab r1 = r1.b(r8)
            if (r1 == 0) goto L_0x0082
            com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getAppContext()
            bsp r0 = defpackage.bsp.a()
            java.lang.String r1 = r1.c
            bte r0 = r0.a(r1)
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r0 = defpackage.ees.a(r0)
        L_0x0082:
            if (r0 != 0) goto L_0x008e
            com.autonavi.jni.route.health.TraceStatistics r1 = r10.i
            if (r1 == 0) goto L_0x008e
            com.autonavi.jni.route.health.TraceStatistics r0 = r10.i
            com.autonavi.minimap.route.ride.beans.RideTraceHistory r0 = defpackage.ees.a(r0, r2, r4)
        L_0x008e:
            java.lang.String r1 = "data"
            r7.putObject(r1, r0)
        L_0x0093:
            java.lang.String r0 = "isfromRidePage"
            r1 = 1
            r7.putBoolean(r0, r1)
            java.lang.String r0 = "where"
            r2 = 2
            r7.putInt(r0, r2)
            java.lang.String r0 = "bundle_is_request_coin"
            r7.putBoolean(r0, r1)
            r6.dismissAllViewLayers()
            r0 = 0
            r10.i = r0
            r1 = 0
            r10.p = r1
            r10.j = r0
            java.lang.Class<com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage> r10 = com.autonavi.minimap.route.sharebike.page.ShareRidingFinishPage.class
            r6.startPage(r10, r7)
            return
        L_0x00b6:
            java.lang.String r10 = "wbsww"
            java.lang.String r0 = "Page context cannot be got , framework exception"
            defpackage.eao.d(r10, r0)
            bid r10 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageFramework.getPageContext()
            if (r10 == 0) goto L_0x00c7
            r10.dismissAllViewLayers()
        L_0x00c7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ehz.c(ehz):void");
    }

    static /* synthetic */ void c() {
        bid pageContext = AMapPageFramework.getPageContext();
        if (pageContext instanceof Ajx3Page) {
            pageContext.finish();
        }
    }

    static /* synthetic */ void b(ehz ehz, final boolean z) {
        eao.d("wbsww", "show dialog with ".concat(String.valueOf(z)));
        new Handler(AMapPageFramework.getAppContext().getMainLooper()).post(new Runnable() {
            public final void run() {
                ehz.c(ehz.this, z);
            }
        });
    }

    static /* synthetic */ void c(ehz ehz, boolean z) {
        Class<?> topPageClass = AMapPageUtil.getTopPageClass();
        if (topPageClass == null || !topPageClass.getSimpleName().equals(ShareRidingFinishPage.class.getSimpleName())) {
            bid pageContext = AMapPageFramework.getPageContext();
            if (pageContext != null) {
                pageContext.dismissAllViewLayers();
                StatusBarManager.d().a(FeatureType.TYPE_BICYCLE);
                eht.e();
                try {
                    String stringValue = ehz.m.getStringValue(ehs.b("share_bike_cp_source"), "");
                    if ("".equals(stringValue)) {
                        stringValue = ehz.l;
                    }
                    AlertView.a aVar = new AlertView.a(AMapPageFramework.getAppContext());
                    if (z) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(ehz.k);
                        sb.append(stringValue);
                        sb.append("\n");
                        sb.append(ehz.n);
                        aVar.b((CharSequence) sb.toString()).a((CharSequence) ehz.o, (a) new a() {
                            public final void onClick(AlertView alertView, int i) {
                                ehz.c(ehz.this);
                            }
                        }).a(true);
                        ehz.d = aVar.a();
                    } else {
                        aVar.b((CharSequence) ehz.g.getString(R.string.share_bike_need_pay_title)).a((CharSequence) ehz.g.getString(R.string.share_bike_need_pay_content), (a) new a() {
                            public final void onClick(AlertView alertView, int i) {
                                ehz.c(ehz.this);
                            }
                        }).a(true);
                        ehz.d = aVar.a();
                    }
                    if (ehz.d instanceof AlertView) {
                        ((AlertView) ehz.d).setOnBackClickListener(ehz.s);
                        ((AlertView) ehz.d).setOnOutSideClickListener(ehz.s);
                    }
                    if (!(ehz.d == null || AMapPageFramework.getAppContext() == null)) {
                        Activity topActivity = AMapAppGlobal.getTopActivity();
                        if (topActivity != null) {
                            InputMethodManager inputMethodManager = (InputMethodManager) AMapPageFramework.getAppContext().getSystemService("input_method");
                            if (inputMethodManager.isActive() && topActivity.getCurrentFocus() != null) {
                                inputMethodManager.hideSoftInputFromWindow(topActivity.getCurrentFocus().getWindowToken(), 2);
                            }
                        }
                    }
                    pageContext.showViewLayer(ehz.d);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        } else {
            ehz.i = null;
            ehz.p = false;
            ehz.j = null;
        }
        ehz.a(true);
    }
}
