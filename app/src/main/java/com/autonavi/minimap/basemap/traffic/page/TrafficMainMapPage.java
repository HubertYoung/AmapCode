package com.autonavi.minimap.basemap.traffic.page;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import com.amap.bundle.maplayer.api.VMapPageConfig;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.bundle.uitemplate.ajx.ModuleSlideContainer;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapWidgetManager;
import com.autonavi.bundle.uitemplate.page.SlidableAjxPage;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant;
import com.autonavi.jni.ae.gmap.scenic.Label3rd;
import com.autonavi.map.core.Real3DManager;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.IAMapHomePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.basemap.traffic.TrafficTopic;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.basemap.traffic.ajx.ModuleTrafficEvent;
import com.autonavi.minimap.basemap.traffic.ajx.ModuleTrafficEvent.a;
import com.autonavi.minimap.basemap.traffic.bean.TwiceReportType;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.AgroupScenes;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.bundle.msgbox.dispatcher.AbsMsgBoxDispatcher;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.SaveOverlay, visible = true), @OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.LocalReportOverlay, visible = true)})
@VMapPageConfig(name = "TrafficMainMap")
public class TrafficMainMapPage extends SlidableAjxPage implements bek, bgm, bgo, a, IVoiceCmdResponder, launchModeSingleTask, IAMapHomePage, a, emc {
    public bdu b;
    private als c;
    private ModuleSlideContainer d;
    private csv e;
    private csd f;
    private csh g;
    private int h = 0;
    private TrafficTopic i;
    private DIYMainMapWidgetManager j;
    private TrafficPageMapWidgetManager k = new TrafficPageMapWidgetManager(this);
    private g l = new g() {
        public final void updateZoomViewVisibility() {
        }

        public final void updateZoomButtonState(bty bty) {
            if (bty != null) {
                int p = bty.p(true);
                boolean b = Real3DManager.a().b(false);
                if (p == 0 && b) {
                    bty.a(bty.e().j(), false);
                }
            }
        }
    };
    private MainMapEventListener m = new awc() {
        public final void onEngineActionGesture(alg alg) {
            int i = alg.a;
            bty mapView = TrafficMainMapPage.this.getMapView();
            GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            if (glGeoPoint2GeoPoint != null) {
                int j = mapView.j(false);
                int i2 = mapView.s() ? 1 : 2;
                HashMap hashMap = new HashMap();
                hashMap.put("type", String.valueOf(j));
                StringBuilder sb = new StringBuilder();
                sb.append(mapView.w());
                hashMap.put("from", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(glGeoPoint2GeoPoint.getLatitude());
                hashMap.put("lat", sb2.toString());
                StringBuilder sb3 = new StringBuilder();
                sb3.append(glGeoPoint2GeoPoint.getLongitude());
                hashMap.put(LocationParams.PARA_FLP_AUTONAVI_LON, sb3.toString());
                hashMap.put("status", String.valueOf(i));
                hashMap.put("isLogin", String.valueOf(i2));
                hashMap.put("itemId", "2");
                StringBuilder sb4 = new StringBuilder();
                sb4.append(mapView.J());
                hashMap.put("text", sb4.toString());
                kd.b("amap.P00001.0.B069", hashMap);
            }
        }

        public final void onUserMapTouchEvent(MotionEvent motionEvent) {
            TrafficMainMapPage.this.onMapTouchEvent(motionEvent);
        }

        public final boolean onBlankClick() {
            bty mapView = TrafficMainMapPage.this.getMapView();
            GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            if (glGeoPoint2GeoPoint != null) {
                int j = mapView.j(false);
                int i = 1;
                if (j == 0) {
                    j = 1;
                } else if (j == 1) {
                    j = 2;
                } else if (j == 2) {
                    j = 3;
                }
                if (!mapView.s()) {
                    i = 2;
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", j);
                    jSONObject.put("from", mapView.w());
                    jSONObject.put("lat", glGeoPoint2GeoPoint.getLatitude());
                    jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, glGeoPoint2GeoPoint.getLongitude());
                    jSONObject.put("status", i);
                    jSONObject.put("itemId", 2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00001", LogConstant.MAIN_CLICK_BLANK_CONTENT, jSONObject);
                HashMap hashMap = new HashMap();
                hashMap.put("type", String.valueOf(j));
                StringBuilder sb = new StringBuilder();
                sb.append(mapView.w());
                hashMap.put("from", sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append(glGeoPoint2GeoPoint.getLatitude());
                hashMap.put("lat", sb2.toString());
                StringBuilder sb3 = new StringBuilder();
                sb3.append(glGeoPoint2GeoPoint.getLongitude());
                hashMap.put(LocationParams.PARA_FLP_AUTONAVI_LON, sb3.toString());
                hashMap.put("status", String.valueOf(i));
                hashMap.put("itemId", "2");
                kd.b("amap.P00001.0.B156", hashMap);
            }
            TrafficMainMapPage.this.finish();
            return super.onBlankClick();
        }
    };

    public void finishSelf() {
    }

    public bgo getPresenter() {
        return this;
    }

    public long getScene() {
        return 1;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 1;
    }

    public boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean isMapHomePage() {
        return true;
    }

    public boolean isShowMapWidgets() {
        return true;
    }

    public boolean isShowPageHeader() {
        return true;
    }

    public final void k() {
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public final boolean u() {
        return true;
    }

    public Ajx3PagePresenter createPresenter() {
        return new csp(this);
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        ModuleTrafficEvent moduleTrafficEvent = (ModuleTrafficEvent) this.mAjxView.getJsModule("traffic_event");
        if (moduleTrafficEvent != null) {
            moduleTrafficEvent.setTrafficEventListener(this);
        }
        this.d = (ModuleSlideContainer) this.mAjxView.getJsModule(ModuleSlideContainer.MODULE_NAME);
        if (this.d != null) {
            this.d.attachContainer(this.a);
            this.d.setContainerSlideMode("mode_fixed");
        }
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().a(getClass(), AgroupScenes.TrafficMainMap, getArguments(), false);
        }
    }

    public void resume() {
        super.resume();
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.b(9001);
            if (awo.d()) {
                awo.e();
            }
            awo.a((List<Integer>) awo.j());
        }
    }

    public void onAnimationStarted(boolean z) {
        if (z) {
            onBindMapWidgets();
        }
    }

    public void start() {
        super.start();
        addMainMapEventListener(this.m);
        csv csv = this.e;
        if (csv.a != null) {
            csv.a.registerListener(WidgetType.LAYER, csv);
        }
    }

    public void stop() {
        super.stop();
        removeMainMapEventListener(this.m);
        csv csv = this.e;
        if (csv.a != null) {
            csv.a.unregisterListener(WidgetType.LAYER, csv);
        }
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.c(9001);
            awo.f();
        }
    }

    private String a(PageBundle pageBundle, boolean z) {
        JSONObject jSONObject;
        this.c = ((a) pageBundle.getObject("key_traffic_args")).a;
        int i2 = ((a) pageBundle.getObject("key_traffic_args")).b;
        boolean z2 = false;
        if (i2 == 0) {
            if (this.c.i != 16777216) {
                i2 = Integer.parseInt(this.c.b, 36);
            } else {
                i2 = Integer.parseInt(this.c.b);
                z2 = true;
            }
        }
        if (z2) {
            LogManager.actionLogV2(LogConstant.TRAFFIC_CARD_EVENT, "B004");
        }
        try {
            jSONObject = new JSONObject();
            try {
                jSONObject.put("eventid", i2);
                jSONObject.put("isNewIntent", z ? "true" : "false");
            } catch (JSONException e2) {
                e = e2;
                e.printStackTrace();
                return jSONObject.toString();
            }
        } catch (JSONException e3) {
            e = e3;
            jSONObject = null;
            e.printStackTrace();
            return jSONObject.toString();
        }
        return jSONObject.toString();
    }

    public final void a(PageBundle pageBundle) {
        this.g.clear();
        this.f.clear();
        getMapView().b(16777216, false);
        c();
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.setResumeData(a(pageBundle, true));
        }
        this.g.a(this.c);
    }

