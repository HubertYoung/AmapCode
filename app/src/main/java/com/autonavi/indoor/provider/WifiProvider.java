package com.autonavi.indoor.provider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresPermission;
import com.alipay.sdk.util.e;
import com.amap.location.g.d.a;
import com.autonavi.indoor.entity.ScanData;
import com.autonavi.indoor.entity.ScanPair;
import com.autonavi.indoor.util.L;
import com.autonavi.indoor.util.MessageHelper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WifiProvider extends IProvider {
    public static final int SCAN_DELAY_FASTEST = 0;
    public static final int SCAN_DELAY_NORMAL = 1;
    private static volatile WifiProvider instance;
    long mLastReportTime = 0;
    long mLastStartTime = -1;
    int mLastWifiState = 3;
    int mNoScanTimes = 0;
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @RequiresPermission(allOf = {"android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE"})
        public void onReceive(Context context, Intent intent) {
            Handler handler = WifiProvider.this.mInnerHandler;
            if (!WifiProvider.this.mIsListening || handler == null) {
                if (L.isLogging) {
                    L.d((String) "wifi scan has been stoped");
                }
                return;
            }
            try {
                List<ScanResult> b = WifiProvider.this.mWifiManager.b();
                if (b != null) {
                    if (!b.isEmpty()) {
                        handler.obtainMessage(1201, b).sendToTarget();
                        if (WifiProvider.this.mScanMode == 0) {
                            handler.removeMessages(1203);
                            long currentTimeMillis = System.currentTimeMillis() - WifiProvider.this.mLastStartTime;
                            long j = 1000;
                            if (currentTimeMillis > 1000) {
                                WifiProvider.this.startScan("WifiManager.startScan by onReceive");
                            } else {
                                Message obtain = Message.obtain(handler, 1203);
                                if (currentTimeMillis > 0) {
                                    j = 1000 - currentTimeMillis;
                                }
                                if (L.isLogging) {
                                    L.d("send message request restart:".concat(String.valueOf(j)));
                                }
                                handler.sendMessageDelayed(obtain, j);
                            }
                        }
                        if (L.isLogging) {
                            StringBuilder sb = new StringBuilder("wifi onReceive:");
                            sb.append(b.size());
                            L.d(sb.toString());
                        }
                        return;
                    }
                }
                if (L.isLogging) {
                    L.d((String) "WifiManager.getScanResults is null");
                }
            } catch (Throwable th) {
                if (L.isLogging) {
                    L.d(th);
                }
            }
        }
    };
    BroadcastReceiver mReceiver2 = new BroadcastReceiver() {
        @RequiresPermission("android.permission.CHANGE_WIFI_STATE")
        public void onReceive(Context context, Intent intent) {
            if (!WifiProvider.this.mIsListening) {
                if (L.isLogging) {
                    L.d((String) "NOT listening");
                }
            } else if (WifiProvider.this.mOutterHandlers.isEmpty()) {
                if (L.isLogging) {
                    L.d((String) "No Handler");
                }
            } else {
                try {
                    if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
                        int intExtra = intent.getIntExtra("wifi_state", 0);
                        if ((intExtra == 1 || intExtra == 3) && intExtra != WifiProvider.this.mLastWifiState) {
                            if (intExtra == 1) {
                                if (L.isLogging) {
                                    L.d((String) "WIFI_STATE_CHANGED_ACTION WIFI DISABLED, wifi scan not valide");
                                }
                                MessageHelper.publishMessage(WifiProvider.this.mOutterHandlers, 501);
                            } else if (intExtra == 3) {
                                if (L.isLogging) {
                                    L.d((String) "WIFI_STATE_CHANGED_ACTION WIFI ENABLED. we are using wifi scan, now restart it.");
                                }
                                WifiProvider.this.reRegisterReceiver();
                            }
                            WifiProvider.this.mLastWifiState = intExtra;
                        }
                    }
                } catch (Throwable th) {
                    if (L.isLogging) {
                        L.d(th);
                    }
                }
            }
        }
    };
    volatile int mScanMode = 0;
    HashMap<Long, List<ScanResult>> mScanResults = new HashMap<>();
    int mUpdateInteval = 2000;
    int mWifiCheckInterval = 3000;
    a mWifiManager;

    static class InnerHandler extends Handler {
        private final WeakReference<WifiProvider> mParent;

        public InnerHandler(WifiProvider wifiProvider) {
            this.mParent = new WeakReference<>(wifiProvider);
        }

        @RequiresPermission("android.permission.CHANGE_WIFI_STATE")
        public void handleMessage(Message message) {
            WifiProvider wifiProvider = (WifiProvider) this.mParent.get();
            if (wifiProvider != null) {
                try {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (message.what == 1201) {
                        List list = (List) message.obj;
                        if (!list.isEmpty()) {
                            wifiProvider.mNoScanTimes = 0;
                            wifiProvider.mScanResults.put(Long.valueOf(currentTimeMillis), list);
                        }
                        if (currentTimeMillis - wifiProvider.mLastReportTime >= ((long) wifiProvider.mUpdateInteval)) {
                            HashMap hashMap = new HashMap();
                            for (List next : wifiProvider.mScanResults.values()) {
                                int size = next.size();
                                for (int i = 0; i < size; i++) {
                                    ScanResult scanResult = (ScanResult) next.get(i);
                                    if (scanResult.level < 0) {
                                        if (scanResult.level >= -128) {
                                            if (!hashMap.containsKey(scanResult.BSSID)) {
                                                hashMap.put(scanResult.BSSID, new ArrayList());
                                            }
                                            ((ArrayList) hashMap.get(scanResult.BSSID)).add(new ScanPair(scanResult));
                                        }
                                    }
                                    if (L.isLogging) {
                                        StringBuilder sb = new StringBuilder("Invalide rssi:");
                                        sb.append(scanResult.level);
                                        L.d(sb.toString());
                                    }
                                }
                            }
                            ArrayList arrayList = new ArrayList();
                            for (ArrayList arrayList2 : hashMap.values()) {
                                int i2 = 0;
                                for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                                    i2 += ((ScanPair) arrayList2.get(i3)).mRSSI;
                                }
                                ScanPair scanPair = (ScanPair) arrayList2.get(0);
                                scanPair.mRSSI = i2 / arrayList2.size();
                                arrayList.add(scanPair);
                            }
                            ScanData scanData = new ScanData(System.currentTimeMillis(), 0, arrayList);
                            wifiProvider.mScanResults.clear();
                            if (L.isLogging) {
                                StringBuilder sb2 = new StringBuilder("send wifi to host, combined wifi size:");
                                sb2.append(scanData.scans_.size());
                                sb2.append(", LastReportTime=");
                                sb2.append(wifiProvider.mLastReportTime);
                                L.d(sb2.toString());
                            }
                            MessageHelper.publishMessage(wifiProvider.mOutterHandlers, 1201, (Object) scanData);
                            wifiProvider.mLastReportTime = System.currentTimeMillis();
                            return;
                        }
                        if (L.isLogging) {
                            StringBuilder sb3 = new StringBuilder("wifi recived.but dont sent to host for elasped:");
                            sb3.append(currentTimeMillis - wifiProvider.mLastReportTime);
                            L.d(sb3.toString());
                        }
                        return;
                    }
                    if (message.what == 1200) {
                        int i4 = wifiProvider.mNoScanTimes;
                        wifiProvider.mNoScanTimes = i4 + 1;
                        if (i4 > 3) {
                            wifiProvider.reRegisterReceiver();
                            if (!WifiProvider.isWifiEnabled(wifiProvider.mContext)) {
                                MessageHelper.publishMessage(wifiProvider.mOutterHandlers, 501);
                                if (L.isLogging) {
                                    L.d((String) "WIFI not Enabled in timer");
                                }
                            } else {
                                MessageHelper.publishMessage(wifiProvider.mOutterHandlers, 503);
                                if (L.isLogging) {
                                    StringBuilder sb4 = new StringBuilder("WIFI not recieved in ");
                                    sb4.append(wifiProvider.mNoScanTimes);
                                    L.d(sb4.toString());
                                }
                            }
                            wifiProvider.mNoScanTimes = 0;
                        }
                        if (wifiProvider.mInnerHandler != null) {
                            wifiProvider.mInnerHandler.sendEmptyMessageDelayed(1200, (long) wifiProvider.mWifiCheckInterval);
                        }
                        if (wifiProvider.mScanMode == 1) {
                            wifiProvider.startScan("WifiManager.startScan by MSG_TIMER for SCAN_DELAY_NORMAL");
                        }
                    } else if (message.what == 1203) {
                        wifiProvider.startScan("WifiManager.startScan by MSG_WIFI_RESTART");
                    }
                } catch (Throwable th) {
                    if (L.isLogging) {
                        L.d(th);
                    }
                }
            }
        }
    }

    public void setScanMode(int i) {
        if (L.isLogging) {
            StringBuilder sb = new StringBuilder("setScanMode:(FASTEST0,NORMAL1)");
            sb.append(this.mScanMode);
            sb.append(", newMode:");
            sb.append(i);
            L.d(sb.toString());
        }
        this.mScanMode = i;
    }

    public static WifiProvider getInstance() {
        if (instance == null) {
            synchronized (WifiProvider.class) {
                try {
                    if (instance == null) {
                        instance = new WifiProvider();
                    }
                }
            }
        }
        return instance;
    }

    protected WifiProvider() {
    }

    public synchronized int init(Context context) {
        int i;
        if (context == null) {
            throw new IllegalArgumentException("PedProvider mConfiguration can not be initialized with null");
        }
        i = 0;
        try {
            if (this.mContext == null) {
                if (L.isLogging) {
                    L.d((String) "Initialize WifiProvider");
                }
                if (!isWifiEnabled(context)) {
                    if (L.isLogging) {
                        L.d((String) "WARNING: Initialize WifiProvider:MSG_WIFI_NOT_ENABLED");
                    }
                    i = 501;
                }
                this.mContext = context;
                this.mWifiManager = a.a(this.mContext);
                if (this.mWifiManager == null && L.isLogging) {
                    L.d((String) "Can't getSystemService of WIFI_SERVICE, WiFi not work!");
                }
            } else if (L.isLogging) {
                L.d((String) "Try to initialize PedProvider which had already been initialized before. To re-init PedProvider with new mConfiguration call PedProvider.destroy() at first.");
            }
        } catch (SecurityException unused) {
            i = 502;
        }
        return i;
    }

    public static boolean isWifiEnabled(Context context) {
        return ((WifiManager) context.getSystemService("wifi")).isWifiEnabled();
    }

    /* access modifiers changed from: 0000 */
    @RequiresPermission("android.permission.CHANGE_WIFI_STATE")
    public void reRegisterReceiver() {
        this.mContext.unregisterReceiver(this.mReceiver);
        this.mContext.registerReceiver(this.mReceiver, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
        startScan("WifiManager.startScan by reRegisterReceiver ");
    }

    /* access modifiers changed from: protected */
    @RequiresPermission("android.permission.CHANGE_WIFI_STATE")
    public int start() {
        if (this.mIsListening) {
            return 0;
        }
        if (this.mWifiManager == null) {
            if (L.isLogging) {
                L.d((String) "Can't getSystemService of WIFI_SERVICE, WiFi not work!");
            }
            return 504;
        }
        this.mContext.registerReceiver(this.mReceiver2, new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"));
        this.mLastWifiState = 3;
        if (!isWifiEnabled(this.mContext)) {
            this.mLastWifiState = 1;
            if (L.isLogging) {
                L.d((String) "Wifi not enabled!!");
            }
        }
        try {
            this.mInnerHandler = new InnerHandler(this);
            this.mContext.registerReceiver(this.mReceiver, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
            startScan("WifiManager.startScan by start");
            this.mLastReportTime = 0;
            this.mInnerHandler.sendEmptyMessageDelayed(1200, (long) this.mWifiCheckInterval);
            if (L.isLogging) {
                L.d((String) "WiFi Scan start finished...");
            }
            this.mIsListening = true;
            return 0;
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
            return 508;
        }
    }

    /* access modifiers changed from: protected */
    public boolean stop() {
        if (!this.mIsListening) {
            return true;
        }
        if (L.isLogging) {
            L.d((String) "STOP wifi Scan");
        }
        this.mIsListening = false;
        try {
            this.mContext.unregisterReceiver(this.mReceiver2);
            this.mContext.unregisterReceiver(this.mReceiver);
            this.mInnerHandler.removeMessages(1200);
            this.mInnerHandler = null;
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        if (L.isLogging) {
            L.d((String) "mWifiCheckTimer.cancel");
        }
        return true;
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public static boolean isWiFiEnabled(Context context) {
        return ((WifiManager) context.getSystemService("wifi")).getWifiState() == 3;
    }

    /* access modifiers changed from: 0000 */
    public void startScan(String str) {
        boolean a = this.mWifiManager.a();
        if (L.isLogging) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(", startScan:");
            sb.append(a ? "success" : e.b);
            L.d(sb.toString());
        }
        this.mLastStartTime = System.currentTimeMillis();
    }
}
