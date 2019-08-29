package com.autonavi.map.search.fragment;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.maplayer.api.VMapPageConfig;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IEventDelegate;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapWidgetManager;
import com.autonavi.bundle.uitemplate.mapwidget.widget.search.MiniSearchFramePresenter;
import com.autonavi.bundle.uitemplate.page.AbstractSlidablePage;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.LineOverlay.LineType;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.LineOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PolygonOverlayItem;
import com.autonavi.jni.eyrie.amap.redesign.maps.typedef.Coord;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.MapPointPOI;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.IAMapHomePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.search.inter.ICQDetailPageController;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@VMapPageConfig(name = "CQDetail")
@OverlayPageProperty(overlays = {@OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.SaveOverlay, visible = true), @OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.LocalReportOverlay, visible = true)})
public class SearchCQDetailPage extends AbstractSlidablePage<cba> implements bek, bgm, bgo, defpackage.bur.a, IVoiceCmdResponder, launchModeSingleTask, IAMapHomePage, emc {
    public static final String a = "SearchCQDetailPage";
    public ICQDetailPageController b;
    public byj c;
    public bza d;
    public byv e;
    public byu f;
    public List<Long> g;
    public int h;
    public POI i;
    public Object j;
    public bdu k;
    public CQDetailPageMapWidgetManager l = new CQDetailPageMapWidgetManager(this);
    public bea m;
    apt n;
    public MainMapEventListener o = new awc() {
        public final void onUserMapTouchEvent(MotionEvent motionEvent) {
            SearchCQDetailPage.this.onMapTouchEvent(motionEvent);
        }

        public final boolean onBlankClick() {
            bty mapView = SearchCQDetailPage.this.getMapView();
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
            SearchCQDetailPage.this.finish();
            return super.onBlankClick();
        }
    };
    public bei p = new bei() {
        public final void onIndoorChanged(boolean z, ami ami) {
            byu a2 = SearchCQDetailPage.this.f;
            if (a2.b != null) {
                if (a2.c == null) {
                    a2.b.setVisible(!z);
                } else if ("citycard".equals(a2.c.getIndustry())) {
                    bty mapView = DoNotUseTool.getMapManager().getMapView();
                    if (mapView != null) {
                        a2.b.setVisible(mapView.w() < 13);
                    }
                } else {
                    a2.b.setVisible(!z);
                }
            }
            if (a2.a != null) {
                a2.a.setVisible(!z);
            }
        }
    };
    private BaseCQDetailOwner q = null;
    private bxr u;
    private boolean v;
    private cdy w;
    private DIYMainMapWidgetManager x;

    public enum POI_DETAIL_TYPE {
        DEFAULT,
        CQ_VIEW,
        PAGE
    }

    static class a implements aps, IEventDelegate {
        public a() {
            apr apr = (apr) defpackage.esb.a.a.a(apr.class);
            if (apr != null) {
                apr.a((aps) this);
            }
        }

        public final void callback(CharSequence charSequence, int i) {
            MiniSearchFramePresenter miniSearchFramePresenter = (MiniSearchFramePresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.MINI_SEARCH);
            if (miniSearchFramePresenter != null) {
                miniSearchFramePresenter.updateText(charSequence, i);
            }
        }
    }

    @Nullable
    public final View a(Context context) {
        return null;
    }

    @Nullable
    public final View b(Context context) {
        return null;
    }

    @Nullable
    public final View c(Context context) {
        return null;
    }

    public void finishSelf() {
    }

