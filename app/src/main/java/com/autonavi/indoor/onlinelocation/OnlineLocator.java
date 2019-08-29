package com.autonavi.indoor.onlinelocation;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5PageData;
import com.autonavi.indoor.constant.Configuration.PDRProvider;
import com.autonavi.indoor.constant.MessageCode;
import com.autonavi.indoor.entity.LocationResult;
import com.autonavi.indoor.entity.PressData;
import com.autonavi.indoor.entity.ScanData;
import com.autonavi.indoor.location.ILocationManager;
import com.autonavi.indoor.pdr.MatStepData;
import com.autonavi.indoor.pdr.PedProvider;
import com.autonavi.indoor.provider.BLEProvider;
import com.autonavi.indoor.provider.WifiProvider;
import com.autonavi.indoor.simulator.FileDataProvider;
import com.autonavi.indoor.util.FileLogger;
import com.autonavi.indoor.util.L;
import com.autonavi.indoor.util.MapUtils;
import com.autonavi.indoor.util.MessageHelper;
import com.autonavi.indoor.util.PackageHelper;
import java.lang.ref.WeakReference;

public class OnlineLocator extends ILocationManager {
    private static volatile OnlineLocator h;
    int a = 0;
    long b = 0;
    boolean c = false;
    LocationResult d = null;
    b e = null;
    MatStepData f = new MatStepData();
    long g = 0;
    /* access modifiers changed from: private */
    public a i = null;
    /* access modifiers changed from: private */
    public volatile boolean j = false;
    private int k = 0;
    /* access modifiers changed from: private */
    public final FileLogger l = new FileLogger();
    /* access modifiers changed from: private */
    public long m = 0;
    public byte[] mLastRequestBuffer = null;
    private int n = -1;
    private String o = "";
    /* access modifiers changed from: private */
    public int p = -1;
    /* access modifiers changed from: private */
    public int q = -1;
    /* access modifiers changed from: private */
    public double r = -1.0d;
    /* access modifiers changed from: private */
    public double s = -1.0d;
    private double t = 0.0d;

    static class a extends Handler {
        private final WeakReference<OnlineLocator> a;

        public a(Looper looper, OnlineLocator onlineLocator) {
            super(looper);
            this.a = new WeakReference<>(onlineLocator);
        }

