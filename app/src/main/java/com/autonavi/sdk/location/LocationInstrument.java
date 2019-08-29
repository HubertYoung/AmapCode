package com.autonavi.sdk.location;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.amap.api.service.IndoorLocationProvider;
import com.amap.bundle.blutils.FileUtil;
import com.amap.bundle.blutils.device.DeviceInfo;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.request.param.NetworkParam;
import com.amap.location.sdk.fusion.LocationManagerProxy;
import com.amap.location.sdk.fusion.LocationParams;
import com.amap.location.sdk.fusion.LocationStatusListener;
import com.amap.location.sdk.fusion.interfaces.LocationNmeaListener;
import com.amap.location.sdk.fusion.interfaces.LocationSatelliteListener;
import com.autonavi.ae.location.LocInfo;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.common.Callback;
import com.autonavi.common.impl.Locator;
import com.autonavi.common.impl.Locator.LOCATION_SCENE;
import com.autonavi.common.impl.Locator.Provider;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.ae.pos.DriveModeObserver;
import com.autonavi.jni.ae.pos.LocListener;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.server.aos.serverkey;
import com.autonavi.widget.ui.BalloonLayout;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.json.JSONObject;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class LocationInstrument implements Locator, DriveModeObserver, LocListener {
    private static final int AMDC_UPDATE_INTERVAL = 120000;
    private static final String ENCODE_START = "_@AMAP@_";
    private static final String FEATURE_LOCATION_GPS = "android.hardware.location.gps";
    public static final String INDOOR_LOCATION_LAT = "idrGcjLat";
    public static final String INDOOR_LOCATION_LON = "idrGcjLon";
    public static final String LOCATION_EXTRAS_KEY_ARACTIVITY = "arActivity";
    public static final String LOCATION_EXTRAS_KEY_ARBEARING = "arBearing";
    public static final String LOCATION_EXTRAS_KEY_COMPASS_COURSE = "compass_course";
    public static final String LOCATION_EXTRAS_KEY_COURSE_ACC = "courseAcc";
    public static final String LOCATION_EXTRAS_KEY_COURSE_TYPE = "course_type";
    public static final String LOCATION_EXTRAS_KEY_ERROR_DIST = "error_dist";
    public static final String LOCATION_EXTRAS_KEY_FITTING_COURSE = "fitting_course";
    public static final String LOCATION_EXTRAS_KEY_FITTING_COURSE_ACC = "fitting_cours_acc";
    public static final String LOCATION_EXTRAS_KEY_FLOOR = "floor";
    public static final String LOCATION_EXTRAS_KEY_GPS_COURE_ACC = "gps_coure_acc";
    public static final String LOCATION_EXTRAS_KEY_GPS_COURSE = "gps_course";
    public static final String LOCATION_EXTRAS_KEY_MATCH_POS_TYPE = "match_pos_type";
    public static final String LOCATION_EXTRAS_KEY_MATCH_ROAD_COURSE = "match_road_course";
    public static final String LOCATION_EXTRAS_KEY_MATCH_ROAD_POS = "match_road_pos";
    public static final String LOCATION_EXTRAS_KEY_POIID = "poiid";
    public static final String LOCATION_EXTRAS_KEY_ROAD_COURSE = "road_course";
    public static final String LOCATION_EXTRAS_KEY_SYSTIME = "systime";
    private static final int LOCATION_TYPE_DEFAULT = 7;
    private static final int LOCATION_TYPE_NETWORK = 6;
    protected static final int MSG_ON_LOCATION_GPS_FAIL = 241;
    protected static final int MSG_ON_LOCATION_GPS_SUCCESS = 243;
    protected static final int MSG_ON_LOCATION_OK = 240;
    protected static final int MSG_ON_LOCATION_ORIGINAL_OK = 242;
    public static final String OPTIMIZD_ACCURACY_KEY = "optimizedAccuracy";
    public static final String TAG = "com.autonavi.sdk.location.LocationInstrument";
    private static LocationInstrument instance;
    private long backUpCurTimes = 0;
    private float distance = 0.0f;
    private boolean firstLocateCompleted = false;
    /* access modifiers changed from: private */
    public volatile boolean giveUpOneNetworkLocation = false;
    private eos gpsCollector = new eos();
    private eot gpsStruct = new eot();
    private long interval = 1000;
    private boolean isGpsFirmwareExist = false;
    private boolean isGpsPermission = false;
    /* access modifiers changed from: private */
    public boolean isInitAE = false;
    private boolean isRemoved = false;
    /* access modifiers changed from: private */
    public volatile boolean isStartLoc = false;
    private volatile boolean isStartLoc2 = false;
    private LocInfo locInfo;
    private ane locWrapperListener;
    private eow locationCache = new eow();
    private b locationChangedListener = new b(this, 0);
    /* access modifiers changed from: private */
    public final LocationManagerProxy locationManager;
    private int locationProvider = 7;
    /* access modifiers changed from: private */
    public boolean mFakeNetworkLocation;
    /* access modifiers changed from: private */
    public eou mHandler = new eou(this, Looper.getMainLooper());
    /* access modifiers changed from: private */
    public ReentrantReadWriteLock mHandlerLock = new ReentrantReadWriteLock();
    /* access modifiers changed from: private */
    public volatile boolean mHasRestTaskNoSend = false;
    private long mLastAmdcUpdateTime;
    private Location mLocation;
    /* access modifiers changed from: private */
    public List<ILocationEventListener> mLocationEventListenerList = new CopyOnWriteArrayList();
    private a mLocationStatusChangedListener = new a(this, 0);
    /* access modifiers changed from: private */
    public LocationNmeaListener mNmeaListener = new LocationNmeaListener() {
        public final void onNmeaStringReceived(long j, String str) {
            if (str != null && str.contains("GSV")) {
                if (epk.a) {
                    epk.a(LocationInstrument.TAG, "传递nmea语句 ".concat(String.valueOf(str)));
                }
                anf.a(j, str);
            }
        }
    };
    /* access modifiers changed from: private */
    public volatile boolean mOnCarNavi;
    private Runnable mOnLocateStartTask = new Runnable() {
        public final void run() {
            LocationInstrument.this.onLocateStart();
        }
    };
    private Runnable mOnLocateStopTask = new Runnable() {
        public final void run() {
            LocationInstrument.this.onLocateStop();
        }
    };
    private volatile AtomicLong mStartLocateSequence = new AtomicLong(0);
    private wo mUtil;
    /* access modifiers changed from: private */
    public c mWorkHandler = null;
    private d mWorkThread = null;
    /* access modifiers changed from: private */
    public Location originalLoc;
    private com.autonavi.common.impl.Locator.b rect;
    AtomicInteger requireCount = new AtomicInteger(0);
    /* access modifiers changed from: private */
    public eor statusChecker;
    private LocationStorage storage = ((LocationStorage) bkl.a.b(LocationStorage.class, AMapAppGlobal.getApplication()));

    class a implements LocationStatusListener {
        public long a;
        public boolean b;

        private a() {
            this.a = 0;
            this.b = false;
        }

        /* synthetic */ a(LocationInstrument locationInstrument, byte b2) {
            this();
        }

        public final void onStatusChanged(String str, long j, long j2, Bundle bundle) {
            if ("sub_gps_switch".equals(str)) {
                this.b = j > 0;
                this.a = j2;
                eoy.a().a(this.a, this.b);
            }
        }
    }

    class b implements LocationListener {
        public final void onProviderEnabled(String str) {
        }

        public final void onStatusChanged(String str, int i, Bundle bundle) {
        }

        private b() {
        }

        /* synthetic */ b(LocationInstrument locationInstrument, byte b) {
            this();
        }

        public final void onLocationChanged(Location location) {
            if (location != null && (location.getLatitude() != 0.0d || location.getLongitude() != 0.0d)) {
                LocationInstrument.this.updateAdCode(location);
                boolean z = false;
                if (LocationInstrument.this.giveUpOneNetworkLocation) {
                    LocationInstrument.this.giveUpOneNetworkLocation = false;
                    if ("network".equals(location.getProvider())) {
                        return;
                    }
                }
                ku.a().a((String) "onLocationChanged", location);
                if (epk.a) {
                    String str = LocationInstrument.TAG;
                    StringBuilder sb = new StringBuilder("收到定位SDK位置：");
                    sb.append(LocationInstrument.getLocationString(location));
                    epk.a(str, sb.toString());
                }
                if (WidgetType.GPS.equals(location.getProvider()) && LocationInstrument.this.statusChecker != null) {
                    LocationInstrument.this.statusChecker.a();
                    LocationInstrument.this.mHandler.obtainMessage(243).sendToTarget();
                }
                eoy.a().a(location);
                if (anf.a) {
                    if (LocationInstrument.this.isInitAE && a(location)) {
                        if (bno.a && LocationInstrument.this.mFakeNetworkLocation) {
                            z = true;
                        }
                        anf.a(location, z);
                    }
                    LocationInstrument.this.mHandler.obtainMessage(242).sendToTarget();
                    DeviceInfo.getInstance(AMapAppGlobal.getApplication()).setLocation((int) (location.getLongitude() * 1000000.0d), (int) (location.getLatitude() * 1000000.0d), (int) location.getAccuracy());
                }
                LocationInstrument.this.originalLoc = location;
            }
        }

        private boolean a(Location location) {
            if (location != null && LocationInstrument.this.mOnCarNavi && "network".equals(location.getProvider())) {
                try {
                    int i = location.getExtras().getInt("locType", 0);
                    if (i == 2 || i == 3) {
                        return false;
                    }
                } catch (Throwable unused) {
                }
            }
            return true;
        }

        public final void onProviderDisabled(String str) {
            if (str.equals(WidgetType.GPS) && LocationInstrument.this.isStartLoc) {
                LocationInstrument.this.mHandler.obtainMessage(241).sendToTarget();
            }
        }
    }

    final class c extends Handler {
        public c(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            try {
                if (message.what == 1) {
                    Location location = (Location) message.obj;
                    if (location != null) {
                        for (ILocationEventListener onLocationChanged : LocationInstrument.this.mLocationEventListenerList) {
                            onLocationChanged.onLocationChanged(location);
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
    }

    final class d extends HandlerThread {
        volatile boolean a;

        /* synthetic */ d(LocationInstrument locationInstrument, String str, byte b2) {
            this(str);
        }

        private d(String str) {
            super(str);
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Can't wrap try/catch for region: R(7:8|9|(2:11|12)|13|14|15|16) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0055 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onLooperPrepared() {
            /*
                r4 = this;
                com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.this
                java.util.concurrent.locks.ReentrantReadWriteLock r0 = r0.mHandlerLock
                java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
                r0.lock()
                android.os.Looper r0 = r4.getLooper()     // Catch:{ all -> 0x0069 }
                boolean r1 = r4.a     // Catch:{ all -> 0x0069 }
                if (r1 == 0) goto L_0x0028
                if (r0 == 0) goto L_0x001a
                r0.quit()     // Catch:{ all -> 0x0069 }
            L_0x001a:
                com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.this
                java.util.concurrent.locks.ReentrantReadWriteLock r0 = r0.mHandlerLock
                java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
                r0.unlock()
                return
            L_0x0028:
                com.autonavi.sdk.location.LocationInstrument r1 = com.autonavi.sdk.location.LocationInstrument.this     // Catch:{ all -> 0x0069 }
                com.autonavi.sdk.location.LocationInstrument$c r2 = new com.autonavi.sdk.location.LocationInstrument$c     // Catch:{ all -> 0x0069 }
                com.autonavi.sdk.location.LocationInstrument r3 = com.autonavi.sdk.location.LocationInstrument.this     // Catch:{ all -> 0x0069 }
                r2.<init>(r0)     // Catch:{ all -> 0x0069 }
                r1.mWorkHandler = r2     // Catch:{ all -> 0x0069 }
                com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.this     // Catch:{ all -> 0x0069 }
                boolean r0 = r0.mHasRestTaskNoSend     // Catch:{ all -> 0x0069 }
                if (r0 == 0) goto L_0x0055
                com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.this     // Catch:{ SecurityException -> 0x0055 }
                com.amap.location.sdk.fusion.LocationManagerProxy r0 = r0.locationManager     // Catch:{ SecurityException -> 0x0055 }
                com.autonavi.sdk.location.LocationInstrument r1 = com.autonavi.sdk.location.LocationInstrument.this     // Catch:{ SecurityException -> 0x0055 }
                com.amap.location.sdk.fusion.interfaces.LocationNmeaListener r1 = r1.mNmeaListener     // Catch:{ SecurityException -> 0x0055 }
                com.autonavi.sdk.location.LocationInstrument r2 = com.autonavi.sdk.location.LocationInstrument.this     // Catch:{ SecurityException -> 0x0055 }
                com.autonavi.sdk.location.LocationInstrument$c r2 = r2.mWorkHandler     // Catch:{ SecurityException -> 0x0055 }
                android.os.Looper r2 = r2.getLooper()     // Catch:{ SecurityException -> 0x0055 }
                r0.addNmeaListener(r1, r2)     // Catch:{ SecurityException -> 0x0055 }
            L_0x0055:
                com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.this     // Catch:{ all -> 0x0069 }
                r1 = 0
                r0.mHasRestTaskNoSend = r1     // Catch:{ all -> 0x0069 }
                com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.this
                java.util.concurrent.locks.ReentrantReadWriteLock r0 = r0.mHandlerLock
                java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
                r0.unlock()
                return
            L_0x0069:
                r0 = move-exception
                com.autonavi.sdk.location.LocationInstrument r1 = com.autonavi.sdk.location.LocationInstrument.this
                java.util.concurrent.locks.ReentrantReadWriteLock r1 = r1.mHandlerLock
                java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r1 = r1.writeLock()
                r1.unlock()
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.sdk.location.LocationInstrument.d.onLooperPrepared():void");
        }
    }

    public static LocationInstrument getInstance() {
        if (instance == null) {
            synchronized (LocationInstrument.class) {
                try {
                    if (instance == null) {
                        instance = new LocationInstrument();
                    }
                }
            }
        }
        return instance;
    }

    private LocationInstrument() {
        Application application = AMapAppGlobal.getApplication();
        checkGpsFirmware();
        checkGpsPermission();
        this.locationManager = LocationManagerProxy.getInstance();
        this.locationManager.setParams(1, constructLocJson(application));
        LocationManagerProxy.getInstance().setStatusListener(this.mLocationStatusChangedListener);
        this.mLocation = new Location("network");
        this.mLocation.setLatitude(ahh.a(decode(this.storage.getLatitude())));
        this.mLocation.setLongitude(ahh.a(decode(this.storage.getLongitude())));
        this.mLocation.setAltitude(ahh.a(decode(this.storage.getAltitude())));
        this.mLocation.setAccuracy(this.storage.getAccuracy());
        this.mLocation.setTime(0);
        this.locationCache.a(this.mLocation);
        this.firstLocateCompleted = this.storage.isFistLocateCompleted();
    }

    private void checkGpsFirmware() {
        PackageManager packageManager = AMapAppGlobal.getApplication().getPackageManager();
        this.isGpsFirmwareExist = packageManager != null && packageManager.hasSystemFeature(FEATURE_LOCATION_GPS);
    }

    private void checkGpsPermission() {
        try {
            ((LocationManager) AMapAppGlobal.getApplication().getSystemService("location")).isProviderEnabled(WidgetType.GPS);
            this.isGpsPermission = true;
        } catch (Throwable unused) {
            DebugLog.error("GPS模块不存在或没有添加GPS使用权限或用户关闭了当前应用的GPS使用权限.");
            this.isGpsPermission = false;
        }
    }

    public void init() {
        this.mHandlerLock.writeLock().lock();
        if (this.mWorkThread == null) {
            this.mWorkThread = new d(this, TAG, 0);
            this.mWorkThread.start();
        }
        this.mHandlerLock.writeLock().unlock();
        if (!this.isInitAE || this.locWrapperListener == null) {
            anf.a();
            this.locWrapperListener = new ane();
            anf.a((LocListener) this, 1);
            anf.a((DriveModeObserver) this);
            ept.a();
            if (AMapAppGlobal.getApplication() != null) {
                eoy.a().a(AMapAppGlobal.getApplication().getApplicationContext());
            }
            this.isInitAE = true;
        }
    }

    public void release() {
        if (this.statusChecker != null) {
            this.statusChecker.b();
            this.statusChecker = null;
        }
        if (this.locationManager != null) {
            this.locationManager.destroy();
        }
        this.mLocation.setTime(0);
        doStopLocate();
        if (this.isInitAE) {
            anf.b(this);
            anf.b();
            if (this.locWrapperListener != null) {
                synchronized (this.locWrapperListener) {
                    this.locWrapperListener = null;
                }
            }
            this.isInitAE = false;
        }
        this.mHandlerLock.writeLock().lock();
        if (this.mWorkThread != null) {
            if (this.mWorkHandler != null) {
                this.mWorkHandler.removeCallbacksAndMessages(null);
            }
            this.mWorkThread.a = true;
            this.mWorkThread.quit();
            this.mWorkThread = null;
        }
        this.mHandlerLock.writeLock().unlock();
    }

    public String getLocationLog(Context context) {
        String request = this.locationManager.getRequest(getLatestLocation().getProvider());
        if (!TextUtils.isEmpty(request)) {
            String mapBaseStorage = FileUtil.getMapBaseStorage(context);
            if (!TextUtils.isEmpty(mapBaseStorage)) {
                StringBuilder sb = new StringBuilder();
                sb.append(mapBaseStorage);
                sb.append("/autonavi/location.log");
                File file = new File(sb.toString());
                FileUtil.writeTextFile(file, request);
                return file.getAbsolutePath();
            }
        }
        return null;
    }

    public void subscribe(Context context, LOCATION_SCENE location_scene) {
        JSONObject constructLocJson = constructLocJson(context);
        try {
            constructLocJson.put(LocationParams.PARA_COMMON_LOC_SCENE, location_scene.type);
            if (epk.a) {
                String str = TAG;
                StringBuilder sb = new StringBuilder("sub业务场景回调：");
                sb.append(location_scene.type);
                epk.a(str, sb.toString());
            }
        } catch (Throwable unused) {
        }
        this.locationManager.setParams(1, constructLocJson);
    }

    public void unsubscribe(Context context) {
        JSONObject constructLocJson = constructLocJson(context);
        try {
            constructLocJson.put(LocationParams.PARA_COMMON_LOC_SCENE, LOCATION_SCENE.TYPE_DEFAULT.type);
            if (epk.a) {
                String str = TAG;
                StringBuilder sb = new StringBuilder("unsub业务场景回调：");
                sb.append(LOCATION_SCENE.TYPE_DEFAULT.type);
                epk.a(str, sb.toString());
            }
        } catch (Exception unused) {
        }
        this.locationManager.setParams(1, constructLocJson);
    }

    public void setFeedbackTime(Context context, long j) {
        JSONObject constructLocJson = constructLocJson(context);
        try {
            constructLocJson.put(LocationParams.PARA_FEEDBAK_TIME, j);
            constructLocJson.put(LocationParams.PARA_COMMON_LOC_SCENE, LOCATION_SCENE.TYPE_DEFAULT.type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.locationManager.setParams(3, constructLocJson);
    }

    private JSONObject constructLocJson(Context context) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(LocationParams.PARA_COMMON_DIP, URLEncoder.encode(NetworkParam.getDip(), "UTF-8"));
            jSONObject.put(LocationParams.PARA_COMMON_DIV, URLEncoder.encode(NetworkParam.getDiv(), "UTF-8"));
            jSONObject.put(LocationParams.PARA_COMMON_DIBV, URLEncoder.encode(NetworkParam.getDibv(), "UTF-8"));
            jSONObject.put(LocationParams.PARA_COMMON_DIE, "Unknown");
            jSONObject.put(LocationParams.PARA_COMMON_DID, "Unknown");
            jSONObject.put(LocationParams.PARA_COMMON_DIC, URLEncoder.encode(NetworkParam.getDic(), "UTF-8"));
            jSONObject.put(LocationParams.PARA_COMMON_DIU, URLEncoder.encode(NetworkParam.getDiu(), "UTF-8"));
            String adiu = NetworkParam.getAdiu();
            if (!TextUtils.isEmpty(adiu)) {
                jSONObject.put(LocationParams.PARA_COMMON_ADIU, URLEncoder.encode(adiu, "UTF-8"));
            }
            if (context != null) {
                jSONObject.put(LocationParams.PARA_COMMON_DIU2, URLEncoder.encode(agq.d(context), "UTF-8"));
            }
            jSONObject.put(LocationParams.PARA_COMMON_DIU3, URLEncoder.encode(NetworkParam.getIsn(), "UTF-8"));
            jSONObject.put(LocationParams.PARA_COMMON_CIFA, NetworkParam.getCifa());
            jSONObject.put("channel", serverkey.getAosChannel());
            jSONObject.put("from", "Unknown");
            jSONObject.put("tid", NetworkParam.getTaobaoID());
        } catch (Throwable th) {
            new StringBuilder("constructLocJson:").append(String.valueOf(th.getMessage()));
        }
        return jSONObject;
    }

    public LocationManagerProxy getProxy() {
        return this.locationManager;
    }

    public boolean isProviderUsed(Provider provider, int i) {
        return (provider.value() & i) > 0;
    }

    public void setParams(int i, JSONObject jSONObject) {
        this.locationManager.setParams(i, jSONObject);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x002c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.location.Location getLatestLocation() {
        /*
            r5 = this;
            android.location.Location r0 = new android.location.Location
            android.location.Location r1 = r5.mLocation
            r0.<init>(r1)
            boolean r1 = r5.firstLocateCompleted
            if (r1 != 0) goto L_0x0029
            com.autonavi.common.model.GeoPoint r1 = r5.getMapCenter()
            if (r1 == 0) goto L_0x0029
            int r2 = r1.x
            if (r2 == 0) goto L_0x0029
            int r2 = r1.y
            if (r2 == 0) goto L_0x0029
            r2 = 1
            double r3 = r1.getLatitude()
            r0.setLatitude(r3)
            double r3 = r1.getLongitude()
            r0.setLongitude(r3)
            goto L_0x002a
        L_0x0029:
            r2 = 0
        L_0x002a:
            if (r2 != 0) goto L_0x003a
            float r1 = r5.getLatestAccuracy()
            float r2 = r5.getGpsBearing()
            r0.setAccuracy(r1)
            r0.setBearing(r2)
        L_0x003a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.sdk.location.LocationInstrument.getLatestLocation():android.location.Location");
    }

    public GeoPoint getLatestPosition() {
        GeoPoint mapCenter = !this.firstLocateCompleted ? getMapCenter() : null;
        return mapCenter == null ? this.locationCache.a() : mapCenter;
    }

    public GeoPoint getLatestPosition(boolean z) {
        GeoPoint mapCenter = (this.firstLocateCompleted || !z) ? null : getMapCenter();
        return mapCenter == null ? this.locationCache.a() : mapCenter;
    }

    public GeoPoint getLatestPosition(int i) {
        long j;
        if (this.mLocation != null) {
            Bundle extras = this.mLocation.getExtras();
            if (extras != null) {
                j = extras.getLong(LOCATION_EXTRAS_KEY_SYSTIME);
                if (i > 0 || System.currentTimeMillis() - j <= ((long) (i * 60 * 1000))) {
                    return getLatestPosition();
                }
                return null;
            }
        }
        j = 0;
        if (i > 0) {
        }
        return getLatestPosition();
    }

    public GeoPoint getCacheLatestPosition() {
        if (!this.firstLocateCompleted) {
            return null;
        }
        return this.locationCache.a();
    }

    public void addStatusCallback(Callback<Status> callback, Object obj) {
        eou eou = this.mHandler;
        if (bno.a && Looper.getMainLooper() != Looper.myLooper()) {
            try {
                throw new Exception("addStatusCallback is Not in Main Thread!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        synchronized (eou.b) {
            eou.a.add(callback);
        }
    }

    public void addHighFrequencyStatusCallback(Callback<Status> callback, Object obj) {
        eou eou = this.mHandler;
        synchronized (eou.e) {
            eou.f.add(callback);
        }
    }

    public void removeHighFrequencyStatusCallback(Callback<Status> callback) {
        eou eou = this.mHandler;
        synchronized (eou.e) {
            eou.f.remove(callback);
        }
    }

    public void removeStatusCallback(Callback<Status> callback) {
        eou eou = this.mHandler;
        if (bno.a && Looper.getMainLooper() != Looper.myLooper()) {
            try {
                throw new Exception("removeStatusCallback is Not in Main Thread!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        synchronized (eou.b) {
            eou.a.remove(callback);
        }
    }

    public boolean removeStatusCallbackForAsyncTask(Callback<Status> callback) {
        return this.mHandler.a(callback);
    }

    public boolean isProviderEnabled(Provider provider) {
        boolean z;
        if (this.locationManager == null) {
            return false;
        }
        try {
            z = this.locationManager.isProviderEnabled(provider == Provider.PROVIDER_GPS ? 1 : 2);
        } catch (Throwable unused) {
            DebugLog.error("GPS模块不存在或没有添加GPS使用权限或用户关闭了当前应用的GPS使用权限.");
            z = false;
        }
        return z;
    }

    public void setInterval(int i) {
        long j = (long) i;
        if (this.interval != j && i >= 1000) {
            this.interval = j;
            if (this.locationManager != null) {
                requestLocationManagerUpdates(this.locationProvider);
            }
        }
    }

    public void setProvider(Provider... providerArr) {
        if (this.locationManager != null) {
            if (providerArr == null || providerArr.length <= 0) {
                removeLocationManagerUpdates(false);
                AMapLog.i(TAG, "providers is null");
                return;
            }
            int i = 0;
            for (Provider provider : providerArr) {
                if (provider != null) {
                    i |= provider.value();
                    if (i >= 7) {
                        break;
                    }
                }
            }
            int i2 = this.locationProvider;
            if (isProviderUsed(Provider.PROVIDER_NETWORK, i) && needGiveupFirstNetworkLocation(i2, i)) {
                this.giveUpOneNetworkLocation = true;
            }
            requestLocationManagerUpdates(i);
        }
    }

    private boolean needGiveupFirstNetworkLocation(int i, int i2) {
        if (!isProviderUsed(Provider.PROVIDER_NETWORK, i2) || isProviderUsed(Provider.PROVIDER_NETWORK, i)) {
            return false;
        }
        long j = 0;
        if (this.mLocation != null) {
            Bundle extras = this.mLocation.getExtras();
            if (extras != null) {
                j = extras.getLong(LOCATION_EXTRAS_KEY_SYSTIME);
            }
        }
        if (!isProviderUsed(Provider.PROVIDER_GPS, i) || !isProviderUsed(Provider.PROVIDER_GPS, i2) || !WidgetType.GPS.equals(this.mLocation.getProvider()) || Math.abs(System.currentTimeMillis() - j) >= BalloonLayout.DEFAULT_DISPLAY_DURATION) {
            return false;
        }
        return true;
    }

    public List<GeoPoint> getLatestGeoPoints() {
        return this.gpsStruct.a;
    }

    public List<Location> getLatestGpsLocations() {
        return this.gpsCollector.a;
    }

    public List<Long> getLatestTimes() {
        return this.gpsStruct.b;
    }

    public List<Float> getLatestSpeeds() {
        return this.gpsStruct.c;
    }

    public List<Float> getLatestBears() {
        return this.gpsStruct.d;
    }

    private float getLatestAccuracy() {
        if (this.mLocation == null) {
            return 0.0f;
        }
        return Math.abs(this.mLocation.getAccuracy());
    }

    public synchronized boolean isLocating() {
        return this.isStartLoc;
    }

    public synchronized boolean isLocating2() {
        return this.isStartLoc2;
    }

    public void addLocationEventListener(ILocationEventListener iLocationEventListener) {
        this.mLocationEventListenerList.add(iLocationEventListener);
    }

    /* access modifiers changed from: private */
    public void onLocateStart() {
        for (ILocationEventListener onLocateStart : this.mLocationEventListenerList) {
            onLocateStart.onLocateStart();
        }
    }

    public synchronized void doStartLocate() {
        doStartLocate(false);
    }

    public synchronized void doStartLocate(boolean z) {
        ku a2 = ku.a();
        if (bno.a) {
            if (a2.b == null) {
                a2.b = new Timer("AELogUtil.Timer");
            }
            if (a2.c == null) {
                a2.c = new TimerTask("######") {
                    final /* synthetic */ String a;

                    {
                        this.a = r2;
                    }

                    public final void run() {
                        StringBuilder sb = new StringBuilder();
                        sb.append(ku.b());
                        sb.append("  ");
                        sb.append(this.a);
                        sb.append("\n");
                        FileUtil.writeStrToFileByAppend(ku.this.a((String) ""), sb.toString());
                    }
                };
                a2.b.scheduleAtFixedRate(a2.c, 0, 1000);
            }
        }
        if (this.locWrapperListener != null) {
            synchronized (this.locWrapperListener) {
                ane ane = this.locWrapperListener;
                if (anf.a) {
                    if (VERSION.SDK_INT < 24) {
                        getInstance().addGpsStatusListener(ane);
                    } else {
                        getInstance().registerGnssStatusCallback(ane.a);
                    }
                }
            }
        }
        if (this.locationManager != null) {
            if (this.isStartLoc) {
                this.requireCount.getAndIncrement();
                return;
            }
            int i = 0;
            try {
                if (this.isGpsFirmwareExist && this.isGpsPermission && isProviderUsed(Provider.PROVIDER_GPS, this.locationProvider)) {
                    i = 1;
                }
                if (isProviderUsed(Provider.PROVIDER_NETWORK, this.locationProvider)) {
                    i |= 6;
                }
                requestLocationManagerUpdates(i, z);
                this.isStartLoc = true;
                this.requireCount.getAndIncrement();
            } catch (Throwable unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void onLocateStop() {
        Boolean a2 = this.mUtil != null ? this.mUtil.a() : null;
        if (a2 != null && !a2.booleanValue() && !this.isStartLoc2 && this.isStartLoc) {
            doStopLocate(true);
        }
        for (ILocationEventListener onLocateStop : this.mLocationEventListenerList) {
            onLocateStop.onLocateStop();
        }
    }

    public synchronized void doStopLocate() {
        doStopLocate(false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00c3, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void doStopLocate(boolean r5) {
        /*
            r4 = this;
            monitor-enter(r4)
            ku r0 = defpackage.ku.a()     // Catch:{ all -> 0x00c6 }
            java.util.TimerTask r1 = r0.c     // Catch:{ all -> 0x00c6 }
            r2 = 0
            if (r1 == 0) goto L_0x0011
            java.util.TimerTask r1 = r0.c     // Catch:{ all -> 0x00c6 }
            r1.cancel()     // Catch:{ all -> 0x00c6 }
            r0.c = r2     // Catch:{ all -> 0x00c6 }
        L_0x0011:
            java.util.Timer r1 = r0.b     // Catch:{ all -> 0x00c6 }
            if (r1 == 0) goto L_0x001c
            java.util.Timer r1 = r0.b     // Catch:{ all -> 0x00c6 }
            r1.cancel()     // Catch:{ all -> 0x00c6 }
            r0.b = r2     // Catch:{ all -> 0x00c6 }
        L_0x001c:
            ane r0 = r4.locWrapperListener     // Catch:{ all -> 0x00c6 }
            if (r0 == 0) goto L_0x0045
            ane r0 = r4.locWrapperListener     // Catch:{ all -> 0x00c6 }
            monitor-enter(r0)     // Catch:{ all -> 0x00c6 }
            ane r1 = r4.locWrapperListener     // Catch:{ all -> 0x0042 }
            boolean r2 = defpackage.anf.a     // Catch:{ all -> 0x0042 }
            if (r2 == 0) goto L_0x0040
            int r2 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x0042 }
            r3 = 24
            if (r2 >= r3) goto L_0x0037
            com.autonavi.sdk.location.LocationInstrument r2 = getInstance()     // Catch:{ all -> 0x0042 }
            r2.removeGpsStatusListener(r1)     // Catch:{ all -> 0x0042 }
            goto L_0x0040
        L_0x0037:
            com.autonavi.sdk.location.LocationInstrument r2 = getInstance()     // Catch:{ all -> 0x0042 }
            android.location.GnssStatus$Callback r1 = r1.a     // Catch:{ all -> 0x0042 }
            r2.unregisterGnssStatusCallback(r1)     // Catch:{ all -> 0x0042 }
        L_0x0040:
            monitor-exit(r0)     // Catch:{ all -> 0x0042 }
            goto L_0x0045
        L_0x0042:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0042 }
            throw r5     // Catch:{ all -> 0x00c6 }
        L_0x0045:
            com.amap.location.sdk.fusion.LocationManagerProxy r0 = r4.locationManager     // Catch:{ all -> 0x00c6 }
            if (r0 != 0) goto L_0x004b
            monitor-exit(r4)
            return
        L_0x004b:
            r0 = 0
            r4.isStartLoc = r0     // Catch:{ Throwable -> 0x00c4 }
            eot r1 = r4.gpsStruct     // Catch:{ Throwable -> 0x00c4 }
            if (r1 == 0) goto L_0x0057
            eot r1 = r4.gpsStruct     // Catch:{ Throwable -> 0x00c4 }
            r1.a()     // Catch:{ Throwable -> 0x00c4 }
        L_0x0057:
            eos r1 = r4.gpsCollector     // Catch:{ Throwable -> 0x00c4 }
            r1.a()     // Catch:{ Throwable -> 0x00c4 }
            eor r1 = r4.statusChecker     // Catch:{ Throwable -> 0x00c4 }
            if (r1 == 0) goto L_0x006a
            eor r1 = r4.statusChecker     // Catch:{ Throwable -> 0x00c4 }
            r1.a()     // Catch:{ Throwable -> 0x00c4 }
            eor r1 = r4.statusChecker     // Catch:{ Throwable -> 0x00c4 }
            r1.b()     // Catch:{ Throwable -> 0x00c4 }
        L_0x006a:
            java.util.concurrent.atomic.AtomicInteger r1 = r4.requireCount     // Catch:{ Throwable -> 0x00c4 }
            r1.set(r0)     // Catch:{ Throwable -> 0x00c4 }
            r4.removeLocationManagerUpdates(r5)     // Catch:{ Throwable -> 0x00c4 }
            epp r5 = defpackage.epp.a()     // Catch:{ Throwable -> 0x00c4 }
            r0 = 4
            r5.a(r0)     // Catch:{ Throwable -> 0x00c4 }
            android.location.Location r5 = r4.mLocation     // Catch:{ Throwable -> 0x00c4 }
            if (r5 == 0) goto L_0x00c2
            com.autonavi.sdk.location.LocationStorage r5 = r4.storage     // Catch:{ Throwable -> 0x00c4 }
            android.location.Location r0 = r4.mLocation     // Catch:{ Throwable -> 0x00c4 }
            double r0 = r0.getLatitude()     // Catch:{ Throwable -> 0x00c4 }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x00c4 }
            java.lang.String r0 = r4.encode(r0)     // Catch:{ Throwable -> 0x00c4 }
            r5.setLatitude(r0)     // Catch:{ Throwable -> 0x00c4 }
            com.autonavi.sdk.location.LocationStorage r5 = r4.storage     // Catch:{ Throwable -> 0x00c4 }
            android.location.Location r0 = r4.mLocation     // Catch:{ Throwable -> 0x00c4 }
            double r0 = r0.getLongitude()     // Catch:{ Throwable -> 0x00c4 }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x00c4 }
            java.lang.String r0 = r4.encode(r0)     // Catch:{ Throwable -> 0x00c4 }
            r5.setLongitude(r0)     // Catch:{ Throwable -> 0x00c4 }
            com.autonavi.sdk.location.LocationStorage r5 = r4.storage     // Catch:{ Throwable -> 0x00c4 }
            android.location.Location r0 = r4.mLocation     // Catch:{ Throwable -> 0x00c4 }
            double r0 = r0.getAltitude()     // Catch:{ Throwable -> 0x00c4 }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x00c4 }
            java.lang.String r0 = r4.encode(r0)     // Catch:{ Throwable -> 0x00c4 }
            r5.setAltitude(r0)     // Catch:{ Throwable -> 0x00c4 }
            com.autonavi.sdk.location.LocationStorage r5 = r4.storage     // Catch:{ Throwable -> 0x00c4 }
            android.location.Location r0 = r4.mLocation     // Catch:{ Throwable -> 0x00c4 }
            float r0 = r0.getAccuracy()     // Catch:{ Throwable -> 0x00c4 }
            r5.setAccuracy(r0)     // Catch:{ Throwable -> 0x00c4 }
        L_0x00c2:
            monitor-exit(r4)
            return
        L_0x00c4:
            monitor-exit(r4)
            return
        L_0x00c6:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.sdk.location.LocationInstrument.doStopLocate(boolean):void");
    }

    public void saveCacheLocate() {
        if (this.locationManager != null) {
            try {
                if (this.mLocation != null) {
                    this.storage.setLatitude(encode(String.valueOf(this.mLocation.getLatitude())));
                    this.storage.setLongitude(encode(String.valueOf(this.mLocation.getLongitude())));
                    this.storage.setAltitude(encode(String.valueOf(this.mLocation.getAltitude())));
                    this.storage.setAccuracy(this.mLocation.getAccuracy());
                }
            } catch (Throwable unused) {
            }
        }
    }

    public long getStartLocateSequence() {
        return this.mStartLocateSequence.get();
    }

    public void requestLocationManagerUpdates(int i) {
        requestLocationManagerUpdates(i, false);
    }

    private void requestLocationManagerUpdates(int i, boolean z) {
        if (this.locationManager != null) {
            this.isStartLoc2 = true;
            if (i != 0) {
                this.locationProvider = i;
                this.mHandler.c = this.locationProvider;
            }
            if (epk.a) {
                String str = TAG;
                StringBuilder sb = new StringBuilder("请求定位SDK：");
                sb.append(this.locationProvider);
                epk.a(str, sb.toString());
            }
            this.locationManager.requestLocationUpdates(this.locationProvider, this.interval, this.distance, this.locationChangedListener);
            this.mStartLocateSequence.incrementAndGet();
            if (!z) {
                aho.b(this.mOnLocateStopTask);
                aho.b(this.mOnLocateStartTask);
                aho.a(this.mOnLocateStartTask);
            }
        }
    }

    private void removeLocationManagerUpdates(boolean z) {
        if (this.locationManager != null) {
            this.isStartLoc2 = false;
            if (epk.a) {
                String str = TAG;
                StringBuilder sb = new StringBuilder("移除定位SDK：");
                sb.append(this.locationProvider);
                epk.a(str, sb.toString());
            }
            this.locationManager.removeUpdates(this.locationChangedListener);
            this.mStartLocateSequence.incrementAndGet();
            if (!z) {
                aho.b(this.mOnLocateStopTask);
                aho.b(this.mOnLocateStartTask);
                aho.a(this.mOnLocateStopTask);
            }
        }
    }

    private void setStartGpsStructValue(GeoPoint geoPoint, long j) {
        if (this.gpsStruct == null) {
            this.gpsStruct = new eot();
        }
        if ((j - this.backUpCurTimes) / 1000 <= 1 || this.backUpCurTimes == 0) {
            try {
                this.gpsStruct.a.add(new GeoPoint(geoPoint.x, geoPoint.y));
                if (this.gpsStruct.a.size() >= 3) {
                    this.gpsStruct.a.remove(0);
                }
                this.gpsStruct.b.add(Long.valueOf(j));
                if (this.gpsStruct.b.size() >= 3) {
                    this.gpsStruct.b.remove(0);
                }
                this.gpsStruct.d.add(Float.valueOf(getGpsBearing()));
                if (this.gpsStruct.d.size() >= 3) {
                    this.gpsStruct.d.remove(0);
                }
                if (this.mLocation != null) {
                    this.gpsStruct.c.add(Float.valueOf(this.mLocation.getSpeed()));
                }
                if (this.gpsStruct.c.size() >= 3) {
                    this.gpsStruct.c.remove(0);
                }
            } catch (Throwable unused) {
            }
        } else {
            this.gpsStruct.a.clear();
            this.gpsStruct.a.add(new GeoPoint(geoPoint.x, geoPoint.y));
            this.gpsStruct.b.clear();
            this.gpsStruct.b.add(Long.valueOf(j));
            this.gpsStruct.d.clear();
            this.gpsStruct.d.add(Float.valueOf(getGpsBearing()));
            this.gpsStruct.c.clear();
            if (this.mLocation != null) {
                this.gpsStruct.c.add(Float.valueOf(this.mLocation.getSpeed()));
            }
        }
        this.backUpCurTimes = j;
    }

    private float getGpsBearing() {
        if (this.mLocation != null) {
            return this.mLocation.getBearing();
        }
        return 0.0f;
    }

    public void addGpsStatusListener(Listener listener) {
        if (this.locationManager != null) {
            this.locationManager.addGpsStatusListener(listener, Looper.getMainLooper());
        }
    }

    public void removeGpsStatusListener(Listener listener) {
        if (this.locationManager != null) {
            this.locationManager.removeGpsStatusListener(listener);
        }
    }

    public void registerGnssStatusCallback(GnssStatus.Callback callback) {
        if (this.locationManager != null && VERSION.SDK_INT >= 24) {
            this.locationManager.registerGnssStatusCallback(callback, Looper.getMainLooper());
        }
    }

    public void unregisterGnssStatusCallback(GnssStatus.Callback callback) {
        if (this.locationManager != null && VERSION.SDK_INT >= 24) {
            this.locationManager.unregisterGnssStatusCallback(callback);
        }
    }

    public GpsStatus getGpsStatus(GpsStatus gpsStatus) {
        return this.locationManager != null ? this.locationManager.getGpsStatus(gpsStatus) : gpsStatus;
    }

    public void addSatelliteListener(LocationSatelliteListener locationSatelliteListener, Looper looper) {
        if (this.locationManager != null) {
            this.locationManager.addSatelliteListener(locationSatelliteListener, looper);
        }
    }

    public void removeSatelliteListener(LocationSatelliteListener locationSatelliteListener) {
        if (this.locationManager != null) {
            this.locationManager.removeSatelliteListener(locationSatelliteListener);
        }
    }

    public void addNmeaListener(LocationNmeaListener locationNmeaListener, Looper looper) {
        if (this.locationManager != null) {
            this.locationManager.addNmeaListener(locationNmeaListener, looper);
        }
    }

    public void removeNmeaListener(LocationNmeaListener locationNmeaListener) {
        if (this.locationManager != null) {
            this.locationManager.removeNmeaListener(locationNmeaListener);
        }
    }

    public void getLocation(Callback<Location> callback, int i) {
        new AsyncGetLocationTask(callback, this.mHandler, i).doGet();
    }

    public com.autonavi.common.Callback.a getPosition(Callback<GeoPoint> callback, int i) {
        AsyncGetLocationTask asyncGetLocationTask = new AsyncGetLocationTask(callback, this.mHandler, i);
        asyncGetLocationTask.doGet();
        return asyncGetLocationTask;
    }

    private GeoPoint getMapCenter() {
        if (this.rect == null) {
            return null;
        }
        return this.rect.a();
    }

    public void setMapRect(com.autonavi.common.impl.Locator.b bVar) {
        this.rect = bVar;
    }

    public void fakeNetworkLocation(boolean z) {
        this.mFakeNetworkLocation = z;
    }

    public void updateNaviInfo(com.autonavi.jni.ae.pos.LocInfo locInfo2) {
        if (!this.isRemoved) {
            LocInfo buildLocInfo = new LocInfo().buildLocInfo(locInfo2);
            if (locInfo2 != null && buildLocInfo != null) {
                Location a2 = anf.a(buildLocInfo);
                if (a2 != null) {
                    this.locInfo = buildLocInfo;
                    if (this.mLocation != null) {
                        this.mLocation.set(a2);
                        if (!(buildLocInfo.isOnGuideRoad == 8 || buildLocInfo.isOnGuideRoad == 2)) {
                            this.locationCache.a(this.mLocation);
                        }
                        if (!this.firstLocateCompleted) {
                            this.storage.setFistLocateCompleted(true);
                            this.storage.setLatitude(encode(String.valueOf(a2.getLatitude())));
                            this.storage.setLongitude(encode(String.valueOf(a2.getLongitude())));
                            this.storage.setAltitude(encode(String.valueOf(a2.getAltitude())));
                            this.storage.setAccuracy(a2.getAccuracy());
                            this.firstLocateCompleted = true;
                        }
                        boolean equals = this.mLocation.getProvider().equals(WidgetType.GPS);
                        this.mHandler.obtainMessage(240, Boolean.valueOf(equals)).sendToTarget();
                        ku.a().a((String) "updateNaviInfo", a2);
                        if (locInfo2.locInfoChange) {
                            eoy.a().b(a2);
                        }
                        if (!equals) {
                            if (this.gpsStruct != null) {
                                this.gpsStruct.a();
                            }
                            this.gpsCollector.a();
                        } else if (((double) this.mLocation.getSpeed()) * 3.6d > 1.0d) {
                            eos eos = this.gpsCollector;
                            Bundle extras = a2.getExtras();
                            long j = 0;
                            long j2 = extras != null ? extras.getLong(LOCATION_EXTRAS_KEY_SYSTIME) : 0;
                            if ((j2 - eos.b) / 1000 <= 1 || eos.b == 0) {
                                synchronized (eos.a) {
                                    if (eos.a.size() >= 3) {
                                        eos.a.remove(0);
                                    }
                                    eos.a.add(a2);
                                }
                            } else {
                                synchronized (eos.a) {
                                    eos.a.clear();
                                }
                            }
                            eos.b = j2;
                            GeoPoint offsetedPoint = getOffsetedPoint(this.mLocation);
                            if (this.mLocation != null) {
                                Bundle extras2 = this.mLocation.getExtras();
                                if (extras2 != null) {
                                    j = extras2.getLong(LOCATION_EXTRAS_KEY_SYSTIME);
                                }
                            }
                            if (offsetedPoint != null) {
                                setStartGpsStructValue(offsetedPoint, j);
                            }
                        }
                        sendMessage(1, a2);
                    }
                }
            }
        }
    }

    public Location getOriginalLocation() {
        if (this.originalLoc == null) {
            return null;
        }
        if (IndoorLocationProvider.NAME.equals(this.originalLoc.getProvider())) {
            Bundle extras = this.originalLoc.getExtras();
            double d2 = extras.getDouble(INDOOR_LOCATION_LAT);
            double d3 = extras.getDouble(INDOOR_LOCATION_LON);
            Location location = new Location(this.originalLoc.getProvider());
            location.set(this.originalLoc);
            location.setLatitude(d2);
            location.setLongitude(d3);
            return location;
        }
        lj b2 = li.a().b(this.originalLoc.getLongitude(), this.originalLoc.getLatitude());
        if (!(b2 != null && b2.a())) {
            return this.originalLoc;
        }
        GeoPoint a2 = kk.a(this.originalLoc.getLongitude(), this.originalLoc.getLatitude());
        Location location2 = new Location(this.originalLoc.getProvider());
        location2.set(this.originalLoc);
        location2.setLatitude(a2.getLatitude());
        location2.setLongitude(a2.getLongitude());
        return location2;
    }

    public LocInfo getLocInfo() {
        return this.locInfo;
    }

    public void addOriginalLocation(ang<Status> ang) {
        eou eou = this.mHandler;
        synchronized (eou.d) {
            eou.d.add(ang);
        }
    }

    public void removeOriginalLocation(ang<Status> ang) {
        eou eou = this.mHandler;
        synchronized (eou.d) {
            eou.d.remove(ang);
        }
    }

    public void startCheckGpsStatus() {
        if (this.statusChecker != null) {
            this.statusChecker.b();
        }
        this.statusChecker = new eor(this, this.mHandler);
        eor eor = this.statusChecker;
        if (eor.b != 1) {
            eor.b = 1;
            eor.a.set(0);
            synchronized (eor.c) {
                eor.c.notify();
            }
        }
        this.statusChecker.start();
    }

    public void stopCheckGpsStatus() {
        if (this.statusChecker != null) {
            this.statusChecker.b();
            this.statusChecker = null;
        }
    }

    public void addAELocLister() {
        this.isRemoved = false;
    }

    public void removeAELocListener() {
        this.isRemoved = true;
    }

    public void clearLastCallbackTime() {
        this.mHandler.g = 0;
    }

    private String encode(String str) {
        if (str == null || !str.startsWith(ENCODE_START)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(ENCODE_START);
        sb.append(serverkey.amapEncode(str));
        return sb.toString();
    }

    private String decode(String str) {
        return (str == null || !str.startsWith(ENCODE_START)) ? str : serverkey.amapDecode(str.substring(8));
    }

    public void setUtils(wo woVar) {
        this.mUtil = woVar;
    }

    public void onDriveModeChanged(int i) {
        JSONObject constructLocJson = constructLocJson(AMapAppGlobal.getApplication());
        try {
            constructLocJson.put(LocationParams.PARA_COMMON_DRIVE_MODE, String.valueOf(i));
        } catch (Throwable unused) {
        }
        this.locationManager.setParams(1, constructLocJson);
        if (epk.a) {
            epk.a(TAG, "驾驶模式回调：".concat(String.valueOf(i)));
        }
    }

    /* access modifiers changed from: private */
    public void updateAdCode(Location location) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.mLastAmdcUpdateTime >= 120000) {
            this.mLastAmdcUpdateTime = currentTimeMillis;
            if (location != null && location.getLatitude() != 0.0d && location.getLongitude() != 0.0d) {
                try {
                    bz.a((String) AutoJsonUtils.JSON_ADCODE, String.valueOf(li.a().a(location.getLongitude(), location.getLatitude())));
                } catch (Exception unused) {
                }
            }
        }
    }

    public static GeoPoint getOffsetedPoint(Location location) {
        if (location == null) {
            return null;
        }
        if (IndoorLocationProvider.NAME.equals(location.getProvider())) {
            Bundle extras = location.getExtras();
            if (extras != null) {
                return new GeoPoint(extras.getDouble(INDOOR_LOCATION_LON), extras.getDouble(INDOOR_LOCATION_LAT));
            }
        }
        if (!anf.a) {
            lj b2 = li.a().b(location.getLongitude(), location.getLatitude());
            if (b2 != null && b2.a()) {
                return kk.a(location.getLongitude(), location.getLatitude());
            }
        }
        return new GeoPoint(location.getLongitude(), location.getLatitude());
    }

    public void startNavi() {
        this.mOnCarNavi = true;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(LocationParams.PARA_COMMAND_NAVI, 1);
            this.locationManager.setParams(4, jSONObject);
        } catch (Exception unused) {
        }
        eoy.a().a(epl.c());
        eoy.a().a(this.mLocationStatusChangedListener.a, this.mLocationStatusChangedListener.b);
        if (this.mWorkHandler != null) {
            try {
                this.locationManager.addNmeaListener(this.mNmeaListener, this.mWorkHandler.getLooper());
            } catch (SecurityException unused2) {
            }
        } else {
            this.mHasRestTaskNoSend = true;
        }
    }

    public void stopNavi() {
        this.mOnCarNavi = false;
        try {
            this.locationManager.removeNmeaListener(this.mNmeaListener);
        } catch (SecurityException unused) {
        }
        this.mHasRestTaskNoSend = false;
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(LocationParams.PARA_COMMAND_NAVI, 0);
            this.locationManager.setParams(4, jSONObject);
        } catch (Exception unused2) {
        }
        eoy.a().b();
    }

    public void sendCloudCommond(int i, JSONObject jSONObject) {
        if (this.locationManager != null) {
            this.locationManager.setParams(i, jSONObject);
        }
    }

    public static String getLocationString(Location location) {
        int i;
        if (location == null) {
            return "null";
        }
        if ("network".equals(location.getProvider())) {
            try {
                i = location.getExtras().getInt("locType", 0);
            } catch (Throwable unused) {
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Location[");
            sb.append(location.getProvider());
            sb.append(String.format(Locale.US, " %.6f,%.6f", new Object[]{Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude())}));
            sb.append(String.format(Locale.US, " acc=%.0f", new Object[]{Float.valueOf(location.getAccuracy())}));
            sb.append(String.format(Locale.US, " bear=%.0f", new Object[]{Float.valueOf(location.getBearing())}));
            sb.append(String.format(Locale.US, " speed=%.0f", new Object[]{Float.valueOf(location.getSpeed())}));
            sb.append(String.format(Locale.US, " time=%s", new Object[]{epl.a(location.getTime())}));
            sb.append(String.format(Locale.US, " netloc=%d", new Object[]{Integer.valueOf(i)}));
            sb.append(']');
            return sb.toString();
        }
        i = 0;
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Location[");
        sb2.append(location.getProvider());
        sb2.append(String.format(Locale.US, " %.6f,%.6f", new Object[]{Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude())}));
        sb2.append(String.format(Locale.US, " acc=%.0f", new Object[]{Float.valueOf(location.getAccuracy())}));
        sb2.append(String.format(Locale.US, " bear=%.0f", new Object[]{Float.valueOf(location.getBearing())}));
        sb2.append(String.format(Locale.US, " speed=%.0f", new Object[]{Float.valueOf(location.getSpeed())}));
        sb2.append(String.format(Locale.US, " time=%s", new Object[]{epl.a(location.getTime())}));
        sb2.append(String.format(Locale.US, " netloc=%d", new Object[]{Integer.valueOf(i)}));
        sb2.append(']');
        return sb2.toString();
    }

    private void sendMessage(Message message) {
        this.mHandlerLock.readLock().lock();
        if (!(this.mWorkHandler == null || this.mWorkThread == null)) {
            try {
                this.mWorkHandler.sendMessage(message);
            } catch (Exception e) {
                epk.a(TAG, "线程发送消息异常", e);
            }
        }
        this.mHandlerLock.readLock().unlock();
    }

    private void sendMessage(int i, Object obj) {
        this.mHandlerLock.readLock().lock();
        if (!(this.mWorkHandler == null || this.mWorkThread == null)) {
            try {
                this.mWorkHandler.obtainMessage(i, obj).sendToTarget();
            } catch (Exception e) {
                epk.a(TAG, "线程发送消息异常", e);
            }
        }
        this.mHandlerLock.readLock().unlock();
    }
}
