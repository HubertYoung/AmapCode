package com.amap.bundle.drive.result.motorresult.result;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.drive.ajx.inter.IMotorActivityCallback;
import com.amap.bundle.drive.ajx.inter.JsCommandCallback;
import com.amap.bundle.drive.ajx.inter.OnCalRouteFromData;
import com.amap.bundle.drive.ajx.inter.OnObtainFocusCallBack;
import com.amap.bundle.drive.ajx.inter.OnRouteStateChangeListener;
import com.amap.bundle.drive.ajx.inter.OnTripPoiChangeListener;
import com.amap.bundle.drive.ajx.inter.RouteEventActionInterface;
import com.amap.bundle.drive.ajx.inter.SearchAlongCallback;
import com.amap.bundle.drive.ajx.module.ModuleCommonBusiness;
import com.amap.bundle.drive.ajx.module.ModuleRouteDriveResult;
import com.amap.bundle.drive.result.autonavisearchmanager.inter.CarSceneSearchAosCallback;
import com.amap.bundle.drive.result.autonavisearchmanager.view.CarSceneTip;
import com.amap.bundle.drive.result.motorresult.browser.AjxRouteMotorResultBrowserPage;
import com.amap.bundle.drive.result.motorresult.event.AjxRouteMotorResultEventDetailPage;
import com.amap.bundle.drive.result.motorresult.result.tip.AjxMotorTipsManager;
import com.amap.bundle.drive.result.motorresult.result.tip.AjxMotorTipsManager.TipPriorityComparator;
import com.amap.bundle.drive.result.tip.util.RouteCarResultTipUtil;
import com.amap.bundle.drive.result.tip.util.RouteCarResultTipUtil.TipType;
import com.amap.bundle.drive.result.tip.view.TipsView;
import com.amap.bundle.drive.result.view.DriveRecommendView;
import com.amap.bundle.drivecommon.model.NavigationResult;
import com.amap.bundle.drivecommon.model.RouteCarResultData;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.util.NetworkReachability;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.NoDBClickUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
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
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.model.Coord2D;
import com.autonavi.jni.ae.route.model.RouteIncident;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.jni.eyrie.amap.tbt.model.RoutePOIInfo;
import com.autonavi.jni.eyrie.amap.tbt.model.RouteWayPoint;
import com.autonavi.map.core.LocationMode.LocationGpsAndNetwork;
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
import com.autonavi.minimap.poi.PoiRequestHolder;
import com.autonavi.minimap.poi.param.NaviinfoRequest;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.widget.SyncPopupWindow;
import com.autonavi.widget.ui.AlertView;
import com.sina.weibo.sdk.utils.UIUtils;
import com.uc.webview.export.internal.SDKFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class AjxRouteMotorResultPage extends Ajx3Page implements OnTouchListener, bgm, OnRouteStateChangeListener, OnTripPoiChangeListener, IVoiceCmdResponder, LocationGpsAndNetwork, f {
    private View A;
    private int B = 0;
    private int C = 1;
    private boolean D = true;
    private boolean E = false;
    private boolean F;
    private alt G;
    private View H;
    private int I;
    private View J;
    /* access modifiers changed from: private */
    public qa K;
    /* access modifiers changed from: private */
    public boolean L = true;
    private ContainerType[] M = null;
    private ContainerType[] N = null;
    private AlertView O;
    private Animation P;
    private CarSceneTip Q;
    /* access modifiers changed from: private */
    public ViewGroup R;
    /* access modifiers changed from: private */
    public boolean S = false;
    /* access modifiers changed from: private */
    public boolean T = false;
    /* access modifiers changed from: private */
    public byte[] U;
    private Handler V = new a(0);
    private View W;
    private int X = -1;
    private String Y;
    private int Z = -1;
    View a;
    /* access modifiers changed from: private */
    public pb aa;
    private ModuleVUI ab;
    private com.amap.bundle.drive.result.motorresult.result.tip.AjxMotorTipsManager.a ac = new com.amap.bundle.drive.result.motorresult.result.tip.AjxMotorTipsManager.a() {
        public final void a(int i) {
            if ((!(i == 3 || i == 2) || !AjxRouteMotorResultPage.this.S) && AjxRouteMotorResultPage.this.q != null && !AjxRouteMotorResultPage.this.q.i) {
                if (AjxRouteMotorResultPage.this.q == null || !AjxRouteMotorResultPage.this.q.c()) {
                    AjxRouteMotorResultPage.this.y.requestJsurfaceAreaSizeChange(SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_PARAM, AjxRouteMotorResultPage.this.q.g());
                } else {
                    AjxRouteMotorResultPage.this.y.requestJsurfaceAreaSizeChange(1100, AjxRouteMotorResultPage.this.q.g());
                }
            }
        }

        public final void b(int i) {
            AjxRouteMotorResultPage.this.S = true;
            AjxRouteMotorResultPage.this.a(true);
            if (i != 14) {
                switch (i) {
                    case 0:
                    case 1:
                    case 8:
                    case 9:
                        AjxRouteMotorResultPage.f(AjxRouteMotorResultPage.this);
                        return;
                    case 2:
                        AjxRouteMotorResultPage.a(AjxRouteMotorResultPage.this, 2, AjxRouteMotorResultPage.this.q.p.id);
                        return;
                    case 3:
                        AjxRouteMotorResultPage.a(AjxRouteMotorResultPage.this, 2, AjxRouteMotorResultPage.this.q.p.id);
                        AjxRouteMotorResultPage.a((String) "B098", (String) "from", (String) ModulePoi.TIPS);
                        return;
                    case 4:
                        AjxRouteMotorResultPage.h(AjxRouteMotorResultPage.this);
                        return;
                    case 5:
                        px<sn> pxVar = AjxRouteMotorResultPage.this.q.s;
                        if (pxVar != null) {
                            AjxRouteMotorResultPage.a(AjxRouteMotorResultPage.this, 1, ((sn) pxVar.f).a);
                            AjxRouteMotorResultPage.a((String) "B099", (String) "from", (String) ModulePoi.TIPS);
                            return;
                        }
                        break;
                    case 6:
                        AjxRouteMotorResultPage.g(AjxRouteMotorResultPage.this);
                        return;
                    case 7:
                        AjxRouteMotorResultPage.i(AjxRouteMotorResultPage.this);
                        return;
                    case 10:
                        AjxMotorTipsManager c = AjxRouteMotorResultPage.this.q;
                        pk pkVar = (c.o == null || c.o.size() <= 0) ? null : c.o.get(0);
                        if (!(pkVar == null || AjxRouteMotorResultPage.this.e == null)) {
                            AjxRouteMotorResultPage.a(AjxRouteMotorResultPage.this, pkVar.a, pkVar.b, pkVar.d, pkVar.c, pkVar.e, pkVar.f, AjxRouteMotorResultPage.this.e.a, 0);
                            break;
                        }
                        break;
                    default:
                        switch (i) {
                            case 20:
                            case 21:
                                AjxRouteMotorResultPage.a(AjxRouteMotorResultPage.this, 2, AjxRouteMotorResultPage.this.q.q.id);
                                return;
                        }
                }
                return;
            }
            AjxRouteMotorResultPage.this.b.b();
        }

        public final void c(int i) {
            AjxRouteMotorResultPage.this.S = false;
            if (i == 4) {
                AjxRouteMotorResultPage.f();
            } else if (i == 6) {
                AjxRouteMotorResultPage.e();
            }
        }
    };
    private IMotorActivityCallback ad = new IMotorActivityCallback() {
        public final void onActivityCallback() {
            if (AjxRouteMotorResultPage.this.L) {
                ctl ctl = (ctl) defpackage.esb.a.a.a(ctl.class);
                if (ctl != null) {
                    ctl.a("25", new Callback<ctm>(ctl) {
                        final /* synthetic */ ctl a;

                        public void error(Throwable th, boolean z) {
                        }

                        {
                            this.a = r2;
                        }

                        public void callback(ctm ctm) {
                            if (ctm != null && AjxRouteMotorResultPage.this != null && AjxRouteMotorResultPage.this.isStarted() && AjxRouteMotorResultPage.this.L && ctm.a == 1 && !TextUtils.isEmpty(ctm.c)) {
                                AjxRouteMotorResultPage.this.L = false;
                                this.a.a(AjxRouteMotorResultPage.this, "25", ctm.c);
                            }
                        }
                    });
                }
            }
        }
    };
    private bfa ae = new bfa() {
        public final void a(boolean z) {
            if (AjxRouteMotorResultPage.this.y != null) {
                AjxRouteMotorResultPage.this.y.dispatchVUILayerEvent(z);
            }
            if (AjxRouteMotorResultPage.this.aa != null && z) {
                AjxRouteMotorResultPage.this.aa.a();
            }
        }
    };
    private OnClickListener af = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.route_car_result_refresh) {
                AjxRouteMotorResultPage.q(AjxRouteMotorResultPage.this);
                AjxRouteMotorResultPage.g();
            }
        }
    };
    private com.amap.bundle.drive.result.motorresult.result.suspend.MotorLongScenePanel.a ag = new com.amap.bundle.drive.result.motorresult.result.suspend.MotorLongScenePanel.a() {
        public final void a(int i, boolean z) {
            switch (i) {
                case 1:
                    AjxRouteMotorResultPage.this.y.requestSupplyShowHide(z);
                    return;
                case 2:
                    AjxRouteMotorResultPage.this.y.requestServiceAreaShowHide(z);
                    return;
                case 3:
                    AjxRouteMotorResultPage.d(AjxRouteMotorResultPage.this, z);
                    break;
            }
        }
    };
    private OnCalRouteFromData ah = new OnCalRouteFromData() {
        public final void onCalRouteFromData() {
            if (AjxRouteMotorResultPage.this.b.c != null && AjxRouteMotorResultPage.this.b.c.getPoint() != null && AjxRouteMotorResultPage.this.b.d != null && AjxRouteMotorResultPage.this.b.d.getPoint() != null) {
                POI poi = AjxRouteMotorResultPage.this.b.c;
                POI poi2 = AjxRouteMotorResultPage.this.b.d;
                RouteWayPoint routeWayPoint = new RouteWayPoint();
                RoutePOIInfo routePOIInfo = new RoutePOIInfo();
                ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
                ISearchPoiData iSearchPoiData2 = (ISearchPoiData) poi2.as(ISearchPoiData.class);
                RouteWayPoint routeWayPoint2 = null;
                if (!(iSearchPoiData == null || iSearchPoiData2 == null)) {
                    GeoPoint point = iSearchPoiData.getPoint();
                    GeoPoint point2 = iSearchPoiData2.getPoint();
                    if (!(point == null || point2 == null)) {
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
                        coord2D3.lon = poi2.getPoint().getLongitude();
                        coord2D3.lat = poi2.getPoint().getLatitude();
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
                        routeWayPoint2 = routeWayPoint;
                    }
                }
                if (routeWayPoint2 != null) {
                    NaviManager.calcRouteFromDataNew(2, routeWayPoint2, AjxRouteMotorResultPage.this.U);
                }
            }
        }
    };
    private JsCommandCallback ai = new JsCommandCallback() {
        public final boolean callback(int i, String... strArr) {
            switch (i) {
                case 1:
                    cde suspendManager = AjxRouteMotorResultPage.this.getSuspendManager();
                    if (suspendManager != null) {
                        cdz d = suspendManager.d();
                        if (d != null) {
                            d.f();
                            d.e();
                        }
                    }
                    return true;
                case 6:
                    AjxRouteMotorResultPage ajxRouteMotorResultPage = AjxRouteMotorResultPage.this;
                    if (ajxRouteMotorResultPage.o != null) {
                        ajxRouteMotorResultPage.b();
                    }
                    ajxRouteMotorResultPage.finish();
                    ON_BACK_TYPE on_back_type = ON_BACK_TYPE.TYPE_IGNORE;
                    return true;
                case 7:
                    AjxRouteMotorResultPage ajxRouteMotorResultPage2 = AjxRouteMotorResultPage.this;
                    RouteType routeType = RouteType.TAXI;
                    if (!ajxRouteMotorResultPage2.c) {
                        ajxRouteMotorResultPage2.j.a(routeType);
                    } else {
                        ajxRouteMotorResultPage2.finish();
                        PageBundle pageBundle = new PageBundle("amap.extra.route.route", "com.autonavi.minimap");
                        ps psVar = ajxRouteMotorResultPage2.b;
                        pageBundle.putObject("bundle_key_poi_start", psVar.e != null ? psVar.e.f() : null);
                        pageBundle.putObject("bundle_key_poi_end", ajxRouteMotorResultPage2.b.c());
                        pageBundle.putBoolean("bundle_key_auto_route", true);
                        pageBundle.putObject("bundle_key_route_type", routeType);
                        bax bax = (bax) defpackage.esb.a.a.a(bax.class);
                        if (bax != null) {
                            bax.a(pageBundle);
                        }
                    }
                    return true;
                case 25:
                    if (AjxRouteMotorResultPage.this.q != null) {
                        AjxRouteMotorResultPage.this.q.a(Float.valueOf(1.0f));
                    }
                    if (AjxRouteMotorResultPage.this.R != null) {
                        AjxRouteMotorResultPage.this.R.setAlpha(1.0f);
                    }
                    if (AjxRouteMotorResultPage.this.b.a() == 102) {
                        AjxRouteMotorResultPage.a(AjxRouteMotorResultPage.this, 1.0f);
                    }
                    if (AjxRouteMotorResultPage.this.b.a() != 102) {
                        AjxRouteMotorResultPage.this.a(0.0f);
                        break;
                    }
                    break;
                case 26:
                    if (strArr != null && strArr.length > 0) {
                        AjxRouteMotorResultPage.d(AjxRouteMotorResultPage.this, strArr[0]);
                        break;
                    }
            }
            if (AjxRouteMotorResultPage.this.isResumed() && i == 1004) {
                AjxRouteMotorResultPage.e(AjxRouteMotorResultPage.this, "46");
            }
            return false;
        }
    };
    private OnObtainFocusCallBack aj = new OnObtainFocusCallBack() {
        public final void onObtainFocus() {
            AjxRouteMotorResultPage.d();
        }
    };
    public ps b;
    public boolean c = false;
    public boolean d;
    public ph e;
    public String f;
    public boolean g;
    SyncPopupWindow h;
    pc i = null;
    public qb j;
    public String k;
    public boolean l;
    public String m;
    public String n;
    ViewGroup o;
    /* access modifiers changed from: 0000 */
    public ViewGroup p;
    public AjxMotorTipsManager q;
    protected Handler r = new Handler();
    OnClickListener s = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.tips_entrance) {
                AjxRouteMotorResultPage ajxRouteMotorResultPage = AjxRouteMotorResultPage.this;
                if (ajxRouteMotorResultPage.q != null) {
                    AjxRouteMotorResultPage.a((String) "B088", (String) "type", String.valueOf(ajxRouteMotorResultPage.q.d()));
                    Animation loadAnimation = AnimationUtils.loadAnimation(ajxRouteMotorResultPage.getContext(), R.anim.tips_showing);
                    loadAnimation.setFillAfter(true);
                    ajxRouteMotorResultPage.p.setVisibility(0);
                    NoDBClickUtil.a((View) ajxRouteMotorResultPage.p, ajxRouteMotorResultPage.s);
                    loadAnimation.setAnimationListener(new AnimationListener() {
                        public final void onAnimationEnd(Animation animation) {
                        }

                        public final void onAnimationRepeat(Animation animation) {
                        }

                        public final void onAnimationStart(Animation animation) {
                            AjxMotorTipsManager c = AjxRouteMotorResultPage.this.q;
                            c.u = true;
                            if (!(c.b == null || c.b.size() == 0 || c.d == null)) {
                                c.d.setVisibility(0);
                                c.i = true;
                                int size = c.b.size() > 3 ? 3 : c.b.size();
                                if (size > 0) {
                                    AnimatorSet animatorSet = new AnimatorSet();
                                    Animator animator = null;
                                    Animator animator2 = null;
                                    Animator animator3 = null;
                                    for (int i = 0; i < size; i++) {
                                        px pxVar = c.b.get(i);
                                        TipsView a2 = c.a(pxVar);
                                        a2.dismissCancelButton();
                                        if ((pxVar.a == 7 && !c.f()) || ((pxVar.a == 1 && !c.e()) || ((pxVar.a == 9 && !c.e()) || pxVar.a == 19))) {
                                            a2.dismissVerticalDivider();
                                        }
                                        LayoutParams layoutParams = new LayoutParams(-2, -2);
                                        if (i == 0) {
                                            layoutParams.topMargin = c.d.getResources().getDimensionPixelOffset(R.dimen.tips_vertical_margin);
                                        } else {
                                            layoutParams.topMargin = (a2.targetHeight * (i - 1)) + c.d.getResources().getDimensionPixelOffset(R.dimen.tips_vertical_margin);
                                        }
                                        c.d.addView(a2, 0, layoutParams);
                                        c.a(pxVar.a, (String) "manu");
                                        AjxMotorTipsManager.d(pxVar.a);
                                        a2.setOnTipClickListener(c);
                                        if (i == 0) {
                                            animator = a2.getTopTipAnimation();
                                        } else if (i == 1) {
                                            animator2 = a2.getTranslateAnimation(300, 100);
                                        } else if (i == 2) {
                                            a2.setAlpha(0.0f);
                                            animator3 = a2.getTranslateAnimation(300, 200);
                                        }
                                    }
                                    if (size == 1) {
                                        animatorSet.play(animator);
                                    } else if (size == 2) {
                                        animatorSet.play(animator).with(animator2);
                                    } else if (size == 3) {
                                        animatorSet.play(animator).with(animator2).with(animator3);
                                    }
                                    animatorSet.start();
                                }
                                ((TextView) c.f.findViewById(R.id.tips_unread_count)).setVisibility(8);
                                c.i = true;
                            }
                        }
                    });
                    ajxRouteMotorResultPage.p.startAnimation(loadAnimation);
                }
            } else if (view.getId() == R.id.tips_bg_mask) {
                AjxRouteMotorResultPage.this.a(true);
            } else {
                if (view.getId() == R.id.title_back_img) {
                    qd.b(AjxRouteMotorResultPage.this.j.j());
                    qd.a(AjxRouteMotorResultPage.this.getContentView(), (AnimatorListener) new AnimatorListener() {
                        public final void onAnimationCancel(Animator animator) {
                        }

                        public final void onAnimationRepeat(Animator animator) {
                        }

                        public final void onAnimationStart(Animator animator) {
                        }

                        public final void onAnimationEnd(Animator animator) {
                            AjxRouteMotorResultPage.this.finish();
                        }
                    });
                }
            }
        }
    };
    mr t = new mr() {
    };
    mq u = new mq() {
        /* JADX WARNING: Removed duplicated region for block: B:17:0x003c  */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x004b  */
        /* JADX WARNING: Removed duplicated region for block: B:22:0x005b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(java.lang.String r4, java.lang.String r5) {
            /*
                r3 = this;
                com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
                r0.<init>()
                int r1 = r4.hashCode()
                r2 = -2136002916(0xffffffff80af2e9c, float:-1.6087932E-38)
                if (r1 == r2) goto L_0x002d
                r2 = 272721754(0x1041675a, float:3.8142158E-29)
                if (r1 == r2) goto L_0x0023
                r2 = 953959764(0x38dc4554, float:1.0503331E-4)
                if (r1 == r2) goto L_0x0019
                goto L_0x0037
            L_0x0019:
                java.lang.String r1 = "carPreview"
                boolean r4 = r4.equals(r1)
                if (r4 == 0) goto L_0x0037
                r4 = 0
                goto L_0x0038
            L_0x0023:
                java.lang.String r1 = "backDefault"
                boolean r4 = r4.equals(r1)
                if (r4 == 0) goto L_0x0037
                r4 = 2
                goto L_0x0038
            L_0x002d:
                java.lang.String r1 = "motorbikeNavi"
                boolean r4 = r4.equals(r1)
                if (r4 == 0) goto L_0x0037
                r4 = 1
                goto L_0x0038
            L_0x0037:
                r4 = -1
            L_0x0038:
                switch(r4) {
                    case 0: goto L_0x005b;
                    case 1: goto L_0x004b;
                    case 2: goto L_0x003c;
                    default: goto L_0x003b;
                }
            L_0x003b:
                goto L_0x0061
            L_0x003c:
                bid r4 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
                if (r4 == 0) goto L_0x0061
                r4.finish()
                java.lang.String r5 = "amap.basemap.action.default_page"
                r4.startPage(r5, r0)
                goto L_0x0061
            L_0x004b:
                java.lang.String r4 = "\n"
                defpackage.tj.a(r4)
                java.lang.String r4 = "motor-startNavi-click_3.1"
                defpackage.tj.a(r4)
                com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage r4 = com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage.this
                defpackage.no.a(r4.getActivity(), r5, (defpackage.no.a) new com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage.AnonymousClass9())
                return
            L_0x005b:
                com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage r4 = com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage.this
                com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage.a(r4, r5)
                return
            L_0x0061:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage.AnonymousClass14.a(java.lang.String, java.lang.String):void");
        }
    };
    RouteEventActionInterface v = new RouteEventActionInterface() {
        public final void showIncidentDetail(String str) {
            int i;
            AMapLog.d("AjxRouteMotorResultPage", "showIncidentDetail---JSON=".concat(String.valueOf(str)));
            if (AjxRouteMotorResultPage.this.isResumed()) {
                if (AjxRouteMotorResultPage.this.q != null && AjxRouteMotorResultPage.this.q.c()) {
                    AjxRouteMotorResultPage.this.q.j();
                }
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    int optInt = jSONObject.optInt("type");
                    int i2 = 0;
                    if (!(optInt == 2 || optInt == 1 || optInt == 9)) {
                        if (optInt != 10) {
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
                                        AjxRouteMotorResultPage.a(AjxRouteMotorResultPage.this, i, jArr, optInt3, optDouble, optDouble2, optInt2, optString);
                                        return;
                                    }
                                }
                                return;
                            } else if (optInt == 4) {
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
                                        AjxRouteMotorResultPage.a(AjxRouteMotorResultPage.this, optInt4, optInt5, optInt6, jSONObject.optString("timeDescription"), jSONObject.optString("roadNameString"), jSONObject.optString("nextRoadNameString"), jArr2, optInt7);
                                        return;
                                    }
                                }
                                return;
                            } else {
                                if (optInt == 8) {
                                    AjxRouteMotorResultPage.c(AjxRouteMotorResultPage.this, str);
                                }
                                return;
                            }
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
                            AjxRouteMotorResultPage.a(AjxRouteMotorResultPage.this, optInt, jArr3, optInt8, optInt9);
                        }
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    };
    SearchAlongCallback w = new SearchAlongCallback() {
        public final void callback(boolean z) {
            if (AjxRouteMotorResultPage.this.K != null) {
                AjxRouteMotorResultPage.this.K.a = z;
            }
        }
    };
    private RelativeLayout x;
    /* access modifiers changed from: private */
    public ModuleRouteDriveResult y;
    private ModuleCommonBusiness z;

    static class a extends Handler {
        public final void handleMessage(Message message) {
        }

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }
    }

    static /* synthetic */ void g() {
    }

    public void finishSelf() {
    }

    public String getAjx3Url() {
        return ModuleRouteDriveResult.URL_MOTOR_ROUTE;
    }

    public long getScene() {
        return 2251799813685248L;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 2251799813685248L;
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

    public bgo getPresenter() {
        return this.b;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        setContentView(R.layout.ajx_route_car_result_map_page);
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().a(getClass(), AgroupScenes.RouteCarResult, getArguments(), false);
        }
    }

    public void onContentViewCreated(View view) {
        this.A = view;
    }

    /* access modifiers changed from: private */
    /* renamed from: i */
    public ps createPresenter() {
        this.b = new ps(this);
        return this.b;
    }

    public void pageCreated() {
        super.pageCreated();
        this.j = new qb(this);
        this.j.a();
        if (getMapView() != null) {
            this.B = getMapView().j(false);
            this.C = getMapView().k(false);
        }
        this.J = findViewById(R.id.mapBottomInteractiveView);
        this.R = this.i.a;
        NoDBClickUtil.a((View) this.R, this.s);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.Y = arguments.getString("bundle_key_from_page");
            this.c = arguments.getBoolean("key_favorites", false);
            Ajx.getInstance().getMemoryStorage("PlanResult").setItem("isFromFavorite", Boolean.valueOf(this.c));
            if (this.c && arguments.containsKey("original_route")) {
                cor cor = (cor) arguments.getObject("original_route");
                if (cor != null) {
                    this.f = cor.f();
                }
            }
            cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
            if (cuh != null) {
                cuh.b().a(!this.c);
            }
            if (arguments.containsKey("voice_process")) {
                this.j.q();
            }
            this.F = arguments.containsKey("voice_process");
            this.d = arguments.getInt("key_source", 0) == 102;
            if (this.d) {
                this.U = arguments.getByteArray("original_route");
                this.b.c = (POI) arguments.getObject("bundle_key_poi_start");
                this.b.d = (POI) arguments.getObject("bundle_key_poi_end");
            }
            this.E = arguments.getBoolean("key_subresult", false);
            if (arguments.containsKey("bundle_key_method_flag") && arguments.getInt("bundle_key_method_flag", 0) == 1 && arguments.containsKey("bundle_key_method")) {
                String string = arguments.getString("bundle_key_method");
                if (!TextUtils.isEmpty(string)) {
                    DriveUtil.putMotorRoutingChoice(string);
                }
            }
        }
        if (this.c) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.route_car_result_map_title, null);
            View findViewById = inflate.findViewById(R.id.route_car_result_map_title_favorite);
            findViewById.setVisibility(0);
            this.W = findViewById.findViewById(R.id.title_back_img);
            NoDBClickUtil.a(this.W, this.s);
            this.j.k();
            this.j.a(inflate);
            this.j.q();
        }
        if (this.F) {
            this.r.post(new Runnable() {
                public final void run() {
                    ViewGroup s = AjxRouteMotorResultPage.this.j.s();
                    if (s != null) {
                        s.bringToFront();
                    }
                }
            });
        }
        this.aa = new pb(this, this.j);
    }

    public final void a(boolean z2, AnimationListener animationListener) {
        if (z2) {
            getContentView().findViewById(R.id.mapTopInteractiveView).setVisibility(4);
            qd.b(getContext(), this.J, animationListener);
            this.j.g();
            return;
        }
        qd.a(getContext(), this.J, animationListener);
        this.j.f();
    }

    public final void a(int i2, int i3) {
        if (this.y != null) {
            this.y.requestJsurfaceAreaSizeChange(i2, i3);
        }
    }

    public void resume() {
        super.resume();
        if (!this.d) {
            awo awo = (awo) defpackage.esb.a.a.a(awo.class);
            if (awo != null) {
                awo.b(9001);
            }
            alu alu = new alu();
            alu.a = 9001;
            this.G = getMapView().a(alu);
            alv alv = new alv();
            alv.a = 9001;
            alv.b = 20;
            alv.c = 16;
            alv.d = 1;
            getMapView().a(alv);
        }
        this.g = false;
        if (this.mAjxView != null) {
            this.mAjxView.setVisibility(0);
        }
        bim.aa().a((biv) new biv() {
            public final void saveSucess() {
                boolean z;
                AjxRouteMotorResultPage ajxRouteMotorResultPage = AjxRouteMotorResultPage.this;
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService == null) {
                    z = false;
                } else {
                    z = iAccountService.a();
                }
                if (!z) {
                    if (ajxRouteMotorResultPage.h == null) {
                        ajxRouteMotorResultPage.a = View.inflate(ajxRouteMotorResultPage.getContext(), R.layout.v4_fromto_car_result_detail_dlg, null);
                        IRouteUI b = ajxRouteMotorResultPage.b.a.b();
                        if (b != null) {
                            b.a(ajxRouteMotorResultPage.a);
                        }
                        ajxRouteMotorResultPage.h = new SyncPopupWindow(ajxRouteMotorResultPage.a);
                    }
                    ajxRouteMotorResultPage.h.show();
                }
            }
        });
        if (this.D) {
            this.D = false;
            IRouteUI b2 = this.b.a.b();
            if (b2 != null) {
                b2.a(this.A);
                this.mAjxView.setLayoutParams(new LayoutParams(-1, -1));
                if (this.F) {
                    LayoutParams layoutParams = new LayoutParams(-1, (int) (Resources.getSystem().getDisplayMetrics().density * 142.0f));
                    layoutParams.addRule(12);
                    this.x = new RelativeLayout(getContext());
                    this.x.setLayoutParams(layoutParams);
                    this.x.setOnTouchListener(this);
                    b2.a((View) this.x);
                }
            }
        } else if (this.q != null) {
            AjxMotorTipsManager ajxMotorTipsManager = this.q;
            if (!(ajxMotorTipsManager.b == null || ajxMotorTipsManager.b.size() == 0 || ajxMotorTipsManager.d == null || !ajxMotorTipsManager.r)) {
                ajxMotorTipsManager.d.setVisibility(0);
            }
        }
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.resumeActivityState();
        }
    }

    public void pause() {
        super.pause();
        if (!(this.b == null || this.b.a() == 102)) {
            a(0.0f);
        }
        if (this.q != null) {
            AjxMotorTipsManager ajxMotorTipsManager = this.q;
            if (ajxMotorTipsManager.h != null) {
                ajxMotorTipsManager.h.setVisibility(8);
            }
        }
        if (this.q != null && this.q.d() > 0) {
            this.q.k();
        }
        if (!this.d) {
            if (this.G != null) {
                alv alv = new alv();
                alv.a = 9001;
                alv.b = this.G.a;
                alv.c = this.G.b;
                alv.d = 1;
                getMapView().a(alv);
            }
            awo awo = (awo) defpackage.esb.a.a.a(awo.class);
            if (awo != null) {
                awo.c(9001);
            }
        }
        this.k = DriveUtil.getMotorRoutingChoice();
        this.l = DriveUtil.isMotorAvoidLimitedPath();
        this.m = DriveUtil.getMotorPlateNum();
        if (this.h != null) {
            this.h.hide();
        }
        bim.aa().a((biv) null);
    }

    public void stop() {
        super.stop();
        if (this.g) {
            this.mAjxView.setVisibility(4);
        }
    }

    public final void a() {
        if (this.q != null) {
            this.q.h();
            this.q.l = null;
        }
    }

    public final void b() {
        if (this.o != null) {
            this.o.setVisibility(8);
            if (this.q != null) {
                this.q.a(0);
            }
            a((int) SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_LENGTH, 0);
        }
    }

    public void destroy() {
        if (this.mAjxView != null) {
            this.mAjxView.onAjxContextCreated(null);
            this.b.a.b().b((View) this.mAjxView);
        }
        if (this.ab != null) {
            this.ab.setMitVuiDialogEventListener(null);
            ModuleVUI.mMitVuiDialogEventCallback = null;
        }
        super.destroy();
        if (getMapView() != null) {
            getMapView().a(this.B, getMapView().l(false), this.C);
        }
        if (this.x != null) {
            this.b.a.b().b((View) this.x);
        }
        if (this.a != null) {
            this.b.a.b().b(this.a);
        }
        if (this.y != null) {
            this.y.setOnTripPoiChangeListener(null);
            this.y.removeSwitchSceneListener(null);
            this.y.setOnRouteStateChangeListener(null);
            this.y.setOnObtainFocusCallBack(null);
            this.y.setJsCommandCallback(null);
            this.y.setOnCalRouteFromData(null);
            this.y.removeRouteEventInterface(null);
            this.y.setGpsButtonActionInterface(null);
            this.y.bindTipsManager(this.q);
        }
        if (this.i != null) {
            this.i.c();
            this.i = null;
        }
        if (this.R != null) {
            NoDBClickUtil.a((View) this.R, (OnClickListener) null);
        }
        if (this.W != null) {
            NoDBClickUtil.a(this.W, (OnClickListener) null);
        }
        if (this.q != null) {
            NoDBClickUtil.a((View) this.p, (OnClickListener) null);
            this.p.clearAnimation();
            a();
            k();
        }
    }

    public boolean backPressed() {
        if (this.aa != null && this.aa.b) {
            this.aa.a();
            return true;
        } else if (this.O != null && isViewLayerShowing(this.O)) {
            dismissViewLayer(this.O);
            this.O = null;
            return true;
        } else if (this.q != null && this.q.c()) {
            a(true);
            return true;
        } else if (this.mAjxView == null || !this.mAjxView.backPressed()) {
            return false;
        } else {
            return true;
        }
    }

    public void onAjxViewCreated(AmapAjxView amapAjxView) {
        amapAjxView.setAttributeListener(new AttributeListener() {
            public final boolean handleAttr(String str, Object obj) {
                if (AjxRouteMotorResultPage.this.g) {
                    return false;
                }
                if ("CAR_FADE_FROM_BOTTOM".equalsIgnoreCase(str)) {
                    Float f = (Float) obj;
                    Stub.getMapWidgetManager().setContainerAlpha(f.floatValue());
                    if (AjxRouteMotorResultPage.this.q != null) {
                        AjxRouteMotorResultPage.this.q.a(f);
                    }
                    if (AjxRouteMotorResultPage.this.R != null) {
                        AjxRouteMotorResultPage.this.R.setAlpha(f.floatValue());
                    }
                    if (AjxRouteMotorResultPage.this.b.a() == 102) {
                        AjxRouteMotorResultPage.a(AjxRouteMotorResultPage.this, f.floatValue());
                    }
                    return true;
                } else if (!"CAR_HEIGHT_TO_TOP".equalsIgnoreCase(str)) {
                    return false;
                } else {
                    if (AjxRouteMotorResultPage.this.b.a() != 102) {
                        AjxRouteMotorResultPage.this.a(((Float) obj).floatValue());
                    }
                    return true;
                }
            }
        });
    }

    public void loadJs() {
        JsRunInfo jsRunInfo = new JsRunInfo(ModuleRouteDriveResult.URL_MOTOR_ROUTE, this.b.b(null, null, null, null, false, false));
        jsRunInfo.setTag("MOTOR_BIKE_MAP_RESULT");
        HashMap hashMap = new HashMap();
        hashMap.put(ModuleRouteDriveResult.AJX_JS_INFO_RUNTIME_ROUTE_TYPE, RouteType.MOTOR);
        jsRunInfo.setRunParams(hashMap);
        this.mAjxView.loadJs(jsRunInfo);
    }

    public static boolean a(String str, String str2) {
        if (!TextUtils.isEmpty(str2) && !str2.equals(str)) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void a(float f2) {
        if (!(this.b.a.b() == null || this.b.a.b() == null)) {
            this.H = this.b.a.b().c();
        }
        if (this.H != null) {
            if (this.I == 0) {
                this.I = this.H.getHeight();
            }
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.H.getLayoutParams();
            double d2 = (double) f2;
            layoutParams.topMargin = -((int) ((d2 - 0.5d) * 3.3333332538604736d * ((double) this.I)));
            if (d2 < 0.5d) {
                layoutParams.topMargin = 0;
            } else if (d2 > 0.8d) {
                layoutParams.topMargin = -this.I;
            }
            layoutParams.bottomMargin = -layoutParams.topMargin;
            this.H.requestLayout();
        }
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        this.y = (ModuleRouteDriveResult) this.mAjxView.getJsModule(ModuleRouteDriveResult.MODULE_NAME);
        this.z = (ModuleCommonBusiness) this.mAjxView.getJsModule(ModuleCommonBusiness.MODULE_NAME);
        if (this.ab == null) {
            this.ab = (ModuleVUI) this.mAjxView.getJsModule(ModuleVUI.MODULE_NAME);
        }
        if (this.z != null) {
            this.z.setMotorResultActivityCallback(this.ad);
        }
        if (this.y != null) {
            this.y.setOnTripPoiChangeListener(this);
            this.y.setOnRouteStateChangeListener(this);
            this.y.setOnObtainFocusCallBack(this.aj);
            this.y.addSwitchSceneListener(this.u);
            this.y.setJsCommandCallback(this.ai);
            this.y.setOnCalRouteFromData(this.ah);
            this.y.addRouteEventActionInterface(this.v);
            this.y.setGpsButtonActionInterface(this.t);
        }
        if (this.ab != null) {
            this.ab.setMitVuiDialogEventListener(this.ae);
        }
    }

    public final void a(String str) {
        if (this.y != null) {
            d();
            this.y.requestCarRoute(str);
            if (!(this.p == null || this.p.getAnimation() == null || this.p.getAnimation().hasEnded())) {
                this.p.getAnimation().cancel();
            }
            a();
            b();
        }
    }

    public void onMidPOIChanged(List<POI> list) {
        a();
        b();
    }

    public void onEndPOIChanged(POI poi, int i2) {
        if (this.b != null) {
            poi.getPoiExtra().put("key_end_poi_source_type", Integer.valueOf(i2));
            this.b.a(poi);
        }
        a();
        b();
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
        if (this.e != null) {
            return this.e.j;
        }
        return null;
    }

    public View getMapSuspendView() {
        this.i = new pc(this, getContext());
        this.i.b = this.af;
        this.i.b();
        return this.i.getSuspendView();
    }

    private void j() {
        if (this.V != null) {
            this.V.removeMessages(1008);
            this.V.sendEmptyMessageDelayed(1008, 10000);
        }
    }

    /* access modifiers changed from: private */
    public void a(final boolean z2) {
        if (this.q != null) {
            if (this.P == null) {
                this.P = AnimationUtils.loadAnimation(getContext(), R.anim.tips_hiding);
            } else if (!this.P.hasEnded()) {
                return;
            }
            this.P.setFillAfter(true);
            this.P.setAnimationListener(new AnimationListener() {
                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                    AjxMotorTipsManager c = AjxRouteMotorResultPage.this.q;
                    if (!(c.b == null || c.b.size() == 0 || c.d == null)) {
                        c.i();
                        c.u = false;
                    }
                }

                public final void onAnimationEnd(Animation animation) {
                    if (!(!AjxRouteMotorResultPage.this.isAlive() || AjxRouteMotorResultPage.this.p == null || AjxRouteMotorResultPage.this.p.getAnimation() == null)) {
                        AjxRouteMotorResultPage.this.p.clearAnimation();
                        AjxRouteMotorResultPage.this.k();
                        if (AjxRouteMotorResultPage.this.q != null) {
                            if (z2) {
                                AjxRouteMotorResultPage.this.q.j();
                                return;
                            }
                            AjxRouteMotorResultPage.this.q.k();
                        }
                    }
                }
            });
            this.p.startAnimation(this.P);
        }
    }

    /* access modifiers changed from: private */
    public void k() {
        this.p.setVisibility(8);
    }

    public void onRouteStateChanged(int i2, Object... objArr) {
        String str;
        int i3 = i2;
        this.X = i3;
        if (i3 == 0) {
            this.b.g = DriveUtil.getMotorRoutingChoice();
            this.e = objArr[0];
            this.e.g = getStartPOI();
            this.e.h = getEndPOI();
            this.e.i = getMidPOIs();
            if (this.e != null && this.e.b()) {
                ps psVar = this.b;
                if (!psVar.b.c && !psVar.h) {
                    ahm.c(new Runnable() {
                        public final void run() {
                            if (ps.this.c != null && ps.this.d != null) {
                                chk.a(ps.this.c, null, ps.this.d, RouteType.MOTOR);
                            }
                        }
                    });
                }
            }
            j();
            if (this.q == null) {
                this.q = new AjxMotorTipsManager(getContext(), this.j.b(), this.c);
                if (this.y != null) {
                    this.y.bindTipsManager(this.q);
                }
                this.q.t = RouteType.MOTOR.getValue();
                this.p = this.q.g;
            }
            if (!this.d && !this.c) {
                this.q.l = this.ac;
                AjxMotorTipsManager ajxMotorTipsManager = this.q;
                ph phVar = this.e;
                if (!(phVar == null || phVar.f.get(0) == null)) {
                    ajxMotorTipsManager.m = phVar;
                    ajxMotorTipsManager.n = phVar.a(0);
                    if (ajxMotorTipsManager.m != null) {
                        ajxMotorTipsManager.n = ajxMotorTipsManager.m.a(ajxMotorTipsManager.m.e);
                        sq sqVar = ajxMotorTipsManager.n.o;
                        if (sqVar != null) {
                            int i4 = sqVar.e;
                            if (i4 != -1) {
                                TipType a2 = RouteCarResultTipUtil.a(i4);
                                if (TipType.INVALID_TYPE != a2) {
                                    px pxVar = new px();
                                    if (a2 == TipType.RESTRICT_ALREADY_AVOID_RESTRICT_AREA) {
                                        pxVar.a = 1;
                                        pxVar.d = 2.0f;
                                        if (sqVar.h.length > 0) {
                                            pxVar.e = sqVar.h;
                                            pxVar.b = ajxMotorTipsManager.j.getString(R.string.tip_carnum_avoid_restrict_title);
                                        } else {
                                            pxVar.b = AjxMotorTipsManager.a(sqVar.a, ajxMotorTipsManager.j.getString(R.string.tip_already_avoid_restriction_default_title));
                                        }
                                    } else if (a2 == TipType.RESTRICT_OTHER_PLACE_OPEN_RESTRICT_SWITCH && DriveUtil.needShowMotorPlateOpenAvoidLimitedNotice()) {
                                        pxVar.a = 4;
                                        pxVar.d = 3.2f;
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(ajxMotorTipsManager.j.getString(R.string.tip_avoid_restrict_title_prefix));
                                        sb.append(sqVar.f);
                                        sb.append("？");
                                        pxVar.b = AjxMotorTipsManager.a(sb.toString(), ajxMotorTipsManager.j.getString(R.string.tip_remind_restriction_default_title));
                                    } else if (a2 == TipType.RESTRICT_OTHER_PLACE_SET_PLATE && DriveUtil.needShowMotorPlateSetting()) {
                                        pxVar.a = 6;
                                        pxVar.d = 3.2f;
                                        String str2 = sqVar.a;
                                        int i5 = R.string.tip_plate_default_title;
                                        if (TextUtils.isEmpty(str2)) {
                                            str2 = ajxMotorTipsManager.j.getString(i5);
                                        }
                                        pxVar.b = str2;
                                    } else if (a2.mPriority >= TipType.RESTRICT_START_POI_IN_RESTRICT_AREA.mPriority && a2.mPriority <= TipType.RESTRICT_ACROSS_RESTRICT_AREA.mPriority) {
                                        pxVar.a = 0;
                                        pxVar.d = 3.1f;
                                        if (sqVar.h.length > 0) {
                                            pxVar.e = sqVar.h;
                                            pxVar.b = ajxMotorTipsManager.j.getString(R.string.tip_carnum_unavoid_restrict_title);
                                        } else {
                                            pxVar.b = ajxMotorTipsManager.j.getString(R.string.tip_unavoid_restrict_title);
                                        }
                                        pxVar.c = sqVar.a;
                                    } else if (a2 == TipType.RESTRICT_SOON_EFFECT_AVOID_RESTRICT_AREA) {
                                        pxVar.a = 8;
                                        pxVar.d = 2.2f;
                                        pxVar.b = sqVar.a;
                                    } else if (a2 == TipType.RESTRICT_SOON_END_ACROSS_RESTRICT_AREA) {
                                        pxVar.a = 9;
                                        pxVar.d = 2.1f;
                                        pxVar.b = sqVar.a;
                                    } else if (a2 == TipType.RESTRICT_ETD_UNABLE_AVOID_RESTRICT) {
                                        pxVar.a = 22;
                                        pxVar.d = 3.06f;
                                        pxVar.b = ajxMotorTipsManager.j.getString(R.string.tip_unavoid_restrict_title);
                                        pxVar.c = sqVar.a;
                                    } else if (a2 == TipType.RESTRICT_ETD_AVOID_RESTRICT) {
                                        pxVar.a = 23;
                                        pxVar.d = 2.05f;
                                        pxVar.b = ajxMotorTipsManager.j.getString(R.string.tip_already_avoid_restriction_title);
                                        pxVar.c = sqVar.a;
                                    }
                                    if (AjxMotorTipsManager.a(a2)) {
                                        ajxMotorTipsManager.b.add(pxVar);
                                    }
                                }
                            }
                        }
                        RouteIncident a3 = AjxMotorTipsManager.a(ajxMotorTipsManager.n.k, ajxMotorTipsManager.n.l);
                        if (a3 != null) {
                            TipType b2 = RouteCarResultTipUtil.b(a3.tipsType);
                            if (TipType.INVALID_TYPE != b2) {
                                px pxVar2 = new px();
                                if (b2.mPriority >= TipType.INCIDENT_UNAVOIDABLE_INCIDENT_AT_END.mPriority && b2.mPriority <= TipType.INCIDENT_UNAVOIDABLE_INCIDENT_AT_START.mPriority) {
                                    pxVar2.a = 2;
                                    pxVar2.d = 3.0f;
                                } else if (b2 == TipType.INCIDENT_AVOIDABLE_INCIDENT) {
                                    pxVar2.a = 3;
                                    pxVar2.d = 1.2f;
                                }
                                pxVar2.b = AjxMotorTipsManager.a(a3);
                                ajxMotorTipsManager.b.add(pxVar2);
                                ajxMotorTipsManager.p = a3;
                            }
                        }
                        List<sn> list = ajxMotorTipsManager.n.j;
                        if (!(list == null || list.size() == 0)) {
                            T t2 = (sn) list.get(0);
                            if (t2 != null && !TextUtils.isEmpty(t2.o) && !TextUtils.isEmpty(t2.p)) {
                                px<sn> pxVar3 = new px<>(5);
                                pxVar3.b = t2.o;
                                pxVar3.c = t2.p;
                                pxVar3.d = 1.1f;
                                pxVar3.f = t2;
                                ajxMotorTipsManager.s = pxVar3;
                                ajxMotorTipsManager.b.add(pxVar3);
                            }
                        }
                        if ((!ajxMotorTipsManager.n.d) && DriveSpUtil.getSearchRouteInNetMode(AMapAppGlobal.getApplication())) {
                            px pxVar4 = new px(14);
                            pxVar4.d = 0.9f;
                            if (DriveUtil.isAvoidLimitedPath()) {
                                str = ajxMotorTipsManager.j.getString(R.string.tip_type_remind_offline_online_desc);
                            } else {
                                str = ajxMotorTipsManager.j.getString(R.string.tip_type_remind_offline_no_avoid_desc);
                            }
                            pxVar4.b = str;
                            ajxMotorTipsManager.b.add(pxVar4);
                        }
                        if (ajxMotorTipsManager.b.isEmpty() && ajxMotorTipsManager.n != null && ajxMotorTipsManager.n.p != null && !TextUtils.isEmpty(ajxMotorTipsManager.n.p.tipInfo)) {
                            px pxVar5 = new px(7);
                            pxVar5.d = 1.0f;
                            pxVar5.b = ajxMotorTipsManager.j.getString(R.string.tip_offline_remind);
                            ajxMotorTipsManager.b.add(pxVar5);
                        }
                        ajxMotorTipsManager.o = ajxMotorTipsManager.n.m;
                        if (ajxMotorTipsManager.o != null) {
                            StringBuilder sb2 = new StringBuilder();
                            if ((ajxMotorTipsManager.o == null || ajxMotorTipsManager.o.size() <= 0 || ajxMotorTipsManager.o.get(0) == null) ? false : true) {
                                sb2.append("禁行");
                            }
                            String format = String.format(ajxMotorTipsManager.j.getString(R.string.tip_unavoid_forbid_title), new Object[]{sb2.toString()});
                            px pxVar6 = new px(10);
                            pxVar6.b = format;
                            pxVar6.c = "";
                            pxVar6.d = 3.05f;
                            ajxMotorTipsManager.b.add(pxVar6);
                        }
                        LinkedList linkedList = new LinkedList();
                        if (ajxMotorTipsManager.n.k != null) {
                            linkedList.addAll(ajxMotorTipsManager.n.k);
                        }
                        if (ajxMotorTipsManager.n.l != null) {
                            linkedList.addAll(ajxMotorTipsManager.n.l);
                        }
                        RouteIncident a4 = AjxMotorTipsManager.a((List<RouteIncident>) linkedList);
                        if (a4 != null) {
                            TipType b3 = RouteCarResultTipUtil.b(a4.tipsType);
                            if (TipType.INVALID_TYPE != b3) {
                                px pxVar7 = new px();
                                if (b3 == TipType.SCHEDULE_ROUTE) {
                                    pxVar7.a = 20;
                                    pxVar7.d = 1.9f;
                                } else if (b3 == TipType.SCHEDULE_END) {
                                    pxVar7.a = 21;
                                    pxVar7.d = 1.8f;
                                }
                                pxVar7.b = AjxMotorTipsManager.a(a4);
                                ajxMotorTipsManager.b.add(pxVar7);
                                ajxMotorTipsManager.q = a4;
                            }
                        }
                        if (ajxMotorTipsManager.c == null) {
                            ajxMotorTipsManager.c = new TipPriorityComparator(0);
                        }
                        Collections.sort(ajxMotorTipsManager.b, ajxMotorTipsManager.c);
                        if (bno.a && ajxMotorTipsManager.n != null) {
                            ku a5 = ku.a();
                            String str3 = AjxMotorTipsManager.a;
                            StringBuilder sb3 = new StringBuilder("parseHolidayFreeTipInfo  mNavigationPath.mIsHolidayFree:");
                            sb3.append(ajxMotorTipsManager.n.r);
                            a5.c(str3, sb3.toString());
                        }
                        if (ajxMotorTipsManager.n != null && ajxMotorTipsManager.n.r) {
                            px pxVar8 = new px(19);
                            pxVar8.d = 1.05f;
                            pxVar8.b = ajxMotorTipsManager.j.getString(R.string.tip_holiday_free);
                            ajxMotorTipsManager.b.add(pxVar8);
                        }
                    }
                }
                if (this.q.d() <= 0) {
                    pb.a(8);
                } else {
                    pb.a(4);
                }
                this.q.f = this.R;
                if (isStarted()) {
                    AjxMotorTipsManager ajxMotorTipsManager2 = this.q;
                    long j2 = 11000;
                    if (!(ajxMotorTipsManager2.b == null || ajxMotorTipsManager2.b.size() == 0 || ajxMotorTipsManager2.d == null)) {
                        ajxMotorTipsManager2.i = false;
                        px pxVar9 = ajxMotorTipsManager2.b.get(0);
                        float f2 = pxVar9.d;
                        if ((f2 >= 2.0f && f2 <= 3.2f && f2 != 3.0f) || f2 == 0.9f || f2 == 1.15f || f2 == 1.05f) {
                            j2 = 21000;
                        }
                        long j3 = j2;
                        ajxMotorTipsManager2.e = ajxMotorTipsManager2.a(pxVar9);
                        ajxMotorTipsManager2.e.setOnTipClickListener(ajxMotorTipsManager2);
                        ajxMotorTipsManager2.d.addView(ajxMotorTipsManager2.e);
                        AjxMotorTipsManager.a(ajxMotorTipsManager2.e);
                        if (j3 > 0) {
                            ViewGroup viewGroup = ajxMotorTipsManager2.d;
                            TipsView tipsView = ajxMotorTipsManager2.e;
                            ajxMotorTipsManager2.k = new rb();
                            ajxMotorTipsManager2.k.a(tipsView.getCountDownView(), viewGroup.getResources().getString(R.string.tip_cancel), "", j3, new b() {
                                public final void a() {
                                    AjxMotorTipsManager.this.n();
                                    AjxMotorTipsManager.this.o();
                                    AjxMotorTipsManager.this.r = false;
                                }
                            });
                            ajxMotorTipsManager2.r = true;
                        }
                        ajxMotorTipsManager2.d.setVisibility(0);
                        ajxMotorTipsManager2.a(pxVar9.a, (String) "auto");
                        AjxMotorTipsManager.d(pxVar9.a);
                        int i6 = pxVar9.a;
                        if (i6 == 20 || i6 == 21) {
                            AjxMotorTipsManager.a((String) "B009", (JSONObject) null);
                        }
                        if (pxVar9.a == 6) {
                            AjxMotorTipsManager.a((String) "B005", (JSONObject) null);
                        }
                        if (ajxMotorTipsManager2.l != null) {
                            ajxMotorTipsManager2.l.a(pxVar9.a);
                        }
                    }
                } else {
                    this.q.j();
                }
            }
            if (this.q != null && !this.c && !this.d && !this.E && this.j.b() != null) {
                this.q.b();
            }
            if (!(this.e != null && this.b.h) && !m() && NetworkReachability.b()) {
                POI c2 = this.b.c();
                if (DriveUtil.isNeedSearchCarScene(c2)) {
                    AnonymousClass10 r6 = new op() {
                        public final void a(oo ooVar) {
                            AjxRouteMotorResultPage.a(AjxRouteMotorResultPage.this, ooVar);
                        }
                    };
                    NaviinfoRequest naviinfoRequest = new NaviinfoRequest();
                    naviinfoRequest.b = c2.getId();
                    naviinfoRequest.c = DriveUtil.getSearchCarSceneParam(c2);
                    PoiRequestHolder.getInstance().sendNaviinfo(naviinfoRequest, new CarSceneSearchAosCallback(c2, r6));
                }
            }
            if (!(!this.T || this.e == null || this.e.h == null || this.e.h.getPoiExtra() == null || !this.e.h.getPoiExtra().containsKey("sub_poi_name"))) {
                this.T = false;
                String name = this.e.h.getName();
                Serializable serializable = this.e.h.getPoiExtra().get("main_poi");
                if (serializable != null && (serializable instanceof POI)) {
                    name = ((POI) serializable).getName();
                }
                StringBuilder sb4 = new StringBuilder("目的地已为您设置为\n");
                sb4.append(name);
                sb4.append(Token.SEPARATOR);
                sb4.append(this.e.h.getPoiExtra().get("sub_poi_name"));
                ToastHelper.showToast(sb4.toString());
            }
            if (c()) {
                new re();
                if (re.a()) {
                    ToastHelper.showToast(getString(R.string.offline_message_tbt_success));
                } else {
                    ToastHelper.showToast(getString(R.string.offline_message_tbt_success_first));
                }
            }
            b(true);
        } else if (2 == i3) {
            d();
            if (!(this.p == null || this.p.getAnimation() == null || this.p.getAnimation().hasEnded())) {
                this.p.getAnimation().cancel();
            }
            a();
            b();
        } else if (1 == i3) {
            b(false);
        }
        Object[] objArr2 = new Object[3];
        if (i3 == 0) {
            objArr2[0] = Boolean.valueOf(m());
            objArr2[1] = Boolean.valueOf(c());
            StringBuilder sb5 = new StringBuilder("updateSuspendIcon---isOffline=");
            sb5.append(objArr2[1]);
            AMapLog.d("AjxRouteMotorResultPage", sb5.toString());
            objArr2[2] = Integer.valueOf(l());
        }
        pb pbVar = this.aa;
        boolean c3 = c();
        String choiceString = DriveUtil.getChoiceString(DriveUtil.getMotorRoutingChoice(), 11);
        BaseMapWidgetPresenter baseMapWidgetPresenter = (BaseMapWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.PATHPREFERENCEANDSCALE);
        if (baseMapWidgetPresenter != null) {
            IMapWidget widget = baseMapWidgetPresenter.getWidget();
            if (widget instanceof RecommendAndScaleWidget) {
                pbVar.g = ((RecommendAndScaleWidget) widget).getRecommendView();
            }
            if (pbVar.g != null) {
                TextView textView = (TextView) pbVar.g.findViewById(R.id.recommend_tv);
                if (textView != null) {
                    textView.setText(choiceString);
                    if ((c3 || !agr.b(pbVar.d)) && !TextUtils.isEmpty(choiceString) && choiceString.contains(pbVar.d.getResources().getString(R.string.car_method_no_block))) {
                        textView.setTextColor(textView.getResources().getColor(R.color.f_c_2_a));
                    } else {
                        textView.setTextColor(textView.getResources().getColor(R.color.f_c_2));
                    }
                }
                NoDBClickUtil.a(pbVar.g, (OnClickListener) new OnClickListener() {
                    public final void onClick(View view) {
                        pb pbVar = pb.this;
                        if (!pbVar.b) {
                            pbVar.c = new DriveRecommendView(pbVar.d, new OnClickListener() {
                                public final void onClick(View view) {
                                    pb.this.a();
                                }
                            }, 11);
                            pbVar.c.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                            pbVar.c.setIsOffline(pbVar.a.c());
                            pbVar.c.updateViewStatus();
                            int[] iArr = new int[2];
                            view.getLocationOnScreen(iArr);
                            agn.a(view.getContext(), 10.0f);
                            int height = iArr[1] + view.getHeight() + agn.a(view.getContext(), 10.0f);
                            View findViewById = ((Activity) pbVar.d).findViewById(16908290);
                            if (findViewById != null) {
                                height = findViewById.getHeight() - height;
                            }
                            pbVar.c.setBottomDistance(height);
                            IRouteUI b = pbVar.e.b();
                            if (b != null) {
                                if (pbVar.f == null) {
                                    pbVar.f = b.a();
                                }
                                b.a(new ContainerType[]{ContainerType.FLOW_VIEW, ContainerType.HEAD_VIEW, ContainerType.CONTAINER_VIEW});
                                b.a((View) pbVar.c);
                                pbVar.c.showRecommendViewAnim();
                                pbVar.b = true;
                            }
                        }
                    }
                });
            }
        }
    }

    public void onRouteFocusIndexChanged(int i2) {
        if (this.e != null) {
            this.e.e = i2;
        }
    }

    private int l() {
        if (this.e == null || this.e.a() == null || this.e.a().q == null || this.e.a().q.b() == null) {
            return 0;
        }
        return this.e.a().q.b().size();
    }

    private boolean m() {
        if (this.e != null) {
            pg a2 = this.e.a();
            if (a2 != null) {
                StringBuilder sb = new StringBuilder("isLongScene length==");
                sb.append(a2.c);
                AMapLog.d("AjxRouteMotorResultPage", sb.toString());
                if (a2.c < 100000 || l() <= 1) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public final boolean c() {
        if (this.e == null || this.e.f == null || this.e.f.get(0) == null || this.e.f.get(0).d) {
            return false;
        }
        return true;
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
        if (str != null && (str.equals("B026") || str.endsWith("B030"))) {
            return;
        }
        if (jSONObject == null) {
            LogManager.actionLogV2("P00435", str);
        } else {
            LogManager.actionLogV2("P00435", str, jSONObject);
        }
    }

    private void b(boolean z2) {
        PageBundle arguments = getArguments();
        int i2 = -1;
        if (arguments != null) {
            i2 = arguments.getInt("bundle_key_token", -1);
        }
        if (defpackage.eqc.a.a.b) {
            if (z2) {
                d.a.a(i2, 10000, (String) null);
            } else {
                d.a.a(i2, (int) SDKFactory.getCoreType, (String) null);
            }
            defpackage.eqc.a.a.b = false;
        }
    }

    public static void d() {
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().f();
        }
    }

    static /* synthetic */ void f(AjxRouteMotorResultPage ajxRouteMotorResultPage) {
        if (ajxRouteMotorResultPage.o != null && ajxRouteMotorResultPage.o.getVisibility() == 0) {
            ajxRouteMotorResultPage.o.startAnimation(AnimationUtils.loadAnimation(ajxRouteMotorResultPage.getContext(), R.anim.tips_hiding));
        }
    }

    static /* synthetic */ void a(AjxRouteMotorResultPage ajxRouteMotorResultPage, final int i2, final int i3) {
        if (ajxRouteMotorResultPage.q != null && ajxRouteMotorResultPage.q.c()) {
            ajxRouteMotorResultPage.a(false);
        }
        if (ajxRouteMotorResultPage.q != null) {
            ajxRouteMotorResultPage.q.a();
        }
        ajxRouteMotorResultPage.a(true, (AnimationListener) new AnimationListener() {
            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteMotorResultPage.this.isAlive() && AjxRouteMotorResultPage.this.e != null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("data_type", i2);
                    pageBundle.putObject(RouteItem.ROUTE_DATA, AjxRouteMotorResultPage.this.e);
                    pageBundle.putObject("event_id", Integer.valueOf(i3));
                    if (AjxRouteMotorResultPage.this.y != null) {
                        pageBundle.putInt("focusIndex", AjxRouteMotorResultPage.this.y.getFocusIndex());
                    }
                    pageBundle.putLongArray("result_id", AjxRouteMotorResultPage.this.e.a);
                    AjxRouteMotorResultPage.this.startPageForResult(AjxRouteMotorResultEventDetailPage.class, pageBundle, 150);
                }
            }
        });
    }

    static /* synthetic */ void g(AjxRouteMotorResultPage ajxRouteMotorResultPage) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("from", 3);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00434", "D001", jSONObject);
        ajxRouteMotorResultPage.n = DriveUtil.getMotorInfo();
        ro.c();
    }

    static /* synthetic */ void h(AjxRouteMotorResultPage ajxRouteMotorResultPage) {
        if (!TextUtils.isEmpty(DriveUtil.getMotorPlateNum())) {
            StringBuilder sb = new StringBuilder("onCheckedChange openVehiclePlate isAvoid: ");
            sb.append(DriveUtil.isMotorAvoidLimitedPath());
            AMapLog.d("Daniel-27", sb.toString());
            ro.a(1);
            ajxRouteMotorResultPage.b.b();
            return;
        }
        ToastHelper.showToast(ajxRouteMotorResultPage.getString(R.string.car_plate_empty));
    }

    static /* synthetic */ void i(AjxRouteMotorResultPage ajxRouteMotorResultPage) {
        if (ajxRouteMotorResultPage.e.a(ajxRouteMotorResultPage.e.e).p.type == 1) {
            ajxRouteMotorResultPage.b.a(null, null, null, null, false, true);
        }
    }

    static /* synthetic */ void a(AjxRouteMotorResultPage ajxRouteMotorResultPage, int i2, int i3, int i4, String str, String str2, String str3, long[] jArr, int i5) {
        AjxRouteMotorResultPage ajxRouteMotorResultPage2 = ajxRouteMotorResultPage;
        if (ajxRouteMotorResultPage2.q != null && ajxRouteMotorResultPage2.q.c()) {
            ajxRouteMotorResultPage2.a(false);
        }
        if (ajxRouteMotorResultPage2.q != null) {
            ajxRouteMotorResultPage2.q.a();
        }
        final int i6 = i2;
        final int i7 = i3;
        final int i8 = i4;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final int i9 = i5;
        final long[] jArr2 = jArr;
        AnonymousClass26 r0 = new AnimationListener() {
            final /* synthetic */ int a = 4;

            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteMotorResultPage.this.isAlive() && AjxRouteMotorResultPage.this.e != null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("data_type", this.a);
                    pageBundle.putInt("forbiddenId", i6);
                    pageBundle.putInt("forbiddenType", i7);
                    pageBundle.putInt("vehicleType", i8);
                    pageBundle.putString("timeDescription", str4);
                    pageBundle.putString("roadNameString", str5);
                    pageBundle.putString("nextRoadNameString", str6);
                    pageBundle.putInt("focusIndex", i9);
                    pageBundle.putLongArray("result_id", jArr2);
                    AjxRouteMotorResultPage.this.startPageForResult(AjxRouteMotorResultEventDetailPage.class, pageBundle, 150);
                }
            }
        };
        ajxRouteMotorResultPage2.a(true, (AnimationListener) r0);
    }

    static /* synthetic */ void e() {
        DriveUtil.setMotorPlateSettingShowCount(DriveUtil.getMotorPlateSettingShowCount() + 1);
        DriveUtil.setMotorPlateLastSettingTime(System.currentTimeMillis());
    }

    static /* synthetic */ void f() {
        DriveUtil.setMotorPlateOpenAvoidLimitedNoticeCount(DriveUtil.getMotorPlateOpenAvoidLimitedNoticeCount() + 1);
        DriveUtil.setMotorPlateOpenAvoidLimitedLastNoticeTime(System.currentTimeMillis());
    }

    static /* synthetic */ void a(AjxRouteMotorResultPage ajxRouteMotorResultPage, float f2) {
        if (ajxRouteMotorResultPage.H == null && ajxRouteMotorResultPage.b.a.b() != null) {
            ajxRouteMotorResultPage.H = ajxRouteMotorResultPage.b.a.b().b();
        }
        if (ajxRouteMotorResultPage.H != null) {
            ajxRouteMotorResultPage.H.setAlpha(f2);
        }
    }

    static /* synthetic */ void q(AjxRouteMotorResultPage ajxRouteMotorResultPage) {
        if (!ajxRouteMotorResultPage.c()) {
            if (!ajxRouteMotorResultPage.V.hasMessages(1008)) {
                DriveUtil.refreshTraffic(ajxRouteMotorResultPage.getMapView());
                ajxRouteMotorResultPage.b.b();
                ajxRouteMotorResultPage.j();
                a((String) "B003", (String) "type", (String) "0");
                return;
            }
            ToastHelper.showLongToast(ajxRouteMotorResultPage.getResources().getString(R.string.route_car_toast_refresh_route));
            a((String) "B003", (String) "type", (String) "1");
        }
    }

    static /* synthetic */ void d(AjxRouteMotorResultPage ajxRouteMotorResultPage, boolean z2) {
        ajxRouteMotorResultPage.y.setWeatherSwitchState(z2);
        ajxRouteMotorResultPage.y.requestJsSuspendClickEvent(1008, z2);
    }

    static /* synthetic */ void a(AjxRouteMotorResultPage ajxRouteMotorResultPage, oo ooVar) {
        if (!ajxRouteMotorResultPage.d && ooVar != null && ooVar.a()) {
            if (ajxRouteMotorResultPage.o == null) {
                ajxRouteMotorResultPage.o = (ViewGroup) ajxRouteMotorResultPage.getContentView().findViewById(R.id.viewstub_car_scene_layout);
                ajxRouteMotorResultPage.Q = (CarSceneTip) ajxRouteMotorResultPage.o.findViewById(R.id.route_carscenetip);
            }
            ajxRouteMotorResultPage.o.setVisibility(0);
            if (ajxRouteMotorResultPage.Q != null) {
                try {
                    ajxRouteMotorResultPage.Q.setData(ooVar);
                    ajxRouteMotorResultPage.Q.setVisibility(0);
                    ajxRouteMotorResultPage.Q.setOnTipClickListener(new com.amap.bundle.drive.result.autonavisearchmanager.view.CarSceneTip.a() {
                        public final void a(defpackage.oo.a aVar) {
                            AjxRouteMotorResultPage.this.a((int) SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_LENGTH, 0);
                            if (AjxRouteMotorResultPage.this.q != null) {
                                AjxRouteMotorResultPage.this.q.a(0);
                            }
                            if (aVar != null) {
                                AjxRouteMotorResultPage.this.T = true;
                                POI c = AjxRouteMotorResultPage.this.b.c();
                                if (aVar.a == 101) {
                                    ArrayList arrayList = new ArrayList();
                                    POI poi = aVar.d;
                                    if (poi.getEntranceList() != null && poi.getEntranceList().size() > 0) {
                                        arrayList.addAll(poi.getEntranceList());
                                    }
                                    POI createPOI = POIFactory.createPOI(poi.getName(), poi.getPoint());
                                    createPOI.setId(poi.getId());
                                    createPOI.setPid(c.getId());
                                    createPOI.setType(poi.getType());
                                    createPOI.getPoiExtra().put("sub_poi_name", aVar.c);
                                    createPOI.getPoiExtra().put("main_poi", c);
                                    createPOI.setEntranceList(arrayList);
                                    createPOI.getPoiExtra().put("scene_poi", Boolean.TRUE);
                                    createPOI.setEndPoiExtension(poi.getEndPoiExtension());
                                    createPOI.setTransparent(poi.getTransparent());
                                    AjxRouteMotorResultPage.this.b.a(createPOI);
                                    AjxRouteMotorResultPage.this.b.a(createPOI, null, null, null, true, false);
                                    return;
                                }
                                if (c != null) {
                                    ArrayList arrayList2 = (ArrayList) aVar.b;
                                    POI createPOI2 = POIFactory.createPOI(c.getName(), c.getPoint());
                                    createPOI2.setId(c.getId());
                                    createPOI2.setType(c.getType());
                                    createPOI2.getPoiExtra().put("build_type", Integer.valueOf(0));
                                    createPOI2.getPoiExtra().put("is_car_scene_request", Boolean.TRUE);
                                    createPOI2.getPoiExtra().put("sub_poi_name", aVar.c);
                                    createPOI2.getPoiExtra().put("main_poi", c);
                                    createPOI2.getPoiExtra().put("build_type_train_station_entrance_exit_poies", arrayList2);
                                    createPOI2.getPoiExtra().put("scene_poi", Boolean.TRUE);
                                    createPOI2.setEndPoiExtension(c.getEndPoiExtension());
                                    createPOI2.setTransparent(c.getTransparent());
                                    AjxRouteMotorResultPage.this.b.a(createPOI2);
                                    AjxRouteMotorResultPage.this.b.a(createPOI2, c, arrayList2, null, true, false);
                                }
                            }
                        }
                    });
                    qd.a(ajxRouteMotorResultPage.o);
                    ajxRouteMotorResultPage.a((int) SecExceptionCode.SEC_ERROE_OPENSDK_DECODE_FAILED, UIUtils.dip2px(44, ajxRouteMotorResultPage.Q.getContext()));
                    if (ajxRouteMotorResultPage.q != null) {
                        ajxRouteMotorResultPage.q.a(UIUtils.dip2px(44, ajxRouteMotorResultPage.Q.getContext()));
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    static /* synthetic */ void a(AjxRouteMotorResultPage ajxRouteMotorResultPage, String str) {
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(str)) {
            pageBundle.putString("jsData", str);
        }
        pageBundle.putString("url", ModuleRouteDriveResult.MOTOR_PREVIEW);
        pageBundle.putBoolean("is_from_favorite", ajxRouteMotorResultPage.c);
        pageBundle.putBoolean("is_from_etrip", ajxRouteMotorResultPage.d);
        pageBundle.putInt("route_car_type_key", 11);
        pageBundle.putObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ, ajxRouteMotorResultPage.e);
        ajxRouteMotorResultPage.startPageForResult(AjxRouteMotorResultBrowserPage.class, pageBundle, 200);
    }

    static /* synthetic */ void a(AjxRouteMotorResultPage ajxRouteMotorResultPage, int i2, long[] jArr, int i3, int i4) {
        if (ajxRouteMotorResultPage.q != null && ajxRouteMotorResultPage.q.c()) {
            ajxRouteMotorResultPage.a(false);
        }
        if (ajxRouteMotorResultPage.q != null) {
            ajxRouteMotorResultPage.q.a();
        }
        final int i5 = i2;
        final int i6 = i3;
        final int i7 = i4;
        final long[] jArr2 = jArr;
        AnonymousClass24 r1 = new AnimationListener() {
            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteMotorResultPage.this.isAlive() && AjxRouteMotorResultPage.this.e != null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("data_type", i5);
                    pageBundle.putInt("event_id", i6);
                    pageBundle.putInt("focusIndex", i7);
                    pageBundle.putLongArray("result_id", jArr2);
                    pageBundle.putObject(RouteItem.ROUTE_DATA, AjxRouteMotorResultPage.this.e);
                    AjxRouteMotorResultPage.this.startPageForResult(AjxRouteMotorResultEventDetailPage.class, pageBundle, 150);
                }
            }
        };
        ajxRouteMotorResultPage.a(true, (AnimationListener) r1);
    }

    static /* synthetic */ void a(AjxRouteMotorResultPage ajxRouteMotorResultPage, int i2, long[] jArr, int i3, double d2, double d3, int i4, String str) {
        AjxRouteMotorResultPage ajxRouteMotorResultPage2 = ajxRouteMotorResultPage;
        if (ajxRouteMotorResultPage2.q != null && ajxRouteMotorResultPage2.q.c()) {
            ajxRouteMotorResultPage2.a(false);
        }
        if (ajxRouteMotorResultPage2.q != null) {
            ajxRouteMotorResultPage2.q.a();
        }
        final int i5 = i3;
        final long[] jArr2 = jArr;
        final double d4 = d2;
        final double d5 = d3;
        final int i6 = i4;
        final String str2 = str;
        final int i7 = i2;
        AnonymousClass25 r0 = new AnimationListener() {
            final /* synthetic */ int a = 3;

            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteMotorResultPage.this.isAlive()) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("data_type", this.a);
                    pageBundle.putInt("focusIndex", i5);
                    pageBundle.putLongArray("result_id", jArr2);
                    pageBundle.putObject(RouteItem.ROUTE_DATA, AjxRouteMotorResultPage.this.e);
                    pageBundle.putDouble("opanlayer_lon", d4);
                    pageBundle.putDouble("opanlayer_lat", d5);
                    pageBundle.putInt("opanlayer_z", i6);
                    pageBundle.putString("opanlayer_poiId", str2);
                    pageBundle.putInt("event_id", i7);
                    AjxRouteMotorResultPage.this.startPageForResult(AjxRouteMotorResultEventDetailPage.class, pageBundle, 150);
                }
            }
        };
        ajxRouteMotorResultPage2.a(true, (AnimationListener) r0);
    }

    static /* synthetic */ void c(AjxRouteMotorResultPage ajxRouteMotorResultPage, final String str) {
        if (ajxRouteMotorResultPage.q != null && ajxRouteMotorResultPage.q.c()) {
            ajxRouteMotorResultPage.a(false);
        }
        if (ajxRouteMotorResultPage.q != null) {
            ajxRouteMotorResultPage.q.a();
        }
        ajxRouteMotorResultPage.a(true, (AnimationListener) new AnimationListener() {
            final /* synthetic */ int a = 8;

            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteMotorResultPage.this.isAlive() && AjxRouteMotorResultPage.this.e != null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("data_type", this.a);
                    pageBundle.putString("detail_weather", str);
                    AjxRouteMotorResultPage.this.startPageForResult(AjxRouteMotorResultEventDetailPage.class, pageBundle, 150);
                }
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x001f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void d(com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage r3, java.lang.String r4) {
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
            ph r1 = r3.e
            if (r1 == 0) goto L_0x0034
            boolean r1 = android.text.TextUtils.isEmpty(r4)
            if (r1 == 0) goto L_0x0026
            goto L_0x0034
        L_0x0026:
            te r1 = new te
            r1.<init>()
            r1.a = r4
            r1.b = r0
            ph r3 = r3.e
            r3.j = r1
            return
        L_0x0034:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage.d(com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage, java.lang.String):void");
    }

    static /* synthetic */ void e(AjxRouteMotorResultPage ajxRouteMotorResultPage, String str) {
        int i2;
        if (TextUtils.isEmpty(str)) {
            str = "47";
        }
        long j2 = 0;
        if (ajxRouteMotorResultPage.e != null) {
            i2 = ajxRouteMotorResultPage.e.e;
            if (ajxRouteMotorResultPage.e.a != null) {
                int length = ajxRouteMotorResultPage.e.a.length;
                int[] iArr = new int[length];
                for (int i3 = 0; i3 < length; i3++) {
                    iArr[i3] = (int) ajxRouteMotorResultPage.e.a[i3];
                }
                j2 = NaviManager.createPathResult(iArr);
            }
        } else {
            i2 = 0;
        }
        PageBundle pageBundle = new PageBundle();
        RouteCarResultData routeCarResultData = new RouteCarResultData(CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN);
        routeCarResultData.setFocusRouteIndex(i2);
        CalcRouteResult calcRouteResult = new CalcRouteResult();
        calcRouteResult.setPtr(j2);
        calcRouteResult.mResultInfo.put("valid", Boolean.TRUE);
        routeCarResultData.setCalcRouteResult(calcRouteResult);
        NavigationResult a2 = rn.a(CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN, ajxRouteMotorResultPage.j.n(), ajxRouteMotorResultPage.j.o(), calcRouteResult);
        ajxRouteMotorResultPage.getContext();
        routeCarResultData.setNaviResultData(ajxRouteMotorResultPage.j.n(), ajxRouteMotorResultPage.j.o(), a2, ru.a("0"));
        routeCarResultData.setShareStartPos(ajxRouteMotorResultPage.j.n().getPoint());
        routeCarResultData.setShareEndPos(ajxRouteMotorResultPage.j.o().getPoint());
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (POI next : ajxRouteMotorResultPage.j.p()) {
            arrayList.add(next.getPoint());
            arrayList2.add(next);
        }
        routeCarResultData.setShareMidPos(arrayList);
        routeCarResultData.setMidPOIs(arrayList2);
        pageBundle.putObject("RouteCarResultErrorReportFragment.bundle_key_result", routeCarResultData);
        pageBundle.putString("RouteCarResultErrorReportFragment.from_page_code", String.valueOf(str));
        pageBundle.putLong("pathResult", j2);
        ajxRouteMotorResultPage.startPage((String) "amap.basemap.action.route_car_error_report", pageBundle);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", AMapAppGlobal.getApplication().getString(R.string.action_log_type_motor));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00001", "B022", jSONObject);
    }
}