        public void handleMessage(Message message) {
            Message message2 = message;
            OnlineLocator onlineLocator = (OnlineLocator) this.a.get();
            if (onlineLocator == null || onlineLocator.j) {
                return;
            }
            if (!onlineLocator.mIsLocating) {
                if (L.isLogging) {
                    L.d((String) "OnlineLocation is not running, can't process any message!please start locating");
                }
                return;
            }
            try {
                if (message2.what >= 714 && message2.what <= 721) {
                    MessageHelper.publishMessage(onlineLocator.mOutterHandlers, message2.what);
                }
                int i = message2.what;
                if (i == 100) {
                    PressData pressData = (PressData) message2.obj;
                    JNIWrapper.jniAddPress(pressData.mTime, pressData.mPress);
                    if (onlineLocator.l.isLogging) {
                        FileLogger h = onlineLocator.l;
                        StringBuilder sb = new StringBuilder("pre:");
                        sb.append(pressData.mTime);
                        sb.append(",");
                        sb.append(pressData.mPress);
                        h.d(sb.toString());
                    }
                } else if (i == 111) {
                    ((MatStepData) message2.obj).copy(onlineLocator.f);
                    MatStepData matStepData = onlineLocator.f;
                    if (L.isLogging) {
                        StringBuilder sb2 = new StringBuilder("mat:");
                        sb2.append(matStepData.timestamp_ / 1000);
                        sb2.append(", mStep=");
                        sb2.append(matStepData.step_);
                        sb2.append(",angle=");
                        sb2.append(matStepData.angle_);
                        sb2.append(",angle_no_mag=");
                        sb2.append(matStepData.angle_no_mag_);
                        sb2.append(", ");
                        sb2.append(matStepData.move_direction_);
                        sb2.append(", move_state_score_=");
                        sb2.append(matStepData.move_state_score_);
                        sb2.append(", m1=");
                        sb2.append(matStepData.mat_data_[0]);
                        sb2.append(", q1=");
                        sb2.append(matStepData.q1_);
                        L.d(sb2.toString());
                    }
                    JNIWrapper.jniAddPress(matStepData.timestamp_, matStepData.pressure);
                    if (onlineLocator.l.isLogging) {
                        FileLogger h2 = onlineLocator.l;
                        StringBuilder sb3 = new StringBuilder("pre:");
                        sb3.append(matStepData.timestamp_);
                        sb3.append(",");
                        sb3.append(matStepData.pressure);
                        h2.d(sb3.toString());
                    }
                    if (onlineLocator.l.isLogging) {
                        FileLogger h3 = onlineLocator.l;
                        StringBuilder sb4 = new StringBuilder("mat:");
                        sb4.append(matStepData.timestamp_);
                        sb4.append(",");
                        sb4.append(matStepData.step_);
                        sb4.append(",");
                        sb4.append(matStepData.angle_);
                        sb4.append(",");
                        sb4.append(matStepData.mat_data_[0]);
                        sb4.append(",");
                        sb4.append(matStepData.mat_data_[1]);
                        sb4.append(",");
                        sb4.append(matStepData.mat_data_[2]);
                        sb4.append(",");
                        sb4.append(matStepData.q1_);
                        sb4.append(",");
                        sb4.append(matStepData.q2_);
                        sb4.append(",");
                        sb4.append(matStepData.q3_);
                        sb4.append(",");
                        sb4.append(matStepData.q4_);
                        sb4.append(",");
                        sb4.append(matStepData.rotate_rate_x_);
                        sb4.append(",");
                        sb4.append(matStepData.rotate_rate_y_);
                        sb4.append(",");
                        sb4.append(matStepData.rotate_rate_z_);
                        sb4.append(",");
                        sb4.append(matStepData.step_len_);
                        sb4.append(",");
                        sb4.append(matStepData.step_len_f_);
                        sb4.append(",");
                        sb4.append(matStepData.step_len_v_);
                        sb4.append(",");
                        sb4.append(matStepData.angle_no_mag_);
                        sb4.append(",");
                        sb4.append(matStepData.move_direction_);
                        sb4.append(",");
                        sb4.append(matStepData.move_state_score_);
                        h3.d(sb4.toString());
                    }
                    long j = matStepData.timestamp_;
                    int i2 = matStepData.step_;
                    double d = matStepData.angle_;
                    double d2 = matStepData.mat_data_[0];
                    double d3 = matStepData.mat_data_[1];
                    double d4 = matStepData.mat_data_[2];
                    double d5 = matStepData.q1_;
                    double d6 = matStepData.q2_;
                    double d7 = matStepData.q3_;
                    double d8 = matStepData.q4_;
                    double d9 = matStepData.rotate_rate_x_;
                    double d10 = matStepData.rotate_rate_y_;
                    double d11 = matStepData.rotate_rate_z_;
                    double d12 = matStepData.step_len_;
                    double d13 = matStepData.step_len_f_;
                    double d14 = matStepData.step_len_v_;
                    double d15 = d14;
                    JNIWrapper.jniAddMatStepData(j, i2, d, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12, d13, d15, matStepData.move_direction_, matStepData.angle_no_mag_, matStepData.move_state_score_);
                    onlineLocator.q = matStepData.step_;
                    onlineLocator.r = matStepData.showangle;
                    onlineLocator.s = matStepData.angle_no_mag_;
                } else if (i == 212) {
                    if (L.isLogging) {
                        StringBuilder sb5 = new StringBuilder("MSG_NETWORK_ERROR Error!");
                        sb5.append(message2.what);
                        L.d(sb5.toString());
                    }
                    MessageHelper.publishMessage(onlineLocator.mOutterHandlers, message2.what);
                } else if (i != 214) {
                    if (!(i == 501 || i == 506)) {
                        if (i != 1218) {
                            switch (i) {
                                case 503:
                                case 504:
                                    break;
                                default:
                                    switch (i) {
                                        case 1200:
                                            if (onlineLocator.mIsLocating) {
                                                if (L.isLogging) {
                                                    StringBuilder sb6 = new StringBuilder("TIMER(2S) PdrEnable=");
                                                    sb6.append(onlineLocator.isPdrEnable());
                                                    sb6.append(", HasScan:");
                                                    sb6.append(onlineLocator.c);
                                                    L.d(sb6.toString());
                                                }
                                                if (onlineLocator.c) {
                                                    if (!(onlineLocator.d == null || onlineLocator.q == -1)) {
                                                        if (onlineLocator.q == onlineLocator.p) {
                                                            if (L.isLogging) {
                                                                StringBuilder sb7 = new StringBuilder("Don't request onlinelocation.for: CurrentStep=");
                                                                sb7.append(onlineLocator.q);
                                                                sb7.append(", LastStep=");
                                                                sb7.append(onlineLocator.p);
                                                                L.d(sb7.toString());
                                                            }
                                                        }
                                                    }
                                                    JNIWrapper.c = 0;
                                                    JNIWrapper.b = null;
                                                    if (JNIWrapper.jniGetSendOnlineRequest(System.currentTimeMillis(), onlineLocator.mBuildingID, onlineLocator.isPdrEnable()) && JNIWrapper.c > 0 && JNIWrapper.b != null) {
                                                        if (L.isLogging) {
                                                            StringBuilder sb8 = new StringBuilder("JniOnlineRequest.length:");
                                                            sb8.append(JNIWrapper.c);
                                                            sb8.append(" request.data.len:");
                                                            sb8.append(JNIWrapper.b.length);
                                                            sb8.append(", parent.mlastReportResult=");
                                                            sb8.append(onlineLocator.d);
                                                            L.d(sb8.toString());
                                                        }
                                                        if (onlineLocator.e != null) {
                                                            onlineLocator.e.a(onlineLocator.mBuildingID, JNIWrapper.b);
                                                        }
                                                        onlineLocator.p = onlineLocator.q;
                                                    } else if (L.isLogging) {
                                                        L.d((String) "Don't request onlinelocation.for:request is null or length is 0");
                                                    }
                                                }
                                                sendEmptyMessageDelayed(1200, 2000);
                                                return;
                                            }
                                            break;
                                        case 1201:
                                            ScanData scanData = (ScanData) message2.obj;
                                            onlineLocator.a = scanData.scans_.size();
                                            onlineLocator.b = System.currentTimeMillis();
                                            onlineLocator.c = true;
                                            if (!JNIWrapper.jniSetScan(scanData.time_, scanData) && L.isLogging) {
                                                L.d((String) "ERROR on JNIWrapper.jniSetScan.");
                                            }
                                            if (scanData.size() > 0 && onlineLocator.l.isLogging) {
                                                onlineLocator.l.d(scanData);
                                            }
                                            if (onlineLocator.m == 0) {
                                                if (L.isLogging) {
                                                    L.d((String) "First Location not success, request it right now");
                                                }
                                                removeMessages(1200);
                                                onlineLocator.c();
                                                sendEmptyMessageDelayed(1200, 2000);
                                                return;
                                            }
                                            break;
                                        case 1202:
                                            ScanData scanData2 = (ScanData) message2.obj;
                                            if (L.isLogging) {
                                                StringBuilder sb9 = new StringBuilder("BleScaned:");
                                                sb9.append(scanData2.scans_.size());
                                                L.d(sb9.toString());
                                            }
                                            onlineLocator.a = scanData2.scans_.size();
                                            onlineLocator.b = System.currentTimeMillis();
                                            onlineLocator.c = true;
                                            if (!JNIWrapper.jniSetScan(scanData2.time_, scanData2) && L.isLogging) {
                                                L.d((String) "ERROR on JNIWrapper.jniSetScan.");
                                            }
                                            if (L.isLogging) {
                                                StringBuilder sb10 = new StringBuilder("ble scan , time = ");
                                                sb10.append(scanData2.time_);
                                                L.d(sb10.toString());
                                            }
                                            if (scanData2.size() > 0 && onlineLocator.l.isLogging) {
                                                onlineLocator.l.d(scanData2);
                                                return;
                                            }
                                    }
                                    break;
                            }
                        } else if (onlineLocator.mIsLocating) {
                            if (!TextUtils.isEmpty(onlineLocator.getConfiguration().mSimulateFile)) {
                                onlineLocator.r = FileDataProvider.getInstance().mLastAngle;
                            } else if (!onlineLocator.isPdrEnable() && onlineLocator.getConfiguration().mPDRProvider == PDRProvider.DEFAULT) {
                                onlineLocator.r = (double) PedProvider.getInstance().getOrientation();
                            }
                            onlineLocator.d();
                            sendEmptyMessageDelayed(1218, 100);
                            return;
                        }
                    }
                    if (L.isLogging) {
                        StringBuilder sb11 = new StringBuilder("WIFI/BLE Scan Error! Has no scan for msg=");
                        sb11.append(message2.what);
                        L.d(sb11.toString());
                    }
                    onlineLocator.a = 0;
                    onlineLocator.c = false;
                    MessageHelper.publishMessage(onlineLocator.mOutterHandlers, message2.what, (Object) onlineLocator);
                } else {
                    onlineLocator.a(message2);
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                if (L.isLogging) {
                    L.d(th2);
                }
            }
        }
    }

