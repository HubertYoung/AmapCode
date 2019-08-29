package com.autonavi.minimap.route.bus.realtimebus.presenter;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.MapInteractiveRelativeLayout;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusAndStationMatchup;
import com.autonavi.minimap.route.bus.realtimebus.net.parser.AosRealTimeAttentionCleanParser;
import com.autonavi.minimap.route.bus.realtimebus.net.parser.AosRealTimeAttentionParser;
import com.autonavi.minimap.route.bus.realtimebus.page.RTBusPositionPage;
import com.autonavi.minimap.route.bus.realtimebus.page.RTBusSearchPage;
import com.autonavi.minimap.route.bus.realtimebus.presenter.RTBusPositionPresenter;
import com.autonavi.minimap.route.bus.realtimebus.view.RealBusPositionArroundPanelView;
import com.autonavi.minimap.route.bus.realtimebus.view.RealBusPositionAttentionView;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.TitleBar;
import com.autonavi.widget.ui.TitleBar.b;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.json.JSONObject;

public final class RTBusPositionPresenter extends eae<RTBusPositionPage> {
    public RTBusDataManager a;
    public int b = 0;
    public String c;
    public List<btd> d;
    public a e = new a(this);
    public btd f;
    public boolean g;
    public boolean h;
    public boolean i = false;
    public boolean j = false;
    public boolean k;
    public boolean l = false;
    public GeoPoint m;
    public float n;
    public int o;
    public MapSharePreference p = new MapSharePreference(SharePreferenceName.user_route_method_info);
    public bso q;
    private String r;
    /* access modifiers changed from: private */
    public HashMap<String, RealTimeBusAndStationMatchup> s;
    /* access modifiers changed from: private */
    public com.autonavi.common.Callback.a t;
    private boolean u;
    private boolean v = true;
    /* access modifiers changed from: private */
    public int w = 0;
    /* access modifiers changed from: private */
    public boolean x = true;
    /* access modifiers changed from: private */
    public Callback<dyk> y = new Callback<dyk>() {
        private static final int REQUEST_SUCCESS = 1;

        public void callback(dyk dyk) {
            if (RTBusPositionPresenter.this.x) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("from", 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dys.a((String) "P00264", (String) "B002", jSONObject);
                RTBusPositionPresenter.this.x = false;
            }
            RTBusPositionPresenter.this.w = 0;
            ((RTBusPositionPage) RTBusPositionPresenter.this.mPage).d.mFollowRealTimeBusListView.onRefreshComplete();
            if (RTBusPositionPresenter.this.j && RTBusPositionPresenter.this.e()) {
                RTBusPositionPresenter.this.j = false;
            }
            if (dyk != null && dyk.errorCode == 1 && RTBusPositionPresenter.this.s != null) {
                HashMap<String, RealTimeBusAndStationMatchup> hashMap = dyk.a;
                for (Entry entry : RTBusPositionPresenter.this.s.entrySet()) {
                    String str = (String) entry.getKey();
                    RealTimeBusAndStationMatchup realTimeBusAndStationMatchup = (RealTimeBusAndStationMatchup) entry.getValue();
                    if (realTimeBusAndStationMatchup != null) {
                        realTimeBusAndStationMatchup.updateRTBusStatus(hashMap != null ? hashMap.get(str) : null);
                    }
                }
                ((RTBusPositionPage) RTBusPositionPresenter.this.mPage).e();
            }
        }

        public void error(Throwable th, boolean z) {
            RTBusPositionPresenter.this.w = RTBusPositionPresenter.this.w + 1;
            ((RTBusPositionPage) RTBusPositionPresenter.this.mPage).e();
            ((RTBusPositionPage) RTBusPositionPresenter.this.mPage).d.mFollowRealTimeBusListView.onRefreshComplete();
        }
    };

    public class RealTimeCollectCallBack implements Callback<AosRealTimeAttentionParser> {
        private RealTimeBusAndStationMatchup mBusData;

        public RealTimeCollectCallBack(RealTimeBusAndStationMatchup realTimeBusAndStationMatchup) {
            this.mBusData = realTimeBusAndStationMatchup;
        }

