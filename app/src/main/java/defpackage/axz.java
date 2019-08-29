package defpackage;

import android.content.res.Resources;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommute.bus.bean.BusCommuteJumpBean;
import com.autonavi.bundle.routecommute.bus.bean.BusCommuteJumpBean.PoiObj;
import com.autonavi.bundle.routecommute.bus.bean.BusCommuteJumpBean.SelectObj;
import com.autonavi.bundle.routecommute.bus.bean.BusCommuteTipBean;
import com.autonavi.bundle.routecommute.bus.details.BusCommuteListPage;
import com.autonavi.bundle.routecommute.bus.overlay.BusCommuteTipOverlay.a;
import com.autonavi.bundle.routecommute.common.bean.CommuteControlBean;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.gdtaojin.basemap.UiExecutor;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.jni.eyrie.amap.tbt.bus.BusServiceManager;
import com.autonavi.jni.eyrie.amap.tbt.bus.BusServiceObserver;
import com.autonavi.jni.eyrie.amap.tbt.bus.IJSONParser;
import com.autonavi.jni.eyrie.amap.tbt.bus.param.BusRealTimeRequestParam;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath.AlterBus;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath.BusSegment;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse.RealTimeBusLineInfo;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRouteResponse;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.TaxiComparatorResponse;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: axz reason: default package */
/* compiled from: BusCommuteMainPageImpl */
public final class axz extends axy implements ayf, com.autonavi.bundle.routecommute.bus.overlay.BusCommuteTipOverlay.a {
    ayj g;
    ayg h;
    boolean i = true;
    HashMap<String, String> j = new HashMap<>();
    final ayu k = new ayu();
    private ayh l;
    private boolean m;
    private boolean n;
    private boolean o = true;
    private axx p = new axx();
    private int q = 10;
    private GeoPoint r;
    private long s = 0;
    private int t;
    private final ayc u = new ayd();

    /* renamed from: axz$a */
    /* compiled from: BusCommuteMainPageImpl */
    class a implements ayk {
        private a() {
        }

        /* synthetic */ a(axz axz, byte b) {
            this();
        }

        public final void a() {
            axz.this.k.b();
            axz.this.k.c();
            axz.a((String) "B021");
        }

        public final void b() {
            azl.a((String) ShowRouteActionProcessor.SEARCH_TYPE_BUS, 12);
            axz.a((String) "B008");
        }
    }

