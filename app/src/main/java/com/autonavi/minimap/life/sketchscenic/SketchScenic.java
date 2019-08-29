package com.autonavi.minimap.life.sketchscenic;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.datamodel.point.GeoPointHD;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.amaphome.page.MapHomePage;
import com.autonavi.bundle.amaphome.page.MapHomeTabPage;
import com.autonavi.bundle.life.api.api.IScenicGuideItemClickCallback;
import com.autonavi.bundle.life.api.api.IScenicPlayCloseCallback;
import com.autonavi.bundle.life.api.api.IScenicPlayRouteItemClickCallback;
import com.autonavi.bundle.life.api.api.IScenicPlaySelectCallback;
import com.autonavi.bundle.life.api.entity.ScenicGuideEntity;
import com.autonavi.bundle.life.api.entity.ScenicGuideListItemEntity;
import com.autonavi.bundle.life.api.entity.ScenicPlayEntity;
import com.autonavi.bundle.life.api.entity.ScenicPlayRouteItemEntity;
import com.autonavi.bundle.life.api.entity.ScenicSpeakEntity;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.scale.ScaleMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.scale.ScaleWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.search.MiniSearchFramePresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.search.MiniSearchFrameWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.weather.WeatherRestrictMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.weather.WeatherRestrictWidgetPresenter;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.search.fragment.SearchCQDetailPage;
import com.autonavi.map.suspend.refactor.scenic.ISketchScenic;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.filter.ScenicGuideFilterRequestHolder;
import com.autonavi.minimap.filter.param.ScenicGuideFilterRequest;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SketchScenic implements ISketchScenic {
    private static final String p = "SketchScenic";
    public dqx a;
    public drg b;
    public dqw c;
    public dra d;
    public boolean e;
    public dqs f;
    public dqt g;
    public ScenicEntranceEntity h;
    public ScenicGuideEntity i;
    public ScenicSpeakEntity j;
    public ScenicPlayEntity k;
    public bid l;
    public bty m;
    public drd n;
    public boolean o = false;
    private boolean q;
    /* access modifiers changed from: private */
    public int r;
    private String s = "";

    public final void c() {
        if (this.e) {
            if (!this.a.a()) {
                IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
                if (iMainMapService != null) {
                    iMainMapService.a(false, "", "");
                    a(iMainMapService);
                }
                if (bno.a) {
                    AMapLog.i(p, "SketchScenic.showIfNeeded: hasSketchMapInEngine is false", true);
                }
                return;
            }
            amv amv = this.a.a;
            if (bno.a) {
                String str = p;
                StringBuilder sb = new StringBuilder("SketchScenic.showIfNeeded: Show. mHasScenicHDMap=");
                sb.append(amv.d);
                sb.append("; mHasScenicHDMapData=");
                sb.append(amv.e);
                AMapLog.i(str, sb.toString(), true);
            }
            if (this.b != null) {
                this.b.a(amv.a);
            }
            Class<?> c2 = AMapPageUtil.getMVPActivityContext().c();
            if (!(this.n == null || AMapPageUtil.getMVPActivityContext() == null || c2 == null || c2.getName().endsWith(SearchCQDetailPage.a))) {
                this.n.b();
            }
            a(amv);
        }
    }

    private void a(amv amv) {
        boolean z = !TextUtils.isEmpty(this.s) && this.s.equals(amv.a);
        if (!this.q || !z) {
            g();
            if (!this.c.a()) {
                if (bno.a) {
                    AMapLog.i(p, "SketchScenic.showSketchIfNeeded: isAllowToShow is false", true);
                }
                return;
            }
            if (bno.a) {
                AMapLog.i(p, "SketchScenic.showSketchIfNeeded: Show", true);
            }
            this.a.b();
            i();
            this.q = true;
        }
    }

    private void g() {
        amv amv = this.a.a;
        if (amv != null && !TextUtils.isEmpty(amv.a) && ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).isNewHomePage()) {
            this.f = new dqs();
            this.f.a(DoNotUseTool.getMapView().w(), amv.a, new a() {
                public final void a(ScenicEntranceEntity scenicEntranceEntity, ScenicGuideEntity scenicGuideEntity, ScenicSpeakEntity scenicSpeakEntity, ScenicPlayEntity scenicPlayEntity) {
                    SketchScenic.this.h = scenicEntranceEntity;
                    SketchScenic.this.i = scenicGuideEntity;
                    SketchScenic.this.j = scenicSpeakEntity;
                    SketchScenic.this.k = scenicPlayEntity;
                    SketchScenic.this.a(scenicEntranceEntity, scenicGuideEntity, scenicSpeakEntity, scenicPlayEntity);
                }
            });
        }
    }

    public final void a(ScenicEntranceEntity scenicEntranceEntity, ScenicGuideEntity scenicGuideEntity, ScenicSpeakEntity scenicSpeakEntity, ScenicPlayEntity scenicPlayEntity) {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        bty mapView = DoNotUseTool.getMapView();
        if (iMainMapService != null && mapView != null) {
            if (this.c.a()) {
                if (scenicEntranceEntity == null || scenicEntranceEntity.a != 1 || scenicEntranceEntity.d > mapView.w() || TextUtils.isEmpty(scenicEntranceEntity.c) || TextUtils.isEmpty(scenicEntranceEntity.b)) {
                    iMainMapService.a(false, "", "");
                } else {
                    iMainMapService.a(true, scenicEntranceEntity.c, scenicEntranceEntity.b);
                }
            }
            a(scenicGuideEntity, scenicSpeakEntity, iMainMapService, mapView);
            ScenicEntranceEntity scenicEntranceEntity2 = scenicEntranceEntity;
            ScenicGuideEntity scenicGuideEntity2 = scenicGuideEntity;
            ScenicPlayEntity scenicPlayEntity2 = scenicPlayEntity;
            IMainMapService iMainMapService2 = iMainMapService;
            bty bty = mapView;
            a(scenicEntranceEntity2, scenicGuideEntity2, scenicPlayEntity2, iMainMapService2, bty);
            b(scenicEntranceEntity2, scenicGuideEntity2, scenicPlayEntity2, iMainMapService2, bty);
            amv amv = this.a.a;
            if (amv != null && !TextUtils.isEmpty(amv.a) && ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).isNewHomePage()) {
                if (TextUtils.isEmpty(this.s) || !this.s.equals(amv.a)) {
                    this.s = amv.a;
                }
            }
        }
    }

    private void a(ScenicGuideEntity scenicGuideEntity, ScenicSpeakEntity scenicSpeakEntity, IMainMapService iMainMapService, bty bty) {
        boolean z;
        ScenicSpeakEntity scenicSpeakEntity2 = scenicSpeakEntity;
        if (scenicSpeakEntity2 == null || TextUtils.isEmpty(scenicSpeakEntity2.d) || scenicGuideEntity.a > bty.w()) {
            if (iMainMapService.isScenicSpeakShown()) {
                iMainMapService.setScenicSpeak(false, "", "", "", 0, false, null);
            }
            return;
        }
        amv amv = this.a.a;
        if (amv != null && !TextUtils.isEmpty(amv.a) && ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).isNewHomePage()) {
            if (scenicSpeakEntity2.g == 0 || TextUtils.isEmpty(scenicSpeakEntity2.f) || TextUtils.isEmpty(scenicSpeakEntity2.e)) {
                z = false;
            } else {
                LogManager.actionLogV25("P00383", "B026", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv.a));
                z = true;
            }
            if (!iMainMapService.isScenicSpeakShown()) {
                LogManager.actionLogV25("P00383", "B025", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv.a));
                iMainMapService.setScenicSpeak(true, scenicSpeakEntity2.f, scenicSpeakEntity2.d, scenicSpeakEntity2.e, scenicSpeakEntity2.g, z, amv);
                return;
            }
            if (!this.s.equals(amv.a)) {
                iMainMapService.setScenicSpeak(false, "", "", "", 0, false, null);
                LogManager.actionLogV25("P00383", "B025", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv.a));
                iMainMapService.setScenicSpeak(true, scenicSpeakEntity2.f, scenicSpeakEntity2.d, scenicSpeakEntity2.e, scenicSpeakEntity2.g, z, amv);
            }
        }
    }

    private void a(ScenicEntranceEntity scenicEntranceEntity, ScenicGuideEntity scenicGuideEntity, ScenicPlayEntity scenicPlayEntity, IMainMapService iMainMapService, bty bty) {
        ScenicPlayEntity scenicPlayEntity2 = scenicPlayEntity;
        IMainMapService iMainMapService2 = iMainMapService;
        if (scenicPlayEntity2 != null) {
            ScenicGuideEntity scenicGuideEntity2 = scenicGuideEntity;
            if (scenicGuideEntity2.a <= bty.w() && scenicPlayEntity2.h != null && scenicPlayEntity2.h.size() > 0) {
                amv amv = this.a.a;
                if (amv != null && !TextUtils.isEmpty(amv.a) && ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).isNewHomePage()) {
                    int scenicPlayWidgetLocationY = iMainMapService.getScenicPlayWidgetLocationY();
                    if (scenicPlayEntity2.h.size() > 1) {
                        if (!iMainMapService.isScenicPlayShown()) {
                            LogManager.actionLogV25("P00383", LogConstant.MAIN_MSGBOX_TIP_CLICK, new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv.a));
                            a(scenicPlayEntity2);
                            a(scenicEntranceEntity, scenicGuideEntity2, scenicPlayEntity2, iMainMapService2, bty, amv, scenicPlayWidgetLocationY);
                        } else if (!this.s.equals(amv.a)) {
                            d(iMainMapService2);
                            LogManager.actionLogV25("P00383", LogConstant.MAIN_MSGBOX_TIP_CLICK, new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv.a));
                            a(scenicPlayEntity2);
                            a(scenicEntranceEntity, scenicGuideEntity2, scenicPlayEntity2, iMainMapService2, bty, amv, scenicPlayWidgetLocationY);
                        }
                        if (!this.o && scenicPlayEntity2.g) {
                            this.n.a(amv.a, scenicPlayEntity2.h.get(0), scenicPlayEntity2.d, scenicPlayWidgetLocationY);
                            return;
                        }
                    } else if (scenicPlayEntity2.h.size() == 1 && amv != null && !TextUtils.isEmpty(amv.a) && ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).isNewHomePage()) {
                        if (iMainMapService.isScenicPlayShown()) {
                            iMainMapService2.setScenicPlay(false, null);
                        }
                        if (!this.o && scenicPlayEntity2.g) {
                            this.n.a(amv.a, scenicPlayEntity2.h.get(0), scenicPlayEntity2.d, scenicPlayWidgetLocationY);
                        }
                    } else {
                        return;
                    }
                    return;
                }
                return;
            }
        }
        d(iMainMapService2);
    }

    private void a(ScenicEntranceEntity scenicEntranceEntity, ScenicGuideEntity scenicGuideEntity, ScenicPlayEntity scenicPlayEntity, IMainMapService iMainMapService, bty bty, amv amv, int i2) {
        final amv amv2 = amv;
        final ScenicPlayEntity scenicPlayEntity2 = scenicPlayEntity;
        final ScenicEntranceEntity scenicEntranceEntity2 = scenicEntranceEntity;
        final ScenicGuideEntity scenicGuideEntity2 = scenicGuideEntity;
        final IMainMapService iMainMapService2 = iMainMapService;
        final bty bty2 = bty;
        final int i3 = i2;
        AnonymousClass2 r0 = new IScenicPlaySelectCallback() {
            public final void a(boolean z) {
                LogManager.actionLogV25("P00383", "B032", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv2.a));
                if (z) {
                    String str = "";
                    ArrayList<ScenicPlayRouteItemEntity> arrayList = scenicPlayEntity2.h;
                    if (!(arrayList == null || arrayList.size() == 0)) {
                        Iterator<ScenicPlayRouteItemEntity> it = arrayList.iterator();
                        while (it.hasNext()) {
                            String valueOf = String.valueOf(it.next().a);
                            if (valueOf != null) {
                                if (TextUtils.isEmpty(str)) {
                                    str = valueOf;
                                } else {
                                    str = str.concat("、").concat(valueOf);
                                }
                            }
                        }
                    }
                    LogManager.actionLogV25("P00383", "B031", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv2.a), new SimpleEntry("itemid", str));
                    SketchScenic.a(SketchScenic.this, scenicEntranceEntity2, amv2, scenicGuideEntity2, scenicPlayEntity2, iMainMapService2, bty2);
                    if (!SketchScenic.this.o && scenicPlayEntity2.g) {
                        SketchScenic.this.n.a(amv2.a, scenicPlayEntity2.h.get(0), scenicPlayEntity2.d, i3);
                    }
                    return;
                }
                SketchScenic.a(SketchScenic.this, scenicEntranceEntity2, iMainMapService2, bty2);
            }
        };
        iMainMapService.setScenicPlay(true, r0);
    }

    private void a(ScenicPlayEntity scenicPlayEntity) {
        if (scenicPlayEntity.g) {
            this.r = 0;
        } else {
            this.r = -1;
        }
    }

    private static void b(IMainMapService iMainMapService) {
        if (iMainMapService.isScenicPlayShown()) {
            iMainMapService.setScenicPlay(false, null);
        }
    }

    public static void e() {
        bid pageContext = AMapPageUtil.getPageContext();
        if ((pageContext instanceof MapHomePage) || (pageContext instanceof MapHomeTabPage)) {
            ScaleWidgetPresenter scaleWidgetPresenter = (ScaleWidgetPresenter) ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).getPresenter(WidgetType.SCALE);
            if (scaleWidgetPresenter != null) {
                ((ScaleMapWidget) scaleWidgetPresenter.getWidget()).setVisibility(8);
            }
            WeatherRestrictWidgetPresenter weatherRestrictWidgetPresenter = (WeatherRestrictWidgetPresenter) ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).getPresenter(WidgetType.WEATHER_RESTRICT);
            if (weatherRestrictWidgetPresenter != null) {
                ((WeatherRestrictMapWidget) weatherRestrictWidgetPresenter.getWidget()).setVisibility(8);
            }
            return;
        }
        if (pageContext instanceof SearchCQDetailPage) {
            ScaleWidgetPresenter scaleWidgetPresenter2 = (ScaleWidgetPresenter) ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).getPresenter(WidgetType.SCALE);
            if (scaleWidgetPresenter2 != null) {
                ((ScaleMapWidget) scaleWidgetPresenter2.getWidget()).setVisibility(8);
            }
            MiniSearchFramePresenter miniSearchFramePresenter = (MiniSearchFramePresenter) ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).getPresenter(WidgetType.MINI_SEARCH);
            if (miniSearchFramePresenter != null) {
                ((MiniSearchFrameWidget) miniSearchFramePresenter.getWidget()).setVisibility(8);
            }
        }
    }

    private static void h() {
        ScaleWidgetPresenter scaleWidgetPresenter = (ScaleWidgetPresenter) ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).getPresenter(WidgetType.SCALE);
        if (scaleWidgetPresenter != null) {
            ((ScaleMapWidget) scaleWidgetPresenter.getWidget()).setVisibility(0);
        }
        WeatherRestrictWidgetPresenter weatherRestrictWidgetPresenter = (WeatherRestrictWidgetPresenter) ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).getPresenter(WidgetType.WEATHER_RESTRICT);
        if (weatherRestrictWidgetPresenter != null) {
            ((WeatherRestrictMapWidget) weatherRestrictWidgetPresenter.getWidget()).setVisibility(0);
        }
        MiniSearchFramePresenter miniSearchFramePresenter = (MiniSearchFramePresenter) ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).getPresenter(WidgetType.MINI_SEARCH);
        if (miniSearchFramePresenter != null) {
            ((MiniSearchFrameWidget) miniSearchFramePresenter.getWidget()).setVisibility(0);
        }
    }

    private void b(ScenicEntranceEntity scenicEntranceEntity, ScenicGuideEntity scenicGuideEntity, ScenicPlayEntity scenicPlayEntity, IMainMapService iMainMapService, bty bty) {
        if (scenicGuideEntity == null || scenicGuideEntity.a > bty.w() || scenicGuideEntity.b == null || scenicGuideEntity.b.size() <= 1) {
            if (iMainMapService.isScenicGuideShown()) {
                iMainMapService.setScenicGuide(false, null, null);
                j();
                l();
            }
        } else if (!iMainMapService.isScenicGuideShown()) {
            c(scenicEntranceEntity, scenicGuideEntity, scenicPlayEntity, iMainMapService, bty);
        } else {
            amv amv = this.a.a;
            if (amv != null && !TextUtils.isEmpty(amv.a) && ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).isNewHomePage() && !this.s.equals(amv.a)) {
                iMainMapService.setScenicGuide(false, null, null);
                j();
                l();
                c(scenicEntranceEntity, scenicGuideEntity, scenicPlayEntity, iMainMapService, bty);
            }
        }
    }

    private void a(ScenicEntranceEntity scenicEntranceEntity, final amv amv, final ScenicPlayEntity scenicPlayEntity, final IMainMapService iMainMapService, bty bty) {
        iMainMapService.setScenicPlayRoute(true, scenicPlayEntity, new IScenicPlayRouteItemClickCallback() {
            public final void a(int i) {
                SketchScenic.this.r = i;
                int i2 = scenicPlayEntity.h.get(i).a;
                LogManager.actionLogV25("P00383", "B034", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv.a), new SimpleEntry("click", Integer.valueOf(i2)), new SimpleEntry("itemid", Integer.valueOf(i2)));
                int scenicPlayWidgetLocationY = iMainMapService.getScenicPlayWidgetLocationY();
                drd drd = SketchScenic.this.n;
                int i3 = scenicPlayEntity.d;
                drd.a.a(amv.a, scenicPlayEntity.h.get(i), drd.b, i3, true, scenicPlayWidgetLocationY, i);
            }
        }, a(scenicEntranceEntity, iMainMapService, bty), amv, this.r);
    }

    @NonNull
    private IScenicPlayCloseCallback a(final ScenicEntranceEntity scenicEntranceEntity, final IMainMapService iMainMapService, final bty bty) {
        return new IScenicPlayCloseCallback() {
            public final void a() {
                SketchScenic.a(SketchScenic.this, scenicEntranceEntity, iMainMapService, bty);
            }
        };
    }

    private static void c(IMainMapService iMainMapService) {
        if (iMainMapService.isScenicPlayRouteShown()) {
            iMainMapService.setScenicPlayRoute(false, null, null, null, null, 0);
            h();
        }
    }

    private void c(ScenicEntranceEntity scenicEntranceEntity, ScenicGuideEntity scenicGuideEntity, ScenicPlayEntity scenicPlayEntity, IMainMapService iMainMapService, bty bty) {
        final amv amv = this.a.a;
        if (amv != null && !TextUtils.isEmpty(amv.a) && ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).isNewHomePage()) {
            String str = "";
            ArrayList<ScenicGuideListItemEntity> arrayList = scenicGuideEntity.b;
            Iterator<ScenicGuideListItemEntity> it = arrayList.iterator();
            while (it.hasNext()) {
                String str2 = it.next().b;
                if (str2 != null) {
                    if (TextUtils.isEmpty(str)) {
                        str = str2;
                    } else {
                        str = str.concat(MergeUtil.SEPARATOR_KV).concat(str2);
                    }
                }
            }
            LogManager.actionLogV25("P00383", "B019", new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv.a), new SimpleEntry("type", str));
            final ScenicEntranceEntity scenicEntranceEntity2 = scenicEntranceEntity;
            final ScenicPlayEntity scenicPlayEntity2 = scenicPlayEntity;
            final IMainMapService iMainMapService2 = iMainMapService;
            final bty bty2 = bty;
            AnonymousClass5 r1 = new IScenicGuideItemClickCallback() {
                public final void a(boolean z, String str, final String str2, boolean z2) {
                    if (z) {
                        SketchScenic.this.o = true;
                        if (!z2) {
                            SketchScenic.this.g = new dqt();
                            dqt b2 = SketchScenic.this.g;
                            String str3 = amv.a;
                            AnonymousClass1 r0 = new a() {
                                public final void a(String str) {
                                    SketchScenic.a(SketchScenic.this, amv, str2, str);
                                }
                            };
                            b2.a = new ScenicGuideFilterRequest();
                            b2.a.b = "83";
                            b2.a.d = str3;
                            b2.a.e = str;
                            ScenicGuideFilterRequest scenicGuideFilterRequest = b2.a;
                            StringBuilder sb = new StringBuilder();
                            bty mapView = DoNotUseTool.getMapManager().getMapView();
                            if (mapView != null) {
                                Rect H = mapView.H();
                                DPoint a2 = cfg.a((long) H.left, (long) H.top);
                                DPoint a3 = cfg.a((long) H.right, (long) H.bottom);
                                sb.append(a2.x);
                                sb.append(MergeUtil.SEPARATOR_KV);
                                sb.append(a2.y);
                                sb.append(MergeUtil.SEPARATOR_KV);
                                sb.append(a3.x);
                                sb.append(MergeUtil.SEPARATOR_KV);
                                sb.append(a3.y);
                            }
                            scenicGuideFilterRequest.c = sb.toString();
                            ScenicGuideFilterRequest scenicGuideFilterRequest2 = b2.a;
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(LocationInstrument.getInstance().getLatestPosition().getLongitude());
                            sb2.append(",");
                            sb2.append(LocationInstrument.getInstance().getLatestPosition().getLatitude());
                            scenicGuideFilterRequest2.f = sb2.toString();
                            ScenicGuideFilterRequestHolder.getInstance().sendScenicGuideFilter(b2.a, new ScenicGuideRequest$1(b2, r0));
                        }
                        SketchScenic.a(SketchScenic.this, scenicEntranceEntity2, scenicPlayEntity2, iMainMapService2, bty2);
                        return;
                    }
                    SketchScenic.this.o = false;
                    if (!z2 && SketchScenic.this.d != null) {
                        SketchScenic.this.d.d = str2;
                        SketchScenic.this.d.c = amv.a;
                        SketchScenic.this.j();
                    }
                }
            };
            iMainMapService.setScenicGuide(true, arrayList, r1);
        }
    }

    private void a(String str, String str2) {
        ToastHelper.showLongToast("未找到相关结果");
        a(str, str2, (String) "2");
        j();
    }

    private static void a(String str, String str2, String str3) {
        LogManager.actionLogV25("P00383", "B023", new SimpleEntry("type", str), new SimpleEntry("from", str3), new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, str2));
    }

    private boolean a(List<ScenicGuidePoiEntity> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            GeoPointHD geoPointHD = new GeoPointHD(Double.valueOf(list.get(i2).e).doubleValue(), Double.valueOf(list.get(i2).f).doubleValue());
            if (this.l != null && cfe.a(this.l, geoPointHD, 0, 100, 0, 0)) {
                return true;
            }
        }
        return false;
    }

    private static void i() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            iMainMapService.h();
        }
    }

    private static List<ScenicGuidePoiEntity> a(JSONArray jSONArray) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            ScenicGuidePoiEntity scenicGuidePoiEntity = new ScenicGuidePoiEntity();
            JSONObject jSONObject = null;
            try {
                jSONObject = (JSONObject) jSONArray.get(i2);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            scenicGuidePoiEntity.b = jSONObject.optString("name");
            scenicGuidePoiEntity.a = jSONObject.optString(LocationInstrument.LOCATION_EXTRAS_KEY_POIID);
            scenicGuidePoiEntity.i = jSONObject.optString("render_rank");
            scenicGuidePoiEntity.g = jSONObject.optString("render_style_main");
            scenicGuidePoiEntity.h = jSONObject.optString("render_style_sub");
            scenicGuidePoiEntity.j = jSONObject.optString("minizoom");
            scenicGuidePoiEntity.k = jSONObject.optString("anchor");
            scenicGuidePoiEntity.c = jSONObject.optString("address");
            scenicGuidePoiEntity.e = jSONObject.optString(DictionaryKeys.CTRLXY_X);
            scenicGuidePoiEntity.f = jSONObject.optString(DictionaryKeys.CTRLXY_Y);
            scenicGuidePoiEntity.d = jSONObject.optString("typecode");
            arrayList.add(scenicGuidePoiEntity);
        }
        return arrayList;
    }

    public final void d() {
        a(false, false);
    }

    public final void a(boolean z, boolean z2) {
        if (this.e) {
            if (z && this.b != null) {
                this.b.a();
            }
            if (!(!z || this.d == null || this.n == null)) {
                j();
                k();
            }
            if (z2 && z && ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).isNewHomePage()) {
                IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
                if (iMainMapService != null) {
                    iMainMapService.a(false, "", "");
                    a(iMainMapService);
                }
            }
            m();
        }
    }

    public final void a(IMainMapService iMainMapService) {
        if (iMainMapService.isScenicGuideShown()) {
            iMainMapService.setScenicGuide(false, null, null);
            j();
            l();
        }
        if (iMainMapService.isScenicSpeakShown()) {
            iMainMapService.setScenicSpeak(false, "", "", "", 0, false, null);
        }
        d(iMainMapService);
    }

    private void d(IMainMapService iMainMapService) {
        b(iMainMapService);
        c(iMainMapService);
        k();
    }

    /* access modifiers changed from: private */
    public void j() {
        if (this.d != null) {
            this.d.a();
        }
    }

    private void k() {
        if (this.n != null) {
            this.n.a();
        }
    }

    private void l() {
        if (this.d != null) {
            this.d.e = null;
        }
    }

    private void m() {
        if (this.q) {
            if (bno.a) {
                AMapLog.i(p, "SketchScenic.hideSketch: Hide", true);
            }
            this.a.c();
            this.q = false;
        }
    }

    public final boolean a() {
        return this.c.a();
    }

    public final void a(boolean z) {
        this.c.a.edit().putBoolean("SKETCH_SCENIC_ALLOW_TO_SHOW", z).apply();
    }

    public final boolean b() {
        return this.a.a();
    }

    public final void f() {
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = null;
    }

    static /* synthetic */ void a(SketchScenic sketchScenic, ScenicEntranceEntity scenicEntranceEntity, amv amv, ScenicGuideEntity scenicGuideEntity, ScenicPlayEntity scenicPlayEntity, IMainMapService iMainMapService, bty bty) {
        iMainMapService.setMapHomePageStateCollapsed();
        e();
        if (scenicPlayEntity == null || scenicGuideEntity.a > bty.w() || scenicPlayEntity.h == null || scenicPlayEntity.h.size() <= 1) {
            c(iMainMapService);
        } else if (!iMainMapService.isScenicPlayRouteShown()) {
            sketchScenic.a(scenicEntranceEntity, amv, scenicPlayEntity, iMainMapService, bty);
        } else if (!sketchScenic.s.equals(amv.a)) {
            c(iMainMapService);
            sketchScenic.a(scenicEntranceEntity, amv, scenicPlayEntity, iMainMapService, bty);
        }
        iMainMapService.a(false, "", "");
        iMainMapService.closeScenicSpeakOperateView();
        iMainMapService.initScenicGuideView(scenicGuideEntity.b);
        sketchScenic.o = false;
        sketchScenic.j();
        sketchScenic.l();
    }

    static /* synthetic */ void a(SketchScenic sketchScenic, ScenicEntranceEntity scenicEntranceEntity, IMainMapService iMainMapService, bty bty) {
        if (iMainMapService.isScenicPlayRouteShown()) {
            iMainMapService.setScenicPlayRoute(false, null, null, null, null, 0);
            h();
        }
        if (sketchScenic.c.a()) {
            if (scenicEntranceEntity == null || scenicEntranceEntity.a != 1 || scenicEntranceEntity.d > bty.w() || TextUtils.isEmpty(scenicEntranceEntity.c) || TextUtils.isEmpty(scenicEntranceEntity.b)) {
                iMainMapService.a(false, "", "");
            } else {
                iMainMapService.a(true, scenicEntranceEntity.c, scenicEntranceEntity.b);
            }
        }
    }

    static /* synthetic */ void a(SketchScenic sketchScenic, amv amv, String str, String str2) {
        if (TextUtils.isEmpty(str2) || TextUtils.equals("requestGuideFailure", str2)) {
            sketchScenic.a(str, amv.a);
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(str2);
            if ("1".equals(jSONObject.optString("code"))) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                if (jSONObject2 == null) {
                    sketchScenic.a(str, amv.a);
                    return;
                }
                JSONArray optJSONArray = jSONObject2.optJSONArray("poi_list");
                if (optJSONArray != null) {
                    if (optJSONArray.length() > 0) {
                        LogManager.actionLogV25("P00383", "B020", new SimpleEntry("type", str), new SimpleEntry(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, amv.a));
                        if (sketchScenic.d != null) {
                            sketchScenic.d.d = str;
                            sketchScenic.d.c = amv.a;
                            sketchScenic.j();
                            List<ScenicGuidePoiEntity> a2 = a(optJSONArray);
                            if (a2.size() > 0) {
                                if (!sketchScenic.a(a2)) {
                                    ToastHelper.showLongToast("屏幕区域无结果");
                                    a(str, amv.a, (String) "1");
                                }
                                dra dra = sketchScenic.d;
                                dra.a.a(amv.a, a2, dra.b, str, dra.e);
                            }
                        }
                        return;
                    }
                }
                sketchScenic.a(str, amv.a);
                return;
            }
            sketchScenic.a(str, amv.a);
        } catch (JSONException e2) {
            e2.printStackTrace();
            sketchScenic.a(str, amv.a);
        }
    }

    static /* synthetic */ void a(SketchScenic sketchScenic, ScenicEntranceEntity scenicEntranceEntity, ScenicPlayEntity scenicPlayEntity, IMainMapService iMainMapService, bty bty) {
        iMainMapService.closeScenicSpeakOperateView();
        boolean z = false;
        if (!(scenicPlayEntity == null || scenicPlayEntity.h == null || scenicPlayEntity.h.size() <= 1)) {
            z = true;
        }
        iMainMapService.closeScenicPlayWidget(sketchScenic.a(scenicEntranceEntity, iMainMapService, bty), z);
        sketchScenic.k();
        sketchScenic.a(scenicPlayEntity);
    }
}
