package com.xiaomi.mipush.sdk;

import com.xiaomi.push.service.module.PushChannelRegion;

public class PushConfiguration {
    private boolean mGeoEnable;
    private boolean mOpenCOSPush;
    private boolean mOpenFCMPush;
    private boolean mOpenHmsPush;
    private PushChannelRegion mRegion;

    public static class PushConfigurationBuilder {
        /* access modifiers changed from: private */
        public boolean mGeoEnable;
        /* access modifiers changed from: private */
        public boolean mOpenCOSPush;
        /* access modifiers changed from: private */
        public boolean mOpenFCMPush;
        /* access modifiers changed from: private */
        public boolean mOpenHmsPush;
        /* access modifiers changed from: private */
        public PushChannelRegion mRegion;

        public PushConfiguration build() {
            return new PushConfiguration(this);
        }

        public PushConfigurationBuilder geoEnable(boolean z) {
            this.mGeoEnable = z;
            return this;
        }

        public PushConfigurationBuilder openCOSPush(boolean z) {
            this.mOpenCOSPush = z;
            return this;
        }

        public PushConfigurationBuilder openFCMPush(boolean z) {
            this.mOpenFCMPush = z;
            return this;
        }

        public PushConfigurationBuilder openHmsPush(boolean z) {
            this.mOpenHmsPush = z;
            return this;
        }

        public PushConfigurationBuilder region(PushChannelRegion pushChannelRegion) {
            this.mRegion = pushChannelRegion;
            return this;
        }
    }

    public PushConfiguration() {
        this.mRegion = PushChannelRegion.China;
        this.mGeoEnable = false;
        this.mOpenHmsPush = false;
        this.mOpenFCMPush = false;
        this.mOpenCOSPush = false;
    }

    private PushConfiguration(PushConfigurationBuilder pushConfigurationBuilder) {
        this.mRegion = pushConfigurationBuilder.mRegion == null ? PushChannelRegion.China : pushConfigurationBuilder.mRegion;
        this.mGeoEnable = pushConfigurationBuilder.mGeoEnable;
        this.mOpenHmsPush = pushConfigurationBuilder.mOpenHmsPush;
        this.mOpenFCMPush = pushConfigurationBuilder.mOpenFCMPush;
        this.mOpenCOSPush = pushConfigurationBuilder.mOpenCOSPush;
    }

    public boolean getGeoEnable() {
        return this.mGeoEnable;
    }

    public boolean getOpenCOSPush() {
        return this.mOpenCOSPush;
    }

    public boolean getOpenFCMPush() {
        return this.mOpenFCMPush;
    }

    public boolean getOpenHmsPush() {
        return this.mOpenHmsPush;
    }

    public PushChannelRegion getRegion() {
        return this.mRegion;
    }

    public void setGeoEnable(boolean z) {
        this.mGeoEnable = z;
    }

    public void setOpenCOSPush(boolean z) {
        this.mOpenCOSPush = z;
    }

    public void setOpenFCMPush(boolean z) {
        this.mOpenFCMPush = z;
    }

    public void setOpenHmsPush(boolean z) {
        this.mOpenHmsPush = z;
    }

    public void setRegion(PushChannelRegion pushChannelRegion) {
        this.mRegion = pushChannelRegion;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("PushConfiguration{");
        stringBuffer.append("Region:");
        stringBuffer.append(this.mRegion == null ? "null" : this.mRegion.name());
        stringBuffer.append('}');
        return stringBuffer.toString();
    }
}