    public void destroy() {
        super.destroy();
        this.l = null;
        getMapView().b(16777216, false);
        a(true);
        if (this.f != null) {
            this.f.clear();
        }
        if (this.g != null) {
            this.g.clear();
        }
    }

    public final boolean a() {
        finish();
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            AbsMsgBoxDispatcher c2 = iMainMapService.c();
            if (c2 != null) {
                c2.setUpdateMsgFlag(false);
            }
        }
        return true;
    }

    private void c() {
        cdz d2 = getSuspendManager().d();
        if (d2 != null) {
            d2.f();
            d2.a(false);
        }
    }

    private void a(boolean z) {
        if (!getMapView().k(getMapView().e().j()) && getMapView().p(true) == 0 && Real3DManager.a().b(false)) {
            getMapView().a(getMapView().e().j(), z);
        }
    }

    public final void a(String str) {
        bty mapView = getMapView();
        int o = mapView != null ? mapView.o(false) : 0;
        csf csf = new csf();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if ("error".equals(jSONObject.optString("result"))) {
                TrafficUtil.logAction(Integer.valueOf(0), (String) null, o);
                return;
            }
            csf.a(jSONObject);
            this.i = csf.b;
            if (this.i != null) {
                if (this.i.getTrafficGroup().size() <= 1 || getMapView().k(getMapView().e().j())) {
                    a(true);
                } else {
                    final TrafficTopic trafficTopic = this.i;
                    new Thread() {
                        public final void run() {
                            TrafficMainMapPage.a(TrafficMainMapPage.this, trafficTopic);
                            TrafficMainMapPage.b(TrafficMainMapPage.this, trafficTopic);
                        }
                    }.start();
                    a(false);
                }
                TrafficTopic trafficTopic2 = this.i;
                ArrayList arrayList = new ArrayList();
                ArrayList<ArrayList<GeoPoint>> arrayList2 = trafficTopic2.getAffectOverlayData().b;
                ArrayList<GeoPoint> arrayList3 = trafficTopic2.getAffectOverlayData().d;
                ArrayList arrayList4 = new ArrayList();
                if (arrayList3 != null && arrayList3.size() > 0) {
                    arrayList4.add(arrayList3);
                    arrayList.add(trafficTopic2.getAffectOverlayData().a);
                    bsa bsa = new bsa();
                    bsa.a = new a(arrayList);
                    if (arrayList2 != null && arrayList2.size() > 0) {
                        int i2 = trafficTopic2.getAffectOverlayData().c;
                        if (i2 != -1) {
                            bsa.b = new b(arrayList2, 0, i2);
                        } else {
                            bsa.b = new b(arrayList2);
                        }
                    }
                    this.f.a(bsa);
                }
                ArrayList arrayList5 = new ArrayList();
                arrayList5.add(new GeoPoint(this.c.e, this.c.f));
                arrayList4.add(arrayList5);
                a(arrayList4);
                TrafficUtil.logAction(Integer.valueOf(csf.a()), TrafficTopic.LayerTag2Title.get(Integer.valueOf(this.i.getLayerTag())), o);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final void b(String str) {
        try {
            int parseFloat = (int) Float.parseFloat(new JSONObject(str).optString("height"));
            Context context = getContext();
            if (context != null) {
                if (isAlive()) {
                    this.h = (int) (((float) (parseFloat / 2)) * context.getResources().getDisplayMetrics().density);
                    int i2 = this.h;
                    if (!b()) {
                        i2 -= agn.a(getContext(), 80.0f);
                    }
                    IMapWidgetManagerService uITemplateService = this.k.getUITemplateService();
                    if (uITemplateService != null) {
                        uITemplateService.setContainerMargin(0, 0, 0, i2);
                    }
                    if (this.d != null) {
                        this.d.setContainerMinHeight(this.h);
                    }
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    public final boolean b() {
        if (this.k != null) {
            return this.k.isNewHomePage();
        }
        return false;
    }

    public final PageBundle a(int i2) {
        PageBundle pageBundle = new PageBundle();
        if (i2 == 4) {
            pageBundle.putObject("intent_twice_report_type", TwiceReportType.a(this.i, 1));
            pageBundle.putBoolean("key_open_traffic_later", true);
        } else if (i2 == 8) {
            pageBundle.putObject("intent_twice_report_type", TwiceReportType.a(this.i, 2));
            pageBundle.putBoolean("intent_report_page_simple_version", true);
            pageBundle.putBoolean("key_open_traffic_later", true);
        } else if (i2 == 16) {
            pageBundle.putObject("intent_twice_report_type", TwiceReportType.a(this.i, 3));
            pageBundle.putBoolean("intent_report_page_simple_version", true);
            pageBundle.putBoolean("key_open_traffic_later", true);
        }
        return pageBundle;
    }

    private void a(ArrayList<ArrayList<GeoPoint>> arrayList) {
        Context context = getContext();
        if (arrayList.size() > 0 && context != null && isAlive()) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            int intrinsicHeight = context.getResources().getDrawable(R.drawable.tmc_poi_hl).getIntrinsicHeight() + 10;
            int a = bet.a(context, 54) + 10;
            int a2 = bet.a(context, 54) + 10;
            int i2 = this.h;
            int i3 = displayMetrics.widthPixels;
            int i4 = displayMetrics.heightPixels;
            int i5 = (i4 - this.h) / 2;
            int i6 = i3 / 2;
            ArrayList arrayList2 = new ArrayList();
            Iterator<ArrayList<GeoPoint>> it = arrayList.iterator();
            while (it.hasNext()) {
                arrayList2.addAll(it.next());
            }
            a a3 = new a().a(a((GeoPoint[]) arrayList2.toArray(new GeoPoint[arrayList2.size()])), a + 50, intrinsicHeight + 50, a2 + 50, i2 + 100).a(getMapView(), i3, i4, i6, i5);
            a3.j = 0;
            cqx a4 = a3.a();
            if (arrayList.size() > 1) {
                a4.a();
                return;
            }
            getMapView().a(200, (GLGeoPoint) new GeoPoint(this.c.e, this.c.f), new Point(i6, i5), true);
        }
    }

    private static Rect a(GeoPoint[] geoPointArr) {
        if (geoPointArr.length == 0) {
            return null;
        }
        int length = geoPointArr.length;
        int i2 = length > 1000 ? 5 : (length <= 500 || length > 1000) ? (length <= 200 || length > 500) ? (length <= 20 || length > 200) ? 1 : 2 : 3 : 4;
        int i3 = 999999999;
        int i4 = 999999999;
        int i5 = -999999999;
        int i6 = -999999999;
        for (int i7 = 0; i7 < length; i7 += i2) {
            i3 = Math.min(i3, geoPointArr[i7].x);
            i4 = Math.min(i4, geoPointArr[i7].y);
            i5 = Math.max(i5, geoPointArr[i7].x);
            i6 = Math.max(i6, geoPointArr[i7].y);
        }
        Rect rect = new Rect();
        rect.set(i3, i4, i5, i6);
        return rect;
    }

    public void onInitMapWidget() {
        super.onInitMapWidget();
        if (!b()) {
            IMapWidgetManagerService uITemplateService = this.k.getUITemplateService();
            if (uITemplateService != null) {
                DIYMainMapPresenter dIYMainMapPresenter = (DIYMainMapPresenter) uITemplateService.getPresenter("diy");
                if (dIYMainMapPresenter != null) {
                    dIYMainMapPresenter.init(this.j);
                }
            }
        }
    }

    public void onCreate(Context context) {
        PageBundle arguments = getArguments();
        if (arguments == null) {
            arguments = new PageBundle();
            setArguments(arguments);
        }
        arguments.putString("url", "path://amap_bundle_trafficevent/src/pages/BizTrafficPage.page.js");
        arguments.putString("jsData", a(arguments, false));
        super.onCreate(context);
        this.f = new csd(getVMapPage());
        this.g = new csh(getVMapPage(), getMapView(), getContext());
        this.e = new csv(this);
        if (!b()) {
            this.j = new DIYMainMapWidgetManager();
            this.b = new bdu(this.j, null);
            this.b.e();
        }
        if (this.a != null) {
            this.a.hideDragBar();
            this.a.setAnchorHeight((int) (getResources().getDisplayMetrics().density * 200.0f));
            this.a.setMinHeight((int) (getResources().getDisplayMetrics().density * 200.0f));
        }
        a(true);
        c();
        cdd.n().a(this.l);
        this.g.a(this.c);
    }

    public IWidgetProperty[] customPageWidgets() {
        return this.k.getPageMapWidgets();
    }

    public boolean onShowPoiTipView(PageBundle pageBundle, int i2) {
        bci bci = (bci) a.a.a(bci.class);
        if (bci == null || pageBundle == null || !(pageBundle.getObject("POI") instanceof POI)) {
            return super.onShowPoiTipView(pageBundle, i2);
        }
        bci.a(this, (POI) pageBundle.getObject("POI"), null, pageBundle.getBoolean(Constant.KEY_IS_FAVORITE, false));
        finish();
        return true;
    }

    static /* synthetic */ void a(TrafficMainMapPage trafficMainMapPage, TrafficTopic trafficTopic) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < trafficTopic.getTrafficGroup().size(); i2++) {
            csa csa = trafficTopic.getTrafficGroup().get(i2);
            Label3rd label3rd = new Label3rd();
            label3rd.mMainkey = Integer.parseInt(csa.b);
            label3rd.mLabelName = csa.a;
            label3rd.mSubkey = Integer.parseInt(csa.c);
            label3rd.mMinzoom = csa.e;
            label3rd.mRank = (float) csa.d;
            label3rd.mPoiId = csa.a;
            GeoPoint geoPoint = new GeoPoint(csa.f, csa.g);
            label3rd.mP20X = geoPoint.x;
            label3rd.mP20Y = geoPoint.y;
            arrayList.add(label3rd);
        }
        trafficMainMapPage.getMapView().a(16777216, (Label3rd[]) arrayList.toArray(new Label3rd[arrayList.size()]), false);
    }

    static /* synthetic */ void b(TrafficMainMapPage trafficMainMapPage, TrafficTopic trafficTopic) {
        TrafficMainMapPage trafficMainMapPage2 = trafficMainMapPage;
        int size = trafficTopic.getTrafficGroup().size();
        GeoPoint[] geoPointArr = new GeoPoint[(size + 1)];
        for (int i2 = 0; i2 < size; i2++) {
            csa csa = trafficTopic.getTrafficGroup().get(i2);
            geoPointArr[i2] = new GeoPoint(csa.f, csa.g);
        }
        geoPointArr[size] = new GeoPoint(trafficMainMapPage2.c.e, trafficMainMapPage2.c.f);
        if (geoPointArr.length > 1) {
            Context context = trafficMainMapPage.getContext();
            if (context != null && trafficMainMapPage.isAlive()) {
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                int applyDimension = (int) TypedValue.applyDimension(1, 44.0f, displayMetrics);
                int a = bet.a(trafficMainMapPage.getContext(), 54) + 10 + applyDimension;
                int i3 = displayMetrics.widthPixels;
                int i4 = displayMetrics.heightPixels;
                Rect a2 = a(geoPointArr);
                a a3 = new a().a(a2, a, context.getResources().getDrawable(R.drawable.tmc_poi_hl).getIntrinsicHeight() + 10, bet.a(trafficMainMapPage.getContext(), 54) + 10 + applyDimension, trafficMainMapPage2.h + applyDimension);
                bty mapView = trafficMainMapPage.getMapView();
                a a4 = a3.a(mapView, i3, i4, i3 / 2, (i4 - trafficMainMapPage2.h) / 2);
                a4.j = 0;
                a4.a().a();
            }
        }
    }
}
