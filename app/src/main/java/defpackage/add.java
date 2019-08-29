package defpackage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drive.api.IMotorGuideManager;
import com.amap.bundle.drive.api.ITruckGuideManager;
import com.amap.bundle.drive.setting.quicknaviwidget.main.QuickAutonNaviSettingFragment;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.planhome.ajx.ModuleHome;
import com.amap.bundle.planhome.page.AbstractPlanHomePage;
import com.amap.bundle.planhome.page.PlanHomePage;
import com.amap.bundle.planhome.view.RippleLayout;
import com.amap.bundle.planhome.view.RouteInputViewContainer;
import com.amap.bundle.planhome.view.RouteInputViewContainer.TitleStyle;
import com.amap.bundle.planhome.view.RoutePageContainer;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.inter.IRouteUI.ContainerType;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.model.RouteHeaderModel;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.PageContainer;
import com.autonavi.map.fragmentcontainer.page.PageContainer.PageAnimationListener;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.Builder;
import com.autonavi.map.fragmentcontainer.page.dialog.NodeAlertDialogPage.NodeDialogFragmentOnClickListener;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.route.common.view.RouteViewGroup;
import com.autonavi.minimap.route.common.view.RouteViewGroup.a;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.vcs.NativeVcsManager;
import com.autonavi.widget.ui.TitleBar;
import com.tencent.connect.common.Constants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: add reason: default package */
/* compiled from: PlanHomePresenter */
public class add extends eaf<AbstractPlanHomePage> implements axe, bgo, IRouteUI {
    private static final String b = "add";
    /* access modifiers changed from: 0000 */
    public RoutePageContainer a;
    /* access modifiers changed from: private */
    public acq c;
    /* access modifiers changed from: private */
    public axe d;
    /* access modifiers changed from: private */
    public axe e;
    /* access modifiers changed from: private */
    public dlg f;
    private boolean g;
    /* access modifiers changed from: private */
    public String h;
    private String i;
    /* access modifiers changed from: private */
    public int j;
    /* access modifiers changed from: private */
    public boolean k = false;
    private boolean l = false;
    private boolean m = false;
    private boolean n;
    private ArrayList<View> o = new ArrayList<>();
    private act p;
    /* access modifiers changed from: private */
    public boolean q;
    private acn r;
    /* access modifiers changed from: private */
    public acm s;
    private RouteInputViewContainer t;

    public add(AbstractPlanHomePage abstractPlanHomePage) {
        super(abstractPlanHomePage);
    }