    public View getMapSuspendView() {
        return null;
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

    public boolean needKeepSessionAlive() {
        return false;
    }

    /* access modifiers changed from: private */
    /* renamed from: f */
    public cba createPresenter() {
        return new cba(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        this.n = (apt) ank.a(apt.class);
        setContentView(R.layout.search_cq_detail);
        if (this.b == null) {
            this.b = (ICQDetailPageController) ank.a(ICQDetailPageController.class);
            if (this.b != null) {
                ICQDetailPageController iCQDetailPageController = this.b;
                if (this.q == null) {
                    this.q = new BaseCQDetailOwner(this);
                }
                iCQDetailPageController.init(this.q);
            }
        }
        this.c = new byk(this, this.q);
        this.u = new bxr(this.c);
        this.d = new bza(this);
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            this.x = new DIYMainMapWidgetManager();
            this.k = new bdu(this.x, null);
            this.k.e();
        }
        if (this.q != null) {
            BaseCQDetailOwner baseCQDetailOwner = this.q;
            baseCQDetailOwner.c = new WeakReference<>(baseCQDetailOwner.a.getTopMapInteractiveView());
            baseCQDetailOwner.d = new WeakReference<>(baseCQDetailOwner.a.getMapSuspendBtnView2());
            baseCQDetailOwner.e = new WeakReference<>(baseCQDetailOwner.a.getBottomMapInteractiveView());
            baseCQDetailOwner.b = new WeakReference<>(baseCQDetailOwner.a.getMapInteractiveView());
        }
        a(getArguments());
        this.e = new byv(getVMapPage(), getMapView());
        this.f = new byu(getVMapPage(), this.i);
        if (!(this.i == null || this.h == 2)) {
            a();
            this.e.a(this.i, this.h == 0);
        }
        AMapLog.d("MapWidget-SetMargin", "---SearchCQDetailPage---onCreate");
        this.b.showCQLayer(this.i, this.j, false, false);
    }

    public boolean isMapHomePage() {
        String state = this.b.getState();
        return TextUtils.isEmpty(state) || ModulePoi.TIPS.equals(state);
    }

    public final boolean u() {
        String state = this.b.getState();
        return TextUtils.isEmpty(state) || ModulePoi.TIPS.equals(state);
    }

    public final void k() {
        bzb bzb = this.d.b;
        if (bzb != null) {
            bzb.b();
        }
    }

    public final void a(PageBundle pageBundle) {
        if (pageBundle != null) {
            this.h = pageBundle.getInt("key_cq_type");
            this.i = (POI) pageBundle.getObject("key_cq_poi");
            this.v = pageBundle.getBoolean("key_cq_is_favorite");
            this.w = (cdy) pageBundle.getObject("key_cq_gps_overlay_item");
            this.g = (List) pageBundle.getObject("key_cq_subWay_Active_Ids");
        }
        if (this.b != null) {
            switch (this.h) {
                case 0:
                    this.j = g();
                    return;
                case 1:
                    this.j = h();
                    return;
                case 2:
                    this.j = i();
                    if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                        getMapView().a(LocationInstrument.getInstance().getLatestPosition().x, LocationInstrument.getInstance().getLatestPosition().y);
                        break;
                    }
                    break;
            }
        }
    }

    public final void a() {
        cdz d2 = getSuspendManager().d();
        if (d2 != null) {
            d2.f();
            d2.a(false);
        }
    }

