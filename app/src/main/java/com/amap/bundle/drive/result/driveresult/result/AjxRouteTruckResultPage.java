package com.amap.bundle.drive.result.driveresult.result;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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
import com.amap.bundle.drive.ajx.inter.JsCommandCallback;
import com.amap.bundle.drive.ajx.inter.OnCalRouteFromData;
import com.amap.bundle.drive.ajx.inter.OnObtainFocusCallBack;
import com.amap.bundle.drive.ajx.inter.OnRouteStateChangeListener;
import com.amap.bundle.drive.ajx.inter.OnTripPoiChangeListener;
import com.amap.bundle.drive.ajx.inter.RouteEventActionInterface;
import com.amap.bundle.drive.ajx.module.ModuleRouteDriveResult;
import com.amap.bundle.drive.api.ITruckGuideManager;
import com.amap.bundle.drive.navi.drivenavi.simulate.page.AjxRouteCarNaviSimulatePage;
import com.amap.bundle.drive.result.autonavisearchmanager.inter.CarSceneSearchAosCallback;
import com.amap.bundle.drive.result.autonavisearchmanager.view.CarSceneTip;
import com.amap.bundle.drive.result.driveresult.browser.AjxRouteCarResultBrowserPage;
import com.amap.bundle.drive.result.driveresult.etd.AjxRouteETDPage;
import com.amap.bundle.drive.result.driveresult.event.AjxRouteCarResultEventDetailPage;
import com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager;
import com.amap.bundle.drive.result.view.DriveRecommendView;
import com.amap.bundle.drive.util.DriveEyrieRouteSharingUtil;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.logs.AMapLog;
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
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.inter.IMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.BaseMapWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.recommend.RecommendAndScaleWidget;
import com.autonavi.bundle.vui.ajx.ModuleVUI;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
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
public class AjxRouteTruckResultPage extends Ajx3Page implements OnTouchListener, bgm, OnRouteStateChangeListener, OnTripPoiChangeListener, IVoiceCmdResponder, LocationGpsAndNetwork, f {
    private int A = 0;
    private int B = 1;
    private boolean C = true;
    private boolean D = false;
    private int E = -1;
    private boolean F;
    private alt G;
    private View H;
    private int I;
    private View J;
    private ContainerType[] K = null;
    private ContainerType[] L = null;
    /* access modifiers changed from: private */
    public AlertView M;
    private Animation N;
    private CarSceneTip O;
    /* access modifiers changed from: private */
    public ViewGroup P;
    /* access modifiers changed from: private */
    public boolean Q = false;
    /* access modifiers changed from: private */
    public boolean R = false;
    /* access modifiers changed from: private */
    public byte[] S;
    private Handler T = new a(0);
    private View U;
    private int V = -1;
    private ModuleVUI W;
    /* access modifiers changed from: private */
    public com.amap.bundle.drive.api.ITruckGuideManager.a X;
    private boolean Y = false;
    /* access modifiers changed from: private */
    public pd Z;
    View a;
    private com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager.a aa = new com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager.a() {
        public final void a(int i) {
            if ((!(i == 3 || i == 2) || !AjxRouteTruckResultPage.this.Q) && AjxRouteTruckResultPage.this.p != null && !AjxRouteTruckResultPage.this.p.e) {
                if (AjxRouteTruckResultPage.this.p.e()) {
                    AjxRouteTruckResultPage.this.y.requestJsurfaceAreaSizeChange(1100, AjxRouteTruckResultPage.this.p.g());
                    return;
                }
                AjxRouteTruckResultPage.this.y.requestJsurfaceAreaSizeChange(SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_PARAM, AjxRouteTruckResultPage.this.p.g());
            }
        }

        /* JADX WARNING: type inference failed for: r2v0 */
        /* JADX WARNING: type inference failed for: r2v2, types: [pp] */
        /* JADX WARNING: type inference failed for: r0v9 */
        /* JADX WARNING: type inference failed for: r2v4 */
        /* JADX WARNING: type inference failed for: r0v10 */
        /* JADX WARNING: type inference failed for: r2v5 */
        /* JADX WARNING: type inference failed for: r1v9, types: [pp] */
        /* JADX WARNING: type inference failed for: r2v6 */
        /* JADX WARNING: type inference failed for: r2v7 */
        /* JADX WARNING: type inference failed for: r0v11 */
        /* JADX WARNING: type inference failed for: r0v12 */
        /* JADX WARNING: type inference failed for: r2v8 */
        /* JADX WARNING: type inference failed for: r2v9, types: [pe$a] */
        /* JADX WARNING: type inference failed for: r0v19 */
        /* JADX WARNING: type inference failed for: r3v11 */
        /* JADX WARNING: type inference failed for: r3v12 */
        /* JADX WARNING: type inference failed for: r2v14 */
        /* JADX WARNING: type inference failed for: r0v20 */
        /* JADX WARNING: type inference failed for: r2v15 */
        /* JADX WARNING: type inference failed for: r2v16 */
        /* JADX WARNING: type inference failed for: r3v13 */
        /* JADX WARNING: type inference failed for: r2v17 */
        /* JADX WARNING: type inference failed for: r0v21 */
        /* JADX WARNING: type inference failed for: r3v14 */
        /* JADX WARNING: type inference failed for: r2v18 */
        /* JADX WARNING: type inference failed for: r0v22 */
        /* JADX WARNING: type inference failed for: r5v4, types: [pe$a] */
        /* JADX WARNING: type inference failed for: r3v15 */
        /* JADX WARNING: type inference failed for: r2v19 */
        /* JADX WARNING: type inference failed for: r0v23 */
        /* JADX WARNING: type inference failed for: r2v20 */
        /* JADX WARNING: type inference failed for: r2v21 */
        /* JADX WARNING: type inference failed for: r0v24 */
        /* JADX WARNING: type inference failed for: r3v16 */
        /* JADX WARNING: type inference failed for: r0v25 */
        /* JADX WARNING: type inference failed for: r0v26 */
        /* JADX WARNING: type inference failed for: r0v27 */
        /* JADX WARNING: type inference failed for: r0v28 */
        /* JADX WARNING: type inference failed for: r2v22 */
        /* JADX WARNING: type inference failed for: r0v29 */
        /* JADX WARNING: type inference failed for: r3v20 */
        /* JADX WARNING: type inference failed for: r3v21 */
        /* JADX WARNING: type inference failed for: r0v30 */
        /* JADX WARNING: type inference failed for: r0v31 */
        /* JADX WARNING: type inference failed for: r3v22 */
        /* JADX WARNING: type inference failed for: r2v23 */
        /* JADX WARNING: type inference failed for: r0v32 */
        /* JADX WARNING: type inference failed for: r3v23 */
        /* JADX WARNING: type inference failed for: r3v24 */
        /* JADX WARNING: type inference failed for: r3v25 */
        /* JADX WARNING: type inference failed for: r3v26 */
        /* JADX WARNING: type inference failed for: r3v27 */
        /* JADX WARNING: type inference failed for: r3v28 */
        /* JADX WARNING: type inference failed for: r2v24 */
        /* JADX WARNING: type inference failed for: r2v25 */
        /* JADX WARNING: type inference failed for: r2v26 */
        /* JADX WARNING: type inference failed for: r2v27 */
        /* JADX WARNING: type inference failed for: r2v28 */
        /* JADX WARNING: type inference failed for: r2v29 */
        /* JADX WARNING: type inference failed for: r0v33 */
        /* JADX WARNING: type inference failed for: r0v34 */
        /* JADX WARNING: type inference failed for: r0v35 */
        /* JADX WARNING: type inference failed for: r0v36 */
        /* JADX WARNING: type inference failed for: r0v37 */
        /* JADX WARNING: type inference failed for: r0v38 */
        /* JADX WARNING: type inference failed for: r3v29 */
        /* JADX WARNING: type inference failed for: r2v30 */
        /* JADX WARNING: type inference failed for: r0v39 */
        /* JADX WARNING: Code restructure failed: missing block: B:120:0x010a, code lost:
            r0 = r0;
         */
        /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v0
          assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], ?[OBJECT, ARRAY]]
          uses: [?[int, boolean, OBJECT, ARRAY, byte, short, char], pp, pe$a]
          mth insns count: 220
        	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
        	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$0(DepthTraversal.java:13)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:13)
        	at jadx.core.ProcessClass.process(ProcessClass.java:30)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
         */
        /* JADX WARNING: Unknown variable types count: 14 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void b(int r11) {
            /*
                r10 = this;
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r0 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                r1 = 1
                r0.Q = r1
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r0 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                r0.b(true)
                r0 = 2
                r2 = 0
                switch(r11) {
                    case 0: goto L_0x01d1;
                    case 1: goto L_0x01d1;
                    case 2: goto L_0x01c1;
                    case 3: goto L_0x01b1;
                    case 4: goto L_0x01ab;
                    case 5: goto L_0x0195;
                    case 6: goto L_0x018f;
                    case 7: goto L_0x0189;
                    case 8: goto L_0x01d1;
                    case 9: goto L_0x01d1;
                    case 10: goto L_0x015a;
                    case 11: goto L_0x00ef;
                    case 12: goto L_0x00ef;
                    case 13: goto L_0x00ef;
                    case 14: goto L_0x00e5;
                    case 15: goto L_0x003a;
                    case 16: goto L_0x003a;
                    case 17: goto L_0x003a;
                    case 18: goto L_0x003a;
                    case 19: goto L_0x0010;
                    case 20: goto L_0x002a;
                    case 21: goto L_0x002a;
                    case 22: goto L_0x0012;
                    case 23: goto L_0x0012;
                    default: goto L_0x0010;
                }
            L_0x0010:
                goto L_0x01dc
            L_0x0012:
                boolean r11 = defpackage.rt.a()
                if (r11 == 0) goto L_0x001e
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.o(r11)
                return
            L_0x001e:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.i(r11)
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                r11.a(r1)
                goto L_0x01dc
            L_0x002a:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager r11 = r11.p
                com.autonavi.jni.ae.route.model.RouteIncident r11 = r11.j
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r1 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                int r11 = r11.id
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.a(r1, r0, r11)
                return
            L_0x003a:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager r11 = r11.p
                java.util.ArrayList r0 = new java.util.ArrayList
                r0.<init>()
                ph r11 = r11.g
                java.util.List<pg> r11 = r11.f
                java.util.Iterator r11 = r11.iterator()
            L_0x004d:
                boolean r3 = r11.hasNext()
                if (r3 == 0) goto L_0x0063
                java.lang.Object r3 = r11.next()
                pg r3 = (defpackage.pg) r3
                java.util.List<pe> r4 = r3.i
                if (r4 == 0) goto L_0x004d
                java.util.List<pe> r3 = r3.i
                r0.addAll(r3)
                goto L_0x004d
            L_0x0063:
                boolean r11 = r0.isEmpty()
                if (r11 == 0) goto L_0x006a
                goto L_0x00ad
            L_0x006a:
                java.util.Iterator r11 = r0.iterator()
                r0 = r2
                r3 = r0
            L_0x0070:
                boolean r4 = r11.hasNext()
                if (r4 == 0) goto L_0x00a5
                java.lang.Object r4 = r11.next()
                pe r4 = (defpackage.pe) r4
                java.util.List<pe$a> r5 = r4.b
                if (r5 == 0) goto L_0x0070
                java.util.List<pe$a> r4 = r4.b
                java.util.Iterator r4 = r4.iterator()
            L_0x0086:
                boolean r5 = r4.hasNext()
                if (r5 == 0) goto L_0x0070
                java.lang.Object r5 = r4.next()
                pe$a r5 = (defpackage.pe.a) r5
                int r6 = r5.a
                switch(r6) {
                    case 0: goto L_0x00a3;
                    case 1: goto L_0x009f;
                    case 2: goto L_0x009b;
                    default: goto L_0x0097;
                }
            L_0x0097:
                if (r3 != 0) goto L_0x0086
                r3 = r5
                goto L_0x0086
            L_0x009b:
                if (r0 != 0) goto L_0x0086
                r0 = r5
                goto L_0x0086
            L_0x009f:
                if (r2 != 0) goto L_0x0086
                r2 = r5
                goto L_0x0086
            L_0x00a3:
                r2 = r5
                goto L_0x00ad
            L_0x00a5:
                if (r2 == 0) goto L_0x00a8
                goto L_0x00ad
            L_0x00a8:
                if (r0 == 0) goto L_0x00ac
                r2 = r0
                goto L_0x00ad
            L_0x00ac:
                r2 = r3
            L_0x00ad:
                if (r2 == 0) goto L_0x00e4
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                r0 = 16
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r3 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                ph r3 = r3.f
                long[] r3 = r3.a
                int r2 = r2.b
                r4 = 0
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.a(r11, r0, r3, r2, r4)
                org.json.JSONObject r11 = new org.json.JSONObject
                r11.<init>()
                java.lang.String r0 = "type"
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r2 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this     // Catch:{ Exception -> 0x00db }
                com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager r2 = r2.p     // Catch:{ Exception -> 0x00db }
                int r2 = r2.m     // Catch:{ Exception -> 0x00db }
                r11.put(r0, r2)     // Catch:{ Exception -> 0x00db }
                java.lang.String r0 = "status"
                r11.put(r0, r1)     // Catch:{ Exception -> 0x00db }
                goto L_0x00df
            L_0x00db:
                r0 = move-exception
                r0.printStackTrace()
            L_0x00df:
                java.lang.String r0 = "B011"
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.b(r0, r11)
            L_0x00e4:
                return
            L_0x00e5:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                ox r11 = r11.c
                r11.b()
                return
            L_0x00ef:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager r11 = r11.p
                java.util.List<pp> r0 = r11.h
                if (r0 != 0) goto L_0x00fa
                goto L_0x0136
            L_0x00fa:
                java.util.List<pp> r11 = r11.h
                if (r11 == 0) goto L_0x0136
                boolean r0 = r11.isEmpty()
                if (r0 == 0) goto L_0x0105
                goto L_0x0136
            L_0x0105:
                java.util.Iterator r11 = r11.iterator()
                r0 = r2
            L_0x010a:
                boolean r1 = r11.hasNext()
                if (r1 == 0) goto L_0x0132
                java.lang.Object r1 = r11.next()
                pp r1 = (defpackage.pp) r1
                int r3 = r1.a
                r4 = 81
                if (r3 != r4) goto L_0x011e
                r2 = r1
                goto L_0x0136
            L_0x011e:
                int r3 = r1.a
                r4 = 82
                if (r3 != r4) goto L_0x0128
                if (r2 != 0) goto L_0x010a
                r2 = r1
                goto L_0x010a
            L_0x0128:
                int r3 = r1.a
                r4 = 83
                if (r3 != r4) goto L_0x010a
                if (r0 != 0) goto L_0x010a
                r0 = r1
                goto L_0x010a
            L_0x0132:
                if (r2 == 0) goto L_0x0135
                goto L_0x0136
            L_0x0135:
                r2 = r0
            L_0x0136:
                if (r2 == 0) goto L_0x0159
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                ph r11 = r11.f
                if (r11 == 0) goto L_0x0159
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                r0 = 15
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r1 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                ph r1 = r1.f
                long[] r1 = r1.a
                int r2 = r2.b
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r3 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                ph r3 = r3.f
                int r3 = r3.e
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.a(r11, r0, r1, r2, r3)
            L_0x0159:
                return
            L_0x015a:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager r11 = r11.p
                pk r11 = r11.j()
                if (r11 == 0) goto L_0x0188
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r0 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                ph r0 = r0.f
                if (r0 == 0) goto L_0x0188
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r1 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                int r2 = r11.a
                int r3 = r11.b
                int r4 = r11.d
                java.lang.String r5 = r11.c
                java.lang.String r6 = r11.e
                java.lang.String r7 = r11.f
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                ph r11 = r11.f
                long[] r8 = r11.a
                r9 = 0
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.a(r1, r2, r3, r4, r5, r6, r7, r8, r9)
            L_0x0188:
                return
            L_0x0189:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.l(r11)
                return
            L_0x018f:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                r11.startPageForResult("amap.basemap.action.car_plate_input", new com.autonavi.common.PageBundle(), (int) com.alipay.mobile.security.faceauth.api.AntDetector.ACTION_ID_TRY_LOGIN)
                return
            L_0x0195:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager r11 = r11.p
                px<sn> r11 = r11.k
                if (r11 == 0) goto L_0x01dc
                T r11 = r11.f
                sn r11 = (defpackage.sn) r11
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r0 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                int r11 = r11.a
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.a(r0, r1, r11)
                return
            L_0x01ab:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.k(r11)
                return
            L_0x01b1:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager r11 = r11.p
                com.autonavi.jni.ae.route.model.RouteIncident r11 = r11.i
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r1 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                int r11 = r11.id
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.a(r1, r0, r11)
                return
            L_0x01c1:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.tip.AjxTipsManager r11 = r11.p
                com.autonavi.jni.ae.route.model.RouteIncident r11 = r11.i
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r1 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                int r11 = r11.id
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.a(r1, r0, r11)
                return
            L_0x01d1:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.i(r11)
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r11 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                r11.a(r1)
                return
            L_0x01dc:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.AnonymousClass25.b(int):void");
        }

        public final void c(int i) {
            AjxRouteTruckResultPage.this.Q = false;
            if (i == 4) {
                AjxRouteTruckResultPage.h();
            } else if (i != 6) {
                switch (i) {
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("type", AjxRouteTruckResultPage.this.p.m);
                            jSONObject.put("status", 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        AjxRouteTruckResultPage.b((String) "B011", jSONObject);
                        break;
                }
            } else {
                AjxRouteTruckResultPage.g();
            }
        }
    };
    private bfa ab = new bfa() {
        public final void a(boolean z) {
            if (z) {
                AjxRouteTruckResultPage.r(AjxRouteTruckResultPage.this);
            }
            if (AjxRouteTruckResultPage.this.y != null) {
                AjxRouteTruckResultPage.this.y.dispatchVUILayerEvent(z);
            }
            if (AjxRouteTruckResultPage.this.Z != null && z) {
                AjxRouteTruckResultPage.this.Z.a();
            }
        }
    };
    private OnClickListener ac = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.route_car_result_online_icon) {
                DriveSpUtil.setSearchRouteInNeMode(AjxRouteTruckResultPage.this.getContext(), true);
                AjxRouteTruckResultPage.this.c.b();
            } else if (view.getId() == R.id.route_car_result_refresh) {
                AjxRouteTruckResultPage.this.a(-1);
                AjxRouteTruckResultPage.i();
            } else if (view.getId() == R.id.route_car_result_dl) {
                int[] iArr = null;
                if (AjxRouteTruckResultPage.this.f != null) {
                    pg a2 = AjxRouteTruckResultPage.this.f.a();
                    if (!(a2 == null || a2.q == null)) {
                        iArr = a2.q.b;
                    }
                }
                IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
                if (iOfflineManager != null) {
                    iOfflineManager.enterAlongWayDownload(iArr);
                }
                if (AjxRouteTruckResultPage.this.p != null) {
                    AjxRouteTruckResultPage.this.p.c();
                }
            } else {
                if (view.getId() == R.id.route_car_result_etd_entrance) {
                    AjxRouteTruckResultPage.o(AjxRouteTruckResultPage.this);
                }
            }
        }
    };
    private com.amap.bundle.drive.result.view.RouteCarLongScenePanel.a ad = new com.amap.bundle.drive.result.view.RouteCarLongScenePanel.a() {
        public final void a(boolean z) {
            AjxRouteTruckResultPage.this.y.requestJsSuspendClickEvent(1002, z);
            AjxRouteTruckResultPage.i();
        }

        public final void b(boolean z) {
            AjxRouteTruckResultPage.this.y.requestJsSuspendClickEvent(1003, z);
            AjxRouteTruckResultPage.i();
        }

        public final void c(boolean z) {
            AjxRouteTruckResultPage.this.y.requestJsSuspendClickEvent(1005, z);
            AjxRouteTruckResultPage.i();
        }

        public final void d(boolean z) {
            AjxRouteTruckResultPage.this.y.requestJsSuspendClickEvent(1010, z);
            AjxRouteTruckResultPage.i();
        }

        public final void e(boolean z) {
            AjxRouteTruckResultPage.this.y.setWeatherSwitchState(z);
            AjxRouteTruckResultPage.this.y.requestJsSuspendClickEvent(1008, z);
        }
    };
    private OnCalRouteFromData ae = new OnCalRouteFromData() {
        public final void onCalRouteFromData() {
            if (AjxRouteTruckResultPage.this.c.c != null && AjxRouteTruckResultPage.this.c.c.getPoint() != null && AjxRouteTruckResultPage.this.c.d != null && AjxRouteTruckResultPage.this.c.d.getPoint() != null) {
                RouteWayPoint routeWayPoint = new RouteWayPoint();
                RoutePOIInfo routePOIInfo = new RoutePOIInfo();
                POI poi = AjxRouteTruckResultPage.this.c.c;
                ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
                ISearchPoiData iSearchPoiData2 = (ISearchPoiData) AjxRouteTruckResultPage.this.c.d.as(ISearchPoiData.class);
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
                        coord2D3.lon = AjxRouteTruckResultPage.this.c.d.getPoint().getLongitude();
                        coord2D3.lat = AjxRouteTruckResultPage.this.c.d.getPoint().getLatitude();
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
                        NaviManager.calcRouteFromDataNew(2, routeWayPoint, AjxRouteTruckResultPage.this.S);
                    }
                }
            }
        }
    };
    private JsCommandCallback af = new JsCommandCallback() {
        public final boolean callback(int i, String... strArr) {
            switch (i) {
                case 1:
                    cde suspendManager = AjxRouteTruckResultPage.this.getSuspendManager();
                    if (suspendManager != null) {
                        cdz d = suspendManager.d();
                        if (d != null) {
                            d.f();
                            d.e();
                        }
                    }
                    return true;
                case 6:
                    AjxRouteTruckResultPage ajxRouteTruckResultPage = AjxRouteTruckResultPage.this;
                    if (ajxRouteTruckResultPage.n != null) {
                        ajxRouteTruckResultPage.b();
                    }
                    if (ajxRouteTruckResultPage.p == null || !ajxRouteTruckResultPage.p.e()) {
                        ajxRouteTruckResultPage.finish();
                        ON_BACK_TYPE on_back_type = ON_BACK_TYPE.TYPE_IGNORE;
                    } else {
                        ajxRouteTruckResultPage.b(true);
                        ON_BACK_TYPE on_back_type2 = ON_BACK_TYPE.TYPE_IGNORE;
                    }
                    return true;
                case 7:
                    AjxRouteTruckResultPage ajxRouteTruckResultPage2 = AjxRouteTruckResultPage.this;
                    RouteType routeType = RouteType.TAXI;
                    if (!ajxRouteTruckResultPage2.d) {
                        ajxRouteTruckResultPage2.j.a(routeType);
                    } else {
                        ajxRouteTruckResultPage2.finish();
                        PageBundle pageBundle = new PageBundle("amap.extra.route.route", "com.autonavi.minimap");
                        pageBundle.putObject("bundle_key_poi_start", ajxRouteTruckResultPage2.c.a.n());
                        pageBundle.putObject("bundle_key_poi_end", ajxRouteTruckResultPage2.c.a.o());
                        pageBundle.putBoolean("bundle_key_auto_route", true);
                        pageBundle.putObject("bundle_key_route_type", routeType);
                        bax bax = (bax) defpackage.esb.a.a.a(bax.class);
                        if (bax != null) {
                            bax.a(pageBundle);
                        }
                    }
                    return true;
                case 25:
                    if (AjxRouteTruckResultPage.this.p != null) {
                        AjxRouteTruckResultPage.this.p.a(Float.valueOf(1.0f));
                    }
                    if (AjxRouteTruckResultPage.this.P != null) {
                        AjxRouteTruckResultPage.this.P.setAlpha(1.0f);
                    }
                    if (AjxRouteTruckResultPage.this.c.a() == 102) {
                        AjxRouteTruckResultPage.a(AjxRouteTruckResultPage.this, 1.0f);
                    }
                    if (AjxRouteTruckResultPage.this.c.a() != 102) {
                        AjxRouteTruckResultPage.this.a(0.0f);
                        break;
                    }
                    break;
                case 26:
                    if (strArr != null && strArr.length > 0) {
                        AjxRouteTruckResultPage.e(AjxRouteTruckResultPage.this, strArr[0]);
                        break;
                    }
            }
            if (!AjxRouteTruckResultPage.this.isResumed()) {
                return false;
            }
            switch (i) {
                case 1003:
                    DriveEyrieRouteSharingUtil.a(AjxRouteTruckResultPage.this.f);
                    break;
                case 1004:
                    AjxRouteTruckResultPage.y(AjxRouteTruckResultPage.this);
                    break;
            }
            return false;
        }
    };
    private OnObtainFocusCallBack ag = new OnObtainFocusCallBack() {
        public final void onObtainFocus() {
        }
    };
    private aij ah = new aij() {
        public final void b(int i) {
        }

        public final void a(int i, int i2) {
            if (AjxRouteTruckResultPage.this.z != null) {
                if (AjxRouteTruckResultPage.this.z.a(AjxRouteTruckResultPage.A(AjxRouteTruckResultPage.this), (String) "switchRoute", i, new Pair<>("index", Integer.valueOf(i2)))) {
                    return;
                }
            }
            sa.a(i, (int) SDKFactory.getCoreType);
        }

        public final void a(int i) {
            if (AjxRouteTruckResultPage.this.z == null || !AjxRouteTruckResultPage.this.z.a(AjxRouteTruckResultPage.A(AjxRouteTruckResultPage.this), (String) "startNavi", i)) {
                sa.a(i, (int) SDKFactory.getCoreType);
            }
        }

        public final void c(int i) {
            if (AjxRouteTruckResultPage.this.z != null) {
                AjxRouteTruckResultPage.this.a(i);
            } else {
                sa.a(i, (int) SDKFactory.getCoreType);
            }
        }

        public final void a(int i, String str) {
            if (AjxRouteTruckResultPage.this.z != null) {
                AjxRouteTruckResultPage.a(AjxRouteTruckResultPage.this, i, str);
            } else {
                sa.a(i, (int) SDKFactory.getCoreType);
            }
        }
    };
    pc b = null;
    public ox c;
    public boolean d = false;
    public boolean e;
    public ph f;
    public String g;
    public boolean h;
    SyncPopupWindow i;
    public qb j;
    public String k;
    public boolean l;
    public String m;
    ViewGroup n;
    /* access modifiers changed from: 0000 */
    public ViewGroup o;
    public AjxTipsManager p;
    protected Handler q = new Handler();
    OnClickListener r = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.tips_entrance) {
                AjxRouteTruckResultPage ajxRouteTruckResultPage = AjxRouteTruckResultPage.this;
                if (ajxRouteTruckResultPage.p != null) {
                    Animation loadAnimation = AnimationUtils.loadAnimation(ajxRouteTruckResultPage.getContext(), R.anim.tips_showing);
                    loadAnimation.setFillAfter(true);
                    ajxRouteTruckResultPage.o.setVisibility(0);
                    NoDBClickUtil.a((View) ajxRouteTruckResultPage.o, ajxRouteTruckResultPage.r);
                    loadAnimation.setAnimationListener(new AnimationListener() {
                        public final void onAnimationEnd(Animation animation) {
                        }

                        public final void onAnimationRepeat(Animation animation) {
                        }

                        public final void onAnimationStart(Animation animation) {
                            AjxRouteTruckResultPage.this.p.h();
                        }
                    });
                    ajxRouteTruckResultPage.o.startAnimation(loadAnimation);
                }
            } else if (view.getId() == R.id.tips_bg_mask) {
                AjxRouteTruckResultPage.this.b(true);
            } else if (view.getId() == R.id.title_back_img) {
                qd.b(AjxRouteTruckResultPage.this.j.j());
                qd.a(AjxRouteTruckResultPage.this.getContentView(), (AnimatorListener) new AnimatorListener() {
                    public final void onAnimationCancel(Animator animator) {
                    }

                    public final void onAnimationRepeat(Animator animator) {
                    }

                    public final void onAnimationStart(Animator animator) {
                    }

                    public final void onAnimationEnd(Animator animator) {
                        AjxRouteTruckResultPage.this.finish();
                    }
                });
            } else {
                if (view.getId() == R.id.navi_truckguide_addtruck_btn) {
                    DriveUtil.startAddCarPage(2, AjxRouteTruckResultPage.this);
                }
            }
        }
    };
    mr s = new mr() {
    };
    mq t = new mq() {
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(java.lang.String r3, java.lang.String r4) {
            /*
                r2 = this;
                com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
                r0.<init>()
                int r1 = r3.hashCode()
                switch(r1) {
                    case -1203606180: goto L_0x0035;
                    case 138748996: goto L_0x002b;
                    case 272721754: goto L_0x0021;
                    case 553280922: goto L_0x0017;
                    case 953959764: goto L_0x000d;
                    default: goto L_0x000c;
                }
            L_0x000c:
                goto L_0x003f
            L_0x000d:
                java.lang.String r1 = "carPreview"
                boolean r3 = r3.equals(r1)
                if (r3 == 0) goto L_0x003f
                r3 = 0
                goto L_0x0040
            L_0x0017:
                java.lang.String r1 = "carNavi"
                boolean r3 = r3.equals(r1)
                if (r3 == 0) goto L_0x003f
                r3 = 1
                goto L_0x0040
            L_0x0021:
                java.lang.String r1 = "backDefault"
                boolean r3 = r3.equals(r1)
                if (r3 == 0) goto L_0x003f
                r3 = 3
                goto L_0x0040
            L_0x002b:
                java.lang.String r1 = "carMockNavi"
                boolean r3 = r3.equals(r1)
                if (r3 == 0) goto L_0x003f
                r3 = 2
                goto L_0x0040
            L_0x0035:
                java.lang.String r1 = "errorReport"
                boolean r3 = r3.equals(r1)
                if (r3 == 0) goto L_0x003f
                r3 = 4
                goto L_0x0040
            L_0x003f:
                r3 = -1
            L_0x0040:
                switch(r3) {
                    case 0: goto L_0x006f;
                    case 1: goto L_0x005f;
                    case 2: goto L_0x0059;
                    case 3: goto L_0x004a;
                    case 4: goto L_0x0044;
                    default: goto L_0x0043;
                }
            L_0x0043:
                goto L_0x0075
            L_0x0044:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r3 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.d(r3, r4)
                goto L_0x0075
            L_0x004a:
                bid r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
                if (r3 == 0) goto L_0x0075
                r3.finish()
                java.lang.String r4 = "amap.basemap.action.default_page"
                r3.startPage(r4, r0)
                return
            L_0x0059:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r3 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.c(r3, r4)
                return
            L_0x005f:
                java.lang.String r3 = "\n"
                defpackage.tj.a(r3)
                java.lang.String r3 = "car-startNavi-click_3.1"
                defpackage.tj.a(r3)
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r3 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                defpackage.nm.a(r3.getActivity(), r4, (defpackage.ms.b) new com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.AnonymousClass10(), (java.lang.String) com.amap.bundle.drivecommon.tools.DriveUtil.NAVI_TYPE_TRUCK)
                return
            L_0x006f:
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r3 = com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.this
                com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.a(r3, r4)
                return
            L_0x0075:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.AnonymousClass15.a(java.lang.String, java.lang.String):void");
        }
    };
    RouteEventActionInterface u = new RouteEventActionInterface() {
        public final void showIncidentDetail(String str) {
            int i;
            AMapLog.d("AjxRouteTruckResultPage", "showIncidentDetail---JSON=".concat(String.valueOf(str)));
            if (AjxRouteTruckResultPage.this.isResumed()) {
                if (AjxRouteTruckResultPage.this.p != null && AjxRouteTruckResultPage.this.p.e()) {
                    AjxRouteTruckResultPage.this.p.m();
                }
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    int optInt = jSONObject.optInt("type");
                    int optInt2 = jSONObject.optInt("focusIndex");
                    int i2 = 0;
                    if (optInt != 2) {
                        if (optInt != 1) {
                            if (optInt == 3) {
                                double optDouble = jSONObject.optDouble(LocationParams.PARA_FLP_AUTONAVI_LON);
                                double optDouble2 = jSONObject.optDouble("lat");
                                int optInt3 = jSONObject.optInt("z");
                                jSONObject.optInt("poiType");
                                jSONObject.optInt("subType");
                                String optString = jSONObject.optString("poiID");
                                try {
                                    i = Integer.parseInt(optString);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    i = 0;
                                }
                                JSONArray optJSONArray = jSONObject.optJSONArray("routeSetId");
                                long[] jArr = null;
                                if (optJSONArray != null) {
                                    jArr = new long[optJSONArray.length()];
                                    while (i2 < optJSONArray.length()) {
                                        jArr[i2] = optJSONArray.getLong(i2);
                                        i2++;
                                    }
                                }
                                AjxRouteTruckResultPage.a(AjxRouteTruckResultPage.this, i, jArr, optInt2, optDouble, optDouble2, optInt3, optString);
                                return;
                            } else if (optInt == 4) {
                                int optInt4 = jSONObject.optInt("forbiddenId");
                                int optInt5 = jSONObject.optInt("forbiddenType");
                                int optInt6 = jSONObject.optInt("vehicleType");
                                JSONArray optJSONArray2 = jSONObject.optJSONArray("routeSetId");
                                if (optJSONArray2 != null) {
                                    if (optJSONArray2.length() != 0) {
                                        long[] jArr2 = new long[optJSONArray2.length()];
                                        while (i2 < optJSONArray2.length()) {
                                            jArr2[i2] = optJSONArray2.getLong(i2);
                                            i2++;
                                        }
                                        AjxRouteTruckResultPage.a(AjxRouteTruckResultPage.this, optInt4, optInt5, optInt6, jSONObject.optString("timeDescription"), jSONObject.optString("roadNameString"), jSONObject.optString("nextRoadNameString"), jArr2, optInt2);
                                        return;
                                    }
                                }
                                return;
                            } else {
                                if (optInt == 15 || optInt == 16) {
                                    JSONArray optJSONArray3 = jSONObject.optJSONArray("routeSetId");
                                    if (optJSONArray3 != null) {
                                        if (optJSONArray3.length() != 0) {
                                            long[] jArr3 = new long[optJSONArray3.length()];
                                            while (i2 < optJSONArray3.length()) {
                                                jArr3[i2] = optJSONArray3.getLong(i2);
                                                i2++;
                                            }
                                            AjxRouteTruckResultPage.a(AjxRouteTruckResultPage.this, optInt, jArr3, jSONObject.optInt("incidentId"), optInt2);
                                        }
                                    }
                                    return;
                                }
                                return;
                            }
                        }
                    }
                    int optInt7 = jSONObject.optInt("incidentId");
                    JSONArray optJSONArray4 = jSONObject.optJSONArray("routeSetId");
                    if (optJSONArray4 != null) {
                        if (optJSONArray4.length() != 0) {
                            long[] jArr4 = new long[optJSONArray4.length()];
                            while (i2 < optJSONArray4.length()) {
                                jArr4[i2] = optJSONArray4.getLong(i2);
                                i2++;
                            }
                            AjxRouteTruckResultPage.a(AjxRouteTruckResultPage.this, optInt, jArr4, optInt7, optInt2);
                        }
                    }
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }
    };
    private View v;
    private RelativeLayout w;
    private View x;
    /* access modifiers changed from: private */
    public ModuleRouteDriveResult y;
    /* access modifiers changed from: private */
    public aia z;

    static class a extends Handler {
        public final void handleMessage(Message message) {
        }

        private a() {
        }

        /* synthetic */ a(byte b) {
            this();
        }
    }

    static /* synthetic */ void i() {
    }

    public void finishSelf() {
    }

    public String getAjx3Url() {
        return ModuleRouteDriveResult.URL_CAR_ROUTE;
    }

    public long getScene() {
        return 8192;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 8192;
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
        IVoiceDriveDispatcher iVoiceDriveDispatcher = (IVoiceDriveDispatcher) ank.a(IVoiceDriveDispatcher.class);
        if (iVoiceDriveDispatcher != null) {
            iVoiceDriveDispatcher.setRouteApiControlListener(this.ah);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: j */
    public ox createPresenter() {
        this.c = new ox(this);
        return this.c;
    }

    public void pageCreated() {
        super.pageCreated();
        this.j = new qb(this);
        this.j.a();
        if (getMapView() != null) {
            this.A = getMapView().j(false);
            this.B = getMapView().k(false);
        }
        this.J = findViewById(R.id.mapBottomInteractiveView);
        this.P = this.b.a;
        NoDBClickUtil.a((View) this.P, this.r);
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.d = arguments.getBoolean("key_favorites", false);
            Ajx.getInstance().getMemoryStorage("PlanResult").setItem("isFromFavorite", Boolean.valueOf(this.d));
            if (this.d && arguments.containsKey("original_route")) {
                cor cor = (cor) arguments.getObject("original_route");
                if (cor != null) {
                    this.g = cor.f();
                }
            }
            if (arguments.containsKey("voice_process")) {
                this.j.q();
            }
            this.F = arguments.containsKey("voice_process");
            this.e = arguments.getInt("key_source", 0) == 102;
            this.E = arguments.getInt("bundle_key_token", -1);
            if (this.e) {
                this.S = arguments.getByteArray("original_route");
                this.c.c = (POI) arguments.getObject("bundle_key_poi_start");
                this.c.d = (POI) arguments.getObject("bundle_key_poi_end");
            }
            this.D = arguments.getBoolean("key_subresult", false);
            if (arguments.containsKey("bundle_key_method_flag") && arguments.getInt("bundle_key_method_flag", 0) == 1 && arguments.containsKey("bundle_key_method")) {
                String string = arguments.getString("bundle_key_method");
                if (!TextUtils.isEmpty(string)) {
                    DriveUtil.putTruckRoutingChoice(string);
                }
            }
        }
        if (this.d) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.route_car_result_map_title, null);
            View findViewById = inflate.findViewById(R.id.route_car_result_map_title_favorite);
            findViewById.setVisibility(0);
            this.U = findViewById.findViewById(R.id.title_back_img);
            NoDBClickUtil.a(this.U, this.r);
            this.j.k();
            this.j.a(inflate);
            this.j.q();
        }
        if (this.F) {
            this.q.post(new Runnable() {
                public final void run() {
                    ViewGroup s = AjxRouteTruckResultPage.this.j.s();
                    if (s != null) {
                        s.bringToFront();
                    }
                }
            });
        }
        if (n()) {
            final ITruckGuideManager iTruckGuideManager = (ITruckGuideManager) ank.a(ITruckGuideManager.class);
            if (iTruckGuideManager != null) {
                if (o()) {
                    this.X = new com.amap.bundle.drive.api.ITruckGuideManager.a() {
                        public final void a(boolean z) {
                            if (!z) {
                                AjxRouteTruckResultPage.this.X = null;
                                iTruckGuideManager.setTruckGuideListener(null);
                                if (AjxRouteTruckResultPage.n()) {
                                    AjxRouteTruckResultPage.this.p();
                                }
                            }
                        }
                    };
                    iTruckGuideManager.setTruckGuideListener(this.X);
                } else {
                    p();
                }
            }
        }
        this.Z = new pd(this, this.j);
    }

    public final void a(boolean z2) {
        if (z2) {
            if (this.p != null && this.p.e()) {
                b(false);
            }
            if (this.p != null) {
                this.p.c();
            }
            a(true, (AnimationListener) new AnimationListener() {
                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                }

                public final void onAnimationEnd(Animation animation) {
                    AjxRouteTruckResultPage.d(AjxRouteTruckResultPage.this);
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
                if (AjxRouteTruckResultPage.this.isAlive()) {
                    AjxRouteTruckResultPage.this.getContentView().findViewById(R.id.mapTopInteractiveView).setVisibility(0);
                }
            }
        });
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

    public void onContentViewCreated(View view) {
        this.v = view;
    }

    public void resume() {
        int i2;
        super.resume();
        k();
        if (!this.e) {
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
        this.h = false;
        if (this.v != null) {
            this.v.setVisibility(0);
        }
        bim.aa().a((biv) new biv() {
            public final void saveSucess() {
                boolean z;
                AjxRouteTruckResultPage ajxRouteTruckResultPage = AjxRouteTruckResultPage.this;
                IAccountService iAccountService = (IAccountService) defpackage.esb.a.a.a(IAccountService.class);
                if (iAccountService == null) {
                    z = false;
                } else {
                    z = iAccountService.a();
                }
                if (!z) {
                    if (ajxRouteTruckResultPage.i == null) {
                        ajxRouteTruckResultPage.a = View.inflate(ajxRouteTruckResultPage.getContext(), R.layout.v4_fromto_car_result_detail_dlg, null);
                        IRouteUI b = ajxRouteTruckResultPage.c.a.b();
                        if (b != null) {
                            b.a(ajxRouteTruckResultPage.a);
                        }
                        ajxRouteTruckResultPage.i = new SyncPopupWindow(ajxRouteTruckResultPage.a);
                    }
                    ajxRouteTruckResultPage.i.show();
                }
            }
        });
        if (this.C) {
            this.C = false;
            IRouteUI b2 = this.c.a.b();
            if (b2 != null) {
                b2.a(this.v);
                this.mAjxView.setLayoutParams(new LayoutParams(-1, -1));
                if (this.F) {
                    LayoutParams layoutParams = new LayoutParams(-1, (int) (Resources.getSystem().getDisplayMetrics().density * 142.0f));
                    layoutParams.addRule(12);
                    this.w = new RelativeLayout(getContext());
                    this.w.setLayoutParams(layoutParams);
                    this.w.setOnTouchListener(this);
                    b2.a((View) this.w);
                }
            }
            this.x = getLayoutInflater().inflate(R.layout.navi_truck_blank_layout, null);
            this.x.setLayoutParams(new LayoutParams(-1, -1));
            ((RelativeLayout) getContentView()).addView(this.x);
            NoDBClickUtil.a(this.x.findViewById(R.id.navi_truckguide_addtruck_btn), this.r);
        } else if (this.p != null) {
            this.p.l();
        }
        if (this.x != null) {
            this.x.setVisibility(DriveUtil.isCarTruckInfoEmpty() ? 0 : 8);
            boolean isCarTruckInfoEmpty = DriveUtil.isCarTruckInfoEmpty();
            if (defpackage.eqc.a.a.b) {
                PageBundle arguments = getArguments();
                if (arguments != null) {
                    i2 = arguments.getInt("bundle_key_token", -1);
                    this.E = i2;
                    ox oxVar = this.c;
                    POI endPOI = getEndPOI();
                    if (oxVar.g != null) {
                        oxVar.g.b(endPOI);
                    }
                } else {
                    i2 = -1;
                }
                if (isCarTruckInfoEmpty) {
                    d.a.a(i2, 10127, (String) null);
                    defpackage.eqc.a.a.b = false;
                    this.E = -1;
                }
            }
        }
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.resumeActivityState();
        }
        if (this.c != null && this.c.a != null && this.c.a.b() != null && this.c.a.b().o()) {
            this.Y = true;
        }
    }

    private void k() {
        if (this.p == null) {
            this.p = new AjxTipsManager(getContext(), 1, this.j.b(), this.d);
            this.o = this.p.d;
            if (this.y != null) {
                this.y.bindTipsManager(this.p);
            }
        }
    }

    public void pause() {
        super.pause();
        if (!(this.c == null || this.c.a() == 102)) {
            a(0.0f);
        }
        if (this.p != null) {
            this.p.b();
        }
        if (this.p != null && this.p.f() > 0) {
            this.p.n();
        }
        if (!this.e) {
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
        this.k = DriveUtil.getTruckRoutingChoice();
        this.l = DriveUtil.getTruckAvoidSwitch();
        this.m = DriveUtil.getTruckCarPlateNumber();
        if (this.i != null) {
            this.i.hide();
        }
        bim.aa().a((biv) null);
    }

    public void stop() {
        super.stop();
        if (this.h) {
            this.v.setVisibility(4);
            if (this.j != null) {
                POI o2 = this.j.o();
                if (o2 != null) {
                    this.j.b(rt.a(o2));
                }
            }
            if (this.x != null) {
                this.x.setVisibility(8);
            }
        }
    }

    public final void a() {
        if (this.p != null) {
            this.p.k();
            this.p.f = null;
        }
    }

    public final void b() {
        if (this.n != null) {
            this.n.setVisibility(8);
            if (this.p != null) {
                this.p.d(0);
            }
            a((int) SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_LENGTH, 0);
        }
    }

    public void destroy() {
        super.destroy();
        if (this.W != null) {
            this.W.setMitVuiDialogEventListener(null);
            ModuleVUI.mMitVuiDialogEventCallback = null;
        }
        if (getMapView() != null) {
            getMapView().a(this.A, getMapView().l(false), this.B);
        }
        IVoiceDriveDispatcher iVoiceDriveDispatcher = (IVoiceDriveDispatcher) ank.a(IVoiceDriveDispatcher.class);
        if (iVoiceDriveDispatcher != null) {
            iVoiceDriveDispatcher.setRouteApiControlListener(null);
        }
        IRouteUI b2 = this.c.a.b();
        if (b2 != null) {
            b2.b((View) this.mAjxView);
            if (this.w != null) {
                b2.b((View) this.w);
            }
            if (this.a != null) {
                b2.b(this.a);
            }
        }
        if (this.y != null) {
            this.y.setTruckTopHeightCallback(null);
            this.y.setOnTripPoiChangeListener(null);
            this.y.setOnRouteStateChangeListener(null);
            this.y.setOnObtainFocusCallBack(null);
            this.y.addRouteEventActionInterface(null);
            this.y.setJsCommandCallback(null);
            this.y.setOnCalRouteFromData(null);
            this.y.removeSwitchSceneListener(this.t);
        }
        if (this.b != null) {
            this.b.c();
            this.b = null;
        }
        if (this.U != null) {
            NoDBClickUtil.a(this.U, (OnClickListener) null);
        }
        if (this.p != null) {
            NoDBClickUtil.a((View) this.o, (OnClickListener) null);
            this.o.clearAnimation();
            a();
            m();
        }
        if (this.x != null) {
            IRouteUI b3 = this.j.b();
            if (b3 != null) {
                b3.b(this.x);
            }
        }
        if (this.X != null) {
            ITruckGuideManager iTruckGuideManager = (ITruckGuideManager) ank.a(ITruckGuideManager.class);
            if (iTruckGuideManager != null) {
                iTruckGuideManager.setTruckGuideListener(null);
            }
        }
    }

    public boolean backPressed() {
        if (this.Z != null && this.Z.b) {
            this.Z.a();
            return true;
        } else if (this.M != null && isViewLayerShowing(this.M)) {
            dismissViewLayer(this.M);
            this.M = null;
            return true;
        } else if (this.p != null && this.p.e()) {
            b(false);
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
                if (AjxRouteTruckResultPage.this.h) {
                    return false;
                }
                if ("CAR_FADE_FROM_BOTTOM".equalsIgnoreCase(str)) {
                    Float f = (Float) obj;
                    Stub.getMapWidgetManager().setContainerAlpha(f.floatValue());
                    if (AjxRouteTruckResultPage.this.p != null) {
                        AjxRouteTruckResultPage.this.p.a(f);
                    }
                    if (AjxRouteTruckResultPage.this.P != null) {
                        AjxRouteTruckResultPage.this.P.setAlpha(f.floatValue());
                    }
                    if (AjxRouteTruckResultPage.this.c.a() == 102) {
                        AjxRouteTruckResultPage.a(AjxRouteTruckResultPage.this, f.floatValue());
                    }
                    return true;
                } else if (!"CAR_HEIGHT_TO_TOP".equalsIgnoreCase(str)) {
                    return false;
                } else {
                    if (AjxRouteTruckResultPage.this.c.a() != 102) {
                        AjxRouteTruckResultPage.this.a(((Float) obj).floatValue());
                    }
                    return true;
                }
            }
        });
    }

    public void loadJs() {
        JsRunInfo jsRunInfo = new JsRunInfo(ModuleRouteDriveResult.URL_CAR_ROUTE, this.c.c());
        jsRunInfo.setTag("TRUCK_MAP_RESULT");
        HashMap hashMap = new HashMap();
        hashMap.put(ModuleRouteDriveResult.AJX_JS_INFO_RUNTIME_ROUTE_TYPE, RouteType.TRUCK);
        jsRunInfo.setRunParams(hashMap);
        this.mAjxView.loadJs(jsRunInfo);
    }

    /* access modifiers changed from: private */
    public void a(float f2) {
        if (!(this.c.a.b() == null || this.c.a.b() == null)) {
            this.H = this.c.a.b().c();
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
        if (this.W == null) {
            this.W = (ModuleVUI) this.mAjxView.getJsModule(ModuleVUI.MODULE_NAME);
        }
        this.y = (ModuleRouteDriveResult) this.mAjxView.getJsModule(ModuleRouteDriveResult.MODULE_NAME);
        if (this.y != null) {
            this.y.setTruckTopHeightCallback(this.c);
            this.y.setOnTripPoiChangeListener(this);
            this.y.setOnRouteStateChangeListener(this);
            this.y.setOnObtainFocusCallBack(this.ag);
            this.y.addRouteEventActionInterface(this.u);
            this.y.setJsCommandCallback(this.af);
            this.y.setOnCalRouteFromData(this.ae);
            this.y.addSwitchSceneListener(this.t);
            this.y.bindTipsManager(this.p);
        }
        this.z = (aia) defpackage.esb.a.a.a(aia.class);
        if (this.W != null) {
            this.W.setMitVuiDialogEventListener(this.ab);
        }
    }

    public final void a(String str) {
        if (this.y != null) {
            this.y.requestCarRoute(str);
            if (!(this.o == null || this.o.getAnimation() == null || this.o.getAnimation().hasEnded())) {
                this.o.getAnimation().cancel();
            }
            a();
            b();
        }
    }

    public void onMidPOIChanged(List<POI> list) {
        if (this.c != null) {
            ox oxVar = this.c;
            if (oxVar.a != null && ((Ajx3Page) oxVar.mPage).isResumed()) {
                oxVar.g.a(RouteType.TRUCK, list);
                oxVar.g.a(list);
            }
        }
        a();
        b();
    }

    public void onEndPOIChanged(POI poi, int i2) {
        if (this.c != null) {
            poi.getPoiExtra().put("key_end_poi_source_type", Integer.valueOf(i2));
            this.c.a(poi);
        }
        a();
        b();
    }

    public POI getStartPOI() {
        if (this.c != null) {
            return this.c.a.n();
        }
        return null;
    }

    public POI getEndPOI() {
        if (this.c != null) {
            return this.c.a.o();
        }
        return null;
    }

    public List<POI> getMidPOIs() {
        if (this.c != null) {
            return this.c.a.p();
        }
        return null;
    }

    public te getRegoPOI() {
        if (this.f != null) {
            return this.f.j;
        }
        return null;
    }

    public View getMapSuspendView() {
        this.b = new pc(this, getContext());
        this.b.b = this.ac;
        this.b.b();
        return this.b.getSuspendView();
    }

    /* access modifiers changed from: private */
    public void a(int i2) {
        if (i2 < 0) {
            if (!e()) {
                if (!this.T.hasMessages(1008)) {
                    DriveUtil.refreshTraffic(getMapView());
                    this.c.b();
                    l();
                    return;
                }
                ToastHelper.showLongToast(getResources().getString(R.string.route_car_toast_refresh_route));
            }
        } else if (e()) {
            sa.a(i2, (int) SDKFactory.getCoreType);
        } else if (!this.T.hasMessages(1008) || this.V == 1) {
            DriveUtil.refreshTraffic(getMapView());
            this.c.a(sb.b("refreshRouteInCarRoutePage", i2, null));
            l();
        } else {
            ToastHelper.showLongToast(getResources().getString(R.string.route_car_toast_refresh_route));
            sa.a(i2, (int) UCMPackageInfo.hadInstallUCMobile);
        }
    }

    private void l() {
        if (this.T != null) {
            this.T.removeMessages(1008);
            this.T.sendEmptyMessageDelayed(1008, 10000);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void b(final boolean z2) {
        if (this.p != null) {
            if (this.N == null) {
                this.N = AnimationUtils.loadAnimation(getContext(), R.anim.tips_hiding);
            } else if (!this.N.hasEnded()) {
                return;
            }
            this.N.setFillAfter(true);
            this.N.setAnimationListener(new AnimationListener() {
                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                    if (AjxRouteTruckResultPage.this.p != null) {
                        AjxRouteTruckResultPage.this.p.i();
                    }
                }

                public final void onAnimationEnd(Animation animation) {
                    if (!(!AjxRouteTruckResultPage.this.isAlive() || AjxRouteTruckResultPage.this.o == null || AjxRouteTruckResultPage.this.o.getAnimation() == null)) {
                        AjxRouteTruckResultPage.this.o.clearAnimation();
                        AjxRouteTruckResultPage.this.m();
                        if (AjxRouteTruckResultPage.this.p != null) {
                            if (z2) {
                                AjxRouteTruckResultPage.this.p.m();
                                return;
                            }
                            AjxRouteTruckResultPage.this.p.n();
                        }
                    }
                }
            });
            this.o.startAnimation(this.N);
        }
    }

    /* access modifiers changed from: private */
    public void m() {
        this.o.setVisibility(8);
    }

    @NonNull
    public final Handler c() {
        return this.q;
    }

    public void onRouteStateChanged(int i2, Object... objArr) {
        JSONObject jSONObject;
        this.V = i2;
        int i3 = 0;
        if (i2 == 0) {
            this.c.e = DriveUtil.getTruckRoutingChoice();
            this.f = objArr[0];
            this.f.g = getStartPOI();
            this.f.h = getEndPOI();
            this.f.i = getMidPOIs();
            IRouteUI b2 = this.c.a.b();
            if (this.f != null && (this.f.b() || (b2 != null && b2.o()))) {
                ox oxVar = this.c;
                if (!oxVar.b.d && !oxVar.f) {
                    ahm.c(new Runnable() {
                        public final void run() {
                            if (ox.this.c != null && ox.this.d != null) {
                                chk.a(ox.this.c, ox.this.h, ox.this.d, RouteType.TRUCK);
                            }
                        }
                    });
                }
            }
            l();
            k();
            a();
            if (this.p != null) {
                this.p.a();
                if (!this.e && !this.d) {
                    this.p.f = this.aa;
                    this.p.a(this.f);
                    if (this.p.f() <= 0) {
                        pd.a(8);
                    } else {
                        pd.a(4);
                    }
                    this.p.c = this.P;
                    if (isStarted()) {
                        this.p.a(11000);
                    } else {
                        this.p.m();
                    }
                }
                if (!this.d && !this.e && !this.D && this.j.b() != null) {
                    this.p.d();
                }
            }
            if (!d()) {
                if (!(this.f != null && this.c.f) && !this.d && NetworkReachability.b()) {
                    POI o2 = this.c.a.o();
                    if (DriveUtil.isNeedSearchCarScene(o2, this.Y)) {
                        AnonymousClass11 r5 = new op() {
                            public final void a(oo ooVar) {
                                AjxRouteTruckResultPage.a(AjxRouteTruckResultPage.this, ooVar);
                            }
                        };
                        NaviinfoRequest naviinfoRequest = new NaviinfoRequest();
                        naviinfoRequest.b = o2.getId();
                        naviinfoRequest.c = DriveUtil.getSearchCarSceneParam(o2);
                        PoiRequestHolder.getInstance().sendNaviinfo(naviinfoRequest, new CarSceneSearchAosCallback(o2, r5));
                    }
                }
            }
            this.Y = false;
            if (!(!this.R || this.f == null || this.f.h == null || this.f.h.getPoiExtra() == null || !this.f.h.getPoiExtra().containsKey("sub_poi_name"))) {
                this.R = false;
                String name = this.f.h.getName();
                Serializable serializable = this.f.h.getPoiExtra().get("main_poi");
                if (serializable != null && (serializable instanceof POI)) {
                    name = ((POI) serializable).getName();
                }
                StringBuilder sb = new StringBuilder("目的地已为您设置为\n");
                sb.append(name);
                sb.append(Token.SEPARATOR);
                sb.append(this.f.h.getPoiExtra().get("sub_poi_name"));
                ToastHelper.showToast(sb.toString());
            }
            if (e()) {
                new re();
                if (re.a()) {
                    ToastHelper.showToast(getString(R.string.offline_message_tbt_success));
                } else {
                    ToastHelper.showToast(getString(R.string.offline_message_tbt_success_first));
                }
            }
            if (this.E > 0) {
                d.a.a(this.E, 10000, (String) null);
                defpackage.eqc.a.a.b = false;
            }
            this.E = -1;
        } else if (2 == i2) {
            if (n() && !o()) {
                p();
            }
            if (!(this.o == null || this.o.getAnimation() == null || this.o.getAnimation().hasEnded())) {
                this.o.getAnimation().cancel();
            }
            a();
            b();
        } else if (1 == i2) {
            if (objArr == null || objArr[0] == null) {
                jSONObject = null;
            } else {
                jSONObject = objArr[0];
            }
            int i4 = SDKFactory.getCoreType;
            if (jSONObject != null) {
                int a2 = agd.a(jSONObject, "errorCode");
                if (a2 > 0) {
                    i4 = a2;
                }
            }
            if (this.E > 0) {
                d.a.a(this.E, i4, (String) null);
                defpackage.eqc.a.a.b = false;
            }
            this.E = -1;
        }
        Object[] objArr2 = new Object[3];
        if (i2 == 0) {
            objArr2[0] = Boolean.valueOf(d());
            objArr2[1] = Boolean.valueOf(e());
            StringBuilder sb2 = new StringBuilder("updateSuspendIcon---isOffline=");
            sb2.append(objArr2[1]);
            AMapLog.d("AjxRouteTruckResultPage", sb2.toString());
            if (!(this.f == null || this.f.a() == null || this.f.a().q == null || this.f.a().q.b() == null)) {
                i3 = this.f.a().q.b().size();
            }
            objArr2[2] = Integer.valueOf(i3);
        }
        pd pdVar = this.Z;
        boolean e2 = e();
        String choiceString = DriveUtil.getChoiceString(DriveUtil.getTruckRoutingChoice(), 1);
        BaseMapWidgetPresenter baseMapWidgetPresenter = (BaseMapWidgetPresenter) Stub.getMapWidgetManager().getPresenter(WidgetType.PATHPREFERENCEANDSCALE);
        if (baseMapWidgetPresenter != null) {
            IMapWidget widget = baseMapWidgetPresenter.getWidget();
            if (widget instanceof RecommendAndScaleWidget) {
                pdVar.g = ((RecommendAndScaleWidget) widget).getRecommendView();
            }
            if (pdVar.g != null) {
                TextView textView = (TextView) pdVar.g.findViewById(R.id.recommend_tv);
                if (textView != null) {
                    textView.setText(choiceString);
                    if ((e2 || !agr.b(pdVar.d)) && !TextUtils.isEmpty(choiceString) && choiceString.contains(pdVar.d.getResources().getString(R.string.car_method_no_block))) {
                        textView.setTextColor(textView.getResources().getColor(R.color.f_c_2_a));
                    } else {
                        textView.setTextColor(textView.getResources().getColor(R.color.f_c_2));
                    }
                }
                NoDBClickUtil.a(pdVar.g, (OnClickListener) new OnClickListener() {
                    public final void onClick(View view) {
                        pd pdVar = pd.this;
                        if (!pdVar.b) {
                            pdVar.c = new DriveRecommendView(pdVar.d, new OnClickListener() {
                                public final void onClick(View view) {
                                    pd.this.a();
                                }
                            }, 1);
                            pdVar.c.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                            pdVar.c.setIsOffline(pdVar.a.e());
                            pdVar.c.updateViewStatus();
                            int[] iArr = new int[2];
                            view.getLocationOnScreen(iArr);
                            agn.a(view.getContext(), 10.0f);
                            int height = iArr[1] + view.getHeight() + agn.a(view.getContext(), 10.0f);
                            View findViewById = ((Activity) pdVar.d).findViewById(16908290);
                            if (findViewById != null) {
                                height = findViewById.getHeight() - height;
                            }
                            pdVar.c.setBottomDistance(height);
                            IRouteUI b = pdVar.e.b();
                            if (b != null) {
                                if (pdVar.f == null) {
                                    pdVar.f = b.a();
                                }
                                b.a(new ContainerType[]{ContainerType.FLOW_VIEW, ContainerType.HEAD_VIEW, ContainerType.CONTAINER_VIEW});
                                b.a((View) pdVar.c);
                                pdVar.c.showRecommendViewAnim();
                                pdVar.b = true;
                            }
                        }
                    }
                });
            }
        }
    }

    public void onRouteFocusIndexChanged(int i2) {
        if (this.f != null) {
            this.f.e = i2;
        }
        if (this.p != null) {
            AjxTipsManager ajxTipsManager = this.p;
            if (ajxTipsManager.g != null && i2 < ajxTipsManager.g.f.size()) {
                ajxTipsManager.h = ajxTipsManager.g.a(i2).n;
            }
        }
    }

    public final boolean d() {
        if (this.f != null) {
            pg a2 = this.f.a();
            if (a2 != null) {
                StringBuilder sb = new StringBuilder("isLongScene length==");
                sb.append(a2.c);
                AMapLog.d("AjxRouteTruckResultPage", sb.toString());
                if (a2.c >= 100000) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public final boolean e() {
        if (this.f == null || this.f.f == null || this.f.f.get(0) == null || this.f.f.get(0).d) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static void b(String str, JSONObject jSONObject) {
        if (str != null && (str.equals("B026") || str.endsWith("B030"))) {
            return;
        }
        if (jSONObject == null) {
            LogManager.actionLogV2("P00330", str);
        } else {
            LogManager.actionLogV2("P00330", str, jSONObject);
        }
    }

    public bgo getPresenter() {
        return this.c;
    }

    /* access modifiers changed from: private */
    public static boolean n() {
        return !tk.a() || (tk.c() && "0".equals(tk.a(tk.b())));
    }

    private static boolean o() {
        ITruckGuideManager iTruckGuideManager = (ITruckGuideManager) ank.a(ITruckGuideManager.class);
        if (iTruckGuideManager != null) {
            return iTruckGuideManager.isGuideShowing();
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void p() {
        if (this.M == null || !isViewLayerShowing(this.M)) {
            com.autonavi.widget.ui.AlertView.a aVar = new com.autonavi.widget.ui.AlertView.a(getContext());
            aVar.a(R.string.navi_type_supplement_title_page);
            aVar.b(R.string.navi_type_supplement_summary_page);
            aVar.a(R.string.navi_type_go_to_supplement, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    tk.a("1", tk.b());
                    AjxRouteTruckResultPage.B(AjxRouteTruckResultPage.this);
                    AjxRouteTruckResultPage.this.dismissViewLayer(AjxRouteTruckResultPage.this.M);
                    AjxRouteTruckResultPage.this.M = null;
                    AjxRouteTruckResultPage.b((String) "B002", LogUtil.createJSONObj((String) "check"));
                }
            });
            aVar.b(R.string.cancle, (defpackage.ern.a) new defpackage.ern.a() {
                public final void onClick(AlertView alertView, int i) {
                    tk.a("1", tk.b());
                    AjxRouteTruckResultPage.this.dismissViewLayer(AjxRouteTruckResultPage.this.M);
                    AjxRouteTruckResultPage.this.M = null;
                    AjxRouteTruckResultPage.b((String) "B002", LogUtil.createJSONObj((String) "cancel"));
                }
            });
            this.M = aVar.a();
            showViewLayer(this.M);
            b((String) "B001", LogUtil.createJSONObj(!tk.a() ? "incomplete" : "abnormal"));
        }
    }

    static /* synthetic */ void d(AjxRouteTruckResultPage ajxRouteTruckResultPage) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_drive/src/car/restrict_page/CarRestrictPage.page.js");
        pageBundle.putInt("cartype", 1);
        pageBundle.putLong("resultId", ajxRouteTruckResultPage.f.c);
        pageBundle.putLong("pathId", ajxRouteTruckResultPage.f.a(ajxRouteTruckResultPage.f.e).a);
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPageForResult((String) "amap.basemap.action.car_restrict", pageBundle, 140);
        }
    }

    static /* synthetic */ void i(AjxRouteTruckResultPage ajxRouteTruckResultPage) {
        if (ajxRouteTruckResultPage.n != null && ajxRouteTruckResultPage.n.getVisibility() == 0) {
            ajxRouteTruckResultPage.n.startAnimation(AnimationUtils.loadAnimation(ajxRouteTruckResultPage.getContext(), R.anim.tips_hiding));
        }
    }

    static /* synthetic */ void a(AjxRouteTruckResultPage ajxRouteTruckResultPage, final int i2, final int i3) {
        if (ajxRouteTruckResultPage.p != null && ajxRouteTruckResultPage.p.e()) {
            ajxRouteTruckResultPage.b(false);
        }
        if (ajxRouteTruckResultPage.p != null) {
            ajxRouteTruckResultPage.p.c();
        }
        ajxRouteTruckResultPage.a(true, (AnimationListener) new AnimationListener() {
            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteTruckResultPage.this.isAlive() && AjxRouteTruckResultPage.this.f != null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("data_type", i2);
                    pageBundle.putInt("route_type", RouteType.TRUCK.getValue());
                    pageBundle.putObject(RouteItem.ROUTE_DATA, AjxRouteTruckResultPage.this.f);
                    pageBundle.putObject("event_id", Integer.valueOf(i3));
                    if (AjxRouteTruckResultPage.this.y != null) {
                        pageBundle.putInt("focusIndex", AjxRouteTruckResultPage.this.y.getFocusIndex());
                    }
                    pageBundle.putLongArray("result_id", AjxRouteTruckResultPage.this.f.a);
                    AjxRouteTruckResultPage.this.startPageForResult(AjxRouteCarResultEventDetailPage.class, pageBundle, 150);
                }
            }
        });
    }

    static /* synthetic */ void k(AjxRouteTruckResultPage ajxRouteTruckResultPage) {
        if (!TextUtils.isEmpty(DriveUtil.getTruckCarPlateNumber())) {
            DriveUtil.setTruckAvoidSwitch(true);
            ajxRouteTruckResultPage.c.b();
            return;
        }
        ToastHelper.showToast(ajxRouteTruckResultPage.getString(R.string.car_plate_empty));
    }

    static /* synthetic */ void l(AjxRouteTruckResultPage ajxRouteTruckResultPage) {
        if (ajxRouteTruckResultPage.f.a(ajxRouteTruckResultPage.f.e).p.type == 1) {
            ajxRouteTruckResultPage.c.a(null, null, null, null, false, true);
        }
    }

    static /* synthetic */ void a(AjxRouteTruckResultPage ajxRouteTruckResultPage, int i2, int i3, int i4, String str, String str2, String str3, long[] jArr, int i5) {
        AjxRouteTruckResultPage ajxRouteTruckResultPage2 = ajxRouteTruckResultPage;
        if (ajxRouteTruckResultPage2.p != null && ajxRouteTruckResultPage2.p.e()) {
            ajxRouteTruckResultPage2.b(false);
        }
        if (ajxRouteTruckResultPage2.p != null) {
            ajxRouteTruckResultPage2.p.c();
        }
        final int i6 = i2;
        final int i7 = i3;
        final int i8 = i4;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final int i9 = i5;
        final long[] jArr2 = jArr;
        AnonymousClass3 r0 = new AnimationListener() {
            final /* synthetic */ int a = 4;

            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteTruckResultPage.this.isAlive() && AjxRouteTruckResultPage.this.f != null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("data_type", this.a);
                    pageBundle.putInt("route_type", RouteType.TRUCK.getValue());
                    pageBundle.putObject(RouteItem.ROUTE_DATA, AjxRouteTruckResultPage.this.f);
                    pageBundle.putInt("forbiddenId", i6);
                    pageBundle.putInt("forbiddenType", i7);
                    pageBundle.putInt("vehicleType", i8);
                    pageBundle.putString("timeDescription", str4);
                    pageBundle.putString("roadNameString", str5);
                    pageBundle.putString("nextRoadNameString", str6);
                    pageBundle.putInt("focusIndex", i9);
                    pageBundle.putLongArray("result_id", jArr2);
                    AjxRouteTruckResultPage.this.startPageForResult(AjxRouteCarResultEventDetailPage.class, pageBundle, 150);
                }
            }
        };
        ajxRouteTruckResultPage2.a(true, (AnimationListener) r0);
    }

    static /* synthetic */ void a(AjxRouteTruckResultPage ajxRouteTruckResultPage, int i2, long[] jArr, int i3, int i4) {
        if (ajxRouteTruckResultPage.p != null && ajxRouteTruckResultPage.p.e()) {
            ajxRouteTruckResultPage.b(false);
        }
        if (ajxRouteTruckResultPage.p != null) {
            ajxRouteTruckResultPage.p.c();
        }
        final int i5 = i2;
        final int i6 = i3;
        final int i7 = i4;
        final long[] jArr2 = jArr;
        AnonymousClass29 r1 = new AnimationListener() {
            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteTruckResultPage.this.isAlive() && AjxRouteTruckResultPage.this.f != null) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("data_type", i5);
                    pageBundle.putInt("route_type", RouteType.TRUCK.getValue());
                    pageBundle.putObject(RouteItem.ROUTE_DATA, AjxRouteTruckResultPage.this.f);
                    pageBundle.putInt("event_id", i6);
                    pageBundle.putInt("focusIndex", i7);
                    pageBundle.putLongArray("result_id", jArr2);
                    AjxRouteTruckResultPage.this.startPageForResult(AjxRouteCarResultEventDetailPage.class, pageBundle, 150);
                }
            }
        };
        ajxRouteTruckResultPage.a(true, (AnimationListener) r1);
    }

    static /* synthetic */ void o(AjxRouteTruckResultPage ajxRouteTruckResultPage) {
        String str;
        if (ajxRouteTruckResultPage.c != null) {
            ox oxVar = ajxRouteTruckResultPage.c;
            if ((oxVar.a == null || oxVar.a.p() == null || oxVar.a.p().size() <= 0) ? false : true) {
                ToastHelper.showToast(ajxRouteTruckResultPage.getContext().getString(R.string.drive_etd_function_not_support));
                return;
            }
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("url", "path://amap_bundle_drive/src/etd/page/RouteETDPage.page.js");
        try {
            if (ajxRouteTruckResultPage.c != null) {
                str = ajxRouteTruckResultPage.c.c();
            } else {
                str = "";
            }
            POI startPOI = ajxRouteTruckResultPage.getStartPOI();
            POI endPOI = ajxRouteTruckResultPage.getEndPOI();
            List<POI> midPOIs = ajxRouteTruckResultPage.getMidPOIs();
            String str2 = null;
            if (endPOI != null) {
                str2 = JSON.toJSONString(ou.a(0, new ta(startPOI, endPOI, midPOIs, CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN)));
            } else {
                tq.a("AjxRouteTruckResultPage", "getRequestRouteParam", "end = null");
            }
            if (str2 == null) {
                str2 = "";
            }
            if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
                AMapLog.e("AjxRouteTruckResultPage", "startRouteETDPage() params is empty!");
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("points", new JSONObject(str));
            jSONObject.put("route_params", new JSONObject(str2));
            pageBundle.putString("jsData", jSONObject.toString());
            ajxRouteTruckResultPage.startPage(AjxRouteETDPage.class, pageBundle);
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("startRouteETDPage() params err! ");
            sb.append(e2.toString());
            AMapLog.e("AjxRouteTruckResultPage", sb.toString());
        }
    }

    static /* synthetic */ void g() {
        DriveUtil.setCarPlateSettingShowCount(DriveUtil.getCarPlateSettingShowCount() + 1);
        DriveUtil.setCarPlateLastSettingTime(System.currentTimeMillis());
    }

    static /* synthetic */ void h() {
        DriveUtil.setCarPlateOpenAvoidLimitedNoticeCount(DriveUtil.getCarPlateOpenAvoidLimitedNoticeCount() + 1);
        DriveUtil.setCarPlateOpenAvoidLimitedLastNoticeTime(System.currentTimeMillis());
    }

    static /* synthetic */ void a(AjxRouteTruckResultPage ajxRouteTruckResultPage, float f2) {
        if (ajxRouteTruckResultPage.H == null && ajxRouteTruckResultPage.c.a.b() != null) {
            ajxRouteTruckResultPage.H = ajxRouteTruckResultPage.c.a.b().b();
        }
        if (ajxRouteTruckResultPage.H != null) {
            ajxRouteTruckResultPage.H.setAlpha(f2);
        }
    }

    static /* synthetic */ void r(AjxRouteTruckResultPage ajxRouteTruckResultPage) {
        if (ajxRouteTruckResultPage.Z != null && ajxRouteTruckResultPage.Z.b) {
            ajxRouteTruckResultPage.Z.a();
        }
        if (ajxRouteTruckResultPage.y != null) {
            ajxRouteTruckResultPage.y.hideSearchAlongView();
            ajxRouteTruckResultPage.y.setCarResultMapState();
        }
    }

    static /* synthetic */ void a(AjxRouteTruckResultPage ajxRouteTruckResultPage, oo ooVar) {
        if (!ajxRouteTruckResultPage.e && ooVar != null && ooVar.a()) {
            if (ajxRouteTruckResultPage.n == null) {
                ajxRouteTruckResultPage.n = (ViewGroup) ajxRouteTruckResultPage.getContentView().findViewById(R.id.viewstub_car_scene_layout);
                ajxRouteTruckResultPage.O = (CarSceneTip) ajxRouteTruckResultPage.n.findViewById(R.id.route_carscenetip);
            }
            ajxRouteTruckResultPage.n.setVisibility(0);
            if (ajxRouteTruckResultPage.O != null) {
                try {
                    ajxRouteTruckResultPage.O.setData(ooVar);
                    ajxRouteTruckResultPage.O.setVisibility(0);
                    ajxRouteTruckResultPage.O.setOnTipClickListener(new com.amap.bundle.drive.result.autonavisearchmanager.view.CarSceneTip.a() {
                        public final void a(defpackage.oo.a aVar) {
                            AjxRouteTruckResultPage.this.a((int) SecExceptionCode.SEC_ERROE_OPENSDK_INVALID_LENGTH, 0);
                            if (AjxRouteTruckResultPage.this.p != null) {
                                AjxRouteTruckResultPage.this.p.d(0);
                            }
                            if (aVar != null) {
                                AjxRouteTruckResultPage.this.R = true;
                                POI o = AjxRouteTruckResultPage.this.c.a.o();
                                if (aVar.a == 101) {
                                    ArrayList arrayList = new ArrayList();
                                    POI poi = aVar.d;
                                    if (poi.getEntranceList() != null && poi.getEntranceList().size() > 0) {
                                        arrayList.addAll(poi.getEntranceList());
                                    }
                                    POI createPOI = POIFactory.createPOI(poi.getName(), poi.getPoint());
                                    createPOI.setId(poi.getId());
                                    createPOI.setPid(o.getId());
                                    createPOI.setType(poi.getType());
                                    createPOI.getPoiExtra().put("sub_poi_name", aVar.c);
                                    createPOI.getPoiExtra().put("main_poi", o);
                                    createPOI.setEntranceList(arrayList);
                                    createPOI.getPoiExtra().put("scene_poi", Boolean.TRUE);
                                    createPOI.setEndPoiExtension(poi.getEndPoiExtension());
                                    createPOI.setTransparent(poi.getTransparent());
                                    AjxRouteTruckResultPage.this.c.a(createPOI);
                                    AjxRouteTruckResultPage.this.c.a(createPOI, null, null, null, true, false);
                                    return;
                                }
                                if (o != null) {
                                    ArrayList arrayList2 = (ArrayList) aVar.b;
                                    POI createPOI2 = POIFactory.createPOI(o.getName(), o.getPoint());
                                    createPOI2.setId(o.getId());
                                    createPOI2.setType(o.getType());
                                    createPOI2.getPoiExtra().put("build_type", Integer.valueOf(0));
                                    createPOI2.getPoiExtra().put("is_car_scene_request", Boolean.TRUE);
                                    createPOI2.getPoiExtra().put("sub_poi_name", aVar.c);
                                    createPOI2.getPoiExtra().put("main_poi", o);
                                    createPOI2.getPoiExtra().put("build_type_train_station_entrance_exit_poies", arrayList2);
                                    createPOI2.getPoiExtra().put("scene_poi", Boolean.TRUE);
                                    createPOI2.setEndPoiExtension(o.getEndPoiExtension());
                                    createPOI2.setTransparent(o.getTransparent());
                                    AjxRouteTruckResultPage.this.c.a(createPOI2);
                                    AjxRouteTruckResultPage.this.c.a(createPOI2, o, arrayList2, null, true, false);
                                }
                            }
                        }
                    });
                    qd.a(ajxRouteTruckResultPage.n);
                    ajxRouteTruckResultPage.a((int) SecExceptionCode.SEC_ERROE_OPENSDK_DECODE_FAILED, UIUtils.dip2px(44, ajxRouteTruckResultPage.O.getContext()));
                    if (ajxRouteTruckResultPage.p != null) {
                        ajxRouteTruckResultPage.p.d(UIUtils.dip2px(44, ajxRouteTruckResultPage.O.getContext()));
                    }
                } catch (Exception unused) {
                }
            }
        }
    }

    static /* synthetic */ void a(AjxRouteTruckResultPage ajxRouteTruckResultPage, String str) {
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(str)) {
            pageBundle.putString("jsData", str);
        }
        pageBundle.putString("url", ModuleRouteDriveResult.CAR_PREVIEW);
        pageBundle.putBoolean("is_from_favorite", ajxRouteTruckResultPage.d);
        pageBundle.putBoolean("is_from_etrip", ajxRouteTruckResultPage.e);
        pageBundle.putInt("route_car_type_key", 1);
        pageBundle.putObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ, ajxRouteTruckResultPage.f);
        ajxRouteTruckResultPage.startPageForResult(AjxRouteCarResultBrowserPage.class, pageBundle, 200);
    }

    static /* synthetic */ void c(AjxRouteTruckResultPage ajxRouteTruckResultPage, String str) {
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(str)) {
            pageBundle.putString("jsData", str);
        }
        pageBundle.putString("url", ModuleRouteDriveResult.CAR_MOCK_NAVI);
        pageBundle.putInt("route_car_type_key", RouteType.TRUCK.getValue());
        ajxRouteTruckResultPage.startPage(AjxRouteCarNaviSimulatePage.class, pageBundle);
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x00ce  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void d(com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r9, java.lang.String r10) {
        /*
            r0 = 0
            r1 = 15
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x003c }
            r2.<init>(r10)     // Catch:{ JSONException -> 0x003c }
            java.lang.String r3 = "focusIndex"
            int r3 = r2.optInt(r3)     // Catch:{ JSONException -> 0x003c }
            java.lang.String r4 = "fromType"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x003a }
            if (r4 == 0) goto L_0x001d
            java.lang.String r4 = "fromType"
            int r4 = r2.optInt(r4)     // Catch:{ JSONException -> 0x003a }
            r1 = r4
        L_0x001d:
            java.lang.String r4 = "routeID"
            org.json.JSONArray r2 = r2.optJSONArray(r4)     // Catch:{ JSONException -> 0x003a }
            int r4 = r2.length()     // Catch:{ JSONException -> 0x003a }
            int[] r5 = new int[r4]     // Catch:{ JSONException -> 0x003a }
        L_0x002a:
            if (r0 >= r4) goto L_0x0035
            int r6 = r2.getInt(r0)     // Catch:{ JSONException -> 0x003a }
            r5[r0] = r6     // Catch:{ JSONException -> 0x003a }
            int r0 = r0 + 1
            goto L_0x002a
        L_0x0035:
            long r4 = com.autonavi.jni.eyrie.amap.tbt.NaviManager.createPathResult(r5)     // Catch:{ JSONException -> 0x003a }
            goto L_0x0044
        L_0x003a:
            r0 = move-exception
            goto L_0x003f
        L_0x003c:
            r2 = move-exception
            r0 = r2
            r3 = 0
        L_0x003f:
            r0.printStackTrace()
            r4 = 0
        L_0x0044:
            java.lang.String r0 = "AjxRouteTruckResultPage"
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
            qb r6 = r9.j
            com.autonavi.common.model.POI r6 = r6.n()
            qb r7 = r9.j
            com.autonavi.common.model.POI r7 = r7.o()
            com.amap.bundle.drivecommon.model.NavigationResult r2 = defpackage.rn.a(r3, r6, r7, r2)
            r9.getContext()
            java.lang.String r3 = "0"
            java.lang.String r3 = defpackage.ru.a(r3)
            qb r6 = r9.j
            com.autonavi.common.model.POI r6 = r6.n()
            qb r7 = r9.j
            com.autonavi.common.model.POI r7 = r7.o()
            r0.setNaviResultData(r6, r7, r2, r3)
            qb r2 = r9.j
            com.autonavi.common.model.POI r2 = r2.n()
            com.autonavi.common.model.GeoPoint r2 = r2.getPoint()
            r0.setShareStartPos(r2)
            qb r2 = r9.j
            com.autonavi.common.model.POI r2 = r2.o()
            com.autonavi.common.model.GeoPoint r2 = r2.getPoint()
            r0.setShareEndPos(r2)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            qb r6 = r9.j
            java.util.List r6 = r6.p()
            if (r6 == 0) goto L_0x00ef
            qb r6 = r9.j
            java.util.List r6 = r6.p()
            java.util.Iterator r6 = r6.iterator()
        L_0x00d8:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x00ef
            java.lang.Object r7 = r6.next()
            com.autonavi.common.model.POI r7 = (com.autonavi.common.model.POI) r7
            com.autonavi.common.model.GeoPoint r8 = r7.getPoint()
            r2.add(r8)
            r3.add(r7)
            goto L_0x00d8
        L_0x00ef:
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
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.d(com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage, java.lang.String):void");
    }

    static /* synthetic */ void a(AjxRouteTruckResultPage ajxRouteTruckResultPage, int i2, long[] jArr, int i3, double d2, double d3, int i4, String str) {
        AjxRouteTruckResultPage ajxRouteTruckResultPage2 = ajxRouteTruckResultPage;
        if (ajxRouteTruckResultPage2.p != null && ajxRouteTruckResultPage2.p.e()) {
            ajxRouteTruckResultPage2.b(false);
        }
        if (ajxRouteTruckResultPage2.p != null) {
            ajxRouteTruckResultPage2.p.c();
        }
        final int i5 = i3;
        final long[] jArr2 = jArr;
        final double d4 = d2;
        final double d5 = d3;
        final int i6 = i4;
        final String str2 = str;
        final int i7 = i2;
        AnonymousClass2 r0 = new AnimationListener() {
            final /* synthetic */ int a = 3;

            public final void onAnimationRepeat(Animation animation) {
            }

            public final void onAnimationStart(Animation animation) {
            }

            public final void onAnimationEnd(Animation animation) {
                if (AjxRouteTruckResultPage.this.isAlive()) {
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putInt("data_type", this.a);
                    pageBundle.putInt("route_type", RouteType.TRUCK.getValue());
                    pageBundle.putObject(RouteItem.ROUTE_DATA, AjxRouteTruckResultPage.this.f);
                    pageBundle.putInt("focusIndex", i5);
                    pageBundle.putLongArray("result_id", jArr2);
                    pageBundle.putDouble("opanlayer_lon", d4);
                    pageBundle.putDouble("opanlayer_lat", d5);
                    pageBundle.putInt("opanlayer_z", i6);
                    pageBundle.putString("opanlayer_poiId", str2);
                    pageBundle.putInt("event_id", i7);
                    AjxRouteTruckResultPage.this.startPageForResult(AjxRouteCarResultEventDetailPage.class, pageBundle, 150);
                }
            }
        };
        ajxRouteTruckResultPage2.a(true, (AnimationListener) r0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x001f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void e(com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage r3, java.lang.String r4) {
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
            ph r1 = r3.f
            if (r1 == 0) goto L_0x0034
            boolean r1 = android.text.TextUtils.isEmpty(r4)
            if (r1 == 0) goto L_0x0026
            goto L_0x0034
        L_0x0026:
            te r1 = new te
            r1.<init>()
            r1.a = r4
            r1.b = r0
            ph r3 = r3.f
            r3.j = r1
            return
        L_0x0034:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage.e(com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage, java.lang.String):void");
    }

    static /* synthetic */ void y(AjxRouteTruckResultPage ajxRouteTruckResultPage) {
        if (ajxRouteTruckResultPage.y != null) {
            ajxRouteTruckResultPage.y.callbackErrorReportClick(true);
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("type", AMapAppGlobal.getApplication().getString(R.string.action_log_type_truck));
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    static /* synthetic */ IAjxContext A(AjxRouteTruckResultPage ajxRouteTruckResultPage) {
        if (ajxRouteTruckResultPage.mAjxView == null) {
            return null;
        }
        return ajxRouteTruckResultPage.mAjxView.getAjxContext();
    }

    static /* synthetic */ void a(AjxRouteTruckResultPage ajxRouteTruckResultPage, int i2, String str) {
        if (TextUtils.equals(str, DriveUtil.getTruckRoutingChoice())) {
            sa.a(i2, 10000);
            return;
        }
        DriveUtil.putTruckRoutingChoice(str);
        DriveUtil.refreshTraffic(ajxRouteTruckResultPage.getMapView());
        ajxRouteTruckResultPage.l();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("method", str);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        ajxRouteTruckResultPage.c.a(sb.b("setRouteParamsInCarRoutePage", i2, jSONObject));
    }

    static /* synthetic */ void B(AjxRouteTruckResultPage ajxRouteTruckResultPage) {
        String truckCarPlateNumber = DriveUtil.getTruckCarPlateNumber();
        if (!TextUtils.isEmpty(truckCarPlateNumber)) {
            String carEditPath = DriveUtil.getCarEditPath(truckCarPlateNumber);
            StringBuilder sb = new StringBuilder();
            sb.append(carEditPath);
            sb.append("&perfectTruck=1");
            ajxRouteTruckResultPage.startScheme(new Intent("android.intent.action.VIEW", Uri.parse(sb.toString())));
        }
    }
}
