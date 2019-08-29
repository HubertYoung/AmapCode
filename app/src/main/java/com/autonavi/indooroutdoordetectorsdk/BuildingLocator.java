package com.autonavi.indooroutdoordetectorsdk;

import android.os.Handler;
import com.autonavi.indoor.constant.Configuration;
import com.autonavi.indoor.entity.ScanData;
import com.autonavi.indoor.provider.BLEProvider;
import com.autonavi.indoor.provider.WifiProvider;
import com.autonavi.indoor.util.L;

class BuildingLocator {
    BLEProvider mBleProvider = null;
    BuildingLocateThread mBuildingLocateThread = null;
    Configuration mConfiguration = null;
    Handler mHandler = null;
    long mLastRequestTime = 0;
    int mRequestCount = 0;
    boolean mWifiFailed = false;
    WifiProvider mWifiProvider = null;

    BuildingLocator() {
    }

    public void initDetect(Configuration configuration) {
        this.mConfiguration = configuration;
    }

    /* JADX WARNING: Removed duplicated region for block: B:72:0x0132  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean startDetect() {
        /*
            r6 = this;
            r0 = 0
            r1 = 1
            com.autonavi.indoor.provider.WifiProvider r2 = r6.mWifiProvider     // Catch:{ Throwable -> 0x0065 }
            if (r2 != 0) goto L_0x006d
            java.lang.String r2 = "WifiStart"
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper.logFile(r2)     // Catch:{ Throwable -> 0x0065 }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r4 = "WifiStart"
            com.autonavi.indooroutdoordetectorsdk.JNIWrapper.jniSetFlag(r2, r4)     // Catch:{ Throwable -> 0x0065 }
            com.autonavi.indoor.provider.WifiProvider r2 = com.autonavi.indoor.provider.WifiProvider.getInstance()     // Catch:{ Throwable -> 0x0065 }
            r6.mWifiProvider = r2     // Catch:{ Throwable -> 0x0065 }
            com.autonavi.indoor.provider.WifiProvider r2 = r6.mWifiProvider     // Catch:{ Throwable -> 0x0065 }
            r2.setScanMode(r1)     // Catch:{ Throwable -> 0x0065 }
            com.autonavi.indoor.provider.WifiProvider r2 = r6.mWifiProvider     // Catch:{ Throwable -> 0x0065 }
            boolean r2 = r2.isInited()     // Catch:{ Throwable -> 0x0065 }
            if (r2 != 0) goto L_0x0044
            com.autonavi.indoor.provider.WifiProvider r2 = r6.mWifiProvider     // Catch:{ Throwable -> 0x0065 }
            com.autonavi.indoor.constant.Configuration r3 = r6.mConfiguration     // Catch:{ Throwable -> 0x0065 }
            android.content.Context r3 = r3.context     // Catch:{ Throwable -> 0x0065 }
            int r2 = r2.init(r3)     // Catch:{ Throwable -> 0x0065 }
            if (r2 == 0) goto L_0x0044
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0065 }
            if (r3 == 0) goto L_0x0044
            java.lang.String r3 = "mWifiProvider.init(mConfiguration)="
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r2 = r3.concat(r2)     // Catch:{ Throwable -> 0x0065 }
            com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x0065 }
        L_0x0044:
            com.autonavi.indoor.provider.WifiProvider r2 = r6.mWifiProvider     // Catch:{ Throwable -> 0x0065 }
            boolean r2 = r2.isInited()     // Catch:{ Throwable -> 0x0065 }
            if (r2 == 0) goto L_0x0059
            com.autonavi.indoor.provider.WifiProvider r2 = r6.mWifiProvider     // Catch:{ Throwable -> 0x0065 }
            android.os.Handler r3 = r6.mHandler     // Catch:{ Throwable -> 0x0065 }
            r2.registerListener(r3)     // Catch:{ Throwable -> 0x0065 }
            java.lang.String r2 = "WifiStarted"
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper.logFile(r2)     // Catch:{ Throwable -> 0x0065 }
            goto L_0x006d
        L_0x0059:
            boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x0065 }
            if (r2 == 0) goto L_0x0062
            java.lang.String r2 = "WifiProvider init failed!!!!!!"
            com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x0065 }
        L_0x0062:
            r6.mWifiProvider = r0     // Catch:{ Throwable -> 0x0065 }
            goto L_0x006d
        L_0x0065:
            r2 = move-exception
            boolean r3 = com.autonavi.indoor.util.L.isLogging
            if (r3 == 0) goto L_0x006d
            com.autonavi.indoor.util.L.d(r2)
        L_0x006d:
            com.autonavi.indoor.provider.BLEProvider r2 = r6.mBleProvider     // Catch:{ Throwable -> 0x00e6 }
            if (r2 != 0) goto L_0x00ee
            java.lang.String r2 = "BleStart"
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper.logFile(r2)     // Catch:{ Throwable -> 0x00e6 }
            long r2 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00e6 }
            java.lang.String r4 = "BleStart"
            com.autonavi.indooroutdoordetectorsdk.JNIWrapper.jniSetFlag(r2, r4)     // Catch:{ Throwable -> 0x00e6 }
            com.autonavi.indoor.provider.BLEHelper r2 = new com.autonavi.indoor.provider.BLEHelper     // Catch:{ Throwable -> 0x00e6 }
            com.autonavi.indoor.constant.Configuration r3 = r6.mConfiguration     // Catch:{ Throwable -> 0x00e6 }
            android.content.Context r3 = r3.context     // Catch:{ Throwable -> 0x00e6 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x00e6 }
            boolean r2 = r2.hasBluetooth()     // Catch:{ Throwable -> 0x00e6 }
            if (r2 == 0) goto L_0x00da
            com.autonavi.indoor.provider.BLEProvider r2 = com.autonavi.indoor.provider.BLEProvider.getInstance()     // Catch:{ Throwable -> 0x00e6 }
            r6.mBleProvider = r2     // Catch:{ Throwable -> 0x00e6 }
            com.autonavi.indoor.provider.BLEProvider r2 = r6.mBleProvider     // Catch:{ Throwable -> 0x00e6 }
            boolean r2 = r2.isInited()     // Catch:{ Throwable -> 0x00e6 }
            if (r2 != 0) goto L_0x00b9
            com.autonavi.indoor.provider.BLEProvider r2 = r6.mBleProvider     // Catch:{ Throwable -> 0x00e6 }
            com.autonavi.indoor.constant.Configuration r3 = r6.mConfiguration     // Catch:{ Throwable -> 0x00e6 }
            android.content.Context r3 = r3.context     // Catch:{ Throwable -> 0x00e6 }
            int r2 = r2.init(r3)     // Catch:{ Throwable -> 0x00e6 }
            if (r2 == 0) goto L_0x00b9
            boolean r3 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x00e6 }
            if (r3 == 0) goto L_0x00b9
            java.lang.String r3 = "mBleProvider.init(mConfiguration)="
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x00e6 }
            java.lang.String r2 = r3.concat(r2)     // Catch:{ Throwable -> 0x00e6 }
            com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x00e6 }
        L_0x00b9:
            com.autonavi.indoor.provider.BLEProvider r2 = r6.mBleProvider     // Catch:{ Throwable -> 0x00e6 }
            boolean r2 = r2.isInited()     // Catch:{ Throwable -> 0x00e6 }
            if (r2 == 0) goto L_0x00ce
            com.autonavi.indoor.provider.BLEProvider r0 = r6.mBleProvider     // Catch:{ Throwable -> 0x00e6 }
            android.os.Handler r2 = r6.mHandler     // Catch:{ Throwable -> 0x00e6 }
            r0.registerListener(r2)     // Catch:{ Throwable -> 0x00e6 }
            java.lang.String r0 = "BleStarted"
            com.autonavi.indooroutdoordetectorsdk.GeoFenceHelper.logFile(r0)     // Catch:{ Throwable -> 0x00e6 }
            goto L_0x00ee
        L_0x00ce:
            boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x00e6 }
            if (r2 == 0) goto L_0x00d7
            java.lang.String r2 = "BleProvider init failed!!!!!!"
            com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x00e6 }
        L_0x00d7:
            r6.mBleProvider = r0     // Catch:{ Throwable -> 0x00e6 }
            goto L_0x00ee
        L_0x00da:
            boolean r2 = com.autonavi.indoor.util.L.isLogging     // Catch:{ Throwable -> 0x00e6 }
            if (r2 == 0) goto L_0x00e3
            java.lang.String r2 = "BLE not supported!"
            com.autonavi.indoor.util.L.d(r2)     // Catch:{ Throwable -> 0x00e6 }
        L_0x00e3:
            r6.mBleProvider = r0     // Catch:{ Throwable -> 0x00e6 }
            goto L_0x00ee
        L_0x00e6:
            r0 = move-exception
            boolean r2 = com.autonavi.indoor.util.L.isLogging
            if (r2 == 0) goto L_0x00ee
            com.autonavi.indoor.util.L.d(r0)
        L_0x00ee:
            r0 = 0
            com.autonavi.indoor.provider.WifiProvider r2 = r6.mWifiProvider     // Catch:{ Throwable -> 0x011f }
            if (r2 != 0) goto L_0x00f7
            com.autonavi.indoor.provider.BLEProvider r2 = r6.mBleProvider     // Catch:{ Throwable -> 0x011f }
            if (r2 == 0) goto L_0x0127
        L_0x00f7:
            com.autonavi.indoor.pdr.PedProvider r2 = com.autonavi.indoor.pdr.PedProvider.getInstance()     // Catch:{ Throwable -> 0x011f }
            boolean r3 = r2.isInited()     // Catch:{ Throwable -> 0x011f }
            if (r3 != 0) goto L_0x0108
            com.autonavi.indoor.constant.Configuration r3 = r6.mConfiguration     // Catch:{ Throwable -> 0x011f }
            android.content.Context r3 = r3.context     // Catch:{ Throwable -> 0x011f }
            r2.init(r3)     // Catch:{ Throwable -> 0x011f }
        L_0x0108:
            boolean r3 = r2.isInited()     // Catch:{ Throwable -> 0x011f }
            if (r3 == 0) goto L_0x0115
            int r2 = r2.getSensorType()     // Catch:{ Throwable -> 0x011f }
            r3 = 2
            if (r2 == r3) goto L_0x011d
        L_0x0115:
            com.autonavi.indoor.constant.Configuration r2 = r6.mConfiguration     // Catch:{ Throwable -> 0x011f }
            com.autonavi.indoor.constant.Configuration$PDRProvider r2 = r2.mPDRProvider     // Catch:{ Throwable -> 0x011f }
            com.autonavi.indoor.constant.Configuration$PDRProvider r3 = com.autonavi.indoor.constant.Configuration.PDRProvider.STEPANGLE     // Catch:{ Throwable -> 0x011f }
            if (r2 != r3) goto L_0x0127
        L_0x011d:
            r2 = 1
            goto L_0x0128
        L_0x011f:
            r2 = move-exception
            boolean r3 = com.autonavi.indoor.util.L.isLogging
            if (r3 == 0) goto L_0x0127
            com.autonavi.indoor.util.L.d(r2)
        L_0x0127:
            r2 = 0
        L_0x0128:
            r6.mRequestCount = r0
            r3 = 0
            r6.mLastRequestTime = r3
            com.autonavi.indooroutdoordetectorsdk.BuildingLocateThread r3 = r6.mBuildingLocateThread
            if (r3 != 0) goto L_0x013f
            com.autonavi.indooroutdoordetectorsdk.BuildingLocateThread r3 = new com.autonavi.indooroutdoordetectorsdk.BuildingLocateThread
            android.os.Handler r4 = r6.mHandler
            com.autonavi.indoor.constant.Configuration r5 = r6.mConfiguration
            android.content.Context r5 = r5.context
            r3.<init>(r4, r2, r5)
            r6.mBuildingLocateThread = r3
        L_0x013f:
            com.autonavi.indoor.provider.WifiProvider r2 = r6.mWifiProvider
            if (r2 != 0) goto L_0x0149
            com.autonavi.indoor.provider.BLEProvider r2 = r6.mBleProvider
            if (r2 == 0) goto L_0x0148
            goto L_0x0149
        L_0x0148:
            return r0
        L_0x0149:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.indooroutdoordetectorsdk.BuildingLocator.startDetect():boolean");
    }

    public void stopDetect() {
        if (L.isLogging) {
            L.d((String) "stop indoorlocation");
        }
        try {
            if (this.mWifiProvider != null) {
                GeoFenceHelper.logFile("WifiStop");
                JNIWrapper.jniSetFlag(System.currentTimeMillis(), "WifiStop");
                this.mWifiProvider.unregisterListener(this.mHandler);
                this.mWifiProvider = null;
                if (L.isLogging) {
                    L.d((String) "Stop WifiProvider");
                }
            }
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
        try {
            if (this.mBleProvider != null) {
                GeoFenceHelper.logFile("BleStop");
                JNIWrapper.jniSetFlag(System.currentTimeMillis(), "BleStop");
                this.mBleProvider.unregisterListener(this.mHandler);
                this.mBleProvider = null;
                if (L.isLogging) {
                    L.d((String) "Stop BleProvider");
                }
            }
        } catch (Throwable th2) {
            if (L.isLogging) {
                L.d(th2);
            }
        }
        try {
            if (this.mBuildingLocateThread != null) {
                this.mBuildingLocateThread.shutDown();
                this.mBuildingLocateThread = null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: 0000 */
    public int detect(ScanData scanData) {
        if (scanData.size() <= 0) {
            return 0;
        }
        if (this.mRequestCount <= 5) {
            this.mRequestCount++;
        } else if (System.currentTimeMillis() - this.mLastRequestTime < 10000) {
            return 0;
        }
        this.mLastRequestTime = System.currentTimeMillis();
        String url = this.mConfiguration.getUrl();
        if (this.mBuildingLocateThread != null) {
            this.mBuildingLocateThread.locate(url, scanData);
        }
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("粗定位：");
        String str = (this.mBleProvider == null || this.mWifiProvider == null) ? this.mWifiProvider != null ? "WIFI" : this.mBleProvider != null ? "蓝牙" : "未启动" : "蓝牙和WIFI";
        sb.append("Sensor:".concat(String.valueOf(str)));
        return sb.toString();
    }
}