    public final void a(POI poi) {
        byu byu = this.f;
        if (!(poi == null || poi.getPoiExtra() == null || poi.getPoiExtra().get("poi_polygon_bounds") == null)) {
            ArrayList arrayList = (ArrayList) poi.getPoiExtra().get("poi_polygon_bounds");
            if (arrayList != null && arrayList.size() > 0) {
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    ArrayList arrayList2 = (ArrayList) arrayList.get(i2);
                    int size = arrayList2.size();
                    Coord[] coordArr = new Coord[size];
                    for (int i3 = 0; i3 < size; i3++) {
                        Coord coord = new Coord();
                        coord.lon = ((GeoPoint) arrayList2.get(i3)).getLongitude();
                        coord.lat = ((GeoPoint) arrayList2.get(i3)).getLatitude();
                        coordArr[i3] = coord;
                    }
                    LineOverlayItem lineOverlayItem = new LineOverlayItem(LineType.LineTypeColor);
                    lineOverlayItem.points.addAll(Arrays.asList(coordArr));
                    lineOverlayItem.lineWidth = 2;
                    lineOverlayItem.borderWidth = 2;
                    lineOverlayItem.fillColor = -784291841;
                    lineOverlayItem.setFillTextureResource(R.drawable.map_lr);
                    byu.a.addItem(lineOverlayItem);
                }
            }
        }
        ArrayList arrayList3 = null;
        if (poi != null) {
            if (!(poi.getPoiExtra() == null || poi.getPoiExtra().get("poi_polygon_bounds") == null)) {
                arrayList3 = (ArrayList) poi.getPoiExtra().get("poi_polygon_bounds");
            }
            if (arrayList3 != null) {
                for (int i4 = 0; i4 < arrayList3.size(); i4++) {
                    ArrayList arrayList4 = (ArrayList) arrayList3.get(i4);
                    Coord[] coordArr2 = new Coord[arrayList4.size()];
                    int size2 = arrayList4.size();
                    for (int i5 = 0; i5 < size2; i5++) {
                        Coord coord2 = new Coord();
                        coord2.lon = ((GeoPoint) arrayList4.get(i5)).getLongitude();
                        coord2.lat = ((GeoPoint) arrayList4.get(i5)).getLatitude();
                        coordArr2[i5] = coord2;
                    }
                    PolygonOverlayItem polygonOverlayItem = new PolygonOverlayItem();
                    polygonOverlayItem.fillColor = 650440190;
                    polygonOverlayItem.points.addAll(Arrays.asList(coordArr2));
                    byu.b.addItem(polygonOverlayItem);
                }
            }
        }
        byu.a(poi);
    }

    public final boolean b() {
        return this.h == 2;
    }

    private Object g() {
        bxr.a(this.i, this.i.getName());
        POI poi = this.i;
        this.i.getName();
        return a(poi, new defpackage.byj.a() {
        });
    }

    private Object h() {
        PageBundle pageBundle = new PageBundle();
        String string = AMapAppGlobal.getApplication().getString(R.string.select_point_from_map);
        String string2 = AMapAppGlobal.getApplication().getString(R.string.select_point_from_map);
        GeocodePOI geocodePOI = (GeocodePOI) this.i.as(GeocodePOI.class);
        geocodePOI.setName(string);
        return a(geocodePOI, string2, pageBundle, new defpackage.byj.a() {
        });
    }

    private Object i() {
        if (this.w != null) {
            GpsPOI gpsPOI = (GpsPOI) this.w.g.as(GpsPOI.class);
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("POI", gpsPOI);
            String b2 = this.w.b();
            String c2 = this.w.c();
            if (TextUtils.isEmpty(b2)) {
                b2 = gpsPOI.getName();
            }
            pageBundle.putString("mainTitle", b2);
            pageBundle.putString("viceTitle", c2);
            pageBundle.putObject("POI", gpsPOI);
            if (this.i == null) {
                this.i = gpsPOI;
            }
            if (this.c != null) {
                return this.c.a(pageBundle, new defpackage.byj.a() {
                });
            }
        }
        return null;
    }

    public final void a(boolean z) {
        if (this.b != null) {
            this.b.onResume(z);
        }
    }

    public static POI_DETAIL_TYPE c() {
        return POI_DETAIL_TYPE.CQ_VIEW;
    }

    private Object a(POI poi, String str, PageBundle pageBundle, defpackage.byj.a aVar) {
        if (TextUtils.isEmpty(str)) {
            str = poi.getName();
        }
        pageBundle.putString("mainTitle", str);
        pageBundle.putString("viceTitle", null);
        pageBundle.putObject("POI", poi);
        if (this.c != null) {
            return this.c.a(pageBundle, aVar);
        }
        return null;
    }

    private Object a(POI poi, defpackage.byj.a aVar) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject("POI", poi.as(MapPointPOI.class));
        if (this.c != null) {
            return this.c.a(pageBundle, aVar);
        }
        return null;
    }

    public boolean isShowPageHeader() {
        return this.b == null || ModulePoi.TIPS.equals(this.b.getState());
    }

    public boolean isShowMapWidgets() {
        return this.b == null || ModulePoi.TIPS.equals(this.b.getState());
    }

    public IWidgetProperty[] customPageWidgets() {
        if (this.l != null) {
            return this.l.getPageMapWidgets();
        }
        return null;
    }

    public void onInitMapWidget() {
        MiniSearchFramePresenter miniSearchFramePresenter = (MiniSearchFramePresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.MINI_SEARCH);
        if (miniSearchFramePresenter != null && miniSearchFramePresenter.getEventDelegate() == null) {
            miniSearchFramePresenter.setEventDelegate(new a());
        }
        if (!Stub.getMapWidgetManager().isNewHomePage()) {
            DIYMainMapPresenter dIYMainMapPresenter = (DIYMainMapPresenter) Stub.getMapWidgetManager().getPresenter("diy");
            if (dIYMainMapPresenter != null) {
                dIYMainMapPresenter.init(this.x);
            }
        }
    }

    public boolean onShowPoiTipView(PageBundle pageBundle, int i2) {
        bci bci = (bci) defpackage.esb.a.a.a(bci.class);
        if (bci == null || pageBundle == null || !(pageBundle.getObject("POI") instanceof POI)) {
            return super.onShowPoiTipView(pageBundle, i2);
        }
        bci.a(this, (POI) pageBundle.getObject("POI"), null, pageBundle.getBoolean(Constant.KEY_IS_FAVORITE, false));
        return true;
    }

    public static void d() {
        awo awo = (awo) defpackage.esb.a.a.a(awo.class);
        if (awo != null) {
            awo.b(9001);
            if (awo.d()) {
                awo.e();
            }
            awo.a((List<Integer>) awo.j());
        }
    }

    public static void e() {
        awo awo = (awo) defpackage.esb.a.a.a(awo.class);
        if (awo != null) {
            awo.c(9001);
            awo.f();
        }
    }
}