    public static String getSubVersion() {
        return "20170413";
    }

    public static String getVersion() {
        return "6.9";
    }

    OnlineLocator() {
    }

    public static OnlineLocator getInstance() {
        if (h == null) {
            synchronized (OnlineLocator.class) {
                try {
                    if (h == null) {
                        h = new OnlineLocator();
                    }
                }
            }
        }
        return h;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0015, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0071, code lost:
        return;
     */
    @android.support.annotation.RequiresPermission(allOf = {"android.permission.BLUETOOTH", "android.permission.ACCESS_WIFI_STATE"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void init(java.lang.String r3, com.autonavi.indoor.constant.Configuration r4, final android.os.Handler r5) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r4 == 0) goto L_0x01a9
            if (r5 != 0) goto L_0x0007
            goto L_0x01a9
        L_0x0007:
            com.autonavi.indoor.constant.Configuration r0 = r2.mConfiguration     // Catch:{ all -> 0x01b1 }
            if (r0 == 0) goto L_0x0016
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x0014
            java.lang.String r3 = "Try to initialize OnlineLocation which had already been initialized before. To re-init LocationManager with new mConfiguration call OnlineLocation.destroy() at first."
            com.autonavi.indoor.util.L.d(r3)     // Catch:{ all -> 0x01b1 }
        L_0x0014:
            monitor-exit(r2)
            return
        L_0x0016:
            android.content.Context r0 = r4.context     // Catch:{ all -> 0x01b1 }
            java.lang.String[] r1 = com.autonavi.indoor.util.PermissionUtil.permissions     // Catch:{ all -> 0x01b1 }
            boolean r0 = com.autonavi.indoor.util.PermissionUtil.hasSelfPermission(r0, r1)     // Catch:{ all -> 0x01b1 }
            if (r0 != 0) goto L_0x0043
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x0029
            java.lang.String r3 = "hasSelfPermission failed"
            com.autonavi.indoor.util.L.d(r3)     // Catch:{ all -> 0x01b1 }
        L_0x0029:
            java.lang.SecurityException r3 = new java.lang.SecurityException     // Catch:{ all -> 0x01b1 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x01b1 }
            java.lang.String r5 = "Need permissions:"
            r4.<init>(r5)     // Catch:{ all -> 0x01b1 }
            java.lang.String[] r5 = com.autonavi.indoor.util.PermissionUtil.permissions     // Catch:{ all -> 0x01b1 }
            java.lang.String r5 = java.util.Arrays.toString(r5)     // Catch:{ all -> 0x01b1 }
            r4.append(r5)     // Catch:{ all -> 0x01b1 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x01b1 }
            r3.<init>(r4)     // Catch:{ all -> 0x01b1 }
            throw r3     // Catch:{ all -> 0x01b1 }
        L_0x0043:
            java.lang.String r0 = com.autonavi.indoor.util.MapUtils.getImei()     // Catch:{ all -> 0x01b1 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x01b1 }
            if (r0 == 0) goto L_0x0052
            android.content.Context r0 = r4.context     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.util.MapUtils.getImei(r0)     // Catch:{ all -> 0x01b1 }
        L_0x0052:
            java.lang.String r0 = com.autonavi.indoor.util.MapUtils.getImsi()     // Catch:{ all -> 0x01b1 }
            boolean r0 = android.text.TextUtils.isEmpty(r0)     // Catch:{ all -> 0x01b1 }
            if (r0 == 0) goto L_0x0061
            android.content.Context r0 = r4.context     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.util.MapUtils.getImsi(r0)     // Catch:{ all -> 0x01b1 }
        L_0x0061:
            boolean r0 = r2.checkSensor(r4, r5)     // Catch:{ all -> 0x01b1 }
            if (r0 != 0) goto L_0x0072
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x0070
            java.lang.String r3 = "WARNING:Senero check failed"
            com.autonavi.indoor.util.L.d(r3)     // Catch:{ all -> 0x01b1 }
        L_0x0070:
            monitor-exit(r2)
            return
        L_0x0072:
            boolean r0 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x01b1 }
            if (r0 == 0) goto L_0x007a
            java.lang.String r3 = ""
        L_0x007a:
            r2.mBuildingID = r3     // Catch:{ all -> 0x01b1 }
            r2.mConfiguration = r4     // Catch:{ all -> 0x01b1 }
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x0099
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01b1 }
            java.lang.String r4 = "Initialize OnlineLocator with mConfiguration. Provider="
            r3.<init>(r4)     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.constant.Configuration r4 = r2.getConfiguration()     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.constant.Configuration$LocationProvider r4 = r4.mLocationProvider     // Catch:{ all -> 0x01b1 }
            r3.append(r4)     // Catch:{ all -> 0x01b1 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.util.L.d(r3)     // Catch:{ all -> 0x01b1 }
        L_0x0099:
            com.autonavi.indoor.constant.Configuration r3 = r2.getConfiguration()     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.constant.Configuration$PDRProvider r3 = r3.mPDRProvider     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.constant.Configuration$PDRProvider r4 = com.autonavi.indoor.constant.Configuration.PDRProvider.DEFAULT     // Catch:{ all -> 0x01b1 }
            if (r3 != r4) goto L_0x00c9
            com.autonavi.indoor.pdr.PedProvider r3 = com.autonavi.indoor.pdr.PedProvider.getInstance()     // Catch:{ all -> 0x01b1 }
            boolean r3 = r3.isInited()     // Catch:{ all -> 0x01b1 }
            if (r3 != 0) goto L_0x00bf
            com.autonavi.indoor.pdr.PedProvider r3 = com.autonavi.indoor.pdr.PedProvider.getInstance()     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.constant.Configuration r4 = r2.mConfiguration     // Catch:{ all -> 0x01b1 }
            android.content.Context r4 = r4.context     // Catch:{ all -> 0x01b1 }
            int r3 = r3.init(r4)     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x00e4
            r5.sendEmptyMessage(r3)     // Catch:{ all -> 0x01b1 }
            goto L_0x00e4
        L_0x00bf:
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x00e4
            java.lang.String r3 = "WARNING:PedProvider isInited"
            com.autonavi.indoor.util.L.d(r3)     // Catch:{ all -> 0x01b1 }
            goto L_0x00e4
        L_0x00c9:
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x00e4
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01b1 }
            java.lang.String r4 = "getConfiguration().mPDRProvider="
            r3.<init>(r4)     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.constant.Configuration r4 = r2.getConfiguration()     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.constant.Configuration$PDRProvider r4 = r4.mPDRProvider     // Catch:{ all -> 0x01b1 }
            r3.append(r4)     // Catch:{ all -> 0x01b1 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.util.L.d(r3)     // Catch:{ all -> 0x01b1 }
        L_0x00e4:
            com.autonavi.indoor.constant.Configuration r3 = r2.getConfiguration()     // Catch:{ all -> 0x01b1 }
            boolean r3 = r3.isUsingWifi()     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x010a
            com.autonavi.indoor.provider.WifiProvider r3 = com.autonavi.indoor.provider.WifiProvider.getInstance()     // Catch:{ all -> 0x01b1 }
            boolean r3 = r3.isInited()     // Catch:{ all -> 0x01b1 }
            if (r3 != 0) goto L_0x010a
            com.autonavi.indoor.provider.WifiProvider r3 = com.autonavi.indoor.provider.WifiProvider.getInstance()     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.constant.Configuration r4 = r2.mConfiguration     // Catch:{ all -> 0x01b1 }
            android.content.Context r4 = r4.context     // Catch:{ all -> 0x01b1 }
            int r3 = r3.init(r4)     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x011d
            r5.sendEmptyMessage(r3)     // Catch:{ all -> 0x01b1 }
            goto L_0x011d
        L_0x010a:
            com.autonavi.indoor.provider.WifiProvider r3 = com.autonavi.indoor.provider.WifiProvider.getInstance()     // Catch:{ all -> 0x01b1 }
            boolean r3 = r3.isInited()     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x011d
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x011d
            java.lang.String r3 = "WARNING:WifiProvider has been Inited"
            com.autonavi.indoor.util.L.d(r3)     // Catch:{ all -> 0x01b1 }
        L_0x011d:
            com.autonavi.indoor.constant.Configuration r3 = r2.getConfiguration()     // Catch:{ all -> 0x01b1 }
            boolean r3 = r3.isUsingWifi()     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x012f
            com.autonavi.indoor.provider.WifiProvider r3 = com.autonavi.indoor.provider.WifiProvider.getInstance()     // Catch:{ all -> 0x01b1 }
            r4 = 0
            r3.setScanMode(r4)     // Catch:{ all -> 0x01b1 }
        L_0x012f:
            com.autonavi.indoor.constant.Configuration r3 = r2.getConfiguration()     // Catch:{ all -> 0x01b1 }
            boolean r3 = r3.isUsingBLE()     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x0155
            com.autonavi.indoor.provider.BLEProvider r3 = com.autonavi.indoor.provider.BLEProvider.getInstance()     // Catch:{ all -> 0x01b1 }
            boolean r3 = r3.isInited()     // Catch:{ all -> 0x01b1 }
            if (r3 != 0) goto L_0x0155
            com.autonavi.indoor.provider.BLEProvider r3 = com.autonavi.indoor.provider.BLEProvider.getInstance()     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.constant.Configuration r4 = r2.mConfiguration     // Catch:{ all -> 0x01b1 }
            android.content.Context r4 = r4.context     // Catch:{ all -> 0x01b1 }
            int r3 = r3.init(r4)     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x0168
            r5.sendEmptyMessage(r3)     // Catch:{ all -> 0x01b1 }
            goto L_0x0168
        L_0x0155:
            com.autonavi.indoor.constant.Configuration r3 = r2.getConfiguration()     // Catch:{ all -> 0x01b1 }
            boolean r3 = r3.isUsingBLE()     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x0168
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x0168
            java.lang.String r3 = "WARNING:BLEProvider has been inited"
            com.autonavi.indoor.util.L.d(r3)     // Catch:{ all -> 0x01b1 }
        L_0x0168:
            com.autonavi.indoor.constant.Configuration r3 = r2.getConfiguration()     // Catch:{ all -> 0x01b1 }
            java.lang.String r3 = r3.mSimulateFile     // Catch:{ all -> 0x01b1 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ all -> 0x01b1 }
            if (r3 != 0) goto L_0x0199
            com.autonavi.indoor.simulator.FileDataProvider r3 = com.autonavi.indoor.simulator.FileDataProvider.getInstance()     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.constant.Configuration r4 = r2.mConfiguration     // Catch:{ all -> 0x01b1 }
            android.content.Context r4 = r4.context     // Catch:{ all -> 0x01b1 }
            int r3 = r3.init(r4)     // Catch:{ all -> 0x01b1 }
            if (r3 == 0) goto L_0x018e
            boolean r4 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x01b1 }
            if (r4 == 0) goto L_0x018b
            java.lang.String r4 = "WARNING:init Simulator failed"
            com.autonavi.indoor.util.L.d(r4)     // Catch:{ all -> 0x01b1 }
        L_0x018b:
            r5.sendEmptyMessage(r3)     // Catch:{ all -> 0x01b1 }
        L_0x018e:
            com.autonavi.indoor.simulator.FileDataProvider r3 = com.autonavi.indoor.simulator.FileDataProvider.getInstance()     // Catch:{ all -> 0x01b1 }
            com.autonavi.indoor.constant.Configuration r4 = r2.mConfiguration     // Catch:{ all -> 0x01b1 }
            java.lang.String r4 = r4.mSimulateFile     // Catch:{ all -> 0x01b1 }
            r3.setSimulatFile(r4)     // Catch:{ all -> 0x01b1 }
        L_0x0199:
            com.autonavi.indoor.onlinelocation.OnlineLocator$1 r3 = new com.autonavi.indoor.onlinelocation.OnlineLocator$1     // Catch:{ all -> 0x01b1 }
            java.lang.String r4 = "OnlineLocationThread"
            r3.<init>(r4, r5)     // Catch:{ all -> 0x01b1 }
            r2.mLocationThread = r3     // Catch:{ all -> 0x01b1 }
            android.os.HandlerThread r3 = r2.mLocationThread     // Catch:{ all -> 0x01b1 }
            r3.start()     // Catch:{ all -> 0x01b1 }
            monitor-exit(r2)
            return
        L_0x01a9:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x01b1 }
            java.lang.String r4 = "OnlineLocator mConfiguration can not be initialized with null"
            r3.<init>(r4)     // Catch:{ all -> 0x01b1 }
            throw r3     // Catch:{ all -> 0x01b1 }
        L_0x01b1:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indoor.onlinelocation.OnlineLocator.init(java.lang.String, com.autonavi.indoor.constant.Configuration, android.os.Handler):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0024, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x005c, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void destroy() {
        /*
            r2 = this;
            monitor-enter(r2)
            boolean r0 = r2.mIsInited     // Catch:{ all -> 0x005d }
            if (r0 != 0) goto L_0x0014
            com.autonavi.indoor.constant.Configuration r0 = r2.mConfiguration     // Catch:{ all -> 0x005d }
            if (r0 != 0) goto L_0x0014
            boolean r0 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x0012
            java.lang.String r0 = "locationManager has been destoried"
            com.autonavi.indoor.util.L.d(r0)     // Catch:{ all -> 0x005d }
        L_0x0012:
            monitor-exit(r2)
            return
        L_0x0014:
            boolean r0 = r2.isLocating()     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x0025
            boolean r0 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x0023
            java.lang.String r0 = "isLocating"
            com.autonavi.indoor.util.L.d(r0)     // Catch:{ all -> 0x005d }
        L_0x0023:
            monitor-exit(r2)
            return
        L_0x0025:
            r2.b()     // Catch:{ all -> 0x005d }
            java.util.ArrayList r0 = r2.mOutterHandlers     // Catch:{ all -> 0x005d }
            r0.clear()     // Catch:{ all -> 0x005d }
            com.autonavi.indoor.pdr.PedProvider r0 = com.autonavi.indoor.pdr.PedProvider.getInstance()     // Catch:{ all -> 0x005d }
            r1 = 0
            r0.setSensorHandler(r1)     // Catch:{ all -> 0x005d }
            android.os.HandlerThread r0 = r2.mLocationThread     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x0040
            android.os.HandlerThread r0 = r2.mLocationThread     // Catch:{ all -> 0x005d }
            r0.quit()     // Catch:{ all -> 0x005d }
            r2.mLocationThread = r1     // Catch:{ all -> 0x005d }
        L_0x0040:
            com.autonavi.indoor.onlinelocation.b r0 = r2.e     // Catch:{ InterruptedException -> 0x004c }
            if (r0 == 0) goto L_0x0050
            com.autonavi.indoor.onlinelocation.b r0 = r2.e     // Catch:{ InterruptedException -> 0x004c }
            r0.a()     // Catch:{ InterruptedException -> 0x004c }
            r2.e = r1     // Catch:{ InterruptedException -> 0x004c }
            goto L_0x0050
        L_0x004c:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x005d }
        L_0x0050:
            r2.mConfiguration = r1     // Catch:{ all -> 0x005d }
            boolean r0 = com.autonavi.indoor.util.L.isLogging     // Catch:{ all -> 0x005d }
            if (r0 == 0) goto L_0x005b
            java.lang.String r0 = "onlinelocator destroyed"
            com.autonavi.indoor.util.L.d(r0)     // Catch:{ all -> 0x005d }
        L_0x005b:
            monitor-exit(r2)
            return
        L_0x005d:
            r0 = move-exception
            monitor-exit(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indoor.onlinelocation.OnlineLocator.destroy():void");
    }

    public void requestLocationUpdates(Handler handler) {
        if (handler != null) {
            if (!isInited()) {
                if (L.isLogging) {
                    L.d((String) "onlinelocator has not been inited");
                }
                return;
            }
            synchronized (this) {
                synchronized (this.mOutterHandlers) {
                    if (!this.mOutterHandlers.contains(handler)) {
                        this.mOutterHandlers.add(handler);
                    } else if (L.isLogging) {
                        L.d((String) "Handler already exist");
                    }
                    if (this.mOutterHandlers.size() > 0 && !this.mIsLocating && !a() && L.isLogging) {
                        L.d((String) "Failed to start OnlineLocation.");
                    }
                }
            }
        }
    }

    public void removeUpdates(Handler handler) {
        if (L.isLogging) {
            StringBuilder sb = new StringBuilder("removeUpdates listener:");
            sb.append(handler);
            sb.append(", Handler count = ");
            sb.append(this.mOutterHandlers.size());
            L.d(sb.toString());
        }
        if (!this.mOutterHandlers.contains(handler)) {
            if (L.isLogging) {
                L.d((String) "removeUpdates listener doesn't exist.");
            }
        } else if (!isInited()) {
            if (L.isLogging) {
                L.d((String) "onlinelocator has not been inited");
            }
        } else {
            synchronized (this) {
                this.mOutterHandlers.remove(handler);
                if (this.mOutterHandlers.isEmpty()) {
                    b();
                }
            }
        }
    }

    public void pause() {
        this.j = true;
        if (L.isLogging) {
            L.d((String) "online pause");
        }
    }

    public void resume() {
        if (L.isLogging) {
            L.d((String) "online resume");
        }
        if (this.j) {
            this.j = false;
            this.i.removeMessages(1200);
            this.i.sendEmptyMessageDelayed(1200, 2000);
            if (!isPdrEnable()) {
                this.i.sendEmptyMessage(1218);
            }
        }
    }

    public boolean isPaused() {
        return this.j;
    }

    public boolean isPdrEnable() {
        return this.k == 2;
    }

    public void recordLocationData(double d2, double d3, int i2) {
        checkInit();
        if (this.mIsLocating && this.l.isLogging) {
            FileLogger fileLogger = this.l;
            StringBuilder sb = new StringBuilder("pts:");
            sb.append(System.currentTimeMillis());
            sb.append(",");
            sb.append(d2);
            sb.append(",");
            sb.append(d3);
            sb.append(",");
            sb.append(i2);
            sb.append(",1");
            fileLogger.d(sb.toString());
        }
    }

    public void recordMessage(String str) {
        if (this.l.isLogging) {
            this.l.d(str);
        }
    }

    public void setPdr(int i2, float f2) {
        if (getConfiguration().mPDRProvider == PDRProvider.DEFAULT) {
            if (L.isLogging) {
                L.d((String) "Please configure PDRProvider first.");
                return;
            }
        } else if (isLocating()) {
            MatStepData matStepData = new MatStepData(i2, (double) f2, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.7d, 0.0d, 0.0d, 0, 0.0d, 0.0d);
            matStepData.timestamp_ = System.currentTimeMillis();
            this.i.obtainMessage(111, matStepData).sendToTarget();
        } else {
            if (L.isLogging) {
                StringBuilder sb = new StringBuilder("isLocating()=");
                sb.append(isLocating());
                L.d(sb.toString());
            }
        }
    }

    public byte[] getFeedbackBuffer() {
        return this.mLastRequestBuffer;
    }

    private boolean a() {
        checkInit();
        if (this.mIsLocating) {
            if (L.isLogging) {
                L.d((String) "locator is running, don't need to start again");
            }
            return true;
        }
        JNIWrapper.jniReset();
        String upperCase = MapUtils.encodeWifiMac(com.amap.location.common.a.f(this.mConfiguration.context)).toUpperCase();
        String str = Build.MODEL;
        StringBuilder sb = new StringBuilder(com.alipay.android.phone.a.a.a.a);
        sb.append(VERSION.SDK_INT);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(PackageHelper.getApplicationName(this.mConfiguration.context));
        sb3.append(PackageHelper.getApplicationVersion(this.mConfiguration.context));
        JNIWrapper.jniSetRequestHeader("IndoorLocation", "6.9", str, sb2, sb3.toString(), "", MapUtils.getImei(), MapUtils.getImsi(), MapUtils.encodeMacLong(upperCase));
        if (L.isLogging) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(Build.MODEL);
            sb4.append(",Android ");
            sb4.append(VERSION.RELEASE);
            sb4.append(",");
            sb4.append(PackageHelper.getApplicationName(this.mConfiguration.context));
            sb4.append(PackageHelper.getApplicationVersion(this.mConfiguration.context));
            L.d(sb4.toString());
        }
        this.l.start(L.logalgoPath);
        if (this.l.isLogging) {
            FileLogger fileLogger = this.l;
            StringBuilder sb5 = new StringBuilder("bID:");
            sb5.append(this.mBuildingID);
            fileLogger.d(sb5.toString());
        }
        this.a = 0;
        this.b = System.currentTimeMillis();
        this.m = 0;
        this.n = -1;
        this.c = false;
        this.mLastRequestBuffer = null;
        try {
            if (!TextUtils.isEmpty(getConfiguration().mSimulateFile)) {
                FileDataProvider.getInstance().registerListener(this.i);
                FileDataProvider.getInstance().mEnablePdr = PedProvider.getInstance().getSensorType() != 0;
                this.k = PedProvider.getInstance().getSensorType();
            } else {
                if (getConfiguration().isUsingWifi()) {
                    WifiProvider.getInstance().registerListener(this.i);
                }
                if (getConfiguration().isUsingBLE()) {
                    BLEProvider.getInstance().registerListener(this.i);
                }
                if (getConfiguration().mPDRProvider == PDRProvider.DEFAULT) {
                    this.k = PedProvider.getInstance().getSensorType();
                    if (this.k == 0) {
                        if (L.isLogging) {
                            L.d((String) "start JNI, 传感器缺失，算法中将禁掉PDR");
                        }
                        MessageHelper.publishMessage(this.mOutterHandlers, MessageCode.MSG_SENSOR_MISSING);
                    }
                    PedProvider.getInstance().registerListener(this.i);
                } else {
                    if (L.isLogging) {
                        StringBuilder sb6 = new StringBuilder("getConfiguration().mPDRProvider=");
                        sb6.append(getConfiguration().mPDRProvider);
                        L.d(sb6.toString());
                    }
                    this.k = 2;
                }
            }
            this.mIsLocating = true;
            this.j = false;
            this.p = -1;
            this.q = -1;
            this.r = -1.0d;
            this.s = -1.0d;
            this.d = null;
            this.i.sendEmptyMessageDelayed(1200, 2000);
            this.i.sendEmptyMessageDelayed(1218, 2000);
            if (L.isLogging) {
                L.d((String) H5PageData.KEY_UC_START);
            }
            return true;
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
            return false;
        }
    }

    private void b() {
        checkInit();
        if (this.mIsLocating) {
            try {
                if (!TextUtils.isEmpty(getConfiguration().mSimulateFile)) {
                    FileDataProvider.getInstance().unregisterListener(this.i);
                }
                this.i.removeMessages(1200);
                this.i.removeMessages(1218);
                if (TextUtils.isEmpty(getConfiguration().mSimulateFile)) {
                    if (getConfiguration().isUsingWifi()) {
                        WifiProvider.getInstance().unregisterListener(this.i);
                    }
                    if (getConfiguration().isUsingBLE()) {
                        BLEProvider.getInstance().unregisterListener(this.i);
                    }
                    if (getConfiguration().mPDRProvider == PDRProvider.DEFAULT) {
                        PedProvider.getInstance().unregisterListener(this.i);
                    }
                }
                this.mIsLocating = false;
                this.j = false;
                this.l.stop();
                if (L.isLogging) {
                    L.d((String) "onlinelocator is stoped");
                }
            } catch (Throwable th) {
                if (L.isLogging) {
                    L.d(th);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        JNIWrapper.c = 0;
        JNIWrapper.b = null;
        if (!JNIWrapper.jniGetSendOnlineRequest(System.currentTimeMillis(), this.mBuildingID, isPdrEnable()) || JNIWrapper.b == null || JNIWrapper.c <= 0) {
            if (L.isLogging) {
                L.d((String) "jniGetSendOnlineRequest return invalid request buffer, pass");
            }
            return;
        }
        if (L.isLogging) {
            StringBuilder sb = new StringBuilder("JniOnlineRequest.length:");
            sb.append(JNIWrapper.c);
            sb.append(" request.data.len:");
            sb.append(JNIWrapper.b.length);
            L.d(sb.toString());
        }
        if (this.e != null) {
            this.e.a(this.mBuildingID, JNIWrapper.b);
        }
        this.mLastRequestBuffer = JNIWrapper.b;
    }

    /* access modifiers changed from: private */
    public void d() {
        String str;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - this.g >= ((long) this.mConfiguration.mReportInterval)) {
            if (this.m == 0 || currentTimeMillis - this.m <= 10000 || this.q == this.n) {
                this.g = currentTimeMillis;
                JNIWrapper.jniGetLocateResult(currentTimeMillis);
                boolean z = false;
                if (L.isLogging) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.format("AddStepAngle, step:%d angle:%.2f \nangle_nomag:%.2f error:%.2f", new Object[]{Integer.valueOf(this.q), Double.valueOf(this.r), Double.valueOf(this.s), Double.valueOf(this.t)}));
                    sb.append("\nLastSuccessTime:");
                    sb.append(this.m);
                    sb.append(",");
                    sb.append(currentTimeMillis - this.m);
                    sb.append("ms, lastPOS:");
                    sb.append(this.o);
                    sb.append(", LastStep:");
                    sb.append(this.p);
                    str = sb.toString();
                    L.d(str);
                    StringBuilder sb2 = new StringBuilder("jniGetLocateResult: x=");
                    sb2.append(JNIWrapper.d);
                    sb2.append(", y=");
                    sb2.append(JNIWrapper.e);
                    sb2.append(", z=");
                    sb2.append(JNIWrapper.f);
                    L.d(sb2.toString());
                } else {
                    str = null;
                }
                if (JNIWrapper.d < -1000.0d || JNIWrapper.e < -1000.0d || JNIWrapper.f < -100 || JNIWrapper.f == 0) {
                    if (this.q != -1 && this.q == this.p) {
                        z = true;
                    }
                    if (z && this.d != null) {
                        if (L.isLogging) {
                            StringBuilder sb3 = new StringBuilder("going to publish result(using old result):");
                            sb3.append(this.d.x);
                            sb3.append(", ");
                            sb3.append(this.d.y);
                            sb3.append(", ");
                            sb3.append(this.d.z);
                            L.d(sb3.toString());
                        }
                        this.d.a = (float) this.r;
                        MessageHelper.publishLocationResult(this.mOutterHandlers, true, this.d, str);
                    }
                    if (this.q != this.p) {
                        if (L.isLogging) {
                            StringBuilder sb4 = new StringBuilder("clear lastReportResult. currentstep=");
                            sb4.append(this.q);
                            sb4.append(", lastStep");
                            sb4.append(this.p);
                            L.d(sb4.toString());
                        }
                        this.d = null;
                    }
                    return;
                }
                LocationResult locationResult = new LocationResult();
                locationResult.x = JNIWrapper.d;
                locationResult.y = JNIWrapper.e;
                locationResult.z = JNIWrapper.f;
                locationResult.a = (float) this.r;
                locationResult.r = 2.0f;
                locationResult.bid = this.mBuildingID;
                if (L.isLogging) {
                    StringBuilder sb5 = new StringBuilder("going to publish result:");
                    sb5.append(JNIWrapper.d);
                    sb5.append(", ");
                    sb5.append(JNIWrapper.e);
                    sb5.append(", ");
                    sb5.append(this.r);
                    sb5.append(", ");
                    sb5.append(locationResult.z);
                    sb5.append(", CurrentStep=");
                    sb5.append(this.q);
                    L.d(sb5.toString());
                }
                MessageHelper.publishLocationResult(this.mOutterHandlers, true, locationResult, str);
                this.d = locationResult;
                return;
            }
            if (L.isLogging) {
                StringBuilder sb6 = new StringBuilder("NetLocation out of date:");
                sb6.append(this.m);
                sb6.append(", ");
                sb6.append(currentTimeMillis - this.m);
                sb6.append("lastStep=");
                sb6.append(this.n);
                sb6.append(", curStep=");
                sb6.append(this.q);
                L.d(sb6.toString());
            }
            this.d = null;
        }
    }

    /* access modifiers changed from: private */
    public void a(Message message) {
        a aVar = (a) message.obj;
        if (aVar == null) {
            if (L.isLogging) {
                L.d((String) "定位返回结果都是无效的，不能用来定位!");
            }
        } else if (Double.isNaN(aVar.a) || Double.isNaN(aVar.b)) {
            if (L.isLogging) {
                L.d((String) "online addRequest failed, server return NaN, nan");
            }
        } else {
            this.m = System.currentTimeMillis();
            this.n = this.q;
            StringBuilder sb = new StringBuilder("LastSuccessPosition:");
            sb.append(aVar.a);
            sb.append(",");
            sb.append(aVar.b);
            this.o = sb.toString();
            this.t = aVar.k;
            JNIWrapper.jniAddOnlineResult(this.m, aVar.a, aVar.b, aVar.c, aVar.g, (double) aVar.e, aVar.i, aVar.j, aVar.h, aVar.k);
            if (L.isLogging) {
                StringBuilder sb2 = new StringBuilder("NLPResponsed: ");
                sb2.append(aVar.l);
                sb2.append(", x=");
                sb2.append(aVar.a);
                sb2.append(", y=");
                sb2.append(aVar.b);
                sb2.append(", z=");
                sb2.append(aVar.c);
                sb2.append(", step_length = ");
                sb2.append(aVar.g);
                sb2.append(",zero_angle = ");
                sb2.append(aVar.h);
                sb2.append(", angle_error=");
                sb2.append(aVar.k);
                sb2.append(", t = ");
                sb2.append(aVar.j);
                L.d(sb2.toString());
            }
            if (this.l.isLogging) {
                FileLogger fileLogger = this.l;
                StringBuilder sb3 = new StringBuilder("tp1:");
                sb3.append(aVar.j);
                sb3.append(",");
                sb3.append(aVar.a);
                sb3.append(",");
                sb3.append(aVar.b);
                sb3.append(",");
                sb3.append(aVar.c);
                sb3.append(",0");
                fileLogger.d(sb3.toString());
            }
            if (TextUtils.isEmpty(this.mBuildingID) && !TextUtils.isEmpty(aVar.l)) {
                MessageHelper.publishMessage(this.mOutterHandlers, (int) MessageCode.MSG_ONLINE_BUILDING_LOCATED, (Object) aVar.l);
                this.mBuildingID = aVar.l;
                if (L.isLogging) {
                    L.d((String) "MSG_ONLINE_BUILDING_LOCATED");
                }
            } else if (!TextUtils.isEmpty(this.mBuildingID) && !TextUtils.isEmpty(aVar.l) && !this.mBuildingID.equals(aVar.l)) {
                MessageHelper.publishMessage(this.mOutterHandlers, (int) MessageCode.MSG_ONLINE_BUILDING_CHANGED, (Object) aVar.l);
                this.mBuildingID = aVar.l;
                if (L.isLogging) {
                    L.d((String) "MSG_ONLINE_BUILDING_CHANGED");
                }
            }
        }
    }
}
