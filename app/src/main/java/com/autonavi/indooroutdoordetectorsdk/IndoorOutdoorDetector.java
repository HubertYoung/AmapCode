package com.autonavi.indooroutdoordetectorsdk;

import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import com.alipay.mobile.beehive.util.MiscUtil;
import com.autonavi.indoor.constant.Configuration;
import com.autonavi.indoor.pdr.PedProvider;
import com.autonavi.indoor.util.L;
import com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper.TimeStatus;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Iterator;

public class IndoorOutdoorDetector {
    static final int MSG_GPS_DISABLED = 1005;
    static final int MSG_GPS_ENABLED = 1006;
    /* access modifiers changed from: private */
    public static volatile IndoorOutdoorDetector instance;
    final int MSG_DETECT_INDOORSTATUS;
    final int MSG_DETECT_INIT;
    final int MSG_DETECT_OUTDOORLOCATION;
    final int MSG_DETECT_START;
    final int MSG_DETECT_STOP;
    boolean isWorking;
    JniSwitchResult mAlgoResult;
    TimeStatus mAlgoResultTime;
    String mBuildingId;
    Configuration mConfiguration;
    HashSet<DetectListener> mDetectListeners;
    GpsDetector mGPSDetector;
    GeoFenceDetector mGeofenceDetector;
    IIndoorFeedback mIIndoorFeedback;
    TimeStatus mIODetectResult;
    BuildingLocator mIndoorlocationDetector;
    /* access modifiers changed from: private */
    public boolean mIsFirstIndoorLocation;
    boolean mIsInGeoFence;
    TimeStatus mIsIndoorlocateRunning;
    JniSwitchResult mLastAlgoResult;
    long mLastAlgoResultTime;
    Coord mLastOutdoorCoord;
    long mLastOutdoorCoordTime;
    int mLastStep;
    LightDetector mLightDetector;
    PdrDetector mMagnetDetector;
    Callback mWorkingCallback;
    Handler mWorkingHandler;
    HandlerThread mWorkingThread;
    String sensorStatus;

    public interface DetectListener {
        void detectorStatus(boolean z, String str, int i, BuildingLocationResult buildingLocationResult);
    }

    public static String getSubVersion() {
        return "20170413";
    }

    public static String getVersion() {
        return "6.9";
    }

