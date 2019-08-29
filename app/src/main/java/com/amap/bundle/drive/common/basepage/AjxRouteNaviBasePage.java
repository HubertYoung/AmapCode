package com.amap.bundle.drive.common.basepage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.amap.bundle.drive.ajx.inter.ILowBrightness;
import com.amap.bundle.drive.ajx.inter.OnRealCityStateChangeListener;
import com.amap.bundle.drive.ajx.module.ModuleCommonBusiness;
import com.amap.bundle.drive.ajx.module.ModuleDriveCommonBusiness;
import com.amap.bundle.drive.ajx.module.ModuleDriveNavi;
import com.amap.bundle.drive.common.basepage.control.NewStatusBarController;
import com.amap.bundle.drive.common.speaker.SpeakerPlayManager;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager.NaviType;
import com.amap.bundle.drivecommon.tools.DriveSpUtil;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.bundle.vui.ajx.ModuleVUI;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.Real3DManager;
import com.autonavi.map.suspend.refactor.scale.ScaleLineView;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.offline.map.inter.IOfflineManager;
import com.autonavi.sdk.location.LocationInstrument;
import com.iflytek.tts.TtsService.PlaySoundUtils;
import com.iflytek.tts.TtsService.PlaySoundUtils.HandleInterruptEvent;
import com.iflytek.tts.TtsService.TtsManagerUtil;

public abstract class AjxRouteNaviBasePage extends Ajx3Page implements HandleInterruptEvent {
    private static final ahn mSingleExecutor = new ahn(1);
    protected final GeoPoint mCarLocation = new GeoPoint();
    private ILowBrightness mILowBrightness = new ILowBrightness() {
        public final void onLowBrightnessOpen(boolean z) {
            if (z) {
                AjxRouteNaviBasePage.this.adjustLightness();
            } else {
                AjxRouteNaviBasePage.this.recoverOriginalLightness();
            }
        }
    };
    public aia mIVoiceService;
    private boolean mMakeReceiveCall = false;
    private a mMapInfo;
    /* access modifiers changed from: private */
    public boolean mMapViewTouchEnable = true;
    protected ModuleCommonBusiness mModuleCommonBusiness = null;
    protected ModuleDriveCommonBusiness mModuleDriveCommonBusiness = null;
    public ModuleDriveNavi mModuleDriveNavi = null;
    protected ModuleVUI mModuleVUI;
    protected NaviManager mNaviMgr = NaviManager.a();
    private PhoneStateListener mNaviPhoneStateListener = null;
    private OnRealCityStateChangeListener mOnRealCityStateChangeListener = new OnRealCityStateChangeListener() {
        public final void onRealCityStateChange(boolean z) {
            AjxRouteNaviBasePage.this.mMapViewTouchEnable = !z;
        }
    };
    private WakeLock mPageWakeLock;
    protected int mScreenHeight;
    protected int mScreenWidth;
    public boolean mShowCustomStatusBar = true;
    protected SpeakerPlayManager mSpeakerPlayManager;
    protected NewStatusBarController mStatusBarController;
    private TelephonyManager mTelephonyManager;

    static class a {
        public float a;
        public float b;
        public float c;
        public int d;
        public int e;
        public GeoPoint f;
        public boolean g;
        public boolean h;
        public boolean i;

        private a() {
            this.g = false;
            this.h = false;
            this.i = false;
        }

        /* synthetic */ a(byte b2) {
            this();
        }
    }

    static class b implements Runnable {
        private b() {
        }

        /* synthetic */ b(byte b) {
            this();
        }

        public final void run() {
            IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
            if (iOfflineManager != null) {
                iOfflineManager.pauseAllByNavi();
            }
        }
    }

    static class c implements Runnable {
        private c() {
        }

        /* synthetic */ c(byte b) {
            this();
        }

        public final void run() {
            IOfflineManager iOfflineManager = (IOfflineManager) ank.a(IOfflineManager.class);
            if (iOfflineManager != null) {
                iOfflineManager.recoveryDownload();
            }
        }
    }

    /* access modifiers changed from: protected */
    public abstract void adjustLightness();

    /* access modifiers changed from: protected */
    public abstract NaviType getNaviType();