        public void callback(AosRealTimeAttentionParser aosRealTimeAttentionParser) {
            ((RTBusPositionPage) RTBusPositionPresenter.this.mPage).a();
            if (aosRealTimeAttentionParser.errorCode == 1) {
                RTBusPositionPresenter.this.a(this.mBusData);
                return;
            }
            String errorDesc = aosRealTimeAttentionParser.getErrorDesc(aosRealTimeAttentionParser.errorCode);
            if (TextUtils.isEmpty(errorDesc)) {
                errorDesc = ((RTBusPositionPage) RTBusPositionPresenter.this.mPage).getString(R.string.busline_attention_cancel_error);
            }
            ToastHelper.showToast(errorDesc);
        }

        public void error(Throwable th, boolean z) {
            ((RTBusPositionPage) RTBusPositionPresenter.this.mPage).a();
            ToastHelper.showToast(((RTBusPositionPage) RTBusPositionPresenter.this.mPage).getString(R.string.busline_attention_cancel_error));
        }
    }

    public static class a extends Handler {
        private final WeakReference<RTBusPositionPresenter> a;

        public a(RTBusPositionPresenter rTBusPositionPresenter) {
            this.a = new WeakReference<>(rTBusPositionPresenter);
        }

        public final void handleMessage(Message message) {
            try {
                RTBusPositionPresenter rTBusPositionPresenter = (RTBusPositionPresenter) this.a.get();
                if (rTBusPositionPresenter != null) {
                    int i = message.what;
                    if (i != 0) {
                        if (i == 4) {
                            if (rTBusPositionPresenter.t != null) {
                                rTBusPositionPresenter.t.cancel();
                                rTBusPositionPresenter.t = null;
                            }
                            if (((RTBusPositionPage) rTBusPositionPresenter.mPage).isStarted()) {
                                rTBusPositionPresenter.t = dxu.a(rTBusPositionPresenter.d, rTBusPositionPresenter.y, "4", "2", "1");
                            } else {
                                return;
                            }
                        }
                        return;
                    }
                    if (rTBusPositionPresenter.t != null) {
                        rTBusPositionPresenter.t.cancel();
                        rTBusPositionPresenter.t = null;
                    }
                    if (((RTBusPositionPage) rTBusPositionPresenter.mPage).isStarted()) {
                        rTBusPositionPresenter.t = dxu.a(rTBusPositionPresenter.d, rTBusPositionPresenter.y, "4", "2", "0");
                    }
                }
            } catch (Exception e) {
                kf.a((Throwable) e);
            }
        }
    }

    public RTBusPositionPresenter(RTBusPositionPage rTBusPositionPage) {
        super(rTBusPositionPage);
        boolean z = false;
        rTBusPositionPage.getContext();
        this.q = bso.a();
        this.g = this.q.a.queryBuilder().list().size() > 0;
        String str = null;
        if (((RTBusPositionPage) this.mPage).getMapView() != null) {
            GeoPoint geoPoint = new GeoPoint(((RTBusPositionPage) this.mPage).getMapView().n().x, ((RTBusPositionPage) this.mPage).getMapView().n().y);
            this.m = geoPoint;
            str = String.valueOf(geoPoint.getAdCode());
        }
        StringBuilder sb = new StringBuilder();
        sb.append(LocationInstrument.getInstance().getLatestPosition().getAdCode());
        this.r = sb.toString();
        if (!TextUtils.equals(str, this.r)) {
            ToastHelper.showLongToast(((RTBusPositionPage) this.mPage).getString(R.string.realtime_arround_adcode_diff_toast));
        }
        PageBundle arguments = ((RTBusPositionPage) this.mPage).getArguments();
        if (arguments != null && arguments.containsKey("where_click_real_time_bus")) {
            this.o = arguments.getInt("where_click_real_time_bus", 0);
            this.h = arguments.getInt("is_support_real_time_bus", 0) == 1 ? true : z;
        }
        this.a = new RTBusDataManager(((RTBusPositionPage) this.mPage).getContext());
        this.a.c = this.r;
        this.a.b = (com.autonavi.minimap.route.bus.realtimebus.RTBusDataManager.a) this.mPage;
    }

