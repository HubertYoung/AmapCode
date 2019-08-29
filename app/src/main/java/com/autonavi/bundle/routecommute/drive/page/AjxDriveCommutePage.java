package com.autonavi.bundle.routecommute.drive.page;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.amap.bundle.drivecommon.navi.navidata.NavigationDataResult;
import com.amap.bundle.drivecommon.tools.TripSpUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.schoolbus.notifcation.ISchoolbusStatusMangger;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommute.modlue.ModuleDriveRouteCommute;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LOCATION_SCENE;
import com.autonavi.common.impl.Locator.LocationPreference;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.drive.ajx.AjxDriveService;
import com.autonavi.jni.drive.ajx.GTraceObserver;
import com.autonavi.jni.drive.ajx.TracePoint;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.LocationMode.LocationGpsOnly;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.AjxPageStateInvoker;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.JsFunctionCallback;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AjxLifeCircleListener;
import com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.sdk.location.LocationInstrument;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
@SuppressFBWarnings({"AMAP_OPT_X", "BIT_SIGNED_CHECK", "DB_DUPLICATE_BRANCHES", "BC_BAD_CAST_TO_CONCRETE_COLLECTION"})
@LocationPreference(availableOnBackground = true)
public class AjxDriveCommutePage extends MapBasePage implements bgm, bgo, Callback<Status>, LocationGpsOnly, launchModeSingleTask, d {
    public static final String AJX_DRIVE_COMMUTE_END = "path://amap_bundle_routecommute/src/drive_commute/pages/CommuteEndPage.page.js";
    public static final String AJX_PAGE_PRELOAD_URL = "path://amap_bundle_routecommute/src/drive_commute/pages/DriveCommutePreload.js";
    public static final String AJX_PAGE_URL = "path://amap_bundle_routecommute/src/drive_commute/pages/DriveCommutePage.page.js";
    private static final String DRIVE_COMMUTE_NAVI_BUTTON_ID = "B015";
    private static final String DRIVE_COMMUTE_NAVI_PAGE_ID = "P00444";
    public static final String NAVI_TYPE = "navi_type";
    public static final String NAVI_TYPE_CAR = "car";
    private final String TAG = "NewRouteBoardPage";
    mq a = new mq() {
        /* JADX WARNING: Removed duplicated region for block: B:12:0x0028  */
        /* JADX WARNING: Removed duplicated region for block: B:16:0x0044  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(java.lang.String r3, java.lang.String r4) {
            /*
                r2 = this;
                int r0 = r3.hashCode()
                r1 = -1367633817(0xffffffffae7b9067, float:-5.7199048E-11)
                if (r0 == r1) goto L_0x0019
                r1 = 553280922(0x20fa659a, float:4.2418882E-19)
                if (r0 == r1) goto L_0x000f
                goto L_0x0023
            L_0x000f:
                java.lang.String r0 = "carNavi"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0023
                r3 = 0
                goto L_0x0024
            L_0x0019:
                java.lang.String r0 = "carEnd"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0023
                r3 = 1
                goto L_0x0024
            L_0x0023:
                r3 = -1
            L_0x0024:
                switch(r3) {
                    case 0: goto L_0x0044;
                    case 1: goto L_0x0028;
                    default: goto L_0x0027;
                }
            L_0x0027:
                goto L_0x004f
            L_0x0028:
                esb r3 = defpackage.esb.a.a
                java.lang.Class<ms> r0 = defpackage.ms.class
                esc r3 = r3.a(r0)
                ms r3 = (defpackage.ms) r3
                if (r3 == 0) goto L_0x0039
                r3.b()
            L_0x0039:
                com.autonavi.bundle.routecommute.drive.page.AjxDriveCommutePage r3 = com.autonavi.bundle.routecommute.drive.page.AjxDriveCommutePage.this
                r3.isJumpAction = true
                com.autonavi.bundle.routecommute.drive.page.AjxDriveCommutePage r3 = com.autonavi.bundle.routecommute.drive.page.AjxDriveCommutePage.this
                r3.startNaviEndPage(r4)
                goto L_0x004f
            L_0x0044:
                com.autonavi.bundle.routecommute.drive.page.AjxDriveCommutePage r3 = com.autonavi.bundle.routecommute.drive.page.AjxDriveCommutePage.this
                r3.startNaviPage(r4)
                com.autonavi.bundle.routecommute.drive.page.AjxDriveCommutePage r3 = com.autonavi.bundle.routecommute.drive.page.AjxDriveCommutePage.this
                r3.isJumpAction = true
                return
            L_0x004f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.bundle.routecommute.drive.page.AjxDriveCommutePage.AnonymousClass3.a(java.lang.String, java.lang.String):void");
        }
    };
    protected AjxPageStateInvoker ajxPageStateInvoker;
    /* access modifiers changed from: private */
    public boolean isJumpAction = false;
    private AjxDriveService mAjxDriveService;
    private AjxLifeCircleListener mAjxLifeCircleListener = new AjxLifeCircleListener() {
        public final void onAjxContxtCreated(IAjxContext iAjxContext) {
            tu.a("ajx_on_end_load");
            if (AjxDriveCommutePage.this.isAlive()) {
                AjxDriveCommutePage.this.mModuleRouteBoard = (ModuleDriveRouteCommute) AjxDriveCommutePage.this.mAjxView.getJsModule(ModuleDriveRouteCommute.MODULE_NAME);
                if (AjxDriveCommutePage.this.mModuleRouteBoard != null) {
                    AjxDriveCommutePage.this.mModuleRouteBoard.setPage(AjxDriveCommutePage.this);
                    AjxDriveCommutePage.this.mModuleRouteBoard.setOnRouteBoardCallback(AjxDriveCommutePage.this.mOnRouteBoardCallback);
                    AjxDriveCommutePage.this.mModuleRouteBoard.addSwitchSceneListener(AjxDriveCommutePage.this.a);
                }
            }
        }

        public final void onJsBack(Object obj, String str) {
            AjxDriveCommutePage.this.finish();
        }
    };
    /* access modifiers changed from: private */
    public AmapAjxView mAjxView;
    private Context mContext;
    private Object mData = null;
    /* access modifiers changed from: private */
    public JsFunctionCallback mDissFooterCallback;
    private boolean mGPSValide = false;
    /* access modifiers changed from: private */
    public Handler mHandler = new Handler();
    private boolean mIsStartNaviPage = false;
    /* access modifiers changed from: private */
    public ModuleDriveRouteCommute mModuleRouteBoard = null;
    /* access modifiers changed from: private */
    public com.autonavi.bundle.routecommute.modlue.ModuleDriveRouteCommute.a mOnRouteBoardCallback = new com.autonavi.bundle.routecommute.modlue.ModuleDriveRouteCommute.a() {
        public final void a(String str, JsFunctionCallback jsFunctionCallback) {
            LogManager.actionLogV2(AjxDriveCommutePage.DRIVE_COMMUTE_NAVI_PAGE_ID, "B015");
            try {
                JSONObject jSONObject = new JSONObject(str);
                AjxDriveCommutePage.this.handleRouteTrafficItemClick(jSONObject.optInt("eventID"), jSONObject.optInt("layerTag"), jSONObject.optInt("jameETA"), jSONObject.optInt("jamLen"), jsFunctionCallback);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public final void a() {
            AMapLog.d("NewRouteBoardPage", "dismissTrafficEventView");
            AjxDriveCommutePage.this.mHandler.post(new Runnable() {
                public final void run() {
                    if (AjxDriveCommutePage.this.mTrafficEventHandler != null) {
                        AjxDriveCommutePage.this.mTrafficEventHandler.j();
                    }
                }
            });
        }
    };
    private IOverlayManager mOverlayManager;
    private NavigationDataResult mResultData;
    private final defpackage.czk.a mServiceCallback = new defpackage.czk.a() {
        public final void a() {
            czk czk = (czk) ((IMainMapService) ank.a(IMainMapService.class)).a(czk.class);
            if (czk != null && AjxDriveCommutePage.this.getContext() != null) {
                czk.a(NotificationChannelIds.g, R.drawable.ic_launcher, AjxDriveCommutePage.this.getResources().getString(R.string.autonavi_app_name_in_route), AjxDriveCommutePage.this.getResources().getString(R.string.autonavi_navi_ing));
            }
        }
    };
    protected b mStoreMapStateParam;
    private long mSysTime = 0;
    private GTraceObserver mTraceObserver = new GTraceObserver() {
        public final void onUploadTrace(TracePoint[] tracePointArr) {
            StringBuilder sb = new StringBuilder("yuanhc onUploadTrace tracePoints = ");
            sb.append(tracePointArr.length);
            AMapLog.d("NewRouteBoardPage", sb.toString());
            for (TracePoint add : tracePointArr) {
                AjxDriveCommutePage.this.mTracePointList.add(add);
            }
        }
    };
    /* access modifiers changed from: private */
    public ArrayList<TracePoint> mTracePointList = new ArrayList<>();
    /* access modifiers changed from: private */
    public defpackage.csb.a mTrafficEventHandler;
    private String mUrl = null;
    /* access modifiers changed from: private */
    public IPageStateListener originPageStateListener = null;

    class a extends defpackage.csb.a {
        public final void a() {
        }

        public final int c() {
            return 0;
        }

        public final void d() {
        }

        public a(MapBasePage mapBasePage) {
            super(mapBasePage);
        }

        public final void b() {
            if (AjxDriveCommutePage.this.mDissFooterCallback != null) {
                AjxDriveCommutePage.this.mDissFooterCallback.callback(new Object[0]);
            }
        }

        public final void a(int i, int i2, boolean z) {
            this.d.a(i, i2, z);
        }
    }

    public static class b {
        public float a;
        public float b;
        public float c;
        public GeoPoint d = new GeoPoint();
        public int e = 0;
        public boolean f = false;
        int g = 0;
        int h = 0;

        protected b() {
        }
    }

    public void attachPage(bgm bgm) {
    }

    public void error(Throwable th, boolean z) {
    }

    public void finishSelf() {
    }

    public View getMapSuspendView() {
        return null;
    }

    public bgo getPresenter() {
        return this;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 9007199254740992L;
    }

    public int getTrafficEventSource() {
        return 6;
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

    public void onPagePause() {
    }

    private void saveMapState() {
        this.mStoreMapStateParam = new b();
        cde suspendManager = getSuspendManager();
        MapManager mapManager = getMapManager();
        if (suspendManager != null && mapManager != null && mapManager.getMapView() != null) {
            bty mapView = mapManager.getMapView();
            bty mapView2 = getMapView();
            this.mStoreMapStateParam.a = mapView.I();
            this.mStoreMapStateParam.b = mapView.v();
            this.mStoreMapStateParam.c = mapView.J();
            this.mStoreMapStateParam.d = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
            this.mStoreMapStateParam.f = mapView.s();
            this.mStoreMapStateParam.e = mapView.l(true);
            this.mStoreMapStateParam.g = mapView2.j(true);
            this.mStoreMapStateParam.h = mapView2.k(true);
            getMapView().t(false);
        }
    }

    /* access modifiers changed from: protected */
    public void recoveryMapState() {
        if (getMapManager() != null && getMapView() != null && !this.isJumpAction) {
            bty mapView = getMapManager().getMapView();
            getMapView().a(this.mStoreMapStateParam.g, this.mStoreMapStateParam.e, this.mStoreMapStateParam.h);
            if (!(this.mStoreMapStateParam.d == null || this.mStoreMapStateParam.d.x == 0 || this.mStoreMapStateParam.d.y == 0)) {
                if (mapView != null) {
                    mapView.a(this.mStoreMapStateParam.d.x, this.mStoreMapStateParam.d.y);
                }
                brj.a(this.mStoreMapStateParam.d.x, this.mStoreMapStateParam.d.y);
            }
            if (mapView != null) {
                mapView.e(this.mStoreMapStateParam.a);
                mapView.d(this.mStoreMapStateParam.b);
                mapView.g(this.mStoreMapStateParam.c);
                mapView.b(this.mStoreMapStateParam.f);
            }
            if (!this.isJumpAction) {
                recoveryMapColorBlindStatus();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void recoveryMapColorBlindStatus() {
        boolean booleanValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("blind_mode_status", false);
        bty mapView = getMapManager().getMapView();
        if (mapView != null) {
            mapView.t(booleanValue);
        }
    }

    /* access modifiers changed from: protected */
    public baf createPresenter() {
        return new baf(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        createNotify();
        requestScreenOrientation(1);
        this.mContext = context;
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.mUrl = arguments.getString("url");
            this.mData = arguments.getObject("jsData");
            this.mSysTime = arguments.getLong("startTime");
        }
        setContentView(R.layout.layout_drive_commute);
        loadAjxView();
        ms msVar = (ms) defpackage.esb.a.a.a(ms.class);
        if (msVar != null) {
            msVar.a(true);
        }
        setPageStateListener();
        saveMapState();
        if (!TripSpUtil.getCommuteBroadCastState(AMapAppGlobal.getApplication())) {
            tr.a(this, getString(R.string.autonavi_audio_switch_closed), R.drawable.voice_closed);
        }
    }

    public void onPageCreated() {
        requestScreenOn(true);
        this.mOverlayManager = getMapManager().getOverlayManager();
        LocationInstrument.getInstance().addStatusCallback(this, null);
        LocationInstrument.getInstance().startCheckGpsStatus();
    }

    public void onPageStart() {
        LocationInstrument.getInstance().subscribe(getContext(), LOCATION_SCENE.TYPE_COMMUTING);
    }

    public void onPageResume() {
        if (this.mAjxView != null) {
            this.mAjxView.setVisibility(0);
        }
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.onResume();
        }
    }

    public void onPageStop() {
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.onStop();
        }
    }

    public void onPageDestroy() {
        if (!this.mIsStartNaviPage) {
            destoryNotify();
        }
        ms msVar = (ms) defpackage.esb.a.a.a(ms.class);
        if (msVar != null) {
            msVar.a(false);
        }
        LocationInstrument.getInstance().stopCheckGpsStatus();
        LocationInstrument.getInstance().removeStatusCallback(this);
        LocationInstrument.getInstance().unsubscribe(getContext());
        AMapLog.d("NewRouteBoardPage", "yuanhc destroy");
        if (this.mAjxDriveService != null) {
            this.mAjxDriveService.nativeDestroy();
        }
        if (this.mAjxView != null) {
            this.mAjxView.destroy();
            this.mAjxView.onAjxContextCreated(null);
            if (this.mModuleRouteBoard != null) {
                this.mModuleRouteBoard.removeSwitchSceneListener(this.a);
            }
            this.mAjxView = null;
        }
        if (this.ajxPageStateInvoker != null) {
            this.ajxPageStateInvoker.onDestroy();
        }
        recoveryMapState();
    }

    public defpackage.csb.a getReleatedTrafficEventHandler() {
        if (this.mTrafficEventHandler == null) {
            this.mTrafficEventHandler = new a(this);
        }
        return this.mTrafficEventHandler;
    }

    public void callback(Status status) {
        azb.a("NewRouteBoardPage", status.name());
        if (status == Status.ON_LOCATION_GPS_FAIL_LOOP) {
            this.mGPSValide = false;
            return;
        }
        if (status == Status.ON_LOCATION_GPS_OK && !this.mGPSValide) {
            this.mGPSValide = true;
        }
    }

    private static String addJSONItemToJSONObject(String str, String str2, Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append("\"");
        sb.append(str2);
        sb.append("\":");
        sb.append(obj);
        sb.append(",");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("{");
        sb2.append(sb);
        sb2.append(str, str.indexOf("{") + 1, str.length());
        return sb2.toString();
    }

    private NavigationDataResult resultData() {
        if (this.mResultData == null) {
            this.mResultData = new NavigationDataResult();
            ArrayList arrayList = (ArrayList) getArguments().getObject("RouteBoardTraceData");
            if (arrayList != null) {
                AMapLog.d("NewRouteBoardPage", "yuanhc resultData routboard data pass to NavigationDataResult");
                this.mResultData.setTraceListReference(arrayList);
            }
        }
        return this.mResultData;
    }

    /* access modifiers changed from: private */
    public void startNaviEndPage(String str) {
        PageBundle pageBundle = new PageBundle();
        if (!TextUtils.isEmpty(str)) {
            pageBundle.putString("jsData", str);
        }
        pageBundle.putString("url", AJX_DRIVE_COMMUTE_END);
        pageBundle.putInt("route_car_type_key", 0);
        pageBundle.putObject("key_navigation_data_result", resultData());
        pageBundle.putString("navi_type", "car");
        startPage(AjxDriveCommuteEndPage.class, pageBundle);
        finish();
    }

    /* access modifiers changed from: private */
    public void handleRouteTrafficItemClick(int i, int i2, int i3, int i4, JsFunctionCallback jsFunctionCallback) {
        this.mDissFooterCallback = jsFunctionCallback;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt(IOverlayManager.EVENT_ID_KEY, i);
        if (!(i3 == 0 && i4 == 0)) {
            ms msVar = (ms) defpackage.esb.a.a.a(ms.class);
            if (msVar != null) {
                pageBundle.putCharSequence(IOverlayManager.EVENT_HEAD_KEY, msVar.a(getContext(), i4, i3));
            }
        }
        pageBundle.putBoolean(IOverlayManager.EVENT_IS_FROM_ROUTE_RESULT, true);
        pageBundle.putInt(IOverlayManager.EVENT_LAYERTAG_FROM_ROUTE_RESULT, i2);
        pageBundle.putBoolean("key_open_traffic_later", false);
        this.mOverlayManager.handleTrafficItemClick(pageBundle);
    }

    private void setPageStateListener() {
        this.originPageStateListener = AMapPageUtil.getPageStateListener(this);
        AMapPageUtil.setPageStateListener(this, new IPageStateListener() {
            public final void onCover() {
                ITrafficReportController iTrafficReportController = (ITrafficReportController) ank.a(ITrafficReportController.class);
                if (iTrafficReportController != null) {
                    iTrafficReportController.a();
                }
                if (AjxDriveCommutePage.this.originPageStateListener != null) {
                    AjxDriveCommutePage.this.originPageStateListener.onCover();
                }
            }

            public final void onAppear() {
                if (AjxDriveCommutePage.this.originPageStateListener != null) {
                    AjxDriveCommutePage.this.originPageStateListener.onCover();
                }
            }
        });
    }

    private void createNotify() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            czk czk = (czk) iMainMapService.a(czk.class);
            if (czk != null) {
                if (czk.b()) {
                    czk.a(NotificationChannelIds.g, R.drawable.ic_launcher, getResources().getString(R.string.autonavi_app_name_in_route), getResources().getString(R.string.autonavi_navi_ing));
                    return;
                }
                czk.a(this.mServiceCallback);
            }
        }
    }

    private void destoryNotify() {
        czk czk = (czk) ((IMainMapService) ank.a(IMainMapService.class)).a(czk.class);
        if (czk != null) {
            ISchoolbusStatusMangger iSchoolbusStatusMangger = (ISchoolbusStatusMangger) ank.a(ISchoolbusStatusMangger.class);
            if (iSchoolbusStatusMangger == null) {
                czk.a();
            } else if (iSchoolbusStatusMangger.isTravelling()) {
                czk.a(NotificationChannelIds.p, R.drawable.ic_launcher, "高德地图", "安心校车正在使用位置服务");
            } else {
                czk.a();
            }
        }
    }

    private void loadAjxView() {
        this.mAjxView = new AmapAjxView(getContext());
        ((ViewGroup) getContentView()).addView(this.mAjxView, new LayoutParams(-1, -1));
        this.mAjxView.setAjxLifeCircleListener(this.mAjxLifeCircleListener);
        this.ajxPageStateInvoker = new AjxPageStateInvoker(this, this.mAjxView);
        this.mAjxView.loadJsWithFullScreen(this.mUrl, this.mData, "CAR_MAP_RESULT", null);
    }

    public ON_BACK_TYPE onPageBackPressed() {
        if (onPageBackPress()) {
            return ON_BACK_TYPE.TYPE_IGNORE;
        }
        return super.onBackPressed();
    }

    public void initMapView() {
        bty mapView = getMapManager().getMapView();
        if (mapView != null) {
            mapView.b(true);
            if (getSuspendManager() != null && getSuspendManager().d() != null) {
                getSuspendManager().d().f();
            }
        }
    }

    public void restoreMap() {
        if (!this.isJumpAction) {
            bty mapView = getMapManager().getMapView();
            if (mapView != null) {
                mapView.a(mapView.p(false), 0, 0);
                mapView.a(0.5f, 0.5f);
            }
        }
    }

    public boolean onPageBackPress() {
        return this.mAjxView != null && this.mAjxView.backPressed();
    }

    /* access modifiers changed from: private */
    public void startNaviPage(String str) {
        ms msVar = (ms) defpackage.esb.a.a.a(ms.class);
        if (msVar != null) {
            this.mIsStartNaviPage = true;
            msVar.a(getActivity(), str, (defpackage.ms.b) new defpackage.ms.b() {
                public final void a(boolean z) {
                    if (!(AjxDriveCommutePage.this.mModuleRouteBoard == null || AjxDriveCommutePage.this.mModuleRouteBoard.mJstNonresponsiblityOnclickCallback == null)) {
                        if (z) {
                            AjxDriveCommutePage.this.mModuleRouteBoard.mJstNonresponsiblityOnclickCallback.callback("1");
                            return;
                        }
                        AjxDriveCommutePage.this.mModuleRouteBoard.mJstNonresponsiblityOnclickCallback.callback("0");
                    }
                }
            });
        }
    }
}