    /* access modifiers changed from: protected */
    public abstract boolean getSpeakTTSMode();

    /* access modifiers changed from: protected */
    public abstract boolean getTtsMixMusicMode();

    /* access modifiers changed from: protected */
    public abstract void initLightness();

    /* access modifiers changed from: protected */
    public abstract boolean isLightnessSwitchOn();

    /* access modifiers changed from: protected */
    public abstract void loadNodeFragmentBundle(PageBundle pageBundle);

    /* access modifiers changed from: protected */
    public abstract void recoverOriginalLightness();

    /* access modifiers changed from: protected */
    public abstract void requestScreenOrientation();

    /* access modifiers changed from: protected */
    public abstract boolean willEnterNaviEndPage();

    public void onCreate(Context context) {
        if (this.mShowCustomStatusBar) {
            createStatusBarController();
        }
        super.onCreate(context);
        initVoice();
        loadNodeFragmentBundle(getArguments());
        buildScreenInfo(getActivity());
        registerPhoneStateListener();
        NaviManager.a().h = getNaviType();
        oh a2 = oh.a();
        oh.a((String) "DriveOfflineManager.pause");
        a2.a.nativePause();
        a2.f = true;
        Real3DManager.a().g = true;
    }

    public void pageCreated() {
        super.pageCreated();
        initLightness();
        initMapView();
        requestScreenOn(true);
        requestScreenOrientation();
        pauseOfflineDataDownloaded();
    }

    public void resume() {
        super.resume();
        if (rq.a()) {
            try {
                StringBuilder sb = new StringBuilder("autonavi:");
                sb.append(getClass().getSimpleName());
                this.mPageWakeLock = ((PowerManager) getContext().getSystemService("power")).newWakeLock(10, sb.toString());
                this.mPageWakeLock.acquire();
            } catch (Exception unused) {
                this.mPageWakeLock = null;
            }
        }
        adjustLightness();
        if (this.mSpeakerPlayManager != null) {
            this.mSpeakerPlayManager.b();
        }
        if (this.mShowCustomStatusBar) {
            tt.a(getActivity());
        }
        if (this.mStatusBarController != null) {
            this.mStatusBarController.registeStatusBarInfoChange();
        }
        setNaviMode(true);
        if (!this.mMapViewTouchEnable) {
            setMapTouchEnable(this.mMapViewTouchEnable);
        }
    }

    public void pause() {
        super.pause();
        recoverOriginalLightness();
        if (this.mShowCustomStatusBar) {
            tt.b(getActivity());
        }
        setNaviMode(false);
        if (this.mPageWakeLock != null) {
            try {
                this.mPageWakeLock.release();
            } catch (Exception unused) {
            } finally {
                this.mPageWakeLock = null;
            }
        }
    }

    public void stop() {
        super.stop();
    }

    public void destroy() {
        if (bno.a) {
            StringBuilder sb = new StringBuilder();
            sb.append(AjxRouteCarNaviBasePage.class.getSimpleName());
            sb.append(" destroy");
            AMapLog.debug("route.drive", "android", sb.toString());
        }
        if (this.mAjxView != null) {
            this.mAjxView.orientationChange(0);
        }
        super.destroy();
        if (this.mStatusBarController != null) {
            this.mStatusBarController.releaseStatusBar();
        }
        if (!willEnterNaviEndPage()) {
            PlaySoundUtils.getInstance().release();
        }
        recoverMapView();
        this.mSpeakerPlayManager.c();
        if (this.mNaviPhoneStateListener != null) {
            this.mTelephonyManager.listen(this.mNaviPhoneStateListener, 0);
        }
        this.mNaviPhoneStateListener = null;
        resumeOfflineDataDownloaded();
        NaviManager.a().h = null;
        if (this.mModuleDriveNavi != null) {
            this.mModuleDriveNavi.setOnRealCityStateChangeListener(null);
            this.mModuleDriveNavi.setILowBrightness(null);
        }
        oh.a().c();
        Real3DManager.a().g = false;
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.mModuleVUI = (ModuleVUI) this.mAjxView.getJsModule(ModuleVUI.MODULE_NAME);
        this.mModuleCommonBusiness = (ModuleCommonBusiness) this.mAjxView.getJsModule(ModuleCommonBusiness.MODULE_NAME);
        this.mModuleDriveNavi = (ModuleDriveNavi) this.mAjxView.getJsModule(ModuleDriveNavi.MODULE_NAME);
        this.mModuleDriveCommonBusiness = (ModuleDriveCommonBusiness) this.mAjxView.getJsModule(ModuleDriveCommonBusiness.MODULE_NAME);
        if (this.mModuleDriveNavi != null) {
            this.mModuleDriveNavi.setOnRealCityStateChangeListener(this.mOnRealCityStateChangeListener);
            this.mModuleDriveNavi.setILowBrightness(this.mILowBrightness);
        }
        if (this.mStatusBarController != null) {
            this.mStatusBarController.setModuleRoute(this.mModuleDriveNavi);
        }
        this.mIVoiceService = (aia) defpackage.esb.a.a.a(aia.class);
    }