    public void onPageCreated() {
        int i2;
        super.onPageCreated();
        AbstractPlanHomePage abstractPlanHomePage = (AbstractPlanHomePage) this.mPage;
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            abstractPlanHomePage.b = mapView.v();
            abstractPlanHomePage.c = mapView.J();
            abstractPlanHomePage.a = mapView.I();
            abstractPlanHomePage.d = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
        }
        this.c = acq.a();
        this.r = new acn((AbstractPlanHomePage) this.mPage);
        this.s = acm.a().a((AbstractPlanHomePage) this.mPage, this);
        adf.a().a((ada) aco.a());
        bdo bdo = (bdo) a.a.a(bdo.class);
        if (bdo != null) {
            bdo.d();
        }
        View contentView = ((AbstractPlanHomePage) this.mPage).getContentView();
        ArrayList arrayList = new ArrayList();
        arrayList.add(contentView.findViewById(R.id.route_input_view_container));
        arrayList.add(contentView.findViewById(R.id.route_input_view_container_shadow));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(contentView.findViewById(R.id.route_flow_container));
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(((AbstractPlanHomePage) this.mPage).getPageContainer());
        eez a2 = eez.a();
        a2.a = arrayList;
        a2.b = arrayList2;
        a2.c = arrayList3;
        a2.d = new ContainerType[]{ContainerType.FLOW_VIEW, ContainerType.HEAD_VIEW, ContainerType.CONTAINER_VIEW};
        this.t = (RouteInputViewContainer) contentView.findViewById(R.id.route_input_view_container);
        this.t.setParentView((RelativeLayout) ((AbstractPlanHomePage) this.mPage).getContentView());
        this.t.setInputEventListener(this);
        this.t.setVisibility(0);
        this.c.b = this.t;
        a(new ContainerType[]{ContainerType.FLOW_VIEW, ContainerType.HEAD_VIEW, ContainerType.CONTAINER_VIEW});
        boolean b2 = ack.b();
        HashSet hashSet = new HashSet();
        if (b2) {
            hashSet.add(RouteType.ETRIP);
        }
        if (ack.e()) {
            hashSet.add(RouteType.TAXI);
        }
        if (ack.a()) {
            hashSet.add(RouteType.FREERIDE);
        }
        hashSet.add(RouteType.CAR);
        hashSet.add(RouteType.TRUCK);
        hashSet.add(RouteType.BUS);
        if (lt.a().g()) {
            hashSet.add(RouteType.RIDE);
        }
        hashSet.add(RouteType.ONFOOT);
        if (ack.c()) {
            hashSet.add(RouteType.AIRTICKET);
        }
        hashSet.add(RouteType.TRAIN);
        if (lt.a().f()) {
            hashSet.add(RouteType.COACH);
        }
        if (ack.d()) {
            hashSet.add(RouteType.MOTOR);
        }
        List<RouteType> a3 = acs.a();
        boolean equals = TextUtils.equals(new MapSharePreference((String) "route_tab_index_sp_data").getStringValue("route_tab_move_car_first_920", ""), "1");
        if (!equals) {
            new MapSharePreference((String) "route_tab_index_sp_data").putStringValue("route_tab_move_car_first_920", "1");
            a3.remove(RouteType.CAR);
        }
        List<RouteType> a4 = a(a3, (Set<RouteType>) hashSet);
        acq acq = this.c;
        if (!(acq.b == null || a4 == null)) {
            acq.b.setRouteTypes(a4);
        }
        if ((!equals || a3.size() == 0) && !this.k) {
            acs.a(a4);
        }
        b(adf.a().b());
        this.a = (RoutePageContainer) ((AbstractPlanHomePage) this.mPage).getPageContainer();
        if (this.a != null) {
            this.a.setHorizontalFadingEdgeEnabled(false);
            this.a.setFadingEdgeLength(0);
            this.a.setRouteInputUI(this);
            this.a.setPageAnimationListener(new PageAnimationListener() {
                public final void onPageAnimationDone() {
                    add.o(add.this);
                }
            });
            acl.a().a = this.a;
        }
        ((RouteViewGroup) contentView.findViewById(R.id.route_view_group)).setStatusListener(new a() {
            public final void a() {
                if (add.this.k) {
                    add.l(add.this);
                    return;
                }
                acq m = add.this.c;
                if (m.b != null) {
                    m.b.prepareEnterAnimator();
                }
                add.this.D();
            }
        });
        x();
        y();
        B();
        if (agr.b(((AbstractPlanHomePage) this.mPage).getContext())) {
            PageBundle arguments = ((AbstractPlanHomePage) this.mPage).getArguments();
            if (arguments == null || (!arguments.containsKey("key_type") && !arguments.containsKey("bundle_key_route_type"))) {
                POI b3 = ade.a().b(true);
                POI d2 = ade.a().d(true);
                if (!(b3 == null || d2 == null)) {
                    String a5 = lo.a().a((String) "SmartTripMode");
                    if (!TextUtils.isEmpty(a5)) {
                        try {
                            i2 = new JSONObject(a5).getInt("SmartTripModeSwitch");
                        } catch (JSONException unused) {
                            i2 = 0;
                        }
                        if (i2 != 0) {
                            this.p = new act();
                            this.p.a = new a() {
                                public final void a(String str) {
                                    final TextView textView = (TextView) ((AbstractPlanHomePage) add.this.mPage).getContentView().findViewById(R.id.smart_trip_description);
                                    ((View) textView.getParent()).addOnLayoutChangeListener(new OnLayoutChangeListener() {
                                        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                                            if (i4 - i2 != i8 - i6) {
                                                if (add.this.d != null) {
                                                    PageBundle pageBundle = new PageBundle();
                                                    pageBundle.putBoolean(IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE.name(), true);
                                                    pageBundle.putInt("tipsHeight", textView.getHeight());
                                                    add.this.d.a(IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE, pageBundle);
                                                    ((View) textView.getParent()).removeOnLayoutChangeListener(this);
                                                }
                                                if (add.this.e != null) {
                                                    PageBundle pageBundle2 = new PageBundle();
                                                    pageBundle2.putBoolean(IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE.name(), true);
                                                    pageBundle2.putInt("tipsHeight", textView.getHeight());
                                                    add.this.e.a(IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE, pageBundle2);
                                                    ((View) textView.getParent()).removeOnLayoutChangeListener(this);
                                                }
                                            }
                                        }
                                    });
                                    textView.setVisibility(0);
                                    textView.setText(str);
                                    textView.setContentDescription(str);
                                    add.this.q = true;
                                }

                                public final void a() {
                                    add.this.u();
                                }
                            };
                            j(this.p.a(adf.a().b(), b3.getPoint(), d2.getPoint(), d2.getType()));
                        }
                    }
                }
            }
        }
        v();
        w();
        z();
        bfo bfo = (bfo) a.a.a(bfo.class);
        if (bfo != null) {
            bfo.a((IRouteUI) this);
        }
        if (!this.k) {
            acw.a().onCreate();
            aco a6 = aco.a();
            ViewGroup a7 = this.c.a(RouteType.TAXI);
            if (a7 != null) {
                a6.b = (RippleLayout) ((ViewGroup) LayoutInflater.from(AMapPageUtil.getPageContext().getContext()).inflate(R.layout.plan_input_taxi_ripple, a7, true)).findViewById(R.id.ripple_layout);
                a6.b.setClickable(false);
            }
        }
    }

    public void onResume() {
        super.onResume();
        this.c.b = this.t;
        acl.a().a = this.a;
        this.s = acm.a().a((AbstractPlanHomePage) this.mPage, this);
    }

    /* access modifiers changed from: private */
    public void u() {
        final TextView textView = (TextView) ((AbstractPlanHomePage) this.mPage).getContentView().findViewById(R.id.smart_trip_description);
        if (textView.getVisibility() == 0) {
            final int height = textView.getHeight();
            ((View) textView.getParent()).addOnLayoutChangeListener(new OnLayoutChangeListener() {
                public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    if (i4 - i2 != i8 - i6) {
                        if (add.this.d != null) {
                            PageBundle pageBundle = new PageBundle();
                            pageBundle.putBoolean(IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE.name(), false);
                            pageBundle.putInt("tipsHeight", height);
                            add.this.d.a(IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE, pageBundle);
                            ((View) textView.getParent()).removeOnLayoutChangeListener(this);
                        }
                        if (add.this.e != null) {
                            PageBundle pageBundle2 = new PageBundle();
                            pageBundle2.putBoolean(IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE.name(), false);
                            pageBundle2.putInt("tipsHeight", height);
                            add.this.e.a(IRouteHeaderEvent.HEAD_SMART_HEAD_TIPS_CHANGE, pageBundle2);
                            ((View) textView.getParent()).removeOnLayoutChangeListener(this);
                        }
                    }
                }
            });
            textView.setVisibility(8);
            this.q = false;
        }
    }

    private void v() {
        if (((AbstractPlanHomePage) this.mPage).isAlive()) {
            if (this.m) {
                a(true);
                this.c.a(TitleStyle.FAVORITATE_STYLE);
                View findViewById = ((AbstractPlanHomePage) this.mPage).getContentView().findViewById(R.id.route_fragment_header_with_shadow);
                if (findViewById != null) {
                    TitleBar titleBar = (TitleBar) findViewById.findViewById(R.id.route_favorite_title_bar);
                    titleBar.setVisibility(0);
                    titleBar.setTitle(((AbstractPlanHomePage) this.mPage).getString(R.string.route_save_route));
                    titleBar.setOnBackClickListener((OnClickListener) new OnClickListener() {
                        public final void onClick(View view) {
                            if (((AbstractPlanHomePage) add.this.mPage).isAlive()) {
                                add.this.F();
                                add.this.G();
                            }
                        }
                    });
                }
            } else if (this.k) {
                this.c.a(TitleStyle.ETRIP_STYLE);
                View j2 = this.c.j();
                if (j2 != null) {
                    ImageView imageView = (ImageView) j2.findViewById(R.id.title_btn_left);
                    imageView.setVisibility(0);
                    if (euk.a()) {
                        LayoutParams layoutParams = (LayoutParams) imageView.getLayoutParams();
                        layoutParams.topMargin += euk.a(imageView.getContext());
                        imageView.setLayoutParams(layoutParams);
                    }
                    imageView.setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            add.C();
                            if (((AbstractPlanHomePage) add.this.mPage).isAlive()) {
                                ((AbstractPlanHomePage) add.this.mPage).finish();
                            }
                        }
                    });
                }
            } else if (this.l) {
                a(true);
                this.c.a(TitleStyle.VOICE_STYLE);
            } else {
                a(true);
                this.c.a(TitleStyle.NORMAL);
            }
        }
    }

    public final void a(boolean z) {
        if (this.a != null) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.a.getLayoutParams();
            if (z) {
                layoutParams.topMargin = -agn.a(AMapPageUtil.getAppContext(), 10.5f);
            } else {
                layoutParams.topMargin = 0;
            }
            this.a.setLayoutParams(layoutParams);
        }
    }

    private static List<RouteType> a(List<RouteType> list, Set<RouteType> set) {
        ArrayList arrayList = new ArrayList();
        if (list == null || list.size() == 0) {
            return a(set, list);
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            RouteType routeType = list.get(i2);
            if (set.contains(routeType)) {
                arrayList.add(routeType);
                set.remove(routeType);
            }
        }
        return a(set, (List<RouteType>) arrayList);
    }

    private static List<RouteType> a(Set<RouteType> set, List<RouteType> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(RouteType.CAR);
        arrayList2.add(RouteType.TAXI);
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add(RouteType.DEFAULT);
        arrayList3.add(RouteType.FREERIDE);
        arrayList3.add(RouteType.BUS);
        arrayList3.add(RouteType.RIDE);
        arrayList3.add(RouteType.ONFOOT);
        arrayList3.add(RouteType.AIRTICKET);
        arrayList3.add(RouteType.TRAIN);
        arrayList3.add(RouteType.COACH);
        arrayList3.add(RouteType.TRUCK);
        arrayList3.add(RouteType.MOTOR);
        ato ato = (ato) a.a.a(ato.class);
        if (!(ato == null || ato.a().b(2) == null)) {
            arrayList3.remove(RouteType.TRUCK);
            arrayList2.add(2, RouteType.TRUCK);
        }
        int size = arrayList2.size();
        for (int i2 = 0; i2 < size; i2++) {
            RouteType routeType = (RouteType) arrayList2.get(i2);
            if (set.contains(routeType)) {
                arrayList.add(routeType);
            }
        }
        if (list != null && list.size() > 0) {
            arrayList.addAll(list);
        }
        int size2 = arrayList3.size();
        for (int i3 = 0; i3 < size2; i3++) {
            RouteType routeType2 = (RouteType) arrayList3.get(i3);
            if (set.contains(routeType2)) {
                arrayList.add(routeType2);
            }
        }
        return arrayList;
    }

    private static void a(RouteType routeType, RouteType routeType2) {
        if (routeType != routeType2 && (routeType == RouteType.ETRIP || routeType2 == RouteType.ETRIP)) {
            try {
                if (routeType == RouteType.ETRIP) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("type", d(routeType2));
                    LogManager.actionLogV2("P00331", "B003", jSONObject);
                    return;
                }
                if (routeType2 == RouteType.ETRIP) {
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("from", d(routeType));
                    LogManager.actionLogV2("P00331", "B001", jSONObject2);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    private static String d(RouteType routeType) {
        switch (routeType) {
            case TAXI:
                return FunctionSupportConfiger.TAXI_TAG;
            case CAR:
                return "car";
            case TRUCK:
                return DriveUtil.NAVI_TYPE_TRUCK;
            case BUS:
                return ShowRouteActionProcessor.SEARCH_TYPE_BUS;
            case RIDE:
                return ShowRouteActionProcessor.SEARCH_TYPE_RIDE;
            case ONFOOT:
                return ShowRouteActionProcessor.SEARCH_TYPE_WALK;
            case TRAIN:
                return "train";
            case COACH:
                return "coach";
            default:
                return null;
        }
    }

    private void b(RouteType routeType, RouteType routeType2) {
        if (routeType != routeType2) {
            RouteType[] routeTypeArr = {RouteType.CAR, RouteType.BUS, RouteType.ONFOOT, RouteType.RIDE, RouteType.TRAIN, RouteType.COACH, RouteType.TAXI, RouteType.TRUCK, RouteType.ETRIP, RouteType.FREERIDE};
            int i2 = 0;
            int i3 = 0;
            for (int i4 = 0; i4 < 10; i4++) {
                if (routeTypeArr[i4] == routeType) {
                    i2 = i4;
                }
                if (routeTypeArr[i4] == routeType2) {
                    i3 = i4;
                }
            }
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("from", i2);
                jSONObject.put("status", i3);
                LogManager.actionLogV2(LogConstant.PAGE_ID_CAR_RESULT_MAP, LogConstant.MAIN_LOCATION_DETAIL, jSONObject);
                HashMap hashMap = new HashMap();
                hashMap.put("from", routeType.getKeyName());
                hashMap.put("to", routeType2.getKeyName());
                kd.b("amap.P00373.0.D009", hashMap);
                if (routeType == RouteType.BUS && this.a != null && this.a.getPageLevel() == 2) {
                    String str = "";
                    int value = routeType2.getValue();
                    if (value != 0) {
                        switch (value) {
                            case 2:
                                str = "BC";
                                break;
                            case 3:
                                str = "QX";
                                break;
                            case 4:
                                str = "HC";
                                break;
                            case 5:
                                str = "KC";
                                break;
                        }
                    } else {
                        str = "JC";
                    }
                    JSONObject jSONObject2 = new JSONObject();
                    jSONObject2.put("action", str);
                    LogManager.actionLogV2("P00018", "B031", jSONObject2);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void w() {
        if (!this.k) {
            e(adf.a().b());
        }
    }

    private void e(RouteType routeType) {
        if (routeType != null) {
            if (routeType == RouteType.TAXI && g(RouteType.TAXI) == null) {
                this.c.c(RouteType.TAXI);
            }
            i(routeType);
            h(routeType);
        }
    }

    private void x() {
        RouteType f2 = f(k(acr.a()));
        PageBundle arguments = ((AbstractPlanHomePage) this.mPage).getArguments();
        if (arguments == null) {
            j(f2);
            return;
        }
        boolean z = false;
        if (arguments.containsKey("plan_bundle_schema_agent") && arguments.getBoolean("plan_bundle_schema_agent", false) && arguments.containsKey("bundle_key_route_type")) {
            f2 = (RouteType) arguments.getObject("bundle_key_route_type");
        }
        if (arguments.containsKey("key_type")) {
            f2 = RouteType.getType(arguments.getInt("key_type", RouteType.CAR.getValue()));
        }
        if (arguments.containsKey("bundle_key_route_type")) {
            f2 = (RouteType) arguments.getObject("bundle_key_route_type");
        }
        if (!RouteType.TAXI.equals(f2)) {
            f2 = f(f2);
        }
        if (arguments.getBoolean("key_favorites", false) && f2 == RouteType.ONFOOT) {
            z = true;
        }
        this.m = z;
        if (arguments.getInt("key_source", 100) == 102) {
            this.k = true;
        }
        if (arguments.containsKey("voice_process")) {
            this.l = true;
        }
        j(f2);
    }

    private void y() {
        PageBundle arguments = ((AbstractPlanHomePage) this.mPage).getArguments();
        if (arguments != null) {
            if (arguments.containsKey("key_result")) {
                IRouteResultData iRouteResultData = (IRouteResultData) arguments.get("key_result");
                if (iRouteResultData != null) {
                    POI fromPOI = iRouteResultData.getFromPOI();
                    POI toPOI = iRouteResultData.getToPOI();
                    this.g = true;
                    a(fromPOI, toPOI, RouteType.DEFAULT);
                    return;
                }
            } else if (this.k) {
                a((POI) arguments.getObject("bundle_key_poi_start"), (POI) arguments.getObject("bundle_key_poi_end"));
                return;
            }
            POI poi = null;
            POI poi2 = arguments.containsKey("bundle_key_poi_start") ? (POI) arguments.get("bundle_key_poi_start") : null;
            if (arguments.containsKey("bundle_key_poi_mids")) {
                ade.a().a((List) arguments.get("bundle_key_poi_mids"));
            }
            if (arguments.containsKey("bundle_key_poi_end")) {
                poi = (POI) arguments.get("bundle_key_poi_end");
            }
            a(poi2, poi, RouteType.DEFAULT);
            boolean z = arguments.getBoolean("bundle_key_from_scheme", false);
            if (RouteType.BUS == adf.a().b() && z) {
                if (poi2 == null) {
                    ToastHelper.showLongToast(((AbstractPlanHomePage) this.mPage).getString(R.string.act_fromto_error_emptystart));
                    return;
                } else if (poi == null) {
                    ToastHelper.showLongToast(((AbstractPlanHomePage) this.mPage).getString(R.string.act_fromto_error_emptyend));
                    return;
                }
            }
            if (arguments.containsKey("bundle_key_auto_route")) {
                this.g = arguments.getBoolean("bundle_key_auto_route", false);
            }
            if (arguments.containsKey("bundle_key_keyword")) {
                this.h = arguments.getString("bundle_key_keyword");
            }
            if (arguments.containsKey("bundle_key_method")) {
                this.i = arguments.getString("bundle_key_method");
            }
            if (arguments.containsKey(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE)) {
                this.j = arguments.getInt(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE, 1002);
            }
            if (arguments.containsKey(Constants.KEY_ACTION) && "actiono_back_scheme".equals(arguments.getString(Constants.KEY_ACTION))) {
                this.f = (dlg) arguments.getObject("key_back_scheme_param");
            }
        }
    }

    private static RouteType f(RouteType routeType) {
        if (!ack.b() && routeType == RouteType.ETRIP) {
            routeType = RouteType.CAR;
        }
        if (!lt.a().g() && routeType == RouteType.RIDE) {
            routeType = RouteType.CAR;
        }
        if (!lt.a().f() && routeType == RouteType.COACH) {
            routeType = RouteType.CAR;
        }
        if (!ack.e() && routeType == RouteType.TAXI) {
            routeType = RouteType.CAR;
        }
        if (!ack.a() && routeType == RouteType.FREERIDE) {
            routeType = RouteType.CAR;
        }
        if (!ack.c() && routeType == RouteType.AIRTICKET) {
            routeType = RouteType.CAR;
        }
        return (ack.d() || routeType != RouteType.MOTOR) ? routeType : RouteType.CAR;
    }

    private void z() {
        if (this.k) {
            PageBundle arguments = ((AbstractPlanHomePage) this.mPage).getArguments();
            RouteType type = (arguments == null || !arguments.containsKey("key_type")) ? null : RouteType.getType(arguments.getInt("key_type", RouteType.CAR.getValue()));
            if (type != null) {
                n();
                this.a.switchPage(type, ((AbstractPlanHomePage) this.mPage).getArguments());
                return;
            }
            return;
        }
        RouteType b2 = adf.a().b();
        if (!this.g || !acj.a()) {
            this.a.switchPage(b2, ((AbstractPlanHomePage) this.mPage).getArguments());
            if (!TextUtils.isEmpty(this.h) && !TextUtils.isEmpty(this.i)) {
                A();
                return;
            }
            return;
        }
        this.c.g();
        this.a.setPageLevel(2);
        this.a.switchPage(b2, ((AbstractPlanHomePage) this.mPage).getArguments());
    }

    private void A() {
        ((AbstractPlanHomePage) this.mPage).getContentView().postDelayed(new Runnable() {
            public final void run() {
                if (add.this.j == 1001) {
                    add.this.s.a(add.this.h, 1001, ((AbstractPlanHomePage) add.this.mPage).getString(R.string.act_fromto_from_input_hint), SelectFor.FROM_POI, true, -1);
                } else {
                    add.this.s.a(add.this.h, 1002, ((AbstractPlanHomePage) add.this.mPage).getString(R.string.act_fromto_to_input_hint), SelectFor.TO_POI, true, -1);
                }
            }
        }, 100);
    }

    /* access modifiers changed from: private */
    public static void C() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("type", d(adf.a().b()));
            LogManager.actionLogV2("P00333", "B001", jSONObject);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public ON_BACK_TYPE onBackPressed() {
        if (this.f != null && this.f.a) {
            if (this.f != null && this.f.a) {
                Builder builder = new Builder(((AbstractPlanHomePage) this.mPage).getActivity());
                builder.setNegativeButton((CharSequence) ((AbstractPlanHomePage) this.mPage).getString(R.string.back_to, this.f.c), (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                    public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                        nodeAlertDialogPage.finish();
                        Intent a2 = add.this.f.a();
                        if (a2 != null) {
                            try {
                                ((AbstractPlanHomePage) add.this.mPage).getActivity().startActivity(a2);
                            } catch (Exception unused) {
                            }
                        }
                        add.this.f = null;
                    }
                });
                builder.setPositiveButton((CharSequence) ((AbstractPlanHomePage) this.mPage).getString(R.string.stay_at_amap), (NodeDialogFragmentOnClickListener) new NodeDialogFragmentOnClickListener() {
                    public final void onClick(NodeAlertDialogPage nodeAlertDialogPage) {
                        add.this.f = null;
                        nodeAlertDialogPage.finish();
                        nodeAlertDialogPage.startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
                    }
                });
                builder.setTitle((CharSequence) ((AbstractPlanHomePage) this.mPage).getString(R.string.be_sure_where_to_back));
                ((AbstractPlanHomePage) this.mPage).startAlertDialogPage(builder);
            }
            return ON_BACK_TYPE.TYPE_IGNORE;
        } else if (this.c.b()) {
            this.c.c();
            return ON_BACK_TYPE.TYPE_IGNORE;
        } else {
            F();
            G();
            if (this.k) {
                C();
                if (((AbstractPlanHomePage) this.mPage).isAlive()) {
                    ((AbstractPlanHomePage) this.mPage).finish();
                }
            }
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        if (i2 == 4) {
            ITruckGuideManager iTruckGuideManager = (ITruckGuideManager) ank.a(ITruckGuideManager.class);
            if (iTruckGuideManager != null && iTruckGuideManager.isGuideShowing()) {
                return true;
            }
            IMotorGuideManager iMotorGuideManager = (IMotorGuideManager) ank.a(IMotorGuideManager.class);
            if (iMotorGuideManager != null && iMotorGuideManager.isGuideShowing()) {
                return true;
            }
        }
        return super.onKeyDown(i2, keyEvent);
    }

    public void onStart() {
        super.onStart();
        s();
        ((AbstractPlanHomePage) this.mPage).requestScreenOrientation(1);
        this.n = false;
    }

    private void a(String str, String str2, SelectFor selectFor, int i2) {
        this.s.a(str, 1003, str2, selectFor, false, i2);
    }

    public void onStop() {
        super.onStop();
        if (this.c != null && this.c.b()) {
            this.c.c();
        }
        this.n = false;
    }

    public void onResult(int i2, ResultType resultType, PageBundle pageBundle) {
        super.onResult(i2, resultType, pageBundle);
        if (!a(IRouteHeaderEvent.PAGE_ON_RESULT, pageBundle) && !acn.a(i2, resultType, pageBundle)) {
            if (i2 == 96 && resultType == ResultType.OK && pageBundle != null && pageBundle.containsKey("result_poi")) {
                POI poi = (POI) pageBundle.get("result_poi");
                if (poi != null) {
                    com com2 = (com) ank.a(com.class);
                    if (com2 != null) {
                        cop b2 = com2.b(com2.a());
                        if (b2 != null) {
                            b2.f(poi);
                        }
                    }
                }
            }
            if (i2 == 97 && resultType == ResultType.OK && pageBundle != null && pageBundle.containsKey("result_poi")) {
                POI poi2 = (POI) pageBundle.get("result_poi");
                if (poi2 != null) {
                    com com3 = (com) ank.a(com.class);
                    if (com3 != null) {
                        cop b3 = com3.b(com3.a());
                        if (b3 != null) {
                            b3.e(poi2);
                        }
                    }
                }
            }
            if (pageBundle != null && pageBundle.containsKey(RouteHeaderModel.ROUTE_HEADER_MODEL_KEY)) {
                RouteHeaderModel routeHeaderModel = (RouteHeaderModel) pageBundle.getObject(RouteHeaderModel.ROUTE_HEADER_MODEL_KEY);
                if (routeHeaderModel != null) {
                    POI poi3 = routeHeaderModel.mStartPoi;
                    POI poi4 = routeHeaderModel.mEndPoi;
                    if (adf.a().b() == RouteType.TAXI) {
                        boolean z = false;
                        boolean z2 = !acj.a(poi3, ade.a().b(true), false) && c(poi3);
                        if (!acj.a(poi4, ade.a().d(true), false) && c(poi4)) {
                            z = true;
                        }
                        if (z2) {
                            acq.a().a(poi3);
                        }
                        if (z) {
                            acq.a().b(poi4);
                        }
                        if (z2 && z) {
                            a(poi3, poi4);
                        } else if (z2) {
                            ade.a().a(poi3);
                        } else {
                            if (z) {
                                ade.a().b(poi4);
                            }
                        }
                    } else {
                        boolean z3 = !acj.a(poi4, ade.a().d(true), true);
                        if (!acj.a(poi3, ade.a().b(true), true)) {
                            acq.a().a(poi3);
                        }
                        if (z3) {
                            acq.a().b(poi4);
                        }
                        StringBuilder sb = new StringBuilder("PlanHomePresenter: onResult :");
                        sb.append(routeHeaderModel.mMidPois == null ? "null" : Integer.valueOf(routeHeaderModel.mMidPois.size()));
                        eao.b("access_point", sb.toString());
                        b(routeHeaderModel.mStartPoi, routeHeaderModel.mMidPois, routeHeaderModel.mEndPoi);
                    }
                }
            }
        }
    }

    private static boolean c(POI poi) {
        return poi == null || !DriveUtil.MY_LOCATION_LOADING.equals(poi.getName());
    }

    public void onDestroy() {
        if (this.p != null) {
            this.p.a = null;
            this.p = null;
        }
        eez a2 = eez.a();
        a2.a = null;
        a2.b = null;
        a2.c = null;
        AbstractPlanHomePage abstractPlanHomePage = (AbstractPlanHomePage) this.mPage;
        bty mapView = DoNotUseTool.getMapView();
        if (mapView != null) {
            mapView.X();
            mapView.a(abstractPlanHomePage.d.x, abstractPlanHomePage.d.y);
            mapView.d(abstractPlanHomePage.b);
            if (abstractPlanHomePage.c == 0.0f && DoNotUseTool.getSuspendManager() != null) {
                DoNotUseTool.getSuspendManager().d().e();
            }
            mapView.e(abstractPlanHomePage.a);
            mapView.g(abstractPlanHomePage.c);
        }
        if (!this.k) {
            acw.a().onDestroy();
            ade.a().d();
            adf.a().b.clear();
            adf.a = null;
            this.c.b = null;
            acq.a = null;
            aco a3 = aco.a();
            adf.a().b(a3);
            a3.b = null;
            aco.a = null;
            acm a4 = acm.a();
            a4.b = null;
            a4.c = null;
            acm.a = null;
            acl.a();
            acl.b();
        }
        atw atw = (atw) a.a.a(atw.class);
        if (atw != null) {
            atw.d().a();
            atw.a().c();
        }
        bfo bfo = (bfo) a.a.a(bfo.class);
        if (bfo != null) {
            bfo.a((IRouteUI) null);
        }
        super.onDestroy();
    }

    public final ContainerType[] a() {
        return eez.a().d;
    }

    public final void a(ContainerType[] containerTypeArr) {
        eez.a().a(containerTypeArr);
        View findViewById = ((AbstractPlanHomePage) this.mPage).getContentView().findViewById(R.id.route_input_tool_bar_container);
        if (findViewById != null) {
            findViewById.bringToFront();
        }
    }

    public final void a(POI poi, List<POI> list, POI poi2) {
        b(poi, list, poi2);
    }

    public final void a(View view) {
        eao.c("DesMapPage scroll check addViewToContainer".concat(String.valueOf(view)));
        if (view != null) {
            if (view.getParent() != null) {
                eao.c("DesMapPage scroll check addViewToContainer The specified child already has a parent");
                if (bno.a) {
                    AMapLog.e("RoutePresenter", "The specified child already has a parent ---->");
                }
                return;
            }
            View findViewById = ((AbstractPlanHomePage) this.mPage).getContentView().findViewById(R.id.route_flow_container);
            StringBuilder sb = new StringBuilder("DesMapPage scroll addViewToContainer check rootView rootView != null ");
            sb.append(findViewById != null);
            sb.append(" rootView instanceof ViewGroup ");
            boolean z = findViewById instanceof ViewGroup;
            sb.append(z);
            eao.c(sb.toString());
            if (findViewById != null && z) {
                ((ViewGroup) findViewById).addView(view);
                if (((AbstractPlanHomePage) this.mPage).getContentView().findViewById(R.id.view_voice_title).getVisibility() == 8) {
                    ((View) view.getParent()).bringToFront();
                }
                this.o.add(view);
            }
        }
    }

    public final void b(View view) {
        if (view != null) {
            View findViewById = ((AbstractPlanHomePage) this.mPage).getContentView().findViewById(R.id.route_flow_container);
            if (findViewById != null && (findViewById instanceof ViewGroup)) {
                ((ViewGroup) findViewById).removeView(view);
            }
            this.o.remove(view);
        }
    }

    public final View b() {
        return this.c.j();
    }

    public final View c() {
        acq acq = this.c;
        if (acq.b == null) {
            return null;
        }
        return acq.b.getInputHeaderWithShadow();
    }

    public final void a(Class<? extends AbstractBasePage> cls, PageBundle pageBundle) {
        this.a.setPageLevel(2);
        this.c.g();
        this.a.showPage(cls, pageBundle);
    }

    public final void a(RouteType routeType) {
        e(routeType);
        h(routeType);
        b(routeType, (PageBundle) null);
    }

    private void a(RouteType routeType, bgb bgb) {
        PageBundle pageBundle;
        e(routeType);
        h(routeType);
        if (!TextUtils.isEmpty(bgb.b)) {
            pageBundle = new PageBundle();
            pageBundle.putString("bundleKeyVoiceCmd", bgb.b);
        } else {
            pageBundle = null;
        }
        b(routeType, pageBundle);
    }

    public final void b(RouteType routeType) {
        this.c.d(routeType);
    }

    public final RouteType[] h() {
        acq acq = this.c;
        if (acq.b == null) {
            return null;
        }
        return acq.b.getCurrentTypes();
    }

    public final boolean j() {
        return this.c.e();
    }

    public final void k() {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("route_edit_dispatch_widget_id", 3);
        pageBundle.putObject("route_edit_dispatch_widget_view", null);
        a(IRouteHeaderEvent.EXCHANGE_CLICK, pageBundle);
    }

    public final boolean l() {
        return this.c.f();
    }

    private ViewGroup g(RouteType routeType) {
        if (this.c != null) {
            return this.c.a(routeType);
        }
        return null;
    }

    private void h(RouteType routeType) {
        this.c.e(routeType);
    }

    public final void n() {
        this.a.setPageLevel(2);
        this.c.g();
    }

    private void b(RouteType routeType, PageBundle pageBundle) {
        j(routeType);
        i(routeType);
        TextView textView = (TextView) ((AbstractPlanHomePage) this.mPage).getContentView().findViewById(R.id.smart_trip_description);
        if (textView != null && textView.getVisibility() == 0) {
            textView.setVisibility(8);
        }
        if (pageBundle == null) {
            pageBundle = new PageBundle();
        }
        pageBundle.putObject(IRouteHeaderEvent.PREPARE_SWITCH_TAB.name(), routeType);
        b(IRouteHeaderEvent.PREPARE_SWITCH_TAB, pageBundle);
        if (routeType == RouteType.TAXI && g(RouteType.TAXI) == null) {
            this.c.c(RouteType.TAXI);
        }
        if (this.c != null) {
            this.c.b(routeType);
        }
        b(adf.a().c(), routeType);
        a(adf.a().b(), routeType);
        this.n = true;
        if (!(adf.a().c() != RouteType.TAXI || ade.a().b(true) == null || ade.a().d(true) == null)) {
            n();
        }
        this.a.switchPage(routeType, pageBundle);
        if (this.o.size() > 0) {
            Iterator<View> it = this.o.iterator();
            while (it.hasNext()) {
                View next = it.next();
                if (next instanceof AmapAjxView) {
                    ModuleHome moduleHome = (ModuleHome) ((AmapAjxView) next).getJsModule(ModuleHome.MODULE_NAME);
                    if (moduleHome != null) {
                        moduleHome.notifyRouteTypeChange(routeType);
                        return;
                    }
                }
            }
            return;
        }
        if (this.a.getChildCount() > 0) {
            for (int i2 = 0; i2 < this.a.getChildCount(); i2++) {
                View childAt = this.a.getChildAt(i2);
                if (childAt instanceof AmapAjxView) {
                    AmapAjxView amapAjxView = (AmapAjxView) childAt;
                    if ("path://amap_bundle_basemap_route/src/index.page.js".equals(amapAjxView.getUrl())) {
                        ModuleHome moduleHome2 = (ModuleHome) amapAjxView.getJsModule(ModuleHome.MODULE_NAME);
                        if (moduleHome2 != null) {
                            moduleHome2.notifyRouteTypeChange(routeType);
                            return;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    private void i(RouteType routeType) {
        if (!this.k && !this.m) {
            acr.a(routeType);
            a.a.a = routeType;
        }
    }

    public final void a(String str) {
        this.s.a(str, 1002, ((AbstractPlanHomePage) this.mPage).getString(R.string.act_fromto_to_input_hint), SelectFor.TO_POI, true, -1);
    }

    public final boolean o() {
        return this.n;
    }

    public final void p() {
        if (this.c != null) {
            acq acq = this.c;
            if (acq.b != null) {
                acq.b.hideRouteTabToolBarView();
            }
        }
    }

    public final boolean a(axe axe) {
        this.d = axe;
        return false;
    }

    public final int c(RouteType routeType) {
        acq acq = this.c;
        if (acq.b == null || routeType == null) {
            return 0;
        }
        return acq.b.getTabPos(routeType);
    }

    public final void b(axe axe) {
        this.e = axe;
    }

    public void onPause() {
        super.onPause();
    }

    public void onNewIntent(PageBundle pageBundle) {
        super.onNewIntent(pageBundle);
        ((AbstractPlanHomePage) this.mPage).setArguments(pageBundle);
        RouteType b2 = adf.a().b();
        x();
        if (b2 != adf.a().b()) {
            ade.a().d();
        } else {
            ade a2 = ade.a();
            a2.a = new adb();
            a2.b = new adb();
            a2.d = RouteType.DEFAULT;
            a2.e = RouteType.DEFAULT;
        }
        y();
        boolean z = false;
        if (pageBundle != null) {
            z = pageBundle.getBoolean("bundle_key_from_scheme", false);
        }
        if (z) {
            POI b3 = ade.a().b(true);
            if (ade.a().d(true) == null && (b3 == null || TextUtils.equals(b3.getName(), "我的位置") || (TextUtils.isEmpty(b3.getName()) && b3.getPoint().x == 0 && b3.getPoint().y == 0))) {
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null) {
                    pageContext.startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
                    AMapPageUtil.getPageContext().startPage(PlanHomePage.class, (PageBundle) null);
                }
            }
        }
        B();
        if (adf.a().c() != adf.a().b()) {
            this.n = true;
        }
        v();
        w();
        z();
    }

    /* access modifiers changed from: private */
    public void D() {
        final View c2 = c();
        if (c2 != null) {
            c2.setVisibility(0);
            c2.setAlpha(0.0f);
            this.a.setVisibility(0);
            this.a.setAlpha(0.0f);
            ((AbstractPlanHomePage) this.mPage).getContentView().post(new Runnable() {
                public final void run() {
                    add add = add.this;
                    View view = c2;
                    if (view != null) {
                        view.setTranslationY((float) (0 - view.getHeight()));
                        view.setTag(R.id.tag_enter_animation_filter, null);
                        view.animate().alpha(1.0f).setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator()).translationY(0.0f).setListener(new AnimatorListenerAdapter(view) {
                            final /* synthetic */ View a;

                            {
                                this.a = r2;
                            }

                            public final void onAnimationEnd(Animator animator) {
                                AMapLog.e("RoutePresenter", "onAnimationEnd");
                                this.a.setAlpha(1.0f);
                                this.a.setVisibility(0);
                                this.a.setTranslationY(0.0f);
                                add.this.E();
                            }
                        });
                    }
                    add add2 = add.this;
                    PageContainer pageContainer = ((AbstractPlanHomePage) add2.mPage).getPageContainer();
                    if (pageContainer != null) {
                        pageContainer.setTranslationY((float) (add2.a.getHeight() + 0));
                        int measuredHeight = add2.a.getMeasuredHeight();
                        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 400.0f});
                        ofFloat.setDuration(400);
                        ofFloat.addUpdateListener(new AnimatorUpdateListener(pageContainer, measuredHeight) {
                            final /* synthetic */ View a;
                            final /* synthetic */ int b;

                            {
                                this.a = r2;
                                this.b = r3;
                            }

                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                                this.a.setAlpha(floatValue / 400.0f);
                                this.a.setTranslationY((((float) this.b) / 400.0f) * (400.0f - floatValue));
                                this.a.requestLayout();
                            }
                        });
                        pageContainer.setVisibility(0);
                        ofFloat.start();
                    }
                    add.this.q();
                }
            });
        }
    }

    public final void q() {
        for (int i2 = 0; i2 < this.o.size(); i2++) {
            View view = this.o.get(i2);
            if (view.isShown()) {
                view.setVisibility(0);
                view.setAlpha(0.0f);
                view.setTranslationY((float) (view.getHeight() + 0));
                view.setTag(R.id.tag_enter_animation_filter, null);
                view.animate().alpha(1.0f).setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator()).translationY(0.0f).setListener(null);
            }
        }
    }

    /* access modifiers changed from: private */
    public void F() {
        final View c2 = c();
        if (c2 != null) {
            c2.post(new Runnable() {
                public final void run() {
                    c2.setTag(R.id.tag_enter_animation_filter, Integer.valueOf(1));
                    c2.animate().alpha(0.0f).setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator()).translationY((float) (0 - c2.getHeight())).setListener(null);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void G() {
        a((IRouteUI.a) new IRouteUI.a() {
            public final void a() {
                if (add.this.mPage != null && ((AbstractPlanHomePage) add.this.mPage).isAlive()) {
                    ((AbstractPlanHomePage) add.this.mPage).finish();
                }
            }
        });
    }

    private void H() {
        if (this.o.size() != 0) {
            for (int i2 = 0; i2 < this.o.size(); i2++) {
                final View view = this.o.get(i2);
                if (view.isShown()) {
                    view.post(new Runnable() {
                        public final void run() {
                            view.setTag(R.id.tag_enter_animation_filter, Integer.valueOf(1));
                            view.animate().alpha(0.0f).setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator()).translationY((float) (view.getHeight() + 0)).setListener(null);
                        }
                    });
                }
            }
        }
    }

    private boolean b(IRouteHeaderEvent iRouteHeaderEvent, PageBundle pageBundle) {
        if ((iRouteHeaderEvent == IRouteHeaderEvent.START_CLICK || iRouteHeaderEvent == IRouteHeaderEvent.END_CLICK) && !this.c.d()) {
            ToastHelper.showToast("当前无法修改起终点位置");
            return true;
        } else if (this.d == null) {
            return false;
        } else {
            if (iRouteHeaderEvent == IRouteHeaderEvent.EXCHANGE_CLICK) {
                NativeVcsManager.getInstance().stopListeningPlayWarning();
            }
            boolean a2 = this.d.a(iRouteHeaderEvent, pageBundle);
            if (this.e != null) {
                this.e.a(iRouteHeaderEvent, pageBundle);
            }
            return a2;
        }
    }

    public final int r() {
        if (this.a != null) {
            return this.a.getPageLevel();
        }
        return 1;
    }

    private static RouteType a(int i2) {
        RouteType[] values;
        for (RouteType routeType : RouteType.values()) {
            if (routeType.getValue() == i2) {
                return routeType;
            }
        }
        return RouteType.DEFAULT;
    }

    private void j(RouteType routeType) {
        if (!this.k) {
            adf.a().a(routeType);
        }
    }

    private static void a(POI poi, RouteType routeType) {
        ade.a().a(poi, routeType);
    }

    private static void a(POI poi, POI poi2) {
        ade.a().a(poi, poi2);
    }

    private static void a(POI poi, POI poi2, RouteType routeType) {
        ade.a().a(poi, poi2, routeType);
    }

    private static void b(POI poi, List<POI> list, POI poi2) {
        ade.a().a(poi, list, poi2);
    }

    private void a(@Nullable final IRouteUI.a aVar) {
        H();
        this.a.post(new Runnable() {
            public final void run() {
                add.this.a.animate().alpha(0.0f).setDuration(400).setInterpolator(new AccelerateDecelerateInterpolator()).translationY((float) add.this.a.getHeight()).setListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        if (aVar != null) {
                            aVar.a();
                        }
                    }
                });
            }
        });
    }

    public final void s() {
        if (I()) {
            D();
        }
    }

    private boolean I() {
        View c2 = c();
        if (c2 == null) {
            return false;
        }
        int i2 = 0;
        boolean z = false;
        while (true) {
            boolean z2 = true;
            if (i2 >= this.o.size()) {
                break;
            }
            if (this.o.get(i2).getTag(R.id.tag_enter_animation_filter) == null) {
                z2 = false;
            }
            z |= z2;
            i2++;
        }
        if ((c2.getTag(R.id.tag_enter_animation_filter) != null) || z) {
            return true;
        }
        return false;
    }

    private static RouteType k(RouteType routeType) {
        if (routeType != RouteType.CAR) {
            return routeType;
        }
        MapSharePreference mapSharePreference = new MapSharePreference((String) "NAMESPACE_TRIP_BUSINESS");
        RouteType a2 = acs.a(mapSharePreference.getStringValue("KEY_TRIP_ROUTETYPE_STATE", ""));
        if (a2 == null) {
            return routeType;
        }
        mapSharePreference.edit().putString("KEY_TRIP_ROUTETYPE_STATE", "").apply();
        return a2;
    }

    private static void B() {
        if (ade.a().b(true) == null) {
            GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
            if (latestPosition != null) {
                POI createPOI = POIFactory.createPOI();
                createPOI.setName("我的位置");
                createPOI.setPoint(latestPosition);
                a(createPOI, RouteType.DEFAULT);
            }
        }
    }

    public final void a(POI poi) {
        ade.a().a(poi);
    }

    public final void a(List<POI> list) {
        ade.a().a(list);
    }

    public final void b(POI poi) {
        ade.a().b(poi);
    }

    public final POI d() {
        return ade.a().b(true);
    }

    public final List<POI> e() {
        List<POI> c2 = ade.a().c();
        return c2 == null ? new ArrayList() : c2;
    }

    public final POI f() {
        return ade.a().d(true);
    }

    public final RouteType g() {
        return adf.a().b();
    }

    public final void a(Class cls, RouteType routeType, PageBundle pageBundle) {
        if (adf.a().b() == routeType && adf.a().b() != null) {
            this.n = false;
        }
        this.c.g();
        this.a.showResultPage(cls, routeType, pageBundle);
    }

    public final void a(RouteType routeType, PageBundle pageBundle) {
        if (adf.a().b() == routeType && adf.a().b() != null) {
            this.n = false;
        }
        this.c.g();
        this.a.showResultPage(routeType, pageBundle);
    }

    public final RouteType i() {
        return adf.a().b();
    }

    public final void b(List<POI> list) {
        if (this.c.f()) {
            a(list);
            PageBundle pageBundle = new PageBundle();
            POI b2 = ade.a().b(true);
            POI d2 = ade.a().d(true);
            if (b2 != null && d2 != null) {
                b(IRouteHeaderEvent.COMPLETE_CLICK, pageBundle);
            }
        }
    }

    public final int m() {
        View j2 = this.c.j();
        if (j2 != null) {
            return j2.getMeasuredHeight();
        }
        return 0;
    }

    /* access modifiers changed from: private */
    public void E() {
        RouteType b2 = adf.a().b();
        if (b2 != null) {
            h(b2);
        }
        acq acq = this.c;
        if (acq.b != null) {
            acq.b.enterAnimation();
        }
        b(IRouteHeaderEvent.HEAD_ANIMATION_DONE, new PageBundle());
        boolean z = false;
        if (this.mPage != null && ((AbstractPlanHomePage) this.mPage).isAlive()) {
            PageBundle arguments = ((AbstractPlanHomePage) this.mPage).getArguments();
            if (arguments != null) {
                z = arguments.getBoolean("key_favorites", false);
            }
        }
        if (!z) {
            IMotorGuideManager iMotorGuideManager = (IMotorGuideManager) ank.a(IMotorGuideManager.class);
            if (iMotorGuideManager != null) {
                iMotorGuideManager.checkShowGuide(this, ack.d());
            }
            ITruckGuideManager iTruckGuideManager = (ITruckGuideManager) ank.a(ITruckGuideManager.class);
            if (iTruckGuideManager != null) {
                iTruckGuideManager.checkShowGuide(this);
            }
        }
    }

    public final boolean a(IRouteHeaderEvent iRouteHeaderEvent, PageBundle pageBundle) {
        int i2;
        int i3;
        int i4;
        int i5;
        RouteType b2 = adf.a().b();
        POI poi = null;
        switch (iRouteHeaderEvent) {
            case REMOVE_PASS_POI_CLICK:
                return b(iRouteHeaderEvent, pageBundle);
            case BACK_CLICK:
                if (b(iRouteHeaderEvent, pageBundle)) {
                    return true;
                }
                LogManager.actionLogV2("P00014", "B001");
                F();
                G();
                return false;
            case ADD_CLICK:
                if (b(iRouteHeaderEvent, pageBundle)) {
                    return true;
                }
                this.s.a(pageBundle);
                return false;
            case START_CLICK:
                u();
                if (b(iRouteHeaderEvent, pageBundle) || this.r.a(IRouteHeaderEvent.START_CLICK, b2, pageBundle)) {
                    return true;
                }
                if (pageBundle != null) {
                    i2 = pageBundle.getInt("route_edit_dispatch_widget_id", 16);
                } else {
                    i2 = 16;
                }
                String str = "";
                POI b3 = ade.a().b(true);
                if (b3 != null) {
                    str = b3.getName();
                }
                this.s.a(str, 1001, ((AbstractPlanHomePage) this.mPage).getString(R.string.act_fromto_from_input_hint), SelectFor.FROM_POI, false, i2);
                return false;
            case PASS_POI_CLICK:
                if (b(iRouteHeaderEvent, pageBundle)) {
                    return false;
                }
                int i6 = -1;
                if (pageBundle != null) {
                    i3 = pageBundle.getInt("route_pass_poi", -1);
                } else {
                    i3 = -1;
                }
                if (i3 == -1 || i3 < 0) {
                    i4 = 0;
                } else {
                    List<POI> c2 = ade.a().c();
                    i4 = c2.size();
                    if (i3 < i4) {
                        poi = c2.get(i3);
                    }
                }
                if (pageBundle != null) {
                    i6 = pageBundle.getInt("route_edit_dispatch_widget_id", 16);
                }
                String str2 = "";
                if (poi != null) {
                    str2 = poi.getName();
                }
                if (i4 != 1) {
                    switch (i3) {
                        case 0:
                            a(str2, "", SelectFor.MID_POI_1, i6);
                            break;
                        case 1:
                            a(str2, "", SelectFor.MID_POI_2, i6);
                            break;
                        case 2:
                            a(str2, "", SelectFor.MID_POI_3, i6);
                            break;
                    }
                } else {
                    a(str2, "", SelectFor.MID_POI, i6);
                }
                return false;
            case END_CLICK:
                u();
                if (b(iRouteHeaderEvent, pageBundle) || this.r.a(IRouteHeaderEvent.END_CLICK, b2, pageBundle)) {
                    return true;
                }
                if (pageBundle != null) {
                    i5 = pageBundle.getInt("route_edit_dispatch_widget_id", 16);
                } else {
                    i5 = 32;
                }
                String str3 = "";
                POI d2 = ade.a().d(true);
                if (d2 != null) {
                    str3 = d2.getName();
                }
                this.s.a(str3, 1002, ((AbstractPlanHomePage) this.mPage).getString(R.string.act_fromto_to_input_hint), SelectFor.TO_POI, false, i5);
                return false;
            case COMPLETE_CLICK:
                boolean b4 = b(iRouteHeaderEvent, pageBundle);
                this.s.a(pageBundle);
                return b4;
            case SUMMARY_CLICK:
                if (b(iRouteHeaderEvent, pageBundle)) {
                    return true;
                }
                this.s.a(pageBundle);
                return false;
            case EXCHANGE_CLICK:
                if (!(this.a == null || !this.c.e() || this.c == null)) {
                    acq acq = this.c;
                    if (acq.b != null) {
                        acq.b.exchange();
                    }
                    POI b5 = ade.a().b(true);
                    List<POI> c3 = ade.a().c();
                    POI d3 = ade.a().d(true);
                    if (c3 != null) {
                        Collections.reverse(c3);
                    }
                    ade.a().a(d3, c3, b5);
                }
                return b(iRouteHeaderEvent, pageBundle);
            case TAB_CLICK:
                if (pageBundle != null) {
                    Object object = pageBundle.getObject("route_type");
                    if (object != null && (object instanceof RouteType)) {
                        b((RouteType) object, (PageBundle) null);
                    }
                }
                return true;
            default:
                b(iRouteHeaderEvent, pageBundle);
                return false;
        }
    }

    public boolean handleVUICmd(bgb bgb, bfb bfb) {
        boolean z;
        RouteType routeType;
        StringBuilder sb = new StringBuilder("handlerSwitchTabCmd() called: cmd string = [");
        sb.append(bgb.d);
        sb.append("]");
        bgo bgo = null;
        if ("switchTravelTool".equals(bgb.d)) {
            JSONObject c2 = eqa.c(bgb.b);
            if (c2 == null) {
                routeType = RouteType.DEFAULT;
            } else {
                routeType = a(agd.a(c2, "param_type"));
            }
            new StringBuilder("handlerSwitchTabCmd: 解析到出行方式为 ").append(routeType.getKeyName());
            if (adf.a().b() == routeType) {
                d.a.a(bgb.a, 10000, (String) null);
            } else if (RouteType.DEFAULT == routeType || routeType != f(routeType)) {
                d.a.a(bgb.a, 10128, (String) null);
            } else {
                a(routeType, bgb);
            }
            z = true;
        } else {
            z = false;
        }
        if (z) {
            return true;
        }
        PageContainer pageContainer = ((AbstractPlanHomePage) this.mPage).getPageContainer();
        if (pageContainer != null) {
            AbstractBasePage cureentRecordPage = pageContainer.getCureentRecordPage();
            if (cureentRecordPage instanceof bgm) {
                bgo = ((bgm) cureentRecordPage).getPresenter();
            }
        }
        if (bgo == null) {
            return false;
        }
        new StringBuilder("handlerSwitchTabCmd: presenter = ").append(bgo.getClass().getSimpleName());
        return bgo.handleVUICmd(bgb, bfb);
    }

    static /* synthetic */ void l(add add) {
        if (add.c.j() != null) {
            add.c.j().setVisibility(0);
        }
        add.a.setVisibility(0);
        for (int i2 = 0; i2 < add.o.size(); i2++) {
            View view = add.o.get(i2);
            if (view.isShown()) {
                view.setVisibility(0);
            }
        }
        add.E();
    }

    static /* synthetic */ void o(add add) {
        RouteType b2 = adf.a().b();
        if (b2 != null) {
            add.h(b2);
        }
        add.b(IRouteHeaderEvent.PAGE_ANIMATION_DONE, new PageBundle());
    }
}
