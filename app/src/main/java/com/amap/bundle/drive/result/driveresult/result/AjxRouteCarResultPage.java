package com.amap.bundle.drive.result.driveresult.result;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drive.ajx.inter.HeadunitBtnEventCallback;
import com.amap.bundle.drive.ajx.inter.JsCommandCallback;
import com.amap.bundle.drive.ajx.inter.OnCalRouteFromData;
import com.amap.bundle.drive.ajx.inter.OnObtainFocusCallBack;
import com.amap.bundle.drive.ajx.inter.OnRouteStateChangeListener;
import com.amap.bundle.drive.ajx.inter.OnTripPoiChangeListener;
import com.amap.bundle.drive.ajx.inter.RouteEventActionInterface;
import com.amap.bundle.drive.ajx.module.ModuleDriveCommonBusiness;
import com.amap.bundle.drive.ajx.module.ModuleHeadunit;
import com.amap.bundle.drive.ajx.module.ModuleRouteDriveResult;
import com.amap.bundle.drive.navi.drivenavi.simulate.page.AjxRouteCarNaviSimulatePage;
import com.amap.bundle.drive.radar.page.NewRouteBoardPage;
import com.amap.bundle.drive.result.autonavisearchmanager.inter.CarSceneSearchAosCallback;
import com.amap.bundle.drive.result.autonavisearchmanager.view.CarSceneTip;
import com.amap.bundle.drive.result.driveresult.browser.AjxRouteCarResultBrowserPage;
import com.amap.bundle.drive.result.driveresult.etd.AjxRouteETDPage;
import com.amap.bundle.drive.result.driveresult.event.AjxRouteCarResultEventDetailPage;
import com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager;
import com.amap.bundle.drive.result.view.DriveRecommendView;
import com.amap.bundle.drive.result.view.RouteCarResultTouchEventView;
import com.amap.bundle.drive.util.DriveEyrieRouteSharingUtil;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.statistics.util.LogUtil;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.amap.bundle.voiceservice.dispatch.IVoiceDriveDispatcher;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.account.api.IAccountService;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.inter.IRouteUI.ContainerType;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.recommend.RecommendAndScaleWidget;
import com.autonavi.bundle.vui.ajx.ModuleVUI;
import com.autonavi.bundle.vui.vuistate.VUIStateManager;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LOCATION_SCENE;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.model.Coord2D;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.jni.eyrie.amap.tbt.model.RoutePOIInfo;
import com.autonavi.jni.eyrie.amap.tbt.model.RouteWayPoint;
import com.autonavi.map.core.LocationMode.LocationGpsAndNetwork;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.JsRunInfo;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AttributeListener;
import com.autonavi.minimap.basemap.favorites.data.RouteItem;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.AgroupScenes;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.minimap.poi.PoiRequestHolder;
import com.autonavi.minimap.poi.param.NaviinfoRequest;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.widget.SyncPopupWindow;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.AlertView;
import com.sina.weibo.sdk.utils.UIUtils;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class AjxRouteCarResultPage extends Ajx3Page implements OnTouchListener, bgm, OnRouteStateChangeListener, OnTripPoiChangeListener, IVoiceCmdResponder, LocationGpsAndNetwork, f {
    RouteEventActionInterface A = new RouteEventActionInterface() {
        public final void showIncidentDetail(String str) {
            int i;
            AMapLog.d("AjxRouteCarResultPage", "showIncidentDetail---JSON=".concat(String.valueOf(str)));
            if (AjxRouteCarResultPage.this.isResumed()) {
                if (AjxRouteCarResultPage.this.s != null && AjxRouteCarResultPage.this.s.e()) {
                    AjxRouteCarResultPage.this.s.m();
                }
                AjxRouteCarResultPage ajxRouteCarResultPage = AjxRouteCarResultPage.this;
                if (ajxRouteCarResultPage.k()) {
                    ajxRouteCarResultPage.j();
                }
                MapSharePreference mapSharePreference = new MapSharePreference(SharePreferenceName.SharedPreferences);
                int i2 = 0;
                if (mapSharePreference.getBooleanValue("route_board_red_point_tip", true) && !emu.a()) {
                    mapSharePreference.putBooleanValue("route_board_red_point_tip", false);
                }
                AjxRouteCarResultPage.a((String) "B113", (JSONObject) null);
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    int optInt = jSONObject.optInt("type");
                    if (optInt != 2) {
                        if (optInt != 1) {
                            if (optInt == 3) {
                                double optDouble = jSONObject.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON);
                                double optDouble2 = jSONObject.optDouble("lat");
                                int optInt2 = jSONObject.optInt("z");
                                jSONObject.optInt("poiType");
                                jSONObject.optInt("subType");
                                String optString = jSONObject.optString("poiID");
                                try {
                                    i = Integer.parseInt(optString);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    i = 0;
                                }
                                int optInt3 = jSONObject.optInt("focusIndex");
                                JSONArray optJSONArray = jSONObject.optJSONArray("routeSetId");
                                if (optJSONArray != null) {
                                    if (optJSONArray.length() != 0) {
                                        long[] jArr = new long[optJSONArray.length()];
                                        while (i2 < optJSONArray.length()) {
                                            jArr[i2] = optJSONArray.getLong(i2);
                                            i2++;
                                        }
                                        AjxRouteCarResultPage.a(AjxRouteCarResultPage.this, i, jArr, optInt3, optDouble, optDouble2, optInt2, optString);
                                        return;
                                    }
                                }
                                return;
                            }
                            if (optInt == 4) {
                                int optInt4 = jSONObject.optInt("forbiddenId");
                                int optInt5 = jSONObject.optInt("forbiddenType");
                                int optInt6 = jSONObject.optInt("vehicleType");
                                int optInt7 = jSONObject.optInt("focusIndex");
                                JSONArray optJSONArray2 = jSONObject.optJSONArray("routeSetId");
                                if (optJSONArray2 != null) {
                                    if (optJSONArray2.length() != 0) {
                                        long[] jArr2 = new long[optJSONArray2.length()];
                                        while (i2 < optJSONArray2.length()) {
                                            jArr2[i2] = optJSONArray2.getLong(i2);
                                            i2++;
                                        }
                                        AjxRouteCarResultPage.a(AjxRouteCarResultPage.this, optInt4, optInt5, optInt6, jSONObject.optString("timeDescription"), jSONObject.optString("roadNameString"), jSONObject.optString("nextRoadNameString"), jArr2, optInt7);
                                    }
                                }
                                return;
                            }
                            return;
                        }
                    }
                    int optInt8 = jSONObject.optInt("incidentId");
                    int optInt9 = jSONObject.optInt("focusIndex");
                    JSONArray optJSONArray3 = jSONObject.optJSONArray("routeSetId");
                    if (optJSONArray3 != null) {
                        if (optJSONArray3.length() != 0) {
                            long[] jArr3 = new long[optJSONArray3.length()];
                            while (i2 < optJSONArray3.length()) {
                                jArr3[i2] = optJSONArray3.getLong(i2);
                                i2++;
                            }
                            AjxRouteCarResultPage.a(AjxRouteCarResultPage.this, optInt, jArr3, optInt8, optInt9);
                        }
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    };
    private RelativeLayout B;
    private ModuleDriveCommonBusiness C;
    /* access modifiers changed from: private */
    public aia D;
    private ModuleHeadunit E;
    private int F = 0;
    private int G = 1;
    private boolean H = true;
    private boolean I = false;
    private boolean J;
    private alt K;
    private View L;
    private View M;
    private int N;
    private View O;
    private ContainerType[] P = null;
    private ContainerType[] Q = null;
    /* access modifiers changed from: private */
    public AlertView R;
    /* access modifiers changed from: private */
    public boolean S = false;
    private Animation T;
    private CarSceneTip U;
    /* access modifiers changed from: private */
    public ViewGroup V;
    /* access modifiers changed from: private */
    public boolean W = false;
    /* access modifiers changed from: private */
    public boolean X = false;
    /* access modifiers changed from: private */
    public byte[] Y;
    private ok Z = null;
    View a;
    private RouteCarResultTouchEventView aa = null;
    /* access modifiers changed from: private */
    public long ab = 0;
    /* access modifiers changed from: private */
    public long ac = 0;
    /* access modifiers changed from: private */
    public boolean ad = false;
    private View ae;
    private int af = -1;
    private a ag;
    /* access modifiers changed from: private */
    public pa ah;
    private boolean ai = false;
    private com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager.a aj = new com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager.a() {
        public final void a(int i) {
            if ((!(i == 3 || i == 2) || !AjxRouteCarResultPage.this.W) && AjxRouteCarResultPage.this.s != null && !AjxRouteCarResultPage.this.s.e) {
                if (AjxRouteCarResultPage.this.s.e()) {
                    AjxRouteCarResultPage.this.d.requestJsurfaceAreaSizeChange(1100, AjxRouteCarResultPage.this.s.g());
                    return;
                }
                AjxRouteCarResultPage.this.d.requestJsurfaceAreaSizeChange(SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_PARAM, AjxRouteCarResultPage.this.s.g());
            }
        }

        public final void b(int i) {
            AjxRouteCarResultPage.this.W = true;
            AjxRouteCarResultPage.this.b(true);
            switch (i) {
                case 0:
                case 1:
                case 8:
                case 9:
                    AjxRouteCarResultPage.g(AjxRouteCarResultPage.this);
                    AjxRouteCarResultPage.this.a(true);
                    return;
                case 2:
                    AjxRouteCarResultPage.a(AjxRouteCarResultPage.this, 2, AjxRouteCarResultPage.this.s.i.id);
                    return;
                case 3:
                    AjxRouteCarResultPage.a(AjxRouteCarResultPage.this, 2, AjxRouteCarResultPage.this.s.i.id);
                    AjxRouteCarResultPage.a((String) "B098", (String) "from", (String) ModulePoi.TIPS);
                    return;
                case 4:
                    AjxRouteCarResultPage.i(AjxRouteCarResultPage.this);
                    return;
                case 5:
                    px<sn> pxVar = AjxRouteCarResultPage.this.s.k;
                    if (pxVar != null) {
                        AjxRouteCarResultPage.a(AjxRouteCarResultPage.this, 1, ((sn) pxVar.f).a);
                        AjxRouteCarResultPage.a((String) "B099", (String) "from", (String) ModulePoi.TIPS);
                        return;
                    }
                    break;
                case 6:
                    AjxRouteCarResultPage.h(AjxRouteCarResultPage.this);
                    return;
                case 7:
                    DriveSpUtil.setSearchRouteInNeMode(AjxRouteCarResultPage.this.getContext(), true);
                    AjxRouteCarResultPage.j(AjxRouteCarResultPage.this);
                    return;
                case 10:
                    pk j = AjxRouteCarResultPage.this.s.j();
                    if (!(j == null || AjxRouteCarResultPage.this.g == null)) {
                        AjxRouteCarResultPage.a(AjxRouteCarResultPage.this, j.a, j.b, j.d, j.c, j.e, j.f, AjxRouteCarResultPage.this.g.a, 0);
                    }
                    return;
                case 11:
                case 12:
                case 13:
                    return;
                case 14:
                    AjxRouteCarResultPage.this.b.b();
                    return;
                case 15:
                    return;
                case 16:
                case 17:
                case 18:
                    return;
                case 20:
                case 21:
                    AjxRouteCarResultPage.a(AjxRouteCarResultPage.this, 2, AjxRouteCarResultPage.this.s.j.id);
                    return;
                case 22:
                case 23:
                    if (!rt.a()) {
                        AjxRouteCarResultPage.g(AjxRouteCarResultPage.this);
                        AjxRouteCarResultPage.this.a(true);
                        break;
                    } else {
                        AjxRouteCarResultPage.m(AjxRouteCarResultPage.this);
                        return;
                    }
            }
        }

        public final void c(int i) {
            AjxRouteCarResultPage.this.W = false;
            if (i == 4) {
                AjxRouteCarResultPage.n();
            } else if (i == 6) {
                AjxRouteCarResultPage.m();
            }
        }
    };
    private OnClickListener ak = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.route_car_result_online_icon) {
                DriveSpUtil.setSearchRouteInNeMode(AjxRouteCarResultPage.this.getContext(), true);
                AjxRouteCarResultPage.this.b.b();
                LogManager.actionLogV2(LogConstant.PAGE_ID_CAR_RESULT_MAP, "B111", LogUtil.createJSONObj(view.getTag(R.id.car_offline_to_online) == null ? "0" : "1"));
            } else if (view.getId() == R.id.route_car_result_refresh) {
                AjxRouteCarResultPage.this.a(-1);
                AjxRouteCarResultPage.o();
            } else if (view.getId() == R.id.route_car_result_dl) {
                int[] iArr = null;
                if (AjxRouteCarResultPage.this.g != null) {
                    pg a2 = AjxRouteCarResultPage.this.g.a();
                    if (!(a2 == null || a2.q == null)) {
                        iArr = a2.q.b;
                    }
                }
                IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
                if (iOfflineManager != null) {
                    iOfflineManager.enterAlongWayDownload(iArr);
                }
                if (AjxRouteCarResultPage.this.s != null) {
                    AjxRouteCarResultPage.this.s.c();
                }
                AjxRouteCarResultPage.a((String) LogConstant.MAIN_MSGBOX_AD_ENTRANCE_CLICK, (JSONObject) null);
            } else {
                if (view.getId() == R.id.route_car_result_etd_entrance) {
                    AjxRouteCarResultPage.m(AjxRouteCarResultPage.this);
                }
            }
        }
    };
    private com.amap.bundle.drive.result.view.RouteCarLongScenePanel.a al = new com.amap.bundle.drive.result.view.RouteCarLongScenePanel.a() {
        public final void a(boolean z) {
            AjxRouteCarResultPage.this.d.requestJsSuspendClickEvent(1002, z);
            AjxRouteCarResultPage.o();
        }

        public final void b(boolean z) {
            AjxRouteCarResultPage.this.d.requestJsSuspendClickEvent(1003, z);
            AjxRouteCarResultPage.o();
        }

        public final void c(boolean z) {
            AjxRouteCarResultPage.this.d.requestJsSuspendClickEvent(1005, z);
            AjxRouteCarResultPage.o();
        }

        public final void d(boolean z) {
            AjxRouteCarResultPage.o();
        }

        public final void e(boolean z) {
            AjxRouteCarResultPage.this.d.setWeatherSwitchState(z);
            AjxRouteCarResultPage.this.d.requestJsSuspendClickEvent(1008, z);
        }
    };
    private OnCalRouteFromData am = new OnCalRouteFromData() {
        public final void onCalRouteFromData() {
            if (AjxRouteCarResultPage.this.b.c != null && AjxRouteCarResultPage.this.b.c.getPoint() != null && AjxRouteCarResultPage.this.b.d != null && AjxRouteCarResultPage.this.b.d.getPoint() != null) {
                RouteWayPoint routeWayPoint = new RouteWayPoint();
                RoutePOIInfo routePOIInfo = new RoutePOIInfo();
                POI poi = AjxRouteCarResultPage.this.b.c;
                ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
                ISearchPoiData iSearchPoiData2 = (ISearchPoiData) AjxRouteCarResultPage.this.b.d.as(ISearchPoiData.class);
                if (iSearchPoiData != null && iSearchPoiData2 != null) {
                    GeoPoint point = iSearchPoiData.getPoint();
                    GeoPoint point2 = iSearchPoiData2.getPoint();
                    if (point != null && point2 != null) {
                        Coord2D coord2D = new Coord2D();
                        coord2D.lon = point.getLongitude();
                        coord2D.lat = point.getLatitude();
                        routePOIInfo.realPos = coord2D;
                        ArrayList<GeoPoint> exitList = iSearchPoiData.getExitList();
                        if (exitList != null && exitList.size() > 0) {
                            Coord2D coord2D2 = new Coord2D();
                            coord2D2.lon = exitList.get(0).getLongitude();
                            coord2D2.lat = exitList.get(0).getLatitude();
                            routePOIInfo.naviPos = coord2D2;
                        }
                        routePOIInfo.poiID = iSearchPoiData.getId();
                        routePOIInfo.typeCode = iSearchPoiData.getType();
                        routePOIInfo.name = iSearchPoiData.getName();
                        routePOIInfo.parentRel = iSearchPoiData.getChildType();
                        routePOIInfo.parentID = iSearchPoiData.getPid();
                        routePOIInfo.angel = iSearchPoiData.getTowardsAngle();
                        routePOIInfo.floorName = iSearchPoiData.getIndoorFloorNoName();
                        routeWayPoint.start.add(routePOIInfo);
                        RoutePOIInfo routePOIInfo2 = new RoutePOIInfo();
                        Coord2D coord2D3 = new Coord2D();
                        coord2D3.lon = AjxRouteCarResultPage.this.b.d.getPoint().getLongitude();
                        coord2D3.lat = AjxRouteCarResultPage.this.b.d.getPoint().getLatitude();
                        routePOIInfo2.realPos = coord2D3;
                        ArrayList<GeoPoint> entranceList = iSearchPoiData2.getEntranceList();
                        if (entranceList != null && entranceList.size() > 0) {
                            Coord2D coord2D4 = new Coord2D();
                            coord2D4.lon = entranceList.get(0).getLongitude();
                            coord2D4.lat = entranceList.get(0).getLatitude();
                            routePOIInfo2.naviPos = coord2D4;
                        }
                        routePOIInfo2.poiID = iSearchPoiData2.getId();
                        routePOIInfo2.typeCode = iSearchPoiData2.getType();
                        routePOIInfo2.name = iSearchPoiData2.getName();
                        routePOIInfo2.parentRel = iSearchPoiData2.getChildType();
                        routePOIInfo2.parentID = iSearchPoiData2.getPid();
                        routePOIInfo2.angel = iSearchPoiData2.getTowardsAngle();
                        routePOIInfo2.floorName = iSearchPoiData2.getIndoorFloorNoName();
                        routeWayPoint.end.add(routePOIInfo2);
                        NaviManager.calcRouteFromDataNew(2, routeWayPoint, AjxRouteCarResultPage.this.Y);
                    }
                }
            }
        }
    };
    private JsCommandCallback an = new JsCommandCallback() {
        public final boolean callback(int i, String... strArr) {
            switch (i) {
                case 1:
                    cde suspendManager = AjxRouteCarResultPage.this.getSuspendManager();
                    if (suspendManager != null) {
                        cdz d = suspendManager.d();
                        if (d != null) {
                            d.f();
                            d.e();
                        }
                    }
                    return true;
                case 6:
                    AjxRouteCarResultPage ajxRouteCarResultPage = AjxRouteCarResultPage.this;
                    if (ajxRouteCarResultPage.q != null) {
                        ajxRouteCarResultPage.c();
                    }
                    if (ajxRouteCarResultPage.s == null || !ajxRouteCarResultPage.s.e()) {
                        ajxRouteCarResultPage.finish();
                    } else {
                        ajxRouteCarResultPage.b(true);
                    }
                    return true;
                case 7:
                    AjxRouteCarResultPage ajxRouteCarResultPage2 = AjxRouteCarResultPage.this;
                    RouteType routeType = RouteType.TAXI;
                    if (!ajxRouteCarResultPage2.e) {
                        ajxRouteCarResultPage2.m.a(routeType);
                    } else {
                        ajxRouteCarResultPage2.finish();
                        PageBundle pageBundle = new PageBundle("amap.extra.route.route", "com.autonavi.minimap");
                        ow owVar = ajxRouteCarResultPage2.b;
                        pageBundle.putObject("bundle_key_poi_start", owVar.i != null ? owVar.i.f() : null);
                        pageBundle.putObject("bundle_key_poi_end", ajxRouteCarResultPage2.b.d());
                        pageBundle.putBoolean("bundle_key_auto_route", true);
                        pageBundle.putObject("bundle_key_route_type", routeType);
                        bax bax = (bax) defpackage.esb.a.a.a(bax.class);
                        if (bax != null) {
                            bax.a(pageBundle);
                        }
                    }
                    return true;
                case 25:
                    if (AjxRouteCarResultPage.this.s != null) {
                        AjxRouteCarResultPage.this.s.a(Float.valueOf(1.0f));
                    }
                    if (AjxRouteCarResultPage.this.V != null) {
                        AjxRouteCarResultPage.this.V.setAlpha(1.0f);
                    }
                    if (AjxRouteCarResultPage.this.b.a() == 102) {
                        AjxRouteCarResultPage.a(AjxRouteCarResultPage.this, 1.0f);
                    }
                    if (AjxRouteCarResultPage.this.b.a() != 102) {
                        AjxRouteCarResultPage.this.a(0.0f);
                        break;
                    }
                    break;
                case 26:
                    if (strArr != null && strArr.length > 0) {
                        AjxRouteCarResultPage.f(AjxRouteCarResultPage.this, strArr[0]);
                        break;
                    }
            }
            if (!AjxRouteCarResultPage.this.isResumed()) {
                return false;
            }
            switch (i) {
                case 1003:
                    DriveEyrieRouteSharingUtil.a(AjxRouteCarResultPage.this.g);
                    break;
                case 1004:
                    AjxRouteCarResultPage.v(AjxRouteCarResultPage.this);
                    break;
            }
            return false;
        }
    };
    private OnObtainFocusCallBack ao = new OnObtainFocusCallBack() {
        public final void onObtainFocus() {
            AjxRouteCarResultPage.i();
        }
    };
    private bfa ap = new bfa() {
        public final void a(boolean z) {
            if (z) {
                AjxRouteCarResultPage.w(AjxRouteCarResultPage.this);
            }
            if (AjxRouteCarResultPage.this.d != null) {
                AjxRouteCarResultPage.this.d.dispatchVUILayerEvent(z);
            }
            if (AjxRouteCarResultPage.this.ah != null && z) {
                AjxRouteCarResultPage.this.ah.a();
            }
        }
    };
    private defpackage.ok.a aq = new defpackage.ok.a() {
        public final void a(float f) {
            if (f <= 5.56f) {
                AjxRouteCarResultPage.this.ac = 0;
                return;
            }
            if (AjxRouteCarResultPage.this.ac == 0 && f > 5.56f) {
                AjxRouteCarResultPage.this.ac = SystemClock.elapsedRealtime();
            }
            if (TextUtils.equals(String.valueOf(Ajx.getInstance().getMemoryStorage("PlanResult").getItem("SearchAlongShow")), "1")) {
                AjxRouteCarResultPage.this.ac = 0;
                return;
            }
            if (SystemClock.elapsedRealtime() - AjxRouteCarResultPage.this.ac >= 10000 && System.currentTimeMillis() - AjxRouteCarResultPage.this.ab >= 60000 && !AjxRouteCarResultPage.D(AjxRouteCarResultPage.this) && AjxRouteCarResultPage.this.isAlive() && !AjxRouteCarResultPage.this.i && !AjxRouteCarResultPage.this.ad && re.a((String) "radar_auto_enter", true) && AjxRouteCarResultPage.this.e() && AjxRouteCarResultPage.this.d != null) {
                AjxRouteCarResultPage.this.d.startDriveRadarPage();
                AjxRouteCarResultPage.this.ad = true;
            }
        }
    };
    private aij ar = new aij() {
        public final void a(int i, int i2) {
            if (AjxRouteCarResultPage.this.D != null) {
                if (AjxRouteCarResultPage.this.D.a(AjxRouteCarResultPage.I(AjxRouteCarResultPage.this), (String) "switchRoute", i, new Pair<>("index", Integer.valueOf(i2)))) {
                    return;
                }
            }
            sa.a(i, (int) SDKFactory.getCoreType);
        }

        public final void a(int i) {
            if (AjxRouteCarResultPage.this.D == null || !AjxRouteCarResultPage.this.D.a(AjxRouteCarResultPage.I(AjxRouteCarResultPage.this), (String) "startNavi", i)) {
                sa.a(i, (int) SDKFactory.getCoreType);
            }
        }

        public final void b(int i) {
            if (AjxRouteCarResultPage.this.D == null || !AjxRouteCarResultPage.this.D.a(AjxRouteCarResultPage.I(AjxRouteCarResultPage.this), (String) "enterRadarMode", i)) {
                sa.a(i, (int) SDKFactory.getCoreType);
            }
        }

        public final void c(int i) {
            if (AjxRouteCarResultPage.this.D != null) {
                AjxRouteCarResultPage.this.a(i);
            } else {
                sa.a(i, (int) SDKFactory.getCoreType);
            }
        }

        public final void a(int i, String str) {
            if (AjxRouteCarResultPage.this.D != null) {
                AjxRouteCarResultPage.a(AjxRouteCarResultPage.this, i, str);
            } else {
                sa.a(i, (int) SDKFactory.getCoreType);
            }
        }
    };
    public ow b;
    public ModuleVUI c;
    public ModuleRouteDriveResult d;
    public boolean e = false;
    public boolean f;
    public ph g;
    public String h;
    public boolean i = false;
    public boolean j;
    SyncPopupWindow k;
    pc l = null;
    public qb m;
    public String n;
    public boolean o;
    public String p;
    ViewGroup q;
    /* access modifiers changed from: 0000 */
    public ViewGroup r;
    public AjxTipsManager s;
    protected Handler t = new Handler();
    public Handler u = new b(0);
    public int v = -1;
    OnClickListener w = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.tips_entrance) {
                AjxRouteCarResultPage ajxRouteCarResultPage = AjxRouteCarResultPage.this;
                if (ajxRouteCarResultPage.s != null) {
                    AjxRouteCarResultPage.a((String) "B088", (String) "type", String.valueOf(ajxRouteCarResultPage.s.f()));
                    Animation loadAnimation = AnimationUtils.loadAnimation(ajxRouteCarResultPage.getContext(), R.anim.tips_showing);
                    loadAnimation.setFillAfter(true);
                    ajxRouteCarResultPage.r.setVisibility(0);
                    NoDBClickUtil.a((View) ajxRouteCarResultPage.r, ajxRouteCarResultPage.w);
                    loadAnimation.setAnimationListener(new AnimationListener() {
                        public final void onAnimationEnd(Animation animation) {
                        }

                        public final void onAnimationRepeat(Animation animation) {
                        }

                        public final void onAnimationStart(Animation animation) {
                            AjxRouteCarResultPage.this.s.h();
                        }
                    });
                    ajxRouteCarResultPage.r.startAnimation(loadAnimation);
                }
            } else if (view.getId() == R.id.tips_bg_mask) {
                AjxRouteCarResultPage.this.b(true);
            } else {
                if (view.getId() == R.id.title_back_img) {
                    qd.b(AjxRouteCarResultPage.this.m.j());
                    qd.a(AjxRouteCarResultPage.this.getContentView(), (AnimatorListener) new AnimatorListener() {
                        public final void onAnimationCancel(Animator animator) {
                        }

                        public final void onAnimationRepeat(Animator animator) {
                        }

                        public final void onAnimationStart(Animator animator) {
                        }

                        public final void onAnimationEnd(Animator animator) {
                            AjxRouteCarResultPage.this.finish();
                        }
                    });
                }
            }
        }
    };
    mr x = new mr() {
    };
    mq y = new mq() {
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(java.lang.String r3, java.lang.String r4) {
            /*
                r2 = this;
                com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
                r0.<init>()
                int r1 = r3.hashCode()
                switch(r1) {
                    case -1203606180: goto L_0x003f;
                    case -24483950: goto L_0x0035;
                    case 138748996: goto L_0x002b;
                    case 272721754: goto L_0x0021;
                    case 553280922: goto L_0x0017;
                    case 953959764: goto L_0x000d;
                    default: goto L_0x000c;
                }
            L_0x000c:
                goto L_0x0049
            L_0x000d:
                java.lang.String r1 = "carPreview"
                boolean r3 = r3.equals(r1)
                if (r3 == 0) goto L_0x0049
                r3 = 0
                goto L_0x004a
            L_0x0017:
                java.lang.String r1 = "carNavi"
                boolean r3 = r3.equals(r1)
                if (r3 == 0) goto L_0x0049
                r3 = 1
                goto L_0x004a
            L_0x0021:
                java.lang.String r1 = "backDefault"
                boolean r3 = r3.equals(r1)
                if (r3 == 0) goto L_0x0049
                r3 = 4
                goto L_0x004a
            L_0x002b:
                java.lang.String r1 = "carMockNavi"
                boolean r3 = r3.equals(r1)
                if (r3 == 0) goto L_0x0049
                r3 = 3
                goto L_0x004a
            L_0x0035:
                java.lang.String r1 = "carRadar"
                boolean r3 = r3.equals(r1)
                if (r3 == 0) goto L_0x0049
                r3 = 2
                goto L_0x004a
            L_0x003f:
                java.lang.String r1 = "errorReport"
                boolean r3 = r3.equals(r1)
                if (r3 == 0) goto L_0x0049
                r3 = 5
                goto L_0x004a
            L_0x0049:
                r3 = -1
            L_0x004a:
                switch(r3) {
                    case 0: goto L_0x007f;
                    case 1: goto L_0x006f;
                    case 2: goto L_0x0069;
                    case 3: goto L_0x0063;
                    case 4: goto L_0x0054;
                    case 5: goto L_0x004e;
                    default: goto L_0x004d;
                }
            L_0x004d:
                goto L_0x0085
            L_0x004e:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage r3 = com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.this
                com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.e(r3, r4)
                goto L_0x0085
            L_0x0054:
                bid r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
                if (r3 == 0) goto L_0x0085
                r3.finish()
                java.lang.String r4 = "amap.basemap.action.default_page"
                r3.startPage(r4, r0)
                return
            L_0x0063:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage r3 = com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.this
                com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.d(r3, r4)
                return
            L_0x0069:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage r3 = com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.this
                com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.c(r3, r4)
                return
            L_0x006f:
                java.lang.String r3 = "\n"
                defpackage.tj.a(r3)
                java.lang.String r3 = "car-startNavi-click_3.1"
                defpackage.tj.a(r3)
                com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage r3 = com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.this
                com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.b(r3, r4)
                return
            L_0x007f:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage r3 = com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.this
                com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.a(r3, r4)
                return
            L_0x0085:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.AnonymousClass15.a(java.lang.String, java.lang.String):void");
        }
    };
    HeadunitBtnEventCallback z = new HeadunitBtnEventCallback() {
        public final void action(String str) {
            if (TextUtils.equals(str, "click")) {
                AjxRouteCarResultPage.this.c(false);
            }
        }
    };

    interface a {
        void a();

        void a(JSONObject jSONObject);
    }

    static class b extends Handler {
        public final void handleMessage(Message message) {
        }

        private b() {
        }

        /* synthetic */ b(byte b) {
            this();
        }
    }

    static /* synthetic */ void o() {
    }

    public void finishSelf() {
    }

    public String getAjx3Url() {
        return ModuleRouteDriveResult.URL_CAR_ROUTE;
    }

    public long getScene() {
        return 4096;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 4096;
    }

    public boolean handleSetContentView() {
        return true;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        return amapAjxView;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent != null) {
            motionEvent.getAction();
        }
        return false;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.ajx_route_car_result_map_page);
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().a(getClass(), AgroupScenes.RouteCarResult, getArguments(), false);
        }
        IVoiceDriveDispatcher iVoiceDriveDispatcher = (IVoiceDriveDispatcher) ank.a(IVoiceDriveDispatcher.class);
        if (iVoiceDriveDispatcher != null) {
            iVoiceDriveDispatcher.setRouteApiControlListener(this.ar);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: q */
    public ow createPresenter() {
        this.b = new ow(this);
        return this.b;
    }

    public void pageCreated() {
        super.pageCreated();
        this.m = new qb(this);
        this.m.a();
        if (getMapView() != null) {
            this.F = getMapView().j(false);
            this.G = getMapView().k(false);
        }
        this.O = findViewById(R.id.mapBottomInteractiveView);
        this.V = this.l.a;
        NoDBClickUtil.a((View) this.V, this.w);
        a();
        this.ah = new pa(this, this.m);
    }

    public final void a() {
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.e = arguments.getBoolean("key_favorites", false);
            Ajx.getInstance().getMemoryStorage("PlanResult").setItem("isFromFavorite", Boolean.valueOf(this.e));
            if (this.e && arguments.containsKey("original_route")) {
                cor cor = (cor) arguments.getObject("original_route");
                if (cor != null) {
                    this.h = cor.f();
                }
            }
            cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
            if (cuh != null) {
                cuh.b().a(!this.e);
            }
            if (arguments.containsKey("voice_process")) {
                this.m.q();
            }
            this.J = arguments.containsKey("voice_process");
            this.f = arguments.getInt("key_source", 0) == 102;
            if (this.f) {
                this.Y = arguments.getByteArray("original_route");
                this.b.c = (POI) arguments.getObject("bundle_key_poi_start");
                this.b.d = (POI) arguments.getObject("bundle_key_poi_end");
            }
            this.I = arguments.getBoolean("key_subresult", false);
            if (arguments.containsKey("bundle_key_method_flag") && arguments.getInt("bundle_key_method_flag", 0) == 1 && arguments.containsKey("bundle_key_method")) {
                String string = arguments.getString("bundle_key_method");
                if (!TextUtils.isEmpty(string)) {
                    DriveUtil.putLastRoutingChoice(string);
                }
            }
            r();
        }
    }

    private void r() {
        if (this.e) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.route_car_result_map_title, null);
            View findViewById = inflate.findViewById(R.id.route_car_result_map_title_favorite);
            findViewById.setVisibility(0);
            this.ae = findViewById.findViewById(R.id.title_back_img);
            NoDBClickUtil.a(this.ae, this.w);
            this.m.k();
            this.m.a(inflate);
            this.m.q();
            if (euk.a()) {
                inflate.setPadding(inflate.getPaddingLeft(), inflate.getPaddingTop() + euk.a(getContext()), inflate.getPaddingRight(), inflate.getPaddingBottom());
            }
        }
        if (this.J) {
            this.t.post(new Runnable() {
                public final void run() {
                    ViewGroup s = AjxRouteCarResultPage.this.m.s();
                    if (s != null) {
                        s.bringToFront();
                    }
                }
            });
        }
    }

    public final void a(boolean z2) {
        if (z2) {
            if (this.s != null && this.s.e()) {
                b(false);
            }
            if (this.s != null) {
                this.s.c();
            }
            a(true, (AnimationListener) new AnimationListener() {
                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                }

                public final void onAnimationEnd(Animation animation) {
                    AjxRouteCarResultPage.b(AjxRouteCarResultPage.this);
                }
            });
            return;
        }
        a(false, (AnimationListener) new AnimationListener() {
            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteCarResultPage.this.isAlive()) {
                    AjxRouteCarResultPage.this.getContentView().findViewById(R.id.mapTopInteractiveView).setVisibility(0);
                }
            }
        });
    }

    public final void a(boolean z2, AnimationListener animationListener) {
        if (z2) {
            getContentView().findViewById(R.id.mapTopInteractiveView).setVisibility(4);
            qd.b(getContext(), this.O, animationListener);
            this.m.g();
            return;
        }
        qd.a(getContext(), this.O, animationListener);
        this.m.f();
    }

    public final void a(int i2, int i3) {
        if (this.d != null) {
            this.d.requestJsurfaceAreaSizeChange(i2, i3);
        }
    }

    public void resume() {
        super.resume();
        if (k()) {
            j();
        }
        s();
        if (!this.f) {
            awo awo = (awo) defpackage.esb.a.a.a(awo.class);
            if (awo != null) {
                awo.b(9001);
            }
            alu alu = new alu();
            alu.a = 9001;
            this.K = getMapView().a(alu);
            alv alv = new alv();
            alv.a = 9001;
            alv.b = 20;
            alv.c = 16;
            alv.d = 1;
            getMapView().a(alv);
        }
        this.j = false;
        if (this.L != null) {
            this.L.setVisibility(0);
        }
        LocationInstrument.getInstance().subscribe(getContext(), LOCATION_SCENE.TYPE_DRIVE_PATH_PLAN);
        bim.aa().a((biv) new biv() {
            public final void saveSucess() {
                boolean z;
                AjxRouteCarResultPage ajxRouteCarResultPage = AjxRouteCarResultPage.this;
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService == null) {
                    z = false;
                } else {
                    z = iAccountService.a();
                }
                if (!z) {
                    if (ajxRouteCarResultPage.k == null) {
                        ajxRouteCarResultPage.a = View.inflate(ajxRouteCarResultPage.getContext(), R.layout.v4_fromto_car_result_detail_dlg, null);
                        IRouteUI b = ajxRouteCarResultPage.b.a.b();
                        if (b != null) {
                            b.a(ajxRouteCarResultPage.a);
                        }
                        ajxRouteCarResultPage.k = new SyncPopupWindow(ajxRouteCarResultPage.a);
                    }
                    ajxRouteCarResultPage.k.show();
                }
            }
        });
        if (this.H) {
            this.H = false;
            IRouteUI b2 = this.b.a.b();
            if (b2 != null) {
                b2.a(this.L);
                this.mAjxView.setLayoutParams(new LayoutParams(-1, -1));
                if (this.J) {
                    LayoutParams layoutParams = new LayoutParams(-1, (int) (Resources.getSystem().getDisplayMetrics().density * 142.0f));
                    layoutParams.addRule(12);
                    this.B = new RelativeLayout(getContext());
                    this.B.setLayoutParams(layoutParams);
                    this.B.setOnTouchListener(this);
                    b2.a((View) this.B);
                }
            }
        } else if (this.s != null) {
            this.s.l();
        }
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.resumeActivityState();
        }
        if (this.b != null && this.b.a != null && this.b.a.b() != null && this.b.a.b().o()) {
            this.ai = true;
        }
    }

    private void s() {
        if (this.s == null) {
            this.s = new AjxTipsManager(getContext(), 0, this.m.b(), this.e);
            this.r = this.s.d;
            if (this.d != null) {
                this.d.bindTipsManager(this.s);
            }
        }
    }

    public void pause() {
        super.pause();
        if (!(this.b == null || this.b.a() == 102)) {
            a(0.0f);
        }
        AMapLog.d("AjxRouteCarResultPage", "stopRouteBoardMonitor");
        if (this.Z != null) {
            this.Z.a();
        }
        if (this.s != null) {
            this.s.b();
        }
        if (this.s != null && this.s.f() > 0) {
            this.s.n();
        }
        if (!this.f) {
            if (this.K != null) {
                alv alv = new alv();
                alv.a = 9001;
                alv.b = this.K.a;
                alv.c = this.K.b;
                alv.d = 1;
                getMapView().a(alv);
            }
            awo awo = (awo) defpackage.esb.a.a.a(awo.class);
            if (awo != null) {
                awo.c(9001);
            }
        }
        this.n = DriveUtil.getLastRoutingChoice();
        this.o = DriveUtil.isAvoidLimitedPath();
        this.p = DriveUtil.getCarPlateNumber();
        if (this.k != null) {
            this.k.hide();
        }
        bim.aa().a((biv) null);
        LocationInstrument.getInstance().unsubscribe(getContext());
    }

    public void stop() {
        super.stop();
        if (this.j) {
            this.L.setVisibility(4);
            if (this.m != null) {
                POI o2 = this.m.o();
                if (o2 != null) {
                    this.m.b(rt.a(o2));
                }
            }
        }
    }

    public boolean backPressed() {
        if (this.ah != null && this.ah.b) {
            this.ah.a();
            return true;
        } else if (this.R == null || !isViewLayerShowing(this.R)) {
            if (this.s != null && this.s.e()) {
                b(true);
                return true;
            } else if (this.mAjxView == null || !this.mAjxView.backPressed()) {
                return false;
            } else {
                return true;
            }
        } else if (this.S) {
            return true;
        } else {
            dismissViewLayer(this.R);
            this.R = null;
            return true;
        }
    }

    public final void b() {
        if (this.s != null) {
            this.s.k();
            this.s.f = null;
        }
    }

    public final void c() {
        if (this.q != null) {
            this.q.setVisibility(8);
            if (this.s != null) {
                this.s.d(0);
            }
            a((int) SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_LENGTH, 0);
        }
    }

    public void destroy() {
        if (this.mAjxView != null) {
            this.mAjxView.onAjxContextCreated(null);
            this.b.a.b().b((View) this.mAjxView);
        }
        super.destroy();
        if (getMapView() != null) {
            getMapView().a(this.F, getMapView().l(false), this.G);
        }
        IVoiceDriveDispatcher iVoiceDriveDispatcher = (IVoiceDriveDispatcher) ank.a(IVoiceDriveDispatcher.class);
        if (iVoiceDriveDispatcher != null) {
            iVoiceDriveDispatcher.setRouteApiControlListener(null);
        }
        VUIStateManager.f();
        if (VUIStateManager.v()) {
            if (this.af == -1) {
                this.af = this.b.h;
            }
            d.a.a(this.af, (int) SDKFactory.getCoreType, (String) "");
        }
        if (this.B != null) {
            this.b.a.b().b((View) this.B);
        }
        if (this.a != null) {
            this.b.a.b().b(this.a);
        }
        if (this.d != null) {
            this.d.setOnTripPoiChangeListener(null);
            this.d.setOnRouteStateChangeListener(null);
            this.d.setOnObtainFocusCallBack(null);
            this.d.addRouteEventActionInterface(null);
            this.d.setJsCommandCallback(null);
            this.d.setOnCalRouteFromData(null);
            this.d.removeSwitchSceneListener(this.y);
            this.d.removeRouteEventInterface(this.A);
        }
        if (this.C != null) {
            this.C.setJsCommandCallback(null);
        }
        if (this.c != null) {
            this.c.setMitVuiDialogEventListener(null);
            ModuleVUI.mMitVuiDialogEventCallback = null;
        }
        if (this.l != null) {
            this.l.c();
            this.l = null;
        }
        if (this.V != null) {
            NoDBClickUtil.a((View) this.V, (OnClickListener) null);
        }
        if (this.ae != null) {
            NoDBClickUtil.a(this.ae, (OnClickListener) null);
        }
        if (this.Z != null) {
            this.Z.a();
            this.Z.a = null;
            this.Z = null;
        }
        if (this.s != null) {
            NoDBClickUtil.a((View) this.r, (OnClickListener) null);
            this.r.clearAnimation();
            b();
            t();
        }
    }

    public void onAjxViewCreated(AmapAjxView amapAjxView) {
        amapAjxView.setAttributeListener(new AttributeListener() {
            public final boolean handleAttr(String str, Object obj) {
                if (AjxRouteCarResultPage.this.j) {
                    return false;
                }
                if ("CAR_FADE_FROM_BOTTOM".equalsIgnoreCase(str)) {
                    Float f = (Float) obj;
                    Stub.getMapWidgetManager().setContainerAlpha(f.floatValue());
                    if (AjxRouteCarResultPage.this.s != null) {
                        AjxRouteCarResultPage.this.s.a(f);
                    }
                    if (AjxRouteCarResultPage.this.V != null) {
                        AjxRouteCarResultPage.this.V.setAlpha(f.floatValue());
                    }
                    if (AjxRouteCarResultPage.this.b.a() == 102) {
                        AjxRouteCarResultPage.a(AjxRouteCarResultPage.this, f.floatValue());
                    }
                    return true;
                } else if (!"CAR_HEIGHT_TO_TOP".equalsIgnoreCase(str)) {
                    return false;
                } else {
                    if (AjxRouteCarResultPage.this.b.a() != 102) {
                        AjxRouteCarResultPage.this.a(((Float) obj).floatValue());
                    }
                    return true;
                }
            }
        });
    }

    public void loadJs() {
        JsRunInfo jsRunInfo = new JsRunInfo(ModuleRouteDriveResult.URL_CAR_ROUTE, this.b.c());
        jsRunInfo.setTag("CAR_MAP_RESULT");
        HashMap hashMap = new HashMap();
        hashMap.put(ModuleRouteDriveResult.AJX_JS_INFO_RUNTIME_ROUTE_TYPE, RouteType.CAR);
        jsRunInfo.setRunParams(hashMap);
        this.mAjxView.loadJs(jsRunInfo);
    }

    /* access modifiers changed from: private */
    public void a(float f2) {
        if (!(this.b.a.b() == null || this.b.a.b() == null)) {
            this.M = this.b.a.b().c();
        }
        if (this.M != null) {
            if (this.N == 0) {
                this.N = this.M.getHeight();
            }
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.M.getLayoutParams();
            double d2 = (double) f2;
            layoutParams.topMargin = -((int) ((d2 - 0.5d) * 3.3333332538604736d * ((double) this.N)));
            if (d2 < 0.5d) {
                layoutParams.topMargin = 0;
            } else if (d2 > 0.8d) {
                layoutParams.topMargin = -this.N;
            }
            layoutParams.bottomMargin = -layoutParams.topMargin;
            this.M.requestLayout();
        }
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        if (this.c == null) {
            this.c = (ModuleVUI) this.mAjxView.getJsModule(ModuleVUI.MODULE_NAME);
        }
        this.d = (ModuleRouteDriveResult) this.mAjxView.getJsModule(ModuleRouteDriveResult.MODULE_NAME);
        this.C = (ModuleDriveCommonBusiness) this.mAjxView.getJsModule(ModuleDriveCommonBusiness.MODULE_NAME);
        if (this.d != null) {
            this.d.setOnTripPoiChangeListener(this);
            this.d.setOnRouteStateChangeListener(this);
            this.d.setOnObtainFocusCallBack(this.ao);
            this.d.addRouteEventActionInterface(this.A);
            this.d.setJsCommandCallback(this.an);
            this.d.setOnCalRouteFromData(this.am);
            this.d.addSwitchSceneListener(this.y);
            this.d.bindTipsManager(this.s);
        }
        if (this.C != null) {
            this.C.setJsCommandCallback(this.an);
        }
        this.E = (ModuleHeadunit) this.mAjxView.getJsModule(ModuleHeadunit.MODULE_NAME);
        if (this.E != null) {
            this.E.setHeadunitBtnEventCallback(this.z);
        }
        if (this.c != null) {
            this.c.setMitVuiDialogEventListener(this.ap);
        }
        this.D = (aia) defpackage.esb.a.a.a(aia.class);
    }

    public final void a(String str, String str2) {
        if (this.d != null) {
            StringBuilder sb = new StringBuilder("requestRoute");
            sb.append(Log.getStackTraceString(new Throwable()));
            tq.b("NaviMonitor", "AjxRouteCarResultPage", sb.toString());
            i();
            if (!TextUtils.isEmpty(str2)) {
                this.d.updateFromPage(str2);
            }
            this.d.requestCarRoute(str);
            if (!(this.r == null || this.r.getAnimation() == null || this.r.getAnimation().hasEnded())) {
                this.r.getAnimation().cancel();
            }
            b();
            c();
        }
    }

    public void onMidPOIChanged(List<POI> list) {
        if (this.b != null) {
            this.b.a(list);
        }
        b();
        c();
    }

    public void onEndPOIChanged(POI poi, int i2) {
        if (this.b != null) {
            poi.getPoiExtra().put("key_end_poi_source_type", Integer.valueOf(i2));
            this.b.a(poi);
        }
        b();
        c();
    }

    public POI getStartPOI() {
        if (this.b != null) {
            return this.b.a.n();
        }
        return null;
    }

    public POI getEndPOI() {
        if (this.b != null) {
            return this.b.a.o();
        }
        return null;
    }

    public List<POI> getMidPOIs() {
        if (this.b != null) {
            return this.b.a.p();
        }
        return null;
    }

    public te getRegoPOI() {
        if (this.g != null) {
            return this.g.j;
        }
        return null;
    }

    public View getMapSuspendView() {
        this.l = new pc(this, getContext());
        this.l.b = this.ak;
        this.l.b();
        return this.l.getSuspendView();
    }

    /* access modifiers changed from: private */
    public void a(int i2) {
        if (i2 < 0) {
            if (!h()) {
                if (!this.u.hasMessages(1008)) {
                    DriveUtil.refreshTraffic(getMapView());
                    this.b.b();
                    d();
                    a((String) LogConstant.MAIN_MSGBOX_MAIN_MAP_ENTRANCE, (String) "type", (String) "valid");
                    return;
                }
                ToastHelper.showLongToast(getResources().getString(R.string.route_car_toast_refresh_route));
                a((String) LogConstant.MAIN_MSGBOX_MAIN_MAP_ENTRANCE, (String) "type", (String) "invalid");
            }
        } else if (h()) {
            sa.a(i2, (int) SDKFactory.getCoreType);
        } else if (!this.u.hasMessages(1008) || this.v == 1) {
            DriveUtil.refreshTraffic(getMapView());
            this.b.a(sb.b("refreshRouteInCarRoutePage", i2, null));
            d();
        } else {
            ToastHelper.showLongToast(getResources().getString(R.string.route_car_toast_refresh_route));
            sa.a(i2, (int) UCMPackageInfo.hadInstallUCMobile);
        }
    }

    public final void d() {
        if (this.u != null) {
            this.u.removeMessages(1008);
            this.u.sendEmptyMessageDelayed(1008, 10000);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(final boolean z2) {
        if (this.s != null) {
            if (this.T == null) {
                this.T = AnimationUtils.loadAnimation(getContext(), R.anim.tips_hiding);
            } else if (!this.T.hasEnded()) {
                return;
            }
            this.T.setFillAfter(true);
            this.T.setAnimationListener(new AnimationListener() {
                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                    AjxRouteCarResultPage.this.s.i();
                }

                public final void onAnimationEnd(Animation animation) {
                    if (!(!AjxRouteCarResultPage.this.isAlive() || AjxRouteCarResultPage.this.r == null || AjxRouteCarResultPage.this.r.getAnimation() == null)) {
                        AjxRouteCarResultPage.this.r.clearAnimation();
                        AjxRouteCarResultPage.this.t();
                        if (AjxRouteCarResultPage.this.s != null) {
                            if (z2) {
                                AjxRouteCarResultPage.this.s.m();
                                return;
                            }
                            AjxRouteCarResultPage.this.s.n();
                        }
                    }
                }
            });
            this.r.startAnimation(this.T);
        }
    }

    /* access modifiers changed from: private */
    public void t() {
        this.r.setVisibility(8);
    }

    /* access modifiers changed from: private */
    public void c(final boolean z2) {
        if (this.E != null) {
            String routeInfo = this.E.getRouteInfo();
            ku a2 = ku.a();
            StringBuilder sb = new StringBuilder("路线规划结果页发送路线到车机   automatic:");
            sb.append(z2);
            sb.append("  routeInfo:");
            sb.append(routeInfo);
            a2.c(ModuleHeadunit.MODULE_NAME, sb.toString());
            vp vpVar = (vp) defpackage.esb.a.a.a(vp.class);
            if (vpVar == null || TextUtils.isEmpty(routeInfo)) {
                if (!z2) {
                    ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_failed));
                }
            } else if (vpVar.b()) {
                if (!z2) {
                    routeInfo = routeInfo.replaceFirst("\"headunit_send_mode\":0,", "\"headunit_send_mode\":1,");
                }
                vpVar.a(routeInfo, (vo) new vo() {
                    public final void onSuccess(int i) {
                        switch (i) {
                            case 0:
                                if (!z2) {
                                    ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_successed_by_linksdk));
                                    return;
                                }
                                break;
                            case 1:
                                if (!z2) {
                                    ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_successed_by_aos));
                                    break;
                                }
                                break;
                        }
                    }

                    public final void onError(String str, String str2) {
                        if (!z2) {
                            ToastHelper.showToast(AMapAppGlobal.getApplication().getString(R.string.send_headunit_failed));
                        }
                        ku a2 = ku.a();
                        StringBuilder sb = new StringBuilder("路线规划结果页发送路线到车机发送失败   code:");
                        sb.append(str);
                        sb.append("  msg:");
                        sb.append(str2);
                        a2.c(ModuleHeadunit.MODULE_NAME, sb.toString());
                    }
                });
            }
        }
    }

    public final boolean e() {
        return getEndPOI() == null || !"我的位置".equals(getEndPOI().getName()) || !(getMidPOIs() == null || getMidPOIs().size() == 0);
    }

    @NonNull
    public final Handler f() {
        return this.t;
    }

    public void onRouteStateChanged(int i2, Object... objArr) {
        this.v = i2;
        if (i2 == 0) {
            this.b.f = DriveUtil.getLastRoutingChoice();
            this.g = objArr[0];
            this.g.g = getStartPOI();
            this.g.h = getEndPOI();
            this.g.i = getMidPOIs();
            IRouteUI b2 = this.b.a.b();
            if (this.g != null && (this.g.b() || (b2 != null && b2.o()))) {
                ow owVar = this.b;
                if (!owVar.b.e && !owVar.g) {
                    ahm.c(new Runnable() {
                        public final void run() {
                            if (ow.this.c != null && ow.this.d != null) {
                                chk.a(ow.this.c, ow.this.e, ow.this.d, RouteType.CAR);
                            }
                        }
                    });
                }
            }
            d();
            s();
            b();
            if (this.s != null) {
                this.s.a();
                if (!this.f && !this.e) {
                    this.s.f = this.aj;
                    this.s.a(this.g);
                    if (this.s.f() <= 0) {
                        pa.a(8);
                    } else {
                        pa.a(4);
                    }
                    this.s.c = this.V;
                    if (isStarted()) {
                        this.s.a(11000);
                    } else {
                        this.s.m();
                    }
                }
                if (!this.e && !this.f && !this.I && this.m.b() != null) {
                    this.s.d();
                }
            }
            if (!g() && !u() && !this.e && NetworkReachability.b()) {
                POI d2 = this.b.d();
                if (DriveUtil.isNeedSearchCarScene(d2, this.ai)) {
                    AnonymousClass11 r7 = new op() {
                        public final void a(oo ooVar) {
                            AjxRouteCarResultPage.a(AjxRouteCarResultPage.this, ooVar);
                        }
                    };
                    NaviinfoRequest naviinfoRequest = new NaviinfoRequest();
                    naviinfoRequest.b = d2.getId();
                    naviinfoRequest.c = DriveUtil.getSearchCarSceneParam(d2);
                    PoiRequestHolder.getInstance().sendNaviinfo(naviinfoRequest, new CarSceneSearchAosCallback(d2, r7));
                }
            }
            this.ai = false;
            StringBuilder sb = new StringBuilder("searchScene longScene=");
            sb.append(g());
            sb.append(" isScene=");
            sb.append(u());
            sb.append(" fromFavorite=");
            sb.append(this.e);
            sb.append(" isConnected=");
            sb.append(NetworkReachability.b());
            tq.b("NaviMonitor", "AjxRouteCarResultPage", sb.toString());
            if (!(!this.X || this.g == null || this.g.h == null || this.g.h.getPoiExtra() == null || !this.g.h.getPoiExtra().containsKey("sub_poi_name"))) {
                this.X = false;
                String name = this.g.h.getName();
                Serializable serializable = this.g.h.getPoiExtra().get("main_poi");
                if (serializable != null && (serializable instanceof POI)) {
                    name = ((POI) serializable).getName();
                }
                StringBuilder sb2 = new StringBuilder("目的地已为您设置为\n");
                sb2.append(name);
                sb2.append(Token.SEPARATOR);
                sb2.append(this.g.h.getPoiExtra().get("sub_poi_name"));
                ToastHelper.showToast(sb2.toString());
            }
            if (h()) {
                if (v()) {
                    ToastHelper.showToast(getString(R.string.offline_message_tbt_success));
                } else {
                    ToastHelper.showToast(getString(R.string.offline_message_tbt_success_first));
                }
            }
            if (bno.a) {
                StringBuilder sb3 = new StringBuilder("onRouteStateChanged mCarRouteResult=");
                sb3.append(this.g);
                tq.b("NaviMonitor", "AjxRouteCarResultPage", sb3.toString());
                StringBuilder sb4 = new StringBuilder("onRouteStateChanged startPoi=");
                sb4.append(pz.a(this.g.g));
                sb4.append(",endPoi=");
                sb4.append(pz.a(this.g.h));
                tq.b("NaviMonitor", "AjxRouteCarResultPage", sb4.toString());
                if (this.g != null) {
                    StringBuilder sb5 = new StringBuilder("onRouteStateChanged mCarRouteResult.token=");
                    sb5.append(this.g.d);
                    sb5.append(" mitVuiRequestStateListener=");
                    sb5.append(this.ag);
                    tq.b("NaviMonitor", "AjxRouteCarResultPage", sb5.toString());
                }
            }
            if (this.g != null && (this.g.d > 0 || this.b.h > 0)) {
                if (this.ag != null) {
                    this.ag.a();
                } else {
                    b(this.b.h);
                    this.b.h = -1;
                }
            }
        } else if (1 == i2) {
            JSONObject jSONObject = (objArr == null || objArr[0] == null) ? null : objArr[0];
            if (this.ag != null) {
                this.ag.a(jSONObject);
            } else if (this.b.h > 0) {
                a(this.b.h, jSONObject, false);
                this.b.h = -1;
            }
            this.g = null;
        } else if (2 == i2) {
            i();
            if (!(this.r == null || this.r.getAnimation() == null || this.r.getAnimation().hasEnded())) {
                this.r.getAnimation().cancel();
            }
            b();
            c();
        }
        pa paVar = this.ah;
        boolean h2 = h();
        String choiceString = DriveUtil.getChoiceString(DriveUtil.getLastRoutingChoice(), 0);
        BaseMapWidgetPresenter baseMapWidgetPresenter = (BaseMapWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.PATHPREFERENCEANDSCALE);
        if (baseMapWidgetPresenter != null) {
            IMapWidget widget = baseMapWidgetPresenter.getWidget();
            if (widget instanceof RecommendAndScaleWidget) {
                paVar.g = ((RecommendAndScaleWidget) widget).getRecommendView();
            }
            if (paVar.g != null) {
                TextView textView = (TextView) paVar.g.findViewById(R.id.recommend_tv);
                if (textView != null) {
                    textView.setText(choiceString);
                    if ((h2 || !agr.b(paVar.d)) && !TextUtils.isEmpty(choiceString) && choiceString.contains(paVar.d.getResources().getString(R.string.car_method_no_block))) {
                        textView.setTextColor(textView.getResources().getColor(R.color.f_c_2_a));
                    } else {
                        textView.setTextColor(textView.getResources().getColor(R.color.f_c_2));
                    }
                }
                NoDBClickUtil.a(paVar.g, (OnClickListener) new OnClickListener() {
                    public final void onClick(View view) {
                        pa paVar = pa.this;
                        if (!paVar.b) {
                            paVar.c = new DriveRecommendView(paVar.d, new OnClickListener() {
                                public final void onClick(View view) {
                                    pa.this.a();
                                }
                            }, 0);
                            paVar.c.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                            paVar.c.setIsOffline(paVar.a.h());
                            paVar.c.updateViewStatus();
                            int[] iArr = new int[2];
                            view.getLocationOnScreen(iArr);
                            agn.a(view.getContext(), 10.0f);
                            int height = iArr[1] + view.getHeight() + agn.a(view.getContext(), 10.0f);
                            View findViewById = ((Activity) paVar.d).findViewById(16908290);
                            if (findViewById != null) {
                                height = findViewById.getHeight() - height;
                            }
                            paVar.c.setBottomDistance(height);
                            IRouteUI b = paVar.e.b();
                            if (b != null) {
                                if (paVar.f == null) {
                                    paVar.f = b.a();
                                }
                                b.a(new ContainerType[]{ContainerType.FLOW_VIEW, ContainerType.HEAD_VIEW, ContainerType.CONTAINER_VIEW});
                                b.a((View) paVar.c);
                                paVar.c.showRecommendViewAnim();
                                paVar.b = true;
                            }
                        }
                    }
                });
            }
        }
    }

    public void onRouteFocusIndexChanged(int i2) {
        if (this.g != null) {
            this.g.e = i2;
        }
    }

    public final boolean g() {
        if (this.g != null) {
            pg a2 = this.g.a();
            if (a2 != null) {
                StringBuilder sb = new StringBuilder("isLongScene length==");
                sb.append(a2.c);
                AMapLog.d("AjxRouteCarResultPage", sb.toString());
                if (a2.c >= 100000) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public final boolean h() {
        if (this.g == null || this.g.f == null || this.g.f.get(0) == null || this.g.f.get(0).d) {
            return false;
        }
        return true;
    }

    private boolean u() {
        return this.g != null && this.b.g;
    }

    /* access modifiers changed from: 0000 */
    public static void a(String str, JSONObject jSONObject) {
        if (str != null && (str.equals("B026") || str.endsWith("B030"))) {
            return;
        }
        if (jSONObject == null) {
            LogManager.actionLogV2(LogConstant.PAGE_ID_CAR_RESULT_MAP, str);
        } else {
            LogManager.actionLogV2(LogConstant.PAGE_ID_CAR_RESULT_MAP, str, jSONObject);
        }
    }

    /* access modifiers changed from: 0000 */
    public static void a(String str, String str2, String str3) {
        JSONObject jSONObject;
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            jSONObject = null;
        } else {
            jSONObject = new JSONObject();
            try {
                jSONObject.put(str2, str3);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        a(str, jSONObject);
    }

    /* access modifiers changed from: 0000 */
    public final void j() {
        AMapLog.d("AjxRouteCarResultPage", "startRouteBoardMonitor");
        this.ab = System.currentTimeMillis();
        this.ac = 0;
        if (this.Z == null) {
            this.Z = new ok();
            this.Z.a = this.aq;
        }
        this.Z.a(getContext());
    }

    private static boolean v() {
        new re();
        return re.a();
    }

    /* access modifiers changed from: 0000 */
    public final boolean k() {
        if (this.ad || this.f || this.e || this.i) {
            return false;
        }
        return true;
    }

    public bgo getPresenter() {
        return this.b;
    }

    public void onContentViewCreated(View view) {
        this.L = view;
    }

    public final void a(final int i2, String str) {
        DriveUtil.refreshTraffic(getMapView());
        d();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("method", str);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        JSONObject a2 = sb.a("setRouteParams", i2, jSONObject);
        StringBuilder sb = new StringBuilder("setRouteParamsJSON ");
        sb.append(a2.toString());
        tq.b("NaviMonitor", "refreshMitRouteCarParamsRequest", sb.toString());
        a(i2, a2, (a) new a() {
            public final void a() {
                AjxRouteCarResultPage.this.b(i2);
            }

            public final void a(JSONObject jSONObject) {
                AjxRouteCarResultPage.this.a(i2, jSONObject, true);
            }
        });
    }

    /* access modifiers changed from: private */
    public void b(int i2) {
        String str;
        tq.b("NaviMonitor", "mitVuiNotifyResultRequestSuccess", "tokenId ".concat(String.valueOf(i2)));
        if (i2 > 0) {
            tq.b("NaviMonitor", "mitVuiNotifyResultRequestSuccess", "mit语音执行成功通知vcs ");
            bgb a2 = d.a.a(i2);
            if (!(a2 == null || this.g == null)) {
                pg a3 = this.g.a(this.g.e);
                if (a3 != null) {
                    bfe bfe = d.a;
                    str = rm.a(bfe.a(a2.h, a2.i, 10000), a3.h, a3.c, a3.g);
                    d.a.a(i2, 10000, str);
                }
            }
            str = null;
            d.a.a(i2, 10000, str);
        }
        this.ag = null;
    }

    /* access modifiers changed from: private */
    public void a(int i2, JSONObject jSONObject, boolean z2) {
        StringBuilder sb = new StringBuilder("tokenId：");
        sb.append(i2);
        sb.append("poiNoChanged:");
        sb.append(z2);
        tq.b("NaviMonitor", "mitVuiNotifyResultRequestFailed", sb.toString());
        if (i2 > 0) {
            int i3 = SDKFactory.getCoreType;
            if (jSONObject != null) {
                int a2 = agd.a(jSONObject, "errorCode");
                if (!z2 || !(a2 == 10004 || a2 == 10005 || a2 == 10006)) {
                    i3 = a2;
                }
                tq.b("NaviMonitor", "mitVuiNotifyResultRequestFailed", "errorCode： ".concat(String.valueOf(i3)));
            }
            d.a.a(i2, i3, (String) null);
        }
        this.ag = null;
    }

    public final void a(int i2, JSONObject jSONObject, a aVar) {
        this.af = i2;
        this.ag = aVar;
        this.b.a(jSONObject);
    }

    private boolean e(String str) {
        boolean z2 = false;
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject optJSONObject = new JSONObject(str).optJSONObject("startNaviMitVoiceToken");
                if (optJSONObject == null) {
                    return false;
                }
                String e2 = agd.e(optJSONObject, "methodID");
                final int a2 = agd.a(optJSONObject, "token");
                if (TextUtils.equals("startNavi", e2)) {
                    z2 = true;
                    nm.a(getActivity(), str, (defpackage.ms.b) new defpackage.ms.a() {
                        public final void a() {
                            if (AjxRouteCarResultPage.this.d != null) {
                                AjxRouteCarResultPage.this.d.triggerAccessNaviCallback();
                            }
                        }

                        public final void a(boolean z) {
                            tq.b("NaviMonitor", "MIT startNaviPage", "免责 isInNavi： ".concat(String.valueOf(z)));
                            if (!z) {
                                d.a.a(a2, 10000, (String) "");
                            }
                        }
                    }, (String) "car");
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
            }
        }
        return z2;
    }

    public final void l() {
        if (this.d != null) {
            this.d.setCarResultMapState();
        }
        if (this.s != null && this.s.e()) {
            b(false);
        }
    }

    private static void d(String str) {
        vp vpVar = (vp) defpackage.esb.a.a.a(vp.class);
        if (vpVar != null && vpVar.b()) {
            a(str, (String) "status", vpVar.c() ? "wifi" : "mqtt");
        }
    }

    public static void i() {
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().f();
        }
    }

    static /* synthetic */ void b(AjxRouteCarResultPage ajxRouteCarResultPage) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_drive/src/car/restrict_page/CarRestrictPage.page.js");
        pageBundle.putInt("cartype", 0);
        pageBundle.putLong("resultId", ajxRouteCarResultPage.g.c);
        pageBundle.putLong("pathId", ajxRouteCarResultPage.g.a(ajxRouteCarResultPage.g.e).a);
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPageForResult((String) "amap.basemap.action.car_restrict", pageBundle, 140);
        }
    }

    static /* synthetic */ void g(AjxRouteCarResultPage ajxRouteCarResultPage) {
        if (ajxRouteCarResultPage.q != null && ajxRouteCarResultPage.q.getVisibility() == 0) {
            ajxRouteCarResultPage.q.startAnimation(AnimationUtils.loadAnimation(ajxRouteCarResultPage.getContext(), R.anim.tips_hiding));
        }
    }

    static /* synthetic */ void a(AjxRouteCarResultPage ajxRouteCarResultPage, final int i2, final int i3) {
        if (ajxRouteCarResultPage.s != null && ajxRouteCarResultPage.s.e()) {
            ajxRouteCarResultPage.b(false);
        }
        if (ajxRouteCarResultPage.s != null) {
            ajxRouteCarResultPage.s.c();
        }
        ajxRouteCarResultPage.a(true, (AnimationListener) new AnimationListener() {
            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteCarResultPage.this.isAlive() && AjxRouteCarResultPage.this.g != null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("data_type", i2);
                    pageBundle.putInt("route_type", RouteType.CAR.getValue());
                    pageBundle.putObject(RouteItem.ROUTE_DATA, AjxRouteCarResultPage.this.g);
                    pageBundle.putObject("event_id", Integer.valueOf(i3));
                    if (AjxRouteCarResultPage.this.d != null) {
                        pageBundle.putInt("focusIndex", AjxRouteCarResultPage.this.d.getFocusIndex());
                    }
                    pageBundle.putLongArray("result_id", AjxRouteCarResultPage.this.g.a);
                    AjxRouteCarResultPage.this.startPageForResult(AjxRouteCarResultEventDetailPage.class, pageBundle, 150);
                }
            }
        });
    }

    static /* synthetic */ void h(AjxRouteCarResultPage ajxRouteCarResultPage) {
        ajxRouteCarResultPage.startPageForResult((String) "amap.basemap.action.car_plate_input", new PageBundle(), 110);
        ajxRouteCarResultPage.i = true;
    }

    static /* synthetic */ void i(AjxRouteCarResultPage ajxRouteCarResultPage) {
        if (!TextUtils.isEmpty(DriveUtil.getCarPlateNumber())) {
            DriveUtil.setAvoidLimitedPath(true);
            ajxRouteCarResultPage.b.b();
            return;
        }
        ToastHelper.showToast(ajxRouteCarResultPage.getString(R.string.car_plate_empty));
    }

    static /* synthetic */ void j(AjxRouteCarResultPage ajxRouteCarResultPage) {
        if (ajxRouteCarResultPage.g.a(ajxRouteCarResultPage.g.e).p.type == 1) {
            ajxRouteCarResultPage.b.a(null, null, null, null, false, true);
        }
    }

    static /* synthetic */ void a(AjxRouteCarResultPage ajxRouteCarResultPage, int i2, int i3, int i4, String str, String str2, String str3, long[] jArr, int i5) {
        AjxRouteCarResultPage ajxRouteCarResultPage2 = ajxRouteCarResultPage;
        if (ajxRouteCarResultPage2.s != null && ajxRouteCarResultPage2.s.e()) {
            ajxRouteCarResultPage2.b(false);
        }
        if (ajxRouteCarResultPage2.s != null) {
            ajxRouteCarResultPage2.s.c();
        }
        final int i6 = i2;
        final int i7 = i3;
        final int i8 = i4;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final int i9 = i5;
        final long[] jArr2 = jArr;
        AnonymousClass2 r0 = new AnimationListener() {
            final /* synthetic */ int a = 4;

            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteCarResultPage.this.isAlive() && AjxRouteCarResultPage.this.g != null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("data_type", this.a);
                    pageBundle.putInt("route_type", RouteType.CAR.getValue());
                    pageBundle.putObject(RouteItem.ROUTE_DATA, AjxRouteCarResultPage.this.g);
                    pageBundle.putInt("forbiddenId", i6);
                    pageBundle.putInt("forbiddenType", i7);
                    pageBundle.putInt("vehicleType", i8);
                    pageBundle.putString("timeDescription", str4);
                    pageBundle.putString("roadNameString", str5);
                    pageBundle.putString("nextRoadNameString", str6);
                    pageBundle.putInt("focusIndex", i9);
                    pageBundle.putLongArray("result_id", jArr2);
                    AjxRouteCarResultPage.this.startPageForResult(AjxRouteCarResultEventDetailPage.class, pageBundle, 150);
                }
            }
        };
        ajxRouteCarResultPage2.a(true, (AnimationListener) r0);
    }

    static /* synthetic */ void m(AjxRouteCarResultPage ajxRouteCarResultPage) {
        if (ajxRouteCarResultPage.b != null) {
            ow owVar = ajxRouteCarResultPage.b;
            if ((owVar.i == null || owVar.i.g() == null || owVar.i.g().size() <= 0) ? false : true) {
                ToastHelper.showToast(ajxRouteCarResultPage.getContext().getString(R.string.drive_etd_function_not_support));
                return;
            }
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_drive/src/etd/page/RouteETDPage.page.js");
        try {
            String c2 = ajxRouteCarResultPage.b != null ? ajxRouteCarResultPage.b.c() : "";
            POI startPOI = ajxRouteCarResultPage.getStartPOI();
            POI endPOI = ajxRouteCarResultPage.getEndPOI();
            List<POI> midPOIs = ajxRouteCarResultPage.getMidPOIs();
            String str = null;
            if (endPOI != null) {
                str = JSON.toJSONString(ou.a(0, new ta(startPOI, endPOI, midPOIs, CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN)));
            } else {
                tq.a("AjxRouteCarResultPage", "getRequestRouteParam", "end = null");
            }
            if (str == null) {
                str = "";
            }
            if (TextUtils.isEmpty(c2) || TextUtils.isEmpty(str)) {
                AMapLog.e("AjxRouteCarResultPage", "startRouteETDPage() params is empty!");
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("points", new JSONObject(c2));
            jSONObject.put("route_params", new JSONObject(str));
            pageBundle.putString("jsData", jSONObject.toString());
            ajxRouteCarResultPage.startPage(AjxRouteETDPage.class, pageBundle);
            LogManager.actionLogV2(LogConstant.PAGE_ID_CAR_RESULT_MAP, "B126");
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("startRouteETDPage() params err! ");
            sb.append(e2.toString());
            AMapLog.e("AjxRouteCarResultPage", sb.toString());
        }
    }

    static /* synthetic */ void m() {
        DriveUtil.setCarPlateSettingShowCount(DriveUtil.getCarPlateSettingShowCount() + 1);
        DriveUtil.setCarPlateLastSettingTime(System.currentTimeMillis());
    }

    static /* synthetic */ void n() {
        DriveUtil.setCarPlateOpenAvoidLimitedNoticeCount(DriveUtil.getCarPlateOpenAvoidLimitedNoticeCount() + 1);
        DriveUtil.setCarPlateOpenAvoidLimitedLastNoticeTime(System.currentTimeMillis());
    }

    static /* synthetic */ void a(AjxRouteCarResultPage ajxRouteCarResultPage, float f2) {
        if (ajxRouteCarResultPage.M == null && ajxRouteCarResultPage.b.a.b() != null) {
            ajxRouteCarResultPage.M = ajxRouteCarResultPage.b.a.b().b();
        }
        if (ajxRouteCarResultPage.M != null) {
            ajxRouteCarResultPage.M.setAlpha(f2);
        }
    }

    static /* synthetic */ void a(AjxRouteCarResultPage ajxRouteCarResultPage, oo ooVar) {
        if (!ajxRouteCarResultPage.f && ooVar != null && ooVar.a()) {
            if (bno.a) {
                AMapLog.debug("route.carnavi", "AjxRouteCarResultPage", "onCarSceneCallback    update car scene tip ");
            }
            if (ajxRouteCarResultPage.q == null) {
                ajxRouteCarResultPage.q = (ViewGroup) ajxRouteCarResultPage.getContentView().findViewById(R.id.viewstub_car_scene_layout);
                ajxRouteCarResultPage.U = (CarSceneTip) ajxRouteCarResultPage.q.findViewById(R.id.route_carscenetip);
            }
            ajxRouteCarResultPage.q.setVisibility(0);
            if (ajxRouteCarResultPage.U != null) {
                try {
                    ajxRouteCarResultPage.U.setData(ooVar);
                    ajxRouteCarResultPage.U.setVisibility(0);
                    ajxRouteCarResultPage.U.setOnTipClickListener(new com.amap.bundle.drive.result.autonavisearchmanager.view.CarSceneTip.a() {
                        public final void a(defpackage.oo.a aVar) {
                            AjxRouteCarResultPage.this.a((int) SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_LENGTH, 0);
                            if (AjxRouteCarResultPage.this.s != null) {
                                AjxRouteCarResultPage.this.s.d(0);
                            }
                            if (aVar != null) {
                                AjxRouteCarResultPage.this.X = true;
                                POI d = AjxRouteCarResultPage.this.b.d();
                                if (aVar.a == 101) {
                                    ArrayList arrayList = new ArrayList();
                                    POI poi = aVar.d;
                                    if (poi.getEntranceList() != null && poi.getEntranceList().size() > 0) {
                                        arrayList.addAll(poi.getEntranceList());
                                    }
                                    POI createPOI = POIFactory.createPOI(poi.getName(), poi.getPoint());
                                    createPOI.setId(poi.getId());
                                    createPOI.setPid(d.getId());
                                    createPOI.setType(poi.getType());
                                    createPOI.getPoiExtra().put("sub_poi_name", aVar.c);
                                    createPOI.getPoiExtra().put("main_poi", d);
                                    createPOI.setEntranceList(arrayList);
                                    createPOI.getPoiExtra().put("scene_poi", Boolean.TRUE);
                                    createPOI.setEndPoiExtension(poi.getEndPoiExtension());
                                    createPOI.setTransparent(poi.getTransparent());
                                    AjxRouteCarResultPage.this.b.a(createPOI);
                                    AjxRouteCarResultPage.this.b.a(createPOI, null, null, null, true, false);
                                    return;
                                }
                                if (d != null) {
                                    ArrayList arrayList2 = (ArrayList) aVar.b;
                                    POI createPOI2 = POIFactory.createPOI(d.getName(), d.getPoint());
                                    createPOI2.setId(d.getId());
                                    createPOI2.setType(d.getType());
                                    createPOI2.getPoiExtra().put("build_type", Integer.valueOf(0));
                                    createPOI2.getPoiExtra().put("is_car_scene_request", Boolean.TRUE);
                                    createPOI2.getPoiExtra().put("sub_poi_name", aVar.c);
                                    createPOI2.getPoiExtra().put("main_poi", d);
                                    createPOI2.getPoiExtra().put("build_type_train_station_entrance_exit_poies", arrayList2);
                                    createPOI2.getPoiExtra().put("scene_poi", Boolean.TRUE);
                                    createPOI2.setEndPoiExtension(d.getEndPoiExtension());
                                    createPOI2.setTransparent(d.getTransparent());
                                    AjxRouteCarResultPage.this.b.a(createPOI2);
                                    AjxRouteCarResultPage.this.b.a(createPOI2, d, arrayList2, null, true, false);
                                }
                            }
                        }
                    });
                    qd.a(ajxRouteCarResultPage.q);
                    ajxRouteCarResultPage.a((int) SecExceptionCode.SEC_ERROE_OPENSDK_DECODE_FAILED, UIUtils.dip2px(44, ajxRouteCarResultPage.U.getContext()));
                    if (ajxRouteCarResultPage.s != null) {
                        ajxRouteCarResultPage.s.d(UIUtils.dip2px(44, ajxRouteCarResultPage.U.getContext()));
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    static /* synthetic */ void a(AjxRouteCarResultPage ajxRouteCarResultPage, String str) {
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(str)) {
            pageBundle.putString("jsData", str);
        }
        pageBundle.putString("url", ModuleRouteDriveResult.CAR_PREVIEW);
        pageBundle.putBoolean("is_from_favorite", ajxRouteCarResultPage.e);
        pageBundle.putBoolean("is_from_etrip", ajxRouteCarResultPage.f);
        pageBundle.putInt("route_car_type_key", 0);
        pageBundle.putObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ, ajxRouteCarResultPage.g);
        ajxRouteCarResultPage.startPageForResult(AjxRouteCarResultBrowserPage.class, pageBundle, 200);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0023  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0084  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void b(com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage r5, final java.lang.String r6) {
        /*
            boolean r0 = r5.f
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x0020
            boolean r0 = r5.e
            if (r0 != 0) goto L_0x0020
            java.lang.String r0 = "route_result_show_truck_guide"
            boolean r0 = defpackage.re.a(r0, r1)
            if (r0 != 0) goto L_0x0020
            com.autonavi.map.db.model.Car r0 = com.amap.bundle.drivecommon.tools.DriveUtil.getCarTruckInfo()
            if (r0 == 0) goto L_0x001b
            r0 = 1
            goto L_0x001c
        L_0x001b:
            r0 = 0
        L_0x001c:
            if (r0 == 0) goto L_0x0020
            r0 = 1
            goto L_0x0021
        L_0x0020:
            r0 = 0
        L_0x0021:
            if (r0 == 0) goto L_0x0084
            int r0 = defpackage.sa.a(r6)
            boolean r3 = defpackage.sa.d(r0)
            if (r3 == 0) goto L_0x0032
            r3 = 10038(0x2736, float:1.4066E-41)
            defpackage.sa.a(r0, r3)
        L_0x0032:
            com.autonavi.widget.ui.AlertView r0 = r5.R
            if (r0 == 0) goto L_0x0046
            com.autonavi.widget.ui.AlertView r0 = r5.R
            boolean r0 = r5.isViewLayerShowing(r0)
            if (r0 == 0) goto L_0x0046
            com.autonavi.widget.ui.AlertView r0 = r5.R
            r5.dismissViewLayer(r0)
            r0 = 0
            r5.R = r0
        L_0x0046:
            com.autonavi.widget.ui.AlertView$a r0 = new com.autonavi.widget.ui.AlertView$a
            android.content.Context r3 = r5.getContext()
            r0.<init>(r3)
            int r3 = com.autonavi.minimap.R.string.car_navigation_is_about_to_start
            r0.a(r3)
            int r3 = com.autonavi.minimap.R.string.can_not_avoid_the_limitation_of_truck
            r0.b(r3)
            int r3 = com.autonavi.minimap.R.string.continue_car_navigation
            com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage$23 r4 = new com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage$23
            r4.<init>(r6)
            r0.a(r3, r4)
            int r6 = com.autonavi.minimap.R.string.start_truck_navigation
            com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage$24 r3 = new com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage$24
            r3.<init>()
            r0.b(r6, r3)
            r0.a(r1)
            com.autonavi.widget.ui.AlertView r6 = r0.a()
            r5.R = r6
            com.autonavi.widget.ui.AlertView r6 = r5.R
            r5.showViewLayer(r6)
            com.autonavi.widget.ui.AlertView r6 = r5.R
            r6.startAnimation()
            r5.S = r2
            r6 = 1
            goto L_0x0097
        L_0x0084:
            boolean r0 = r5.e(r6)
            if (r0 != 0) goto L_0x00a5
            android.app.Activity r0 = r5.getActivity()
            com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage$8 r1 = new com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage$8
            r1.<init>()
            boolean r6 = defpackage.nm.a(r0, r6, r1)
        L_0x0097:
            boolean r0 = r5.f
            if (r0 != 0) goto L_0x00a5
            if (r6 == 0) goto L_0x00a5
            r5.c(r2)
            java.lang.String r5 = "B130"
            d(r5)
        L_0x00a5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.b(com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage, java.lang.String):void");
    }

    static /* synthetic */ void c(AjxRouteCarResultPage ajxRouteCarResultPage, final String str) {
        if (!ajxRouteCarResultPage.e()) {
            ToastHelper.showLongToast(ajxRouteCarResultPage.getActivity().getString(R.string.navi_to_cur_location));
            return;
        }
        if (ajxRouteCarResultPage.g != null) {
            new MapSharePreference(SharePreferenceName.SharedPreferences).putBooleanValue("route_board_red_point_tip", false);
            if (ajxRouteCarResultPage.getSuspendManager() != null) {
                ajxRouteCarResultPage.getSuspendManager().d().f();
            }
            if (ajxRouteCarResultPage.s != null && ajxRouteCarResultPage.s.e()) {
                ajxRouteCarResultPage.b(false);
            }
            if (ajxRouteCarResultPage.s != null) {
                ajxRouteCarResultPage.s.c();
            }
            ajxRouteCarResultPage.a(true, (AnimationListener) new AnimationListener() {
                final /* synthetic */ boolean b = true;

                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                }

                public final void onAnimationEnd(Animation animation) {
                    if (AjxRouteCarResultPage.this.isAlive()) {
                        AjxRouteCarResultPage.this.b;
                        if (AjxRouteCarResultPage.this.g != null) {
                            NewRouteBoardPage.startNewRouteBoardPage(AjxRouteCarResultPage.this.getPageContext(), str, AjxRouteCarResultPage.this.g, this.b);
                        }
                    }
                }
            });
            ajxRouteCarResultPage.c(true);
            d((String) LogConstant.MAIN_YANSHU_XUANDIAN_SOU_ZHOU_BIAN);
        }
    }

    static /* synthetic */ void d(AjxRouteCarResultPage ajxRouteCarResultPage, String str) {
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(str)) {
            pageBundle.putString("jsData", str);
        }
        pageBundle.putString("url", ModuleRouteDriveResult.CAR_MOCK_NAVI);
        pageBundle.putInt("route_car_type_key", RouteType.CAR.getValue());
        ajxRouteCarResultPage.startPage(AjxRouteCarNaviSimulatePage.class, pageBundle);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00d1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void e(com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage r9, java.lang.String r10) {
        /*
            r0 = 0
            r1 = 15
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x003f }
            r2.<init>(r10)     // Catch:{ JSONException -> 0x003f }
            java.lang.String r3 = "focusIndex"
            int r3 = r2.optInt(r3)     // Catch:{ JSONException -> 0x003f }
            java.lang.String r4 = "fromType"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x003d }
            if (r4 == 0) goto L_0x001d
            java.lang.String r4 = "fromType"
            int r4 = r2.optInt(r4)     // Catch:{ JSONException -> 0x003d }
            r1 = r4
        L_0x001d:
            java.lang.String r4 = "routeID"
            org.json.JSONArray r2 = r2.optJSONArray(r4)     // Catch:{ JSONException -> 0x003d }
            if (r2 != 0) goto L_0x0027
            return
        L_0x0027:
            int r4 = r2.length()     // Catch:{ JSONException -> 0x003d }
            int[] r5 = new int[r4]     // Catch:{ JSONException -> 0x003d }
        L_0x002d:
            if (r0 >= r4) goto L_0x0038
            int r6 = r2.getInt(r0)     // Catch:{ JSONException -> 0x003d }
            r5[r0] = r6     // Catch:{ JSONException -> 0x003d }
            int r0 = r0 + 1
            goto L_0x002d
        L_0x0038:
            long r4 = com.autonavi.jni.eyrie.amap.tbt.NaviManager.createPathResult(r5)     // Catch:{ JSONException -> 0x003d }
            goto L_0x0047
        L_0x003d:
            r0 = move-exception
            goto L_0x0042
        L_0x003f:
            r2 = move-exception
            r0 = r2
            r3 = 0
        L_0x0042:
            r0.printStackTrace()
            r4 = 0
        L_0x0047:
            java.lang.String r0 = "AjxRouteCarResultPage"
            java.lang.String r2 = "startErrorReport--params="
            java.lang.String r10 = java.lang.String.valueOf(r10)
            java.lang.String r10 = r2.concat(r10)
            com.amap.bundle.logs.AMapLog.d(r0, r10)
            com.autonavi.common.PageBundle r10 = new com.autonavi.common.PageBundle
            r10.<init>()
            com.amap.bundle.drivecommon.model.RouteCarResultData r0 = new com.amap.bundle.drivecommon.model.RouteCarResultData
            com.autonavi.minimap.drive.route.CalcRouteScene r2 = com.autonavi.minimap.drive.route.CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN
            r0.<init>(r2)
            r0.setFocusRouteIndex(r3)
            com.autonavi.jni.ae.route.route.CalcRouteResult r2 = new com.autonavi.jni.ae.route.route.CalcRouteResult
            r2.<init>()
            r2.setPtr(r4)
            java.util.Map<java.lang.Object, java.lang.Object> r3 = r2.mResultInfo
            java.lang.String r6 = "valid"
            java.lang.Boolean r7 = java.lang.Boolean.TRUE
            r3.put(r6, r7)
            r0.setCalcRouteResult(r2)
            com.autonavi.minimap.drive.route.CalcRouteScene r3 = com.autonavi.minimap.drive.route.CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN
            qb r6 = r9.m
            com.autonavi.common.model.POI r6 = r6.n()
            qb r7 = r9.m
            com.autonavi.common.model.POI r7 = r7.o()
            com.amap.bundle.drivecommon.model.NavigationResult r2 = defpackage.rn.a(r3, r6, r7, r2)
            r9.getContext()
            java.lang.String r3 = "0"
            java.lang.String r3 = defpackage.ru.a(r3)
            qb r6 = r9.m
            com.autonavi.common.model.POI r6 = r6.n()
            qb r7 = r9.m
            com.autonavi.common.model.POI r7 = r7.o()
            r0.setNaviResultData(r6, r7, r2, r3)
            qb r2 = r9.m
            com.autonavi.common.model.POI r2 = r2.n()
            com.autonavi.common.model.GeoPoint r2 = r2.getPoint()
            r0.setShareStartPos(r2)
            qb r2 = r9.m
            com.autonavi.common.model.POI r2 = r2.o()
            com.autonavi.common.model.GeoPoint r2 = r2.getPoint()
            r0.setShareEndPos(r2)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            qb r6 = r9.m
            java.util.List r6 = r6.p()
            if (r6 == 0) goto L_0x00f2
            qb r6 = r9.m
            java.util.List r6 = r6.p()
            java.util.Iterator r6 = r6.iterator()
        L_0x00db:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x00f2
            java.lang.Object r7 = r6.next()
            com.autonavi.common.model.POI r7 = (com.autonavi.common.model.POI) r7
            com.autonavi.common.model.GeoPoint r8 = r7.getPoint()
            r2.add(r8)
            r3.add(r7)
            goto L_0x00db
        L_0x00f2:
            r0.setShareMidPos(r2)
            r0.setMidPOIs(r3)
            java.lang.String r2 = "RouteCarResultErrorReportFragment.bundle_key_result"
            r10.putObject(r2, r0)
            java.lang.String r0 = "RouteCarResultErrorReportFragment.from_page_code"
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r10.putString(r0, r1)
            java.lang.String r0 = "pathResult"
            r10.putLong(r0, r4)
            java.lang.String r0 = "amap.basemap.action.route_car_error_report"
            r9.startPage(r0, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.e(com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage, java.lang.String):void");
    }

    static /* synthetic */ void a(AjxRouteCarResultPage ajxRouteCarResultPage, int i2, long[] jArr, int i3, int i4) {
        if (ajxRouteCarResultPage.s != null && ajxRouteCarResultPage.s.e()) {
            ajxRouteCarResultPage.b(false);
        }
        if (ajxRouteCarResultPage.s != null) {
            ajxRouteCarResultPage.s.c();
        }
        final int i5 = i2;
        final int i6 = i3;
        final int i7 = i4;
        final long[] jArr2 = jArr;
        AnonymousClass36 r1 = new AnimationListener() {
            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteCarResultPage.this.isAlive() && AjxRouteCarResultPage.this.g != null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("data_type", i5);
                    pageBundle.putInt("route_type", RouteType.CAR.getValue());
                    pageBundle.putObject(RouteItem.ROUTE_DATA, AjxRouteCarResultPage.this.g);
                    pageBundle.putInt("event_id", i6);
                    pageBundle.putInt("focusIndex", i7);
                    pageBundle.putLongArray("result_id", jArr2);
                    AjxRouteCarResultPage.this.startPageForResult(AjxRouteCarResultEventDetailPage.class, pageBundle, 150);
                }
            }
        };
        ajxRouteCarResultPage.a(true, (AnimationListener) r1);
    }

    static /* synthetic */ void a(AjxRouteCarResultPage ajxRouteCarResultPage, int i2, long[] jArr, int i3, double d2, double d3, int i4, String str) {
        AjxRouteCarResultPage ajxRouteCarResultPage2 = ajxRouteCarResultPage;
        if (ajxRouteCarResultPage2.s != null && ajxRouteCarResultPage2.s.e()) {
            ajxRouteCarResultPage2.b(false);
        }
        if (ajxRouteCarResultPage2.s != null) {
            ajxRouteCarResultPage2.s.c();
        }
        final int i5 = i3;
        final long[] jArr2 = jArr;
        final double d4 = d2;
        final double d5 = d3;
        final int i6 = i4;
        final String str2 = str;
        final int i7 = i2;
        AnonymousClass37 r0 = new AnimationListener() {
            final /* synthetic */ int a = 3;

            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteCarResultPage.this.isAlive()) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("data_type", this.a);
                    pageBundle.putInt("route_type", RouteType.CAR.getValue());
                    pageBundle.putObject(RouteItem.ROUTE_DATA, AjxRouteCarResultPage.this.g);
                    pageBundle.putInt("focusIndex", i5);
                    pageBundle.putLongArray("result_id", jArr2);
                    pageBundle.putDouble("opanlayer_lon", d4);
                    pageBundle.putDouble("opanlayer_lat", d5);
                    pageBundle.putInt("opanlayer_z", i6);
                    pageBundle.putString("opanlayer_poiId", str2);
                    pageBundle.putInt("event_id", i7);
                    AjxRouteCarResultPage.this.startPageForResult(AjxRouteCarResultEventDetailPage.class, pageBundle, 150);
                }
            }
        };
        ajxRouteCarResultPage2.a(true, (AnimationListener) r0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x001f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void f(com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage r3, java.lang.String r4) {
        /*
            r0 = 0
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0016 }
            r1.<init>(r4)     // Catch:{ JSONException -> 0x0016 }
            java.lang.String r4 = "name"
            java.lang.String r4 = r1.optString(r4)     // Catch:{ JSONException -> 0x0016 }
            java.lang.String r2 = "adcode"
            java.lang.String r1 = r1.optString(r2)     // Catch:{ JSONException -> 0x0014 }
            r0 = r1
            goto L_0x001b
        L_0x0014:
            r1 = move-exception
            goto L_0x0018
        L_0x0016:
            r1 = move-exception
            r4 = r0
        L_0x0018:
            r1.printStackTrace()
        L_0x001b:
            ph r1 = r3.g
            if (r1 == 0) goto L_0x0034
            boolean r1 = android.text.TextUtils.isEmpty(r4)
            if (r1 == 0) goto L_0x0026
            goto L_0x0034
        L_0x0026:
            te r1 = new te
            r1.<init>()
            r1.a = r4
            r1.b = r0
            ph r3 = r3.g
            r3.j = r1
            return
        L_0x0034:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage.f(com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage, java.lang.String):void");
    }

    static /* synthetic */ void v(AjxRouteCarResultPage ajxRouteCarResultPage) {
        if (ajxRouteCarResultPage.d != null) {
            ajxRouteCarResultPage.d.callbackErrorReportClick(true);
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", AMapAppGlobal.getApplication().getString(R.string.action_log_type_car));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00001", "B022", jSONObject);
    }

    static /* synthetic */ void w(AjxRouteCarResultPage ajxRouteCarResultPage) {
        tq.b("NaviMonitor", "dissMitNeedMissView", "dissMitNeedMissView ");
        if (ajxRouteCarResultPage.ah != null && ajxRouteCarResultPage.ah.b) {
            ajxRouteCarResultPage.ah.a();
        }
        if (ajxRouteCarResultPage.d != null) {
            ajxRouteCarResultPage.d.hideSearchAlongView();
        }
        IRouteUI b2 = ajxRouteCarResultPage.b.a.b();
        if (b2 != null) {
            b2.p();
        }
    }

    static /* synthetic */ void c(String str) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", str);
            LogManager.actionLogV2("P00014", LogConstant.MAIN_PAGE_MD5_ERROR_BUTTONID, jSONObject);
        } catch (JSONException unused) {
        }
    }

    static /* synthetic */ boolean D(AjxRouteCarResultPage ajxRouteCarResultPage) {
        return ajxRouteCarResultPage.h() || !v();
    }

    static /* synthetic */ IAjxContext I(AjxRouteCarResultPage ajxRouteCarResultPage) {
        if (ajxRouteCarResultPage.mAjxView == null) {
            return null;
        }
        return ajxRouteCarResultPage.mAjxView.getAjxContext();
    }

    static /* synthetic */ void a(AjxRouteCarResultPage ajxRouteCarResultPage, int i2, String str) {
        if (TextUtils.equals(str, DriveUtil.getLastRoutingChoice())) {
            sa.a(i2, 10000);
            return;
        }
        DriveUtil.putLastRoutingChoice(str);
        DriveUtil.refreshTraffic(ajxRouteCarResultPage.getMapView());
        ajxRouteCarResultPage.d();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("method", str);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        ajxRouteCarResultPage.b.a(sb.b("setRouteParamsInCarRoutePage", i2, jSONObject));
    }
}