    private void initMapView() {
        bty mapView = getMapView();
        if (mapView != null) {
            saveMapView();
            mapView.g(false);
            mapView.B();
            mapView.q(true);
            if (!(getSuspendManager() == null || getSuspendManager().b() == null)) {
                getSuspendManager().b().setNaviMode(1);
                getSuspendManager().b().disableView(-1);
            }
        }
    }

    private void saveMapView() {
        this.mMapInfo = new a(0);
        bty mapView = getMapView();
        if (mapView != null) {
            this.mMapInfo.a = mapView.I();
            this.mMapInfo.b = mapView.v();
            this.mMapInfo.c = mapView.J();
            this.mMapInfo.d = mapView.p(false);
            this.mMapInfo.e = mapView.o(true);
            this.mMapInfo.f = mapView.o();
            this.mMapInfo.g = mapView.D();
            this.mMapInfo.h = mapView.C();
            this.mMapInfo.i = mapView.ai();
        }
    }

    /* access modifiers changed from: protected */
    public void recoverMapView() {
        if (getMapManager() != null) {
            bty mapView = getMapManager().getMapView();
            if (mapView != null) {
                if (!(getSuspendManager() == null || getSuspendManager().f() == null)) {
                    getSuspendManager().f().a();
                    ScaleLineView scaleLineView = getSuspendManager().f().a().getScaleLineView();
                    if (scaleLineView != null) {
                        scaleLineView.setScaleLineColor(-16777216, -1);
                        scaleLineView.postInvalidate();
                    }
                }
                if (mapView.w() != ((int) this.mMapInfo.b)) {
                    mapView.T();
                    mapView.f(this.mMapInfo.b);
                }
                mapView.g(this.mMapInfo.c);
                if (!willEnterNaviEndPage()) {
                    mapView.a(this.mMapInfo.d, 0, this.mMapInfo.e);
                }
                ku a2 = ku.a();
                StringBuilder sb = new StringBuilder("[");
                sb.append(Ajx3Page.TAG);
                sb.append("]recoverMapView#mMapInfo.getMapMode(false):");
                sb.append(this.mMapInfo.d);
                a2.c("NaviMonitor", sb.toString());
                mapView.e(this.mMapInfo.a);
                mapView.a(this.mMapInfo.f.x, this.mMapInfo.f.y);
                mapView.g(this.mMapInfo.g);
                if (this.mMapInfo.h) {
                    mapView.e(false);
                } else {
                    mapView.B();
                }
                mapView.af();
                mapView.q(this.mMapInfo.i);
                mapView.b(this.mScreenWidth / 2, this.mScreenHeight / 2);
                Editor edit = new MapSharePreference(SharePreferenceName.SharedPreferences).edit();
                edit.putInt("X", this.mMapInfo.f.x);
                edit.putInt("Y", this.mMapInfo.f.y);
                if (LocationInstrument.getInstance().getLatestPosition(5) != null) {
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                    if (latestPosition != null) {
                        edit.putInt("myX", latestPosition.x);
                        edit.putInt("myY", latestPosition.y);
                    }
                }
                edit.putFloat("PRESISE_ZOOM_LEVEL", this.mMapInfo.b);
                edit.putFloat("D", this.mMapInfo.a);
                edit.putFloat("C", this.mMapInfo.c);
                edit.apply();
                bqx bqx = (bqx) ank.a(bqx.class);
                if (bqx != null) {
                    bqx.a(false, tf.a("SharedPreferences", "traffic", false), false, getMapManager(), getContext());
                }
            }
        }
    }