    public static IndoorOutdoorDetector getInstance() {
        if (instance == null) {
            synchronized (IndoorOutdoorDetector.class) {
                try {
                    if (instance == null) {
                        instance = new IndoorOutdoorDetector();
                    }
                }
            }
        }
        return instance;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001c, code lost:
        return -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int initDetect(com.autonavi.indoor.constant.Configuration r4) {
        /*
            r3 = this;
            monitor-enter(r3)
            android.os.Handler r0 = r3.mWorkingHandler     // Catch:{ all -> 0x002b }
            r1 = -1
            if (r0 != 0) goto L_0x0008
            monitor-exit(r3)
            return r1
        L_0x0008:
            android.content.Context r0 = r4.context     // Catch:{ all -> 0x002b }
            java.lang.String[] r2 = com.autonavi.indoor.util.PermissionUtil.permissions     // Catch:{ all -> 0x002b }
            boolean r0 = com.autonavi.indoor.util.PermissionUtil.hasSelfPermission(r0, r2)     // Catch:{ all -> 0x002b }
            if (r0 != 0) goto L_0x001d
            boolean r4 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x002b }
            if (r4 == 0) goto L_0x001b
            java.lang.String r4 = "hasSelfPermission failed"
            com.autonavi.indoor.util.L.d(r4)     // Catch:{ all -> 0x002b }
        L_0x001b:
            monitor-exit(r3)
            return r1
        L_0x001d:
            android.os.Handler r0 = r3.mWorkingHandler     // Catch:{ all -> 0x002b }
            r1 = 1000(0x3e8, float:1.401E-42)
            android.os.Message r4 = r0.obtainMessage(r1, r4)     // Catch:{ all -> 0x002b }
            r4.sendToTarget()     // Catch:{ all -> 0x002b }
            r4 = 0
            monitor-exit(r3)
            return r4
        L_0x002b:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.initDetect(com.autonavi.indoor.constant.Configuration):int");
    }

    public int registerListener(DetectListener detectListener) {
        if (detectListener == null) {
            if (L.isLogging) {
                L.d((String) "registerListener , listener is null");
            }
            return -1;
        }
        synchronized (this) {
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("registerListener:");
                sb.append(this.mDetectListeners.size());
                L.d(sb.toString());
            }
            if (this.mDetectListeners.size() == 0 && this.mWorkingHandler != null) {
                this.mWorkingHandler.sendEmptyMessage(1001);
            }
            this.mDetectListeners.add(detectListener);
        }
        return 0;
    }

    public int unregisterListener(DetectListener detectListener) {
        if (detectListener == null) {
            if (L.isLogging) {
                L.d((String) "unregisterListener, listener is null");
            }
            return -1;
        }
        synchronized (this) {
            this.mDetectListeners.remove(detectListener);
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("unregisterListener:");
                sb.append(this.mDetectListeners.size());
                L.d(sb.toString());
            }
            if (this.mDetectListeners.size() == 0 && this.mWorkingHandler != null) {
                this.mWorkingHandler.sendEmptyMessage(1002);
            }
        }
        return 0;
    }

    public int setOutdoorLocation(boolean z, double d, double d2) {
        if (L.isLogging) {
            StringBuilder sb = new StringBuilder("setOutdoorLocation: wgs84=");
            sb.append(z);
            sb.append(", longitude=");
            sb.append(d);
            sb.append(", latitude=");
            sb.append(d2);
            L.d(sb.toString());
        }
        if (this.mWorkingHandler != null) {
            this.mWorkingHandler.obtainMessage(1003, new Object[]{Boolean.valueOf(z), Double.valueOf(d), Double.valueOf(d2)}).sendToTarget();
        } else if (L.isLogging) {
            L.d((String) "mWorkingHandler == null");
        }
        return 0;
    }

    public String toString() {
        String str = "JNI Error";
        try {
            str = JNIWrapper.jniGetIndoorSwitchDebugString();
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder("\nIODetectResult:   ");
        sb2.append(this.mIODetectResult.status ? "室内" : "室外");
        sb.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder("\nGeofence:   ");
        sb3.append(this.mGeofenceDetector);
        sb.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder("\nGPS:        ");
        sb4.append(this.mGPSDetector);
        sb.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder("\n地磁:     ");
        sb5.append(this.mMagnetDetector);
        sb5.append("  LastStep:");
        sb5.append(this.mLastStep);
        sb5.append("步");
        sb.append(sb5.toString());
        StringBuilder sb6 = new StringBuilder("\n");
        sb6.append(this.mIndoorlocationDetector);
        sb.append(sb6.toString());
        if (this.mIsIndoorlocateRunning.status) {
            sb.append(" Running......");
        } else {
            sb.append(" Stoped!!!!!!");
        }
        StringBuilder sb7 = new StringBuilder("\nAlgoResult:");
        sb7.append(GeoFenceHelper.timeFormat());
        sb7.append("\n");
        sb.append(sb7.toString());
        if (this.mAlgoResult == null) {
            sb.append(MiscUtil.NULL_STR);
        } else {
            sb.append(this.mAlgoResult);
        }
        sb.append("\nAlgoDetail:".concat(String.valueOf(str)));
        StringBuilder sb8 = new StringBuilder("\nLight:");
        sb8.append(this.mLightDetector);
        sb.append(sb8.toString());
        return sb.toString();
    }

    public int getN1() {
        if (this.mGeofenceDetector != null) {
            return this.mGeofenceDetector.N1;
        }
        return 10;
    }

    public int getN2() {
        if (this.mGeofenceDetector != null) {
            return this.mGeofenceDetector.N2;
        }
        return 30;
    }

    /* access modifiers changed from: 0000 */
    public void reportIndoor(String str, int i, BuildingLocationResult buildingLocationResult) {
        if (!this.mIODetectResult.status && this.mIODetectResult.isTimeout(5000)) {
            Building detect = this.mGeofenceDetector.detect((float) buildingLocationResult.x, (float) buildingLocationResult.y);
            if (L.isLogging) {
                L.d("dist=".concat(String.valueOf(detect)));
            }
            if (detect != null && detect.isContained) {
                setFlag("didIndoor");
                if (L.isLogging) {
                    StringBuilder sb = new StringBuilder("reportIndoor: ");
                    sb.append(this.mIODetectResult);
                    L.d(sb.toString());
                }
                this.mIODetectResult.changeStatus(true);
                writeWeight(true);
                synchronized (this) {
                    Iterator<DetectListener> it = this.mDetectListeners.iterator();
                    while (it.hasNext()) {
                        it.next().detectorStatus(true, str, i, buildingLocationResult);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void reportOutdoor() {
        if (this.mIODetectResult.status && this.mIODetectResult.isTimeout(5000)) {
            setFlag("didOutdoor");
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("reportOutdoor: ");
                sb.append(this.mIODetectResult);
                L.d(sb.toString());
            }
            this.mIODetectResult.changeStatus(false);
            writeWeight(false);
            synchronized (this) {
                Iterator<DetectListener> it = this.mDetectListeners.iterator();
                while (it.hasNext()) {
                    it.next().detectorStatus(false, "", 0, null);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int onSetOutdoorLocation(boolean z, double d, double d2) {
        if (!this.isWorking) {
            if (L.isLogging) {
                L.d((Object) Boolean.valueOf(this.isWorking));
            }
            return -1;
        } else if (this.mIODetectResult.status) {
            if (L.isLogging) {
                L.d((String) "\tindoors, ignore outdoor NLP result");
            }
            return 0;
        } else {
            if (z) {
                if (L.isLogging) {
                    StringBuilder sb = new StringBuilder("wgs84：longitude=");
                    sb.append(d);
                    sb.append(", latitude=");
                    sb.append(d2);
                    L.d(sb.toString());
                }
                Coord transform = GeoFenceHelper.transform((float) d2, (float) d);
                d = (double) transform.longitude;
                d2 = (double) transform.latitude;
            }
            if (L.isLogging) {
                StringBuilder sb2 = new StringBuilder("MarsCoord:longitude=");
                sb2.append(d);
                sb2.append(", latitude=");
                sb2.append(d2);
                L.d(sb2.toString());
            }
            boolean z2 = !this.mIODetectResult.status;
            boolean z3 = (this.mLastOutdoorCoord != null && ((double) this.mLastOutdoorCoord.longitude) == d && ((double) this.mLastOutdoorCoord.latitude) == d2) ? false : true;
            if (!z3 && System.currentTimeMillis() - this.mLastOutdoorCoordTime < 5000) {
                return 0;
            }
            float f = (float) d;
            float f2 = (float) d2;
            Building detect = this.mGeofenceDetector.detect(f, f2);
            if (detect == null) {
                if (L.isLogging) {
                    L.d((String) "Detect building failed.");
                }
                return 0;
            }
            if (L.isLogging) {
                L.d("dist=".concat(String.valueOf(detect)));
            }
            if (z3) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(detect.buildingId);
                sb3.append(":");
                sb3.append(this.mGeofenceDetector.mNearBuildings.size());
                sb3.append(",");
                sb3.append(detect.isNearby);
                sb3.append(",");
                sb3.append(detect.isContained);
                sb3.append(",");
                sb3.append(GeoFenceHelper.round(detect.distance, 2));
                sb3.append(",");
                sb3.append(GeoFenceHelper.round(d, 6));
                sb3.append(",");
                sb3.append(GeoFenceHelper.round(d2, 6));
                setFlag(sb3.toString());
            }
            if (detect.isContained) {
                if (L.isLogging) {
                    StringBuilder sb4 = new StringBuilder("Step Into building, Start Sensor Light & GPS. isOutdoor:");
                    sb4.append(z2);
                    sb4.append(", IsIndoorlocateRunning:");
                    sb4.append(this.mIsIndoorlocateRunning.status);
                    sb4.append(", mIsFirstIndoorLocation:");
                    sb4.append(this.mIsFirstIndoorLocation);
                    L.d(sb4.toString());
                }
                this.mLightDetector.startDetect();
                if (!this.mGPSDetector.startDetect()) {
                    if (L.isLogging) {
                        L.d((String) "Start GPS failed, going to start Magnetic");
                    }
                    this.mMagnetDetector.startDetect();
                }
                if (this.mIsFirstIndoorLocation && z2 && !this.mIsIndoorlocateRunning.status) {
                    this.mIsIndoorlocateRunning.changeStatus(this.mIndoorlocationDetector.startDetect());
                    if (L.isLogging) {
                        StringBuilder sb5 = new StringBuilder("Inside Building, Start IndoorLocation : ");
                        sb5.append(this.mIsIndoorlocateRunning);
                        L.d(sb5.toString());
                    }
                    this.mIsFirstIndoorLocation = false;
                }
                if (this.mIIndoorFeedback != null && !this.mIsInGeoFence) {
                    writeSensorStatus(true);
                    this.mIsInGeoFence = true;
                }
            } else if (detect.distance > 500.0d) {
                if (L.isLogging) {
                    StringBuilder sb6 = new StringBuilder("Current location is far enough from any building, stop sensor: Light, GPS, Magnetic, indoorlocation:");
                    sb6.append(this.mIsIndoorlocateRunning);
                    L.d(sb6.toString());
                }
                this.mLightDetector.stopDetect();
                this.mGPSDetector.stopDetect();
                this.mMagnetDetector.stopDetect();
                this.mIndoorlocationDetector.stopDetect();
                if (this.mIsIndoorlocateRunning.status) {
                    this.mIsIndoorlocateRunning.changeStatus(false);
                }
                this.mIsFirstIndoorLocation = true;
                if (this.mIIndoorFeedback != null && this.mIsInGeoFence) {
                    writeSensorStatus(false);
                    this.mIsInGeoFence = false;
                }
            }
            if (this.mGeofenceDetector.mBuildings.size() > 0) {
                this.mLastOutdoorCoord = new Coord(f, f2);
                this.mLastOutdoorCoordTime = System.currentTimeMillis();
            }
            return 0;
        }
    }

    public int setIndoorOutdoorState(int i) {
        if (this.mWorkingHandler != null) {
            this.mWorkingHandler.obtainMessage(1004, Integer.valueOf(i)).sendToTarget();
        }
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public int onSetIndoorOutdoorState(int i) {
        if (!this.isWorking) {
            if (L.isLogging) {
                L.d((Object) Boolean.valueOf(this.isWorking));
            }
            return -1;
        }
        setFlag("indoorError:".concat(String.valueOf(i)));
        if (this.mGPSDetector.startDetect()) {
            this.mMagnetDetector.stopDetect();
        }
        reportOutdoor();
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public void reportAlgoResult() {
        if (!this.isWorking) {
            if (L.isLogging) {
                L.d((String) "don't reportAlgoResult cause of IOD stoped");
            }
        } else if (System.currentTimeMillis() - this.mAlgoResultTime.time >= 100) {
            try {
                this.mAlgoResult = JNIWrapper.jniGetSwitchResult();
                if (L.isLogging) {
                    L.d(JNIWrapper.jniGetIndoorSwitchDebugString());
                }
                if (this.mLastAlgoResult == null || this.mLastAlgoResult.confidence != this.mAlgoResult.confidence || System.currentTimeMillis() - this.mLastAlgoResultTime >= 5000) {
                    if (L.isLogging) {
                        StringBuilder sb = new StringBuilder(";");
                        sb.append(this.mAlgoResult);
                        L.d(sb.toString());
                    }
                    this.mLastAlgoResultTime = System.currentTimeMillis();
                    if (this.mLastAlgoResult == null || this.mLastAlgoResult.confidence != this.mAlgoResult.confidence) {
                        StringBuilder sb2 = new StringBuilder("Confidence:");
                        sb2.append(GeoFenceHelper.round(this.mAlgoResult.confidence, 2));
                        setFlag(sb2.toString());
                    }
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append(this.mAlgoResult.confidence);
                    GeoFenceHelper.logFile("conf", sb3.toString());
                    this.mAlgoResultTime.time = System.currentTimeMillis();
                    if (this.mAlgoResult.confidence < 0.0d) {
                        if (L.isLogging) {
                            StringBuilder sb4 = new StringBuilder("Invalide state, try to start GPS/MAG/Locate. IODetectResult = ");
                            sb4.append(this.mIODetectResult);
                            sb4.append(", IndoorlocateStatus=");
                            sb4.append(this.mIsIndoorlocateRunning);
                            L.d(sb4.toString());
                        }
                        if (!this.mGPSDetector.startDetect() && !this.mMagnetDetector.startDetect() && !this.mIODetectResult.status && !this.mIsIndoorlocateRunning.status && this.mIsIndoorlocateRunning.isTimeout(10000)) {
                            this.mIsIndoorlocateRunning.changeStatus(this.mIndoorlocationDetector.startDetect());
                        }
                    } else if (this.mAlgoResult.confidence <= 0.3d) {
                        if (L.isLogging) {
                            StringBuilder sb5 = new StringBuilder("i'm sure you are at indoor. IODetectResult = ");
                            sb5.append(this.mIODetectResult);
                            sb5.append(", IndoorlocateStatus=");
                            sb5.append(this.mIsIndoorlocateRunning);
                            L.d(sb5.toString());
                        }
                        if (this.mGPSDetector.startDetect()) {
                            this.mMagnetDetector.stopDetect();
                        }
                        if (!this.mIODetectResult.status && !this.mIsIndoorlocateRunning.status && this.mIsIndoorlocateRunning.isTimeout(10000)) {
                            if (L.isLogging) {
                                L.d((String) "Steped into indoor, start GPS & STOP Magnetic & start indoorlocation");
                            }
                            this.mIsIndoorlocateRunning.changeStatus(this.mIndoorlocationDetector.startDetect());
                        }
                    } else if (this.mAlgoResult.confidence < 0.5d) {
                        if (L.isLogging) {
                            StringBuilder sb6 = new StringBuilder("not sure where you're, Start GPS & Magnetic, maybe start indoorlocation. IODetectResult = ");
                            sb6.append(this.mIODetectResult);
                            sb6.append(", IndoorlocateStatus=");
                            sb6.append(this.mIsIndoorlocateRunning);
                            L.d(sb6.toString());
                        }
                        this.mGPSDetector.startDetect();
                        if (!this.mMagnetDetector.startDetect() && !this.mIODetectResult.status && !this.mIsIndoorlocateRunning.status && this.mIsIndoorlocateRunning.isTimeout(10000)) {
                            if (L.isLogging) {
                                L.d((String) "start indoorlocation");
                            }
                            this.mIsIndoorlocateRunning.changeStatus(this.mIndoorlocationDetector.startDetect());
                        }
                    } else if (this.mAlgoResult.confidence < 0.7d) {
                        if (L.isLogging) {
                            L.d((String) "pretty not sure where you're, Start GPS & Magnetic, but don't start indoorlocaion");
                        }
                        this.mGPSDetector.startDetect();
                        this.mMagnetDetector.startDetect();
                    } else {
                        if (L.isLogging) {
                            StringBuilder sb7 = new StringBuilder("i am sure you're at Outdoor, start GPS, STOP magnetic. IODetectResult = ");
                            sb7.append(this.mIODetectResult);
                            sb7.append(", IndoorlocateStatus=");
                            sb7.append(this.mIsIndoorlocateRunning);
                            L.d(sb7.toString());
                        }
                        if (this.mGPSDetector.startDetect()) {
                            this.mMagnetDetector.stopDetect();
                        }
                        this.mIndoorlocationDetector.stopDetect();
                        if (this.mIsIndoorlocateRunning.status) {
                            this.mIsIndoorlocateRunning.changeStatus(false);
                        }
                        reportOutdoor();
                    }
                    this.mLastAlgoResult = this.mAlgoResult;
                }
            } catch (Throwable th) {
                if (L.isLogging) {
                    L.d(th);
                }
            }
        }
    }

    IndoorOutdoorDetector() {
        this.mGeofenceDetector = new GeoFenceDetector();
        this.mGPSDetector = new GpsDetector();
        this.mIndoorlocationDetector = new BuildingLocator();
        this.mLightDetector = new LightDetector();
        this.mMagnetDetector = new PdrDetector();
        this.MSG_DETECT_INIT = 1000;
        this.MSG_DETECT_START = 1001;
        this.MSG_DETECT_STOP = 1002;
        this.MSG_DETECT_OUTDOORLOCATION = 1003;
        this.MSG_DETECT_INDOORSTATUS = 1004;
        this.mIODetectResult = new TimeStatus();
        this.mIsIndoorlocateRunning = new TimeStatus();
        this.mBuildingId = "";
        this.mConfiguration = null;
        this.mLastOutdoorCoord = null;
        this.mLastOutdoorCoordTime = 0;
        this.mAlgoResult = null;
        this.mAlgoResultTime = new TimeStatus();
        this.mLastAlgoResult = null;
        this.mLastAlgoResultTime = 0;
        this.isWorking = false;
        this.mWorkingThread = null;
        this.mWorkingHandler = null;
        this.mLastStep = 0;
        this.mDetectListeners = new HashSet<>();
        this.mIsFirstIndoorLocation = true;
        this.mIsInGeoFence = false;
        this.mIIndoorFeedback = null;
        this.sensorStatus = null;
        this.mWorkingCallback = new Callback() {
            /* JADX WARNING: Code restructure failed: missing block: B:62:0x01b8, code lost:
                r11.this$0.mIndoorlocationDetector.detect((com.autonavi.indoor.entity.ScanData) r12.obj);
             */
            /* JADX WARNING: Removed duplicated region for block: B:81:0x023e A[Catch:{ Throwable -> 0x0280 }] */
            /* JADX WARNING: Removed duplicated region for block: B:84:0x0245 A[Catch:{ Throwable -> 0x0280 }] */
            /* JADX WARNING: Removed duplicated region for block: B:87:0x0258 A[Catch:{ Throwable -> 0x0280 }] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public boolean handleMessage(android.os.Message r12) {
                /*
                    r11 = this;
                    r0 = 0
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.instance     // Catch:{ Throwable -> 0x0280 }
                    if (r1 != 0) goto L_0x0011
                    boolean r12 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0280 }
                    if (r12 == 0) goto L_0x0010
                    java.lang.String r12 = "instance==null"
                    com.autonavi.indoor.util.L.d(r12)     // Catch:{ Throwable -> 0x0280 }
                L_0x0010:
                    return r0
                L_0x0011:
                    int r1 = r12.what     // Catch:{ Throwable -> 0x0280 }
                    switch(r1) {
                        case 212: goto L_0x0278;
                        case 213: goto L_0x0278;
                        default: goto L_0x0016;
                    }     // Catch:{ Throwable -> 0x0280 }
                L_0x0016:
                    switch(r1) {
                        case 215: goto L_0x0268;
                        case 216: goto L_0x0268;
                        default: goto L_0x0019;
                    }     // Catch:{ Throwable -> 0x0280 }
                L_0x0019:
                    r2 = 1
                    switch(r1) {
                        case 501: goto L_0x023a;
                        case 502: goto L_0x023a;
                        case 503: goto L_0x023a;
                        case 504: goto L_0x0232;
                        case 505: goto L_0x0232;
                        case 506: goto L_0x0232;
                        default: goto L_0x001d;
                    }     // Catch:{ Throwable -> 0x0280 }
                L_0x001d:
                    switch(r1) {
                        case 1000: goto L_0x0228;
                        case 1001: goto L_0x0222;
                        case 1002: goto L_0x021c;
                        case 1003: goto L_0x01f8;
                        case 1004: goto L_0x01e9;
                        case 1005: goto L_0x01d7;
                        case 1006: goto L_0x01c5;
                        default: goto L_0x0020;
                    }     // Catch:{ Throwable -> 0x0280 }
                L_0x0020:
                    switch(r1) {
                        case 1201: goto L_0x01b2;
                        case 1202: goto L_0x01b8;
                        default: goto L_0x0023;
                    }     // Catch:{ Throwable -> 0x0280 }
                L_0x0023:
                    switch(r1) {
                        case 105: goto L_0x0171;
                        case 111: goto L_0x011e;
                        case 208: goto L_0x002f;
                        case 210: goto L_0x002f;
                        case 508: goto L_0x023a;
                        case 802: goto L_0x0028;
                        default: goto L_0x0026;
                    }     // Catch:{ Throwable -> 0x0280 }
                L_0x0026:
                    goto L_0x0288
                L_0x0028:
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r12 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    r12.reportAlgoResult()     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x002f:
                    java.lang.Object r12 = r12.obj     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.BuildingLocationResult r12 = (com.autonavi.indooroutdoordetectorsdk.BuildingLocationResult) r12     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r3 = r12.toString()     // Catch:{ Throwable -> 0x0280 }
                    r4 = 20
                    java.lang.String r3 = r3.substring(r4)     // Catch:{ Throwable -> 0x0280 }
                    r1.setFlag(r3)     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r1 = r12.bid     // Catch:{ Throwable -> 0x0280 }
                    boolean r1 = com.autonavi.indoor.util.MapUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x0280 }
                    if (r1 != 0) goto L_0x0051
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r3 = r12.bid     // Catch:{ Throwable -> 0x0280 }
                    r1.mBuildingId = r3     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0057
                L_0x0051:
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r1 = r1.mBuildingId     // Catch:{ Throwable -> 0x0280 }
                    r12.bid = r1     // Catch:{ Throwable -> 0x0280 }
                L_0x0057:
                    int r1 = r12.z     // Catch:{ Throwable -> 0x0280 }
                    r3 = -100
                    if (r1 <= r3) goto L_0x0288
                    int r1 = r12.z     // Catch:{ Throwable -> 0x0280 }
                    if (r1 == 0) goto L_0x0288
                    int r1 = r12.d     // Catch:{ Throwable -> 0x0280 }
                    if (r1 != r2) goto L_0x006b
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.BuildingLocator r1 = r1.mIndoorlocationDetector     // Catch:{ Throwable -> 0x0280 }
                    r1.mWifiFailed = r0     // Catch:{ Throwable -> 0x0280 }
                L_0x006b:
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector r1 = r1.mGeofenceDetector     // Catch:{ Throwable -> 0x0280 }
                    java.util.ArrayList<com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building> r1 = r1.mNearBuildings     // Catch:{ Throwable -> 0x0280 }
                    java.util.Iterator r1 = r1.iterator()     // Catch:{ Throwable -> 0x0280 }
                L_0x0075:
                    boolean r3 = r1.hasNext()     // Catch:{ Throwable -> 0x0280 }
                    if (r3 == 0) goto L_0x0090
                    java.lang.Object r3 = r1.next()     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector$Building r3 = (com.autonavi.indooroutdoordetectorsdk.GeoFenceDetector.Building) r3     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r4 = r3.buildingId     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r5 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r5 = r5.mBuildingId     // Catch:{ Throwable -> 0x0280 }
                    boolean r4 = r4.equals(r5)     // Catch:{ Throwable -> 0x0280 }
                    if (r4 == 0) goto L_0x0075
                    int r1 = r3.support     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0091
                L_0x0090:
                    r1 = 0
                L_0x0091:
                    int r3 = r12.d     // Catch:{ Throwable -> 0x0280 }
                    r1 = r1 & r3
                    if (r1 == 0) goto L_0x010a
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$TimeStatus r1 = r1.mIODetectResult     // Catch:{ Throwable -> 0x0280 }
                    boolean r1 = r1.status     // Catch:{ Throwable -> 0x0280 }
                    r1 = r1 ^ r2
                    if (r1 == 0) goto L_0x00b9
                    boolean r1 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0280 }
                    if (r1 == 0) goto L_0x00a8
                    java.lang.String r1 = "Got reportIndoor location, sent to client. and stop indoorlocation"
                    com.autonavi.indoor.util.L.d(r1)     // Catch:{ Throwable -> 0x0280 }
                L_0x00a8:
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r2 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r2 = r2.mBuildingId     // Catch:{ Throwable -> 0x0280 }
                    int r3 = r12.d     // Catch:{ Throwable -> 0x0280 }
                    r1.reportIndoor(r2, r3, r12)     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r12 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    r12.mIsFirstIndoorLocation = r0     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x00fa
                L_0x00b9:
                    boolean r1 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0280 }
                    if (r1 == 0) goto L_0x00fa
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r2 = "mIODetectResult.status="
                    r1.<init>(r2)     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r2 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$TimeStatus r2 = r2.mIODetectResult     // Catch:{ Throwable -> 0x0280 }
                    boolean r2 = r2.status     // Catch:{ Throwable -> 0x0280 }
                    r1.append(r2)     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r2 = ", currentTimeMillis="
                    r1.append(r2)     // Catch:{ Throwable -> 0x0280 }
                    long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0280 }
                    r1.append(r2)     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r2 = ", IndoorLocatingResult.t="
                    r1.append(r2)     // Catch:{ Throwable -> 0x0280 }
                    long r2 = r12.t     // Catch:{ Throwable -> 0x0280 }
                    r1.append(r2)     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r2 = ", diff="
                    r1.append(r2)     // Catch:{ Throwable -> 0x0280 }
                    long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0280 }
                    long r4 = r12.t     // Catch:{ Throwable -> 0x0280 }
                    r12 = 0
                    long r2 = r2 - r4
                    r1.append(r2)     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r12 = r1.toString()     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indoor.util.L.d(r12)     // Catch:{ Throwable -> 0x0280 }
                L_0x00fa:
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r12 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.BuildingLocator r12 = r12.mIndoorlocationDetector     // Catch:{ Throwable -> 0x0280 }
                    r12.stopDetect()     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r12 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$TimeStatus r12 = r12.mIsIndoorlocateRunning     // Catch:{ Throwable -> 0x0280 }
                    r12.changeStatus(r0)     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x010a:
                    boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0280 }
                    if (r2 == 0) goto L_0x0115
                    java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indoor.util.L.d(r1)     // Catch:{ Throwable -> 0x0280 }
                L_0x0115:
                    boolean r1 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0280 }
                    if (r1 == 0) goto L_0x0288
                    com.autonavi.indoor.util.L.d(r12)     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x011e:
                    java.lang.Object r12 = r12.obj     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indoor.pdr.MatStepData r12 = (com.autonavi.indoor.pdr.MatStepData) r12     // Catch:{ Throwable -> 0x0280 }
                    int r1 = r12.step_     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r2 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    int r2 = r2.mLastStep     // Catch:{ Throwable -> 0x0280 }
                    if (r1 == r2) goto L_0x0149
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    int r2 = r12.step_     // Catch:{ Throwable -> 0x0280 }
                    r1.mLastStep = r2     // Catch:{ Throwable -> 0x0280 }
                    boolean r1 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0280 }
                    if (r1 == 0) goto L_0x0149
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r2 = "Step="
                    r1.<init>(r2)     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r2 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    int r2 = r2.mLastStep     // Catch:{ Throwable -> 0x0280 }
                    r1.append(r2)     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r1 = r1.toString()     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indoor.util.L.d(r1)     // Catch:{ Throwable -> 0x0280 }
                L_0x0149:
                    java.lang.String r1 = "pedd"
                    java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0280 }
                    r2.<init>()     // Catch:{ Throwable -> 0x0280 }
                    int r3 = r12.step_     // Catch:{ Throwable -> 0x0280 }
                    r2.append(r3)     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r3 = " "
                    r2.append(r3)     // Catch:{ Throwable -> 0x0280 }
                    double r3 = r12.angle_     // Catch:{ Throwable -> 0x0280 }
                    r2.append(r3)     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper.logFile(r1, r2)     // Catch:{ Throwable -> 0x0280 }
                    long r1 = r12.timestamp_     // Catch:{ Throwable -> 0x0280 }
                    int r3 = r12.step_     // Catch:{ Throwable -> 0x0280 }
                    double r4 = r12.angle_     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.JNIWrapper.jniSetPedData(r1, r3, r4)     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x0171:
                    java.lang.Object r12 = r12.obj     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indoor.pdr.JniMagCaliResult r12 = (com.autonavi.indoor.pdr.JniMagCaliResult) r12     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r1 = "magn"
                    java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0280 }
                    r2.<init>()     // Catch:{ Throwable -> 0x0280 }
                    double r3 = r12.mX     // Catch:{ Throwable -> 0x0280 }
                    r2.append(r3)     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r3 = " "
                    r2.append(r3)     // Catch:{ Throwable -> 0x0280 }
                    double r3 = r12.mY     // Catch:{ Throwable -> 0x0280 }
                    r2.append(r3)     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r3 = " "
                    r2.append(r3)     // Catch:{ Throwable -> 0x0280 }
                    double r3 = r12.mZ     // Catch:{ Throwable -> 0x0280 }
                    r2.append(r3)     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper.logFile(r1, r2)     // Catch:{ Throwable -> 0x0280 }
                    long r3 = r12.mTime     // Catch:{ Throwable -> 0x0280 }
                    double r5 = r12.mX     // Catch:{ Throwable -> 0x0280 }
                    double r7 = r12.mY     // Catch:{ Throwable -> 0x0280 }
                    double r9 = r12.mZ     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.JNIWrapper.jniSetMagData(r3, r5, r7, r9)     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r12 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    android.os.Handler r12 = r12.mWorkingHandler     // Catch:{ Throwable -> 0x0280 }
                    r1 = 802(0x322, float:1.124E-42)
                    r12.sendEmptyMessage(r1)     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x01b2:
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.BuildingLocator r1 = r1.mIndoorlocationDetector     // Catch:{ Throwable -> 0x0280 }
                    r1.mWifiFailed = r0     // Catch:{ Throwable -> 0x0280 }
                L_0x01b8:
                    java.lang.Object r12 = r12.obj     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indoor.entity.ScanData r12 = (com.autonavi.indoor.entity.ScanData) r12     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.BuildingLocator r1 = r1.mIndoorlocationDetector     // Catch:{ Throwable -> 0x0280 }
                    r1.detect(r12)     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x01c5:
                    boolean r12 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0280 }
                    if (r12 == 0) goto L_0x01ce
                    java.lang.String r12 = "mMagnetDetector.stopDetect for MSG_GPS_ENABLED"
                    com.autonavi.indoor.util.L.d(r12)     // Catch:{ Throwable -> 0x0280 }
                L_0x01ce:
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r12 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.PdrDetector r12 = r12.mMagnetDetector     // Catch:{ Throwable -> 0x0280 }
                    r12.stopDetect()     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x01d7:
                    boolean r12 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0280 }
                    if (r12 == 0) goto L_0x01e0
                    java.lang.String r12 = "mMagnetDetector.startDetect for MSG_GPS_DISABLED"
                    com.autonavi.indoor.util.L.d(r12)     // Catch:{ Throwable -> 0x0280 }
                L_0x01e0:
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r12 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.PdrDetector r12 = r12.mMagnetDetector     // Catch:{ Throwable -> 0x0280 }
                    r12.startDetect()     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x01e9:
                    java.lang.Object r12 = r12.obj     // Catch:{ Throwable -> 0x0280 }
                    java.lang.Integer r12 = (java.lang.Integer) r12     // Catch:{ Throwable -> 0x0280 }
                    int r12 = r12.intValue()     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    r1.onSetIndoorOutdoorState(r12)     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x01f8:
                    java.lang.Object r12 = r12.obj     // Catch:{ Throwable -> 0x0280 }
                    java.lang.Object[] r12 = (java.lang.Object[]) r12     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r3 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    r1 = r12[r0]     // Catch:{ Throwable -> 0x0280 }
                    java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ Throwable -> 0x0280 }
                    boolean r4 = r1.booleanValue()     // Catch:{ Throwable -> 0x0280 }
                    r1 = r12[r2]     // Catch:{ Throwable -> 0x0280 }
                    java.lang.Double r1 = (java.lang.Double) r1     // Catch:{ Throwable -> 0x0280 }
                    double r5 = r1.doubleValue()     // Catch:{ Throwable -> 0x0280 }
                    r1 = 2
                    r12 = r12[r1]     // Catch:{ Throwable -> 0x0280 }
                    java.lang.Double r12 = (java.lang.Double) r12     // Catch:{ Throwable -> 0x0280 }
                    double r7 = r12.doubleValue()     // Catch:{ Throwable -> 0x0280 }
                    r3.onSetOutdoorLocation(r4, r5, r7)     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x021c:
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r12 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    r12.stopDetectWorking()     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x0222:
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r12 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    r12.startDetectWorking()     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x0228:
                    java.lang.Object r12 = r12.obj     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indoor.constant.Configuration r12 = (com.autonavi.indoor.constant.Configuration) r12     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    r1.onInitDetectWorking(r12)     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x0232:
                    boolean r1 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0280 }
                    if (r1 == 0) goto L_0x0288
                    com.autonavi.indoor.util.L.d(r12)     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x023a:
                    boolean r1 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0280 }
                    if (r1 == 0) goto L_0x0241
                    com.autonavi.indoor.util.L.d(r12)     // Catch:{ Throwable -> 0x0280 }
                L_0x0241:
                    boolean r12 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0280 }
                    if (r12 == 0) goto L_0x024a
                    java.lang.String r12 = "WIFI_NO_SCAN, we are goning to start ble"
                    com.autonavi.indoor.util.L.d(r12)     // Catch:{ Throwable -> 0x0280 }
                L_0x024a:
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r12 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.BuildingLocator r12 = r12.mIndoorlocationDetector     // Catch:{ Throwable -> 0x0280 }
                    r12.mWifiFailed = r2     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r12 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$TimeStatus r12 = r12.mIsIndoorlocateRunning     // Catch:{ Throwable -> 0x0280 }
                    boolean r12 = r12.status     // Catch:{ Throwable -> 0x0280 }
                    if (r12 == 0) goto L_0x0288
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r12 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper$TimeStatus r12 = r12.mIsIndoorlocateRunning     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    com.autonavi.indooroutdoordetectorsdk.BuildingLocator r1 = r1.mIndoorlocationDetector     // Catch:{ Throwable -> 0x0280 }
                    boolean r1 = r1.startDetect()     // Catch:{ Throwable -> 0x0280 }
                    r12.changeStatus(r1)     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x0268:
                    boolean r1 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0280 }
                    if (r1 == 0) goto L_0x026f
                    com.autonavi.indoor.util.L.d(r12)     // Catch:{ Throwable -> 0x0280 }
                L_0x026f:
                    com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector r1 = com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.this     // Catch:{ Throwable -> 0x0280 }
                    java.lang.Object r12 = r12.obj     // Catch:{ Throwable -> 0x0280 }
                    java.lang.String r12 = (java.lang.String) r12     // Catch:{ Throwable -> 0x0280 }
                    r1.mBuildingId = r12     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x0278:
                    boolean r1 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0280 }
                    if (r1 == 0) goto L_0x0288
                    com.autonavi.indoor.util.L.d(r12)     // Catch:{ Throwable -> 0x0280 }
                    goto L_0x0288
                L_0x0280:
                    r12 = move-exception
                    boolean r1 = com.autonavi.indoor.util.L.isLogging
                    if (r1 == 0) goto L_0x0288
                    com.autonavi.indoor.util.L.d(r12)
                L_0x0288:
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indooroutdoordetectorsdk.IndoorOutdoorDetector.AnonymousClass1.handleMessage(android.os.Message):boolean");
            }
        };
        this.mWorkingThread = new HandlerThread("IndoorOutdoorDetector");
        this.mWorkingThread.start();
        this.mWorkingHandler = new Handler(this.mWorkingThread.getLooper(), this.mWorkingCallback);
        PedProvider.getInstance().setSensorHandler(this.mWorkingHandler);
    }

    /* access modifiers changed from: 0000 */
    public int onInitDetectWorking(Configuration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException("ioProvider mConfiguration can not be initialized with null");
        }
        try {
            if (this.mConfiguration == null) {
                if (L.isLogging) {
                    L.d((String) "Initialize ioProvider with mConfiguration");
                }
                this.mConfiguration = configuration;
            } else if (L.isLogging) {
                L.d((String) "Try to initialize ioProvider which had already been initialized before. To re-init ioProvider with new mConfiguration call ioProvider.destroy() at first.");
            }
            this.mGeofenceDetector.initDetect(this.mConfiguration);
            this.mGPSDetector.initDetect(this.mConfiguration);
            this.mMagnetDetector.initDetect(this.mConfiguration);
            this.mIndoorlocationDetector.initDetect(this.mConfiguration);
            this.mLightDetector.mHandler = this.mWorkingHandler;
            this.mLightDetector.initDetect(this.mConfiguration);
            this.mIndoorlocationDetector.mHandler = this.mWorkingHandler;
            this.mGPSDetector.mHandler = this.mWorkingHandler;
            this.mMagnetDetector.mHandler = this.mWorkingHandler;
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public int startDetectWorking() {
        if (L.isLogging) {
            L.d((String) "startDetectWorking");
        }
        try {
            JNIWrapper.jniStartIndoorSwitch();
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        setFlag("startDetect");
        this.mIODetectResult.reset();
        this.mLastOutdoorCoord = null;
        this.mLastStep = 0;
        this.mLastOutdoorCoordTime = 0;
        this.mLastAlgoResult = null;
        this.mLastAlgoResultTime = 0;
        this.mAlgoResult = null;
        this.mAlgoResultTime.reset();
        this.mIsIndoorlocateRunning.reset();
        this.mBuildingId = "";
        this.mGeofenceDetector.startDetect();
        this.isWorking = true;
        this.mIsFirstIndoorLocation = true;
        this.mIsInGeoFence = false;
        return 0;
    }

    /* access modifiers changed from: 0000 */
    public int stopDetectWorking() {
        if (L.isLogging) {
            L.d((String) "stopDetectWorking");
        }
        this.isWorking = false;
        setFlag("stopDetect");
        this.mMagnetDetector.stopDetect();
        this.mGPSDetector.stopDetect();
        this.mLightDetector.stopDetect();
        this.mIndoorlocationDetector.stopDetect();
        this.mIsIndoorlocateRunning.changeStatus(false);
        try {
            JNIWrapper.jniStopIndoorSwitch();
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        this.mGeofenceDetector.stopDetect();
        this.mIODetectResult.reset();
        this.mLastOutdoorCoord = null;
        this.mLastStep = 0;
        this.mLastOutdoorCoordTime = 0;
        this.mLastAlgoResult = null;
        this.mLastAlgoResultTime = 0;
        this.mAlgoResult = null;
        this.mAlgoResultTime.reset();
        this.mIsIndoorlocateRunning.reset();
        this.mBuildingId = "";
        this.mIsInGeoFence = false;
        return 0;
    }

    public void setFlag(String str) {
        try {
            GeoFenceHelper.logFile("scen", str);
            JNIWrapper.jniSetFlag(System.currentTimeMillis(), str);
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
    }

    public void setIndoorFeedback(IIndoorFeedback iIndoorFeedback) {
        this.mIIndoorFeedback = iIndoorFeedback;
    }

    /* access modifiers changed from: 0000 */
    public String getSensorStatus() {
        StringBuilder sb = new StringBuilder();
        try {
            SensorManager sensorManager = (SensorManager) this.mConfiguration.context.getSystemService("sensor");
            sb.append(sensorManager.getDefaultSensor(1) != null ? "1" : "0");
            sb.append(",");
            sb.append(sensorManager.getDefaultSensor(9) != null ? "1" : "0");
            sb.append(",");
            sb.append(sensorManager.getDefaultSensor(4) != null ? "1" : "0");
            sb.append(",");
            sb.append(sensorManager.getDefaultSensor(2) != null ? "1" : "0");
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d((String) "Can't getSystemService of SENSOR_SERVICE, PED not work!");
            }
            if (L.isLogging) {
                L.d(th);
            }
        }
        return sb.toString();
    }

    /* access modifiers changed from: 0000 */
    public void writeSensorStatus(boolean z) {
        if (this.mIIndoorFeedback != null) {
            StringBuilder sb = new StringBuilder();
            if (this.sensorStatus == null) {
                this.sensorStatus = getSensorStatus();
            }
            sb.append(System.currentTimeMillis());
            sb.append(",");
            sb.append(this.mGPSDetector.isAvailable ? "1" : "0");
            sb.append(",");
            sb.append(this.sensorStatus);
            int i = 700002;
            try {
                this.mIIndoorFeedback.reportData(z ? 700001 : 700002, sb.toString().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException unused) {
                IIndoorFeedback iIndoorFeedback = this.mIIndoorFeedback;
                if (z) {
                    i = 700001;
                }
                iIndoorFeedback.reportData(i, sb.toString().getBytes());
            } catch (Exception e) {
                if (L.isLogging) {
                    L.d((Throwable) e);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void writeWeight(boolean z) {
        if (this.mIIndoorFeedback != null && JNIWrapper.jniGetSwitchInfo()) {
            StringBuilder sb = new StringBuilder();
            sb.append(System.currentTimeMillis());
            sb.append(",");
            sb.append(JNIWrapper.mMagMsg);
            sb.append(",");
            sb.append(JNIWrapper.mLightMsg);
            sb.append(",");
            sb.append(JNIWrapper.mGpsMsg);
            int i = 700004;
            try {
                this.mIIndoorFeedback.reportData(z ? 700003 : 700004, sb.toString().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException unused) {
                IIndoorFeedback iIndoorFeedback = this.mIIndoorFeedback;
                if (z) {
                    i = 700003;
                }
                iIndoorFeedback.reportData(i, sb.toString().getBytes());
            } catch (Exception e) {
                if (L.isLogging) {
                    L.d((Throwable) e);
                }
            }
        }
    }
}