    public final void a(AbstractBaseMapPage abstractBaseMapPage) {
        super.a(abstractBaseMapPage);
        if (this.b != null) {
            this.g = new ayj(this.b);
            ayj ayj = this.g;
            if (ayj.b != null) {
                ayj.b.setOnBusCommuteTipClickListener(new com.autonavi.bundle.routecommute.bus.overlay.BusCommuteTipOverlay.a(this) {
                    final /* synthetic */ a a;

                    {
                        this.a = r2;
                    }

                    public final void k() {
                        if (this.a != null) {
                            this.a.k();
                            ayp.a(ayp.a, ayp.f, ayj.this.f, true);
                        }
                    }

                    public final void l() {
                        if (this.a != null) {
                            this.a.l();
                            ayp.a(ayp.a, ayp.b, ayj.this.f, false);
                        }
                    }
                });
            }
            this.g.j = new a(this, 0);
            this.a = ayi.a();
            this.l = new ayh(new Runnable() {
                public final void run() {
                    axz axz = axz.this;
                    if (axz.h != null && axz.j != null && axz.j.size() > 1) {
                        axz.h.a(ayo.d);
                        POI poi = axz.c;
                        HashMap<String, String> hashMap = axz.j;
                        boolean z = axz.i;
                        azb.a("song---", "start request realtime");
                        if (poi == null || hashMap == null || hashMap.isEmpty()) {
                            azb.a("song---", "realtime map.isEmpty() return");
                        } else {
                            String str = hashMap.get(ays.e);
                            String str2 = hashMap.get(ays.d);
                            BusRealTimeRequestParam busRealTimeRequestParam = new BusRealTimeRequestParam();
                            busRealTimeRequestParam.adcode = poi.getAdCode() == null ? "" : poi.getAdCode();
                            busRealTimeRequestParam.count = "2";
                            busRealTimeRequestParam.from_page = "10";
                            busRealTimeRequestParam.lines = str;
                            busRealTimeRequestParam.stations = str2;
                            busRealTimeRequestParam.need_bus_status = "1";
                            busRealTimeRequestParam.need_bus_track = z ? "1" : "0";
                            BusServiceManager.getInstance().requestBusRealTime(ayo.d, busRealTimeRequestParam);
                        }
                        StringBuilder sb = new StringBuilder("send new request for real time bus data, mIsRTBusCloudOpen=");
                        sb.append(axz.i);
                        azb.a("BusCommuteMainPageImpl", sb.toString());
                    }
                }
            });
        }
        if (this.h == null) {
            this.h = new ayg();
            ayg ayg = this.h;
            String str = "https://m5.amap.com/";
            if (ayg.a.a) {
                str = "http://maps.testing.amap.com/";
            }
            NaviManager.setConfig(100, str);
            BusServiceManager.getInstance().setJsonParser(new IJSONParser() {
                public final String toJSONString(Object obj) {
                    return JSON.toJSONString(obj);
                }

                public final <T> T parseObject(String str, Class<T> cls) {
                    try {
                        return JSON.parseObject(str, cls);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            });
            ayg.b = new BusServiceObserver(this) {
                final /* synthetic */ ayf a;

                {
                    this.a = r2;
                }

                public final void onResult(final int i, final int i2, final String str) {
                    azb.a("song---", "Result json = ".concat(String.valueOf(str)));
                    azb.a("song---", "Result requestId = ".concat(String.valueOf(i)));
                    azb.a("song---", "Result requestType = ".concat(String.valueOf(i2)));
                    if (this.a != null) {
                        UiExecutor.post(new Runnable() {
                            public final void run() {
                                AnonymousClass1.this.a.a(i, i2, str);
                            }
                        });
                    }
                }

                public final void onError(final int i, final int i2, final int i3) {
                    azb.a("song---", "-----Result onError-callback ---");
                    UiExecutor.post(new Runnable() {
                        public final void run() {
                            if (AnonymousClass1.this.a != null) {
                                StringBuilder sb = new StringBuilder("-----Result onError-requestId = ");
                                sb.append(i);
                                azb.a("song---", sb.toString());
                                StringBuilder sb2 = new StringBuilder("-----Result onError-requestType = ");
                                sb2.append(i2);
                                azb.a("song---", sb2.toString());
                                StringBuilder sb3 = new StringBuilder("-----Result onError-errorCode = ");
                                sb3.append(i3);
                                azb.a("song---", sb3.toString());
                                AnonymousClass1.this.a.a(i, i2, i3);
                            }
                        }
                    });
                }
            };
            ayg ayg2 = this.h;
            if (ayg2.a != null) {
                azb.a("song---", "eyrie register");
                BusServiceManager.getInstance().registerBusService(ayg2.b);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0058  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00c9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(int r5, java.lang.String r6) {
        /*
            r4 = this;
            super.a(r5, r6)
            com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage r5 = r4.b
            if (r5 != 0) goto L_0x0008
            return
        L_0x0008:
            axx r5 = r4.p
            com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage r0 = r4.b
            boolean r0 = r0 instanceof com.autonavi.map.fragmentcontainer.page.MapBasePage
            r1 = 0
            if (r0 == 0) goto L_0x0020
            com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage r0 = r4.b
            com.autonavi.map.fragmentcontainer.page.MapBasePage r0 = (com.autonavi.map.fragmentcontainer.page.MapBasePage) r0
            com.autonavi.map.fragmentcontainer.page.ICQLayerController r0 = r0.getCQLayerController()
            if (r0 == 0) goto L_0x0020
            boolean r0 = r0.isShowing()
            goto L_0x0021
        L_0x0020:
            r0 = 0
        L_0x0021:
            r5.a = r0
            boolean r5 = v()
            r4.i = r5
            r5 = 1
            r4.m = r5
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r0 = defpackage.azf.b()
            int r0 = defpackage.ayl.a(r0)
            r4.t = r0
            java.lang.String r0 = "BusCommuteMainPageImpl"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "onOpenBusCommute(), tip type= "
            r2.<init>(r3)
            int r3 = r4.t
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            defpackage.azb.a(r0, r2)
            int r0 = r4.t
            if (r0 != r5) goto L_0x0058
            r4.A()
            java.lang.String r5 = "B009"
            a(r5)
            goto L_0x00ba
        L_0x0058:
            int r5 = r4.t
            r0 = 3
            if (r5 == r0) goto L_0x0074
            int r5 = r4.t
            r0 = 4
            if (r5 != r0) goto L_0x0063
            goto L_0x0074
        L_0x0063:
            java.lang.String r5 = "BusCommuteMainPageImpl"
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r2 = "Invalid tip type."
            r0.<init>(r2)
            java.lang.String r0 = r0.toString()
            defpackage.azb.a(r5, r0)
            goto L_0x00ba
        L_0x0074:
            r4.a()
            r4.b()
            java.lang.String r5 = "0"
            boolean r5 = android.text.TextUtils.equals(r6, r5)
            if (r5 == 0) goto L_0x008f
            boolean r5 = r4.o
            if (r5 == 0) goto L_0x0095
            r4.p()
            r4.r()
            r4.o = r1
            goto L_0x0095
        L_0x008f:
            r4.b(r6)
            r4.t()
        L_0x0095:
            org.json.JSONObject r5 = new org.json.JSONObject
            r5.<init>()
            java.lang.String r0 = "from"
            r5.put(r0, r6)     // Catch:{ Exception -> 0x00a0 }
            goto L_0x00a4
        L_0x00a0:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00a4:
            java.lang.String r0 = "song---"
            java.lang.String r2 = "BusCommuteMainPage from = "
            java.lang.String r3 = java.lang.String.valueOf(r6)
            java.lang.String r2 = r2.concat(r3)
            defpackage.azb.a(r0, r2)
            java.lang.String r0 = defpackage.ayp.a
            java.lang.String r2 = defpackage.ayp.c
            com.amap.bundle.statistics.LogManager.actionLogV2(r0, r2, r5)
        L_0x00ba:
            java.lang.String r5 = "3"
            boolean r5 = android.text.TextUtils.equals(r6, r5)
            if (r5 == 0) goto L_0x00c9
            defpackage.bmn.b(r1)
            r4.a(r1)
            return
        L_0x00c9:
            boolean r5 = defpackage.brj.a()
            if (r5 == 0) goto L_0x00d2
            defpackage.bmn.b()
        L_0x00d2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.axz.a(int, java.lang.String):void");
    }

    public final void d() {
        boolean z = false;
        if (!this.m || this.o) {
            this.o = false;
            return;
        }
        this.t = ayl.a(azf.b());
        StringBuilder sb = new StringBuilder("onOpenBusCommute(), tip type= ");
        sb.append(this.t);
        azb.a("BusCommuteMainPageImpl", sb.toString());
        if (this.t == 1) {
            A();
        } else if (this.t == 3 || this.t == 4) {
            int a2 = ayq.a(LocationInstrument.getInstance().getLatestPosition(), azf.c(), azf.d());
            this.e = a2;
            azb.a("song---", "setLocationType locationType = ".concat(String.valueOf(a2)));
            a();
            boolean b = b();
            azb.a("BusCommuteMainPageImpl", "checkAndSetCompanyHomePoi isChangePoi = ".concat(String.valueOf(b)));
            boolean z2 = !(this.s > 0 && System.currentTimeMillis() - this.s < TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL);
            StringBuilder sb2 = new StringBuilder("Determine type of route request, isChangePoi=");
            sb2.append(b);
            sb2.append(", BusCommuteResult == null: ");
            sb2.append(this.a == null);
            sb2.append(", getBusCommutePaths() == null: ");
            if (this.a == null || this.a.c == null) {
                z = true;
            }
            sb2.append(z);
            sb2.append(", isResponseDataExpired: ");
            sb2.append(z2);
            azb.a("BusCommuteMainPageImpl", sb2.toString());
            if (b || this.a == null || this.a.c == null || z2) {
                p();
                r();
                return;
            }
            x();
            if (this.i) {
                y();
            }
            if (u()) {
                q();
            } else {
                z();
            }
        } else {
            azb.a("BusCommuteMainPageImpl", new IllegalArgumentException("Invalid tip type.").toString());
        }
    }

    public final void e() {
        if (this.m) {
            o();
            s();
            B();
        }
    }

    public final void f() {
        if (this.m) {
            m();
        }
    }

    public final void g() {
        this.m = false;
        m();
    }

    public final void h() {
        if (this.m) {
            b(true);
            this.p.a = true;
        }
    }

    public final void i() {
        if (this.m) {
            this.p.a = false;
            if (!this.p.a()) {
                b(false);
            }
        }
    }

    public final void a(boolean z) {
        if (this.m) {
            this.p.b = z;
            if (z) {
                b(true);
                return;
            }
            if (!this.p.a()) {
                b(false);
            }
        }
    }

    public final void j() {
        if (this.g != null) {
            this.k.b();
            this.k.c();
            this.g.b();
        }
    }

    private void b(boolean z) {
        if (z) {
            B();
            o();
            return;
        }
        this.t = ayl.a(azf.b());
        StringBuilder sb = new StringBuilder("onOpenBusCommute(), tip type= ");
        sb.append(this.t);
        azb.a("BusCommuteMainPageImpl", sb.toString());
        if (this.t == 1) {
            A();
        } else if (this.t == 3 || this.t == 4) {
            x();
            z();
            if (this.i) {
                y();
            }
            q();
        } else {
            azb.a("BusCommuteMainPageImpl", new IllegalArgumentException("Invalid tip type.").toString());
        }
    }

    private void m() {
        o();
        B();
        if (this.h != null) {
            s();
            this.h.a();
            this.h = null;
        }
        n();
        this.a = null;
    }

    private void n() {
        if (this.a != null) {
            this.a.d();
        }
    }

    private void o() {
        if (this.l != null) {
            this.l.a();
        }
    }

    public final void k() {
        b((String) "0");
    }

    public final void l() {
        UiExecutor.postDelayed(new Runnable() {
            public final void run() {
                if (axz.this.g != null) {
                    axz.this.g.a(4);
                }
            }
        }, 100);
    }

    private void p() {
        if (this.h != null) {
            this.h.a(this.q);
            this.q++;
            ayg.a(this.q, this.c, this.d);
        }
    }

    private void q() {
        if (this.l != null && !this.p.a()) {
            this.l.b();
        }
    }

    private void r() {
        if (this.h != null) {
            this.h.a(ayo.e);
            ayg.a(this.c, this.d);
        }
    }

    private void s() {
        if (this.h != null) {
            this.h.b();
        }
    }

    public final void a(int i2, int i3, String str) {
        if (C()) {
            if (i2 == this.q) {
                n();
            }
            return;
        }
        if (i2 == this.q) {
            n();
            b(i3, str);
            BusRouteResponse busRouteResponse = this.a.c;
            HashMap<String, String> hashMap = null;
            boolean z = true;
            if (busRouteResponse != null) {
                ArrayList<BusPath> arrayList = busRouteResponse.buslist;
                if (arrayList != null && !arrayList.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    StringBuilder sb2 = new StringBuilder();
                    HashSet hashSet = new HashSet();
                    Iterator<BusPath> it = arrayList.iterator();
                    while (it.hasNext()) {
                        BusPath next = it.next();
                        if (next != null) {
                            GeoPoint c = ayt.c(next);
                            if (c != null) {
                                hashSet.add(c);
                                ArrayList<BusSegment> arrayList2 = next.segmentlist;
                                if (arrayList2 != null && !arrayList2.isEmpty()) {
                                    BusSegment busSegment = arrayList2.get(0);
                                    if (busSegment != null) {
                                        ArrayList<AlterBus> arrayList3 = busSegment.alterlist;
                                        if (arrayList3 != null && arrayList3.size() > 0) {
                                            for (AlterBus next2 : arrayList3) {
                                                if (!TextUtils.equals(next2.realtime, "0")) {
                                                    sb.append(next2.busid);
                                                    sb.append(",");
                                                    sb2.append(next2.startid);
                                                    sb2.append(",");
                                                }
                                            }
                                        }
                                        StringBuilder sb3 = new StringBuilder("busSegment name = ");
                                        sb3.append(busSegment.startname);
                                        azb.a("song---", sb3.toString());
                                        StringBuilder sb4 = new StringBuilder("busSegment busSegment.realtime = ");
                                        sb4.append(busSegment.realtime);
                                        azb.a("song---", sb4.toString());
                                        if (!TextUtils.equals(busSegment.realtime, "0")) {
                                            sb.append(busSegment.busid);
                                            sb.append(",");
                                            sb2.append(busSegment.startid);
                                            sb2.append(",");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (!TextUtils.isEmpty(sb) && !TextUtils.isEmpty(sb2)) {
                        StringBuilder sb5 = new StringBuilder(sb.substring(0, sb.length() - 1));
                        StringBuilder sb6 = new StringBuilder(sb2.substring(0, sb2.length() - 1));
                        azb.a("song---", "lineids = ".concat(String.valueOf(sb5)));
                        azb.a("song---", "stationIds = ".concat(String.valueOf(sb6)));
                        HashMap<String, String> hashMap2 = new HashMap<>();
                        hashMap2.put(ays.e, sb5.toString());
                        hashMap2.put(ays.d, sb6.toString());
                        hashMap = hashMap2;
                    }
                }
            }
            this.j = hashMap;
            if (this.j == null || this.j.size() <= 1) {
                z = false;
            }
            this.n = z;
            x();
            if (u()) {
                q();
            } else {
                z();
            }
            t();
        }
        if (i2 == ayo.d) {
            b(i3, str);
            if (this.i) {
                y();
            }
            if (!C() && !this.p.a() && this.g != null && this.a != null) {
                ayj ayj = this.g;
                BusRouteResponse busRouteResponse2 = this.a.c;
                BusRealTimeResponse busRealTimeResponse = this.a.e;
                if (!(busRouteResponse2 == null || busRealTimeResponse == null || busRouteResponse2.buslist == null || busRouteResponse2.buslist.size() <= 0 || ayj.d == null)) {
                    ayj.d.addBusStationDescOverlay(busRouteResponse2.buslist, busRealTimeResponse.buses);
                }
            }
            z();
            if (!(this.l == null || this.a == null || this.a.b() == null)) {
                this.l.a(this.a.b(), this.a.e);
            }
        }
        if (i2 == ayo.e) {
            b(i3, str);
        }
    }

    private void t() {
        this.s = System.currentTimeMillis();
        azb.a("BusCommuteMainPageImpl", "Saved time for last received route response.");
    }

    public final void a(int i2, int i3, int i4) {
        StringBuilder sb = new StringBuilder("onRequestError: requestId = ");
        sb.append(i2);
        sb.append(",requestType = ");
        sb.append(i3);
        sb.append(", errorCode = ");
        sb.append(i4);
        azb.a("BusCommuteMainPageImpl", sb.toString());
        if (i2 == this.q) {
            B();
            n();
        }
        if (i2 == ayo.d) {
            z();
        }
    }

    private boolean u() {
        return this.n && this.l != null;
    }

    private static boolean v() {
        String a2 = lo.a().a((String) "commute_config");
        if (TextUtils.isEmpty(a2)) {
            a2 = azi.m();
        }
        azb.a(null, "deng---bus云控:".concat(String.valueOf(a2)));
        if (TextUtils.isEmpty(a2)) {
            return true;
        }
        try {
            return TextUtils.equals("1", new JSONObject(a2).optString("showBus"));
        } catch (Exception unused) {
            return true;
        }
    }

    private void b(int i2, String str) {
        if (this.a != null) {
            this.a.a(i2, str);
        }
    }

    private void b(String str) {
        if (this.a != null) {
            BusCommuteJumpBean busCommuteJumpBean = new BusCommuteJumpBean();
            busCommuteJumpBean.commuteEndType = c() ? 1 : 0;
            busCommuteJumpBean.userType = this.f;
            busCommuteJumpBean.startPoi = a(this.c);
            busCommuteJumpBean.endPoi = a(this.d);
            busCommuteJumpBean.from = str;
            busCommuteJumpBean.isCpoint = ayt.a();
            busCommuteJumpBean.showCloseRTToast = false;
            int i2 = this.a.a;
            BusRouteResponse busRouteResponse = this.a.c;
            if (busRouteResponse == null || busRouteResponse.buslist == null || i2 >= busRouteResponse.buslist.size()) {
                busCommuteJumpBean.selectindex = a(0, this.a.b);
            } else {
                busCommuteJumpBean.busPaths = busRouteResponse.buslist;
                busCommuteJumpBean.selectindex = a(this.a.a, this.a.b);
                busCommuteJumpBean.realTimeInfo = w();
            }
            busCommuteJumpBean.endName = this.d == null ? "" : this.d.getName();
            TaxiComparatorResponse taxiComparatorResponse = this.a.d;
            if (taxiComparatorResponse != null) {
                busCommuteJumpBean.taxiInfo = taxiComparatorResponse.data;
            }
            if (busRouteResponse != null) {
                busCommuteJumpBean.stopEventList = busRouteResponse.stopEventList;
            }
            String jSONString = JSON.toJSONString(busCommuteJumpBean);
            if (!TextUtils.isEmpty(jSONString)) {
                azb.a("song---", "jump json = ".concat(String.valueOf(jSONString)));
                PageBundle pageBundle = new PageBundle();
                pageBundle.putString(ays.a, jSONString);
                AMapPageUtil.getPageContext().startPage(BusCommuteListPage.class, pageBundle);
            }
        }
    }

    private static SelectObj a(int i2, int i3) {
        SelectObj selectObj = new SelectObj();
        selectObj.busListIndex = i2;
        selectObj.alterIndex = i3;
        return selectObj;
    }

    private static PoiObj a(POI poi) {
        if (poi == null) {
            return null;
        }
        PoiObj poiObj = new PoiObj();
        poiObj.longitude = String.valueOf(poi.getPoint().getLongitude());
        poiObj.latitude = String.valueOf(poi.getPoint().getLatitude());
        poiObj.name = poi.getName();
        poiObj.adCode = poi.getAdCode();
        poiObj.id = poi.getId();
        poiObj.type = poi.getType();
        return poiObj;
    }

    private RealTimeBusLineInfo w() {
        BusRealTimeResponse busRealTimeResponse = this.a.e;
        if (busRealTimeResponse == null) {
            return null;
        }
        ArrayList<RealTimeBusLineInfo> arrayList = busRealTimeResponse.buses;
        if (arrayList == null || arrayList.isEmpty()) {
            return null;
        }
        BusSegment c = this.a.c();
        if (c == null) {
            return null;
        }
        Iterator<RealTimeBusLineInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            RealTimeBusLineInfo next = it.next();
            if (next != null && TextUtils.equals(next.line, c.busid) && TextUtils.equals(next.station, c.startid)) {
                return next;
            }
        }
        return null;
    }

    private void x() {
        if (!C() && !this.p.a() && this.g != null && this.a != null) {
            this.g.a(this.a.c);
        }
    }

    private void y() {
        if (!C() && !this.p.a() && this.g != null && this.a != null) {
            this.g.a(this.a.e);
        }
    }

    private void z() {
        boolean C = C();
        boolean a2 = this.p.a();
        StringBuilder sb = new StringBuilder("showTipOverlay:isBusCommuteStop = ");
        sb.append(C);
        sb.append(",isAvoiding = ");
        sb.append(a2);
        azb.a("BusCommuteMainPageImpl", sb.toString());
        if (!C && !a2 && this.g != null) {
            boolean c = c();
            ayi ayi = this.a;
            boolean z = true;
            if (this.f != 1) {
                z = false;
            }
            BusCommuteTipBean a3 = ayt.a(ayi, z, c, ayt.a());
            StringBuilder sb2 = new StringBuilder("isNearHome = ");
            sb2.append(c);
            sb2.append(", mUserTyper = ");
            sb2.append(this.f);
            sb2.append(",tipbean  = ");
            sb2.append(a3);
            azb.a("BusCommuteMainPageImpl", sb2.toString());
            if (a3 != null) {
                this.g.a(this.t, a3);
            }
        }
    }

    private void A() {
        if (C() || this.p.a()) {
            StringBuilder sb = new StringBuilder("Shouldn't show guide tip, Bus commute status=");
            sb.append(C());
            sb.append("mBusCommuteAvoidState.isAvoiding()=");
            sb.append(this.p.a());
            azb.a("BusCommuteMainPageImpl", sb.toString());
            return;
        }
        aya aya = new aya();
        aya.a = this.k.a();
        aya.b = this.k.d();
        if (this.u.a(aya)) {
            azg.a(new b() {
                public final void a(boolean z) {
                    if (z) {
                        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
                        if (latestPosition != null) {
                            ayj ayj = axz.this.g;
                            if (ayj.e != null) {
                                azp b = defpackage.azn.a.a.b();
                                if (b != null) {
                                    defpackage.azp.a aVar = b.b;
                                    if (aVar != null) {
                                        Resources resources = AMapAppGlobal.getApplication().getResources();
                                        String string = TextUtils.isEmpty(aVar.a) ? resources.getString(R.string.route_commute_guide_tip_action_text) : aVar.a;
                                        String string2 = TextUtils.isEmpty(aVar.b) ? resources.getString(R.string.route_commute_guide_tip_description_text) : aVar.b;
                                        String a2 = ayr.a(10, string);
                                        String a3 = ayr.a(12, string2);
                                        if (ayj.i == null) {
                                            com.autonavi.bundle.routecommute.bus.overlay.RouteCommuteGuideTipOverlay.a aVar2 = new com.autonavi.bundle.routecommute.bus.overlay.RouteCommuteGuideTipOverlay.a(a2, a3, latestPosition, new defpackage.aly.a() {
                                                public final void a() {
                                                    ayj.this.j.b();
                                                    azb.a(ayj.this.a, "Guide Tip, tip clicked.");
                                                }
                                            }, new defpackage.aly.a() {
                                                public final void a() {
                                                    ayj.this.b();
                                                    ayj.this.j.a();
                                                    azb.a(ayj.this.a, "Guide Tip, close clicked.");
                                                }
                                            });
                                            ayj.i = aVar2;
                                        } else {
                                            ayj.i.b = a2;
                                            ayj.i.c = a3;
                                            ayj.i.f = latestPosition;
                                        }
                                        CommuteControlBean commuteControlBean = defpackage.azn.a.a.a;
                                        if (commuteControlBean != null) {
                                            boolean isOperateEventEnable = commuteControlBean.isOperateEventEnable(ShowRouteActionProcessor.SEARCH_TYPE_BUS);
                                            com.autonavi.bundle.routecommute.common.bean.CommuteControlBean.a busOperationOptions = commuteControlBean.getBusOperationOptions();
                                            if (isOperateEventEnable && busOperationOptions != null) {
                                                String str = busOperationOptions.b;
                                                if (!TextUtils.isEmpty(str)) {
                                                    ayj.i.g = str;
                                                    azb.a(ayj.a, "received custom icon for guide tip, url=".concat(String.valueOf(str)));
                                                }
                                            }
                                        }
                                        ayj.e.draw(ayj.i);
                                    }
                                }
                            }
                            StringBuilder sb = new StringBuilder("Guide tip is displayed at ");
                            sb.append(String.format(Locale.getDefault(), "(%d, %d)", new Object[]{Integer.valueOf(latestPosition.x), Integer.valueOf(latestPosition.y)}));
                            azb.a("BusCommuteMainPageImpl", sb.toString());
                            return;
                        }
                        azb.a("BusCommuteMainPageImpl", "Guide tip is not displayed due to a null GeoPoint.");
                    }
                }
            });
        }
    }

    private void B() {
        if (this.g != null) {
            this.g.a();
        }
    }

    private boolean C() {
        return this.b == null || !this.b.isStarted() || !this.m;
    }

    public final void a(GeoPoint geoPoint) {
        if (this.r == null || !this.r.equals(geoPoint)) {
            this.r = geoPoint;
            if (this.t != 1) {
                ayj ayj = this.g;
                if (ayj.f != null && ayj.g) {
                    String str = ayj.a;
                    StringBuilder sb = new StringBuilder("updateTipOverlayPosition:x =  ");
                    sb.append(geoPoint.x);
                    sb.append(", y = ");
                    sb.append(geoPoint.y);
                    azb.a(str, sb.toString());
                    ayj.f.currentLocPoint = geoPoint;
                    switch (ayj.h) {
                        case 3:
                            ayj.b.show(ayj.f, false);
                            break;
                        case 4:
                            ayj.c.show(ayj.f, false);
                            break;
                    }
                }
            } else {
                ayj ayj2 = this.g;
                PointOverlayItem pointOverlayItem = (PointOverlayItem) ayj2.e.getItem(0);
                if (pointOverlayItem != null) {
                    ayj2.e.updateItem(pointOverlayItem, geoPoint, pointOverlayItem.mAngle);
                }
            }
            StringBuilder sb2 = new StringBuilder("onLocationChange(), update overlay position on map, x=");
            sb2.append(geoPoint.x);
            sb2.append(", y=");
            sb2.append(geoPoint.y);
            sb2.append(", tipType=");
            sb2.append(this.t);
            azb.a("BusCommuteMainPageImpl", sb2.toString());
        }
    }

    static void a(String str) {
        String str2 = "";
        String str3 = "";
        aym aym = b.a;
        if (aym.a(aym.a)) {
            str3 = "sbtq";
        } else if (aym.a(aym.c)) {
            str3 = "sbftq";
        } else if (aym.a(aym.b)) {
            str3 = "xbtq";
        } else if (aym.a(aym.d)) {
            str3 = "xbftq";
        }
        ayn ayn = new ayn(azf.b());
        if (ayn.c() || ayn.b()) {
            str2 = "data_mining_user";
        } else if (ayn.d()) {
            str2 = "greenhand_user";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", str2);
            jSONObject.put("time", str3);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        LogManager.actionLogV2(ayp.a, str, jSONObject);
    }
}