    private void pauseOfflineDataDownloaded() {
        mSingleExecutor.execute(new b(0));
    }

    private void resumeOfflineDataDownloaded() {
        mSingleExecutor.execute(new c(0));
    }

    public void setMakeReceiveCallEvent(int i) {
        if (i > 0) {
            tl.a().a.add(getLatestPosition());
        } else if (this.mMakeReceiveCall) {
            tl.a().b.add(getLatestPosition());
        }
        this.mMakeReceiveCall = i > 0;
    }

    private GeoPoint getLatestPosition() {
        GeoPoint geoPoint = new GeoPoint();
        return (geoPoint.x == 0 || geoPoint.y == 0) ? LocationInstrument.getInstance().getLatestPosition() : geoPoint;
    }

    private void setNaviMode(boolean z) {
        MapManager mapManager = getMapManager();
        if (mapManager != null) {
            bty mapView = mapManager.getMapView();
            if (mapView != null) {
                mapView.h(z);
            }
        }
    }

    private void setMapTouchEnable(boolean z) {
        bty mapView = getMapManager().getMapView();
        if (mapView != null) {
            mapView.f(z);
        }
    }

    /* access modifiers changed from: protected */
    public void buildScreenInfo(Activity activity) {
        if (activity != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            this.mScreenWidth = displayMetrics.widthPixels;
            this.mScreenHeight = displayMetrics.heightPixels;
        }
    }

    private void registerPhoneStateListener() {
        this.mNaviPhoneStateListener = new PhoneStateListener() {
            public final void onCallStateChanged(int i, String str) {
                if (i == 0) {
                    AjxRouteNaviBasePage.this.mSpeakerPlayManager.a();
                }
            }
        };
        this.mTelephonyManager = (TelephonyManager) getContext().getSystemService("phone");
        this.mTelephonyManager.listen(this.mNaviPhoneStateListener, 32);
    }

    private void initSpeakerMode() {
        this.mSpeakerPlayManager = new SpeakerPlayManager(getContext());
        this.mSpeakerPlayManager.d();
    }

    public void onBaseBlueToothConnected(boolean z) {
        if (z) {
            to.a(this, false);
        }
    }

    public void onBlueToothConnected(boolean z) {
        if (z) {
            to.a(this, false);
        }
    }

    private void createStatusBarController() {
        if (this.mStatusBarController == null) {
            this.mStatusBarController = new NewStatusBarController(this, new com.amap.bundle.drive.common.basepage.control.NewStatusBarController.a() {
                public final void a(boolean z) {
                    AjxRouteNaviBasePage.this.onBaseBlueToothConnected(z);
                }
            });
            this.mStatusBarController.initStatusBar();
        }
    }

    private void initVoice() {
        rp.a();
        PlaySoundUtils.getInstance().setHandleInterruptEventObj(this);
        PlaySoundUtils.getInstance().setTTSMixedMusic(getTtsMixMusicMode());
        PlaySoundUtils.getInstance().setCallingSpeakTTS(getSpeakTTSMode());
        NaviManager.a().d();
        rw a2 = rw.a(getContext());
        a2.b = 0;
        a2.d = a2.c.getStreamVolume(3);
        if (DriveSpUtil.getBool(a2.a, DriveSpUtil.NAVIGATION_VOLUME_GAIN_SWITCH, false)) {
            TtsManagerUtil.setVolumeGain(9);
        } else {
            TtsManagerUtil.setVolumeGain(0);
        }
        initSpeakerMode();
    }

    /* access modifiers changed from: protected */
    public GeoPoint curCarLocation() {
        if (this.mCarLocation == null || this.mCarLocation.x == 0 || this.mCarLocation.y == 0) {
            return LocationInstrument.getInstance().getLatestPosition();
        }
        return this.mCarLocation;
    }
}
