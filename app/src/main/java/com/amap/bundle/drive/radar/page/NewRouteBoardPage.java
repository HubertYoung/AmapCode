package com.amap.bundle.drive.radar.page;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.amap.bundle.blutils.notification.NotificationChannelIds;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.drive.radar.module.ModuleRouteBoard;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.schoolbus.notifcation.ISchoolbusStatusMangger;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LOCATION_SCENE;
import com.autonavi.common.impl.Locator.LocationPreference;
import com.autonavi.common.impl.Locator.Status;
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
import com.autonavi.minimap.ajx3.Ajx;
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
public class NewRouteBoardPage extends MapBasePage implements bgm, bgo, Callback<Status>, LocationGpsOnly, launchModeSingleTask, d {
    public static final String AJX_PAGE_PRELOAD_URL = "path://amap_bundle_drive/src/radar/DriveRadarPreload.js";
    public static final String AJX_PAGE_URL = "path://amap_bundle_drive/src/radar/DriveRadar.page.js";
    private final String TAG = "NewRouteBoardPage";
    mq a = new mq() {
        public final void a(String str, String str2) {
            if (((str.hashCode() == 553280922 && str.equals("carNavi")) ? (char) 0 : 65535) == 0) {
                NewRouteBoardPage.this.startNaviPage(str2);
            }
        }
    };
    protected AjxPageStateInvoker ajxPageStateInvoker;
    private AjxDriveService mAjxDriveService;
    private AjxLifeCircleListener mAjxLifeCircleListener = new AjxLifeCircleListener() {
        public final void onAjxContxtCreated(IAjxContext iAjxContext) {
            tu.a("ajx_on_end_load");
            if (NewRouteBoardPage.this.isAlive()) {
                NewRouteBoardPage.this.mModuleRouteBoard = (ModuleRouteBoard) NewRouteBoardPage.this.mAjxView.getJsModule(ModuleRouteBoard.MODULE_NAME);
                if (NewRouteBoardPage.this.mModuleRouteBoard != null) {
                    NewRouteBoardPage.this.mModuleRouteBoard.setOnRouteBoardCallback(NewRouteBoardPage.this.mOnRouteBoardCallback);
                    NewRouteBoardPage.this.mModuleRouteBoard.addSwitchSceneListener(NewRouteBoardPage.this.a);
                }
            }
        }

        public final void onJsBack(Object obj, String str) {
            NewRouteBoardPage.this.finish();
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
    /* access modifiers changed from: private */
    public ModuleRouteBoard mModuleRouteBoard = null;
    /* access modifiers changed from: private */
    public com.amap.bundle.drive.radar.module.ModuleRouteBoard.a mOnRouteBoardCallback = new com.amap.bundle.drive.radar.module.ModuleRouteBoard.a() {
        public final void a(String str, JsFunctionCallback jsFunctionCallback) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                NewRouteBoardPage.this.handleRouteTrafficItemClick(jSONObject.optInt("eventID"), jSONObject.optInt("layerTag"), jSONObject.optInt("jameETA"), jSONObject.optInt("jamLen"), jsFunctionCallback);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public final void a() {
            AMapLog.d("NewRouteBoardPage", "dismissTrafficEventView");
            NewRouteBoardPage.this.mHandler.post(new Runnable() {
                public final void run() {
                    if (NewRouteBoardPage.this.mTrafficEventHandler != null) {
                        NewRouteBoardPage.this.mTrafficEventHandler.j();
                    }
                }
            });
        }
    };
    private IOverlayManager mOverlayManager;
    private final defpackage.czk.a mServiceCallback = new defpackage.czk.a() {
        public final void a() {
            czk czk = (czk) ((IMainMapService) ank.a(IMainMapService.class)).a(czk.class);
            if (czk != null && NewRouteBoardPage.this.getContext() != null) {
                czk.a(NotificationChannelIds.g, R.drawable.ic_launcher, NewRouteBoardPage.this.getResources().getString(R.string.autonavi_app_name_in_route), NewRouteBoardPage.this.getResources().getString(R.string.autonavi_navi_ing));
            }
        }
    };
    private GTraceObserver mTraceObserver = new GTraceObserver() {
        public final void onUploadTrace(TracePoint[] tracePointArr) {
            StringBuilder sb = new StringBuilder("yuanhc onUploadTrace tracePoints = ");
            sb.append(tracePointArr.length);
            AMapLog.d("NewRouteBoardPage", sb.toString());
            for (TracePoint add : tracePointArr) {
                NewRouteBoardPage.this.mTracePointList.add(add);
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
            if (NewRouteBoardPage.this.mDissFooterCallback != null) {
                NewRouteBoardPage.this.mDissFooterCallback.callback(new Object[0]);
            }
        }

        public final void a(int i, int i2, boolean z) {
            this.d.a(i, i2, z);
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
        return 562949953421312L;
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

    /* access modifiers changed from: protected */
    public ol createPresenter() {
        return new ol(this);
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        requestScreenOrientation(1);
        this.mContext = context;
        PageBundle arguments = getArguments();
        if (arguments != null) {
            this.mUrl = arguments.getString("url");
            this.mData = arguments.getObject("jsData");
        }
        setContentView(R.layout.ajx_route_board_layout);
        loadAjxView();
        NaviManager.a().i = true;
        setPageStateListener();
        if (!DriveSpUtil.getDriveRadderCameraPlay(AMapAppGlobal.getApplication())) {
            tr.a(this, getString(R.string.autonavi_navi_audio_switch_closed), R.drawable.voice_closed);
        }
        createNotify();
    }

    public void onPageCreated() {
        requestScreenOn(true);
        MapManager mapManager = getMapManager();
        this.mOverlayManager = mapManager != null ? mapManager.getOverlayManager() : null;
        LocationInstrument.getInstance().addStatusCallback(this, null);
        LocationInstrument.getInstance().startCheckGpsStatus();
    }

    public void onPageStart() {
        LocationInstrument.getInstance().subscribe(getContext(), LOCATION_SCENE.TYPE_ROUTE_EXPLORE);
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
        destoryNotify();
        NaviManager.a().i = false;
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
    }

    public defpackage.csb.a getReleatedTrafficEventHandler() {
        if (this.mTrafficEventHandler == null) {
            this.mTrafficEventHandler = new a(this);
        }
        return this.mTrafficEventHandler;
    }

    public void callback(Status status) {
        ku a2 = ku.a();
        StringBuilder sb = new StringBuilder("[NewRouteBoardPage]");
        sb.append(status.name());
        a2.c("RouteBoardNaviMonitor", sb.toString());
        AMapLog.e("RouteBoardNaviMonitor", status.name());
        if (status == Status.ON_LOCATION_GPS_FAIL_LOOP) {
            this.mGPSValide = false;
            return;
        }
        if (status == Status.ON_LOCATION_GPS_OK && !this.mGPSValide) {
            this.mGPSValide = true;
        }
    }

    public static void startNewRouteBoardPage(bid bid, String str, ph phVar, boolean z) {
        if (!TextUtils.isEmpty(str)) {
            Ajx.sStartTime = System.currentTimeMillis();
            PageBundle pageBundle = new PageBundle();
            pageBundle.putString("url", AJX_PAGE_URL);
            pageBundle.putObject("jsData", addJSONItemToJSONObject(str, "is_enter_by_user_click", Boolean.valueOf(z)));
            pageBundle.putObject("car_result", phVar);
            pageBundle.putLong("startTime", System.currentTimeMillis());
            pageBundle.putString("env", AJX_PAGE_PRELOAD_URL);
            bid.startPageForResult(NewRouteBoardPage.class, pageBundle, 99);
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

    /* access modifiers changed from: private */
    public void startNaviPage(String str) {
        nm.a(getActivity(), str, new b() {
            public final void a(boolean z) {
                if (!(NewRouteBoardPage.this.mModuleRouteBoard == null || NewRouteBoardPage.this.mModuleRouteBoard.mJstNonresponsiblityOnclickCallback == null)) {
                    if (z) {
                        NewRouteBoardPage.this.mModuleRouteBoard.mJstNonresponsiblityOnclickCallback.callback("1");
                        return;
                    }
                    NewRouteBoardPage.this.mModuleRouteBoard.mJstNonresponsiblityOnclickCallback.callback("0");
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void handleRouteTrafficItemClick(int i, int i2, int i3, int i4, JsFunctionCallback jsFunctionCallback) {
        this.mDissFooterCallback = jsFunctionCallback;
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt(IOverlayManager.EVENT_ID_KEY, i);
        if (!(i3 == 0 && i4 == 0)) {
            pageBundle.putCharSequence(IOverlayManager.EVENT_HEAD_KEY, pw.a(getContext(), i4, i3));
        }
        pageBundle.putBoolean(IOverlayManager.EVENT_IS_FROM_ROUTE_RESULT, true);
        pageBundle.putInt(IOverlayManager.EVENT_LAYERTAG_FROM_ROUTE_RESULT, i2);
        pageBundle.putBoolean("key_open_traffic_later", false);
        if (this.mOverlayManager != null) {
            this.mOverlayManager.handleTrafficItemClick(pageBundle);
        }
    }

    private void setPageStateListener() {
        this.originPageStateListener = AMapPageUtil.getPageStateListener(this);
        AMapPageUtil.setPageStateListener(this, new IPageStateListener() {
            public final void onCover() {
                ITrafficReportController iTrafficReportController = (ITrafficReportController) ank.a(ITrafficReportController.class);
                if (iTrafficReportController != null) {
                    iTrafficReportController.a();
                }
                if (NewRouteBoardPage.this.originPageStateListener != null) {
                    NewRouteBoardPage.this.originPageStateListener.onCover();
                }
            }

            public final void onAppear() {
                if (NewRouteBoardPage.this.originPageStateListener != null) {
                    NewRouteBoardPage.this.originPageStateListener.onCover();
                }
            }
        });
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

    public boolean onPageBackPress() {
        bty mapView = getMapManager() != null ? getMapManager().getMapView() : null;
        if (mapView != null) {
            mapView.a(false);
        }
        if (this.mAjxView == null || !this.mAjxView.backPressed()) {
            return false;
        }
        return true;
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
}