    public final void onPageCreated() {
        super.onPageCreated();
        RTBusPositionPage rTBusPositionPage = (RTBusPositionPage) this.mPage;
        View contentView = rTBusPositionPage.getContentView();
        rTBusPositionPage.e = (MapInteractiveRelativeLayout) contentView.findViewById(R.id.mapInteractiveRelativeLayout);
        rTBusPositionPage.d = (RealBusPositionAttentionView) contentView.findViewById(R.id.realbus_attention_layout);
        rTBusPositionPage.d.setListener(rTBusPositionPage);
        rTBusPositionPage.g = (RealBusPositionArroundPanelView) contentView.findViewById(R.id.realbus_around_layout);
        rTBusPositionPage.g.setIsSupportRTBus(((RTBusPositionPresenter) rTBusPositionPage.mPresenter).h);
        if (rTBusPositionPage.getMapView() != null) {
            ((RTBusPositionPresenter) rTBusPositionPage.mPresenter).n = rTBusPositionPage.getMapView().v();
            ((RTBusPositionPresenter) rTBusPositionPage.mPresenter).k = rTBusPositionPage.getMapView().s();
            rTBusPositionPage.getMapView().s(false);
        }
        if (rTBusPositionPage.getSuspendManager() != null) {
            rTBusPositionPage.getSuspendManager().d().f();
        }
        rTBusPositionPage.f = new dwb(rTBusPositionPage);
        rTBusPositionPage.f.a(true);
        rTBusPositionPage.b();
        rTBusPositionPage.b = (TitleBar) contentView.findViewById(R.id.title_bar_b);
        rTBusPositionPage.a = (TitleBar) contentView.findViewById(R.id.title_bar_a1);
        rTBusPositionPage.b.setBackImgContentDescription(rTBusPositionPage.getString(R.string.autonavi_back));
        rTBusPositionPage.a.setBackImgContentDescription(rTBusPositionPage.getString(R.string.autonavi_back));
        rTBusPositionPage.a.setActionImgContentDescription(rTBusPositionPage.getString(R.string.route_search));
        if (((RTBusPositionPresenter) rTBusPositionPage.mPresenter).h || ((RTBusPositionPresenter) rTBusPositionPage.mPresenter).g) {
            rTBusPositionPage.a.setVisibility(0);
            rTBusPositionPage.b.setVisibility(8);
            rTBusPositionPage.a.setOnBackClickListener((OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    RTBusPositionPage.this.finish();
                }
            });
            rTBusPositionPage.a.setOnActionClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    ((RTBusPositionPresenter) RTBusPositionPage.this.mPresenter).a();
                }
            });
            if (!((RTBusPositionPresenter) rTBusPositionPage.mPresenter).h) {
                rTBusPositionPage.a.setActionImgVisibility(8);
            } else {
                rTBusPositionPage.a.setActionImgVisibility(0);
            }
            rTBusPositionPage.c = new ArrayList<>();
            rTBusPositionPage.c.add(new b((CharSequence) rTBusPositionPage.getString(R.string.real_time_bus_attention)));
            rTBusPositionPage.c.add(new b((CharSequence) rTBusPositionPage.getString(R.string.real_time_bus_around)));
            rTBusPositionPage.a.setOnTabSelectedListener(new erq() {
                public final void b(int i) {
                }

                public final void a(int i) {
                    if (i == 0) {
                        RTBusPositionPage.this.f();
                        return;
                    }
                    if (i == 1) {
                        RTBusPositionPage.this.g();
                    }
                }
            });
            if (((RTBusPositionPresenter) rTBusPositionPage.mPresenter).o == 0) {
                rTBusPositionPage.a.addTabs(rTBusPositionPage.c, 1);
                rTBusPositionPage.g();
            } else {
                rTBusPositionPage.a.addTabs(rTBusPositionPage.c, 0);
                rTBusPositionPage.f();
            }
        } else {
            rTBusPositionPage.a.setVisibility(8);
            rTBusPositionPage.b.setVisibility(0);
            rTBusPositionPage.b.setOnBackClickListener((OnClickListener) new OnClickListener() {
                public final void onClick(View view) {
                    RTBusPositionPage.this.finish();
                }
            });
            rTBusPositionPage.g();
        }
        rTBusPositionPage.g.setRTBusPageInteraction(rTBusPositionPage.h);
        this.a.a(false);
        b();
        f();
        ((RTBusPositionPage) this.mPage).c();
    }

    public final void onPause() {
        super.onPause();
        zt.a().a("realtime_bus_home", false);
    }

    public final void onStart() {
        zt.a().a("realtime_bus_home", true);
        ebo.a((AbstractBaseMapPage) this.mPage);
        super.onStart();
        RTBusPositionPage rTBusPositionPage = (RTBusPositionPage) this.mPage;
        boolean z = this.v;
        boolean z2 = this.u;
        rTBusPositionPage.b();
        if (!z && rTBusPositionPage.f != null) {
            rTBusPositionPage.f.a(false);
            if (z2) {
                dwb dwb = rTBusPositionPage.f;
                GeoPoint a2 = dwb.a(dwb.g);
                if (a2 != null) {
                    dwb.b.a(a2.x, a2.y);
                }
            }
            dwb dwb2 = rTBusPositionPage.f;
            if (dwb2.b != null && dwb2.i > 0.0f) {
                dwb2.b.d(dwb2.i);
            }
        }
        if (!this.v && new MapSharePreference(SharePreferenceName.user_route_method_info).getBooleanValue("realbus_position_attention_need_roaledDB", true)) {
            b();
            ahl.b(new defpackage.ahl.a<Boolean>() {
                public final void onError(Throwable th) {
                }

                public final /* synthetic */ void onFinished(Object obj) {
                    if (((Boolean) obj).booleanValue()) {
                        RTBusDataManager.this.a(DataManagerRequest.AROUND, DataManagerStatus.DATASET_CHANGED);
                    }
                }

                /* access modifiers changed from: private */
                /* renamed from: a */
                public Boolean doBackground() throws Exception {
                    boolean z;
                    HashMap hashMap = new HashMap();
                    if (!TextUtils.isEmpty(RTBusDataManager.this.c)) {
                        List<btd> c = bso.a().c(RTBusDataManager.this.c);
                        if (c != null) {
                            for (btd next : c) {
                                hashMap.put(next.station_id, next);
                            }
                        }
                    }
                    synchronized (RTBusDataManager.this.f) {
                        Iterator<Entry<String, RealTimeBusAndStationMatchup>> it = RTBusDataManager.this.f.entrySet().iterator();
                        while (true) {
                            z = false;
                            while (true) {
                                if (it.hasNext()) {
                                    RealTimeBusAndStationMatchup realTimeBusAndStationMatchup = (RealTimeBusAndStationMatchup) it.next().getValue();
                                    if (realTimeBusAndStationMatchup != null) {
                                        boolean isFollow = realTimeBusAndStationMatchup.isFollow();
                                        realTimeBusAndStationMatchup.setFollow(hashMap.containsKey(realTimeBusAndStationMatchup.mStationID));
                                        if (z || isFollow != realTimeBusAndStationMatchup.isFollow()) {
                                            z = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return Boolean.valueOf(z);
                }
            });
        }
        if (!this.v && this.a != null && !e()) {
            this.a.b(false);
        }
        this.v = false;
        RTBusPositionPage rTBusPositionPage2 = (RTBusPositionPage) this.mPage;
        if (rTBusPositionPage2.getSuspendManager() != null) {
            rTBusPositionPage2.getSuspendManager().d().f();
        }
        if (rTBusPositionPage2.f != null) {
            dwb dwb3 = rTBusPositionPage2.f;
            if (dwb3.a != null) {
                dwb3.a.addOverlay(dwb3.d, false);
                dwb3.a.addOverlay(dwb3.c, false);
                dwb3.a.addOverlay(dwb3.e, false);
            }
            rTBusPositionPage2.f.a();
        }
        PageBundle arguments = ((RTBusPositionPage) this.mPage).getArguments();
        if (arguments != null && arguments.containsKey("start_from_busline_follow") && TextUtils.equals("rt_position_start_from_busline_follow", arguments.getString("start_from_busline_follow"))) {
            ((RTBusPositionPage) this.mPage).a.setSelectTab(0);
            arguments.putString("start_from_busline_follow", "");
        }
    }

    public final void onStop() {
        super.onStop();
        d();
        this.a.d();
        RTBusPositionPage rTBusPositionPage = (RTBusPositionPage) this.mPage;
        if (rTBusPositionPage.f != null) {
            if (rTBusPositionPage.getMapView() != null) {
                rTBusPositionPage.f.i = rTBusPositionPage.getMapView().v();
            }
            dwb dwb = rTBusPositionPage.f;
            if (dwb.b != null) {
                dwb.b.z();
            }
            dwb.b(false);
            dwb.h = true;
            dwb.d();
            if (dwb.a != null) {
                dwb.a.removeOverlay(dwb.c);
                dwb.a.removeOverlay(dwb.d);
                dwb.a.removeOverlay(dwb.e);
            }
        }
        RTBusPositionPage rTBusPositionPage2 = (RTBusPositionPage) this.mPage;
        boolean z = this.k;
        float f2 = this.n;
        GeoPoint geoPoint = this.m;
        if (rTBusPositionPage2.f != null) {
            dwb dwb2 = rTBusPositionPage2.f;
            if (dwb2.b != null) {
                ebf.a(dwb2.b, dwb2.b.p(false), dwb2.b.ae(), 0);
                dwb2.b.s(brv.a());
                dwb2.b.b(dwb2.b.al() / 2, dwb2.b.am() / 2);
                if (geoPoint != null) {
                    dwb2.b.a(geoPoint.x, geoPoint.y);
                }
            }
            dwb dwb3 = rTBusPositionPage2.f;
            if (dwb3.b != null) {
                dwb3.b.b(z);
            }
            dwb dwb4 = rTBusPositionPage2.f;
            if (dwb4.b != null) {
                dwb4.b.d(f2);
            }
        }
    }

    public final void onDestroy() {
        RTBusPositionPage rTBusPositionPage = (RTBusPositionPage) this.mPage;
        if (rTBusPositionPage.getSuspendWidgetHelper() != null) {
            rTBusPositionPage.getSuspendWidgetHelper().a((defpackage.ceb.a) null);
        }
        super.onDestroy();
        d();
        if (this.d != null) {
            this.d.clear();
            this.d = null;
        }
        if (this.s != null) {
            this.s.clear();
            this.s = null;
        }
        RTBusDataManager rTBusDataManager = this.a;
        rTBusDataManager.b = null;
        rTBusDataManager.c();
        rTBusDataManager.d();
        rTBusDataManager.a();
    }

    public final ON_BACK_TYPE onBackPressed() {
        return ON_BACK_TYPE.TYPE_NORMAL;
    }

    public final void onMapSurfaceChanged(int i2, int i3) {
        super.onMapSurfaceChanged(i2, i3);
        RTBusPositionPage rTBusPositionPage = (RTBusPositionPage) this.mPage;
        boolean z = this.v;
        if (rTBusPositionPage.f != null) {
            if (!z) {
                rTBusPositionPage.f.a(false);
            }
            rTBusPositionPage.f.a();
        }
    }

    public final void onResult(int i2, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i2, resultType, pageBundle);
        this.u = true;
        if (((RTBusPositionPage) this.mPage).getMapView() != null) {
            this.k = ((RTBusPositionPage) this.mPage).getMapView().s();
        }
        ((RTBusPositionPage) this.mPage).b();
    }

    public final void a() {
        if (this.q.b() >= 50) {
            ToastHelper.showToast(((RTBusPositionPage) this.mPage).getString(R.string.real_time_bus_attention_upperlimit));
            return;
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("real_time_bus_adcode", this.r);
        ((RTBusPositionPage) this.mPage).startPage(RTBusSearchPage.class, pageBundle);
    }

    public final void b() {
        this.d = a((String) "");
        ((RTBusPositionPage) this.mPage).c();
        if (this.d != null && !this.d.isEmpty()) {
            this.s = dxu.a(this.d);
            ((RTBusPositionPage) this.mPage).a(b(false));
            h();
        }
    }

    private void h() {
        c();
        this.e.sendMessage(this.e.obtainMessage(0));
    }

    public final void c() {
        if (this.e != null) {
            this.e.removeMessages(0);
        }
    }

    public final void d() {
        c();
        if (this.t != null) {
            this.t.cancel();
            this.t = null;
        }
    }

    public final boolean e() {
        return this.p.getBooleanValue("last_realbus_position_is_attention", false);
    }

    public final void a(boolean z) {
        this.p.putBooleanValue("last_realbus_position_is_attention", z);
    }

    public final void f() {
        if (this.p.getBooleanValue("realbus_position_push_clean", true)) {
            dyj.a(new Callback<AosRealTimeAttentionCleanParser>() {
                public void callback(AosRealTimeAttentionCleanParser aosRealTimeAttentionCleanParser) {
                    if (aosRealTimeAttentionCleanParser.errorCode == 1) {
                        RTBusPositionPresenter.this.p.putBooleanValue("realbus_position_push_clean", false);
                        if (RTBusPositionPresenter.this.l) {
                            RTBusPositionPresenter.this.g();
                        }
                    } else if (RTBusPositionPresenter.this.l) {
                        ToastHelper.showToast(((RTBusPositionPage) RTBusPositionPresenter.this.mPage).getString(R.string.busline_attention_save_error));
                    }
                }

                public void error(Throwable th, boolean z) {
                    if (RTBusPositionPresenter.this.l) {
                        ToastHelper.showToast(((RTBusPositionPage) RTBusPositionPresenter.this.mPage).getString(R.string.busline_attention_save_error));
                    }
                }
            });
        }
    }

    public final void g() {
        this.i = true;
        ((RTBusPositionPage) this.mPage).a(((RTBusPositionPage) this.mPage).getString(R.string.loading), (AosRequest) null);
        ahl.b(new defpackage.ahl.a<Boolean>() {
            public final /* synthetic */ void onFinished(Object obj) {
                RTBusPositionPage rTBusPositionPage = (RTBusPositionPage) RTBusPositionPresenter.this.mPage;
                if (((Boolean) obj).booleanValue()) {
                    rTBusPositionPage.a();
                    if (rTBusPositionPage.g != null) {
                        rTBusPositionPage.g.onListItemDataChanged();
                    }
                    ToastHelper.showToast(rTBusPositionPage.getString(R.string.real_time_add_success_2));
                    return;
                }
                ToastHelper.showToast(rTBusPositionPage.getString(R.string.real_time_add_fail));
            }

            public final void onError(Throwable th) {
                ((RTBusPositionPage) RTBusPositionPresenter.this.mPage).a();
                ToastHelper.showToast(((RTBusPositionPage) RTBusPositionPresenter.this.mPage).getString(R.string.real_time_add_fail));
            }

            public final /* synthetic */ Object doBackground() throws Exception {
                if (RTBusPositionPresenter.this.f != null) {
                    return RTBusPositionPresenter.this.q.a(RTBusPositionPresenter.this.f);
                }
                return Boolean.FALSE;
            }
        });
    }

    public final void a(final RealTimeBusAndStationMatchup realTimeBusAndStationMatchup) {
        this.i = true;
        ((RTBusPositionPage) this.mPage).a(((RTBusPositionPage) this.mPage).getString(R.string.loading), (AosRequest) null);
        ahl.b(new defpackage.ahl.a<Object>() {
            public final Object doBackground() throws Exception {
                RTBusPositionPresenter.a(RTBusPositionPresenter.this, RTBusPositionPresenter.this.c);
                return null;
            }

            public final void onFinished(Object obj) {
                ((RTBusPositionPage) RTBusPositionPresenter.this.mPage).a();
                if (realTimeBusAndStationMatchup != null) {
                    realTimeBusAndStationMatchup.setFollow(false);
                }
                if (((RTBusPositionPage) RTBusPositionPresenter.this.mPage).g != null) {
                    ((RTBusPositionPage) RTBusPositionPresenter.this.mPage).g.onListItemDataChanged();
                }
                ToastHelper.showToast(((RTBusPositionPage) RTBusPositionPresenter.this.mPage).getString(R.string.busline_attention_cancel_success));
            }

            public final void onError(Throwable th) {
                ((RTBusPositionPage) RTBusPositionPresenter.this.mPage).a();
            }
        });
    }

    private List<btd> a(String str) {
        return this.q.c(str);
    }

    public final List<RealTimeBusAndStationMatchup> b(boolean z) {
        if (this.d == null || this.s == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (btd next : this.d) {
            for (Entry next2 : this.s.entrySet()) {
                String str = (String) next2.getKey();
                RealTimeBusAndStationMatchup realTimeBusAndStationMatchup = (RealTimeBusAndStationMatchup) next2.getValue();
                realTimeBusAndStationMatchup.setLoadFinish(z);
                if (z) {
                    realTimeBusAndStationMatchup.retryTimes = this.w;
                }
                if (next.station_id.equals(str)) {
                    arrayList.add(realTimeBusAndStationMatchup);
                }
            }
        }
        return arrayList;
    }

    static /* synthetic */ void a(RTBusPositionPresenter rTBusPositionPresenter, String str) {
        rTBusPositionPresenter.q.b(str);
        if (rTBusPositionPresenter.e() && rTBusPositionPresenter.d != null && rTBusPositionPresenter.s != null) {
            RealTimeBusAndStationMatchup remove = rTBusPositionPresenter.s.remove(str);
            if (remove != null) {
                rTBusPositionPresenter.d.remove(remove.mBean);
            }
        }
    }
}
