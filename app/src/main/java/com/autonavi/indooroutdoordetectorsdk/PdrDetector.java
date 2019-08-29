package com.autonavi.indooroutdoordetectorsdk;

import android.os.Handler;
import com.autonavi.indoor.constant.Configuration;
import com.autonavi.indoor.pdr.PedProvider;
import com.autonavi.indoor.util.L;

class PdrDetector {
    boolean isAvailable = true;
    boolean isStarted = false;
    Configuration mConfiguration = null;
    Handler mHandler = null;
    PedProvider mPedProvider = null;

    PdrDetector() {
    }

    public void initDetect(Configuration configuration) {
        this.mConfiguration = configuration;
    }

    public boolean startDetect() {
        if (L.isLogging) {
            L.d((String) "start pdr");
        }
        if (this.mPedProvider == null) {
            try {
                GeoFenceHelper.logFile("PdrStart");
                JNIWrapper.jniSetFlag(System.currentTimeMillis(), "PdrStart");
                this.mPedProvider = PedProvider.getInstance();
                this.mPedProvider.setPublishOther(true);
                if (!this.mPedProvider.isInited()) {
                    this.mPedProvider.init(this.mConfiguration.context);
                }
                if (this.mPedProvider.isInited() && this.mPedProvider.getSensorType() == 2) {
                    this.mPedProvider.registerListener(this.mHandler);
                    this.isAvailable = true;
                    this.isStarted = true;
                    GeoFenceHelper.logFile("PdrStarted");
                    return true;
                }
            } catch (Throwable th) {
                if (L.isLogging) {
                    L.d(th);
                }
            }
            if (L.isLogging) {
                L.d((String) "start PDR detector failed.");
            }
            this.mPedProvider = null;
            this.isAvailable = false;
            this.isStarted = false;
        }
        return this.mPedProvider != null;
    }

    public void stopDetect() {
        if (L.isLogging) {
            L.d((String) "stop pdr");
        }
        try {
            if (this.mPedProvider != null) {
                GeoFenceHelper.logFile("PdrStop");
                JNIWrapper.jniSetFlag(System.currentTimeMillis(), "PdrStop");
                this.mPedProvider.setPublishOther(false);
                this.mPedProvider.unregisterListener(this.mHandler);
                this.mPedProvider = null;
            }
        } catch (Throwable th) {
            if (L.isLogging) {
                L.d(th);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("磁");
        sb.append(this.isAvailable ? "" : "无效");
        sb.append(this.mPedProvider != null ? "开启" : "关闭");
        return sb.toString();
    }
}
