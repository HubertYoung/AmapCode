package com.alipay.mobile.common.transport.monitor;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.HandlerThread;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.monitor.Performance;
import com.alipay.mobile.common.transport.config.TransportConfigureItem;
import com.alipay.mobile.common.transport.config.TransportConfigureManager;
import com.alipay.mobile.common.transport.monitor.networkqos.AlipayQosService;
import com.alipay.mobile.common.transport.sys.telephone.NetTelephonyManagerFactory;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.NetworkAsyncTaskExecutor;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.alipay.mobile.common.utils.config.fmk.ConfigureItem;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class SignalStateHelper {
    private static SignalStateHelper e = null;
    private static long g = 0;
    /* access modifiers changed from: private */
    public TelephonyManager a;
    /* access modifiers changed from: private */
    public PhoneStateListener b;
    /* access modifiers changed from: private */
    public int c = 0;
    /* access modifiers changed from: private */
    public int d = 0;
    private HandlerThread f;
    private long h = 180000;

    class InnerPhoneStateListener extends PhoneStateListener {
        InnerPhoneStateListener() {
        }

        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            try {
                Class classSignalStrength = Class.forName(SignalStrength.class.getName());
                Method methodGetLevel = classSignalStrength.getDeclaredMethod("getLevel", new Class[0]);
                Method methodGetDbm = classSignalStrength.getDeclaredMethod("getDbm", new Class[0]);
                methodGetLevel.setAccessible(true);
                methodGetDbm.setAccessible(true);
                SignalStateHelper.this.d = ((Integer) methodGetDbm.invoke(signalStrength, new Object[0])).intValue();
                SignalStateHelper.this.c = ((Integer) methodGetLevel.invoke(signalStrength, new Object[0])).intValue();
                LogCatUtil.debug("SSMonitor", "SS Updated: dbm=[" + SignalStateHelper.this.d + "] sLevel=[" + SignalStateHelper.this.c + "]");
            } catch (Throwable e) {
                LogCatUtil.error((String) "SSMonitor", "Failed to invoke methods:" + e.getMessage());
            }
        }
    }

    public static SignalStateHelper getInstance() {
        if (e != null) {
            return e;
        }
        synchronized (SignalStateHelper.class) {
            try {
                if (e == null) {
                    e = new SignalStateHelper();
                }
            }
        }
        return e;
    }

    private SignalStateHelper() {
        try {
            this.a = (TelephonyManager) TransportEnvUtil.getContext().getSystemService("phone");
        } catch (Throwable ex) {
            LogCatUtil.error("SSMonitor", "SignalStateHelper constructor exception", ex);
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a() {
        /*
            r5 = this;
            r1 = 0
            java.lang.String r2 = "SSMonitor"
            java.lang.String r3 = "startMonitor"
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r2, r3)     // Catch:{ Throwable -> 0x0025 }
            android.os.HandlerThread r2 = r5.f     // Catch:{ Throwable -> 0x0025 }
            if (r2 == 0) goto L_0x0014
            java.lang.String r2 = "SSMonitor"
            java.lang.String r3 = "there is a task working still"
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r2, r3)     // Catch:{ Throwable -> 0x0025 }
        L_0x0013:
            return r1
        L_0x0014:
            monitor-enter(r5)     // Catch:{ Throwable -> 0x0025 }
            android.os.HandlerThread r2 = r5.f     // Catch:{ all -> 0x0022 }
            if (r2 == 0) goto L_0x003f
            java.lang.String r2 = "SSMonitor"
            java.lang.String r3 = "there is a task working still"
            com.alipay.mobile.common.transport.utils.LogCatUtil.debug(r2, r3)     // Catch:{ all -> 0x0022 }
            monitor-exit(r5)     // Catch:{ all -> 0x0022 }
            goto L_0x0013
        L_0x0022:
            r2 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0022 }
            throw r2     // Catch:{ Throwable -> 0x0025 }
        L_0x0025:
            r0 = move-exception
            java.lang.String r2 = "SSMonitor"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "startMonitor,ex:"
            r3.<init>(r4)
            java.lang.String r4 = r0.toString()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alipay.mobile.common.transport.utils.LogCatUtil.error(r2, r3)
            goto L_0x0013
        L_0x003f:
            android.os.HandlerThread r2 = new android.os.HandlerThread     // Catch:{ all -> 0x0022 }
            java.lang.String r3 = "SignalThread"
            r2.<init>(r3)     // Catch:{ all -> 0x0022 }
            r5.f = r2     // Catch:{ all -> 0x0022 }
            android.os.HandlerThread r2 = r5.f     // Catch:{ all -> 0x0022 }
            r2.start()     // Catch:{ all -> 0x0022 }
            monitor-exit(r5)     // Catch:{ all -> 0x0022 }
            android.os.Handler r2 = new android.os.Handler     // Catch:{ Throwable -> 0x0025 }
            android.os.HandlerThread r3 = r5.f     // Catch:{ Throwable -> 0x0025 }
            android.os.Looper r3 = r3.getLooper()     // Catch:{ Throwable -> 0x0025 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x0025 }
            com.alipay.mobile.common.transport.monitor.SignalStateHelper$1 r3 = new com.alipay.mobile.common.transport.monitor.SignalStateHelper$1     // Catch:{ Throwable -> 0x0025 }
            r3.<init>()     // Catch:{ Throwable -> 0x0025 }
            r2.post(r3)     // Catch:{ Throwable -> 0x0025 }
            r1 = 1
            goto L_0x0013
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.transport.monitor.SignalStateHelper.a():boolean");
    }

    /* access modifiers changed from: private */
    public void b() {
        try {
            LogCatUtil.debug("SSMonitor", "stopMonitor");
            if (this.b != null) {
                this.a.listen(this.b, 0);
            }
            if (this.f != null) {
                this.f.quit();
                this.f = null;
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "SSMonitor", "stopMonitor,ex:" + ex.toString());
        }
    }

    public String getWifiSignalLevel() {
        try {
            WifiInfo wifiInfo = ((WifiManager) TransportEnvUtil.getContext().getSystemService("wifi")).getConnectionInfo();
            if (wifiInfo == null) {
                return "0";
            }
            int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 5);
            LogCatUtil.debug("SSMonitor", wifiInfo.toString() + ", level: " + level);
            StringBuilder wifiSignalInfo = new StringBuilder();
            wifiSignalInfo.append(wifiInfo.getSSID()).append("_").append(wifiInfo.getBSSID()).append("_").append(level);
            return wifiSignalInfo.toString();
        } catch (Throwable ex) {
            LogCatUtil.error((String) "SSMonitor", ex);
            return "0";
        }
    }

    public String getMobileSignalInfo() {
        return "[Dbm=" + this.d + ",SignalLevel=" + this.c + "]";
    }

    public String getCellInfo() {
        try {
            CellLocation cellLocation = NetTelephonyManagerFactory.getInstance().getCellLocation();
            if (cellLocation == null) {
                LogCatUtil.warn((String) "SSMonitor", (String) "getCellInfo. cellLocation is null.");
                return "";
            }
            if (cellLocation instanceof CdmaCellLocation) {
                CdmaCellLocation cdmaCell = (CdmaCellLocation) cellLocation;
                if (cdmaCell != null) {
                    LogCatUtil.debug("SSMonitor", "CDMA CELL info" + cdmaCell.toString());
                    return cdmaCell.toString();
                }
            } else {
                GsmCellLocation gsmCell = (GsmCellLocation) cellLocation;
                if (gsmCell != null) {
                    LogCatUtil.debug("SSMonitor", "GSM CELL info" + gsmCell.toString());
                    return gsmCell.toString();
                }
            }
            return "";
        } catch (Throwable ex) {
            LogCatUtil.error((String) "SSMonitor", "getCellInfo exception:" + ex.toString());
        }
    }

    public void reportNetStateInfo() {
        try {
            if (!TextUtils.equals(TransportConfigureManager.getInstance().getStringValue((ConfigureItem) TransportConfigureItem.SINGAL_STATE_SWITCH), "T")) {
                LogCatUtil.debug("SSMonitor", "singal state is off");
                return;
            }
            long curTime = System.currentTimeMillis();
            if (curTime - g < this.h) {
                LogCatUtil.debug("SSMonitor", "lastReportTime: " + g + ",ignore this time");
                return;
            }
            g = curTime;
            final String cellInfo = getCellInfo();
            int netType = NetworkUtils.getNetworkType(TransportEnvUtil.getContext());
            if (netType == 3) {
                a(getWifiSignalLevel(), "", cellInfo);
            } else if (netType != 0 && a()) {
                NetworkAsyncTaskExecutor.schedule((Runnable) new Runnable() {
                    public void run() {
                        SignalStateHelper.a("", SignalStateHelper.this.getMobileSignalInfo(), cellInfo);
                        SignalStateHelper.this.b();
                    }
                }, 2, TimeUnit.SECONDS);
            }
        } catch (Throwable ex) {
            LogCatUtil.error((String) "SSMonitor", "reportNetStateInfo exception: " + ex.toString());
        }
    }

    /* access modifiers changed from: private */
    public static void a(String wifiSingal, String mobileSignalInfo, String cellInfo) {
        Performance pf = new TransportPerformance();
        pf.setSubType("SignalState");
        pf.setParam1(MonitorLoggerUtils.getLogBizType("SignalState"));
        pf.setParam2("INFO");
        if (!TextUtils.isEmpty(cellInfo)) {
            pf.getExtPramas().put(RPCDataItems.CELLINFO, cellInfo);
        }
        if (!TextUtils.isEmpty(wifiSingal)) {
            pf.getExtPramas().put(RPCDataItems.SIGNAL_STATE, wifiSingal);
        }
        if (!TextUtils.isEmpty(mobileSignalInfo)) {
            pf.getExtPramas().put(RPCDataItems.SIGNAL_STATE, mobileSignalInfo);
        }
        pf.getExtPramas().put(RPCDataItems.QOS, String.valueOf(AlipayQosService.getInstance().getQosLevel()));
        LogCatUtil.debug("SSMonitor", pf.toString());
        MonitorLoggerUtils.uploadPerfLog(pf);
    }
}
