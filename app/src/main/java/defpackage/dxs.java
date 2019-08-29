package defpackage;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.gpsbutton.GPSButtonNewMainPage;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.route.bus.inter.IBusLineSearchResult;
import com.autonavi.minimap.route.bus.inter.impl.BusLineSearch;
import com.autonavi.minimap.route.bus.realtimebus.BusRadarViewManager$16$1;
import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatManager;
import com.autonavi.minimap.route.bus.realtimebus.data.HeartBeatRequest;
import com.autonavi.minimap.route.bus.realtimebus.data.POISearchManager;
import com.autonavi.minimap.route.bus.realtimebus.model.BusStationData;
import com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses;
import com.autonavi.minimap.route.bus.realtimebus.model.RealtimeBuses.RealtimeBus;
import com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines;
import com.autonavi.minimap.route.bus.realtimebus.view.DefaultPageRealtimeTipViewGroup;
import com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview.FrameGifView;
import com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview.RealtimeInfoLayoutManager;
import com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview.RealtimeInfoRecyclerView;
import com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview.ZoomBoardViewBg;
import com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview.ZoomHorizonBoardViewBg;
import com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.widget.ui.LoadingViewBL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.json.JSONObject;

/* renamed from: dxs reason: default package */
/* compiled from: BusRadarViewManager */
public class dxs {
    private View A;
    private long B;
    private long C;
    private final long D = 2000;
    private boolean E;
    private LoadingViewBL F;
    /* access modifiers changed from: private */
    public DefaultPageRealtimeTipViewGroup G;
    /* access modifiers changed from: private */
    public RealtimeInfoRecyclerView H;
    private View I;
    /* access modifiers changed from: private */
    public ViewGroup J;
    private RealtimeInfoLayoutManager K;
    /* access modifiers changed from: private */
    public BusStationData L;
    /* access modifiers changed from: private */
    public dyg M;
    /* access modifiers changed from: private */
    public int N;
    private boolean O;
    public dyy a;
    public HeartBeatRequest b;
    public a c;
    /* access modifiers changed from: private */
    public MapBasePage d;
    /* access modifiers changed from: private */
    public RouteRealtimeListenerImpl e;
    private View f;
    /* access modifiers changed from: private */
    public View g;
    /* access modifiers changed from: private */
    public View h;
    /* access modifiers changed from: private */
    public ZoomHorizonBoardViewBg i;
    private View j;
    private View k;
    private View l;
    private FrameGifView m;
    private TextView n;
    private TextView o;
    private View p;
    /* access modifiers changed from: private */
    public View q;
    private View r;
    private boolean s;
    /* access modifiers changed from: private */
    public ZoomBoardViewBg t;
    /* access modifiers changed from: private */
    public ZoomBoardViewBg u;
    /* access modifiers changed from: private */
    public ZoomBoardViewBg v;
    private View w;
    private ScaleView x;
    private GPSButtonNewMainPage y;
    private ImageView z;

    /* renamed from: dxs$a */
    /* compiled from: BusRadarViewManager */
    static class a {
        String a;
        String b;
        String c;
        dyc d;

        private a() {
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    public dxs(AbstractBaseMapPage abstractBaseMapPage, RouteRealtimeListenerImpl routeRealtimeListenerImpl) {
        this.e = routeRealtimeListenerImpl;
        if (abstractBaseMapPage != null && (abstractBaseMapPage instanceof MapBasePage)) {
            this.d = (MapBasePage) abstractBaseMapPage;
        }
        this.a = new dyy(this.d.getContext());
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            iMainMapService.a((Object) new dab() {
                public final void a(boolean z) {
                    if (dxs.this.G != null) {
                        dxs.b();
                        bmn.a(z);
                        dxs.a(dxs.this, z);
                    }
                }
            });
        }
    }

    public final void a(final dyg dyg) {
        a(false);
        aho.a(new Runnable() {
            public final void run() {
                if (dxs.this.d != null && dxs.this.d.isAlive() && dyg != null) {
                    dxs.a(dxs.this, dyg);
                    dxs.b(dxs.this, dyg);
                    dxs.this.f(dyg);
                    dxs.d(dxs.this, dyg);
                    dxs.this.M = dyg;
                    dxs.this.c();
                }
            }
        });
    }

    public final void a(BusStationData busStationData, boolean z2) {
        if (this.d != null && this.d.isAlive()) {
            this.s = false;
            this.M = null;
            d();
            bmn.a(this.d.getMapView());
            LayoutInflater from = LayoutInflater.from(this.d.getContext());
            if (this.f == null && from != null && this.H == null) {
                this.f = from.inflate(R.layout.realtime_tip_container_layout, null);
                this.H = (RealtimeInfoRecyclerView) this.f.findViewById(R.id.realtime_bus_detail_recycler_view);
                this.H.initAnimationController();
                this.g = this.f.findViewById(R.id.real_bus_scene_select_line);
                this.h = this.f.findViewById(R.id.real_bus_scene_more_function);
                this.i = (ZoomHorizonBoardViewBg) this.f.findViewById(R.id.realtime_tip_more_function_board_bg);
                this.n = (TextView) this.f.findViewById(R.id.realtime_bus_select_bus_line);
                this.n.setMaxWidth((int) (((float) ags.a(this.d.getContext()).width()) - ((float) agn.a(this.d.getContext(), 228.0f))));
                this.m = (FrameGifView) this.f.findViewById(R.id.realtime_bus_select_bus_line_gif);
                this.w = this.f.findViewById(R.id.realtime_info_item_summary_icon_tip);
                this.o = (TextView) this.f.findViewById(R.id.realtime_current_line_notice_tip);
                this.p = this.f.findViewById(R.id.realtime_bus_select_bus_bt);
                this.q = this.f.findViewById(R.id.realtime_bus_select_bus_line_more);
                this.r = this.f.findViewById(R.id.realtime_bus_select_bus_holder_loading);
                this.t = (ZoomBoardViewBg) this.f.findViewById(R.id.realtime_bus_select_bus_bg_front);
                this.u = (ZoomBoardViewBg) this.f.findViewById(R.id.realtime_bus_select_bus_bg_front_second);
                this.v = (ZoomBoardViewBg) this.f.findViewById(R.id.realtime_bus_select_bus_bg_front_third);
                this.t.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                    public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                        dxs.this.u.setMinimumWidth((int) (((double) dxs.this.t.getWidth()) * 0.8d));
                        dxs.this.u.setMinimumHeight((int) (((double) dxs.this.t.getHeight()) * 0.8d));
                        dxs.this.v.setMinimumWidth((int) (((double) dxs.this.t.getWidth()) * 0.64d));
                        dxs.this.v.setMinimumHeight((int) (((double) dxs.this.t.getHeight()) * 0.64d));
                    }
                });
                this.q.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        dxs.this.h.setVisibility(0);
                        dxs.this.i.startAnimation(true);
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(dxs.this.h.findViewById(R.id.realtime_tip_more_function_content), "alpha", new float[]{0.0f, 1.0f});
                        ofFloat.setDuration(375);
                        ofFloat.setInterpolator(new DecelerateInterpolator());
                        ofFloat.start();
                        dxs.this.g.setVisibility(8);
                        LogManager.actionLogV2("P00367", "B010");
                    }
                });
                this.h.setOnClickListener(new OnClickListener() {
                    public final void onClick(View view) {
                        dxs.o(dxs.this);
                    }
                });
                this.j = this.h.findViewById(R.id.realtime_tip_more_function_favorite_bt);
                this.k = this.h.findViewById(R.id.realtime_tip_more_function_exchange_bt);
                this.l = this.h.findViewById(R.id.realtime_tip_more_function_detail_bt);
                this.y = (GPSButtonNewMainPage) this.f.findViewById(R.id.real_bus_scene_select_line_gps_bt);
                this.x = (ScaleView) this.f.findViewById(R.id.real_bus_scene_select_line_scale_view);
                this.x.setMapManager(this.d.getMapManager());
                this.x.getScaleLineView().mAlignRight = false;
                this.J = (ViewGroup) this.f.findViewById(R.id.real_bus_scene_detail);
                this.I = this.f.findViewById(R.id.realtime_bus_detail_recycler_view_place_holder);
                LayoutParams layoutParams = (LayoutParams) this.I.getLayoutParams();
                layoutParams.height = (int) ((((float) ags.a(this.d.getContext()).height()) / 9.0f) * 2.0f);
                this.I.setLayoutParams(layoutParams);
                this.K = new RealtimeInfoLayoutManager(this.d.getContext());
                this.H.setLayoutManager(this.K);
                this.G = (DefaultPageRealtimeTipViewGroup) this.f.findViewById(R.id.default_page_realtimetip_container);
                this.G.setMapView(this.d.getMapView());
                this.z = (ImageView) this.f.findViewById(R.id.realtime_refresh);
                this.A = this.f.findViewById(R.id.realtime_refresh_container);
                this.F = (LoadingViewBL) this.f.findViewById(R.id.realtime_refresh_btn_loading);
                this.H.setAdapter(this.a);
                this.G.setTipStatusListener(new com.autonavi.minimap.route.bus.realtimebus.view.DefaultPageRealtimeTipViewGroup.a() {
                    public final void a() {
                        eao.e(dxs.class.getName(), "RealtimeTipStatusListener show");
                    }

                    public final void b() {
                        eao.e(dxs.class.getName(), "RealtimeTipStatusListener dissmiss");
                    }
                });
            }
            this.x.changeLogoStatus(true);
            a(false);
            this.L = busStationData;
            this.a.f = busStationData;
            this.A.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    dxs.g(dxs.this);
                }
            });
            this.p.setOnTouchListener(new OnTouchListener() {
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    dxs.d(dxs.this, dxs.this.M);
                    dxs.a(dxs.this, dxs.this.q, false, dxs.this.M);
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("action", "click");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    LogManager.actionLogV2("P00367", "B008", jSONObject);
                    return true;
                }
            });
            this.g.setVisibility(0);
            this.J.setVisibility(8);
            this.d.getSuspendManager();
            Context context = this.d.getContext();
            GPSButtonNewMainPage gPSButtonNewMainPage = this.y;
            gPSButtonNewMainPage.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (dxs.this.d != null && dxs.this.d.isAlive()) {
                        LogManager.actionLogV2("P00367", "B006");
                        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                        if (latestPosition != null) {
                            dxs.this.d.getGLMapView().a(latestPosition.x, latestPosition.y);
                        }
                    }
                }
            });
            if (gPSButtonNewMainPage.getParent() == null) {
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) gPSButtonNewMainPage.getLayoutParams();
                int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.map_container_btn_margin);
                layoutParams2.leftMargin = dimensionPixelSize;
                layoutParams2.bottomMargin = dimensionPixelSize;
                gPSButtonNewMainPage.setLayoutParams(layoutParams2);
            }
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.q.getLayoutParams();
            layoutParams3.width = agn.a(this.d.getContext(), 52.0f);
            this.q.setLayoutParams(layoutParams3);
            this.q.requestLayout();
            e((dyg) null);
            a(true);
            if (!z2) {
                c();
            }
            IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
            if (iMainMapService != null) {
                iMainMapService.a(czm.class);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean z2) {
        if (z2) {
            if (!this.F.isShown()) {
                this.z.setVisibility(8);
                this.F.setVisibility(0);
            }
            this.E = true;
            return;
        }
        this.z.setVisibility(0);
        this.F.setVisibility(8);
        this.A.setClickable(true);
        this.E = false;
    }

    /* access modifiers changed from: private */
    @Nullable
    public btd c(dyg dyg) {
        RealTimeBusStation realTimeBusStation;
        RecommenStationLines d2 = d(dyg);
        if (d2 == null) {
            return null;
        }
        String lineid = d2.getLineid();
        if (this.L != null) {
            List<RealTimeBusStation> list = this.L.stations;
            int i2 = 0;
            while (true) {
                if (i2 >= list.size()) {
                    break;
                }
                realTimeBusStation = list.get(i2);
                if (realTimeBusStation != null && TextUtils.equals(realTimeBusStation.bus_id, lineid)) {
                    break;
                }
                i2++;
            }
            if (realTimeBusStation == null || this.e == null) {
                return realTimeBusStation;
            }
            RouteRealtimeListenerImpl routeRealtimeListenerImpl = this.e;
            if (routeRealtimeListenerImpl.c == null) {
                return null;
            }
            dwa dwa = routeRealtimeListenerImpl.c;
            if (TextUtils.isEmpty(lineid)) {
                return null;
            }
            int size = dwa.p.size();
            for (int i3 = 0; i3 < size; i3++) {
                BusStationData busStationData = dwa.p.get(i3);
                if (busStationData != null && !dxx.a((Collection<T>) busStationData.stations)) {
                    int size2 = busStationData.stations.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        RealTimeBusStation realTimeBusStation2 = busStationData.stations.get(i4);
                        if (realTimeBusStation2 != null && lineid.equals(realTimeBusStation2.bus_id)) {
                            return realTimeBusStation2;
                        }
                    }
                    continue;
                }
            }
            return null;
        }
        realTimeBusStation = null;
        if (realTimeBusStation == null) {
        }
        return realTimeBusStation;
    }

    /* access modifiers changed from: private */
    public static RecommenStationLines d(dyg dyg) {
        if (dyg == null) {
            return null;
        }
        return dyg.a();
    }

    /* access modifiers changed from: private */
    public void b(boolean z2) {
        this.j.findViewById(R.id.realtime_tip_more_function_favorite_icon).setBackgroundResource(z2 ? R.drawable.realtime_bus_select_bus_line_exchange_icon_favorite : R.drawable.realtime_bus_select_bus_line_exchange_icon_not_favorite);
        ((TextView) this.j.findViewById(R.id.realtime_tip_more_function_favorite_text)).setText(z2 ? "取消关注" : "关注车辆");
    }

    /* access modifiers changed from: private */
    public void c() {
        if (this.s) {
            this.t.setTranslationY(0.0f);
            this.u.setTranslationY(0.0f);
            this.v.setTranslationY(0.0f);
            return;
        }
        this.t.clearAnimation();
        this.u.clearAnimation();
        this.v.clearAnimation();
        float height = (float) (this.t.getHeight() + agn.a(this.d.getContext(), 22.0f));
        float height2 = (float) (this.u.getHeight() + agn.a(this.d.getContext(), 17.0f));
        float height3 = (float) (this.v.getHeight() + agn.a(this.d.getContext(), 12.0f));
        this.s = true;
        this.t.setTranslationY(height);
        this.u.setTranslationY(height2);
        this.v.setTranslationY(height3);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.t, "translationY", new float[]{height, 0.0f});
        ofFloat.setDuration(375);
        ofFloat.setInterpolator(new DecelerateInterpolator());
        ofFloat.start();
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.u, "translationY", new float[]{height2, 0.0f});
        ofFloat2.setDuration(220);
        ofFloat2.setInterpolator(new DecelerateInterpolator());
        ofFloat2.setStartDelay(187);
        ofFloat2.start();
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.v, "translationY", new float[]{height3, 0.0f});
        ofFloat3.setDuration(220);
        ofFloat3.setInterpolator(new DecelerateInterpolator());
        ofFloat3.setStartDelay(297);
        ofFloat3.start();
    }

    /* access modifiers changed from: private */
    public void e(dyg dyg) {
        this.h.setVisibility(8);
        this.g.setVisibility(0);
        f(dyg);
    }

    /* access modifiers changed from: private */
    public void f(dyg dyg) {
        g(dyg);
        if (dyg == null) {
            eao.f("BusRadarViewManager", "bus is null ");
            return;
        }
        boolean z2 = !dxx.a((Collection<T>) dyg.c);
        RecommenStationLines d2 = d(dyg);
        if (d2 != null) {
            String lineid = d2.getLineid();
            d2.getStationid();
            boolean equals = "1".equals(d2.getIs_realtime());
            this.n.setText(d2.getKey_name());
            String str = "0";
            if (this.a != null) {
                str = this.a.a(lineid);
            }
            if (z2) {
                List<RealtimeBus> list = dyg.c;
                int i2 = 0;
                while (true) {
                    if (i2 >= list.size()) {
                        break;
                    }
                    RealtimeBus realtimeBus = list.get(i2);
                    if (realtimeBus != null && TextUtils.equals(lineid, realtimeBus.line)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(realtimeBus.status);
                        d2.setStatus(sb.toString());
                        break;
                    }
                    i2++;
                }
            }
            a(a(d2.getStatus()), a(str), lineid, equals);
        }
    }

    private void a(int i2, int i3, String str, boolean z2) {
        String str2;
        if (z2) {
            this.d.getContext();
            str2 = dxx.a(i2);
        } else {
            str2 = "";
        }
        eao.e("showNoticeTipData RealTimeBusAndStationMatchup", "notice text ".concat(String.valueOf(str2)));
        if (!TextUtils.isEmpty(str2)) {
            this.o.setText(str2);
            this.o.setVisibility(0);
            this.n.setTextColor(this.d.getContext().getResources().getColor(R.color.f_c_3));
        } else {
            this.o.setVisibility(8);
            this.n.setTextColor(this.d.getContext().getResources().getColor(R.color.f_c_2));
        }
        if (z2) {
            FrameGifView frameGifView = this.m;
            int i4 = 1;
            if (i2 != 1) {
                i4 = 2;
            }
            frameGifView.setStatus(i4);
        } else {
            this.m.setVisibility(4);
        }
        if (i3 == 3) {
            this.w.setVisibility(0);
            this.w.setBackgroundResource(R.drawable.realtime_info_item_summary_icon_home);
        } else if (i3 == 4) {
            this.w.setVisibility(0);
            this.w.setBackgroundResource(R.drawable.realtime_info_item_summary_icon_company);
        } else {
            this.w.setVisibility(8);
        }
        RealTimeBusStation realTimeBusStation = null;
        if (this.L != null) {
            List<RealTimeBusStation> list = this.L.stations;
            int i5 = 0;
            while (true) {
                if (i5 >= list.size()) {
                    break;
                }
                RealTimeBusStation realTimeBusStation2 = list.get(i5);
                if (TextUtils.equals(realTimeBusStation2.bus_id, str)) {
                    realTimeBusStation = realTimeBusStation2;
                    break;
                }
                i5++;
            }
        }
        if (realTimeBusStation != null && realTimeBusStation.isFollow) {
            this.w.setVisibility(0);
            this.w.setBackgroundResource(R.drawable.realtime_info_item_summary_icon_favorite);
        }
    }

    private void g(dyg dyg) {
        if (dyg == null) {
            this.r.setVisibility(0);
            this.p.setVisibility(8);
            this.q.setVisibility(8);
            this.m.setVisibility(4);
            this.u.setMinimumWidth((int) (((double) this.t.getWidth()) * 0.8d));
            this.u.setMinimumHeight((int) (((double) this.t.getHeight()) * 0.8d));
            this.v.setMinimumWidth((int) (((double) this.t.getWidth()) * 0.64d));
            this.v.setMinimumHeight((int) (((double) this.t.getHeight()) * 0.64d));
            return;
        }
        this.r.setVisibility(8);
        this.p.setVisibility(0);
        this.q.setVisibility(0);
        this.m.setVisibility(0);
    }

    private static int a(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }

    public final HeartBeatRequest a(a aVar) {
        if (aVar == null) {
            return null;
        }
        this.N = 0;
        return POISearchManager.a(aVar.a, aVar.b, aVar.c, aVar.d);
    }

    /* access modifiers changed from: private */
    public void d() {
        if (this.b != null) {
            HeartBeatManager.a().a(this.b);
            this.b = null;
        }
        this.c = null;
    }

    public final void a(int i2) {
        if (this.d != null && this.d.isAlive()) {
            Context appContext = AMapPageUtil.getAppContext();
            if (appContext != null) {
                ToastHelper.showToast(appContext.getString(i2));
            }
        }
    }

    public static void a() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            iMainMapService.a(czm.class);
        }
    }

    static /* synthetic */ void b() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            iMainMapService.a(czl.class);
        }
    }

    static /* synthetic */ void a(dxs dxs, boolean z2) {
        if (dxs.O != z2) {
            dxs.O = z2;
            IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
            if (iMainMapService != null) {
                czh czh = (czh) iMainMapService.a(czh.class);
                if (((czm) iMainMapService.a(czm.class)) != null) {
                    ViewGroup viewGroup = (ViewGroup) czh.b();
                    if (viewGroup != null) {
                        FrameLayout frameLayout = (FrameLayout) viewGroup.findViewById(R.id.realtimebus_container);
                        frameLayout.removeView(dxs.G);
                        if (z2) {
                            frameLayout.addView(dxs.G);
                            dxs.G.setVisibility(0);
                            return;
                        }
                        dxs.G.setVisibility(8);
                        dxs.d();
                    }
                }
            }
        }
    }

    static /* synthetic */ void a(dxs dxs, final dyg dyg) {
        if (dxs.d != null && dxs.d.isAlive()) {
            dxs.A.post(new Runnable() {
                public final void run() {
                    dxs.this.a(false);
                }
            });
            if (dxs.H != null) {
                dxs.H.post(new Runnable() {
                    public final void run() {
                        if (dxs.this.a != null) {
                            dxs.this.a.a(dyg);
                        }
                    }
                });
            }
            dxs.A.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    dxs.g(dxs.this);
                }
            });
        }
    }

    static /* synthetic */ void b(dxs dxs, final dyg dyg) {
        if (dxs.d.isAlive() && dyg != null) {
            boolean z2 = false;
            if (dyg != null) {
                btd c2 = dxs.c(dyg);
                if (c2 != null) {
                    z2 = c2.isFollow;
                }
            }
            dxs.b(z2);
            dxs.j.setOnClickListener(new OnClickListener() {
                /* JADX WARNING: Code restructure failed: missing block: B:51:0x0146, code lost:
                    r0 = r8;
                 */
                /* JADX WARNING: Removed duplicated region for block: B:25:0x00bc  */
                /* JADX WARNING: Removed duplicated region for block: B:34:0x00e8  */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void onClick(android.view.View r8) {
                    /*
                        r7 = this;
                        dxs r8 = defpackage.dxs.this
                        com.autonavi.map.fragmentcontainer.page.MapBasePage r8 = r8.d
                        if (r8 == 0) goto L_0x0183
                        dxs r8 = defpackage.dxs.this
                        com.autonavi.map.fragmentcontainer.page.MapBasePage r8 = r8.d
                        boolean r8 = r8.isAlive()
                        if (r8 != 0) goto L_0x0016
                        goto L_0x0183
                    L_0x0016:
                        dyg r8 = r3
                        com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines r8 = defpackage.dxs.d(r8)
                        if (r8 == 0) goto L_0x0036
                        java.lang.String r0 = "1"
                        java.lang.String r8 = r8.getIs_realtime()
                        boolean r8 = r0.equals(r8)
                        if (r8 != 0) goto L_0x0036
                        java.lang.String r8 = "无到站信息，关注失败"
                        com.amap.bundle.utils.ui.ToastHelper.showToast(r8)
                        dxs r8 = defpackage.dxs.this
                        defpackage.dxs.o(r8)
                        return
                    L_0x0036:
                        dxs r8 = defpackage.dxs.this
                        dyg r0 = r3
                        btd r8 = r8.c(r0)
                        r0 = 0
                        r1 = 1
                        if (r8 == 0) goto L_0x00f7
                        dxs r2 = defpackage.dxs.this
                        com.autonavi.map.fragmentcontainer.page.MapBasePage r2 = r2.d
                        r2.getContext()
                        boolean r2 = r8.isFollow
                        if (r2 != 0) goto L_0x0095
                        r8.isFollow = r1
                        bso r2 = defpackage.bso.a()
                        java.lang.String r3 = r8.bus_id
                        java.lang.String r4 = r8.station_id
                        boolean r2 = r2.a(r3, r4)
                        if (r2 == 0) goto L_0x0068
                        bso r2 = defpackage.bso.a()
                        boolean r2 = r2.b(r8)
                        goto L_0x0074
                    L_0x0068:
                        bso r2 = defpackage.bso.a()
                        java.lang.Boolean r2 = r2.a(r8)
                        boolean r2 = r2.booleanValue()
                    L_0x0074:
                        if (r2 == 0) goto L_0x008c
                        com.amap.bundle.mapstorage.MapSharePreference r8 = new com.amap.bundle.mapstorage.MapSharePreference
                        com.amap.bundle.mapstorage.MapSharePreference$SharePreferenceName r2 = com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName.user_route_method_info
                        r8.<init>(r2)
                        java.lang.String r2 = "realbus_position_attention_need_roaledDB"
                        r8.putBooleanValue(r2, r1)
                        dxs r8 = defpackage.dxs.this
                        r8.b(r1)
                        defpackage.dwc.a(r1)
                        r8 = 1
                        goto L_0x00b4
                    L_0x008c:
                        r8.isFollow = r0
                        java.lang.String r8 = "关注失败"
                        com.amap.bundle.utils.ui.ToastHelper.showToast(r8)
                        goto L_0x00b3
                    L_0x0095:
                        r8.isFollow = r0
                        bso r2 = defpackage.bso.a()
                        java.lang.String r8 = r8.station_id
                        r2.b(r8)
                        dxs r8 = defpackage.dxs.this
                        dxs r2 = defpackage.dxs.this
                        dyg r2 = r2.M
                        r8.a(r2)
                        dxs r8 = defpackage.dxs.this
                        r8.b(r0)
                        defpackage.dwc.a(r0)
                    L_0x00b3:
                        r8 = 0
                    L_0x00b4:
                        dxs r2 = defpackage.dxs.this
                        com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r2 = r2.e
                        if (r2 == 0) goto L_0x00e0
                        dxs r2 = defpackage.dxs.this
                        com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r2 = r2.e
                        dwa r3 = r2.c
                        if (r3 == 0) goto L_0x00e0
                        dwa r3 = r2.c
                        r3.k()
                        dwa r3 = r2.c
                        com.autonavi.minimap.route.bus.realtimebus.model.BusStationData r4 = r2.i
                        if (r4 != 0) goto L_0x00d3
                        r4 = 0
                        goto L_0x00d9
                    L_0x00d3:
                        com.autonavi.minimap.route.bus.realtimebus.model.BusStationData r4 = r2.i
                        boolean r4 = r4.isFollow()
                    L_0x00d9:
                        com.autonavi.minimap.route.bus.realtimebus.model.BusStationData r5 = r2.i
                        r3.a(r4, r5)
                        r2.r = r1
                    L_0x00e0:
                        dxs r2 = defpackage.dxs.this
                        com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview.RealtimeInfoRecyclerView r2 = r2.H
                        if (r2 == 0) goto L_0x00fe
                        dxs r2 = defpackage.dxs.this
                        com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview.RealtimeInfoRecyclerView r2 = r2.H
                        dxs$6$1 r3 = new dxs$6$1
                        r3.<init>()
                        r2.post(r3)
                        goto L_0x00fe
                    L_0x00f7:
                        java.lang.String r8 = "关注失败"
                        com.amap.bundle.utils.ui.ToastHelper.showToast(r8)
                        r8 = 0
                    L_0x00fe:
                        dxs r2 = defpackage.dxs.this
                        dyg r2 = r2.M
                        dxs r3 = defpackage.dxs.this
                        dyg r3 = r3.M
                        int r3 = r3.b
                        java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines> r4 = r2.a
                        boolean r4 = defpackage.dxx.a(r4)
                        if (r4 != 0) goto L_0x017d
                        if (r3 < 0) goto L_0x017d
                        java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines> r4 = r2.a
                        int r4 = r4.size()
                        if (r3 >= r4) goto L_0x017d
                        java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines> r4 = r2.a
                        java.lang.Object r4 = r4.remove(r3)
                        com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines r4 = (com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines) r4
                        if (r8 == 0) goto L_0x014b
                        r8 = 0
                    L_0x0129:
                        if (r8 >= r3) goto L_0x0176
                        java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines> r1 = r2.a
                        java.lang.Object r1 = r1.get(r8)
                        com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines r1 = (com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines) r1
                        boolean r5 = r1.isFollow
                        if (r5 != 0) goto L_0x0138
                        goto L_0x0146
                    L_0x0138:
                        int r5 = r1.getIndexWithInt()
                        int r6 = r4.getIndexWithInt()
                        if (r5 <= r6) goto L_0x0148
                        boolean r1 = r1.isFollow
                        if (r1 == 0) goto L_0x0148
                    L_0x0146:
                        r0 = r8
                        goto L_0x0176
                    L_0x0148:
                        int r8 = r8 + 1
                        goto L_0x0129
                    L_0x014b:
                        java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines> r8 = r2.a
                        int r8 = r8.size()
                        int r0 = r8 + -1
                        java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines> r8 = r2.a
                        int r8 = r8.size()
                    L_0x0159:
                        if (r3 >= r8) goto L_0x0176
                        java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines> r1 = r2.a
                        java.lang.Object r1 = r1.get(r3)
                        com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines r1 = (com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines) r1
                        int r5 = r1.getIndexWithInt()
                        int r6 = r4.getIndexWithInt()
                        if (r5 <= r6) goto L_0x0173
                        boolean r1 = r1.isFollow
                        if (r1 != 0) goto L_0x0173
                        r0 = r3
                        goto L_0x0176
                    L_0x0173:
                        int r3 = r3 + 1
                        goto L_0x0159
                    L_0x0176:
                        r2.b = r0
                        java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RecommenStationLines> r8 = r2.a
                        r8.add(r0, r4)
                    L_0x017d:
                        dxs r8 = defpackage.dxs.this
                        defpackage.dxs.o(r8)
                        return
                    L_0x0183:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: defpackage.dxs.AnonymousClass6.onClick(android.view.View):void");
                }
            });
            dxs.k.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (!dxs.h(dxs.this, dxs.this.M)) {
                        ToastHelper.showToast("方向切换失败");
                    }
                    LogManager.actionLogV2("P00367", "B012");
                    dxs.o(dxs.this);
                }
            });
            dxs.l.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    String str = "";
                    String str2 = "";
                    btd g = dxs.this.c(dyg);
                    if (g != null) {
                        str = g.bus_id;
                        str2 = g.bus_areacode;
                    } else {
                        if (dxs.this.L != null && !dxx.a((Collection<T>) dxs.this.L.stations)) {
                            List<RealTimeBusStation> list = dxs.this.L.stations;
                            for (int i = 0; i < list.size(); i++) {
                                RealTimeBusStation realTimeBusStation = list.get(i);
                                if (realTimeBusStation != null) {
                                    str2 = realTimeBusStation.adcode;
                                    if (!TextUtils.isEmpty(str2)) {
                                        break;
                                    }
                                }
                            }
                        }
                        RecommenStationLines b2 = dxs.d(dyg);
                        if (b2 != null) {
                            str = b2.getLineid();
                        }
                    }
                    if (!TextUtils.isEmpty(str)) {
                        BusLineSearch.a(str, str2, (Callback<IBusLineSearchResult>) new BusRadarViewManager$16$1<IBusLineSearchResult>(this), true);
                    } else {
                        ToastHelper.showToast("查看失败");
                    }
                    dxs.o(dxs.this);
                }
            });
        }
    }

    static /* synthetic */ void d(dxs dxs, dyg dyg) {
        int i2;
        if (dyg == null) {
            dxs.r.setVisibility(0);
            dxs.t.setVisibility(4);
            dxs.u.setVisibility(4);
            dxs.v.setVisibility(4);
        } else {
            List<RecommenStationLines> list = dyg.a;
            if (list != null) {
                i2 = list.size();
                if (i2 <= 1 || dyg == null) {
                    dxs.u.setVisibility(0);
                    dxs.v.setVisibility(0);
                    dxs.u.setAlpha(0.8f);
                    dxs.v.setAlpha(0.64f);
                }
                dxs.u.setVisibility(8);
                dxs.v.setVisibility(8);
                return;
            }
        }
        i2 = 0;
        if (i2 <= 1) {
        }
        dxs.u.setVisibility(0);
        dxs.v.setVisibility(0);
        dxs.u.setAlpha(0.8f);
        dxs.v.setAlpha(0.64f);
    }

    static /* synthetic */ void g(dxs dxs) {
        if (!NetworkReachability.b()) {
            dxs.a(R.string.toast_realtime_network_error);
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        boolean z2 = Math.abs(dxs.C - currentTimeMillis) < 2000;
        eao.e("busrada", "onQuickRefresh ".concat(String.valueOf(z2)));
        if (z2) {
            if (dxs.e != null) {
                RouteRealtimeListenerImpl routeRealtimeListenerImpl = dxs.e;
                if (routeRealtimeListenerImpl.c != null) {
                    dwa dwa = routeRealtimeListenerImpl.c;
                    if (AMapPageUtil.isHomePage() && dwa.g != null) {
                        dwa.g.updateTipMsg();
                    }
                }
            }
            dwc.b(2);
        } else {
            dwc.b(1);
        }
        dxs.C = dxs.B;
        dxs.B = currentTimeMillis;
        if (!dxs.E) {
            dxs.a(true);
            if (dxs.M != null) {
                RouteRealtimeListenerImpl routeRealtimeListenerImpl2 = dxs.e;
                dyg dyg = dxs.M;
                if (dyg != null) {
                    if (dyg.d != null) {
                        routeRealtimeListenerImpl2.a(dyg.d, dyg.a, dyg.b);
                    } else {
                        routeRealtimeListenerImpl2.a(dyg.a, dyg.b, (String) "");
                    }
                }
                dxs.E = true;
                return;
            }
            dxs.e.a(dxs.L, false, -1);
        }
    }

    static /* synthetic */ void a(dxs dxs, final View view, boolean z2, final dyg dyg) {
        final int i2;
        final int a2 = z2 ? agn.a(dxs.d.getContext(), 52.0f) : 0;
        if (z2) {
            i2 = 0;
        } else {
            i2 = agn.a(dxs.d.getContext(), 52.0f);
        }
        view.clearAnimation();
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{0, 200});
        ofInt.setDuration(400);
        ofInt.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                if (dxs.this.d.isAlive()) {
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    int i = ((int) ((((float) (a2 - i2)) / 200.0f) * ((float) intValue))) + i2;
                    if (i < 0) {
                        i = 0;
                    }
                    eao.e("showRealtimeMoreBt ---> ", "showRealtimeMoreBt VALUE".concat(String.valueOf(intValue)));
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.width = i;
                    view.setLayoutParams(layoutParams);
                    view.requestLayout();
                }
            }
        });
        if (!z2) {
            dxs.g.setVisibility(8);
            dxs.J.setVisibility(0);
            dxs.H.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (dxs.this.d.isAlive()) {
                        dxs.this.H.startNewOutAnimation(-1, dyg.b);
                    }
                }
            });
            dxs.I.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    if (dxs.this.d.isAlive()) {
                        dxs.this.H.startNewOutAnimation(-1, dxs.this.M.b);
                    }
                }
            });
            dxs.H.setAdapter(dxs.a);
            dxs.H.setRealtimeInfoStatusListener(new com.autonavi.minimap.route.bus.realtimebus.view.fliprecyclerview.RealtimeInfoRecyclerView.a() {
                public final void a() {
                    dxs.i(dxs.this, dyg);
                    if (dxs.this.e != null) {
                        RouteRealtimeListenerImpl p = dxs.this.e;
                        p.r = false;
                        p.c();
                    }
                }

                public final void a(int i) {
                    dxs.this.J.setVisibility(8);
                    dxs.this.g.setVisibility(0);
                    dxs.this.d();
                    dxs.this.H.scrollToPosition(i + 1);
                    if (dyg != null && !dxx.a((Collection<T>) dyg.a) && i < dyg.a.size()) {
                        dyg.b = i;
                    }
                    dxs.this.c();
                    dxs.this.e(dyg);
                    dxs.b(dxs.this, dyg);
                    dxs.d(dxs.this, dyg);
                    dxs.a(dxs.this, dxs.this.q, true, dyg);
                    if (dyg != null) {
                        dxs.this.e.a(dyg.a, dyg.b, (String) "");
                    }
                }
            });
            dxs.H.post(new Runnable() {
                public final void run() {
                    if (dxs.this.a != null) {
                        ((LinearLayoutManager) dxs.this.H.getLayoutManager()).scrollToPositionWithOffset(dxs.this.a.g + 1, dxs.this.a.a());
                    }
                    dxs.this.H.startEnterAnimation();
                }
            });
            dxs.H.setVisibility(0);
            dxs.J.setVisibility(0);
            new ArrayList();
            dxs.a.a = new b() {
                public final void a(View view, int i) {
                    if (dxs.this.H != null) {
                        int childCount = dxs.this.H.getChildCount();
                        int i2 = 1;
                        int i3 = 1;
                        while (true) {
                            if (i3 >= childCount) {
                                break;
                            } else if (view == dxs.this.H.getChildAt(i3)) {
                                i2 = i3;
                                break;
                            } else {
                                i3++;
                            }
                        }
                        dxs.this.H.startNewOutAnimation(i2, i);
                    }
                }
            };
        }
        view.requestLayout();
        ofInt.start();
    }

    static /* synthetic */ void o(dxs dxs) {
        if (dxs.h != null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(dxs.h.findViewById(R.id.realtime_tip_more_function_content), "alpha", new float[]{1.0f, 0.0f});
            ofFloat.setDuration(375);
            ofFloat.setInterpolator(new DecelerateInterpolator());
            ofFloat.start();
            dxs.i.stopAnimation(new AnimatorListener() {
                public final void onAnimationCancel(Animator animator) {
                }

                public final void onAnimationRepeat(Animator animator) {
                }

                public final void onAnimationStart(Animator animator) {
                }

                public final void onAnimationEnd(Animator animator) {
                    if (dxs.this.d != null && dxs.this.d.isAlive()) {
                        dxs.this.h.setVisibility(8);
                        dxs.this.g.setVisibility(0);
                        dxs.this.e(dxs.this.M);
                        dxs.b(dxs.this, dxs.this.M);
                    }
                }
            });
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b4  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00b6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ boolean h(defpackage.dxs r18, defpackage.dyg r19) {
        /*
            if (r19 == 0) goto L_0x00c3
            r1 = r18
            com.autonavi.minimap.route.inter.impl.RouteRealtimeListenerImpl r1 = r1.e
            dwa r2 = r1.c
            if (r2 == 0) goto L_0x00c1
            dwa r2 = r1.c
            java.lang.String r3 = r1.m
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L_0x00a9
            java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.BusStationData> r4 = r2.p
            int r4 = r4.size()
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = -1
        L_0x001e:
            if (r7 >= r4) goto L_0x0093
            java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.BusStationData> r11 = r2.p
            java.lang.Object r11 = r11.get(r7)
            com.autonavi.minimap.route.bus.realtimebus.model.BusStationData r11 = (com.autonavi.minimap.route.bus.realtimebus.model.BusStationData) r11
            if (r11 == 0) goto L_0x0089
            java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation> r12 = r11.stations
            boolean r12 = defpackage.dxx.a(r12)
            if (r12 != 0) goto L_0x0089
            java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation> r12 = r11.stations
            int r12 = r12.size()
            r13 = r10
            r10 = r9
            r9 = r8
            r8 = 0
        L_0x003c:
            if (r8 >= r12) goto L_0x0080
            java.util.List<com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation> r14 = r11.stations
            java.lang.Object r14 = r14.get(r8)
            com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation r14 = (com.autonavi.minimap.route.bus.realtimebus.model.RealTimeBusStation) r14
            java.lang.String r15 = r14.bus_id
            boolean r15 = r3.equals(r15)
            if (r15 == 0) goto L_0x0074
            com.autonavi.common.model.GeoPoint r15 = new com.autonavi.common.model.GeoPoint
            java.lang.Double r5 = r14.station_lon
            r16 = r1
            double r0 = r5.doubleValue()
            java.lang.Double r5 = r14.station_lat
            r17 = r7
            double r6 = r5.doubleValue()
            r15.<init>(r0, r6)
            com.autonavi.common.model.GeoPoint r0 = r2.B
            int r0 = defpackage.cfe.b(r0, r15)
            r1 = -1
            if (r1 == r13) goto L_0x006e
            if (r0 >= r13) goto L_0x0079
        L_0x006e:
            r11.selectedLineIndex = r8
            r13 = r0
            r9 = r11
            r10 = r15
            goto L_0x0079
        L_0x0074:
            r16 = r1
            r17 = r7
            r1 = -1
        L_0x0079:
            int r8 = r8 + 1
            r1 = r16
            r7 = r17
            goto L_0x003c
        L_0x0080:
            r16 = r1
            r17 = r7
            r1 = -1
            r8 = r9
            r9 = r10
            r10 = r13
            goto L_0x008e
        L_0x0089:
            r16 = r1
            r17 = r7
            r1 = -1
        L_0x008e:
            int r7 = r17 + 1
            r1 = r16
            goto L_0x001e
        L_0x0093:
            r16 = r1
            if (r8 == 0) goto L_0x00ab
            int r0 = r8.pointItemIndex
            r2.J = r0
            if (r9 == 0) goto L_0x00a7
            r2.a(r8)
            bty r0 = r2.l
            com.autonavi.common.model.GeoPoint r1 = r2.B
            r0.a(r1)
        L_0x00a7:
            r2 = r8
            goto L_0x00ac
        L_0x00a9:
            r16 = r1
        L_0x00ab:
            r2 = 0
        L_0x00ac:
            if (r2 == 0) goto L_0x00b0
            r0 = 1
            goto L_0x00b1
        L_0x00b0:
            r0 = 0
        L_0x00b1:
            r3 = 0
            if (r2 != 0) goto L_0x00b6
            r4 = 0
            goto L_0x00b9
        L_0x00b6:
            int r1 = r2.selectedLineIndex
            r4 = r1
        L_0x00b9:
            r5 = -1
            r6 = 1
            r1 = r16
            r1.a(r2, r3, r4, r5, r6)
            goto L_0x00c2
        L_0x00c1:
            r0 = 0
        L_0x00c2:
            return r0
        L_0x00c3:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dxs.h(dxs, dyg):boolean");
    }

    static /* synthetic */ void i(dxs dxs, dyg dyg) {
        if (dxs.d.isAlive() && dyg != null) {
            List<RecommenStationLines> list = dyg.a;
            String str = "";
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();
            AnonymousClass17 r3 = new dyc<RealtimeBuses>() {
                public final /* synthetic */ void a(Object obj) {
                    RealtimeBuses realtimeBuses = (RealtimeBuses) obj;
                    eao.e("HeartBeatManager", " send onSuccess");
                    a(dxs.this.b, realtimeBuses);
                    if (realtimeBuses == null) {
                        dxs.this.N = dxs.this.N + 1;
                        if (dxs.this.N == 5) {
                            ToastHelper.showToast("暂时无法获取实时公交数据");
                            if (dxs.this.H != null) {
                                dxs.this.H.post(new Runnable() {
                                    public final void run() {
                                        if (dxs.this.a != null) {
                                            dxs.this.a.b();
                                        }
                                    }
                                });
                            }
                        }
                    } else {
                        dxs.this.N = 0;
                    }
                    if (dxs.this.d != null && dxs.this.d.isAlive()) {
                        dxs.a(dxs.this, realtimeBuses);
                    }
                }

                public final void a() {
                    eao.e("HeartBeatManager", " send onFailure");
                    dxs.this.N = dxs.this.N + 1;
                    if (dxs.this.N == 5) {
                        ToastHelper.showToast("暂时无法获取实时公交数据");
                        if (dxs.this.H != null) {
                            dxs.this.H.post(new Runnable() {
                                public final void run() {
                                    if (dxs.this.a != null) {
                                        dxs.this.a.b();
                                    }
                                }
                            });
                        }
                    }
                }
            };
            RealTimeBusStation realTimeBusStation = dxs.L.stations.get(0);
            if (realTimeBusStation != null) {
                str = realTimeBusStation.adcode;
            }
            if (!dxx.a((Collection<T>) list)) {
                RecommenStationLines recommenStationLines = list.get(0);
                if (recommenStationLines != null) {
                    sb.append(recommenStationLines.getLineid());
                    sb2.append(recommenStationLines.getStationid());
                }
                for (int i2 = 1; i2 < list.size(); i2++) {
                    RecommenStationLines recommenStationLines2 = list.get(i2);
                    if (recommenStationLines2 != null) {
                        sb.append(",");
                        sb.append(recommenStationLines2.getLineid());
                        sb2.append(",");
                        sb2.append(recommenStationLines2.getStationid());
                    }
                }
            }
            dxs.d();
            dxs.c = new a(0);
            dxs.c.a = str;
            dxs.c.b = sb.toString();
            dxs.c.c = sb2.toString();
            dxs.c.d = r3;
            dxs.b = dxs.a(dxs.c);
        }
    }

    static /* synthetic */ void a(dxs dxs, final RealtimeBuses realtimeBuses) {
        if (dxs.d != null && dxs.d.isAlive()) {
            dxs.a(false);
            if (dxs.H != null) {
                dxs.H.post(new Runnable() {
                    public final void run() {
                        if (dxs.this.a != null) {
                            dxs.this.a.a(realtimeBuses);
                        }
                    }
                });
            }
        }
    }
}
